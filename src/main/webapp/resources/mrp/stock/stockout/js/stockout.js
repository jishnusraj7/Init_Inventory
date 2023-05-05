//Controller for Buttons 
mrpApp.controller('btn_ctrl', function($scope, $rootScope, $http, $window,
		$timeout, $compile) {

	var vm = this;
	vm.form_next_data = form_next_data;
	vm.fun_show_addmode_btn = fun_show_addmode_btn;
	$scope.saveAlertMessage = true;
	$scope.stockoutFormflag = stockOutStrings['isRequest'];
	$scope.stockRegisterList = [];

	if ($scope.stockoutFormflag == 0) {
		$scope.show_stockrequest_form = false;
	} else if ($scope.stockoutFormflag == 1) {
		$scope.show_stockrequest_form = true;
	}

	$scope.fun_show_form = function() {
		// $scope.editData=false;
		$("#ref_code").css("display","none");
		$("#grnCode_label").css("display","none");
		if (stockOutStrings['isRequest'] == 1 && settings['combinePurchase'] == 0) {
			$rootScope.$emit('hide_table');
			$("#advsearchbox").hide();
			// $('#itemLists').hide();
			manageButtons("next");
			$scope.show_type_form = false;

			$('#show_form').val(1);
		} else if (stockOutStrings['isRequest'] == 0) {
			$rootScope.$emit('hide_form_type');
			manageButtons("save");
			$('#show_form').val(1);
		} else if (stockOutStrings['isRequest'] == 1
				&& settings['combinePurchase'] == 1) {

			$rootScope.$emit('hide_form_type');
			manageButtons("save");
			$('#show_form').val(1);

		}
		$rootScope.$emit("fun_enable_inputs");
		$rootScope.$emit("fun_enable_inputs_code");
		$rootScope.$emit("fun_clear_form"); // clear and enable form
		// fileds
		clearform();
		$("#btnBack").show();
		//alert("stock out 1==========>");
		$("#status_div_id").css("display", "none");

	}

	$scope.Search = function() {
		$rootScope.$emit('Search');
	}

	$scope.advSearch = function() {
		$rootScope.$emit('advSearch');
	}

	$scope.fun_show_form1 = function() {
		//$scope.editData=false;
		$("#ref_code").css("display","none");
		$("#grnCode_label").css("display","none");
		$rootScope.$emit('hide_form_type');
		$rootScope.$emit("fun_enable_inputs");
		$rootScope.$emit("fun_enable_inputs_code");
		$rootScope.$emit("fun_clear_form"); // clear and enable form
		// fileds
		clearform();
		manageButtons("save");
		// $('#itemLists').hide();
		$("#btnBack").show();
		//alert("stock out 2==========>");
		$("#status_div_id").css("display", "none");
		$("#companyId option[value='0']").prop('selected', true);
		$('#show_form').val(2);
	}

	$scope.fun_save_form = function() { // fun for save or update Data -
		// click on SAVE
		$rootScope.$emit('fun_save_data');
	}

	$scope.fun_backTo_table = function() {

		if (stockOutStrings['isRequest'] == 1 && $scope.show_type_form == false
				&& settings['combinePurchase'] == 0) {
			$rootScope.$emit('hide_table');
			$("#advsearchbox").hide();
			// $('#itemLists').hide();
			manageButtons("next");
			clearform();
			$("#btnBack").show();
		//	alert("stock out 3==========>");
			$("#status_div_id").css("display", "none");
			$scope.show_type_form = true;
			$("#show_form").val(1);
		}

		else {
			$scope.show_type_form = false;
			$rootScope.$emit('disable_status');// Button Back
			fun_show_addmode_btn();
			// $('#itemLists').show();
			$("#advsearchbox").show();
			$("#btnEdit").css("display", "block");
			$("#show_form").val(0);
			// $('#itemLists').hide();
		}

	}

	$scope.fun_backTo_table1 = function() {
		$rootScope.$emit('disable_status');// Button Back
		fun_show_addmode_btn();
	}

	$rootScope.$on("show_addmode_aftr_edit", function(event, id) {
		fun_show_addmode_btn();
	});

	function fun_show_addmode_btn() {
		$rootScope.$emit('hide_type_form');
		manageButtons("add");
	}

	$scope.fun_discard_form = function(data) { // Discard Formfileds
		// values in edit or
		// view mode

		$rootScope.$emit("fun_discard_form");
	}

	$scope.fun_delete_form = function() { // func for Delete
		$rootScope.$emit("fun_delete_current_data");
	}

	$scope.fun_edit_form = function() { // When EDIT btn clk disable all
		// fileds
		$rootScope.$emit("fun_enable_inputs");
		manageButtons("save");
		$("#btnBack").hide();
	}

	$rootScope.$on("show_edit_btn_div", function(event, id) { // Show
		// Edit
		// btn
		// group
		// in
		// Edit
		// mode
		manageButtons("view");
		form_next_data(id);
	});

	$scope.prev_formData = function(event) { // functions for
		// NEXT-PREV button
		// actions
		$rootScope.$emit("fun_prev_rowData", event.target.id);
	}

	$scope.next_formData = function(event) {
		$rootScope.$emit("fun_next_rowData", event.target.id);
	}

	function form_next_data(id) {
		$scope.row_id = id;
	}

	$rootScope.$on("next_formdata_set", function(event, id) {
		form_next_data(id);
	});

	$rootScope.$on("disable_next_btn", function(event) {
		$scope.disable_next_btn = true;
	});

	$rootScope.$on("disable_prev_btn", function(event) {
		$scope.disable_prev_btn = true;
	});

	$rootScope.$on("enable_next_btn", function(event) {
		$scope.disable_next_btn = false;
	});

	$rootScope.$on("enable_prev_btn", function(event) {
		$scope.disable_prev_btn = false;
	});
	$rootScope.$on("on_AlertMessage_SUCC", function(event, msg) {
		setSuc_AlertMessage(event, msg);
	});
	$rootScope.$on("on_AlertMessage_ERR", function(event, msg) {
		setErr_AlertMessage(event, msg);
	});

	function setSuc_AlertMessage(event, msg) {
		$scope.succ_alertMessageStatus = false;
		$scope.succ_alertMeaasge = msg;
		$timeout(function() {
			$scope.succ_alertMessageStatus = true;
		}, 1500);
	}

	function setErr_AlertMessage(event, msg) {
		$scope.err_alertMessageStatus = false;
		$scope.err_alertMeaasge = msg;
		$timeout(function() {
			$scope.err_alertMessageStatus = true;
		}, 1500);
	}
	$rootScope.$on('get_dep_list', function(event, args) {
		$scope.dep_list = args.dep;
	});

	$scope.filterDeprtmnt = function() {

		$scope.stkfilterDepartment = $scope.depListData;

		if ($scope.filerDeprtmnt != "") {
			$scope.stkfilterData = [];

			for (var i = 0; i < $scope.dep_list.length; i++) {
				if ($scope.dep_list[i].id == $scope.filerDeprtmnt) {
					$scope.stkfilterData.push($scope.dep_list[i]);
					break;
				}
			}
		}
		$rootScope.filteredDepartmentId = $scope.stkfilterData[0].id;
		$rootScope.filteredDepName = $scope.stkfilterData[0].name;
	}

});

// Controller for Table and Form
mrpApp.controller('stockout', stockout);

function stockout($controller, $scope, $interval, $http, $mdDialog, $rootScope,
		DTOptionsBuilder, $compile, DTColumnBuilder, MRP_CONSTANT,
		DATATABLE_CONSTANT, FORM_MESSAGES, ITEM_TABLE_MESSAGES, RECORD_STATUS,
		STATUS_BTN_TEXT, RECORD_STATUS_STYLE, $timeout, $q, $window) {

	$controller('DatatableController', {
		$scope : $scope
	});
	//$scope.editData=false;
	$("#ref_code").css("display","none");
	$("#grnCode_label").css("display","none");
	$scope.stockoutFormflag = stockOutStrings['isRequest'];
	/* set_sub_menu("#store"); */
	if ($scope.stockoutFormflag == 0) {
		/*
		 * setMenuSelected("#stockrequest_left_menu"); //active leftmenu
		 */} else if ($scope.stockoutFormflag == 1) {
		/* setMenuSelected("#stockout_left_menu"); */// active leftmenu
	}
	manageButtons("add");
	$scope.formData = {};
	$scope.show_table = true;
	$("#advsearchbox").show();
	// $('#itemLists').show();
	$scope.show_form = false;
	$scope.hide_code_existing_er = true;
	$scope.baseUomCode=[];
	var baseUomCode="";
	// $scope.REFNO = "";

	var itemsMastersData = [];
	$scope.issue_stat = -1;
	$scope.table_validation_alert_msg = "";
	$scope.status = "";
	$scope.ClassName = "";
	$scope.statusBtnText = "";
	$scope.stockoutFormflag = stockOutStrings['isRequest'];
	$scope.isStoreToStore = true;
	$scope.stkfilterData = [];
	$rootScope.filteredDepartmentId = "";
	$rootScope.filteredDepName = "";
	$scope.searchTxtItms = {};
	$scope.hide_code_existing_er = true;

	if (settings['combinePurchase'] == 1) {
		$scope.status_app = -1;
	}
	if (version_value == 1) {
		$("#rad_1").hide();
	}

	$scope.getCurrentDate = function() {
		$http.get('getCurrentDate').success(function(response) {
			$scope.CurrentDate = geteditDateFormat(response);
			return $scope.CurrentDate;
		});
	}
	$scope.CurrentDate = $scope.getCurrentDate();

	$scope.itemsBatchData = [];

	var jsonUrl = "getDataTableData";
	if ($scope.stockoutFormflag == 0) {
		$("div.checkbox").hide();
		$scope.show_stockrequest_form = false;
		$("#items_table tbody tr th:nth-child(3)").css("width", "75%");
		jsonUrl = "requestJsonData";
	} else if ($scope.stockoutFormflag == 1) {
		$("div.checkbox").show();
		$scope.show_stockrequest_form = true;
		$("#items_table tbody tr th:nth-child(3)").css("width", "39%");
		jsonUrl = "getDataTableData";
	}

	var department_data = [];
	$http({
		url : '../stockin/depList',
		method : "GET",
		async : false,
	}).then(function(response) {
		department_data = response.data.data;
	}, function(response) { // optional
	});

	/*
	 * $http({ url : '../stockin/formJsonDataStkin', method : "GET",
	 * 
	 * }).then(function(response) {
	 * 
	 * //$scope.department_data = response.data.depData; //
	 * $scope.stockInDetailsList = response.data.stockInDtlData;
	 * $scope.itemsMastersData = response.data.stockItmDatastkin;
	 * $scope.itemsBatchData = response.data.stockItmBatchData;
	 * $scope.stockRegisterList = response.data.stockRegData;
	 * itemsMastersData=response.data.stockItmDatastkin; //supplier_data =
	 * response.data.supData; //$("#advsearchbox").show();
	 * //$scope.prograssing1=false; //$scope.show_table=true;
	 * //$('#btnAdd').show(); }, function(response) { // optional
	 * 
	 * });
	 */

	// common confirm box
	$scope.showConfirm = function(title, sourse, event) {
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
						}).title(title).targetEvent(event).ok('Yes').cancel(
						'No');
		$mdDialog.show(confirm).then(function() {
			if (sourse == "DELETE") {
				$scope.deleteData();
			} else if (sourse == "DISCARD") {
				$scope.discardData();
			} else if (sourse == STATUS_BTN_TEXT.ISSUE) {
				$scope.issue();
			} else if (sourse == STATUS_BTN_TEXT.PRINT) {
				$scope.print();
			} else if (sourse == "rowDelete") {
				$scope.removeRowItem(event);
			}
		}, function() {
		});
	};

	$scope.alertBox = function(msg, event) {
		$mdDialog.show($mdDialog.alert().parent(
				angular.element(document.querySelector('#dialogContainer')))
				.clickOutsideToClose(true).textContent(msg).ok('Ok!')
				.targetEvent(event));
	}

	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.code_existing_validation = code_existing_validation;
	vm.setItemtableValues = setItemtableValues;
	vm.loadItemDetailsTable = loadItemDetailsTable;
	vm.fun_get_refno = fun_get_refno;
	vm.fun_get_dep_name = fun_get_dep_name;
	vm.getSourcedep = getSourcedep;
	vm.getCurrentDate = getCurrentDate;

	function getSourcedep() {
		$("#form_div_desti_code").find("input").val("");
		$scope.formData.source_department_id = strings['isDefDepartment'];
		$scope.formData.source_code = strings['isDefDepartmentcode'];
		$scope.formData.source_name = strings['isDefDepartmentname'];
		$("#form_div_source_code").find(".acontainer input").val(
				strings['isDefDepartmentcode']);
		if (stockOutStrings['isRequest'] == 0) {
			/*
			 * $scope.formData.dest_department_id =
			 * $scope.formData.source_department_id; $scope.formData.dest_code =
			 * $scope.formData.source_code; $scope.formData.desti_name =
			 * $scope.formData.source_name ;
			 * $("#form_div_desti_code").find(".acontainer
			 * input").val(depList.code);
			 * $("#form_div_desti_code").find("#to_department_name").val(depList.name);
			 */
		}
	}

	function fun_get_refno() {
		$http.get('getCounterPrefix').success(function(response) {
			$scope.formData.stockTransfeNo = response;

		});
	}

	$scope.statusFilters = [ 0, 1, 2, 3, 4 ];
	$scope.selectedStatus = [ 0, 1, 2, 3, 4 ];
	$scope.toggle = function(item, list) {

		$('#SearchText').text("");

		var idx = list.indexOf(item);
		if (idx > -1) {
			list.splice(idx, 1);
		} else {
			list.push(item);
		}
		statFilter = list.join();
		DataObj.adnlFilters = [ {
			col : 6,
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
			col : 6,
			filters : statFilter
		} ];
		vm.dtInstance.reloadData();
	};

	vm.dtInstance = {};
	var DataObj = {};
	var statFilter = "0,1,2,3,4";
	DataObj.sourceUrl = jsonUrl;
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.adnlFilters = [ {
		col : 6,
		filters : statFilter
	} ];
	DataObj.order = "desc";
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm, DataObj);

	if (settings['combinePurchase'] == 0) {
		vm.dtColumns = [
				DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
				DTColumnBuilder.newColumn('stock_transfer_no').withTitle(
						'REF. NO').withOption('type', 'natural').renderWith(
						function(data, type, full, meta) {
							return urlFormater(data);
						}),
				DTColumnBuilder.newColumn('stock_request_date').withTitle(
						'REQUEST DATE').renderWith(
						function(data, type, full, meta) {
							data = geteditDateFormat(data);
							return data;
						}),
				DTColumnBuilder.newColumn('department_name').withTitle(
						'DEPARTMENT').withOption('searchable', 'false')
						.renderWith(function(data, type, full, meta) {

							return data == undefined ? "" : data;
						}),

				DTColumnBuilder.newColumn('stock_transfer_date').withTitle(
						'DELIVERED DATE').withOption('width', '235px')
						.renderWith(
								function(data, type, full, meta) {
									return type === 'display'
											&& data === '1000-01-01' ? ""
											: geteditDateFormat(data);
								}),
				DTColumnBuilder.newColumn('total_amount').withTitle(
						'AMOUNT(' + settings['currencySymbol'] + ')')
						.withClass("dt-body-right").renderWith(
								function(data, type, full, meta) {
									data = parseFloat(data).toFixed(
											settings['decimalPlace']);
									return data
								}),
				DTColumnBuilder
						.newColumn('req_status')
						.withTitle('STATUS')
						.withOption('width', '150px')
						.renderWith(
								function(data, type, full, meta) {
									if (data == 0) {
										data = "<span style='color:green;'>"
												+ RECORD_STATUS.NEW + "</span>";
									} else if (data == 1) {
										data = "<span style='color:#3c8dbc;'>"
												+ RECORD_STATUS.APPROVED
												+ "</span>";
									} else if (data == 2) {
										data = "<span style='color:red;'>"
												+ RECORD_STATUS.REJECTED
												+ "<span>";
									} else if (data == 3) {
										data = "<span style='color:#e313d3;'>"
												+ RECORD_STATUS.ISSUED
												+ "<span>";
									} else if (data == 4) {
										data = "<span style='color:red;'>"
												+ RECORD_STATUS.PRINTED
												+ "<span>";
									}

									return data;

								}),
				DTColumnBuilder.newColumn('remarks').withTitle('Remarks')
						.notVisible(),

		];

	} else {

		vm.dtColumns = [
				DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
				DTColumnBuilder.newColumn('stock_transfer_no').withTitle(
						'REF. NO').withOption('type', 'natural').renderWith(
						function(data, type, full, meta) {
							return urlFormater(data);
						}),
				DTColumnBuilder.newColumn('stock_request_date').withTitle(
						'REQUEST DATE').renderWith(
						function(data, type, full, meta) {
							data = geteditDateFormat(data);
							return data;
						}),
				DTColumnBuilder.newColumn('department_name').withTitle(
						'DEPARTMENT').withOption('searchable', 'false')
						.renderWith(function(data, type, full, meta) {

							return data == undefined ? "" : data;
						}),

				DTColumnBuilder.newColumn('stock_transfer_date').withTitle(
						'DELIVERED DATE').withOption('width', '235px')
						.renderWith(
								function(data, type, full, meta) {
									return type === 'display'
											&& data === '1000-01-01' ? ""
											: geteditDateFormat(data);
								}),
				DTColumnBuilder.newColumn('total_amount').withTitle(
						'AMOUNT(' + settings['currencySymbol'] + ')')
						.withClass("dt-body-right").notVisible().renderWith(
								function(data, type, full, meta) {
									data = parseFloat(data).toFixed(
											settings['decimalPlace']);
									return data
								}),
				DTColumnBuilder
						.newColumn('req_status')
						.withTitle('STATUS')
						.withOption('width', '150px')
						.renderWith(
								function(data, type, full, meta) {
									if (data == 0) {
										data = "<span style='color:green;'>"
												+ RECORD_STATUS.NEW + "</span>";
									} else if (data == 1) {
										data = "<span style='color:#3c8dbc;'>"
												+ RECORD_STATUS.APPROVED
												+ "</span>";
									} else if (data == 2) {
										data = "<span style='color:red;'>"
												+ RECORD_STATUS.REJECTED
												+ "<span>";
									} else if (data == 3) {
										data = "<span style='color:#e313d3;'>"
												+ RECORD_STATUS.ISSUED
												+ "<span>";
									} else if (data == 4) {
										data = "<span style='color:red;'>"
												+ RECORD_STATUS.PRINTED
												+ "<span>";
									}

									return data;

								}),
				DTColumnBuilder.newColumn('remarks').withTitle('Remarks')
						.notVisible(),

		];

	}

	function urlFormater(data) {
		return '<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
				+ data + '</a>';
	}

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) { // Rowcallback
		// fun
		// for
		// Get
		// Edit
		// Data
		// when
		// clk
		// on
		// Code
		$('a', nRow).unbind('click');
		$('a', nRow)
				.bind(
						'click',
						function(e) {
							$scope
									.$apply(function() {
										$('tr.selected')
												.removeClass('selected');
										if (e.target.id == "rcd_edit") {
											var rowData = aData;
											$(nRow).addClass('selected');
											var current_row_index = 0;
											if (stockOutStrings['isRequest'] == 1) {
												// current_row_index =
												// vm.dtInstance.DataTable.row(".selected").index();
												var dataIndex = vm.dtInstance.DataTable
														.rows();
												for (var i = 0; i < dataIndex[0].length; i++) {
													if (dataIndex[0][i] == vm.dtInstance.DataTable
															.row(".selected")
															.index()) {
														current_row_index = i;
													}
												}
											} else if (stockOutStrings['isRequest'] == 0) {
												current_row_index = nRow.rowIndex - 1;
											}
											$('#show_form').val(1);
											edit(rowData, current_row_index, e);
										}
									});
						});
		return nRow;
	}

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
	$scope.fun_status = function(event) {
		if ($scope.statusBtnText != STATUS_BTN_TEXT.PRINT
				&& $scope.statusBtnText != STATUS_BTN_TEXT.RE_PRINT) {
			var params = {};
			if ($scope.statusBtnText == STATUS_BTN_TEXT.APPROVE_OR_REJECT) {
				//alert("comming=========>")
				if (code_existing_validation($scope.formData)) {
					$('#myModal').modal('show');
					$scope.change_reason = "";
					$("#change_reason").focus();
					$scope.isFocusDemo1 = true;
				}
			} else if ($scope.statusBtnText == STATUS_BTN_TEXT.ISSUE) {
				$scope.showConfirm(FORM_MESSAGES.ISSUE_WRNG,
						STATUS_BTN_TEXT.ISSUE, event);
			}

		} else if ($scope.statusBtnText == STATUS_BTN_TEXT.PRINT) {
			/* $scope.showConfirm(FORM_MESSAGES.PRINT_WRNG,STATUS_BTN_TEXT.PRINT,event); */

			$scope.print();
		} else if ($scope.statusBtnText == "RE-PRINT") {
			$window.open("../stockout/report?id=" + $scope.formData.id + "",
					'_blank');
			$scope.changeDataStatus(STATUS_BTN_TEXT.RE_PRINT,
					RECORD_STATUS_STYLE.PRINTED_STYLE, RECORD_STATUS.PRINTED);
			vm.dtInstance.reloadData();
		}

	}

	$scope.print = function() {
		$scope.change_reason = RECORD_STATUS.PRINTED;
		var printView = $window.open("../stockout/report?change_reason="
				+ $scope.change_reason + "&id=" + $scope.formData.id + "",
				'_blank');
		$scope.changeDataStatus(STATUS_BTN_TEXT.RE_PRINT,
				RECORD_STATUS_STYLE.PRINTED_STYLE, RECORD_STATUS.PRINTED);
		$scope.formData.req_status = 4;
		$timeout(function() {
			vm.dtInstance.reloadData();
		}, 1600);
	}

	$scope.issue = function() {
		$scope.formData.fromDepartmentId = $scope.formData.source_department_id;
		$scope.formData.toDepartmentId = $scope.formData.dest_department_id;
		$scope.change_reason = RECORD_STATUS.ISSUED;
		$scope.formData.stock_request_date = $("#stock_request_date").val();
		$scope.formData.req_status = 3;
		$scope.formData.store_id = 1;
		$scope.formData.stockOutType = $scope.stockout_type;
		$scope.formData.reqBy = strings['userID'];
		for (var i = 0; i < $scope.invoice.items.length; i++) {
			$scope.invoice.items[i].amount = $scope.invoice.items[i].delivered_qty
					* $scope.invoice.items[i].unit_price;

		}
		$scope.formData.total_amount = $scope.total();
		$scope.stockDtlList = [];
		var date = moment().format("YYYY-MM-DD");
		$scope.formData.change_date = moment().format("YYYY-MM-DD");
		for (var i = 0; i < $scope.invoice.items.length; i++) {
			for (var i = 0; i < $scope.invoice.items.length; i++) {
				$scope.stockDtlList.push($scope.invoice.items[i]);
			}
		}
		$scope.formData.stockRequestDate = getMysqlFormat($scope.formData.stock_request_date);
		if ($scope.formData.stock_transfer_date == undefined
				|| $scope.formData.stock_transfer_date == "") {
			$scope.formData.stockTransferDate = '1000-01-01';
		} else {
			$scope.formData.stockTransferDate = getMysqlFormat($scope.formData.stock_transfer_date);
		}

		$scope.formData.stockDetailLists = JSON.stringify($scope.stockDtlList);

		$scope.formData.change_reason = $scope.change_reason;
		$scope.formData.change_date = $scope.formData.change_date;
		$scope.formData.reqStatus = 3;
		$scope.formData.stockOutType = $scope.stockout_type;

		$http({
			url : 'status',
			method : "POST",
			async : false,
			data : $scope.formData,
		})
				.then(
						function(response) {
							$scope.changeDataStatus(STATUS_BTN_TEXT.PRINT,
									RECORD_STATUS_STYLE.ISSUED_STYLE,
									RECORD_STATUS.ISSUED);
							vm.dtInstance.reloadData(null, true);
							$scope.change_reason = "ISSUED";
							$scope.formData.stock_request_date = geteditDateFormat($scope.formData.stockRequestDate);
							if ($scope.formData.stockTransferDate == undefined
									|| $scope.formData.stockTransferDate == "") {
								$scope.formData.stock_transfer_date = '1000-01-01';
							} else {
								$scope.formData.stock_transfer_date = geteditDateFormat($scope.formData.stockTransferDate);
							}
						}, function(response) { // optional

						});
	}

	$('#myModal').on('show.bs.modal', function() {
		$interval(function() {
			$("#change_reason").focus();
		}, 200);
	});
	$scope.accept = function() {

		$scope.formData.fromDepartmentId = $scope.formData.source_department_id;
		$scope.formData.toDepartmentId = $scope.formData.dest_department_id;
		// $scope.formData.stockTransfeNo = $("#stock_transfer_no").val();
		$scope.formData.stock_request_date = $("#stock_request_date").val();

		$scope.formData.store_id = 1;
		$scope.formData.reqBy = strings['userID'];
		for (var i = 0; i < $scope.invoice.items.length; i++) {
			$scope.invoice.items[i].amount = $scope.invoice.items[i].delivered_qty
					* $scope.invoice.items[i].unit_price;

		}
		$scope.formData.total_amount = $scope.total();
		$scope.stockDtlList = [];
		for (var i = 0; i < $scope.invoice.items.length; i++) {
			for (var i = 0; i < $scope.invoice.items.length; i++) {
				$scope.stockDtlList.push($scope.invoice.items[i]);
			}
		}
		$scope.formData.stockRequestDate = getMysqlFormat($scope.formData.stock_request_date);
		if ($scope.formData.stock_transfer_date == undefined
				|| $scope.formData.stock_transfer_date == "") {
			$scope.formData.stockTransferDate = '1000-01-01';
		} else {
			$scope.formData.stockTransferDate = getMysqlFormat($scope.formData.stock_transfer_date);
		}

		$scope.formData.stockDetailLists = JSON.stringify($scope.stockDtlList);
		$scope.formData.req_status = 1;

		$scope.formData.change_reason = $("#change_reason").val();
		$scope.formData.change_date = $("#change_date").val();
		$scope.formData.reqStatus = 1;
		$scope.formData.stockOutType = $scope.stockout_type;

		$http({
			url : 'status',
			method : "POST",
			async : false,
			data : $scope.formData,

		})
				.then(
						function(response) {
							manageButtons("view");

							vm.dtInstance.reloadData(null, true);
							$('#myModal').modal('hide');
							location.reload();
							$scope.change_reason = "";

							if (stockOutStrings['isRequest'] == 0) {

								$scope.changeDataStatus(STATUS_BTN_TEXT.PRINT,
										RECORD_STATUS_STYLE.ISSUED_STYLE,
										RECORD_STATUS.ISSUED);
								// $scope.changeDataStatus(
								// STATUS_BTN_TEXT.APPROVED,
								// RECORD_STATUS_STYLE.APPROVED_STYLE,
								// RECORD_STATUS.APPROVED);
								// $("#btnDelete,#btnEdit").attr("disabled",true);
								$("#btn_status").attr("disabled", true);
								$("#btnDelete").hide();
								// $("#btnAdd").hide();
								$("#btnEdit").hide();

							}

							if (stockOutStrings['isRequest'] == 1) {

								if (settings['combinePurchase'] == 1) {
									$scope.status_app = 1;
								}
								// $scope.changeDataStatus(
								// STATUS_BTN_TEXT.APPROVED,
								// RECORD_STATUS_STYLE.APPROVED_STYLE,
								// RECORD_STATUS.APPROVED);

								$scope.changeDataStatus(STATUS_BTN_TEXT.PRINT,
										RECORD_STATUS_STYLE.ISSUED_STYLE,
										RECORD_STATUS.ISSUED);

								$scope.disable_all = false;
								$scope.disable_all_date = false;
								$(".acontainer input").attr('disabled', false);
								$("#btn_status").css("display", "none");
								$("#form_div_source_code").find(
										".acontainer input").attr('disabled',
										true);
								manageButtons("save");
								$("#btnBack").hide();
							}
							$scope.formData.stock_request_date = geteditDateFormat($scope.formData.stockRequestDate);
							if ($scope.formData.stockTransferDate == undefined
									|| $scope.formData.stockTransferDate == "") {
								$scope.formData.stock_transfer_date = '1000-01-01';
							} else {
								$scope.formData.stock_transfer_date = geteditDateFormat($scope.formData.stockTransferDate);
							}

							// $("#btnDelete,#btnEdit").css("display","none");
						}, function(response) { // optional

						});
	}

	$scope.reject = function() {
		// $scope.formData.stockTransfeNo = $("#stock_transfer_no").val();
		$scope.formData.stock_request_date = $("#stock_request_date").val();

		$scope.formData.store_id = 1;
		$scope.formData.reqBy = strings['userID'];
		for (var i = 0; i < $scope.invoice.items.length; i++) {
			$scope.invoice.items[i].amount = $scope.invoice.items[i].delivered_qty
					* $scope.invoice.items[i].unit_price;

		}
		$scope.formData.total_amount = $scope.total();
		$scope.stockDtlList = [];
		for (var i = 0; i < $scope.invoice.items.length; i++) {
			for (var i = 0; i < $scope.invoice.items.length; i++) {
				$scope.stockDtlList.push($scope.invoice.items[i]);
			}
		}
		$scope.formData.stockRequestDate = getMysqlFormat($scope.formData.stock_request_date);
		if ($scope.formData.stock_transfer_date == undefined
				|| $scope.formData.stock_transfer_date == "") {
			$scope.formData.stockTransferDate = '1000-01-01';
		} else {
			$scope.formData.stockTransferDate = getMysqlFormat($scope.formData.stock_transfer_date);
		}

		$scope.formData.stockDetailLists = JSON.stringify($scope.stockDtlList);

		$scope.formData.req_status = 2;
		$scope.formData.reqStatus = 2;
		$http({
			url : 'status',
			method : "POST",
			async : false,
			params : {
				id : $scope.formData.id,
				change_reason : $scope.change_reason,
				change_date : $("#change_date").val(),
				req_status : 2,
				stock_out_type : $scope.stockout_type
			},
			data : $scope.formData,

		})
				.then(
						function(response) {
							$scope.changeDataStatus(STATUS_BTN_TEXT.REJECTED,
									RECORD_STATUS_STYLE.REJECTED_STYLE,
									RECORD_STATUS.REJECTED);
							$("#btn_status").attr("disabled", true);
							$("#div_btn_edit").find("#btnEdit").attr(
									"disabled", true);
							$("#btnEdit").css("display", "none");
							vm.dtInstance.reloadData(null, true);
							$('#myModal').modal('hide');
							$scope.change_reason = "";

							$scope.formData.stock_request_date = geteditDateFormat($scope.formData.stockRequestDate);
							if ($scope.formData.stockTransferDate == undefined
									|| $scope.formData.stockTransferDate == "") {
								$scope.formData.stock_transfer_date = '1000-01-01';
							} else {
								$scope.formData.stock_transfer_date = geteditDateFormat($scope.formData.stockTransferDate);

							}
						}, function(response) { // optional

						});
	}

	$rootScope.$on('reloadDatatable', function(event) { // reload Datatable
		reloadData();
	});

	$rootScope.$on('hide_table', function(event) {
		showTable(event);
		$scope.stockout_type = 0;
		$scope
		$scope.invoice = {
			items : []
		};
		if (settings['combinePurchase'] == 1) {
			$scope.status_app = -1;
		}
		$scope.invoice.items.push({
			id : "",
			stock_item_id : "",
			stock_item_code : "",
			stockRegDtl_id : "",
			request_qty : 0,
			delivered_qty : 0,
			stock_item_batch_id : "",
			stock_item_batch_stock : 0,
			unit_price : 0,
			compound_unit:0.0,
			base_uom_code:""
		});
	});

	$rootScope.$on('hide_form', function(event) {
		if ($scope.formData.id == undefined || $scope.formData.id == "") {
			manageButtons("next");
			$scope.showReqiuredForm(false, false, true);
			$scope.hide_code_existing_er = true;

		} else {
			manageButtons("add");
			$scope.showReqiuredForm(true, false, false);
		}
		$scope.isStoreToStore = true;
		vm.dtInstance.reloadData();
	});

	$rootScope.$on('hide_type_form', function(event) {
		$scope.showReqiuredForm(true, false, false);
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass(
				'selected');
		$scope.isStoreToStore = true;
	});

	function showTable(event) {

		$("#div_finlize_print").css("display", "none");
		$("#btnDiscard,#btnSave").attr("disabled", false);
		$scope.showReqiuredForm(false, false, true);
	}

	$rootScope.$on("hide_form_type", function() {

		if (settings['combinePurchase'] == 1) {
			$scope.status_app = -1;
		}

		$("#div_finlize_print").css("display", "none");
		$("#btnDiscard,#btnSave").attr("disabled", false);
		$scope.showReqiuredForm(false, true, false);
		$("#advsearchbox").hide();
		// $('#itemLists').hide();

		if ($scope.stockout_type == 0) {
			$scope.isStoreToStore = true;
		} else if ($scope.stockout_type == 1) {
			$scope.isStoreToStore = false;
		}

	});

	$scope.showReqiuredForm = function(showTable, ShowForm, ShowStockTypeForm) {
		$scope.show_table = showTable;
		$scope.show_form = ShowForm;
		$scope.show_type_form = ShowStockTypeForm;

	}

	$rootScope.$on("disable_status", function() {
		$scope.status = "";
		$scope.ClassName = "form_list_con";
		$scope.invoice = {
			items : []
		};
		$scope.invoice.items.push({
			id : "",
			stock_item_id : "",
			stock_item_code : "",
			stockRegDtl_id : "",
			request_qty : 0,
			delivered_qty : 0,
			stock_item_batch_id : "",
			stock_item_batch_stock : 0,
			unit_price : 0,
			compound_unit:0.0,
			base_uom_code:""
		});
	});

	$scope.show_table = false;
	// $('#itemLists').hide();
	$("#advsearchbox").hide();
	$scope.prograssing1 = true;
	$('#btnAdd').hide();
	$scope.depListData = [ {
		id : -1,
		name : "ALL"
	} ];
	$scope.itemsData = [];
	$http({
		url : '../stockin/formJsonData',
		method : "GET",
		async : false,
	}).then(function(response) {
		$scope.itemsData = response.data.stockItmData;
		console.log($scope.itemsData);
		/* $scope.stockRegisterList = response.data.stockRegData; */
		$scope.depData = response.data.depData;
		console.log($scope.depData);
		$scope.prograssing1 = false;
		$scope.show_table = true;
		$("#advsearchbox").show();
		// $('#itemLists').show();
		$('#btnAdd').show();
		for (var i = 0; i < $scope.depData.length; i++) {
			$scope.depListData.push($scope.depData[i]);

		}
		$rootScope.$emit("get_dep_list", {
			dep : $scope.depListData
		});
	}, function(response) { // optional

	});

	function loadItemDetailsTable() {
		$http({
			url : '../batch/json',
			method : "GET",
			async : false,
		}).then(function(response) {
			$scope.itemsBatchData = response.data.data;
		}, function(response) { // optional

		});
	}
	function reloadData() {
		vm.dtInstance.reloadData(null, true);
		loadItemDetailsTable();
	}

	$scope.reloadDatatable = function(e) {
		var filter_id = e.target.id;
		if (filter_id == "isall") {
			$("#pending,#approved,#rejected,#print,#issued").prop("checked",
					false);
		} else if (filter_id == "pending" || filter_id == "approved"
				|| filter_id == "rejected" || filter_id == "print"
				|| filter_id == "issued") {
			$("#isall").prop("checked", false);
			if ($("#pending").is(':checked') == false
					&& $("#approved").is(':checked') == false
					&& $("#rejected").is(':checked') == false
					&& $("#print").is(':checked') == false
					&& $("#issued").is(':checked') == false) {
				$("#isall").prop("checked", true);
			}
		}
		vm.dtInstance.reloadData(null, true);
	}

	function edit(row_data, cur_row_index, event) {
		$scope.invoice = {
			items : []
		};
		$scope.invoice.items.push({
			id : "",
			stock_item_id : "",
			stock_item_code : "",
			stockRegDtl_id : "",
			request_qty : 0.00,
			delivered_qty : 0,
			stock_item_batch_id : "",
			stock_item_batch_stock : 0,
			unit_price : 0,
			base_uom_code :"",
			compound_unit:0.0
		});

		if (row_data.req_status >= 3) {

			$scope.currentstockreg_id = fun_get_stockRegId(row_data.id);
		}

		$scope.clearForm();

		//$scope.editData=true;
		$("#ref_code").css("display","block");
		$("#grnCode_label").css("display","block");
		$("#btn_status").css("display", "block");
		//alert("stock out 4==========>");
		$("#status_div_id").css("display", "block");
		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");// Edit function
		var row_count = vm.dtInstance.DataTable.rows().data();
		row_count = row_count.length;
		if (row_count == 1) {
			$rootScope.$emit("disable_next_btn");
			$rootScope.$emit("disable_prev_btn");
		} else {
			if (cur_row_index == 0) {
				$rootScope.$emit("enable_next_btn");
				$rootScope.$emit("disable_prev_btn");
			} else if (row_count - 1 == cur_row_index) {
				$rootScope.$emit("disable_next_btn");
				$rootScope.$emit("enable_prev_btn");
			}
		}
		$("#div_finlize_print").css("display", "none");
		$scope.show_table = false;
		$("#advsearchbox").hide();
		// $('#itemLists').hide();
		$scope.show_form = true;

		$scope.show_type_form = false;
		clearform();
		$scope.hide_code_existing_er = true;

		$rootScope.$emit("show_edit_btn_div", cur_row_index);
		// $scope.REFNO = row_data.stock_transfer_no;
		$scope.formData.stockTransfeNo = row_data.stock_transfer_no;
		$scope.CurrentDate = row_data.stock_request_date;
		var form_depList = fun_get_dep_name(row_data.source_department_id);
		var to_depList = fun_get_dep_name(row_data.dest_department_id);
		$("#form_div_source_code").find(".acontainer input").val(
				form_depList.code);
		$("#form_div_desti_code").find(".acontainer input")
				.val(to_depList.code);
		$('#to_department_name').val(to_depList.name);

		$scope.formData = {
			id : row_data.id,
			req_status : row_data.req_status,
			stockTransfeNo : row_data.stock_transfer_no,
			source_department_id : row_data.source_department_id,
			dest_department_id : row_data.dest_department_id,
			source_code : form_depList.code,
			dest_code : to_depList.code,
			source_name : form_depList.name,
			desti_name : to_depList.name,
			remarks : row_data.remarks,
			stock_transfer_date : row_data.stock_transfer_date === '1000-01-01' ? ""
					: geteditDateFormat(row_data.stock_transfer_date),
			stock_request_date : geteditDateFormat(row_data.stock_request_date)
		};

		if (settings['combinePurchase'] == 1) {
			$scope.status_app = row_data.req_status;
		}

		$scope.stockout_type = row_data.stock_out_type;

		var des_department = row_data.dest_department_id;

		setItemtableValues(row_data.id, des_department);
		$scope.disable_all = true;
		$scope.disable_all_date = true;
		$scope.disable_grn = true;
		$scope.issue_stat = row_data.req_status;

		$(".acontainer input").attr('disabled', true);
		// $scope.disable_code = true;
		$("#div_finlize_print").css("display", "block");
		$scope.cur_date_finalize = moment().format("YYYY-MM-DD");
		if (row_data.req_status == 0) {
			$scope.changeDataStatus(STATUS_BTN_TEXT.APPROVE_OR_REJECT,
					RECORD_STATUS_STYLE.NEW_STYLE, RECORD_STATUS.NEW);
			$("#btn_status").attr("disabled", false);
			$("#btnDelete,#btnEdit").css("display", "block");
			// $("#btnDelete,#btnEdit").attr("disabled",false);
		} else if (row_data.req_status == 1) {

			$scope.changeDataStatus(STATUS_BTN_TEXT.ISSUE,
					RECORD_STATUS_STYLE.APPROVED_STYLE, RECORD_STATUS.APPROVED);

			// $scope.apprvstatus=row_data.req_status;

			$scope.disable_all_date = false;

			$scope.disable_all = false;
			$(".acontainer input").attr('disabled', false);

			$("#btn_status").css("display", "none");
			$("#form_div_source_code").find(".acontainer input").attr(
					'disabled', true);
			manageButtons("save");
			$("#btnBack").hide();

		} else if (row_data.req_status == 2) {
			$scope.changeDataStatus(STATUS_BTN_TEXT.REJECTED,
					RECORD_STATUS_STYLE.REJECTED_STYLE, RECORD_STATUS.REJECTED);
			// $("#div_btn_edit").find("#btnEdit").attr("disabled",true);
			$("#btn_status").attr("disabled", true);
			$("#btnDelete,#btnEdit").css("display","none");
			$("#btnEdit").css("display", "none");
		} else if (row_data.req_status == 3) {
			$scope.changeDataStatus(STATUS_BTN_TEXT.PRINT,
					RECORD_STATUS_STYLE.ISSUED_STYLE, RECORD_STATUS.ISSUED);
			 $("#btnDelete,#btnSave").attr("disabled",true);
			$("#btn_status").attr("disabled", false);

			$("#btnDelete,#btnEdit").css("display","none");
			// $("#btnEdit").css("display","none");
		} else if (row_data.req_status == 4) {
			$scope.changeDataStatus(STATUS_BTN_TEXT.RE_PRINT,
					RECORD_STATUS_STYLE.PRINTED_STYLE, RECORD_STATUS.PRINTED);
			$("#btn_status").attr("disabled", false);
			 $("#btnDelete,#btnSave").attr("disabled",true);
			$("#btnDelete,#btnEdit").css("display","none");
		}
		if (row_data.stock_out_type == 1) {
			$scope.isStoreToStore = false;
			$("#companyId option[value='" + row_data.dest_company_id + "']")
					.prop('selected', true);
			$scope.formData.dest_company_id = row_data.dest_company_id;
			$scope.formData.dest_company_code = row_data.dest_company_code;
			$scope.formData.dest_company_name = row_data.dest_company_name;
		} else if (row_data.stock_out_type == 0) {
			$scope.isStoreToStore = true;
			$("#companyId option[value='0']").prop('selected', true);
		}

		var permissionn = $('#finalizepermission').val();

		if (permissionn == 'false' && row_data.req_status != 0
				&& row_data.req_status != 1) {

			$("#btnEdit").hide();

		}

		if (row_data.stock_out_status == "RECEIVED"
				|| row_data.stock_out_status == "ISSUED"
				|| row_data.stock_out_status == "PRINTED") {
			$("#btnEdit").hide();
		}
	}

	function fun_get_stockRegId(refno) {

		var stockRegid = "";
		$http({
			url : '../stockin/getstockredid',
			method : "GET",
			params : {
				id : refno
			},
		}).then(function(response) {

			$scope.currentstockreg_id = response.data.stockregid[0].id;
			if (response.data.stockregid.length != 0) {
				stockRegid = response.data.stockregid[0].id;
			}

		}, function(response) { // optional

		});

		return stockRegid;
	}

	$(document).on('keyup', '#form_div_source_code input', function(e) {
		if (e.which == 13) {
			$("#form_div_desti_code input").focus();
		}
		if (e.which != 9 && e.which != 13) {
			$scope.$apply(function() {
				$scope.formData.source_department_id = "";
				$scope.formData.source_code = "";
				$scope.formData.source_name = "";
				$scope.getChangeStock();

			});
		}
	});

	$(document).on(
			'keyup',
			'#form_div_desti_code input',
			function(e) {
				if (e.which == 13) {
					$(
							"#items_table tr:nth-child(" + (2)
									+ ") td:nth-child(" + (3) + ")").find(
							".acontainer input").focus();
				}
				if (e.which != 9 && e.which != 13) {
					$scope.$apply(function() {
						$scope.formData.dest_department_id = "";
						$scope.formData.desti_name = "";
						$scope.formData.dest_code = "";
					});
				}
			});

	function setItemtableValues(id, des_department, stockregId) {
		$scope.prograssing = true;

		$scope.invoice = {
			items : []
		};
		loadItemDetailsTable();
		$http({
			url : 'getStockOutDtlData',
			method : "GET",
			params : {
				id : id,
				destDepartmentName : des_department
			}
		})
				.then(
						function(response) {

							for (var i = 0; i < response.data.stkOutDtl.length; i++) {

								$scope.invoice.items
										.push({
											uomcode : response.data.stkOutDtl[i].uomcode,
											id : response.data.stkOutDtl[i].id,
											stock_item_id : response.data.stkOutDtl[i].stock_item_id,
											// currentStock :
											// $scope.getItemmasterBatchCurrentStock(response.data.stkOutDtl[i].stock_item_id),
											currentStock : (parseFloat(response.data.stkOutDtl[i].current_stock)
													.toFixed(settings['decimalPlace'])),
											pending_qty : (parseFloat(response.data.stkPendingOutDtl[i].pending_stock1)
													.toFixed(settings['decimalPlace'])),// response.data.stkPendingOutDtl[i].pending_stock
											stock_item_code : response.data.stkOutDtl[i].stock_item_code,
											stock_item_name : response.data.stkOutDtl[i].stock_item_name,

											request_qty : (parseFloat(response.data.stkOutDtl[i].request_qty)
													.toFixed(settings['decimalPlace'])),

											/*
											 * delivered_qty :
											 * (parseFloat(response.data.stkOutDtl[i].issued_qty))>0?(parseFloat(response.data.stkOutDtl[i].issued_qty)
											 * .toFixed(settings['decimalPlace'])):((parseFloat(response.data.stkOutDtl[i].request_qty)+parseFloat(response.data.stkPendingOutDtl[i].pending_stock1))
											 * .toFixed(settings['decimalPlace'])),
											 */

											delivered_qty : (parseFloat(response.data.stkOutDtl[i].issued_qty)) > 0 ? (parseFloat(response.data.stkOutDtl[i].issued_qty)
													.toFixed(settings['decimalPlace']))
													: (parseFloat(response.data.stkOutDtl[i].request_qty)
															.toFixed(settings['decimalPlace'])),

											unit_price : (parseFloat(response.data.stkOutDtl[i].cost_price)
													.toFixed(settings['decimalPlace'])),
													stock_out_hdr_id : response.data.stkOutDtl[i].stock_out_hdr_id,
													base_uom_code:	response.data.stkOutDtl[i].base_uom_code,
													compound_unit:  (parseFloat(response.data.stkOutDtl[i].compound_unit)
															.toFixed(settings['decimalPlace'])),
															isSet : false,

						});

						baseUomCode[i] = $scope.setuomCode(response.data.stkOutDtl[i].uomcode,i);
					}
					$scope.prograssing = false;

						}, function(response) {
							$scope.prograssing = false;

						});
	}

	function fun_get_dep_name(id) {
		var depList = [];
		for (var i = 0; i < department_data.length; i++) {
			if (department_data[i].id == id) {
				depList = department_data[i];
			}
		}

		return depList;
	}
	$rootScope.$on("fun_delete_current_data", function(event) { // Delete
		// Function
		$scope.showConfirm(FORM_MESSAGES.DELETE_WRNG, "DELETE", event);
	});

	function setBatchData() {

		$scope.formData.batch = [];
		for (var i = 0; i < $scope.invoice.items.length; i++) {
			$scope.formData.batch
					.push({
						stock_item_batch_id : $scope.invoice.items[i].stock_item_batch_id,
						delivered_qty : $scope.invoice.items[i].delivered_qty,
						cost_price : $scope.invoice.items[i].cost_price,
						stock_item_id : $scope.invoice.items[i].stock_item_id,
					});

		}
	}

	$scope.deleteData = function(event) {
		/*
		 * setBatchData();
		 */var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$http({
			url : 'delete',
			method : "POST",
			params : {
				id : $scope.formData.id,
				stock_transfer_no : $scope.formData.stockTransfeNo,
				stockreg_id : $scope.currentstockreg_id
			/* ,batch:$scope.formData.batch */},
		}).then(
				function(response) {
					if (response.data == 1) {
						$scope.disable_all = true;
						$scope.disable_all_date = true;
						$scope.disable_code = true;
						$rootScope.$broadcast('on_AlertMessage_SUCC',
								FORM_MESSAGES.DELETE_SUC);
						if ($scope.formData.id == undefined
								|| $scope.formData.id == "") {
							manageButtons("next");
							$scope.show_table = false;
							 $("#ref_code").css("display","none");
							 $("#grnCode_label").css("display","none");
							$("#advsearchbox").hide();
							// $('#itemLists').hide();
							$scope.show_form = false;
							$scope.show_type_form = true;
						} else {
							manageButtons("add");
							$scope.show_table = true;
							$("#advsearchbox").show();
							// $('#itemLists').show();
							$scope.show_form = false;
							 // $scope.editData=false;
							 $("#ref_code").css("display","none");
							 $("#grnCode_label").css("display","none");
							$scope.show_type_form = false;
						}
						$scope.isStoreToStore = true;
						$('#show_form').val(0);
						vm.dtInstance.reloadData(null, true);
					} else {
						$mdDialog.show($mdDialog.alert().parent(
								angular.element(document
										.querySelector('#dialogContainer')))
								.clickOutsideToClose(true).textContent(
										"Delete failed.").ok('Ok!')
								.targetEvent(event));
					}
				},
				function(response) { // optional
					$mdDialog.show($mdDialog.alert().parent(
							angular.element(document
									.querySelector('#dialogContainer')))
							.clickOutsideToClose(true).textContent(
									"Delete failed.").ok('Ok!').targetEvent(
									event));
				});
	}

	function view_mode_aftr_edit() {
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div", cur_row_index);
		$scope.disable_all = true;
		$scope.disable_all_date = true;
		$scope.disable_code = true;
		$scope.disable_grn = true;
		$(".acontainer input").attr('disabled', true);
	}

	$scope.setFormValues = function() {
		$scope.formData.fromDepartmentId = $scope.formData.source_department_id;
		$scope.formData.toDepartmentId = $scope.formData.dest_department_id;
		// $scope.formData.stockTransfeNo = $("#stock_transfer_no").val();
		$scope.formData.stockRequestDate = $("#stock_request_date").val();
		if ($scope.formData.id == undefined) {
			$scope.formData.reqStatus = 0;
		} else {
			$scope.formData.reqStatus = $scope.formData.req_status;
		}

		$scope.formData.reqBy = strings['userID'];
		for (var i = 0; i < $scope.invoice.items.length; i++) {
			$scope.invoice.items[i].amount = $scope.invoice.items[i].delivered_qty
					* $scope.invoice.items[i].unit_price;
		}
		if (stockOutStrings['isRequest'] == 0) {
			$scope.formData.stockOutType = 0;
		} else if (stockOutStrings['isRequest'] == 1) {
			$scope.formData.stockOutType = $scope.stockout_type;
			if ($scope.formData.stockOutType == 1) {
				$scope.formData.destCompanyId = $("#companyId").val();
				$scope.formData.destCompanyCode = $(
						"#companyId option[value='"
								+ $scope.formData.dest_company_id + "']").attr(
						"id");
				$scope.formData.destCompanyName = $(
						"#companyId option[value='"
								+ $scope.formData.dest_company_id + "']")
						.text();
			} else {
				$scope.formData.destCompanyId = "";
				$scope.formData.destCompanyCode = "";
				$scope.formData.destCompanyName = "";
			}
		}

		$scope.formData.total_amount = $scope.total();
		$scope.formData.change_date = moment().format("YYYY-MM-DD");
		$scope.stockDtlList = [];
		for (var i = 0; i < $scope.invoice.items.length; i++) {
			$scope.stockDtlList.push($scope.invoice.items[i]);
		}

	}
	$scope.stockDtlList = [];
	$scope.saveData = function(event) {

		if (code_existing_validation($scope.formData)) {
			$scope.setFormValues();
			$scope.formData.stockRequestDate = getMysqlFormat($scope.formData.stockRequestDate);
			if ($scope.formData.stock_transfer_date == undefined
					|| $scope.formData.stock_transfer_date == "") {
				$scope.formData.stockTransferDate = '1000-01-01';
			} else {
				$scope.formData.stockTransferDate = getMysqlFormat($scope.formData.stock_transfer_date);
			}
			$scope.formData.stockDetailLists = JSON
					.stringify($scope.stockDtlList);

			if (($scope.formData.dest_department_id == ""
					&& $scope.formData.dest_code == "" && $scope.formData.formData.desti_name == "")
					|| ($scope.formData.dest_department_id == undefined
							&& $scope.formData.dest_code == undefined && $scope.formData.desti_name == undefined)) {
				$("#form_div_desti_code").addClass("has-error");
				$("#form_div_supplier_code_error").show();
				flg = false;
				$("#form_div_supplier_code").find(".acontainer input").focus();
				return false;
			} else {
				$("#form_div_supplier_code_error").removeClass("has-error");
				$("#form_div_supplier_code_error").hide();
			}

			$http({
				url : 'saveStockItem',
				method : "POST",
				data : $scope.formData,
				async : false,
			})
					.then(
							function(response) {
								if (response.data == 1) {
									reloadData();
									if ($scope.formData.id != undefined) {
										$rootScope.$broadcast(
												'on_AlertMessage_SUCC',
												FORM_MESSAGES.UPDATE_SUC);
										view_mode_aftr_edit();
										$("#btn_status")
												.css("display", "block");
										$scope.formData.stock_request_date = geteditDateFormat($scope.formData.stockRequestDate);
										if ($scope.formData.stock_transfer_date == undefined
												|| $scope.formData.stock_transfer_date == "") {
											$scope.formData.stock_transfer_date = '1000-01-01';
										} else {
											$scope.formData.stock_transfer_date = geteditDateFormat($scope.formData.stockTransferDate);
										}
									} else {
										$rootScope.$broadcast(
												'on_AlertMessage_SUCC',
												FORM_MESSAGES.SAVE_SUC);
										fun_get_refno();
										$scope.invoice = {
											items : []
										};
										$scope.invoice.items.push({
											id : "",
											stock_item_id : "",
											stock_item_code : "",
											stockRegDtl_id : "",
											request_qty : 0,
											delivered_qty : 0,
											stock_item_batch_id : "",
											stock_item_batch_stock : 0,
											unit_price : 0,
									compound_unit:0.0,
									base_uom_code:""
										});
										$("#form_div_desti_code").find(
												".acontainer input").val("");
										$("#form_div_source_code").find(
												".acontainer input").val("");
										$("#table_validation_alert")
												.removeClass("in");
										getSourcedep();
										getCurrentDate();
										$("#companyId option[value='0']").prop(
												'selected', true);

									}

									if ($scope.formData.req_status != 0
											&& $scope.formData.req_status != 1
											&& $scope.formData.req_status != ""
											&& $scope.formData.req_status != undefined) {
										$scope.issue();
									}

									if ($scope.formData.req_status == 1) {
										$scope
												.changeDataStatus(
														STATUS_BTN_TEXT.ISSUE,
														RECORD_STATUS_STYLE.APPROVED_STYLE,
														RECORD_STATUS.APPROVED);
										$("#btn_status")
												.attr("disabled", false);
									}

								} else {
									$mdDialog
											.show($mdDialog
													.alert()
													.parent(
															angular
																	.element(document
																			.querySelector('#dialogContainer')))
													.clickOutsideToClose(true)
													.textContent("Save failed.")
													.ok('Ok!').targetEvent(
															event));
								}
							}, function(response) { // optional
								$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
							});
		}
	}

	$rootScope.$on('fun_save_data', function(event) { // Save Function
		$scope.saveData(event);
	});

	$rootScope.$on("fun_discard_form", function(event) { // Discard function
		$scope.showConfirm(FORM_MESSAGES.DISCARD_WRNG, "DISCARD", event);
	});

	$scope.discardData = function() {

		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		if ($scope.formData.id == undefined) {
			$scope.formData = {};
			$scope.hide_code_existing_er = true;
			$("#form_div_source_code").find(".acontainer input").val("");
			$("#form_div_desti_code").find(".acontainer input").val("");
			$("#table_validation_alert").removeClass("in");
			$scope.invoice = {
				items : []
			};
			$scope.invoice.items.push({
				id : "",
				stock_item_id : "",
				stock_item_code : "",
				stockRegDtl_id : "",
				request_qty : 0,
				delivered_qty : 0,
				stock_item_batch_id : "",
				stock_item_batch_stock : 0,
				unit_price : 0,
				base_uom_code:"",
				compound_unit:0.0
			});
			getSourcedep();
			getCurrentDate();
		} else {
			if (stockOutStrings['isRequest'] == 1) {
				var dataIndex = vm.dtInstance.DataTable.rows();
				var row_data = vm.dtInstance.DataTable.rows(
						dataIndex[0][cur_row_index]).data();
				edit(row_data[0], cur_row_index);
			} else if (stockOutStrings['isRequest'] == 0) {
				var row_datas = vm.dtInstance.DataTable.rows().data();
				var data = [];
				var current_row_index = 0;
				for (var i = 0; i < row_datas.length; i++) {
					if (row_datas[i].req_status == 0) {
						data.push(row_datas[i]);
					}
				}
				for (var j = 0; j < data.length; j++) {
					if (data[j].id == $scope.formData.id) {
						current_row_index = j;
					}
				}
				edit(data[current_row_index], cur_row_index);
			}
		}
		clearform();
		fun_get_refno();
		$scope.hide_code_existing_er = true;
	}

	$rootScope.$on("fun_enable_inputs", function() {

		$scope.disable_all = false;
		$scope.disable_all_date = false;
		$(".acontainer input").attr('disabled', false);
		$("#btn_status").css("display", "none");
		$("#form_div_source_code").find(".acontainer input").attr('disabled',
				true);

		$scope.disable_grn = true;

		if ($scope.issue_stat >= 3) {
			$scope.disable_all_date = true;

		}
	});

	$rootScope.$on("fun_enable_inputs_code", function() {
		$scope.disable_code = true;
		$scope.issue_stat = -1;
	});

	$rootScope.$on("fun_clear_form", function() {
		if ($scope.issue_stat >= 3) {
			$scope.disable_all_date = true;

		}
		else
		{
			$scope.disable_all_date=false;
		//	$scope.editData=false;
			$("#ref_code").css("display","none");
			$("#grnCode_label").css("display","none");
		}
		$scope.hide_code_existing_er = true;
		$("#form_div_dest_code").find(".acontainer input").focus()
		$scope.CurrentDate = $scope.getCurrentDate();
		fun_get_refno();
		$scope.formData = {};
		$(".acontainer input").val("");
		$scope.invoice = {
			items : []
		};

		$scope.invoice.items.push({
			id : "",
			stock_item_id : "",
			stock_item_code : "",
			stockRegDtl_id : "",
			request_qty : 0,
			delivered_qty : 0,
			unit_price : 0,
			compound_unit:0.0,
			base_uom_code:""
		});
		$('#stockin_type option:eq(0)').prop('selected', true);
		$("#table_validation_alert").removeClass("in");
		// function_clear_select();
		getSourcedep();
		getCurrentDate();
		if (stockOutStrings['isRequest'] == 0) {
			$scope.isStoreToStore = true;
		} else if (stockOutStrings['isRequest'] == 0) {
			if ($scope.stockout_type == 0) {
				$scope.isStoreToStore = true;
			} else {
				$scope.isStoreToStore = false;
			}
		}

		$(".acontainer input").attr('disabled', false);
		$scope.clearForm();
		$scope.formData.req_status = 0;
		$scope.disable_grn = true;

	});

	function getCurrentDate() {
		var curDate = moment().format("YYYY-MM-DD");
		$scope.formData.stock_request_date = geteditDateFormat(curDate);
		$scope.formData.stock_transfer_date = geteditDateFormat(curDate);
	}

	// Manupulate Formdata when Edit mode - Prev-Next feature add
	$rootScope.$on("fun_next_rowData", function(e, id) {
		var current_row_index = 0;
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		if (stockOutStrings['isRequest'] == 1) {
			var current_row_index = parseInt(id.split("_")[1]);
			var dataIndex = vm.dtInstance.DataTable.rows();
			if (current_row_index == 0) {
				$rootScope.$emit("enable_prev_btn");
			}
			if (row_data.length == current_row_index + 1) {
				$rootScope.$emit("disable_next_btn");
			}
			var next_row_index = current_row_index + 1;
			if (row_data[next_row_index] != undefined) {
				var selcted_row_data = vm.dtInstance.DataTable.rows(
						dataIndex[0][next_row_index]).data();
				edit(selcted_row_data[0], next_row_index);
				$rootScope.$emit("next_formdata_set", next_row_index);
			}
		} else if (stockOutStrings['isRequest'] == 0) {
			var data = [];
			for (var i = 0; i < row_data.length; i++) {
				if (row_data[i].req_status == 0) {
					data.push(row_data[i]);
				}
			}
			for (var j = 0; j < data.length; j++) {
				if (data[j].id == $scope.formData.id) {
					current_row_index = j;
				}
			}
			if (current_row_index == 0) {
				$rootScope.$emit("enable_prev_btn");
			}
			if (row_data.length == current_row_index + 1) {
				$rootScope.$emit("disable_next_btn");
			}
			var next_row_index = current_row_index + 1;
			if (data[next_row_index] != undefined) {
				var selcted_row_data = data[next_row_index];
				edit(selcted_row_data, next_row_index);
				$rootScope.$emit("next_formdata_set", next_row_index);
			}
		}

	});

	$rootScope.$on("fun_prev_rowData", function(e, id) {
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var dataIndex = vm.dtInstance.DataTable.rows();
		if (stockOutStrings['isRequest'] == 1) {
			var current_row_index = parseInt(id.split("_")[1]);
			if (row_count - 1 == current_row_index) {
				$rootScope.$emit("enable_next_btn");
			}
			var prev_row_index = current_row_index - 1;
			if (row_data[prev_row_index] != undefined) {
				var selcted_row_data = vm.dtInstance.DataTable.rows(
						dataIndex[0][prev_row_index]).data();
				edit(selcted_row_data[0], prev_row_index);
				$rootScope.$emit("next_formdata_set", prev_row_index);
			}
			if (current_row_index - 1 == 0) {
				$rootScope.$emit("disable_prev_btn");
			}
		} else if (stockOutStrings['isRequest'] == 0) {
			var data = [];
			var current_row_index = 0;
			for (var i = 0; i < row_data.length; i++) {
				if (row_data[i].req_status == 0) {
					data.push(row_data[i]);
				}
			}
			for (var j = 0; j < data.length; j++) {
				if (data[j].id == $scope.formData.id) {
					current_row_index = j;
				}
			}
			if (data.length - 1 == current_row_index) {
				$rootScope.$emit("enable_next_btn");
			}
			var prev_row_index = current_row_index - 1;
			if (data[prev_row_index] != undefined) {
				var selcted_row_data = data[prev_row_index];
				edit(selcted_row_data, prev_row_index);
				$rootScope.$emit("next_formdata_set", prev_row_index);
			}
			if (current_row_index - 1 == 0) {
				$rootScope.$emit("disable_prev_btn");
			}

		}

	});

	/*
	 * $scope.itemsBatchData1 = [];
	 * 
	 * $http({ url : '../itemstock/json', method : "GET", async : false,
	 * }).then(function(response) { $scope.itemsBatchData1 = response.data.data; },
	 * function(response) { // optional
	 * 
	 * });
	 */

	/*
	 * $scope.getItemmasterBatchCurrentStock = function(itemid) {
	 * $scope.itemBatchcurrentStock = 0; for (var i = 0; i <
	 * $scope.itemsBatchData1.length; i++) { if
	 * ($scope.itemsBatchData1[i].stock_item_id == itemid &&
	 * $scope.formData.source_department_id ==
	 * $scope.itemsBatchData1[i].department_id) { $scope.itemBatchcurrentStock =
	 * $scope.itemsBatchData1[i].current_stock; break; } }
	 * $scope.itemBatchcurrentStock = ($scope.itemBatchcurrentStock == "") ? 0 :
	 * $scope.itemBatchcurrentStock;
	 * 
	 * return parseFloat($scope.itemBatchcurrentStock).toFixed(
	 * settings['decimalPlace']); }
	 */

	$scope.getItemmasterCurrentStock = function(itemid, deptId, curdate, index,
			desDepTd) {
		var current_stock = 0;
		var checkReturn=0;
		if (curdate != "" && curdate != undefined) {
			$http({
				url : 'getCurrStock',
				method : "GET",
				params : {
					checkReturn:checkReturn,
					itemId : itemid,
					department_id : deptId,
					current_date : getMysqlFormat(curdate),
					desDepartmentId : desDepTd
				},
				async : false,
			}).then(function(response) {
				console.log(response);
				current_stock = response.data[1];

				$scope.invoice.items[index].currentStock = current_stock;
				$scope.invoice.items[index].pending_qty = response.data[0];

			}, function(response) { // optional

			});
		}
		return parseFloat(current_stock).toFixed(settings['decimalPlace']);

	}

	$scope.getChangeStock = function() {
		if ($scope.invoice.items.length > 0) {
			for (var i = 0; i < $scope.invoice.items.length; i++) {
				$scope.invoice.items[i].currentStock = $scope
						.getItemmasterCurrentStock(
								$scope.invoice.items[i].stock_item_id,
								$scope.formData.source_department_id,
								$scope.formData.stock_transfer_date, i)
			}
		}

	}

	// @@@@@@@
	$scope.getOnchangePendingStock = function(deptId) {

		if ($scope.invoice.items.length > 0) {
			for (var i = 0; i < $scope.invoice.items.length; i++) {

				$scope.invoice.items[i].pending_qty = $scope.getPendingStock(
						$scope.invoice.items[i].stock_item_id, deptId, i);

				/*
				 * if($scope.invoice.items[i].issued_qty<=0){
				 * $scope.invoice.items[i].delivered_qty=
				 * parseFloat($scope.invoice.items[i].request_qty)+parseFloat($scope.invoice.items[i].pending_qty);
				 * }else{ $scope.invoice.items[i].delivered_qty=
				 * parseFloat($scope.invoice.items[i].request_qty).toFixed(settings['decimalPlace']); }
				 */
				$scope.invoice.items[i].delivered_qty = parseFloat(
						$scope.invoice.items[i].request_qty).toFixed(
						settings['decimalPlace']);

				/*
				 * delivered_qty :
				 * (parseFloat(response.data.stkOutDtl[i].issued_qty))>0?(parseFloat(response.data.stkOutDtl[i].issued_qty)
				 * .toFixed(settings['decimalPlace'])):((parseFloat(response.data.stkOutDtl[i].request_qty)+parseFloat(response.data.stkPendingOutDtl[i].pending_stock1))
				 * .toFixed(settings['decimalPlace'])),
				 */

			}
		}

	}

	//

	$scope.getPendingStock = function(itemid, deptId, index) {
		var pending_qty = 0;
		if (itemid != "") {
			$http({
				url : 'getPendingStock',
				method : "GET",
				params : {
					itemId : itemid,
					department_id : deptId
				},
				async : false,
			}).then(function(response) {

				pending_qty = response.data;
				$scope.invoice.items[index].pending_qty = response.data;
			}, function(response) { // optional

			});
		}

		return parseFloat(pending_qty).toFixed(settings['decimalPlace']);

	}

	// Validation

	function code_existing_validation(data) {

		var row_data = vm.dtInstance.DataTable.rows().data();
		if (data.id == undefined || data.id == "") {
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#code").select();
			}

			for (var i = 0; i < row_data.length; i++) {
				if (row_data[i].stock_transfer_no == data.stockTransfeNo) {
					$scope.hide_code_existing_er = false;
					$scope.existing_code = '"' + data.stockTransfeNo
							+ '" Existing';
					flg = false;
				}
			}

		}

		if ($scope.invoice.items[$scope.invoice.items.length - 1].stock_item_id == ""
				&& $scope.invoice.items.length != 1) {
			$scope.invoice.items.splice($scope.invoice.items.length - 1, 1);
		}
		var flg = true;
		if (validation() == false) {
			flg = false;
		}

		if ($scope.formData.id == undefined || $scope.formData.id == "") {
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#ref_code").select();

			}
		}

		if ($scope.formData.stock_request_date == "") {
			$("#form_div_stock_request_date").addClass("has-error");
			$("#form_div_stock_request_date_error").show();
			flg = false;
			$("#stock_request_date").focus();
			return false;

		} else {
			$("#form_div_stock_request_date").removeClass("has-error");
			$("#form_div_stock_request_date_error").hide();
		}
		if ($scope.stockoutFormflag == 1) {

			if ($scope.formData.source_department_id == ""
					&& $scope.formData.source_code == ""
					&& $scope.formData.source_name == "") {
				$("#form_div_source_code").addClass("has-error");
				$("#form_div_source_code_error").show();
				flg = false;
				$("#form_div_source_code").find(".acontainer input").focus();
				return false;
			} else {
				$("#form_div_source_code").removeClass("has-error");
				$("#form_div_source_code_error").hide();
			}

			if (($scope.formData.dest_department_id == "" || $scope.formData.dest_department_id == undefined)
					&& ($scope.formData.dest_code == "" || $scope.formData.dest_code == undefined)
					&& ($scope.formData.desti_name == "" || $scope.formData.desti_name == undefined)) {

				$("#form_div_desti_code").addClass("has-error");
				$("#form_div_desti_code_error").show();
				flg = false;
				$("#form_div_desti_code").find(".acontainer input").focus();
				return false;
			} else {
				$("#form_div_desti_code").removeClass("has-error");
				$("#form_div_desti_code_error").hide();
			}

		}
		if ($scope.isStoreToStore == false) {
			if ($("#companyId").val() == 0) {
				$("#form_div_dest_company").addClass("has-error");
				$("#form_div_source_company_error").show();
				flg = false;
				$("#companyId").focus();
				return false;

			} else {
				$("#form_div_dest_company").removeClass("has-error");
				$("#form_div_source_company_error").hide();
			}

			if ($scope.formData.source_department_id == ""
					&& $scope.formData.source_code == ""
					&& $scope.formData.source_name == "") {
				$("#form_div_source_code").addClass("has-error");
				$("#form_div_source_code_error").show();
				flg = false;
				$("#form_div_source_code").find(".acontainer input").focus();
				return false;

			} else {
				$("#form_div_source_code").removeClass("has-error");
				$("#form_div_source_code_error").hide();
			}

		}

		if ($scope.invoice.items.length > 0) {
			$scope.item_table_empty = [];
			$scope.item_details_table_empty = [];
			for (var i = 0; i < $scope.invoice.items.length; i++) {
				/* $.each($scope.invoice.items[i],function(key,value){ */
				if ($scope.invoice.items[i].stock_item_id != ""
						&& $scope.invoice.items[i].stock_item_code != "") {
					if (stockOutStrings['isRequest'] == 1
							&& settings['combinePurchase'] == 0) {
						if ($scope.invoice.items[i].delivered_qty == ""
								|| $scope.invoice.items[i].delivered_qty == parseFloat(
										0).toFixed(settings['decimalPlace'])
								|| $scope.invoice.items[i].delivered_qty == "0"
								|| $scope.invoice.items[i].delivered_qty == "0.") {
							$rootScope.$broadcast('on_AlertMessage_ERR',
									'Please enter the Deliverd Qty for Items');
							flg = false;
							$scope.item_details_table_empty
									.push($scope.invoice.items[i]);
							$("#items_table tr:nth-child(" + (i + 2) + ")")
									.find("#delivered_qty").select();
						}
					}

					if (settings['combinePurchase'] == 1
							&& $scope.status_app == 1) {
						if ($scope.invoice.items[i].delivered_qty == ""
								|| $scope.invoice.items[i].delivered_qty == parseFloat(
										0).toFixed(settings['decimalPlace'])
								|| $scope.invoice.items[i].delivered_qty == "0"
								|| $scope.invoice.items[i].delivered_qty == "0.") {
							$rootScope.$broadcast('on_AlertMessage_ERR',
									'Please enter the Deliverd Qty for Items');
							flg = false;
							$scope.item_details_table_empty
									.push($scope.invoice.items[i]);

							$("#items_table tr:nth-child(" + (i + 2) + ")")
									.find("#delivered_qty").select();
						}

					}

					if (stockOutStrings['isRequest'] == 0
							|| settings['combinePurchase'] == 1) {
						if ($scope.invoice.items[i].request_qty == ""
								|| $scope.invoice.items[i].request_qty == "0"
								|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == parseFloat(
										0).toFixed(settings['decimalPlace'])
								|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == "0.") {
							$rootScope.$broadcast('on_AlertMessage_ERR',
									'Please Enter Request Qty');
							flg = false;
							$scope.item_details_table_empty
									.push($scope.invoice.items[i]);

							if (settings['combinePurchase'] == 1) {
								$("#items_table tr:nth-child(" + (i + 2) + ")")
										.find("#request_qty").select();

							} else {
								$("#items_table tr:nth-child(" + (i + 2) + ")")
										.find("#request_qty").select();
							}

						}
					}

					// if ($scope.formData.id == undefined) {
					if (settings['combinePurchase'] == 0) {
						if (stockOutStrings['isRequest'] == 1) {

							if ($scope.invoice.items[i].delivered_qty == ""
									|| $scope.invoice.items[i].delivered_qty == parseFloat(
											0)
											.toFixed(settings['decimalPlace'])
									|| $scope.invoice.items[i].delivered_qty == "0"
									|| $scope.invoice.items[i].delivered_qty == "0.") {
								$rootScope
										.$broadcast('on_AlertMessage_ERR',
												'Please enter the Deliverd Qty for Items');
								flg = false;
								$scope.item_details_table_empty
										.push($scope.invoice.items[i]);

								$("#items_table tr:nth-child(" + (i + 2) + ")")
										.find("#delivered_qty").select();
							}

							if ((parseFloat($scope.invoice.items[i].delivered_qty) > parseFloat($scope.invoice.items[i].currentStock))
									|| (parseFloat($scope.invoice.items[i].delivered_qty) < 0)) {

								$rootScope.$broadcast('on_AlertMessage_ERR',
										'Please enter less than current stock');
								flg = false;
								$("#items_table tr:nth-child(" + (i + 2) + ")")
										.find("#delivered_qty").select();
								break;
							}
						}
						
						else {
							if ((parseFloat($scope.invoice.items[i].request_qty) > parseFloat($scope.invoice.items[i].currentStock))
									|| (parseFloat($scope.invoice.items[i].request_qty) < 0)) {

								$rootScope.$broadcast('on_AlertMessage_ERR',
										'Please enter less than current stock');
								flg = false;
								$("#items_table tr:nth-child(" + (i + 2) + ")")
										.find("#request_qty").select();
								break;
							}
						}
					}
					// }

					if (settings['combinePurchase'] == 1) {
						if (stockOutStrings['isRequest'] == 1) {

							if ($scope.invoice.items[i].delivered_qty == ""
									|| $scope.invoice.items[i].delivered_qty == parseFloat(
											0)
											.toFixed(settings['decimalPlace'])
									|| $scope.invoice.items[i].delivered_qty == "0"
									|| $scope.invoice.items[i].delivered_qty == "0."
									|| $scope.invoice.items[i].delivered_qty == "0000"
									|| $scope.invoice.items[i].delivered_qty == "0.00") {
								$rootScope
										.$broadcast('on_AlertMessage_ERR',
												'Please enter the Deliverd Qty for Items');
								flg = false;
								$scope.item_details_table_empty
										.push($scope.invoice.items[i]);

								$("#items_table tr:nth-child(" + (i + 2) + ")")
										.find("#delivered_qty").select();
							}
              else if ($scope.invoice.items[i].base_uom_code == ""
								|| $scope.invoice.items[i].base_uom_code==undefined) {
								$rootScope.$broadcast('on_AlertMessage_ERR',' Please Enter Out Unit');
								flg = false;
								$scope.item_details_table_empty.push($scope.invoice.items[i]);

								$("#items_table tr:nth-child(" + (i + 2) + ")").find("#base_uom_code").select();
							}
							if ((parseFloat($scope.invoice.items[i].delivered_qty) > parseFloat($scope.invoice.items[i].currentStock))
									|| (parseFloat($scope.invoice.items[i].delivered_qty) < 0)) {

								$rootScope.$broadcast('on_AlertMessage_ERR',
										'Please enter less than current stock');
								flg = false;
								$("#items_table tr:nth-child(" + (i + 2) + ")")
										.find("#delivered_qty").select();
								break;
							}
						}

						else {
							if ((parseFloat($scope.invoice.items[i].request_qty) > parseFloat($scope.invoice.items[i].currentStock))
									|| (parseFloat($scope.invoice.items[i].request_qty) < 0)) {

								$rootScope.$broadcast('on_AlertMessage_ERR',
										'Please enter less than current stock');
								flg = false;
								$("#items_table tr:nth-child(" + (i + 2) + ")")
										.find("#request_qty").select();
								break;
							}
						}

					}

				} else {
					$scope.item_table_empty.push($scope.invoice.items[i]);
				}
				/* }); */
			}
			if ($scope.item_table_empty.length > 0) {
				$rootScope.$broadcast('on_AlertMessage_ERR',
						ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$(".acontainer").find("input").focus();
				flg = false;
			} else {
				if ($scope.item_details_table_empty.length > 0) {

					if (settings['combinePurchase'] == 0) {
						$("#table_validation_alert").addClass("in");

						if (stockOutStrings['isRequest'] == 1) {
							$rootScope.$broadcast('on_AlertMessage_ERR',
									'Please enter the  Deliverd Qty for Items');
							$(
									"#items_table tr:nth-child("
											+ ($scope.invoice.items.length + 1)
											+ ")").find("#delivered_qty")
									.select();
						} else if (stockOutStrings['isRequest'] == 0) {
							$rootScope.$broadcast('on_AlertMessage_ERR',
									'"Please enter less than current stock');
							$(
									"#items_table tr:nth-child("
											+ ($scope.invoice.items.length + 1)
											+ ")").find("#request_qty")
									.select();
						}
						flg = false;
					}

				} else {
					$("#table_validation_alert").removeClass("in");
				}
			}
		} else if ($scope.invoice.items.length == 0) {
			$rootScope.$broadcast('on_AlertMessage_ERR',
					ITEM_TABLE_MESSAGES.TABLE_ERR);
			flg = false;
		}

		return flg;
	}

	$scope.clearForm = function() {
		$("#form_div_source_code").removeClass("has-error");
		$("#form_div_source_code_error").hide();
		$("#form_div_desti_code").removeClass("has-error");
		$("#form_div_desti_code_error").hide();

		$("#form_div_dest_company").removeClass("has-error");
		$("#form_div_source_company_error").hide();
	}

	/* Create item Table */
	$scope.invoice = {
		items : []
	};
	$scope.invoice.items.push({
		id : "",
		stock_item_id : "",
		stock_item_code : "",
		stockRegDtl_id : "",
		request_qty : 0,
		delivered_qty : 0,
		unit_price : 0,
		base_uom_code:"",
		compound_unit:0.0
	});
			$scope.addItem = function(index, id) {
				if ($scope.disable_all == false) {
					if ($scope.show_stockrequest_form == true && id == 0) {

						/*
						 * $( "#items_table tr:nth-child(" + (index + 2) + ")
						 * td:nth-child(" + (4) + ")").find(
						 * "#delivered_qty").select();
						 */
						$("#items_table tr:nth-child(" + (index + 2) + ")")
								.find("#delivered_qty").focus();
					} else if (index < $scope.invoice.items.length - 1) {
						$(
								"#items_table tr:nth-child(" + (index + 3)
										+ ") td:nth-child(" + (3) + ")").find(
								".acontainer input").focus();
					} else {
						if ($scope.invoice.items.length != 0) {
							if ($scope.invoice.items[$scope.invoice.items.length - 1].stock_item_id != ""
									&& $scope.invoice.items[$scope.invoice.items.length - 1].stock_item_code != "") {
								if (stockOutStrings['isRequest'] == 1) {
									if (settings['combinePurchase'] == 0) {

										if ($scope.invoice.items[$scope.invoice.items.length - 1].delivered_qty == ""
												|| $scope.invoice.items[$scope.invoice.items.length - 1].delivered_qty == "0"
												|| $scope.invoice.items[$scope.invoice.items.length - 1].delivered_qty == "0."
												|| $scope.invoice.items[$scope.invoice.items.length - 1].delivered_qty == parseFloat(
														0)
														.toFixed(
																settings['decimalPlace'])) {
											$rootScope
													.$broadcast(
															'on_AlertMessage_ERR',
															"Please enter the  Deliverd Qty for Items");
											$(
													"#items_table tr:nth-child("
															+ ($scope.invoice.items.length + 1)
															+ ")").find(
													"#delivered_qty").select();
										}

										else {
											$scope.invoice.items.push({
												id : "",
												stock_item_id : "",
												stock_item_code : "",
												stockRegDtl_id : "",
												request_qty : 0,
												delivered_qty : 0,
												unit_price : 0
											});
										}

									}

									if (settings['combinePurchase'] == 1) {

										if ($scope.invoice.items[$scope.invoice.items.length - 1].request_qty == ""
												|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == "0"
												|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == parseFloat(
														0)
														.toFixed(
																settings['decimalPlace'])
												|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == "0.") {
											$rootScope
													.$broadcast(
															'on_AlertMessage_ERR',
															"Please enter the Request Qty for Items");
											/*
											 * $( "#items_table tr:nth-child(" +
											 * ($scope.invoice.items.length + 1) +
											 * ")").find(
											 * "#request_qty").select();
											 */
											$(
													"#items_table tr:nth-child("
															+ ($scope.invoice.items.length + 1)
															+ ") td:nth-child("
															+ (6) + ")").find(
													"#request_qty").select();

										}

								else {
									$scope.invoice.items.push({
										id : "",
										stock_item_id : "",
										stock_item_code : "",
										stockRegDtl_id : "",
										request_qty : 0,
										delivered_qty : 0,
										unit_price : 0,
										compound_unit:0.0,
										base_uom_code:""
											});
										}

									}

							if(settings['combinePurchase'] == 1 )
							{

								if ($scope.invoice.items[$scope.invoice.items.length - 1].request_qty == ""
									|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == "0"
										|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == parseFloat(0).toFixed(settings['decimalPlace'])
										|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == "0.") {
									$rootScope
									.$broadcast(
											'on_AlertMessage_ERR',
									"Please enter the Request Qty for Items");
									/*$(
												"#items_table tr:nth-child("
														+ ($scope.invoice.items.length + 1)
														+ ")").find(
												"#request_qty").select();
									 */								
									$("#items_table tr:nth-child("+($scope.invoice.items.length+1)+") td:nth-child("+(6)+")").find("#request_qty").select();



								}


								else {
									$scope.invoice.items.push({
										id : "",
										stock_item_id : "",
										stock_item_code : "",
										stockRegDtl_id : "",
										request_qty : 0,
										delivered_qty : 0,
										unit_price : 0,
										base_uom_code:"",
										compound_unit:0.0

									});
								}


							}





						} else if (stockOutStrings['isRequest'] == 0) {

									if ($scope.invoice.items[$scope.invoice.items.length - 1].request_qty == ""
											|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == "0"
											|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == parseFloat(
													0).toFixed(
													settings['decimalPlace'])
											|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == "0.") {
										$rootScope
												.$broadcast(
														'on_AlertMessage_ERR',
														"Please enter the Request Qty for Items");
										$(
												"#items_table tr:nth-child("
														+ ($scope.invoice.items.length + 1)
														+ ")").find(
												"#request_qty").select();
									} else {
										$scope.invoice.items.push({
											id : "",
											stock_item_id : "",
											stock_item_code : "",
											stockRegDtl_id : "",
											request_qty : 0,
											delivered_qty : 0,
									unit_price : 0,
									base_uom_code:"",
									compound_unit:0.0
										});
									}
								}
							} else {
								if ($scope.invoice.items[$scope.invoice.items.length - 1].stock_item_id == ""
										&& $scope.invoice.items[$scope.invoice.items.length - 1].stock_item_code == "") {
									$rootScope.$broadcast(
											'on_AlertMessage_ERR',
											ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
									$(
											"#items_table tr:nth-child("
													+ ($scope.invoice.items.length + 1)
													+ ")").find(
											".acontainer input").focus();
								}
							}
						} else if ($scope.invoice.items.length == 0) {
							$scope.invoice.items.push({
								id : "",
								stock_item_id : "",
								stock_item_code : "",
								stockRegDtl_id : "",
								request_qty : 0,
								delivered_qty : 0,
						unit_price : 0,base_uom_code:"",compound_unit:0.0
							});
						}
					}
				}
			},

			$scope.removeRowItem = function(index) {

				if ($scope.invoice.items.length != 1) {
					$scope.invoice.items.splice(index, 1);
					$(
							"#items_table tr:nth-child("
									+ ($scope.invoice.items.length + 1) + ")")
							.find("#request_qty").select();

				} else {
					$rootScope.$broadcast('on_AlertMessage_ERR',
							"Atleast one item is required.");
				}
			}

	$scope.removeItem = function(index) {
		if ($scope.disable_all == false) {
			$scope.showConfirm(FORM_MESSAGES.ROW_DELETE_WRNG, "rowDelete",
					index);
		}
	},

	$scope.total = function() {
		var total = 0;
		angular.forEach($scope.invoice.items, function(item) {
			total += item.delivered_qty * item.unit_price;
		})
		return parseFloat(total).toFixed(settings['decimalPlace']);
	}

	$scope.amount = function(item) {

		return parseFloat(item).toFixed(settings['decimalPlace']);
	}

	var sourceData = $("#source_code")
			.tautocomplete(
					{
						columns : [ 'id', 'code', 'name' ],
						hide : [ false, true, true, false ],
						placeholder : "search..",
						highlight : "hightlight-classname",
						norecord : "No Records Found",
						delay : 10,
						onchange : function() {
							var selected_department_data = sourceData.all();
							$scope
									.$apply(function() {
										$scope.formData.source_department_id = selected_department_data.id;
										$scope.formData.source_code = selected_department_data.code;
										$scope.formData.source_name = selected_department_data.name;
										$scope.getChangeStock();

									});

						},
						data : function() {

							var data = department_data;
							var filterData = [];
							var searchData = eval("/" + sourceData.searchdata()
									+ "/gi");
							$
									.each(
											data,
											function(i, v) {
												if (v.code.search(new RegExp(
														searchData)) != -1
														|| v.name
																.search(new RegExp(
																		searchData)) != -1) {
													if ($scope.formData.dest_department_id == undefined) {
														filterData.push(v);
													} else {
														if ($scope.formData.dest_department_id != v.id) {
															filterData.push(v);
														}
													}
												}
											});

							return filterData;
						},
					});

	var destinationData = $("#dest_code")
			.tautocomplete(
					{
						columns : [ 'id', 'code', 'name' ],
						hide : [ false, true, true ],
						placeholder : "search..",
						highlight : "hightlight-classname",
						norecord : "No Records Found",
						delay : 10,
						onchange : function() {
							var selected_department_data = destinationData
									.all();
							$scope
									.$apply(function() {
										$scope.formData.dest_department_id = selected_department_data.id;
										$scope.formData.dest_code = selected_department_data.code;
										$scope.formData.desti_name = selected_department_data.name;

										$scope
												.getOnchangePendingStock(selected_department_data.id);
									});

						},
						data : function() {

							var data = department_data;
							var filterData = [];
							var searchData = eval("/"
									+ destinationData.searchdata() + "/gi");
							$
									.each(
											data,
											function(i, v) {
												if (v.code.search(new RegExp(
														searchData)) != -1
														|| v.name
																.search(new RegExp(
																		searchData)) != -1) {
													if (stockOutStrings['isRequest'] == 1) {
														if ($scope.formData.source_department_id == undefined) {
															filterData.push(v);
														} else {
															if ($scope.formData.source_department_id != v.id) {
																filterData
																		.push(v);
															}
														}
													} else {
														filterData.push(v);
													}

												}
											});

							return filterData;
						},

					});

	$scope.getItemmasterBatchId = function(itemid) {
		$scope.itemBatchId = "";
		for (var i = 0; i < $scope.itemsBatchData.length; i++) {
			if ($scope.itemsBatchData[i].stock_item_id == itemid) {
				$scope.itemBatchId = $scope.itemsBatchData[i].id;
			}
		}

		return $scope.itemBatchId;
	}
	$scope.goBackToTypePage = function() {
		// $scope.fun_backTo_table();

		$rootScope.$emit('hide_table');
		// $('#itemLists').hide();
		$("#advsearchbox").hide();
		manageButtons("next");
		clearform();
		$("#btnBack").show();
		//alert("stock out 5==========>");
		$("#status_div_id").css("display", "none");
		$scope.show_type_form = true;
		$('#show_form').val(1);
	}

	$scope.getItemmasterBatchName = function(itemid) {
		$scope.itemBatchStock = "";
		for (var i = 0; i < $scope.itemsBatchData.length; i++) {
			if ($scope.itemsBatchData[i].stock_item_id == itemid) {
				$scope.itemBatchStock = $scope.itemsBatchData[i].stock;
			}
		}

		return $scope.itemBatchStock;
	}

	$scope.disable_search_text = function(elemenvalue) {
		if ($scope.disable_all == true) {
			$(elemenvalue).attr("disabled", true);
		}
	}
	$scope.alert_for_codeexisting = function(flg) {
		if (flg == 0) {
		} else if (flg == 1) {
			$rootScope.$broadcast('on_AlertMessage_ERR',
					ITEM_TABLE_MESSAGES.CODE_EXISIT);
		}
	}

	$scope.getCostPrice = function(data, index, id) {
		var url = "getLastCostPrice";
		/*
		 * var costPrice =0.00; $.each(data, function (i, v) { if(v.id == id){
		 * if(v.is_manufactured ==0 && v.is_active == 1){ if(v.valuation_method ==
		 * 1){ url = "getLastCostPrice"; }else{ url = "getAverageCostPrice"; } } }
		 * });
		 */
		$http({
			url : url,
			method : "GET",
			params : {
				itemId : id
			},
			async : false,
		}).then(function(response) {
			$scope.invoice.items[index].unit_price = response.data;
		}, function(response) { // optional

		});
	}

	$scope.clear_stock_details_editmode = function(event) {

		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_id = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_code = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_name = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_batch_id = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].uomcode = "";
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].request_qty = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].delivered_qty = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].unit_price = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].currentStock = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].compound_unit = 0.0;
		$scope.invoice.items[event.currentTarget.parentElement.rowIndex - 1].base_uom_code = "";
	}
	$scope.changeDataStatus = function(statusBtnTxt, className, status) {
		$scope.statusBtnText = statusBtnTxt;
		$scope.ClassName = className;
		$scope.status = status;
	}

	// Advanced search
	$timeout(function() {
		$("#DataTables_Table_0_filter").hide();
	}, 1);

	$rootScope
			.$on(
					"advSearch",
					function(event) {
						$("#dropdownnew").toggle();
						DataObj.adnlFilters = [ {} ];
						$('#SearchText').text("");
						vm.dtInstance.DataTable.search('').draw();
						$scope.req_date = $('#reqDate1').val();
						$scope.del_date = $('#delDate1').val();

						if ($scope.req_date != null
								&& $scope.req_date != undefined
								&& $scope.req_date != "") {
							$scope.req_date = getMysqlFormat($scope.req_date);
						}

						if ($scope.del_date != null
								&& $scope.del_date != undefined
								&& $scope.del_date != "") {
							$scope.del_date = getMysqlFormat($scope.del_date);
						}

						$scope.refrNo = $('#refrNo').val();
						if ($rootScope.filteredDepartmentId == -1) {
							$rootScope.filteredDepartmentId = "";
							$rootScope.filteredDepName = "";
						}

						$scope.searchTxtItms = {
							1 : $scope.req_date,
							2 : $scope.del_date,
							3 : $scope.refrNo,
							4 : $rootScope.filteredDepName
						};
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
											$scope.req_date = "";
											$('#reqDate1').val("");
											break;
										case 2:
											$scope.del_date = "";
											$('#delDate1').val("");
											break;
										case 3:
											$scope.refrNo = "";
											$('#refrNo').val("");
											break;
										case 4:
											$rootScope.filteredDepartmentId = "";
											$rootScope.filteredDepName = "";
											$('#dep_filter_id').val("");
										}
										DataObj.adnlFilters = [
												{
													col : 2,
													filters : $scope.req_date
												},
												{
													col : 4,
													filters : $scope.del_date
												},
												{
													col : 1,
													filters : $scope.refrNo
												},
												{
													col : 3,
													filters : $rootScope.filteredDepartmentId
												} ];
										vm.dtInstance.reloadData();

									};

								}
							}
						}

						DataObj.adnlFilters = [ {
							col : 2,
							filters : $scope.req_date
						}, {
							col : 4,
							filters : $scope.del_date
						}, {
							col : 1,
							filters : $scope.refrNo
						}, {
							col : 3,
							filters : $rootScope.filteredDepartmentId
						} ];
						vm.dtInstance.reloadData();
						$scope.searchTxtItms = {};
					});

	$rootScope.$on("Search", function(event) {
		DataObj.adnlFilters = [ {} ];
		$scope.searchTxtItms = {};
		/* vm.dtInstance.reloadData(); */
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();

	});

	$("#clear").click(function() {
		DataObj.adnlFilters = [ {} ];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();
		$scope.searchTxtItms = {};
	});

	$("#clearFeilds").click(function() {
		$("#dropdownnew").toggle();

		$('#delDate1').val("");
		$('#reqDate1').val("");
		$('#refrNo').val("");
		$('#dep_filter_id').val("");

	});

	$("#closebtnew").click(function() {
		$("#dropdownnew").toggle();

	});



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
		$scope.invoice.items[index].delivered_qty=$scope.invoice.items[index].compound_unit*recQty;	

	}


}

mrpApp
		.directive(
				'autocompeteText',
				[
						'$timeout',
						function($timeout) {
							return {
								controller : function($scope, $http) {
									$scope.currentIndex = 0;
									$scope.selctedindex = 0;
									$("#items_table tbody tr td")
											.keyup(
													'input',
													function(e) {
														$scope.currentIndex = e.currentTarget.parentElement.rowIndex - 1;

														if (e.which != 9
																&& e.which != 13
																&& e.which != 109
																&& e.which == 8) {
															if (e.currentTarget.cellIndex == 2) {
																$scope
																		.$apply(function() {
																			$scope
																					.clear_stock_details_editmode(e);
																			$scope
																					.alert_for_codeexisting(0);
																		});
															}
														} else if (e.which == 13) {

															if (e.currentTarget.cellIndex == 2) {
																if ($scope.invoice.items[$scope.invoice.items.length - 1].stock_item_id != "") {
																	{
																		$scope.selctedindex = 0;
																		$(
																				"#items_table tr:nth-child("
																						+ (e.currentTarget.parentElement.rowIndex + 1)
																						+ ")")
																				.find(
																						"#request_qty")
																				.select();

																	}
																}
															}
														} else if (e.which == 9) {
															if (e.currentTarget.cellIndex == 2) {

																{
																	$(
																			"#items_table tr:nth-child("
																					+ (e.currentTarget.parentElement.rowIndex + 1)
																					+ ") td:nth-child("
																					+ (3)
																					+ ")")
																			.find(
																					".acontainer input")
																			.focus();

																}
															}

															else if (e.currentTarget.cellIndex == 5
																	&& $scope.selctedindex == 0) {
																$scope.selctedindex = 1;
																$(
																		"#items_table tr:nth-child("
																				+ (e.currentTarget.parentElement.rowIndex + 1)
																				+ ")")
																		.find(
																				"#request_qty")
																		.select();

															} else if (e.currentTarget.cellIndex == 5
																	&& $scope.selctedindex == 1) {

																{
																	$scope.selctedindex = 0;
																	$(
																			"#items_table tr:nth-child("
																					+ (e.currentTarget.parentElement.rowIndex + 1)
																					+ ")")
																			.find(
																					"#delivered_qty")
																			.select();

																}
															}
														}

													});

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

														columns : [
																'id',
																'code',
																'name',
																'input_tax_id',
																'tax_percentage',
																'is_active',
																'is_manufactured',
																'unit_price',
																'valuation_method',
																'uomname',
																'uomcode'

														],
														hide : [ false, true,
																true, false,
																false, false,
																false, false,
																false, false,
																false ],
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
																		for (var i = 0; i < strl_scope.invoice.items.length; i++) {
																			if (selected_item_data.id != "") {
																				if (i != strl_scope.currentIndex) {
																					if (selected_item_data.id == strl_scope.invoice.items[i].stock_item_id) {
																						count = 1;
																					}
																				}
																			}
																		}
																		if (count != 1) {
																			strl_scope.invoice.items[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;

																			strl_scope.invoice.items[strl_scope.currentIndex].stock_item_id = selected_item_data.id;
																			strl_scope.invoice.items[strl_scope.currentIndex].stock_item_code = selected_item_data.code;
																			strl_scope.invoice.items[strl_scope.currentIndex].stock_item_name = selected_item_data.name;
																			strl_scope.invoice.items[strl_scope.currentIndex].stock_item_batch_id = strl_scope
																					.getItemmasterBatchId(selected_item_data.id);
																			strl_scope.invoice.items[strl_scope.currentIndex].stock_item_batch_stock = strl_scope
																					.getItemmasterBatchName(selected_item_data.id);
														strl_scope.invoice.items[strl_scope.currentIndex].base_uom_code = strl_scope.setuomCode(selected_item_data.uomcode,strl_scope.currentIndex);
																			strl_scope
																					.alert_for_codeexisting(0);
																			/*
																			 * strl_scope.invoice.items[strl_scope.currentIndex].currentStock =
																			 * strl_scope
																			 * .getItemmasterBatchCurrentStock(selected_item_data.id);
																			 */
																			strl_scope.invoice.items[strl_scope.currentIndex].currentStock = strl_scope
																					.getItemmasterCurrentStock(
																							selected_item_data.id,
																							strl_scope.formData.source_department_id,
																							strl_scope.formData.stock_transfer_date,
																							strl_scope.currentIndex,
																							strl_scope.formData.dest_department_id);

																			strl_scope.invoice.items[strl_scope.currentIndex].unit_price = selected_item_data.unit_price;
																			/*							strl_scope.getCostPrice(strl_scope.itemsData,strl_scope.currentIndex,selected_item_data.id);
																			 */

																			$timeout(
																					function() {
																						$(
																								"#items_table tr:nth-child("
																										+ (strl_scope.currentIndex + 2)
																										+ ")")
																								.find(
																			"#base_uom_code")
																								.focus();
																					},
																					1);
																		} else {
																			elem[0].parentNode.lastChild.value = "";
																			strl_scope
																					.alert_for_codeexisting(1);
																			strl_scope.invoice.items[strl_scope.currentIndex].uomcode = "";

																		}

																	});
														},
														data : function() {

															var data = strl_scope.itemsData;
															var filterData = [];
															var searchData = eval("/"
																	+ items
																			.searchdata()
																	+ "/gi");
															$
																	.each(
																			data,
																			function(
																					i,
																					v) {
															if (v.name.search(new RegExp(searchData)) != -1) {

																					filterData
																							.push(v);

																				}
																			});

															return filterData;
														}
													});

									for (var i = 0; i < strl_scope.invoice.items.length; i++) {
										if (strl_scope.formData.id != undefined
												&& strl_scope.formData.id != '') {
											if (strl_scope.invoice.items[i].isSet == false) {
												elem[0].parentNode.lastChild.value = strl_scope.invoice.items[i].stock_item_code;
												strl_scope
														.disable_search_text(elem[0].parentNode.lastChild);
												strl_scope.invoice.items[i].isSet = true;
												break;
											}
										}
									}
									$timeout(
											function() {
												if (strl_scope.formData.dest_department_id != undefined) {
													$(
															"#items_table tr:nth-child("
																	+ (strl_scope.invoice.items.length + 1)
																	+ ") td:nth-child("
																	+ (3) + ")")
															.find(
																	".acontainer input")
															.select();

												}
											}, 1);
								}
							};
						} ]);
