/**
 * 
 */
//Controller for Table and Form 
mrpApp.controller('stocktransfer', stocktransfer);


function stocktransfer($compile,$controller,$scope,$interval,$timeout,$http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,MRP_CONSTANT,DATATABLE_CONSTANT,STATUS_BTN_TEXT,$q ,RECORD_STATUS, $window,FORM_MESSAGES,ITEM_TABLE_MESSAGES) {

	$controller('DatatableController', {$scope: $scope});	
	$scope.transfer_departments = [];
	$scope.baseUomCode=[];
	$scope.prograssing1 = true;
	//set_sub_menu("#production");						
	setMenuSelected("#stocktranfer_left_menu");
	var baseUomCode="";
	$http({
		url : 'formJsonData',
		method : "GET",
	}).then(function(response) {

		$scope.transfer_departments = response.data.sourcedepData;
		$scope.source_department_data = response.data.sourcedepData;
		$scope.itemsMastersData = response.data.stockItmData;
		$scope.customers = response.data.departments;
		$scope.shops = response.data.shopsData;

		$scope.transfer_departments.splice(0,0,{id : "" ,name : "Select"});
		$scope.customers.splice(0,0,{code : "" ,name : "Select Departments"});
		$scope.shops.splice(0,0,{code : "" ,name : "Select Shop"});
		$scope.fillData = $scope.shops;
		//$("#advsearchbox").show();
		$scope.prograssing1 = false;
		$scope.show_table = true;
		$('#btnAdd').show();
	}, function(response) { /*optional*/});
	
	$("#stkoptradioExternal").prop("checked", true);
	$("#form_div_dest_department_code").hide();
	manageButtons("add");
	$scope.formData = {};
	$scope.show_table=true;
	$("#advsearchbox").show();
	$scope.show_form=false;
	$scope.hide_code_existing_er = true;

	$scope.status = "";
	$scope.formData.source_department_id = "";
	$scope.formData.closing_date = dateForm(new Date());
	$scope.formData.stock_transfer_date = dateForm(new Date());
	$scope.customer_id = "";
	$scope.type_of_order = 0;
	
	$scope.itemList=[];
	$scope.transferList = [];
	$scope.getOrderstoTransfer = function()
	{
		$scope.prograssing1 = true;
		if( $scope.formData.closing_date!="" && $scope.formData.closing_date!=undefined)
		{	
			$("#form_div_delevery_date").removeClass("has-error");
			$("#form_div_scheduledate_error").hide();
			var transferData;
			transferData = {
					delevery_date : getMysqlFormat($scope.formData.closing_date),
					customerID : $scope.customer_id,
					is_shop : $scope.type_of_order,
			};

			$http({
				url : 'getOrderstoTransfer',
				async : false,
				params : transferData,
				method : "GET",
			}).then(function(response) {

				$scope.itemList = response.data.totalItemList;
				$scope.prograssing1 = false;
			});
		}
		else{

			$("#form_div_delevery_date").addClass("has-error");
			$("#form_div_scheduledate_error").show();
			$scope.itemList=[];
		}
	}
	
	$scope.getTransferData = function(){
		$scope.prograssing1 = true;
		if( $scope.formData.stock_transfer_date != "" && $scope.formData.stock_transfer_date != undefined)
		{	
			$("#form_div_stock_transfer_date").removeClass("has-error");
			$("#form_div_scheduler_date_error").hide();
			var transferData;
			transferData = {
					transfer_date : getMysqlFormat($scope.formData.stock_transfer_date),
			};

			$http({
				url : 'getTransferHdrData',
				async : false,
				params : transferData,
				method : "GET",
			}).then(function(response) {

				$scope.transferList = response.data.transferHdrData;
				$scope.prograssing1 = false;
			});
		}
		else{

			$("#form_div_stock_transfer_date").addClass("has-error");
			$("#form_div_scheduler_date_error").show();
			$scope.transferList = [];
		}
	}
	
	$scope.getOrderstoTransfer();
	$scope.getTransferData();
	
	var vm = this;
	vm.edit = edit;
//	vm.reloadData = reloadData;
	vm.showTable = showTable;

	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	
	vm.form_validation = form_validation;
	vm.getSourcedep = getSourcedep;
	vm.fun_get_dep_name = fun_get_dep_name;
	$scope.stockRegisterList=[];
	$scope.stockTransferDetailList=[];
	$scope.stockRegDetailsList =[];
	
	$scope.itemsBatchData = [];
	$scope.itemsMastersData = [];

	
	$scope.source_department_data = [];
	$scope.dest_department_data = [];
	
	function getSourcedep() {
		
		$scope.formData.source_department_id = "";
		/*$("#form_div_desti_code").find("input").val("");
		$scope.formData.source_department_code = strings['isDefDepartmentcode'];
		$scope.formData.source_department_name = strings['isDefDepartmentname'];
		$("#form_div_source_department_code").find("#source_department_name").val(
				strings['isDefDepartmentname']);
		
		$("#form_div_source_department_code").find(".acontainer input").val(
				strings['isDefDepartmentcode']);*/		
	}
	
	$( "#companyId" ).change(function() {
		
		$("#form_div_dest_department_code").hide();
	});
	
	$scope.fun_get_dest_department=function()
	{
		$http({
			url : 'destdepartment',
			method : "GET",
			params:{dest_cmpny_id:$( "#companyId" ).val()}
		}).then(function(response) {
			$scope.dest_department_data = response.data.destdepData;
			var to_depList = fun_get_dep_name(row_data.dest_department_id);
			$("#form_div_dest_department_code").find(".acontainer input").val(to_depList.code);
			$scope.formData.dest_department_code=to_depList.code;
			$scope.formData.dest_department_name=to_depList.name ;
		});	
	}
	
	
	$("#advsearchbox").hide();

	$scope.show_table=false;
	$scope.prograssing1=true;
    $('#btnAdd').hide();

	$("#stkoptradioInternal").click(function(){
		
		//$("#msg").hide();
		if($("#stkoptradioInternal").prop('checked'))
		{
		/*	$scope.formData.dest_department_id="";
			$scope.formData.dest_department_code="";
			$scope.formData.dest_department_name="";
			$("#form_div_dest_department_code").find(".acontainer input").val("");
			$scope.fun_get_current_department();*/
			$("#form_div_dest_company").hide();
			$("#form_div_dest_department_code").show();
		}
	});

    $("#stkoptradioExternal").click(function(){
	
		if($("#stkoptradioExternal").prop('checked'))
		{
			//$scope.dest_department_data = [];
			$("#form_div_dest_company").show();
			$("#form_div_source_department_code").show();
			$("#form_div_dest_department_code").hide();
		}
	});
	
	$scope.fun_get_current_department=function()
	{
		$http({
			url : 'currentdestdepartment',
			method : "GET",
		}).then(function(response) {
			$scope.dest_department_data = response.data.destdepData;
		});	
	}
	
	$scope.disable_search_text=function(elemenvalue){
		if($scope.disable_all==true){
			$(elemenvalue).attr("disabled", true);
		}
	}
	
	/*vm.dtInstance = {};

	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.order = "desc";
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	vm.dtColumns = [
	                DTColumnBuilder.newColumn('id').withTitle('ID').notVisible().withOption('searchable','false'),
	                DTColumnBuilder.newColumn('stock_transfer_no').withTitle('TRANSFER NO.').withOption('width', '120px')
	                .withOption('type', 'natural').renderWith(function(data, type, full, meta) {
	                	return urlFormater(data);  
	                }),	                
	                DTColumnBuilder.newColumn('stock_transfer_date').withTitle('TRANSFER DATE').
	                withOption('width', '120px').withOption('type', 'natural')
	                .renderWith(function(data, type, full, meta) {
	                	data = geteditDateFormat(data);
	                	return data;
	                }),	
	                DTColumnBuilder.newColumn('dest_department_name').withTitle('FROM - TO')
	                .withOption('type', 'natural').renderWith(function(data, type, full, meta) {
	                	var data1= full.source_department_name +" - "+full.dest_department_name;
	                	return data1;
	                }),
	                DTColumnBuilder.newColumn('dest_company_name').withTitle('DESTINATION COMPANY')
	                .withOption('type', 'natural').renderWith(function(data, type, full, meta) {
	                	if(data ==""){
	                		data =  "<span style='color:green;'>Internal Transfer</span>";
	                	}
	                	return data;
	                }),
	                //DTColumnBuilder.newColumn('dest_company_name').withTitle('DESTINATION COMPANY'),
	                DTColumnBuilder.newColumn('req_status').withOption('width', '110px').withTitle('STATUS')
	                .renderWith(function(data, type, full, meta) {
	                	if(data == 0){
	                		data =  "<span style='color:green;'>"+RECORD_STATUS.PENDING+"</span>";
	                	}else if(data ==1){
	                		data = "<span style='color:#3c8dbc;'>"+RECORD_STATUS.FINALIZED+"</span>";
	                	}else if(data ==2){
	                		data ="<span style='color:red;'>"+RECORD_STATUS.PRINTED+"<span>";
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
					$('#show_form').val(1);
					edit(rowData,current_row_index,e);
				} 
			});
		});
		return nRow;
	}


	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){
			return pageInfo.page +" / "+pageInfo.pages;
		}else{
			return pageInfo.page+1 +" / "+pageInfo.pages;
		}


	}


	$rootScope.$on('reloadDatatable',function(event){					//reload Datatable
		reloadData();
	});*/

	$rootScope.$on('hide_table',function(event){
		showTable(event);
		$scope.disable_add_remove = true;
	});

	$rootScope.$on('hide_form',function(event){
		
		$('#show_form').val(0);
		$scope.formData.closing_date = dateForm(new Date());
		$scope.formData.stock_transfer_date = dateForm(new Date());
		$scope.getOrderstoTransfer();
		$scope.getTransferData();		
		
		$scope.disable_all = false;
		$scope.disable_request_qty = false;
		$scope.show_table = true;
		$scope.show_form=false;
		//$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');
//		$("#advsearchbox").show();
	});

	function showTable(event){
//		$("#advsearchbox").hide();
		$scope.show_table=false;
		$scope.show_form=true;
	}
	
    /*$timeout(function () {$("#DataTables_Table_0_filter").hide(); }, 1); 
    
	$rootScope.$on("advSearch",function(event){
		
		$("#dropdownnew").toggle();
		DataObj.adnlFilters=[{}];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search('').draw();	

		$scope.date=$('#orderDate').val();
		if($scope.date !=null && $scope.date !=undefined && $scope.date !="")
		{
		$scope.date=getMysqlFormat($scope.date);
		}
		$scope.reqNo=$('#reqNo').val();

	
       $scope.searchTxtItms={2:$scope.date,3:$scope.reqNo};
      
       var close_option=[];
       var counter=0;
       for (var key in $scope.searchTxtItms) {
    	   
    	   if ($scope.searchTxtItms.hasOwnProperty(key)) {
    		   if($scope.searchTxtItms[key] !=null && $scope.searchTxtItms[key] !=undefined && $scope.searchTxtItms[key] !="")
    			   {
    			
    				angular.element(document.getElementById('SearchText')).append($compile("<div id="+key+"  class='advseacrh '  contenteditable='false'>"+$scope.searchTxtItms[key]+"<span class='close-thik' contenteditable='false'  ng-click='deleteOptn("+key+"); '></span></div>")($scope))
    				$scope.deleteOptn = function (key) {
    					delete $scope.searchTxtItms[key];	
                        $('#'+key).remove();
                       switch(key)
                       {
                     
                       case 2:
                    	   $scope.date="";
                           $('#orderDate').val();
                           break;
                       case 3:
                    	   $scope.reqNo="";
                    	    $('#reqNo').val("");
                    	    break;
                     
                       }
               		DataObj.adnlFilters=[{col:2,filters:$scope.date},{col:1,filters:$scope.reqNo}];

                       vm.dtInstance.reloadData(); 
                     };
    			   
	   				counter++;
	   				//
	   			//	Object.keys(object).find(key => object[key] === value)
	   				
    			   }
    	   }
    	 }
       
		
		
		DataObj.adnlFilters=[{col:2,filters:$scope.date},{col:1,filters:$scope.reqNo}];
			vm.dtInstance.reloadData();
			$scope.searchTxtItms={};
			
	});

	$rootScope.$on("Search",function(event){
		DataObj.adnlFilters=[{}];
		$scope.searchTxtItms={};
			vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	

	});
	

	$("#clear").click(function(){
		DataObj.adnlFilters=[{}];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	
		$scope.searchTxtItms={};
	});
	
	
	$("#clearFeilds").click(function(){
		$("#dropdownnew").toggle();
		
		 $('#orderDate').val("");
	    
	   $('#reqNo').val("");
	 

		
	});

	$("#closebtnew").click(function(){
		$("#dropdownnew").toggle();
		
	});
	

	function reloadData() {
		vm.dtInstance.reloadData(null, true);
		//loadItemDetailsTable();

	}*/
	
	function fun_get_dep_name(id) {
		var depList = [];
		for (var i = 0; i < $scope.dest_department_data.length; i++) {
			if ($scope.dest_department_data[i].id == id) {
				depList = $scope.dest_department_data[i];
			}
		}

		return depList;
	}

	function edit(row_data,cur_row_index,event) {									

		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function
		//var row_count =vm.dtInstance.DataTable.rows().data();
		//row_count=row_count.length;
		var row_count = $('#transfer_list tr').length - 1;
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
		$("#form_div_source_department_code").removeClass("has-error");
		$("#form_div_source_department_code_error").hide();
		$("#form_div_dest_department_code").removeClass("has-error");
		$("#form_div_dest_department_code_error").hide();
		$("#form_div_dest_company").removeClass("has-error");
		$("#form_div_dest_company_error").hide();
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		
		
		$scope.formData = {id:row_data.id,stock_transfer_no : row_data.stock_transfer_no ,
				stock_transfer_date1 : geteditDateFormat(row_data.stock_transfer_date),
				source_department_id : row_data.source_department_id,
				dest_department_id:row_data.dest_department_id,source_department_name:row_data.source_department_name,
				dest_department_name:$scope.formData.dest_department_name,source_department_code:row_data.source_department_code,transfer_type:row_data.transfer_type};
		// disable radio button before edit 
		$("#stkoptradioExternal").attr('disabled', true);
		$("#stkoptradioInternal").attr('disabled', true);
		
		if(row_data.transfer_type == 0){
			$("#stkoptradioExternal").prop('checked',true)
			$("#form_div_dest_company").show();
			$("#form_div_dest_department_code").hide();
			
		}else{
			$("#stkoptradioInternal").prop('checked',true)
			$("#form_div_dest_company").hide();	
			$("#form_div_dest_department_code").show();
		}
		
		 if(row_data.req_status==1)
			{
			
			$('#btnDelete').hide();
			$('#btnEdit').hide();
			$('#btnBack').show();
			$("#div_finlize_print").css("display","none");
			$scope.status =RECORD_STATUS.FINALIZED;
			$scope.ClassName ="form_list_con  approved_bg";
			}
		else
			{
			 $scope.status = RECORD_STATUS.PENDING;
				$scope.ClassName ="form_list_con pending_bg";
			
			
			$("#btnDelete").show();
			 $("#btnAdd").show();
		     $("#btnEdit").show();
		 	$("#div_finlize_print").css("display","block");
			}
		
		$("#form_div_source_department_code").find(".acontainer input").val(
				row_data.source_department_code);
		 /* $('#companyId').val(row_data.dest_company_id);*/
		$("#companyId option[value='"+row_data.dest_company_id+"']").prop('selected', true);
		
		/*var a= $( "#companyId" ).val();
		
		var companyId="";
			if($("#companyId").val()!=" "){
				companyId=$("#companyId").val();
			}
		
		  $http({
				url : 'destdepartment',
				method : "GET",
				params:{dest_cmpny_id:companyId}
			}).then(function(response) {
				$scope.dest_department_data = response.data.destdepData;
				var to_depList = fun_get_dep_name(row_data.dest_department_id);
				$("#form_div_dest_department_code").find(".acontainer input").val(to_depList.code);
				$scope.formData.dest_department_code=to_depList.code;
				$scope.formData.dest_department_name=to_depList.name ;
			
			});	*/		
		
		$("#form_div_dest_department_code").find(".acontainer input").val(row_data.dest_department_code);
		$scope.formData.dest_department_code=row_data.dest_department_code;
		$scope.formData.dest_department_name=row_data.dest_department_name ;
		$scope.formData.order_id = "";
		$(".acontainer input").attr('disabled', true);
		$scope.disable_all = true;
		$scope.disable_request_qty = true;
		$scope.disable_code = true;
		
		setItemtableValues(row_data.id);
	}

	function setItemtableValues(id){
		
		$scope.prograssing = true;
		$scope.invoice = {items: []};

		$http({
			url : 'getStockTransDtl',method : "GET",params:{id:id},
		}).then(function(response) {

			$scope.invoice.items = response.data.stkTrsnDtl;
			for(var i = 0;i<response.data.stkTrsnDtl.length;i++){
				$scope.invoice.items[i].currentStock= parseFloat(response.data.stkTrsnDtl[i].currentStock).toFixed(settings['decimalPlace']);
				$scope.invoice.items[i].issued_qty= parseFloat(response.data.stkTrsnDtl[i].issued_qty).toFixed(settings['decimalPlace']);
				$scope.invoice.items[i].request_qty= parseFloat(response.data.stkTrsnDtl[i].request_qty).toFixed(settings['decimalPlace']);
				$scope.invoice.items[i].cost_price= parseFloat(response.data.stkTrsnDtl[i].cost_price).toFixed(2);
				$scope.invoice.items[i].tax_pc= parseFloat(response.data.stkTrsnDtl[i].tax_pc).toFixed(2);
			//	$scope.invoice.items[i].base_uom_code= response.data.stkTrsnDtl[i].base_uom_code;
				$scope.invoice.items[i].compound_unit= response.data.stkTrsnDtl[i].compound_unit;
				$scope.invoice.items[i].flag = 0;
				
				baseUomCode[i] = $scope.setuomCode(response.data.stkTrsnDtl[i].uomcode,i);
			}
			$scope.prograssing = false;
		}, function(response) { 
			$scope.prograssing = false;
		});
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
				params : {id:$scope.formData.id,
							order_no : $scope.transferList[current_row_index].order_no},
			}).then(function(response) {
				if(response.data == 1){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);

					$('#show_form').val(0);
					$scope.formData.closing_date = dateForm(new Date());
					$scope.formData.stock_transfer_date = dateForm(new Date());
					$scope.getOrderstoTransfer();
					$scope.getTransferData();
					//vm.dtInstance.reloadData(null, true);
					//reloadData();
					//$("#advsearchbox").show();
					$scope.show_table = true;
					$scope.show_form = false;
					manageButtons("add");
					$scope.disable_all = false;
					$scope.disable_request_qty = false;
					$(".acontainer input").attr('disabled', true);
					$scope.disable_code = true;
					
				}else{
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Delete failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
			}}, function(response) { // optional
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
			$scope.show_table=true;$("#advsearchbox").show();
			$scope.show_form=false;
			manageButtons("add");
		}
		return index;

	}


	function view_mode_aftr_edit(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$scope.disable_request_qty = true;
		$scope.disable_code = true;
		$(".acontainer input").attr('disabled', true);
	}

	//finalize stockin
	$scope.filalize_stockin = function(event){
		
		
		/*if($("#div_finlize_print button").text() == "FINALIZE"){
			 $scope.showConfirm(FORM_MESSAGES.FINALIZE_WRNG,STATUS_BTN_TEXT.FINALIZE,event);	
		}else if($("#div_finlize_print button").text() == "PRINT" || $("#div_finlize_print button").text() == "RE-PRINT"){
							$scope.print();
			
		}*/
		
		 $scope.showConfirm(FORM_MESSAGES.FINALIZE_WRNG,STATUS_BTN_TEXT.FINALIZE,event);
			
	}
	
	
	 //common confirm box
    $scope.showConfirm = function(title,sourse,event) {
    	var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
            var $dialog = angular.element(document.querySelector('md-dialog'));
            var $actionsSection = $dialog.find('md-dialog-actions');
            var $cancelButton = $actionsSection.children()[0];
            var $confirmButton = $actionsSection.children()[1];
            angular.element($confirmButton).removeClass('md-focused');
            angular.element($cancelButton).addClass('md-focused');
            $cancelButton.focus();
		}}
		).title(title).targetEvent(event).ok(
						'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
            	if(sourse=="DELETE"){ 
            		$scope.deleteData();
            	}else if(sourse==STATUS_BTN_TEXT.FINALIZE){	
            		
            		$scope.finalize();
            	}
            	
            	else if(sourse == STATUS_BTN_TEXT.PRINT){
            		$scope.print();
            	}	
               }, function() { 
            });
      };

     	//Finalize:
  		$scope.finalize=function(ev){
  			$scope.formData.stock_transfer_date = getMysqlFormat($scope.formData.stock_transfer_date1);
  			
  			$scope.formData.dest_company_id=" ";
			
			if($("#stkoptradioExternal").prop('checked')){
				$scope.formData.dest_company_id=$("#companyId").val();
				
			}
			
			if($scope.formData.dest_company_id== " "){
				$scope.formData.dest_company_id=0;
			}
  			
		if(form_validation($scope.invoice.items)){
  				$scope.prograssing = true;
  				$scope.formData.stockDetailLists=JSON.stringify($scope.invoice.items);
  				$http({
  					url : 'finalize',
  					method : "POST",
  					data:$scope.formData,
  				}).then(function(response) {

  					if(response.data==1){
  						$('#btnDelete').hide();
  						$('#btnEdit').hide();
  						$('#btnBack').show();
  						$("#div_finlize_print").css("display","none");
  						$scope.showFinalize=false;
  						
  						location.reload();
  						$scope.prograssing = false;
  						//vm.dtInstance.reloadData(null, true);
  						$scope.status =RECORD_STATUS.FINALIZED;
  						$scope.ClassName ="form_list_con  approved_bg";
  						//reloadData();
  					}else
  					{

  						$mdDialog.show($mdDialog.alert()
  								.parent(angular.element(document.querySelector('#dialogContainer')))
  								.clickOutsideToClose(true)
  								.textContent("Finalize failed.")
  								.ok('Ok!')
  								.targetEvent(event)
  						);
  						$scope.prograssing = false;
  					
  					}
  				}, function(response) { // optional
  					$mdDialog.show($mdDialog.alert()
  							.parent(angular.element(document.querySelector('#dialogContainer')))
  							.clickOutsideToClose(true)
  							.textContent("Finalize failed.")
  							.ok('Ok!')
  							.targetEvent(event)
  					);
  					$scope.prograssing = false;
  					

		});
		}
	//	}



	}


	$rootScope.$on('fun_save_data',function(event){		//Save Function
		$scope.saveData(event);
	});
		
	$scope.saveData=function(event){
		 $scope.stockItemList=[];
		if (form_validation($scope.formData)) {
			$scope.formData.stock_transfer_date = getMysqlFormat($scope.formData.stock_transfer_date1);
			$scope.formData.dest_company_id=$("#companyId").val();
			for(var i = 0;i<$scope.invoice.items.length;i++){
				$scope.invoice.items[i].amount = $scope.invoice.items[i].issued_qty * $scope.invoice.items[i].cost_price;
			}
			$scope.formData.total_amount = $scope.total();
			for(var i=0;i<$scope.invoice.items.length;i++){
				if($scope.invoice.items[i].issued_qty>0){
					
					 $scope.stockItemList.push($scope.invoice.items[i]);
				}
			}
			//$scope.formData.stockDetailLists=JSON.stringify($scope.invoice.items);
			$scope.formData.stockDetailLists=JSON.stringify($scope.stockItemList);
			var transfer_type=0;

			if(!$("#stkoptradioExternal").prop('checked')){
				transfer_type=1;
				$scope.formData.dest_company_id="";
			}
			$scope.formData.transfer_type=transfer_type;
			console.log($scope.formData);
			$http({
				url : 'saveStockTransfer',
				method : "POST",
				data:$scope.formData
			}).then(function(response) {

				if(response.data == 1 )
				{

					if($scope.formData.id !=undefined && $scope.formData.id !="")
					{
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
						view_mode_aftr_edit();
						$("#div_finlize_print").css("display","block");
					}
					else
					{
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
						$scope.hide_code_existing_er = true;
						$scope.formData={};
						fun_get_grnno();
						var curDate = moment().format("YYYY-MM-DD");
						$scope.formData.stock_transfer_date1 = geteditDateFormat(curDate);

						getSourcedep();
						if(settings['combinePurchase']==1)
							$scope.disable_code=false;
						else
							$scope.disable_code=true;

						$scope.invoice = {items: []};
						$scope.invoice.items.push({
							id: "",stock_item_id :"",stock_item_code:"",currentStock:"",
							request_qty: 0,issued_qty:0,cost_price: 0,tax_pc:0,compound_unit:0.0,base_uom_code:""
						});
						$("#form_div_source_department_code").find(".acontainer input").val("");
						$("#form_div_dest_department_code").find(".acontainer input").val("");
					}

				}else
				{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Save failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
				}}, function(response) { // optional

				});
		}
	}

	//Discard function
	$rootScope.$on("fun_discard_form",function(event){
		
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}
		).title(FORM_MESSAGES.DISCARD_WRNG).targetEvent(event).ok(
		'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {

			var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			if($scope.formData.id == undefined){
				$scope.formData ={};	
				$("#companyId option[value=' ']").prop('selected', true);
				fun_get_grnno();
				var curDate = moment().format("YYYY-MM-DD");
				$scope.formData.stock_transfer_date1 = geteditDateFormat(curDate);
				getSourcedep();				
				$scope.disable_code=true;

				$scope.invoice = {items: []};
				$scope.invoice.items.push({id: "",stock_item_id :"",stock_item_code:"",
					currentStock:"",request_qty: 0,issued_qty:0,cost_price: 0,tax_pc:0,compound_unit:0.0,base_uom_code:""
				});		 
				$("#form_div_dest_department_code").find(".acontainer input").val('');
			}else{
				
				//var dataIndex = vm.dtInstance.DataTable.rows();
				//var row_data = vm.dtInstance.DataTable.rows(dataIndex[0][cur_row_index]).data();
				edit($scope.transferList[cur_row_index],cur_row_index);
			}
			clearform();
			$("#form_div_source_department_code").removeClass("has-error");
			$("#form_div_source_department_code_error").hide();
			$("#form_div_dest_department_code").removeClass("has-error");
			$("#form_div_dest_department_code_error").hide();
			$("#form_div_dest_company").removeClass("has-error");
			$("#form_div_dest_company_error").hide();
		});

	});

	$rootScope.$on("fun_enable_inputs",function(){
		$('#show_form').val(1);
		$scope.disable_all = false;
		if($scope.formData.order_no != null && $scope.formData.order_no != "" 
			&& $scope.formData.order_no != undefined){
			$scope.disable_request_qty = true;
		}
		else{
			$scope.disable_request_qty = false;			
		}
		$scope.disable_code = true;
		$(".acontainer input").attr('disabled', false);
		$("#div_finlize_print").css("display","none");
		
		// enable radio button before edit 
		$("#stkoptradioExternal").attr('disabled', false);
		$("#stkoptradioInternal").attr('disabled', false);
		$('#companyId').attr('disabled',false);
	});

	$rootScope.$on("fun_enable_inputs_code", function() {
		$scope.disable_code = true;
		
	});

	 $scope.amount = function(item){
	    	var amount = 0.00;
	    	amount = item.issued_qty * item.cost_price;
	    	return parseFloat(amount).toFixed(2);
	    }
	    
	
	  $scope.taxAmt = function(item){
	    	var taxAmount = 0.00;
	    	taxAmount = item.cost_price * (item.tax_pc/100) * item.issued_qty;
	    	return parseFloat(taxAmount).toFixed(2);
	    }
	
	  $scope.taxAmtTtl = function(item){
	    	var taxAmtTotal = 0.00;
	    	
	    	taxAmtTotal = (item.cost_price * (item.tax_pc/100) * item.issued_qty) + (item.issued_qty * item.cost_price);

	    	return parseFloat(taxAmtTotal).toFixed(2);
	    }
	  
	  
	  $scope.total = function() {
	        var total = 0.00;
	        angular.forEach($scope.invoice.items, function(item) {
	        	
	            total += item.issued_qty * item.cost_price;
	        	  })
	        return parseFloat(total).toFixed(2);
	    }
	    
	    $scope.tax_total = function() {
	        var tax_total = 0.00;
	        angular.forEach($scope.invoice.items, function(item) {
	        	
	        	tax_total += item.cost_price * (item.tax_pc/100) * item.issued_qty;
	        	})
	       
	        return parseFloat(tax_total).toFixed(2);
	    }
	    
	    $scope.gendTotal = function(){
	    	var grandTotal = 0.00;
	    	angular.forEach($scope.invoice.items, function(item) {

	    		grandTotal += (item.issued_qty * item.cost_price) + (item.cost_price * (item.tax_pc/100) * item.issued_qty);
	    	})

	    	return parseFloat(grandTotal).toFixed(2);
	    }

	    $rootScope.$on("fun_clear_form",function(){

	    	$scope.hide_code_existing_er = true;
	    	$scope.formData={};

	    	//$("#companyId option[value='']").prop('selected', true);
	    	//	$('#companyId').val('');
	    	$('#companyId option').prop('selected', function() {
	    		return this.defaultSelected;
	    	});
	    	$scope.dest_department_data=[];
	    	fun_get_grnno();
	    	var curDate = moment().format("YYYY-MM-DD");
	    	$scope.formData.stock_transfer_date1 = geteditDateFormat(curDate);

	    	getSourcedep();
	    	if(settings['combinePurchase']==1)
	    	{
	    		$scope.disable_code = true;
	    	}
	    	else
	    	{
	    		$scope.disable_code=true;

	    	}

		$scope.invoice = {items: []};
		$scope.invoice.items.push({id: "",stock_item_id :"",stock_item_code:"",currentStock:"",request_qty: 0,issued_qty:0,cost_price: 0,tax_pc:0,compound_unit:0.0,base_uom_code:""
		});

	    	$scope.status ="";
	    	$scope.ClassName ="";
	    	$("#form_div_dest_department_code").find(".acontainer input").val('');
	    });

	    function fun_get_grnno(){
	    	$http.get('getCounterPrefix')
	    	.success(function (response) {
	    		$scope.formData.stock_transfer_no = response;
	    	});
	    }
	    
	    $rootScope.$on("fun_next_rowData",function(e,id) {

	    	/*var dataIndex = vm.dtInstance.DataTable.rows();
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();*/
	    	var current_row_index = parseInt(id.split("_")[1]);
	    	if(current_row_index == 0){
	    		$rootScope.$emit("enable_prev_btn");
	    	}

	    	if($scope.transferList.length == current_row_index + 1){
	    		$rootScope.$emit("disable_next_btn");
	    	}
	    	var next_row_index = current_row_index + 1;

	    	if($scope.transferList[next_row_index] != undefined){
	    		/*var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][next_row_index]).data();
			edit(selcted_row_data[0],next_row_index);*/
	    		edit($scope.transferList[next_row_index],next_row_index);
	    		$rootScope.$emit("next_formdata_set",next_row_index);
	    	}
	    });

	    $rootScope.$on("fun_prev_rowData",function(e,id) {

	    	/*var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var dataIndex = vm.dtInstance.DataTable.rows();
		//var dataIndex = $scope.filterRowData;
		var row_data = vm.dtInstance.DataTable.rows().data();*/
	    	var current_row_index = parseInt(id.split("_")[1]);
	    	if($scope.transferList.length - 1 == current_row_index){
	    		$rootScope.$emit("enable_next_btn");
	    	}
	    	var prev_row_index = current_row_index - 1;

	    	if($scope.transferList[prev_row_index] != undefined){
	    		/*var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][prev_row_index]).data();
			edit(selcted_row_data[0],prev_row_index);*/
	    		edit($scope.transferList[prev_row_index],prev_row_index);
	    		$rootScope.$emit("next_formdata_set",prev_row_index);
	    	}
	    	if(current_row_index-1 == 0){
	    		$rootScope.$emit("disable_prev_btn");
	    	}

	    });


	// Validation 
	    function form_validation(data){

	    	var flg = true;
	    	if ($scope.invoice.items[$scope.invoice.items.length - 1].stock_item_id == ""
	    		&& $scope.invoice.items.length != 1) {
	    		$scope.invoice.items.splice($scope.invoice.items.length - 1, 1);
	    	}
	    	if (validation() == false) {
	    		flg = false;
	    	}

	    	if ($scope.formData.id == undefined || $scope.formData.id == "") {
	    		if (!$scope.hide_code_existing_er) {
	    			flg = false;
	    			$("#stock_transfer_no").select();
	    		}
	    	}

	    	if($("#stkoptradioExternal").prop('checked')){
	    		if ($("#companyId").val().trim() == "") {
	    			$("#form_div_dest_company").addClass("has-error");
	    			$("#form_div_dest_company_error").show();

	    			flg = false;
	    		} else {
	    			$("#form_div_dest_company").removeClass("has-error");
	    			$("#form_div_dest_company_error").hide();
	    		}
	    	}

	    	if(!$("#stkoptradioExternal").prop('checked'))
	    	{
	    		if (($scope.formData.dest_department_id == "" && $scope.formData.dest_department_code== "" 
	    			&& $scope.formData.dest_department_name== "") || ($scope.formData.dest_department_id == undefined 
	    					&& $scope.formData.dest_department_code == undefined && $scope.formData.dest_department_name== undefined )) 
	    		{
	    			$("#form_div_dest_department_code").addClass("has-error");
	    			$("#form_div_dest_department_code_error").show();
	    			flg = false;

	    		}else {
	    			$("#form_div_dest_department_code").removeClass("has-error");
	    			$("#form_div_dest_department_code_error").hide();
	    		}
	    	}

	    	if ($scope.formData.source_department_id == ""  || $scope.formData.source_department_id == undefined) 
	    	{
	    		$("#form_div_source_department_code").addClass("has-error");
	    		$("#form_div_source_department_code_error").show();

	    		flg = false;
	    	} else {
	    		$("#form_div_source_department_code").removeClass("has-error");
	    		$("#form_div_source_department_code_error").hide();
	    	}

	    	if ($scope.invoice.items[$scope.invoice.items.length - 1].stock_item_id != undefined
	    			&& $scope.invoice.items[$scope.invoice.items.length - 1].stock_item_id != ""
	    				|| $scope.invoice.items.length == 1) {
	    		if ($scope.invoice.items.length > 0) {
	    			var count = 0;
	    			for (var i = 0; i < $scope.invoice.items.length; i++) {

	    			/*	if ($scope.invoice.items[i].stock_item_id != undefined && $scope.invoice.items[i].stock_item_id != "") {
	    					count = 0;
	    					if ($scope.invoice.items[i].issued_qty == "" || parseFloat($scope.invoice.items[i].issued_qty)<= 0 || $scope.invoice.items[i].issued_qty == undefined) {
	    						$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
	    						$("#items_table tr:nth-child(" + (i + 1) + ")").find("#issued_qty").select();
	    						flg = false;
	    						break;
	    					}
	    				} else {
	    					count = 1;
	    					$("#items_table tr:nth-child(" + (i + 1)+ ") td:nth-child(" + (3) + ")").find(".acontainer input").select();
	    					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
	    					flg = false;
	    					break;
	    				}*/

					if(parseFloat($scope.invoice.items[i].issued_qty)>parseFloat($scope.invoice.items[i].currentStock) )
					{
						$rootScope.$broadcast('on_AlertMessage_ERR',"Current Stock is Unavailable");
						$("#items_table tr:nth-child(" + (i + 1) + ")").find("#issued_qty").select();
						flg = false;
						break;
					}else if ($scope.invoice.items[i].request_qty == "" || parseFloat($scope.invoice.items[i].request_qty)<= 0 || $scope.invoice.items[i].request_qty == undefined) {
    						$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Transfer Qty");
    						$("#items_table tr:nth-child(" + (i + 1) + ")").find("#request_qty").select();
    						flg = false;
    						break;
    				}else if($scope.invoice.items[i].base_uom_code == "" || $scope.invoice.items[i].base_uom_code ==undefined){
    					
    					$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Transfer Unit");
						$("#items_table tr:nth-child(" + (i + 1) + ")").find("#base_uom_code").select();
						flg = false;
						break;
    				}else if($scope.invoice.items[i].currentStock == "" || $scope.invoice.items[i].currentStock ==undefined){
    					
    					$rootScope.$broadcast('on_AlertMessage_ERR',"Current Stock is unavailable");
						$("#items_table tr:nth-child(" + (i + 1) + ")").find("#issued_qty").select();
						flg = false;
						break;
    				}
				}
			}

	    		if ($scope.invoice.items.length == 0) {
	    			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.TABLE_ERR);
	    			flg = false;
	    		}
	    		if (flg == false) 
	    			focus();
	    	} else {
	    		$scope.invoice.items.splice($scope.invoice.items - 1, 1);

	    	}		
	    	return flg;			
	    }

	$scope.invoice = {
			items: []
	};

	$scope.invoice.items.push({id: "",stock_item_id :"",stock_item_code:"",currentStock:"",request_qty: 0,issued_qty:0,cost_price: 0,tax_pc:0,compound_unit:0.0,base_uom_code:""
	});

	$scope.addItem = function(index) {

		if($scope.disable_all == false){
			if(index<$scope.invoice.items.length-1){
				$("#items_table tr:nth-child("+(index+3)+") td:nth-child("+(3)+")").find(".acontainer input").focus();
			}else{
				if($scope.invoice.items.length != 0){
					if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id != "" && $scope.invoice.items[$scope.invoice.items.length-1].stock_item_code != ""){

	/*					if($scope.invoice.items[$scope.invoice.items.length-1].issued_qty == "" || parseFloat($scope.invoice.items[$scope.invoice.items.length-1].issued_qty)<0 ){
							$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Issued Qty");
							$("#items_table tr:nth-child("+($scope.invoice.items.length)+")").find("#issued_qty").select();


						}*/
					    if((parseFloat($scope.invoice.items[$scope.invoice.items.length-1].issued_qty)> parseFloat($scope.invoice.items[$scope.invoice.items.length-1].currentStock))){

							$rootScope.$broadcast('on_AlertMessage_ERR',"Incorrect Issued Qty");
							$("#items_table tr:nth-child("+($scope.invoice.items.length)+")").find("#issued_qty").select();


						}
						
						else{

							$scope.invoice.items.push({id: "",stock_item_id :"",stock_item_code:"",currentStock:"",request_qty: 0,issued_qty:0,cost_price: 0,tax_pc:0,compound_unit:0.0,base_uom_code:""
							});							
							$("#items_table tr:nth-child(" + ($scope.invoice.items.length)+ ") td:nth-child(" + (3) + ")").find(".acontainer input").focus();

						//	$("#items_table tr:nth-child(2) td:nth-child(3)").find(".acontainer input").select();
						}
					}
					else{		    					
						$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);

						$("#items_table tr:nth-child("+($scope.invoice.items.length)+")").find(".acontainer input").focus();
						if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id == "" && $scope.invoice.items[$scope.invoice.items.length-1].stock_item_code == ""){
							$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
						}	

					}

				}	
				else if($scope.invoice.items.length == 0){
					$scope.invoice.items.push({id: "",stock_item_id :"",stock_item_code:"",currentStock:"",request_qty: 0,issued_qty:0,cost_price: 0,tax_pc:0,compound_unit:0.0,base_uom_code:""
					});

					$("#items_table tr:nth-child(" + ($scope.invoice.items.length)+ ") td:nth-child(" + (3) + ")").find(".acontainer input").focus();

				}
			}}
	},	
	
	$scope.removeItem = function(index,event) {
		if($scope.disable_all == false){
			
						//Discard function
				var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
					var $dialog = angular.element(document.querySelector('md-dialog'));
					var $actionsSection = $dialog.find('md-dialog-actions');
					var $cancelButton = $actionsSection.children()[0];
					var $confirmButton = $actionsSection.children()[1];
					angular.element($confirmButton).removeClass('md-focused');
					angular.element($cancelButton).addClass('md-focused');
					$cancelButton.focus();
				}}
				).title(FORM_MESSAGES.ROW_DELETE_WRNG).targetEvent(event).ok(
				'Yes').cancel('No');
				$mdDialog.show(confirm).then(function() {

	
			if(index == 0 && $scope.invoice.items.length==1){
				$rootScope.$broadcast('on_AlertMessage_ERR',"Atleast one item is required.");
			}
			else
			{
				$scope.invoice.items.splice(index, 1);
				$("#items_table tr:nth-child("+($scope.invoice.items.length)+")").find("#issued_qty").focus();	
			}

				});
			
		}
			
	},	

	$scope.alert_for_codeexisting = function(flg){
		if(flg==0){
		}else if(flg == 1){
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.CODE_EXISIT);
		}

	}

	$scope.clear_stock_details_editmode =  function(event){

		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].stock_item_id = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].stock_item_code = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].stock_item_name = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].cost_price = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].currentStock = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].issued_qty = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].uomcode = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].request_qty = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].tax_pc = 0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].base_uom_code="";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].compound_unit=0.0;
	}

	$("#dispose_by").keyup('input',function(e){
		if(e.which!=9 || e.which==13){
			$("#items_table tr:nth-child("+($scope.invoice.items.length+2)+")").find(".acontainer input").focus();
		}});
	
	
	$(document).on('keyup','#form_div_source_department_code input',function(e){
		
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$('#source_department_code').val("");
				$scope.formData.source_department_code=  "";
				$scope.formData.source_department_name =  "";
				$scope.formData.source_department_id = "";
				$scope.getChangeStock();
				});
			}
		});

	
	
	//autocompelete source department 
	/*var sourceDepartmentData = $("#source_department_code").tautocomplete({
		columns: ['id','code','name'],
		hide: [false,true,true],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay : 10,
		onchange: function () {
			var selected_department_data =  sourceDepartmentData.all();
			$scope.$apply(function(){
				$scope.formData.source_department_id =  selected_department_data.id;
				$scope.formData.source_department_code =  selected_department_data.code;
				$scope.formData.source_department_name =  selected_department_data.name;
				$scope.getChangeStock();
			});},
			data: function () {
				var data = $scope.source_department_data;
				var filterData = [];
				var searchData = eval("/^" + sourceDepartmentData.searchdata() + "/gi");
				$.each(data, function (i, v) {
					if (v.name.search(new RegExp(searchData)) != -1) {
						filterData.push(v);}});
				return filterData;}});*/

	
	$(document).on('keyup','#form_div_dest_department_code  input',function(e){
		
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$('#dest_department_code').val("");
				$scope.formData.dest_department_code=  "";
				$scope.formData.dest_department_name =  "";
				$scope.formData.dest_department_id = "";
				});
			}
		});	
	
	//autocompelete dest department 
	/*var destDepartmentData = $("#dest_department_code").tautocomplete({
		columns: ['id','code','name'],
		hide: [false,true,true],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay : 10,
		onchange: function () {
			var selected_department_data =  destDepartmentData.all();
			$scope.$apply(function(){
				$scope.formData.dest_department_id =  selected_department_data.id;
				$scope.formData.dest_department_code =  selected_department_data.code;
				$scope.formData.dest_department_name =  selected_department_data.name;
			});
		},
		data: function () {
			var data = $scope.dest_department_data;
			var filterData = [];
			var searchData = eval("/^" + destDepartmentData.searchdata() + "/gi");
			$.each(data, function (i, v) {
				if ( v.name.search(new RegExp(searchData)) != -1) {
					filterData.push(v);}});
			return filterData;
		}
	});
*/
	$scope.getItemmasterCurrentStock = function(itemid,deptId,curdate,index) {
		var current_stock=0;
		var checkReturn=0;
		if(curdate!="" && curdate!=undefined && itemid != "")	
		{
			$http({
				url : '../stockout/getCurrStock',
				method : "GET",
				params:{checkReturn:checkReturn,itemId:itemid,department_id:deptId,current_date:getMysqlFormat(curdate)},
				async : false,
			}).then(function(response) {
				current_stock = response.data[1];
				$scope.invoice.items[index].currentStock = parseFloat(current_stock).toFixed(settings['decimalPlace']);
			}, function(response) { // optional

			});
		}
		return parseFloat(current_stock).toFixed(settings['decimalPlace']);

	}
	
	$scope.getChangeStock = function()
	{
		if($scope.invoice.items.length > 0)
		{
			for(var i=0; i < $scope.invoice.items.length; i++)
			{
				$scope.getItemmasterCurrentStock($scope.invoice.items[i].stock_item_id,
						$scope.formData.source_department_id,$scope.formData.stock_transfer_date1,i)
			}
		}
	}
	
	$scope.loadFillData = function(){

		if($scope.type_of_order == '0'){
			$scope.fillData = $scope.shops;
			$scope.customer_id = "";
		}else{
			
			$scope.fillData = $scope.customers;
			$scope.customer_id = "";						
		}
		
	}	

	//retrieve punit  @gana 22012020
	$scope.setuomCode=function(uomCode,index){

		$http({

			url:'../stockin/getPunit',
			async:false,
			method:'POST',
			params:{uomCode:uomCode},
		}).then(function(response){

			$scope.baseUomCode[index]=response.data.baseUom;

		});
	}

	//change punit and calc @gana 22012020
	$scope.selectPuomCode=function(baseUomCode,recQty,index){

		$scope.invoice.items.compound_unit=[];
		$scope.invoice.items.pack_qty=[];
		for(i=0;i<$scope.baseUomCode[index].length;i++){

			if($scope.baseUomCode[index][i].base_uom_code==baseUomCode){

				$scope.invoice.items[index].compound_unit=$scope.baseUomCode[index][i].compound_unit;
				$scope.invoice.items[index].base_uom_code=baseUomCode;
			}
		}
		$scope.invoice.items[index].issued_qty=$scope.invoice.items[index].compound_unit*recQty;	

	}
	
	$scope.orderDetails = function(orderListItem){
		
		$scope.status ="";
		$scope.ClassName ="";
		$("#form_div_source_department_code").removeClass("has-error");
		$("#form_div_source_department_code_error").hide();
		$("#form_div_dest_department_code").removeClass("has-error");
		$("#form_div_dest_department_code_error").hide();
		$("#form_div_dest_company").removeClass("has-error");
		$("#form_div_dest_company_error").hide();
		showTable();
		clearform();
		var curDate = moment().format("YYYY-MM-DD");
		$scope.formData.stock_transfer_date1 = geteditDateFormat(curDate);
		fun_get_grnno();
		$('#show_form').val(1);
		$scope.disable_all = false;
		$(".acontainer input").attr('disabled', false);
		$("#div_finlize_print").css("display","none");		
		// enable radio button before edit 
		$("#stkoptradioExternal").attr('disabled', false);
		$("#stkoptradioInternal").attr('disabled', false);
		$("#div_btn_new").show();
		$('#div_btn_add').hide();
		$('#btnDiscard').hide();
		
		$scope.status ="";
		$scope.ClassName ="";
		if(orderListItem.order_type == 'Shop')
		{
			$("#form_div_dest_company").show();
			$("#form_div_source_department_code").show();
			$("#form_div_dest_department_code").hide();
			$("#companyId option[data-dr='"+ orderListItem.order_from +"']").prop('selected', true);
			$('#companyId').attr('disabled',true);
		}
		else{
			$("#stkoptradioInternal").attr('checked',true);
			$("#form_div_dest_company").hide();
			$("#form_div_source_department_code").show();
			$("#form_div_dest_department_code").show();
			$("#form_div_dest_department_code").find(".acontainer input").val(orderListItem.source_department_code);
			$scope.formData.dest_department_id = orderListItem.source_department_id;
			$scope.formData.dest_department_code = orderListItem.source_department_code;
			$scope.formData.dest_department_name = orderListItem.source_department_name;
		}
		$scope.formData.order_date = orderListItem.order_date;
		$scope.formData.order_no = orderListItem.order_no;
		$scope.formData.source_department_id = "";
		$scope.formData.order_id = orderListItem.order_id;
		getOrderDtls(orderListItem.order_id);
		$scope.disable_request_qty = true;
		$('#stkoptradioInternal, #stkoptradioExternal').attr('disabled',true);
		$scope.disable_code = true;
		$scope.disable_add_remove = false;
	}
	
	$scope.transferDetails = function(item, element){
		clearform();
		$('#show_form').val(1);
		$scope.formData.stock_transfer_date1 = item.stock_transfer_date;
		$scope.formData.order_date = item.order_date;
		$scope.formData.order_no = item.order_no;
		if(item.order_no != null && item.order_no != "" && item.order_no != undefined){
			$scope.disable_request_qty = true;
			$scope.disable_add_remove = false;
		}
		edit(item,element);
	}
	
	$scope.onChangeSourceDepartment = function(){
		
		/*if($scope.formData.order_id != "" && $scope.formData.order_id != undefined)
			getOrderDtls($scope.formData.order_id, $scope.formData.source_department_id);*/
		$scope.getChangeStock();
		
	}
	
	function getOrderDtls(order_id, department_id){
		$scope.prograssing = true;
		 $scope.invoice = {items: []};
		$http({
			url : 'getOrderDtlData',
			method : "GET",
			params : { order_id : order_id }
		}).then(function(response) {
			$scope.invoice.items = response.data.orderDtlList;
			var default_val = 0.0;
			if($scope.invoice.items.length == 0)
				$rootScope.$broadcast('on_AlertMessage_ERR',"No items available");
			else{
				for(i=0; i<response.data.orderDtlList.length;i++){
					$scope.invoice.items[i].tax_pc = default_val;
					$scope.invoice.items[i].issued_qty = default_val;				
					$scope.invoice.items[i].stock_item_code = response.data.orderDtlList[i].stock_item_code;
					$scope.invoice.items[i].id = "";
					$scope.invoice.items[i].flag = 0;
					$scope.invoice.items[i].base_uom_code=response.data.orderDtlList[i].base_uom_code;
					baseUomCode[i] = $scope.setuomCode($scope.invoice.items[i].uomcode,i);
				}
			}
			$scope.prograssing = false;
		})		
	}
}

mrpApp.directive('autocompeteText', ['$timeout',function($timeout) {
	return {

		controller: function ($scope,$http) {


		
			$scope.currentIndex = 0;
			$("#items_table tbody tr td").keyup('input',function(e){
				$scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;

				if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){
					if(e.currentTarget.cellIndex == 2){
						$scope.$apply(function(){
							$scope.clear_stock_details_editmode(e);
							$scope.alert_for_codeexisting(false);
						});
					}
				}else if(e.which == 13 ){
					if(e.currentTarget.cellIndex == 2){
						if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id!="" || $scope.invoice.items[$scope.invoice.items.length-1].stock_item_id!=undefined){
							{ 
							$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+") td:nth-child("+(5)+")").find("#request_qty").select();
							}
						}}
				}else if(e.which == 9 ){
					if(e.currentTarget.cellIndex == 2){


						{

							$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+") td:nth-child("+(3)+")").find(".acontainer input").focus();
						}
					}
				
				}

			});
			$scope.element = [];
			$scope.table_itemsearch_rowindex=0;
			$scope.tableClicked = function (index) {

				$scope.table_itemsearch_rowindex= index;
			};
			return $scope;
		},
		link: function(scope, elem, attrs ,controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns: ['id','code','name','input_tax_id','tax_percentage','valuation_method','is_active','department_id',
				          'department_code','is_manufactured','uomcode','uomname','unit_price'],
	    		hide: [false,true,true,false,false,false,false,false,false,false,false,false,false,false,false],
				placeholder: "search ..",
				highlight: "hightlight-classname",
				norecord: "No Records Found",
				delay : 10,
				onchange: function () {
					var selected_item_data =  items.all();
					strl_scope.$apply(function(){
						var count =0;
						for(var i=0;i<strl_scope.invoice.items.length;i++){
							if(selected_item_data.id != ""){
								if(i != strl_scope.currentIndex){
									if(selected_item_data.id == strl_scope.invoice.items[i].stock_item_id){
										count=1;
									}
								}
							}
						}
						if(count != 1){
							if(selected_item_data.id!="")
							{
								strl_scope.invoice.items[strl_scope.currentIndex].stock_item_id = selected_item_data.id;
								strl_scope.invoice.items[strl_scope.currentIndex].stock_item_code = selected_item_data.code;
								strl_scope.invoice.items[strl_scope.currentIndex].stock_item_name = selected_item_data.name;
								
			    				 strl_scope.invoice.items[strl_scope.currentIndex].uomcode= selected_item_data.uomcode;
									strl_scope.invoice.items[strl_scope.currentIndex].base_uom_code = strl_scope.setuomCode(selected_item_data.uomcode,strl_scope.currentIndex);
			    				 strl_scope.invoice.items[strl_scope.currentIndex].cost_price=selected_item_data.unit_price;
			    				 strl_scope.getItemmasterCurrentStock(selected_item_data.id,strl_scope.formData.source_department_id
			    						 ,strl_scope.formData.stock_transfer_date1,strl_scope.currentIndex);

									$timeout(function() {$("#items_table tr:nth-child("+ (strl_scope.currentIndex + 1)+ ")").find("#base_uom_code").focus();},1); 

								strl_scope.alert_for_codeexisting(0);
							}


						}else{
							elem[0].parentNode.lastChild.value="";
							strl_scope.alert_for_codeexisting(1);
						}

					});
				},
				data: function () {

					var data = strl_scope.itemsMastersData;

					var filterData = [];

					var searchData = eval("/" + items.searchdata() + "/gi");

					$.each(data, function (i, v) {

						if ( v.NAME.search(new RegExp(searchData)) != -1) {
							//if (v.department_id == strl_scope.formData.source_department_id) 
								filterData.push(v);
						}
					});

					return filterData;
				}
			});

			for(var i = 0;i<strl_scope.invoice.items.length;i++){
				if(strl_scope.formData.id != undefined || strl_scope.formData.id != '' ){
					if(strl_scope.invoice.items[i].flag==0){
						$("#items_table tr:nth-child("+(i+1)+")").find(".acontainer input").attr('disabled', true);
						elem[0].parentNode.lastChild.value = strl_scope.invoice.items[i].stock_item_code;
						strl_scope.disable_search_text(elem[0].parentNode.lastChild);
						strl_scope.invoice.items[i].flag=1;break;
					}
				}
			}$timeout(function () {

				/*if(strl_scope.formData.dest_department_id!=undefined)*/
					$("#items_table tr:nth-child("+(strl_scope.invoice.items.length)+") td:nth-child("+(3)+")").find(".acontainer input").select();
			}, 1); 
		}
	};
}]);