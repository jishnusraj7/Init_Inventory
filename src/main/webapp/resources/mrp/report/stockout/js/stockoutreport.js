var myApp = angular.module('stockout_report_app', [ 'ngMaterial' , 'ngMessages' , 'common_app' ,'checklist-model']);

myApp.controller('stockoutCtrlr', stockoutCtrlr);

function stockoutCtrlr($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window ) {
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
			//$scope.purchaseorderdtllist = response.data.purchaseorderdtllist;
			//$scope.supplier_data1 = response.data.supData;
//			$scope.itemsData = response.data.stockItmData;
			
			$scope.itemsData1 = response.data.stockItmData;
			angular.copy($scope.itemsData1, $scope.itemsData);	
			
			}, function(response) { // optional
			});


		var vm = this;
		
		
		
		
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
			    var searchData = eval("/.*" + $scope.serch + "/gi");

			    $.each($scope.itemsData , function (i, v) {
			        if (v.name.search(new RegExp(searchData)) != -1) {
			        	
			        		$scope.filterItemData.push(v);
			        
			        }
			    });

			  
			  }
			}
			
			
		
			$scope.hideit=true;
		 
			

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
				
			
			
			
			
			
			
			
			
			
			
		 
	//checkbox list end  

		 
		 
		
		
		
		$(document).on('keyup','#form_div_source_department_code input',function(e){
			
			if(e.which != 9 && e.which != 13){
				$scope.$apply(function(){
					$('#source_department_code').val("");
					$scope.formData.source_department_code=  "";
					$scope.formData.source_department_name =  "";
					$scope.formData.source_department_id = "";});}});



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
			if($("#select_report").val() == "number:4"){
			/*	$scope.formData.source_department_id =  '';	
				$scope.formData.source_department_code = '';
				$scope.formData.source_department_name =  '';*/
				//var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));
				$scope.formData.source_department_id = parseInt(strings['isDefDepartment']);
				$scope.formData.source_department_name = strings['isDefDepartmentname'];
				$scope.formData.source_department_code = strings['isDefDepartmentcode'];
				$("#source_department_code").val($scope.formData.source_department_code);
				$("#form_div_source_department_code").find(".acontainer input").val($scope.formData.source_department_code);
				$("#source_department_name").val($scope.formData.source_department_name);
			}
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
		
		
		function fun_get_dep_name(id){
			var depList =[];
			for(var i=0;i < $scope.department_data.length;i++){
				if($scope.department_data1[i].id == id){
					depList = $scope.department_data1[i];
				}
			}

			return depList;
		}

		
		
		
		
		//autocompelete source department 
		var sourceDepartmentData = $("#source_department_code").tautocomplete({
			columns: ['id','code','name'],
			hide: [false,true,true],
			placeholder: "search..",
			highlight: "hightlight-classname",
			norecord: "No Records Found",
			delay : 10,
			onchange: function () {
				var selected_department_data =  sourceDepartmentData.all();
				$scope.$apply(function(){
					$scope.formData.source_department_id =  selected_department_data.id;
					$scope.formData.source_department_code =  selected_department_data.code;
					$scope.formData.source_department_name =  selected_department_data.name;
				});},
				data: function () {
					var data = $scope.department_data;
					var filterData = [];
					var searchData = eval("/^" + sourceDepartmentData.searchdata() + "/gi");
					$.each(data, function (i, v) {
						if (v.name.search(new RegExp(searchData)) != -1) {
							filterData.push(v);}});
					return filterData;}});

		
$(document).on('keyup','#form_div_dest_department_code input',function(e){
			
			if(e.which != 9 && e.which != 13){
				$scope.$apply(function(){
					$('#dest_department_code').val("");
					$scope.formData.dest_department_code=  "";
					$scope.formData.dest_department_name =  "";
					$scope.formData.dest_department_id = "";});}});
	
		
		
		//autocompelete dest department 
		var destDepartmentData = $("#dest_department_code").tautocomplete({
			columns: ['id','code','name'],
			hide: [false,true,true],
			placeholder: "search..",
			highlight: "hightlight-classname",
			norecord: "No Records Found",
			delay : 10,
			onchange: function () {
				var selected_department_data =  destDepartmentData.all();
				$scope.$apply(function(){
					$scope.formData.dest_department_id =  selected_department_data.id;
					$scope.formData.dest_department_code =  selected_department_data.code;
					$scope.formData.dest_department_name =  selected_department_data.name;
				});},
				data: function () {
					var data = $scope.department_data;
					var filterData = [];
					var searchData = eval("/^" + destDepartmentData.searchdata() + "/gi");
					$.each(data, function (i, v) {
						if ( v.name.search(new RegExp(searchData)) != -1) {
							filterData.push(v);}});
					return filterData;
					
				}});
	
		

		$("#btn_finalize").click(function(){

			
			
			if($("#select_report").val() == "number:4"){
				
				
				$scope.stock_iem_Ids=[];
				if($scope.selectedItemList.length!=0)
				{
			for(var i=0;i<$scope.selectedItemList.length;i++ )
				{
				$scope.stock_iem_Ids.push($scope.selectedItemList[i].id);
				}
				}
				
				
				if(validation()){	
					if($scope.formData.itemid1==undefined || $scope.formData.itemid1=="" ){$scope.formData.itemid1=-1;}
					if($scope.formData.source_department_id==undefined || $scope.formData.source_department_id=="" ){$scope.formData.source_department_id=-1;}
					if($scope.formData.dest_department_id==undefined || $scope.formData.dest_department_id=="" ){$scope.formData.dest_department_id=-1;}
	
					
					
					var startdate = getMysqlFormat($scope.formData.Deliverystartdate);
					var enddate = getMysqlFormat($scope.formData.Deliveryenddate);  
					
					
					var option;
					if($("#optradioSumSTk").prop('checked') == true){
						option=0;
					}
					if($("#optradioDetailSTK").prop('checked') == true){
						option=1;
					}
					
					$window.open("../stockoutreport/Stockout Report?option="+option+"&stockItemId="+$scope.stock_iem_Ids+"&sourceDepartmentId="+parseInt($scope.formData.source_department_id)+"&destDepartmentId="+parseInt($scope.formData.dest_department_id)+"&startdate="+startdate+"&enddate="+enddate+"", '_blank');

					
					
					
				     }
			
				}
			});

		
		
		$("#excelView").click(function(){

			
			
			if($("#select_report").val() == "number:4"){
				
				
				$scope.stock_iem_Ids=[];
				if($scope.selectedItemList.length!=0)
				{
			for(var i=0;i<$scope.selectedItemList.length;i++ )
				{
				$scope.stock_iem_Ids.push($scope.selectedItemList[i].id);
				}
				}
				
				
				if(validation()){	
					if($scope.formData.itemid1==undefined || $scope.formData.itemid1=="" ){$scope.formData.itemid1=-1;}
					if($scope.formData.source_department_id==undefined || $scope.formData.source_department_id=="" ){$scope.formData.source_department_id=-1;}
					if($scope.formData.dest_department_id==undefined || $scope.formData.dest_department_id=="" ){$scope.formData.dest_department_id=-1;}
	
					
					
					var startdate = getMysqlFormat($scope.formData.Deliverystartdate);
					var enddate = getMysqlFormat($scope.formData.Deliveryenddate);  
					
					
					var option;
					if($("#optradioSumSTk").prop('checked') == true){
						option=0;
					}
					if($("#optradioDetailSTK").prop('checked') == true){
						option=1;
					}
					
					//$window.open("../stockoutreport/Stockout Report?option="+option+"&stockItemId="+$scope.stock_iem_Ids+"&sourceDepartmentId="+parseInt($scope.formData.source_department_id)+"&destDepartmentId="+parseInt($scope.formData.dest_department_id)+"&startdate="+startdate+"&enddate="+enddate+"", '_blank');

					 var link=document.createElement('a');
			    	    link.href="../stockoutreport/Stockout Report Excel?option="+option+"&stockItemId="+$scope.stock_iem_Ids+"&sourceDepartmentId="+parseInt($scope.formData.source_department_id)+"&destDepartmentId="+parseInt($scope.formData.dest_department_id)+"&startdate="+startdate+"&enddate="+enddate+"";

			    	    link.click();
					
					
				     }
			
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






myApp.directive('tableAutocomplete1', [function() {
	return {

		controller: function ($scope,$http) {
	/*		$scope.itemsData = [];

			$http({
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
$("#optradioDetailSTK").prop('checked',true);
angular.bootstrap(document.getElementById("stockout_report_app"), ['stockout_report_app']);