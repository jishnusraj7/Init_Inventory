var myApp = angular.module('report_ctrlr_app', [ 'ngMaterial' , 'ngMessages' , 'common_app' ]);


myApp.controller('report_ctrlr', report_ctrlr);


function report_ctrlr($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$timeout, $q, $window ) {
	$scope.formData = {};
	$scope.getValue = {};
	$scope.categoryList = [];
	$scope.itemsData = [];
	$scope.stockId = [];
	$scope.department_data1 = [];
	$("#itemLists").hide();
	$("#btnTools").show();
	$("#btnTally").hide();
	
	$http({
		url : '../stockin/formJsonData',
		method : "GET",
	}).then(function(response) {
		$scope.department_data1 = response.data.depData;


			var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));


			$("#div_department_code1").find(".acontainer input").val(depList.code);
			$("#form_div_department_code").find(".acontainer input").val(depList.code);

			$("#department_code").val(depList.code);
			$("#department_id").val(depList.id);
			$("#source_department_name").val(depList.name);
			}, function(response) { // optional
			});

	set_sub_menu("#report");						
	setMenuSelected("#currentstock_left_menu");	

	var vm = this;

	/*$scope.itemMaster_report = function(event){					//functions for NEXT-PREV button actions
		$rootScope.$broadcast("itemMaster_report");
	}*/
	$("#stock_in_report_form_div").hide();
	$("#stock_out_report_form").hide();

	$("#item_stock_form_div").hide();
	$("#purchaseorder_report_form_div").hide();
	$("#stock_register_report_form_div").hide();
	$("#stock_register_details_report_form_div").hide();
	$("#non_moving_form_div").hide();
	$("#stock_disposal_report_form_div").hide();
	$("#stock_transfer_report_form_div").hide();
	/* Done by anandu on 21-01-2020 */	
	$("#stock_excise_report_form_div").hide();
	$("#department_wise_report_form_div").hide();
	$("#purchase_return_report_form").hide();
	$("#tally_export_report_form").hide();
	$scope.fillselectReport =[{id : 0 ,name : "Current Stock Report" },
		{id : 1 ,name : "Stock Register Report" },
		{id : 2 ,name : "Purchase Order Report" },
		{id : 3 ,name : "Stock In  Report" },
		{id : 4 ,name : "Stock Out Report"},
		{id:5 ,name:"Non Moving Items"},
		{id:6,name:"Stock Disposal Report"},
		{id:7,name:"Stock Transfer Report"},
		/*	{id:8,name:"Stock Register Details Report"}*/
		/* Done by anandu on 21-01-2020 */
		{id:9,name:"Stock Excise Report"},
		{id:10,name:"Department Wise Stock Register"},
		{id:11,name:"Purchase Return Report"},
		{id:13,name:"Tally Export Report"},
		];

	$scope.formData.select_report = $scope.fillselectReport[0].id;

	$("#item_stock_form_div").show();


	$scope.selectreport = function(){


		var dateFormat = get_date_format();
		var date = new Date(),y = date.getFullYear(), m = date.getMonth();
		var curDate = new Date();
		var firstDay = new Date(y, m, 1);
		var lastDay = new Date(y, m + 1, 0);

		$("#form_div_startdate input").val(dateForm(firstDay));
		$("#form_div_stockout_startdate input").val(dateForm(firstDay));
		$("#form_div_enddate input").val(dateForm(lastDay));
		$("#form_div_stockout_enddate input").val(dateForm(lastDay));

		$("#enddate").val(dateForm(lastDay));
		if($scope.formData.select_report == 0){
			$("#btnTools").show();
			$("#item_stock_form_div").show();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$("#stock_out_report_form").hide();
			$("#non_moving_form_div").hide();
			$("#stock_disposal_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			/* Done by anandu on 21-01-2020 */			
			$("#stock_excise_report_form_div").hide();
			$("#department_wise_report_form_div").hide();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").hide();
		}
		if($scope.formData.select_report == 1){
			$("#btnTools").show();
			$("#non_moving_form_div").hide();
			$("#form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error").hide();
			$("#form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error").hide();
			$("#stock_register_report_form_div").show();
			$("#stock_register_details_report_form_div").hide();
			$("#item_stock_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$("#department_id").prop('selectedIndex',0);
			$(".itmCat").prop('selectedIndex',0);
			$("#trans_type").prop('selectedIndex',0);
			$(".acontainer").find('input').val('');
			$("#stock_out_report_form").hide();
			/* Done by anandu on 21-01-2020 */			
			$("#stock_excise_report_form_div").hide();
			$("#stock_disposal_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			$("#department_wise_report_form_div").hide();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").hide();
		}
		if($scope.formData.select_report == 2){
			$("#btnTools").show();
			$("#divErrormsg2_po").hide();
			$("#non_moving_form_div").hide();
			$("#form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error").hide();
			$("#form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error").hide();
			$("#form_div_startmonth").removeClass("has-error");
			$("#form_div_startmonth_error").hide();
			$("#poStatus").val(-1);
			$("#form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error").hide();
			$("#form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error").hide();
			$("#purchaseorder_report_form_div").show();
			$("#item_stock_form_div").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$(".acontainer").find('input').val('');
			$("#supplier_name").val('');
			$("#itemname2").val('');
			$("#stock_out_report_form").hide();
			$("#stock_disposal_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			/* Done by anandu on 21-01-2020 */			
			$("#stock_excise_report_form_div").hide();
			$("#department_wise_report_form_div").hide();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").hide();
		}

		if($scope.formData.select_report == 3){
			$("#btnTools").show();
			$("#non_moving_form_div").hide();
			$("#stkStatus").val(-1);
			$("#form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error").hide();
			$("#form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_out_report_form").hide();
			$("#item_stock_form_div").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").hide();
			$("#stock_in_report_form_div").show();
			$(".acontainer").find('input').val('');
			$("#supplier_name1").val('');
			$("#itemname1").val('');
			$("#stock_disposal_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			/* Done by anandu on 21-01-2020 */
			$("#stock_excise_report_form_div").hide();
			$("#department_wise_report_form_div").hide();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").hide();
		}
		if($scope.formData.select_report == 4){
			$("#btnTools").show();
			$("#non_moving_form_div").hide();
			$("#form_div_stockout_startdate").removeClass("has-error");
			$("#form_div_stockout_startdate_error").hide();
			$("#form_div_stockout_enddate").removeClass("has-error");
			$("#form_div_stockout_enddate_error").hide();
			$("#stock_out_report_form").show();
			$("#item_stock_form_div").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$("#divErrormsgStkout").hide();
			$("#source_department_name").val('');
			$("#dest_department_name").val('');
			$("#itemname1").val('');
			$("#form_div_item_id_stockout #itemname1").val('');
			$("#item_id").val('');
			$("#form_div_item_id_stockout").find(".acontainer input").val('');
			$("#stock_disposal_report_form_div").hide();
			/* Done by anandu on 21-01-2020 */
			$("#stock_excise_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			$("#department_wise_report_form_div").hide();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").hide();
		}
		if($scope.formData.select_report == 5){
			$("#btnTools").show();
			$("#item_stock_form_div").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$("#stock_out_report_form").hide();
			$("#non_moving_form_div").show();
			$("#stock_disposal_report_form_div").hide();
			/* Done by anandu on 21-01-2020 */
			$("#stock_excise_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			$("#department_wise_report_form_div").hide();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").hide();
		}
		if($scope.formData.select_report == 6){
			$("#btnTools").show();
			$("#item_stock_form_div").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$("#stock_out_report_form").hide();
			$("#non_moving_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			/* Done by anandu on 21-01-2020 */
			$("#stock_excise_report_form_div").hide();
			$("#stock_disposal_report_form_div").show();
			$("#department_wise_report_form_div").hide();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").hide();
		}


		if($scope.formData.select_report == 7){
			$("#btnTools").show();
			$("#item_stock_form_div").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$("#stock_out_report_form").hide();
			$("#non_moving_form_div").hide();
			/* Done by anandu on 21-01-2020 */
			$("#stock_excise_report_form_div").hide();
			$("#stock_disposal_report_form_div").hide();
			$("#stock_transfer_report_form_div").show();
			$("#department_wise_report_form_div").hide();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").hide();
		}
// for stock register details report
	/*	if($scope.formData.select_report == 8){

			$("#non_moving_form_div").hide();
			$("#form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error").hide();
			$("#form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").show();
			$("#item_stock_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$("#department_id").prop('selectedIndex',0);
			$(".itmCat").prop('selectedIndex',0);
			$("#trans_type").prop('selectedIndex',0);
			$(".acontainer").find('input').val('');
			$("#stock_out_report_form").hide();
			$("#stock_disposal_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
		}*/

		/* Done by anandu on 21-01-2020 */
		if($scope.formData.select_report == 9){
			$("#btnTools").show();
			$("#item_stock_form_div").hide();
			$("#non_moving_form_div").hide();
			$("#form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error").hide();
			$("#form_div_enddate").removeClass("has-error");
			$("#form_div_enddate_error").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_excise_report_form_div").show();
			$("#stock_register_details_report_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$("#stock_out_report_form").hide();
			$("#stock_disposal_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			$("#department_wise_report_form_div").hide();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").hide();
		}

		if($scope.formData.select_report == 10){
			$("#btnTools").show();
			$("#non_moving_form_div").hide();
			$("#form_div_startdate").removeClass("has-error");
			$("#form_div_startdate_error").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").hide();
			$("#item_stock_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$("#department_id").prop('selectedIndex',0);
			$(".itmCat").prop('selectedIndex',0);
			$(".acontainer").find('input').val('');
			$("#stock_out_report_form").hide();	
			$("#stock_excise_report_form_div").hide();
			$("#stock_disposal_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			$("#department_wise_report_form_div").show();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").hide();
		}
		if($scope.formData.select_report == 11){
			$("#btnTools").show();
			$("#non_moving_form_div").hide();
			$("#form_div_stockout_startdate").removeClass("has-error");
			$("#form_div_stockout_startdate_error").hide();
			$("#form_div_stockout_enddate").removeClass("has-error");
			$("#form_div_stockout_enddate_error").hide();
			$("#purchase_return_report_form").show();
			$("#stock_out_report_form").hide();
			$("#item_stock_form_div").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#tally_export_report_form").hide();
			$("#stock_in_report_form_div").hide();
			$("#divErrormsgStkout").hide();
			$("#source_department_name").val('');
			$("#dest_department_name").val('');
			$("#itemname1").val('');
			$("#form_div_item_id_stockout #itemname1").val('');
			$("#item_id").val('');
			$("#form_div_item_id_stockout").find(".acontainer input").val('');
			$("#stock_disposal_report_form_div").hide();
			/* Done by anandu on 21-01-2020 */
			$("#stock_excise_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			$("#department_wise_report_form_div").hide();
		}
		if($scope.formData.select_report == 13){
			$("#non_moving_form_div").hide();
			$("#form_div_stockout_startdate").removeClass("has-error");
			$("#form_div_stockout_startdate_error").hide();
			$("#form_div_stockout_enddate").removeClass("has-error");
			$("#form_div_stockout_enddate_error").hide();
			$("#purchase_return_report_form").hide();
			$("#tally_export_report_form").show();
			$("#stock_out_report_form").hide();
			$("#item_stock_form_div").hide();
			$("#stock_register_report_form_div").hide();
			$("#stock_register_details_report_form_div").hide();
			$("#purchaseorder_report_form_div").hide();
			$("#stock_in_report_form_div").hide();
			$("#divErrormsgStkout").hide();
			$("#source_department_name").val('');
			$("#dest_department_name").val('');
			$("#itemname1").val('');
			$("#form_div_item_id_stockout #itemname1").val('');
			$("#item_id").val('');
			$("#form_div_item_id_stockout").find(".acontainer input").val('');
			$("#stock_disposal_report_form_div").hide();
			/* Done by anandu on 21-01-2020 */
			$("#stock_excise_report_form_div").hide();
			$("#stock_transfer_report_form_div").hide();
			$("#department_wise_report_form_div").hide();
		}
		var depList = fun_get_dep_name(parseInt(strings['isDefDepartment']));

		$("#form_div_source_department_code").find(".acontainer input").val(depList.code);

		$("#div_department_code1").find(".acontainer input").val(depList.code);
		$("#form_div_department_code").find(".acontainer input").val(depList.code);
		$("#div_department_code1_disposal").find(".acontainer input").val(depList.code);
		$("#department_name").val(depList.name);
		$("#department_name1").val(depList.name);
		$("#department_code").val(depList.code);
		$("#department_code1").val(depList.code);
		$("#department_code1_disposal").val(depList.code);
		$("#department_id").val(depList.id);
		$("#department_id1").val(depList.id);


		$("#source_department_name").val(depList.name);
		$("#source_department_code").val(depList.code);
		$("#source_department_id").val(depList.id);






	}
	function fun_get_dep_name(id){
		var depList =[];
		for(var i=0;i < $scope.department_data1.length;i++){
			if($scope.department_data1[i].id == id){
				depList = $scope.department_data1[i];
			}
		}

		return depList;
	}




}


angular.bootstrap(document.getElementById("app2"), ['report_ctrlr_app']);
