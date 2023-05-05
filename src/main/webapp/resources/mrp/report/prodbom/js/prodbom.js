//Controller for Table and Form 

mrpApp.controller('prodBomCntrlr', prodBomCntrlr);

function prodBomCntrlr($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window ) {
	
	$scope.option=1;
	set_sub_menu("#report");						
	setMenuSelected("#productionreport_left_menu");		
	
	/*$("#exportpdf").click(function(){
		if($scope.startdate!=undefined){
		$window.open("report?&option="+$scope.option+"&startdate="+getMysqlFormat($scope.startdate)+"&enddate="+getMysqlFormat($scope.enddate)+"", '_blank');
		}
	
	});*/
	
	$('#select_prodreport').click(function(){
		if($('#select_prodreport').val()=="number:2")
		{
			var date = new Date(),y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();
			var firstDay = new Date(y, m, 1);
			var lastDay = new Date(y, m + 1, 0);
			var date1 = moment().format('YYYY-MMM');
			
			$('#prod_bom_report_div').show();
			$('#prod_report_div').hide();
			$("#profit_summary_report_div").hide();
			$("#bom_analysis_report_div").hide();
			$("#prod_bal_report_div").hide();
			$("#stockadjustment_report_div").hide();


			$scope.option=1;
			$scope.source_department_id ="";
			$scope.source_name ="";
			$scope.source_code ="";
			$("#form_div_source_code .acontainer input").val('');
			$("#msg").hide();
			    $("#bom_form_div_startdate").removeClass("has-error");
				$("#form_div_startdate_error_bom").hide();
				$("#bom_form_div_enddate").removeClass("has-error");
				$("#form_div_enddate_error_bom").hide();
				$("#divErrormsg1_bom").hide();
				$("#form_div_startmonth").removeClass("has-error");
				   $timeout(function () {
					   $scope.bom_enddate =  dateForm(lastDay);
						$scope.bom_startdate = dateForm(firstDay); 
						$('#startmonth2').val(date1);
					    }, 1);
					
				  
				
		}
		
	});
	$scope.clearErr=function()
	{
		var date = new Date(),y = date.getFullYear(), m = date.getMonth();
		var curDate = new Date();
		var firstDay = new Date(y, m, 1);
		var lastDay = new Date(y, m + 1, 0);

		
		 $("#bom_form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error_bom").hide();
			$("#bom_form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error_bom").hide();
			$("#divErrormsg1_bom").hide();
			$("#form_div_startmonth").removeClass("has-error");
		  $("#msg").hide();
		  if($scope.option ==1)
			  {
			  	date = moment().format('YYYY-MMM');
			  	$('#startmonth2').val(date);
              }
		  $scope.source_code ="";
		  $("#form_div_source_code .acontainer input").val('');
		  $scope.source_department_id ="";
		  $scope.source_name ="";
		  $scope.bom_enddate =  dateForm(lastDay);
		  $scope.bom_startdate = dateForm(firstDay);
	}
	
	
	$("#exportpdf").click(function(){
		  $("#msg").hide();
		if($('#select_prodreport').val()=="number:2")
		{
			
			if($scope.source_department_id==undefined || $scope.source_department_id==""){
				
				  $("#msg").text('Please select source department');
				  $("#form_div_source_code .acontainer input").focus();
				  $("#msg").show();
					
				
			}else{
				
				
			
			
			
	if($scope.option==1){
		
		
		
		var strtDate2=process($scope.bom_startdate);
		var endDate2=process($scope.bom_enddate);
			
			if(validation() && strtDate2 < endDate2){
			   
			    $("#bom_form_div_startdate").removeClass("has-error");
				$("#form_div_startdate_error_bom").hide();
				$("#bom_form_div_enddate").removeClass("has-error");
				$("#form_div_enddate_error_bom").hide();
				$("#divErrormsg1_bom").hide();
				
		$window.open("../prodbomreport/BOM Report?&startdate="+getMysqlFormat($scope.bom_startdate)+"&enddate="+getMysqlFormat($scope.bom_enddate)+" &department_id="+$scope.source_department_id+"&department_name="+$scope.source_name+"&pdfExcel=pdf", '_blank');

		   
			}	
			
	}else {
		
		if($('#startmonth1').val() != "") 
		{/*
			$("#form_div_startmonth").removeClass("has-error");
			 $("#msg").hide();
		var date = new Date(), yr = date.getFullYear(), mnth = date
		.getMonth();
		
		var monthNo=getMonthFromString($('#startmonth1').val());
		
		var startdate = new Date(Date.parse(monthNo
				+ " 1," + yr));
		
		
		alert($('#startmonth1').val());
		$window.open("../prodbomreport/bommonthreport?&monthNo="+monthNo+"&year="+yr+"&startdate="+convertDate(startdate)+"&month="+$('#startmonth1').val()+"&department_id="+$scope.source_department_id+"&department_name="+$scope.source_name+"", '_blank');

	*/}
		if($('#startmonth2').val() !='')
			{

			var startdate = "";
			var enddate = "";
			startmonth2 = $("#startmonth2").val();

			var fields = startmonth2.split('-');
			var year1 = fields[0];
			var monthName1 = fields[1];

			var date = new Date(), yr = date.getFullYear(), mnth = date
					.getMonth();

			
			var monthNo = getMonthFromString(monthName1);

			var startdate1 = new Date(Date.parse(monthNo
					+ " 1," + year1));

			startdate = convertDate(startdate1);
			//alert(monthNo+" "+year1+" "+startdate+" "+monthName1);
			$window.open("../prodbomreport/bommonthreport?&monthNo="+monthNo+"&year="+year1+"&startdate="+startdate+"&month="+monthName1+"&department_id="+$scope.source_department_id+"&department_name="+$scope.source_name+"&pdfExcel=pdf", '_blank');
			
			$("#form_div_startmonth").removeClass("has-error");
			}
		
		else
		{
		$("#form_div_startmonth").addClass("has-error");
		 $("#msg").text('Please select Month');
		  $("#msg").show();
		}
	}}
			
		}
	});
	
	
	
	$("#excelView").click(function(){
		  $("#msg").hide();
		if($('#select_prodreport').val()=="number:2")
		{
			
			if($scope.source_department_id==undefined || $scope.source_department_id==""){
				
				  $("#msg").text('Please select source department');
				  $("#form_div_source_code .acontainer input").focus();
				  $("#msg").show();
					
				
			}else{
				
				
			
			
			
	if($scope.option==1){
		
		
		
		var strtDate2=process($scope.bom_startdate);
		var endDate2=process($scope.bom_enddate);
			
			if(validation() && strtDate2 < endDate2){
			   
			    $("#bom_form_div_startdate").removeClass("has-error");
				$("#form_div_startdate_error_bom").hide();
				$("#bom_form_div_enddate").removeClass("has-error");
				$("#form_div_enddate_error_bom").hide();
				$("#divErrormsg1_bom").hide();
				
		$window.open("../prodbomreport/BOM Report?&startdate="+getMysqlFormat($scope.bom_startdate)+"&enddate="+getMysqlFormat($scope.bom_enddate)+" &department_id="+$scope.source_department_id+"&department_name="+$scope.source_name+"&pdfExcel=excel", '_blank');

		   
			}	
			
	}else {
		
		if($('#startmonth1').val() != "") 
		{/*
			$("#form_div_startmonth").removeClass("has-error");
			 $("#msg").hide();
		var date = new Date(), yr = date.getFullYear(), mnth = date
		.getMonth();
		
		var monthNo=getMonthFromString($('#startmonth1').val());
		
		var startdate = new Date(Date.parse(monthNo
				+ " 1," + yr));
		
		
		alert($('#startmonth1').val());
		$window.open("../prodbomreport/bommonthreport?&monthNo="+monthNo+"&year="+yr+"&startdate="+convertDate(startdate)+"&month="+$('#startmonth1').val()+"&department_id="+$scope.source_department_id+"&department_name="+$scope.source_name+"", '_blank');

	*/}
		if($('#startmonth2').val() !='')
			{

			var startdate = "";
			var enddate = "";
			startmonth2 = $("#startmonth2").val();

			var fields = startmonth2.split('-');
			var year1 = fields[0];
			var monthName1 = fields[1];

			var date = new Date(), yr = date.getFullYear(), mnth = date
					.getMonth();

			
			var monthNo = getMonthFromString(monthName1);

			var startdate1 = new Date(Date.parse(monthNo
					+ " 1," + year1));

			startdate = convertDate(startdate1);
			//alert(monthNo+" "+year1+" "+startdate+" "+monthName1);
			$window.open("../prodbomreport/bommonthreport?&monthNo="+monthNo+"&year="+year1+"&startdate="+startdate+"&month="+monthName1+"&department_id="+$scope.source_department_id+"&department_name="+$scope.source_name+"&pdfExcel=excel", '_blank');
			
			$("#form_div_startmonth").removeClass("has-error");
			}
		
		else
		{
		$("#form_div_startmonth").addClass("has-error");
		 $("#msg").text('Please select Month');
		  $("#msg").show();
		}
	}}
			
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
	
	function getMonthFromString(mon) {
		return new Date(Date.parse(mon + " 1, 2012")).getMonth() + 1
	}
	
	
	function validation(){
		var flg = true;

		if($scope.bom_startdate == undefined || $scope.bom_startdate == "" || $scope.bom_startdate== null){
			$("#bom_form_div_startdate").addClass("has-error");
			$("#form_div_startdate_error_bom").show();
			flg = false;
		}else{
			$("#bom_form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error_bom").hide();
		}

		if($scope.bom_enddate == undefined || $scope.bom_enddate == "" || $scope.bom_enddate == null){
			$("#bom_form_div_enddate").addClass("has-error");
			$("#form_div_enddate_error_bom").show();
			flg = false;
		}else{
			$("#bom_form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error_bom").hide();
		}
		

		return flg;
	}
	$scope.tableDatevalidation=function(){
		
		if ($scope.bom_enddate != undefined && $scope.bom_enddate != "" && $scope.bom_enddate !=null ) {
			var strtDate2=process($scope.bom_startdate);
			var endDate2=process($scope.bom_enddate);
			if (strtDate2 > endDate2) {

				$("#divErrormsg1_bom").text('Start date must be less than Enddate!');
				$("#divErrormsg1_bom").show();
			} else
			if(strtDate2-endDate2 == 0)
				{
				$("#divErrormsg1_bom").text('Start date and End date cannot be same!');
				$("#divErrormsg1_bom").show();
				}
			else
				{
				
				$("#divErrormsg1_bom").hide();
				}
		}
		
	}
	
	var destinationData = $("#source_code").tautocomplete({
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


	});	 
	
	var department_data = [];
	//$http({	url : '../stockin/depList',	method : "GET",async:false,
	$http({	url : '../prodbomreport/getDepList',	
		method : "GET",
		async:false,
	}).then(function(response) {
		department_data = response.data.data;
	}, function(response) { // optional
	});
	function setDepDetailsById(id)
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
	}
	
	$(document).on('keyup','#form_div_source_code input',function(e){
		/*if( e.which==13){
			$("#items_table tr:nth-child("+(2)+") td:nth-child("+(3)+")").find(".acontainer input").focus();
		}*/
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$scope.source_department_id ="";
				$scope.source_name ="";
				$scope.source_code ="";
			});
		}
	});
	
	$scope.changetype=function()
	{
		$scope.source_department_id ="";
		$scope.source_name ="";
		$scope.source_code ="";
		$("#form_div_source_code .acontainer input").val('');
		$("#msg").hide();
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

	
}

mrpApp.directive('daterangePicker2', [ function() {
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


mrpApp.directive('daterangePicker01', [function() {
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

			controller.bom_enddate =  dateForm(lastDay);
			controller.bom_startdate = dateForm(firstDay);

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

