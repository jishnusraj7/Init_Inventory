var myApp = angular.module('master_import_app', [ 'datatables', 'ngMaterial', "common_app"]);

// Controller for Buttons 
myApp.controller('btn_ctrl', function($scope,$timeout,$rootScope,$http,$window) {
	set_sub_menu("#settings");						
	setMenuSelected("#master_import_left_menu");			//active leftmenu
	
	
	$rootScope.$emit('importData', function(event, args) {});
	
	$scope.import = function(event){
		
		$rootScope.$emit("importData");
		
	}
	
	
	
});

//Controller for Table and Form 
myApp.controller('master_import', master_import);
function master_import($compile,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window ) {
	var nwImp = this;
	$scope.disableButtons=false;
	
	
	nwImp.dtInstance1 = {};
	nwImp.dtInstance2 = {};
	var urlString1="getUpdatedDataToImport?module=item";
	var urlString2="getNewDataToImport?module=item";

	nwImp.dtOptions1 = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		$http.get(urlString1).then(function(result) {
			defer.resolve(result.data);
		});
		return defer.promise;
	}).withPaginationType('full_numbers').withOption('rowCallback', rowCallback);
	nwImp.dtColumns1 = [DTColumnBuilder.newColumn("code","Code"),
	                DTColumnBuilder.newColumn("name","Name")];
	
	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		if(aData.is_deleted == 1)
		{$(nRow).css('background-color','#ea7373');}		
		return nRow;
	}

	nwImp.dtOptions2 = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		$http.get(urlString2).then(function(result) {
			defer.resolve(result.data);
		});
		return defer.promise;
	}).withPaginationType('full_numbers');
	
	nwImp.dtColumns2 = [DTColumnBuilder.newColumn("code","Code"),
	                DTColumnBuilder.newColumn("name","Name")];
	
	$scope.loadImportDetails = function(str){

		urlString2="getNewDataToImport?module="+str;
		urlString1="getUpdatedDataToImport?module="+str;		
		nwImp.dtInstance1.reloadData();	
		nwImp.dtInstance2.reloadData();	
	}
	
	$scope.getSelected = function(event)
	{
		$(".list_btn").click(function(){
			var id = $(this).attr("id");
			$(".list_btn").removeClass("active");
			$("#"+id).addClass("active");

		});

	};
	
	  
	$rootScope.$on("importData", function(event){
		$scope.disableButtons=true;
		$http({
			url : 'import',
			method : "POST"
		}).then(function(response) {
			$mdDialog.show($mdDialog.alert()
                    .parent(angular.element(document.querySelector('#dialogContainer')))
                    .clickOutsideToClose(true)
                    .textContent(response.data)
                    .ok('Ok!')
                    .targetEvent(event)
              );
			nwImp.dtInstance1.reloadData();	
			nwImp.dtInstance2.reloadData();	
			$scope.disableButtons=false;
		}, function(response) { 
			$mdDialog.show($mdDialog.alert()
                    .parent(angular.element(document.querySelector('#dialogContainer')))
                    .clickOutsideToClose(true)
                    .textContent("some error occured!")
                    .ok('Ok!')
                    .targetEvent(event)
              );
			$scope.disableButtons=false;
		});
	});
	
}

angular.bootstrap(document.getElementById("masterImportApp"), ['master_import_app']);
