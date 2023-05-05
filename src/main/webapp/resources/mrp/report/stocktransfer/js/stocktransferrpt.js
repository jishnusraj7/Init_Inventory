var myApp = angular.module('stock_transfer_report_app', [ 'ngMaterial',
		'ngMessages', 'common_app', 'checklist-model' ]);

myApp.controller('transfer_report_ctrlr', transfer_report_ctrlr);

function transfer_report_ctrlr($scope, $http, $mdDialog, $rootScope,
		DTOptionsBuilder, DTColumnBuilder, $timeout, $q, $window, FORM_MESSAGES) {
	$scope.formData = {};

	$scope.startmonth = "";
	$scope.endmonth = "";
	$scope.selectMonth = "";
	// $("#optnDateTrans").prop('checked', true);
	// $("#date_div_trans").show();
	// $("#month_div_trans").hide();
	$("#stkoptradioExternalRep").prop("checked", true);

	/*
	 * if ($("#stkoptradioExternalRep").attr('checked', true)) {
	 * 
	 * $("#form_div_dest_company").show();
	 * $("#form_div_dest_department").hide(); }
	 * 
	 * 
	 * $("#stkoptradioExternalRep").click(function() {
	 * 
	 * if ($("#stkoptradioExternalRep").attr('checked', true)) {
	 * 
	 * $("#form_div_dest_company").show();
	 * $("#form_div_dest_department").hide(); }
	 * 
	 * });
	 * 
	 * $("#stkoptradioInternalRep").click(function() {
	 * 
	 * if ($("#stkoptradioInternalRep").attr('checked', true)) {
	 * 
	 * $("#form_div_dest_company").hide();
	 * $("#form_div_dest_department").show(); }
	 * 
	 * });
	 */

	$("#select_report").change(function() {
		if ($("#select_report").val() == "number:7") {

			$("#divErrormsg6").hide();
			// $("#date_div_disp").show();
			// $("#month_div_disp").hide();
			// $("#optnDateTrans").prop('checked', true);
			var dateFormat = get_date_format();
			var date = new Date();
			var y = date.getFullYear();
			var m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);

			$timeout(function() {

				$scope.formData.enddate = dateForm(lastDay);
				$scope.formData.startdate = dateForm(firstDay);
			}, 1);

			$("#form_div_startdate_trans").removeClass("has-error");
			$("#form_div_startmonth_trans").removeClass("has-error");
			$("#form_div_startdate_error_trans").hide();
			$("#form_div_enddate_trans").removeClass("has-error");
			$("#form_div_enddate_error_trans").hide();
			$("#divErrormsg6").hide();

		}
	});
	// checkboxlist

	$scope.itemsData = [];

	// commnted by gana on 20-07-2019
	/*
	 * $http({ url : '../itemmaster/json', method : "GET",
	 * }).then(function(itemresponse) {
	 * 
	 * $scope.itemsData = itemresponse.data.data; console.log($scope.itemsData); },
	 * function(itemresponse) { // optional
	 * 
	 * });
	 */
	$http({
		url : '../stockin/formJsonData',
		method : "GET",
	}).then(function(itemresponse) {

		$scope.itemsData = itemresponse.data.stockItmData;
		console.log($scope.itemsData);
	}, function(itemresponse) { // optional

	});

	/*
	 * $("#optnDateTrans").click(function() {
	 * 
	 * if ($("#optnDateTrans").prop('checked') == true) {
	 * 
	 * 
	 * var dateFormat = get_date_format(); var date = new Date(); var y =
	 * date.getFullYear(); var m = date.getMonth(); var curDate = new Date();
	 * var firstDay = new Date(y, m, 1); var lastDay = new Date(y, m + 1, 0);
	 * 
	 * 
	 * 
	 * $timeout(function() { $scope.formData.enddate = dateForm(lastDay);
	 * $scope.formData.startdate = dateForm(firstDay); }, 1);
	 * 
	 * 
	 * $("#date_div_trans").show(); $("#month_div_trans").hide(); }
	 * 
	 * 
	 * 
	 * $("#form_div_startmonth_trans").removeClass("has-error"); });
	 */
	/*
	 * $("#optnMonthTrans").click(function() {
	 * 
	 * 
	 * 
	 * if ($("#optnMonthTrans").prop('checked') == true) {
	 * 
	 * month = moment().format("MMM"); date = moment().format('YYYY-MMM');
	 * $('#startmonth_trans').val(date);
	 * 
	 * $("#month_div_trans").show(); $("#date_div_trans").hide(); }
	 * 
	 * 
	 * $("#form_div_startdate_trans").removeClass("has-error");
	 * $("#form_div_startdate_error_trans").hide();
	 * $("#form_div_enddate_trans").removeClass("has-error");
	 * $("#form_div_enddate_error_trans").hide(); $("#divErrormsg6").hide();
	 * 
	 * 
	 * });
	 */

	$("#btn_finalize")
			.click(
					function() {
						if ($('#select_report').val() == "number:7") {
							if (validation()) {
								$("#form_div_startdate").removeClass(
										"has-error");
								$("#form_div_startdate_error").hide();
								$("#form_div_enddate").removeClass("has-error");
								$("#form_div_enddate_error").hide();
								$("#divErrormsg6").hide();

								var transfer_type = 0;
								if (!$("#stkoptradioExternalRep").prop(
										'checked')) {
									transfer_type = 1;

								}

								$scope.stock_item_Ids = [];
								if ($scope.selectedItemList.length != 0) {
									for (var i = 0; i < $scope.selectedItemList.length; i++) {
										$scope.stock_item_Ids
												.push($scope.selectedItemList[i].id);
									}
								}
								var stkItem = $scope.stock_item_Ids;

								console.log(stkItem);
								$window
										.open(
												"../stocktransferreport/stocktransferReport?&startdate="
														+ getMysqlFormat($scope.formData.startdate)
														+ "&enddate="
														+ getMysqlFormat($scope.formData.enddate)
														+ "&transferType="
														+ transfer_type
														+ "&stockitem="
														+ stkItem
														+ "&pdfExcel=pdf",
												'_blank');
							}
						}

					});

	$("#excelView")
			.click(
					function() {
						if ($('#select_report').val() == "number:7") {
							if (validation()) {
								$("#form_div_startdate").removeClass(
										"has-error");
								$("#form_div_startdate_error").hide();
								$("#form_div_enddate").removeClass("has-error");
								$("#form_div_enddate_error").hide();
								$("#divErrormsg6").hide();

								var transfer_type = 0;
								if (!$("#stkoptradioExternalRep").prop(
										'checked')) {
									transfer_type = 1;

								}

								$scope.stock_item_Ids = [];
								if ($scope.selectedItemList.length != 0) {
									for (var i = 0; i < $scope.selectedItemList.length; i++) {
										$scope.stock_item_Ids
												.push($scope.selectedItemList[i].id);
									}
								}
								var stkItem = $scope.stock_item_Ids;

								console.log(stkItem);
								$window
										.open(
												"../stocktransferreport/stocktransferReport?&startdate="
														+ getMysqlFormat($scope.formData.startdate)
														+ "&enddate="
														+ getMysqlFormat($scope.formData.enddate)
														+ "&transferType="
														+ transfer_type
														+ "&stockitem="
														+ stkItem
														+ "&pdfExcel=excel",
												'_blank');
							}
						}

					});

	$scope.filterItemData = [];

	$scope.dataFetch = function() {

		$scope.searchValue();

	}

	$scope.searchValue = function() {
		$scope.filterItemData = [];
		if ($scope.serch != "") {
			var searchData = eval("/.*" + $scope.serch + "/gi");

			$.each($scope.itemsData, function(i, v) {
				if (v.name.search(new RegExp(searchData)) != -1) {

					$scope.filterItemData.push(v);

				}
			});

		}
	}

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

	function validation() {
		var flg = true;

		if ($scope.formData.startdate == undefined
				|| $scope.formData.startdate == ""
				|| $scope.formData.startdate == null) {
			$("#form_div_startdate_trans").addClass("has-error");
			$("#form_div_startdate_error_trans").show();
			flg = false;
		} else {
			$("#form_div_startdate_trans").removeClass("has-error");
			$("#form_div_startdate_error_trans").hide();
		}

		if ($scope.formData.enddate == undefined
				|| $scope.formData.enddate == ""
				|| $scope.formData.enddate == null) {
			$("#form_div_enddate_trans").addClass("has-error");
			$("#form_div_enddate_error_trans").show();
			flg = false;
		} else {
			$("#form_div_enddate_trans").removeClass("has-error");
			$("#form_div_enddate_error_trans").hide();
		}

		if ($scope.formData.enddate != "") {
			var strtDate2 = process($scope.formData.startdate);
			var endDate2 = process($scope.formData.enddate);
			if (strtDate2 > endDate2) {

				$("#divErrormsg6")
						.text('Start date must be less than Enddate!');
				$("#divErrormsg6").show();
				flg = false;
			} else if (strtDate2 - endDate2 == 0) {
				$("#divErrormsg6").text(
						'Start date and End date cannot be same!');
				$("#divErrormsg6").show();
				flg = false;
			} else {

				$("#divErrormsg6").hide();
			}
		} else {
			$("#divErrormsg6").hide();
		}

		return flg;
	}

}

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

			controller.formData.enddate = dateForm(lastDay);

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a
			// day
			var diff = todayTimeStamp - oneDayTimeStamp;
			var yesterdayDate = new Date(diff);

			controller.formData.startdate = dateForm(firstDay);

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
} ]);

angular.bootstrap(document.getElementById("app_transfer_report"),
		[ 'stock_transfer_report_app' ]);