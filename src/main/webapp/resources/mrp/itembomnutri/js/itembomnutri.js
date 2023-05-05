/**
 * 
 */
//Controller for Table and Form 
mrpApp.controller('item_bom_nutri', item_bom_nutri);


function item_bom_nutri($compile, $controller, $scope, $interval, $timeout, $http, $mdDialog, $rootScope, DTOptionsBuilder,
	DTColumnBuilder, MRP_CONSTANT, DATATABLE_CONSTANT, $q, $window, FORM_MESSAGES, ITEM_TABLE_MESSAGES, RECORD_STATUS, $filter, $document) {

	$scope.selectMe = function(event) {

		$("#myModal").modal("show");

	}

	$scope.syncNutri = function() {
		debugger
		// Use $http to send an HTTP POST request to the Java controller
		$http.get('/mrp/itembomnutri/SyncNutriData', {

		}).then(function(response) {
			debugger
			// Handle the response from the Java controller
			let strmsg = response.data.jsonArray;

		});

	};


	$controller('DatatableController', { $scope: $scope });
	//$scope.decimalPlace = 4;
	manageButtons("add");
	$scope.formData = {};
	$scope.show_table = true;
	$("#advsearchbox").show();
	$scope.show_form = false;
	$scope.hide_code_existing_er = true;
	$scope.stkdata = [];
	$scope.itemList = [];
	var mode = 0;
	//var decimapl_palces=0;
	var oldId;
	var isBase = 0;
	var deptId = 0;

	$scope.back_up_tax_id = "";
	$scope.back_up_tax_calculation_method = false;


	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.loadData = loadData;
	vm.setItemtableValues = setItemtableValues;
	vm.setChildValues = setChildValues;

	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;

	$scope.bomList = [{
		id: "", bom_item_id: "", qty: "", bom_item_name: "", bom_item_code: "",
		unit_price: parseFloat(0).toFixed(2), last_unit_price: parseFloat(0).toFixed(2)
	}];

	$scope.prodCostList = [{
		id: "", prod_cost_id: "", prod_cost_code: "", prod_cost_name: "", prod_cost_type: "",
		isPercentage: false, rate: ""
	}];

	$scope.formData = {
		stock_item_id: "", stock_item_code: "", stock_item_name: "", base_item_id: "", base_bom_item_code: "",
		base_bom_item_name: "", base_bom_itemId: "", uom: "", item_category_name: "",
		stock_item_qty: parseFloat(100).toFixed(settings['decimalPlace']), sales_margin: 0, is_semi_finished: false,
		is_finished: false, is_sales_margin_percent: false, tax_percentage: (0).toFixed(settings['decimalPlace']),
		tax_calculation_method: false
	};

	var item_data = [];
	var basebomitem_data = [];
	loadData();
	$("#advsearchbox").hide();
	$scope.show_table = false;
	$scope.list_prograssing = true;
	$('#btnAdd').hide();

	function loadData() {

		$http({
			url: 'formJsonData',
			async: false,
			method: "GET",
		}).then(function(response) {

			$scope.taxList = response.data.tax;
			$scope.categoryList = response.data.itemCategory;
			$scope.stkdata = response.data.bomItems;
			//	$scope.itemList=response.data.itmList;
			item_data = response.data.itmList;
			basebomitem_data = response.data.basebomList;
			console.log(basebomitem_data);
			$scope.categoryList.splice(0, 0, { id: "", name: "select" });
			$scope.taxList.splice(0, 0, { id: "", name: "select" });
			$rootScope.$emit("get_itmCtgry_list", { dep: $scope.categoryList });
			$("#advsearchbox").show();
			$scope.list_prograssing = false;
			$scope.show_table = true;
			$('#btnAdd').show();
		}, function(response) {/*optional*/ });
	}

	$scope.formData.tax_id = "";

	//function for tax estimation 
	$scope.tax_amount = 0.00;
	$scope.taxAmountCal = function() {

		$scope.tax_amount = 0.00;
		if ($scope.formData.tax_id == "") {
			$scope.tax_amount = "";
			return $scope.tax_amount;
		} else {

			var tax_percentage = 0.00;
			for (var i = 0; i < $scope.taxList.length; i++) {
				if ($scope.formData.tax_id == $scope.taxList[i].id) {
					if ($scope.taxList[i].is_tax1_applicable == 1)
						tax_percentage = parseFloat(tax_percentage) + parseFloat($scope.taxList[i].tax1_percentage);

					if ($scope.taxList[i].is_tax2_applicable == 1)
						tax_percentage = parseFloat(tax_percentage) + parseFloat($scope.taxList[i].tax2_percentage);

					/*if($scope.taxList[i].is_tax3_applicable==1)
							tax_percentage = parseFloat(tax_percentage) + parseFloat($scope.taxList[i].tax3_percentage);*/
					break;
				}
			}

			if ($scope.formData.tax_calculation_method == false)
				$scope.tax_amount = (parseFloat($scope.total_sale_price) * parseFloat(tax_percentage)) / 100;
			else
				$scope.tax_amount = $scope.total_sale_price - ((parseFloat($scope.total_sale_price)) / (1 + (parseFloat(tax_percentage)) / 100));

			//return  parseFloat($scope.tax_amount).toFixed(settings['decimalPlace']); 
			return parseFloat($scope.tax_amount).toFixed(2);
		}
	}

	//function for estimate unit cost
	$scope.costPerUnit = function() {

		$scope.cost_per_unit = 0.00;
		angular.forEach($scope.bomList, function(item) {
			//$scope.cost_per_unit  += (item.qty * item.unit_price);
			$scope.cost_per_unit += (item.qty * item.last_unit_price);
		})

		angular.forEach($scope.prodCostList, function(prod) {
			if (prod.rate != "" && prod.rate != 0 && prod.rate != undefined)
				$scope.cost_per_unit += parseFloat(prod.rate);
		})

		if ($scope.formData.bom_item_weight != "" && $scope.formData.bom_item_weight != undefined
			&& !isNaN($scope.formData.bom_item_weight) && angular.isNumber(+$scope.formData.bom_item_weight)
			&& $scope.formData.bom_item_weight != 0) {

			$scope.cost_per_unit = $scope.cost_per_unit / $scope.formData.stock_item_qty;
			//$scope.cost_per_unit = parseFloat($scope.cost_per_unit).toFixed(settings['decimalPlace']);
			//$("#cost_per_unit").val(parseFloat($scope.cost_per_unit).toFixed(settings['decimalPlace']));
			$("#cost_per_unit").val(parseFloat($scope.cost_per_unit).toFixed(2));

		}
		else if ($scope.formData.stock_item_qty != "" && $scope.formData.stock_item_qty != 0 && $scope.formData.stock_item_qty != undefined
			&& !isNaN($scope.formData.stock_item_qty) && angular.isNumber(+$scope.formData.stock_item_qty)
			&& $scope.formData.stock_item_qty != 0) {
			/*$scope.cost_per_unit = 0.00;
		  angular.forEach($scope.bomList, function(item) {

			  $scope.cost_per_unit  += (item.qty * item.unit_price);
				})

				 angular.forEach($scope.prodCostList, function(prod) {
					if(prod.rate!="" && prod.rate!=parseFloat(0).toFixed(settings['decimalPlace']) && prod.rate!=undefined)
					{
					$scope.cost_per_unit  +=parseFloat(prod.rate);
			  }
				})*/
			if ($scope.formData.stock_item_qty != 0) {
				$scope.cost_per_unit = $scope.cost_per_unit / $scope.formData.stock_item_qty;
				//$("#cost_per_unit").val($scope.cost_per_unit.toFixed(settings['decimalPlace']));
				$("#cost_per_unit").val($scope.cost_per_unit.toFixed(2));
			}
			else {
				//$scope.cost_per_unit = parseFloat( $scope.cost_per_unit ).toFixed(settings['decimalPlace']);
				$scope.cost_per_unit = parseFloat($scope.cost_per_unit).toFixed(2);
				$("#cost_per_unit").val($scope.cost_per_unit);
			}

			//return  parseFloat($scope.cost_per_unit).toFixed(settings['decimalPlace']); 
			return parseFloat($scope.cost_per_unit).toFixed(2);
		} else {
			$scope.cost_per_unit = "";
			$("#cost_per_unit").val($scope.cost_per_unit);
			return $scope.cost_per_unit;
		}
	}

	//function for estimate total sales price
	$scope.totalSalesPrice = function() {
		//if($scope.formData.bom_item_weight !="" && $scope.formData.bom_item_weight != undefined){
		if ($scope.formData.stock_item_qty != "" && $scope.formData.stock_item_qty != 0 && $scope.formData.stock_item_qty != undefined) {

			$scope.total_sale_price = 0.00;
			if ($scope.formData.is_sales_margin_percent == false) {
				if (parseFloat($scope.formData.sales_margin) > 0)
					$scope.total_sale_price = parseFloat($scope.formData.sales_margin) + parseFloat($scope.cost_per_unit);
				else
					$scope.total_sale_price = $scope.cost_per_unit;
			}
			else {

				if (parseFloat($scope.formData.sales_margin) > 0) {
					$scope.total_sale_price_add = parseFloat($scope.cost_per_unit) * $scope.formData.sales_margin / 100;
					$scope.total_sale_price = parseFloat($scope.total_sale_price_add) + parseFloat($scope.cost_per_unit);
				}
				else
					$scope.total_sale_price = $scope.cost_per_unit;
			}
			//return  parseFloat($scope.total_sale_price).toFixed(settings['decimalPlace']);;
			return parseFloat($scope.total_sale_price).toFixed(2);;
		}
		else {
			$scope.total_sale_price = "";
			return $scope.total_sale_price;
		}
	}

	//function for estimate amount after tax
	$scope.amountAfterTax = function() {

		if ($scope.formData.stock_item_qty != "" && $scope.formData.stock_item_qty != 0 && $scope.formData.stock_item_qty != undefined) {
			$scope.amout_after_tax = 0.00;
			$scope.amout_after_tax_add = 0.00;

			if ($scope.formData.tax_calculation_method == true)
				//$scope.amout_after_tax=parseFloat( $scope.total_sale_price ).toFixed(settings['decimalPlace']);
				$scope.amout_after_tax = parseFloat($scope.total_sale_price).toFixed(2);
			else {
				/*if(parseFloat($scope.formData.tax_percentage)>0)
					$scope.amout_after_tax=parseFloat( $scope.total_sale_price )+(parseFloat( $scope.total_sale_price )*parseFloat($scope.formData.tax_percentage)/100);
				else
					$scope.amout_after_tax=parseFloat( $scope.total_sale_price ).toFixed(settings['decimalPlace']);*/

				if ($scope.formData.tax_id != "")
					$scope.amout_after_tax = parseFloat($scope.total_sale_price) + parseFloat($scope.tax_amount);
				else
					//$scope.amout_after_tax=parseFloat( $scope.total_sale_price ).toFixed(settings['decimalPlace']);
					$scope.amout_after_tax = parseFloat($scope.total_sale_price).toFixed(2);
			}
			//return  parseFloat($scope.amout_after_tax).toFixed(settings['decimalPlace']);
			return parseFloat($scope.amout_after_tax).toFixed(2);

		}
		else {
			$scope.amout_after_tax = "";
			return $scope.amout_after_tax;
		}
	}

	$scope.disable_search_text = function(elemenvalue) {
		if ($scope.disable_all == true) {
			$(elemenvalue).attr("disabled", true);
		}
	}

	vm.dtInstance = {};

	var DataObj = {};
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.order = "asc";
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm, DataObj);
	if (version_value == 1) {
		vm.dtColumns = [
			DTColumnBuilder.newColumn('stock_item_id').withTitle('Stock Item Id').notVisible().withOption('searchable', 'false'),
			DTColumnBuilder.newColumn('stock_item_code').withTitle('Stock Item Code').withOption('type', 'natural').renderWith(
				function(data, type, full, meta) {
					return urlFormater(data);
				}),
			DTColumnBuilder.newColumn('stock_item_name').withTitle('Stock Item Name').withOption('type', 'natural'),
			DTColumnBuilder.newColumn('item_category_name').withTitle('Item Category Name').withOption('type', 'natural'),
			DTColumnBuilder.newColumn('dept_name').withTitle('Department Name').withOption('type', 'natural'),
		];
	}
	else {
		vm.dtColumns = [
			DTColumnBuilder.newColumn('stock_item_id').withTitle('Stock Item Id').notVisible().withOption('searchable', 'false'),
			DTColumnBuilder.newColumn('stock_item_code').withTitle('Stock Item Code').withOption('type', 'natural').renderWith(
				function(data, type, full, meta) {
					return urlFormater(data);
				}),
			DTColumnBuilder.newColumn('stock_item_name').withTitle('Stock Item Name').withOption('type', 'natural'),
			DTColumnBuilder.newColumn('item_category_name').withTitle('Item Category Name').withOption('type', 'natural'),
		];
	}

	function urlFormater(data) {
		return '<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
			+ data + '</a>';
	}

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {					//Rowcallback fun for Get Edit Data when clk on Code
		$('a', nRow).unbind('click');
		$('a', nRow).bind('click', function(e) {
			$scope.$apply(function() {
				$('tr.selected').removeClass('selected');
				if (e.target.id == "rcd_edit") {
					var rowData = aData;
					mode = 1;
					$(nRow).addClass('selected');
					var current_row_index = 0;
					var test = vm.dtInstance.DataTable.rows();
					for (var i = 0; i < test[0].length; i++) {
						if (test[0][i] == vm.dtInstance.DataTable.row(".selected").index()) {
							current_row_index = i;
						}
					}
					$(".base_bom_item_div").find(".acontainer input").val("");
					edit(rowData, current_row_index, e);
					$('#show_form').val(1);
					//$scope.show_table=false;
					//$("#advsearchbox").hide();
				}
			});
		});
		return nRow;
	}


	function infoCallback(settings, start, end, max, total, pre) {    //function for get  page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if (pageInfo.pages == 0) {
			return pageInfo.page + " / " + pageInfo.pages;
		} else {
			return pageInfo.page + 1 + " / " + pageInfo.pages;
		}


	}


	$rootScope.$on('reloadDatatable', function(event) {					//reload Datatable
		reloadData();
	});

	$rootScope.$on('hide_table', function(event) {
		showTable(event);
		mode = 0;
	});

	$rootScope.$on('hide_form', function(event) {
		$('#show_form').val(0);
		$scope.show_table = true; $("#advsearchbox").show();
		$scope.show_form = false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');

	});

	function showTable(event) {
		$("#advsearchbox").hide();
		$scope.show_table = false;
		$scope.show_form = true;
	}



	$timeout(function() { $("#DataTables_Table_0_filter").hide(); }, 1);

	$rootScope.$on("advSearch", function(event) {

		$("#dropdownnew").toggle();
		DataObj.adnlFilters = [{}];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search('').draw();

		$scope.itmCode = $('#itmCode').val();

		$scope.itmName = $('#itmName').val();

		var itemcatvalue = $('#itmcatId').val();
		if (itemcatvalue != "" && itemcatvalue != undefined) {
			var itemcatvaluearr = itemcatvalue.split(":");
			$scope.itmcatval = itemcatvaluearr[1];
		}
		else {
			$scope.itmcatval = "";

		}
		if ($scope.itmcatval != "" && $scope.itmcatval != undefined) {
			var itmcatvalsat = $('#itmcatId').find(":selected").text();
		}
		$scope.searchTxtItms = { 2: $scope.itmCode, 3: $scope.itmName, 4: itmcatvalsat };

		var close_option = [];
		var counter = 0;
		for (var key in $scope.searchTxtItms) {

			if ($scope.searchTxtItms.hasOwnProperty(key)) {
				if ($scope.searchTxtItms[key] != null && $scope.searchTxtItms[key] != undefined && $scope.searchTxtItms[key] != "") {

					angular.element(document.getElementById('SearchText')).append($compile("<div id=" + key + "  class='advseacrh '  contenteditable='false'>" + $scope.searchTxtItms[key] + "<span class='close-thik' contenteditable='false'  ng-click='deleteOptn(" + key + "); '></span></div>")($scope))
					$scope.deleteOptn = function(key) {
						delete $scope.searchTxtItms[key];
						$('#' + key).remove();
						switch (key) {

							case 2:
								$scope.itmCode = "";
								$('#itmCode').val();
								break;
							case 3:
								$scope.itmName = "";
								$('#itmName').val("");
								break;
							case 4:
								$scope.itmcatval = "";
								$rootScope.$emit("setItmcategory");
								//$('#itmcatId').val("");
								break;

						}
						DataObj.adnlFilters = [{ col: 1, filters: $scope.itmCode }, { col: 2, filters: $scope.itmName }];

						vm.dtInstance.reloadData();
					};

					counter++;
					//
					//	Object.keys(object).find(key => object[key] === value)

				}
			}
		}



		DataObj.adnlFilters = [{ col: 1, filters: $scope.itmCode }, { col: 2, filters: $scope.itmName }, { col: 3, filters: $scope.itmcatval }];
		vm.dtInstance.reloadData();
		$scope.searchTxtItms = {};

	});

	$rootScope.$on("Search", function(event) {
		DataObj.adnlFilters = [{}];
		$scope.searchTxtItms = {};
		/*		vm.dtInstance.reloadData();
		 */			vm.dtInstance.DataTable.search($('#SearchText').text()).draw();

	});


	$("#clear").click(function() {
		DataObj.adnlFilters = [{}];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();
		$scope.searchTxtItms = {};
		$rootScope.$emit("setItmcategory");
	});


	$("#clearFeilds").click(function() {
		$("#dropdownnew").toggle();
		$("#itmCode").val("");
		$('#itmName').val("");
		$rootScope.$emit("setItmcategory");



	});

	$("#closebtnew").click(function() {
		$("#dropdownnew").toggle();


	});


	function reloadData() {
		vm.dtInstance.reloadData(null, true);
		loadItemDetailsTable();

	}


	function edit(row_data, cur_row_index, event) {

		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function
		var row_count = vm.dtInstance.DataTable.rows().data();
		row_count = row_count.length;
		if (row_count == 1) {
			$rootScope.$emit("disable_next_btn");
			$rootScope.$emit("disable_prev_btn");
		} else {
			if (cur_row_index == 0) {
				$rootScope.$emit("enable_next_btn");
				$rootScope.$emit("disable_prev_btn");
			} else if (row_count - 1 == cur_row_index) {
				$rootScope.$emit("disable_next_btn");
				$rootScope.$emit("enable_prev_btn");
			}
		}

		//	setItemtableValues(row_data.id,fun_get_stockRegId(row_data.ref_no));



		showTable();
		clearform();
		$rootScope.$emit("show_edit_btn_div", cur_row_index);
		$scope.formData = {
			stock_item_id: row_data.stock_item_id, stock_item_code: row_data.stock_item_code,
			stock_item_name: row_data.stock_item_name, base_item_id: row_data.base_item_id, base_bom_item_code: row_data.base_bom_item_code, base_bom_item_name: row_data.base_bom_item_name, base_bom_itemId: oldId, uom: row_data.uomcode, item_category_name: row_data.item_category_name,
			stock_item_qty: parseFloat(row_data.stock_item_qty).toFixed(settings['decimalPlace']),
			sales_margin: row_data.sales_margin, tax_percentage: row_data.tax_name, dept_name: row_data.dept_name,
		};
		$scope.formData.tax_id = row_data.tax_id;
		$scope.back_up_tax_id = row_data.tax_id;

		$scope.formData.is_semi_finished = (row_data.is_semi_finished == 0) ? false : true;
		$scope.formData.is_finished = (row_data.is_finished == 0) ? false : true;
		$scope.formData.is_sales_margin_percent = (row_data.is_sales_margin_percent == 0) ? false : true;
		$scope.formData.tax_calculation_method = (row_data.tax_calculation_method == 0 || row_data.tax_calculation_method == "") ? false : true;
		$scope.back_up_tax_calculation_method = (row_data.tax_calculation_method == 0 || row_data.tax_calculation_method == "") ? false : true;
		$("#form_div_bom_qty").removeClass("has-error");
		$("#form_div_bom_qty_error").hide();

		$(".stock_item_div").find(".acontainer input").val(row_data.stock_item_code);

		$scope.disable_all = true;
		$scope.disable_item_code = true;





		setItemtableValues(row_data.stock_item_id, 1, row_data.dept_name);
		//setChildValues(row_data.stock_item_id,1);
		$('#btnBack').show();

		$timeout(function() {
			$(".acontainer input").attr('disabled', true);

		}, 1);


	}

	$scope.bomList = [];
	$scope.prodCostList = [];

	function setItemtableValues(itmId, isBase, deptName) {
		console.log("deptId===========" + deptName);
		$scope.prograssing = true;

		$http({
			method: 'GET',
			url: "getDataToEdit",
			params: {
				itemId: itmId,
				isBase: isBase,
				deptName: deptName
			},
			async: false,
		}).success(function(result) {

			$scope.bomList = result.bom;
			console.log($scope.bomList);
			$scope.prodCostList = result.prd_costdata;
			for (var i = 0; i < $scope.bomList.length; i++) {
				$scope.bomList[i].qty = parseFloat($scope.bomList[i].qty).toFixed($scope.bomList[i].decimal_places);
				/*$scope.bomList[i].unit_price=parseFloat($scope.bomList[i].unit_price).toFixed(settings['decimalPlace']);
				$scope.bomList[i].last_unit_price=parseFloat($scope.bomList[i].last_unit_price).toFixed(settings['decimalPlace']);*/
				$scope.bomList[i].unit_price = parseFloat($scope.bomList[i].unit_price).toFixed(2);
				$scope.bomList[i].last_unit_price = parseFloat($scope.bomList[i].last_unit_price).toFixed(2);
				$scope.bomList[i].isSet = false;
			}

			for (var i = 0; i < $scope.prodCostList.length; i++) {
				//$scope.prodCostList[i].rate=parseFloat($scope.prodCostList[i].rate).toFixed(settings['decimalPlace']);
				$scope.prodCostList[i].rate = parseFloat($scope.prodCostList[i].rate).toFixed(2);
				if ($scope.prodCostList[i].is_percentage == 1) { $scope.prodCostList[i].isPercentage = true; } else {
					$scope.prodCostList[i].isPercentage = false;
				}
				$scope.prodCostList[i].isSet = false;
			}
			if ($scope.bomList.length == 0) {
				$scope.bomList.push({
					id: "", bom_item_id: "", qty: "", bom_item_name: "", bom_item_code: "",
					unit_price: parseFloat(0).toFixed(2), last_unit_price: parseFloat(0).toFixed(2)
				});

			}
			if ($scope.prodCostList.length == 0) {
				$scope.prodCostList.push({
					id: "", prod_cost_id: "", prod_cost_code: "", prod_cost_name: "",
					prod_cost_type: "", isPercentage: false, rate: ""
				});
				$("#prodCost .acontainer input").attr('disabled', true);
			}

			if (mode == 1) {
				//if ($scope.bomList[i].base_item_id === undefined) {
				$scope.prograssing = false;
				//	}


				if ($scope.bomList[i].base_item_id != "" && $scope.bomList[i].base_item_id != undefined) {
					$scope.formData.base_bom_itemId = $scope.bomList[i].id;
					//$scope.formData.base_item_id = $scope.bomList[i].base_item_id;
					for (var j = 0; j < basebomitem_data.length; j++) {
						if (basebomitem_data[j].id == $scope.bomList[i].base_item_id) {
							$scope.formData.base_item_id = basebomitem_data[j].id;
							$scope.formData.base_bom_item_code = basebomitem_data[j].code;
							$(".base_bom_item_div").find(".acontainer input").val(basebomitem_data[j].code);
							$scope.formData.base_bom_item_name = basebomitem_data[j].name;
							$scope.formData.base_bom_qty = basebomitem_data[j].stock_item_qty;
							var bomItemWeight = basebomitem_data[j].stock_item_qty / $scope.formData.stock_item_qty;
							$scope.formData.bom_item_weight = bomItemWeight;
						}
					}

				}
			}

			$scope.prograssing = false;
		}, function(response) {
			$scope.prograssing = false;
		});


	}



	//	Delete Function
	$rootScope.$on("fun_delete_current_data", function(event) {
		var confirm = $mdDialog.confirm({
			onComplete: function afterShowAnimation() {
				var $dialog = angular.element(document.querySelector('md-dialog'));
				var $actionsSection = $dialog.find('md-dialog-actions');
				var $cancelButton = $actionsSection.children()[0];
				var $confirmButton = $actionsSection.children()[1];
				angular.element($confirmButton).removeClass('md-focused');
				angular.element($cancelButton).addClass('md-focused');
				$cancelButton.focus();
			}
		}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).cancel('No').ok(
			'Yes')
			;
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			$http({
				url: 'delete',
				method: "POST",
				params: { id: $scope.formData.stock_item_id },
			}).then(function(response) {
				if (response.data != 0) {
					$rootScope.$broadcast('on_AlertMessage_SUCC', FORM_MESSAGES.DELETE_SUC);

					$('#show_form').val(0);
					vm.dtInstance.reloadData(null, true);
					reloadData();
					$scope.show_table = true; $("#advsearchbox").show();
					$scope.show_form = false;
					manageButtons("add");
					$scope.disable_all = true;
					$scope.disable_item_code = true;

					$(".acontainer input").attr('disabled', true);
					$scope.disable_code = true;
					loadData();

				} else {
					$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Delete failed.")
						.ok('Ok!')
						.targetEvent(event)
					);
				}
			}, function(response) { // optional
				$mdDialog.show($mdDialog.alert()
					.parent(angular.element(document.querySelector('#dialogContainer')))
					.clickOutsideToClose(true)
					.textContent("Delete failed.")
					.ok('Ok!')
					.targetEvent(event)
				);
			});

		}, function() {

		});
	});

	function select_next_prev_row(index) {										//set NEXT-PREV Data In Form Atfer Deleteion

		var cur_index = index;
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		if (row_count != 1) {
			var row_data = vm.dtInstance.DataTable.rows().data();
			if (index == row_count - 1) {
				index = index - 1;
			} else {
				index = index + 1;
			}
			var selcted_row_data = vm.dtInstance.DataTable.rows(index).data();
			edit(selcted_row_data[0], index);
			$rootScope.$emit("next_formdata_set", index);
			if (cur_index != 0) {
				if (cur_index - 1 == 0) {
					$rootScope.$emit("disable_prev_btn");

				} else if (index + 1 == row_count - 1) {
					$rootScope.$emit("disable_next_btn");

				}
			} else if (cur_index == 0) {
				if (index - 1 == 0) {
					$rootScope.$emit("disable_prev_btn");

				} else if (index + 1 == row_count - 1) {
					$rootScope.$emit("disable_next_btn");

				}
			}


		} else if (row_count == 1) {
			$scope.show_table = true; $("#advsearchbox").show();
			$scope.show_form = false;
			manageButtons("add");
		}
		return index;

	}


	function view_mode_aftr_edit() {
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div", cur_row_index);
		$scope.disable_all = true;
		$scope.disable_code = true;
		$(".acontainer input").attr('disabled', true);
	}


	var prodcost_data = [];
	$scope.prodcost_data = [];

	$http({
		url: '../prodcost/costTypeList', method: "GET", async: false,
	}).then(function(response) {
		prodcost_data = response.data.data;
		$scope.prodcost_data = response.data.data;

	}, function(response) { // optional
	});



	//Discard function
	$rootScope.$on("fun_discard_form", function(event) {				//Discard function
		var confirm = $mdDialog.confirm({
			onComplete: function afterShowAnimation() {
				var $dialog = angular.element(document.querySelector('md-dialog'));
				var $actionsSection = $dialog.find('md-dialog-actions');
				var $cancelButton = $actionsSection.children()[0];
				var $confirmButton = $actionsSection.children()[1];
				angular.element($confirmButton).removeClass('md-focused');
				angular.element($cancelButton).addClass('md-focused');
				$cancelButton.focus();
			}
		}
		).title(FORM_MESSAGES.DISCARD_WRNG).targetEvent(event).ok(
			'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {

			var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);

			//if($scope.bomList[0].id ==undefined || $scope.bomList[0].id ==""|| mode == 0){
			if (mode == 0) {
				$scope.formData = {
					stock_item_id: "", stock_item_code: "", stock_item_name: "", base_item_id: "", base_bom_item_code: "", base_bom_item_name: "", base_bom_itemId: "", uom: "", item_category_name: "", stock_item_qty: parseFloat(100).toFixed(settings['decimalPlace']), sales_margin: 0,
					is_semi_finished: false, is_finished: false,
					is_sales_margin_percent: false, tax_percentage: (0).toFixed(settings['decimalPlace']), tax_calculation_method: false
				};

				$scope.bomList = [];
				$scope.bomList = [{
					id: "", bom_item_id: "", qty: "", bom_item_name: "", bom_item_code: "",
					unit_price: parseFloat(0).toFixed(2), last_unit_price: parseFloat(0).toFixed(2)
				}];

				$scope.prodCostList = [];
				$scope.prodCostList = [{
					id: "", prod_cost_id: "", prod_cost_code: "", prod_cost_name: "",
					prod_cost_type: "", isPercentage: false, rate: ""
				}];

				$(".stock_item_div").find(".acontainer input").val("");
				$(".base_bom_item_div").find(".acontainer input").val("");

				$scope.formData.tax_id = "";


			} else {
				$(".base_bom_item_div").find(".acontainer input").val("");
				var dataIndex = vm.dtInstance.DataTable.rows();
				var row_data = vm.dtInstance.DataTable.rows(dataIndex[0][cur_row_index]).data();
				edit(row_data[0], cur_row_index);
				/*if(row_data[0].stock_item_id != "" ){
						  if($scope.bomList[0].base_item_id == ""){
							  $(".base_bom_item_div").find(".acontainer input").val("");
						  }
					  }*/
			}

			clearform();
		});

	});


	$(document).on('keyup', '#form_div_stock_item_code input', function(e) {
		if (e.which == 13) {
			$("#form_div_stock_item_code input").focus();
		}
		if (e.which != 9 && e.which != 13) {
			$scope.$apply(function() {
				$scope.formData.stock_item_id = "";
				$scope.formData.stock_item_code = "";
				$scope.formData.stock_item_name = "";
				$scope.formData.uom = "";
				$scope.formData.item_category_name = "";
				$scope.formData.dept_name = "";
				$scope.formData.tax_id = "";
				$scope.formData.tax_calculation_method = false;
				$("#stock_item_code").val("");
			});
		}
	});

	//base bom item search clear

	//$(document).on('keyup','#form_div_bom_master_code input',function(e){
	//$('#form_div_bom_master_code').find('.acontainer input').keyup(function(e){
	$("#form_div_bom_master_code").keyup('input', function(e) {

		if (e.which == 13) {
			$("#form_div_bom_master_code").find(".acontainer input").focus();
		}
		if (e.which != 9 && e.which != 13) {
			if (e.which == 8) {
				$scope.$apply(function() {
					$scope.formData.base_item_id = "";
					$scope.formData.base_bom_item_code = "";
					$scope.formData.base_bom_item_name = "";
					$scope.formData.base_bom_itemId = "";

					$scope.bomList = [];
					$scope.bomList = [{
						id: "", bom_item_id: "", qty: "", bom_item_name: "", bom_item_code: "",
						unit_price: parseFloat(0).toFixed(2), last_unit_price: parseFloat(0).toFixed(2)
					}];
					$scope.prodCostList = [];
					$scope.prodCostList = [{ id: "", prod_cost_id: "", prod_cost_code: "", prod_cost_name: "", prod_cost_type: "", isPercentage: false, rate: parseFloat(0).toFixed(2) }];


					//$("#base_bom_item_code").val("");

				});
			}
		}
	});


	$rootScope.$on("fun_enable_inputs", function() {
		$('#show_form').val(1);
		$scope.disable_all = false;
		$scope.disable_item_code = false;

		$(".acontainer input").attr('disabled', false);
		$("#form_div_bom_master_code").find(".acontainer input").attr('disabled', false);
		if ($scope.formData.stock_item_id == undefined || $scope.formData.stock_item_id != "") {
			$("#form_div_stock_item_code").find(".acontainer input").attr('disabled', true);
		}

		/*if($scope.formData.base_item_id==undefined || $scope.formData.base_item_id!="")
		{
				$("#form_div_bom_master_code").find(".acontainer input").attr('disabled', true);
		}*/

		$scope.showFinalize = false;
		//$("#stockHead tr:nth-child(01)  td:nth-child(1)").find(".acontainer input").focus();

	});


	$rootScope.$on("fun_clear_form", function() {

		$(".stock_item_div").find(".acontainer input").val("");
		$("#form_div_stock_item_code").find(".acontainer input").attr('disabled', false);
		$("#form_div_stock_item_code").find(".acontainer input").focus();
		$(".base_bom_item_div").find(".acontainer input").val("");
		$("#form_div_bom_master_code").find(".acontainer input").attr('disabled', false);
		$("#cost_per_unit").val("0");
		$scope.bomList = [];
		$scope.bomList = [{
			id: "", bom_item_id: "", qty: "", bom_item_name: "", bom_item_code: "",
			unit_price: parseFloat(0).toFixed(2), last_unit_price: parseFloat(0).toFixed(2)
		}];
		$scope.prodCostList = [];
		$scope.prodCostList = [{ id: "", prod_cost_id: "", prod_cost_code: "", prod_cost_name: "", prod_cost_type: "", isPercentage: false, rate: parseFloat(0).toFixed(2) }];



		$scope.formData = {};
		$scope.formData = {
			stock_item_id: "", stock_item_code: "", stock_item_name: "", base_item_id: "", base_bom_item_code: "", base_bom_item_name: "", base_bom_itemId: "", uom: "", item_category_name: "", stock_item_qty: parseFloat(100).toFixed(settings['decimalPlace']), sales_margin: 0,
			is_semi_finished: false, is_finished: false,
			is_sales_margin_percent: false, tax_percentage: (0).toFixed(settings['decimalPlace']), tax_calculation_method: false
		};
		$scope.formData.tax_id = "";
		$("#form_div_bom_qty").removeClass("has-error");
		$("#form_div_bom_qty_error").hide();
		$scope.tax_amount = 0.00;

	});

	$rootScope.$on("fun_next_rowData", function(e, id) {


		var current_row_index = 0;
		var dataIndex = vm.dtInstance.DataTable.rows();
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if (current_row_index == 0) {
			$rootScope.$emit("enable_prev_btn");
		}

		if (row_data.length == current_row_index + 1) {
			$rootScope.$emit("disable_next_btn");
		}
		var next_row_index = current_row_index + 1;
		if (row_data[next_row_index] != undefined) {
			var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][next_row_index]).data();
			edit(selcted_row_data[0], next_row_index);
			$rootScope.$emit("next_formdata_set", next_row_index);
		}
	});

	$rootScope.$on("fun_prev_rowData", function(e, id) {
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var dataIndex = vm.dtInstance.DataTable.rows();
		//var dataIndex = $scope.filterRowData;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if (row_count - 1 == current_row_index) {
			$rootScope.$emit("enable_next_btn");
		}
		var prev_row_index = current_row_index - 1;
		if (row_data[prev_row_index] != undefined) {
			var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][prev_row_index]).data();
			edit(selcted_row_data[0], prev_row_index);
			$rootScope.$emit("next_formdata_set", prev_row_index);
		}
		if (current_row_index - 1 == 0) {
			$rootScope.$emit("disable_prev_btn");
		}

	});




	$scope.amount = function(item) {
		var amount = 0.00;
		//amount = item.qty * item.unit_price;
		amount = item.qty * item.last_unit_price;
		//return parseFloat(amount).toFixed(settings['decimalPlace']);
		return parseFloat(amount).toFixed(2);
	}









	//Save function 
	$scope.save_data = function(event) {
		if (form_validation($scope.formData)) {

			/*for(var i=0;i<$scope.bomList.length;i++)
				{
				if($scope.bomList[i])
				}*/

			//var Alldata = JSON.stringify({stock_item_data:$scope.formData,bom:$scope.bomList,prodCostList:$scope.prodCostList});
			var amount_after_tax = $("#amount_after_tax").val();
			//	$scope.qty=parseFloat($scope.qty).toFixed(decimapl_palces);
			var Alldata = JSON.stringify({ stock_item_data: $scope.formData, bom: $scope.bomList, prodCostList: $scope.prodCostList, amount_after_tax: amount_after_tax });
			console.log(Alldata);


			$http({
				url: 'saveBom',
				method: "POST",
				data: Alldata,
			}).then(function(response) {

				if (response.data == 1) {

					if ($scope.bomList[0].bom_item_id != 0 && $scope.bomList[0].bom_item_id != undefined) {

						$scope.disable_all = true;
						$scope.disable_item_code = true;
						$(".acontainer input").attr('disabled', true);
						$("#form_div_source_code .acontainer input").attr('disabled', true);
						$("#depId1 .acontainer input").attr('disabled', true);
						$rootScope.$broadcast('on_AlertMessage_SUCC', FORM_MESSAGES.UPDATE_SUC);
						view_mode_aftr_edit();

						$scope.formData.tax_id = $scope.back_up_tax_id;
						//$scope.formData.tax_calculation_method=$scope.back_up_tax_calculation_method;

					} else {

						loadData();
						$rootScope.$broadcast('on_AlertMessage_SUCC', FORM_MESSAGES.SAVE_SUC);



						$scope.formData = {
							stock_item_id: "", stock_item_code: "", stock_item_name: "", base_item_id: "", base_bom_item_code: "", base_bom_item_name: "", base_bom_itemId: "", uom: "", item_category_name: "", stock_item_qty: parseFloat(100).toFixed(settings['decimalPlace']), sales_margin: 0,
							is_semi_finished: false, is_finished: false,
							is_sales_margin_percent: false, tax_percentage: (0).toFixed(settings['decimalPlace']), tax_calculation_method: false
						};
						$scope.formData.tax_id = "";
						$scope.bomList = [];
						$scope.bomList = [{
							id: "", bom_item_id: "", qty: "", bom_item_name: "", bom_item_code: "",
							unit_price: parseFloat(0).toFixed(2), last_unit_price: parseFloat(0).toFixed(2)
						}];

						$(".stock_item_div").find(".acontainer input").val("");

					}
					loadData();
					reloadData();
					manageButtons("add");
					$scope.formData = {};
					$scope.show_table = true;
					$("#advsearchbox").show();
					$scope.show_form = false;
					$scope.hide_code_existing_er = true;
					$scope.stkdata = [];
					$scope.itemList = []

					$scope.back_up_tax_id = "";
					$scope.back_up_tax_calculation_method = false;
				} else {

					$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Save failed.")
						.ok('Ok!')
						.targetEvent(event)
					);
					//$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
					$scope.prograssing = false;
				}
			}, function(response) { // optional
				$mdDialog.show($mdDialog.alert()
					.parent(angular.element(document.querySelector('#dialogContainer')))
					.clickOutsideToClose(true)
					.textContent("Save failed.")
					.ok('Ok!')
					.targetEvent(event)
				);

			});


		}
	}
	$rootScope.$on('fun_save_data', function(event) {
		$scope.save_data();
	});



	$scope.removeBomRow = function(index) {
		if ($scope.disable_all == false) {

			if ($scope.bomList.length == 1) {


				if (index == 0) {
					$rootScope.$broadcast('on_AlertMessage_ERR', ITEM_TABLE_MESSAGES.TABLE_ERR);
					$("#stockHead tbody tr:nth-child(" + ($scope.bomList.length) + ") td:nth-child(" + (2) + ")").find(".acontainer input").focus();


				}
			}
			else {
				$scope.bomList.splice(index, 1);

			}
		}
	}


	$scope.addBomRow = function(index) {

		if ($scope.disable_all == false || $scope.disable_bom == false) {
			if (index < $scope.bomList.length - 1) {
				$("#stockHead tbody tr:nth-child(" + (index + 2) + ") td:nth-child(" + (2) + ")").find(".acontainer input").focus();
			} else {


				if ($scope.bomList.length >= 1) {
					if ($scope.bomList[$scope.bomList.length - 1].bom_item_id == "" && $scope.bomList[$scope.bomList.length - 1].bom_item_code == "") {

						$("#stockHead tbody tr:nth-child(" + ($scope.bomList.length) + ") td:nth-child(" + (2) + ")").find(".acontainer input").focus();
						$rootScope.$broadcast('on_AlertMessage_ERR', ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);


					}
					else if ($scope.bomList[$scope.bomList.length - 1].qty == "" || parseFloat($scope.bomList[$scope.bomList.length - 1].qty) <= 0) {

						$("#stockHead tbody tr:nth-child(" + ($scope.bomList.length) + ") td:nth-child(" + (4) + ")").find("#qty").focus();
						$rootScope.$broadcast('on_AlertMessage_ERR', "Please Enter qty");


					}
					/*				else if($scope.bomList[$scope.bomList.length-1].unit_price==""  || parseFloat($scope.bomList[$scope.bomList.length-1].unit_price)<=0)
					{

						$("#stockHead tbody tr:eq("+($scope.bomList.length)+") td:eq("+(5)+")").find("#unit_price").focus();
						$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Bom Rate");


					}*/
					else {
						$scope.bomList.push({
							id: "", bom_item_id: "", qty: "", bom_item_name: "", bom_item_code: "",
							unit_price: parseFloat(0).toFixed(2), last_unit_price: parseFloat(0).toFixed(2)
						});

						//	$("#stockHead tbody tr:eq("+($scope.bomList.length)+") td:eq("+(0)+")").find(".acontainer input").focus();
						$("#ItemId").find(".acontainer input").focus();
						//$("#ItemId .acontainer input").focus();

					}


				}
				else {
					$scope.bomList.push({
						id: "", bom_item_id: "", qty: "", bom_item_name: "", bom_item_code: "",
						unit_price: parseFloat(0).toFixed(2), last_unit_price: parseFloat(0).toFixed(2)
					});
					//	$("#ItemId .acontainer input").focus();
				}


			}
			//	$("#stockHead tbody tr:eq("+($scope.bomList.length)+") td:eq("+(0)+")").find(".acontainer input").focus();
			$("#ItemId").find(".acontainer input").focus();
		}

	}

	//show nutrition list
	$scope.showNutriBomRow = function(index) {
		debugger
		$("#nutri_modal").toggle("modal");

	}

	//	prodcost
	$scope.addProdCostRow = function(index) {

		//if($scope.disable_all == false){
		if ($scope.prodCostList.length >= 1) {
			if ($scope.prodCostList[$scope.prodCostList.length - 1].prod_cost_id == "") {
				$rootScope.$broadcast('on_AlertMessage_ERR', "Please Enter The Production cost Code");

				$("#prodCost tbody tr:nth-child(" + ($scope.prodCostList.length) + ") td:nth-child(" + (2) + ")").find(".acontainer input").focus();

			}
			else if ($scope.prodCostList[$scope.prodCostList.length - 1].rate == "") {
				$rootScope.$broadcast('on_AlertMessage_ERR', "Please Enter The rate");

				$("#prodCost tbody tr:nth-child(" + ($scope.prodCostList.length) + ") td:nth-child(" + (5) + ")").find("#cost_rate").focus();

			}
			else {
				$scope.prodCostList.push({ id: "", prod_cost_id: "", prod_cost_code: "", prod_cost_name: "", prod_cost_type: "", isPercentage: false, rate: parseFloat(0).toFixed(2) });

			}


		}
		else {
			$scope.prodCostList.push({ id: "", prod_cost_id: "", prod_cost_code: "", prod_cost_name: "", prod_cost_type: "", isPercentage: false, rate: parseFloat(0).toFixed(2), flag1: 1 });

		}


		//}

	}

	$scope.removeProdCostRow = function(index) {
		if ($scope.disable_all == false) {

			if ($scope.prodCostList.length == 1) {
				if (index == 0) {
					$rootScope.$broadcast('on_AlertMessage_ERR', ITEM_TABLE_MESSAGES.TABLE_ERR);

				}
			}
			else {
				$scope.prodCostList.splice(index, 1);

			}
		}
	}





	//validation:
	function form_validation(data) {

		var flg = true;


		if (($scope.formData.stock_item_id == "" && $scope.formData.stock_item_code == "" && $scope.formData.stock_item_name == "") || ($scope.formData.stock_item_id == undefined && $scope.formData.stock_item_code == undefined && $scope.formData.stock_item_name == undefined)) {
			$("#form_div_stock_item_code").addClass("has-error");
			$("#form_div_stock_item_code_error").show();
			flg = false;
			$("#form_div_stock_item_code").find(".acontainer input").focus();

		} else {
			$("#form_div_stock_item_code").removeClass("has-error");
			$("#form_div_stock_item_code_error").hide();

		}

		if (($scope.formData.stock_item_qty == "") || ($scope.formData.stock_item_qty == undefined) ||
			parseFloat($scope.formData.stock_item_qty) <= 0) {
			$("#form_div_bom_qty").addClass("has-error");
			$("#form_div_bom_qty_error").show();
			flg = false;

		} else {
			$("#form_div_bom_qty").removeClass("has-error");
			$("#form_div_bom_qty_error").hide();

		}

		if (validation() == false) {
			flg = false;

		}
		else {
			if (flg == true) {

				if ($scope.bomList.length > 0) {
					$scope.item_table_empty = [];
					$scope.item_details_table_empty = [];

					for (var i = 0; i < $scope.bomList.length; i++) {

						if ($scope.bomList[i].bom_item_id != "" && $scope.bomList[i].bom_item_code != "") {
							if ($scope.bomList[i].qty == "" || $scope.bomList[i].qty == undefined
								|| parseFloat($scope.bomList[i].qty) <= 0) {

								$rootScope.$broadcast('on_AlertMessage_ERR', "Please Enter  Qty");
								$("#stockHead tbody tr:nth-child(" + (i + 1) + ")").find("#qty").select();
								flg = false;
								$scope.item_details_table_empty.push($scope.bomList[i]);

								break;

							}

						} else {
							$("#stockHead tr:nth-child(" + (i + 1) + ")  td:nth-child(2)").find(".acontainer input").focus();
							flg = false;
							$scope.item_table_empty.push($scope.bomList[i]);

							$rootScope.$broadcast('on_AlertMessage_ERR', "Please Select An Item");
							break;
						}

					}

				}
				else if ($scope.bomList.length == 0) {
					$("#table_validation_alert").addClass("in");
					$rootScope.$broadcast('on_AlertMessage_ERR', ITEM_TABLE_MESSAGES.TABLE_ERR);
					flg = false;
				}


				if ($scope.prodCostList.length > 0) {

					for (var i = 0; i < $scope.prodCostList.length; i++) {

						if ($scope.prodCostList[i].prod_cost_id != "" && $scope.prodCostList[i].prod_cost_code != "") {
							if ($scope.prodCostList[i].rate == "" || $scope.prodCostList[i].rate == undefined
								|| parseFloat($scope.prodCostList[i].rate) <= 0) {

								$rootScope.$broadcast('on_AlertMessage_ERR', "Please Enter  Rate");
								$("#prodCost tbody tr:nth-child(" + (i + 1) + ")").find("#cost_rate").select();
								flg = false;


								break;

							}

						} else {

							if ($scope.prodCostList.length != 1) {
								$("#prodCost tr:nth-child(" + (i + 1) + ")  td:nth-child(2)").find(".acontainer input").focus();
								flg = false;


								$rootScope.$broadcast('on_AlertMessage_ERR', "Please Select An Item");
								break;
							}
						}

					}


				}






			}
		}

		if (flg == false) {
			focus();
		}
		return flg;
	}


	$scope.alert_for_codeexisting = function(flg) {

		if (flg == 1) {
			//$("#table_validation_alert").show();
			//$("#table_validation_alert").addClass("in");
			$rootScope.$broadcast('on_AlertMessage_ERR', ITEM_TABLE_MESSAGES.CODE_EXISIT);
		}/*else {
				$("#table_validation_alert").hide();
				$("#table_validation_alert").removeClass("in");
			}*/

	}


	$scope.isCodeExistis1 = function(code) {
		$http({
			url: 'codeexisting', method: "GET", params: { code: code }
		}).then(
			function(response) {
				if (response.data == 1) {
					$scope.hide_code_existing_er1 = false;
					$scope.existing_code1 = '"' + code + '" Existing';

				} else if (response.data == 0) {
					$scope.hide_code_existing_er1 = true;
				}
			}, function(response) { // optional
			});
	}





	function reloadData() {
		vm.dtInstance.reloadData(null, true);


	}


	$scope.clear_stock_item_details_editmode = function(event) {
		$scope.itemList.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_id = "";
		$scope.itemList.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_code = "";
		$scope.itemList.items[event.currentTarget.parentElement.rowIndex - 1].stock_item_name = "";
		$scope.itemList.items[event.currentTarget.parentElement.rowIndex - 1].base_item_id = "";
		$scope.itemList.items[event.currentTarget.parentElement.rowIndex - 1].base_bom_item_code = "";
		$scope.itemList.items[event.currentTarget.parentElement.rowIndex - 1].base_bom_item_name = "";
		$scope.itemList.items[event.currentTarget.parentElement.rowIndex - 1].uom = "";
		$scope.itemList.items[event.currentTarget.parentElement.rowIndex - 1].item_category_name = "";




	}
	$scope.clear_stock_details_editmode = function(event) {
		// $scope.bomList[event.currentTarget.parentElement.rowIndex-1].id = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].bom_item_id = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].bom_item_code = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].bom_item_name = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].qty = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].uomcode = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].unit_price = parseFloat(0).toFixed(2);
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].last_unit_price = parseFloat(0).toFixed(2);

	}


	$scope.clear_cost_details_editmode = function(event) {
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].id = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].prod_cost_id = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].prod_cost_code = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].prod_cost_name = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].prod_cost_type = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].isPercentage = false;
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].rate = parseFloat(0).toFixed(2);
	}

	var itemData = $("#stock_item_code").tautocomplete({
		columns: ['id', 'code', 'name', 'item_category_name', 'uom', 'tax_id', 'tax_calculation_method', 'dept_name', 'decimal_places'],
		hide: [false, true, true, false, false, false, false, false, false],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay: 10,
		onchange: function() {
			var selected_item_data = itemData.all();
			$scope.$apply(function() {
				$scope.formData.stock_item_id = selected_item_data.id;
				$scope.formData.stock_item_code = selected_item_data.code;
				$scope.formData.stock_item_name = selected_item_data.name;
				$scope.formData.uom = selected_item_data.uom;
				$scope.formData.item_category_name = selected_item_data.item_category_name;
				$scope.formData.tax_id = selected_item_data.tax_id;
				$scope.formData.dept_name = selected_item_data.dept_name;
				//$scope.back_up_tax_id= selected_item_data.tax_id;

				$scope.formData.tax_calculation_method = (selected_item_data.tax_calculation_method == 0 || selected_item_data.tax_calculation_method == "") ? false : true;
				//$scope.back_up_tax_calculation_method=(selected_item_data.tax_calculation_method==0 || selected_item_data.tax_calculation_method=="")?false:true;
			});
		},
		data: function() {
			var data = item_data;
			var filterData = [];
			var searchData = eval("/" + itemData.searchdata() + "/gi");
			$.each(data, function(i, v) {
				if (v.name.search(new RegExp(searchData)) != -1) {
					filterData.push(v);
				}
			});

			return filterData;
		}
	});

	//	base bom fill data

	var basebomitemData = $("#base_bom_item_code").tautocomplete({
		columns: ['id', 'code', 'name', 'stdQty'],
		hide: [false, true, true, false],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay: 10,
		onchange: function() {
			var selected_basebom_data = basebomitemData.all();
			$scope.$apply(function() {
				$scope.formData.base_item_id = selected_basebom_data.id;
				$scope.formData.base_bom_item_code = selected_basebom_data.code;
				$scope.formData.base_bom_item_name = selected_basebom_data.name;
				$scope.formData.base_bom_qty = selected_basebom_data.stdQty;
				var bomItemWeight = selected_basebom_data.stdQty / $scope.formData.stock_item_qty;
				$scope.formData.bom_item_weight = bomItemWeight;

				/*for(var i=0;i<$scope.bomList.length;i++){
							if($scope.bomList[i].id != ""){
								oldId = $scope.bomList[i].id;
							}
						}*/

				//$scope.formData.base_bom_itemId = oldId;

				setChildValues(selected_basebom_data.id, 1);
				//setItemtableValues(selected_basebom_data.id,1);

				$scope.costPerUnit();
				//bombaseitemData = [];
			});
		},
		data: function() {
			//var data1=[];
			var bombaseitemData = [];
			if ($scope.formData.stock_item_id != "" && $scope.formData.stock_item_id != undefined) {
				for (var i = 0; i < basebomitem_data.length; i++) {
					if ($scope.formData.stock_item_id != basebomitem_data[i].id) {
						bombaseitemData.push(basebomitem_data[i]);
					}
				}
				//data = bombaseitemData;
				//	console.log(data);
			}/*else{
						data = basebomitem_data;
					}*/
			var data = bombaseitemData;
			var filterData = [];
			var searchData = eval("/" + basebomitemData.searchdata() + "/gi");
			$.each(data, function(i, v) {
				if (v.name.search(new RegExp(searchData)) != -1) {
					filterData.push(v);
				}
			});

			return filterData;
		}
	});


	function setChildValues(itmId, isBase) {
		$scope.prograssing = true;
		//$scope.formData.base_item_id = oldId;
		$http({
			method: 'GET',
			url: "getDataToEdit",
			params: {
				itemId: itmId,
				isBase: isBase
			},
			async: false,
		}).success(function(result) {

			$scope.bomList = result.bom;
			$scope.prodCostList = result.prd_costdata;
			for (var i = 0; i < $scope.bomList.length; i++) {
				$scope.bomList[i].qty = parseFloat($scope.bomList[i].qty).toFixed($scope.bomList[i].decimal_places);
				$scope.bomList[i].unit_price = parseFloat($scope.bomList[i].unit_price).toFixed(2);
				$scope.bomList[i].last_unit_price = parseFloat($scope.bomList[i].last_unit_price).toFixed(2);

				$scope.bomList[i].isSet = false;

				//if(action == 1){
				/*if($scope.bomList[i].base_item_id != "" && $scope.bomList[i].base_item_id != undefined){

								for(var j=0; j<basebomitem_data.length; j++){
									if(basebomitem_data[j].id == $scope.bomList[i].base_item_id){
										$scope.formData.base_item_id = basebomitem_data[j].id;
										$scope.formData.base_bom_item_code =  basebomitem_data[j].code;
										$(".base_bom_item_div").find(".acontainer input").val(basebomitem_data[j].code);
										$scope.formData.base_bom_item_name =  basebomitem_data[j].name;
										$scope.formData.base_bom_qty = basebomitem_data[j].stock_item_qty;
										var bomItemWeight = basebomitem_data[j].stock_item_qty / $scope.formData.stock_item_qty;
										$scope.formData.bom_item_weight = bomItemWeight;
									}
								}

							}*/

				/*	for(var i=0;i<$scope.bomList.length;i++){
							if($scope.bomList[i].id != ""){
								oldId = $scope.bomList[i].stock_item_id;
							}
						}*/
				//$scope.formData.base_bom_itemId = itmId;

				//}


			}

			for (var i = 0; i < $scope.prodCostList.length; i++) {
				$scope.prodCostList[i].rate = parseFloat($scope.prodCostList[i].rate).toFixed(2);
				if ($scope.prodCostList[i].is_percentage == 1) { $scope.prodCostList[i].isPercentage = true; } else {
					$scope.prodCostList[i].isPercentage = false;
				}
				$scope.prodCostList[i].isSet = false;
			}
			if ($scope.bomList.length == 0) {
				$scope.bomList.push({
					id: "", bom_item_id: "", qty: "", bom_item_name: "", bom_item_code: "",
					unit_price: parseFloat(0).toFixed(2), last_unit_price: parseFloat(0).toFixed(2)
				});

			}
			if ($scope.prodCostList.length == 0) {
				$scope.prodCostList.push({
					id: "", prod_cost_id: "", prod_cost_code: "", prod_cost_name: "",
					prod_cost_type: "", isPercentage: false, rate: ""
				});
				$("#prodCost .acontainer input").attr('disabled', true);
			}



			$scope.prograssing = false;
		}, function(response) {
			$scope.prograssing = false;
		});

		if (itmId != "" && itmId != undefined) {
			//$scope.formData.base_item_id = $scope.bomList[i].base_item_id;
			for (var j = 0; j < basebomitem_data.length; j++) {
				if (basebomitem_data[j].id == itmId) {
					$scope.formData.base_item_id = basebomitem_data[j].id;
					$scope.formData.base_bom_item_code = basebomitem_data[j].code;
					$(".base_bom_item_div").find(".acontainer input").val(basebomitem_data[j].code);
					$scope.formData.base_bom_item_name = basebomitem_data[j].name;
					$scope.formData.base_bom_qty = basebomitem_data[j].stock_item_qty;
					var bomItemWeight = basebomitem_data[j].stock_item_qty / $scope.formData.stock_item_qty;
					$scope.formData.bom_item_weight = bomItemWeight;
				}
			}

		}

	}

	$scope.getBomItemWeight = function() {
		if ($scope.formData.base_item_id != "" && !isNaN($scope.formData.stock_item_qty) && angular.isNumber(+$scope.formData.stock_item_qty) && $scope.formData.stock_item_qty != 0) {

			$scope.formData.bom_item_weight = $scope.formData.base_bom_qty / $scope.formData.stock_item_qty;
		}

	}

	$scope.stockProdQty = function(index, ev) {

		decimapl_palces = parseInt(decimapl_palces);
		if (ev.which != 8 && $scope.bomList[index].qty > 0) {
			$scope.bomList[index].qty = parseFloat($scope.bomList[index].qty).toFixed(decimapl_palces);
		}
		console.log($scope.bomList[index].qty);
	}
}



mrpApp.directive('tableProductioncost', ['$timeout', function($timeout) {
	return {
		controller: function($scope, $http) {
			$scope.currentIndex1 = 0;
			$("#prodCost tr td").keyup('input', function(e) {
				$scope.currentIndex1 = e.currentTarget.parentElement.rowIndex - 1;

				if (e.which != 9 && e.which != 13 && e.which != 109 && e.which == 8) {
					if (e.currentTarget.cellIndex == 1) {
						$scope.$apply(function() {
							$scope.clear_cost_details_editmode(e);
							$scope.alert_for_codeexisting(0);
						});
					}
				} else if (e.which == 13) {
					if (e.currentTarget.cellIndex == 1) {
						if ($scope.prodCostList[$scope.prodCostList.length - 1].prod_cost_id != "") {
							{
								$("#prodCost tr:nth-child(" + e.currentTarget.parentElement.rowIndex + ") td:nth-child(" + (5) + ")").find("#cost_rate").select();
							}
						}
					}
				} else if (e.which == 9) {
					if (e.currentTarget.cellIndex == 1) {
						{
							$("#prodCost tr:nth-child(" + (e.currentTarget.parentElement.rowIndex) + ") td:nth-child(" + (2) + ")").find(".acontainer input").focus();
							/*$("#items_table tbody tr:nth-child("+e.currentTarget.cellIndex+") td").find(".acontainer input").focus();*/
						}
					}
				}

			});


			$scope.element = [];
			$scope.table_itemsearch_rowindex1 = 0;
			$scope.tableClicked2 = function(index) {
				$scope.table_itemsearch_rowindex1 = index;
			};
			return $scope;
		},
		link: function(scope, elem, attrs, controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns: ['id', 'code', 'name', 'cost_type_name'],
				hide: [false, true, true, false],

				placeholder: "search ..",
				highlight: "hightlight-classname",
				norecord: "No Records Found",
				delay: 10,
				onchange: function() {
					var selected_cost_data = items.all();
					strl_scope.$apply(function() {
						var count = 0;
						for (var i = 0; i < strl_scope.prodCostList.length; i++) {
							if (!strl_scope.prodCostList[i].isDeleted) {
								if (selected_cost_data.id != "") {
									if (i != strl_scope.currentIndex1) {
										if (selected_cost_data.id == strl_scope.prodCostList[i].prod_cost_id) {
											count = 1;
										}
									}
								} strl_scope
							}
						}
						if (count != 1) {

							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_id = selected_cost_data.id;
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_code = selected_cost_data.code;
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_name = selected_cost_data.name;
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_type = selected_cost_data.cost_type_name;
							strl_scope.prodCostList[strl_scope.currentIndex1].rate = parseFloat(0).toFixed(2)

							/* strl_scope.getCostPrice(strl_scope.currentIndex,selected_item_data.id);
							 */

							strl_scope.alert_for_codeexisting(0);
							$timeout(function() { $("#prodCost tbody tr:nth-child(" + strl_scope.currentIndex1 + 1 + ") td").find("#cost_rate").focus(); }, 1);
						} else {
							elem[0].parentNode.lastChild.value = "";
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_id = "";
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_code = "";
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_name = "";
							strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_type = "";
							strl_scope.prodCostList[strl_scope.currentIndex1].rate = parseFloat(0).toFixed(2)

							strl_scope.alert_for_codeexisting(1);
						}

					});
				},
				data: function() {

					var data = strl_scope.prodcost_data;

					var filterData = [];
					var searchData = eval("/^" + items.searchdata() + "/gi");
					$.each(data, function(i, v) {
						if (v.name.search(new RegExp(searchData)) != -1) {
							filterData.push(v);
						}
					});

					return filterData;
				}
			});

			for (var i = 0; i < strl_scope.prodCostList.length; i++) {
				if (strl_scope.formData.stock_item_id != undefined && strl_scope.formData.stock_item_id != '') {
					if (strl_scope.prodCostList[i].isSet == false) {
						elem[0].parentNode.lastChild.value = strl_scope.prodCostList[i].prod_cost_code;
						strl_scope.disable_search_text(elem[0].parentNode.lastChild);
						strl_scope.prodCostList[i].isSet = true; break;

					}
				} else {
					if (strl_scope.prodCostList[i].isSet == false) {
						if (strl_scope.prodCostList[i].po_no != undefined && strl_scope.prodCostList[i].po_no != "") {
							elem[0].parentNode.lastChild.value = strl_scope.prodCostList[i].prod_cost_code;
							strl_scope.prodCostList[i].isSet = true; break;
						}
					}
				}
			}
			if (strl_scope.formData.stock_item_id != "") {
				$timeout(function() {
					$("#prodCost tr:nth-child(" + (strl_scope.prodCostList.length) + ") td:nth-child(" + (2) + ")").find(".acontainer input").select();
				}, 1);
			}
		}
	};
}]);




mrpApp.directive('autocompeteText', ['$timeout', function($timeout) {
	return {
		controller: function($scope, $http) {
			$scope.currentIndex = 0;
			$("#stockHead tr td").keyup('input', function(e) {
				$scope.currentIndex = e.currentTarget.parentElement.rowIndex - 1;

				if (e.which != 9 && e.which != 13 && e.which != 109 && e.which == 8) {
					if (e.currentTarget.cellIndex == 1) {
						$scope.$apply(function() {
							$scope.clear_stock_details_editmode(e);
							$scope.alert_for_codeexisting(0);
						});
					}
				} else if (e.which == 13) {
					if (e.currentTarget.cellIndex == 1) {
						if ($scope.bomList[$scope.bomList.length - 1].bom_item_id != "") {
							{ $("#stockHead tr:nth-child(" + e.currentTarget.parentElement.rowIndex + ") td:nth-child(" + (4) + ")").find("#qty").select(); }
						}
					}
				} else if (e.which == 9) {
					if (e.currentTarget.cellIndex == 1) {


						{


							$("#stockHead tr:nth-child(" + (e.currentTarget.parentElement.rowIndex) + ") td:nth-child(" + (2) + ")").find(".acontainer input").focus();



						}
					}
				}

			});

			$scope.itemsData = $scope.stkdata;
			$scope.element = [];
			$scope.table_itemsearch_rowindex = 0;
			$scope.tableClicked = function(index) {
				debugger
				$scope.table_itemsearch_rowindex = index;
			};
			return $scope;
		},
		link: function(scope, elem, attrs, controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns: ['id', 'code', 'name', 'input_tax_id', 'tax_percentage', 'valuation_method', 'is_active',
					'is_manufactured', 'uomcode', 'uomname', 'decimal_places', 'unit_price', 'cost_price'],
				hide: [false, true, true, false, false, false, false, false, false, false, false, false, false],
				/*columns: ['id','code','name','input_tax_id','tax_percentage','valuation_method','is_active','is_manufactured','uomcode','uomname'],
			hide: [false,true,true,false,false,false,false,false,true,false],*/
				placeholder: "searching ..",
				highlight: "hightlight-classname",
				norecord: "No Records Found",
				delay: 10,
				onchange: function() {
					var selected_item_data = items.all();
					strl_scope.$apply(function() {
						var count = 0;
						for (var i = 0; i < strl_scope.bomList.length; i++) {
							if (!strl_scope.bomList[i].isDeleted) {
								if (selected_item_data.id != "" || selected_item_data.id != undefined) {
									if (i != strl_scope.currentIndex) {
										if (selected_item_data.id == strl_scope.bomList[i].bom_item_id) {
											count = 1;
										}
									}
								} strl_scope
							}
						}
						if (count != 1) {
							strl_scope.bomList[strl_scope.currentIndex].bom_item_id = selected_item_data.id;
							strl_scope.bomList[strl_scope.currentIndex].bom_item_code = selected_item_data.code;
							strl_scope.bomList[strl_scope.currentIndex].bom_item_name = selected_item_data.name;
							strl_scope.bomList[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;
							strl_scope.bomList[strl_scope.currentIndex].decimalPlaces = parseFloat(selected_item_data.decimal_places);
							strl_scope.bomList[strl_scope.currentIndex].unit_price = parseFloat(selected_item_data.cost_price)
								.toFixed(2);
							strl_scope.bomList[strl_scope.currentIndex].last_unit_price = parseFloat(selected_item_data.unit_price)
								.toFixed(2);
							//if(strl_scope.bomList[strl_scope.currentIndex].qty=="NaN"){
							strl_scope.bomList[strl_scope.currentIndex].qty = "";
							//}

							/* strl_scope.getCostPrice(strl_scope.currentIndex,selected_item_data.id);
							 */
							decimapl_palces = selected_item_data.decimal_places;
							//stockProdQty(qty,decimapl_palces);
							$timeout(function() { $("#stockHead tbody tr:nth-child(" + strl_scope.currentIndex + 1 + ") td").find("#qty").focus(); }, 1);

							strl_scope.alert_for_codeexisting(0);
							// $("#stockHead tbody tr:nth-child("+strl_scope.table_itemsearch_rowindex+1+") td").find("#po_qty").focus();
						} else {
							elem[0].parentNode.lastChild.value = "";
							strl_scope.bomList[strl_scope.currentIndex].bom_item_id = "";
							strl_scope.bomList[strl_scope.currentIndex].bom_item_code = "";
							strl_scope.bomList[strl_scope.currentIndex].bom_item_name = "";
							strl_scope.bomList[strl_scope.currentIndex].uomcode = "";
							strl_scope.bomList[strl_scope.currentIndex].unit_price = parseFloat(0).toFixed(2);
							strl_scope.bomList[strl_scope.currentIndex].last_unit_price = parseFloat(0).toFixed(2);
							//  strl_scope.bomList[strl_scope.currentIndex].qty="";
							strl_scope.bomList[strl_scope.currentIndex].qty = parseFloat(0).toFixed(selected_item_data.decimal_places);
							//strl_scope.bomList[strl_scope.currentIndex].qty=parseFloat(strl_scope.bomList[strl_scope.currentIndex].qty).toFixed(selected_item_data.decimal_places);
							strl_scope.alert_for_codeexisting(1);
						}

					});

				},
				data: function() {

					var data = strl_scope.itemsData;

					var filterData = [];
					var searchData = eval("/^" + items.searchdata() + "/gi");
					$.each(data, function(i, v) {
						if (v.name.search(new RegExp(searchData)) != -1) {
							filterData.push(v);
						}
					});

					return filterData;
				}
			});

			for (var i = 0; i < strl_scope.bomList.length; i++) {
				if (strl_scope.formData.stock_item_id != undefined && strl_scope.formData.stock_item_id != '') {
					if (strl_scope.bomList[i].isSet == false) {
						elem[0].parentNode.lastChild.value = strl_scope.bomList[i].bom_item_code;
						strl_scope.disable_search_text(elem[0].parentNode.lastChild);
						strl_scope.bomList[i].isSet = true; break;

					}
				} else {
					if (strl_scope.bomList[i].isSet == false) {
						if (strl_scope.bomList[i].po_no != undefined && strl_scope.bomList[i].po_no != "") {
							elem[0].parentNode.lastChild.value = strl_scope.bomList[i].bom_item_code;
							strl_scope.bomList[i].isSet = true; break;
						}
					}
				}
			}

			$timeout(function() {
				if (strl_scope.formData.stock_item_id != "") {
					$("#stockHead tr:nth-child(" + (strl_scope.bomList.length) + ") td:nth-child(" + (2) + ")").find(".acontainer input").select();
				}
				//$("#form_div_stock_item_code").find(".acontainer input").focus();

			}, 1);

		}
	};
}]);


