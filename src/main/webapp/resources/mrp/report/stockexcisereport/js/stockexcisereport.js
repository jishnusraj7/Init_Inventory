/* Done by anandu on 21-01-2020 */
var myApp = angular.module('stock-excise_report_app', [ 'ngMaterial',
		'ngMessages', 'common_app', 'checklist-model' ]);

myApp.controller('stockexciseCtrlr', stockexciseCtrlr);

function stockexciseCtrlr($scope, $http, $mdDialog, $timeout, $q, $window,
		FORM_MESSAGES) {
	$scope.formData = {};
	$scope.startmonth = "";
	$scope.endmonth = "";
	$scope.selectMonth = "";
	$scope.category = 1;
	$scope.superClass = 'LIQUOR';
	var vm = this;
	var category = "";
	var superClass = "";

	$('#category').on('change', function(e) {
		e.stopPropagation();
		this.value = this.checked ? 1 : 0;
		if (this.value == 1) {
			$scope.category = 1;
		} else {
			$scope.category = 0;
		}
	});

	$("#optnDate").prop('checked', true);
	$("#date_div").show();
	//	$("#month_div").hide();

	$("#select_report").change(function() {
		if ($("#select_report").val() == "number:9") {
			$("div#divErrormsg2").hide();
			$("#btnTally").hide();
			$("#form_div_startdate_stockexcise").removeClass("has-error");
			$("#form_div_startdate_error_stockexcise").hide();
			$("#form_div_enddate_stockexcise").removeClass("has-error");
			$("#form_div_enddate_error_stockexcise").hide();
			$("div#divErrormsg2").hide();
		}
	});

	$("#optnDate").click(function() {
		if ($("#optnDate").prop('checked') == true) {
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
			$scope.formData.itemname = "";

			$("#date_div").show();
			//	$("#month_div").hide();
		}

		$("#form_div_startmonth").removeClass("has-error");
	});

	/*	$("#optnMonth").click(function() {
	 if ($("#optnMonth").prop('checked') == true) {
	 month = moment().format("MMM");
	 date = moment().format('YYYY-MMM');
	 $('#startmonth1').val(date);
	 $("#month_div").show();
	 $("#date_div").hide();
	 }

	 $("#form_div_startdate_stockexcise").removeClass("has-error");
	 $("#form_div_startdate_error_stockexcise").hide();
	 $("#form_div_enddate_stockexcise").removeClass("has-error");
	 $("#form_div_enddate_error_stockexcise").hide();
	 $("#divErrormsges").hide();
	 });*/


	$("#btn_finalize")
			.click(
					function() {
						if ($("#select_report").val() == "number:9") {
							var monthNo = "";
							var startdate = "";
							var enddate = "";
							var option = "";
							var firstday = "";
							var lastday = "";
							var startmonth = "";
							if ($("#optnDate").prop('checked') == true) {
								startdate = "";
								enddate = "";
								startdate = getMysqlFormat($scope.formData.startdate);
								enddate = getMysqlFormat($scope.formData.enddate);
								category = $scope.category;
								superClass = $scope.superClass;
								option = "1";
							}
							/*if ($("#optnMonth").prop('checked') == true) {
								startdate = "";
								enddate = "";
								startdate = $("#startmonth").val();
								startmonth = $("#startmonth1").val();

								var fields = startmonth.split('-');
								var year1 = fields[0];
								var monthName1 = fields[1];

								var date = new Date(), yr = date.getFullYear(), mnth = date
										.getMonth();

								startdate1 = new Date(Date.parse(startdate
										+ " 1," + yr));

								startdate = convertDate(startdate1);

								var monthNo = getMonthFromString(monthName1);

								startdate1 = new Date(Date.parse(monthNo
										+ " 1," + year1));

								startdate = convertDate(startdate1);
								option = "0";
							}*/

							/*if ($("#optnMonth").prop('checked') == true) {

								if ($("#startmonth1").val() != ""
										&& $("#startmonth1").val() != undefined) {
									$("#form_div_startdate_stockexcise")
											.removeClass("has-error");
									$("#form_div_startdate_error_stockexcise")
											.hide();
									$("#form_div_enddate_stockexcise")
											.removeClass("has-error");
									$("#form_div_enddate_error_stockexcise")
											.hide();
									$("#form_div_startmonth").removeClass(
											"has-error");
									$("#form_div_startmonth_error").hide();
									$window.open(
											"../stockexciseerreport/Stock Excise?&option="
													+ option + "&startdate="
													+ startdate + "&enddate="
													+ monthNo + "", '_blank');
								} else {
									$("#form_div_startmonth").addClass(
											"has-error");
									$("#form_div_startmonth_error").show();
								}

							}*/

							if ($("#optnDate").prop('checked') == true) {
								var strtDate2 = process($scope.formData.startdate);
								var endDate2 = process($scope.formData.enddate);
								var category = $scope.category;
								var superClass = $scope.superClass;
								if ($scope.formData.startdate != undefined
										&& $scope.formData.startdate != ""
										&& $scope.formData.enddate != ""
										&& $scope.formData.startdate != undefined
										&& strtDate2 < endDate2) {
									$("#form_div_startdate_stockexcise")
											.removeClass("has-error");
									$("#form_div_startdate_error_stockexcise")
											.hide();
									$("#form_div_enddate_stockexcise")
											.removeClass("has-error");
									$("#form_div_enddate_error_stockexcise")
											.hide();
									$("#form_div_startmonth").removeClass(
											"has-error");
									$("#form_div_startmonth_error").hide();
									$window.open(
											"../stockexcisereport/Stock Excise?&option="
													+ option + "&startdate="
													+ startdate + "&enddate="
													+ enddate + "&category="
													+ category + "&superClass="
													+ superClass + "", '_blank');

								} else {
									if ($scope.formData.startdate == undefined
											|| $scope.formData.startdate == "") {

										$("#form_div_startdate_stockexcise")
												.addClass("has-error");
										$(
												"#form_div_startdate_error_stockexcise")
												.show();
									}
									if ($scope.formData.enddate == undefined
											|| $scope.formData.enddate == "") {
										$("#form_div_enddate_stockexcise")
												.addClass("has-error");
										$("#form_div_enddate_error_stockexcise")
												.show();
									}
									
							
								}
							}
						}
					});

	$("#excelView")
			.click(
					function() {

						if ($("#select_report").val() == "number:9") {
							var monthNo = "";
							var startdate = "";
							var enddate = "";
							var option = "";
							var firstday = "";
							var lastday = "";
							var startmonth = "";
							if ($("#optnDate").prop('checked') == true) {
								startdate = "";
								enddate = "";
								startdate = getMysqlFormat($scope.formData.startdate);
								enddate = getMysqlFormat($scope.formData.enddate);
								category = $scope.category;
								superClass = $scope.superClass;
								option = "1";
							}
							/*	if ($("#optnMonth").prop('checked') == true) {
									startdate = "";
									enddate = "";
									startdate = $("#startmonth").val();
									startmonth = $("#startmonth1").val();
									category=$scope.category;
									var fields = startmonth.split('-');
									var year1 = fields[0];
									var monthName1 = fields[1];

									var date = new Date(), yr = date.getFullYear(), mnth = date
											.getMonth();
									startdate1 = new Date(Date.parse(startdate
											+ " 1," + yr));

									startdate = convertDate(startdate1);

									var monthNo = getMonthFromString(monthName1);

									startdate1 = new Date(Date.parse(monthNo
											+ " 1," + year1));

									startdate = convertDate(startdate1);
									option = "0";
								}*/

							/*if ($("#optnMonth").prop('checked') == true) {
								category=$scope.category;
								if ($("#startmonth1").val() != ""
										&& $("#startmonth1").val() != undefined) {
									$("#form_div_startdate_stockexcise")
											.removeClass("has-error");
									$("#form_div_startdate_error_stockexcise")
											.hide();
									$("#form_div_enddate_stockexcise")
											.removeClass("has-error");
									$("#form_div_enddate_error_stockexcise")
											.hide();
									$("#form_div_startmonth").removeClass(
											"has-error");
									$("#form_div_startmonth_error").hide();
									var link = document.createElement('a');
									link.href = "../stockexciserreport/Stock Excise Excel?&option="
											+ option
											+ "&startdate="
											+ startdate
											+ "&enddate="
											+ monthNo
											+ "&category="
											+ "";

									link.click();

								} else {
									$("#form_div_startmonth").addClass(
											"has-error");
									$("#form_div_startmonth_error").show();
								}

							}*/

							if ($("#optnDate").prop('checked') == true) {
								var strtDate2 = process($scope.formData.startdate);
								var endDate2 = process($scope.formData.enddate);
								var category = ($scope.category);
								var superClass = $scope.superClass;
								if ($scope.formData.startdate != undefined
										&& $scope.formData.startdate != ""
										&& $scope.formData.enddate != ""
										&& $scope.formData.startdate != undefined
										&& strtDate2 < endDate2) {
									$("#form_div_startdate_stockexcise")
											.removeClass("has-error");
									$("#form_div_startdate_error_stockexcise")
											.hide();
									$("#form_div_enddate_stockexcise")
											.removeClass("has-error");
									$("#form_div_enddate_error_stockexcise")
											.hide();
									$("#form_div_startmonth").removeClass(
											"has-error");
									$("#form_div_startmonth_error").hide();
									var link = document.createElement('a');
									link.href = "../stockexcisereport/Stock Excise Excel?&option="
											+ option
											+ "&startdate="
											+ startdate
											+ "&enddate="
											+ enddate
											+ "&category=" + category + "&superClass=" + superClass + "";

									link.click();

								} else {
									if ($scope.formData.startdate == undefined
											|| $scope.formData.startdate == "") {

										$("#form_div_startdate_stockexcise")
												.addClass("has-error");
										$(
												"#form_div_startdate_error_stockexcise")
												.show();
									}
									if ($scope.formData.enddate == undefined
											|| $scope.formData.enddate == "") {
										$("#form_div_enddate_stockexcise")
												.addClass("has-error");
										$("#form_div_enddate_error_stockexcise")
												.show();
									}
								}
							}
						}
					});

	$scope.tableDatevalidation = function() {
		if ($scope.formData.enddate != "") {
			var strtDate2 = process($scope.formData.startdate);
			var endDate2 = process($scope.formData.enddate);
			if (strtDate2 > endDate2) {

				//	$("#form_div_enddate").parent().after("<div class='validation' style='color:red;margin-bottom: 20px;'>Start date must be less than Enddate!</div>");
				$("div#divErrormsg2")
						.text("Start date must be less than Enddate!")
				$("div#divErrormsg2").show();
			} else if (strtDate2 - endDate2 == 0) {
				$("div#divErrormsg2").text(
						'Start date and End date cannot be same!');
				$("div#divErrormsg2").show();
			} else {
							$("div#divErrormsg2").hide();
						}
		}
	}

	function convertDate(inputFormat) {
		function pad(s) {
			return (s < 10) ? '0' + s : s;
		}

		var d = new Date(inputFormat);

		return [ d.getFullYear(), pad(d.getMonth() + 1), pad(d.getDate()) ]
				.join('-');
	}

	function getMonthFromString(mon) {
		return new Date(Date.parse(mon + " 1, 2012")).getMonth() + 1
	}

	$scope.getFirstDateInMonth = function(str) {
		var date = new Date(), yr = date.getFullYear(), mnth = date.getMonth();
		var strtNum = new Date(Date.parse(str + " 1," + yr)).getMonth() + 1;

		var strtDt = new Date(Date.parse(str + " 1," + yr));
		var formatedFirstDay = dateForm(strtDt);
		return formatedFirstDay;
	}

	$scope.getLastDateInMonth = function(str) {
		var date = new Date(), yr = date.getFullYear();
		var monthNum = new Date(Date.parse(str + " 1," + yr)).getMonth();
		var monthStart = new Date(yr, monthNum, 1);
		var monthEnd = new Date(year, monthNum + 1, 1);
		var monthLength = (monthEnd - monthStart) / (1000 * 60 * 60 * 24);
		var lastDay = new Date(Date.parse(str + " " + monthLength + "," + yr));
		var formatedLastDay = dateForm(lastDay);
		return formatedLastDay;
	}

	function validation() {
		var flg = true;
		if ($scope.formData.startdate == undefined
				|| $scope.formData.startdate == ""
				|| $scope.formData.startdate == null) {
			$("#form_div_startdate").addClass("has-error");
			$("#form_div_startdate_error").show();
			flg = false;
		} else {
			$("#form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error").hide();
		}

		if ($scope.formData.enddate == undefined
				|| $scope.formData.enddate == ""
				|| $scope.formData.enddate == null) {
			$("#form_div_enddate").addClass("has-error");
			$("#form_div_enddate_error").show();
			flg = false;
		} else {
			$("#form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error").hide();
		}

		return flg;
	}

	$(function() {
		var bindDatePicker = function() {
			$(".date").datetimepicker({
				format : 'YYYY-MMM',
				icons : {
					time : "fa fa-clock-o",
					date : "fa fa-calendar",

				},
				viewMode : "months",

			})
		}

		bindDatePicker();
	});

	var check = $scope.value;
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

myApp.directive('daterangePicker2', [ function() {
	return {
		controller9 : function($scope, $http) {

			return $scope;
		},
		link : function(scope, elem, attrs, controller9) {
			var dateFormat = get_date_format();
			$(elem).datetimepicker({
				"format" : 'MMM',
				viewMode : "months",
			}, function(start, end, label) {
			});

		}
	};
} ]);

angular.bootstrap(document.getElementById("app_stock-excise"),
		[ 'stock-excise_report_app' ]);