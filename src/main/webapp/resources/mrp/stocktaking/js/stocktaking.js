mrpApp.controller('stocktaking',stocktaking);
var getValue;
function stocktaking($compile, $controller, $scope, $interval, $timeout, $http, $mdDialog, $rootScope, DTOptionsBuilder, 
		DTColumnBuilder, MRP_CONSTANT, DATATABLE_CONSTANT, STATUS_BTN_TEXT, $q, RECORD_STATUS, $window, FORM_MESSAGES, 
		ITEM_TABLE_MESSAGES){

	setMenuSelected("#stocktaking_left_menu");
	$scope.prograssing = true;
	//$scope.show_table = true;
	$scope.show_form = false;
	$scope.saveMessage = false;
	$scope.isDisabled = true;
	$scope.prod_date = dateForm(new Date())
	$scope.item_category_id = 0;
	
	$scope.departments=[];
	$http({
		url:'department',
		method:'GET'
	}).then(function(response){
	
		$scope.departments=response.data.departments;
		$scope.department_id = strings['isDefDepartment'];;
		$scope.getData();
	});		
	
	// Done by anandu on 22-01-2020
	$scope.item_category=[];
	$http({
		url:'item_category',
		method:'GET'
	}).then(function(response){
		$scope.item_category = response.data.itemCategory;	
	//	$scope.getData();
	});		
	
	// Done by anandu on 22-01-2020
	$("#cat_id").on('change',function(){
	    var getValue=$(this).val();
	    $scope.item_category_id = getValue;
	    $scope.getData();
	  });
	
	
	// Done by anandu on 22-01-2020
	$scope.getData =function(){
		$scope.prograssing = true;
		$http({

			url:'stockTaking',
			async:false,
			method:'GET',
			params:{selDate:getMysqlFormat($scope.prod_date),department_id : $scope.department_id,item_category_id: $scope.item_category_id}

		}).then(function(response){
			
			$scope.stock_details = response.data.stockItems;		
			console.log($scope.stock_details);
			for(i=0;i<$scope.stock_details.length;i++){
				$scope.stock_details[i].current_stock=parseFloat($scope.stock_details[i].current_stock).toFixed(settings['decimalPlace']);
				if($scope.stock_details[i].actual_stock==0){
					$scope.stock_details[i].actual_stock="";
				}
			}
			$scope.focusRow ("");
			$scope.prograssing = false;
		},
		function(response){
			$scope.prograssing = false;
		});
	}
	
	$scope.EnableDisable=function(){

		var numbReg="/^[A-Za-z]+$/";
		//var textBox = $.trim( $('.actual_stock').val());
		$scope.isDisabled=true;
		textboxes = $('#items_table').find('.actual_stock');	
		
		textboxes.each(function() {
			var textBox=$(this).val();  
			if(textBox!=numbReg && textBox!=""){
				$scope.isDisabled=false;

			}

		});

	}

	/*$scope.EnableDisable=function(){

		var charReg="/^[A-Za-z]+$/";          // values in all textbox is entered then only enable button
		var numReg="/^\d+$/.";
		textboxes = $('#items_table').find('.actual_stock');
		textboxes.each(function() {
			var textBox=$(this).val();  

		});
		if(textBox==charReg || textBox==""){
			$scope.isDisabled=true;
		}else {
			$scope.isDisabled=false;
		}

	}*/
	
	$scope.updateStock=function(){

		
		
		var confirm = $mdDialog.confirm(
				{
					onComplete : function afterShowAnimation() {
						var $dialog = angular.element(document
								.querySelector('md-dialog'));
						var $actionsSection = $dialog
						.find('md-dialog-actions');
						var $cancelButton = $actionsSection
						.children()[0];
						var $confirmButton = $actionsSection
						.children()[1];
						angular.element($confirmButton)
						.removeClass('md-focused');
						angular.element($cancelButton)
						.addClass('md-focused');
						$cancelButton.focus();
					}
				}).title("Do You Want To Update these items?")
				.targetEvent(event).cancel('No').ok('Yes');
     
		//$scope.prograssing = false;
		$mdDialog
		.show(confirm)
		.then(function() {

			$scope.prograssing = true;
			var prodData={stock_data : $scope.stock_details,prod_date :getMysqlFormat($scope.prod_date),dept_id:$scope.department_id};

			$http({

				url:'updateStockItem',
				async:false,
				method:'POST',
				data:prodData,
			}).then(function(response){

				if(response.data==1){

					$('.actual_stock').val('');
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
					$scope.getData();
					$scope.prograssing = false;
				}

			}),
			function(response){
				$scope.prograssing = false;
			}

		});
		
	}
	

	
	
	$scope.differenceView = function(actual,curr) {
		var total = 0;

		var actual = (actual == "" || actual ==  "-" || actual == ".") ? 0 : parseFloat(actual).toFixed(settings['decimalPlace']);

		total=(actual)-(curr);

		return total.toFixed(settings['decimalPlace']);
	}
	
	var cursorFocus=function(elem){
		
		var scrollPointX=window.scrollX,scrollPointY=window.scrollY;
		elem.focus();
		 window.scrollTo(scrollPointX, scrollPointY);
	}
	
	$scope.focusRow = function(searchValue){
		var iterate = true;
		
		angular.forEach($scope.stock_details,function(value,index){
			if(iterate && searchValue !="" && value.name.toUpperCase().startsWith(searchValue.toUpperCase())){
				
				var element = $window.document.getElementById('search_data');
				textboxes = $('#items_table').find('.stock_item_name');
				var scroll=document.getElementsByClassName('stock_item_name')[index];
		        if(element)
	                element.focus();
		        scroll.scrollIntoView(index);
				$scope['tr_'+index]="selected"
				iterate = false;
			}else{
				$scope['tr_'+index]=""
			}
		});
            		
	}


}