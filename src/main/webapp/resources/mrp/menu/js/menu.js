
mrpApp.controller('menu', menu);

function menu($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,$q,
		         MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {
	
	
/* ##  extend DatatableController ##  */
$controller('DatatableController', {$scope: $scope});

/*$("#form_div_description").hide();*/

       /* set_sub_menu("#settings");						
        setMenuSelected("#menu_left_menu");*/			//active leftmenu
        manageButtons("add");

//    	generate number
    	$scope.fun_get_pono=function(){
    		$http.get('getCounterPrefix')
    		.success(function (response) {
    			
    			$scope.formData.code = response;});}
        
		$scope.formData = {};
		
		$scope.show_table=true;
		$scope.show_form=false;
		$scope.hide_code_existing_er = true;
		$scope.Quetable={};
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
		$scope.formData.enable_h1_button=false;
		$scope.formData.enable_h2_button=false;
		$scope.formData.enable_h3_button=false;
		$scope.formData.is_default_menu=false;
		$scope.formData.is_active=false;
	
	

		
		var vm = this;	 
		$scope.formData = {};
		$scope.show_table=true;
		$scope.show_form=false;
		$scope.hide_code_existing_er = true;
		vm.reloadData = reloadData;
		vm.showTable1=showTable1;
		vm.showTable  = showTable;
		vm.view_mode_aftr_edit = view_mode_aftr_edit;
		vm.code_existing_validation = code_existing_validation;
		$scope.formData.is_valid=false;
		$scope.formData.is_refundable=false;

		vm.dtInstance = {};	
		var DataObj = {};		
		DataObj.sourceUrl = "getDataTableData";
		DataObj.infoCallback = infoCallback;
		DataObj.rowCallback = rowCallback;
		vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 				
	    vm.dtColumns = [
			          DTColumnBuilder.newColumn('code').withOption('width','250px').withTitle('CODE').withOption('width','150px').renderWith(
					           function(data, type, full, meta) {
			               			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}

						              return urlFormater(data);  
					            }),
			          DTColumnBuilder.newColumn('name').withTitle('NAME'),
			         DTColumnBuilder.newColumn('description').withTitle('DESCRIPTION').withOption('width','280px'),
			
		               ];

	   //department table 
	    
	    vm.selectedDep = {};
		$scope.departmentIdList=[];
		$scope.departmentlist=[];
	    
	    
		
		function showTable1(menuId)
		{
			var menuId=menuId;
			
			urlString="../menudepartment/deptdata?menuId="+menuId;
			vm.dtInstance1.reloadData(null, true);
		}
		
		
		
		
		var urlString="../menudepartment/deptdata?menuId=0";
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
          	if(full.mdpId!="")
          		{
          		vm.selectedDep[full.id] = true;
          		}

              return '<input ng-model="item.selectedDep[' + full.id + ']"  ng-click="toggleOne(item.selectedDep)" type="checkbox" ng-disabled="disable_all">';
          }).withOption('width','100px'),
		 
	        		
		
		 ];
		
		
		
		
	    
	   /* vm.dtInstance1 = {};	
		var DataObj1 = {};		
		DataObj1.sourceUrl = "../menudepartment/getDataTableData";
		//DataObj1.infoCallback = infoCallback1;
		//DataObj1.rowCallback = rowCallback1;
		//vm.dtOptions1 = $scope.getMRP_DataTable_dtOptions(vm,DataObj1); 
		var  menuLength = [ [ 5, 10, 15, 20, 50,100,250 ], [ 5, 10, 15, 20,50,100,250] ]; 
		vm.dtOptions1 = DTOptionsBuilder.newOptions().withOption('ajax', {
			"url": DataObj1.sourceUrl,
			"data"  : function(data) {
				if(DataObj1.hasOwnProperty("adnlFilters")){
					data.additionalFilters=DataObj1.adnlFilters;
				}
				$scope.planify(data);  
			},
			error: function(response){
				location.href="errorpage";
			}
		})
		//.withOption('rowCallback', DataObj1.rowCallback)
		//.withOption('infoCallback',DataObj1.infoCallback)
		.withOption('order',[[1, (DataObj1.order == undefined) ? "asc" : DataObj1.order]])
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
				}).withPaginationType(DATATABLE_CONSTANT.PAGINATIONTYPE).withDisplayLength(5)
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
		                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','250px'),
		                DTColumnBuilder.newColumn('name').withTitle('NAME'),
		                DTColumnBuilder.newColumn(null).withTitle('').notSortable()
	    	            .renderWith(function(data, type, full, meta) {
	    	            	vm.selectedDep[full.id] = false;
	    	            	if(full.mdpId!="")
	    	            		{
	    	            		vm.selectedDep[full.id] = true;
	    	            		}
            	$scope.one(full.id);
	    	                return '<input ng-model="item.selectedDep[' + full.id + ']"  ng-click="toggleOne(item.selectedDep)" type="checkbox" ng-disabled="disable_all">';
	    	            }).withOption('width','100px'),
	    	            
		                		
	    	
		                		];    */
		
	    
		 $scope.toggleOne=function(selectedItems) {
			$scope.seletedIds=selectedItems;
			      
			
				
				}    
	    
		 $rootScope.$on("excel_Print",function(){
				$scope.ExcelSheet();
				
			});
			
			$scope.ExcelSheet=function()
			{
				window.open("MenuReport.xls");
			}
	    
	    
	    
	 
	   function urlFormater(data) {
			
		  return '<a id="rcd_edit" class="'+css+'" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
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
						$("#show_form").val(1);
						edit(rowData,current_row_index,e);
					} 
				});
			});
			return nRow;
		}

	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
	    var pageInfo = api.page.info();
	    
	    return pageInfo.page+1 +" / "+pageInfo.pages;
	    
	}

	
	$rootScope.$on('reloadDatatable',function(event){					//reload Datatable
		reloadData();
	});
	
	$rootScope.$on('hide_table',function(event){
		showTable(event);
	});
	
	$rootScope.$on('hide_form',function(event){
		$("#show_form").val(0);
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
		
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		vm.dtInstance1.reloadData(null, true);
		$scope.tableShwerr=false;
		 $scope.selectedIndexTab=0;
		$http({
			url : 'editData',
			method : "GET",
			params : {id:row_data.id},
		}).then(function(response) {
		$scope.formData = {id:response.data.editDet.id,name:response.data.editDet.name,code:response.data.editDet.code,
				description:response.data.editDet.description,is_default_menu:response.data.editDet.is_default_menu,
				enable_h1_button:response.data.editDet.enable_h1_button,enable_h2_button:response.data.editDet.enable_h2_button,
				enable_h3_button:response.data.editDet.enable_h3_button,color:response.data.editDet.color,is_active:response.data.editDet.is_active}
					
		
		
		$scope.createdBy = response.data.editDet.created_by;
		$scope.createdAt =  response.data.editDet.created_at;
		
		
		$scope.formData.enable_h1_button=($scope.formData.enable_h1_button==1)?true:false;
		$scope.formData.enable_h2_button=($scope.formData.enable_h2_button==1)?true:false;
		$scope.formData.enable_h3_button=($scope.formData.enable_h3_button==1)?true:false;
		$scope.formData.is_default_menu=($scope.formData.is_default_menu==1)?true:false;
		$scope.formData.is_active=($scope.formData.is_active==1)?true:false;
		
		
	   $scope.depdata=response.data.deptData;
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
       }
	   showTable1(response.data.editDet.id);
		
	   	$('#table2_filter').find('input').attr('disabled',true);
	   	$('#table2_length ').find("select").attr('disabled',true);
  
	   
		});
		$scope.disable_all = true;
		$scope.disable_code = true;
		$("#form_div_base_uom_id").removeClass("has-error");
		$("#form_div_base_uom_id_error").hide();
		$("#form_div_compound_unit").removeClass("has-error");
		$("#form_div_compound_unit_error").hide();
		
	/*	DataObj1.adnlFilters = [ {
			col : 1,
			filters : row_data.id
		} ];
		vm.dtInstance1.reloadData();*/
	}

	$rootScope.$on("fun_delete_current_data",function(event){			//Delete Function
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
            var $dialog = angular.element(document.querySelector('md-dialog'));
            var $actionsSection = $dialog.find('md-dialog-actions');
            var $cancelButton = $actionsSection.children()[0];
            var $confirmButton = $actionsSection.children()[1];
            angular.element($confirmButton).removeClass('md-focused');
            angular.element($cancelButton).addClass('md-focused');
            $cancelButton.focus();
		}}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).ok(
				'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			
			 
			$http({
				url : 'delete',
				method : "POST",
				params : {id:$scope.formData.id},
			}).then(function(response) {

				if(response.data==0)
				{
					
					$rootScope.$broadcast('on_AlertMessage_ERR','Menu'+FORM_MESSAGES.ALREADY_USE);

				}else if(response.data==1){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);

					$("#show_form").val(0);
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
					$scope.disable_all = true;
					$scope.disable_code = true;
					vm.dtInstance.reloadData(null, true);
					vm.dtInstance1.reloadData(null, true);
				}else
					{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
					}
			
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
	function view_mode_aftr_edit(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$scope.disable_code = true;
	}
	
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
			}
			
			}
	
		
		if (code_existing_validation($scope.formData)) {
			$scope.formData.created_at = $scope.createdAt;
			  $scope.formData.created_by =  $scope.createdBy;
			 

			    $scope.formData.enable_h1_button=($scope.formData.enable_h1_button==true)?1:0;
				$scope.formData.enable_h2_button=($scope.formData.enable_h2_button==true)?1:0;
				$scope.formData.enable_h3_button=($scope.formData.enable_h3_button==true)?1:0;
				$scope.formData.is_default_menu=($scope.formData.is_default_menu==true)?1:0;
				$scope.formData.is_active=($scope.formData.is_active==true)?1:0;
			  
				
			  
			  
				$scope.Quetable.shopId=settings['currentcompanyid1'];
				$scope.Quetable.origin=settings['currentcompanycode1'];
			$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
			
			
			
			
		
			
	       	   var cmpData = JSON.stringify({depData:$scope.departmentIdList,Quetable:$scope.Quetable});

			
			$http({
				url : 'saveMenu',
				method : "POST",
				params : $scope.formData,
				data : cmpData,
			}).then(function(response) {
				
				if(response.data == 1){
				if ($scope.formData.id != undefined) {
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
					
					$scope.formData.enable_h1_button=($scope.formData.enable_h1_button==1)?true:false;
					$scope.formData.enable_h2_button=($scope.formData.enable_h2_button==1)?true:false;
					$scope.formData.enable_h3_button=($scope.formData.enable_h3_button==1)?true:false;
					$scope.formData.is_default_menu=($scope.formData.is_default_menu==1)?true:false;
					$scope.formData.is_active=($scope.formData.is_active==1)?true:false;

					view_mode_aftr_edit();
					$('#table2_filter').find('input').attr('disabled',true);
				   	$('#table2_length ').find("select").attr('disabled',true);
					
				} else {
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
					$scope.formData = {};
					$scope.formData.is_valid=false;
					$scope.formData.is_refundable=false;
					$scope.fun_get_pono();
					
					$scope.formData.enable_h1_button=false;
					$scope.formData.enable_h2_button=false;
					$scope.formData.enable_h3_button=false;
					$scope.formData.is_default_menu=false;
					$scope.formData.is_active=false;
					
					 $scope.departmentIdList=[];
						
				}
				 reloadData();
				 vm.dtInstance1.reloadData(null, true);
				 $scope.selectedIndexTab=0;

					$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
					$scope.tableShwerr=false;
				 $scope.hide_code_existing_er = true;
				
			}else
				{
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Save failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
				$scope.departmentIdList=[];
				}}, function(response) { // optional
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
	
	
	
	
	
	$rootScope.$on("fun_discard_form",function(event){		
		
		//Discard function
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
		/*	if($scope.formData.id==undefined){
				$scope.show_table=true;
				$scope.show_form=false;
				manageButtons("add");
			}else{*/
		
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		if($scope.formData.id == undefined){
			$scope.formData ={};
			$scope.fun_get_pono();
			 vm.dtInstance1.reloadData(null, true);

			$scope.hide_code_existing_er = true;
		}else{
			var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
			edit(row_data[0],cur_row_index);
		}
		clearform();}
		/*}*/);
	});
	
	$rootScope.$on("fun_enable_inputs",function(){
        $("#show_form").val(1);
	 	$('#table2_filter').find('input').attr('disabled',false);
	 	$('#table2_length ').find("select").attr('disabled',false);
		$scope.disable_all = false;
	});
	
	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});
	
	
	
	
	
	
	$rootScope.$on("fun_clear_form",function(){
		$scope.hide_code_existing_er = true;
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		$scope.formData = {};
		urlString="../menudepartment/deptdata?menuId=0"
		vm.dtInstance1.reloadData(null, true);
		
		$scope.fun_get_pono();
		$scope.tableShwerr=false;
		$scope.formData.enable_h1_button=false;
		$scope.formData.enable_h2_button=false;
		$scope.formData.enable_h3_button=false;
		$scope.formData.is_default_menu=false;
		$scope.formData.is_active=false;
		$scope.selectedIndexTab=0;
		$("#form_div_base_uom_id").removeClass("has-error");
		$("#form_div_base_uom_id_error").hide();
		$("#form_div_compound_unit").removeClass("has-error");
		$("#form_div_compound_unit_error").hide();
	});
	

	
	
	//Manupulate Formdata when Edit mode - Prev-Next feature add
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
		var flg_code=0;
		var flg = true;
		if(data.id == undefined || data.id == ""){
		
			
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#code").select();
			}
		}
		
		 if(validation() == false){
			flg = false;
			$scope.selectedIndexTab=0;
		}
	
		else if($scope.departmentIdList.length==0)
			{
			$scope.tableShwerr=true;
			flg = false;
			$scope.selectedIndexTab=1;
			}
	/*	else
			{
			$scope.tableShwerr=false;
			}*/
		
		
		
		
		if(flg==false)
		{
			focus();
		}
		return flg;
	}
};







