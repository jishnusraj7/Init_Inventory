//Controller for Stock Adjustment Summary Report form
mrpApp.controller('stockadjustmentsummaryCntrlr', stockadjustmentsummaryCntrlr);

function stockadjustmentsummaryCntrlr($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder, DTColumnBuilder,$timeout, $q, $window){
	
	set_sub_menu("#report");						
	setMenuSelected("#productionreport_left_menu");	
	
	$('#select_prodreport').click(function(){
		/* do if selected is stock adjustment summary report */
		if($('#select_prodreport').val()=="number:7")
		{
			/*get 1st and last date of the current month */
			var date = new Date(),y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);
			
			/* hide all other forms and show only the same */
			$("#stockadjustment_report_div").show();
			$('#prod_report_div').hide();
			$('#prod_bom_report_div').hide();
			$("#profit_summary_report_div").hide();
			$("#bom_analysis_report_div").hide();
			$("#prod_bal_report_div").hide();
			$("#bom_rate_comparison_div").hide();
			
			/* hide validation errors and set from and to date for form loading */
		    $("#stockadjustment_form_div_startdate").removeClass("has-error");
			$("#stockadjustment_form_div_startdate_error").hide();
			$("#form_div_stockadjustment_enddate").removeClass("has-error");
			$("#form_div_stockadjustment_enddate_error").hide();
			$("#div_stockadjustment_error_msg").hide();
			$timeout(function () {
				$scope.stockadjustment_startdate = dateForm(firstDay); 
				$scope.stockadjustment_enddate =  dateForm(lastDay);
			}, 1);
		}
	});
	
	/* validate and generate pdf report in button click */
	$("#exportpdf").click(function(){
		$("#div_stockadjustment_msg").hide();
		if($('#select_prodreport').val()=="number:7"){	

			var strtDate2 = process($scope.stockadjustment_startdate);
			var endDate2 = process($scope.stockadjustment_enddate);

			if(validation() && strtDate2 < endDate2){

				$("#stockadjustment_form_div_startdate").removeClass("has-error");
				$("#stockadjustment_form_div_startdate_error").hide();
				$("#form_div_stockadjustment_enddate").removeClass("has-error");
				$("#form_div_stockadjustment_enddate_error").hide();
				$("#div_stockadjustment_error_msg").hide();
				$window.open("../stockadjustmentreport/StockTakingSummary?&pdfexcel=pdf&startdate="+getMysqlFormat($scope.stockadjustment_startdate)
						+"&enddate="+getMysqlFormat($scope.stockadjustment_enddate)+"", '_blank');

			}	
		}
	});
	
	
	$("#excelView").click(function(){
		$("#div_stockadjustment_msg").hide();
		if($('#select_prodreport').val()=="number:7"){	

			var strtDate2 = process($scope.stockadjustment_startdate);
			var endDate2 = process($scope.stockadjustment_enddate);

			if(validation() && strtDate2 < endDate2){

				$("#stockadjustment_form_div_startdate").removeClass("has-error");
				$("#stockadjustment_form_div_startdate_error").hide();
				$("#form_div_stockadjustment_enddate").removeClass("has-error");
				$("#form_div_stockadjustment_enddate_error").hide();
				$("#div_stockadjustment_error_msg").hide();
				$window.open("../stockadjustmentreport/StockTakingSummary?&pdfexcel=excel&startdate="+getMysqlFormat($scope.stockadjustment_startdate)
						+"&enddate="+getMysqlFormat($scope.stockadjustment_enddate)+"", '_blank');

			}	
		}
	});
	
	/* validate empty date fields */
	function validation() {
		
		var flg = true;
		if($scope.stockadjustment_startdate == undefined || $scope.stockadjustment_startdate == "" 
			|| $scope.stockadjustment_startdate== null)
		{
			$("#form_div_stockadjustment_startdate").addClass("has-error");
			$("#form_div_stockadjustment_startdate_error").show();
			flg = false;
		}else{
			$("#form_div_stockadjustment_startdate").removeClass("has-error");
			$("#form_div_stockadjustment_startdate_error").hide();
		}

		if($scope.stockadjustment_enddate == undefined || $scope.stockadjustment_enddate == "" || $scope.stockadjustment_enddate == null){
			$("#form_div_stockadjustment_enddate").addClass("has-error");
			$("#form_div_stockadjustment_enddate_error").show();
			flg = false;
		}else{
			$("#form_div_stockadjustment_enddate").removeClass("has-error");
			$("#form_div_stockadjustment_enddate_error").hide();
		}	
		return flg;
	}
	
	/* validate date ranges on changing the date */
	$scope.tableDatevalidation = function(){
		
		if ($scope.stockadjustment_enddate != undefined && $scope.stockadjustment_enddate != "" && $scope.stockadjustment_enddate !=null ) {
			var strtDate2 = process($scope.stockadjustment_startdate);
			var endDate2 = process($scope.stockadjustment_enddate);
			if (strtDate2 > endDate2) {

				$("#div_stockadjustment_error_msg").text('Start date must be less than Enddate!');
				$("#div_stockadjustment_error_msg").show();
			}
			else
				$("#div_stockadjustment_error_msg").hide();
		}		
	}
}

	