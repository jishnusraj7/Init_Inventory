

mrpApp.controller('uomctrl', uomctrl);
mrpApp.controller('clndr_ctrl', clndr_ctrl);
function clndr_ctrl($controller, $compile, $timeout, $scope, $http, $mdDialog,
		$rootScope, ITEM_TABLE_MESSAGES, $window, DTOptionsBuilder,
		DTColumnBuilder, MRP_CONSTANT, DATATABLE_CONSTANT, FORM_MESSAGES,$filter) {

//	for calender
	/*$scope.clndrshow=true;*/
	var datetd=new Date();
	var curr_date1 = datetd.getDate();
	var curr_month1 = datetd.getMonth() + 1; //Months are zero based
	var curr_year1 = datetd.getFullYear();
	var todaydate= curr_year1 + "-" + curr_month1 + "-" + curr_date1;
	$scope.closing_date = geteditDateFormat(todaydate);
	$scope.highlightDays=[];
	$scope.totalorderList=[];
	$scope.minDate = datetd.getDate()-13;
	$scope.getorderDet=function()
	{
		$http({
			url : 'getDetailsplan',
			async : false,
			method : "GET",
			params:{closingdate:getMysqlFormat($scope.closing_date)}
		}).then(function(response) {
			$scope.totalorderList=response.data.totalorderList;
			$scope.highlightDays=response.data.alreadyaddorder

		});
	}
	/*	$scope.highlightDays = [
	                        {date: "2018-02-01", css: 'prodadd', selectable: true,title: ''},
	                        {date:"2018-03-01", css: 'prodadd', selectable: true,title: ''},
	                        {date: "2018-03-04", css: 'prodadd', selectable: true,title: ''}
	                    ];*/
	$scope.getorderDet();

	$scope.selectedDays3 = [];
	$scope.oneDaySelectionOnly = function (event, date) {

		$scope.selectedDays3.length = 0;
		var date1 = date.date._d;
		var curr_date = date1.getDate();
		var curr_month = date1.getMonth() + 1; //Months are zero based
		var curr_year = date1.getFullYear();
		var clkdate=curr_year + "-" + curr_month + "-" + curr_date;
		$rootScope.$emit('checkvalidData',clkdate);
		// $scope.checkDateValidity(clkdate);

		$scope.closing_date= geteditDateFormat(clkdate);

		$scope.getorderDet();
		$rootScope.$on('changeview_details', function(event) {
			$scope.getorderDet();
		});

	};


}

function uomctrl($controller, $compile, $timeout, $scope, $http, $mdDialog,
		$rootScope, ITEM_TABLE_MESSAGES, $window, DTOptionsBuilder,
		DTColumnBuilder, MRP_CONSTANT, DATATABLE_CONSTANT, FORM_MESSAGES,$filter) {

	/* ## extend DatatableController ## */
	$controller('DatatableController', {
		$scope : $scope
	});

	$scope.getDeptData = function(){

		$http({
			url : 'getDepartments',
			async : false,
			method : "GET",
		}).then(function(response) {
			$scope.departmentsList =response.data.departments;
		});
	}

	$scope.formDatacust = {};
	$('#btnExport').hide();
	$('#summaryBttn').hide();
	$("#form_div_description").hide();
	$('#create_date').prop("disabled",false);
	$rootScope.showSummary = false;
	$scope.hide_code_existing_er = true;

	$scope.stockData = [];
	$scope.stkfilterData = [];
	$scope.remarks = '';
	$scope.ItemCtgry = [ { id : 0, name : "All" } ];
	$rootScope.clndrshow=true;
	manageButtons("hideall");
	$('#btnAdd').show();

	$scope.fun_get_PRDno = function(event) {
		$http.get('getCounterPrefix').success(function(response) {
			$scope.PRDNO = response;
		});
	}

	$scope.departmentsList = [];	
	$scope.getDeptData();	  
	$scope.versions = [{"id": 0,  "name": "All"}, { "id": 1,"name": "Customers Orders"},
		{"id": 2, "name": "Shops Orders"}, {  "id": 3, "name": "Summary"}] 

	$scope.shoporder=0;
	$scope.catidList = [];
	$scope.selectedDate = new Date();
	
	$scope.min = new Date();

	$scope.minDate = $scope.min.setDate($scope.min.getDate()-15);
	
	var dt = new Date();
	var time = dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
	$scope.Time = time;

	//$scope.show_calndr = true;
	$scope.show_order_form = false;
	$scope.show_addForm = false;
	$('#form_div_cat').hide();

	$scope.shops = {};
	$scope.balanceQtyData = {};
	//set delivery date as next day
	$scope.delivery_date = moment(new Date()).add(1,'days').format('YYYY-MM-DD');
	$scope.closing_date = geteditDateFormat(moment(new Date()).format('YYYY-MM-DD'));

	var date = get_date_format();
	$scope.date = date.mindate;

	/*$http({
		url : 'calendarViewList',
		async : false,
		method : "GET",
	}).then(function(response) {
		$scope.loadFormData();

		$scope.events1 = [];

		$scope.events1 = response.data.orderDataList;

		  for (var i = 0; i < $scope.events1.length; i++) {

              if ($scope.events1[i].isNotFinalized!=1) { 
              	$scope.events1[i].className = ['green'];
              }
          }
          //callback($scope.events1);
		$timeout(function() {
			$('#demo-calendar').fullCalendar('removeEvents');

			$("#demo-calendar").fullCalendar('addEventSource', $scope.events1);
		}, 1000);

		$scope.prograssing = false;

		$('#demo-calendar').fullCalendar('today');

	});
	 */

	var vm = this;	
	vm.dtInstance = {};
	var DataObj = {};
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.order = "asc";
	DataObj.adnlFilters = [ {
		col : 1,
		filters : $scope.date
	} ];
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm, DataObj);
	vm.dtColumns = [
		DTColumnBuilder.newColumn('order_id').withTitle('Order Id')
		.notVisible().withOption('searchable', false),

		DTColumnBuilder.newColumn('closing_date').notVisible()
		.withOption('searchable', false),

		DTColumnBuilder.newColumn('order_no').withTitle('ORDER NO').renderWith(
				function(data, type, full, meta) {
					data = '<a id="rcd_edit" ng-click="show_table = false;show_form=true;" '
						+ 'style="cursor:pointer;"  ng-href="#">' + data + '</a>';
					return data;
				}),

				DTColumnBuilder.newColumn('closing_date').withTitle('DELIVERY DATE')
				.withOption('width', '250px').withOption('searchable', false).renderWith(
						function(data, type, full, meta) {
							return dateForm(data);
						}),

						DTColumnBuilder.newColumn('delevery_time').withTitle('DELIVERY TIME')
						.withOption('width', '250px').withOption('searchable', false),

						DTColumnBuilder.newColumn('is_ar_customer').withOption('searchable', false)
						.withTitle('ORDER TYPE').renderWith(
								function(data, type, full, meta) {
									if(data == 0){
										data = 'Department';
									}else if(data == 1){
										data = 'Customer';
									}else{
										data = 'Shop';
									}
									return data;
								}),

								DTColumnBuilder.newColumn('orderfrom').withTitle('ORDER FROM')
								.withOption('width', '350px').withOption('searchable',false),

								DTColumnBuilder.newColumn('status').withTitle('STATUS')
								.withOption('searchable', false).withOption('width','300px').renderWith(
										function(data, type, full, meta) {
											// data='<i class="fa fa-th-large " ></i>';
											var clas="greenColor";

											if(full.status==0)
											{
												clas="greenColor";
												data="PENDING";
											}
											else if(full.status==1)
											{
												clas="blueColor";
												data="VERIFIED";
											}
											else if(full.status==2)
											{
												clas="pinkColor";
												data="PRODUCTION";
											}
											else if(full.status==3)
											{
												clas="redColor";
												data="FINISHED ";
											}
											else if(full.status == 4)
											{
												clas="redColor";
												data="DELIVERED";
											}
											else if(full.status == 5)
											{
												clas="redColor";
												data="DELIVERED";
											}

											return '<a  class='+clas+'  ng-href="#">'
											+ data + '</a>';
										}),
										];


	$scope.isCodeExistisCust = function(code) {
		$http({
			url : '../customers/codeexisting',
			method : "GET",
			params : {
				code : code
			}
		}).then(function(response) {
			if (response.data == 1) {
				$scope.hide_code_existing_er = false;
				$scope.existing_code = '"' + code + '" Existing';
			} else if (response.data == 0) {
				$scope.hide_code_existing_er = true;
			}
		}, function(response) { // optional
		});
	}

	// Rowcallback fun for Get Edit Data when clk on Code
	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		$('a', nRow).unbind('click');
		$('a', nRow).bind(
				'click',
				function(e) {
					$scope.$apply(function() {
						$('tr.selected').removeClass('selected');
						if (e.target.id == "rcd_edit") {
							var rowData = aData;
							$(nRow).addClass('selected');
							var current_row_index = 0;
							var test = vm.dtInstance.DataTable.rows();
							for (var i = 0; i < test[0].length; i++) {
								if (test[0][i] == vm.dtInstance.DataTable.row(
								".selected").index()) {
									current_row_index = i;
								}
							}
							$rootScope.showSummary=false;
							$scope.edit(rowData);
						}

						if (e.target.id == "editcombo") {

							sendItemToProduction(aData.order_id);

						}
					});
				});
		return nRow;
	}

	$scope.goBackToTypePage=function(){
		$scope.show_formPlanning();
	}

	$scope.disable_adjst=true;
	$scope.finalizebttn = false;

	$scope.edit = function(data) {

		//$scope.checkDateValidity($scope.closing_date);


		$scope.hide_code_existing_er = true;

		$('#create_date').prop("disabled",true);
		$('#summaryBttn').hide();
		$('#show_form').val(2);
		$('#advsearchbox').hide();

		$scope.selectedDate1 = {
				orderhdrid : data.order_id,
				closing_date:getMysqlFormat($scope.closing_date)
		};
		$scope.order_id = data.order_id;

		$http({
			url : 'salesOrderData',
			async : false,
			params : $scope.selectedDate1,
			method : "GET",
		})
		.then(
				function(response) {
					$scope.balnceDataByDate=response.data.balanceByDate;

					$scope.customer_orders = {
							items : []
					};
					var date = get_date_format();

					$scope.customer_orders.items = response.data.orderhdrData;
					console.log($scope.customer_orders.items);
					$scope.setBalanceDataForFinalized($scope.customer_orders.items);
					if ($scope.customer_orders.items.length <= 0) {
						$scope.customer_orders.items.push({
							order_id : 0,
							stock_item_id : "",
							stock_item_code : "",
							stock_item_name : "",
							uomcode : "",
							quantity : 0,
							adjustqty:0,
							balanceqty:0,
							dtlorder_date : date.mindate,
							flag : 1
						});
					} else {

						$scope.departmentIds = response.data.orderhdrData[0].department_id;
						$scope.remarks = response.data.orderhdrData[0].remarks;
						$scope.slctypreq = $scope.customer_orders.items[0].is_ar_customer;
						if($scope.slctypreq == 1)
						{
							$scope.customerIds1=$scope.customer_orders.items[0].customerIds1;
							for (var i = 0; i < $scope.customer_orders.items.length; i++) {
								$scope.customer_orders.items[i].balanceqty=$scope.getBalanceQtyData($scope.customer_orders.items[i].stock_item_id);
							}
						}
						$scope.setBalanceDataForFinalized($scope.customer_orders.items);
						$scope.customerType = $scope.customer_orders.items[0].customer_type;

						$scope.custname = $scope.customer_orders.items[0].customer_name;
						$scope.address = $scope.customer_orders.items[0].customer_address;
						$scope.contact1 = $scope.customer_orders.items[0].customer_contact1;
						$scope.contact2 = $scope.customer_orders.items[0].customer_contact2;

						$('#companyId')
						.val(
								'string:'
								+ $scope.customer_orders.items[0].orderfrom);
						$scope.companyId=$scope.customer_orders.items[0].orderfrom;
						/*setCompnyName();*/
						$scope.PRDNO = $scope.customer_orders.items[0].order_no;
						$scope.selectedDate = geteditDateFormat($scope.customer_orders.items[0].order_date);

						var ordrTime = $scope.customer_orders.items[0].order_time;
						var first = ordrTime.split(".")[0];
						var timepart = first.split(" ")[1];

						var dt = new Date(
								$scope.customer_orders.items[0].order_time);
						$scope.Time = timepart;


						$scope.time_slot_id = $scope.customer_orders.items[0].closing_time_slot_id;

						$scope.closing_date = geteditDateFormat(response.data.orderhdrData[0].closing_date);

						for (var i = 0; i < $scope.customer_orders.items.length; i++) {
							$scope.customer_orders.items[i].dtlorder_date = geteditDateFormat($scope.customer_orders.items[i].dtlorder_date);
							if($scope.customer_orders.items[i].dtlremarks != '')
							{
								$scope.customer_orders.items[i].getColor =true;
							}else
							{
								$scope.customer_orders.items[i].getColor =false;
							}
							$("#items_table tr:nth-child("+(i+1)+") #adjustqty").attr('disabled',false);



							if(parseFloat($scope.customer_orders.items[i].is_adjst)>0)
							{
								$scope.customer_orders.items[i].is_adjst=true;
							}
							else
							{
								$scope.customer_orders.items[i].is_adjst=false;
							}

						}



					}
					$scope.show_addForm = true;
					$scope.show_table = false;
					$scope.show_calndr = false;

					$scope.show_salesForm = false;
					$scope.show_form = true;
					manageButtons("save");
					$scope.disable_all = true;

					//$('#summaryBttn').hide();
					$scope.disable_all_clos=true;


					$rootScope.clndrshow=false;
					$( "#dataTabform" ).removeClass( "col-sm-9" ).addClass( "col-sm-12" );


					if (response.data.orderhdrData[0].status == 0) {
						$scope.disable_all = false;
						$('#btnSave').show();
						$('#btnDiscard').show();
						$scope.finalizebttn = true;
						$scope.disable_adjst=false;

						$scope.disable_all_clos=false;
					} else {
						$scope.finalizebttn = false;
						$scope.disable_adjst=true;

						$scope.disable_all = true;
						$('#btnSave').hide();
						$('#btnDiscard').hide();
						$scope.disable_all_clos=true;
					}
				}

				, function(itemresponse) { // optional

				});

		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass(
		'selected');

	}

	$scope.print = function() {
		$scope.reportData = {
				selectedDate : getMysqlFormat($scope.closing_date),
				iscustomer : $scope.shoporder
		};
		if ($scope.orderitemtotal.length > 0) {
			$window.open("report?iscustomer=" + $scope.shoporder
					+ "&selectedDate=" + getMysqlFormat($scope.closing_date)+"&item_category="+$scope.item_category_id
					+ "", '_blank');
		} else {
			$mdDialog.show($mdDialog.alert()
					.parent(angular.element(document
							.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("No Item Available.").ok('Ok!').targetEvent(event));
		}
	}

	$scope.printRaw = function() {
		$scope.reportData = {
				selectedDate : getMysqlFormat($scope.closing_date),
				iscustomer : $scope.shoporder,
				item_category:$scope.item_category_id
		};
		if ($scope.orderitemtotal.length > 0) {
			$window.open("reportRaw?iscustomer=" + $scope.shoporder
					+ "&selectedDate=" + getMysqlFormat($scope.closing_date)+"&item_category="+$scope.item_category_id
					+ "", '_blank');
		} else {
			$mdDialog.show($mdDialog.alert()
					.parent(angular.element(document
							.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("No Item Available.").ok('Ok!').targetEvent(event));
		}
	}


	$scope.makeEnable = function(adjust) {

		if(settings['dailyprodview']==1)
		{

			if($scope.finalizebttn==true && $scope.slctypreq == 1)
			{
				disableValue=adjust;
			}
			else
			{
				disableValue=true;
			}
			return disableValue;

		}

		else
		{




			if($scope.finalizebttn==true )
			{
				disableValue=adjust;
			}
			else
			{
				disableValue=true;
			}
			return disableValue;



		}



	}



	$scope.setBalanceDataForFinalized=function(items){
		if(settings['dailyprodview']==0)
		{
			for(var i=0;i<$scope.customer_orders.items.length;i++)
			{
				for(var j=0;j<$scope.balnceDataByDate.length;j++)
				{
					if($scope.balnceDataByDate[j].stock_item_id ==$scope.customer_orders.items[i].stock_item_id
							&& $scope.balnceDataByDate[j].order_date == $scope.customer_orders.items[i].order_date
							&& $scope.balnceDataByDate[j].shop_id == $scope.customer_orders.items[i].customerIds1)
					{
						$scope.customer_orders.items[i].balanceqty=parseFloat($scope.balnceDataByDate[j].balance).toFixed(settings['decimalPlace']);;
						break;
					}
				}

			}

		}
		if(settings['dailyprodview']==1)
		{
			if($scope.slctypreq == 1)
			{


				for(var i=0;i<$scope.customer_orders.items.length;i++)
				{
					for(var j=0;j<$scope.balnceDataByDate.length;j++)
					{
						if($scope.balnceDataByDate[j].stock_item_id ==$scope.customer_orders.items[i].stock_item_id
								&& $scope.balnceDataByDate[j].order_date == $scope.customer_orders.items[i].order_date
								&& $scope.balnceDataByDate[j].shop_id == $scope.customer_orders.items[i].customerIds1)
						{
							$scope.customer_orders.items[i].balanceqty=parseFloat($scope.balnceDataByDate[j].balance).toFixed(settings['decimalPlace']);
							break;
						}
					}

				}

			}	

			else
			{
				for(var i=0;i<$scope.customer_orders.items.length;i++)
				{

					$scope.customer_orders.items[i].balanceqty=parseFloat($scope.customer_orders.items[i].balanceqty).toFixed(settings['decimalPlace']);

				}



			}



		}	



	}


	// info of table
	function infoCallback(settings, start, end, max, total, pre) { // function
		// for get
		// page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if (pageInfo.pages == 0) {
			return pageInfo.page + " / " + pageInfo.pages;
		} else {
			return pageInfo.page + 1 + " / " + pageInfo.pages;
		}
	}

	// reload Datatable
	$rootScope.$on('reloadDatatable', function(event) {

		$scope.reloadData();
	});

	$scope.reloadData = function() {

		vm.dtInstance.reloadData();
	}

	$('.header-view').hide();

	$rootScope
	.$on(
			"fun_discard_form",
			function(event) { // Discard function

				var confirm = $mdDialog.confirm(
						{
							onComplete : function afterShowAnimation() {
								var $dialog = angular.element(document
										.querySelector('md-dialog'));
								var $actionsSection = $dialog
								.find('md-dialog-actions');
								var $cancelButton = $actionsSection
								.children()[0];
								var $confirmButton = $actionsSection
								.children()[1];
								angular.element($confirmButton)
								.removeClass('md-focused');
								angular.element($cancelButton)
								.addClass('md-focused');
								$cancelButton.focus();
							}
						}).title(FORM_MESSAGES.DISCARD_WRNG)
						.targetEvent(event).cancel('No').ok('Yes');
				$mdDialog
				.show(confirm)
				.then(

						function() {
							var date =get_date_format();
							$scope.closing_date=date.mindate;
							$scope.closing_time = $scope.Time;
							if ($scope.order_id == undefined
									|| $scope.order_id == "") {
								$scope.remarks="";
								var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
									var $dialog = angular.element(document.querySelector('md-dialog'));
									var $actionsSection = $dialog.find('md-dialog-actions');
									var $cancelButton = $actionsSection.children()[0];
									var $confirmButton = $actionsSection.children()[1];
									angular.element($confirmButton).removeClass('md-focused');
									angular.element($cancelButton).addClass('md-focused');
									$cancelButton.focus();
								}}).title("Do You Want To Load The Pending Items?").targetEvent(event).cancel('No').ok(
								'Yes')

								;

								if ($scope.slctypreq != 1){

									//$scope.getShopData();
									$scope.companyId = $scope.shops[0].code;
									$scope.customerIds1="";
									$scope.getShopData();

									var isPendingExists=0;
									var shopId=getShopIdFromCode();
									$scope.selectedShop = {
											selectedShop : shopId,
											closing_date:getMysqlFormat($scope.closing_date)
									};
									$http({
										url : 'getShopBalanceData',
										async : false,
										params : $scope.selectedShop,
										method : "GET",
									}).then(function(response) {
										$scope.customer_orders.items = response.data.shopBalanceData;
										for(var i=0;i<$scope.customer_orders.items.length;i++)
										{
											$scope.customer_orders.items[i].dtlorder_date = date.mindate;
											$scope.customer_orders.items[i].adjustqty=0;

											$timeout(function() {$("#items_table tr:nth-child("+(i+1)+") #adjustqty").attr('disabled',true);}, 1);

										}
										if ($scope.customer_orders.items.length <= 0) {
											$scope.customer_orders.items.push({
												order_id : 0,
												stock_item_id : "",
												stock_item_code : "",
												stock_item_name : "",
												uomcode : "",
												quantity : 0,
												adjustqty:0,
												dtlorder_date : date.mindate,
												flag : 1
											});

											isPendingExists=0;
										}else
										{
											isPendingExists=1;
											if(isPendingExists == 1)
											{
												$mdDialog.show(confirm).then(function() {
													$scope.hide_code_existing_er = true;

													for (var i = 0; i < $scope.customer_orders.items.length; i++) {
														$scope.customer_orders.items[i].balanceqty=$scope.getBalanceQtyData($scope.customer_orders.items[i].stock_item_id);
													}

												}, function() {
													$scope.customer_orders = {
															items : []
													};
													$scope.customer_orders.items
													.push({

														order_id : 0,
														stock_item_id : "",
														stock_item_code : "",
														stock_item_name : "",
														uomcode : "",
														quantity : 0,
														adjustqty:0,
														dtlorder_date : date.mindate,
														flag : 1



													});

												});
											}else
											{


												$scope.customer_orders = {
														items : []
												};
												$scope.customer_orders.items
												.push({

													order_id : 0,
													stock_item_id : "",
													stock_item_code : "",
													stock_item_name : "",
													uomcode : "",
													quantity : 0,
													adjustqty:0,
													dtlorder_date : date.mindate,
													flag : 1



												});



											}
										}

									}

									, function(itemresponse) { // optional

									});


								}	else
								{


									$scope.customerIds1="";
									$scope.getCustomerData1();
									$scope.custname = "";
									$scope.address = "";
									$scope.contact1 = "";
									$scope.contact2 = "";



									$scope.customer_orders = {
											items : []
									};
									$scope.customer_orders.items
									.push({

										order_id : 0,
										stock_item_id : "",
										stock_item_code : "",
										stock_item_name : "",
										uomcode : "",
										quantity : 0,
										adjustqty:0,
										dtlorder_date : date.mindate,
										flag : 1



									});


								}


							} else {

								$scope.selectedDate1 = {
										orderhdrid : $scope.order_id,
										closing_date:getMysqlFormat($scope.closing_date)
								};

								$http(
										{
											url : 'salesOrderData',
											async : false,
											params : $scope.selectedDate1,
											method : "GET",
										})
										.then(
												function(
														response) {
													$scope.customer_orders = {
															items : []
													};
													var date = get_date_format();

													$scope.customer_orders.items = response.data.orderhdrData;

													if ($scope.customer_orders.items.length <= 0) {
														$scope.customer_orders.items
														.push({
															order_id : 0,
															stock_item_id : "",
															stock_item_code : "",
															stock_item_name : "",
															uomcode : "",
															quantity : 0,
															dtlorder_date : date.mindate,
															flag : 1
														});
													} else {
														$scope.remarks = response.data.orderhdrData[0].remarks;
														$scope.slctypreq = $scope.customer_orders.items[0].is_ar_customer;
														$scope.custname = $scope.customer_orders.items[0].customer_name;
														$scope.address = $scope.customer_orders.items[0].customer_address;
														$scope.contact1 = $scope.customer_orders.items[0].customer_contact1;
														$scope.contact2 = $scope.customer_orders.items[0].customer_contact2;

														$(
														'#companyId')
														.val(
																'string:'
																+ $scope.customer_orders.items[0].orderfrom);
														$scope.companyId=$scope.customer_orders.items[0].orderfrom;
														/*setCompnyName();*/
														$scope.PRDNO = $scope.customer_orders.items[0].order_no;
														$scope.selectedDate = geteditDateFormat($scope.customer_orders.items[0].order_date);
														var dt = new Date(
																$scope.customer_orders.items[0].order_time);
														var time = dt
														.getHours()
														+ ":"
														+ dt
														.getMinutes()
														+ ":"
														+ dt
														.getSeconds();

														$scope.Time = time;

														for (var i = 0; i < $scope.customer_orders.items.length; i++) {
															$scope.customer_orders.items[i].dtlorder_date = geteditDateFormat($scope.customer_orders.items[i].dtlorder_date);

															if(parseFloat($scope.customer_orders.items[i].is_adjst)>0)
															{
																$scope.customer_orders.items[i].is_adjst=true;
															}
															else
															{
																$scope.customer_orders.items[i].is_adjst=false;
															}


														}

													}

													$scope.show_table = false;
													$scope.show_calndr = false;
													$scope.show_addForm = true;
													$scope.show_salesForm = false;
													$scope.show_form = true;
													manageButtons("save");
													$(
													'#btnSave')
													.show();
													$(
													'#btnDiscard')
													.show();
												}

												,
												function(
														itemresponse) { // optional

												});

								$(
										vm.dtInstance.DataTable
										.row(
										".selected")
										.node())
										.removeClass('selected');
							}

						});
			});

	$scope.editOrderData = function(index) {
		if ($scope.disable_all == false) {
			$scope.dtlremarks = '';
			$scope.dtlremarks = $scope.customer_orders.items[index].dtlremarks;
			if($scope.dtlremarks != '')
			{
				$scope.customer_orders.items[index].getColor=true;
			}else
			{
				$scope.customer_orders.items[index].getColor=false;
			}
			$scope.indexordedtl = index;
			$('#orderData').modal('toggle');
		}

	}

	$scope.Addremarks = function() {
		$scope.customer_orders.items[$scope.indexordedtl].dtlremarks = $scope.dtlremarks;

	}

	$scope.filterStkitm = function() {
		//if ($scope.item_category_id != 0 && $scope.shoporder != 3) {
		if ($scope.shoporder != 3) {	
			$scope.orderTotal = false;

			for (var i = 0; i < $scope.stkfilterData.length; i++) {
				//if ($scope.stkfilterData[i].item_category_id == $scope.item_category_id) {
				if ($scope.stkfilterData[i].item_category_id != "") {

					$scope.orderTotal = true;
					break;

				} else {
					$scope.orderTotal = false;

				}
			}
		}
	}

	$("#DataTables_Table_0_filter").hide();
	$scope.customer_orders = {
			items : []
	};
	$scope.customer_orders.items.push({
		order_id : 0,
		stock_item_id : "",
		stock_item_code : "",
		stock_item_name : "",
		uomcode : "",
		quantity : 0,
		adjustqty:0,
		flag : 1
	});

	$scope.Finalize = function(event) {

		/*	if($scope.isFinLZ==true)
			{*/
		if ($scope.validation()) {
			var confirm = $mdDialog
			.confirm(
					{
						onComplete : function afterShowAnimation() {
							var $dialog = angular.element(document
									.querySelector('md-dialog'));
							var $actionsSection = $dialog
							.find('md-dialog-actions');
							var $cancelButton = $actionsSection.children()[0];
							var $confirmButton = $actionsSection.children()[1];
							angular.element($confirmButton).removeClass(
							'md-focused');
							angular.element($cancelButton).addClass(
							'md-focused');
							$cancelButton.focus();
						}
					}).title("Are you sure to finalize this data?")
					.targetEvent(event).cancel('No').ok('Yes');
			$mdDialog.show(confirm).then(
					function() {

						if ($scope.validation()) {

							if(balanceIsNotNull())
							{
								$("#form_div_cat").removeClass("has-error");

								$("#form_div_cat_error").hide();
								$("#form_div_shop").removeClass("has-error");

								$("#form_div_shop_error").hide()


								//	var q = $("#companyId option:selected").text();
								var shopcode=$scope.companyId;
								var shopId1=getShopIdFromCode();

								var currentdate = new Date(getMysqlFormat($scope.selectedDate)
										+ ' ' + getMySqlTimeFormat($scope.Time));

								var datetime = +currentdate.getFullYear() + "-"
								+ (currentdate.getMonth() + 1) + "-"
								+ currentdate.getDate() + " " + currentdate.getHours()
								+ ":" + currentdate.getMinutes() + ":"
								+ currentdate.getSeconds();

								/*var closing_time = new Date(getMysqlFormat($scope.closing_date)
								+ ' ' + getMySqlTimeFormat($scope.closing_time));

						var closing_time1 = +closing_time.getFullYear() + "-"
						+ (closing_time.getMonth() + 1) + "-"
						+ closing_time.getDate() + " " + closing_time.getHours()
						+ ":" + closing_time.getMinutes() + ":"
						+ closing_time.getSeconds();*/

								$scope.formData = {
										customer_name : $scope.custname,
										closing_date : getMysqlFormat($scope.closing_date),
										closing_time_slot_id : $scope.time_slot_id,
										customer_address : $scope.address,
										customer_contact1 : $scope.contact1,
										customer_contact2 : $scope.contact2,
										is_ar_customer : $scope.slctypreq,
										user_id : strings['userID'],
										created_at : datetime,
										order_id : $scope.order_id,
										created_by : strings['userID'],
										order_date : getMysqlFormat($scope.selectedDate),
										order_no : $scope.PRDNO,
										order_time : datetime,
										remarks : $scope.remarks,
										shop_code : shopcode,
										shopId1:shopId1,
										status : '0',
										sync_status : 0,
										total_amount : 0,
										total_amount_paid : 0,
										total_balance : 0,
										updated_at : datetime,
										updated_by : strings['userID']
								}

								if($scope.slctypreq == 1)
								{
									$scope.formDatacust={
											id:$scope.customerIds1,
											code:$scope.cust_code,
											name:$scope.custname,
											customer_type:$scope.customerType,
											address:$scope.address,
											is_ar:0,
											is_valid:1,
											created_by : strings['userID'],
											created_at : datetime,
											phone:$scope.contact1
									}

								}
								else
								{
									$scope.formDatacust={};
									$scope.formData.customer_name="";
									$scope.formData.customer_address="";
									$scope.formData.customer_contact1="";
									$scope.formData.customer_contact2="";

								}

								if($scope.slctypreq == 0)
								{
									$scope.formData.department_id = $scope.departmentIds;
									$scope.formData.shop_code=$scope.getDepartmentCode($scope.formData.department_id);
									//$scope.formData.shop_id=$scope.departmentIds;
									$scope.formData.customer_id=$scope.departmentIds;
									$scope.slctypreq = 0;

								}



								if($scope.slctypreq == 1)
								{
									$scope.formData.shop_code=null;
									$scope.formData.shopId1=null;
									$scope.formData.customer_id=$scope.customerIds1;
								}else if($scope.slctypreq != 0)
								{
									//if($scope.formData.department_id == "" && $scope.formData.department_id == undefined){
									$scope.formData.customer_id=getShopIdFromCode();
									//}

								}
								for (var i = 0; i < $scope.customer_orders.items.length; i++) {
									$scope.customer_orders.items[i].dtlorder_date = getMysqlFormat($scope.customer_orders.items[i].dtlorder_date);
								}
								$scope.formData.planningDetailLists = JSON
								.stringify($scope.customer_orders.items);	
								$scope.orderid = {
										orderid : $scope.order_id
								};

								$scope.formData.customerList = JSON
								.stringify($scope.formDatacust);

								$http({
									url : 'updateStatus',
									async : false,
									params : $scope.orderid,
									data:$scope.formData,
									method : "POST",
								}).then(
										function(response) {
											$scope.formDatacust={};

											if(response.data != 0){
												$scope.finalizebttn = false;
												$scope.disable_adjst=true;
												$scope.reloadData();
												$rootScope.$broadcast('on_AlertMessage_SUCC',
												"Data  Finalize Successfully");
												$scope.reloadBalnceOnFinalize();
												$('#btnSave').hide();
												$('#btnDiscard').hide();
												$scope.disable_all = true;
												$scope.disable_all_clos=true;
												$(".acontainer input").attr('disabled', true);




												$http({
													url : 'getcustomers',
													async : false,
													method : "GET",
												}).then(function(response) {
													$scope.customerIds =response.data.customerIds;
													$scope.customerIds.splice(0,0,{id : "" ,name : "NEW"});

												});


											}else
											{
												$mdDialog.show($mdDialog.alert().parent(
														angular.element(document
																.querySelector('#dialogContainer')))
																.clickOutsideToClose(true).textContent(
																"Finalize failed.").ok('Ok!').targetEvent(
																		event));
												$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
											}});
							}}
					});
		}

		//	}

		/*else
			{



			var confirm = $mdDialog
			.confirm(
					{
						onComplete : function afterShowAnimation() {
							var $dialog = angular.element(document
									.querySelector('md-dialog'));
							var $actionsSection = $dialog
							.find('md-dialog-actions');
							var $cancelButton = $actionsSection.children()[0];
							var $confirmButton = $actionsSection.children()[1];
							angular.element($confirmButton).removeClass(
							'md-focused');
							angular.element($cancelButton).addClass(
							'md-focused');
							$cancelButton.focus();
						}
					}).title("Previous Pending Orders Should be Finalized ")
					.targetEvent(event).cancel('Cancel').ok('Ok');
			$mdDialog.show(confirm).then(
					function() {
						$scope.show_addForm =false;
						$scope.show_table = true;
						$scope.show_form =false;
						$scope.show_formPlanning();
						$rootScope.clndrshow=true;
						$( "#dataTabform" ).removeClass( "col-sm-12" ).addClass( "col-sm-9" );

					});






			}*/
	}

	function balanceIsNotNull()
	{
		var flg=true;
		for (var i = 0; i < $scope.customer_orders.items.length; i++) {
			if($scope.customer_orders.items[i].balanceqty == null ||
					$scope.customer_orders.items[i].balanceqty==undefined ||
					$scope.customer_orders.items[i].balanceqty =="") {

				$("#items_table tr:nth-child(" + (i + 1) + ")")
				.find("#balanceqty").select();
				$rootScope.$broadcast('on_AlertMessage_ERR',
				"Balance Cannot Be Left Blank");

				flg=false;
				break;

			}
			else if($scope.customer_orders.items[i].adjustqty == null ||
					$scope.customer_orders.items[i].adjustqty == undefined ||
					$scope.customer_orders.items[i].adjustqty == "")
			{
				$("#items_table tr:nth-child(" + (i + 1) + ")")
				.find("#adjustqty").select();
				$rootScope.$broadcast('on_AlertMessage_ERR',
				"Adjust Quantity Cannot Be Left Blank");

				flg=false;
				break;
			}
			else if($scope.customer_orders.items[i].adjustqty < -($scope.customer_orders.items[i].balanceqty))
			{
				$("#items_table tr:nth-child(" + (i + 1) + ")")
				.find("#adjustqty").select();
				$rootScope.$broadcast('on_AlertMessage_ERR',
				"Please Enter A Valid Adjust Quantity");
				flg=false;
				break;
			}

		}
		return flg;
	}

	$rootScope.$on("fun_clear_form", function() {
		$('#summaryBttn').hide();		
		$('#form_div_cat').hide();
		$('#show_form').val(2);
		$scope.show_addForm = true;
		$scope.finalizebttn = false;
		$scope.disable_adjst=true;

		$('.header-view').hide();
		$('#advsearchbox').hide();
		$scope.disable_all = false;
		$scope.disable_all_clos=true;
		$scope.hide_code_existing_er = true;

		$scope.order_id = null;
		$scope.custname = "";
		$scope.customerType="0";
		$scope.cust_code="";
		$scope.address = "";
		$scope.contact1 = "";
		$scope.contact2 = "";
		var date = get_date_format();
		$scope.selectedDate = date.mindate;
		
		$scope.min = new Date();

		$scope.minDate = $scope.min.setDate($scope.min.getDate()-15);
		
		var dt = new Date();
		var time = dt.getHours() + ":" + dt.getMinutes() + ":"
		+ dt.getSeconds();
		$scope.Time = time;

		$scope.closing_time = $scope.Time;

		$scope.slctypreq = 2;
		$scope.remarks = '';
		$scope.customer_orders = {
				items : []
		};
		$scope.customer_orders.items.push({
			order_id : 0,
			stock_item_id : "",
			stock_item_code : "",
			stock_item_name : "",
			uomcode : "",
			quantity : 0,
			adjustqty:0,
			dtlorder_date : date.mindate,
			flag : 1
		});

		$scope.fun_get_PRDno();
		$scope.show_table = false;
		$scope.show_calndr = false;

		$scope.show_salesForm = false;
		$scope.closing_date = geteditDateFormat($scope.delivery_date);
		$scope.show_form = true;
		manageButtons("save");
		$('#btnSave').show();
		$('#btnDiscard').show();
		/*$('#companyId').val(-1);*/
		$scope.companyId = $scope.shops[0].code;
		//$('#companyId').val(0);
		$scope.getShopData();
		$rootScope.showSummary=false;
		$rootScope.clndrshow=false;
		$scope.time_slot_id = "";
		$( "#dataTabform" ).removeClass( "col-sm-9" ).addClass( "col-sm-12" );
		$scope.delivery_date = moment(new Date()).add(1,'days').format('YYYY-MM-DD');
		$scope.closing_date = geteditDateFormat($scope.delivery_date);	

	});

	$scope.salesAddEdit = function() {
		$scope.customer_orders = {
				items : []
		};
		$scope.customer_orders.items.push({
			order_id : 0,
			stock_item_id : "",
			stock_item_code : "",
			stock_item_name : "",
			uomcode : "",
			quantity : 0,
			adjustqty:0,
			flag : 1
		});

		$scope.fun_get_PRDno();
		$scope.show_table = true;
		$scope.show_calndr = false;
		$scope.show_addForm = false;
		$scope.show_salesForm = false;
		$scope.show_form = false;
		$rootScope.showSummary=true;

		$scope.item_category_id = 0;
		manageButtons("add");
		//$('#companyId').val(0);

		//$('#btnBackprod').show();
		$scope.reloadData();
		$("#DataTables_Table_0_filter").hide();
		$('#advsearchbox').show();
	}



	$scope.clearfields = function() {

		if($scope.disable_all==false){



			if ($scope.slctypreq == 1) {
				$scope.customerIds1="";
				$scope.getCustomerData1();
				$scope.custname = "";
				$scope.cust_code="";
				$scope.customerType="0";
				$scope.address = "";
				$scope.contact1 = "";
				$scope.contact2 = "";


				for (var i = 0; i < $scope.customer_orders.items.length; i++) {
					$scope.customer_orders.items[i].balanceqty=$scope.getBalanceQtyData($scope.customer_orders.items[i].stock_item_id);
				}
			} else if ($scope.slctypreq == 2) {
				debugger	
				$scope.companyId = $scope.shops[0].code;
				$scope.customerIds1="";
				$scope.getShopData();
				for (var i = 0; i < $scope.customer_orders.items.length; i++) {
					$scope.customer_orders.items[i].balanceqty=$scope.getBalanceQtyData($scope.customer_orders.items[i].stock_item_id);
				}
			} else if ($scope.slctypreq == 0) {

				$scope.departmentIds = "";
				$scope.customerIds1="";
				$scope.getCustomerData1();
				$scope.custname = "";
				$scope.cust_code="";
				$scope.customerType="0";
				$scope.address = "";
				$scope.contact1 = "";
				$scope.contact2 = "";
				$scope.companyId = $scope.shops[0].code;

				for (var i = 0; i < $scope.customer_orders.items.length; i++) {
					$scope.customer_orders.items[i].balanceqty=$scope.getBalanceQtyData($scope.customer_orders.items[i].stock_item_id);
				}

			}

		}
	}

	$scope.orderedData = function() {

		//$scope.item_category_id = 0;
		$scope.selectedDate1 = {
				closing_date : getMysqlFormat($scope.closing_date),
				iscustomer : $scope.shoporder
		};
		$scope.stkfilterData = [];
		$scope.orderitemtotal = [];
		$http({
			url : 'orderhdrDateData',
			async : false,
			params : $scope.selectedDate1,
			method : "GET",
		}).then(function(response) {
			$scope.stkfilterData = response.data.orderhdrDateData;
			$scope.orderitemtotal = response.data.orderitemtotal;
			if ($scope.orderitemtotal.length <= 0) {
				$scope.orderTotal = false;
			} else {
				$scope.orderTotal = true;
			}

		}

		, function(itemresponse) { // optional

		});
	}

	$scope.orderTotal = true;

	$scope.shoporder = 0;

	$scope.validation = function() {
		if ($scope.customer_orders.items[$scope.customer_orders.items.length - 1].stock_item_id == ""
			&& $scope.customer_orders.items.length != 1) {
			$scope.customer_orders.items.splice(
					$scope.customer_orders.items.length - 1, 1);

		}
		flg = true;


		if ($scope.time_slot_id == "") {
			$("#form_div_time_slot").addClass("has-error");
			$("#form_div_time_slot_error").show();

			flg = false;
			//return false;
		} else {
			$("#form_div_time_slot").removeClass("has-error");
			$("#form_div_time_slot_error").hide();
		}




		$scope.company = $('#companyId').val();
		var clsDate=process($scope.closing_date);
		var sltDate=process($scope.selectedDate);


		if (clsDate < sltDate && clsDate != 'Invalid Date') {
			flg = false;
			$rootScope.$broadcast('on_AlertMessage_ERR',
			"Deliver date cannot be less than order date");
			$('#create_date').focus();
		}else if(clsDate == 'Invalid Date')
		{
			flg = false;
			$rootScope.$broadcast('on_AlertMessage_ERR',
			"Please Enter Valid Delivery Date");
			$('#create_date').focus();
		}

		else if ($scope.slctypreq == 2)
		{
			if($scope.companyId=="" || $scope.companyId==undefined )
			{
				$rootScope.$broadcast('on_AlertMessage_ERR',
				"Please Select Shop");
				flg=false;
			}
		}

		//else if ($scope.slctypreq != 2) {
		else if ($scope.slctypreq == 1) {
			if($scope.customerIds1 =="" || $scope.customerIds1 ==undefined)
			{
				if($scope.cust_code =="" || $scope.cust_code ==undefined)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',
					"code cannot be blank");
					flg=false;
				}
				else
				{

					if (!$scope.hide_code_existing_er) {
						flg = false;
						$("#cust_code").select();
					}



				}

			}

			if ($scope.custname == undefined || $scope.custname == "") {
				$rootScope.$broadcast('on_AlertMessage_ERR',
				"Name cannot be blank");

				$("#name").focus();
				flg = false;
			} else if ($scope.address == undefined || $scope.address == "") {
				$rootScope.$broadcast('on_AlertMessage_ERR',
				"Address cannot be blank");
				flg = false;
			} else if ($scope.contact1 == undefined || $scope.contact1 == "") {
				$rootScope.$broadcast('on_AlertMessage_ERR',
				"Contact number  cannot be blank");
				flg = false;
			}
		}
		else if ($scope.slctypreq == 0) {


			if ($scope.departmentIds == undefined || $scope.departmentIds == "") {
				$rootScope.$broadcast('on_AlertMessage_ERR',
				"Department cannot be blank");

				flg = false;
			} 
		}

		else if ($scope.company == -1 || $scope.company == null) {
			$rootScope
			.$broadcast('on_AlertMessage_ERR', "Shop cannot be blank");
			flg = false;
		}

		if ($scope.customer_orders.items[$scope.customer_orders.items.length - 1].stock_item_id != undefined
				&& $scope.customer_orders.items[$scope.customer_orders.items.length - 1].stock_item_id != ""
					|| $scope.customer_orders.items.length == 1) {
			if ($scope.customer_orders.items.length > 0) {
				var count = 0;
				for (var i = 0; i < $scope.customer_orders.items.length; i++) {

					if ($scope.customer_orders.items[i].stock_item_id != undefined
							&& $scope.customer_orders.items[i].stock_item_id != "") {
						count = 0;
						if ($scope.customer_orders.items[i].quantity == ""
							|| $scope.customer_orders.items[i].quantity <= 0
							|| $scope.customer_orders.items[i].quantity == undefined 
							|| $scope.customer_orders.items[i].quantity== "." || $scope.customer_orders.items[i].quantity.toString().split(".")[0].length>9) {
							$rootScope.$broadcast('on_AlertMessage_ERR',
									ITEM_TABLE_MESSAGES.QTY_ERR);
							$("#items_table tr:nth-child(" + (i + 1) + ")")
							.find("#qty").select();

							flg = false;
							break;
						}
					} else {
						count = 1;
						$(
								"#items_table tr:nth-child(" + (i + 1)
								+ ") td:nth-child(" + (2) + ")").find(
								".acontainer input").select();
						$rootScope.$broadcast('on_AlertMessage_ERR',
								ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);

						flg = false;
						break;
					}

					if(parseFloat($scope.customer_orders.items[i].adjustqty)>0 || parseFloat($scope.customer_orders.items[i].adjustqty)<0 )

					{
						if((parseFloat($scope.customer_orders.items[i].adjustqty)>parseFloat($scope.customer_orders.items[i].balanceqty)) || parseFloat($scope.customer_orders.items[i].adjustqty)<0 )
						{

							$rootScope.$broadcast('on_AlertMessage_ERR',"Incorrect adjust quantity");
							$("#items_table tr:nth-child(" + (i + 1) + ")")
							.find("#adjustqty").select();
							flg = false;
							break;


						}

					}

				}
			}

			if ($scope.customer_orders.items.length == 0) {
				$rootScope.$broadcast('on_AlertMessage_ERR',
						ITEM_TABLE_MESSAGES.TABLE_ERR);
				flg = false;
			}
			if (flg == false) {
				focus();
			}

		} else {
			$scope.customer_orders.items.splice(
					$scope.customer_orders.items - 1, 1);

		}
		return flg;

	}

	$scope.saveData = function(event) {

		if ($scope.validation()) {
			$("#btnSave").prop('disabled', true);


			$("#form_div_cat").removeClass("has-error");

			$("#form_div_cat_error").hide();
			$("#form_div_shop").removeClass("has-error");

			$("#form_div_shop_error").hide()

			//added by udhayan for storing shop code in the order_hdrs_booking table on 26-Oct-2021
			//	var q = $("#companyId option:selected").text();
			if ($scope.slctypreq == 2) {
				var shopcode=$scope.companyId;				
			}
			else if($scope.slctypreq == 1){
				var shopcode=$scope.customerShopcode;	
			}

			var shopId1=getShopIdFromCode();

			var currentdate = new Date(getMysqlFormat($scope.selectedDate)
					+ ' ' + getMySqlTimeFormat($scope.Time));

			var datetime = +currentdate.getFullYear() + "-"
			+ (currentdate.getMonth() + 1) + "-"
			+ currentdate.getDate() + " " + currentdate.getHours()
			+ ":" + currentdate.getMinutes() + ":"
			+ currentdate.getSeconds();

			/*var closing_time = new Date(getMysqlFormat($scope.closing_date)
					+ ' ' + getMySqlTimeFormat($scope.closing_time));*/

			/*var closing_time1 = +closing_time.getFullYear() + "-"
			+ (closing_time.getMonth() + 1) + "-"
			+ closing_time.getDate() + " " + closing_time.getHours()
			+ ":" + closing_time.getMinutes() + ":"
			+ closing_time.getSeconds();*/




			$scope.formData = {
					customer_name : $scope.custname,
					closing_date : getMysqlFormat($scope.closing_date),
					//closing_time : closing_time1,
					closing_time_slot_id : $scope.time_slot_id,
					customer_address : $scope.address,
					customer_contact1 : $scope.contact1,
					customer_contact2 : $scope.contact2,
					is_ar_customer : $scope.slctypreq,
					user_id : strings['userID'],
					created_at : datetime,
					order_id : $scope.order_id,
					created_by : strings['userID'],
					order_date : getMysqlFormat($scope.selectedDate),
					order_no : $scope.PRDNO,
					order_time : datetime,
					remarks : $scope.remarks,
					shop_code : shopcode,
					shopId1:shopId1,
					status : '1',
					sync_status : 0,
					total_amount : 0,
					total_amount_paid : 0,
					total_balance : 0,
					updated_at : datetime,
					updated_by : strings['userID'],
					department_id : $scope.departmentIds
			}


			if($scope.slctypreq == 1)
			{
				$scope.formDatacust={
						id:$scope.customerIds1,
						code:$scope.cust_code,
						name:$scope.custname,
						customer_type:$scope.customerType,
						address:$scope.address,
						is_ar:0,
						is_valid:1,
						created_by : strings['userID'],
						created_at : datetime,
						phone:$scope.contact1
				}

			}
			else
			{
				$scope.formDatacust={};
				$scope.formData.customer_name="";
				$scope.formData.customer_address="";
				$scope.formData.customer_contact1="";
				$scope.formData.customer_contact2="";
				$scope.formData.department_id = "";

			}

			if($scope.slctypreq == 0)
			{
				$scope.formData.department_id = $scope.departmentIds;
				$scope.formData.shop_code=$scope.getDepartmentCode($scope.formData.department_id);
				//$scope.formData.shop_code=$scope.formData.department_id;
				$scope.slctypreq = 0;

			}

			if($scope.slctypreq == 1 )
			{
				//$scope.formData.shop_code=null;
				$scope.formData.shop_code = shopcode;
				$scope.formData.shopId1=null;
				$scope.formData.customer_id=$scope.customerIds1;
			}else
			{
				$scope.formData.customer_id=getShopIdFromCode();
			}

			for (var i = 0; i < $scope.customer_orders.items.length; i++) {
				$scope.customer_orders.items[i].dtlorder_date = getMysqlFormat($scope.customer_orders.items[i].dtlorder_date);
			}
			$scope.formData.planningDetailLists = JSON
			.stringify($scope.customer_orders.items);

			$scope.formData.customerList = JSON
			.stringify($scope.formDatacust);

			$http({
				url : 'saveStockItem',
				method : "POST",
				async : false,
				//	params : $scope.formDatacust,
				data : $scope.formData
			}).then(
					function(response) {
						if(response.data == 1){
							$scope.hide_code_existing_er = true;
							$("#btnSave").prop('disabled', false);
							$timeout(function(){$rootScope.$broadcast('changeview_details')},1);

							$scope.reloadData();
							location.reload();

							manageButtons("add");
							//$('#companyId').val(0);

							//$('#btnBackprod').show();
							$scope.show_table = true;
							$scope.show_calndr = false;
							$scope.show_addForm = false;
							$scope.show_salesForm = false;
							$scope.show_form = false;
							$scope.formDatacust={};

							$scope.remarks = '';
							$('#advsearchbox').show();
							geteditDateFormat($scope.selectedDate);
							$rootScope.clndrshow=true;
							$( "#dataTabform" ).removeClass( "col-sm-12" ).addClass( "col-sm-9" );

							if ($scope.order_id == ""
								|| $scope.order_id == undefined) {
								$rootScope.$broadcast('on_AlertMessage_SUCC',
										FORM_MESSAGES.SAVE_SUC);
								$scope.showSummary=true;

							} else {
								$rootScope.$broadcast('on_AlertMessage_SUCC',
										FORM_MESSAGES.UPDATE_SUC);
								$scope.disable_all_clos=true;

							}



							$http({
								url : 'getcustomers',
								async : false,
								method : "GET",
							}).then(function(response) {
								$scope.customerIds =response.data.customerIds;
								$scope.customerIds.splice(0,0,{id : "" ,name : "NEW"});

							});
						}
						
						else{
							$("#btnSave").prop('disabled', false);
							$mdDialog.show($mdDialog.alert().parent(
									angular.element(document
											.querySelector('#dialogContainer')))
											.clickOutsideToClose(true).textContent(
											"Save failed.").ok('Ok!').targetEvent(
													event));
							$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
						}
						
					},
					function(response) { // optional
						$mdDialog.show($mdDialog.alert().parent(
								angular.element(document
										.querySelector('#dialogContainer')))
										.clickOutsideToClose(true).textContent(
										"Save failed.").ok('Ok!').targetEvent(
												event));
						$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
					});

		}
	}


	$scope.getDepartmentCode = function(id){
		var deptCode = "";

		//$scope.getDeptData();

		for(var i=0; i<$scope.departmentsList.length; i++){
			if($scope.departmentsList[i].id == id){
				deptCode = $scope.departmentsList[i].code;
			}
		}
		return deptCode;

	}


	function getShopIdFromCode()
	{
		var compId="";
		for(var i=0;i<$scope.shops.length;i++)
		{
			if($scope.shops[i].code == $scope.companyId)
			{
				compId=$scope.shops[i].shop_id;
			}
		}
		return compId;
	}

	$rootScope.$on('fun_save_data', function(event) {
		$scope.saveData(event);
	});

	$scope.timeSlot=[];

	$scope.loadFormData = function() {
		$scope.prograssing1=true;  
		$http({
			url : 'formJsonData',
			async : false,
			method : "GET",
		}).then(function(response) {

			var dataitemcat = response.data.itmcatData;
			dataitemcat.unshift({
				id : 0,
				name : "All"
			});
			$scope.ItemCtgry = dataitemcat;
			$scope.timeSlot=response.data.timeSlot;
			$scope.shops = response.data.customerData;
			$scope.stockData = response.data.itemsData;
			$scope.balanceQtyData=response.data.balanceQtyData;
			console.log($scope.balanceQtyData);
			$scope.customerIds =response.data.customerIds;
			$scope.customerTypes =response.data.customerTypes;
			$scope.departmentList =response.data.departmentData;
			$scope.customerType="0";
			/*$scope.customerTypes.splice(0,0,{id : "" ,code : "select"});*/
			$scope.customerIds.splice(0,0,{id : "" ,name : "NEW"});
			$scope.shops.splice(0,0,{id : "" ,name : "select"});
			$scope.timeSlot.splice(0,0,{id : "" ,name : "select"});
			$scope.departmentList.splice(0,0,{id : "" ,name : "select"});
			$scope.time_slot_id="";	       
			$scope.prograssing1=false;

		});

	}
	$scope.getBalanceQtyData=function(stockItmId)
	{
		if(settings['dailyprodview']==0)
		{

			var balanceQty=0;
			var shopId=getShopIdFromCode();
			if ($scope.slctypreq == 1)
			{
				shopId=$scope.customerIds1;
			}
			for(var i=0;i<$scope.balanceQtyData.length;i++)
			{
				if($scope.balanceQtyData[i].stock_item_id==stockItmId && $scope.balanceQtyData[i].shop_id==shopId)
				{
					balanceQty=$scope.balanceQtyData[i].balance_qty;
				}
			}
			balanceQty=parseFloat(balanceQty).toFixed(settings['decimalPlace']);
			return balanceQty;

		}

		if(settings['dailyprodview']==1)
		{

			if($scope.slctypreq == 1)
			{
				var balanceQty=0;
				var shopId=getShopIdFromCode();
				if ($scope.slctypreq == 1)
				{
					shopId=$scope.customerIds1;
				}
				for(var i=0;i<$scope.balanceQtyData.length;i++)
				{
					if($scope.balanceQtyData[i].stock_item_id==stockItmId && $scope.balanceQtyData[i].shop_id==shopId)
					{
						balanceQty=$scope.balanceQtyData[i].balance_qty;
					}
				}
				balanceQty=parseFloat(balanceQty).toFixed(settings['decimalPlace']);
				return balanceQty;

			}else if($scope.slctypreq == 2)
			{
				var balanceQty=0;
				var shopId=getShopIdFromCode();
				if ($scope.slctypreq == 1)
				{
					shopId=$scope.customerIds1;
				}
				for(var i=0;i<$scope.balanceQtyData.length;i++)
				{
					if($scope.balanceQtyData[i].stock_item_id==stockItmId && $scope.balanceQtyData[i].shop_id==shopId)
					{
						balanceQty=$scope.balanceQtyData[i].balance_qty;
					}
				}
				balanceQty=parseFloat(balanceQty).toFixed(settings['decimalPlace']);
				return balanceQty;

			}
			else
			{

				return parseFloat(0).toFixed(settings['decimalPlace']);

			}




		}

	}


	$scope.setAdjustValue=function(stockItmId)
	{
		var is_adjst=false;

		var shopId=getShopIdFromCode();
		if ($scope.slctypreq == 1)
		{
			shopId=$scope.customerIds1;
		}


		for(var i=0;i<$scope.balanceQtyData.length;i++)
		{
			if($scope.balanceQtyData[i].stock_item_id==stockItmId && $scope.balanceQtyData[i].shop_id==shopId)
			{
				is_adjst=($scope.balanceQtyData[i].is_adjst>0)?true:false;
				break;
			}

		}
		return is_adjst;


	}



	$scope.reloadBalnceOnFinalize=function()
	{

		$http({
			url : 'getBalnceQty',
			async : true,
			method : "GET",
			params:{closing_date:getMysqlFormat($scope.closing_date)},
		}).then(function(response) {
			$scope.balanceQtyData=response.data.balanceQtyForItem;


			for(var i=0;i<$scope.balanceQtyData.length;i++)
			{
				if(parseFloat($scope.balanceQtyData[i].is_adjst)>0)
				{
					$scope.balanceQtyData[i].is_adjst=true;
				}
				else
				{
					$scope.balanceQtyData[i].is_adjst=false;
				}
			}


		}

		, function(itemresponse) { // optional


		});

	}



	$('#advsearchbox').hide();



	/*//for calender


	$scope.selectedDays3 = [];

	$scope.oneDaySelectionOnly = function (event, date) {



	    $scope.selectedDays3.length = 0;
	    var date1 = date.date._d;
	    var curr_date = date1.getDate();
	    var curr_month = date1.getMonth() + 1; //Months are zero based
	    var curr_year = date1.getFullYear();
	    var clkdate=curr_year + "-" + curr_month + "-" + curr_date;
	    $scope.checkDateValidity(clkdate);

	};
	 */

	$rootScope.$on('checkvalidData', function(event,clickdate) {
		$scope.closing_date = geteditDateFormat(clickdate);
		$scope.checkDateValidity($scope.closing_date);

	});


	$scope.checkDateValidity=function(clickdate)
	{
		/*$scope.isDateValid=true;
		$scope.isFinLZ=true;
		$scope.closing_date = clickdate;
		$scope.closing_date_original=clickdate;
		var dateCurrent; 
		dateCurrent = {
				dateCurrent : getMysqlFormat($scope.closing_date)
		};
		$http({
			url : 'checkDateValidity',
			async : false,
			params : dateCurrent,
			method : "GET",
		})
		.then(
				function(response) {
					$scope.isFinLZ=(response.data.isValid==false)?false:true;
					$scope.isDateValid=(response.data.isDateValid=="false")?false:true;
					if(response.data.isDateValid==false)
						{
						 $('#btnAdd').hide();
						 $('#btn_finalize').hide();
						}
					else
						{
						 $('#btnAdd').show();
						 $('#btn_finalize').show();
						}
				}

				, function(itemresponse) { // optional

				});
		 */

		/*$scope.checkVal=function()
		{



			var dateCurrent; 
			dateCurrent = {
					dateCurrent : getMysqlFormat($scope.closing_date)
			};
			$http({
				url : 'checkDateValidity',
				async : false,
				params : dateCurrent,
				method : "GET",
			})
			.then(
					function(response) {
						$scope.dateValidation=(response.data.isDateValid==false)?false:true;

						if($scope.dateValidation==false)
							{
							$scope.closing_date=$scope.closing_date_original;


							var confirm = $mdDialog.confirm(
									{
										onComplete : function afterShowAnimation() {
											var $dialog = angular.element(document
													.querySelector('md-dialog'));
											var $actionsSection = $dialog
											.find('md-dialog-actions');
											var $cancelButton = $actionsSection.children()[0];
											var $confirmButton = $actionsSection.children()[1];
											angular.element($confirmButton).removeClass(
											'md-focused');
											angular.element($cancelButton).addClass(
											'md-focused');
											$cancelButton.focus();
										}
									}).title("Invalid Date").targetEvent(event)
									.cancel('Cancel').ok('Ok');
							$mdDialog.show(confirm).then(
									function() {

										$scope.closing_date="";

									});



							}

					});

		}

		 */


		$scope.showOnDayClick(clickdate);
	}

	$scope.checkVal=function(){

		var closing_date=[];
		$scope.selectedDate = {
				closing_date : getMysqlFormat($scope.closing_date),
				iscustomer : 0
		};
		$http({

			url:'orderhdrDateData',
			params:$scope.selectedDate,
			method:'GET',
		}).then(function(response){

			$scope.stkfilterData = response.data.orderhdrDateData;
			$scope.orderitemtotal = response.data.orderitemtotal;
			if ($scope.orderitemtotal.length <= 0) {
				$scope.orderTotal = false;
			} else {
				$scope.orderTotal = true;
			}
		});
	}
	//check validation initial call
	/*$scope.checkVali=function()
	{
		$scope.isDateValid=true;
		$scope.isFinLZ=true;

		var dateCurrent; 
		dateCurrent = {
				dateCurrent : getMysqlFormat($scope.closing_date)
		};
		$http({
			url : 'checkDateValidity',
			async : false,
			params : dateCurrent,
			method : "GET",
		})
		.then(
				function(response) {
					$scope.isFinLZ=(response.data.isValid==false)?false:true;
					$scope.isDateValid=(response.data.isDateValid=="false")?false:true;
					if(response.data.isDateValid==false)
						{
						 $('#btnAdd').hide();
						 $('#btn_finalize').hide();
						}
					else
						{
						 $('#btnAdd').show();
						 $('#btn_finalize').show();
						}
				}

				, function(itemresponse) { // optional

				});

	}
	$scope.checkVali();*/

	$scope.loadFormData();




	$scope.tableShow=function()
	{
		$('#show_form').val(1);

		$('#advsearchbox').show();
		$("#DataTables_Table_0_filter").hide();

		$scope.shoporder = 0;

		$scope.show_calndr = false;
		$scope.show_order_form = true;
		$scope.show_addForm = false;
		$scope.show_form = true;
		manageButtons("save");
		$('#btnDiscard').hide();
		$('#btnSave').hide();
		$scope.item_category_id = 0;

		var today = new Date();
		if (today.getHours() != 0 && today.getMinutes() != 0
				&& today.getSeconds() != 0
				&& today.getMilliseconds() != 0) {
			today.setHours(0, 0, 0, 0);
		}

		$scope.date = getMysqlFormat($scope.closing_date);
		DataObj.adnlFilters = [ {
			col : 1,
			filters : $scope.date
		} ];
		//	vm.dtInstance.reloadData();
		$scope.show_table = true;
		$scope.show_calndr = false;
		$scope.show_addForm = false;
		$scope.show_salesForm = false;
		$scope.show_form = false;

		$scope.item_category_id = 0;
		manageButtons("add");
		/*$('#companyId').val(0);*/

		//$('#btnBackprod').show();
		$rootScope.showSummary=true;

		$('#SearchText').text("");
		$('#ordernum').val("");
		$('#ordertype').val("");
		//	vm.dtInstance.DataTable.search($('#SearchText').text()).draw();
		//$scope.searchTxtItms = {};


		$scope.reloadBalnceOnFinalize();

	}

	$scope.tableShow();
	$scope.showOnDayClick=function(clickdate)
	{
		//vm.selected = {};
		$('#show_form').val(1);

		$('#advsearchbox').show();
		$("#DataTables_Table_0_filter").hide();

		$scope.shoporder = 0;
		//$scope.closing_date = geteditDateFormat(clickdate);
		$scope.closing_date = clickdate;
		$scope.show_calndr = false;
		$scope.show_order_form = true;
		$scope.show_addForm = false;
		$scope.show_form = true;
		manageButtons("save");
		$('#btnDiscard').hide();
		$('#btnSave').hide();
		$scope.item_category_id = 0;

		var today = new Date();
		if (today.getHours() != 0 && today.getMinutes() != 0
				&& today.getSeconds() != 0
				&& today.getMilliseconds() != 0) {
			today.setHours(0, 0, 0, 0);
		}

		$scope.date = getMysqlFormat($scope.closing_date);
		DataObj.adnlFilters = [ {
			col : 1,
			filters : $scope.date
		} ];
		vm.dtInstance.reloadData();
		$scope.show_table = true;
		$scope.show_calndr = false;
		$scope.show_addForm = false;
		$scope.show_salesForm = false;
		$scope.show_form = false;

		$scope.item_category_id = 0;
		manageButtons("add");
		//$('#companyId').val(0);

		//$('#btnBackprod').show();
		$rootScope.showSummary=true;

		$('#SearchText').text("");
		$('#ordernum').val("");
		$('#ordertype').val("");
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();
		$scope.searchTxtItms = {};


		$scope.reloadBalnceOnFinalize();
		// $scope.getorderDet();



	}
	$scope.getSummary=function()
	{

		$('#summaryBttn').show();
		$('#show_form').val(1);
		$("#DataTables_Table_0_filter").hide();
		$scope.shoporder = 0;

		$rootScope.showSummary=false;
		$scope.show_calndr = false;
		$scope.show_order_form = true;
		$scope.show_addForm = false;
		$scope.show_form = true;
		$scope.show_table=false;
		$('#advsearchbox').hide();

		manageButtons("save");
		$('#btnDiscard').hide();
		$('#btnSave').hide();
		$scope.item_category_id = 0;

		var today = new Date();
		if (today.getHours() != 0 && today.getMinutes() != 0
				&& today.getSeconds() != 0
				&& today.getMilliseconds() != 0) {
			today.setHours(0, 0, 0, 0);
		}

		$scope.date = getMysqlFormat($scope.closing_date);
		DataObj.adnlFilters = [ {
			col : 1,
			filters : $scope.date
		} ];
		vm.dtInstance.reloadData();

		$scope.selectedDate1 = {
				closing_date : getMysqlFormat($scope.closing_date),
				iscustomer : 0
		};
		$scope.stkfilterData = [];
		$scope.orderitemtotal = [];
		$http({
			url : 'orderhdrDateData',
			async : true,
			params :$scope.selectedDate1,
			method : "GET",
		}).then(function(response) {
			$scope.stkfilterData = response.data.orderhdrDateData;
			$scope.orderitemtotal = response.data.orderitemtotal;
			if ($scope.orderitemtotal.length <= 0) {
				$scope.orderTotal = false;
			} else {
				$scope.orderTotal = true;
			}
		}

		, function(itemresponse) { // optional
			$scope.prograssing = false;

		});


	}

	$rootScope.$on('show_summary', function(event) {
		$( "#dataTabform" ).removeClass( "col-sm-9" ).addClass( "col-sm-12" );
		$rootScope.clndrshow=false;
		$('#form_div_cat').show();
		$scope.getSummary();

	})


	function sendItemToProduction(idss)

	{


		var confirm = $mdDialog.confirm(
				{
					onComplete : function afterShowAnimation() {
						var $dialog = angular.element(document
								.querySelector('md-dialog'));
						var $actionsSection = $dialog
						.find('md-dialog-actions');
						var $cancelButton = $actionsSection
						.children()[0];
						var $confirmButton = $actionsSection
						.children()[1];
						angular.element($confirmButton)
						.removeClass('md-focused');
						angular.element($cancelButton)
						.addClass('md-focused');
						$cancelButton.focus();
					}
				}).title("Do You Want To Send order to production?")
				.targetEvent(event).cancel('No').ok('Yes');
		$mdDialog
		.show(confirm)
		.then(
				function() {



					$http({
						url : 'GotoProduction',
						method : "POST",
						params :{productionIds:idss},
					}).then(function(response) {
						if(response.data=="1")
						{
							$rootScope.$broadcast('on_AlertMessage_SUCC',
							"Items Send To Production");

							$timeout(function(){$rootScope.$broadcast('changeview_details')},1);

							vm.dtInstance.reloadData();
						}
						else
						{
							//alert("fail");
							$mdDialog.show($mdDialog.alert()
									.parent(
											angular.element(document
													.querySelector('#dialogContainer')))
													.clickOutsideToClose(true)
													.textContent("FAIL").ok('Ok!').targetEvent(
															event));
							vm.dtInstance.reloadData();
						}

					});




				});










	}









	/*	
	$scope.calendarTab = 1;

	$scope.selectCalTab = function(tab) { // function to set which calendar
		// tab is shown in html via ng-if
		if (tab == 1) {
			$scope.calendarTab = 1;
		} else {
			$scope.calendarTab = 2;
		}
	};*/

	$scope.addItem = function(index) {
		if ($scope.disable_all == false) {
			var date = get_date_format();
			if (index < $scope.customer_orders.items.length - 1) {
				$(
						"#items_table tr:nth-child(" + (index + 2)
						+ ") td:nth-child(" + (2) + ")").find(
						".acontainer input").select();
			} else {
				if ($scope.customer_orders.items[($scope.customer_orders.items.length - 1)].stock_item_id != "" && 
						$scope.customer_orders.items[($scope.customer_orders.items.length - 1)].quantity != "" &&  
						parseFloat($scope.customer_orders.items[($scope.customer_orders.items.length - 1)].quantity)>0)
				{
					$scope.customer_orders.items.push({
						order_id : 0,
						stock_item_id : "",
						stock_item_code : "",
						stock_item_name : "",
						uomcode : "",
						dtlorder_date : date.mindate,
						quantity : 0,
						adjustqty:0,
						flag : 1
					});
					$("#items_table tr:nth-child(" + ($scope.customer_orders.items.length)+ ") td:nth-child(" + (2) + ")").find(".acontainer input").focus();


				}
				else if ($scope.customer_orders.items[($scope.customer_orders.items.length - 1)].stock_item_id != "" &&
						($scope.customer_orders.items[($scope.customer_orders.items.length - 1)].quantity == "" || 
								parseFloat($scope.customer_orders.items[($scope.customer_orders.items.length - 1)].quantity)<=0))
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Item Qty");
					$("#items_table tr:nth-child(" + ($scope.customer_orders.items.length)+ ") ").find("#qty").select();
				}

				else {
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					$("#items_table tr:nth-child(" + ($scope.customer_orders.items.length)+ ") td:nth-child(" + (2) + ")").find(".acontainer input").select();
				}
			}
		}
	},

	$scope.del_dtl_id = [];
	$scope.removeItem = function(event, index) {

		if ($scope.disable_all == false) {

			var confirm = $mdDialog.confirm(
					{
						onComplete : function afterShowAnimation() {
							var $dialog = angular.element(document
									.querySelector('md-dialog'));
							var $actionsSection = $dialog
							.find('md-dialog-actions');
							var $cancelButton = $actionsSection.children()[0];
							var $confirmButton = $actionsSection.children()[1];
							angular.element($confirmButton).removeClass(
							'md-focused');
							angular.element($cancelButton).addClass(
							'md-focused');
							$cancelButton.focus();
						}
					}).title(FORM_MESSAGES.ROW_DELETE_WRNG).targetEvent(event)
					.cancel('No').ok('Yes');
			$mdDialog.show(confirm).then(
					function() {


						if ($scope.customer_orders.items.length != 1) {
							$scope.customer_orders.items.splice(index, 1);
						} else {
							$rootScope.$broadcast('on_AlertMessage_ERR',
							"Atleast one item is required.");
						}
					});
		}
	}
	$scope.tableClicked = function(index) {
		$scope.table_itemsearch_rowindex = index;
	};

	$rootScope.$on("show_calendr", function(event) {

		$scope.show_order_form = false;
		$scope.show_calndr = true;
	});


	$timeout(function() {
		$("#DataTables_Table_0_filter").hide();
	}, 1);

	$rootScope
	.$on(
			"advSearch",
			function(event) {
				DataObj.adnlFilters = [ {} ];
				$('#SearchText').text("");
				vm.dtInstance.DataTable.search('').draw();
				$scope.orderstatus = $('#ordertype').val();
				if ($scope.orderstatus != "") {
					var orderType = $('#ordertype').find(":selected").text();

				}

				$scope.ordernum = $('#ordernum').val();
				$scope.orderType = $('#ordertype').val();

				$scope.orderStatus=$('#orderStatus').val();
				if($scope.orderStatus != "")
				{
					var orderstat=$('#orderStatus').find(":selected").text();
				}

				$scope.searchTxtItms = {
						1 : $scope.ordernum,
						2 : orderType,
						3: orderstat
				};

				var close_option = [];
				var counter = 0;
				for ( var key in $scope.searchTxtItms) {

					if ($scope.searchTxtItms.hasOwnProperty(key)) {
						if ($scope.searchTxtItms[key] != null
								&& $scope.searchTxtItms[key] != undefined
								&& $scope.searchTxtItms[key] != "") {

							angular
							.element(
									document
									.getElementById('SearchText'))
									.append(
											$compile(
													"<div id="
													+ key
													+ "  class='advseacrh '  contenteditable='false'>"
													+ $scope.searchTxtItms[key]
													+ "<span class='close-thik' contenteditable='false'  ng-click='deleteOptn("
													+ key
													+ "); '></span></div>")
													($scope))
													$scope.deleteOptn = function(key) {
								// alert(key);
								delete $scope.searchTxtItms[key];
								$('#' + key).remove();
								switch (key) {
								case 1:
									$scope.ordernum = "";
									$('#ordernum').val("");
									break;
								case 2:
									$scope.orderType = "";
									$('#ordertype').val("");
									break;
								case 3:
									$scope.orderStatus = "";
									$('#orderStatus').val("");
									break;	


								}

								DataObj.adnlFilters = [ {
									col : 1,
									filters : $scope.date
								}, {
									col : 2,
									filters : $scope.ordernum
								}, {
									col : 5,
									filters : $scope.orderType
								},{
									col : 7,
									filters : $scope.orderStatus
								} ];
								vm.dtInstance.reloadData();
							};

							counter++;

						}
					}
				}

				DataObj.adnlFilters = [ {
					col : 1,
					filters : $scope.date
				}, {
					col : 2,
					filters : $scope.ordernum
				}, {
					col : 5,
					filters : $scope.orderType
				} ,{
					col : 7,
					filters : $scope.orderStatus
				} ];
				vm.dtInstance.reloadData();
				$scope.searchTxtItms = {};

			});

	$("#closebtnew").click(function(){
		DataObj.adnlFilters = [ {
			col : 1,
			filters : $scope.date
		} ];

		$('#SearchText').text("");
		$('#ordernum').val("");
		$('#ordertype').val("");
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();
		$scope.searchTxtItms = {};

		$('#ordernum').val("");
		$('#ordertype').val("");


	});

	$rootScope.$on("Search", function(event) {
		DataObj.adnlFilters = [ {
			col : 1,
			filters : $scope.date
		} ];
		vm.dtInstance.reloadData();
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();

	});

	$("#clear").click(function() {
		DataObj.adnlFilters = [ {
			col : 1,
			filters : $scope.date
		} ];

		$('#SearchText').text("");
		$('#ordernum').val("");
		$('#ordertype').val("");
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();
		$scope.searchTxtItms = {};
	});

	$scope.item_category_id = $scope.ItemCtgry[0].id;

	$scope.rows = $scope.stockData.length;
	$scope.columns = 2;

	$rootScope.$on('hide_form', function(event) {
		$rootScope.clndrshow=true;
		$( "#dataTabform" ).removeClass( "col-sm-12" ).addClass( "col-sm-9" );
		$timeout(function(){$rootScope.$broadcast('changeview_details')},1);
		$scope.show_formPlanning();


	})

	$scope.show_formPlanning=function(){


		vm.dtInstance.reloadData();
		$('.header-view').hide();
		$('#advsearchbox').hide();
		if ($scope.show_addForm == true) {
			$('#create_date').prop("disabled",false);
			$('#advsearchbox').show();
			$scope.show_table = true;
			$scope.show_calndr = false;
			$scope.show_addForm = false;
			$scope.show_salesForm = false;
			$scope.show_form = false;
			$rootScope.showSummary=true;
		}

		else if ($scope.show_addForm == true) {
			
			$('#create_date').prop("disabled",false);
			$scope.show_addForm = true;
			$('#advsearchbox').show();
			$rootScope.showSummary=false;
			$scope.show_calndr = false;
			$scope.show_form = true;

			$scope.item_category_id = 0;
			$scope.show_table = true;
			manageButtons("save");
			$('#btnDiscard').hide();
			$('#btnSave').hide();
		} else if ($scope.show_form == true) {

			$('#advsearchbox').show();
			$scope.show_table = true;
			$scope.show_calndr = false;
			$scope.show_addForm = false;
			$scope.show_salesForm = false;
			$scope.show_form = false;
			$rootScope.showSummary=true;



		} else if ($scope.show_table == true) {
			$('#advsearchbox').show();
			/*$('#show_form').val(0); 
			$scope.orderedData();
			$scope.show_calndr = true;
			$scope.show_form = false;
			$scope.show_addForm = false;
			$scope.item_category_id = 0;
			$scope.show_table = false;
			manageButtons("hideall");
			$rootScope.showSummary=false;*/
		}






	}



	$scope.clear_stock_details_editmode = function(event) {
		$scope.customer_orders.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_id = "";
		$scope.customer_orders.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_code = "";
		$scope.customer_orders.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_name = "";
		$scope.customer_orders.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_batch_id = "";
		$scope.customer_orders.items[event.currentTarget.parentElement.rowIndex - 1].uomcode = "";
		$scope.customer_orders.items[event.currentTarget.parentElement.rowIndex - 1].quantity = 0;
		$scope.customer_orders.items[event.currentTarget.parentElement.rowIndex - 1].balanceqty=0;
	}

	// return total amount of stock item
	$scope.total = function() {
		var total = 0.00;
		angular.forEach($scope.customer_orders.items, function(item) {
			total += (item.qty * item.unit_price);
		});
		return total;
	}

	// onchange stock item name
	$scope.elementid = function(elemenvalue) {
		if ($scope.disable_all == true) {
			$(elemenvalue).attr("disabled", true);
		}
	}

	$scope.alert_for_codeexisting = function(flg) {
		if (flg == 1) {
			$rootScope.$broadcast('on_AlertMessage_ERR',
					ITEM_TABLE_MESSAGES.CODE_EXISIT);
		}
	}
	$('.header-view').hide();

	$scope.filterTimes1 = filterTime();

	$scope.getShopData=function()
	{

		debugger	

		if($scope.order_id == undefined || $scope.order_id == "")
		{

			if(settings['dailyprodview']==0)
			{

				$scope.balanceQtyData1={};
				var shopId=getShopIdFromCode();

				$scope.selectedShop = {
						selectedShop : shopId,
						closing_date:getMysqlFormat($scope.closing_date)

				};
				$http({
					url : 'getShopBalanceData',
					async : false,
					params : $scope.selectedShop,
					method : "GET",
				}).then(function(response) {
					$scope.customer_orders.items = response.data.shopBalanceData;


					if(response.data.shopBalanceData.length>0)

					{
						$scope.customer_orders.items=[];
						$scope.customer_orders.items.push({
							order_id : 0,
							stock_item_id : "",
							stock_item_code : "",
							stock_item_name : "",
							uomcode : "",
							quantity : 0,
							balanceqty:0,
							adjustqty:0,
							dtlorder_date : date.mindate,
							flag : 1
						});
						var confirm = $mdDialog.confirm(
								{
									onComplete : function afterShowAnimation() {
										var $dialog = angular.element(document
												.querySelector('md-dialog'));
										var $actionsSection = $dialog
										.find('md-dialog-actions');
										var $cancelButton = $actionsSection
										.children()[0];
										var $confirmButton = $actionsSection
										.children()[1];
										angular.element($confirmButton)
										.removeClass('md-focused');
										angular.element($cancelButton)
										.addClass('md-focused');
										$cancelButton.focus();
									}
								}).title("Do You Want To Load The Pending Items?")
								.targetEvent(event).cancel('No').ok('Yes');
						$mdDialog
						.show(confirm)
						.then(
								function() {

									$scope.customer_orders.items = response.data.shopBalanceData;
									for(var i=0;i<$scope.customer_orders.items.length;i++)
									{
										$scope.customer_orders.items[i].dtlorder_date = date.mindate;
										$scope.customer_orders.items[i].adjustqty=0;

										$timeout(function() {$("#items_table tr:nth-child("+(i+1)+") #adjustqty").attr('disabled',true);}, 1);
									}

								});

					}



					if ($scope.customer_orders.items.length <= 0) {
						$scope.customer_orders.items.push({
							order_id : 0,
							stock_item_id : "",
							stock_item_code : "",
							stock_item_name : "",
							uomcode : "",
							quantity : 0,
							balanceqty:0,
							adjustqty:0,
							dtlorder_date : date.mindate,
							flag : 1
						});
					}

				}

				, function(itemresponse) { // optional

				});
			}

/*			else
			{

				$scope.customer_orders.items=[];
				$scope.customer_orders.items.push({
					order_id : 0,
					stock_item_id : "",
					stock_item_code : "",
					stock_item_name : "",
					uomcode : "",
					quantity : 0,
					balanceqty:0,
					adjustqty:0,
					dtlorder_date : date.mindate,
					flag : 1
				});


			}*/

		}




	}


	$scope.getCustomerData1=function()
	{
		if($scope.customerIds1 !="")
		{
			for(var i=0;i<$scope.customerIds.length;i++)
			{
				if($scope.customerIds[i].id==$scope.customerIds1)
				{
					$scope.custname=$scope.customerIds[i].name;
					$scope.address=$scope.customerIds[i].address;
					$scope.contact1=$scope.customerIds[i].phone;
					$scope.customerType=$scope.customerIds[i].customer_type;
					$scope.customerShopcode=$scope.customerIds[i].code;

					break;
				}
				else
				{
					$scope.custname="";
					$scope.address="";
				}
			}
		}
		else
		{
			$scope.custname="";
			$scope.address="";
			$scope.cust_code="";
			$scope.contact1="";
			$scope.contact2="";
			$scope.customerType="0";

		}
		if($scope.order_id == undefined || $scope.order_id == "")
		{
			if($scope.customerIds1 !="")
			{
				for(var i=0;i<$scope.customerIds.length;i++)
				{
					if($scope.customerIds[i].id==$scope.customerIds1)
					{
						$scope.custname=$scope.customerIds[i].name;
						$scope.address=$scope.customerIds[i].address;
						$scope.contact1=$scope.customerIds[i].phone;
						$scope.customerType=$scope.customerIds[i].customer_type;


						break;
					}
					else
					{
						$scope.custname="";
						$scope.address="";
					}
				}

				if($scope.order_id == undefined || $scope.order_id == "")
				{


















					$scope.balanceQtyData1={};


					$scope.selectedShop = {
							selectedShop : $scope.customerIds1,
							closing_date:getMysqlFormat($scope.closing_date)
					};
					$http({
						url : 'getShopBalanceData',
						async : false,
						params : $scope.selectedShop,
						method : "GET",
					}).then(function(response) {
						$scope.customer_orders.items = response.data.shopBalanceData;
						if (response.data.shopBalanceData <= 0) {
							$scope.customer_orders.items.push({
								order_id : 0,
								stock_item_id : "",
								stock_item_code : "",
								stock_item_name : "",
								uomcode : "",
								quantity : 0,
								balanceqty:0,
								adjustqty:0,
								dtlorder_date : date.mindate,
								flag : 1
							});
						}

						else

						{


							$scope.customer_orders.items =[];

							$scope.customer_orders.items.push({
								order_id : 0,
								stock_item_id : "",
								stock_item_code : "",
								stock_item_name : "",
								uomcode : "",
								quantity : 0,
								balanceqty:0,
								adjustqty:0,
								dtlorder_date : date.mindate,
								flag : 1
							});

							var confirm = $mdDialog.confirm(
									{
										onComplete : function afterShowAnimation() {
											var $dialog = angular.element(document
													.querySelector('md-dialog'));
											var $actionsSection = $dialog
											.find('md-dialog-actions');
											var $cancelButton = $actionsSection
											.children()[0];
											var $confirmButton = $actionsSection
											.children()[1];
											angular.element($confirmButton)
											.removeClass('md-focused');
											angular.element($cancelButton)
											.addClass('md-focused');
											$cancelButton.focus();
										}
									}).title("Do You Want To Load The Pending Items?")
									.targetEvent(event).cancel('No').ok('Yes');
							$mdDialog
							.show(confirm)
							.then(
									function() {
										$scope.customer_orders.items = response.data.shopBalanceData;
										for(var i=0;i<$scope.customer_orders.items.length;i++)
										{
											$scope.customer_orders.items[i].dtlorder_date = date.mindate;
											$scope.customer_orders.items[i].adjustqty=0;

											$timeout(function() {$("#items_table tr:nth-child("+(i+1)+") #adjustqty").attr('disabled',true);}, 1);
										}


									});






						} }

					, function(itemresponse) { // optional

					});
				}
			}else
			{
				$scope.custname="";
				$scope.address="";
				$scope.customer_orders.items=[];
				if ($scope.customer_orders.items.length <= 0) {
					$scope.customer_orders.items.push({
						order_id : 0,
						stock_item_id : "",
						stock_item_code : "",
						stock_item_name : "",
						uomcode : "",
						quantity : 0,
						balanceqty:0,
						adjustqty:0,
						dtlorder_date : date.mindate,
						flag : 1
					});
				}

			}
		}}









};






mrpApp.directive( 'autocompeteText', [ '$timeout', function($timeout) {
	return {
		controller : function($scope, $http) {

			$scope.currentIndex = 0;
			$scope.selctedindex = 0;
			$("#items_table tbody tr td").keyup( 'input', function(e) {

				$scope.currentIndex = e.currentTarget.parentElement.rowIndex - 1;

				if (e.shiftKey && e.keyCode == 9) {
					$( "#items_table tr:nth-child(" + (e.currentTarget.parentElement.rowIndex + 1) + ")").find( ".acontainer input") .focus();
				} else if (e.which != 9 && e.which != 13 && e.which != 109 && e.which == 8 && !(e.shiftKey && e.keyCode == 9)) {
					if (e.currentTarget.cellIndex == 1) {
						$scope.$apply(function() {

							$scope.clear_stock_details_editmode(e);
						});
					}
				} else if (e.which == 13) {
					if (e.currentTarget.cellIndex == 1) {
						if ($scope.customer_orders.items[e.currentTarget.parentElement.rowIndex - 1].stock_item_id != undefined
								|| $scope.customer_orders.items[e.currentTarget.parentElement.rowIndex - 1].stock_item_id != "") {
							{
								$( "#items_table tr:nth-child(" + (e.currentTarget.parentElement.rowIndex) + ")") .find( "#qty") .select();
							}
						}
					}
				} else if (e.which == 9) {

					if (e.currentTarget.cellIndex == 1 && $scope.selctedindex ==0) 
					{
						$( "#items_table tr:nth-child(" + (e.currentTarget.parentElement.rowIndex) + ")") .find( ".acontainer input") .focus();
						$scope.selctedindex=1;
					}
					else if(e.currentTarget.cellIndex == 1 && $scope.selctedindex ==1) 
					{
						$( "#items_table tr:nth-child(" + (e.currentTarget.parentElement.rowIndex) + ")") .find( "#qty") .focus();
						$scope.selctedindex =0;

					}

				}


			});
			$scope.element = [];
			$scope.table_itemsearch_rowindex = 0;
			$scope.tableClicked = function(index) {
				$scope.table_itemsearch_rowindex = index;
			};
			return $scope;
		},
		link : function(scope, elem, attrs, controller) {
			var strl_scope = controller;
			var items = $(elem)
			.tautocomplete(
					{
						columns : [ 'id', 'code', 'name', 'uomcode', 'uomname', 'is_combo_item' ],
						hide : [ false, true, true, false, false, false, false ],
						placeholder : "search ..",
						highlight : "hightlight-classname",
						norecord : "No Records Found",
						delay : 10,
						onchange : function() {
							var selected_item_data = items
							.all();
							strl_scope
							.$apply(function() {
								var count = 0;
								for (var i = 0; i < strl_scope.customer_orders.items.length; i++) {
									if (selected_item_data.id != "") {
										if (i != strl_scope.currentIndex) {
											if (selected_item_data.id == strl_scope.customer_orders.items[i].stock_item_id) {
												count = 1;
											}
										}
									}
								}

								if (count != 1) {
									strl_scope.customer_orders.items[strl_scope.currentIndex].stock_item_id = selected_item_data.id;
									strl_scope.customer_orders.items[strl_scope.currentIndex].stock_item_code = selected_item_data.code;
									strl_scope.customer_orders.items[strl_scope.currentIndex].stock_item_name = selected_item_data.name;
									strl_scope.customer_orders.items[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;
									strl_scope.customer_orders.items[strl_scope.currentIndex].uomname = selected_item_data.uomname;
									strl_scope.customer_orders.items[strl_scope.currentIndex].is_combo_item = selected_item_data.is_combo_item;
									strl_scope.customer_orders.items[strl_scope.currentIndex].balanceqty=strl_scope.getBalanceQtyData(selected_item_data.id);

									strl_scope.customer_orders.items[strl_scope.currentIndex].is_adjst=strl_scope.setAdjustValue(selected_item_data.id);

									strl_scope.customer_orders.items[strl_scope.currentIndex].quantity = 0.0;
									$( "#items_table tr:nth-child(" + (strl_scope.customer_orders.items.length) + ") td:nth-child(" + (2) + ")").find( ".acontainer input").select(); 
									strl_scope.alert_for_codeexisting(0);
									$timeout(function () {$("#items_table tr:nth-child("+(strl_scope.currentIndex+1)+")").find("#qty").select();}, 1); 

								} else {
									elem[0].parentNode.lastChild.value = "";
									strl_scope.customer_orders.items[strl_scope.currentIndex].uomcode = "";
									strl_scope
									.alert_for_codeexisting(1);
								}
							});
						},
						data : function() {
							var data = strl_scope.stockData;
							var filterData = [];
							var searchData = eval("/" + items .searchdata() + "/gi");
							$.each(data, function(i, v) {
								if (v.name.search(new RegExp(searchData)) != -1) {
									if (v.is_active == 1 && v.is_manufactured == 1) {
										filterData.push(v);
									}
								}
							});



							return filterData;
						}
					});
			for (var i = 0; i < strl_scope.customer_orders.items.length; i++) {
				if (strl_scope.customer_orders.items[i].stock_item_id != undefined
						&& strl_scope.customer_orders.items[i].stock_item_id != "") {
					if (strl_scope.customer_orders.items[i].flag == 0) {
						elem[0].parentNode.lastChild.value = strl_scope.customer_orders.items[i].stock_item_code;
						strl_scope.elementid(elem[0].parentNode.lastChild);
						strl_scope.customer_orders.items[i].flag = 1;
						break;
					}
				}
			}

			$timeout(function() { 
				$( "#items_table tr:nth-child(" + (strl_scope.customer_orders.items.length) + ") td:nth-child(" + (2) + ")").find( ".acontainer input").select(); 
			}, 1);

		}
	};
} ]);


