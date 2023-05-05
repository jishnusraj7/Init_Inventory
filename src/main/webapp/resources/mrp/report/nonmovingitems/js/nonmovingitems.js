var myApp = angular.module('non_moving_report_app', [ 'ngMaterial' , 'ngMessages' , 'common_app' ]);

myApp.controller('non_moving_report_ctrlr', non_moving_report_ctrlr);

function non_moving_report_ctrlr($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window ) {
	
	$("#showMsg").hide();
	$scope.formData={days:0,item_category_id:0};
	
	$("#select_report").change(function(){
		if($("#select_report").val() == "number:5"){
			$("#showMsg").hide();
			$("#non_moving_duration").val('');  
			$("#daysInput").focus();
			$scope.formData.item_category_id=0;
			$scope.formData.days=0;
			 $('#form_div_duration').removeClass("has-error");
			$('#daysInput input').focus();
		}
	});
	
	$("#btn_finalize").click(function(){	
	
		if($("#select_report").val() == "number:5"){
		var date=new Date();
		var startdate ="";
		var enddate = "";
		
		enddate=formatDate(date);
		
		date.setDate(date.getDate()-($scope.formData.days-1));
		startdate=formatDate(date);
		
 		for(var i=0; i<$scope.listItemCtgry.length;i++){
	    	if($scope.listItemCtgry[i].id == $scope.formData.item_category_id )
	    		{
	    			$scope.categoryName=$scope.listItemCtgry[i].name;
	    			break;
	    		}
	    	
	    }
 		if($scope.formData.item_category_id == undefined || $scope.formData.item_category_id == 0)
 			{
 			$scope.formData.item_category_id=0;
 			$scope.categoryName="";
 			}
 		if($scope.formData.days != 0 && $scope.formData.days != undefined)
 			{
			  $window.open("../nonmovingitem/NonMoving Report?startdate=" + startdate + "&enddate=" + enddate + "&days=" 
					  + $scope.formData.days + "&item_category_id=" + $scope.formData.item_category_id + "&categoryName=" 
					  + $scope.categoryName + "&pdfExcel=pdf", '_blank');
			  $("#showMsg").hide();
			  $('#form_div_duration').removeClass("has-error");
			  $scope.formData.item_category_id=0;
				  
 			}
 		else 
 			{
 			 $("#showMsg").text('Please Enter The Days.');
		     $("#showMsg").show();
		     $("#daysInput input").focus();
		     $('#form_div_duration').addClass("has-error");
 			}
 		
	}});
	
	$('#excelView').click(function(){	
	
		if($("#select_report").val() == "number:5"){
		var date=new Date();
		var startdate ="";
		var enddate = "";
		
		enddate=formatDate(date);
		
		date.setDate(date.getDate()-($scope.formData.days-1));
		startdate=formatDate(date);
		
 		for(var i=0; i<$scope.listItemCtgry.length;i++){
	    	if($scope.listItemCtgry[i].id == $scope.formData.item_category_id )
	    		{
	    			$scope.categoryName=$scope.listItemCtgry[i].name;
	    			break;
	    		}
	    	
	    }
 		if($scope.formData.item_category_id == undefined || $scope.formData.item_category_id == 0)
 			{
 			$scope.formData.item_category_id=0;
 			$scope.categoryName="";
 			}
 		if($scope.formData.days != 0 && $scope.formData.days != undefined)
 			{
			  $window.open("../nonmovingitem/NonMoving Report?startdate=" + startdate + "&enddate=" + enddate + "&days=" 
					  + $scope.formData.days + "&item_category_id=" + $scope.formData.item_category_id + "&categoryName=" 
					  + $scope.categoryName + "&pdfExcel=excel", '_blank');
			  $("#showMsg").hide();
			  $('#form_div_duration').removeClass("has-error");
			  $scope.formData.item_category_id=0;
				  
 			}
 		else 
 			{
 			 $("#showMsg").text('Please Enter The Days.');
		     $("#showMsg").show();
		     $("#daysInput input").focus();
		     $('#form_div_duration').addClass("has-error");
 			}
 		
	}
		
		
	});
	
	function formatDate(date) {
	    var d = new Date(date),
	        month = '' + (d.getMonth() + 1),
	        day = '' + d.getDate(),
	        year = d.getFullYear();

	    if (month.length < 2) month = '0' + month;
	    if (day.length < 2) day = '0' + day;

	    return [year, month, day].join('-');
	}
	
	$scope.listItemCtgry =[];
    $http({
     method: 'GET',
     url : "../itemcategory/json",
     data: { applicationId: 1 }
 }).success(function (result) {
 	
 	for(var i=0; i<result.data.length;i++){
	    	$scope.listItemCtgry.push(result.data[i]);
	    	
	    }
 	
});
	
	
}


/*myApp.directive('numbersOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^0-9]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }            
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
});*/

angular.bootstrap(document.getElementById("app5"), ['non_moving_report_app']);
