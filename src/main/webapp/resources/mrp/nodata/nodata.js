var noItemApp = angular.module('no_item_app', [ 'ngMaterial' , 'ngMessages' , 'common_app','checklist-model' ]);

no_item_app.controller('empty_item_ctrl', empty_item_ctrl);

function empty_item_ctrl($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window ,$filter) {
	
	$("#itemListsModal").show();
}
angular.bootstrap(document.getElementById("no_item_app"), ['no_item_app']);