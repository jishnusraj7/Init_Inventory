
//Controller for Table and Form 
mrpApp.controller('discount', discount);


function discount($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,$q,
		MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS) {
	
	$controller('DatatableController', {$scope: $scope});

	$scope.is_subclass = false;
//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			
			$scope.formData.code = response;});}

	/*set_sub_menu("#settings");		
	setMenuSelected("#discounts_leftmenu");	*/		//active leftmenu
	manageButtons("add");

	$scope.formData = {grouping_quantity:1};
	$scope.show_table=true;

	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
	

	var vm = this;
	$scope.table2=false;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.code_existing_validation = code_existing_validation;
	vm.function_clear_select=function_clear_select;
	vm.ar_check={};
	$scope.selectedIndexTab =0;
	$scope.custTypeList=[];
	vm.selected = {};
	vm.itemDiscount={};
	$scope.seletedItems=[];
	$scope.itemData={};
	$scope.selectedSaleItems=[];
	vm.showTable1=showTable1;
	$scope.isAllowEditingNone=true;
	 $scope.showPercentAndOverrde=true;
	 $scope.showItemSpecific=true;
	
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
	vm.dtInstance = {};	
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	vm.dtColumns = [
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','250px').renderWith(
	                		function(data, type, full, meta) {	

	                			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}
	                			
	                			return '<a id="rcd_edit" class='+css+' ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
	                			+ data + '</a>';
	                		


	                		}),

	                		DTColumnBuilder.newColumn('name').withTitle('NAME'),
	                		DTColumnBuilder.newColumn('price').withTitle('DISCOUNT').withClass("dt-body-right").withOption('width','120px').renderWith(
            						function(data, type, full, meta) {
            							if(full.price !='')
            								{
            							data=parseFloat(data).toFixed(settings['decimalPlace']);}
            							return data	}),
	                		DTColumnBuilder.newColumn('is_overridable').withTitle('OVERRIDABLE').renderWith(
	            					function(data,type,full,meta){
	            						if(data==1)
	            							{
	            							data='<i class="fa fa-check-circle-o rowactive" data-toggle="tooltip" data-placement="bottom" title="" aria-hidden="true" data-original-title="Active"></i>';
	            							}
	            						else if(data==0)
	            							{
	            							data='<i class="fa fa-times-circle-o rowinactive" data-toggle="tooltip" data-placement="bottom" title="" aria-hidden="true" data-original-title="inactive"></i>';
	            							}
	            						return data;
	            					}),
	            		   DTColumnBuilder.newColumn('is_item_specific').withTitle('ITEM SPECIFIC').renderWith(
	    	            					function(data,type,full,meta){
	    	            						if(data==1)
	    	            							{
	    	            							data='<i class="fa fa-check-circle-o rowactive" data-toggle="tooltip" data-placement="bottom" title="" aria-hidden="true" data-original-title="Active"></i>';
	    	            							}
	    	            						else if(data==0)
	    	            							{
	    	            							data='<i class="fa fa-times-circle-o rowinactive" data-toggle="tooltip" data-placement="bottom" title="" aria-hidden="true" data-original-title="inactive"></i>';
	    	            							}
	    	            						return data;
	    	            					}),
	            			

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
					$("#show_form").val(1);
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

	function showTable1(discountId)
	{
		var discountId=discountId;
		
		urlString="../saleitemdiscount/saleItemDataByDiscountId?discountId="+discountId;
		//vm.dtInstance1.reloadData();
		$scope.table2=true;

	}
	
	var urlString="../saleitemdiscount/saleItemDataByDiscountId?discountId=0";
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
	                 
	                  DTColumnBuilder.newColumn(null).withTitle(null).notSortable()
	                  .renderWith(function(data, type, full, meta) {
	                      vm.selected[full.id] = false;
	                      if(full.sale_item_id !="")
	                		{
	                		vm.selected[full.id] = true;
	                		}
	                      return '<input ng-disabled="disable_all" ng-model="item.selected[' + full.id + ']" ng-click="toggleOne(item.selected);clearPrice(item.selected[' + full.id + '],' + full.id + ');" type="checkbox">';

	                  
	                  }),
	                  DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','100px'),
                      DTColumnBuilder.newColumn('name').withTitle('NAME').withOption('width','300px'),
                      DTColumnBuilder.newColumn(null).withTitle('DISCOUNT').notSortable()
	                  .renderWith(function(data, type, full, meta) {
	                	  vm.itemDiscount[full.id] = "";
	                	  if(full.sale_item_discount !="")
	                		{
	                	    parseFloat(full.sale_item_discount).toFixed(settings['decimalPlace'])
	                		vm.itemDiscount[full.id] = parseFloat(full.sale_item_discount).toFixed(settings['decimalPlace']).toString() ;
	                		}
	                      return '<input ng-disabled="!item.selected[' + full.id + ']" ng-model="item.itemDiscount[' + full.id + ']"'  
	                      +'type="input" ng-change="getSelectedDiscounts(item.itemDiscount,item.selected)" valid-number>';
	                  }),
	 
        		
	
	 ];
	


	 $scope.toggleOne=function(selectedItems) {
			$scope.seletedItems=selectedItems;
			
		   
			}
	 $scope.clearPrice=function(isDisble,saleId)
	 {
		 if(isDisble == false)
			 {
			 vm.itemDiscount[saleId]="";
			 $scope.selectedItemDiscounts[saleId]="";
			 }
	 }
	 $scope.getSelectedDiscounts=function(selectedDiscounts,selectedItems)
	 {
		 $scope.selectedItemDiscounts=selectedDiscounts;
		 $scope.seletedItems=selectedItems;
	 }
	 $scope.setItemSpecificData=function()
	 {
		 //get true values only:
		 $scope.selectedSaleItems=[];
		 for (var key in $scope.seletedItems) { 
			  if ($scope.seletedItems.hasOwnProperty(key)){ 
			    if (!$scope.seletedItems[key]) { 
			      delete $scope.seletedItems[key];
			    }
			  }
			}
		 for (var key in $scope.selectedItemDiscounts) { 
			  if ($scope.selectedItemDiscounts.hasOwnProperty(key)){ 
			    if ($scope.selectedItemDiscounts[key]=="") { 
			      delete $scope.seletedItems[key];
			      delete $scope.selectedItemDiscounts[key];
			    }
			  }
			}
		 var count=Object.keys($scope.seletedItems).length;
		 
		 for(var i=0;i<count;i++)
		 { 
			$scope.itemData={}; 
		  $scope.itemData.sale_item_id=Object.keys($scope.seletedItems)[i];
		  $scope.itemData.price=$scope.selectedItemDiscounts[Object.keys($scope.seletedItems)[i]];
		  $scope.selectedSaleItems.push($scope.itemData);
		 }
		 console.log($scope.selectedSaleItems);
	 }
	 
	$rootScope.$on('reloadDatatable',function(event){					//reload Datatable
		reloadData();
	});

	$rootScope.$on('hide_table',function(event){
		showTable(event);
		$scope.selectedIndexTab =0;
	});

	$rootScope.$on('hide_form',function(event){
		$("#show_form").val(0);
		$scope.show_table=true;
		$scope.show_form=false;
		$scope.table2=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
		getDefaultstartDate();
	});

	function showTable(event){
		$scope.show_table=false;
		$scope.show_form=true;
	}


	function reloadData() {
		vm.dtInstance.reloadData(null, true);
	}


	function edit(row_data,cur_row_index,event) {									
		
		$scope.selectedIndexTab =0;
		$scope.is_arCompany=vm.ar_check[row_data.id];
		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function
		/*var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;*/
		var row_count = vm.dtInstance.DataTable.rows().data();
		$(".acontainer input").attr('disabled', true);
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
		//vm.dtInstance1.reloadData(null, true);
		$scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,description:row_data.description,account_code:row_data.account_code,
				date_from:geteditDateFormat(row_data.date_from),is_valid:row_data.is_valid,is_item_specific:row_data.is_item_specific,
				disc_password:row_data.disc_password,grouping_quantity:row_data.grouping_quantity,
				is_promotion:row_data.is_promotion,week_days:row_data.week_days,
				created_by:row_data.created_by,created_at:row_data.created_at,allow_editing:row_data.allow_editing,permitted_for:row_data.permitted_for};
		
		$scope.formData.price=row_data.price ?parseFloat(row_data.price).toFixed(settings['decimalPlace']):'';
		if(row_data.date_to != '')
			{
		$scope.formData.date_to=geteditDateFormat(row_data.date_to);
			}
		if(row_data.is_valid==1){$scope.is_Valid1=true;}else{$scope.is_Valid1=false;}
		if(row_data.is_item_specific==1){$scope.itemSpecific=true;}else{$scope.itemSpecific=false;}
		if(row_data.is_percentage==1){$scope.percentageVal=true;}else{$scope.percentageVal=false;}
		if(row_data.is_overridable==1){$scope.isOverridble=true;}else{$scope.isOverridble=false;}
		if(row_data.is_promotion==1){$scope.obj = {isPromotion: true};}else{$scope.obj = {isPromotion: false};}
		
		
		var selectdDays1=row_data.week_days;
		var daysArray=row_data.week_days.split(",");
		var daysObj = {};
		for (var i = 0; i < daysArray.length; i++) {
		   var p=daysArray[i];
		   daysObj[p]=true;
		}
		$scope.formData.selectedDays=daysObj;
		
		if(row_data.time_from != "")
		{

			var startTime 	= row_data.time_from.split(":");
			$("#start_time").val(startTime[0]);
			$("#start_time_min").val(startTime[1]);
		}
		if(row_data.time_to != "")
		{

			var endTime 	= 	row_data.time_to.split(":");
			$("#end_time").val(endTime[0]);
			$("#end_time_min").val(endTime[1]);
		}
		$scope.filterPermit();
		$scope.filterEditing();
		showTable1(row_data.id);
		$scope.disable_all = true;
		 $scope.disabledByPromo=true;
		$scope.disable_code=true;
		$scope.hide_code_existing_er = true;
	}
	$scope.tableDatevalidation = function() {
		if ($scope.formData.date_to != "") {
			if ($scope.formData.date_from > $scope.formData.date_to) {

				$("#divErrormsg2").text('Date_From must be less than Date_To!');
				$("#divErrormsg2").show();
			} else {
				$("#divErrormsg2").hide();
			}
		}
	}
	
	
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
					$rootScope.$broadcast('on_AlertMessage_ERR',"customer "+FORM_MESSAGES.ALREADY_USE+"");
				}

				else if(response.data == 1)
				{
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);

					$("#show_form").val(0);
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
					$scope.disable_all = true;
					$scope.disable_code = true;
					 $scope.disabledByPromo=true;
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
		 $scope.disabledByPromo=true;
		$scope.disable_code = true;
		$(".acontainer input").attr('disabled', true);
	}

	function d2(n) {
		if(n<9) return "0"+n;
		return n;
	}
	today = new Date();
	
	
	$rootScope.$on('fun_save_data',function(event){		//Save Function
		
		
		if (code_existing_validation($scope.formData)) {
			
		//if($scope.formData.disc_password ==undefined || $scope.formData.disc_password == null || $scope.formData.disc_password ==" "){$scope.formData.disc_password = " ";}
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
		if($scope.formData.id != null && $scope.formData.id != undefined)
			{
		$scope.formData.updated_at=today.getFullYear() + "-" + d2(parseInt(today.getMonth()+1)) + "-" + d2(today.getDate()) + " " + d2(today.getHours()) + ":" + d2(today.getMinutes()) + ":" + d2(today.getSeconds());
		$scope.formData.updated_by=parseInt(strings['userID']);
		    }
		
		$scope.formData.date_from = getMysqlFormat($scope.formData.date_from);
		if($scope.formData.date_to !=undefined && $scope.formData.date_to !=""){
		$scope.formData.date_to = getMysqlFormat($scope.formData.date_to);
		}
		//alert($scope.formData.date_from);
		if($scope.is_Valid1==true){$scope.formData.is_valid=1;}else{$scope.formData.is_valid=0;}
		if($scope.itemSpecific==true){$scope.formData.is_item_specific =1;}else{$scope.formData.is_item_specific =0;}
		if($scope.percentageVal==true){$scope.formData.is_percentage=1}else{$scope.formData.is_percentage=0;}
		if($scope.isOverridble==true){$scope.formData.is_overridable=1}else{$scope.formData.is_overridable=0}
		if($scope.obj.isPromotion==true){$scope.formData.is_promotion=1;}else{$scope.formData.is_promotion=0;}
		$scope.formData.week_days=$scope.getSelectedDays();
		$scope.setTimeDuration();
		$scope.setItemSpecificData();
		checkIsAllowEditing();
		
		$http({
				url : 'saveDiscount',
				method : "POST",
				params : $scope.formData,
				data : {Quetable:$scope.Quetable,SaleItemDiscountData:$scope.selectedSaleItems},
			}).then(function(response){
				if(response.data == 1){
					$scope.formData.date_from=geteditDateFormat($scope.formData.date_from);
					if($scope.formData.date_to !=undefined && $scope.formData.date_to !=""){
						$scope.formData.date_to = geteditDateFormat($scope.formData.date_to);
						}
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
					$scope.itemSpecific=false;
					$scope.percentageVal=false;
					$scope.isOverridble=false;
					$scope.obj = {isPromotion: false};
					$scope.is_Valid1=true;
					$scope.enablePass=false;
					$scope.fun_get_pono();
					$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
					 $scope.isAllowEditingNone=true;
					 $scope.showPercentAndOverrde=true;
					 $scope.showDiscount=true;
					 getDefaultstartDate();
				}
				reloadData();
				$scope.selectedIndexTab =0;
				$scope.hide_code_existing_er = true;
				function_clear_select();
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
		$("#show_form").val(1);
		$scope.disable_all = false;
		if($scope.obj != undefined && $scope.obj.isPromotion == true)
			{
			$scope.disabledByPromo=false;
			}else
				{
				$scope.disabledByPromo=true;
				}
		$(".acontainer input").attr('disabled', false);
		$("#name").focus();
		
	});

	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
		
	});
	
	
	
	 $scope.showEditing=false;
	$rootScope.$on("fun_clear_form",function(){
		function_clear_select();
		$scope.formData = {grouping_quantity:1,allow_editing:"0"};
		$scope.is_Valid1=true;
		$scope.itemSpecific=false;
		$scope.percentageVal=false;
		$scope.isOverridble=false;
		$scope.obj = {isPromotion: false};
		$scope.fun_get_pono();
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		$scope.hide_code_existing_er = true;
		$scope.is_subclass=false;
		$scope.is_arCompany=false;
		$scope.isValid=false;
		$(".acontainer input").attr('disabled', false);
		 vm.selected = {}; 
		 $scope.selectedItems ={};
		 vm.itemDiscount={};
		 $scope.selectedSaleItems=[];
		 $scope.itemData={};
		 $scope.table2=true;
		 urlString="../saleitemdiscount/saleItemDataByDiscountId?discountId=0";
		 getDefaultstartDate();
		 $scope.isAllowEditingNone=true;
		 $scope.showPercentAndOverrde=true;
		 $scope.showDiscount=true;
		 $scope.enablePass=false;
		 $scope.showEditing=true;
		 $scope.showItemSpecific=true;
		//$scope.formData.parent_id = $scope.sample[0].id;
	});
	 //Get Start date as current date by default:
	function getDefaultstartDate()
	{
	var dateFormat = get_date_format();
	var date = new Date();
	
	$scope.formData.date_from = dateForm(date);
}

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
				$scope.selectedIndexTab =0;
			}


		}

		if(validation() == false){
			flg = false;
			$scope.selectedIndexTab =0;
			
		} if($scope.formData.permitted_for == undefined || $scope.formData.permitted_for == "")
			{ 
				 flg = false;
				 $("#form_div_permitted_for").addClass("has-error");
				 $("#form_div_permitted_for_error").show();
				 $scope.selectedIndexTab =0;
			}else
				{
				 $("#form_div_permitted_for").removeClass("has-error");
				 $("#form_div_permitted_for_error").hide();
				
				}
		if($scope.formData.date_from == undefined || $scope.formData.date_from==""  && $scope.formData.date_from < $scope.formData.date_to)
		{ 
			 flg = false;
			 $("#form_div_startdate_discount").addClass("has-error");
			 $("#form_div_startdate_discount_error").show();
			 $scope.selectedIndexTab =0;
		}else
			{
			 $("#form_div_startdate_discount").removeClass("has-error");
			 $("#form_div_startdate_discount_error").hide();
			
			}
		if($scope.formData.allow_editing== undefined || $scope.formData.allow_editing=="" )
		{ 
			 flg = false;
			 $("#form_div_allow_editing").addClass("has-error");
			 $("#form_div_allow_editing_error").show();
			 $scope.selectedIndexTab =1;
		}else
			{
			 $("#form_div_allow_editing").removeClass("has-error");
			 $("form_div_allow_editing_error").hide();
			
			}
		if(($scope.itemSpecific==undefined || $scope.itemSpecific==false)&&
		   ($scope.formData.price == undefined || $scope.formData.price==""  || $scope.formData.price==0)&&
		   $scope.formData.allow_editing == "0")
		{ 
			 flg = false;
			 $("#form_div_discount").addClass("has-error");
			 $("#form_div_discount_error").show();
			 $scope.selectedIndexTab =1;
		}else
			{
			 $("#form_div_discount").removeClass("has-error");
			 $("form_div_discount_error").hide();
			
			}
		if(($scope.formData.disc_password ==undefined || $scope.formData.disc_password == null )&& $scope.enablePass == true){
			 $scope.formData.disc_password = " ";
			 flg = false;
			 $("#form_div_passwrd").addClass("has-error");
			 $("#form_div_passwrd_error").show();
			 $scope.selectedIndexTab =1;
			}else
				{
				 $("#form_div_passwrd").removeClass("has-error");
				 $("#form_div_passwrd_error").hide();
				}
		 if(($scope.formData.disc_password != $scope.formData.conf_pass)  && ($scope.formData.disc_password !=undefined  || $scope.formData.disc_password != null))
		 {
		 flg=false;
		 $("#form_div_confirm_pass").addClass("has-error");
		 $("#form_div_confirm_pass_error").show();
		 $scope.selectedIndexTab =1;
		 }else
			 {
			 $("#form_div_confirm_pass").removeClass("has-error");
			 $("#form_div_confirm_pass_error").hide();
			 }
		if($scope.itemSpecific==true)
			{
			//alert("fill data");
			}
		if($scope.formData.grouping_quantity == undefined || $scope.formData.grouping_quantity =="" )
			{ 
				 flg = false;
				 $("#form_div_group_qty").addClass("has-error");
				 $("#form_div_group_qty_error").show();
				 $scope.selectedIndexTab =1;
			}else
				{
				 $("#form_div_group_qty").removeClass("has-error");
				 $("form_div_group_qty_error").hide();
				
				}
	
		if(flg==false)
		{
			focus();
		}

		return flg;

	}


	function function_clear_select(){
		
		 $("#form_div_permitted_for").removeClass("has-error");
		 $("#form_div_permitted_for_error").hide();
		 $("#form_div_startdate_discount").removeClass("has-error");
		 $("#form_div_startdate_discount_error").hide();
		 $("#form_div_allow_editing").removeClass("has-error");
		 $("#form_div_allow_editing_error").hide();
		 $("#form_div_discount").removeClass("has-error");
		 $("#form_div_discount_error").hide();
		 $("#form_div_group_qty").removeClass("has-error");
		 $("#form_div_group_qty_error").hide();
		 $("#divErrormsg2").hide();
		 
	}
	//Item specific discounts
	$scope.addItemSpecificDiscount=function()
	{
		$('#itemSpecificModal').modal('toggle');
		//showTable1($scope.formData.id);
	}
	
	
	
	$scope.arCodeChange=function()
	{
		if($scope.is_arCompany == false)
			{
			$scope.formData.ar_code="";
			}
	}
	
	var selectedDays = {
		    Sun: false,Mon:false,Tue:false,Wed:false,Thu:false,Fri:false,Sat:false
		  };
		  
		  var calculateSelected = function() {
		    $scope.someSelected = Object.keys(selectedDays).some(function (key) {
		    		      return selectedDays[key];
		      
		    });
		  };
		  
		  $scope.formData = {
		    selectedDays: selectedDays
		  };
		  
		  $scope.days = [{'name':'Sun'}, {'name':'Mon'}, {'name':'Tue'}, {'name':'Wed'},
		                 {'name':'Thu'}, {'name':'Fri'}, {'name':'Sat'}];
		  $scope.formData.selectedDays={'Sun':false,'Mon':false,'Tue':false,'Wed':false,'Thu':false,'Fri':false,'Sat':false}
		 
		  $scope.checkboxChanged = calculateSelected;
		  
		  
		  
		 $scope.changePromotion=function()
		 {
			//alert($scope.obj.isPromotion);
			 if($scope.obj.isPromotion == false)
				 {
				  $scope.disabledByPromo=true;
				 }else
					 {
					 $scope.disabledByPromo=false;
					 }
			for(var  key in selectedDays){
				selectedDays[key]=$scope.obj.isPromotion;
			}
			$scope.formData.selectedDays=selectedDays;
			selectedDays = {
				    Sun: false,Mon:false,Tue:false,Wed:false,Thu:false,Fri:false,Sat:false
				  };
			//$scope.getSelectedDays();
		 }
		 $scope.getSelectedDays=function()
		 {
			 var selectdDays='';
			 $scope.daysSel='';
				for (var key in $scope.formData.selectedDays) {
					if ($scope.formData.selectedDays.hasOwnProperty(key)) {
						if($scope.formData.selectedDays[key]!=null && $scope.formData.selectedDays[key]!=undefined &&$scope.formData.selectedDays[key]!=false&&$scope.formData.selectedDays[key]!="")
						  selectdDays+= key +',';
					  }
					}
				
				$scope.daysSel=selectdDays.substring(0, selectdDays.length - 1);
				//alert($scope.daysSel);
				return $scope.daysSel;
				
		 }
		 $scope.filterPermit=function()
		 {
			
			 if($scope.formData.permitted_for == 1)
				 {
				 $scope.showEditing=false;
				 $scope.showItemSpecific=false;
				 $scope.showEditing=false;
				 $scope.itemSpecific=false;
				 $scope.isAllowEditingNone=false;
				 $scope.showDiscount=true;
				 $scope.showPercentAndOverrde=true;
				 $scope.obj={isPromotion:false};
				 $scope.formData.allow_editing="0";
				 checkIsAllowEditing();
				 }else
					 {
					 $scope.showEditing=true;
					 $scope.showItemSpecific=true;
					 $scope.filterEditing();
					 }
		 }
		 
		 $(function () {
             $('#datetimepicker3').datetimepicker({
                 format: 'LT',
                	
             });
         });
		 
		 $(function () {
             $('#datetimepicker4').datetimepicker({
                 format: 'LT',
                
             });
         });
		 $scope.clearDiscountValue=function()
		 {
			 if($scope.itemSpecific==false)
				 {
				 $scope.formData.price="";
				 }
			 else
				 {
				 vm.selected = {}; 
				 $scope.selectedItems ={};
				 vm.itemDiscount={};
				 $scope.selectedSaleItems=[];
				 }
		 }
		 
		 $scope.setTimeDuration=function()
		 {
			  var time_from		=	$("#start_time").val();
			  var time_from_min		=	$("#start_time_min").val();
			  /*if(!(time_from == "00" && time_from_min == "00"))
				  {*/
			  $scope.formData.time_from =time_from+":"+time_from_min+":"+"00";
				 // }
			  
			  var time_to= $("#end_time").val();
			  var time_to_min=$("#end_time_min").val();
			/*  if(!(time_to == "00" && time_to_min == "00"))
				 {*/
			     $scope.formData.time_to =	time_to+":"+time_to_min+":"+"00";
				/* }*/
			  
		 }
		 $scope.filterEditing=function()
		 {
			// alert($scope.formData.allow_editing);
			 if($scope.formData.permitted_for != 1){
			 if($scope.formData.allow_editing != "0")
				 {
			       $scope.isAllowEditingNone=false;
			       $scope.showPercentAndOverrde=false;
			       $scope.showDiscount=false;
			   	   $scope.percentageVal=false;
				   $scope.isOverridble=false;
				  //$scope.isPromotion=false;
				   $scope.obj={isPromotion:false};
				   $scope.formData.price="";
				 }
			 else
				 {
				 $scope.isAllowEditingNone=true;
				 $scope.showDiscount=true;
				 $scope.showPercentAndOverrde=true;
				 }
			 }
		 }
		 function checkIsAllowEditing()
		 {
			 if($scope.formData.allow_editing != "0" && $scope.formData.permitted_for != 1)
				 {
				   $scope.formData.time_from ="";
				   $scope.formData.time_to ="";
				   $scope.formData.week_days="";
				 }else if($scope.formData.permitted_for == 1)
					 {
					  $scope.formData.time_from ="";
					   $scope.formData.time_to ="";
					   $scope.formData.week_days="";
					 }
		 }
	
}
mrpApp.directive('daterangePicker1', [function() {
	return {
		controller: function ($scope,$http) {

			return $scope;
		},
		link: function(scope, elem, attrs ,controller) {
			var dateFormat = get_date_format();
			var date = new Date(),y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			//var lastDay = new Date(y, m + 1, 0);

			//controller.formData.date_to =  dateForm(lastDay);

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a day
			var diff = todayTimeStamp - oneDayTimeStamp;
			var yesterdayDate = new Date(diff);

			//controller.formData.date_from = dateForm(firstDay);

			$(elem).inputmask({
				mask : dateFormat.mask,
				placeholder : dateFormat.format,
				alias : "date",
			});

			$(elem).daterangepicker({
				"format" : dateFormat.format,
				"drops" : "down",
				"singleDatePicker" : true,
				"showDropdowns" : true,
				"autoApply" : false,
				"linkedCalendars" : false,

			}, function(start, end, label) {
			});
		}
	};
}]);



