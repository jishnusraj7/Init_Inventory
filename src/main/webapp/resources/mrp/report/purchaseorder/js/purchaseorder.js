var myApp = angular.module('purchaseorder_report_app', [ 'ngMaterial',
		'ngMessages', 'common_app', 'checklist-model' ]);

myApp.controller('purchaseorderCtrlr', purchaseorderCtrlr);

function purchaseorderCtrlr($scope, $http, $mdDialog, $rootScope,
		DTOptionsBuilder, DTColumnBuilder, $timeout, $q, $window) {
	$scope.formData = {};
	$scope.stockData = [];
	$scope.purchaseorderdtllist = [];
	$scope.supplier_data = [];
	$scope.itemsData = [];
	$scope.itemsData1 = [];

	$http({
		url : '../purchaseorderhdr/formJsonData',
		method : "GET",
	}).then(function(response) {

		$scope.purchaseorderdtllist = response.data.purchaseorderdtllist;
		$scope.supplier_data = response.data.supplier_data;
		$scope.itemsData1 = response.data.itemsData;
		angular.copy($scope.itemsData1, $scope.itemsData);

	}, function(response) { // optional
	});

	$("#select_report").change(function() {

		$("#form_div_startdate_po").removeClass("has-error");
		$("#form_div_startdate_error_po").hide();
		$("#form_div_enddate_po").removeClass("has-error");
		$("#form_div_enddate_error_po").hide();

		$scope.$apply(function() {
			$scope.selectedItemList = [];
			$scope.filterItemData = [];
			angular.copy($scope.itemsData1, $scope.itemsData);
			$scope.serch = "";
			$scope.user.roles = [];
			$scope.userSelect.roles = [];
			$scope.is_active = false;
			$scope.is_active_select = false;
		});
	});

	// checkboxlist

	$scope.is_active = false;
	$scope.is_active_select = false;
	$scope.serch = "";
	$scope.user = {
		roles : []
	};

	$scope.userSelect = {
		roles : []
	};

	$scope.checkAll = function() {
		$scope.user.roles = angular.copy($scope.filterItemData);
	};
	$scope.uncheckAll = function() {
		$scope.user.roles = [];
	};

	$scope.checkAllSelect = function() {
		$scope.userSelect.roles = angular.copy($scope.selectedItemList);
	};
	$scope.uncheckAllSelect = function() {
		$scope.userSelect.roles = [];
	};

	$scope.moveItem = function() {

		if ($scope.is_active == true) {

			$scope.checkAll();
		} else {
			$scope.uncheckAll();
		}
	}

	$scope.moveItemSelect = function() {

		if ($scope.is_active_select == true) {

			$scope.checkAllSelect();
		} else {
			$scope.uncheckAllSelect();
		}
	}
	$scope.selectedShop = [];

	$scope.selectedItemList = [];

	$scope.btnRight = function() {
		// move selected.
		if ($scope.user.roles.length != 0) {
			if ($scope.is_active == true) {
				$scope.is_active = false;
			}
			$scope.is_active_select = false;
			for (var i = 0; i < $scope.user.roles.length; i++) {
				$scope.selectedItemList.push($scope.user.roles[i]);
			}

			// remove the ones that were moved from the source container.

			for (var i = 0; i < $scope.user.roles.length; i++) {
				for (var j = 0; j < $scope.itemsData.length; j++) {
					if ($scope.user.roles[i].id == $scope.itemsData[j].id) {
						$scope.itemsData.splice(j, 1);
					}
				}
			}

		}
		$scope.searchValue();
		$scope.user.roles = [];

	}

	$scope.btnLeft = function() {
		// move selected.
		if ($scope.userSelect.roles.length != 0) {

			if ($scope.is_active_select == true) {
				$scope.is_active_select = false;
			}

			$scope.is_active = false;
			for (var i = 0; i < $scope.userSelect.roles.length; i++) {
				$scope.itemsData.push($scope.userSelect.roles[i]);
			}
			// remove the ones that were moved from the source container.

			for (var i = 0; i < $scope.userSelect.roles.length; i++) {
				for (var j = 0; j < $scope.selectedItemList.length; j++) {
					if ($scope.userSelect.roles[i].id == $scope.selectedItemList[j].id) {
						$scope.selectedItemList.splice(j, 1);
					}
				}
			}

		}
		$scope.searchValue();
		$scope.userSelect.roles = [];

	}

	$scope.unselectstockItem = function() {
		if ($scope.filterItemData.length != $scope.user.roles.length) {
			$scope.is_active = false;
		} else {
			$scope.is_active = true;
		}
	}

	$scope.unselectstockItemSelect = function() {
		if ($scope.selectedItemList.length != $scope.userSelect.roles.length) {
			$scope.is_active_select = false;
		} else {
			$scope.is_active_select = true;
		}
	}

	// checkbox list end

	$scope.stockId = [];
	$scope.hideit = true;
	/*
	 * set_sub_menu("#report"); setMenuSelected("#currentstock_left_menu");
	 */

	var vm = this;

	$("#msg").hide();

	$scope.filterItemData = [];

	$scope.dataFetch = function() {

		$scope.searchValue();

	}

	$scope.searchValue = function() {
		$scope.filterItemData = [];
		if ($scope.serch != "") {
			var searchData = eval("/^" + $scope.serch + "/gi");

			$.each($scope.itemsData, function(i, v) {
				if (v.name.search(new RegExp(searchData)) != -1) {

					$scope.filterItemData.push(v);

				}
			});

		}
	}

	$scope.hideit = true;

	$("#divErrormsg").hide();

	$scope.elementcode = function(codevalue) {
		$scope.stkcode;

		for (var j = 0; j < $scope.stockListDtlList.length; j++) {
			if ($scope.stockListDtlList[j].id == codevalue) {
				$scope.stkcode = $scope.stockListDtlList[j].code;
			}
		}
		return $scope.stkcode;
	}

	$(document).on('keyup', '#form_div_item_id .acontainer input',
			function(event) {
				if (event.which != 9 && event.which != 13) {
					$scope.$apply(function() {
						// $('#form_div_item_id #itemid,#itemname').val('');
						$scope.formData.itemid = '';
						$scope.formData.itemname = '';
					});

				}
			});

	$(document).on('keyup', '#form_div_supplier_code input', function(e) {
		if (e.which == 9) {
			$("#supplier_doc_no").focus();
		}
		if (e.which != 9 && e.which != 13) {
			$scope.$apply(function() {
				$('#supplier_code').val("");
				$scope.formData.supplier_id = "";
				$scope.formData.supplier_code = "";
				$scope.formData.supplier_name = "";
			});
		}
	});

	// autocompelete suplier data
	var supplieryData = $("#supplier_code")
			.tautocomplete(
					{
						columns : [ 'id', 'code', 'name' ],
						hide : [ false, true, true ],
						placeholder : "search..",
						highlight : "hightlight-classname",
						norecord : "No Records Found",
						delay : 10,
						onchange : function() {
							var selected_supplier_data = supplieryData.all();
							$scope
									.$apply(function() {
										$scope.formData.supplier_id = selected_supplier_data.id;
										$scope.formData.supplier_code = selected_supplier_data.code;
										$scope.formData.supplier_name = selected_supplier_data.name;
									});
						},
						data : function() {
							var data = $scope.supplier_data;
							var filterData = [];
							var searchData = eval("/^"
									+ supplieryData.searchdata() + "/gi");
							$
									.each(
											data,
											function(i, v) {
												if (v.code.search(new RegExp(
														searchData)) != -1
														|| v.name
																.search(new RegExp(
																		searchData)) != -1) {
													filterData.push(v);
												}
											});
							return filterData;
						}
					});

	$("#btn_finalize")
			.click(
					function() {

						$scope.stock_iem_Ids = [];
						if ($scope.selectedItemList.length != 0) {
							for (var i = 0; i < $scope.selectedItemList.length; i++) {
								$scope.stock_iem_Ids
										.push($scope.selectedItemList[i].id);
							}
						}

						if ($("#select_report").val() == "number:2") {
							if (validation()) {
								if ($scope.formData.supplier_id == undefined
										|| $scope.formData.supplier_id == "") {
									$scope.formData.supplier_id = 0;
								}
								if ($scope.formData.poStatus == undefined
										|| $scope.formData.poStatus == "") {
									$scope.formData.poStatus = -1;
								}
								if ($scope.formData.itemid == undefined
										|| $scope.formData.itemid == "") {
									$scope.formData.itemid = 0;
								}

								var startdate = getMysqlFormat($scope.formData.postartdate);
								var enddate = getMysqlFormat($scope.formData.poenddate);
								$window
										.open(
												"../purchaseorderreport/Purchase Order Report?stock_item_id="
														+ $scope.stock_iem_Ids
														+ "&po_status="
														+ parseInt($scope.formData.poStatus)
														+ "&supplier_id="
														+ parseInt($scope.formData.supplier_id)
														+ "&startdate="
														+ startdate
														+ "&enddate=" + enddate
														+ "&pdfExcel=pdf",
												'_blank');

							}
						} else {
							$("#form_div_startdate_po")
									.removeClass("has-error");
							$("#form_div_startdate_error_po").hide();
							$("#form_div_enddate_po").removeClass("has-error");
							$("#form_div_enddate_error_po").hide();
						}
					});

	$("#excelView")
			.click(
					function() {

						$scope.stock_iem_Ids = [];
						if ($scope.selectedItemList.length != 0) {
							for (var i = 0; i < $scope.selectedItemList.length; i++) {
								$scope.stock_iem_Ids
										.push($scope.selectedItemList[i].id);
							}
						}

						if ($("#select_report").val() == "number:2") {
							if (validation()) {
								if ($scope.formData.supplier_id == undefined
										|| $scope.formData.supplier_id == "") {
									$scope.formData.supplier_id = 0;
								}
								if ($scope.formData.poStatus == undefined
										|| $scope.formData.poStatus == "") {
									$scope.formData.poStatus = -1;
								}
								if ($scope.formData.itemid == undefined
										|| $scope.formData.itemid == "") {
									$scope.formData.itemid = 0;
								}

								var startdate = getMysqlFormat($scope.formData.postartdate);
								var enddate = getMysqlFormat($scope.formData.poenddate);
								$window
										.open(
												"../purchaseorderreport/Purchase Order Report?stock_item_id="
														+ $scope.stock_iem_Ids
														+ "&po_status="
														+ parseInt($scope.formData.poStatus)
														+ "&supplier_id="
														+ parseInt($scope.formData.supplier_id)
														+ "&startdate="
														+ startdate
														+ "&enddate=" + enddate
														+ "&pdfExcel=excel",
												'_blank');

							}
						} else {
							$("#form_div_startdate_po")
									.removeClass("has-error");
							$("#form_div_startdate_error_po").hide();
							$("#form_div_enddate_po").removeClass("has-error");
							$("#form_div_enddate_error_po").hide();
						}
					});

	function validation() {
		var flg = true;

		if ($scope.formData.postartdate == undefined
				|| $scope.formData.postartdate == "") {
			$("#form_div_startdate_po").addClass("has-error");
			$("#form_div_startdate_error_po").show();
			flg = false;
		} else {
			$("#form_div_startdate_po").removeClass("has-error");
			$("#form_div_startdate_error_po").hide();
		}

		if ($scope.formData.poenddate == undefined
				|| $scope.formData.poenddate == "") {
			$("#form_div_enddate_po").addClass("has-error");
			$("#form_div_enddate_error_po").show();
			flg = false;
		} else {
			$("#form_div_enddate_po").removeClass("has-error");
			$("#form_div_enddate_error_po").hide();
		}

		if ($scope.formData.poenddate != ""
				&& $scope.formData.poenddate != undefined) {
			var strtDate2 = process($scope.formData.postartdate);
			var endDate2 = process($scope.formData.poenddate);
			if (strtDate2 > endDate2) {
				$("#divErrormsg2_po").text(
						'Startdate must be less than Enddate!');
				$("#divErrormsg2_po").show();
				flg = false;
			} else {
				$("#divErrormsg2_po").hide();
			}
		}

		return flg;
	}

	$scope.podateChange = function() {
		if ($scope.formData.poenddate != ""
				&& $scope.formData.poenddate != undefined) {
			var strtDate2 = process($scope.formData.postartdate);
			var endDate2 = process($scope.formData.poenddate);
			if (strtDate2 > endDate2) {

				$("#divErrormsg2_po").text(
						'Startdate must be less than Enddate!');
				$("#divErrormsg2_po").show();
			} else {
				$("#divErrormsg2_po").hide();
			}
		}

	}

	$(document).on(
			'keyup',
			'#form_div_item_id2 .acontainer input',
			function(e) {
				if (e.which != 9 && e.which != 13 && e.which != 109
						&& e.which == 8) {

					$('#form_div_item_id2 #itemid2,#itemname2').val('');
					$scope.formData.itemid = "";
					$scope.formData.itemname = "";
				}
			});

}

myApp.directive('tableAutocomplete', [ function() {
	return {

		controller : function($scope, $http) {
			return $scope;
		},
		link : function($scope, elem, attrs, controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns : [ 'id', 'code', 'name' ],
				hide : [ false, true, true ],
				placeholder : "search...",
				highlight : "hightlight-classname",
				norecord : "No Records Found",
				delay : 10,
				onchange : function() {
					var selected_item_data = items.all();

					$scope.$apply(function() {

						$scope.formData.itemid = selected_item_data.id;
						$scope.formData.itemname = selected_item_data.name;

					});
				},
				data : function() {

					var strl_scope = controller;
					var data;

					data = strl_scope.itemsData;

					var filterData = [];

					var searchData = eval("/^" + items.searchdata() + "/gi");

					$.each(data, function(i, v) {
						if (v.name.search(new RegExp(searchData)) != -1) {
							if (v.is_active == 1) {
								filterData.push(v);
							}
						}
					});

					return filterData;
				}

			});

		}
	};
} ]);

myApp.directive('daterangePicker1', [ function() {
	return {
		controller : function($scope, $http) {

			return $scope;
		},
		link : function(scope, elem, attrs, controller) {
			var dateFormat = get_date_format();
			var date = new Date(), y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);

			controller.formData.poenddate = dateForm(lastDay);

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a
			// day
			var diff = todayTimeStamp - oneDayTimeStamp;
			var yesterdayDate = new Date(diff);

			controller.formData.postartdate = dateForm(firstDay);

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
				"autoApply" : true,
				"linkedCalendars" : false,

			}, function(start, end, label) {
			});
		}
	};
} ]);

angular.bootstrap(document.getElementById("app_purchaseorder"),
		[ 'purchaseorder_report_app' ]);