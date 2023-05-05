var stockItemApp = angular.module('stock_item_app', [ 'ngMaterial',
		'ngMessages', 'common_app', 'checklist-model' ]);

stockItemApp.controller('stock_item_ctrl', stock_item_ctrl);

function stock_item_ctrl($scope, $http, $mdDialog, $rootScope,
		DTOptionsBuilder, DTColumnBuilder, $timeout, $q, $window, $filter) {

	$http({
		url : 'getDepartments',
		async : false,
		method : "GET",
	}).then(function(response) {
		$scope.departmentsList = response.data.departments;
		console.log($scope.departmentsList);
	});
	var itemsMastersData = [];
	$scope.retrieveItemsData = function() {
		$http({
			url : '../stockin/formJsonItemData',
			method : "GET",

		}).then(function(response) {

			itemsMastersData = response.data.stockItmDatastkin;
			console.log(itemsMastersData);
		}, function(response) { // optional

		});
	}

	var searchItemsData = $("#stock_item_data").tautocomplete(
			{
				columns : [ 'id', 'code', 'name' ],
				hide : [ false, true, true ],
				placeholder : "search..",
				highlight : "hightlight-classname",
				norecord : "No Records Found",
				delay : 10,
				onchange : function() {

					var selectedItems = searchItemsData.all();
					$scope.$apply(function() {

						$scope.getItemsData = $scope.itemsMastersData;
						$scope.stock_item_code = selectedItems.code;
						$scope.stock_item_name = selectedItems.name;
					});
				},
				data : function() {
					var data = itemsMastersData;
					var filterData = [];
					var searchData = eval("/.*" + searchItemsData.searchdata()
							+ "/gi");
					$.each(data, function(i, v) {
						if (v.name.search(new RegExp(searchData)) != -1) {
							filterData.push(v);
						}
					});

					return filterData;
				},

			});

	$("#itemLists").click(function() {
		$("#itemListsModal").modal('show');
		$("#itemListsModal .acontainer input").attr('disabled', false);
		$scope.itemsData = $scope.itemsMastersData;
		$scope.showTotal = false;
		$scope.retrieveItemsData();
	});

	$('#itemListsModal').on('shown.bs.modal', function(e) {
		$("#itemListsModal .acontainer input").focus();
	});

	$scope.cancelBtn = function() {

		$scope.stock_item_name = "";
		$scope.itemDataOnDepartment = [];
		$scope.showTotal = false;
		$("#form_stock_item_data").find(".acontainer input").val("");

	};

	$scope.currentStockTotal = function() {
		var stockTotal = 0.00;
		angular.forEach($scope.itemDataOnDepartment, function(item) {

			stockTotal += parseFloat(item.current_stock);
		})

		return parseFloat(stockTotal).toFixed(settings['decimalPlace']);
	}

	$scope.getItemData = function() {

		$http({

			url : '../stockin/getItemData',
			params : {
				itemCode : $scope.stock_item_code,
				itemName : $scope.stock_item_name
			},
			method : 'GET',
		})
				.then(
						function(response) {

							$scope.itemDataOnDepartment = response.data.itemDataOnDepartment;
							if ($scope.itemDataOnDepartment.length == 0) {
								$scope.showTotal = false;
							} else {
								$scope.showTotal = true;
							}
							for (i = 0; i < $scope.itemDataOnDepartment.length; i++) {
								$scope.itemDataOnDepartment[i].current_stock = parseFloat(
										$scope.itemDataOnDepartment[i].current_stock)
										.toFixed(settings['decimalPlace']);
							}
							$scope.prograssing = false;
						});
	}

}

angular.bootstrap(document.getElementById("stock_item_app"),
		[ 'stock_item_app' ]);