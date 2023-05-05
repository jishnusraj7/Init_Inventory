
//Controller for Table and Form 
mrpApp.controller('stockin', stockin);
function stockin($compile,$controller,$scope,$interval,$timeout,$http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,MRP_CONSTANT,DATATABLE_CONSTANT ,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS,STATUS_BTN_TEXT,RECORD_STATUS_STYLE,$q , $window) {

	$controller('DatatableController', {$scope: $scope});
	/*	set_sub_menu("#store");						
		setMenuSelected("#stockin_left_menu");	*/		//active leftmenu
	manageButtons("add");
	$scope.formData = {};
	$("#advsearchbox").show();

//	$scope.editData=false;
	$("#grn_code").css("display","none");
	$("#grnCode_label").css("display","none");
	$scope.BTNTEXT="FINALIZE";

	$scope.show_table=true;
	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
	//$scope.GRNNO = "";
	$scope.stockInDetailsList =[];
	$scope.table_validation_alert_msg="";
	$scope.status = "";
	$scope.ClassName = "";
	$scope.searchTxtItms={};
	$scope.searchTxt1="";
	var paymentMode;
	$scope.baseUomCode=[];
	var baseUomCode="";
	
	$scope.BindUomCode=[];
	

	$('#selectall').prop('checked', false);
	$scope.ispoTableHide = true;
	if(stockInStrings['stockintype'] == 3 || stockInStrings['stockintype'] ==2){
		$scope.show_suppler = false;
		$scope.ispoTableHide = true;
	}else{
		$scope.show_suppler = true;
		$scope.formData.supplierId = "";
		$scope.formData.supplierCode ="";
		$scope.formData.supplierName = "";
		$("#form_div_supplier_code").find(".acontainer input").val("");
		$scope.ispoTableHide = false;
	}

	$scope.getCurrentDate = function(){									//getCurrentdate
		$http.get('getCurrentDate')
		.success(function (response) {
			$scope.CurrentDate1 = geteditDateFormat(response);
			return $scope.CurrentDate1;
		});
	}
	var date =get_date_format();
	$scope.CurrentDate = date.mindate;
	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.form_validation = form_validation;
	vm.setItemtableValues = setItemtableValues;
	vm.loadItemDetailsTable  = loadItemDetailsTable;
	vm.fun_get_grnno = fun_get_grnno;
	vm.fun_get_dep_name = fun_get_dep_name;
	vm.fun_set_def_dep_supplier = fun_set_def_dep_supplier;
	vm.fun_get_stockRegId = fun_get_stockRegId;
	vm.function_clear_select = function_clear_select;
	vm.setFormDatas = setFormDatas;
	vm.setPoDetails = setPoDetails;
	$scope.stockRegisterList=[];


	//common confirm box
	$scope.showConfirm = function(title,sourse,event) {
		//debugger
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
			}else if(sourse=="DISCARD"){	
				$scope.discardData();
			}else if(sourse==STATUS_BTN_TEXT.FINALIZE){	
				$scope.finalize();
			}

			else if(sourse == STATUS_BTN_TEXT.PRINT){
				$scope.print();
			}else if (sourse=="rowDelete")  {
				$scope.removeRowItem(event);
			}  	
		}, function() { 
		});
	};

	//common alert box
	$scope.alertBox = function(msg,event){
		//debugger
		$mdDialog.show($mdDialog.alert()
				.parent(angular.element(document.querySelector('#dialogContainer')))
				.clickOutsideToClose(true)
				.textContent(msg)
				.ok('Ok!')
				.targetEvent(event)
		);
	}

	//get GRF_No from counter with prefix
	/*$http.get('getCounterPrefix')
	.success(function (response) {
		$scope.GRNNO = response;
	});*/


	function fun_get_grnno(){
		//debugger
		$http.get('getCounterPrefix')
		.success(function (response) {
			$scope.formData.grnNo = response;
		});
	}

	//advance filter for new-finalize-print
	$scope.statusFilters = [ 0, 1, 2 ];
	$scope.selectedStatus = [ 0, 1, 2 ];
	$scope.toggle = function(item, list) {
		var idx = list.indexOf(item);
		if (idx > -1) {
			list.splice(idx, 1);
		} else {
			list.push(item);
		}
		statFilter = list.join();
		DataObj.adnlFilters = [ {
			col : 7,
			filters : statFilter
		} ];
		vm.dtInstance.reloadData();
	};

	$scope.exists = function(item, list) {
		return list.indexOf(item) > -1;
	};

	$scope.isIndeterminate = function() {
		return ($scope.selectedStatus.length !== 0 && $scope.selectedStatus.length !== $scope.statusFilters.length);
	};

	$scope.isChecked = function() {
		return $scope.selectedStatus.length === $scope.statusFilters.length;
	};

	$scope.toggleAllStatus = function() {
		if ($scope.selectedStatus.length === $scope.statusFilters.length) {
			$scope.selectedStatus = [];
		} else if ($scope.selectedStatus.length === 0
				|| $scope.selectedStatus.length > 0) {
			$scope.selectedStatus = $scope.statusFilters.slice(0);
		}
		statFilter = $scope.selectedStatus.join();
		DataObj.adnlFilters = [ {
			col : 7,
			filters : statFilter
		} ];
		vm.dtInstance.reloadData();
	};
	//create datatable for list
	vm.dtInstance = {};	
	var DataObj = {};		
	var statFilter="0, 1, 2";
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.adnlFilters=[{col:7,filters:statFilter}];
	DataObj.order = "desc";
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	if(settings['combinePurchase']==0)
	{

		vm.dtColumns = [
			DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
			DTColumnBuilder.newColumn('grn_no').withTitle('REF. NO').withOption('type', 'natural').renderWith(
					function(data, type, full, meta) {
						return urlFormater(data);  
					}),
					DTColumnBuilder.newColumn('received_date').withTitle('DATE').renderWith(
							function(data, type, full, meta) {
								data = geteditDateFormat(data);
								return data;
							}),
							DTColumnBuilder.newColumn('supplier_name').withTitle('SUPPLIER').withOption('width','300px'),
							DTColumnBuilder.newColumn('supplier_doc_no').withTitle('SUPP. REF NO'),
							DTColumnBuilder.newColumn('payment_mode').withTitle('PAYMENT').renderWith(
									function(data, type, full, meta) {

										if(data==0){
											data =  "<span style='color:green;'>"+full.payment_type+"</span>";
										}else{
											data =  "<span style='color:red;'>"+full.payment_type+"</span>";
										}
										return data;
									}),

									DTColumnBuilder.newColumn('stock_in_type').withTitle('STOCK IN TYPE'),
									DTColumnBuilder.newColumn('status').withTitle('STATUS').renderWith(
											function(data, type, full, meta) {
												if(data == 0){
													data =  "<span style='color:green;'>"+RECORD_STATUS.PENDING+"</span>";
												}else if(data ==1){
													data = "<span style='color:#3c8dbc;'>"+RECORD_STATUS.FINALIZED+"</span>";


												}else if(data ==2){
													data ="<span style='color:red;'>"+RECORD_STATUS.PRINTED+"<span>";
												}
												return data;
											}),	DTColumnBuilder.newColumn('finalized_date').withTitle('').notVisible(),
											];

	}

	else
	{

		vm.dtColumns = [
			DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
			DTColumnBuilder.newColumn('grn_no').withTitle('REF. NO').withOption('type', 'natural').renderWith(
					function(data, type, full, meta) {
						return urlFormater(data);  
					}),
					DTColumnBuilder.newColumn('received_date').withTitle('DATE').renderWith(
							function(data, type, full, meta) {
								data = geteditDateFormat(data);
								return data;
							}),
							DTColumnBuilder.newColumn('supplier_name').withTitle('SUPPLIER').withOption('width','300px'),
							DTColumnBuilder.newColumn('supplier_doc_no').withTitle('SUPP. REF NO'),
							DTColumnBuilder.newColumn('payment_mode').withTitle('PAYMENT').renderWith(
									function(data, type, full, meta) {
										if(data==0){
											data =  "<span style='color:green;'>"+full.payment_type+"</span>";
										}else{
											data =  "<span style='color:red;'>"+full.payment_type+"</span>";
										}
										return data;
									}),

									DTColumnBuilder.newColumn('status').withTitle('STOCK IN TYPE').notVisible(),
									DTColumnBuilder.newColumn('status').withTitle('STATUS').renderWith(
											function(data, type, full, meta) {
												if(data == 0){
													data =  "<span style='color:green;'>"+RECORD_STATUS.PENDING+"</span>";
												}else if(data ==1){
													data = "<span style='color:#3c8dbc;'>"+RECORD_STATUS.FINALIZED+"</span>";


												}else if(data ==2){
													data ="<span style='color:red;'>"+RECORD_STATUS.PRINTED+"<span>";
												}
												return data;
											}),	DTColumnBuilder.newColumn('finalized_date').withTitle('').notVisible(),
											];


	}



	//url for edit
	function urlFormater(data) {
		//debugger
		return '<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
		+ data + '</a>';   
	}

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {	
		//debugger				//Rowcallback fun for Get Edit Data when clk on Code
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

	$scope.filterRowData = [];
	function fnFooterCallback(nRow, aaData, iStart, iEnd, aiDisplay){
		//debugger
		$scope.filterRowData = [];
		$scope.filterRowData.push(aiDisplay);
	}

	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
	//debugger
		$scope.start = start;
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){
			return pageInfo.page +" / "+pageInfo.pages;
		}else{
			return pageInfo.page+1 +" / "+pageInfo.pages;
		}
	}


	$("#advsearchbox").hide();

	$scope.show_table=false;
	$scope.prograssing1=true;
	$('#btnAdd').hide();

	var supplier_data = [];
	var department_data = [];
	var itemsMastersData=[];
	$scope.stockInDetailsList = [];
	$scope.itemsMastersData = [];
	$scope.itemsBatchData = [];
	$http({
		url : 'formJsonDataStkin',
		method : "GET",

	}).then(function(response) {
//debugger
		$scope.department_data = response.data.depData;
		// $scope.stockInDetailsList = response.data.stockInDtlData;
		$scope.itemsMastersData = response.data.stockItmDatastkin;
		/*				 $scope.itemsBatchData = response.data.stockItmBatchData;
		 */				/* $scope.stockRegisterList = response.data.stockRegData;*/
		itemsMastersData=response.data.stockItmDatastkin;
		supplier_data = response.data.supData;
		$("#advsearchbox").show();
		$scope.prograssing1=false;
		$scope.show_table=true;
		$('#btnAdd').show();
	}, function(response) { // optional

	});


	//finalize stockin
	$scope.filalize_stockin = function(event){
//debugger
		if($("#div_finlize_print button").text() == "FINALIZE"){
			$scope.showConfirm(FORM_MESSAGES.FINALIZE_WRNG,STATUS_BTN_TEXT.FINALIZE,event);	
		}else if($("#div_finlize_print button").text() == "PRINT" || $("#div_finlize_print button").text() == "RE-PRINT"){
			$scope.print();

		}

	}
	$scope.normal_print_stockin = function(event){
//debugger
		if($("#div_normal_print button").text() == "PRINT PO"){
			$scope.normalprint();
		}
		
	}

	$scope.finalize = function(){
//debugger
		setFormDatas();
		$scope.formData.finalizedBy = strings['userID'];
		$scope.formData.finalizedDate = $("#finalized_date").val();
		$scope.formData.status = 1;
		if (form_validation($scope.formData)) {
			$scope.formData.stockDetailLists=JSON.stringify($scope.stockDtlList);
			$http({
				url : 'finalize',
				method : "POST",
				/*		params:{stkDtls:$scope.stockDtlList},*/
				data : $scope.formData
			}).then(function(response) {
				manageButtons("view");
				//$("#btnDelete,#btnEdit").css("display","none");
				$("#div_finlize_print button").text("PRINT");
				$scope.status =RECORD_STATUS.FINALIZED;

				$("#btnEdit").hide();
				$scope.ClassName ="form_list_con  approved_bg";
				vm.dtInstance.reloadData(null, true);


				//new for refresh itemrate

				$http({
					url : 'formJsonDataStkin',
					method : "GET",

				}).then(function(response) {
					$scope.itemsMastersData = response.data.stockItmDatastkin;
				}, function(response) { // optional

				});











			}, function(response) { // optional
				$scope.alertBox(FORM_MESSAGES.FINALIZE_ERR);
			});
		}
	}


	//print stockin
	$scope.print = function(){
		//debugger
		$scope.formData.status=2;
		$window.open("../stockin/report?pdfExcel=pdf&reportName=stockin&id="+$scope.formData.id+"", '_blank');
		$scope.status =RECORD_STATUS.PRINTED;
		$scope.ClassName ="form_list_con print_bg";
		$("#div_finlize_print button").text("RE-PRINT");
		$timeout(function () { vm.dtInstance.reloadData();}, 1); 
	}
	
	//print stockin
	$scope.normalprint = function(){
		//debugger
		$scope.formData.status=2;
		$window.open("../stockin/normalreport?pdfExcel=pdf&reportName=stockin&id="+$scope.formData.id+"", '_blank');
		//$scope.status =RECORD_STATUS.PRINTED;
		$scope.ClassName ="form_list_con print_bg";
		//$("#div_finlize_print button").text("RE-PRINT");
		$timeout(function () { vm.dtInstance.reloadData();}, 1); 
	}



	/*...........select Stock In type.........*/
	$scope.isStoreToStore = true;
	$scope.selectStockIntype = function(){
		//debugger
		$('#selectall').prop('checked', false);
		$scope.poHder = {items: []};

		if($scope.stockInTypeOldVal ==0 || $scope.stockInTypeOldVal ==1 
				|| $scope.formData.stockInType==0 || $scope.formData.stockInType==1)
		{
			$scope.invoice = {items: []};
			$scope.invoice.items.push({sino:1,id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",po_id : "",po_no : "",po_qty: 0,current_stock:0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isDeleted:false,compound_unit:0.0,base_uom_code:""});
		}
		$scope.stockInTypeOldVal=$scope.formData.stockInType;
		if($scope.formData.stockInType == 2){
			$("#form_div_source_company").removeClass("has-error");
			$("#form_div_source_company_error").hide();
			$scope.formDatasetSupplier(supplier_data[1].id,supplier_data[1].code,supplier_data[1].name,false,true,true);
			$("#form_div_supplier_code").find(".acontainer input").val(supplier_data[1].code);
			$("#companyId option[value='0']").prop('selected', true);
		}
		else if($scope.formData.stockInType == 3 ){


			$scope.formDatasetSupplier("","","",true,false,true);
			$("#form_div_supplier_code").find(".acontainer input").val("");
			$("#form_div_source_company").removeClass("has-error");
			$("#form_div_source_company_error").hide();

		}else{
			$scope.formDatasetSupplier("","","",true,true,false)
			$("#form_div_supplier_code").find(".acontainer input").val("");
		}


	}


	//set supplier data
	$scope.formDatasetSupplier = function(id,code,name,isStoreToStore,show_suppler,ispoTableHide){
		//debugger
		$scope.isStoreToStore = isStoreToStore;
		$scope.show_suppler = show_suppler;
		$scope.ispoTableHide=ispoTableHide;
		$scope.formData.supplierId = id;
		$scope.formData.supplierCode =code;
		$scope.formData.supplierName = name;
	}

	$rootScope.$on('reloadDatatable',function(event){		
		//debugger			//reload Datatable
		reloadData();
	});

	$rootScope.$on('hide_table',function(event){
		showTable(event);
	});

	$rootScope.$on('hide_form',function(event){
		$("#advsearchbox").show();
		$scope.show_table=true;
		$scope.show_form=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
		//vm.dtInstance.reloadData();
		$scope.show_suppler = false;
		$scope.ispoTableHide = true;
		$('#show_form').val(0);
		$scope.hide_code_existing_er = true;

	});

	function showTable(event){
		$("#div_finlize_print").css("display","none");
		$("#div_normal_print").css("display","none");
		$("#btnDiscard,#btnSave").attr("disabled",false);	
		$scope.show_table=false;
		$("#advsearchbox").hide();
		//$('#itemLists').hide();
		$scope.show_form=true;
	}

	$rootScope.$on("disable_status",function(){
		$scope.status = "";
		$scope.ClassName ="form_list_con";
	});

	function loadItemDetailsTable(id){
//debugger
		$http({
			url : 'stockRegJsonData',
			method : "GET",
		}).then(function(response) {
//debugger
			$scope.stockInDetailsList = response.data.stockInDtlData;
			for(i=0;i<$scope.stockInDetailsList.length;i++){

				$scope.baseUomCode[i] =$scope.stockInDetailsList[i].base_uom_code;
			}

		}, function(response) { // optional 

		});
	}
	function reloadData() {
		//debugger
		vm.dtInstance.reloadData(null, true);
		loadItemDetailsTable();
	}

	$scope.reloadDatatable = function(e){
		//debugger
		var filter_id = e.target.id;
		if(filter_id == "isall"){
			$("#waiting,#finalized,#printed").prop("checked",false);
		}else if(filter_id == "waiting" || filter_id == "finalized" || filter_id == "printed"){
			$("#isall").prop("checked",false);
			if($("#waiting").is(':checked') == false && $("#finalized").is(':checked') == false && $("#printed").is(':checked') == false ){
				$("#isall").prop("checked",true);
			}
		}
		vm.dtInstance.reloadData(null, true);
	}

	//edit function
	function edit(row_data,cur_row_index,event) {
		//debugger
		//$scope.editData=true;
		$("#grn_code").css("display","block");
		$("#grnCode_label").css("display","block");
		
		$scope.ClassName = "";
		$scope.ispoTableHide=true;
		//alert("stock in 1==========>");
		$("#status_div_id").css("display","block");



		if(row_data.status == 0){
			$scope.status = RECORD_STATUS.PENDING;
			$scope.ClassName ="form_list_con pending_bg";
		}else if(row_data.status == 1){
			$scope.status =RECORD_STATUS.FINALIZED;
			$scope.ClassName ="form_list_con  approved_bg";
		}else if(row_data.status == 2){
			$scope.status =RECORD_STATUS.PRINTED;
			$scope.ClassName ="form_list_con print_bg";
		}


		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function
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
		$scope.hide_code_existing_er = true;

		function_clear_select();
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.formData.grnNo = row_data.grn_no;
       
		//getPunitList(baseUomCode,cur_row_index);
		$scope.CurrentDate = geteditDateFormat(row_data.received_date);
		var selectedOption = parseInt(row_data.stock_in_type)+1;
		/*	var depList = fun_get_dep_name(row_data.department_id);*/


		$("#finalized_date").val(row_data.finalized_date);
		$scope.formData = {grnNo:row_data.grn_no,status:row_data.status,id:row_data.id,stockInType:row_data.stock_in_type,supplierRefNo:row_data.supplier_doc_no,departmentId : row_data.department_id,supplierId : row_data.supplier_id,supplierCode: row_data.supplier_code,supplierName : row_data.supplier_name,remarks:row_data.remarks,stockInPaymentType:row_data.stockInPaymentType};
		/*		$scope.department_name = depList.name;
		$scope.department_code = depList.code;*/
		$scope.formData.departmentId = strings['isDefDepartment'];
		$scope.department_code = strings['isDefDepartmentcode'];
		$scope.department_name = strings['isDefDepartmentname'];
		if(settings['combinePurchase']==1)
		{
			$scope.setSupplierAddress($scope.formData.supplierId);
		}
		$("#form_div_department_code").find(".acontainer input").val(strings['isDefDepartmentcode']);



		if(row_data.stock_in_type == 2){
			$scope.show_suppler = false;
			$scope.isStoreToStore = false;
			$("#companyId option[value='"+row_data.source_company_id+"']").prop('selected', true);
		}else if(row_data.stock_in_type == 3){
			$scope.show_suppler = false;
			$scope.isStoreToStore = true;
			$("#companyId option[value='0']").prop('selected', true);
		}else{
			$scope.show_suppler = true;
			$scope.isStoreToStore = true;
			$("#companyId option[value='0']").prop('selected', true);
		}
		$("#form_div_supplier_code").find(".acontainer input").val(row_data.supplier_code);
		/*$("#form_div_department_code").find(".acontainer input").val(depList.code);*/
		setItemtableValues(row_data.id);
		$scope.disable_all = true;
		$scope.disable_grn=true;

		$(".acontainer input").attr('disabled', true);
		$("#div_finlize_print").css("display","block");
		$("#div_normal_print").css("display","block");
		if(row_data.status == 0){
			$scope.cur_date_finalize  = $scope.getCurrentDate();
			$("#finalized_by").val(0);

			$("#div_finlize_print button").text("FINALIZE");


			$("#btnDelete,#btnEdit").css("display","block");
		}else if(row_data.status == 1){
			$("#finalized_by").val(row_data.finalized_by);
			$("#finalized_date").val(row_data.finalized_date);
			$("#div_finlize_print button").text("PRINT");
			//$("#btnDelete,#btnEdit").css("display","none");
		}else if(row_data.status == 2){
			$("#div_finlize_print button").text("RE-PRINT");
			//$("#btnDelete,#btnEdit").css("display","none");
		}
		var permissionn =$('#finalizepermission').val();


		if( permissionn == 'false' &&   row_data.status != 0){

			$("#btnEdit").hide();

		}
		$scope.formData.stockreg_id=fun_get_stockRegId(row_data.id);
		//alert($scope.formData.stockreg_id);
	}

	$(document).on('keyup','#form_div_supplier_code input',function(e){
		//debugger
		if( e.which==13){
			$("#supplier_doc_no").focus();
		}
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$scope.formData.supplierId =  "";
				$scope.formData.supplierCode =  "";
				$scope.formData.supplierName = "";
				$scope.supplier_address="";

			});
		}
	});

	$(document).on('keyup','#form_div_department_code input',function(e){
		//debugger
		if( e.which==13){
			$("#form_div_supplier_code input").focus();
		}
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$scope.formData.departmentId ="";
				$scope.department_name ="";
				$scope.department_code ="";
			});
		}
	});

	function setItemtableValues(id){
		//debugger
		$scope.prograssing = true;
		$scope.invoice = {items: []};
		loadItemDetailsTable(id);


		$http({
			url : 'getStockInDtlData',method : "GET",params:{id:id},async:false,
		}).then(function(response) {
			//debugger
			$scope.invoice.items = response.data.stkInDtl;
		
			for(var i = 0;i<response.data.stkInDtl.length;i++){
				$scope.invoice.items[i].po_qty=parseFloat(response.data.stkInDtl[i].po_qty).toFixed(settings['decimalPlace']);
				$scope.invoice.items[i].current_stock=parseFloat(response.data.stkInDtl[i].current_stock).toFixed(settings['decimalPlace']);
				$scope.invoice.items[i].received_qty=(parseFloat(response.data.stkInDtl[i].received_qty).toFixed(settings['decimalPlace']))>0?
						parseFloat(response.data.stkInDtl[i].received_qty).toFixed(settings['decimalPlace']):parseFloat(response.data.stkInDtl[i].po_qty).toFixed(settings['decimalPlace']);
						$scope.invoice.items[i].unit_price=parseFloat(response.data.stkInDtl[i].unit_price).toFixed(2);
						$scope.invoice.items[i].tax_pc=parseFloat(response.data.stkInDtl[i].tax_pc).toFixed(settings['decimalPlace']);
				//debugger
						$scope.formData.stockInPaymentType=response.data.stkInDtl[i].payment_mode;
						$scope.invoice.items[i].pack_qty=parseFloat(response.data.stkInDtl[i].pack_qty).toFixed(settings['decimalPlace']);
					//	$scope.invoice.items[i].base_uom_code=response.data.stkInDtl[i].base_uom_code;
						$scope.invoice.items[i].isSet = false;
						$scope.invoice.items[i].isDeleted = false;						

					//	$scope.baseUomCode[i] = $scope.setuomCode(response.data.stkInDtl[i].uomcode,i);
					//	$scope.invoice.items[i].base_uom_code=$scope.setuomCode(response.data.stkInDtl[i].uomcode,i);
						$scope.setuomCode(response.data.stkInDtl[i].uomcode,i);	
						$scope.invoice.items[i].sino = i+1;	
						

			}
			$scope.prograssing = false;
		}, function(response) { 
			$scope.prograssing = false;
		});


	}
	function fun_get_dep_name(id){
//debugger
		var depList =[];
		for(var i=0;i < department_data.length;i++){
			if(department_data[i].id == id){
				depList = department_data[i];
			}
		}

		return depList;
	}

	function fun_set_def_dep_supplier(){
		//debugger

		//	var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
		$scope.formData.departmentId = strings['isDefDepartment'];
		$scope.department_code = strings['isDefDepartmentcode'];
		$scope.department_name = strings['isDefDepartmentname'];
		$("#form_div_department_code").find(".acontainer input").val(strings['isDefDepartmentcode']);
		if(stockInStrings['isDefSupplier'] == 1){
			$scope.formData.supplierId = strings['isDefSupplierid'];
			$scope.formData.supplierCode =strings['isDefSuppliercode'];
			$scope.formData.supplierName = strings['isDefSuppliername'];
			$("#form_div_supplier_code").find(".acontainer input").val(stockInStrings['suppliercode']);
		}else if(stockInStrings['isDefSupplier'] == 0){
			$scope.formData.supplierId = "";
			$scope.formData.supplierCode ="";
			$scope.formData.supplierName = "";
			$("#form_div_supplier_code").find(".acontainer input").val("");
		}

		if(settings['combinePurchase']==1)
		{
			$scope.setSupplierAddress($scope.formData.supplierId);
		}

	}



	function setBatchData()
	{
//debugger
		$scope.formData.batch=[];
		for(var i = 0;i<$scope.invoice.items.length;i++){
			$scope.formData.batch.push({
				stock_item_batch_id:$scope.invoice.items[i].stock_item_batch_id,
				qty: $scope.invoice.items[i].received_qty,
				cost_price:$scope.invoice.items[i].unit_price,
				stock_item_id:$scope.invoice.items[i].stock_item_id,
			});

		}
	}




	$rootScope.$on("fun_delete_current_data",function(event){	
		//debugger		//Delete Function
		$scope.showConfirm(FORM_MESSAGES.DELETE_WRNG,"DELETE",event);
	});

	$scope.deleteData = function(event){
		//debugger
		setBatchData();
		var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$http({
			url : 'delete',
			method : "POST",async:false,
			params : {id:$scope.formData.id,GRN_No:$("#grn_code").val(),stockreg_id:$scope.formData.stockreg_id,batch:$scope.formData.batch},
		}).then(function(response) {
			$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
			$scope.disable_all = true;
			$scope.disable_grn=true;
			$scope.disable_code = true;
			$('#show_form').val(0);
			vm.dtInstance.reloadData(null, true);
			$scope.show_table=true;
			$("#advsearchbox").show();
			$scope.show_form=false;
			$scope.show_suppler = false;
			manageButtons("add");

		}, function(response) { // optional
			$scope.alertBox(FORM_MESSAGES.DELETE_ERR);
		});

	}

	function fun_get_stockRegId(refno){
//debugger
		var stockRegid = "";
		$http({
			url : 'getstockredid',
			method : "GET",
			params:{id:refno},
		}).then(function(response) {

			if(response.data.stockregid.length!=0){
				$scope.formData.stockreg_id=response.data.stockregid[0].id;


				stockRegid = response.data.stockregid[0].id;
			}
		}, function(response) { // optional 

		});


		return stockRegid;
	}

	function view_mode_aftr_edit(){
		//debugger
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;		
		$scope.disable_grn=true;
		$scope.disable_code = true;
		$(".acontainer input").attr('disabled', true);
	}

	function setFormDatas(){
		//debugger
		var total=0.0;var tax_total=0.0;var grand_total=0.0;
		total=$scope.total();
		tax_total=$scope.tax_total();
		grand_total=$scope.gendTotal();
		$scope.formData.finalizedDate = $("#finalized_date").val();
		//$scope.formData.grnNo = $scope.GRNNO;
		$scope.formData.receivedDate = $("#received_date").val();
		$scope.formData.receivedDate = getMysqlFormat($scope.formData.receivedDate);
		for(var i = 0;i<$scope.invoice.items.length;i++){
			$scope.invoice.items[i].amount = $scope.invoice.items[i].received_qty * $scope.invoice.items[i].unit_price;
			$scope.invoice.items[i].tax_amount = $scope.invoice.items[i].unit_price * (parseFloat($scope.invoice.items[i].tax_pc)/100) * $scope.invoice.items[i].received_qty;
		}
		if($scope.formData.status==undefined){
			$scope.formData.status = 0;}
		if(settings['combinePurchase']==1)
		{
			$scope.formData.status = 0;
		}

		if($scope.formData.stockInType == 2){
			$scope.formData.companyId = $("#companyId").val();
			$scope.formData.companyCode = $("#companyId option[value='"+$scope.formData.companyId+"']").attr("id");
			$scope.formData.companyName = $("#companyId option[value='"+$scope.formData.companyId+"']").text();
		}else{
			$scope.formData.companyId = "";
			$scope.formData.companyCode = "";
			$scope.formData.companyName = "";
		}
		$scope.formData.receivedBy=strings['userID'];
		//$scope.formData.stockInDetails = [];
		$scope.stockDtlList = [];
		for(var i = 0;i<$scope.invoice.items.length;i++){
			//$scope.formData.stockInDetails.push($scope.invoice.items[i]);
			$scope.stockDtlList.push($scope.invoice.items[i]);
		}
	}



	$scope.saveData=function(event){  
//debugger
		if (form_validation($scope.formData)) {

			$("#btnSave").prop('disabled', true);

			setFormDatas();

			$scope.formData.stockDetailLists=JSON.stringify($scope.stockDtlList);
			$http({
				url : 'saveStockItem',
				method : "POST",async:false,
				data : $scope.formData
			}).then(function(response) {
				$("#btnSave").prop('disabled', false);
				if(response.data==1){
					if($scope.formData.id !=undefined){
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
						$("#div_finlize_print").css("display","block");
						$("#div_normal_print").css("display","block");
						view_mode_aftr_edit();
					}else{
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
						fun_get_grnno();
						$("#form_div_supplier_code").find(".acontainer input").val("");
						$("#form_div_department_code").find(".acontainer input").val("");
						$("#table_validation_alert").removeClass("in");
						$scope.formData = {
								stockInType:stockInStrings['stockintype'],
						};
						if($scope.formData.stockInType == 2 || $scope.formData.stockInType == 3){
							$scope.show_suppler = false;
						}else{
							$scope.show_suppler = true;
						}
						fun_set_def_dep_supplier();
						$scope.invoice = {items: []};
						$scope.invoice.items.push({sino:1,id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",po_id : "",po_no : "",po_qty: 0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isDeleted:false,compound_unit:0.0,base_uom_code:""});
						$scope.isStoreToStore = true;
						$scope.ispoTableHide = true;
					}

					vm.dtInstance.reloadData(null, true);
				}else{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Save failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
					$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
				}


				if($scope.status!=RECORD_STATUS.PENDING && $scope.status !="" && $scope.status !=undefined  && $scope.status !=0 ){


					$scope.finalize();}

				// reloadData();
			}, function(response) { // optional
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Save failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
				$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
			});
		}

	}

	$rootScope.$on('fun_save_data',function(event){	
		//debugger
		$scope.saveData(event);
	});

	$rootScope.$on("fun_discard_form",function(event){			
		//debugger	//Discard function
		$scope.showConfirm(FORM_MESSAGES.DISCARD_WRNG,"DISCARD",event);
	});

	$scope.discardData = function(){
		//debugger//debugger
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		if($scope.formData.id == undefined){
			$scope.formData = {
					stockInType:stockInStrings['stockintype'],
			};
			if($scope.formData.stockInType == 3 || $scope.formData.stockInType ==2){
				$scope.show_suppler = false;
			}else{
				$scope.show_suppler = true;
				$scope.formData.supplierId = "";
				$scope.formData.supplierCode ="";
				$scope.formData.supplierName = "";
				$("#form_div_supplier_code").find(".acontainer input").val("");
			}
			fun_get_grnno();
			$scope.hide_code_existing_er = true;
			$("#form_div_supplier_code").find(".acontainer input").val("");
			$("#form_div_department_code").find(".acontainer input").val("");
			$("#table_validation_alert").removeClass("in");
			$scope.invoice = {items: []};
			$scope.invoice.items.push({sino:1,id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",po_id : "",po_no : "",po_qty: 0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isDeleted:false,base_uom_code:"",compound_unit:0.0});
			$scope.isStoreToStore = true;
			fun_set_def_dep_supplier();
			var date =get_date_format();
			$scope.CurrentDate = date.mindate;	
		}else{
			var dataIndex = vm.dtInstance.DataTable.rows();
			var row_data = vm.dtInstance.DataTable.rows(dataIndex[0][cur_row_index]).data();
			edit(row_data[0],cur_row_index);
		}
		clearform();
		function_clear_select();
		$scope.ispoTableHide=true;
		$scope.poHder = {
				items: []
		};
	}

	$rootScope.$on("fun_enable_inputs",function(){
		//debugger
		$('#show_form').val(1);
		$scope.disable_all = false;
		$(".acontainer input").attr('disabled', false);
		$("#form_div_department_code").find(".acontainer input").attr('disabled', true);

		$("#div_finlize_print").css("display","none");
		$("#div_normal_print").css("display","none");
		$scope.disable_grn=true;
	});

	$rootScope.$on("fun_enable_inputs_code",function(){
		//debugger
		$scope.disable_code = false;
	});

	$rootScope.$on("fun_clear_form",function(){
		//debugger
	//	$scope.editData=false;
		$("#grn_code").css("display","none");
		$("#grnCode_label").css("display","none");
		$scope.hide_code_existing_er = true;
		$scope.formData={};
		var date =get_date_format();
		$scope.CurrentDate = date.mindate;		
//		fun_get_grnno();
		$(".acontainer input").val("");
		$scope.invoice = {items: []};
		$scope.invoice.items.push({sino:1,id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",po_id : "",po_no : "",po_qty: 0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isDeleted:false,compound_unit:0.0,base_uom_code:""
		});
		$('#stockin_type option:eq(0)').prop('selected', true);
		function_clear_select();
		$scope.formData.stockInType=stockInStrings['stockintype'];

		$scope.stockInTypeOldVal=$scope.formData.stockInType;
		var option= parseInt(stockInStrings['stockintype'])+1;
		$('#stockin_type option:eq('+option+')').prop('selected', true);
		$('#selectall').prop('checked', false);
		if($scope.formData.stockInType == 3 || $scope.formData.stockInType ==2){
			$scope.show_suppler = false;
			$scope.ispoTableHide=true;
		}else{
			$scope.show_suppler = true;
			$scope.formData.supplierId = "";
			$scope.formData.supplierCode ="";
			$scope.formData.supplierName = "";


			$("#form_div_supplier_code").find(".acontainer input").val("");
			$scope.ispoTableHide=false;

		}
		fun_set_def_dep_supplier();
		$scope.isStoreToStore = true;
		$("#form_div_source_company").removeClass("has-error");
		$("#form_div_source_company_error").hide();
		$("#status_div_id").css("display","none");
		$(".acontainer input").attr('disabled', false);
		$("#form_div_department_code").find(".acontainer input").attr('disabled', true);

	//	fun_get_grnno();
		$scope.disable_grn = true;
	});


	//Manupulate Formdata when Edit mode - Prev-Next feature add
	$rootScope.$on("fun_next_rowData",function(e,id){
//debugger
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
		//debugger
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var dataIndex = vm.dtInstance.DataTable.rows();
		//var dataIndex = $scope.filterRowData;
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
	function form_validation(data){
		//debugger
		var flg = true;
		if(data.id == undefined || data.id == ""){
			if(!$scope.hide_code_existing_er){
				flg=false;
				$("#grn_code").select();

			}
		}


		if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id=="" && $scope.invoice.items.length!=1 ){
			$scope.invoice.items.splice($scope.invoice.items.length-1, 1);
		}
		if($scope.formData.stockInType == 2){
			if($("#companyId").val() == 0){
				$("#form_div_source_company").addClass("has-error");
				$("#form_div_source_company_error").show();
				flg = false;
				$("#companyId").focus();
				return false;
			}else{
				$("#form_div_source_company").removeClass("has-error");
				$("#form_div_source_company_error").hide();
			}
		}else{
			$("#form_div_source_company").removeClass("has-error");
			$("#form_div_source_company_error").hide();
		}
		if($scope.formData.stockInType == undefined){
			$("#form_div_stockin_type").addClass("has-error");
			$("#form_div_stockin_type_error").show();
			flg = false;
		}else{
			$("#form_div_stockin_type").removeClass("has-error");
			$("#form_div_stockin_type_error").hide();
		}
		if(validation() == false){
			flg = false;
		}if(($scope.formData.departmentId == "" && $scope.department_code == "" && $scope.department_code == "") || ($scope.formData.supplierId == undefined && $scope.formData.supplierCode == undefined && $scope.formData.supplierName == undefined )){
			$("#form_div_department_code").addClass("has-error");
			$("#form_div_department_code_error").show();
			flg = false;
			$("#form_div_department_code").find(".acontainer input").focus();
			return false;
		}else{
			$("#form_div_department_code").removeClass("has-error");
			$("#form_div_department_code_error").hide();
		}

		if(($scope.formData.supplierId == "" && $scope.formData.supplierCode == "" && $scope.formData.supplierName == "") || ($scope.formData.supplierId == undefined && $scope.formData.supplierCode == undefined && $scope.formData.supplierName == undefined )){
			$("#form_div_supplier_code").addClass("has-error");
			$("#form_div_supplier_code_error").show();
			flg = false;
			$("#form_div_supplier_code").find(".acontainer input").focus();
			return false;
		}else{
			$("#form_div_stockin_type").removeClass("has-error");
			$("#form_div_supplier_code_error").hide();
		}

		if(($scope.formData.stockInPaymentType == "") || ($scope.formData.stockInPaymentType == undefined )){
			$("#form_div_supplier_payment").addClass("has-error");
			$("#form_div_payment_error").show();
			flg = false;
			$("#form_div_payment_error").find(".acontainer input").focus();
			return false;
		}else{
			$("#form_div_supplier_payment").removeClass("has-error");
			$("#form_div_payment_error").hide();
		}

		if(($scope.formData.supplierRefNo == "") || ($scope.formData.supplierRefNo == undefined )){
			$("#form_div_supplier_ref").addClass("has-error");
			$("#form_div_supplier_ref").show();
			flg = false;
			$("#form_div_supplier_ref").find(".acontainer input").focus();
			return false;
		}else{
			$("#form_div_supplier_ref").removeClass("has-error");
			$("#form_div_supplier_ref").hide();
		}

       
		if($scope.invoice.items.length > 0){
			$scope.item_table_empty=[];
			$scope.item_details_table_empty=[];
			var countOfDeletedRows=0;
			for(var i = 0;i<$scope.invoice.items.length;i++){
				if(!$scope.invoice.items[i].isDeleted){
					$.each($scope.invoice.items[i],function(key,value){
						if($scope.invoice.items[i].stock_item_id != "" && $scope.invoice.items[i].stock_item_code != ""){
							if(settings['combinePurchase'] == 0)
							{
								if($scope.invoice.items[i].received_qty == "" || $scope.invoice.items[i].received_qty =="0" ||$scope.invoice.items[i].received_qty =="0." || $scope.invoice.items[i].received_qty ==parseFloat(0).toFixed(settings['decimalPlace']))
								{
									$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
									flg = false;
									$scope.item_details_table_empty.push($scope.invoice.items[i]);
									$("#items_table tr:nth-child("+(i+1)+") td:nth-child("+(7)+")").find("#received_qty").select();
								}
							}

							if(settings['combinePurchase'] == 1)
							{

								if( $scope.formData.status ==1)
								{

									if($scope.invoice.items[i].received_qty == "" || $scope.invoice.items[i].received_qty =="0" ||$scope.invoice.items[i].received_qty =="0." || $scope.invoice.items[i].received_qty ==parseFloat(0).toFixed(settings['decimalPlace']))
									{
										$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
										flg = false;
										$scope.item_details_table_empty.push($scope.invoice.items[i]);
										$("#items_table tr:nth-child("+(i+1)+") td:nth-child("+(7)+")").find("#received_qty").select();

									}

								}

									else
								{
									if($scope.invoice.items[i].base_uom_code == "" || $scope.invoice.items[i].base_uom_code == undefined)
									{
										$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter PUnit");
										flg = false;
										$scope.item_details_table_empty.push($scope.invoice.items[i]);
										$("#items_table tr:nth-child("+(i+1)+") td:nth-child("+(3)+")").find("#base_uom_code").select();

									}else if($scope.invoice.items[i].received_qty == "" || $scope.invoice.items[i].received_qty =="0" ||$scope.invoice.items[i].received_qty =="0." || $scope.invoice.items[i].received_qty ==parseFloat(0).toFixed(settings['decimalPlace'])){
										
										$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Recieved Qty");
										flg = false;
										$scope.item_details_table_empty.push($scope.invoice.items[i]);
										$("#items_table tr:nth-child("+(i+1)+") td:nth-child("+(5)+")").find("#received_qty").select();
									}

								}
							}	

						}else{
							$scope.item_table_empty.push($scope.invoice.items[i]);
						}
					});
				}else{
					countOfDeletedRows = countOfDeletedRows+1;
				}
			}
			if(countOfDeletedRows == $scope.invoice.items.length){
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.TABLE_ERR);
				flg = false;
			}
			if($scope.item_table_empty.length > 0){
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$(".acontainer").find("input").focus();
				flg = false;
			}else{
				if($scope.item_details_table_empty.length > 0){
					if( settings['combinePurchase'] == 0)
					{
						$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
						flg = false;
					}
				}else{
					$("#table_validation_alert").removeClass("in");
				}
			}
		}else if($scope.invoice.items.length == 0){
			$rootScope.$broadcast('on_AlertMessage_ERR', ITEM_TABLE_MESSAGES.TABLE_ERR);
			flg = false;
		}
		if(flg==false)
		{
			focus();
		}
		return flg;
	}

	function function_clear_select(){
		//debugger
		$("#form_div_stockin_type").removeClass("has-error");
		$("#form_div_stockin_type_error").hide();
		$("#table_validation_alert").removeClass("in");

	}
	$scope.invoice = {items: []};
	$scope.invoice.items.push({sino:1,	id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",po_id : "",po_no : "",po_qty: 0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isDeleted:false,compound_unit:0.0,base_uom_code:""});
	$scope.addItem = function(index) {
		if($scope.disable_all == false){
			if(index<$scope.invoice.items.length-1){
				$("#items_table tr:nth-child("+(index+2)+") td:nth-child("+(5)+")").find(".acontainer input").focus();
			}else{
				if($scope.invoice.items.length > 0){
					if(!$scope.invoice.items[$scope.invoice.items.length-1].isDeleted){
						if($scope.invoice.items.length != 0){
							if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id != "" && $scope.invoice.items[$scope.invoice.items.length-1].stock_item_code != "")
							{
								if(settings['combinePurchase'] == 0)
								{	
									if($scope.invoice.items[$scope.invoice.items.length-1].received_qty == "" || $scope.invoice.items[$scope.invoice.items.length-1].received_qty =="0"|| $scope.invoice.items[$scope.invoice.items.length-1].received_qty =="0."){
										$("#items_table tr:nth-child("+($scope.invoice.items.length)+") td:nth-child("+(7)+")").find("#received_qty").select();
										$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
									}

									else{
										$scope.invoice.items.push({sino:"",id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",po_id : "",po_no : "",po_qty: 0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isDeleted:false,uomcode:"",current_stock:0,base_uom_code:""});
									}
								}
								if(settings['combinePurchase'] == 1 )
								{	
									if($scope.invoice.items[$scope.invoice.items.length-1].base_uom_code == "" || $scope.invoice.items[$scope.invoice.items.length-1].base_uom_code ==undefined){
										$("#items_table tr:nth-child("+($scope.invoice.items.length)+") td:nth-child("+(3)+")").find("#base_uom_code").select();
										$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Punit");
									}
										else if($scope.invoice.items[$scope.invoice.items.length-1].pack_qty == "" || parseFloat($scope.invoice.items[$scope.invoice.items.length-1].pack_qty)<=0){
										$("#items_table tr:nth-child("+($scope.invoice.items.length)+") td:nth-child("+(8)+")").find("#pack_qty").select();
										$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Pack qty");
									}

									 									else{
										 $scope.invoice.items.push({sino:"",id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",po_id : "",po_no : "",po_qty: 0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isDeleted:false,uomcode:"",current_stock:0,base_uom_code:""});
										 //$scope.formData={base_uom_code:"",compound_unit:""};
									 }
								}


							}
							else{
								if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id == "" && $scope.invoice.items[$scope.invoice.items.length-1].stock_item_code == ""){
									$("#items_table tr:nth-child("+($scope.invoice.items.length)+") td:nth-child("+(5)+")").find(".acontainer input").focus();
									$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
								}
							}

						}else if($scope.invoice.items.length == 0){
							$scope.invoice.items.push({sino:"",id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",po_id : "",po_no : "",po_qty: 0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isDeleted:false,uomcode:"",current_stock:0,base_uom_code:"",compound_unit:0.0});
						}
					}else{
						$scope.invoice.items.push({sino:"",id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",po_id : "",po_no : "",po_qty: 0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isDeleted:false,uomcode:"",current_stock:0,base_uom_code:"",compound_unit:0.0});
					}
					$scope.setSiNo();
				}else{
					$scope.invoice.items.push({sino:1,id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",po_id : "",po_no : "",po_qty: 0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isDeleted:false,uomcode:"",current_stock:0,base_uom_code:"",compound_unit:0.0});
				}
			}}
	},


	$scope.removeRowItem=function(index){
//debugger
		var length=0;
		for(var i=0;i<$scope.invoice.items.length;i++){
			if($scope.invoice.items[i].isDeleted==false){
				length++;}

		}
		if(length!=1){
			if($scope.invoice.items[index].hasOwnProperty('isSet')){
				$scope.invoice.items[index].isDeleted = true;
			}else{
				$scope.invoice.items.splice(index, 1);
				$("#items_table tbody tr:nth-child("+$scope.invoice.items.length+") td").find("#po_qty").focus();
			}
			$scope.setSiNo();
		}else{
			$rootScope.$broadcast('on_AlertMessage_ERR',"Atleast one item is required.");
		}

	}


	$scope.removeItem = function(index) {
//debugger
		if($scope.disable_all == false){
			$scope.showConfirm(FORM_MESSAGES.ROW_DELETE_WRNG,"rowDelete",index);	
		}
	},

	$scope.setSiNo = function(){
		
		var siNo=0;
		for(var i = 0;i < $scope.invoice.items.length;i++){
			if(!$scope.invoice.items[i].isDeleted){
				siNo++;
				$scope.invoice.items[i].sino = siNo;
			}
		}
	}

	$scope.amount = function(item){
		var amount = 0.00;
		amount = item.received_qty * item.unit_price;
		//return parseFloat(amount).toFixed(settings['decimalPlace']);
		return parseFloat(amount).toFixed(2)
	}

	$scope.taxAmt = function(item){
		var taxAmount = 0.00;
		taxAmount = item.unit_price * (item.tax_pc/100) * item.received_qty;
		//return parseFloat(taxAmount).toFixed(settings['decimalPlace']);
		return parseFloat(taxAmount).toFixed(2);
	}

	$scope.taxAmtTtl = function(item){
		var taxAmtTotal = 0.00;
		//taxAmtTotal = (item.unit_price * (item.tax_pc/100) * item.received_qty) + (item.received_qty * item.unit_price);

		taxAmtTotal = item.received_qty * item.unit_price;

		//return parseFloat(taxAmtTotal).toFixed(settings['decimalPlace']);
		return parseFloat(taxAmtTotal).toFixed(2);
	}

	$scope.total = function(item) {
		var net_total = 0.00;
		var index=0;
		angular.forEach($scope.invoice.items, function(item) {
			if(item.isDeleted==false){
				net_total += item.received_qty * item.unit_price;
			}  
		   
		})
		$scope.formData.netTotal=net_total;
			return parseFloat(net_total).toFixed(2);
	}

	$scope.tax_total = function(item) {
		var tax_total = 0.00;
		angular.forEach($scope.invoice.items, function(item) {
			if(item.isDeleted==false){
				tax_total += item.unit_price * (item.tax_pc/100) * item.received_qty;
			}})
			$scope.formData.taxTotal=tax_total;
			return parseFloat(tax_total).toFixed(2);
	}
	$scope.changeUnitPrice=function(item)
	{

		if(parseFloat(item.pack_qty)>0)
		{
			item.unit_price=item.unit_price1*item.pack_qty;
		}
		else
		{
			item.pack_qty=1;
			item.unit_price=item.unit_price1*item.pack_qty;
		}
	}


	$scope.gendTotal = function(item){
		var grandTotal = 0.00;
		angular.forEach($scope.invoice.items, function(item) {
			if(item.isDeleted==false){
				grandTotal += (item.received_qty * item.unit_price) + (item.unit_price * (item.tax_pc/100) * item.received_qty);
			}})

			$scope.formData.grandTotal=grandTotal;
			return parseFloat(grandTotal).toFixed(2);
	}


	/*Manage po_hdr table*/
	$scope.poHder = {items: []};

	$scope.toggleAll=function (selectAll) {

	};
	$scope.toggleOne=function(selectedItems,poNo) {
//debugger
		for(var i=0;i<$scope.invoice.items.length;i++){
			if($scope.invoice.items[i].stock_item_id == "" && $scope.invoice.items[i].received_qty == 0  && $scope.invoice.items[i].received_qty == "" && $scope.invoice.items[i].po_id == ""){
				$scope.invoice.items.splice(i, 1);
			}
		}

		for (var id in selectedItems) {
			if (selectedItems.hasOwnProperty(id)) {
				if(selectedItems[id]){
					$http({url : 'getPoDtlList',method : "GET",params : {poId:id},async:false,
					}).then(function(response) {
						for(var i=0;i<response.data.poDtl.length;i++){
							$scope.invoice.items.push({sino:"",id: "",poDtl_id :response.data.poDtl[i].id,po_id : id,po_no : poNo,stock_item_id :response.data.poDtl[i].stock_item_id,stock_item_code:response.data.poDtl[i].stock_item_code,stock_item_name: response.data.poDtl[i].stock_item_name,stock_item_batch_id:"",po_qty: response.data.poDtl[i].qty,received_qty:response.data.poDtl[i].qty,unit_price: response.data.poDtl[i].unit_price,tax_id:response.data.poDtl[i].tax_id,tax_pc:response.data.poDtl[i].tax_pc,isSet:false,isDeleted:false});
							$scope.setSiNo();
						}
					}, function(response) { // optional

					});
				}else{

					$scope.newInvoice = {items :[]}
					for(var i=0;i<$scope.invoice.items.length;i++){
						if(id != $scope.invoice.items[i].po_id){
							$scope.newInvoice.items.push($scope.invoice.items[i]);
						}
					}
					if($scope.newInvoice.items.length == 0){
						$scope.newInvoice.items.push({sino:"",id: "",stock_item_id :"",stock_item_code:"",stockRegDtl_id: "",stock_item_batch_id:"",poDtl_id:"",po_id : "",po_no : "",po_qty: 0,received_qty:0,pack_qty:"",unit_price: 0,tax_id:0,tax_pc:0,isSet:false,isDeleted:false });
						$scope.setSiNo();
					}
					$scope.invoice = $scope.newInvoice;
				}
			}
		}
	};

	function setPoDetails(suppplierId){
		//debugger
		$scope.poHder = {items: []};

		$http({url : 'getPOlist',method : "GET",params : {supllierId:suppplierId},async:false,
		}).then(function(response) {
			for(var i=0;i<response.data.poList.length;i++){
				$scope.poHder.items.push({po_id: response.data.poList[i].id,po_no : response.data.poList[i].po_number,});
			}
		}, function(response) { // optional

		});
	}

	//retrieve punit  @gana 22012020
	$scope.setuomCode=function(uomCode,index){
		//debugger
		$http({

			url:'../stockin/getPunit',
			async:false,
			method:'POST',
			params:{uomCode:uomCode},
		}).then(function(response){
			$scope.baseUomCode[index]=response.data.baseUom;
			$scope.BindUomCode[index]=response.data.baseUom;
		debugger

		});
	}

	//change punit and calc @gana 22012020
	$scope.selectPuomCode=function(baseUomCode,recQty,index){
		//debugger
		$scope.invoice.items.compound_unit=[];
		$scope.invoice.items.pack_qty=[];
		for(i=0;i<$scope.baseUomCode[index].length;i++){

			if($scope.baseUomCode[index][i].base_uom_code==baseUomCode){

				$scope.invoice.items[index].compound_unit=parseFloat($scope.baseUomCode[index][i].compound_unit).toFixed(settings['decimalPlace']);
				$scope.invoice.items[index].base_uom_code=baseUomCode;
			}
		}
		$scope.invoice.items[index].pack_qty=parseFloat($scope.invoice.items[index].compound_unit*recQty).toFixed(settings['decimalPlace']);	

	}

	$scope.setSupplierAddress=function(suppplierId)
	{
		//debugger
		$scope.supplier_address="";
		$http({url : 'getSupplierAddress',method : "GET",params : {supllierId:suppplierId},async:false,
		}).then(function(response) {

			$scope.supplier_address=response.data;

		}, function(response) { // optional

		});


	}


	var supplieryData = $("#supplier_code").tautocomplete({
		columns: ['id' , 'code', 'name'],
		hide: [false,true,true],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay : 10,
		onchange: function () {
			var selected_supplier_data =  supplieryData.all();
			$scope.$apply(function(){
				$scope.formData.supplierId =  selected_supplier_data.id;
				$scope.formData.supplierCode =  selected_supplier_data.code;
				$scope.formData.supplierName =  selected_supplier_data.name;

				setPoDetails($scope.formData.supplierId);
				if(settings['combinePurchase']==1)
				{

					$scope.setSupplierAddress($scope.formData.supplierId);
				}
			});
		},
		data: function () {
			var data = supplier_data;
			var filterData = [];
			var searchData = eval("/^" + supplieryData.searchdata() + "/gi");
			$.each(data, function (i, v) {
				if (v.name.search(new RegExp(searchData)) != -1) {
					filterData.push(v);
				}
			});

			return filterData;
		}
	});

	var departmentData = $("#department_code").tautocomplete({
		columns: ['id','code' , 'name'],
		hide: [false,true,true],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay : 10,
		onchange: function () {
			var selected_department_data =  departmentData.all();
			$scope.$apply(function(){
				$scope.formData.departmentId =  selected_department_data.id;
				$scope.department_code =  selected_department_data.code;
				$scope.department_name =  selected_department_data.name;
			});

		},
		data: function () {
			var data = department_data;
			var filterData = [];
			var searchData = eval("/^" + departmentData.searchdata() + "/gi");
			$.each(data, function (i, v) {
				if ( v.name.search(new RegExp(searchData)) != -1) {
					filterData.push(v);
				}
			});

			return filterData;
		},
	});

	$scope.getItemmasterBatchId = function(itemid){
//debugger
		$scope.itemBatchId = "";

		$http({
			url : 'getItemMasterBatchId',
			method : "GET",
			params:{id:itemid},
		}).then(function(response) {

			$scope.itemsBatchData = response.data.itemsBatchData;
			if($scope.itemsBatchData.length!=0)
			{
				$scope.itemBatchId = $scope.itemsBatchData[0].id;
			}

		}, function(response) { // optional 

		});




		/*    	 for(var i=0;i<$scope.itemsBatchData.length;i++){
    		 if($scope.itemsBatchData[i].stock_item_id == itemid && $scope.formData.source_department_id==$scope.itemsBatchData[i].department_id){
    			 $scope.itemBatchId = $scope.itemsBatchData[i].id;
    		 }
    	 }*/

		return $scope.itemBatchId;


	}

	$scope.currentStockTotal = function(){
		//debugger
		var stockTotal = 0.00;
		angular.forEach($scope.itemDataOnDepartment, function(item) {

			stockTotal += parseFloat(item.current_stock);
		})

		// return parseFloat(grandTotal).toFixed(settings['decimalPlace']);
		return parseFloat(stockTotal).toFixed(settings['decimalPlace']);
	}

	$scope.disable_search_text=function(elemenvalue){
		//debugger
		if($scope.disable_all==true){
			$(elemenvalue).attr("disabled", true);
		}
	}
	$scope.alert_for_codeexisting = function(flg){
		//debugger
		if(flg){
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.CODE_EXISIT);
		}
	}


	$scope.getCostPrice = function(data,index,id){
		//debugger
		var url="getLastCostPrice";
		/*var costPrice =0.00;
			$.each(data, function (i, v) {
				if(v.id == id){


						url = "getLastCostPrice";



				}
			});*/

		$http({url :url,method : "GET",params:{itemId:id},async:false,
		}).then(function(response) {

			$scope.invoice.items[index].unit_price = parseFloat(response.data).toFixed(2);
		}, function(response) { // optional

		});
	}


	$scope.clear_stock_details_editmode =  function(event){
		//debugger
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].stock_item_id = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].stock_item_code = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].stock_item_name = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].stock_item_batch_id ="";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].uomcode = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].current_stock = 0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].tax_pc = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].tax_id = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].unit_price = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].unit_price1 = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].received_qty = 0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].pack_qty = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].po_qty = 0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].pack_contains = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].base_uom_code = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].compound_unit = 0.0;
	}

	// Advanced search
	$timeout(function () {$("#DataTables_Table_0_filter").hide(); }, 1); 

	$rootScope.$on("advSearch",function(event){
		//debugger
		$("#dropdownnew").toggle();
		DataObj.adnlFilters=[{}];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search('').draw();	
		$scope.orderstatus=$('#ordertype').val();
		if($scope.orderstatus != "")
		{
			var orderstat=$('#ordertype').find(":selected").text();
		}
		$scope.date=$('#orderDate').val();
		if($scope.date !=null && $scope.date !=undefined && $scope.date !="")
		{
			$scope.date=getMysqlFormat($scope.date);
		}
		$scope.reqNo=$('#reqNo').val();
		$scope.supplierNme=$('#supplierNme').val();	
		$scope.stocktype=$('#stocktype').val();

		$scope.searchTxtItms={1:orderstat,2:$scope.date,3:$scope.reqNo,4:$scope.supplierNme,5:$scope.stocktype};

		var close_option=[];
		var counter=0;
		for (var key in $scope.searchTxtItms) {

			if ($scope.searchTxtItms.hasOwnProperty(key)) {
				// console.log(key + " -> " + $scope.searchTxtItms[key]);
				if($scope.searchTxtItms[key] !=null && $scope.searchTxtItms[key] !=undefined && $scope.searchTxtItms[key] !="")
				{
					/* var string = '<div style="display:inline ;padding:4px;background-color:#3c8dbc" >'+$scope.searchTxtItms[key]+'<span class="close-thik" ng-click="deleteOptn()"></span></div>';
	    			   var linkFn = $compile(string);
	    			  // var element = linkFn(scope);
	    			   $('#SearchText').append(linkFn);*/
					angular.element(document.getElementById('SearchText')).append($compile("<div id="+key+"  class='advseacrh '  contenteditable='false'>"+$scope.searchTxtItms[key]+"<span class='close-thik' contenteditable='false'  ng-click='deleteOptn("+key+"); '></span></div>")($scope))
					$scope.deleteOptn = function (key) {
						// alert(key);
						delete $scope.searchTxtItms[key];	
						$('#'+key).remove();
						switch(key)
						{
						case 1:
							$scope.orderstatus="";
							$('#ordertype').val("");
							break;
						case 2:
							$scope.date="";
							$('#orderDate').val("");
							break;
						case 3:
							$scope.reqNo="";
							$('#reqNo').val("");
							break;
						case 4:
							$scope.supplierNme="";
							$('#supplierNme').val("");	
						case 5:
							$scope.stocktype="";
							$('#stocktype').val("");
						}
						DataObj.adnlFilters=[{col:2,filters:$scope.date},{col:1,filters:$scope.reqNo},{col:3,filters:$scope.supplierNme},{col:5,filters:$scope.stocktype},{col:6,filters:$scope.orderstatus}];
						vm.dtInstance.reloadData(); 
					};

					counter++;
					//
					//	Object.keys(object).find(key => object[key] === value)

				}
			}
		}



		DataObj.adnlFilters=[{col:2,filters:$scope.date},{col:1,filters:$scope.reqNo},{col:3,filters:$scope.supplierNme},{col:5,filters:$scope.stocktype},{col:6,filters:$scope.orderstatus}];
		vm.dtInstance.reloadData();
		$scope.searchTxtItms={};

	});
	/*$scope.deleteOptn=function(choice)
			{
				alert(choice);
				//$(this).parent().remove();
			}*/
	$rootScope.$on("Search",function(event){
		//debugger
		DataObj.adnlFilters=[{}];
		$scope.searchTxtItms={};
		//	vm.dtInstance.reloadData();
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	

	});


	$("#clear").click(function(){
		DataObj.adnlFilters=[{}];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	
		$scope.searchTxtItms={};
		$("#ordertype").val("");
	});

	$("#clearFeilds").click(function(){

		$("#dropdownnew").toggle();
		$('#reqNo').val("");
		$('#supplierNme').val("");
		$('#stocktype').val("");
		$('#orderDate').val("");
		$('#ordertype').val("");


	});

	$("#closebtnew").click(function(){
		$("#dropdownnew").toggle();

	});


	$scope.setFormData = function(){

		$http({
			url:'../StockSummary/formJsonData',
			async:false,
			method:'GET',

		}).then(function(response){
			$scope.departments = response.data.departmentData; 
		});
		$scope.formData.department_id = strings['isDefDepartment'];
		$scope.formData.end_date = dateForm(moment(new Date()).add(1,'days'));
		$scope.formData.start_date = dateForm(new Date());		
	}

	

}

mrpApp.directive('autocompeteText', ['$timeout',function($timeout) {
	//debugger
	return {
		controller: function ($scope,$http) {
			$scope.currentIndex = 0;
			$("#items_table tbody tr td").keyup('input',function(e){
				$scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;

				if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){
					if(e.currentTarget.cellIndex == 3){
						$scope.$apply(function(){
							$scope.clear_stock_details_editmode(e);
							$scope.alert_for_codeexisting(false);
						});
					}
				}else if(e.which == 13 ){
					if(e.currentTarget.cellIndex == 3){
						if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id!=""){
							{ $("#items_table tbody tr:nth-child("+e.currentTarget.parentElement.rowIndex+") td").find("#po_qty").select();}
						}}
				}else if(e.which == 9 ){
					if(e.currentTarget.cellIndex == 3){


						{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex)+") td:nth-child("+(3)+")").find(".acontainer input").focus();


						/*$("#items_table tbody tr:nth-child("+e.currentTarget.cellIndex+") td").find(".acontainer input").focus();*/}
					}
				}
				/*if(e.which == 9){
	     			if(e.currentTarget.cellIndex == 3){
	     				$scope.$apply(function(){
	     					 $("#items_table tbody tr:nth-child("+e.currentTarget.parentElement.rowIndex+") td").find("#po_qty").select();
	     				});
	     			}
	     		}*/
			});

			$scope.itemsData = $scope.itemsMastersData;
			console.log($scope.itemsData);
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
				columns: ['id','code','name','input_tax_id','tax_percentage','valuation_method','is_active','current_stock','is_manufactured','uomcode','uomname','unit_price'],
				hide: [false,true,true,false,false,false,false,false,false,false,false,false,false,false],
				placeholder: "search ..",
				highlight: "hightlight-classname",
				norecord: "No Records Found",
				delay : 10,
				onchange: function () {
					var selected_item_data =  items.all();
					console.log("selected_item_data1===========>"+JSON.stringify(selected_item_data))
					strl_scope.$apply(function(){
						var count =0;
						for(var i=0;i<strl_scope.invoice.items.length;i++){
							if(!strl_scope.invoice.items[i].isDeleted){
								if(selected_item_data.id != ""){
									if(i != strl_scope.currentIndex){
										if(selected_item_data.id == strl_scope.invoice.items[i].stock_item_id){
											count=1;
										}
									}
								}
							}
						}
						if(count != 1){
							debugger
							console.log("selected_item_data===========>"+JSON.stringify(selected_item_data))
							strl_scope.invoice.items[strl_scope.currentIndex].stock_item_id = selected_item_data.id;
							strl_scope.invoice.items[strl_scope.currentIndex].stock_item_code = selected_item_data.code;
							strl_scope.invoice.items[strl_scope.currentIndex].stock_item_name = selected_item_data.name;
							strl_scope.invoice.items[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;					
							//strl_scope.invoice.items[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;	
							//for calc Punit	
							strl_scope.invoice.items[strl_scope.currentIndex].base_uom_code = strl_scope.setuomCode(selected_item_data.uomcode,strl_scope.currentIndex);
						//	edit(selected_item_data.uomcode,strl_scope.currentIndex);
							strl_scope.invoice.items[strl_scope.currentIndex].current_stock = parseFloat(selected_item_data.current_stock).toFixed(settings['decimalPlace']);
							strl_scope.invoice.items[strl_scope.currentIndex].stock_item_batch_id = strl_scope.getItemmasterBatchId(selected_item_data.id);
							strl_scope.invoice.items[strl_scope.currentIndex].tax_pc = (selected_item_data.tax_percentage !="") ? selected_item_data.tax_percentage : 0;
							strl_scope.invoice.items[strl_scope.currentIndex].tax_id = (selected_item_data.input_tax_id !="") ? selected_item_data.input_tax_id : 0;
							strl_scope.invoice.items[strl_scope.currentIndex].unit_price=parseFloat(selected_item_data.unit_price).toFixed(2);
							strl_scope.invoice.items[strl_scope.currentIndex].unit_price1=parseFloat(selected_item_data.unit_price).toFixed(2);
							/*								strl_scope.getCostPrice(strl_scope.itemsMastersData,strl_scope.currentIndex,selected_item_data.id);
							 */

							//	strl_scope.invoice.items[strl_scope.currentIndex].pack_contains=selected_item_data.pack_contains;
							strl_scope.alert_for_codeexisting(false);

							$timeout(function () { $("#items_table tbody tr:nth-child("+strl_scope.currentIndex+1+") td").find("#po_qty").select();
							}, 1); 	    			
						}else{
							//debugger
							elem[0].parentNode.lastChild.value="";
							strl_scope.invoice.items[strl_scope.currentIndex].stock_item_id = "";
							strl_scope.invoice.items[strl_scope.currentIndex].stock_item_code = "";
							strl_scope.invoice.items[strl_scope.currentIndex].tax_pc = "";
							strl_scope.invoice.items[strl_scope.currentIndex].tax_id = "";
							strl_scope.invoice.items[strl_scope.currentIndex].uomcode = "";
							strl_scope.invoice.items[strl_scope.currentIndex].current_stock = "";
							strl_scope.invoice.items[strl_scope.currentIndex].compound_unit = "";
							strl_scope.invoice.items[strl_scope.currentIndex].base_uom_code = "";
							//strl_scope.invoice.items[strl_scope.currentIndex].pack_contains = "";


							strl_scope.alert_for_codeexisting(true);
						}



					});
				},
				data: function () {
//debugger
					var data = strl_scope.itemsData;
					console.log("data===========>"+JSON.stringify(data))
					var filterData = [];
					var searchData = eval("/" + items.searchdata() + "/gi");
					console.log("searchData===========>"+JSON.stringify(searchData))
					$.each(data, function (i, v) {
						if ( v.NAME.search(new RegExp(searchData)) != -1) {
							filterData.push(v);
							console.log("filterData===========>"+JSON.stringify(filterData));
						}
					});

					return filterData;
				}
			});

			for(var i = 0;i<strl_scope.invoice.items.length;i++){
				if(strl_scope.formData.id!=undefined && strl_scope.formData.id!='' ){
					if(strl_scope.invoice.items[i].isSet == false){
						elem[0].parentNode.lastChild.value = strl_scope.invoice.items[i].stock_item_code;
						strl_scope.disable_search_text(elem[0].parentNode.lastChild);
						strl_scope.invoice.items[i].isSet=true;break;

					}
				}else{
					if(strl_scope.invoice.items[i].isSet == false){
						if(strl_scope.invoice.items[i].po_no != undefined && strl_scope.invoice.items[i].po_no != ""){
							elem[0].parentNode.lastChild.value = strl_scope.invoice.items[i].stock_item_code;
							strl_scope.invoice.items[i].isSet=true;break;
						}
					}
				}
			}
			/*$timeout(function () { $("#items_table tr:nth-child("+(strl_scope.invoice.items.length)+") td:nth-child("+(4)+")").find(".acontainer input").select();
			}, 1);*/ 
		}
	};

}]);



