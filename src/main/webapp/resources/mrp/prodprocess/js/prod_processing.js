mrpApp.controller('prodprcess', prodprcess);

function prodprcess($compile, $controller, $scope, $interval, $timeout, $http,
		$mdDialog, $rootScope, DTOptionsBuilder, DTColumnBuilder, MRP_CONSTANT,
		DATATABLE_CONSTANT, $q, $window, FORM_MESSAGES, ITEM_TABLE_MESSAGES,
		RECORD_STATUS, $filter) {

	$scope.getDropDownDatas = function() {
		$http({
			url : 'dropdownprocess',
			async : false,
			method : "GET",
		}).then(function(response) {
			debugger
			$scope.shop = response.data.shop;
			console.log("value===========================================>"+$('#cust').val())
			console.log($scope.shop);
			$scope.customerIds = response.data.customer;
			$scope.timeSlot = response.data.timeSlot;
			$scope.departments = response.data.departments;
			$scope.stkData = response.data.itmData;
			// console.log($scope.stkData);
			$scope.customerIds.splice(0, 0, {
				id : "0",
				name : "Select Customer"
			});
			// $scope.timeSlot.splice(0,0,{id : "" ,name : "ALL"});
			
			$scope.customerIds1 = "0";
			//$scope.department_id = "0";
			
			$scope.fillData = response.data.shop;
			// $scope.fillData.splice(0,0,{id : "" ,name : "ALL"});
			$scope.shop.splice(0, 0, {
				id : "0",
				name : "Select Shop"
			});
			$scope.timeSlot.splice(0, 0, {
				id : "0",
				name : "Select Time"
			});
			$scope.departments.splice(0, 0, {
				id : "0",
				name : "Select Department"
			});
			$scope.customerIds1=$scope.fillData[0].value;
			$scope.time_slot_id = $scope.timeSlot[0].id;
			$scope.department_id=$scope.departments[0].id;
		});
	}

	$scope.itemList = [];
	$scope.invoice = {
		items : []
	};
	$("tbody").hide();
	$scope.totalItemOrders = function() {
		$scope.selectAll = false;
		if ($scope.formData.closing_date != ""
				&& $scope.formData.closing_date != undefined) {
			$("#form_div_delevery_date").removeClass("has-error");
			$("#form_div_scheduledate_error").hide();
			var productionDate;
			productionDate = {
				delevery_date : getMysqlFormat($scope.formData.closing_date),
				customerID : $scope.customerIds1,
				departmentID : $scope.departments_id,
			};

			$http({
				url : 'totalItemOrders',
				async : false,
				params : productionDate,
				method : "GET",
			}).then(function(response) {

				$scope.itemList = response.data.totalItemList;
				// console.log($scope.itemList);
				for ( var i = 0; i < $scope.itemList.length; i++) {
					$scope.itemList[i].check_val = false;
				}

			});

		}

		else {

			$("#form_div_delevery_date").addClass("has-error");
			$("#form_div_scheduledate_error").show();
			$scope.itemList = [];
		}

	}

	function getShopIdFromCode() {
		var compId = "";
		for ( var i = 0; i < $scope.shop.length; i++) {
			if ($scope.shop[i].code == $scope.companyId) {
				compId = $scope.shop[i].shop_id;
			}
		}
		return compId;
	}

	$scope.itemListData = [];
	$scope.itemWithDeptChange = function() {
		debugger
		console.log($scope.fillData);
        for(var i=0;i<$scope.fillData.length;i++){
        	if($scope.customerIds1!=null){
        		if($scope.fillData[i].code==$scope.customerIds1){
            		$scope.customerIdTosave = $scope.fillData[i].id;
            	}
        	}
        	else{
        		$scope.customerIdTosave = 0;
        	}
        	
        }

        if($scope.customerIds1!="CNTRL1"){
        	productionDate = {
			delevery_date : getMysqlFormat($scope.formData.closing_date),
			customerID : $scope.customerIdTosave,
			departmentID : $scope.departments_id,
		};
        }
        else{
        	productionDate = {
			delevery_date : getMysqlFormat($scope.formData.closing_date),
			customerID : $scope.customerIds1,
			departmentID : $scope.departments_id,
		};
        }
		

		$http({

			url : 'orderDetailsWithDept',
			async : false,
			params : productionDate,
			method : "GET",
		})
				.then(
						function(response) {

							$scope.itemList = [];
							$scope.itemListData = response.data.totalItemListData;
							console.log($scope.itemListData);
							console.log("ithu value===========================================>"+$('#cust').val())
							
							if($('#cust').val()=='?' || $('#cust').val()==''|| $('#cust').val()=='undefined:undefined'||typeof($('#cust').val())  === "undefined"){
										$("tbody").hide();
									}else {
										$("tbody").show();
									}
							for ( var i = 0; i < $scope.itemListData.length; i++) {
								$scope.itemListData[i].check_val = false;
								var objItemList = {
									check_val : $scope.itemListData[i].check_val,
									order_no : $scope.itemListData[i].order_no,
									department : $scope.itemListData[i].department,
									dept_id : $scope.itemListData[i].dept_id,
									sale_item_id : $scope.itemListData[i].sale_item_id,
									stock_item_name : $scope.itemListData[i].sub_class_name,
									shop_code : $scope.itemListData[i].shop_code,
									qty : parseFloat($scope.itemListData[i].qty)
											.toFixed(settings['decimalPlace']),
									closing_time_slot_id : $scope.itemListData[i].closing_time_slot_id,
									timeslot : $scope.itemListData[i].timeslot
								};
								$scope.itemList.push(objItemList);
							}
							/*
							 * if($scope.itemListData.length==0){
							 * 
							 * var body = "<tr>"; body += "<td> NO DATA
							 * AVAILABLE </td>"; $("#orders_list
							 * tbody").append(body); }
							 */

						})
	}
	$scope.showMaterialRequisition = false;
	$scope.formData = {};
	$scope.disable_all = false;
	$scope.customerIds = [];
	$scope.stkCodeList = [];
	$scope.stockoutFormflag = 0;
	destDeptData = [];
	$scope.inOrderList = [];
	$scope.ProductionList = [];

	var todayDate = dateForm(new Date());
	$scope.formData.prod_date = todayDate;
	$scope.prodDate = todayDate;
	$scope.formData.closing_date = todayDate;
	$scope.slctypreq = 0;
	$scope.getDropDownDatas();

	$scope.customerIds1 = "0";
	$scope.ProductionList.push({
		id : "",
		stock_item_id : "",
		stock_item_code : "",
		uomcode : "",
		department_name : "",
		currentStock : 0.00,
		prod_qty : "",
		damageqty : "",
		order_qty : 0,
		cost_price : "",
		remarks : "",
		itemMaterialCost : 0,
		otherCost : 0
	});

	$scope.totalItemOrders();
	
	if($('#cust').val()=='?' || $('#cust').val()==''|| $('#cust').val()=='undefined:undefined'||typeof($('#cust').val())  === "undefined"){
				$("tbody").hide();
			}else {
				$("tbody").show();
			}
	
	var vm = this;
	vm.form_validation = form_validation;
	vm.fun_get_refno = fun_get_refno;
	vm.code_existing_validation = code_existing_validation;

	$scope.itemsData = [];
	$http({
		url : 'formJsonData',
		method : "GET",
		async : false,
	}).then(function(response) {
		$scope.itemsData = response.data.inProductionItemsData;
		// $scope.itemDeptData=response.data.itmData;
		// console.log($scope.itemDeptData);
	}, function(response) { // optional

	});

	$scope.sort = function(keyname) {
		$scope.sortKey = keyname; // set the sortKey to the param passed
		$scope.reverse = !$scope.reverse; // if true make it false and vice
		// versa
	}

	$scope.setSuc_AlertMessageModal = function(event, msg) {
		$scope.succ_alertMessageStatusModal = false;
		$scope.succ_alertMeaasgeModal = msg;
		$timeout(function() {
			$scope.succ_alertMessageStatusModal = true;
		}, 1500);
	}
	$scope.setErr_AlertMessageModal = function(event, msg) {
		$scope.err_alertMessageStatusModal = false;
		$scope.err_alertMeaasgeModal = msg;
		$timeout(function() {
			$scope.err_alertMessageStatusModal = true;
		}, 1500);
	}

	var prodcost_data = [];
	$scope.prodcost_data = [];

	$http({
		url : '../prodcost/costTypeList',
		method : "GET",
		async : false,
	}).then(function(response) {
		prodcost_data = response.data.data;
		$scope.prodcost_data = response.data.data;

	}, function(response) { // optional
	});

	$scope.orderDetails = function(itemlst) {
		$scope.itmName = itemlst.stock_item_name;
		$scope.totalorder = parseFloat(itemlst.qty).toFixed(
				settings['decimalPlace']);

		$http({
			url : 'getShopDataProcess',
			method : "GET",
			params : {
				closing_date : getMysqlFormat($scope.formData.closing_date),
				customer_id : $scope.customerIds1,
				stock_item_id : itemlst.sale_item_id,
				time_slot_id : itemlst.closing_time_slot_id,
			},

		}).then(
				function(response) {
					$scope.shopOrder = response.data.shopOrder;
					for ( var i = 0; i < $scope.shopOrder.length; i++) {
						$scope.shopOrder[i].qty = parseFloat(
								$scope.shopOrder[i].qty).toFixed(
								settings['decimalPlace']);

					}
					$('#orderDataSplit').modal('toggle');
				});

	}

	// check box function

	$scope.selectAll = false;

	$scope.toggleAll = function(selectAll) {
		for ( var i = 0; i < $scope.itemList.length; i++) {
			$scope.itemList[i].check_val = selectAll;
		}
	}

	$scope.toggleOne = function(item) {
		$scope.selectAll = true;
		for ( var i = 0; i < $scope.itemList.length; i++) {
			if ($scope.itemList[i].check_val == false) {
				$scope.selectAll = false;
			}

		}
	}

	$scope.sendToProduction = function() {
		if ($scope.validDate()) {
			//console.log($(cust).val());
		if($('#cust').val()=='?' || $('#cust').val()=='undefined:undefined'){
			//console.log("fsdfdsfsd======"+$scope.slctypreq)
			if($scope.slctypreq=='1'){
				$rootScope
				.$broadcast(
						'on_AlertMessage_ERR',
						FORM_MESSAGES.CUST_VAL_ERR);				
			}
			else{
				$rootScope
				.$broadcast(
						'on_AlertMessage_ERR',
						FORM_MESSAGES.SHOP_VAL_ERR);
			}
			
			
		}
		else{
			if (form_validation($scope.itemsList)) {

				
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
								})
						.title(
								"Do You Want To Send These Items  To Production?")
						.targetEvent(event).cancel('No').ok('Yes');
				$mdDialog
						.show(confirm)
						.then(
								function() {
									var details;
									details = {
										close_date : getMysqlFormat($scope.formData.closing_date),
										customerID : $scope.customerIds1,
										prod_date : getMysqlFormat($scope.formData.prod_date)
									};

									$http({
										url : 'updateStatusProcess',
										method : "POST",
										async : false,
										params : details,
										data : $scope.itemList
									})
											.then(
													function(response) {
														if (response.data == 1) {
															$rootScope
																	.$broadcast(
																			'on_AlertMessage_SUCC',
																			FORM_MESSAGES.UPDATE_SUC);
															// $scope.setSuc_AlertMessageModal('setSuc_AlertMessageModal',FORM_MESSAGES.UPDATE_SUC);
														
															$scope
																	.totalItemOrders();
															if($('#cust').val()=='?' || $('#cust').val()==''|| $('#cust').val()=='undefined:undefined'||typeof($('#cust').val())  === "undefined"){
																$("tbody").hide();
															}else {
																$("tbody").show();
															}
															
														} else {
															$mdDialog
																	.show($mdDialog
																			.alert()
																			.parent(
																					angular
																							.element(document
																									.querySelector('#dialogContainer')))
																			.clickOutsideToClose(
																					true)
																			.textContent(
																					"FAIL")
																			.ok(
																					'Ok!')
																			.targetEvent(
																					event));
															vm.dtInstance
																	.reloadData();
														}
													});
								});
			} else {
				/*
				 * $mdDialog.show($mdDialog.alert().parent(
				 * angular.element(document .querySelector('#dialogContainer')))
				 * .clickOutsideToClose(true).textContent( "Please adjust atleat
				 * one.").ok('Ok!').targetEvent( event));
				 */
				// $scope.setSuc_AlertMessageModal('setErr_AlertMessageModal',"please
				// select atleast one");
				$rootScope.$broadcast('on_AlertMessage_ERR',
						"please select atleast one");
			}
			
		}


		}
	}

	$scope.editDamageQty = function(index) {
		if ($scope.disable_all == false) {
			$scope.damageqty = $scope.ProductionList[index].damageqty;
			$scope.currentDamageIndex = index;
			$('#damageQtyPopup').modal('toggle');

			/* $timeout(function () { */
			$("#damageqty1").select();
			/* }, 1); */
		}
	}

	$scope.addDamageQty = function() {
		if ($scope.damageqty != '' && $scope.damageqty != undefined
				&& $scope.damageqty != null) {
			$scope.ProductionList[$scope.currentDamageIndex].damageqty = $scope.damageqty;
		} else {
			$scope.ProductionList[$scope.currentDamageIndex].damageqty = 0;
		}
	}

	$scope.showCostDataDetails = function(index, item) {
		/*
		 * if($scope.disable_all == false) {
		 */
		if (item.stock_item_id != undefined && item.stock_item_id != "") {
			$('#costDataSplit').modal('toggle');
			$scope.materialCost1 = $scope.ProductionList[index].itemMaterialCost;
			$scope.otherCost1 = $scope.ProductionList[index].otherCost;
			$scope.showTotalCost = $scope.setTotalCost(item);
		} else {
			$scope.setSuc_AlertMessageModal('setErr_AlertMessageModal',
					"Please Select An Item");
		}
		// }
	}

	$scope.getMaterialAndOtherCost = function(itemid) {

		$scope.costMatrlOther = [];
		for ( var i = 0; i < $scope.costData.length; i++) {
			if ($scope.costData[i].stock_id == itemid) {
				$scope.costMatrlOther[0] = $scope.costData[i].material_cost;
				$scope.costMatrlOther[1] = $scope.costData[i].other_cost;
				break;
			} else {
				$scope.costMatrlOther[0] = "";
				$scope.costMatrlOther[1] = "";
			}
		}

		for ( var i = 0; i < $scope.costMatrlOther.length; i++) {
			$scope.costMatrlOther[i] = ($scope.costMatrlOther[i] == "") ? 0
					: $scope.costMatrlOther[i];
			$scope.costMatrlOther[i] = parseFloat($scope.costMatrlOther[i])
					.toFixed(settings['decimalPlace']);
		}
		return $scope.costMatrlOther;
	}

	// Edit And Save Bom Data
	$scope.editBomData = function(index, item) {
		/*
		 * if($scope.disable_all==false) {
		 */
		$("#bom_qty").css("border-color", "#d2d6de");
		$scope.selectedStkItemId = $scope.ProductionList[index].stock_item_id;
		$scope.selectedStkItemName = $scope.ProductionList[index].stock_item_name;
		if (item.stock_item_id != undefined && item.stock_item_id != "") {
			$('#orderData').modal('toggle');
			$http({
				method : 'GET',
				url : "getBomAndProdData",
				params : {
					itemId : item.stock_item_id
				},
				async : false,
			})
					.success(
							function(result) {
								$scope.bomDetailsList = result.bom;
								$scope.prodCostList = result.prd_costdata;
								$scope.bomList = $scope.bomDetailsList;
								for ( var i = 0; i < $scope.bomList.length; i++) {
									$scope.bomList[i].qty = parseFloat(
											$scope.bomList[i].qty).toFixed(
											settings['decimalPlace']);
									$scope.bomList[i].unit_price = parseFloat(
											$scope.bomList[i].unit_price)
											.toFixed(2);
									$scope.bomList[i].isSet = false;

								}
								for ( var i = 0; i < $scope.prodCostList.length; i++) {
									$scope.prodCostList[i].rate = parseFloat(
											$scope.prodCostList[i].rate)
											.toFixed(2);
									if ($scope.prodCostList[i].is_percentage == 1) {
										$scope.prodCostList[i].isPercentage = true;
									} else {
										$scope.prodCostList[i].isPercentage = false;
									}
									$scope.prodCostList[i].isSet = false;
								}
								if (result.bomQty.length != 0
										&& result.bomQty[0].bom_qty != undefined
										&& result.bomQty[0].bom_qty != "") {
									$scope.bomQty = parseFloat(
											result.bomQty[0].bom_qty).toFixed(
											settings['decimalPlace']);
								} else {
									$scope.bomQty = 0;
								}
								if ($scope.bomList.length == 0) {
									$scope.bomList.push({
										id : "",
										bom_item_id : "",
										qty : "",
										bom_item_name : ""
									});
									$("#rowAdd").show();
									$("#AddrowHd").hide();

								}
								if ($scope.prodCostList.length == 0) {
									$scope.prodCostList.push({
										id : "",
										prod_cost_id : "",
										prod_cost_code : "",
										prod_cost_name : "",
										prod_cost_type : "",
										isPercentage : false,
										rate : ""
									});
								}
								if ($scope.formData.status == 1
										|| $scope.disable_all == true) {
									$timeout(function() {
										$("#stockHead .acontainer input").attr(
												'disabled', true);
										$("#prodCost .acontainer input").attr(
												'disabled', true);
									}, 1);

								} else {
									$("#stockHead .acontainer input").attr(
											'disabled', false);
									$("#prodCost .acontainer input").attr(
											'disabled', false);
								}

								$timeout(
										function() {
											for ( var i = 0; i < $scope.bomList.length; i++) {
												if ($scope.bomList[i].bom_item_code != ""
														&& $scope.bomList[i].bom_item_code != undefined) {
													$(
															"#stockHead > tbody tr:eq("
																	+ i + ") ")
															.find(
																	".acontainer input")
															.val(
																	$scope.bomList[i].bom_item_code);
												}
											}

											for ( var i = 0; i < $scope.prodCostList.length; i++) {
												if ($scope.prodCostList[i].prod_cost_code != ""
														&& $scope.prodCostList[i].prod_cost_code != undefined) {
													$(
															"#prodCost > tbody tr:eq("
																	+ i + ") ")
															.find(
																	".acontainer input")
															.val('pppp');
												}
											}

										}, 1);

							});

		} else {
			$rootScope.$broadcast('on_AlertMessage_ERR',
					"Please Select An Item");
		}
		// }
	}

	$scope.addBomRow = function(index) {

		/*
		 * if(index<$scope.bomList.length-1){ $("#stockHead tbody
		 * tr:nth-child("+(index+2)+") td:nth-child("+(2)+")").find(".acontainer
		 * input").focus(); }else{
		 */

		if ($scope.bomList.length >= 1) {
			if ($scope.bomList[$scope.bomList.length - 1].bom_item_id == "") {
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						"Please Enter The Item Code");
				$(
						"#stockHead tbody tr:nth-child("
								+ ($scope.bomList.length) + ") td:nth-child("
								+ (2) + ")").find(".acontainer input").focus();

			} else if ($scope.bomList[$scope.bomList.length - 1].qty == "") {
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						"Please Enter The Item Qty");
				$(
						"#stockHead tbody tr:nth-child("
								+ ($scope.bomList.length) + ") td:nth-child("
								+ (4) + ")").find("#qty").focus();

			} else {
				$scope.bomList.push({
					id : "",
					bom_item_id : "",
					qty : "",
					bom_item_name : ""
				});

			}

		} else {
			$scope.bomList.push({
				id : "",
				bom_item_id : "",
				qty : "",
				bom_item_name : ""
			});
		}

		// }

	}

	$scope.removeBomRow = function(index) {
		if ($scope.disable_all == false) {
			if ($scope.bomList.length == 1) {
				if (index == 0) {
					$rootScope.$broadcast('on_AlertMessage_ERR',
							ITEM_TABLE_MESSAGES.TABLE_ERR);

				}
			} else {
				$scope.bomList.splice(index, 1);

			}
		}

	}

	$scope.addProdCostRow = function(index) {

		// if($scope.disable_all == false){
		if ($scope.prodCostList.length >= 1) {
			if ($scope.prodCostList[$scope.prodCostList.length - 1].prod_cost_id == "") {
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						"Please Enter The Production cost Code");
				$(
						"#prodCost tbody tr:nth-child("
								+ ($scope.prodCostList.length)
								+ ") td:nth-child(" + (2) + ")").find(
						".acontainer input").focus();

			} else if ($scope.prodCostList[$scope.prodCostList.length - 1].rate == "") {
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						"Please Enter The rate");
				$(
						"#prodCost tbody tr:nth-child("
								+ ($scope.prodCostList.length)
								+ ") td:nth-child(" + (5) + ")").find(
						"#cost_rate").focus();

			} else {
				$scope.prodCostList.push({
					id : "",
					prod_cost_id : "",
					prod_cost_code : "",
					prod_cost_name : "",
					prod_cost_type : "",
					isPercentage : false,
					rate : ""
				});

			}

		} else {
			$scope.prodCostList.push({
				id : "",
				prod_cost_id : "",
				prod_cost_code : "",
				prod_cost_name : "",
				prod_cost_type : "",
				isPercentage : false,
				rate : "",
				flag1 : 1
			});

		}

		// }

	}
	$scope.removeProdCostRow = function(index) {
		if ($scope.disable_all == false) {

			if ($scope.prodCostList.length == 1) {
				if (index == 0) {
					$rootScope.$broadcast('on_AlertMessage_ERR',
							ITEM_TABLE_MESSAGES.TABLE_ERR);

				}
			} else {
				$scope.prodCostList.splice(index, 1);

			}
		}
	}

	$scope.clear_bom_details_editmode = function(event) {
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].id = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].bom_item_id = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].bom_item_code = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].bom_item_name = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].qty = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].uomcode = "";
		$scope.bomList[event.currentTarget.parentElement.rowIndex - 1].unit_price = "";
	}
	$scope.clear_cost_details_editmode = function(event) {
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].id = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].prod_cost_id = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].prod_cost_code = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].prod_cost_name = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].prod_cost_type = "";
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].isPercentage = false;
		$scope.prodCostList[event.currentTarget.parentElement.rowIndex - 1].rate = "";
	}

	$scope.disable_search_text = function(elemenvalue) {
		if ($scope.disable_all == true) {
			$(elemenvalue).attr("disabled", true);
		}
	}

	$scope.updateBomData = function() {
		if (bomValidation() && prodCostValidation()) {
			// alert($scope.selectedStkItemId);
			$scope.Quetable = {
				id : "",
				dateTime : "",
				sysSaleFlag : true,
				syncNow : 0,
				curdAction : "U"
			};
			$scope.Quetable.shopId = settings['currentcompanyid1'];
			$scope.Quetable.origin = settings['currentcompanycode1'];
			var data = JSON.stringify({
				bomData1 : $scope.bomList,
				prodCostList : $scope.prodCostList,
				Quetable : $scope.Quetable
			});

			var fdata = new FormData();
			fdata.append("data", data);
			$http
					.post("updateBomData", fdata, {
						transformRequest : angular.identity,
						params : {
							stkId : $scope.selectedStkItemId,
							bomQty : $scope.bomQty,
							prodCostList : $scope.prodCostList
						},
						headers : {
							'Content-Type' : undefined
						}
					})
					.success(
							function(response) {
								if (response.data != 0) {
									$scope.setSuc_AlertMessageModal(
											'setSuc_AlertMessageModal',
											FORM_MESSAGES.UPDATE_SUC);

									$http({
										url : 'getUpdateRate',
										async : false,
										method : "GET",
									})
											.then(
													function(response) {
														$scope.stkData = response.data.itmsData;
													});

									$scope
											.getUpdatedCostData($scope.selectedStkItemId);

								}
							})
					.error(
							function(response) { // optional

								$mdDialog
										.show($mdDialog
												.alert()
												.parent(
														angular
																.element(document
																		.querySelector('#dialogContainer')))
												.clickOutsideToClose(true)
												.textContent("Update failed.")
												.ok('Ok!').targetEvent(event));

							});
			// $("#importDataModal").toggle();
		}
	}

	$scope.getUpdatedCostData = function(itemId) {
		$http({
			url : 'getUpdatedCost',
			method : "GET",
			async : false,
		}).then(
				function(response) {
					$scope.costData = response.data.costData;
					$scope.getMaterialAndOtherCost(itemId);
					for ( var i = 0; i < $scope.ProductionList.length; i++) {
						if ($scope.ProductionList[i].stock_item_id == itemId) {
							$scope.ProductionList[i].itemMaterialCost = $scope
									.getMaterialAndOtherCost(itemId)[0];
							$scope.ProductionList[i].otherCost = $scope
									.getMaterialAndOtherCost(itemId)[1];
						}
					}
				}, function(response) { // optional
				});
	}

	function bomValidation() {
		var flg = true;
		$("#bom_qty").css("border-color", "#d2d6de");

		if ($scope.bomList.length == 1) {

			if ($scope.bomQty == "" && $scope.bomQty == "0"
					&& $scope.bomList[0].bom_item_id != "") {
				$("#bom_qty").css("border-color", "red");

				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						'Enter the Standard Production Quantity');
				// $rootScope.$broadcast('on_AlertMessage_ERR','Enter the
				// Standard Production Quantity');

				flg = false;

			} else if ($scope.bomList[0].bom_item_id == "") {

				$(
						"#stockHead tbody tr:nth-child("
								+ ($scope.bomList.length) + ") td:nth-child("
								+ (2) + ")").find(".acontainer input").focus();
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						'Enter the Item ');
				// $rootScope.$broadcast('on_AlertMessage_ERR','Enter the
				// Standard Production Quantity');

				flg = false;

			}

			else if ($scope.bomQty != "" && $scope.bomQty != "0"
					&& $scope.bomList[0].bom_item_id != ""
					&& $scope.bomList[0].qty == "") {
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						ITEM_TABLE_MESSAGES.QTY_ERR);
				// $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
				$scope.selectedIndex = 1;
				$(
						"#stockHead tbody tr:nth-child("
								+ ($scope.bomList.length) + ") td:nth-child("
								+ (4) + ")").find("#qty").focus();

				flg = false;
			}

			else if ($scope.bomQty != "" && $scope.bomQty != "0"
					&& $scope.bomList[0].bom_item_id == ""
					&& $scope.prodCostList[0].prod_cost_id == "") {
				// $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$(
						"#stockHead tbody tr:nth-child("
								+ ($scope.bomList.length) + ") td:nth-child("
								+ (2) + ")").find(".acontainer input").focus();

				flg = false;

			} else if ($scope.bomList[0].bom_item_id != ""
					&& $scope.bomList[0].bom_item_id != undefined
					&& $scope.bomList[0].qty == "") {
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						ITEM_TABLE_MESSAGES.QTY_ERR);

				// $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
				$scope.selectedIndex = 1;
				$(
						"#stockHead tbody tr:nth-child("
								+ ($scope.bomList.length) + ") td:nth-child("
								+ (4) + ")").find("#qty").focus();
				flg = false;
			} else if ($scope.bomList[0].bom_item_id == ""
					&& $scope.bomList[0].qty != "") {
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				// $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$scope.selectedIndex = 1;
				$(
						"#stockHead tbody tr:nth-child("
								+ ($scope.bomList.length) + ") td:nth-child("
								+ (2) + ")").find(".acontainer input").focus();
				flg = false;
			}

			else if ($scope.bomList[0].bom_item_id != ""
					&& $scope.bomList[0].bom_item_id != undefined) {
				if ($scope.bomQty == "" || $scope.bomQty == "0") {

					$("#bom_qty").css("border-color", "red");
					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
							'Enter the Standard Production Quantity');
					// $rootScope.$broadcast('on_AlertMessage_ERR','Enter the
					// Standard Production Quantity');
					$scope.disable_all == false;
					flg = false;

				}
			}

		}

		else if ($scope.bomList.length > 1) {
			for ( var i = 0; i < $scope.bomList.length; i++) {
				if ($scope.bomList[i].bom_item_id == ""
						|| $scope.bomList[i].bom_item_id == undefined) {

					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
							ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					// $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					$(
							"#stockHead tbody tr:nth-child(" + (i + 1)
									+ ") td:nth-child(" + (2) + ")").find(
							".acontainer input").focus();
					$scope.selectedIndex = 1;
					flg = false;
					break;
				}

				else if ($scope.bomList[i].qty == ""
						|| $scope.bomList[i].qty == undefined) {

					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
							ITEM_TABLE_MESSAGES.QTY_ERR);

					// $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
					$(
							"#stockHead tbody tr:nth-child(" + (i + 1)
									+ ") td:nth-child(" + (4) + ")").find(
							"#qty").focus();
					$scope.selectedIndex = 1;
					flg = false;
					break;
				}

				else if ($scope.bomQty == "" || $scope.bomQty == "0") {

					$("#bom_qty").css("border-color", "red");
					// $rootScope.$broadcast('on_AlertMessage_ERR','Enter the
					// Standard Production Quantity');

					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
							'Enter the Standard Production Quantity');

					flg = false;
					break;

				}

			}

		}
		return flg;
	}

	function prodCostValidation() {

		var flg = true;
		if ($scope.prodCostList.length == 1) {

			if ($scope.prodCostList[0].prod_cost_id != ""
					&& $scope.prodCostList[0].rate == "") {
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						ITEM_TABLE_MESSAGES.COST_ERR);

				// $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_ERR);
				$scope.selectedIndex = 1;
				$(
						"#prodCost tbody tr:nth-child("
								+ ($scope.prodCostList.length)
								+ ") td:nth-child(" + (5) + ")").find(
						"#cost_rate").focus();

				flg = false;

			} else if ($scope.prodCostList[0].prod_cost_id != ""
					&& ($scope.bomQty == "" || $scope.bomQty == 0 || $scope.bomQty == undefined)) {
				$("#bom_qty").css("border-color", "red");
				$scope.setErr_AlertMessageModal('on_AlertMessage_ERR',
						'Enter the Standard Production Quantity');
				flg = false;
			}

			else if ($scope.prodCostList[0].prod_cost_id == ""
					&& $scope.prodCostList[0].rate != "") {
				// $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
						ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);

				$scope.selectedIndex = 1;
				$(
						"#prodCost tbody tr:nth-child("
								+ ($scope.prodCostList.length)
								+ ") td:nth-child(" + (2) + ")").find(
						".acontainer input").focus();
				flg = false;

			}
			if (($scope.prodCostList[0].rate == "" || $scope.prodCostList[0].rate == undefined)
					&& $scope.prodCostList[0].prod_cost_id != "") {
				if ($scope.prodCostList[0].isPercentage == false) {
					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
							ITEM_TABLE_MESSAGES.COST_ERR);

					// $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_ERR);
				} else {

					$rootScope.$broadcast('on_AlertMessage_ERR',
							ITEM_TABLE_MESSAGES.COST_PERCNTGE_ERR);
				}
				flg = false;
				$scope.selectedIndex = 1;
				$(
						"#prodCost tbody tr:nth-child("
								+ ($scope.prodCostList.length)
								+ ") td:nth-child(" + (5) + ")").find(
						"#cost_rate").focus();

			} else if (($scope.prodCostList[0].isPercentage == true
					&& $scope.prodCostList[0].rate > 100 && $scope.prodCostList[0].prod_cost_id != "")
					|| ($scope.prodCostList[0].isPercentage == false && $scope.prodCostList[0].rate
							.split(".")[0].length > 10)) {
				$rootScope.$broadcast('on_AlertMessage_ERR',
						ITEM_TABLE_MESSAGES.PERCENT_LIMIT_ERR);
				flg = false;
				$scope.selectedIndex = 1;
				$(
						"#prodCost tbody tr:nth-child("
								+ ($scope.prodCostList.length)
								+ ") td:nth-child(" + (5) + ")").find(
						"#cost_rate").focus();

			}

		} else if ($scope.prodCostList.length > 1) {
			for ( var i = 0; i < $scope.prodCostList.length; i++) {
				if ($scope.prodCostList[i].prod_cost_id == ""
						|| $scope.prodCostList[i].prod_cost_id == undefined) {
					$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
							ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					$scope.selectedIndex = 1;
					$(
							"#prodCost tbody tr:nth-child(" + (i + 1)
									+ ") td:nth-child(" + (2) + ")").find(
							".acontainer input").focus();

					flg = false;
					break;
				}

				else if (($scope.prodCostList[i].rate == "" || $scope.prodCostList[i].rate == undefined)
						&& $scope.prodCostList[0].prod_cost_id != "") {
					if ($scope.prodCostList[i].isPercentage == false) {
						$scope.setErr_AlertMessageModal(
								'setSuc_AlertMessageModal',
								ITEM_TABLE_MESSAGES.COST_ERR);

						// $rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.COST_ERR);
					} else {
						// $scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',ITEM_TABLE_MESSAGES.COST_PERCNTGE_ERR);

						$rootScope.$broadcast('on_AlertMessage_ERR',
								ITEM_TABLE_MESSAGES.COST_PERCNTGE_ERR);
					}
					flg = false;
					$scope.selectedIndex = 1;
					$(
							"#prodCost tbody tr:nth-child(" + (i + 1)
									+ ") td:nth-child(" + (5) + ")").find(
							"#cost_rate").focus();
					break;

				}
				if ((($scope.prodCostList[i].isPercentage == true && $scope.prodCostList[i].rate > 100) || ($scope.prodCostList[i].isPercentage == false && $scope.prodCostList[i].rate
						.split(".")[0].length > 10))
						&& $scope.prodCostList[0].prod_cost_id != "") {
					$rootScope.$broadcast('on_AlertMessage_ERR',
							ITEM_TABLE_MESSAGES.PERCENT_LIMIT_ERR);
					flg = false;
					$scope.selectedIndex = 1;
					$(
							"#prodCost tbody tr:nth-child(" + (i + 1)
									+ ") td:nth-child(" + (5) + ")").find(
							"#cost_rate").focus();
					break;

				}

			}

		}
		return flg;

	}

	$scope.alert_for_codeexisting = function(flg) {
		if (flg == 0) {
		} else if (flg == 1) {
			$rootScope.$broadcast('on_AlertMessage_ERR',
					ITEM_TABLE_MESSAGES.CODE_EXISIT);
		}

	}

	$scope.alert_for_codeexisting2 = function(flg) {
		if (flg == 1) {
			$scope.setErr_AlertMessageModal('setSuc_AlertMessageModal',
					'code Exist');
		}
	}

	$scope.materialList = [];

	$scope.getDepartment = function(id) {
		for ( var i = 0; i < department_data.length; i++) {
			if (id == department_data[i].id) {
				return department_data[i].code;
			}
		}
	}

	var department_data = [];
	$http({
		url : '../stockin/depList',
		method : "GET",
		async : false,
	}).then(function(response) {
		department_data = response.data.data;
	}, function(response) { // optional
	});

	$scope.myClickEvent1 = function() {
		$scope.selectAll = false;
		// $scope.selectAllInprod=false;
		$scope.formData.closing_date = todayDate;
		$scope.formData.prod_date = todayDate;
		$scope.customerIds1 = "0";
		
		$scope.customerIds1=$scope.fillData[0].value;
		//$scope.getDropDownDatas();
		
		$scope.totalItemOrders();	
		if($('#cust').val()=='?' || $('#cust').val()==''|| $('#cust').val()=='undefined:undefined'||typeof($('#cust').val())  === "undefined"){
			$("tbody").hide();
		}else {
			$("tbody").show();
		}
		$("#form_div_prodDate").removeClass("has-error");
		$("#form_div_prodDate_error").hide();

	}

	// $scope.myClickEvent3=function()
	// {
	// if ($('.acontainer').length){

	// $('.acontainer:get(1)').remove();
	// }

	// var todayDate = new Date();
	// $scope.formData.prod_date=todayDate;
	// $scope.formData.stock_request_date = todayDate;

	// if($("#cmbn_mode").val() == 1){
	// $("#form_div_source_code").show();

	// srcDeptData = [];
	// var sourceData = $("#source_code")
	// .tautocomplete(
	// {
	// columns : [ 'id', 'code', 'name' ],
	// hide : [ false, true, true ],
	// placeholder : "search..",
	// highlight : "hightlight-classname",
	// norecord : "No Records Found",
	// delay : 10,
	// onchange : function() {
	// var selected_source_data = sourceData.all();

	// $scope
	// .$apply(function() {
	// $scope.formData.source_department_id = selected_source_data.id;
	// $scope.formData.source_code = selected_source_data.code;
	// $scope.formData.source_name = selected_source_data.name;
	// //$("#from_department_name").val(selected_source_data.name);

	// });

	// },
	// data : function() {

	// var data = department_data;

	// var filterData = [];
	// var searchData = eval("/"
	// + sourceData.searchdata() + "/gi");
	// $.each(
	// data,
	// function(i, v) {
	// if (v.code.search(new RegExp(
	// searchData)) != -1
	// || v.name
	// .search(new RegExp(
	// searchData)) != -1) {

	// //filterData.push(v);
	// if ($scope.formData.dest_department_id == undefined) {
	// filterData.push(v);
	// } else {
	// if ($scope.formData.dest_department_id != v.id) {
	// filterData.push(v);
	// }
	// }

	// }
	// });

	// return filterData;
	// },

	// });

	// }else{
	// $("#form_div_source_code").hide();
	// }

	// destDeptData = [];
	// var destinationData = $("#dest_code")
	// .tautocomplete(
	// {
	// columns : [ 'id', 'code', 'name' ],
	// hide : [ false, true, true ],
	// placeholder : "search..",
	// highlight : "hightlight-classname",
	// norecord : "No Records Found",
	// delay : 10,
	// onchange : function() {
	// var selected_department_data = destinationData.all();

	// $scope
	// .$apply(function() {
	// $scope.formData.dest_department_id = selected_department_data.id;
	// $scope.formData.dest_code = selected_department_data.code;
	// $scope.formData.desti_name = selected_department_data.name;
	// destDeptData = [];
	// });

	// },
	// data : function() {

	// var data = department_data;

	// var filterData = [];
	// var searchData = eval("/"
	// + destinationData.searchdata() + "/gi");
	// $.each(
	// data,
	// function(i, v) {
	// if (v.code.search(new RegExp(
	// searchData)) != -1
	// || v.name
	// .search(new RegExp(
	// searchData)) != -1) {

	// //filterData.push(v);
	// if ($scope.formData.source_department_id == undefined) {
	// filterData.push(v);
	// } else {
	// if ($scope.formData.source_department_id != v.id) {
	// filterData
	// .push(v);
	// }
	// }

	// }
	// });

	// return filterData;
	// },

	// });

	// $(document).on('keyup', '#form_div_source_code input', function(e) {

	// if (e.which != 9 && e.which != 13) {
	// $scope.$apply(function() {
	// $scope.formData.source_department_id = "";
	// $scope.formData.source_code = "";
	// $scope.formData.source_name = "";

	// });
	// }
	// });

	// $(document).on('keyup','#form_div_desti_code input',function(e) {

	// if (e.which != 9 && e.which != 13 && e.which == 8) {
	// $scope.$apply(function() {
	// $scope.formData.dest_department_id = "";
	// $scope.formData.desti_name = "";
	// $scope.formData.dest_code = "";
	// $scope.formData.pending = false;
	// clearItemsTable();
	// });
	// }
	// });

	// fun_get_refno();

	// clearItemsTable();

	// $scope.total = function() {
	// var total = 0;
	// angular.forEach($scope.invoice.items, function(item) {
	// total += item.delivered_qty * item.unit_price;
	// })
	// return parseFloat(total).toFixed(settings['decimalPlace']);
	// }

	// $scope.amount = function(item) {

	// return parseFloat(item).toFixed(settings['decimalPlace']);
	// }

	// $scope.addItem = function(index, id) {
	// if ($scope.disable_all == false) {

	// if ($scope.invoice.items.length != 0) {
	// if ($scope.invoice.items[$scope.invoice.items.length - 1].stock_item_id
	// != ""
	// && $scope.invoice.items[$scope.invoice.items.length - 1].stock_item_code
	// != "") {
	// $scope.invoice.items.push({
	// id : "",
	// stock_item_id : "",
	// stock_item_code : "",
	// stockRegDtl_id : "",
	// request_qty : 0,
	// delivered_qty : 0,
	// pendingStock : 0,
	// unit_price : 0
	// });

	// //$("#items_table1 > tbody tr").eq(1).find(".acontainer
	// input").val('cccc');

	// } else {
	// if ($scope.invoice.items[$scope.invoice.items.length - 1].stock_item_id
	// == ""
	// && $scope.invoice.items[$scope.invoice.items.length - 1].stock_item_code
	// == "") {
	// $rootScope.$broadcast(
	// 'on_AlertMessage_ERR',
	// ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
	// $(
	// "#items_table1 tr:nth-child("
	// + ($scope.invoice.items.length + 1)
	// + ")").find(
	// ".acontainer input").focus();
	// }
	// }

	// }
	// }
	// },

	// $scope.removeRowItem = function(index) {

	// if ($scope.invoice.items.length != 1) {
	// $scope.invoice.items.splice(index, 1);
	// $(
	// "#items_table1 tr:nth-child("
	// + ($scope.invoice.items.length + 1) + ")")
	// .find("#request_qty").select();

	// } else {
	// $rootScope.$broadcast('on_AlertMessage_ERR',
	// "Atleast one item is required.");
	// }
	// }

	// $scope.amount = function(item) {

	// return parseFloat(item).toFixed(settings['decimalPlace']);
	// }

	// $("#printstore1").click(function(){
	// //alert($("#cmbn_mode").val());
	// $scope.savedata();

	// //if($scope.formData.dest_department_id != "" &&
	// $scope.formData.dest_department_id != undefined){
	// //$window.open("printStore?pdfExcel=pdf&reportName=BOM
	// DETAILS&req_no="+$scope.formData.stockTransfeNo+"", '_blank');
	// //}

	// });

	// $scope.showPending = function(pending){

	// if(pending == true){
	// if($scope.formData.dest_department_id != "" &&
	// $scope.formData.dest_department_id != undefined){

	// $scope.getpendingMaterial($scope.formData.dest_department_id);
	// $timeout(
	// function() {

	// if($scope.stkCodeList.length != 0){
	// for(var i=0; i < $scope.stkCodeList.length; i++ ){
	// var index = i+2;

	// $("#items_table1 > tbody tr:nth-child("+index+") ").find(".acontainer
	// input").val($scope.stkCodeList[i]);

	// }
	// }

	// }, 100);

	// }

	// }else{

	// clearItemsTable();

	// }
	// }

	// }

	$scope.getpendingMaterial = function(department) {

		$http({
			url : 'getPendingMaterial',
			method : "GET",
			async : false,
			params : {
				department : department
			},
		})
				.then(
						function(response) {
							$scope.materialStockList = response.data.pendingList;

							$scope.invoice = {
								items : []
							};

							for ( var j = 0; j < $scope.materialStockList.length; j++) {

								if ($scope.materialStockList[j].pending_qty != 0) {

									$scope.pendingStockList
											.push($scope.materialStockList[j]);

								}

							}

							if ($scope.pendingStockList.length != 0) {

								for ( var i = 0; i < $scope.pendingStockList.length; i++) {

									$scope.invoice.items.push({
										id : "",
										stock_item_id : "",
										stock_item_code : "",
										stockRegDtl_id : "",
										request_qty : 0,
										delivered_qty : 0,
										pendingStock : 0,
										unit_price : 0
									});

									$scope.invoice.items[i].uomcode = $scope.pendingStockList[i].uom_code;
									$scope.invoice.items[i].stock_item_id = $scope.pendingStockList[i].stock_item_id;
									$scope.invoice.items[i].stock_item_code = $scope.pendingStockList[i].stock_item_code;
									$scope.invoice.items[i].stock_item_name = $scope.pendingStockList[i].stock_item_name;
									$scope.invoice.items[i].currentStock = $scope
											.getItemmasterCurrentStock(
													$scope.pendingStockList[i].stock_item_id,
													$scope.formData.source_department_id,
													$scope.formData.stock_request_date,
													i);
									$scope.invoice.items[i].pendingStock = $scope.pendingStockList[i].pending_qty;
									$scope.invoice.items[i].unit_price = parseFloat(
											$scope.pendingStockList[i].cost_price)
											.toFixed(2);

									$scope.stkCodeList
											.push($scope.pendingStockList[i].stock_item_code);

								}

							} else {

								clearItemsTable();
							}

						}, function(response) { // optional

						});

		$scope.materialStockList = [];
		$scope.pendingStockList = [];
		$scope.stkCodeList = [];

	}

	function fun_get_refno() {
		$http.get('getStkrqstCounterPrefix').success(function(response) {
			$scope.formData.stockTransfeNo = response;
			$("#ref_code").prop('disabled', true);
		});
	}

	$scope.stockDtlList = [];
	$scope.reportData = [];
	var result;
	$scope.savedata = function() {

		result = $scope.formData.stockTransfeNo;
		if ($scope.invoice.items.length != 0) {
			if (code_existing_validation($scope.formData)) {
				/*
				 * if ($scope.invoice.items[$scope.invoice.items.length -
				 * 1].stock_item_id != "" &&
				 * $scope.invoice.items[$scope.invoice.items.length -
				 * 1].stock_item_code != "") {
				 */
				for ( var i = 0; i < $scope.invoice.items.length; i++) {
					$scope.invoice.items[i].amount = $scope.invoice.items[i].delivered_qty
							* $scope.invoice.items[i].unit_price;
					$scope.stockDtlList.push($scope.invoice.items[i]);
				}

				// $scope.stockDtlList.push({amount:"0.0"});
				$scope.formData.stockTransferDate = getMysqlFormat($scope.formData.stock_request_date);
				$scope.formData.stockRequestDate = getMysqlFormat($scope.formData.stock_request_date);
				if ($scope.formData.source_department_id != ""
						&& $scope.formData.source_department_id != undefined) {
					$scope.formData.fromDepartmentId = $scope.formData.source_department_id;
				} else {
					$scope.formData.fromDepartmentId = 2;
				}
				$scope.formData.toDepartmentId = $scope.formData.dest_department_id;
				$scope.formData.reqStatus = 0;
				$scope.formData.stockDetailLists = JSON
						.stringify($scope.stockDtlList);

				console.log("before sending====ProdProcessing===========>"+$scope.formData)
				// var materialData =
				// JSON.stringify({stockDtlList:$scope.stockDtlList,
				// formvalues:$scope.formData});

				$http({
					// url : 'saveMaterialData',
					url : '../stockout/saveStockItem',
					method : "POST",
					data : $scope.formData,
					async : false,
				})
						.then(
								function(response) {
									if (response.data == 1) {

										$rootScope.$broadcast(
												'on_AlertMessage_SUCC',
												FORM_MESSAGES.SAVE_SUC);
										fun_get_refno();

										clearItemsTable();

										$("#form_div_source_code").find(
												".acontainer input").val("");

										$("#form_div_desti_code").find(
												".acontainer input").val("");

										$scope.formData.source_department_id = "";
										$scope.formData.source_code = "";
										$scope.formData.source_name = "";

										$scope.formData.dest_department_id = "";
										$scope.formData.dest_code = "";
										$scope.formData.desti_name = "";

										$window
												.open(
														"printStore?pdfExcel=pdf&reportName=BOM DETAILS&req_no="
																+ result + "",
														'_blank');
									} else {
										res = 0;
										$mdDialog
												.show($mdDialog
														.alert()
														.parent(
																angular
																		.element(document
																				.querySelector('#dialogContainer')))
														.clickOutsideToClose(
																true)
														.textContent(
																"Save failed.")
														.ok('Ok!').targetEvent(
																event));
									}
								}, function(response) { // optional
									$scope.alertBox(FORM_MESSAGES.SAVE_ERR);
								});

			}

		}

	}

	function clearItemsTable() {
		$scope.invoice = {
			items : []
		};
		$scope.invoice.items.push({
			id : "",
			stock_item_id : "",
			stock_item_code : "",
			stockRegDtl_id : "",
			request_qty : 0,
			delivered_qty : 0,
			pendingStock : 0,
			stock_item_batch_id : "",
			stock_item_batch_stock : 0,
			unit_price : 0
		});
	}

	function code_existing_validation(data) {

		var flg = true;
		if (validation() == false) {
			flg = false;
		}
		if ($scope.formData.stock_request_date == "") {
			$("#form_div_stock_request_date").addClass("has-error");
			$("#form_div_stock_request_date_error").show();
			flg = false;
			$("#stock_request_date").focus();
			return false;

		} else {
			$("#form_div_stock_request_date").removeClass("has-error");
			$("#form_div_stock_request_date_error").hide();
		}

		if ($("#cmbn_mode").val() == 1) {
			if ($scope.formData.source_department_id == ""
					|| $scope.formData.source_department_id == undefined
					&& $scope.formData.source_code == ""
					|| $scope.formData.source_code == undefined
					&& $scope.formData.source_name == ""
					|| $scope.formData.source_name == undefined) {
				$("#form_div_source_code").addClass("has-error");
				$("#form_div_source_code_error").show();
				flg = false;
				$("#form_div_source_code").find(".acontainer input").focus();
				return false;
			} else {
				$("#form_div_source_code").removeClass("has-error");
				$("#form_div_source_code_error").hide();
			}
		}

		if (($scope.formData.dest_department_id == "" || $scope.formData.dest_department_id == undefined)
				&& ($scope.formData.dest_code == "" || $scope.formData.dest_code == undefined)
				&& ($scope.formData.desti_name == "" || $scope.formData.desti_name == undefined)) {

			$("#form_div_desti_code").addClass("has-error");
			$("#form_div_desti_code_error").show();
			flg = false;
			$("#form_div_desti_code").find(".acontainer input").focus();
			return false;
		} else {
			$("#form_div_desti_code").removeClass("has-error");
			$("#form_div_desti_code_error").hide();
		}

		if ($scope.invoice.items.length > 0) {

			for ( var i = 0; i < $scope.invoice.items.length; i++) {
				/* $.each($scope.invoice.items[i],function(key,value){ */
				if ($scope.invoice.items[i].stock_item_id != ""
						&& $scope.invoice.items[i].stock_item_code != "") {
					/*
					 * if ($("#cmbn_mode").val() == 1 ) { if
					 * ($scope.invoice.items[i].delivered_qty == "" ||
					 * $scope.invoice.items[i].delivered_qty ==
					 * parseFloat(0).toFixed(settings['decimalPlace']) ||
					 * $scope.invoice.items[i].delivered_qty == "0" ||
					 * $scope.invoice.items[i].delivered_qty == "0.") {
					 * $rootScope.$broadcast('on_AlertMessage_ERR', 'Please
					 * enter the Deliverd Qty for Items'); flg = false;
					 * 
					 * $("#items_table1 tr:nth-child(" + (i + 2) + ")")
					 * .find("#delivered_qty").select(); } }
					 */

					if ($scope.invoice.items[i].request_qty == ""
							|| $scope.invoice.items[i].request_qty == "0"
							|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == parseFloat(
									0).toFixed(settings['decimalPlace'])
							|| $scope.invoice.items[$scope.invoice.items.length - 1].request_qty == "0.") {
						$rootScope.$broadcast('on_AlertMessage_ERR',
								'Please Enter Request Qty');
						flg = false;

					}

				} else {
					$rootScope.$broadcast('on_AlertMessage_ERR',
							ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
					$(
							"#items_table1 tr:nth-child("
									+ ($scope.invoice.items.length) + ")")
							.find(".acontainer input").focus();

					flg = false;
				}
			}

		} else if ($scope.invoice.items.length == 0) {
			$rootScope.$broadcast('on_AlertMessage_ERR',
					ITEM_TABLE_MESSAGES.TABLE_ERR);
			flg = false;
		}

		return flg;
	}

	// .....................................

	function form_validation(data) {
		flag = false;
		for ( var i = 0; i < $scope.itemList.length; i++) {
			if ($scope.itemList[i].check_val == true) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	$scope.validDate = function() {
		flg = true;
		if (process($scope.formData.closing_date) == 'Invalid Date') {
			$("#form_div_delevery_date").addClass("has-error");
			$("#form_div_scheduledate_error").show();
			flg = false;
		} else {
			$("#form_div_delevery_date").removeClass("has-error");
			$("#form_div_scheduledate_error").hide();
		}

		if (process($scope.formData.prod_date) == 'Invalid Date') {
			$("#form_div_prod_date").addClass("has-error");
			$("#form_div_prddate_error").show();
			flg = false;
		} else {
			$("#form_div_prod_date").removeClass("has-error");
			$("#form_div_prddate_error").hide();
		}
		return flg;
	}

	/*
	 * $scope.printStore=function() {
	 * $window.open("reportProd?pdfExcel=pdf&reportName=BOM
	 * DETAILS&dept_id="+$scope.department_id+"&production_date="
	 * +getMysqlFormat($scope.prodDate)+"&time_slot_id="+parseInt($scope.time_slot_id)+"",
	 * '_blank'); }
	 */

	// send to production and print VERIFIED ORDERS
	$scope.printAndSendToProduction = function() {
		var details;
		var checked_Ids = [];
		var dtl_Ids = [];
		$('#orders_list').find('tbody').find('tr').each(
				function() {
					var row = $(this);
					if (row.find('input[type="checkbox"]').is(':checked')) {
						var id = row.find('input[type="checkbox"]').attr(
								'data-prd-id');
						var dtl_id = row.find('input[type="hidden"]').attr(
								'data-dtl-id');
						checked_Ids.push(id);
						dtl_Ids.push(dtl_id);
					}
				});
		if (checked_Ids.length != 0) {
			var shopName = "";
			var process = true;
			angular.forEach($scope.fillData, function(value, index) {
				if (process) {
					if ($scope.customerIds1 != 0
							&& $scope.customerIds1 == value.id) {
						shopName = value.name
						process = false;
					} else {
						shopName = "All Shopes"
					}
				}
			});

			$window.open(
					"printAndSendToProduction?pdfExcel=pdf&reportName=BOM DETAILS&itemIdArray="
							+ checked_Ids + "&shopName=" + shopName
							+ "&departmentID=" + $scope.department_id
							+ "&delevery_date="
							+ getMysqlFormat($scope.formData.closing_date)
							+ "&customerID=" + $scope.customerIds1
							+ "&orderDtlID=" + dtl_Ids + "", '_blank');
		} else
			$rootScope.$broadcast('on_AlertMessage_ERR',
					"please select atleast one");
	}

	$scope.onChangeSourceDepartment = function() {

		/*
		 * if($scope.formData.order_id != "" && $scope.formData.order_id !=
		 * undefined) getOrderDtls($scope.formData.order_id,
		 * $scope.formData.source_department_id);
		 */
		$scope.getChangeStock();

	}
	$scope.getChangeStock = function() {
		/*
		 * if($scope.itemsData.length > 0) { for(var i=0; i <
		 * $scope.itemsData.length; i++) {
		 * if($scope.itemsData[i].id==$scope.ProductionList[index].stock_item_id){
		 * $scope.getItemmasterCurrentStock($scope.itemsData[i].id,
		 * $scope.department_id,$scope.prodDate,i); } } }
		 */

		if ($scope.ProductionList.length > 0) {
			for ( var i = 0; i < $scope.ProductionList.length; i++) {
				$scope.getItemmasterCurrentStock(
						$scope.ProductionList[i].stock_item_id,
						$scope.department_id, $scope.prodDate, i)
			}
		}
	}
	$scope.getItemmasterCurrentStock = function(itemid, deptId, prodDate, index) {
		var current_stock = 0;
		if (prodDate != "" && prodDate != undefined && itemid != "") {
			$http({
				url : 'getCurrStock',
				method : "GET",
				params : {
					itemId : itemid,
					department_id : deptId,
					current_date : getMysqlFormat($scope.prodDate)
				},
				async : false,
			}).then(function(response) {
				current_stock = response.data;
				$scope.ProductionList[index].currentStock = current_stock;
			}, function(response) { // optional

			});
		}
		return parseFloat($scope.ProductionList[index].currentStock).toFixed(
				settings['decimalPlace']);

	}

	// varified orders

	$scope.loadData = function() {
		console.log("valuesssssss===========================================>"+$('#cust').val())
		if ($scope.slctypreq == '0') {
			$scope.fillData = $scope.shop;
			$scope.customerIds1=$scope.fillData[0].value;
			
			for ( var i = 0; i < $scope.shop.length; i++) {
				if ($('#cust').val() == $scope.shop[i]) {
					if($('#cust').val()=='?' || $('#cust').val()==''|| $('#cust').val()=='undefined:undefined'||typeof($('#cust').val())  === "undefined"){
						$("tbody").hide();
					}else {
						$("tbody").show();
					}
				}
				else{
					$("tbody").hide();
				}
			}
			
				/*if($('#cust').val()=='?' || $('#cust').val()==''|| $('#cust').val()=='undefined:undefined'||typeof($('#cust').val())  === "undefined"){
					$("tbody").hide();
				}else {
					$("tbody").show();
				}*/
			

		} else if ($scope.slctypreq == '1') {
			$scope.fillData = $scope.customerIds;
			var a = $scope.fillData[0];
			$scope.customerIds1=$scope.fillData[0].value;
			//$scope.fillData.replace(0, a);
			for ( var i = 0; i < $scope.customerIds.length; i++) {
				if ($('#cust').val() == $scope.customerIds[i]) {
					if($('#cust').val()=='?' || $('#cust').val()==''|| $('#cust').val()=='undefined:undefined'||typeof($('#cust').val())  === "undefined"){
						$("tbody").hide();
					}else {
						$("tbody").show();
					}
				}
				else{
					$("tbody").hide();
				}
			}
		} else {
			$scope.fillData = $scope.departments;
			$("#cust").val("string:");
		}

		// $scope.fillData.splice(0,0,{id : "" ,name : "ALL"});
	}

	/*
	 * Tab 2 In Production methods described below
	 */

	$scope.clickOnInProductionTab = function() {
		$scope.selectAll = false;
		$scope.clearInProductionTable();
		$scope.prodDate = todayDate;
		$scope.time_slot_id = $scope.timeSlot[0].id;
		$scope.department_id = "0";
	}

	$scope.clear_in_production_editmode = function(event) {

		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].stock_item_id = "";
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].stock_item_code = "";
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].stock_item_name = "";
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].uomcode = "";
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].currentStock = 0.0;
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].produced_qty = "";
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].order_qty = "";
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].itemMaterialCost = 0.0;
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].otherCost = 0.0;
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].totalCost = "";
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].schedule_qty = "";
		$scope.ProductionList[event.currentTarget.parentElement.rowIndex - 1].department_name = "";
	}

	/* method for changing date in In Production Tab */
	$scope.dateChange = function() {
		if ($scope.prodDate != "" && $scope.prodDate != undefined) {
			$("#form_div_prodDate").removeClass("has-error");
			$("#form_div_prodDate_error").hide();
			$http({
				url : 'datecheck',
				async : false,
				params : {
					production_date : getMysqlFormat($scope.prodDate)
				},
				method : "GET",
			})
					.then(
							function(response) {
								if (response.data == "0") {
									$scope.clearInProductionTable();
								} else {
									$mdDialog
											.show($mdDialog
													.alert()
													.parent(
															angular
																	.element(document
																			.querySelector('#dialogContainer')))
													.clickOutsideToClose(true)
													.textContent(
															"Production Entered in the succeeding date.So This date can not be selected")
													.ok('Ok!').targetEvent(
															event));

									$scope.prodDate = todayDate;
									$scope.clearInProductionTable();
								}
							});
		} else {
			$("#form_div_prodDate").addClass("has-error");
			$("#form_div_prodDate_error").show();
			$scope.ProductionList = [];

		}
	}

	$scope.form_validation_production = function() {

		var flg = true;
		if (process($scope.prodDate) == 'Invalid Date') {
			$("#form_div_prodDate").addClass("has-error");
			$("#form_div_prodDate_error").show();
			flg = false;
		} else {
			$("#form_div_prodDate").removeClass("has-error");
			$("#form_div_prodDate_error").hide();
		}

		if ($scope.department_id == "") {
			$("#form_div_in_production_department").addClass("has-error");
			$("#form_div_in_production_department_error").show();
			$rootScope.$broadcast('on_AlertMessage_ERR',
					"Please Select Department");
			return false;
		} else {
			$("#form_div_in_production_department").removeClass("has-error");
			$("#form_div_in_production_department_error").hide();
		}

		if ($scope.ProductionList.length == 1
				&& $scope.ProductionList[0].stock_item_id == "") {
			$rootScope.$broadcast('on_AlertMessage_ERR',
					"Please enter items for production");
			flg = false;
		} else if ($scope.ProductionList.length > 0) {
			for ( var i = 0; i < $scope.ProductionList.length; i++) {
				if ($scope.ProductionList[i].stock_item_id == ""
						&& $scope.ProductionList[i].stock_item_code == "") {
					$scope.ProductionList.splice(i, 1);
				} else if ($scope.ProductionList[i].prod_qty == ""
						|| parseFloat($scope.ProductionList[i].prod_qty) <= 0) {
					$(
							"#items_table tr:nth-child(" + (i + 1)
									+ ")  td:nth-child(3)").find("#prod_qty")
							.focus();
					$rootScope.$broadcast('on_AlertMessage_ERR',
							"Please enter the production Qty");
					flg = false;
					break;
				} else if ($scope.ProductionList[i].current_stock == "") {
					$scope.ProductionList[i].current_stock = 0.0;
				}
			}
		}
		return flg;
	}
	
	
	
	
	/* to confirm finished items to produce */
	$scope.MarkFinished = function(ev) {

		if ($scope.form_validation_production()) {
			var confirm = $mdDialog.confirm(
					{
						onComplete : function afterShowAnimation() {
							var $dialog = angular.element(document
									.querySelector('md-dialog'));
							var $actionsSection = $dialog
									.find('md-dialog-actions');
							var $cancelButton = $actionsSection.children()[0];
							var $confirmButton = $actionsSection.children()[1];
							angular.element($confirmButton).removeClass(
									'md-focused');
							angular.element($cancelButton).addClass(
									'md-focused');
							$cancelButton.focus();
						}
					}).title('Mark  Finished?').targetEvent(ev).cancel('No')
					.ok('Yes');

			$mdDialog.show(confirm).then(function() {

				$scope.getBomListOfCurrentProduct();

			});
		}
	}

	/* To get bom list for current product */

	$scope.getBomListOfCurrentProduct = function() {
		var departmentName = null;
		console.log($scope.department_id);
		console.log($scope.departments.length);
		for ( var i = 0; i < $scope.departments.length; i++) {
			if ($scope.departments[i].id == $scope.department_id) {
				departmentName = $scope.departments[i].name;
				break;
			}
		}
		console.log(departmentName);

		console.log($scope.department_id);
		var department_id = $scope.department_id;

		var stockItemIdList = [], orderQtyList = [];
		$scope.bomListOfCurrentProduct=[];
		$scope.bomCurrentStockList=[];

		for ( var i = 0; i < $scope.ProductionList.length; i++) {
			console.log($scope.ProductionList)
			stockItemIdList[i] = $scope.ProductionList[i].stock_item_id;
		}

		$http({
			url : 'getBomListOfCurrentProduct',
			method : "POST",
			params : {
				department_id : department_id
			},
			data : JSON.stringify(stockItemIdList)
		})
				.then(
						function(response) {
							$scope.bomListOfCurrentProduct = response.data.bomListOfCurrentProduct;
							console.log($scope.bomListOfCurrentProduct);
							$scope.bomCurrentStockList = response.data.bomCurrentStockList;
							$scope.CurrentItemsStockCheck();
						});
	}

	$scope.sufficientBomProductionList = [];
	$scope.insufficientBomProductionList = [];

	$scope.insufficientList = [];
	$scope.insufficientList.push({
		bom_name : "",
		product_name : "",
		current_stock : "",
		required_qty : ""
	});

	var productsListLength = 0;

	var p = 0;
	var count = 0;
	$scope.CurrentItemsStockCheck = function() {
		productsListLength = $scope.ProductionList.length;
		var t = 0;

		$scope.prograssing = true;
		var requiredQty = 0, statusFlag = 0, itemName = null, productionListLoopFlag = 0,noCurrentStockFlag=0;
		var requiredQtyLoopFlag = 0, bomName = null, bomId = 0, depName = null, productName = null, currentStock = 0;
		for ( var i = 0; i < $scope.bomListOfCurrentProduct.length; i++) {
			for ( var j = i + 1; j <= $scope.bomListOfCurrentProduct.length - 1; j++) {
				if ($scope.bomListOfCurrentProduct[i].stock_item_id == $scope.bomListOfCurrentProduct[j].stock_item_id
						&& $scope.bomListOfCurrentProduct[i].bom_item_id == $scope.bomListOfCurrentProduct[j].bom_item_id) {
					$scope.bomListOfCurrentProduct.splice(j, 1);
					j--;
				}
			}
		}
		
		
		if($scope.bomListOfCurrentProduct.length==0 && $scope.bomCurrentStockList.length == 0){
			$scope.insufficientList = [];
			for (p = 0; p < $scope.ProductionList.length; p++) {
				if($scope.ProductionList[p].stock_item_id!=""){
					$scope.insufficientList.push({
						bom_name : "",
						product_name : "",
						current_stock : "",
						required_qty : ""
					});
					/*$mdDialog
					.show($mdDialog
							.alert()
							.parent(
									angular
											.element(document
													.querySelector('#dialogContainer')))
							.clickOutsideToClose(
									true)
							.textContent(
									"Please add item bom for this product before production.")
							.ok('Ok!').targetEvent(
									event));
					statusFlag=1;
					$scope.prograssing = false;*/
					
					statusFlag = 1;
					$scope.insufficientList[t].product_name = $scope.ProductionList[p].stock_item_name;
					$scope.insufficientList[t].bom_name = "Missing Bom";
					$scope.insufficientList[t].current_stock = 0.00000;
					$scope.insufficientList[t].required_qty = 0;
					$scope.prograssing = false;
					t++;
					$scope.insufficientList.push({
						bom_name : "",
						product_name : "",
						current_stock : "",
						required_qty : ""
					});
					
				}
			}
			if(p==$scope.ProductionList.length && statusFlag == 1){
				$scope.insufficientList.length = $scope.insufficientList.length - 1;
				$scope.prograssing = false;
				$('#insufficientItemsList').modal('toggle');
			}
			
		}

		else if ($scope.bomCurrentStockList.length == 0) {
			$scope.insufficientList = [];
			$scope.insufficientList.push({
				bom_name : "",
				product_name : "",
				current_stock : "",
				required_qty : ""
			});
			for (p = 0; p < $scope.ProductionList.length; p++) {
				for ( var j = 0; j < $scope.bomListOfCurrentProduct.length; j++) {
					if ($scope.bomListOfCurrentProduct[j].stock_item_id == $scope.ProductionList[p].stock_item_id) {
						if ($scope.ProductionList[p].prod_qty >= Math
								.floor($scope.bomListOfCurrentProduct[j].stock_item_qty)) {
							requiredQty = $scope.bomListOfCurrentProduct[j].bom_item_qty
									* ($scope.ProductionList[p].prod_qty / $scope.bomListOfCurrentProduct[j].stock_item_qty);
							requiredQty = requiredQty.toFixed(5);
							requiredQtyLoopFlag = 1;
						} else {
							requiredQty = $scope.bomListOfCurrentProduct[j].bom_item_qty
									/ ($scope.bomListOfCurrentProduct[j].stock_item_qty / $scope.ProductionList[p].prod_qty);
							requiredQty = requiredQty.toFixed(5);
							requiredQtyLoopFlag = 1;
						}
						statusFlag = 1;
						$scope.insufficientList[t].product_name = $scope.ProductionList[p].stock_item_name;
						$scope.insufficientList[t].bom_name = $scope.bomListOfCurrentProduct[j].bom_name;
						$scope.insufficientList[t].current_stock = 0.00000;
						$scope.insufficientList[t].required_qty = requiredQty;
						$scope.insufficientList.push({
							bom_name : "",
							product_name : "",
							current_stock : "",
							required_qty : ""
						});
						t++;
					}
				}
			}
			
			if (p == $scope.ProductionList.length && statusFlag == 1) {
				$scope.insufficientList.length = $scope.insufficientList.length - 1;
				$scope.prograssing = false;
				$('#insufficientItemsList').modal('toggle');
			}
			else{
				$scope.insufficientList.length = $scope.insufficientList.length - 1;
			}
		}

		else if ($scope.bomCurrentStockList.length < $scope.bomListOfCurrentProduct.length) {
			$scope.insufficientList = [];
			$scope.insufficientList.push({
				bom_name : "",
				product_name : "",
				current_stock : "",
				required_qty : ""
			});
			$scope.sufficientBomProductionList=[];
			for (p = 0; p < $scope.ProductionList.length; p++) {
				count=0;
				for ( var j = 0; j < $scope.bomListOfCurrentProduct.length; j++) {
					for ( var k = 0; k < $scope.bomCurrentStockList.length; k++) {
						if ($scope.bomListOfCurrentProduct[j].stock_item_id == $scope.ProductionList[p].stock_item_id) {
							productionListLoopFlag = 1;
							statusFlag = 0;
							requiredQtyLoopFlag = 0;
							//count = 0;
							// count = 0;
							if ($scope.bomListOfCurrentProduct[j].bom_item_id == $scope.bomCurrentStockList[k].stock_item_id) {
								count=1;
								requiredQty = 0;
								requiredQtyLoopFlag = 0;
								if ($scope.ProductionList[p].prod_qty >= Math
										.floor($scope.bomListOfCurrentProduct[j].stock_item_qty)) {
									requiredQty = $scope.bomListOfCurrentProduct[j].bom_item_qty
											* ($scope.ProductionList[p].prod_qty / $scope.bomListOfCurrentProduct[j].stock_item_qty);
									requiredQty = requiredQty.toFixed(5);
									requiredQtyLoopFlag = 1;
								} else {
									requiredQty = $scope.bomListOfCurrentProduct[j].bom_item_qty
											/ ($scope.bomListOfCurrentProduct[j].stock_item_qty / $scope.ProductionList[p].prod_qty);
									requiredQty = requiredQty.toFixed(5);
									requiredQtyLoopFlag = 1;
								}

								if (requiredQtyLoopFlag == 1
										&& parseFloat(requiredQty) > parseFloat($scope.bomCurrentStockList[k].current_stock)
										|| Math.sign(requiredQty) == (-1)) {
									statusFlag = 1;
									$scope.insufficientList[t].product_name = $scope.ProductionList[p].stock_item_name;
									$scope.insufficientList[t].bom_name = $scope.bomCurrentStockList[k].name;
									$scope.insufficientList[t].current_stock = $scope.bomCurrentStockList[k].current_stock;
									$scope.insufficientList[t].required_qty = requiredQty;
									depName = $scope.bomCurrentStockList[k].department_name;
									$scope.insufficientList.push({
										bom_name : "",
										product_name : "",
										current_stock : "",
										required_qty : ""
									});
									t++;
									break;
								} else {
									statusFlag = 0;
								}
								if (requiredQtyLoopFlag == 1
										&& parseFloat($scope.bomCurrentStockList[k].current_stock) == 0) {
									statusFlag = 1;
									$scope.insufficientList[t].product_name = $scope.ProductionList[p].stock_item_name;
									$scope.insufficientList[t].bom_name = $scope.bomCurrentStockList[k].name;
									$scope.insufficientList[t].current_stock = $scope.bomCurrentStockList[k].current_stock;
									$scope.insufficientList[t].required_qty = requiredQty;
									depName = $scope.bomCurrentStockList[k].department_name;
									$scope.insufficientList.push({
										bom_name : "",
										product_name : "",
										current_stock : "",
										required_qty : ""
									});
									t++;
									break;
								}
							}

							/*
							 * To check the items which have no stock in that
							 * department
							 */

							if (($scope.insufficientList.length == 1 && statusFlag == 1)
									|| (k == $scope.bomCurrentStockList.length - 1 && requiredQtyLoopFlag == 0)) {
								if ($scope.ProductionList[p].prod_qty >= Math
										.floor($scope.bomListOfCurrentProduct[j].stock_item_qty)) {
									requiredQty = $scope.bomListOfCurrentProduct[j].bom_item_qty
											* ($scope.ProductionList[p].prod_qty / $scope.bomListOfCurrentProduct[j].stock_item_qty);
									requiredQtyLoopFlag = 1;
								} else {
									requiredQty = $scope.bomListOfCurrentProduct[j].bom_item_qty
											/ ($scope.bomListOfCurrentProduct[j].stock_item_qty / $scope.ProductionList[p].prod_qty);
									requiredQtyLoopFlag = 1;
								}

								noCurrentStockFlag=1;
								$scope.insufficientList[t].product_name = $scope.ProductionList[p].stock_item_name;
								$scope.insufficientList[t].bom_name = $scope.bomListOfCurrentProduct[j].bom_name;
								$scope.insufficientList[t].current_stock = 0;
								$scope.insufficientList[t].required_qty = requiredQty;
								$scope.insufficientList.push({
									bom_name : "",
									product_name : "",
									current_stock : "",
									required_qty : ""
								});
								t++;
								break;

							}

							else {
								for ( var x = 0; x < $scope.insufficientList.length; x++) {
									if ($scope.ProductionList[p].stock_item_name == $scope.insufficientList[x].product_name) {
										statusFlag = 1;
										break;
									}
								}
							}

						} else {
							productionListLoopFlag = 0;
							break;
						}

						if (productionListLoopFlag == 0) {
							break;
						}

						if (requiredQtyLoopFlag == 1) {
							break;
						}

					}

					if (j == $scope.bomListOfCurrentProduct.length - 1) {
						if (productionListLoopFlag == 0 && count == 0 && noCurrentStockFlag ==0) {
							statusFlag = 1;
							$scope.insufficientList[t].product_name = $scope.ProductionList[p].stock_item_name;
							$scope.insufficientList[t].bom_name = "Missing Bom";
							$scope.insufficientList[t].current_stock = 0;
							$scope.insufficientList[t].required_qty = 0;
							$scope.insufficientList.push({
								bom_name : "",
								product_name : "",
								current_stock : "",
								required_qty : ""
							});
							t++;
							break;
						}
					}

					if (count > 0 && statusFlag == 0
							&& j == $scope.bomListOfCurrentProduct.length - 1) {
						break;
					}

				}

				if ($scope.insufficientList.length != 0) {
					for ( var x = 0; x < $scope.insufficientList.length; x++) {
						if ($scope.ProductionList[p].stock_item_name == $scope.insufficientList[x].product_name) {
							statusFlag = 1;
							break;
						}
					}
				}

				if (statusFlag == 1) {
					$scope.insufficientBomProductionList.push(angular
							.copy($scope.ProductionList[p]));
				}

				else if (statusFlag == 0) {
					$scope.sufficientBomProductionList.push(angular
							.copy($scope.ProductionList[p]));
				}
				if (statusFlag == 0) {
					$scope.saveProductionProcess($scope.sufficientBomProductionList);
					break;
				} else {
					continue;
				}

			}
			if (p == $scope.ProductionList.length && statusFlag == 1) {
				$scope.insufficientList.length = $scope.insufficientList.length - 1;
				$scope.prograssing = false;
				$('#insufficientItemsList').modal('toggle');
			}
			else{
				$scope.insufficientList.length = $scope.insufficientList.length - 1;
			}

		}

		


		
		else if ($scope.bomCurrentStockList.length == $scope.bomListOfCurrentProduct.length) {
			$scope.insufficientList = [];
			$scope.insufficientList.push({
				bom_name : "",
				product_name : "",
				current_stock : "",
				required_qty : ""
			});
			$scope.sufficientBomProductionList=[];
			for (p = 0; p < $scope.ProductionList.length; p++) {
				count=0;
				for ( var j = 0; j < $scope.bomListOfCurrentProduct.length; j++) {
					for ( var k = 0; k < $scope.bomCurrentStockList.length; k++) {
						if ($scope.bomListOfCurrentProduct[j].stock_item_id == $scope.ProductionList[p].stock_item_id) {
							productionListLoopFlag = 1;
							requiredQty = 0;
							requiredQtyLoopFlag = 0;
							statusFlag = 0;
							//count=0;
							if ($scope.bomListOfCurrentProduct[j].bom_item_id == $scope.bomCurrentStockList[k].stock_item_id) {
								count=1;
								requiredQty = 0;
								requiredQtyLoopFlag = 0;
								if ($scope.ProductionList[p].prod_qty >= Math
										.floor($scope.bomListOfCurrentProduct[j].stock_item_qty)) {
									requiredQty = $scope.bomListOfCurrentProduct[j].bom_item_qty
											* ($scope.ProductionList[p].prod_qty / $scope.bomListOfCurrentProduct[j].stock_item_qty);
									requiredQty = requiredQty.toFixed(5);
									requiredQtyLoopFlag = 1;
								} else {
									requiredQty = $scope.bomListOfCurrentProduct[j].bom_item_qty
											/ ($scope.bomListOfCurrentProduct[j].stock_item_qty / $scope.ProductionList[p].prod_qty);
									requiredQty = requiredQty.toFixed(5);
									requiredQtyLoopFlag = 1;
								}

								if (requiredQtyLoopFlag == 1
										&& parseFloat(requiredQty) > parseFloat($scope.bomCurrentStockList[k].current_stock)
										|| Math.sign(requiredQty) == (-1)) {
									statusFlag = 1;
									$scope.insufficientList[t].product_name = $scope.ProductionList[p].stock_item_name;
									$scope.insufficientList[t].bom_name = $scope.bomCurrentStockList[k].name;
									$scope.insufficientList[t].current_stock = $scope.bomCurrentStockList[k].current_stock;
									$scope.insufficientList[t].required_qty = requiredQty;
									depName = $scope.bomCurrentStockList[k].department_name;
									$scope.insufficientList.push({
										bom_name : "",
										product_name : "",
										current_stock : "",
										required_qty : ""
									});
									t++;
									break;
								} else {
									statusFlag = 0;
								}
								if (requiredQtyLoopFlag == 1
										&& parseFloat($scope.bomCurrentStockList[k].current_stock) == 0) {
									statusFlag = 1;
									$scope.insufficientList[t].product_name = $scope.ProductionList[p].stock_item_name;
									$scope.insufficientList[t].bom_name = $scope.bomCurrentStockList[k].name;
									$scope.insufficientList[t].current_stock = $scope.bomCurrentStockList[k].current_stock;
									$scope.insufficientList[t].required_qty = requiredQty;
									depName = $scope.bomCurrentStockList[k].department_name;

									$scope.insufficientList.push({
										bom_name : "",
										product_name : "",
										current_stock : "",
										required_qty : ""
									});
									t++;
									break;
								}

							} else {
								for ( var x = 0; x < $scope.insufficientList.length; x++) {
									if ($scope.ProductionList[p].stock_item_name == $scope.insufficientList[x].product_name) {
										statusFlag = 1;
										break;
									}
								}
							}

						} else {
							productionListLoopFlag = 0;
							break;
						}

						if (productionListLoopFlag == 0) {
							break;
						}

						if (requiredQtyLoopFlag == 1) {
							break;
						}

					}
					if (j == $scope.bomListOfCurrentProduct.length - 1) {
						if (productionListLoopFlag == 0 && count == 0) {
							statusFlag = 1;
							$scope.insufficientList[t].product_name = $scope.ProductionList[p].stock_item_name;
							$scope.insufficientList[t].bom_name = "Missing Bom";
							$scope.insufficientList[t].current_stock = 0;
							$scope.insufficientList[t].required_qty = 0;
							$scope.insufficientList.push({
								bom_name : "",
								product_name : "",
								current_stock : "",
								required_qty : ""
							});
							t++;
							break;
						}
					}

					if (count > 0 && statusFlag == 0
							&& j == $scope.bomListOfCurrentProduct.length - 1) {
						break;
					}

				}
				if ($scope.insufficientList.length != 0) {
					for ( var x = 0; x < $scope.insufficientList.length; x++) {
						if ($scope.ProductionList[p].stock_item_name == $scope.insufficientList[x].product_name) {
							statusFlag = 1;
							break;
						}
					}
				}

				if (statusFlag == 1) {
					$scope.insufficientBomProductionList.push(angular
							.copy($scope.ProductionList[p]));
				}

				else if (statusFlag == 0) {
					$scope.sufficientBomProductionList.push(angular
							.copy($scope.ProductionList[p]));
				}
				if (statusFlag == 0) {
				
				
					$scope.saveProductionProcess($scope.sufficientBomProductionList);
					break;
				} else {
					continue;
				}

			}

			if (p == $scope.ProductionList.length && statusFlag == 1) {
				$scope.insufficientList.length = $scope.insufficientList.length - 1;
				$scope.prograssing = false;
				$('#insufficientItemsList').modal('toggle');
			}
			else{
				$scope.insufficientList.length = $scope.insufficientList.length - 1;
			}
		}
	}

	
	$scope.formData2 = {};
	$scope.formData2.dailyPlanningDetailLists=[];
	$scope.saveProductionProcess = function(sufficientBomProductionList) {
		console.log(sufficientBomProductionList);
		$scope.formData.status = 1;
		var now = new moment();
		var datetime = now.format("YYYY-MM-DD HH:mm:ss");

		
		
		$scope.formData2 = {
			status : 1,
			prod_date : getMysqlFormat($scope.prodDate),
			prod_time : datetime,
			schedDate : getMysqlFormat($scope.prodDate),
			prodUpTo : getMysqlFormat($scope.prodDate),
			department_id : $scope.department_id
		};
		$scope.formData2.dailyPlanningDetailLists = JSON
				.stringify(sufficientBomProductionList);
		$scope.prograssing = true;
		
		$http({
			url : 'saveProductionProcess',
			method : "POST",
			data : $scope.formData2,
			params : {
				time_slot : $scope.time_slot_id
			}
		})
				.then(
						function(response) {
							if (response.data == 1) {
								console.log(p);

								if (p != productsListLength - 1) {
									$scope.ProductionList.splice(p, 1);
									$scope.prograssing = false;
									$scope.getBomListOfCurrentProduct();
								}

								else {
									if ($scope.insufficientList.length != 0) {
										for ( var s = 0; s < sufficientBomProductionList.length; s++) {
											for ( var k = 0; k < $scope.ProductionList.length; k++) {
												if (sufficientBomProductionList[s].stock_item_id == $scope.ProductionList[k].stock_item_id) {
													$scope.ProductionList
															.splice(k, 1);
												}
											}
										}
										if (s = $scope.sufficientBomProductionList.length) {
											$scope.prograssing = false;
											$scope.getBomListOfCurrentProduct();
											  /*
												 * $('#insufficientItemsList').modal(
												 * 'toggle');
												 */
											 
										}
									} else {
										$scope.prograssing = false;
										$rootScope.$broadcast(
												'on_AlertMessage_SUCC',
												FORM_MESSAGES.SAVE_SUC);
										$scope.clearInProductionTable();
									}
								}

								if ($scope.ProductionList.length == 0) {
									$scope.prograssing = false;
									$rootScope.$broadcast(
											'on_AlertMessage_SUCC',
											FORM_MESSAGES.SAVE_SUC);
									$scope.clearInProductionTable();
								}
							} else {
								$mdDialog
										.show($mdDialog
												.alert()
												.parent(
														angular
																.element(document
																		.querySelector('#dialogContainer')))
												.clickOutsideToClose(true)
												.textContent(
														"Mark Finished failed.")
												.ok('Ok!').targetEvent(event));
								$scope.prograssing = false;
							}
						},
						function(response) {
							// optional
							$mdDialog
									.show($mdDialog
											.alert()
											.parent(
													angular
															.element(document
																	.querySelector('#dialogContainer')))
											.clickOutsideToClose(true)
											.textContent(
													"Mark Finished failed.")
											.ok('Ok!').targetEvent(event));
							$scope.prograssing = false;
						});

	}

	$scope.printMarkFinished = function() {
		if ($scope.form_validation_production()) {
			window.open("markFinishedPrint?stock_itemArray="
					+ encodeURIComponent(JSON.stringify($scope.ProductionList))
					+ "", '_blank');
		}
	}

	/* to calculate unit cost price from material and other cost */
	$scope.setTotalCost = function(item) {

		var totalCost = 0.00;
		totalCost = parseFloat(item.itemMaterialCost)
				+ parseFloat(item.otherCost);
		/*
		 * item.totalCost =
		 * parseFloat(totalCost).toFixed(settings['decimalPlace']); return
		 * parseFloat(totalCost).toFixed(settings['decimalPlace']);
		 */
		item.totalCost = parseFloat(totalCost)
				.toFixed(settings['decimalPlace']);
		return parseFloat(totalCost).toFixed(settings['decimalPlace']);
	}

	/* to display order_qty split up */
	$scope.showShopwiseOrderDetails = function(index, item) {
		var detailsdata;
		detailsdata = {
			production_date : getMysqlFormat($scope.prodDate),
			time_slot_id : $scope.time_slot_id,
			stock_item_id : item.stock_item_id
		};

		$http({
			url : 'orderDetailsProd',
			async : false,
			params : detailsdata,
			method : "GET",
		})
				.then(
						function(response) {

							$scope.orderSplitData = response.data.orderSplitData;
							if ($scope.orderSplitData.length != 0) {
								var qty = 0.0;
								for ( var i = 0; i < $scope.orderSplitData.length; i++) {
									qty += parseFloat($scope.orderSplitData[i].orderqty);
								}
								$scope.previousBalance = parseFloat(
										(item.order_qty - qty)).toFixed(
										settings['decimalPlace']) < 0 ? parseFloat(
										(0)).toFixed(settings['decimalPlace'])
										: parseFloat((item.order_qty - qty))
												.toFixed(
														settings['decimalPlace']);

								$('#orderDataSplitprod').modal('toggle');
							}
						});
	}

	/* add new row to In Production table */
	$scope.addItem = function(index) {

		var length = $scope.ProductionList.length;
		if (index < length - 1)
			$(
					"#items_table tr:nth-child(" + (index + 3)
							+ ") td:nth-child(" + (2) + ")").find(
					".acontainer input").focus();

		if (length != 0) {
			if ($scope.ProductionList[length - 1].stock_item_id != ""
					&& $scope.ProductionList[length - 1].stock_item_code != "") {

				if (parseFloat($scope.ProductionList[length - 1].prod_qty) == 0
						|| $scope.ProductionList[length - 1].prod_qty == "") {
					$rootScope.$broadcast('on_AlertMessage_ERR',
							"Please Enter Produced Qty");
					$("#items_table tr:nth-child(" + (length + 1) + ")").find(
							"#produced_qty").select();
				} else {
					$scope.ProductionList.push({
						id : "",
						stock_item_id : "",
						stock_item_code : "",
						uomcode : "",
						department_name : "",
						currentStock : "",
						prod_qty : "",
						damageqty : "",
						order_qty : 0,
						cost_price : "",
						remarks : "",
						itemMaterialCost : 0,
						otherCost : 0
					});
				}
			} else {
				$rootScope.$broadcast('on_AlertMessage_ERR',
						ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$("#items_table tr:nth-child(" + (length + 1) + ")").find(
						".acontainer input").focus();
			}

		} else if (length == 0) {
			$scope.ProductionList.push({
				id : "",
				stock_item_id : "",
				stock_item_code : "",
				uomcode : "",
				department_name : "",
				currentStock : "",
				prod_qty : "",
				damageqty : "",
				order_qty : 0,
				cost_price : "",
				remarks : "",
				itemMaterialCost : 0,
				otherCost : 0
			});
		}
	}

	/* remove a row to In Production table */
	$scope.removeItem = function(index, event) {

		var confirm = $mdDialog
				.confirm(
						{
							onComplete : function afterShowAnimation() {
								var $dialog = angular.element(document
										.querySelector('md-dialog'));
								var $actionsSection = $dialog
										.find('md-dialog-actions');
								var $cancelButton = $actionsSection.children()[0];
								var $confirmButton = $actionsSection.children()[1];
								angular.element($confirmButton).removeClass(
										'md-focused');
								angular.element($cancelButton).addClass(
										'md-focused');
								$cancelButton.focus();
							}
						}).title(FORM_MESSAGES.ROW_DELETE_WRNG).targetEvent(
						event).ok('Yes').cancel('No');

		$mdDialog.show(confirm).then(
				function() {

					if (index == 0 && $scope.ProductionList.length == 1) {
						$rootScope.$broadcast('on_AlertMessage_ERR',
								"Atleast one item is required.");
					} else {
						$scope.ProductionList.splice(index, 1);
						$(
								"#items_table tr:nth-child("
										+ ($scope.ProductionList.length + 1)
										+ ")").find("#produced_qty").focus();
					}
				});
	}

	/*
	 * to set current stock and cost details of selected item to In Production
	 * table
	 */
	$scope.getCurrentItem = function(selected_item_id, index) {

		var productionData = {
			selected_item_id : selected_item_id,
			production_date : getMysqlFormat($scope.prodDate),
			time_slot_id : $scope.time_slot_id,
			departmentId : $scope.department_id
		};

		$scope.inOrderList = [];
		$http({
			url : 'ProductionList',
			async : false,
			params : productionData,
			method : "GET",
		})
				.then(
						function(response) {
							$scope.inOrderList = response.data.ProductionList;
							if ($scope.inOrderList == "") {
								$scope.ProductionList[index].currentStock = 0.00;
							}
							$scope.ProductionList[index].currentStock = $scope.inOrderList[0].current_stock;
							$scope.ProductionList[index].itemMaterialCost = $scope.inOrderList[0].item_material_cost;
							$scope.ProductionList[index].otherCost = $scope.inOrderList[0].other_cost;
							$scope.ProductionList[index].cost_price = $scope
									.setTotalCost($scope.ProductionList[index]);
							$scope.ProductionList[index].order_qty = parseFloat(
									$scope.inOrderList[0].order_qty).toFixed(
									settings['decimalPlace']);
							$scope.ProductionList[index].schedule_qty = 0.00;
						});

	}

	/* to clear table rows */
	$scope.clearInProductionTable = function() {

		$scope.ProductionList = [];
		$scope.ProductionList.push({
			id : "",
			stock_item_id : "",
			stock_item_code : "",
			uomcode : "",
			department_name : "",
			currentStock : "",
			prod_qty : "",
			damageqty : "",
			order_qty : "",
			cost_price : "",
			remarks : "",
			itemMaterialCost : 0,
			otherCost : 0
		});
	}

	/*
	 * $('.clickable').bind('click', function (ev) { alert("hi"); var $div =
	 * $(ev.target); var $display = $div.find('.display'); alert("hi"); var
	 * offset = $div.offset(); var x = ev.clientX - offset.left; var y =
	 * ev.clientY - offset.top;
	 * 
	 * $display.text('x: ' + x + ', y: ' + y); alert($display); });
	 */

	/*
	 * $("#clickable").click(function(){ alert("hi"); var parentOffset =
	 * $(this).parent().offset(); //or $(this).offset(); if you really just want
	 * the current element's offset var relX = e.pageX - parentOffset.left; var
	 * relY = e.pageY - parentOffset.top; alert(relY); });
	 */

	// $scope.clickable = function(e){
	// console.log(e.clientX+","+e.clientY);
	// var drop = angular.element(".newDiv");
	// drop[0].addClass("class_drop");
	// drop[0].clientLeft = e.clientX;
	// drop[0].clientTop = e.clientY;
	// }
}

// directive for material requisition
/*
 * mrpApp.directive('autocompeteText',['$timeout',function($timeout) { return {
 * controller : function($scope, $http) { $scope.currentIndex = 0;
 * $scope.selctedindex = 0; $("#items_table1 tbody tr td") .keyup( 'input',
 * function(e) { $scope.currentIndex = e.currentTarget.parentElement.rowIndex -
 * 1;
 * 
 * if (e.which != 9 && e.which != 13 && e.which != 109 && e.which == 8) { if
 * (e.currentTarget.cellIndex == 2) { $scope.$apply(function() {
 * $scope.clear_stock_details_editmode(e); $scope.alert_for_codeexisting(0); }); } }
 * else if (e.which == 13) {
 * 
 * if (e.currentTarget.cellIndex == 2) { if
 * ($scope.invoice.items[$scope.invoice.items.length - 1].stock_item_id != "") { {
 * $scope.selctedindex = 0; $("#items_table1 tr:nth-child("+
 * (e.currentTarget.parentElement.rowIndex + 1)+
 * ")").find("#request_qty").select(); } } } } else if (e.which == 9) { if
 * (e.currentTarget.cellIndex == 2) { { $( "#items_table1 tr:nth-child(" +
 * (e.currentTarget.parentElement.rowIndex + 1) + ") td:nth-child(" + (3) + ")")
 * .find( ".acontainer input") .focus(); } }
 * 
 * else if (e.currentTarget.cellIndex == 5 && $scope.selctedindex == 0) {
 * $scope.selctedindex = 1; $( "#items_table1 tr:nth-child(" +
 * (e.currentTarget.parentElement.rowIndex + 1) + ")") .find( "#request_qty")
 * .select(); } else if (e.currentTarget.cellIndex == 5 && $scope.selctedindex ==
 * 1) { { $scope.selctedindex = 0; $( "#items_table1 tr:nth-child(" +
 * (e.currentTarget.parentElement.rowIndex + 1) +
 * ")").find("#delivered_qty").select(); } } }
 * 
 * });
 * 
 * $scope.table_itemsearch_rowindex = 0; $scope.tableClicked = function(index) {
 * 
 * $scope.table_itemsearch_rowindex = index; }; return $scope; }, link :
 * function(scope, elem, attrs, controller) { var strl_scope = controller; var
 * items = $(elem) .tautocomplete( {
 * 
 * columns : [ 'id', 'code', 'name', 'input_tax_id', 'tax_percentage',
 * 'valuation_method', 'is_active', 'is_manufactured', 'uomcode', 'uomname',
 * 'unit_price' ], hide : [ false, true, true, false, false, false, false,
 * false, false, false, false, ], placeholder : "search ..", highlight :
 * "hightlight-classname", norecord : "No Records Found", delay : 10, onchange :
 * function() { var selected_item_data = items .all(); strl_scope
 * .$apply(function() { var count = 0; for (var i = 0; i <
 * strl_scope.invoice.items.length; i++) { if (selected_item_data.id != "") { if
 * (i != strl_scope.currentIndex) { if (selected_item_data.id ==
 * strl_scope.invoice.items[i].stock_item_id) { count = 1; } } } } if (count !=
 * 1) { strl_scope.invoice.items[strl_scope.currentIndex].uomcode =
 * selected_item_data.uomcode;
 * 
 * strl_scope.invoice.items[strl_scope.currentIndex].stock_item_id =
 * selected_item_data.id;
 * strl_scope.invoice.items[strl_scope.currentIndex].stock_item_code =
 * selected_item_data.code;
 * strl_scope.invoice.items[strl_scope.currentIndex].stock_item_name =
 * selected_item_data.name;
 * strl_scope.invoice.items[strl_scope.currentIndex].stock_item_batch_id =
 * strl_scope .getItemmasterBatchId(selected_item_data.id);
 * strl_scope.invoice.items[strl_scope.currentIndex].stock_item_batch_stock =
 * strl_scope .getItemmasterBatchName(selected_item_data.id); strl_scope
 * .alert_for_codeexisting(0);
 * strl_scope.invoice.items[strl_scope.currentIndex].currentStock = strl_scope
 * .getItemmasterBatchCurrentStock(selected_item_data.id);
 * strl_scope.invoice.items[strl_scope.currentIndex].currentStock = strl_scope
 * .getItemmasterCurrentStock(selected_item_data.id,strl_scope.formData.source_department_id,strl_scope.formData.stock_request_date,strl_scope.currentIndex);
 * 
 * strl_scope.invoice.items[strl_scope.currentIndex].unit_price =
 * selected_item_data.unit_price;
 * strl_scope.getCostPrice(strl_scope.itemsData,strl_scope.currentIndex,selected_item_data.id);
 * 
 * 
 * $timeout( function() { $( "#items_table1 tr:nth-child(" +
 * (strl_scope.currentIndex + 2) + ")") .find( "#request_qty") .focus(); }, 1); }
 * else { elem[0].parentNode.lastChild.value = ""; strl_scope
 * .alert_for_codeexisting(1);
 * strl_scope.invoice.items[strl_scope.currentIndex].uomcode = ""; }
 * 
 * }); }, data : function() {
 * 
 * var data = strl_scope.itemsData; var filterData = []; var searchData =
 * eval("/" + items .searchdata() + "/gi"); $.each(data,function(i,v) { if
 * (v.name.search(new RegExp(searchData)) != -1) {
 * 
 * filterData.push(v); } });
 * 
 * return filterData; } });
 * 
 * for (var i = 0; i < strl_scope.invoice.items.length; i++) { if
 * (strl_scope.formData.id != undefined && strl_scope.formData.id != '') { if
 * (strl_scope.invoice.items[i].isSet == false) {
 * elem[0].parentNode.lastChild.value =
 * strl_scope.invoice.items[i].stock_item_code; strl_scope
 * .disable_search_text(elem[0].parentNode.lastChild);
 * strl_scope.invoice.items[i].isSet = true; break; } } } $timeout( function() {
 * if (strl_scope.formData.dest_department_id != undefined) { $("#items_table1
 * tr:nth-child("+ (strl_scope.invoice.items.length + 1)+ ") td:nth-child("+ (3) +
 * ")").find(".acontainer input").select(); } }, 1); } }; } ]);
 */
/*
 * mrpApp.directive('autocompeteText', ['$timeout',function($timeout) { return {
 * controller: function ($scope,$http) { $scope.currentIndex3= 0; $("#stockHead
 * tr td").keyup('input',function(e){ $scope.currentIndex3 =
 * e.currentTarget.parentElement.rowIndex-1;
 * 
 * if(e.which != 9 && e.which != 13 && e.which != 109 && e.which == 8){
 * if(e.currentTarget.cellIndex == 1){ $scope.$apply(function(){
 * $scope.clear_bom_details_editmode(e); $scope.alert_for_codeexisting2(0); }); }
 * }else if(e.which == 13 ){ if(e.currentTarget.cellIndex == 1){
 * if($scope.bomList[$scope.bomList.length-1].bom_item_id!=""){ { $("#stockHead
 * tr:nth-child("+e.currentTarget.parentElement.rowIndex+")
 * td:nth-child("+(4)+")").find("#qty").select();} }} }else if(e.which == 9 ){
 * if(e.currentTarget.cellIndex == 4){
 * 
 * 
 * {$("#stockHead tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")
 * td:nth-child("+(2)+")").find(".acontainer input").focus(); } } }
 * 
 * }); $scope.itemsData = $scope.stkData; $scope.element = [];
 * $scope.table_itemsearch_rowindex3=0; $scope.tableClicked3 = function (index) {
 * $scope.table_itemsearch_rowindex3= index; }; return $scope; }, link:
 * function(scope, elem, attrs ,controller) { var strl_scope = controller; var
 * items = $(elem).tautocomplete({
 * 
 * columns:
 * ['id','code','name','input_tax_id','tax_percentage','valuation_method','is_active','is_manufactured','uomcode','uomname','unit_price','cost_price'],
 * hide:[false,true,true,false,false,false,false,false,false,false,false,false],
 * hide:
 * [false,true,true,false,false,false,false,false,false,false,false,false,false],
 * columns:
 * ['id','code','name','input_tax_id','tax_percentage','valuation_method','is_active','is_manufactured','uomcode','uomname'],
 * hide: [false,true,true,false,false,false,false,false,true,false],
 * placeholder: "search ..", highlight: "hightlight-classname", norecord: "No
 * Records Found", delay : 10, onchange: function () { var selected_item_data =
 * items.all(); strl_scope.$apply(function(){ var count =0; for(var i=0;i<strl_scope.bomList.length;i++){
 * if(!strl_scope.bomList[i].isDeleted){ if(selected_item_data.id != ""){ if(i !=
 * strl_scope.currentIndex3){ if(selected_item_data.id ==
 * strl_scope.bomList[i].bom_item_id){ count=1; } } }strl_scope } } if(count !=
 * 1){ strl_scope.bomList[strl_scope.currentIndex3].bom_item_id =
 * selected_item_data.id;
 * strl_scope.bomList[strl_scope.currentIndex3].bom_item_code =
 * selected_item_data.code;
 * strl_scope.bomList[strl_scope.currentIndex3].bom_item_name =
 * selected_item_data.name;
 * 
 * strl_scope.bomList[strl_scope.currentIndex3].uomcode =
 * selected_item_data.uomcode;
 * strl_scope.bomList[strl_scope.currentIndex3].unit_price=parseFloat(selected_item_data.cost_price).toFixed(settings['decimalPlace']);
 * strl_scope.getCostPrice(strl_scope.currentIndex,selected_item_data.id);
 * 
 * 
 * $timeout(function () { $("#stockHead tbody
 * tr:nth-child("+strl_scope.currentIndex3+1+") td").find("#qty").focus();}, 1);
 * 
 * strl_scope.alert_for_codeexisting2(0); $("#stockHead tbody
 * tr:nth-child("+strl_scope.table_itemsearch_rowindex3+1+")
 * td").find("#po_qty").focus(); }else{ elem[0].parentNode.lastChild.value="";
 * strl_scope.bomList[strl_scope.currentIndex3].bom_item_id = "";
 * strl_scope.bomList[strl_scope.currentIndex3].bom_item_code = "";
 * strl_scope.bomList[strl_scope.currentIndex3].bom_item_name = "";
 * strl_scope.bomList[strl_scope.currentIndex3].uomcode = "";
 * strl_scope.bomList[strl_scope.currentIndex3].unit_price=parseFloat(0).toFixed(settings['decimalPlace']);
 * strl_scope.alert_for_codeexisting2(1); }
 * 
 * }); }, data: function () {
 * 
 * var data = strl_scope.itemsData;
 * 
 * var filterData = []; var searchData = eval("/^" + items.searchdata() +
 * "/gi"); $.each(data, function (i, v) { if ( v.name.search(new
 * RegExp(searchData)) != -1) { filterData.push(v); } });
 * 
 * return filterData; } });
 * 
 * for(var i = 0;i<strl_scope.bomList.length;i++){
 * if(strl_scope.selectedStkItemId!=undefined &&
 * strl_scope.selectedStkItemId!='' ){ if(strl_scope.bomList[i].isSet == false){
 * elem[0].parentNode.lastChild.value = strl_scope.bomList[i].bom_item_code;
 * strl_scope.disable_search_text(elem[0].parentNode.lastChild);
 * strl_scope.bomList[i].isSet=true;break; } }else{
 * if(strl_scope.bomList[i].isSet == false){ if(strl_scope.bomList[i].po_no !=
 * undefined && strl_scope.bomList[i].po_no != ""){
 * elem[0].parentNode.lastChild.value = strl_scope.bomList[i].bom_item_code;
 * strl_scope.bomList[i].isSet=true;break; } } } } $timeout(function () {
 * $("#stockHead tr:nth-child("+(strl_scope.bomList.length)+")
 * td:nth-child("+(2)+")").find(".acontainer input").select(); }, 1); } }; }]);
 */

/*
 * mrpApp.directive('tableProductioncost', ['$timeout',function($timeout) {
 * return { controller: function ($scope,$http) { $scope.currentIndex1 = 0;
 * $("#prodCost tr td").keyup('input',function(e){ $scope.currentIndex1 =
 * e.currentTarget.parentElement.rowIndex-1;
 * 
 * if(e.which != 9 && e.which != 13 && e.which != 109 && e.which == 8){
 * if(e.currentTarget.cellIndex == 1){ $scope.$apply(function(){
 * $scope.clear_cost_details_editmode(e); $scope.alert_for_codeexisting(0); }); }
 * }else if(e.which == 13 ){ if(e.currentTarget.cellIndex == 1){
 * if($scope.prodCostList[$scope.prodCostList.length-1].prod_cost_id!=""){ {
 * $("#prodCost tr:nth-child("+e.currentTarget.parentElement.rowIndex+")
 * td:nth-child("+(5)+")").find("#cost_rate").select();} }} }else if(e.which ==
 * 9 ){ if(e.currentTarget.cellIndex == 1){ {$("#prodCost
 * tr:nth-child("+(e.currentTarget.parentElement.rowIndex)+")
 * td:nth-child("+(2)+")").find(".acontainer input").focus(); $("#items_table
 * tbody tr:nth-child("+e.currentTarget.cellIndex+") td").find(".acontainer
 * input").focus();} } }
 * 
 * });
 * 
 * 
 * $scope.element = []; $scope.table_itemsearch_rowindex1=0;
 * $scope.tableClicked2 = function (index) { $scope.table_itemsearch_rowindex1=
 * index; }; return $scope; }, link: function(scope, elem, attrs ,controller) {
 * var strl_scope = controller; var items = $(elem).tautocomplete({
 * 
 * columns: ['id' , 'code', 'name','cost_type_name'], hide:
 * [false,true,true,false],
 * 
 * placeholder: "search ..", highlight: "hightlight-classname", norecord: "No
 * Records Found", delay : 10, onchange: function () { var selected_cost_data =
 * items.all(); strl_scope.$apply(function(){ var count =0; for(var i=0;i<strl_scope.prodCostList.length;i++){
 * if(!strl_scope.prodCostList[i].isDeleted){ if(selected_cost_data.id != ""){
 * if(i != strl_scope.currentIndex1){ if(selected_cost_data.id ==
 * strl_scope.prodCostList[i].prod_cost_id){ count=1; } } }strl_scope } }
 * if(count != 1){
 * 
 * strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_id=selected_cost_data.id;
 * strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_code =
 * selected_cost_data.code;
 * strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_name=
 * selected_cost_data.name;
 * strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_type=
 * selected_cost_data.cost_type_name;
 * strl_scope.getCostPrice(strl_scope.currentIndex,selected_item_data.id);
 * 
 * 
 * strl_scope.alert_for_codeexisting2(0); $timeout(function () { $("#prodCost
 * tbody tr:nth-child("+strl_scope.currentIndex1+1+")
 * td").find("#cost_rate").focus();}, 1); }else{
 * elem[0].parentNode.lastChild.value="";
 * strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_id = "";
 * strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_code = "";
 * strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_name = "";
 * strl_scope.prodCostList[strl_scope.currentIndex1].prod_cost_type = "";
 * 
 * strl_scope.alert_for_codeexisting2(1); }
 * 
 * }); }, data: function () {
 * 
 * var data = strl_scope.prodcost_data;
 * 
 * var filterData = []; var searchData = eval("/^" + items.searchdata() +
 * "/gi"); $.each(data, function (i, v) { if ( v.name.search(new
 * RegExp(searchData)) != -1) { filterData.push(v); } });
 * 
 * return filterData; } });
 * 
 * for(var i = 0;i<strl_scope.prodCostList.length;i++){
 * if(strl_scope.selectedStkItemId!=undefined &&
 * strl_scope.selectedStkItemId!='' ){ if(strl_scope.prodCostList[i].isSet ==
 * false){ elem[0].parentNode.lastChild.value =
 * strl_scope.prodCostList[i].prod_cost_code;
 * strl_scope.disable_search_text(elem[0].parentNode.lastChild);
 * strl_scope.prodCostList[i].isSet=true;break; } }else{
 * if(strl_scope.prodCostList[i].isSet == false){
 * if(strl_scope.prodCostList[i].po_no != undefined &&
 * strl_scope.prodCostList[i].po_no != ""){ elem[0].parentNode.lastChild.value =
 * strl_scope.prodCostList[i].prod_cost_code;
 * strl_scope.prodCostList[i].isSet=true;break; } } } } $timeout(function () {
 * $("#prodCost tr:nth-child("+(strl_scope.prodCostList.length)+")
 * td:nth-child("+(2)+")").find(".acontainer input").select(); }, 1); } }; }]);
 */

mrpApp
		.directive(
				'autocompeteText',
				[
						'$timeout',
						function($timeout) {
							return {

								controller : function($scope, $http) {
									$scope.currentIndex = 0;
									$("#items_table tbody tr td")
											.keyup(
													'input',
													function(e) {
														$scope.currentIndex = e.currentTarget.parentElement.rowIndex - 1;

														if (e.which != 9
																&& e.which != 13
																&& e.which != 109
																&& e.which == 8) {
															if (e.currentTarget.cellIndex == 1) {
																$scope
																		.$apply(function() {
																			$scope
																					.clear_in_production_editmode(e);
																			$scope
																					.alert_for_codeexisting(false);
																		});
															}
														} else if (e.which == 13) {
															if (e.currentTarget.cellIndex == 1) {
																if ($scope.ProductionList[$scope.ProductionList.length - 1].stock_item_id != ""
																		|| $scope.ProductionList[$scope.ProductionList.length - 1].stock_item_id != undefined) {
																	$(
																			"#items_table tr:nth-child("
																					+ (e.currentTarget.parentElement.rowIndex + 1)
																					+ ") td:nth-child("
																					+ (4)
																					+ ")")
																			.find(
																					"#produced_qty")
																			.select();
																}
															}
														} else if (e.which == 9) {
															if (e.currentTarget.cellIndex == 1) {
																$(
																		"#items_table tr:nth-child("
																				+ (e.currentTarget.parentElement.rowIndex + 1)
																				+ ") td:nth-child("
																				+ (2)
																				+ ")")
																		.find(
																				".acontainer input")
																		.focus();
															}
														}

													});
									$scope.element = [];
									$scope.table_itemsearch_rowindex = 0;
									$scope.tableClicked = function(index) {

										$scope.table_itemsearch_rowindex = index;
									};
									return $scope;

								},
								link : function(scope, elem, attrs, controller) {
									var strl_scope = controller;
									var items = $(elem)
											.tautocomplete(
													{
														columns : [
																'id',
																'code',
																'name',
																'uomcode',
																'uomname',
																'is_combo_item',
																'is_manufactured',
																'is_active',
																'is_synchable',
																'item_category_name',
																'item_category_id',
																'department_id',
																'department_name',
																'fixed_price',
																'item_cost' ],
														hide : [ false, true,
																true, false,
																false, false,
																false, false,
																false, false,
																false, false,
																false, false,
																false ],
														placeholder : "search ..",
														highlight : "hightlight-classname",
														norecord : "No Records Found",
														delay : 10,
														onchange : function() {
															var selected_item_data = items
																	.all();
															strl_scope
																	.$apply(function() {
																		var count = 0;
																		for ( var i = 0; i < strl_scope.ProductionList.length; i++) {
																			if (selected_item_data.id != "")
																				if (i != strl_scope.currentIndex)
																					if (selected_item_data.id == strl_scope.ProductionList[i].stock_item_id)
																						count = 1;
																		}

																		if (count != 1) {
																			if (selected_item_data.id != "") {
																				strl_scope
																						.getCurrentItem(
																								selected_item_data.id,
																								strl_scope.currentIndex);
																				strl_scope.ProductionList[strl_scope.currentIndex].stock_item_id = selected_item_data.id;
																				strl_scope.ProductionList[strl_scope.currentIndex].stock_item_code = selected_item_data.code;
																				strl_scope.ProductionList[strl_scope.currentIndex].stock_item_name = selected_item_data.name;
																				strl_scope.ProductionList[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;
																				strl_scope.ProductionList[strl_scope.currentIndex].department_name = selected_item_data.department_name;
																				$timeout(
																						function() {
																							$(
																									"#items_table tr:nth-child("
																											+ (strl_scope.currentIndex + 1)
																											+ ")")
																									.find(
																											"#produced_qty")
																									.focus();
																						},
																						1);
																			}
																		} else {
																			elem[0].parentNode.lastChild.value = "";
																			strl_scope
																					.alert_for_codeexisting(1);
																		}
																	})
														},
														// autocomplete search
														// stock items includes
														// in selected
														// department
														data : function() {
															var data = strl_scope.itemsData;
															var filterData = [];
															var searchData = eval("/"
																	+ items
																			.searchdata()
																	+ "/gi");
															$
																	.each(
																			data,
																			function(
																					i,
																					v) {
																				if (v.name
																						.search(new RegExp(
																								searchData)) != -1) {
																					// if
																					// (v.department_id
																					// ==
																					// strl_scope.department_id)
																					filterData
																							.push(v);
																				}
																			});
															return filterData;
														}
													});
									for ( var i = 0; i < strl_scope.ProductionList.length; i++) {
										if (strl_scope.formData.id != undefined
												&& strl_scope.formData.id != '') {
											if (strl_scope.ProductionList[i].flag == 0) {
												elem[0].parentNode.lastChild.value = strl_scope.ProductionList[i].stock_item_code;
												strl_scope
														.disable_search_text(elem[0].parentNode.lastChild);
												strl_scope.ProductionList[i].flag = 1;
												break;
											}
										}
									}
									$timeout(
											function() {
												$(
														"#items_table tr:nth-child("
																+ (strl_scope.ProductionList.length)
																+ ") td:nth-child("
																+ (2) + ")")
														.find(
																".acontainer input")
														.select();
											}, 1);
								}
							}
						} ]);

// to set body height
/*
 * $(document).ready(function() { var divHeight = $('body').height();
 * alert(divHeight); $('.prod_process_tab_insert_div').css('height',
 * divHeight+'px'); alert(divHeight); });
 */