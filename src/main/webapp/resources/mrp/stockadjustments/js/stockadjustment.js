/**
 * 
 */
//Controller for Table and Form 
mrpApp.controller('stockdisposal', stockdisposal);


function stockdisposal($compile,$controller,$scope,$interval,$timeout,$http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,MRP_CONSTANT,DATATABLE_CONSTANT,$q , $window,FORM_MESSAGES,ITEM_TABLE_MESSAGES) {

	$controller('DatatableController', {$scope: $scope});

	/*	set_sub_menu("#store");		
	setMenuSelected("#stockadjustment_left_menu");	*/		//active leftmenu
	manageButtons("add");
	$scope.formData = {};
	//$scope.formData2={};
	$scope.show_table=true;$("#advsearchbox").show();
	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
	$scope.CurrentDate = new Date();


	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.setItemtableValues = setItemtableValues;
	vm.fun_get_refno = fun_get_refno;
	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.setFormDatas = setFormDatas;
	vm.form_validation = form_validation;
	vm.loadItemDetailsTable  = loadItemDetailsTable;
	vm.getSourcedep = getSourcedep;
	vm.fun_get_stockRegId = fun_get_stockRegId;
	vm.fun_get_stockRegDtl_id = fun_get_stockRegDtl_id;
	$scope.REFNO = "";
	$scope.stockRegisterList=[];
	$scope.stockDisposalDetailList=[];
	$scope.stockRegDetailsList =[];
	$scope.department_data = [];
	$scope.itemsBatchData = [];
	$scope.itemsMastersData = [];
	var itemsMastersData=[];

	function getSourcedep(){
		$("#form_div_department_code").find("input").val("");
		//	var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));


		$scope.formData.department_id = strings['isDefDepartment'];
		$scope.formData.department_code = strings['isDefDepartmentcode'];
		$scope.formData.department_name = strings['isDefDepartmentname'];
		$('#department_name').val(strings['isDefDepartmentname']);
		$("#form_div_department_code").find(".acontainer input").val(strings['isDefDepartmentcode']);


	}$("#advsearchbox").hide();
	$scope.show_table=false;
	$scope.prograssing1=true;
	$('#btnAdd').hide();



	$http({
		url : 'formJsonData',
		method : "GET",
		async:false,
	}).then(function(response) {
		loadItemDetailsTable();	 
		$scope.department_data = response.data.depData;
		console.log($scope.department_data);
		/*	$scope.stockDisposalDetailList = response.data.stockDispDtlData;
		 */	/*$scope.stockRegDetailsList = response.data.stockRegDtlData;*/
		/*	$scope.stockRegisterList = response.data.stockRegData;*/
		$scope.itemsMastersData = response.data.stockItmData;
		itemsMastersData=response.data.stockItmData;
		/*	$scope.itemsBatchData = response.data.stockItmBatchData;
		$scope.itemsBatchData1 = response.data.stockItmBatchData1;*/
		$scope.prograssing1=false;
		$scope.show_table=true;$("#advsearchbox").show();
		$('#btnAdd').show();
	}, function(response) { // optional

	});




	$http({
		url : '../itemstock/json',
		method : "GET",
		async:false,
	}).then(function(response) {
		$scope.itemsBatchData1=[];
		$scope.itemsBatchData1 = response.data.data;

	}, function(response) { // optional

	});


	$scope.getItemmasterBatchCurrentStock = function(itemid,i){

		$scope.itemBatchcurrentStock =0;
		for(var i=0;i<$scope.itemsBatchData1.length;i++){
			if($scope.itemsBatchData1[i].stock_item_id == itemid && $scope.formData.department_id==$scope.itemsBatchData1[i].department_id){
				$scope.itemBatchcurrentStock= $scope.itemsBatchData1[i].current_stock;
				break;
			}
		}
		$scope.itemBatchcurrentStock=($scope.itemBatchcurrentStock=="") ? 0 :$scope.itemBatchcurrentStock;

		return parseFloat($scope.itemBatchcurrentStock).toFixed(settings['decimalPlace']);
	}  



	/*

	$scope.getItemmasterBatchCurrentStock = function(itemid,index){


		$scope.itemData= {itemsid:itemid,departmentid:$scope.formData.department_id};
		$scope.itemBatchcurrentStock=0;


		$http({
		url : '../itemstock/currentStock',
		method : "GET",
		async:false,
		params:$scope.itemData,
	}).then(function(response) {

		$scope.itemBatchcurrentStock=response.data.stockItmBatchData1[0].current_stock;

		$timeout(function () {


			$scope.invoice.items[index].currentStock=parseFloat($scope.itemBatchcurrentStock).toFixed(settings['decimalPlace']);


		}, 1);

	}, function(response) { // optional

	});


		return parseFloat($scope.itemBatchcurrentStock).toFixed(settings['decimalPlace']);
	}  
	 */

	$scope.getItemmasterBatchId = function(itemid){
		$scope.itemBatchId = "";
		for(var i=0;i<$scope.itemsBatchData.length;i++){
			if($scope.itemsBatchData[i].stock_item_id == itemid ){
				$scope.itemBatchId = $scope.itemsBatchData[i].id;
			}
		}

		return $scope.itemBatchId;
	}



	$scope.disable_search_text=function(elemenvalue){
		if($scope.disable_all==true){
			$(elemenvalue).attr("disabled", true);
		}
	}




	$(document).on('keyup','#form_div_department_code input',function(e){
		if(e.which == 9 || e.which == 13 ){
			if($scope.formData.department_id!=""){
				$("#dispose_by").focus();}
		}
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$('#department_code').val("");

				$scope.formData.department_id ="";
				$scope.formData.department_name ="";
				$scope.formData.department_code ="";
			});
		}
	});  



	function fun_get_refno(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			$scope.REFNO = response;

		});
	}   


	$scope.fun_set_ref_no = function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			$scope.REFNUM  = response;

		});

		return $scope.REFNUM;
	}



	vm.dtInstance = {};

	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.order = "desc";
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	vm.dtColumns = [


		DTColumnBuilder.newColumn('ref_no').withTitle('REF. NO').withOption('type', 'natural').renderWith(
				function(data, type, full, meta) {
					return  urlFormater(data);
				}),
				DTColumnBuilder.newColumn('id').withTitle('ID').notVisible().withOption('searchable','false'),



						DTColumnBuilder.newColumn('adjust_date').withTitle('ADJUST DATE').renderWith(
								function(data, type, full, meta) {
									data = geteditDateFormat(data);
									return  data;
								}).withOption('width','300px'),
								

								/* DTColumnBuilder.newColumn('department_id').withTitle('department_id').notVisible().withOption('searchable','false'),*/
								DTColumnBuilder.newColumn('department_name').withTitle('DEPARTMENT').withOption('searchable','false').renderWith(
										function(data, type, full, meta) {

											return data == undefined  ? "" : data;
										}).withOption('width','300px'),

								DTColumnBuilder.newColumn('adjust_by').withTitle('ADJUST BY').withOption('width','300px'),	
								DTColumnBuilder.newColumn('approval_date').withTitle('APPROVAL DATE').withOption('width','400px').renderWith(
										function(data, type, full, meta) {
											if(data!=""){
												data = geteditDateFormat(data);}
											return data;
										}),
										DTColumnBuilder.newColumn('approval_by').withTitle('APPROVAL BY').withOption('width','300px'),
										DTColumnBuilder.newColumn('remarks').withTitle('REMARKS'),

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
	});

	$rootScope.$on('hide_table',function(event){
		showTable(event);
	});

	$rootScope.$on('hide_form',function(event){
		$('#show_form').val(0); 
		$("#advsearchbox").show();
		$("#btnDelete").show();
		$("#btnAdd").show();
		$("#btnEdit").show();
		$scope.show_table=true;
		$scope.show_form=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
	});

	function showTable(event){
		//	$('#itemLists').hide();
		$("#advsearchbox").hide();
		$scope.show_table=false;
		$scope.show_form=true;
	}



	function loadItemDetailsTable(){
		$http({
			url : 'stockRegJsonData',
			method : "GET",
		}).then(function(response) {
			/*			$scope.itemsBatchData1=[];
			 */			$scope.stockDisposalDetailList = response.data.stockDispDtlData;
			 $scope.stockRegDetailsList = response.data.stockRegDtlData;
			 $scope.stockRegisterList = response.data.stockRegData;
			 $scope.itemsBatchData = response.data.stockItmBatchData;
			 /*			$scope.itemsBatchData1 = response.data.itemsBatchData1;
			  */			/*console.log($scope.stockDisposalDetailList);*/
		}, function(response) { // optional
		});


		/*	$http({
			url : '../itemstock/json',
			method : "GET",
		}).then(function(response) {
			$scope.itemsBatchData1 = response.data.data;

		}, function(response) { // optional

		});*/

	}






	function reloadData() {

		vm.dtInstance.reloadData(null, true);
		loadItemDetailsTable();

	}


	function edit(row_data,cur_row_index,event) {									
		$("#advsearchbox").hide();

		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function
		var row_count =vm.dtInstance.DataTable.rows().data();
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

		$scope.CurrentDate = row_data.adjust_date;
		var depList = fun_get_dep_name(row_data.department_id);


		$scope.REFNO=row_data.ref_no;
		$scope.formData = {id:row_data.id,adjust_date:geteditDateFormat(row_data.adjust_date),approval_date:row_data.approval_date,approval_by:row_data.approval_by,remarks:row_data.remarks,department_id : row_data.department_id ,department_code : depList.code,department_name : depList.name,adjust_by:row_data.adjust_by};
		$("#form_div_department_code").find(".acontainer input").val(depList.code);
		$scope.disable_all = true;
		if(row_data.approval_date==""){
			$scope.approve=true;
			$('#Approve_by').val(0);
		}else{
			$scope.approve=false;
			$('#Approve_by').val(1);
		}
		if(row_data.approval_date!="")
		{
			$("#btnDelete").hide();
			$("#btnAdd").hide();
			$("#btnEdit").hide();

		}

		$(".acontainer input").attr('disabled', true);
		loadItemDetailsTable();
		$scope.formData.stockreg_id=fun_get_stockRegId(row_data.id);
		setItemtableValues(row_data);

		$("#form_div_department_code").find("input").val("");
		//	var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));


		$scope.formData.department_id = strings['isDefDepartment'];
		$scope.formData.department_code = strings['isDefDepartmentcode'];
		$scope.formData.department_name = strings['isDefDepartmentname'];
		$('#department_name').val(strings['isDefDepartmentname']);
		$("#form_div_department_code").find(".acontainer input").val(strings['isDefDepartmentcode']);

	}


	/*$timeout(function () {$("#DataTables_Table_0_filter").hide(); }, 1); 		

	$rootScope.$on("advSearch",function(event){

		vm.dtInstance.DataTable.search('').draw();	
		$scope.orderstatus=$('#ordertype').val();
		$scope.date=$('#orderDate').val();
		$scope.reqNo=$('#reqNo').val();
		$("#SearchText").append("<span class='label label-important'>"+$scope.date+'</span>');

		DataObj.adnlFilters=[{col:3,filters:$scope.date},{col:0,filters:$scope.reqNo}];
			vm.dtInstance.reloadData();

	});

	$("#clear").click(function(){
		DataObj.adnlFilters=[{}];
		$('#SearchText').val("");
		vm.dtInstance.DataTable.search($('#SearchText').val()).draw();	
	});

	$rootScope.$on("Search",function(event){
		DataObj.adnlFilters=[{col:2,filters:""},{col:1,filters:""},{col:3,filters:""}];
		vm.dtInstance.reloadData();
			vm.dtInstance.DataTable.search($('#SearchText').val()).draw();	

	});

	$("#clear").click(function(){

		$('#SearchText').val("");
		vm.dtInstance.DataTable.search($('#SearchText').val()).draw();	
	});
	 */



	// Advanced search
	$timeout(function () {$("#DataTables_Table_0_filter").hide(); }, 1); 

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
						DataObj.adnlFilters=[{col:3,filters:$scope.date},{col:0,filters:$scope.reqNo}];

						vm.dtInstance.reloadData(); 
					};

					counter++;
					//
					//	Object.keys(object).find(key => object[key] === value)

				}
			}
		}



		DataObj.adnlFilters=[{col:3,filters:$scope.date},{col:0,filters:$scope.reqNo}];
		vm.dtInstance.reloadData();
		$scope.searchTxtItms={};

	});

	$rootScope.$on("Search",function(event){
		DataObj.adnlFilters=[{}];
		$scope.searchTxtItms={};
		/*		vm.dtInstance.reloadData();
		 */			vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	

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


	function setItemtableValues(row_data){
		$scope.prograssing = true;
		$scope.invoice = {items: []};
		loadItemDetailsTable();


		$http({
			url : 'getStockAdjstDtl',method : "GET",params:{id:row_data.id},
		}).then(function(response) {

			$scope.invoice.items = response.data.stkAdjstDtl;
			for(var i = 0;i<response.data.stkAdjstDtl.length;i++){

				$scope.invoice.items[i].actual_qty= parseFloat(response.data.stkAdjstDtl[i].actual_qty).toFixed(settings['decimalPlace']);
				$scope.invoice.items[i].rate= parseFloat(response.data.stkAdjstDtl[i].cost_price).toFixed(2);
				if(row_data.approval_date=="")
				{
					$scope.invoice.items[i].currentStock=$scope.getItemmasterBatchCurrentStock(response.data.stkAdjstDtl[i].stock_item_id,i);
				}
				else
				{
					$scope.invoice.items[i].currentStock=parseFloat(response.data.stkAdjstDtl[i].system_qty).toFixed(settings['decimalPlace']);
				}
				$scope.invoice.items[i].stock_item_batch_id=$scope.getItemmasterBatchId(response.data.stkAdjstDtl[i].stock_item_id);



				$scope.invoice.items[i].flag = 0;


			}
			$scope.prograssing = false;
		}, function(response) { 
			$scope.prograssing = false;
		});


	}



	/*	function fun_get_stockRegDtl_id(dispDetId){

		var stockRegDtlid = "";
		for(var i = 0;i<$scope.stockRegDetailsList.length;i++){
			if($scope.stockRegDetailsList[i].ext_ref_dtl_id == dispDetId){
				stockRegDtlid = $scope.stockRegDetailsList[i].id;
			}
		}

		return stockRegDtlid;
	}*/




	function fun_get_stockRegDtl_id(dispDetId){

		var stockRegDtlid = "";

		$http({
			url : 'getstockregdtlid',
			method : "GET",
			params:{id:dispDetId},
		}).then(function(response) {

			if(response.data.stockregdtlid.length!=0){



				stockRegDtlid = response.data.stockregdtlid[0].id;
			}
		}, function(response) { // optional 

		});




		return stockRegDtlid;
	}


	function setBatchData()
	{

		$scope.formData.batch=[];
		for(var i = 0;i<$scope.invoice.items.length;i++){
			$scope.formData.batch.push({
				stock_item_batch_id:$scope.invoice.items[i].stock_item_batch_id,
				adjust_qty: $scope.invoice.items[i].adjust_qty,
				cost_price:$scope.invoice.items[i].rate,
				stock_item_id:$scope.invoice.items[i].stock_item_id,
			});

		}
	}





//	Delete Function
	$rootScope.$on("fun_delete_current_data",function(event){
		setBatchData();
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
				params : {id:$scope.formData.id,ext_ref_no:$scope.REFNUM ,stockreg_id:$scope.formData.stockreg_id,batch:$scope.formData.batch},
			}).then(function(response) {
				if(response.data == 1){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
					$('#show_form').val(0); 
					vm.dtInstance.reloadData(null, true);
					reloadData();
					$("#advsearchbox").show();
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
					//var index = select_next_prev_row(current_row_index);
					$scope.disable_all = true;
					$(".acontainer input").attr('disabled', true);
					$scope.disable_code = true;
					//});



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
			$("#advsearchbox").show();
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
		$(".acontainer input").attr('disabled', true);
	}

	function fun_get_dep_name(id){
		var depList =[];
		for(var i=0;i < $scope.department_data.length;i++){
			if($scope.department_data[i].id == id){
				depList = $scope.department_data[i];
				break;
			}
		}


		return depList;
	}
	/*
	function fun_get_stockRegId(refno){
		var stockRegid = "";
		for(var i = 0;i<$scope.stockRegisterList.length;i++){

			if($scope.stockRegisterList[i].ext_ref_no == refno){
				$scope.formData.stockreg_id = $scope.stockRegisterList[i].id;
				stockRegid = $scope.stockRegisterList[i].id;
			}


		}

		return stockRegid;
	}*/


	function fun_get_stockRegId(refno){

		var stockRegid = "";
		$http({
			url : '../stockin/getstockredid',
			method : "GET",
			params:{id:refno},
		}).then(function(response) {

			if(response.data.stockregid.length!=0){



				stockRegid = response.data.stockregid[0].id;
			}
		}, function(response) { // optional 

		});


		return stockRegid;
	}

	function setFormDatas(){
		$scope.formData.ref_no=$scope.REFNO;
		$scope.formData.adjust_date=getMysqlFormat($scope.formData.adjust_date);
		var date =get_date_format();
		$scope.formData.approvalStatus=0;
		if(($('#Approve_by').val())==1){
			$scope.formData.approvalStatus=1;
			$scope.formData.approval_date=getMysqlFormat(date.mindate);

			$scope.formData.approval_by=strings['name'];}
		for(var i = 0;i<$scope.invoice.items.length;i++){
			$scope.invoice.items[i].stock_item_batch_id = $scope.getItemmasterBatchId($scope.invoice.items[i].stock_item_id);

			$scope.invoice.items[i].diff_qty=$scope.diff($scope.invoice.items[i].currentStock,$scope.invoice.items[i].actual_qty);
		}

	}

	$rootScope.$on('fun_save_data',function(event){		//Save Function
		$scope.saveData(event);
	});

	$scope.aproveData=function(event){
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(' Are you sure to approve?').targetEvent(event).cancel('No').ok('Yes');
		$mdDialog.show(confirm).then(function() {

			$('#Approve_by').val(1);
			$scope.saveData(event);
			$scope.stockdata==0;
			$http({
				url : '../itemstock/json',
				method : "GET",
			}).then(function(response) {
				$scope.itemsBatchData1=[];
				$scope.itemsBatchData1 = response.data.data;

			}, function(response) { // optional

			});
		});
	}


	$scope.saveData=function(event){
		if (form_validation($scope.formData)) {
			setFormDatas();

			$http({
				url : 'saveStockAdjustment',
				method : "POST",
				params : $scope.formData,
				data:{invoiceItems:$scope.invoice.items},
			}).then(function(response) {
				vm.dtInstance.reloadData(null, true);

				reloadData();
				if(response.data == 1 )
				{
					if($scope.formData.id !=undefined)
					{
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
						view_mode_aftr_edit();
						$scope.formData.adjust_date=geteditDateFormat($scope.formData.adjust_date);
						if($scope.formData.approval_date==""){
							$scope.formData.approvalStatus=0;
							$scope.approve=true;}
						else{
							$("#btnDelete").hide();
							$("#btnAdd").hide();
							$("#btnEdit").hide();
							$scope.approve=false;
							$scope.formData.approvalStatus=1;


						}

					}
					else
					{
						var date =get_date_format();
						$('#Approve_by').val(0);
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
						$scope.formData ={};
						fun_get_refno();
						$scope.invoice = {items: []};
						$scope.invoice.items.push({id:0,stock_adjust_hdr_id :"",stock_item_batch_id:"",stock_item_batch_stock:0,stock_item_id:"",stock_item_code:"",stock_item_name:"",actual_qty:0,adjust_qty: 0,diff_qty:0,rate:0,currentStock:0,});
						$scope.formData.adjust_date=date.mindate;
						$scope.approve=false;
						$("#form_div_department_code").find(".acontainer input").val("");



						getSourcedep();
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
	$rootScope.$on("fun_discard_form",function(event){				//Discard function
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

				$scope.invoice = {
						items: []
				};

				$scope.invoice.items.push({id:0,stock_adjust_hdr_id :"",stock_item_batch_id:"",stock_item_batch_stock:0,stock_item_id:"",stock_item_code:"",stock_item_name:"",actual_qty:0,adjust_qty: 0,diff_qty:0,rate:0,currentStock:0,});
				var date =get_date_format();
				$scope.formData.adjust_date=date.mindate;
				getSourcedep();

			}else{
				var dataIndex = vm.dtInstance.DataTable.rows();
				var row_data = vm.dtInstance.DataTable.rows(dataIndex[0][cur_row_index]).data();
				edit(row_data[0],cur_row_index);
			}
			clearform();
			$('#form_div_dispose_date').removeClass("has-error");
			$('#form_div_received_date_error').hide();
		});

	});

	$rootScope.$on("fun_enable_inputs",function(){
		$('#show_form').val(1); 
		$scope.approve=false;
		$scope.disable_all = false;
		$(".acontainer input").attr('disabled', false);
		$('#form_div_department_code').find(".acontainer input").attr('disabled', true);


	});


	$scope.stockdata=1;
	$rootScope.$on("fun_clear_form",function(){
		if($scope.stockdata==1){
			$scope.stockdata==0;
			$http({
				url : '../itemstock/json',
				method : "GET",
			}).then(function(response) {
				$scope.itemsBatchData1=[];
				$scope.itemsBatchData1 = response.data.data;

			}, function(response) { // optional

			});
		}

		var date =get_date_format();
		$('#Approve_by').val(0);
		$scope.formData.approvalStatus=0;
		$(".acontainer input").val("");
		$scope.formData = {};
		$scope.formData.adjust_date=date.mindate;
		getSourcedep();
		fun_get_refno();
		$scope.invoice = {
				items: []
		};
		$scope.invoice.items.push({id:0,stock_adjust_hdr_id :"",stock_item_batch_id:"",stock_item_batch_stock:0,stock_item_id:"",stock_item_code:"",stock_item_name:"",actual_qty:0,adjust_qty: 0,diff_qty:0,rate:0,currentStock:0,});
		$('#form_div_department_code').find(".acontainer input").attr('disabled', true);
		$('#form_div_dispose_date').removeClass("has-error");
		$('#form_div_received_date_error').hide();

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
		if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id=="" && $scope.invoice.items.length!=1 ){
			$scope.invoice.items.splice($scope.invoice.items.length-1, 1);
		}
		var flg = true;


		if(validation() == false){
			flg = false;
		}
		if($scope.formData.department_code==null || $scope.formData.department_code=="" || $scope.formData.department_code==undefined  ){

			$("#form_div_department_code .acontainer").find("input").focus();
			$rootScope.$emit("departmenterrorcode");
			flg = false;
		}
		if($scope.formData.adjust_date == "")
		{
			$('#form_div_dispose_date').addClass("has-error");
			$('#form_div_received_date_error').show();flg = false;
		}else
		{
			$('#form_div_dispose_date').removeClass("has-error");
			$('#form_div_received_date_error').hide();
		}
		if($scope.invoice.items.length > 0){
			$scope.item_table_empty=[];
			$scope.item_details_table_empty=[];

			for(var i = 0;i<$scope.invoice.items.length;i++){
				/*$.each($scope.invoice.items[i],function(key,value){*/
				if($scope.invoice.items[i].stock_item_id != "" && $scope.invoice.items[i].stock_item_code != ""){
					if($scope.invoice.items[i].actual_qty == "" || $scope.invoice.items[i].actual_qty =="0" || $scope.invoice.items[i].actual_qty =="0."
						|| $scope.invoice.items[i].actual_qty == "." || $scope.invoice.items[i].actual_qty =="0.0"){

						$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter actual Qty");
						flg = false;
						$scope.item_details_table_empty.push($scope.invoice.items[i]);
						$("#items_table tr:nth-child("+(i+2)+")").find("#damaged_qty").select();


					}
					/*else if((parseInt($scope.invoice.items[i].damaged_qty)> parseInt($scope.invoice.items[i].currentStock)) || (parseInt($scope.invoice.items[i].damaged_qty)<0)){

						$rootScope.$broadcast('on_AlertMessage_ERR',"Incorrect damaged Qty");
						flg = false;
						//$scope.item_details_table_empty.push($scope.invoice.items[i]);
						$("#items_table tr:nth-child("+(i+2)+")").find("#damaged_qty").select();

					}*/



				}else{
					$scope.item_table_empty.push($scope.invoice.items[i]);
				}
				/*});*/
			}
			if($scope.item_table_empty.length > 0){
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				//$rootScope.$emit("tableItemCodeErrorMsg");
				$(".acontainer").find("input").focus();
				flg = false;
			}else{
				if($scope.item_details_table_empty.length > 0){
					$("#table_validation_alert").addClass("in");
					//$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Adjustment Qty");
					flg = false;
				}else{
					$("#table_validation_alert").removeClass("in");
				}
			}
		}else if($scope.invoice.items.length == 0){
			$("#table_validation_alert").addClass("in");
			$rootScope.$broadcast('on_AlertMessage_ERR', ITEM_TABLE_MESSAGES.TABLE_ERR);
			flg = false;
		}

		if(flg==false)
		{
			focus();
		}
		return flg;
	}



	$scope.invoice = {
			items: []
	};

	$scope.invoice.items.push({id:0,stock_adjust_hdr_id :"",stock_item_batch_id:"",stock_item_batch_stock:0,stock_item_id:"",stock_item_code:"",stock_item_name:"",actual_qty:0,adjust_qty: 0,diff_qty:0,rate:0,currentStock:0,});


	$scope.addItem = function(index) {

		if($scope.disable_all == false){
			if(index<$scope.invoice.items.length-1){
				$("#items_table tr:nth-child("+(index+3)+") td:nth-child("+(3)+")").find(".acontainer input").focus();
			}else{
				if($scope.invoice.items.length != 0){
					if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id != "" && $scope.invoice.items[$scope.invoice.items.length-1].stock_item_code != ""){

						if($scope.invoice.items[$scope.invoice.items.length-1].actual_qty == "0." ||$scope.invoice.items[$scope.invoice.items.length-1].actual_qty == "" || $scope.invoice.items[$scope.invoice.items.length-1].actual_qty =="0"){
							$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter actual Qty");
							$("#items_table tr:nth-child("+($scope.invoice.items.length+1)+")").find("#damaged_qty").select();


						}
						else if((parseInt($scope.invoice.items[$scope.invoice.items.length-1].damaged_qty)> parseInt($scope.invoice.items[$scope.invoice.items.length-1].currentStock))){

							$rootScope.$broadcast('on_AlertMessage_ERR',"Incorrect damaged Qty");
							$("#items_table tr:nth-child("+($scope.invoice.items.length+1)+")").find("#damaged_qty").select();


						}
						else{
							$scope.invoice.items.push({id:0,stock_adjust_hdr_id :"",stock_item_batch_id:"",stock_item_batch_stock:0,stock_item_id:"",stock_item_code:"",stock_item_name:"",actual_qty:0,adjust_qty: 0,diff_qty:0,rate:0,currentStock:0,});
						}
					}
					else{		    					
						$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);

						$("#items_table tr:nth-child("+($scope.invoice.items.length+1)+")").find(".acontainer input").focus();
						if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id == "" && $scope.invoice.items[$scope.invoice.items.length-1].stock_item_code == ""){
							$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
						}	

					}

				}	
				else if($scope.invoice.items.length == 0){
					$scope.invoice.items.push({id:0,stock_adjust_hdr_id :"",stock_item_batch_id:"",stock_item_batch_stock:0,stock_item_id:"",stock_item_code:"",stock_item_name:"",actual_qty:0,adjust_qty: 0,diff_qty:0,rate:0,currentStock:0,});
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
					$("#items_table tr:nth-child("+($scope.invoice.items.length+1)+")").find("#adjust_qty").focus();	
				}

			});

		}

	},

	$scope.total = function() {
		var total = 0;
		angular.forEach($scope.invoice.items, function(item) {
			total += item.damaged_qty * item.rate;
		})

		tota=parseFloat(total).toFixed(2);
		return tota;
	}


	$scope.amount = function(amount) {    
		amount=parseFloat(amount).toFixed(2);
		return amount;
	}

	$scope.diff = function(curr,actual) {
		var total = 0;

		var actual = (actual == "" || actual ==  "-" || actual == ".") ? 0 : parseFloat(actual).toFixed(settings['decimalPlace']);

		if((parseInt(curr)) > (parseInt(actual))){
			total=(curr) - (actual);
		}else{
			total=(actual) - (curr);
		}


		//total=(curr) - (actual);

		return total.toFixed(settings['decimalPlace']);
	}

	$scope.differenceView = function(curr,actual) {
		var total = 0;

		var actual = (actual == "" || actual ==  "-" || actual == ".") ? 0 : parseFloat(actual).toFixed(settings['decimalPlace']);

		/*if((parseInt(curr)) > (parseInt(actual))){
			total=(curr) - (actual);
		}else{
			total=(actual) - (curr);
		}*/


		total=(actual)-(curr);

		return total.toFixed(settings['decimalPlace']);
	}



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
				$scope.formData.department_id =  selected_department_data.id;
				$scope.formData.department_code =  selected_department_data.code;
				$scope.formData.department_name =  selected_department_data.name;
			});

		},
		data: function () {

			var data = $scope.department_data;

			var filterData = [];

			var searchData = eval("/" + departmentData.searchdata() + "/gi");

			$.each(data, function (i, v) {
				if (v.code.search(v.name.search(new RegExp(searchData)) != -1)) {
					filterData.push(v);
				}
			});

			return filterData;
		},


	});


	$scope.alert_for_codeexisting = function(flg){
		if(flg==0){
		}else if(flg == 1){
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.CODE_EXISIT);
		}

	}


	$scope.getCostPrice = function(data,index,id){
		var url="";
		var costPrice =0.00;
		$.each(data, function (i, v) {
			if(v.id == id){

				if(v.valuation_method == 1){
					url = "getLastCostPrice";
				}else{
					url = "getAverageCostPrice";
				}

			}
		});

		$http({url :url,method : "GET",params:{itemId:id}
		}).then(function(response) {
			$scope.invoice.items[index].rate = response.data;
		}, function(response) { // optional

		});
	}



	$scope.clear_stock_details_editmode =  function(event){

		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].stock_item_id = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].stock_item_code = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].stock_item_name = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].rate = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].currentStock = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].actual_qty = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].uomcode = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex-1].rate = 0.0;

	}

	$("#dispose_by").keyup('input',function(e){
		if(e.which!=9 || e.which==13){
			$("#items_table tr:nth-child("+($scope.invoice.items.length+2)+")").find(".acontainer input").focus();
		}});

}

mrpApp.directive('autocompeteText', ['$timeout',function($timeout) {
	return {

		controller: function ($scope,$http) {


			/* $("#items_table tbody tr td").keyup('input',function(e){
	  					   $scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;

		  	    		 if(e.which != 9 && e.which != 13){
		  	     			if(e.currentTarget.cellIndex == 2){
		  	     				$scope.$apply(function(){
		  	     					$scope.alert_for_codeexisting(0);
		  	     				});
		  	     			}
		  	    		 }
		  	    			if(e.which == 8 || e.which == 46){
		  	  				if(e.currentTarget.cellIndex == 2){
		  	  					$scope.$apply(function(){
		  	  						$scope.clear_stock_details_editmode(e);
		  	  					$scope.alert_for_codeexisting(0);
		  	  					});}}


		  	     		else if(e.which == 13 ){
		  	     			if(e.currentTarget.cellIndex == 2){
		  	     				if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id!=""){
		  	     				{$("#items_table tr:nth-child("+($scope.invoice.items.length+1)+")").find("#damaged_qty").select();}
		  	     		}}
		  				}else if(e.which == 9 ){
		  	     			if(e.currentTarget.cellIndex == 2){
		  	     				//if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id==""	){
		  	     				{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find(".acontainer input").focus();}
		  	     		}
		  				}
		  	     	});*/
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
						if($scope.invoice.items[$scope.invoice.items.length-1].stock_item_id!=""){
							{ 
								$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+") td:nth-child("+(5)+")").find("#damaged_qty").select();
							}
						}}
				}else if(e.which == 9 ){
					if(e.currentTarget.cellIndex == 2){


						{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+") td:nth-child("+(3)+")").find(".acontainer input").focus();


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

				columns: ['id','code','name','department_id','department_name','input_tax_id','tax_percentage','valuation_method','is_active','is_manufactured','uomcode','uomname','unit_price'],
				hide: [false,true,true,false,false,false,false,false,false,false,false,false,false],
				/*columns: ['id','code','name','uomcode','unit_price'],
	    		hide: [false,true,true,false,false],*/
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
								strl_scope.invoice.items[strl_scope.currentIndex].stock_item_batch_id = 0;
								strl_scope.invoice.items[strl_scope.currentIndex].currentStock = strl_scope.getItemmasterBatchCurrentStock(selected_item_data.id,strl_scope.currentIndex);
								strl_scope.invoice.items[strl_scope.currentIndex].uomcode= selected_item_data.uomcode;
								strl_scope.invoice.items[strl_scope.currentIndex].rate=selected_item_data.unit_price;

								strl_scope.alert_for_codeexisting(0);
								/*		strl_scope.getCostPrice(strl_scope.itemsMastersData,strl_scope.currentIndex,selected_item_data.id);*/

								$timeout(function () {$("#items_table tr:nth-child("+(strl_scope.currentIndex+2)+")").find("#damaged_qty").focus();}, 1); 

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

						if ( v.name.search(new RegExp(searchData)) != -1) {
							filterData.push(v);
						}
					});

					return filterData;
				}
			});

			for(var i = 0;i<strl_scope.invoice.items.length;i++){
				if(strl_scope.formData.id!=undefined && strl_scope.formData.id!='' ){
					if(strl_scope.invoice.items[i].flag==0){
						elem[0].parentNode.lastChild.value = strl_scope.invoice.items[i].stock_item_code;
						strl_scope.disable_search_text(elem[0].parentNode.lastChild);
						strl_scope.invoice.items[i].flag=1;break;
					}
				}
			}
			$timeout(function () {

				//if(strl_scope.formData.adjust_by!=undefined)
				$("#items_table tr:nth-child("+(strl_scope.invoice.items.length+1)+") td:nth-child("+(3)+")").find(".acontainer input").select();
			}, 1); 
		}
	};
}]);










