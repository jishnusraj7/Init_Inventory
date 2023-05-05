
//Controller for Table and Form 
mrpApp.controller('shops', shops);


function shops($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,$q,
		MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {

	$controller('DatatableController', {$scope: $scope});

	
//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			
			$scope.formData.code = response;});}

/*	set_sub_menu("#settings");		
	setMenuSelected("#shops_left_menu");*/			//active leftmenu
	manageButtons("add");

	$scope.formData = {};
	$scope.CustData ={} ;
	//$scope.show_table=true;
	$scope.formData.is_ar=false;
	$scope.CustData.customer_type=1;
	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
	$scope.departmentIdList=[];
	

	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.code_existing_validation = code_existing_validation;
	vm.showTable1=showTable1;
	vm.function_clear_select=function_clear_select;
	vm.selectedDep = {};
	vm.selectAll = false;
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	$scope.departmntsByShopId={};
	$scope.showAreaTxt=false;
	$scope.isSyncable=false;
	$scope.isEdit=false;
	$scope.dbServer=settings['database_server_name1'];
	$scope.dbUser=settings['database_user1'];
	
	if(companytype != 0)
	{
	$scope.show_table=false;
	$scope.show_form=true;
	$scope.showAreaTxt=true;
	/*manageButtons("save");
	$("#btnBack").hide();
	 $('.btnBack').hide();
	 $('.btnDiscard').hide();*/
	manageButtons("view");
	 $('.btnDiscard').hide();
	$("#btnBack").hide();
	 $('.btnBack').hide();
	 $scope.isEdit=true;
	 $scope.disable_all=true;
	 $scope.disable_code=true;
	 setFormDataForShops();
	
	}else if(companytype == 0)
		{
		$scope.show_table=true;
		manageButtons("add");
		
		}
	
	
	$scope.custTypeList=[];
	$http({
		method: 'GET',
		async: false,
		url : "../customertypes/customerTypeList",
		data: { applicationId: 1 }
	}).success(function (result) {
		
		$scope.custTypeList=result.customTypeList;
		$scope.custTypeList.splice(0,0,{id:"",name:"select"});
		
	});
	
	

	vm.dtInstance = {};	
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 
	
	function showTable1(shopId)
	{
		var shopId=shopId;
		
		urlString="../shopdepartments/deptdata?shopId="+shopId;
		vm.dtInstance1.reloadData(null, true);
	}
	
	
	
	var urlString="../shopdepartments/deptdata?shopId=0";
	vm.dtInstance1={};
	vm.dtOptions1 = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		$http.get(urlString).then(function(result) {
			defer.resolve(result.data);
		});
		return defer.promise;
	}).withPaginationType('full_numbers')
	.withDataProp('data')
    .withOption('paging', true)
    .withOption('info', false)
    .withOption('searching', true)
     .withOption('scrollY', 200)
     .withDisplayLength(10000)
   // .withOption('drawCallback', drawCallback)
    // .withOption('rowCallback', rowCallback_table1)
    .withOption('createdRow', function(row, data, dataIndex) {
	              		$compile(angular.element(row).contents())($scope);
	              	})
	              	.withOption('headerCallback', function(header) {
	              		if (!vm.headerCompiled) {
	              			vm.headerCompiled = true;
	              			$compile(angular.element(header).contents())($scope);
	              		}
	              	})
    // .withOption('drawCallback', drawCallback)	
	
	
	 vm.dtColumns1 = [
	 DTColumnBuilder.newColumn('id').withTitle('ID'),
	  DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','250px'),
      DTColumnBuilder.newColumn('name').withTitle('NAME'),
      DTColumnBuilder.newColumn(null).withTitle('').notSortable()
      .renderWith(function(data, type, full, meta) {
      	vm.selectedDep[full.id] = false;
      	if(full.shp_dep_id!="")
  		{
  		vm.selectedDep[full.id] = true;
  		/*$scope.departmentIdList.push(full.id);*/
  		}
        return '<input ng-disabled="disable_all" ng-model="item.selectedDep[' + full.id + ']" ng-click="toggleOne(item.selectedDep)" type="checkbox">';
    }).withOption('width','100px'),
	 
        		
	
	 ];
	
	

	/*vm.dtInstance1 = {};	
	var DataObj1 = {};		
	DataObj1.sourceUrl = "../department/getDataTableData";
	DataObj1.infoCallback = infoCallback1;
	DataObj1.rowCallback = rowCallback1;
	//vm.dtOptions1 = $scope.getMRP_DataTable_dtOptions(vm,DataObj1); 
	var  menuLength = [ [ 5, 10, 15, 20, 50,100,250 ], [ 5, 10, 15, 20,50,100,250] ]; 
	vm.dtOptions1 = DTOptionsBuilder.newOptions().withOption('ajax', {
		"url": DataObj1.sourceUrl,
		"data"  : function(data) {
			if(DataObj.hasOwnProperty("adnlFilters")){
				data.additionalFilters=DataObj.adnlFilters;
			}
			$scope.planify(data);  
		},
		error: function(response){
			location.href="errorpage";
		}
	}).withOption('rowCallback', DataObj1.rowCallback)
	.withOption('infoCallback',DataObj1.infoCallback)
	//.withOption('infoCallback',DataObj.fnFooterCallback)
	.withOption('order',[[1, (DataObj.order == undefined) ? "asc" : DataObj.order]])
	.withPaginationType('page_search')
	.withDataProp('data')
	.withOption('lengthMenu',menuLength).withOption('deferRender', true)
	.withOption('serverSide', true)
	.withOption('processing', true)
	.withDataProp('data')  
	.withOption(
			'language',
			{
				"sInfo"             : DATATABLE_CONSTANT.SINFO,
				"sInfoEmpty"        : DATATABLE_CONSTANT.SINFOEMPTY,
				"lengthMenu"        : DATATABLE_CONSTANT.LENGTHMENU,
				"search"            : DATATABLE_CONSTANT.SEARCH,
				"searchPlaceholder" : DATATABLE_CONSTANT.SEARCHPLACEHOLDER,
				"paginate"          : {
					"previous" : DATATABLE_CONSTANT.PREVIOUS,
					"next"     : DATATABLE_CONSTANT.NEXT,
					"search_page":DATATABLE_CONSTANT.SEARCH_PAGE,
					"first"	: DATATABLE_CONSTANT.FIRST,
					"last"	: DATATABLE_CONSTANT.LAST
				},
				"infoFiltered"      : DATATABLE_CONSTANT.INFOFILTERED,
			}).withPaginationType(DATATABLE_CONSTANT.PAGINATIONTYPE).withDisplayLength(250)
			.withDOM(DATATABLE_CONSTANT.WITHDOM)
			.withScroller()
        .withOption('deferRender', true)
        .withOption('scrollY', 200)
        .withOption('createdRow', function(row, data, dataIndex) {
            // Recompiling so we can bind Angular directive to the DT
            $compile(angular.element(row).contents())($scope);
        })
    .withOption('headerCallback', function(header) {
            if (!vm.headerCompiled) {
                // Use this headerCompiled field to only compile header once
                vm.headerCompiled = true;
                $compile(angular.element(header).contents())($scope);
            }
        });
	vm.dtColumns1 = [
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','100px'),
	                DTColumnBuilder.newColumn('name').withTitle('NAME'),
	                DTColumnBuilder.newColumn(null).withTitle('').notSortable()
    	            .renderWith(function(data, type, full, meta) {
    	            	
    	            	
    	            	vm.selectedDep[full.id] = false;
    	            	
    	                return '<input ng-disabled="disable_all" ng-model="item.selectedDep[' + full.id + ']" ng-click="toggleOne(item.selectedDep)" type="checkbox">';
    	            }).withOption('width','100px'),
    	            
    	           
    	
	                		];

	*/

	function rowCallback1(nRow, aData, iDisplayIndex, iDisplayIndexFull) {					//Rowcallback fun for Get Edit Data when clk on Code
		$('a', nRow).unbind('click');
		$('a', nRow).bind('click', function(e) {
			$scope.$apply(function(){
				$('tr.selected').removeClass('selected');
				if (e.target.id == "rcd_edit") {
					var rowData = aData;
					$(nRow).addClass('selected');	
					var current_row_index = 0;
					var test = vm.dtInstance.DataTable.rows();
					for(var i = 0;i<test[0].length;i++){
						if(test[0][i] == vm.dtInstance.DataTable.row(".selected").index()){
							current_row_index = i;
						}
					}
					edit(rowData,current_row_index,e);
				} 
			});
		});
		return nRow;
	}

	/*function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
	    var pageInfo = api.page.info();

	    return pageInfo.page+1 +" / "+pageInfo.pages;

	}*/

	function infoCallback1(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){
			return pageInfo.page +" / "+pageInfo.pages;
		}else{
			return pageInfo.page+1 +" / "+pageInfo.pages;
		}


	}
	
	vm.dtColumns = [
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','250px').renderWith(
	                		function(data, type, full, meta) {
	                			

	                			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}
	                			
	                			return '<a id="rcd_edit" class='+css+' ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
	                			+ data + '</a>';
	                		    
	                			
	                		}),
	                		DTColumnBuilder.newColumn('name').withTitle('NAME'),
	                		DTColumnBuilder.newColumn('address').withTitle('ADDRESS'),
	                		

	                		];

	function urlFormater(data) {

		return '<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
		+ data + '</a>';   
	}

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {					//Rowcallback fun for Get Edit Data when clk on Code
		$('a', nRow).unbind('click');
		$('a', nRow).bind('click', function(e) {
			$scope.$apply(function(){
				$('tr.selected').removeClass('selected');
				if (e.target.id == "rcd_edit") {
					var rowData = aData;
					$(nRow).addClass('selected');	
					var current_row_index = 0;
					var test = vm.dtInstance.DataTable.rows();
					for(var i = 0;i<test[0].length;i++){
						if(test[0][i] == vm.dtInstance.DataTable.row(".selected").index()){
							current_row_index = i;
						}
					}
					edit(rowData,current_row_index,e);
				} 
			});
		});
		return nRow;
	}

	/*function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
	    var pageInfo = api.page.info();

	    return pageInfo.page+1 +" / "+pageInfo.pages;

	}*/

	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){
			return pageInfo.page +" / "+pageInfo.pages;
		}else{
			return pageInfo.page+1 +" / "+pageInfo.pages;
		}


	}


	 $scope.toggleOne=function(selectedItems) {
		$scope.seletedIds=selectedItems;
	       
		}

	$rootScope.$on('reloadDatatable',function(event){					//reload Datatable
		reloadData();
	});

	$rootScope.$on('hide_table',function(event){
		showTable(event);
	});

	$rootScope.$on('hide_form',function(event){
		$scope.show_table=true;
		$scope.show_form=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
	});

	function showTable(event){
		$scope.show_table=false;
		$scope.show_form=true;
	}


	function reloadData() {
		vm.dtInstance.reloadData(null, true);
	}


	function edit(row_data,cur_row_index,event) {	
		
		
		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function
		/*var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;*/
		var row_count = vm.dtInstance.DataTable.rows().data();
		row_count=row_count.length;	
		if(row_count == 1){
			$rootScope.$emit("disable_next_btn");
			$rootScope.$emit("disable_prev_btn");
		}else{
			if(cur_row_index == 0){
				$rootScope.$emit("enable_next_btn");
				$rootScope.$emit("disable_prev_btn");
			}else if(row_count-1 == cur_row_index){
				$rootScope.$emit("disable_next_btn");
				$rootScope.$emit("enable_prev_btn");
			}
		}
		
		showTable();
		clearform();
		function_clear_select();
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,description:row_data.description,
				address:row_data.address,city:row_data.city,country:row_data.country,state:row_data.state,zip_code:row_data.zip_code,
				created_by:row_data.created_by,created_at:row_data.created_at,area_id:row_data.area_id,
				cst_no:row_data.cst_no,company_license_no:row_data.company_license_no,company_tax_no:row_data.company_tax_no,
				phone:row_data.phone,email:row_data.email,email_subscribe:row_data.email_subscribe};
		
		$scope.formData.email_subscribe= ($scope.formData.email_subscribe == 1) ? true:false;
		$http({
			url : 'editData',
			method : "GET",
			params : {id:row_data.id},
		}).then(function(response) {
			
			$scope.CustData ={} ;
			
			
			
	

			if(response.data.data.length > 0 ){
				$scope.is_customer=true;
				$scope.CustData.is_ar=(response.data.data[0].is_ar==1)?true:false;
				$scope.CustData.ar_code=response.data.data[0].ar_code;
				$scope.CustData.customer_type=response.data.data[0].customer_type;
				$scope.CustData.card_no=response.data.data[0].card_no;
			}else{
				$scope.is_customer=false;
				$scope.CustData ={} ;
			}
			//$scope.CustData=response.data.data;
			
		
		
		});
		
		
		$scope.dbServer=settings['database_server_name1'];
		$scope.dbUser=settings['database_user1'];;
		$scope.dbDatabase=$('#client_name').val().substr(1)+"_"+row_data.code;;
		$scope.disable_all = true;
		$scope.disable_code=true;
		$scope.hide_code_existing_er = true;
		
		$scope.isEdit=true;
		//var depIds2=$scope.getDepIdsByShopId(row_data.id);
		 showTable1(row_data.id);
		
	}
	function setFormDataForShops()
	{
		//alert(settings['currentcompanyid1']);
		$scope.formData.id=settings['currentcompanyid1'];
		$http({
			method : 'GET',
			url : "../shops/getShopDataById",
		    params:{shopId:$scope.formData.id}
		}).success(function(response) {
			
			
			$scope.formData = {id:settings['currentcompanyid1'],name:response.data1[0].name,code:response.data1[0].code,description:response.data1[0].description,
					address:response.data1[0].address,city:response.data1[0].city,country:response.data1[0].country,
					state:response.data1[0].state,zip_code:response.data1[0].zip_code,
					created_by:response.data1[0].created_by,created_at:response.data1[0].created_at,is_synchable:response.data1[0].is_synchable};
			if($scope.formData.is_synchable ==1){$scope.isSyncable =true;}else{$scope.isSyncable =false;}
			if(response.data2.length != 0)
				{
			     $scope.formData.area_id=parseInt(response.data2[0].area_id);
			     $scope.areaName=response.data2[0].name;
				}
			
			$scope.seletedIds=$scope.getDepIdsByShopId($scope.formData.id);
		    // showTable1(settings['currentcompanyid1']);
			$scope.itemThumb=response.imagPath[0].imgPath;
			 /*  $scope.depdata=response.data3;
			   vm.selectedDep;
			   
			   
			   for (var id in vm.selectedDep) {
		           if (vm.selectedDep.hasOwnProperty(id)) {
		        	   
		        	   for(var i=0;i<$scope.depdata.length;i++)
		        		   {
		        		   if(id==$scope.depdata[i].department_id) {
		                      	
		        			   vm.selectedDep[id]=true;
		                       $scope.departmentIdList.push($scope.depdata[i].department_id);
		                   }
		        		   }
		               
		           }
		       }*/
			if($scope.itemThumb!="")
			{
			var time=new Date().getTime() / 1000;
			
			     $("#imgshw").empty();
	             $("#imgshw").html('<img src="'+$scope.itemThumb+'" style="max_height:70px" >');
	          	 $("#imgdiv").addClass('fileupload-exists').removeClass('fileupload-new');
	         
			}
		else
			{
			 $("#imgshw").empty();
			 $("#imgdiv").addClass('fileupload-new').removeClass('fileupload-exists');

			}
			
			
		});
		
		
	}
	
	
	$scope.getDepIdsByShopId=function(id)
	{
		$http({
			method : 'GET',
			url : "../shopdepartments/getDepIdsByShopId",
		    params:{curshopId:id}
		}).success(function(result) {
			$scope.departmntsByShopId = result.depDtls;
			$scope.depArray=[];
			
			for(var i=0;i<$scope.departmntsByShopId.length;i++)
				{
				 $scope.depArray[i]=parseInt($scope.departmntsByShopId[i].department_id);
				}
		/*	console.log($scope.depArray);
			console.log("selected dep"+vm.selectedDep);*/
			/*for (var key in vm.selectedDep)
			{
				if(vm.selectedDep.hasOwnProperty(key))
					{
					
				   	vm.selectedDep[key]=false;
					
					}
			}*/
			for (var key in vm.selectedDep)
			{
				
				if(vm.selectedDep.hasOwnProperty(key))
					{
					for(var i=0;i<$scope.depArray.length;i++){
					if(key==$scope.depArray[i]){
						vm.selectedDep[key]=true;
						 $scope.departmentIdList.push($scope.depArray[i]);
					}}
					}
				
			}
		});
		return vm.selectedDep;
	}
	
	  $rootScope.$on("excel_Print",function(){
			$scope.ExcelSheet();
			
		});
		
		$scope.ExcelSheet=function()
		{
			window.open("ShopsReport.xls");
		}
 
	
//Area dropdown
	$http({
		method: 'GET',
		url : "getAreaDropDown"
	}).success(function (result) {
		$scope.areaList = result.areaList;

		$scope.areaList.splice(0,0,{id : 0 ,name : "select"});
		$scope.formData.area_id='0';
		console.log($scope.areaList);
		
	}); 

//	Delete Function
	$rootScope.$on("fun_delete_current_data",function(event){			
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).cancel('No').ok(
		'Yes')
		;
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			$http({
				url : 'delete',
				method : "POST",
				params : {id:$scope.formData.id},
			}).then(function(response) {
				if(response.data==0)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Department "+FORM_MESSAGES.ALREADY_USE+"");
				}

				else if(response.data == 1)
				{
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);


					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
					$scope.disable_all = true;
					$scope.disable_code = true;
					vm.dtInstance.reloadData(null, true);
					vm.dtInstance1.reloadData(null, true);
					$(".acontainer input").attr('disabled', true);
					reloadData();
				}
				else{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event)
					);}

			}, function(response) { // optional
				
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Delete failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
				
			});

		}, function() {

		});
	});

	
	function select_next_prev_row(index){										//set NEXT-PREV Data In Form Atfer Deleteion

		var cur_index=index;
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		if(row_count != 1){
			var row_data = vm.dtInstance.DataTable.rows().data();
			if(index == row_count-1){
				index = index-1;
			}else{
				index = index+1;
			}
			var selcted_row_data = vm.dtInstance.DataTable.rows(index).data();
			edit(selcted_row_data[0],index);
			$rootScope.$emit("next_formdata_set",index);
			if(cur_index !=0){
				if(cur_index-1 == 0){
					$rootScope.$emit("disable_prev_btn");

				}else if(index+1 == row_count-1){
					$rootScope.$emit("disable_next_btn");

				}
			}else if(cur_index == 0){
				if(index-1 == 0){
					$rootScope.$emit("disable_prev_btn");

				}else if(index+1 == row_count-1){
					$rootScope.$emit("disable_next_btn");

				}
			}


		}else if(row_count == 1){
			$scope.show_table=true;
			$scope.show_form=false;
			manageButtons("add");
		}
		return index;

	}


	function view_mode_aftr_edit(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$scope.disable_code = true;
	}

	function d2(n) {
		if(n<9) return "0"+n;
		return n;
	}
	today = new Date();

	$rootScope.$on('fun_save_data',function(event){		//Save Function
		
		if($scope.seletedIds!=undefined)
			{
			$scope.departmentIdList=[];
		for (var id in $scope.seletedIds) {
		    if ($scope.seletedIds.hasOwnProperty(id)) {
		    	if($scope.seletedIds[id] == true)
		    		{
		        	$scope.departmentIdList.push(parseInt(id));}
		       
		        }
		}}
				//alert($scope.departmentIdList);
	//	$scope.formData.depList=$scope.departmentIdList;
	if(companytype == 0){
		$scope.departmentIdList=[];
	    for (var id in vm.selectedDep) {
	    if (vm.selectedDep.hasOwnProperty(id)) {
	    	if(vm.selectedDep[id] == true)
	    		{
	        	$scope.departmentIdList.push(parseInt(id));}
	       
	        }
	}}
			
if (code_existing_validation($scope.formData)) {
	$scope.is_customer= ($scope.is_customer == true) ? 1:0;
	$scope.formData.email_subscribe= ($scope.formData.email_subscribe == true) ? 1:0;
		$scope.Quetable.shopId=settings['currentcompanyid1'];
		$scope.Quetable.origin=settings['currentcompanycode1'];
		
		$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
		if($scope.formData.created_by == null || $scope.formData.created_by ==undefined ||$scope.formData.created_by =="")
		{
	$scope.formData.created_by=parseInt(strings['userID']);
		}
	if($scope.formData.created_at == null || $scope.formData.created_at ==undefined ||$scope.formData.created_at =="")
		{
	$scope.formData.created_at = today.getFullYear() + "-" + d2(parseInt(today.getMonth()+1)) + "-" + d2(today.getDate()) + " " + d2(today.getHours()) + ":" + d2(today.getMinutes()) + ":" + d2(today.getSeconds());
		}
	
	if($scope.formData.id!="" && $scope.formData.id!=undefined)
		{
		$scope.formData.updated_at=today.getFullYear() + "-" + d2(parseInt(today.getMonth()+1)) + "-" + d2(today.getDate()) + " " + d2(today.getHours()) + ":" + d2(today.getMinutes()) + ":" + d2(today.getSeconds());
		$scope.formData.updated_by=parseInt(strings['userID']);
		
		}
	
	
		$scope.formData.compnyId=settings['currentcompanyid1'];
		
		if(companytype == 0){
			$scope.formData.is_hq=true;
			var client_name=$('#client_name').val().substr(1)+"_"+$scope.formData.code;
			$scope.formData.db_database=client_name;
			}
		else{$scope.formData.is_hq=false;};
		
			/*$http({
				url : 'saveShop',
				method : "POST",
				params : $scope.formData,
				data : inList,
			}).then(function(response){
				if(response.data == 1){
				var DataObj = {};	
				DataObj.alertClass   = "alert-box";
				DataObj.alertStatus  = false;
				DataObj.divId        = "#alertMessageId";	
				if($scope.formData.id !=undefined)
				{
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
				}
				else
				{
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);

				}




				if($scope.formData.id !=undefined){
					view_mode_aftr_edit();
				}else{
					$scope.formData ={};
					$scope.departmentIdList=[];
					$scope.fun_get_pono();
				}
				reloadData();
				$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
				$scope.hide_code_existing_er = true;
			}else{
				
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Save failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
			}
				
			}, function(response) { // optional
				
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Save failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
				
			});*/
		//}
		
		 var fdata = new FormData();
			
			if(companytype != 0)
				{
				var file = $scope.myFile;
				fdata.append("imgFile",file);
				if($scope.isSyncable == true){$scope.formData.is_synchable=1;}else{$scope.formData.is_synchable=0;}
				}
			$scope.CustData.id=null;
			$scope.CustData.is_ar= ($scope.CustData.is_ar == true) ? 1:0;
			$scope.CustData.address=$scope.formData.address;
			$scope.CustData.is_valid=1;
		
			$scope.formData.is_customer=($scope.is_customer==true) ? 1:0;
			
	    var data = JSON.stringify({itemData:$scope.formData,depData1:$scope.departmentIdList,Quetable:$scope.Quetable,CustomerData:$scope.CustData});
		fdata.append("data",data);
		 $http.post("saveShop",fdata,{
				transformRequest : angular.identity,
				params:$scope.formData,
				headers : {
				'Content-Type' : undefined
				}
				}).success(function(response) {
					$scope.CustData.is_ar= ($scope.CustData.is_ar == 1) ? true:false;
					$scope.is_customer= ($scope.is_customer == 1) ? true:false;
					$scope.formData.email_subscribe= ($scope.formData.email_subscribe == 1) ? true:false;
				
					if(response == "status:success"){
					var DataObj = {};	
					DataObj.alertClass   = "alert-box";
					DataObj.alertStatus  = false;
					DataObj.divId        = "#alertMessageId";	
					if(companytype != 0){
						$("#prev_NaN").hide();
						$("#next_NaN").hide();
						$("#btnBack").hide();}
					if($scope.formData.id !=undefined)
					{
						if(companytype != 0){
							$("#prev_NaN").hide();
							$("#next_NaN").hide();}
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
					}
					else
					{
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);

					}




					if($scope.formData.id !=undefined){
						view_mode_aftr_edit();
						if(companytype != 0){
							 $('.position_prev_next_btn_form').hide();
							 $('.btnBack').hide();
							 $('.btnDiscard').hide();}
						
					}else{
						$scope.formData ={};
						$scope.is_customer=false;
						$scope.formData.email_subscribe=false;
						$scope.departmentIdList=[];
						$scope.departmntsByShopId={};
						vm.selectedDep={};
						$scope.CustData ={} ;
						$scope.seletedIds=[];
						$scope.fun_get_pono();
						$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
					}
					reloadData();
					if(companytype == 0){
					 vm.dtInstance1.reloadData(null, true);
					}
					$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
					$scope.hide_code_existing_er = true;
				}else{
					
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Save failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
					$scope.departmentIdList=[];
				}
			}).error(function(response) { // optional

				
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Save failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
				

			});
	}
	});

	$rootScope.$on("fun_discard_form",function(event){				//Discard function

		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(FORM_MESSAGES.DISCARD_WRNG).targetEvent(event).cancel('No').ok(
		'Yes')
		;
		$mdDialog.show(confirm).then(function() {
			var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			if($scope.formData.id == undefined){
				$scope.formData ={};
				$scope.fun_get_pono();
				//$scope.formData.parent_id = $scope.sample[0].id;
				$scope.hide_code_existing_er = true;

			}else{
				var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
				edit(row_data[0],cur_row_index);
			}
			clearform();
			function_clear_select();
		});
	});

	$rootScope.$on("fun_enable_inputs",function(){
		$scope.disable_all = false;
		if(companytype !=0)
		{
		setFormDataForShops();
		}
		//var depIds2=$scope.getDepIdsByShopId(row_data.id);
	});

	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});
	


	$rootScope.$on("fun_clear_form",function(){
		//alert(settings['database_server_name1']);
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		function_clear_select();
		$scope.formData = {};
		$scope.CustData ={} ;
		$scope.CustData.customer_type=$scope.custTypeList[0].id;
		$scope.CustData.is_ar=false;
		$scope.departmntsByShopId={};
		$scope.departmentIdList=[];
		$scope.fun_get_pono();
		vm.selectedDep={};
	
		$scope.hide_code_existing_er = true;
		$scope.formData.area_id='0';
		$scope.isEdit=false;
		urlString="../shopdepartments/deptdata?shopId=0"
		$scope.isSyncable=false;
		$scope.dbServer=settings['database_server_name1'];
		$scope.dbUser=settings['database_user1'];
		$scope.is_customer=false;
		$scope.formData.email_subscribe=false;
		//$scope.formData.parent_id = $scope.sample[0].id;
	});

	$rootScope.$on("fun_next_rowData",function(e,id){

		var current_row_index = 0;
		var dataIndex = vm.dtInstance.DataTable.rows();
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(current_row_index == 0){
			$rootScope.$emit("enable_prev_btn");
		}

		if(row_data.length == current_row_index+1){
			$rootScope.$emit("disable_next_btn");
		}
		var next_row_index = current_row_index+1;
		if(row_data[next_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][next_row_index]).data();
			edit(selcted_row_data[0],next_row_index);
			$rootScope.$emit("next_formdata_set",next_row_index);
		}

	});

	$rootScope.$on("fun_prev_rowData",function(e,id){
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var dataIndex = vm.dtInstance.DataTable.rows();
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(row_count-1 == current_row_index){
			$rootScope.$emit("enable_next_btn");
		}
		var prev_row_index = current_row_index-1;
		if(row_data[prev_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][prev_row_index]).data();
			edit(selcted_row_data[0],prev_row_index);
			$rootScope.$emit("next_formdata_set",prev_row_index);
		}
		if(current_row_index-1 == 0){
			$rootScope.$emit("disable_prev_btn");
		}

	});

	// Validation 

	function code_existing_validation(data){
		var flg = true;

		var row_data = vm.dtInstance.DataTable.rows().data();
		if(data.id == undefined || data.id == ""){
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#code").select();
			}


		}

		if(validation() == false){
			flg = false;	}
		if(validateEmail()==false){
			flg= false;
		}
		/*if($scope.departmentIdList.length == 0){
			$("#divErrormsg2").text('Please select a department');
			$("#divErrormsg2").show();
			flg = false;
		}else{
			$("#divErrormsg2").hide();
		}*/
		if($scope.formData.area_id == undefined || $scope.formData.area_id == '0'){
			$("#form_div_area").addClass("has-error");
			$("#form_div_area_error").show();

			flg = false;
		}else{
			$("#form_div_area").removeClass("has-error");
			$("#form_div_area_error").hide();
		}
		if($scope.is_customer == true && ($scope.CustData.customer_type == "" || $scope.CustData.customer_type ==null
				|| $scope.CustData.customer_type == undefined))
			{
			$("#form_div_custtype").addClass("has-error");
			flg=false;
			}else
				{
				$("#form_div_custtype").removeClass("has-error");
				}
		if($scope.CustData.is_ar == true && ($scope.CustData.ar_code == "" || $scope.CustData.ar_code==undefined))
			{
			$("#form_div_ar_code").addClass("has-error");
			flg=false;
			}
		else
			{
			$("#form_div_ar_code").removeClass("has-error");
			}
		/*if( $scope.myFile!=undefined && $scope.myFile!="" && companytype != 0)
		{
			if($scope.myFile.size != 70*49)
			{
				$("#imgshw").css("border-color","red");
				$rootScope.$broadcast('on_AlertMessage_ERR','Select image size of 70*49');
				flg = false;
			}
		}*/
		if(companytype != 0)
			{
			var file, img;
		    if ((file = $scope.myFile)) {
		        img = new Image();
		        img.onload = function () {
		            //alert("Width:" + this.width + "   Height: " + this.height);//this will give you image width and height and you can easily validate here....
		        	if(!(this.width == 70 && this.height ==49))
		        		{
		        		flg=false;
		        		$("#imgshw").css("border-color","red");
						$rootScope.$broadcast('on_AlertMessage_ERR','Select image of dimension 70*49');
		        		}else
		        			{
		        			$("#imgshw").css("border","none");
		        		    $("#imgdiv").addClass('fileupload-exists').removeClass('fileupload-new');
		        			flg=true;
		        			}
		        };
		        img.src =  window.URL.createObjectURL(file);
		      }
			}

		if(flg==false)
		{
			focus();
		}

		return flg;

	}


	function function_clear_select(){
		$("#form_div_dept_type").removeClass("has-error");
		$("#form_div_dept_type_error").hide();
		$("#divErrormsg2").hide();
		$("#form_div_area").removeClass("has-error");
		$("#form_div_area_error").hide();
	}
	$scope.filterArea=function()
	{
		/*alert($scope.formData.area_id);*/
	}
	$scope.removeImage=function()
	{
		$scope.myFile="";
		$("#imgshw img").remove();
		$("#defaultImage img").remove();
		var imgName='logo_'+settings['currentcompanyid1']+'.jpg';
		$("#item_thumb").addClass('fileupload-new').removeClass('fileupload-exists');
		  $http({
				url : 'deleteLogo',
				method : "POST",
				params :{imgName:imgName} ,
			}).then(function(response) {
				$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
				
			}, function(response) { // optional
		
			});

	}
	
	$scope.changeCustmr=function()
	{
		
		if($scope.is_customer == false)
			{
			$scope.CustData={};
			$scope.CustData.customer_type=$scope.custTypeList[0].id;
			$scope.CustData.is_ar=false;
			
			}
	}
	$scope.changeARCustmr=function(){
		if($scope.CustData.is_ar == false)
			{
			$scope.CustData.ar_code="";
			}
	}
	

}
mrpApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);


