//Controller for Table and Form 

mrpApp.controller('bomAnalysisCntrlr', bomAnalysisCntrlr);

function bomAnalysisCntrlr($scope, $http, $mdDialog, $rootScope,
		DTOptionsBuilder, DTColumnBuilder, $timeout, $q, $window, $filter) {

	setMenuSelected("#productionreport_left_menu");
	var dateFormat = get_date_format();
	var date = new Date();
	var y = date.getFullYear();
	var m = date.getMonth();
	var curDate = new Date();
	var firstDay = new Date(y, m, 1);
	var lastDay = new Date(y, m + 1, 0);

	$timeout(function() {
		$scope.bomanalysis_startdate = dateForm(firstDay);
		$scope.bomanalysis_enddate = dateForm(lastDay);
	}, 1);

	$('#select_prodreport').click(function() {
		if ($('#select_prodreport').val() == "number:4") {

			$('#prod_bom_report_div').hide();
			$('#prod_report_div').hide();
			$("#profit_summary_report_div").hide();
			$("#bom_analysis_report_div").show();
			$("#prod_bal_report_div").hide();
			$("#stockadjustment_report_div").hide();			

			$("#bomanalysis_startdate").removeClass("has-error");
			$("#bomanalysis_startdate_error").hide();
			$("#bomanalysis_enddate").removeClass("has-error");
			$("#bomanalysis_enddate_error").hide();
			$("#divErrormsg1_bomanalysis").hide();

			var dateFormat = get_date_format();
			var date = new Date();
			var y = date.getFullYear();
			var m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);

			$timeout(function() {
				$scope.bomanalysis_startdate = dateForm(firstDay);
				$scope.bomanalysis_enddate = dateForm(lastDay);
			}, 1);

		}

	});

	$("#form_div_startdate").removeClass("has-error");
	$("#form_div_startdate_error").hide();

	$("#form_div_enddate").removeClass("has-error");
	$("#form_div_enddate_error").hide();
	$("#divErrormsg2").hide();
	$("#profit_summary_report_div").hide();

	$("#exportpdf")
	.click(
			function() {

				if($('#select_prodreport').val()=="number:4")
				{



					if (validation()
							&& process($scope.bomanalysis_startdate) < process($scope.bomanalysis_enddate)) {

						$("#bomanalysis_startdate")
						.removeClass("has-error");
						$("#bomanalysis_startdate_error").hide();
						$("#bomanalysis_enddate").removeClass("has-error");
						$("#bomanalysis_enddate_error").hide();
						$("#divErrormsg1_bomanalysis").hide();

						$window.open("../bomanalysisreport/bomanalysisReport?&startdate="+getMysqlFormat($scope.bomanalysis_startdate)+"&enddate="+getMysqlFormat($scope.bomanalysis_enddate)+"&pdfExcel=pdf", '_blank');

					}

				}

			});
	
	
	$("#excelView")
	.click(
			function() {

				if($('#select_prodreport').val()=="number:4")
				{



					if (validation()
							&& process($scope.bomanalysis_startdate) < process($scope.bomanalysis_enddate)) {

						$("#bomanalysis_startdate")
						.removeClass("has-error");
						$("#bomanalysis_startdate_error").hide();
						$("#bomanalysis_enddate").removeClass("has-error");
						$("#bomanalysis_enddate_error").hide();
						$("#divErrormsg1_bomanalysis").hide();

						$window.open("../bomanalysisreport/bomanalysisReport?&startdate="+getMysqlFormat($scope.bomanalysis_startdate)+"&enddate="+getMysqlFormat($scope.bomanalysis_enddate)+"&pdfExcel=excel", '_blank');

					}

				}

			});

	function validation() {
		var flg = true;

		if ($scope.bomanalysis_startdate == undefined
				|| $scope.bomanalysis_startdate == ""
					|| $scope.bomanalysis_startdate == null) {
			$("#bomanalysis_startdate").addClass("has-error");
			$("#bomanalysis_startdate_error").show();
			flg = false;
		} else {
			$("#bomanalysis_startdate").removeClass("has-error");
			$("#bomanalysis_startdate_error").hide();
		}

		if ($scope.bomanalysis_enddate == undefined
				|| $scope.bomanalysis_enddate == ""
					|| $scope.bomanalysis_enddate == null) {
			$("#bomanalysis_enddate").addClass("has-error");
			$("#bomanalysis_enddate_error").show();
			flg = false;
		} else {

			$("#bomanalysis_enddate").removeClass("has-error");
			$("#bomanalysis_enddate_error").hide();
		}
		var strtDte = process($scope.bomanalysis_startdate);
		var endDte = process($scope.bomanalysis_enddate);
		if (strtDte > endDte) {

			$("#divErrormsg1_bomanalysis").text(
			'Start date must be less than Enddate!');
			$("#divErrormsg1_bomanalysis").show();
			flg = false;
		} else if (strtDte - endDte == 0) {
			$("#divErrormsg1_bomanalysis").text(
			'Start date and End date cannot be same!');
			$("#divErrormsg1_bomanalysis").show();
			flg = false;
		} else {
			$("#divErrormsg1_bomanalysis").hide();

		}

		return flg;
	}

	$scope.tableDatevalidation = function() {
		if ($scope.bomanalysis_enddate != undefined
				&& $scope.bomanalysis_enddate != ""
					&& $scope.bomanalysis_enddate != null) {
			var strtDte = process($scope.bomanalysis_startdate);
			var endDte = process($scope.bomanalysis_enddate);
			if (strtDte > endDte) {

				$("#divErrormsg1_bomanalysis").text(
				'Start date must be less than Enddate!');
				$("#divErrormsg1_bomanalysis").show();

			} else if (strtDte - endDte == 0) {
				$("#divErrormsg1_bomanalysis").text(
				'Start date and End date cannot be same!');
				$("#divErrormsg1_bomanalysis").show();
			} else {
				$("#divErrormsg1_bomanalysis").hide();

			}
		}
	}

}

mrpApp.directive('daterangePicker19', [ function() {
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

			controller.bomanalysis_enddate = dateForm(lastDay);

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a
			// day
			var diff = todayTimeStamp - oneDayTimeStamp;
			var yesterdayDate = new Date(diff);

			controller.bomanalysis_startdate = dateForm(firstDay);

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
