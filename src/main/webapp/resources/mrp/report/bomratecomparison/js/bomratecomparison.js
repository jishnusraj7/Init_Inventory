//Controller for Table and Form 

mrpApp.controller('bomratecomparisonCntrlr', bomratecomparisonCntrlr);

function bomratecomparisonCntrlr($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window ) {
	
	$scope.formData = {};
	$scope.option=1;
	$scope.stockData = [];
	set_sub_menu("#report");						
	setMenuSelected("#productionreport_left_menu");		
	
	/*$("#exportpdf").click(function(){
		if($scope.startdate!=undefined){
		$window.open("report?&option="+$scope.option+"&startdate="+getMysqlFormat($scope.startdate)+"&enddate="+getMysqlFormat($scope.enddate)+"", '_blank');
		}
	
	});*/
	
	//to fill itemcategory dropdown 
	
	
	$scope.sampleItemCtgry =[{id : 0 ,name : "select" }];
    $http({
     method: 'GET',
     url : "../itemcategory/json",
     data: { applicationId: 1 }
 }).success(function (result) {
		for(var i=0; i<result.data.length;i++){
  	    	$scope.sampleItemCtgry.push(result.data[i]);
  	    	
  	    }
 	
 	
});
	
	$('#select_prodreport').click(function(){
		if($('#select_prodreport').val()=="number:6")
		{
			
			$("#bom_rate_comparison_div").show();
			$('#prod_bom_report_div').hide();
			$('#prod_report_div').hide();
			$("#profit_summary_report_div").hide();
			$("#bom_analysis_report_div").hide();
			$("#prod_bal_report_div").hide();
			$("#stockadjustment_report_div").hide();
		
			$scope.formData.item_category_id = $scope.sampleItemCtgry[0].id;
			//$("#item_category_id").val("0");
			$scope.filterItemData = [];
			
			$("#msg").hide();
			    
			$scope.$apply(function(){
				$scope.selectedItemList=[];
				$scope.filterItemData = [];
				//angular.copy($scope.itemsData1,$scope.itemsData);
				angular.copy($scope.itemsData);
				$scope.serch="";
				$scope.user.roles=[];
				$scope.userSelect.roles = [];
				$scope.is_active=false;
				$scope.is_active_select=false;
				});
			
		}
		
	});
	

	//stock item list box

	/*$scope.filterStkitm = function() {
		$scope.stkfilterData = $scope.stockData;

		if ($scope.formData.item_category_id != "") {
			$scope.stkfilterData = [];

			for (var i = 0; i < $scope.stockData.length; i++) {
				if ($scope.stockData[i].item_category_id == $scope.formData.item_category_id) {
					$scope.stkfilterData.push($scope.stockData[i]);

				}
			}
		}
	}*/
	
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
	
	//checkboxlist
    
		$scope.itemsData=[];
		
		$http({
			 url : '../itemmaster/json',
				method : "GET",
			}).then(function(itemresponse) {
				
				$scope.itemsData = itemresponse.data.data;
			
			}, function(itemresponse) { // optional
		
			});
		
		 //$scope.itemsData1=[];
		$http({
			url : '../stockin/formJsonData',
			method : "GET",
		}).then(function(response) {
			$scope.department_data = response.data.depData;

		/*	$scope.itemsData1 = response.data.stockItmData;
			angular.copy($scope.itemsData1, $scope.itemsData);
			$scope.stockData = $scope.itemsData1;
			$scope.stkfilterData = $scope.stockData;*/
			var department1 = $scope.department_data;
			/*department1.unshift({
				id : 0,
				name : "select"
			});*/
			$scope.sampleDepartment = department1;
		}, function(response) { // optional
		});
		
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
	
	
	$scope.clearErr=function()
	{
		
		
	}
	
	$("#exportpdf").click(function(){
		  $("#msg").hide();
		
		  if($('#select_prodreport').val()=="number:6"){
			  
		  	
			$scope.stock_item_Ids=[];
			if($scope.selectedItemList.length!=0)
			{
		for(var i=0;i<$scope.selectedItemList.length;i++ )
			{
			$scope.stock_item_Ids.push($scope.selectedItemList[i].id);
			}
			}
			
			var itemCategory= $scope.formData.item_category_id;
			var stkItem= $scope.stock_item_Ids;
			
			//alert(itemCategory +","+stkItem);
					
			$window.open("../bomratecomparison/BomRateComparisonReport?&itemcategory="+itemCategory+"&stockitem="+stkItem+""+"&pdfExcel=pdf", '_blank');

		  }
				
		});
	
	
	
	$("#excelView").click(function(){
		  $("#msg").hide();
		
		  if($('#select_prodreport').val()=="number:6"){
			  
		  	
			$scope.stock_item_Ids=[];
			if($scope.selectedItemList.length!=0)
			{
		for(var i=0;i<$scope.selectedItemList.length;i++ )
			{
			$scope.stock_item_Ids.push($scope.selectedItemList[i].id);
			}
			}
			
			var itemCategory= $scope.formData.item_category_id;
			var stkItem= $scope.stock_item_Ids;
			
			//alert(itemCategory +","+stkItem);
					
			$window.open("../bomratecomparison/BomRateComparisonReport?&itemcategory="+itemCategory+"&stockitem="+stkItem+""+"&pdfExcel=excel", '_blank');

		  }
				
		});
	
	
	function validation(){
	/*	var flg = true;

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
		

		return flg;*/
	}

	$(function() {
	
		
		
	});

	
}

