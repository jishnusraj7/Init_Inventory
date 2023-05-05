var myApp = angular.module('stockin_report_app', [ 'ngMaterial' , 'ngMessages' , 'common_app' ,'checklist-model']);

myApp.controller('stockinCtrlr', stockinCtrlr);

function stockinCtrlr($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window ) {
	$scope.formData = {};
	$scope.stockData = [];
	$scope.purchaseorderdtllist=[];
	$scope.supplier_data1=[];
	$scope.itemsData = [];
	$scope.department_data = [];
	$scope.itemsData1 = [];


	$http({
		url : '../stockin/formJsonDataReport',
		method : "GET",
	}).then(function(response) {
		$scope.department_data = response.data.depData;
		$scope.purchaseorderdtllist = response.data.purchaseorderdtllist;
		$scope.supplier_data1 = response.data.supData;
//		$scope.itemsData = response.data.stockItmData;
		$scope.itemsData1 = response.data.stockItmData;
		angular.copy($scope.itemsData1, $scope.itemsData);	
	}, function(response) { // optional
		});


	var vm = this;

	$("#divErrormsg").hide();



	$("#select_report")
	.change(
			function() {
				$("#btnTally").hide();
				$("#form_div_startdate_stkIn").removeClass("has-error");
				$("#form_div_startdate_error_stkIn").hide();
				$("#form_div_enddate_stkIn").removeClass("has-error");
				$("#form_div_enddate_error_stkIn").hide();
				
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
			});
	
	
	//checkboxlist
    
    $scope.is_active=false;
	$scope.is_active_select=false;
    $scope.serch="";
	 $scope.user = {
			    roles: []
			  };
	 
	 $scope.userSelect={
			 roles:[]
	 };
    
    
	 $scope.checkAll = function() {
		  $scope.user.roles = angular.copy( $scope.filterItemData );
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
	
	
	$scope.moveItem=function()
	{
		
		if($scope.is_active==true)
			{
			
			$scope.checkAll();
						}
		else
			{
			 $scope.uncheckAll();
			}
	}
	
	$scope.moveItemSelect=function()
	{
		
		if($scope.is_active_select==true)
			{
			
			$scope.checkAllSelect();
						}
		else
			{
			 $scope.uncheckAllSelect();
			}
	}
	$scope.selectedShop = []; 
	
	
	$scope.selectedItemList=[];

	 
	 $scope.btnRight = function () {
	        //move selected.
		if($scope.user.roles.length!=0)
			{
			if($scope.is_active==true )
			{
			$scope.is_active=false;
			}
			$scope.is_active_select=false;
		  for(var i=0;i<$scope.user.roles.length;i++)
			  {
			  $scope.selectedItemList.push($scope.user.roles[i]);
			  }
		 
		  
		  //remove the ones that were moved from the source container.
		  
		  for(var i=0;i<$scope.user.roles.length;i++)
			  {
			  for(var j=0;j<$scope.itemsData.length;j++)
				  {
			 if( $scope.user.roles[i].id==$scope.itemsData[j].id)
				 {
				 $scope.itemsData.splice(j, 1);
				 }
			  }
			  }
		  
			}
		 $scope.searchValue();
		$scope.user.roles=[];
		
		
		  
	  }
	
	
	
	 $scope.btnLeft = function () {
	        //move selected.
		 if($scope.userSelect.roles.length!=0)
			 {
			 
			 if($scope.is_active_select==true )
				{
				$scope.is_active_select=false;
				}
		
			 $scope.is_active=false;
		 for(var i=0;i<$scope.userSelect.roles.length;i++)
		  {
		  $scope.itemsData.push($scope.userSelect.roles[i]);
		  }
		 //remove the ones that were moved from the source container.
		  
		  for(var i=0;i<$scope.userSelect.roles.length;i++)
			  {
			  for(var j=0;j<$scope.selectedItemList.length;j++)
				  {
			 if( $scope.userSelect.roles[i].id==$scope.selectedItemList[j].id)
				 {
				 $scope.selectedItemList.splice(j, 1);
				 }
			  }
			  }
		  
		
			 }
		 $scope.searchValue();
		 $scope.userSelect.roles=[];
			
	 }
	
 
	 
	 
		$scope.stockId = [];
		$scope.hideit=true;
		/*set_sub_menu("#report");						
		setMenuSelected("#currentstock_left_menu");	*/
		
		var vm = this;
		
		$("#msg").hide();
		

		 
		 $scope.filterItemData = [];
		 
		$scope.dataFetch=function()
		{
			 
			
			
	      $scope.searchValue();
			
		}
		
		$scope.searchValue=function()
		{
			$scope.filterItemData = [];
		if($scope.serch!="")
		  {
		    var searchData = eval("/^" + $scope.serch + "/gi");

		    $.each($scope.itemsData , function (i, v) {
		        if (v.name.search(new RegExp(searchData)) != -1) {
		        	
		        		$scope.filterItemData.push(v);
		        
		        }
		    });

		  
		  }
		}
		
		
		

		 $scope.unselectstockItem=function(){
				if($scope.filterItemData.length!=$scope.user.roles.length)
					{
					$scope.is_active=false;
					}
				else
					{
					$scope.is_active=true;
					}
			}
			
			
		 $scope.unselectstockItemSelect=function(){
			 if($scope.selectedItemList.length!=$scope.userSelect.roles.length)
				{
				$scope.is_active_select=false;
				}
			else
				{
				$scope.is_active_select=true;
				}	
			}
			
		
	
		$scope.hideit=true;
	

	 
	
//checkbox list end 

	$scope.elementcode=function(codevalue){ 
		//console.log($scope.stockListDtlList);
		$scope.stkcode;

		for(var j=0; j < $scope.stockListDtlList.length; j++){
			if($scope.stockListDtlList[j].id==codevalue){
				$scope.stkcode = $scope.stockListDtlList[j].code;
			} 
		}
		return 	$scope.stkcode;
	}

	$(document).on('keyup','#form_div_item_id .acontainer input', function(event) {
		if(event.which != 9 && event.which != 13){
			$scope.$apply(function(){
				//$('#form_div_item_id #itemid,#itemname').val('');
				$scope.formData.itemid='';
				$scope.formData.itemname='';
			});

		}
	});



	 
  
	$(document).on('keyup','#form_div_supplier_code1 input',function(e){
		
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$('#supplier_code1').val("");
				$scope.formData.supplier_id1 =  "";
				$scope.formData.supplier_code1 =  "";
				$scope.formData.supplier_name1 = "";});}});

//	autocompelete suplier data  
	var supplierData = $("#supplier_code1").tautocomplete({
		columns: ['id','code','name'],
		hide: [false,true,true],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay : 10,
		onchange: function () {
			var selected_supplier_data =  supplierData.all();
			$scope.$apply(function(){
				$scope.formData.supplier_id1 =  selected_supplier_data.id;
				$scope.formData.supplier_code1 =  selected_supplier_data.code;
				$scope.formData.supplier_name1 =  selected_supplier_data.name;
			});},
			data: function () {
				var data = $scope.supplier_data1;
				var filterData = [];
				var searchData = eval("/^" + supplierData.searchdata() + "/gi");
				$.each(data, function (i, v) {
					if (v.code.search(new RegExp(searchData)) != -1 || v.name.search(new RegExp(searchData)) != -1) {
						filterData.push(v);}});
				return filterData;}});
	
	
	$("#btn_finalize").click(function(){

		if($("#select_report").val() == "number:3"){


			$scope.stock_iem_Ids=[];
			if($scope.selectedItemList.length!=0)
			{
				for(var i=0;i<$scope.selectedItemList.length;i++ )
				{
					$scope.stock_iem_Ids.push($scope.selectedItemList[i].id);
				}
			}

			if(validation()){	
				if($scope.formData.supplier_id1==undefined || $scope.formData.supplier_id1=="" ){$scope.formData.supplier_id1=0;}
				if($scope.formData.stkStatus==undefined || $scope.formData.stkStatus=="" ){$scope.formData.stkStatus=-1;}
				if($scope.formData.itemid1==undefined || $scope.formData.itemid1=="" ){$scope.formData.itemid1=0;}

				var startdate = getMysqlFormat($scope.formData.postartdate);
				var enddate = getMysqlFormat($scope.formData.poenddate);     
				var option;
				if($("#optradioSum").prop('checked') == true){
					option=0;

				}
				if($("#optradioDetail").prop('checked') == true){
					option=1;

				}

				$window.open("../stockinreport/Stockin Report?option=" + option + "&stock_item_id=" + $scope.stock_iem_Ids 
						+ "&status=" + parseInt($scope.formData.stkStatus) + "&supplier_id=" + parseInt($scope.formData.supplier_id1) 
						+ "&startdate=" + startdate + "&enddate=" + enddate + "&pdfExcel=pdf", '_blank');

			}
		}else{
			$("#form_div_startdate_stkIn").removeClass("has-error");
			$("#form_div_startdate_error_stkIn").hide();
			$("#form_div_enddate_stkIn").removeClass("has-error");
			$("#form_div_enddate_error_stkIn").hide();
		}
	});
	
	$('#excelView').click(function(){

		if($("#select_report").val() == "number:3"){

			$scope.stock_iem_Ids=[];
			if($scope.selectedItemList.length!=0)
			{
				for(var i=0;i<$scope.selectedItemList.length;i++ )
				{
					$scope.stock_iem_Ids.push($scope.selectedItemList[i].id);
				}
			}

			if(validation()){	
				if($scope.formData.supplier_id1==undefined || $scope.formData.supplier_id1=="" ){$scope.formData.supplier_id1=0;}
				if($scope.formData.stkStatus==undefined || $scope.formData.stkStatus=="" ){$scope.formData.stkStatus=-1;}
				if($scope.formData.itemid1==undefined || $scope.formData.itemid1=="" ){$scope.formData.itemid1=0;}

				var startdate = getMysqlFormat($scope.formData.postartdate);
				var enddate = getMysqlFormat($scope.formData.poenddate);     
				var option;
				if($("#optradioSum").prop('checked') == true){
					option=0;

				}
				if($("#optradioDetail").prop('checked') == true){
					option=1;

				}

				$window.open("../stockinreport/Stockin Report?option=" + option + "&stock_item_id=" + $scope.stock_iem_Ids 
						+ "&status=" + parseInt($scope.formData.stkStatus) + "&supplier_id=" + parseInt($scope.formData.supplier_id1) 
						+ "&startdate=" + startdate + "&enddate=" + enddate + "&pdfExcel=excel", '_blank');

			}
		}else{
			$("#form_div_startdate_stkIn").removeClass("has-error");
			$("#form_div_startdate_error_stkIn").hide();
			$("#form_div_enddate_stkIn").removeClass("has-error");
			$("#form_div_enddate_error_stkIn").hide();
		}
	});
	
	 $("#form_div_radio_slctrprt input:radio").click(function() {
			$("#form_div_startdate_stkIn").removeClass("has-error");
			$("#form_div_startdate_error_stkIn").hide();
			$("#form_div_enddate_stkIn").removeClass("has-error");
			$("#form_div_enddate_error_stkIn").hide();
		 
		 $scope.$apply(function(){
				$scope.selectedItemList=[];
				$scope.serch="";
				$scope.filterItemData = [];
				angular.copy($scope.itemsData1,$scope.itemsData);
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
				
				$scope.formData.poenddate = dateForm(lastDay);
				$scope.formData.postartdate = dateForm(firstDay);
			}, 1);
		 $(".acontainer").find('input').val('');
		  $("#supplier_name1").val('');
	        $("#itemname1").val('');
	        $scope.formData.itemid1='';
			$scope.formData.itemname1='';
			$scope.formData.supplier_id1 =  "";
			$scope.formData.supplier_code1 =  "";
			$scope.formData.supplier_name1 = "";
		   });

	function validation(){
		var flg = true;

		if($scope.formData.postartdate == undefined || $scope.formData.postartdate == ""){
			$("#form_div_startdate_stkIn").addClass("has-error");
			$("#form_div_startdate_error_stkIn").show();
			flg = false;
		}else{
			$("#form_div_startdate_stkIn").removeClass("has-error");
			$("#form_div_startdate_error_stkIn").hide();
		}

		if($scope.formData.poenddate == undefined || $scope.formData.poenddate == ""){
			$("#form_div_enddate_stkIn").addClass("has-error");
			$("#form_div_enddate_error_stkIn").show();
			flg = false;
		}else{
			$("#form_div_enddate_stkIn").removeClass("has-error");
			$("#form_div_enddate_error_stkIn").hide();
		}

		if($scope.formData.poenddate != "" && $scope.formData.poenddate != undefined){
			var strtDate2=process($scope.formData.postartdate);
			var endDate2=process($scope.formData.poenddate);
			
			if(strtDate2 >endDate2){
				$("#divErrormsg1_stkIn").text('Startdate must be less than Enddate!');
				$("#divErrormsg1_stkIn").show();
				flg = false;
			}else{
				$("#divErrormsg1_stkIn").hide();
			}
		}

		return flg;
	}

	$scope.podateChange = function(){
		if($scope.formData.poenddate != "" && $scope.formData.poenddate != undefined){
			var strtDate2=process($scope.formData.postartdate);
			var endDate2=process($scope.formData.poenddate);
			
			if(strtDate2 >endDate2){
				$("#divErrormsg1_stkIn").text('Startdate must be less than Enddate!');
				$("#divErrormsg1_stkIn").show();
			}else{
				$("#divErrormsg1_stkIn").hide();
			}
		}

	}
	
	
	 $(document).on('keyup','#form_div_item_id1 .acontainer input', function(e) {
		 if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){

		 	$('#form_div_item_id1 #itemid1,#itemname1').val('');
		 	$scope.formData.itemid1="";
			$scope.formData.itemname1="";}
			
		 });

}

myApp.directive('tableAutocomplete1', [function() {
	return {

		controller: function ($scope,$http) {
			$scope.itemsData = [];

	/*		$http({
				url : '../itemmaster/json',
				method : "GET",
			}).then(function(itemresponse) {
				$scope.itemsData1 = itemresponse.data.data;
			}, function(itemresponse) { // optional

			});*/

			return $scope;
		},
		link: function($scope, elem, attrs ,controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns: ['id' , 'code', 'name'],
				hide: [false,true,true],
				placeholder: "search...",
				highlight: "hightlight-classname",
				norecord: "No Records Found",
				delay : 10,
				onchange: function () {
					var selected_item_data =  items.all();

					$scope.$apply(function(){

						$scope.formData.itemid1=selected_item_data.id;
						$scope.formData.itemname1=selected_item_data.name;

					});
				},
				data: function () {

					var strl_scope = controller;
					var data;

					data = strl_scope.itemsData1;

					var filterData = [];

					var searchData = eval("/^" + items.searchdata() + "/gi");

					$.each(data, function (i, v) {
						if ( v.name.search(new RegExp(searchData)) != -1) {
							if(v.is_active==1){
							filterData.push(v);}
						}
					});

					return filterData;
				}

			});

		}
	};
}]);

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
	    	
			controller.formData.poenddate =  dateForm(lastDay);

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a day
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
}]);

$("#optradioDetail").prop('checked',true);
angular.bootstrap(document.getElementById("stockin_report_app"), ['stockin_report_app']);