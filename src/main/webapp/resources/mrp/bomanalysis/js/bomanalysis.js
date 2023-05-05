

//Controller for Table and Form 
mrpApp.controller('bomanalysisctrl', bomanalysisctrl);

function bomanalysisctrl($scope, $http, $mdDialog, $rootScope, $window, $controller,
		MRP_CONSTANT, FORM_MESSAGES, ITEM_TABLE_MESSAGES, RECORD_STATUS) {

	$controller('DatatableController', {$scope : $scope});

	//for calender
	var datetd = new Date();
	var curr_date1 = datetd.getDate();
	var curr_month1 = datetd.getMonth() + 1; //Months are zero based
	var curr_year1 = datetd.getFullYear();
	var todaydate = curr_year1 + "-" + curr_month1 + "-" + curr_date1;

	$scope.closing_date = geteditDateFormat(todaydate);
	$scope.highlightDays = [];
	$scope.totalorderList = [];
	$scope.getBomData = function()
	{
		$http({
			url : 'getBomData',
			async : false,
			method : "GET",
		}).then(function(response) {

			$scope.highlightDays=response.data.alreadyaddorder
		});
	}
	
	$http({
		url : 'dropdownprocessBom',
		async : false,
		method : "GET",
	}).then(function(response) {

		$scope.departments = response.data.departments;
		$scope.department_inBom = strings['isDefDepartment'];
	});

	$scope.getBomData();
	var clickedDate;
	$scope.oneDaySelectionOnly = function () {

		var fromDate = getMysqlFormat($scope.startDate);
		var toDate = getMysqlFormat($scope.endDate);
		var departmentId = $scope.department_inBom;

		if(fromDate <= toDate){
			$http({
				url : 'getItemData',
				method : "GET",
				params : {
					startDate : getMysqlFormat($scope.startDate),
					endDate : getMysqlFormat($scope.endDate),
					deptId : departmentId,
				},

			}).then(function(response) {

				$scope.itemsList1 = response.data.itemData;

				if( typeof $scope.itemsList1 != undefined){

					$scope.stockregData=false;
					for (var i = 0; i < $scope.itemsList1.length; i++) {

						$scope.itemsList1[i].opening_stock = parseFloat($scope.itemsList1[i].opening_stock)
						.toFixed(settings['decimalPlace']);
						$scope.itemsList1[i].stock_in = parseFloat($scope.itemsList1[i].stock_in)
						.toFixed(settings['decimalPlace']);
						$scope.itemsList1[i].total = parseFloat($scope.itemsList1[i].total)
						.toFixed(settings['decimalPlace']);
						$scope.itemsList1[i].bom_consumption=parseFloat($scope.itemsList1[i].bom_consumption)
						.toFixed(settings['decimalPlace']);
						$scope.itemsList1[i].closing_stock=parseFloat($scope.itemsList1[i].closing_stock)
						.toFixed(settings['decimalPlace']);
						$scope.itemsList1[i].actual_stock=parseFloat($scope.itemsList1[i].actual_stock)
						.toFixed(settings['decimalPlace']);

						$scope.itemsList1[i].remarks=$scope.itemsList1[i].remarks;
						//$scope.itemsList1[i].difference=parseFloat($scope.itemsList1[i].difference).toFixed(settings['decimalPlace']);
						if ($scope.itemsList1[i].adjust == 1) {

							$scope.itemsList1[i].adjust = true;
							$scope.stockregData=true;
						} else {
							$scope.itemsList1[i].adjust = false;
						}
					}
					$scope.itemsList = $scope.itemsList1;	
				}		
			});
		}else{
			$scope.itemsList = [];
			$rootScope.$broadcast('on_AlertMessage_ERR',"Please enter valid date range");
		}
	};


	//calender end
	$scope.itemsList = [];
	var date4 = new Date();
	$scope.startDate = dateForm(date4);
	$scope.endDate = dateForm(date4);
	$scope.newdep = "";
	var vm = this;
	vm.changeItemList = changeItemList;
	changeItemList();

	/* calender */	
	$rootScope.$on('checkvalidData', function(event,clickdate) {
		$scope.closing_date = geteditDateFormat(clickdate);
		$scope.checkDateValidity($scope.closing_date);		

	});

	

	$scope.showbtn = function() {

		var value=false;
		if($scope.itemsList.length == 0)
			value = false;
		else
			value =! $scope.stockregData
		return value;
	}	

	function changeItemList() {

		var fromDate = getMysqlFormat($scope.startDate);
		var toDate = getMysqlFormat($scope.endDate);
		var departmentId = $scope.department_inBom;

		if(fromDate <= toDate){

			$http({
				url : 'getItemData',
				method : "GET",
				params : {
					startDate : getMysqlFormat($scope.startDate),
					endDate : getMysqlFormat($scope.endDate),
					deptId : departmentId,
				},
			}).then(function(response) {

				$scope.itemsList1 = response.data.itemData;

				if( typeof $scope.itemsList1 != undefined){			

					$scope.stockregData = false;
					for (var i = 0; i < $scope.itemsList1.length; i++) {

						$scope.itemsList1[i].opening_stock = parseFloat($scope.itemsList1[i].opening_stock)
						.toFixed(settings['decimalPlace']);
						$scope.itemsList1[i].stock_in=parseFloat($scope.itemsList1[i].stock_in)
						.toFixed(settings['decimalPlace']);
						$scope.itemsList1[i].total=parseFloat($scope.itemsList1[i].total)
						.toFixed(settings['decimalPlace']);
						$scope.itemsList1[i].bom_consumption=parseFloat($scope.itemsList1[i].bom_consumption)
						.toFixed(settings['decimalPlace']);
						$scope.itemsList1[i].closing_stock=parseFloat($scope.itemsList1[i].closing_stock)
						.toFixed(settings['decimalPlace']);
						$scope.itemsList1[i].actual_stock=parseFloat($scope.itemsList1[i].actual_stock)
						.toFixed(settings['decimalPlace']);

						$scope.itemsList1[i].remarks=$scope.itemsList1[i].remarks;
						if ($scope.itemsList1[i].adjust == 1) {
							$scope.itemsList1[i].adjust = true;
							$scope.stockregData=true;
						} else {
							$scope.itemsList1[i].adjust = false;

						}
					}
					$scope.itemsList = $scope.itemsList1;
				}						
				//$scope.selectedDate = "";
			});
		}else{
			$scope.itemsList = [];
			$rootScope.$broadcast('on_AlertMessage_ERR',"Please enter valid date range");
		}
	}

	$scope.bomConsumptionDetails = function(itemlst)
	{
		$scope.itmName = itemlst.stock_item_name;
		$scope.totalBom = parseFloat(itemlst.bom_consumption).toFixed(settings['decimalPlace']);

		$http({
			url : 'getBomConsumption',
			method : "GET",
			params : {
				startDate : getMysqlFormat($scope.startDate),
				endDate : getMysqlFormat($scope.endDate),
				department_id:itemlst.department_id,
				stock_item_id:itemlst.stock_item_id
			},

		}).then(function(response) {
			$scope.bomData = response.data.bomData;
			$scope.bomStatusData = response.data.bomList;
			
			
			 for(var i = 0; i < $scope.bomStatusData.length; i++){
     
   				// Last i elements are already in place  
   				for(var j = 0; j < ( $scope.bomStatusData.length - i -1 ); j++){
       
    				// Checking if the item at present iteration 
     				// is greater than the next iteration
     				if($scope.bomStatusData[j].id > $scope.bomStatusData[j+1].id){
         
       					// If the condition is true then swap them
       					var temp = $scope.bomStatusData[j]
       					$scope.bomStatusData[j] = $scope.bomStatusData[j + 1]
       					$scope.bomStatusData[j+1] = temp
     				}
   				}
 			}
			
			
			console.log($scope.bomStatusData);
			
			var count=0;
			/*for(var i = 0; i < $scope.bomStatusData.length; i++){
				for(var j=i+1;j<$scope.bomStatusData.length;j++){
						if($scope.bomStatusData[i].stock_item_id==$scope.bomStatusData[j].stock_item_id){
							count++;
						}
				}
				
				
				if($scope.bomStatusData.length - count == $scope.bomData.length){
					break;
				}
			}*/
				for (var i = 0; i < $scope.bomStatusData.length; i++) {
					if($scope.bomStatusData.length!= $scope.bomData.length){
				
						for(j=$scope.bomStatusData.length-1;j>0;j--){
							if($scope.bomStatusData[i].stock_item_id== $scope.bomStatusData[j].stock_item_id && (i!=j && j>i)){
								$scope.bomStatusData.splice(i, 1)
							}
							//else{
								//$scope.bomStatusData.splice(j, 1)
							//}
							if($scope.bomStatusData.length== $scope.bomData.length){
								break;
							}
						}
					}
					
				}

				
				/*for(var i=0;i<$scope.bomStatusData.length;i++){
					i=0;
					if($scope.bomStatusData.length!= $scope.bomData.length){
						$scope.bomStatusData.splice(i, 1);
					}
					else{
						break;
					}			
				}
				*/
				
				for(var i=0;i<$scope.bomStatusData.length;i++){
					//i=0;
					if($scope.bomStatusData.length!= $scope.bomData.length){
						for(var j=1;j<$scope.bomStatusData.length;j++){
							if($scope.bomStatusData[i].stock_item_id==$scope.bomStatusData[j].stock_item_id){
								$scope.bomStatusData.splice(i, 1);
							}
						    /*else{
						    	break;
						    }*/
						}
					}
				}
				
			
			
			
			
			/*for (var i = 0; i < $scope.bomData.length; i++) {
				if($scope.bomData.length!=$scope.bomStatusData.length){
					for (var i = 0; i < $scope.bomStatusData.length; i++){
						for(var j=i+1;j<$scope.bomStatusData.length;j++){
							if($scope.bomStatusData[i].stock_item_id==$scope.bomStatusData[j].stock_item_id){
								if($scope.bomStatusData[i].id>$scope.bomStatusData[j].id){
									$scope.bomStatusData.splice(j, 1);
								}
							
							}
						}
					}
					
				}
			}
			*/
			if($scope.bomStatusData.length!=0){
				for (var i = 0; i < $scope.bomData.length; i++) {
					for(var j=0;j<$scope.bomData.length;j++){
						if($scope.bomData[i].stock_item_id==$scope.bomStatusData[j].stock_item_id){
							angular.merge($scope.bomData[i],$scope.bomStatusData[j]);
						}
					}
				
				}
			}
			else{
				
			}
			
			
			console.log($scope.bomData);
			
			
			for (var i = 0; i < $scope.bomData.length; i++) {
				$scope.bomData[i].prod_qty = parseFloat($scope.bomData[i].prod_qty).toFixed(settings['decimalPlace']);
				$scope.bomData[i].bom_quantity = parseFloat($scope.bomData[i].bom_quantity).toFixed(settings['decimalPlace']);
			}
			
			
			
			
			$('#orderDataSplit').modal('toggle');
		});
	}


	$scope.tdInput = "";
	$scope.trIndex = "";
	$scope.bomAdjustmentReamrks=function( remarks)
	{
		$('#itemsRemarks').modal('toggle');
		$scope.trIndex = $(event.currentTarget).closest('tr').index();
		$scope.remarksPoUp = remarks;
	}

	$scope.copyRemarksToHidanTxtBox=function($event)
	{
		var changedRemark = $(event.currentTarget).val();
		$scope.itemsList[$scope.trIndex].remarks = changedRemark;
	}	

	//print stockin
	$("#pdfView").click(function(){	

		$window.open("report?pdfExcel=pdf&reportName=BomAnalysis&startDate="+  getMysqlFormat($scope.startDate) +
				"&endDate="+  getMysqlFormat($scope.endDate) +"&department_id="+ $scope.department_inBom +"", '_blank');		 
	});
	
	$("#excelView").click(function(){	

		$window.open("report?pdfExcel=excel&reportName=BomAnalysis&startDate="+  getMysqlFormat($scope.startDate) +
				"&endDate="+  getMysqlFormat($scope.endDate) +"&department_id="+ $scope.department_inBom +"", '_blank');		 
	});
	
}
