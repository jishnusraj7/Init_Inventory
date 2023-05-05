
//Controller for Table and Form 
mrpApp.controller('dailyproduction', dailyproduction);

function dailyproduction($compile,$controller,$scope,$interval,$timeout,$http, $mdDialog , $rootScope, DTOptionsBuilder,
		DTColumnBuilder,MRP_CONSTANT,DATATABLE_CONSTANT,$q , $window,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS,$filter) {

	$controller('DatatableController', {$scope: $scope});

	$("#btnAdd").hide();
	//manageButtons("add");
	$scope.formData = {};
	$scope.show_table = true;
	$("#advsearchbox").show();
	$scope.show_form = false;
	$scope.hide_code_existing_er = true;
	$scope.CurrentDate = new Date();
	$('#btnBack').show();
	$scope.disable_all_shop=false;
	
	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.setItemtableValues = setItemtableValues;
	vm.fun_get_refno = fun_get_refno;
	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.setFormDatas = setFormDatas;
	
	$scope.REFNO = "";
	var dt=new Date();
	$scope.prod_status ="FINALIZED" ;
	$scope.prodStatus=true;	
	$rootScope.filteredDepartmentId = "";
	$rootScope.filteredDepName = "";
	
	var todayDate = new Date();
	$scope.formData.prodDate=dateForm(todayDate);
	$scope.daily_production ={items: []};
	
	$scope.daily_production.items.push({
		stock_item_id: "", stock_item_code: "", stock_item_name: "", uomcode: "",
		schedule_qty: 0,order_qty: 0,totalOrder: 0,prod_qty: 0, itemRate: 0,
		remarks: "",flag: 0,prodnHdrId: 0,hdrRemarks: "",sales_price: 0,
		itemMaterialCost: 0,otherCost: 0,saleRate: 0,totalCost: 0, damageqty: 0
	});	

	var department_data = [];
	$scope.department_data=[];

	$http({	url : 'depList',	method : "GET",async:false,
	}).then(function(response) {
		department_data = response.data.data;
		$scope.department_data=response.data.data;

	}, function(response) { // optional
	});
	
	function setDepDetailsById(id)
	{
		for(var i=0;i<department_data.length;i++)
		{
			if(department_data[i].id == id)
			{
				$scope.formData.source_code=department_data[i].code;
				$("#form_div_source_code").find(".acontainer input").val($scope.formData.source_code);
				$scope.formData.source_name=department_data[i].name;
			}
		}
	}
	function setDepDetailsByIdRow(id,index)
	{
		for(var i=0;i<department_data.length;i++)
		{
			if(department_data[i].id == id)
			{
				$scope.daily_production.items[index].source_code =  department_data[i].code;
				$scope.daily_production.items[index].source_name=  department_data[i].name;
			}
		}
	}

	$scope.addBomRow = function(index) {

		if($scope.disable_all == false){
			if(index<$scope.bomList.length-1){
				$("#stockHead tbody tr:nth-child("+(index+2)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
			}else{


				if($scope.bomList.length >=1){
					if($scope.bomList[$scope.bomList.length-1].bom_item_id=="")
					{

						$("#stockHead tbody tr:nth-child("+(index+2)+") td:nth-child("+(2)+")").find(".acontainer input").focus();

					}
					else if($scope.bomList[$scope.bomList.length-1].qty=="")
					{

						$("#stockHead tbody tr:nth-child("+(index)+") td:nth-child("+(4)+")").find("#qty").focus();

					}
					else
					{
						$scope.bomList.push({id : "",bom_item_id :"",qty: "",bom_item_name:""});

					}


				}
				else
				{
					$scope.bomList.push({id : "",bom_item_id :"",qty: "",bom_item_name:""});
				}


			}}

	}

	$scope.removeBomRow = function(index) {
		if($scope.disable_all == false){			
			if($scope.bomList.length==1)
			{							
				if(index==0)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.TABLE_ERR);

				}
			}
			else
			{
				$scope.bomList.splice(index, 1);

			}
		}

	}
	$scope.clear_bom_details_editmode =  function(event){
		$scope.bomList[event.currentTarget.parentElement.rowIndex-1].id = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex-1].bom_item_id = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex-1].bom_item_code = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex-1].bom_item_name = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex-1].qty ="";
		$scope.bomList[event.currentTarget.parentElement.rowIndex-1].uomcode ="";
		$scope.bomList[event.currentTarget.parentElement.rowIndex-1].unit_price ="";	
	}

	$scope.setSuc_AlertMessageModal=function(event,msg){
		$scope.succ_alertMessageStatusModal=false; 
		$scope.succ_alertMeaasgeModal    =  msg;
		$timeout(function () { $scope.succ_alertMessageStatusModal = true; }, 1500); 
	}
	$scope.setErr_AlertMessageModal=function(event,msg){
		$scope.err_alertMessageStatusModal=false; 
		$scope.err_alertMeaasgeModal    =  msg;
		$timeout(function () { $scope.err_alertMessageStatusModal = true; }, 1500); 
	}



	$scope.updateBomData=function()
	{
		if(bomValidation() && prodCostValidation())
		{
			//alert($scope.selectedStkItemId);
			$scope.Quetable={id:"",dateTime:"",sysSaleFlag:true,syncNow:0,curdAction:"U"};
			$scope.Quetable.shopId=settings['currentcompanyid1'];
			$scope.Quetable.origin=settings['currentcompanycode1'];
			var data = JSON.stringify({
				bomData1 : $scope.bomList,
				prodCostList:$scope.prodCostList,
				Quetable:$scope.Quetable
			});

			var fdata = new FormData();
			fdata.append("data", data);
			$http.post("updateBomData", fdata, {
				transformRequest : angular.identity,
				params:{stkId:$scope.selectedStkItemId,bomQty:$scope.bomQty,prodCostList:$scope.prodCostList},
				headers : {
					'Content-Type' : undefined
				}
			}).success(
					function(response) {
						if(response.data != 0)
						{
							$scope.setSuc_AlertMessageModal('setSuc_AlertMessageModal',FORM_MESSAGES.UPDATE_SUC);
							$scope.getUpdatedCostData($scope.selectedStkItemId);

						}
					}).error(
							function(response) { // optional

								$mdDialog.show($mdDialog.alert().parent(
										angular.element(document
												.querySelector('#dialogContainer')))
												.clickOutsideToClose(true).textContent(
												"Update failed.").ok('Ok!')
												.targetEvent(event));

							});
			//$("#importDataModal").toggle();
		}} 

	$scope.disable_search_text=function(elemenvalue){
		if($scope.disable_all==true){
			$(elemenvalue).attr("disabled", true);
		}
	}

	vm.dtInstance = {};

	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.order = "desc";
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	
	
		
	
	vm.dtColumns = [
	                DTColumnBuilder.newColumn('id').withTitle('ID').notVisible().withOption('searchable','false'),
	                DTColumnBuilder.newColumn('prod_no').withTitle('PROD NO').withOption('type', 'natural').renderWith(
	                		function(data, type, full, meta) {

	                			return urlFormater(data);  
	                		}),
	                		/*DTColumnBuilder.newColumn('dept').withTitle('DEPARTMENT').withOption('type', 'natural').renderWith(
	                				function(data, type, full, meta) {
	                					//data = getDeparmentNameCol(full.id);
	                					return data;
	                				}),
	                		 */

	                		DTColumnBuilder.newColumn('prod_date').withTitle('PROD DATE').withOption('type', 'natural').renderWith(
	                				function(data, type, full, meta) {
	                					data = geteditDateFormat(data);
	                					return data;
	                				}),
	                				DTColumnBuilder.newColumn('prod_time')
	                				.withTitle('PROD TIME').withOption('width', '250px')
	                				.withOption('searchable', false).renderWith(
	                						function(data, type, full, meta) {
	                							return $filter('date')(new Date(full.prod_time),filterTime());

	                						}),
	                						/*DTColumnBuilder.newColumn('status').withTitle('STATUS').withOption('searchable',false).renderWith(
	                								function(data, type, full, meta){
	                									if(full.status == 0){
	                										data =  "<span style='color:green;'>"+RECORD_STATUS.PENDING+"</span>";
	                									}else if(full.status ==1){
	                										data = "<span style='color:#3c8dbc;'>"+RECORD_STATUS.FINALIZED+"</span>";
	                									}
	                									return data;
	                								})*/

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
					$('#show_form').val(1);
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
		$scope.show_table=true;$("#advsearchbox").show();
		$scope.show_form=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
	});

	function showTable(event){
		$("#advsearchbox").hide();
		$scope.show_table=false;
		$scope.show_form=true;
	}



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

					angular.element(document.getElementById('SearchText'))
					.append($compile("<div id="+key+"  class='advseacrh ' contenteditable='false'>"+$scope.searchTxtItms[key]
					+"<span class='close-thik' contenteditable='false'  ng-click='deleteOptn("+key+"); '></span></div>")($scope))
					
					$scope.deleteOptn = function (key) {
						delete $scope.searchTxtItms[key];	
						$('#'+key).remove();
						switch(key){

							case 2:
								$scope.date="";
								$('#orderDate').val();
								break;
							case 3:
								$scope.reqNo="";
								$('#reqNo').val("");
								break;
						}
						
						DataObj.adnlFilters=[{col:3,filters:$scope.date},{col:1,filters:$scope.reqNo}];
						vm.dtInstance.reloadData(); 
					};
					counter++;
				}
			}
		}

		DataObj.adnlFilters=[{col:3,filters:$scope.date},{col:1,filters:$scope.reqNo}];
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
		loadItemDetailsTable();

	}


	function edit(row_data,cur_row_index,event) {									

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

		//	setItemtableValues(row_data.id,fun_get_stockRegId(row_data.ref_no));



		showTable();
		clearform();
		manageButtons('view');
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.formData = {id:row_data.id};
		$scope.REFNO=row_data.prod_no;
		$scope.formData.prod_no=row_data.prod_no;
		$scope.formData.prodDate = geteditDateFormat(row_data.prod_date);
		$scope.formData.produptoDate= geteditDateFormat(row_data.prod_upto);
		
		$scope.disable_all_shop=true;
		if(settings['dailyprodview']==1)
		{
			$scope.slctypreq = row_data.slctypreq;
			if($scope.slctypreq==0)
			{
				$scope.companyId = row_data.shop_id;
			}
			else
			{
				$scope.customerIds1=row_data.shop_id;
			}
		}		
		
		$scope.disable_all = true;
		$timeout(function () {
			$(".acontainer input").attr('disabled', true);
		}, 1);

		//loadItemDetailsTable();
		setItemtableValues(row_data.id); 
		$('#btnBack').show();

	}

	function setItemtableValues(id){
		$scope.prograssing = true;
		$scope.daily_production ={items: []};
		// loadItemDetailsTable();


		$http({
			url : 'getProductionDtl',method : "GET",params:{id:id},
		}).then(function(response) {

			
			
			$scope.daily_production.items ={items: []};
			$scope.daily_production.items=response.data.prodDtl;

			$scope.salesOrderHdrIds=response.data.orderHdrIds;
			setItemsData($scope.daily_production.items);
			$scope.formData.remarks=$scope.daily_production.items[0].hdrRemarks;
			$scope.formData.prodId=$scope.daily_production.items[0].prodnHdrId;
			$scope.formData.id=$scope.daily_production.items[0].prodnHdrId;
			$scope.formData.stock_reg_id=$scope.daily_production.items[0].stckRegHdrId;
			$scope.formData.status=$scope.daily_production.items[0].status;
			$('#btnDelete').hide();
			$('#btnEdit').hide();
			$scope.showFinalize=false;
			$scope.prodStatus=false;
			$('#btnBack').show();
			$scope.formData.department_id=$scope.daily_production.items[0].department_id;
			setDepDetailsById($scope.formData.department_id);
			$scope.formData.schedDate = getMysqlFormat($scope.formData.prodDate);
			$scope.formData.prodUpTo=getMysqlFormat($scope.formData.produptoDate);


			var prod_time=$scope.daily_production.items[0].prod_time;
			$scope.Time=new Date(prod_time);
			$scope.formData.prod_time=$scope.daily_production.items[0].prod_time;

			$scope.prograssing = false;
		}, function(response) { 
			//$scope.prograssing = false;
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
				params : {id:$scope.formData.prodId,stockreg_id:$scope.formData.stock_reg_id,prod_no:$scope.formData.prod_no},
			}).then(function(response) {
				if(response.data != 0){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);

					$('#show_form').val(0);
					vm.dtInstance.reloadData(null, true);
					reloadData();
					$scope.show_table=true;$("#advsearchbox").show();
					$scope.show_form=false;
					manageButtons("add");
					$scope.disable_all = true;
					$scope.disable_all_shop=true;
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
		$scope.disable_all_shop=true;
		$scope.disable_code = true;
		$(".acontainer input").attr('disabled', true);
	}




	function setFormDatas(){

		$scope.formData.ref_no=$scope.REFNO;

		/*	$scope.formData.disposal_date=$("#disposal_date").val();

		$scope.formData.disposal_date = getMysqlFormat($scope.formData.disposal_date);
		//alert($scope.formData.disposal_date);

		for(var i = 0;i<$scope.invoice.items.length;i++){
			$scope.invoice.items[i].stock_item_batch_id = $scope.getItemmasterBatchId($scope.invoice.items[i].stock_item_id);
			$scope.invoice.items[i].amount = $scope.invoice.items[i].damaged_qty * $scope.invoice.items[i].rate;
			$scope.invoice.items[i].reason_type=($scope.invoice.items[i].reason_type!="")?$scope.invoice.items[i].reason_type:0;
		}
		$scope.formData.total_amount = $scope.total();*/


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
			if($scope.daily_production.items[0].prodnHdrId == 0 ||
					$scope.daily_production.items[0].prodnHdrId ==undefined ||
					$scope.daily_production.items[0].prodnHdrId ==null){
				$scope.formData ={};	
				$("#form_div_department_code").find(".acontainer input").val("");
				$scope.daily_production ={items: []};
				$scope.daily_production.items.push({stock_item_id:"",stock_item_code:"",stock_item_name: "",uomcode:"",schedule_qty:0,order_qty:0,totalOrder:0,prod_qty:0,
					itemRate:0,remarks:"",flag:0,prodnHdrId:0,hdrRemarks:"",sales_price:0,itemMaterialCost:0,otherCost:0,saleRate:0,totalCost:0,
					damageqty:0});
				$scope.formData.status=0;
				$scope.getRateDetails();
				var date4 = new Date();
				$scope.formData.prodDate = dateForm(date4);
				$scope.formData.produptoDate=dateForm(todayDate);
				//loadOrderData();
				
				if(settings['dailyprodview']==1)
				{
				
					
					$scope.slctypreq = 0;
					$scope.companyId = "";
					$scope.customerIds1="";

										
					$scope.loadOrderShopwiseData();
				}
				else
				{
				loadOrderData();
				}



			}else{
				var dataIndex = vm.dtInstance.DataTable.rows();
				var row_data = vm.dtInstance.DataTable.rows(dataIndex[0][cur_row_index]).data();
				edit(row_data[0],cur_row_index);
			}
			clearform();
		});

	});

	$rootScope.$on("fun_enable_inputs",function(){
		$('#show_form').val(1);
		$scope.disable_all=false;
		
		$scope.disable_all_shop=true;

		

		$(".acontainer input").attr('disabled', false);
		$scope.showFinalize=false;
		$("#items_table tr:nth-child(01)  td:nth-child(1)").find(".acontainer input").focus();

	});

	$scope.stockdata=1;
	$rootScope.$on("fun_clear_form",function(){
		$("#form_div_shop").find("select").select();

		fun_get_refno();
		$scope.formData.status=0;
		$scope.getRateDetails();
		$scope.daily_production ={items: []};
		
		$scope.daily_production.items.push({stock_item_id:"",stock_item_code:"",stock_item_name: "",uomcode:"",schedule_qty:0,order_qty:0,totalOrder:0,prod_qty:0,
			itemRate:0,remarks:"",flag:0,prodnHdrId:0,hdrRemarks:"",sales_price:0,itemMaterialCost:0,otherCost:0,saleRate:0,totalCost:0,
			damageqty:0});
		var date4 = new Date();
		$scope.formData.prodDate = dateForm(date4);
		$scope.formData.produptoDate=dateForm(todayDate);
		$scope.formData.schedDate=getMysqlFormat($scope.formData.prodDate);
		
		
		$scope.formData.prodUpTo=getMysqlFormat($scope.formData.produptoDate);
		$scope.showFinalize=false;

		$('#btnDelete').show();
		$('#btnEdit').show();
		$scope.prodStatus=true;
		$scope.Time=dt;
		$("#form_div_date").removeClass("has-error");
		$("#form_div_scheduledate_error").hide();
		$scope.disable_all_shop=false;

		
		//loadOrderData();
		
		if(settings['dailyprodview']==1)
		{

			$scope.slctypreq = 0;
			$scope.companyId = "";
			$scope.customerIds1="";
			
			$timeout(function () {				
				$scope.loadOrderShopwiseData();

			}, 1);

		}
		else
		{
		loadOrderData();
		}

	});

	$scope.filterTimes=function()
	{		
		return filterTime();
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





	$scope.alert_for_codeexisting = function(flg){
		if(flg==0){
		}else if(flg == 1){
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.CODE_EXISIT);
		}

	}
	$scope.alert_for_codeexisting2 = function(flg){
		if(flg == 1){
			$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal','code Exist');
		}
	}

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

	$scope.clear_stock_details_editmode =  function(event){
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].stock_item_id = "";
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].stock_item_code = "";
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].stock_item_name = "";
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].uomcode ="";
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].schedule_qty =0;
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].order_qty =0;
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].totalOrder =0;
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].prod_qty =0;
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].itemRate =0;
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].itemMaterialCost=0;
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].totalCost=0
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].saleRate=0;
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].otherCost=0;
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].remarks ="";
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].flag =0;
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].sales_price =0;



	}
	$scope.clear_stock_details_Departmode=  function(event){
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].department_id = "";
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].source_name = "";
		$scope.daily_production.items[event.currentTarget.parentElement.rowIndex-1].source_code = "";
	}

	$scope.addProdCostRow = function(index) {

		if($scope.disable_all == false){
			if($scope.prodCostList.length >=1){
				if($scope.prodCostList[$scope.prodCostList.length-1].prod_cost_id=="")
				{

					$("#prodCost tbody tr:nth-child("+(index+2)+") td:nth-child("+(2)+")").find(".acontainer input").focus();

				}
				else if($scope.prodCostList[$scope.prodCostList.length-1].rate=="")
				{

					$("#prodCost tbody tr:nth-child("+(index)+") td:nth-child("+(5)+")").find("#cost_rate").focus();

				}
				else
				{
					$scope.prodCostList.push({id : "",prod_cost_id :"",prod_cost_code:"",prod_cost_name:"",prod_cost_type:"",isPercentage: false,rate:""});

				}


			}
			else
			{
				$scope.prodCostList.push({id : "",prod_cost_id :"",prod_cost_code:"",prod_cost_name:"",prod_cost_type:"",isPercentage: false,rate:"",flag1:1});

			}


		}

	}
	$scope.removeProdCostRow = function(index) {
		if($scope.disable_all == false){

			if($scope.prodCostList.length==1)
			{	
				if(index==0)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.TABLE_ERR);

				}
			}
			else
			{
				$scope.prodCostList.splice(index, 1);

			}
		}
	}
	$scope.clear_cost_details_editmode =  function(event){
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].id = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].prod_cost_id = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].prod_cost_code = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].prod_cost_name = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].prod_cost_type ="";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].isPercentage =false;
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex-1].rate ="";	
	}
	var prodcost_data = [];
	$scope.prodcost_data=[];

	$http({	url : '../prodcost/costTypeList',	method : "GET",async:false,
	}).then(function(response) {
		prodcost_data = response.data.data;
		$scope.prodcost_data=response.data.data;

	}, function(response) { // optional
	});

	$scope.getSaleRate1=function(itemId)
	{
		var price1 =0;
		for(var i=0;i<$scope.rateDetails.length;i++){
			if($scope.rateDetails[i].stock_item_id == itemId){
				price1= $scope.rateDetails[i].selling_price;
				break;
			}
		}
		return price1;
	}

	$scope.getRateDetails=function()
	{
		$http({	url : 'getItemRates',	method : "GET",async:false,
		}).then(function(response) {
			$scope.rateDetails = response.data.rateDetails;

		}, function(response) { // optional
		});
	}
	$scope.getMaterialAndOtherCost = function(itemid){

		$scope.costMatrlOther =[];
		for(var i=0;i<$scope.costData.length;i++){
			if($scope.costData[i].stock_id == itemid){
				$scope.costMatrlOther[0]=$scope.costData[i].material_cost;
				$scope.costMatrlOther[1]=$scope.costData[i].other_cost;
				break;
			}else
			{
				$scope.costMatrlOther[0]="";
				$scope.costMatrlOther[1]="";
			}
		}

		for(var i=0;i<$scope.costMatrlOther.length;i++)
		{
			$scope.costMatrlOther[i]=($scope.costMatrlOther[i]=="") ? 0 :$scope.costMatrlOther[i];
			$scope.costMatrlOther[i]= parseFloat($scope.costMatrlOther[i]).toFixed(settings['decimalPlace']);
		}
		return $scope.costMatrlOther;
	}  

	$scope.setTotalCost = function(item){
		var totalCost = 0.00;
		//taxAmtTotal = (item.unit_price * (item.tax_pc/100) * item.received_qty) + (item.received_qty * item.unit_price);

		totalCost = parseFloat(item.itemMaterialCost) + parseFloat(item.otherCost);

		return parseFloat(totalCost).toFixed(settings['decimalPlace']);
	}
	$scope.total= function(){
		var total = 0.00;

		angular.forEach($scope.daily_production.items, function(item) {

			if(item.prod_qty !="" && item.prod_qty !=undefined)
			{
				total += ((parseFloat(item.itemMaterialCost)+parseFloat(item.otherCost))*parseFloat(item.prod_qty));
			}else
			{
				total=0;
			}

		})

		return parseFloat(total).toFixed(settings['decimalPlace']);
	}
	$scope.netSalesAmount=function()
	{
		var netSalesAmt=0.00;
		angular.forEach($scope.daily_production.items, function(item) {
			//if(item.isDeleted==false){
			if(item.prod_qty !="" && item.prod_qty !=undefined && (item.saleRate !='' && item.saleRate != undefined)){
				netSalesAmt += (parseFloat(item.saleRate)*parseFloat(item.prod_qty));
			}else
			{
				netSalesAmt=0;
			}
			//}  
		})
		return parseFloat(netSalesAmt).toFixed(settings['decimalPlace']);

	}
	$scope.netDamageAmount=function()
	{
		var netDamageAmount=0.00;
		angular.forEach($scope.daily_production.items, function(item) {
			//if(item.isDeleted==false){
			if(item.damageqty !="" && item.damageqty !=undefined ){
				netDamageAmount += ((parseFloat(item.itemMaterialCost)+parseFloat(item.otherCost))*parseFloat(item.damageqty));
			}else
			{
				netDamageAmount=0;
			}
			//}  
		})
		return parseFloat(netDamageAmount).toFixed(settings['decimalPlace']);

	}

	$scope.netEstProfit=function()
	{
		var netEstmtdProfit=0.00;
		if($scope.netSalesAmount()>$scope.total())
		{
			netEstmtdProfit =parseFloat($scope.netSalesAmount())-parseFloat($scope.total());
		}
		return parseFloat(netEstmtdProfit).toFixed(settings['decimalPlace']);
	}
	$scope.setTotalCost = function(item){
		var totalCost = 0.00;
		//taxAmtTotal = (item.unit_price * (item.tax_pc/100) * item.received_qty) + (item.received_qty * item.unit_price);

		totalCost = parseFloat(item.itemMaterialCost) + parseFloat(item.otherCost);

		return parseFloat(totalCost).toFixed(settings['decimalPlace']);
	}
	$scope.setStockValue=function(item)
	{
		var stockVal=0.00;
		if(item.prod_qty != "" && item.prod_qty!=undefined )
		{
			stockVal=(parseFloat(item.itemMaterialCost) + parseFloat(item.otherCost))* parseFloat(item.prod_qty);
		}
		else
		{
			stockVal=0;
		}
		return parseFloat(stockVal).toFixed(settings['decimalPlace']);
	}
	$scope.setSaleValue=function(item)
	{
		var saleVal=0.00;
		if(item.prod_qty != "" && item.prod_qty!=undefined && item.saleRate !="" && item.saleRate !=undefined)
		{
			saleVal=parseFloat(item.saleRate)*parseFloat(item.prod_qty);
		}else
		{
			saleVal=0;
		}
		return parseFloat(saleVal).toFixed(settings['decimalPlace']);
	}
	$scope.getDamageAmount=function(item)
	{
		var amount=0.00;
		amount=parseFloat(item.damageqty)*(parseFloat(item.itemMaterialCost) + parseFloat(item.otherCost));
		return parseFloat(amount).toFixed(settings['decimalPlace']);
	}
	$scope.setEstProfit=function(item)
	{
		var estProfit=0.00;
		var saleVal=$scope.setSaleValue(item);
		var stockVal=$scope.setStockValue(item);

		estProfit=$scope.setSaleValue(item)-$scope.setStockValue(item);

		return parseFloat(estProfit).toFixed(settings['decimalPlace']);

	}
	$scope.tableClicked = function (index) {
		$scope.table_itemsearch_rowindex= index;};

		$scope.addItem = function(index) {

			if($scope.disable_all == false){
				if(index<$scope.daily_production.items.length-1){
					$("#items_table tbody tr:nth-child("+(index+2)+") td:nth-child("+(1)+")").find(".acontainer input").focus();
				}else{
					if($scope.daily_production.items.length != 0){
						if($scope.daily_production.items[$scope.daily_production.items.length-1].stock_item_id != "" && $scope.daily_production.items[$scope.daily_production.items.length-1].stock_item_code != ""){


							if($scope.daily_production.items[$scope.daily_production.items.length-1].prod_qty == undefined || $scope.daily_production.items[$scope.daily_production.items.length-1].prod_qty==""
								|| $scope.daily_production.items[$scope.daily_production.items.length-1].prod_qty == 0 ||
								$scope.daily_production.items[$scope.daily_production.items.length-1].prod_qty.split(".")[0].length>13)
							{

								$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Production Qty");
								$("#items_table tbody tr:nth-child("+(index+1)+")").find("#prod_qty").select();

							}
							else {
								$scope.daily_production.items.push({stock_item_id:"",stock_item_code:"",stock_item_name: "",uomcode:"",schedule_qty:0,order_qty:0,totalOrder:0,prod_qty:0,
									itemRate:0,remarks:"",flag:0,prodnHdrId:0,hdrRemarks:"",sales_price:0,
									itemMaterialCost:0,otherCost:0,saleRate:0,totalCost:0,damageqty:0});
								/*	$timeout(function () {				
								$("#items_table tbody tr:nth-child("+(index+2)+") td:nth-child(1)").find(".acontainer input").select();
						}, 1);*/
							}
						}
						else{		    					
							$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);

							/*	$("#items_table tbody tr:nth-child("+(index+1)+")").find(".acontainer input").focus();*/
							$("#items_table tr:nth-child("+(index+1)+")  td:nth-child(1)").find(".acontainer input").focus();
							//$(".acontainer").find("input").focus();
							if($scope.daily_production.items[$scope.daily_production.items.length-1].stock_item_id == "" && $scope.daily_production.items[$scope.daily_production.items.length-1].stock_item_code == ""){
								$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
								//$(".acontainer").find("input").focus();
							}	

						}

					}	
					else if($scope.daily_production.items.length == 0){
						$scope.daily_production.items.push({stock_item_id:"",stock_item_code:"",stock_item_name: "",uomcode:"",schedule_qty:0,order_qty:0,totalOrder:0,prod_qty:0,
							itemRate:0,remarks:"",flag:0,prodnHdrId:0,hdrRemarks:"",sales_price:0,itemMaterialCost:0,otherCost:0,
							saleRate:0,totalCost:0,damageqty:0});



					}
				}}
		}


		$scope.removeItem = function(index,event) {
			if($scope.disable_all == false){

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


					if(index == 0 && $scope.daily_production.items.length==1){
						$rootScope.$broadcast('on_AlertMessage_ERR',"Atleast one item is required.");
						$("#items_table tbody tr:nth-child(1) td:nth-child("+(1)+")").find(".acontainer input").focus();
					}
					else 
					{
						$scope.daily_production.items.splice(index, 1);
						$("#items_table tbody tr:nth-child("+(index)+")").find("#prod_qty").focus();
					}

				});

			}

		}

		$scope.dateChange=function()
		{
			/*if(($scope.daily_production.items[0].prodnHdrId==0||$scope.daily_production.items[0].prodnHdrId==undefined
					|| $scope.daily_production.items[0].prodnHdrId==null) && 
					process($scope.formData.prodDate) != 'Invalid Date'
						&& dt > process($scope.formData.prodDate))
		*/
			if(process($scope.formData.prodDate) != 'Invalid Date'	&& dt > process($scope.formData.prodDate))
						
						
						
			{
				
				if(settings['dailyprodview']==1)
				{
				
					$scope.loadOrderShopwiseData();
				}
				else
				{
				loadOrderData();
				}
			}
			
			 if(dt < process($scope.formData.prodDate)){
				$rootScope.$broadcast('on_AlertMessage_ERR',"Production Date Is Not Valid");
				$('#scheduledate').focus();
				flg = false;
			}
			else if(process($scope.formData.prodDate) == 'Invalid Date')
			{
				$rootScope.$broadcast('on_AlertMessage_ERR',"Enter valid production date");
				$('#scheduledate').focus();

			}
			 if(process($scope.formData.produptoDate) == 'Invalid Date')
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Enter valid production upto date");
					$('#scheduledate').focus();

				}
			 
			
			
		}
		$scope.is_pendingArray=[];
		
		$scope.getShopData=function()
		{
			$scope.loadOrderShopwiseData();	
		}
		$scope.getCustomerData1=function()
		{
			$scope.loadOrderShopwiseData();
			
		}
		
		
		
		$scope.loadOrderShopwiseData=function()
		{
			var customerID=($scope.slctypreq==0)?$scope.companyId:$scope.customerIds1;
		
		if( $scope.formData.prodDate!="" && $scope.formData.prodDate!=undefined && customerID!=undefined)
			
		{	
			 $("#form_div_shop").removeClass("has-error");
		      $("#form_div_shop_error").hide();
			 $("#form_div_cust").removeClass("has-error");
		        $("#form_div_cust_error").hide();
			$("#form_div_date").removeClass("has-error");
			$("#form_div_scheduledate_error").hide();
			
			
			var productionDate;
			productionDate= {
					productionDate : getMysqlFormat($scope.formData.produptoDate),
					prod_date:getMysqlFormat($scope.formData.prodDate),
					customerID:($scope.slctypreq==0)?$scope.companyId:$scope.customerIds1,
					request_type:$scope.slctypreq,
			};

			$http({
				url : 'getProductionOrderDataShopwise',
				async : false,
				params : productionDate,
				method : "GET",
			}).then(function(response) {
				$scope.daily_production.items ={items: []};
				$scope.orderArrayAndBalanceData=response.data.orderArray;
				$scope.is_pendingArray=response.data.is_pending;
				//$scope.daily_production.items=angular.copy($scope.orderArrayAndBalanceData);
				$scope.salesOrderHdrIds=response.data.orderHdrIds;
				//setItemsData(response.data.orderArray);
				if($scope.is_pendingArray.length==0 )
					{
				if(response.data.orderArray.length>0)
				{
					var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
						var $dialog = angular.element(document.querySelector('md-dialog'));
						var $actionsSection = $dialog.find('md-dialog-actions');
						var $cancelButton = $actionsSection.children()[0];
						var $confirmButton = $actionsSection.children()[1];
						angular.element($confirmButton).removeClass('md-focused');
						angular.element($cancelButton).addClass('md-focused');
						$cancelButton.focus();
					}}).title("Do You Want To Load The Pending Orders On "+$scope.formData.prodDate+"?").targetEvent(event).cancel('No').ok(
					'Yes')

					;

					$mdDialog.show(confirm).then(function() {
						$scope.daily_production.items=angular.copy($scope.orderArrayAndBalanceData);
						setItemsData(response.data.orderArray);
						$scope.formData.status=0;
						$scope.formData.id=null;
						clearDepData();


					},function() {

						$scope.daily_production.items=[];
						$scope.daily_production.items.push({stock_item_id:"",stock_item_code:"",stock_item_name: "",uomcode:"",
							schedule_qty:0,order_qty:0,totalOrder:0,prod_qty:0,itemRate:0,remarks:"",flag:0,prodnHdrId:0,hdrRemarks:"",sales_price:0
							,itemMaterialCost:0,otherCost:0,saleRate:0,totalCost:0,damageqty:0});
						$("#form_div_source_code .acontainer input").attr('disabled',false);
						$("#depId1 .acontainer input").attr('disabled',false);
						clearDepData();
						$scope.formData.remarks="";
						$scope.formData.id=null;
						$scope.formData.status=0;   
						$timeout(function () {				
							$("#items_table tbody tr:nth-child(1) td:nth-child("+(1)+")").find(".acontainer input").select();
						}, 1);




					});


				}else
				{
					// optional
					$scope.daily_production.items=[];
					$scope.daily_production.items.push({stock_item_id:"",stock_item_code:"",stock_item_name: "",uomcode:"",
						schedule_qty:0,order_qty:0,totalOrder:0,prod_qty:0,itemRate:0,remarks:"",flag:0,prodnHdrId:0,hdrRemarks:"",sales_price:0
						,itemMaterialCost:0,otherCost:0,saleRate:0,totalCost:0,damageqty:0});
					$("#form_div_source_code .acontainer input").attr('disabled',false);
					$("#depId1 .acontainer input").attr('disabled',false);
					clearDepData();
					$scope.formData.remarks="";
					$scope.formData.id=null;
					$scope.formData.status=0;   
					$timeout(function () {				
						$("#items_table tbody tr:nth-child(1) td:nth-child("+(1)+")").find(".acontainer input").select();
					}, 1);



				}
			}
				else
					{
					
					
					
				if(productionDate.customerID!='' && productionDate.customerID!=undefined)
						{
				$rootScope.$broadcast('on_AlertMessage_ERR',"Please finalize pending production! ");

					$scope.show_addForm =false;
					$scope.show_table = true;
					$scope.show_form =false;
					manageButtons("add");
					/*$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Please finalize pending production! ")
							.ok('Ok!')
							.targetEvent(event)
					);*/
						}
				
					}

			}

			, function(itemresponse) {

			});

			
			
			
			
		}
		else
			{
			
			if(dt < process($scope.formData.prodDate)){
				$rootScope.$broadcast('on_AlertMessage_ERR',"Production Date Is Not Valid");
				$('#scheduledate').focus();
				
			}  if(process($scope.formData.prodDate) == 'Invalid Date')
			{
				
				$("#form_div_date").addClass("has-error");
				$("#form_div_scheduledate_error").show();
				
			}
			
			if(process($scope.formData.prodDate) != 'Invalid Date')
			{
				
				$("#form_div_date").removeClass("has-error");
				$("#form_div_scheduledate_error").hide();
			}
			
			
			
			
			
			
			
			
			
			
			}
		
		}
		
		

		function loadOrderData()
		{

			var productionDate;
			productionDate= {
					productionDate : getMysqlFormat($scope.formData.prodDate)
			};
			$http({
				url : 'getProductionOrderData',
				async : false,
				params : productionDate,
				method : "GET",
			}).then(function(response) {
				$scope.daily_production.items ={items: []};
				$scope.orderArrayAndBalanceData=response.data.orderArray;
				$scope.daily_production.items=angular.copy($scope.orderArrayAndBalanceData);
				$scope.salesOrderHdrIds=response.data.orderHdrIds;
				$scope.is_pendingArray=response.data.is_pending;
				setItemsData(response.data.orderArray);
				if($scope.is_pendingArray.length==0 )
				{
				if($scope.daily_production.items.length>0)
				{
					var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
						var $dialog = angular.element(document.querySelector('md-dialog'));
						var $actionsSection = $dialog.find('md-dialog-actions');
						var $cancelButton = $actionsSection.children()[0];
						var $confirmButton = $actionsSection.children()[1];
						angular.element($confirmButton).removeClass('md-focused');
						angular.element($cancelButton).addClass('md-focused');
						$cancelButton.focus();
					}}).title("Do You Want To Load The Pending Orders On "+$scope.formData.prodDate+"?").targetEvent(event).cancel('No').ok(
					'Yes')

					;

					$mdDialog.show(confirm).then(function() {

						$scope.formData.status=0;
						$scope.formData.id=null;
						clearDepData();


					},function() {

						$scope.daily_production.items=[];
						$scope.daily_production.items.push({stock_item_id:"",stock_item_code:"",stock_item_name: "",uomcode:"",
							schedule_qty:0,order_qty:0,totalOrder:0,prod_qty:0,itemRate:0,remarks:"",flag:0,prodnHdrId:0,hdrRemarks:"",sales_price:0
							,itemMaterialCost:0,otherCost:0,saleRate:0,totalCost:0,damageqty:0});
						$("#form_div_source_code .acontainer input").attr('disabled',false);
						$("#depId1 .acontainer input").attr('disabled',false);
						clearDepData();
						$scope.formData.remarks="";
						$scope.formData.id=null;
						$scope.formData.status=0;   
						$timeout(function () {				
							$("#items_table tbody tr:nth-child(1) td:nth-child("+(1)+")").find(".acontainer input").select();
						}, 1);




					});


				}else
				{
					// optional
					$scope.daily_production.items=[];
					$scope.daily_production.items.push({stock_item_id:"",stock_item_code:"",stock_item_name: "",uomcode:"",
						schedule_qty:0,order_qty:0,totalOrder:0,prod_qty:0,itemRate:0,remarks:"",flag:0,prodnHdrId:0,hdrRemarks:"",sales_price:0
						,itemMaterialCost:0,otherCost:0,saleRate:0,totalCost:0,damageqty:0});
					$("#form_div_source_code .acontainer input").attr('disabled',false);
					$("#depId1 .acontainer input").attr('disabled',false);
					clearDepData();
					$scope.formData.remarks="";
					$scope.formData.id=null;
					$scope.formData.status=0;   
					$timeout(function () {				
						$("#items_table tbody tr:nth-child(1) td:nth-child("+(1)+")").find(".acontainer input").select();
					}, 1);



				}
				
			}	
				
				else
				{
				
				
				
			
			$rootScope.$broadcast('on_AlertMessage_ERR',"Please finalize pending production! ");

				$scope.show_addForm =false;
				$scope.show_table = true;
				$scope.show_form =false;
				manageButtons("add");
				/*$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Please finalize pending production! ")
						.ok('Ok!')
						.targetEvent(event)
				);*/
					
			
				}
	


			}

			, function(itemresponse) {

			});

		}
		//Save function 
		$scope.save_data=function(event){
			if (form_validation($scope.formData))
			{
				for( var i=0;i<$scope.daily_production.items.length;i++ ){
					$scope.daily_production.items[i].totalOrder=$scope.getTotal($scope.daily_production.items[i]);
					$scope.daily_production.items[i].totalAmt=$scope.getTotalItemAmount($scope.daily_production.items[i]);
					$scope.daily_production.items[i].stock_value=$scope.setStockValue($scope.daily_production.items[i]);
					$scope.daily_production.items[i].sales_value=$scope.setSaleValue($scope.daily_production.items[i]);
					$scope.daily_production.items[i].damage_value=$scope.getDamageAmount($scope.daily_production.items[i]);
				}

				
				$scope.formData.totalDamageValue=$scope.netDamageAmount();
				if($scope.daily_production.items.length > 0)
				{
					$scope.formData.stockreg_id=$scope.daily_production.items[0].stckRegHdrId;
				}
				$scope.formData.stock_reg_id=$scope.daily_production.items[0].stckRegHdrId;
				for(var i=0;i<$scope.daily_production.items.length;i++)
				{
					$scope.daily_production.items[i].totalCost=parseFloat(parseFloat($scope.daily_production.items[i].itemMaterialCost)+
							parseFloat($scope.daily_production.items[i].otherCost)).toFixed(settings['decimalPlace']);
				}
				$scope.formData.dailyPlanningDetailLists=JSON.stringify($scope.daily_production.items);
				$scope.prograssing = true;
				$scope.formData.prod_no=$scope.REFNO;
				var custIdSave=-1;
				if(settings['dailyprodview']==1)
				{
					custIdSave=($scope.slctypreq==0)?$scope.companyId:$scope.customerIds1;
				}
				
					
				$scope.formData.schedDate = getMysqlFormat($scope.formData.prodDate);
				$scope.formData.prodUpTo=getMysqlFormat($scope.formData.produptoDate);

				$scope.formData.orderHdrsIdsList=JSON.stringify($scope.salesOrderHdrIds);
				$http({
					url : 'saveDailyProduction',
					method : "POST",
					params:{custId:custIdSave},
					data:$scope.formData,
				}).then(function(response) {

					if(response.data!=0){

						if($scope.daily_production.items[0].prodnHdrId != 0 && $scope.daily_production.items[0].prodnHdrId !=undefined)
						{

							$scope.disable_all_shop=true;
							$scope.disable_all=true;
							$(".acontainer input").attr('disabled', true);
							$("#form_div_source_code .acontainer input").attr('disabled',true);
							$("#depId1 .acontainer input").attr('disabled',true);
							if( $scope.formData.status==1){
								$('#btnDelete').hide();
								$('#btnEdit').hide();
								$scope.prodStatus=false;
								/*$scope.disable_btn=true;*/
								$scope.prograssing = false; 
							}
							else{
								$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
								view_mode_aftr_edit();
								$scope.disable_btn=false;
								$scope.showFinalize=true;
								$scope.prograssing =false;
							}


						}else{
							$scope.daily_production.items[0].prodnHdrId=response.data.prodId;
							$scope.formData.prodId=response.data.prodId;
							$scope.formData.stock_reg_id=response.data.stock_reg_id;
							$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);	

							$scope.prograssing =false;

							$scope.formData ={};
							fun_get_refno();
							$scope.formData.status=0;
							$scope.getRateDetails();
							$scope.daily_production ={items: []};
							$scope.daily_production.items.push({stock_item_id:"",stock_item_code:"",stock_item_name: "",uomcode:"",schedule_qty:0,order_qty:0,totalOrder:0,prod_qty:0,
								itemRate:0,remarks:"",flag:0,prodnHdrId:0,hdrRemarks:"",sales_price:0,itemMaterialCost:0,otherCost:0,saleRate:0,totalCost:0,
								damageqty:0});
							var date4 = new Date();
							$scope.formData.prodDate = dateForm(date4);
							$scope.formData.produptoDate= dateForm(date4);


						}
						reloadData();
					}else
					{

						$mdDialog.show($mdDialog.alert()
								.parent(angular.element(document.querySelector('#dialogContainer')))
								.clickOutsideToClose(true)
								.textContent("Save failed.")
								.ok('Ok!')
								.targetEvent(event)
						);
						//$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
						$scope.prograssing =false;
					}
				}, function(response) { // optional
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Save failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
					//$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
					$scope.prograssing =false;

				});


			}
		}
		$rootScope.$on('fun_save_data',function(event){		
			$scope.save_data();
		});

		function clearDepData()
		{
			$scope.formData.department_id="";
			$scope.formData.source_code="";
			$scope.formData.source_name="";
			$("#form_div_source_code").find(".acontainer input").val("");
		}
		function setItemsData(items)
		{
			for(var i=0;i<items.length;i++)
			{
				/*$scope.daily_production.items[i].department_id=response.data.scheduledProdData[i].id;
		$scope.daily_production.items[i].source_code =  response.data.scheduledProdData[i].code;
		$scope.daily_production.items[i].source_name=  response.data.scheduledProdData[i].name;
				 */setDepDetailsByIdRow(items[i].department_id,i);
				 $scope.daily_production.items[i].order_qty = parseFloat(items[i].order_qty).toFixed(settings['decimalPlace']);
				 $scope.daily_production.items[i].prod_qty = parseFloat(items[i].prod_qty).toFixed(settings['decimalPlace']);
				 $scope.daily_production.items[i].itemRate = parseFloat(items[i].itemRate).toFixed(settings['decimalPlace']);
				 $scope.daily_production.items[i].totalAmt=parseFloat(items[i].totalAmt).toFixed(settings['decimalPlace']);
				 $scope.daily_production.items[i].sales_price=parseFloat(items[i].sales_price).toFixed(settings['decimalPlace']);
				 $scope.daily_production.items[i].damageqty=parseFloat(items[i].damageqty).toFixed(settings['decimalPlace']);
			}

		}

		
		//validation:
		function form_validation(data){

			var flg = true;

			/*if(dt < process($scope.formData.prodDate)){
				$rootScope.$broadcast('on_AlertMessage_ERR',"Production Date Is Not Valid");
				$('#scheduledate').focus();
				flg = false;
			}else  if(process($scope.formData.prodDate) == 'Invalid Date')
			{
				$("#form_div_date").addClass("has-error");
				$("#form_div_scheduledate_error").show();
				flg =  false;
			}*/
			  if(process($scope.formData.produptoDate) == 'Invalid Date')
			{
				$("#form_div_date").addClass("has-error");
				$("#form_div_scheduledate_error").show();
				flg =  false;
			}
			else if(settings['dailyprodview']==1)
			 {
				if($scope.slctypreq == 0 && ($scope.companyId=="" || $scope.companyId==undefined ))
					{
					 $("#form_div_shop").addClass("has-error");
				     $("#form_div_shop_error").show();
				     flg = false;
								 
			       }
			     else{
			    	 $("#form_div_shop").removeClass("has-error");
				      $("#form_div_shop_error").hide();
			
			           }
				 if($scope.slctypreq == 1 && ($scope.customerIds1=="" || $scope.customerIds1==undefined ))
					{
					 $("#form_div_cust").addClass("has-error");
				     $("#form_div_cust_error").show();
				     flg = false;

					
					}
				 else
					 {
					 $("#form_div_cust").removeClass("has-error");
				        $("#form_div_cust_error").hide();

					 }
				
				
				}
			
			/*if(process($scope.formData.prodDate) != 'Invalid Date')
			{
				$("#form_div_date").removeClass("has-error");
				$("#form_div_scheduledate_error").hide();
			}*/
			if(process($scope.formData.produptoDate) != 'Invalid Date')
			{
				$("#form_div_date").removeClass("has-error");
				$("#form_div_scheduledate_error").hide();
			}
			

			if($scope.daily_production.items.length > 0){
				$scope.item_table_empty=[];
				$scope.item_details_table_empty=[];

				for(var i = 0;i<$scope.daily_production.items.length;i++){

					if($scope.daily_production.items[i].stock_item_id != "" && $scope.daily_production.items[i].stock_item_code != ""){
						if($scope.daily_production.items[i].prod_qty == "" || $scope.daily_production.items[i].prod_qty == undefined
								|| $scope.daily_production.items[i].prod_qty==0
								|| $scope.daily_production.items[i].prod_qty.split(".")[0].length>13){

							$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Production Qty");
							$("#items_table tbody tr:nth-child("+(i+1)+")").find("#prod_qty").select();
							flg = false;
							$scope.item_details_table_empty.push($scope.daily_production.items[i]);

							break;

						}else{

							if($scope.daily_production.items[i].source_code == "" || $scope.daily_production.items[i].source_code == undefined){
								$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Department");
								$("#items_table tr:nth-child("+(i+1)+")  td:nth-child(3)").find(".acontainer input").focus();
								$scope.item_details_table_empty.push($scope.daily_production.items[i]);
								flg = false;
								break;
							}
						}
						if($scope.daily_production.items[i].saleRate == "" || $scope.daily_production.items[i].saleRate == undefined
								|| $scope.daily_production.items[i].saleRate==0
								|| $scope.daily_production.items[i].saleRate.split(".")[0].length>9){
							$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Sale rate");
							$("#items_table tbody tr:nth-child("+(i+1)+")").find("#sale_rate").focus();
							flg = false;								
							break;

						}
						if($scope.setEstProfit($scope.daily_production.items[i]) < 0)
						{

							$rootScope.$broadcast('on_AlertMessage_ERR',"Sale Rate Cannot Be Less Than Cost Rate");
							$("#items_table tbody tr:nth-child("+(i+1)+")").find("#sale_rate").focus();
							$scope.item_details_table_empty.push($scope.daily_production.items[i]);
							flg = false;
							break;
						}

						else if(parseFloat($scope.daily_production.items[i].damageqty) > parseFloat($scope.daily_production.items[i].prod_qty))
						{
							$rootScope.$broadcast('on_AlertMessage_ERR',"Damage Qty Cannot Be Greater Than Production");
							//$("#items_table tbody tr:nth-child("+(i+1)+")").find("#damageqty").focus();
							$scope.item_details_table_empty.push($scope.daily_production.items[i]);
							flg = false;
							break;
						}


					}else{
						$("#items_table tr:nth-child("+(i+1)+")  td:nth-child(1)").find(".acontainer input").focus();
						flg=false;
						$scope.item_table_empty.push($scope.daily_production.items[i]);
						$rootScope.$broadcast('on_AlertMessage_ERR',"Please Select An Item");
						break;
					}
					/*});*/
				}

			}
			else if($scope.daily_production.items.length == 0){
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



		$scope.getTotal=function(item)
		{

			var amount=0.00;
			amount = parseFloat(item.schedule_qty) + parseFloat(item.order_qty);
			return parseFloat(amount).toFixed(settings['decimalPlace']);
		}
		$scope.getTotalItemAmount=function(item)
		{

			var amount=0.00;
			if(item.prod_qty !=undefined && item.itemRate !=undefined && item.prod_qty !="" && item.itemRate !="")
			{
				amount = parseFloat(item.prod_qty) * parseFloat(item.itemRate);
				return parseFloat(amount).toFixed(settings['decimalPlace']);}
			else return 0;

		}

		function reloadData() {
			vm.dtInstance.reloadData(null, true);


		}

		$scope.elementid=function(elemenvalue){
			if($scope.disable_all==true){$(elemenvalue).attr("disabled", true);}}

		$scope.editDamageQty=function(index)
		{
			if($scope.disable_all == false)
			{
				$scope.damageqty=$scope.daily_production.items[index].damageqty;
				$scope.currentDamageIndex = index;
				$('#damageQtyPopup').modal('toggle');

				/*$timeout(function () {		*/		
				$("#damageqty1").select();
				/*}, 1);*/
			}
		}
		$scope.addDamageQty=function()
		{
			if($scope.damageqty != '' && $scope.damageqty !=undefined && $scope.damageqty !=null)
			{
				$scope.daily_production.items[$scope.currentDamageIndex].damageqty=$scope.damageqty;
			}else
			{
				$scope.daily_production.items[$scope.currentDamageIndex].damageqty=0;
			}
		}
		$(document).keyup(function(e) {
			if(e.keyCode==13 && $(e.target.outerHTML)[0].id == "damageqty1"){
				$('.modal').modal('hide');
			}
		});
		$('#damageQtyPopup').on('shown.bs.modal', function () {
			$('#damageqty1').focus();
		}) ;

		$scope.showCostDataDetails=function(index,item)
		{
			/*if($scope.disable_all == false)
				{*/
			if(item.stock_item_id !=undefined && item.stock_item_id !="")
			{
				$('#costDataSplit').modal('toggle');	
				$scope.materialCost1=$scope.daily_production.items[index].itemMaterialCost;
				$scope.otherCost1=$scope.daily_production.items[index].otherCost;
				$scope.showTotalCost=$scope.setTotalCost(item);
			}else
			{
				$rootScope.$broadcast('on_AlertMessage_ERR',"Please Select An Item");
			}
			// }
		}

		$scope.showShopwiseOrderDetails=function(index,item)
		{
			/*
			
				
				if(settings['dailyprodview']==0)
				{
				
				var data = JSON.stringify({
					orderData1 : $scope.salesOrderHdrIds		
				});

				var fdata = new FormData();
				fdata.append("data", data);
				$http.post("getShopWiseSplitData", fdata, {
					transformRequest : angular.identity,
					params:{stkId:item.stock_item_id,date:getMysqlFormat($scope.formData.prodDate)},
					headers : {
						'Content-Type' : undefined
					}
				}).success(
						function(response) {
							console.log(response.currentdayPrevBalance);
							$scope.orderSplitData = response.orderSplitData;
							if($scope.orderSplitData.length !=0)
							{
							//$scope.previousBalance=$scope.orderSplitData[0].balanceqty;
								var qty=0.0;
								for(var i=0;i<$scope.orderSplitData.length;i++)
									{
									qty+=parseFloat($scope.orderSplitData[i].orderqty);
									}
								$scope.previousBalance=parseFloat((item.order_qty-qty)).toFixed(settings['decimalPlace'])<0
								?$scope.previousBalance=parseFloat((0)).toFixed(settings['decimalPlace']):
									parseFloat((item.order_qty-qty)).toFixed(settings['decimalPlace']);
								
							}else
								{
								$scope.previousBalance=parseFloat((0)).toFixed(settings['decimalPlace']);
								}
							if(item.stock_item_id !=undefined && item.stock_item_id !="")
							{
								if(item.order_qty>0)
								{
									$('#orderDataSplit').modal('toggle');
								}else
								{
									$rootScope.$broadcast('on_AlertMessage_ERR',"No Sales Order Data Available For this Item");
								}

							}else
							{
								$rootScope.$broadcast('on_AlertMessage_ERR',"Please Select An Item");
							}


						}).error(
								function(response) { // optional

									$mdDialog.show($mdDialog.alert().parent(
											angular.element(document
													.querySelector('#dialogContainer')))
													.clickOutsideToClose(true).textContent(
													"Sales Order Details Not Available").ok('Ok!')
													.targetEvent(event));

								});
				



			}
				else
					
					{
					

					
					var data = JSON.stringify({
						orderData1 : $scope.salesOrderHdrIds		
					});

					var fdata = new FormData();
					fdata.append("data", data);
					$http.post("getShopWiseSplitDataShopWise", fdata, {
						transformRequest : angular.identity,
						params:{productionDate : getMysqlFormat($scope.formData.produptoDate),
							prod_date : getMysqlFormat($scope.formData.prodDate),
							customerID:($scope.slctypreq==0)?$scope.companyId:$scope.customerIds1,
									stkId:item.stock_item_id},
						headers : {
							'Content-Type' : undefined
						}
					}).success(
							function(response) {
								console.log(response.currentdayPrevBalance);
								$scope.orderSplitData = response.orderSplitData;
								if($scope.orderSplitData.length !=0)
								{
								//$scope.previousBalance=$scope.orderSplitData[0].balanceqty;
									var qty=0.0;
									for(var i=0;i<$scope.orderSplitData.length;i++)
										{
										qty+=parseFloat($scope.orderSplitData[i].orderqty);
										}
									$scope.previousBalance=parseFloat((item.order_qty-qty)).toFixed(settings['decimalPlace'])<0
									?$scope.previousBalance=parseFloat((0)).toFixed(settings['decimalPlace']):
										parseFloat((item.order_qty-qty)).toFixed(settings['decimalPlace']);
									
								}else
									{
									$scope.previousBalance=parseFloat((0)).toFixed(settings['decimalPlace']);
									}
								if(item.stock_item_id !=undefined && item.stock_item_id !="")
								{
									if(item.order_qty>0)
									{
										$('#orderDataSplit').modal('toggle');
									}else
									{
										$rootScope.$broadcast('on_AlertMessage_ERR',"No Sales Order Data Available For this Item");
									}

								}else
								{
									$rootScope.$broadcast('on_AlertMessage_ERR',"Please Select An Item");
								}


							}).error(
									function(response) { // optional

										$mdDialog.show($mdDialog.alert().parent(
												angular.element(document
														.querySelector('#dialogContainer')))
														.clickOutsideToClose(true).textContent(
														"Sales Order Details Not Available").ok('Ok!')
														.targetEvent(event));

									});
					



				
					
					
					
					
					
					
					
					}*/
				
				
				
				

			
			var detailsdata;
			detailsdata= {
					
					dtl_id:item.id
					
			
			};

			$http({
				url : 'orderDetailsdailyprod',
				async : false,
				params : detailsdata,
				method : "GET",
			}).then(function(response) {
				$scope.orderSplitData=[];
				$scope.orderSplitData=response.data.orderSplitData;
				if($scope.orderSplitData.length !=0)
				{
				var qty=0.0;
				for(var i=0;i<$scope.orderSplitData.length;i++)
					{
					qty+=parseFloat($scope.orderSplitData[i].orderqty);
					}
				$scope.previousBalance=parseFloat((item.order_qty-qty)).toFixed(settings['decimalPlace'])<0
				?parseFloat((0)).toFixed(settings['decimalPlace']):
					parseFloat((item.order_qty-qty)).toFixed(settings['decimalPlace']);
				
				
					$('#orderDataSplit').modal('toggle');
				
				
				}
				else
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"No Sales Order Data Available For this Item");
				}
				
				
			});
			
				
				
				
				
			}
		//Edit And Save Bom Data
		$scope.editBomData=function(index,item)
		{
			/*	if($scope.disable_all==false)
			{*/
			$("#bom_qty").css("border-color","#d2d6de");
			$scope.selectedStkItemId=$scope.daily_production.items[index].stock_item_id;
			$scope.selectedStkItemName=$scope.daily_production.items[index].stock_item_name;
			if(item.stock_item_id !=undefined && item.stock_item_id !="")
			{
				$('#orderData').modal('toggle');
				$http({
					method: 'GET',
					url : "getBomAndProdData",
					params:{itemId:item.stock_item_id},
					async:false,
				}).success(function (result) {
					$scope.bomDetailsList=result.bom;
					$scope.prodCostList=result.prd_costdata;
					$scope.bomList=$scope.bomDetailsList;
					for(var i=0;i<$scope.bomList.length;i++){
						$scope.bomList[i].qty=parseFloat($scope.bomList[i].qty).toFixed(settings['decimalPlace']);
						$scope.bomList[i].unit_price=parseFloat($scope.bomList[i].unit_price).toFixed(settings['decimalPlace']);
						$scope.bomList[i].isSet=false;
					}
					for(var i=0;i<$scope.prodCostList.length;i++){
						$scope.prodCostList[i].rate=parseFloat($scope.prodCostList[i].rate).toFixed(settings['decimalPlace']);
						if($scope.prodCostList[i].is_percentage == 1){$scope.prodCostList[i].isPercentage = true;}else
						{
							$scope.prodCostList[i].isPercentage =false;
						}
						$scope.prodCostList[i].isSet=false;
					}
					if(result.bomQty.length !=0 && result.bomQty[0].bom_qty != undefined && result.bomQty[0].bom_qty !="")
					{
						$scope.bomQty=parseFloat(result.bomQty[0].bom_qty).toFixed(settings['decimalPlace']);
					}else
					{
						$scope.bomQty=0;
					}
					if($scope.bomList.length == 0){
						$scope.bomList.push({id : "",bom_item_id :"",qty: "",bom_item_name:""});
						$("#rowAdd").show();
						$("#AddrowHd").hide();
					}
					if($scope.prodCostList.length == 0)
					{
						$scope.prodCostList.push({id : "",prod_cost_id :"",prod_cost_code:"",prod_cost_name:"",
							prod_cost_type:"",isPercentage: false,rate:""});
					}
					if($scope.formData.status == 1 || $scope.disable_all== true)
					{
						$timeout(function () {				
							$("#stockHead .acontainer input").attr('disabled',true);
							$("#prodCost .acontainer input").attr('disabled',true);
						}, 1);

					}else
					{
						$("#stockHead .acontainer input").attr('disabled',false);
						$("#prodCost .acontainer input").attr('disabled',false);
					}
				});

			}else
			{
				$rootScope.$broadcast('on_AlertMessage_ERR',"Please Select An Item");
			}
			//}
		}

		function bomValidation(){
			var flg = true;
			$("#bom_qty").css("border-color","#d2d6de");



			if($scope.bomList.length==1)
			{

				if($scope.bomQty == "" && $scope.bomQty == "0" && $scope.bomList[0].bom_item_id !="")
				{
					$("#bom_qty").css("border-color","red");

					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal','Enter the Standard Production Quantity');
					// $rootScope.$broadcast('on_AlertMessage_ERR','Enter the Standard Production Quantity');

					flg = false;

				}

				else if($scope.bomQty != "" && $scope.bomQty != "0" && $scope.bomList[0].bom_item_id !="" && $scope.bomList[0].qty =="")
				{
					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.QTY_ERR);
					//$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
					$scope.selectedIndex = 1;
					$("#stockHead tbody tr:nth-child("+($scope.bomList.length)+") td:nth-child("+(4)+")").find("#qty").focus();

					flg=false;
				}

				else if($scope.bomQty != "" && $scope.bomQty != "0" && $scope.bomList[0].bom_item_id =="" &&
						$scope.prodCostList[0].prod_cost_id =="")
				{
					//$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					$("#stockHead tbody tr:nth-child("+($scope.bomList.length)+") td:nth-child("+(2)+")").find(".acontainer input").focus();

					flg=false;

				}
				else if($scope.bomList[0].bom_item_id !="" && $scope.bomList[0].bom_item_id !=undefined && $scope.bomList[0].qty =="" )
				{
					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.QTY_ERR);

					//$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
					$scope.selectedIndex = 1;
					$("#stockHead tbody tr:nth-child("+($scope.bomList.length)+") td:nth-child("+(4)+")").find("#qty").focus();
					flg=false;
				}
				else if($scope.bomList[0].bom_item_id =="" && $scope.bomList[0].qty !="")
				{
					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					//$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					$scope.selectedIndex = 1;
					$("#stockHead tbody tr:nth-child("+($scope.bomList.length)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
					flg=false;
				}

				else if($scope.bomList[0].bom_item_id !="" && $scope.bomList[0].bom_item_id!=undefined )
				{
					if($scope.bomQty == "" || $scope.bomQty == "0"){

						$("#bom_qty").css("border-color","red");
						$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal','Enter the Standard Production Quantity');
						//$rootScope.$broadcast('on_AlertMessage_ERR','Enter the Standard Production Quantity');
						$scope.disable_all == false;
						flg = false;

					}	
				}

			}

			else if($scope.bomList.length >1)
			{
				for(var i = 0;i<$scope.bomList.length;i++){
					if($scope.bomList[i].bom_item_id =="" || $scope.bomList[i].bom_item_id ==undefined  )
					{

						$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
						//$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
						$("#stockHead tbody tr:nth-child("+(i+1)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
						$scope.selectedIndex = 1;
						flg=false;
						break;
					}

					else if($scope.bomList[i].qty =="" || $scope.bomList[i].qty ==undefined  )
					{

						$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.QTY_ERR);

						//$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);					
						$("#stockHead tbody tr:nth-child("+(i+1)+") td:nth-child("+(4)+")").find("#qty").focus();
						$scope.selectedIndex = 1;
						flg=false;
						break;
					}



					else if($scope.bomQty == "" || $scope.bomQty == "0"){

						$("#bom_qty").css("border-color","red");
						//$rootScope.$broadcast('on_AlertMessage_ERR','Enter the Standard Production Quantity');

						$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal','Enter the Standard Production Quantity');

						flg = false;
						break;


					}





				}


			}
			return flg;	
		}

		function prodCostValidation()
		{

			var flg = true;	
			if($scope.prodCostList.length==1)
			{

				if($scope.prodCostList[0].prod_cost_id !="" && $scope.prodCostList[0].rate == "")
				{
					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.COST_ERR);

					//$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_ERR);
					$scope.selectedIndex = 1;
					$("#prodCost tbody tr:nth-child("+($scope.prodCostList.length)+") td:nth-child("+(5)+")").find("#cost_rate").focus();

					flg=false;

				}
				else if($scope.prodCostList[0].prod_cost_id !="" && ($scope.bomQty == "" || $scope.bomQty==0 ||  $scope.bomQty==undefined))
				{
					$("#bom_qty").css("border-color","red");
					$scope.setErr_AlertMessageModal('on_AlertMessage_ERR','Enter the Standard Production Quantity');
					flg = false;
				}

				else if($scope.prodCostList[0].prod_cost_id =="" && $scope.prodCostList[0].rate !="")
				{
					//$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					
					$scope.selectedIndex = 1;
					$("#prodCost tbody tr:nth-child("+($scope.prodCostList.length)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
					flg=false;

				}
				if(($scope.prodCostList[0].rate =="" || $scope.prodCostList[0].rate ==undefined )&& $scope.prodCostList[0].prod_cost_id !="" )
				{
					if($scope.prodCostList[0].isPercentage == false){
						$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.COST_ERR);

						//$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_ERR);			
					}else 
					{


						$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_PERCNTGE_ERR);	
					}
					flg=false;
					$scope.selectedIndex = 1;
					$("#prodCost tbody tr:nth-child("+($scope.prodCostList.length)+") td:nth-child("+(5)+")").find("#cost_rate").focus();


				}
				else if(($scope.prodCostList[0].isPercentage == true && $scope.prodCostList[0].rate > 100 && $scope.prodCostList[0].prod_cost_id !="") || 
						($scope.prodCostList[0].isPercentage == false && $scope.prodCostList[0].rate.split(".")[0].length > 10))
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.PERCENT_LIMIT_ERR);
					flg=false;
					$scope.selectedIndex = 1;
					$("#prodCost tbody tr:nth-child("+($scope.prodCostList.length)+") td:nth-child("+(5)+")").find("#cost_rate").focus();


				}

			}
			else if($scope.prodCostList.length >1)
			{
				for(var i = 0;i<$scope.prodCostList.length;i++){
					if($scope.prodCostList[i].prod_cost_id =="" || $scope.prodCostList[i].prod_cost_id ==undefined  )
					{
						$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
						$scope.selectedIndex = 1;
						$("#prodCost tbody tr:nth-child("+(i+1)+") td:nth-child("+(2)+")").find(".acontainer input").focus();

						flg=false;
						break;
					}

					else if(($scope.prodCostList[i].rate =="" || $scope.prodCostList[i].rate ==undefined) && $scope.prodCostList[0].prod_cost_id !="" )
					{
						if($scope.prodCostList[i].isPercentage == false){
							$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.COST_ERR);

							//$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_ERR);			
						}else 
						{
							//$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.COST_PERCNTGE_ERR);

							$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_PERCNTGE_ERR);	
						}
						flg=false;
						$scope.selectedIndex = 1;
						$("#prodCost tbody tr:nth-child("+(i+1)+") td:nth-child("+(5)+")").find("#cost_rate").focus();
						break;

					}
					if((($scope.prodCostList[i].isPercentage == true && $scope.prodCostList[i].rate > 100) || 
							($scope.prodCostList[i].isPercentage == false && $scope.prodCostList[i].rate.split(".")[0].length > 10))
							&& $scope.prodCostList[0].prod_cost_id !="")
					{
						$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.PERCENT_LIMIT_ERR);
						flg=false;
						$scope.selectedIndex = 1;
						$("#prodCost tbody tr:nth-child("+(i+1)+") td:nth-child("+(5)+")").find("#cost_rate").focus();
						break;

					}


				}


			}
			return flg;	

		}

		$scope.getUpdatedCostData=function(itemId)
		{
			$http({	url : 'getUpdatedCost',	method : "GET",async:false,
			}).then(function(response) {
				$scope.costData=response.data.costData;
				$scope.getMaterialAndOtherCost(itemId);
				for(var i=0;i<$scope.daily_production.items.length;i++)
				{
					if($scope.daily_production.items[i].stock_item_id==itemId)
					{
						$scope.daily_production.items[i].itemMaterialCost=$scope.getMaterialAndOtherCost(itemId)[0];
						$scope.daily_production.items[i].otherCost=$scope.getMaterialAndOtherCost(itemId)[1];
					}
				}
			}, function(response) { // optional
			});
		}

		//Finalize:
		$scope.finalize=function(ev){
			var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
				var $dialog = angular.element(document.querySelector('md-dialog'));
				var $actionsSection = $dialog.find('md-dialog-actions');
				var $cancelButton = $actionsSection.children()[0];
				var $confirmButton = $actionsSection.children()[1];
				angular.element($confirmButton).removeClass('md-focused');
				angular.element($cancelButton).addClass('md-focused');
				$cancelButton.focus();
			}}).title('You cannot make any changes on finalize. Are you sure you want to finalize?').targetEvent(ev).cancel('No').ok('Yes');
			$mdDialog.show(confirm).then(function() {

				$scope.formData.status=1;
				$scope.prograssing = true;
				$scope.disableProdDate =true;
				$http({
					url : 'finalize',
					method : "POST",
					data:$scope.formData,
					params : {id:$scope.formData.prodId,stockreg_id:$scope.formData.stock_reg_id},
				}).then(function(response) {

					if(response.data==1){
						$('#btnDelete').hide();
						$('#btnEdit').hide();
						$('#btnBack').show();
						$scope.prodStatus=false;
						$scope.showFinalize=false;
						/*$scope.disable_btn=true;*/
						$scope.prograssing = false;
						$scope.disableProdDate =false;
						reloadData();
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
						/* $scope.alertBox(FORM_MESSAGES.SAVE_ERR);*/
						$scope.disableProdDate =false;
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
					/*$scope.alertBox(FORM_MESSAGES.SAVE_ERR);*/
					$scope.disableProdDate =false;

				});

			});
			
		}

		$scope.getSalesOrderData = function(itemid){
			$scope.saleOrder =0;
			/*for(var i=0;i<$scope.orderData.length;i++){
				if($scope.orderData[i].stock_item_id == itemid 
						&& getMysqlFormat($scope.formData.prodDate)==$scope.orderData[i].delivery_date){
					$scope.saleOrder= $scope.orderData[i].total_qty;
					break;
				}
			}*/
			for(var i=0;i<$scope.orderArrayAndBalanceData.length;i++){
				if($scope.orderArrayAndBalanceData[i].stock_item_id == itemid 
					&& getMysqlFormat($scope.formData.prodDate)==$scope.orderArrayAndBalanceData[i].delivery_date){
					$scope.saleOrder= $scope.orderArrayAndBalanceData[i].order_qty;
					break;
				}
			}
			$scope.saleOrder=($scope.saleOrder=="") ? 0 :$scope.saleOrder;

			return parseFloat($scope.saleOrder).toFixed(settings['decimalPlace']);
		}  
}

mrpApp.directive('tableAutocomplete01', ['$timeout',function($timeout) {
	return {

		controller: function ($scope,$http) {
			$scope.currentIndex = 0;
			$scope.itemsData = [];

			$("#items_table tbody tr td").keyup('input',function(e){

				$scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;

				if(e.shiftKey && e.keyCode == 9){
					$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find(".acontainer input").focus();
				}else if(  e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8 && !(e.shiftKey && e.keyCode == 9)){
					if(e.currentTarget.cellIndex == 0){
						$scope.$apply(function(){

							$scope.clear_stock_details_editmode(e);});}}
				else if(e.which == 13 ){
					if(e.currentTarget.cellIndex == 0){
						if($scope.daily_production.items[e.currentTarget.parentElement.rowIndex-1].stock_item_id!=undefined){
							{
								/*$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find("#qty").focus();*/
								/*$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")  td:nth-child(3)").find(".acontainer input").focus();*/
								$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex)+") td:nth-child(3)").find("#source_code").focus();

							}
						}}



				}else if(e.which == 9 ){
					if(e.currentTarget.cellIndex == 2){
						{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex)+")  td:nth-child(3)").find(".acontainer input").focus();/*}*/}
					}
				}});
			$scope.element = [];
			$scope.table_itemsearch_rowindex=0;
			$scope.tableClicked = function (index) {
				$scope.table_itemsearch_rowindex= index;};
				return $scope;
		},
		link: function($scope, elem, attrs ,controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns: ['id' , 'code', 'name','uomcode'],
				hide: [false,true,true,false],
				placeholder: "search...",
				highlight: "hightlight-classname",
				norecord: "No Records Found",
				delay : 10,
				onchange: function () {
					var selected_item_data =  items.all();

					strl_scope.$apply(function(){var count =0;

					for(var i=0;i<strl_scope.daily_production.items.length;i++){
						if(selected_item_data.id != ""){
							if(i != strl_scope.currentIndex){
								if(selected_item_data.id == strl_scope.daily_production.items[i].stock_item_id){
									count=1;}}}}
					if(count != 1){
						strl_scope.daily_production.items[strl_scope.currentIndex].stock_item_id = selected_item_data.id;
						strl_scope.daily_production.items[strl_scope.currentIndex].stock_item_code = selected_item_data.code;
						strl_scope.daily_production.items[strl_scope.currentIndex].stock_item_name = selected_item_data.name;
						strl_scope.daily_production.items[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;
						strl_scope.daily_production.items[strl_scope.currentIndex].order_qty = strl_scope.getSalesOrderData(selected_item_data.id);
						strl_scope.daily_production.items[strl_scope.currentIndex].saleRate=strl_scope.getSaleRate1(selected_item_data.id); 
						strl_scope.daily_production.items[strl_scope.currentIndex].itemMaterialCost=strl_scope.getMaterialAndOtherCost(selected_item_data.id)[0];
						strl_scope.daily_production.items[strl_scope.currentIndex].otherCost=strl_scope.getMaterialAndOtherCost(selected_item_data.id)[1];
						//strl_scope.getUpdatedCostData(selected_item_data.id);
						strl_scope.alert_for_codeexisting(0);
					}else
					{

						elem[0].parentNode.lastChild.value="";
						strl_scope.daily_production.items[strl_scope.currentIndex].uomcode = "";
						strl_scope.alert_for_codeexisting(1);
					}

					});
				},
				data: function () {

					var strl_scope = controller;
					var data;

					data = strl_scope.stockData;

					var filterData = [];

					var searchData = eval("/" + items.searchdata() + "/gi");

					$.each(data, function (i, v) {
						if ( v.name.search(new RegExp(searchData)) != -1) {
							if(v.is_active == 1 && v.is_manufactured ==1)
							{
								filterData.push(v);
							}
						}
					});



					return filterData;	

				}

			});
			for(var i = 0;i<strl_scope.daily_production.items.length;i++){
				if(strl_scope.daily_production.items[i].prodnHdrId!=undefined && strl_scope.daily_production.items[i].prodnHdrId!=''){
					if(strl_scope.daily_production.items[i].flag==0){
						elem[0].parentNode.lastChild.value = strl_scope.daily_production.items[i].stock_item_code;
						strl_scope.elementid(elem[0].parentNode.lastChild);
						strl_scope.daily_production.items[i].flag=1;break;}}

			}$timeout(function () {
				if(settings['dailyprodview']==0)
				{

				if(strl_scope.formData.prodDate!=undefined && process(strl_scope.formData.prodDate)!='Invalid Date')
					$("#items_table tbody tr:nth-child("+(strl_scope.daily_production.items.length+1)+") td:nth-child("+(1)+")").find(".acontainer input").select();
				}
			}, 1);
		}
	};
}]);

mrpApp.directive('tableDepartment', ['$timeout',function($timeout) {
	return {

		controller: function ($scope,$http) {
			$scope.currentIndex = 0;



			$("#items_table tbody tr td").keyup('input',function(e){
				$scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;

				if(e.shiftKey && e.keyCode == 9){
					$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+2)+")").find(".acontainer input").focus();
				}else if(  e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8 && !(e.shiftKey && e.keyCode == 9)){
					if(e.currentTarget.cellIndex == 2){
						$scope.$apply(function(){


							$scope.clear_stock_details_Departmode(e);});}} else if(e.which == 13 ){
								if(e.currentTarget.cellIndex == 2){
									if($scope.customer_orders.items[e.currentTarget.parentElement.rowIndex-1].stock_item_id!=undefined){
										{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find("#qty").focus();}
									}}
							}else if(e.which == 9 ){
								if(e.currentTarget.cellIndex == 2){
									{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex)+")  td:nth-child(3) ").find(".acontainer input").focus();/*}*/}
								}
							}});

			$scope.element = [];
			$scope.table_itemsearch_rowindex=0;
			$scope.tableClicked1 = function (index) {
				$scope.table_itemsearch_rowindex= index;};
				return $scope;
		},
		link: function($scope, elem, attrs ,controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns: ['id' , 'code', 'name','uomcode'],
				hide: [false,true,true,false],
				placeholder: "search...",
				highlight: "hightlight-classname",
				norecord: "No Records Found",
				delay : 10,
				onchange: function () {
					var selected_department_data =  items.all();

					strl_scope.$apply(function(){var count =0;

					strl_scope.daily_production.items[strl_scope.currentIndex].department_id=selected_department_data.id;
					strl_scope.daily_production.items[strl_scope.currentIndex].source_code =  selected_department_data.code;
					strl_scope.daily_production.items[strl_scope.currentIndex].source_name=  selected_department_data.name;

					});
				},
				data: function () {

					var strl_scope = controller;
					var data;

					data = strl_scope.department_data;

					var filterData = [];

					var searchData = eval("/^" + items.searchdata() + "/gi");

					$.each(data, function (i, v) {
						if ( v.name.search(new RegExp(searchData)) != -1) {							

							filterData.push(v);								
						}
					});									
					return filterData;	

				}

			});for(var i = 0;i<strl_scope.daily_production.items.length;i++){
				if(strl_scope.daily_production.items[i].prodnHdrId!=undefined && strl_scope.daily_production.items[i].prodnHdrId!=''){
					if(strl_scope.daily_production.items[i].Dflag==0){
						elem[0].parentNode.lastChild.value = strl_scope.daily_production.items[i].source_code;
						strl_scope.elementid(elem[0].parentNode.lastChild);
						strl_scope.daily_production.items[i].Dflag=1;break;}}

			}
		}
	};
}]);

mrpApp.directive('autocompeteText', ['$timeout',function($timeout) {
	return {
		controller: function ($scope,$http) {
			$scope.currentIndex3= 0;
			$("#stockHead tr td").keyup('input',function(e){
				$scope.currentIndex3 = e.currentTarget.parentElement.rowIndex-1;

				if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){
					if(e.currentTarget.cellIndex == 1){
						$scope.$apply(function(){
							$scope.clear_bom_details_editmode(e);
							$scope.alert_for_codeexisting2(0);
						});
					}
				}else if(e.which == 13 ){
					if(e.currentTarget.cellIndex == 1){
						if($scope.bomList[$scope.bomList.length-1].bom_item_id!=""){
							{ $("#stockHead tr:nth-child("+e.currentTarget.parentElement.rowIndex+") td:nth-child("+(4)+")").find("#qty").select();}
						}}
				}else if(e.which == 9 ){
					if(e.currentTarget.cellIndex == 4){


						{$("#stockHead tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+") td:nth-child("+(2)+")").find(".acontainer input").focus();

						}
					}
				}

			});
			$scope.itemsData = $scope.stkData;
			$scope.element = [];
			$scope.table_itemsearch_rowindex3=0;
			$scope.tableClicked3 = function (index) {
				$scope.table_itemsearch_rowindex3= index;
			};
			return $scope;
		},
		link: function(scope, elem, attrs ,controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns: ['id','code','name','input_tax_id','tax_percentage','valuation_method','is_active','is_manufactured','uomcode','uomname','unit_price','cost_price'],
				hide:[false,true,true,false,false,false,false,false,false,false,false,false],
				/*hide: [false,true,true,false,false,false,false,false,false,false,false,false,false],*/
				/*columns: ['id','code','name','input_tax_id','tax_percentage','valuation_method','is_active','is_manufactured','uomcode','uomname'],
				hide: [false,true,true,false,false,false,false,false,true,false],*/
				placeholder: "search ..",
				highlight: "hightlight-classname",
				norecord: "No Records Found",
				delay : 10,
				onchange: function () {
					var selected_item_data =  items.all();
					strl_scope.$apply(function(){
						var count =0;
						for(var i=0;i<strl_scope.bomList.length;i++){
							if(!strl_scope.bomList[i].isDeleted){
								if(selected_item_data.id != ""){
									if(i != strl_scope.currentIndex3){
										if(selected_item_data.id == strl_scope.bomList[i].bom_item_id){
											count=1;
										}
									}
								}strl_scope
							}
						}
						if(count != 1){
							strl_scope.bomList[strl_scope.currentIndex3].bom_item_id = selected_item_data.id;
							strl_scope.bomList[strl_scope.currentIndex3].bom_item_code = selected_item_data.code;
							strl_scope.bomList[strl_scope.currentIndex3].bom_item_name = selected_item_data.name;

							strl_scope.bomList[strl_scope.currentIndex3].uomcode = selected_item_data.uomcode;
							strl_scope.bomList[strl_scope.currentIndex3].unit_price=parseFloat(selected_item_data.cost_price).toFixed(settings['decimalPlace']);
							/* strl_scope.getCostPrice(strl_scope.currentIndex,selected_item_data.id);
							 */ 

							$timeout(function () { $("#stockHead tbody tr:nth-child("+strl_scope.currentIndex3+1+") td").find("#qty").focus();}, 1); 

							strl_scope.alert_for_codeexisting2(0);
							$("#stockHead tbody tr:nth-child("+strl_scope.table_itemsearch_rowindex3+1+") td").find("#po_qty").focus();
						}else{
							elem[0].parentNode.lastChild.value="";
							strl_scope.bomList[strl_scope.currentIndex3].bom_item_id = "";
							strl_scope.bomList[strl_scope.currentIndex3].bom_item_code = "";
							strl_scope.bomList[strl_scope.currentIndex3].bom_item_name = "";
							strl_scope.bomList[strl_scope.currentIndex3].uomcode = "";
							strl_scope.alert_for_codeexisting2(1);
						}

					});
				},
				data: function () {

					var data = strl_scope.itemsData;

					var filterData = [];
					var searchData = eval("/^" + items.searchdata() + "/gi");
					$.each(data, function (i, v) {
						if ( v.name.search(new RegExp(searchData)) != -1) {
							filterData.push(v);
						}
					});

					return filterData;
				}
			});

			for(var i = 0;i<strl_scope.bomList.length;i++){
				if(strl_scope.selectedStkItemId!=undefined && strl_scope.selectedStkItemId!='' ){
					if(strl_scope.bomList[i].isSet == false){
						elem[0].parentNode.lastChild.value = strl_scope.bomList[i].bom_item_code;
						strl_scope.disable_search_text(elem[0].parentNode.lastChild);
						strl_scope.bomList[i].isSet=true;break;

					}
				}else{
					if(strl_scope.bomList[i].isSet == false){
						if(strl_scope.bomList[i].po_no != undefined && strl_scope.bomList[i].po_no != ""){
							elem[0].parentNode.lastChild.value = strl_scope.bomList[i].bom_item_code;
							strl_scope.bomList[i].isSet=true;break;
						}
					}
				}
			}
			$timeout(function () { 
				$("#stockHead tr:nth-child("+(strl_scope.bomList.length)+") td:nth-child("+(2)+")").find(".acontainer input").select();
			}, 1); 
		}
	};
}]);

mrpApp.directive('tableProductioncost', ['$timeout',function($timeout) {
	return {
		controller: function ($scope,$http) {
			$scope.currentIndex1 = 0;
			$("#prodCost tr td").keyup('input',function(e){
				$scope.currentIndex1 = e.currentTarget.parentElement.rowIndex-1;

				if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){
					if(e.currentTarget.cellIndex == 1){
						$scope.$apply(function(){
							$scope.clear_cost_details_editmode(e);
							$scope.alert_for_codeexisting(0);
						});
					}
				}else if(e.which == 13 ){
					if(e.currentTarget.cellIndex == 1){
						if($scope.prodCostList[$scope.prodCostList.length-1].prod_cost_id!=""){
							{ $("#prodCost tr:nth-child("+e.currentTarget.parentElement.rowIndex+") td:nth-child("+(5)+")").find("#cost_rate").select();}
						}}
				}else if(e.which == 9 ){
					if(e.currentTarget.cellIndex == 1){				
						{$("#prodCost tr:nth-child("+(e.currentTarget.parentElement.rowIndex)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
						/*$("#items_table tbody tr:nth-child("+e.currentTarget.cellIndex+") td").find(".acontainer input").focus();*/}
					}
				}

			});


			$scope.element = [];
			$scope.table_itemsearch_rowindex1=0;
			$scope.tableClicked2 = function (index) {
				$scope.table_itemsearch_rowindex1= index;
			};
			return $scope;
		},
		link: function(scope, elem, attrs ,controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns: ['id' , 'code', 'name','cost_type_name'],
				hide: [false,true,true,false],

				placeholder: "search ..",
				highlight: "hightlight-classname",
				norecord: "No Records Found",
				delay : 10,
				onchange: function () {
					var selected_cost_data =  items.all();
					strl_scope.$apply(function(){
						var count =0;
						for(var i=0;i<strl_scope.prodCostList.length;i++){
							if(!strl_scope.prodCostList[i].isDeleted){
								if(selected_cost_data.id != ""){
									if(i != strl_scope.currentIndex1){
										if(selected_cost_data.id == strl_scope.prodCostList[i].prod_cost_id){
											count=1;
										}
									}
								}strl_scope
							}
						}
						if(count != 1){

							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_id=selected_cost_data.id;
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_code =  selected_cost_data.code;
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_name=  selected_cost_data.name;
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_type=  selected_cost_data.cost_type_name;
							/* strl_scope.getCostPrice(strl_scope.currentIndex,selected_item_data.id);
							 */ 

							strl_scope.alert_for_codeexisting2(0);
							$timeout(function () { $("#prodCost tbody tr:nth-child("+strl_scope.currentIndex1+1+") td").find("#cost_rate").focus();}, 1); 
						}else{
							elem[0].parentNode.lastChild.value="";
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_id = "";
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_code = "";
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_name = "";
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_type = "";

							strl_scope.alert_for_codeexisting2(1);
						}

					});
				},
				data: function () {

					var data = strl_scope.prodcost_data;

					var filterData = [];
					var searchData = eval("/^" + items.searchdata() + "/gi");
					$.each(data, function (i, v) {
						if ( v.name.search(new RegExp(searchData)) != -1) {
							filterData.push(v);
						}
					});

					return filterData;
				}
			});

			for(var i = 0;i<strl_scope.prodCostList.length;i++){
				if(strl_scope.selectedStkItemId!=undefined && strl_scope.selectedStkItemId!='' ){
					if(strl_scope.prodCostList[i].isSet == false){
						elem[0].parentNode.lastChild.value = strl_scope.prodCostList[i].prod_cost_code;
						strl_scope.disable_search_text(elem[0].parentNode.lastChild);
						strl_scope.prodCostList[i].isSet=true;break;

					}
				}else{
					if(strl_scope.prodCostList[i].isSet == false){
						if(strl_scope.prodCostList[i].po_no != undefined && strl_scope.prodCostList[i].po_no != ""){
							elem[0].parentNode.lastChild.value = strl_scope.prodCostList[i].prod_cost_code;
							strl_scope.prodCostList[i].isSet=true;break;
						}
					}
				}
			}
			$timeout(function () { $("#prodCost tr:nth-child("+(strl_scope.prodCostList.length)+") td:nth-child("+(2)+")").find(".acontainer input").select();
			}, 1); 
		}
	};
}]);



mrpApp.directive('daterangePicker01', [function() {
	return {
		controller: function ($scope,$http) {

			return $scope;
		},
		link: function(scope, elem, attrs ,controller) {
			var dateFormat = get_date_format();
			var date = new Date(),y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a day
			var diff = todayTimeStamp - oneDayTimeStamp;
			var yesterdayDate = new Date(diff);
			var yr=new Date().getFullYear()-10;

			/*controller.formData.prodDate = dateForm(curDate);*/
			controller.formData.produptoDate = dateForm(curDate);
			$(elem).inputmask({
				mask : dateFormat.mask,
				placeholder : dateFormat.format,
				alias : "date",
			});

			$(elem).daterangepicker({
				"format" : dateFormat.format,
				"drops" : "down",
				"autoclose": true,
				"singleDatePicker" : true,
				"showDropdowns" : true,
				"autoApply" : false,
				"linkedCalendars" : false,
				/*"maxDate": dateFormat.mindate,
				"minDate": new Date(yr, 0, 1)*/
				"minDate": dateFormat.mindate
			}, function(start, end, label) {
			});
		}
	};
}]);





