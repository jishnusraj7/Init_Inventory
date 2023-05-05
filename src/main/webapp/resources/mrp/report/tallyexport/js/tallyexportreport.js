var myApp = angular.module('tally_export_report_app', [ 'ngMaterial' , 'ngMessages' , 'common_app' ,'checklist-model']);

myApp.controller('tallyExportCtrlr', tallyExportCtrlr);

function tallyExportCtrlr($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window,$filter) {
	$scope.formData = {};
	$scope.itemsData = [];
	$scope.department_data = [];
	$scope.itemsData1 = [];

	$http({
		url : '../stockin/formJsonData',
		method : "GET",
	}).then(function(response) {
		$scope.department_data = response.data.depData;
		$scope.itemsData1=response.data.stockItmData;
		$scope.formData.department_id= strings['isDefDepartment'];
		$scope.department_code = strings['isDefDepartmentcode'];
		$scope.department_name = strings['isDefDepartmentname'];
		$scope.itemsData1 = response.data.stockItmData;
		angular.copy($scope.itemsData1, $scope.itemsData);	

	}, function(response) { // optional
	});


	var vm = this;

	$scope.deliveryDateChange = function(){
		if($scope.formData.Deliveryenddate!= ""){
			var strtDate2=process($scope.formData.Deliverystartdate);
			var endDate2=process($scope.formData.Deliveryenddate);
			if(strtDate2 >endDate2){
				$("#divErrormsgStkout").text('Startdate must be less than Enddate!');
				$("#divErrormsgStkout").show();
			}else{
				$("#divErrormsgStkout").hide();
			}
		}

	}	

	$("#select_report").change(function(){
		if($("#select_report").val() == "number:13"){
			$("#btnTally").show();
			$("#btnTools").hide();
		}
	});


	$("#btn_cash").click(function(){
		
		if($("#select_report").val() == "number:13"){

			var option="0";
			var startDate = getMysqlFormat($scope.formData.Deliverystartdate);
			var endDate = getMysqlFormat($scope.formData.Deliveryenddate); 
			$http({

				url:'../tallyExportReport/tallyPurchase',
				params:{option:option,startDate:startDate,endDate:endDate},
				method:'GET',
			}).then(function(response){
				if(response.data!=""){
				download(response.data, "tallyPurchase.txt", "application/txt" ); 
				}else{
					$("#tallyModal").modal('show');
				}
			});
		}
	});

	$("#btn_credit").click(function(){

		if($("#select_report").val() == "number:13"){
            
			var option="1";
			var startDate = getMysqlFormat($scope.formData.Deliverystartdate);
			var endDate = getMysqlFormat($scope.formData.Deliveryenddate); 
			$http({

				url:'../tallyExportReport/tallyPurchase',
				params:{option:option,startDate:startDate,endDate:endDate},
				method:'GET',
			}).then(function(response){
				if(response.data!=""){
				download(response.data, "tallyPurchase.txt", "application/txt" ); 
				}else{
					$("#tallyModal").modal('show');
				}
			});
		
		    }
	});

	$("#btn_all").click(function(){

		if($("#select_report").val() == "number:13"){

			var option="ALL";
			var startDate = getMysqlFormat($scope.formData.Deliverystartdate);
			var endDate = getMysqlFormat($scope.formData.Deliveryenddate); 
			//$window.open("../tallyExportReport/tallyPurchase?option="+option+"&startDate="+startDate+"&endDate="+endDate+"", '_blank');
			$http({

				url:'../tallyExportReport/tallyPurchase',
				params:{option:option,startDate:startDate,endDate:endDate},
				method:'GET',
			}).then(function(response){
				if(response.data!=""){
				download(response.data, "tallyPurchase.txt", "application/txt" ); 
				}else{
					$("#tallyModal").modal('show');
				}
			});
		}
	});

	
	$("#form_div_radio_slctrprtSTK input:radio").click(function() {

		$scope.$apply(function(){
			$scope.selectedItemList=[];
			$scope.filterItemData = [];
			angular.copy($scope.itemsData1,$scope.itemsData);
			$scope.serch="";
			$scope.user.roles=[];
			$scope.userSelect.roles = [];
			$scope.is_active=false;
			$scope.is_active_select=false;
		});


		var dateFormat = get_date_format();
		var date = new Date();
		var y = date.getFullYear();
		var m = date.getMonth();
		var curDate = new Date();
		var firstDay = new Date(y, m, 1);
		var lastDay = new Date(y, m + 1, 0);
		$timeout(function() {

			$scope.formData.Deliveryenddate = dateForm(lastDay);
			$scope.formData.Deliverystartdate = dateForm(firstDay);
		}, 1);

		$(".acontainer").find('input').val('');
		$("#source_department_name").val('');
		$("#dest_department_name").val('');
		$("#form_div_item_id_stockout #itemname1").val('');
		$scope.formData.itemid1='';
		$scope.formData.source_department_id='';
		$scope.formData.dest_department_id='';

		$("#form_div_stockout_startdate").removeClass("has-error");
		$("#form_div_stockout_startdate_error").hide();
		$("#form_div_stockout_enddate").removeClass("has-error");
		$("#form_div_stockout_enddate_error").hide();
	});

	function validation()
	{
		var flg = true;	

		if($scope.formData.Deliverystartdate == undefined || $scope.formData.Deliverystartdate == ""){
			$("#form_div_stockout_startdate").addClass("has-error");
			$("#form_div_stockout_startdate_error").show();
			flg = false;
		}else{
			$("#form_div_stockout_startdate").removeClass("has-error");
			$("#form_div_stockout_startdate_error").hide();
		}

		if($scope.formData.Deliveryenddate== undefined || $scope.formData.Deliveryenddate == ""){
			$("#form_div_stockout_enddate").addClass("has-error");
			$("#form_div_stockout_enddate_error").show();
			flg = false;
		}else{

			$("#form_div_stockout_enddate").removeClass("has-error");
			$("#form_div_stockout_enddate_error").hide();
		}
		if($scope.formData.Deliveryenddate!= ""){
			var strtDate2=process($scope.formData.Deliverystartdate);
			var endDate2=process($scope.formData.Deliveryenddate);
			if(strtDate2 >endDate2){

				$("#divErrormsgStkout").text('Startdate must be less than Enddate!');
				$("#divErrormsgStkout").show();
				flg = false;
			}else{
				$("#divErrormsgStkout").hide();
			}
		}


		return flg;

	}



	$(document).on('keyup','#form_div_item_id_stockout .acontainer input', function(e) {
		if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){
			$("#form_div_item_id_stockout").find(".acontainer input").val('');
			$('#form_div_item_id_stockout #itemname1').val('');
			$scope.formData.itemid1="";
			$scope.formData.itemname1="";}

	});

}



myApp.directive('daterangePicker1', [function() {
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

			controller.formData.Deliveryenddate =  dateForm(lastDay);

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a day
			var diff = todayTimeStamp - oneDayTimeStamp;
			var yesterdayDate = new Date(diff);

			controller.formData.Deliverystartdate = dateForm(firstDay);

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
}]);

angular.bootstrap(document.getElementById("tally_export_report_app"), ['tally_export_report_app']);