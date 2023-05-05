//Controller for Table and Form 

mrpApp.controller('profitSummaryCntrlr', profitSummaryCntrlr);

function profitSummaryCntrlr($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window ) {
	
	$scope.summaryOption=1;
	set_sub_menu("#report");						
	setMenuSelected("#productionreport_left_menu");		
	
	/*$("#exportpdf").click(function(){
		if($scope.startdate!=undefined){
		$window.open("report?&option="+$scope.option+"&startdate="+getMysqlFormat($scope.startdate)+"&enddate="+getMysqlFormat($scope.enddate)+"", '_blank');
		}
	
	});*/
	
	$scope.clearErr=function()
	{
		$("#profitsummary_startdate").removeClass("has-error");
		$("#profitsummary_startdate_error").hide();
		$("#profitsummary_enddate").removeClass("has-error");
		$("#profitsummary_enddate_error").hide();
		$("#divErrormsg1_profit").hide();
		var dateFormat = get_date_format();
		var date = new Date();
		var y = date.getFullYear();
		var m = date.getMonth();
		var curDate = new Date();
		var firstDay = new Date(y, m, 1);
		var lastDay = new Date(y, m + 1, 0);

	

		$timeout(function() {
			$scope.profit_startdate =dateForm(firstDay); 
           $scope.profit_enddate= dateForm(lastDay);
		}, 1);
		
	}
	
	$('#select_prodreport').click(function(){
		if($('#select_prodreport').val()=="number:3")
		{
			$('#prod_bom_report_div').hide();
			$('#prod_report_div').hide();
			$("#profit_summary_report_div").show();
			$("#bom_analysis_report_div").hide();
			$("#divErrormsg1_profit").hide();
			$("#prod_bal_report_div").hide();
			$("#stockadjustment_report_div").hide();

			$scope.$apply(function(){
				$scope.summaryOption=1;
				$scope.clearErr();
			});
			
		}
		
	});
	
	$("#exportpdf").click(function(){
		if($('#select_prodreport').val()=="number:3")
		{
	
		
	
			
			if(validation() && $scope.profit_startdate < $scope.profit_enddate){
			   
			    $("#profitsummary_startdate").removeClass("has-error");
				$("#profitsummary_startdate_error").hide();
				$("#profitsummary_enddate").removeClass("has-error");
				$("#profitsummary_enddate_error").hide();
				$("#divErrormsg1_profit").hide();
				if($scope.summaryOption==1)
			{		
		$window.open("../profitsummary/profitreport?&startdate="+getMysqlFormat($scope.profit_startdate)+"&enddate="+getMysqlFormat($scope.profit_enddate)+"&option=1"+"&pdfExcel=pdf", '_blank');
			}else
				{
				$window.open("../profitsummary/profitreport?&startdate="+getMysqlFormat($scope.profit_startdate)+"&enddate="+getMysqlFormat($scope.profit_enddate)+"&option=2"+"&pdfExcel=pdf", '_blank');	
				}
		   
			}	
			
	
	/*else {
		var date = new Date(), yr = date.getFullYear(), mnth = date
		.getMonth();
		
		var monthNo=getMonthFromString($('#startmonth1').val());
		
		var startdate = new Date(Date.parse(monthNo
				+ " 1," + yr));
		
		
		
		$window.open("../prodbomreport/bommonthreport?&monthNo="+monthNo+"&year="+yr+"&startdate="+convertDate(startdate)+"&month="+$('#startmonth1').val()+"&department_id="+$scope.source_department_id+"&department_name="+$scope.source_name+"", '_blank');

	}*/
		}
	});
	
	
	$("#excelView").click(function(){
		if($('#select_prodreport').val()=="number:3")
		{
	
		
	
			
			if(validation() && $scope.profit_startdate < $scope.profit_enddate){
			   
			    $("#profitsummary_startdate").removeClass("has-error");
				$("#profitsummary_startdate_error").hide();
				$("#profitsummary_enddate").removeClass("has-error");
				$("#profitsummary_enddate_error").hide();
				$("#divErrormsg1_profit").hide();
				if($scope.summaryOption==1)
			{		
		$window.open("../profitsummary/profitreport?&startdate="+getMysqlFormat($scope.profit_startdate)+"&enddate="+getMysqlFormat($scope.profit_enddate)+"&option=1"+"&pdfExcel=excel", '_blank');
			}else
				{
				$window.open("../profitsummary/profitreport?&startdate="+getMysqlFormat($scope.profit_startdate)+"&enddate="+getMysqlFormat($scope.profit_enddate)+"&option=2"+"&pdfExcel=excel", '_blank');	
				}
		   
			}	
			
	
	/*else {
		var date = new Date(), yr = date.getFullYear(), mnth = date
		.getMonth();
		
		var monthNo=getMonthFromString($('#startmonth1').val());
		
		var startdate = new Date(Date.parse(monthNo
				+ " 1," + yr));
		
		
		
		$window.open("../prodbomreport/bommonthreport?&monthNo="+monthNo+"&year="+yr+"&startdate="+convertDate(startdate)+"&month="+$('#startmonth1').val()+"&department_id="+$scope.source_department_id+"&department_name="+$scope.source_name+"", '_blank');

	}*/
		}
	});
	

	function convertDate(inputFormat) {

		function pad(s) {
			return (s < 10) ? '0' + s : s;
		}
		var d = new Date(inputFormat);

		return [ d.getFullYear(), pad(d.getMonth() + 1), pad(d.getDate()) ]
				.join('-');

	}
	
	/*function getMonthFromString(mon) {
		return new Date(Date.parse(mon + " 1, 2012")).getMonth() + 1
	}*/
	
	
	function validation(){
		var flg = true;

		if($scope.profit_startdate == undefined || $scope.profit_startdate == "" || $scope.profit_startdate== null){
			$("#profitsummary_startdate").addClass("has-error");
			$("#profitsummary_startdate_error").show();
			flg = false;
		}else{
			$("#profitsummary_startdate").removeClass("has-error");
			$("#profitsummary_startdate_error").hide();
		}

		if($scope.profit_enddate == undefined || $scope.profit_enddate == "" || $scope.profit_enddate == null){
			$("#profitsummary_enddate").addClass("has-error");
			$("#profitsummary_enddate_error").show();
			flg = false;
		}else{
			
			$("#profitsummary_enddate").removeClass("has-error");
			$("#profitsummary_enddate_error").hide();
		}
		var strtDte=process($scope.profit_startdate);
		var endDte=process($scope.profit_enddate);
		if(strtDte > endDte ){
			
			$("#divErrormsg1_profit").text('Start date must be less than Enddate!');
			$("#divErrormsg1_profit").show();
			flg = false;
		}
		else if(strtDte-endDte==0)
			{
			$("#divErrormsg1_profit").text('Start date and End date cannot be same!');
			$("#divErrormsg1_profit").show();
			flg = false;
			}
		else{
			$("#divErrormsg1_profit").hide();
			
		}
		
		
		

		return flg;
	}
	$scope.tableDatevalidation=function(){
		if($scope.profit_enddate != undefined && $scope.profit_enddate != "" && $scope.profit_enddate !=null ){
			var strtDte=process($scope.profit_startdate);
			var endDte=process($scope.profit_enddate);
			if(strtDte > endDte ){
				
				$("#divErrormsg1_profit").text('Start date must be less than Enddate!');
				$("#divErrormsg1_profit").show();
				
			}else if(strtDte-endDte == 0)
				{
				$("#divErrormsg1_profit").text('Start date and End date cannot be same!');
				$("#divErrormsg1_profit").show();
				}
			else{
				$("#divErrormsg1_profit").hide();
				
			}
		}}
	
/*	var destinationData = $("#source_code").tautocomplete({
		columns: ['id','code' , 'name'],
		hide: [false,true,true],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay : 10,
		onchange: function () {
			var selected_department_data =  destinationData.all();
			$scope.$apply(function(){
				$scope.source_department_id =  selected_department_data.id;
				$scope.source_code =  selected_department_data.code;
				$scope.source_name =  selected_department_data.name;
			});

		},
		data: function () {

			var data = department_data;
			var filterData = [];
			var searchData = eval("/" + destinationData.searchdata() + "/gi");
			$.each(data, function (i, v) {
				if (v.code.search(new RegExp(searchData)) != -1 || v.name.search(new RegExp(searchData)) != -1) {
					{
						filterData.push(v);
					}

				}
			});

			return filterData;
		},


	});	 */
	
	/*var department_data = [];
	$http({	url : '../stockin/depList',	method : "GET",async:false,
	}).then(function(response) {
		department_data = response.data.data;
	}, function(response) { // optional
	});*/
	/*function setDepDetailsById(id)
	{
		for(var i=0;i<department_data.length;i++)
			{
			if(department_data[i].id == id)
			{
				$scope.source_code=department_data[i].code;
				$("#form_div_source_code").find(".acontainer input").val($scope.source_code);
				$scope.source_name=department_data[i].name;
			}
		}
	}*/
	
/*	$(document).on('keyup','#form_div_source_code input',function(e){
		if( e.which==13){
			$("#items_table tr:nth-child("+(2)+") td:nth-child("+(3)+")").find(".acontainer input").focus();
		}
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$scope.source_department_id ="";
				$scope.source_name ="";
				$scope.source_code ="";
			});
		}
	});*/
	
}

/*mrpApp.directive('daterangePicker2', [ function() {
	return {
		controller1 : function($scope, $http) {

			return $scope;
		},
		link : function(scope, elem, attrs, controller1) {
			var dateFormat = get_date_format();
			$(elem).datetimepicker({
				"format" : 'MMM',
				viewMode : "months",
			}, function(start, end, label) {
			});

		}
	};
} ]);
*/

mrpApp.directive('daterangePicker02', [function() {
	return {
		controller: function ($scope,$http) {

			return $scope;
		},
		link: function(scope, elem, attrs ,controller) {
			var dateFormat = get_date_format();
			var date = new Date(),y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);

			controller.profit_enddate =  dateForm(lastDay);
			controller.profit_startdate = dateForm(firstDay);

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
}]);

