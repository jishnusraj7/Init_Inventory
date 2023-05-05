//Controller for Table and Form 
mrpApp.controller('table_location', table_location);

function table_location($controller, $compile, $timeout, $scope, $http,
		$mdDialog, $rootScope, DTOptionsBuilder, DTColumnBuilder, MRP_CONSTANT,
		DATATABLE_CONSTANT, FORM_MESSAGES) {

	$controller('DatatableController', {
		$scope : $scope
	});

	// generate number
	$scope.fun_get_pono = function() {
		$http.get('getCounterPrefix').success(function(response) {

			$scope.formData.code = response;
		});
	}

/*	set_sub_menu("#settings");
	setMenuSelected("#table_location_left_menu"); */// active leftmenu
	manageButtons("add");

	$scope.formData = {};
	$scope.tableData={};
	$scope.servingTablesData={};
	$scope.show_table = true;

	$scope.show_form = false;
	$scope.show_form1 = false;
	$scope.hide_code_existing_er = true;
	$scope.myFile = "";
	$scope.isTableSaveFlag=true;
	$scope.disableTableCode=true;
	
	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.code_existing_validation = code_existing_validation;
	vm.function_clear_select = function_clear_select;
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	$scope.selected = {value: 0};
	$scope.servingTablesByLocId=[];
	$scope.disableDelete=false;

	vm.dtInstance = {};
	var DataObj = {};
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm, DataObj);
	vm.dtColumns = [
			DTColumnBuilder
					.newColumn('code')
					.withTitle('CODE')
					.withOption('type', 'natural')
					.withOption('width', '250px')
					.renderWith(
							function(data, type, full, meta) {
								/* if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"} */

								return '<a id="rcd_edit" class="queTableOutColor" ng-click="show_table = false;show_form=true;show_form1=false;" style="cursor:pointer;"  ng-href="#">'
										+ data + '</a>';
							}),
			DTColumnBuilder.newColumn('name').withTitle('NAME'),

			DTColumnBuilder.newColumn(null).withTitle(' ').renderWith(
							function(data, type, full, meta) {
								// data='<i class="fa fa-th-large " ></i>';
								data = "Table Layout"
								return '<a id="tbl_layout" class="dt-body-center" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
										+ data + '</a>';

							}),

	];

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) { // Rowcallback fun for Get Edit Data when clk on Code
																			
		$('a', nRow).unbind('click');
		$('a', nRow).bind(
				'click',
				function(e) {
					$scope.$apply(function() {
						$('tr.selected').removeClass('selected');
						if (e.target.id == "rcd_edit") {
							var rowData = aData;
							$(nRow).addClass('selected');
							var current_row_index = 0;
							var test = vm.dtInstance.DataTable.rows();
							for (var i = 0; i < test[0].length; i++) {
								if (test[0][i] == vm.dtInstance.DataTable.row(
										".selected").index()) {
									current_row_index = i;
								}
							}
							$("#show_form").val(1);
							edit(rowData, current_row_index, e);
						}
						if (e.target.id == "tbl_layout") {
							var rowData = aData;
							$(nRow).addClass('selected');
							var current_row_index = 0;
							var test = vm.dtInstance.DataTable.rows();
							for (var i = 0; i < test[0].length; i++) {
								if (test[0][i] == vm.dtInstance.DataTable.row(
										".selected").index()) {
									current_row_index = i;
								}
							}
							$("#show_form").val(1);
							imageDragDrop(rowData, current_row_index, e);
						}
					});
				});
		return nRow;
	}

	
	function infoCallback(settings, start, end, max, total, pre) { // function for get page Info
																
		var api = this.api();
		var pageInfo = api.page.info();
		if (pageInfo.pages == 0) {
			return pageInfo.page + " / " + pageInfo.pages;
		} else {
			return pageInfo.page + 1 + " / " + pageInfo.pages;
		}

	}

	$rootScope.$on('reloadDatatable', function(event) { // reload Datatable
		reloadData();
	});

	$rootScope.$on('hide_table', function(event) {
		showTable(event);
	});

	$rootScope.$on('hide_form', function(event) {
		$("#show_form").val(0);
		$scope.show_table = true;
		$scope.isTableSaveFlag=true;
		$scope.show_form = false;
		$scope.show_form1 = false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass(
				'selected');
	});

	function showTable(event) {
		$scope.show_table = false;
		$scope.show_form = true;
		$scope.show_form1 = false;
	}

	function reloadData() {
		vm.dtInstance.reloadData(null, true);
	}

	function edit(row_data, cur_row_index, event) {

		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");// Edit function
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
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
		showTable();
		clearform();
		function_clear_select();
		$rootScope.$emit("show_edit_btn_div", cur_row_index);
		$scope.formData = {
			id : row_data.id,
			name : row_data.name,
			code : row_data.code,
			description : row_data.description,
			sc_amount:row_data.sc_amount,
			apply_service_charge:row_data.apply_service_charge,
		    is_sc_percentage:row_data.is_sc_percentage,
			is_auto_layout:row_data.is_auto_layout
		};
		if($scope.formData.is_auto_layout==1){$scope.is_autoLayout=true;}else{$scope.is_autoLayout=false;}
		if($scope.formData.is_sc_percentage==1){$scope.isPercentage=true;}else{$scope.isPercentage=false;}
		if($scope.formData.apply_service_charge==1){$scope.is_serviceCharge=true;}else{$scope.is_serviceCharge=false;}

		$scope.disable_all = true;
		$scope.disable_code = true;
		$scope.hide_code_existing_er = true;
	}

	function imageDragDrop(row_data, cur_row_index, event) {
		manageButtons("save");
		$('#btnSave').hide();
		/*$('#btnDiscard').hide();
		$('#btnSave').hide();*/
		$("#btnBack").show();
		$scope.show_table = false;
		$scope.show_form = false;
		$scope.show_form1 = true;
		$scope.isTableSaveFlag=false;
		$scope.formData = {
				id : row_data.id,
				name : row_data.name,
				code : row_data.code,
				description : row_data.description,
			};
		$scope.tableData={};
		$scope.servingTablesData=$scope.getServingTablesData(row_data.id);
		$scope.bgImageUrl=row_data.bg_image;
		//alert("bgImage :"+$scope.bgImageUrl);
		/*if($scope.servingTablesByLocId.length !=0)
			{
			$("#servingTable-0").removeClass("whiteBorder");
			$("#servingTable-0").addClass("selectedBorder");
			}*/
	}
	
	$scope.getServingTablesData=function(id)
	{
		$http({
			method : 'GET',
			url : "../servingtables/getImagesbyTableLoc",
		    params:{tableLocId:id}
		}).success(function(result) {
			$scope.servingTablesByLocId = result.servingTabls;
			console.log($scope.servingTablesByLocId);
			//$scope.tableData=$scope.servingTablesByLocId[0];
			
			if($scope.servingTablesByLocId.length != 0)
				{
				$scope.disableTableCode=true;
				$("#servingTable-0").removeClass("whiteBorder");
				$("#servingTable-0").addClass("selectedBorder");
			$scope.tableData.id=$scope.servingTablesByLocId[0].id;
			$scope.tableData.code=$scope.servingTablesByLocId[0].code;
			$scope.tableData.name=$scope.servingTablesByLocId[0].name;
			$scope.tableData.covers=$scope.servingTablesByLocId[0].covers;
			$scope.tableData.layout_image=$scope.servingTablesByLocId[0].layout_image;
			$scope.tableData.height=$scope.servingTablesByLocId[0].height;
			$scope.tableData.width=$scope.servingTablesByLocId[0].width;
			$scope.selectedImageId=$scope.servingTablesByLocId[0].layout_image;
			$scope.selectedImage=$scope.getImagePath($scope.selectedImageId);
			for(var i=0;i<$scope.servingTablesByLocId.length;i++)
				{
				//alert($scope.servingTablesByLocId[i].row_position+"px");
				$scope.servingTablesByLocId[i].row_position1=$scope.servingTablesByLocId[i].row_position+"px";
				$scope.servingTablesByLocId[i].column_position1=$scope.servingTablesByLocId[i].column_position+"px";
				}
			
		}else
			{
			$scope.tableData={};
			$scope.disableTableCode=false;
			if($scope.imageList!= undefined)
				{if($scope.imageList.length != 0){
			$scope.selectedImageId=$scope.imageList[0].id;
			$scope.selectedImage=$scope.imageList[0].image;
			}}}
		});
	}

	$http({
		method : 'GET',
		url : "getImageData"
	}).success(function(result) {
		$scope.imageList = result.tbleImages;
		console.log($scope.imageList);
	});

	// Delete Function
	$rootScope.$on("fun_delete_current_data",function(event) {
						var confirm = $mdDialog.confirm(
								{
									onComplete : function afterShowAnimation() {
										var $dialog = angular.element(document.querySelector('md-dialog'));
										var $actionsSection = $dialog.find('md-dialog-actions');
										var $cancelButton = $actionsSection.children()[0];
										var $confirmButton = $actionsSection.children()[1];
										angular.element($confirmButton).removeClass('md-focused');
										angular.element($cancelButton).addClass('md-focused');
										$cancelButton.focus();
									}
								}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).cancel('No').ok('Yes');
						$mdDialog.show(confirm).then(
										function() {
											var current_row_index = parseInt($(
													".btn_prev").attr("id")
													.split("_")[1]);
											$http({
												url : 'delete',
												method : "POST",
												params : {
													id : $scope.formData.id
												},
											})
											.then(
											function(response) {
												if (response.data == 0) {
														$rootScope.$broadcast('on_AlertMessage_ERR',"Department "+ FORM_MESSAGES.ALREADY_USE+ "");
																}

												else if (response.data == 1) {
													    $rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
													                $("#show_form").val(0);
                                                                    $scope.show_table = true;
																	$scope.show_form = false;
																	$scope.show_form1 = false;
																	manageButtons("add");
																	$scope.disable_all = true;
																	$scope.disable_code = true;
																	vm.dtInstance.reloadData(null,true);
																	vm.dtInstance1.reloadData(null,true);
																	$(".acontainer input").attr('disabled',true);
																	reloadData();
																} else {
																	$mdDialog.show($mdDialog.alert().parent(angular.element(document.querySelector('#dialogContainer')))
																	.clickOutsideToClose(true).textContent("Delete failed.").ok('Ok!').targetEvent(event));
																}

															},
															function(response) { // optional

																$mdDialog.show($mdDialog.alert().parent(angular.element(document.querySelector('#dialogContainer')))
																		.clickOutsideToClose(true).textContent("Delete failed.").ok('Ok!').targetEvent(event));
																				
																				
														});

										}, function() {

										});
					});

	function select_next_prev_row(index) { // set NEXT-PREV Data In Form Atfer
											// Deleteion

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
			$scope.show_table = true;
			$scope.show_form = false;
			$scope.show_form1 = false;
			manageButtons("add");
		}
		return index;

	}

	function view_mode_aftr_edit() {
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div", cur_row_index);
		$scope.disable_all = true;
		$scope.disable_code = true;
	}

	
	function d2(n) {
		if (n < 9)
			return "0" + n;
		return n;
	}
	today = new Date();

	$rootScope.$on('fun_save_data', function(event) { // Save Function
		
		if($scope.isTableSaveFlag==true)
			{
		if (code_existing_validation($scope.formData)) {

		if ($scope.formData.created_by == null
				|| $scope.formData.created_by == undefined
				|| $scope.formData.created_by == "") {
			$scope.formData.created_by = parseInt(strings['userID']);
		}
		if ($scope.formData.created_at == null
				|| $scope.formData.created_at == undefined
				|| $scope.formData.created_at == "") {
			$scope.formData.created_at = today.getFullYear() + "-"
					+ d2(parseInt(today.getMonth() + 1)) + "-"
					+ d2(today.getDate()) + " " + d2(today.getHours()) + ":"
					+ d2(today.getMinutes()) + ":" + d2(today.getSeconds());
		}
		if($scope.formData.id!="" && $scope.formData.id!=undefined)
		{
		$scope.formData.updated_at = today.getFullYear() + "-"
				+ d2(parseInt(today.getMonth() + 1)) + "-"
				+ d2(today.getDate()) + " " + d2(today.getHours()) + ":"
				+ d2(today.getMinutes()) + ":" + d2(today.getSeconds());
		$scope.formData.updated_by = parseInt(strings['userID']);
		}
		if($scope.is_autoLayout==true){$scope.formData.is_auto_layout=1;}else{$scope.formData.is_auto_layout=0;}
		if($scope.isPercentage==true){$scope.formData.is_sc_percentage=1;}else{$scope.formData.is_sc_percentage=0;}
		if($scope.is_serviceCharge==true){$scope.formData.apply_service_charge=1;}else{$scope.formData.apply_service_charge=0;}
		$scope.Quetable.shopId=settings['currentcompanyid1'];
		$scope.Quetable.origin=settings['currentcompanycode1'];
		$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
		
		var data = JSON.stringify({
			itemData : $scope.formData,
			Quetable:$scope.Quetable
		});

		var fdata = new FormData();
		fdata.append("data", data);
		$http.post("savetblLocation", fdata, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).success(
				function(response) {

					if (response == "status:success") {
						var DataObj = {};
						DataObj.alertClass = "alert-box";
						DataObj.alertStatus = false;
						DataObj.divId = "#alertMessageId";
						if ($scope.formData.id != undefined) {
							$rootScope.$broadcast('on_AlertMessage_SUCC',
									FORM_MESSAGES.UPDATE_SUC);
						} else {
							$rootScope.$broadcast('on_AlertMessage_SUCC',
									FORM_MESSAGES.SAVE_SUC);

						}

						if ($scope.formData.id != undefined) {
							view_mode_aftr_edit();
						} else {
							$scope.formData = {};
							$scope.is_autoLayout=false;
							$scope.isPercentage=false;
							$scope.is_serviceCharge=false;
							$scope.departmentIdList = [];
							$scope.fun_get_pono();
						}
						reloadData();
						$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
						$scope.hide_code_existing_er = true;
					} else {

						$mdDialog.show($mdDialog.alert().parent(
								angular.element(document
										.querySelector('#dialogContainer')))
								.clickOutsideToClose(true).textContent(
										"Save failed.").ok('Ok!').targetEvent(
										event));
					}
				}).error(
				function(response) { // optional

					$mdDialog.show($mdDialog.alert().parent(
							angular.element(document
									.querySelector('#dialogContainer')))
							.clickOutsideToClose(true).textContent(
									"Save failed.").ok('Ok!')
							.targetEvent(event));

				});
			}
	}else if($scope.isTableSaveFlag==false)      // Row and Column position update 
		{
		
		
		for(var i=0;i<$scope.servingTablesByLocId.length;i++)
			{
			var str=$scope.servingTablesByLocId[i].row_position1;
			$scope.servingTablesByLocId[i].row_position=str.substring(0,str.length - 2).split(".")[0];
			var str1=$scope.servingTablesByLocId[i].column_position1;
			$scope.servingTablesByLocId[i].column_position=str1.substring(0,str1.length - 2).split(".")[0];
			if(str==""){str="0"}else if(str1==""){str1="0"} 
			}
		console.log($scope.servingTablesByLocId);
		console.log(JSON.stringify($scope.servingTablesByLocId));
		$scope.servingTablesById=JSON.stringify($scope.servingTablesByLocId);
		$scope.servingTablesById=angular.toJson($scope.servingTablesByLocId);
		$http({
			url : '../servingtables/saveServingtableList',
			method : "POST",
			data:$scope.servingTablesById,
		}).then(function(response) {
			
			if(response.data!=0){
				$rootScope.$broadcast('on_AlertMessage_SUCC',
						FORM_MESSAGES.UPDATE_SUC);
				$('#btnSave').hide();
			}else
				{

					 $mdDialog.show($mdDialog.alert()
								.parent(angular.element(document.querySelector('#dialogContainer')))
								.clickOutsideToClose(true)
								.textContent("Save failed.")
								.ok('Ok!')
								.targetEvent(event)
						);
					 //$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
					 $scope.prograssing =false;
				}
		}, function(response) { // optional
			$mdDialog.show($mdDialog.alert()
					.parent(angular.element(document.querySelector('#dialogContainer')))
					.clickOutsideToClose(true)
					.textContent("Save failed.")
					.ok('Ok!')
					.targetEvent(event)
			);
			//$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
			

		})
		//$scope.servingTableImgs.items ={items: []};
		//$scope.servingTableImgs.items=$scope.servingTablesByLocId;
		//$scope.servingTableList=JSON.stringify($scope.servingTableImgs.items);
		/*$scope.tableData.row_position=servingTable.row_position1.substring(0,servingTable.row_position1.length - 2);
		$scope.tableData.column_position=servingTable.column_position1.substring(0,servingTable.column_position1.length - 2);
		$scope.selectedImageId=servingTable.layout_image;*/
		//$scope.saveServingTable();
		/*$scope.isTableSaveFlag=true;*/
		}
	});

	$rootScope.$on("fun_discard_form",
			function(event) { // Discard function
            if($scope.isTableSaveFlag==true){
				var confirm = $mdDialog
						.confirm(
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
								}).title(FORM_MESSAGES.DISCARD_WRNG)
						.targetEvent(event).cancel('No').ok('Yes');
				$mdDialog.show(confirm).then(
						function() {
							var cur_row_index = parseInt($(".btn_prev").attr(
									"id").split("_")[1]);
							if ($scope.formData.id == undefined) {
								$scope.formData = {};
								$scope.fun_get_pono();
								// $scope.formData.parent_id =
								// $scope.sample[0].id;
								$scope.hide_code_existing_er = true;

							} else {
								var row_data = vm.dtInstance.DataTable.rows(
										cur_row_index).data();
								edit(row_data[0], cur_row_index);
							}
							clearform();
							function_clear_select();
						});
	}});

	$rootScope.$on("fun_enable_inputs", function() {
		 $("#show_form").val(1);
		$scope.disable_all = false;
	});

	$rootScope.$on("fun_enable_inputs_code", function() {
		$scope.disable_code = false;
	});

	$rootScope.$on("excel_Print", function() {
		$scope.ExcelSheet();

	});

	$scope.ExcelSheet = function() {
		window.open("../department/excelReport.xls");
	}

	$rootScope.$on("fun_clear_form", function() {
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		function_clear_select();
		$scope.formData = {};
		$scope.tableData={};
		$scope.servingTablesData={};
		$scope.isTableSaveFlag=true;
		$scope.fun_get_pono();
		$scope.hide_code_existing_er = true;
		$scope.servingTableImgs.items ={items: []};
		$scope.is_autoLayout=false;
		$scope.isPercentage=false;
		$scope.is_serviceCharge=false;
		$scope.servingTablesByLocId=[];
		$scope.disableDelete=false;
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
			var selcted_row_data = vm.dtInstance.DataTable.rows(
					dataIndex[0][next_row_index]).data();
			edit(selcted_row_data[0], next_row_index);
			$rootScope.$emit("next_formdata_set", next_row_index);
		}

	});

	$rootScope.$on("fun_prev_rowData", function(e, id) {
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var dataIndex = vm.dtInstance.DataTable.rows();
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if (row_count - 1 == current_row_index) {
			$rootScope.$emit("enable_next_btn");
		}
		var prev_row_index = current_row_index - 1;
		if (row_data[prev_row_index] != undefined) {
			var selcted_row_data = vm.dtInstance.DataTable.rows(
					dataIndex[0][prev_row_index]).data();
			edit(selcted_row_data[0], prev_row_index);
			$rootScope.$emit("next_formdata_set", prev_row_index);
		}
		if (current_row_index - 1 == 0) {
			$rootScope.$emit("disable_prev_btn");
		}

	});

	// Validation

	function code_existing_validation(data) {
		var flg = true;

		var row_data = vm.dtInstance.DataTable.rows().data();
		if (data.id == undefined || data.id == "") {
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#code").select();
			}

		}

		if (validation() == false) {
			flg = false;
		}
		if ($scope.is_serviceCharge == true && ($scope.formData.sc_amount == undefined || $scope.formData.sc_amount=="" )) {
			$("#form_div_amount").addClass("has-error");
			$("#form_div_amount_error").show();
			flg = false;
		}
		else
			{
			$("#form_div_amount").removeClass("has-error");
			$("#form_div_amount_error").hide();

			}
		if( $scope.formData.sc_amount !=undefined){
			if($scope.formData.sc_amount.split(".")[0].length >6){
				$("#form_div_amount").addClass("has-error");
				$("#form_div_amount_error").show();
				flg = false;
			}
			else
			{
			$("#form_div_amount").removeClass("has-error");
			$("#form_div_amount_error").hide();

			}
		}
		if (flg == false) {
			focus();
		}

		return flg;

	}
	function serving_table_data_validation(data) {

		var flg = true;

		if ($scope.tableData.code == undefined || $scope.tableData.code =="" ) {
			$("#tablecode").addClass("has-error");
			flg = false;
		}
		else
			{
			$("#tablecode").removeClass("has-error");

			}
		if ($scope.tableData.name == undefined || $scope.tableData.name =="" ) {
			$("#tablename").addClass("has-error");
			flg = false;
		}
		else
			{
			$("#tablename").removeClass("has-error");
            }
		if ($scope.tableData.covers== undefined || $scope.tableData.covers =="" ) {
			$("#tableseat").addClass("has-error");
			flg = false;
		}
		else
			{
			$("#tableseat").removeClass("has-error");
            }
		
		if (flg == false) {
			focus();
		}

		return flg;

	
		
	}
	
	function function_clear_select() {
		$("#form_div_amount").removeClass("has-error");
		$("#form_div_amount_error").hide();

	}
	$scope.selectImage = function(image) {
		$scope.disableDelete=false;
		$scope.selectedImg= image.image;
		$scope.selectedImgId=image.id;
		/*$scope.selectedImageId=$scope.getImageId(imagePath);*/
		//alert($scope.selectedImgId);
		for(var i=0;i<3;i++){
		if($scope.selectedImgId == $scope.imageList[i].id)
			{
			$scope.disableDelete=true;
			break;
			}
		}
	}
	$scope.getSelectedImage=function()
	{
		if(!isImageExists())
		{
		$scope.selectedImage=$scope.selectedImg;
		$scope.selectedImageId=$scope.selectedImgId;
		}
		//alert($scope.selectedImage + " "+ $scope.selectedImageId);
		//alert($scope.divtop+" "+$scope.divleft);
		//alert($scope.formData.id);
	}
	function isImageExists()
	{
		var isExist=false;
		if($scope.servingTablesByLocId != undefined)
			{
			for(var i=0;i<$scope.servingTablesByLocId.length;i++)
				{
				if($scope.selectedImgId==$scope.servingTablesByLocId[i].layout_image){
					isExist=true;
					break;
				}
				
				}
			}
		return isExist;
	}
	$scope.chooseTableImages = function() {
		$('#addVchrClassModal').modal('toggle');
		$scope.disableDelete=false;

	}

	$scope.chooseTableImage = function() {
		$('#addNewImageModal').modal('toggle');
	}
	$scope.saveTableImage = function() {
		$scope.Quetable.shopId=settings['currentcompanyid1'];
		$scope.Quetable.origin=settings['currentcompanycode1'];
		$scope.Quetable.curdAction="C";
		$scope.imageData = "";
		var file = $scope.myFile;
		//alert($scope.myFile);
		var fdata = new FormData();
		var data = JSON.stringify({
			tableImgData : $scope.imageData,
			file : $scope.myFile,
			Quetable:$scope.Quetable
		});
		fdata.append("imgFile", file);
		fdata.append("data", data);
		
		$http.post("../tableimages/savetblImages", fdata, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).success(
				function(response) {

					if (response.newImg !=null) {
						//console.log(response.newImg);
						$scope.imageList.push(JSON.parse(response.newImg));
						console.log($scope.imageList);
						$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
					} else {

						$mdDialog.show($mdDialog.alert().parent(
								angular.element(document
										.querySelector('#dialogContainer')))
								.clickOutsideToClose(true).textContent(
										"Save failed.").ok('Ok!').targetEvent(
										event));
					}
					$("#imgshw").empty();
					 $("#imgdiv").addClass('fileupload-new').removeClass('fileupload-exists');
				}).error(
				function(response) { // optional

					$mdDialog.show($mdDialog.alert().parent(
							angular.element(document
									.querySelector('#dialogContainer')))
							.clickOutsideToClose(true).textContent(
									"Save failed.").ok('Ok!')
							.targetEvent(event));

				});
	}

	$scope.getImageId=function(imagePath)
	{
		for(var i=0;i<$scope.imageList.length;i++)
			{
			if($scope.imageList[i].image==imagePath)
				{
				return $scope.imageList[i].id;
				}
			}
	}
	$scope.getImagePath=function(id)
	{
		for(var i=0;i<$scope.imageList.length;i++)
			{
			if($scope.imageList[i].id==id)
				{
				return $scope.imageList[i].image;
				}
			}
	}
	
	
	$scope.divtop=0;
	$scope.divleft=0;
	
	$scope.saveServingTable=function()
	{
		if(serving_table_data_validation($scope.tableData)){
		$scope.Quetable.shopId=settings['currentcompanyid1'];
		$scope.Quetable.origin=settings['currentcompanycode1'];
		$scope.Quetable.curdAction=($scope.tableData.id=="" || $scope.tableData.id==undefined )?"C":"U";
		if($scope.tableData.id == undefined || $scope.tableData.id == "")
		{
		$scope.tableData.created_by = parseInt(strings['userID']);
		$scope.tableData.created_at = today.getFullYear() + "-"
					+ d2(parseInt(today.getMonth() + 1)) + "-"
					+ d2(today.getDate()) + " " + d2(today.getHours()) + ":"
					+ d2(today.getMinutes()) + ":" + d2(today.getSeconds());
		}else
			{
			/*for(var i =0;i<$scope.servingTablesByLocId.length;i++)
	    	{
				if($scope.tableData.id ==$scope.servingTablesByLocId[i].id)
					{
					$scope.tableData.row_position=$scope.servingTablesByLocId[0].row_position;
					$scope.tableData.column_position=$scope.servingTablesByLocId[0].column_position;
					break;
					}
	    	}*/
			}
		$scope.tableData.serving_table_location_id=$scope.formData.id;
		$scope.tableData.layout_image=$scope.selectedImageId;
		
		var data1 = JSON.stringify({
			tblData :$scope.tableData,
			Quetable:$scope.Quetable
		});

		var fdata = new FormData();
		fdata.append("data", data1);
		$http.post("../servingtables/saveServngTable", fdata, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).success(
				function(response) {
		
					if (response.tablDta !=null) {
						console.log(response.tablDta);
						var DataObj = {};
						DataObj.alertClass = "alert-box";
						DataObj.alertStatus = false;
						DataObj.divId = "#alertMessageId";
						if ($scope.tableData.id != undefined) {
							$rootScope.$broadcast('on_AlertMessage_SUCC',
									FORM_MESSAGES.UPDATE_SUC);
							
						} else {
							$rootScope.$broadcast('on_AlertMessage_SUCC',
									FORM_MESSAGES.SAVE_SUC);
						}
						if($scope.isTableSaveFlag==false && $scope.servingTablesByLocId !=undefined)
	                       {
							var json=JSON.parse(response.tablDta);
							json.image=$scope.getImagePath(json.layout_image);
							if($scope.tableData.id == undefined)
								{
	                             $scope.servingTablesByLocId.push(json);
								}else
									{   
							        	   for(var i=0;i<$scope.servingTablesByLocId.length;i++)
							        		   {
							        		   if(json.id.toString()==$scope.servingTablesByLocId[i].id){
							        			   $scope.servingTablesByLocId=$scope.servingTablesByLocId.filter(function(obj)
															{
														     return obj.id != json.id;
															}
													);
							        			   $scope.servingTablesByLocId.push(json);
							        			   break;
							        		   }
							        		   }
							               
							           
									}
							
	                         console.log($scope.servingTablesByLocId);
	                        // $scope.selectedInCanvas(response.tablDta);
	                        // $scope.getServingTablesData( $scope.servingTablesByLocId[0].serving_table_location_id);
	                          }
						if ($scope.tableData.id != undefined) {
							//view_mode_aftr_edit();
						} else {
							$scope.tableData = {};
							
						}
						reloadData();
						$scope.hide_code_existing_er = true;
						$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
					} else {

						$mdDialog.show($mdDialog.alert().parent(
								angular.element(document
										.querySelector('#dialogContainer')))
								.clickOutsideToClose(true).textContent(
										"Save failed.").ok('Ok!').targetEvent(
										event));
					}
				}).error(
				function(response) { // optional

					$mdDialog.show($mdDialog.alert().parent(
							angular.element(document
									.querySelector('#dialogContainer')))
							.clickOutsideToClose(true).textContent(
									"Save failed.").ok('Ok!')
							.targetEvent(event));

				});
	}
	}
	
	$scope.selectedInCanvas=function(servingTable)
	{
		$scope.tableData={};
		//alert(servingTable.id);
		//alert(servingTable.id+" "+servingTable.row_position1 +" "+servingTable.column_position1);
		$("#servingTable-"+$scope.selected.value).removeClass("whiteBorder");
		$("#servingTable-"+$scope.selected.value).addClass("selectedBorder");
		for(var i=0;i<$scope.servingTablesByLocId.length;i++)
			{
			if(i !=$scope.selected.value)
				{
				$("#servingTable-"+i).removeClass("selectedBorder");
				}
			}
		$scope.tableData.id=servingTable.id;
		$scope.tableData.code=servingTable.code;
		$scope.tableData.name=servingTable.name;
		$scope.tableData.covers=servingTable.covers;
		$scope.tableData.layout_image=servingTable.layout_image;
		$scope.tableData.row_position =servingTable.row_position;
		$scope.tableData.row_position1=servingTable.row_position+'px';
		$scope.tableData.column_position =servingTable.column_position;
		$scope.tableData.column_position1 =servingTable.column_position+'px';
		$scope.tableData.height=servingTable.height;
		$scope.tableData.width=servingTable.width;
		$scope.tableData.created_at=servingTable.created_at;
		$scope.tableData.created_by=servingTable.created_by;
		$scope.selectedImage= servingTable.image;
		$scope.selectedImageId=$scope.getImageId(servingTable.image);
		
	}
	$scope.addServingTable=function()
	{
		$scope.tableData={};
		$scope.disableTableCode=false;
		
	}
	$scope.changeServiceCharge=function()
	{
		if($scope.is_serviceCharge == false)
			{
			$scope.formData.sc_amount ="";
			}
	}
	//$scope.testurl="/mrp_uploads/bgImages/img2.jpg";
	
	$scope.updateBgImage=function()
	{

		$scope.bgImageData = "";
		var file = $scope.myFile1;
		alert($scope.myFile1);
		alert($scope.formData.id);
		var fdata = new FormData();
		var data = JSON.stringify({
			bgImgData : $scope.bgImageData,
			file : $scope.myFile1
		});
		fdata.append("imgFile", file);
		fdata.append("data", data);

		$http.post("../tablelocation/saveBgImage", fdata, {
			transformRequest : angular.identity,
			params:{id:$scope.formData.id},
			headers : {
				'Content-Type' : undefined
			}
		}).success(
				function(response) {

					if (response.newBgImage !=null) {
						console.log(response.newBgImage);
						$scope.bgImageUrl=response.newBgImage[0].bg_image;
						reloadData();
						$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
					} else {

						$mdDialog.show($mdDialog.alert().parent(
								angular.element(document
										.querySelector('#dialogContainer')))
								.clickOutsideToClose(true).textContent(
										"Save failed.").ok('Ok!').targetEvent(
										event));
					}
				}).error(
				function(response) { // optional

					$mdDialog.show($mdDialog.alert().parent(
							angular.element(document
									.querySelector('#dialogContainer')))
							.clickOutsideToClose(true).textContent(
									"Save failed.").ok('Ok!')
							.targetEvent(event));

				});
	
	}
	
	$scope.deleteServingtable = function()
	{
		//Delete Function

		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).ok(
		'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);	
		//	alert($scope.tableData.code);
			$http({
				url : '../servingtables/delete',
				method : "POST",
				params : {id:$scope.tableData.id},
			}).then(function(response) {
				
				 if(response.data !=0){
					 console.log(response.data.deletdId);
					 var deletedId=response.data.deletdId;
					$scope.servingTablesByLocId=$scope.servingTablesByLocId.filter(function(obj)
							{
						     return obj.id != deletedId;
							}
					);
					
					$scope.getServingTablesData($scope.formData.id);
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
					
				}
				else
					{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
					}
				
			}, function(response) { // optional
				
				{
					 $mdDialog.show($mdDialog.alert()
								.parent(angular.element(document.querySelector('#dialogContainer')))
								.clickOutsideToClose(true)
								.textContent("Delete failed.")
								.ok('Ok!')
								.targetEvent(event)
						);
					}
				
			});});
	}
	
	$scope.deleteBgImage=function()
	{
		//Delete background Function
		$scope.Quetable.shopId=settings['currentcompanyid1'];
		$scope.Quetable.origin=settings['currentcompanycode1'];
		$scope.Quetable.curdAction="U";
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).ok(
		'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);	
		//	alert($scope.tableData.code);
			$http({
				url : '../tablelocation/deleteBgImage',
				method : "POST",
				params : {id:$scope.formData.id},
				data : {Quetable:$scope.Quetable}
			}).then(function(response) {
				
				 if(response.data !=0){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
					
				}
				else
					{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
					}
				 $scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
			}, function(response) { // optional
				
				{
					 $mdDialog.show($mdDialog.alert()
								.parent(angular.element(document.querySelector('#dialogContainer')))
								.clickOutsideToClose(true)
								.textContent("Delete failed.")
								.ok('Ok!')
								.targetEvent(event)
						);
					}
				
			});});
	
	}
	
	$scope.deleteFromImageList=function()
	{

		//Delete Function


		
		
		$http({
			url : '../tableimages/delete',
			method : "POST",
			params : {id:$scope.selectedImgId},
		}).then(function(response) {
			if(response.data==0)
			{
				$rootScope.$broadcast('on_AlertMessage_ERR','Image '+FORM_MESSAGES.ALREADY_USE);

			}else if(response.data ==1){

				 
				 var deletedId=$scope.selectedImgId;
				$scope.imageList=$scope.imageList.filter(function(obj)
						{
					     return obj.id != deletedId;
						}
				);
				console.log($scope.imageList);
				
				$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
				$scope.getServingTablesData($scope.formData.id);
			
			}
			
			
			else
				{
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Delete failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
				}
			
		}, function(response) { // optional
			
			{
				 $mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
				}
			
		});
		
	
	}
	
}
mrpApp.directive('fileModel', [ '$parse', function($parse) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
} ]);

mrpApp.directive('draggable', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs,e) {
        
        	
            element.draggable({
            	 
                cursor: "move",
                containment: "#border",
                stop: function (event, ui) {
           
                	
                	scope.servingTable.row_position1=ui.helper[0].offsetLeft+'px';
                	scope.servingTable.row_position=ui.helper[0].offsetLeft;
                	scope.servingTable.column_position1=ui.helper[0].offsetTop+'px';
                	scope.servingTable.column_position=ui.helper[0].offsetTop;
                	$('#btnSave').show();
                	/*for(var i =0;i<scope.servingTablesByLocId.length;i++)
                	{
                		if(i !=scope.$index){
                		var xleftTop=parseInt(scope.servingTablesByLocId[i].row_position);
                		var xrightTop=xleftTop+parseInt(scope.servingTablesByLocId[i].width);
                		var xleftBottom=xleftTop;
                		var xrightBottom=xrightTop;
                		var yleftTop=parseInt(scope.servingTablesByLocId[i].column_position);
                		var yrightTop=yleftTop;
                		var yleftBottom=yleftTop+parseInt(scope.servingTablesByLocId[i].height);
                		var yrightBottom=yleftBottom;
                		if(scope.servingTable.row_position>=xleftTop && scope.servingTable.row_position<=xrightTop
                				&& scope.servingTable.column_position>=yleftTop && scope.servingTable.column_position<=yleftBottom)
                			{        
                			break;
                			}
                		
                	}}*/
                	 
                },
                
               
            });
          
        }
    };
});
/*mrpApp.directive('backImg', function () {
	
	return function(scope, element, attrs){
        var url = attrs.backImg;
        element.css({
            'background-image': 'url(' + url +')',
            'background-size' : 'cover'
        });
    };
});*/

