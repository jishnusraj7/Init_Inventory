/**
 * 
 */
mrpApp.controller('stocksummary', stocksummary);

function stocksummary($compile, $controller, $scope, $interval, $timeout,
	$http, $mdDialog, $rootScope, DTOptionsBuilder, DTColumnBuilder,
	MRP_CONSTANT, DATATABLE_CONSTANT, STATUS_BTN_TEXT, $q, RECORD_STATUS,
	$window, FORM_MESSAGES, ITEM_TABLE_MESSAGES) {

	$scope.category_id = 0;
	$scope.formData = [];
	var itemsMastersData = [];
	$scope.getStockSumaryTable = function() {
		$scope.prograssing = true;
		if (formValidaion()) {
			$http({

				url: 'summary',
				async: false,
				method: 'GET',
				params: {
					start_date: getMysqlFormat($scope.formData.start_date),
					end_date: getMysqlFormat($scope.formData.end_date),
					department_id: $scope.formData.department_id,
					category_id: $scope.formData.category_id
				},

			})
				.then(
					function(response) {

						$scope.summary_details = response.data.stockSummaryData;
						console.log($scope.summary_details);
						for (i = 0; i < $scope.summary_details.length; i++) {

							$scope.summary_details[i].opening_stock = parseFloat(
								$scope.summary_details[i].opening_stock)
								.toFixed(settings['decimalPlace']);
							$scope.summary_details[i].production = parseFloat(
								$scope.summary_details[i].production)
								.toFixed(settings['decimalPlace']);
							$scope.summary_details[i].stock_adjustment = parseFloat(
								$scope.summary_details[i].stock_adjustment)
								.toFixed(settings['decimalPlace']);
							$scope.summary_details[i].stock_disposal = parseFloat(
								$scope.summary_details[i].stock_disposal)
								.toFixed(settings['decimalPlace']);
							$scope.summary_details[i].stock_in = parseFloat(
								$scope.summary_details[i].stock_in)
								.toFixed(settings['decimalPlace']);
							$scope.summary_details[i].stock_out = parseFloat(
								$scope.summary_details[i].stock_out)
								.toFixed(settings['decimalPlace']);
							$scope.summary_details[i].stock_out_BOM = parseFloat(
								$scope.summary_details[i].stock_out_BOM)
								.toFixed(settings['decimalPlace']);
							$scope.summary_details[i].stock_transfer = parseFloat(
								$scope.summary_details[i].stock_transfer)
								.toFixed(settings['decimalPlace']);
							$scope.summary_details[i].closing_stock = parseFloat(
								$scope.summary_details[i].closing_stock)
								.toFixed(settings['decimalPlace']);
						}
						$scope.prograssing = false;
					}, function(response) {
						$scope.prograssing = false;
					});
		} else {
			$scope.prograssing = false;
		}
	}

	// Done by anandu on 25-01-2020



	$scope.setFormData = function() {
		$scope.category_id_list = [];
		$http({
			url: 'item_category',
			method: 'GET'
		}).then(function(response) {
			$scope.category_id_list = response.data.categoryData;
			//	$scope.formData.category_id = $scope.category_id;

		});

		$http({
			url: 'formJsonData',
			async: false,
			method: 'GET',

		}).then(function(response) {
			$scope.departments = response.data.departmentData;
		});
		$scope.category_id = $scope.category_id;
		$scope.formData.department_id = strings['isDefDepartment'];
		$scope.formData.end_date = dateForm(moment(new Date()).add(1, 'days'));
		$scope.formData.start_date = dateForm(new Date());
	}

	$scope.getValueData = function() {
		$("#category_id").on('change', function() {
			var getValue = $(this).val();
			$scope.category_id = getValue;
			//  $scope.getData();
		});
	}

	$http({
		url: '../stockin/formJsonDataStkin',
		method: "GET",

	}).then(function(response) {

		itemsMastersData = response.data.stockItmDatastkin;
	}, function(response) { // optional

	});

	$scope.setFormData();
	$scope.getStockSumaryTable();

	/*
	 * function printStockSummaryReport(){
	 * 
	 * if(formValidaion()){ if($scope.summary_details.length != 0){
	 * window.open("printSummary?pdf=1&exc=2&start_date=" +
	 * encodeURIComponent(getMysqlFormat($scope.formData.start_date)) +
	 * "&end_date=" +
	 * encodeURIComponent(getMysqlFormat($scope.formData.end_date))
	 * +"&department_id=" + encodeURIComponent($scope.formData.department_id)
	 * +"", '_blank'); } else $rootScope.$broadcast('on_AlertMessage_ERR',"No
	 * available data to print"); } }
	 */

	// $scope.printStockSummary = function(){
	$("#btn_finalize")
		.click(
			function() {

				if (formValidaion()) {
					if ($scope.summary_details.length != 0) {
						if ($scope.formData.category_id == null || $scope.formData.category_id == undefined) {
							$scope.formData.category_id = 0;
						}
						window
							.open(
								"printSummary?pdf=0&start_date="
								+ encodeURIComponent(getMysqlFormat($scope.formData.start_date))
								+ "&end_date="
								+ encodeURIComponent(getMysqlFormat($scope.formData.end_date))
								+ "&department_id="
								+ encodeURIComponent($scope.formData.department_id)
								+ "&category_id=" + encodeURIComponent($scope.formData.category_id) + "", '_blank');
					} else
						$rootScope.$broadcast('on_AlertMessage_ERR',
							"No available data to print");
				}

			});

	// }

	$("#excelView")
		.click(
			function() {

				if (formValidaion()) {
					if ($scope.summary_details.length != 0) {
						if ($scope.formData.category_id == null || $scope.formData.category_id == undefined) {
							$scope.formData.category_id = 0;
						}
						window
							.open(
								"printSummary?pdf=1&start_date="
								+ encodeURIComponent(getMysqlFormat($scope.formData.start_date))
								+ "&end_date="
								+ encodeURIComponent(getMysqlFormat($scope.formData.end_date))
								+ "&department_id="
								+ encodeURIComponent($scope.formData.department_id)
								+ "&category_id=" + encodeURIComponent($scope.formData.category_id) + "", '_blank');

						/*
						 * var link = document.createElement('a');
						 * link.href =
						 * "../stocksummary/printSummary?pdf=1&start_date=" +
						 * encodeURIComponent(getMysqlFormat($scope.formData.start_date)) +
						 * "&end_date=" +
						 * encodeURIComponent(getMysqlFormat($scope.formData.end_date)) +
						 * "&department_id=" +
						 * encodeURIComponent($scope.formData.department_id) +
						 * "";
						 * 
						 * link.click();
						 */
					} else
						$rootScope.$broadcast('on_AlertMessage_ERR',
							"No available data to print");
				}
			});

	function getCurrentStock(summary_row) {
		var currentStock = 0.0;
		var total_stock_in = parseFloat(summary_row.opening_stock)
			+ parseFloat(summary_row.stock_in)
			+ parseFloat(summary_row.production)
			+ parseFloat(summary_row.stock_adjustment);
		var total_stock_out = parseFloat(summary_row.stock_out)
			+ parseFloat(summary_row.stock_out_BOM)
			+ parseFloat(summary_row.stock_transfer)
			+ parseFloat(summary_row.stock_disposal);
		currentStock = total_stock_in - total_stock_out;
		return parseFloat(currentStock).toFixed(settings['decimalPlace']);
	}

	function formValidaion() {

		var flag = true;
		if ($scope.formData.start_date == ""
			|| $scope.formData.start_date == null
			|| $scope.formData.start_date == undefined) {
			$('#form_div_start_date').addClass('has-error');
			$('#form_div_from_date_error').show();
			flag = false;
		} else {
			$('#form_div_start_date').removeClass('has-error');
			$('#form_div_from_date_error').hide();
		}

		if ($scope.formData.end_date == "" || $scope.formData.end_date == null
			|| $scope.formData.end_date == undefined) {
			$('#form_div_end_date').addClass('has-error');
			$('#form_div_to_date_error').show();
			flag = false;
		} else {
			$('#form_div_end_date').removeClass('has-error');
			$('#form_div_to_date_error').hide();
		}

		if ($scope.formData.start_date != "" && $scope.formData.end_date != "") {

			var startDate = getDate($scope.formData.start_date);
			var endDate = getDate($scope.formData.end_date);
			if (new Date(startDate) > new Date(endDate)) {
				$rootScope.$broadcast('on_AlertMessage_ERR',
					"End date should be greater than start date");
				$scope.formData.end_date = dateForm(moment(new Date()).add(1,
					'days'));
				$scope.formData.start_date = dateForm(new Date());
			}
		}

		return flag;
	}

	function getDate(date) {
		var currentDateFormat = get_date_format();
		return moment(date, currentDateFormat.format).format("MM-DD-YYYY");
	}

}
