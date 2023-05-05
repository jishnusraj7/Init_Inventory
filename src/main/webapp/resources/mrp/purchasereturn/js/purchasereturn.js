//Controller for Table and Form 
mrpApp.controller('purchasereturn', purchasereturn);

function purchasereturn($compile,$scope, $http,$timeout, $window,$mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,$controller,
		MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS,STATUS_BTN_TEXT,RECORD_STATUS_STYLE) {

	setMenuSelected("#stockreturn_left_menu");
	manageButtons("add");
	$controller('DatatableController', {$scope: $scope});
	$scope.show_table = true;
	$scope.show_finalize_btn = false;
	$scope.show_return_no = false;
	$scope.formData = {};
	$scope.baseUomCode=[];
	$scope.department_data=[];
	var baseUomCode="";
	$scope.checkSupplier={};
	$scope.checkReturn={};
	$scope.returnStock = {items: []};
	
	$("#btnSave").prop('disabled', false);
	$http({
		url : 'formJsonData',
		method : "GET",
	}).then(function(response) {

		$scope.department_data = response.data.depData;

		$scope.supplier_data = response.data.supplier_data;
		$scope.items_data = response.data.items_data;
		$scope.formData.department_id= strings['isDefDepartment'];
		$scope.department_code = strings['isDefDepartmentcode'];
		$scope.department_name = strings['isDefDepartmentname'];
	}, function(response) {});


	//advance filter for new-finalize-print
	$scope.statusFilters = [ 0, 1, 2 ];
	$scope.selectedStatus = [ 0, 1, 2 ];
	$scope.toggle = function(item, list) {
		var idx = list.indexOf(item);
		if (idx > -1) {
			list.splice(idx, 1);
		} else {
			list.push(item);
		}
		statFilter = list.join();
		DataObj.adnlFilters = [ {
			col : 7,
			filters : statFilter
		} ];
		vm.dtInstance.reloadData();
	};

	$scope.exists = function(item, list) {
		return list.indexOf(item) > -1;
	};

	$scope.isIndeterminate = function() {
		return ($scope.selectedStatus.length !== 0 && $scope.selectedStatus.length !== $scope.statusFilters.length);
	};

	$scope.isChecked = function() {
		return $scope.selectedStatus.length === $scope.statusFilters.length;
	};

	$scope.toggleAllStatus = function() {
		if ($scope.selectedStatus.length === $scope.statusFilters.length) {
			$scope.selectedStatus = [];
		} else if ($scope.selectedStatus.length === 0
				|| $scope.selectedStatus.length > 0) {
			$scope.selectedStatus = $scope.statusFilters.slice(0);
		}
		statFilter = $scope.selectedStatus.join();
		DataObj.adnlFilters = [ {
			col : 7,
			filters : statFilter
		} ];
		vm.dtInstance.reloadData();
	};
	
	//create datatable for list
	vm=this;
	vm.dtInstance = {};	
	var DataObj = {};		
	var statFilter="0, 1, 2";
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.adnlFilters=[{col:7,filters:statFilter}];
	DataObj.order = "desc";
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	if(settings['combinePurchase']==0)
	{

		vm.dtColumns = [
			DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
			DTColumnBuilder.newColumn('return_no').withTitle('REF. NO').withOption('type', 'natural').renderWith(
					function(data, type, full, meta) {
						return urlFormater(data);  
					}),
					DTColumnBuilder.newColumn('return_date').withTitle('DATE').renderWith(
							function(data, type, full, meta) {
								data = geteditDateFormat(data);
								return data;
							}),
							DTColumnBuilder.newColumn('supplier_name').withTitle('SUPPLIER').withOption('width','300px'),
						//	DTColumnBuilder.newColumn('supplier_id').withTitle('ID').notVisible().withOption('searchable','false'),
									DTColumnBuilder.newColumn('return_status').withTitle('STATUS').renderWith(
											function(data, type, full, meta) {
												if(data == 0){
													data =  "<span style='color:green;'>"+RECORD_STATUS.PENDING+"</span>";
												}else if(data ==1){
													data = "<span style='color:#3c8dbc;'>"+RECORD_STATUS.FINALIZED+"</span>";


												}else if(data ==2){
													data ="<span style='color:red;'>"+RECORD_STATUS.PRINTED+"<span>";
												}
												return data;
											}),	
											  DTColumnBuilder.newColumn('remarks').withTitle('REMARKS').withOption('width', '120px')
								                .withOption('type', 'natural').renderWith(function(data, type, full, meta) {	                	
								                	return data;
								                }),
										
											];

	}

	else
	{

		vm.dtColumns = [
			DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
			DTColumnBuilder.newColumn('return_no').withTitle('REF. NO').withOption('type', 'natural').renderWith(
					function(data, type, full, meta) {
						return urlFormater(data);  
					}),
					DTColumnBuilder.newColumn('return_date').withTitle('DATE').renderWith(
							function(data, type, full, meta) {
								data = geteditDateFormat(data);
								return data;
							}),
							DTColumnBuilder.newColumn('supplier_name').withTitle('SUPPLIER').withOption('width','300px'),
						//	DTColumnBuilder.newColumn('supplier_id').withTitle('ID').notVisible().withOption('searchable','false'),
									DTColumnBuilder.newColumn('return_status').withTitle('STATUS').renderWith(
											function(data, type, full, meta) {
												if(data == 0){
													data =  "<span style='color:green;'>"+RECORD_STATUS.PENDING+"</span>";
												}else if(data ==1){
													data = "<span style='color:#3c8dbc;'>"+RECORD_STATUS.FINALIZED+"</span>";


												}else if(data ==2){
													data ="<span style='color:red;'>"+RECORD_STATUS.PRINTED+"<span>";
												}
												return data;
											}),	
											  DTColumnBuilder.newColumn('remarks').withTitle('REMARKS').withOption('width', '120px')
								                .withOption('type', 'natural').renderWith(function(data, type, full, meta) {	                	
								                	return data;
								                }),
											
											];

	


	}


	function urlFormater(data) {
		return '<a id="rcd_edit" ng-click="show_table=false;show_form=true;" style="cursor:pointer;" ng-href="#">'
		+ data + '</a>';   
	}

	//Rowcallback fun for Get Edit Data when clk on Code
	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {					
		$('a', nRow).unbind('click');
		$('a', nRow).bind('click', function(e) {
			$scope.$apply(function(){
				$('tr.selected').removeClass('selected');
				if (e.target.id == "rcd_edit") {
					var rowData = aData;
					$(nRow).addClass('selected');	
					var current_row_index = 0;
					var test = vm.dtInstance.DataTable.rows();
					for(var i = 0;i<test[0].length;i++){
						if(test[0][i] == vm.dtInstance.DataTable.row(".selected").index())
							current_row_index = i;
					}
					$('#show_form').val(1);
					edit(rowData,current_row_index,e);
				} 
			});
		});
		return nRow;
	}

	//function for get  page Info
	function infoCallback(settings, start, end, max, total, pre){    
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){
			return pageInfo.page +" / "+pageInfo.pages;
		}else{
			return pageInfo.page+1 +" / "+pageInfo.pages;
		}
	}

	$timeout(function () {$("#DataTables_Table_0_filter").hide(); }, 1); 

	$rootScope.$on("Search",function(event){
		DataObj.adnlFilters = [{}];
		$scope.searchTxtItms = {};
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	
	});

	$("#clear").click(function(){
		DataObj.adnlFilters = [{}];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	
		$scope.searchTxtItms = {};
	});

	$("#clearFeilds").click(function() {		
		$("#dropdownnew").toggle();
		$('#returnStatus').val($("#returnStatus option:first").val());
		$('#returnDate').val("");
		$('#returnNo').val("");
		$('#supplierName').val("");
	});

	$("#closebtnew").click(function(){
		$("#dropdownnew").toggle();		
	});	

	$rootScope.$on("advSearch",function(event){

		$("#dropdownnew").toggle();
		DataObj.adnlFilters = [{}];
		$('#SearchText').text("");
		vm.dtInstance.DataTable.search('').draw();	
		$scope.returnstatus = $('#returnStatus').val();
		$scope.date = $('#returnDate').val();

		if($scope.returnstatus != "")
			var returnstat = $('#returnStatus').find(":selected").text();			

		if($scope.date != null && $scope.date != undefined && $scope.date != "")
			$scope.date = getMysqlFormat($scope.date);

		$scope.returnNo = $('#returnNo').val();
		$scope.suppliername = $('#supplierName').val();			
		$scope.searchTxtItms = {1:returnstat, 2: $scope.date,3: $scope.returnNo, 4:$scope.suppliername};

		for (var key in $scope.searchTxtItms) {
			if ($scope.searchTxtItms.hasOwnProperty(key)) {
				if($scope.searchTxtItms[key] != null && $scope.searchTxtItms[key] != undefined 
						&& $scope.searchTxtItms[key] != "") {

					angular.element(document.getElementById('SearchText')).append(
							$compile("<div id="+key+"  class='advseacrh '  contenteditable='false'>"
									+ $scope.searchTxtItms[key]+"<span class='close-thik' contenteditable='false' "
									+" ng-click='deleteOptn("+key+"); '></span></div>")($scope))

									$scope.deleteOptn = function (key) {
						delete $scope.searchTxtItms[key];	
						$('#'+key).remove();
						switch(key)
						{
						case 1:
							$scope.returnstat = "";
							$('#returnStatus').val("");
							break;
						case 2:
							$scope.date = "";
							$('#returnDate').val();
							break;
						case 3:
							$scope.returnNo = "";
							$('#returnNo').val("");
							break;
						case 4:
							$scope.suppliername = "";
							$('#supplierName').val("");
							break;
						}
						DataObj.adnlFilters=[{col:1,filters:$scope.returnNo},{col:2,filters:$scope.suppliername}
						,{col:3,filters:$scope.date},{col:4,filters:$scope.returnstatus}];
						vm.dtInstance.reloadData(); 
					};
				}
			}
		}
		DataObj.adnlFilters=[{col:1,filters:$scope.returnNo},{col:2,filters:$scope.suppliername},
			{col:3,filters:$scope.date},{col:4,filters:$scope.returnstatus}];
		vm.dtInstance.reloadData();
		$scope.searchTxtItms = {};
	});

	$rootScope.$on('hide_table',function(event){
		hideTable();
	});

	$rootScope.$on("fun_enable_inputs",function(){
		$('#show_form').val(1); 
		$scope.disable_all = false;
		$(".acontainer input").attr('disabled', false);		
	});

	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});

	$rootScope.$on("fun_clear_form",function(){
		$scope.formData = {};
		$scope.show_return_no = false;
		//$scope.isHideReturnStatus = true;
		$("#status_div_id").css("display", "none");
		$scope.show_finalize_btn = false;
		hideTable();
		$scope.returnDtlData = { items : [] };
		var date = get_date_format();
		$scope.formData = { return_status: 0, supplier_id: "", department_id: 2,
				supplier_code: "", supplier_name: "", return_date: date.mindate ,remarks: '' };
		$("#form_div_supplier_code .acontainer").find("input").val("");
		$scope.returnDtlData.items.push({ id : "", stock_return_hdr_id : "", uomcode : "",
			stock_item_id : "", stock_item_code : "", stock_item_name : "", current_stock:"",base_uom_code:"" ,compound_unit:"",return_qty : 0,unit_qty:"" });
	});

	$rootScope.$on('hide_form',function(event){

		$('#show_form').val(0); 
		$("#advsearchbox").show();
		$("#btnAdd").show();
		$scope.show_table = true;
		$scope.show_form = false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
	});

	//Discard function
	$rootScope.$on("fun_discard_form",function(event){		
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}
		).title(FORM_MESSAGES.DISCARD_WRNG).targetEvent(event).ok('Yes').cancel('No');

		$mdDialog.show(confirm).then(function() {
			var date = get_date_format();
			var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			if($scope.formData.id == undefined){
				$scope.formData = {};	
				$scope.returnDtlData = { items: [] };

				$scope.returnDtlData.items.push({ id : "", stock_return_hdr_id : "", uomcode : "", 
					stock_item_id : "", stock_item_code : "", stock_item_name : "", current_stock:"", base_uom_code:"",compound_unit:"",return_qty : 0,unit_qty:"" });

				$scope.formData = { return_status: 0, supplier_id: "",department_id: 2, supplier_code: "", 
						supplier_name: "",  return_date: date.mindate ,remarks: ''};
			}else{
				var dataIndex = vm.dtInstance.DataTable.rows();
				var row_data = vm.dtInstance.DataTable.rows(dataIndex[0][cur_row_index]).data();
				edit(row_data[0],cur_row_index);
			}
			clearform();
			
			$('#form_div_dispose_date').removeClass("has-error");
			$('#form_div_received_date_error').hide();
		});
	});

	//Save Function
	$rootScope.$on('fun_save_data',function(event){
		$scope.saveData(event);
	});

//	Delete Function
	$rootScope.$on("fun_delete_current_data",function(event){

		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).cancel('No').ok('Yes');

		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			$http({
				url : 'delete',
				method : "POST",
				params : {stock_return_hdr_id : $scope.formData.id},
			}).then(function(response) {
				if(response.data == 1){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
					$('#show_form').val(0); 
					vm.dtInstance.reloadData(null, true);
					$("#advsearchbox").show();
					$scope.show_table = true;
					$scope.show_form = false;
					manageButtons("add");
					$scope.disable_all = true;
					$(".acontainer input").attr('disabled', true);
					$scope.disable_code = true;
				}else{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!').targetEvent(event)
					);
				}}, function(response) { // optional
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event));
				});
		}, function() { });
	});

	//supplier backspace keypress 
	$(document).on('keyup','#form_div_supplier_code input',function(e){

		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){

				$('#supplier_code').val("");
				$scope.formData.supplier_id =  "";
				$scope.formData.supplier_code =  "";
				$scope.formData.supplier_name = "";
			});
		}
	});

	//autocompelete suplier data  
	var supplieryData = $("#supplier_code").tautocomplete({
		columns: ['id','code','name'],
		hide: [false,true,true],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay : 10,
		onchange: function () {
			var selected_supplier_data =  supplieryData.all();
			$scope.$apply(function(){
				$scope.formData.supplier_id =  selected_supplier_data.id;
				$scope.formData.supplier_code =  selected_supplier_data.code;
				$scope.formData.supplier_name =  selected_supplier_data.name;
			});
		},
		data: function () {
			var data = $scope.supplier_data;
			var filterData = [];
			var searchData = eval("/^" + supplieryData.searchdata() + "/gi");
			$.each(data, function (i, v) {
				if (v.code.search(new RegExp(searchData)) != -1 || v.name.search(new RegExp(searchData)) != -1) {
					filterData.push(v);}});
			return filterData;
		}
	});

	$rootScope.$on("fun_next_rowData",function(e, id){

		var current_row_index = 0;
		var dataIndex = vm.dtInstance.DataTable.rows();
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(current_row_index == 0)
			$rootScope.$emit("enable_prev_btn");
		if(row_data.length == current_row_index + 1)
			$rootScope.$emit("disable_next_btn");
		var next_row_index = current_row_index + 1;
		if(row_data[next_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][next_row_index]).data();
			edit(selcted_row_data[0],next_row_index);
			$rootScope.$emit("next_formdata_set",next_row_index);
		}
	});

	$rootScope.$on("fun_prev_rowData",function(e, id){

		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var dataIndex = vm.dtInstance.DataTable.rows();
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(row_count-1 == current_row_index){
			$rootScope.$emit("enable_next_btn");
		}
		var prev_row_index = current_row_index-1;
		if(row_data[prev_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][prev_row_index]).data();
			edit(selcted_row_data[0],prev_row_index);
			$rootScope.$emit("next_formdata_set",prev_row_index);
		}
		if(current_row_index-1 == 0){
			$rootScope.$emit("disable_prev_btn");
		}

	});


	$scope.saveData = function(event){
		var stock=[];
		if (form_validation($scope.formData)) 
		{
		$scope.returnData = {stocks: []};	
		angular.forEach($scope.returnDtlData.items, function (item) { 
			$scope.returnData.stocks.push(item.stock_item_code);
		}); 
		var returnStock={stock:$scope.returnData.stocks,supplier:$scope.formData.supplier_id};
		$http({
			url : 'checkReturnStock',
			async:false,
			method:'POST',
			data: returnStock,
		}).then(function(response) {

			if(response.data!=0){				
				for(j=0;j<response.data.stockData.length;j++){
					textboxes = $('#items_table').find('.stock_item_code_return');
					$(textboxes).each(function() {
						var textBox=$(this).val();  					
						if(textBox==response.data.stockData[j].stock_item_code){							
							var row = $(this).closest("tr")[0];
							$("span").closest(row).css({"color": "red", "border": "2px solid red"});
							 $rootScope.$broadcast('on_AlertMessage_ERR',"The item is not purchased from the selected supplier");
						}
					});

					
				}

			}else{
				if (form_validation($scope.formData)) 
				{
						var date = get_date_format();
						$scope.formData.created_by = strings['userID'];
						$scope.formData.created_date = getMysqlFormat(date.mindate);
						$scope.formData.return_date = getMysqlFormat($scope.formData.return_date);
						$scope.formData.returnDetailLists=JSON.stringify($scope.returnDtlData.items);
						$http({
							url : 'savereturn',
							method : "POST",
							data : $scope.formData,
						}).then(function(response) {

							vm.dtInstance.reloadData(null, true);

							if(response.data == 1 )
							{
								if($scope.formData.id != undefined)
								{
									$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
									view_mode_aftr_edit();
									$scope.formData.return_date = geteditDateFormat($scope.formData.return_date);
									$("#btnAdd").hide();
									if($scope.formData.return_status != 0){
										$scope.show_finalize_btn = false;
										$('#btnDelete').hide();
										 $("#btnEdit").hide();
									}
								}else{
									setReturnNumber();
									var date = get_date_format();
									$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
									$scope.formData = {};
									$scope.returnDtlData = {items: []};						
									$scope.formData.return_date = date.mindate;
									$scope.formData.return_status = 0 

									$scope.returnDtlData.items.push({ id : null, stock_return_hdr_id : "", uomcode : "",
										stock_item_id : "", stock_item_code : "", stock_item_name : "", current_stock:"", base_uom_code:"",compound_unit:"", return_qty : 0,unit_qty:"" });
								}
							}else {
								$mdDialog.show($mdDialog.alert()
										.parent(angular.element(document.querySelector('#dialogContainer')))
										.clickOutsideToClose(true)
										.textContent("Save failed.")
										.ok('Ok!').targetEvent(event)
								);
							}
						});

				 }
			}
		});

	}
	}

	$scope.addItem = function(index) {	

		var date = get_date_format();
		var rowCount = $scope.returnDtlData.items.length;

		if($scope.disable_all == false){
			if(index < rowCount - 1){
			//	$("#items_table tr:nth-child("+(index+3)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
				$("#items_table tr:nth-child("+(index+3)+") td:nth-child("+(3)+")").find(".acontainer input").focus();
			}else{
				if(rowCount != 0){
					if($scope.returnDtlData.items[rowCount-1].stock_item_id != undefined  
							&& $scope.returnDtlData.items[rowCount-1].stock_item_id != "")
					{
						if($scope.returnDtlData.items[rowCount-1].base_uom_code == "" || $scope.returnDtlData.items[rowCount-1].base_uom_code == undefined)
						{
							//$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RUnit");
							//flg = false;
							$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
							$("#items_table tr:nth-child("+(rowCount+1)+") td:nth-child("+(3)+")").find("#base_uom_code").select();

						}

						if($scope.returnDtlData.items[rowCount-1].return_qty == "" || $scope.returnDtlData.items[rowCount-1].return_qty <= 0)
						{
							$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
							$("#items_table tr:nth-child("+(rowCount+1)+")").find("#return_qty").select();
						}else
							$scope.returnDtlData.items.push({
								id:null, return_qty: "", return_date: date.mindate,
							});
					}else{
						$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
						$("#items_table tr:nth-child("+(rowCount+1)+") td:nth-child("+(3)+")").find(".acontainer input").focus();
					}
				}else{
					$scope.returnDtlData.items.push({ id : "", stock_return_hdr_id : "", uom_code : "",
						stock_item_id : "", stock_item_code : "", stock_item_name : "",current_stock:"", return_qty : 0 });
				}
			}
		} 
	}

	$scope.removeItem = function(index,event) {

		if($scope.disable_all == false){
			var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
				var $dialog = angular.element(document.querySelector('md-dialog'));
				var $actionsSection = $dialog.find('md-dialog-actions');
				var $cancelButton = $actionsSection.children()[0];
				var $confirmButton = $actionsSection.children()[1];
				angular.element($confirmButton).removeClass('md-focused');
				angular.element($cancelButton).addClass('md-focused');
				$cancelButton.focus();
			}}
			).title(FORM_MESSAGES.ROW_DELETE_WRNG).targetEvent(event).ok('Yes').cancel('No');

			$mdDialog.show(confirm).then(function() {				
				if(index == 0 && $scope.returnDtlData.items.length == 1)
					$rootScope.$broadcast('on_AlertMessage_ERR',"Atleast one item is required.");
				else{
					$scope.returnDtlData.items.splice(index, 1);
					$("#items_table tr:nth-child("+($scope.returnDtlData.items.length+1)+")").find("#return_qty").focus();	
				}
			});
		}
	}

	$scope.ShowCodeExistingError = function(isCodeExists){		
		if(isCodeExists == 1){
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.CODE_EXISIT);
		}
	}

	$scope.disable_search_text = function(elemenvalue){
		if($scope.disable_all == true){
			$(elemenvalue).attr("disabled", true);
		}
	}

	$scope.finalizeReturn = function(event){
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(' Are you sure to finalize?').targetEvent(event).cancel('No').ok('Yes');
		$mdDialog.show(confirm).then(function() {
			$scope.formData.return_status = 1;
			$scope.saveData(event);
		});
	}

	function hideTable() {
		$("#advsearchbox").hide();
		$scope.show_table = false;
		$scope.show_form = true;
	}

	$scope.return_no = "";
	function setReturnNumber() {

		$http.get('getCounterPrefix')
		.success(function (response) {
			$scope.return_no = response;
			$scope.formData.return_no = response;
		});
		return $scope.return_no;
	}

	function view_mode_aftr_edit() {

		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$scope.disable_code = true;
		$(".acontainer input").attr('disabled', true);
	}

	//Edit function
	function edit(row_data,cur_row_index,event) {			

		$("#advsearchbox").hide();
		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");
		
		var row_count = vm.dtInstance.DataTable.rows().data();
		row_count = row_count.length;
		if(row_count == 1){
			$rootScope.$emit("disable_next_btn");
			$rootScope.$emit("disable_prev_btn");
		}else{
			if(cur_row_index == 0){
				$rootScope.$emit("enable_next_btn");
				$rootScope.$emit("disable_prev_btn");
			}else if(row_count-1 == cur_row_index){
				$rootScope.$emit("disable_next_btn");
				$rootScope.$emit("enable_prev_btn");
			}
		}
		hideTable();
		clearform();
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.formData = { id: row_data.id, return_status: row_data.return_status, supplier_id: row_data.supplier_id, 
				supplier_code: row_data.supplier_code, supplier_name: row_data.supplier_name, return_no: row_data.return_no,
				return_date: geteditDateFormat(row_data.return_date), remarks: row_data.remarks};

		if($scope.formData.return_status == 0){
			$scope.show_finalize_btn = true;
			$scope.status = 'NEW';
			$("#status_div_id").css("display", "block");
			$('#btnDelete').show();
			$("#btnEdit").show();
			$scope.changeDataStatus(STATUS_BTN_TEXT.PENDING,
					RECORD_STATUS_STYLE.PENDING_STYLE, RECORD_STATUS.PENDING);
		}
		else{
			$scope.show_finalize_btn = false;
			$scope.status = 'FINALIZED';
			$("#status_div_id").css("display", "block");
			$('#btnDelete').show();
			$("#btnEdit").hide();
			$scope.changeDataStatus(STATUS_BTN_TEXT.FINALIZE,
					RECORD_STATUS_STYLE.ISSUED_STYLE, RECORD_STATUS.FINALIZED);
			//$("#btn_status").css("display", "none");
		}
		$scope.disable_all = true;
		$scope.show_return_no = true;
		//$scope.isHideReturnStatus = false;
		
		$(".acontainer input").attr('disabled', true);
		$("#form_div_supplier_code").find(".acontainer input").val(row_data.supplier_code);
		$scope.formData.department_id= strings['isDefDepartment'];
		$scope.department_code = strings['isDefDepartmentcode'];
		$scope.department_name = strings['isDefDepartmentname'];
		setReturnDetailsTable(row_data);
	}

	$scope.goBackToTypePage = function() {
		$rootScope.$emit('hide_table');
		$("#advsearchbox").hide();
		manageButtons("next");
		clearform();
		$("#btnBack").show();
		$("#status_div_id").css("display", "none");
		$scope.show_type_form = true;
		$('#show_form').val(1);
	}
	
	$scope.changeDataStatus = function(statusBtnTxt, className, status) {
		$scope.statusBtnText = statusBtnTxt;
		$scope.ClassName = className;
		$scope.status = status;
	}
	
	function setReturnDetailsTable(row_data){

		$scope.returnDtlData = {items : []};
		$http({
			url : 'getReturnDetailData',
			method : "GET",
			params : { stock_return_hdr_id:row_data.id },
		}).then(function(response) {
			$scope.returnDtlData.items = response.data.ReturnDtlData;
			for(var i=0;i<$scope.returnDtlData.items.length;i++){
				baseUomCode[i] = $scope.setuomCode($scope.returnDtlData.items[i].uomcode,i);
				$scope.returnDtlData.items[i].unit_qty=parseFloat($scope.returnDtlData.items[i].issued_qty).toFixed(settings['decimalPlace']);
				$scope.returnDtlData.items[i].current_stock=parseFloat($scope.returnDtlData.items[i].current_stock).toFixed(settings['decimalPlace']);
				$scope.returnDtlData.items[i].return_qty=parseFloat($scope.returnDtlData.items[i].return_qty).toFixed(settings['decimalPlace']);
			}			
		})
	}

	function form_validation(data){

		if($scope.returnDtlData.items[$scope.returnDtlData.items.length-1].stock_item_id == "" 
			&& $scope.returnDtlData.items.length != 1 )
		{
			$scope.returnDtlData.items.splice($scope.returnDtlData.items.length-1, 1);
		}

		var flg = true;
		if(validation() == false)
			flg = false;

		if($scope.formData.supplier_code == null || $scope.formData.supplier_code == "" 
			|| $scope.formData.supplier_code == undefined ){
			$("#form_div_supplier_code .acontainer").find("input").focus();
			$('#form_div_supplier_code').addClass("has-error");
			$('#form_div_supplier_code_error').show();
			flg = false;
		}else{
			$('#form_div_supplier_code').removeClass("has-error");
			$('#form_div_supplier_code_error').hide();
		}

		if($scope.returnDtlData.items.length > 0){

			$scope.item_table_empty = [];
			$scope.item_details_table_empty = [];

			for(var i = 0;i<$scope.returnDtlData.items.length;i++){
				if($scope.returnDtlData.items[i].stock_item_id != "" && $scope.returnDtlData.items[i].stock_item_code != ""){
					if($scope.returnDtlData.items[i].return_qty == "" || parseInt($scope.returnDtlData.items[i].return_qty) == 0 
							|| $scope.returnDtlData.items[i].return_qty == "." )
					{
						$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter return Qty");
						$scope.item_details_table_empty.push($scope.returnDtlData.items[i]);
						$("#items_table tr:nth-child("+(i+2)+")").find("#return_qty").select();
						flg = false;
					}					
				}else
					$scope.item_table_empty.push($scope.returnDtlData.items[i]);

				if(parseFloat($scope.returnDtlData.items[i].current_stock =="" ) || parseFloat($scope.returnDtlData.items[i].current_stock)==undefined || parseFloat($scope.returnDtlData.items[i].current_stock)==0.00
						|| parseFloat($scope.returnDtlData.items[i].current_stock) < parseFloat($scope.returnDtlData.items[i].return_qty))
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Current Stock is Unavailable");
					$("#items_table tr:nth-child(" + (i + 1) + ")").find("#issued_qty").select();
					flg = false;
				}
				if($scope.returnDtlData.items[i].base_uom_code == "" || $scope.returnDtlData.items[i].base_uom_code == undefined)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RUnit");
					flg = false;
					$scope.item_details_table_empty.push($scope.returnDtlData.items[i]);
					$("#items_table tr:nth-child("+(i+1)+") td:nth-child("+(3)+")").find("#base_uom_code").select();

				}
			}
			if($scope.item_table_empty.length > 0){
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				//$rootScope.$emit("tableItemCodeErrorMsg");
				$(".acontainer").find("input").focus();
				flg = false;
			}else{
				if($scope.item_details_table_empty.length > 0){
					$("#table_validation_alert").addClass("in");
					//$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter Adjustment Qty");
					flg = false;
				}else{
					$("#table_validation_alert").removeClass("in");
				}
			}
		}else if($scope.returnDtlData.items.length == 0){
			$("#table_validation_alert").addClass("in");
			$rootScope.$broadcast('on_AlertMessage_ERR', ITEM_TABLE_MESSAGES.TABLE_ERR);
			flg = false;
		}

		//if(flg == false)
		//focus();
		return flg;
	}

	$scope.getItemmasterCurrentStock = function(itemId,deptId,retDate,index) {
		var current_stock=0;
		var checkReturn="1";
		if(retDate!="" && retDate!=undefined)	
		{
			$http({
				url : '../stockout/getCurrStock',
				async:false,
				method : "GET",
				params:{checkReturn:checkReturn,itemId:itemId,department_id:deptId,current_date:getMysqlFormat(retDate)},
				async : false,
			}).then(function(response) {
				current_stock = response.data[0];

				$scope.returnDtlData.items[index].current_stock=current_stock;
				//	$scope.invoice.items[index].pending_qty=response.data[0];


			}, function(response) { // optional

			});
		}
		return parseFloat(current_stock).toFixed(settings['decimalPlace']);

	}

	$scope.setuomCode=function(uomCode,index){

		$http({

			url:'../stockin/getPunit',
			async:false,
			method:'POST',
			params:{uomCode:uomCode},
		}).then(function(response){

			$scope.baseUomCode[index]=response.data.baseUom;

		});
	}


	//change punit and calc @gana 22012020
	$scope.selectPuomCode=function(baseUomCode,recQty,index){

		$scope.returnDtlData.items.compound_unit=[];
		$scope.returnDtlData.items.pack_qty=[];
		for(i=0;i<$scope.baseUomCode[index].length;i++){

			if($scope.baseUomCode[index][i].base_uom_code==baseUomCode){

				$scope.returnDtlData.items[index].compound_unit=$scope.baseUomCode[index][i].compound_unit;
				$scope.returnDtlData.items[index].base_uom_code=baseUomCode;
			}
		}
		$scope.returnDtlData.items[index].unit_qty=$scope.returnDtlData.items[index].compound_unit*recQty;	

	}



}

mrpApp.directive('autocompeteText', ['$timeout',function($timeout) {

	return {
		controller: function ($scope,$http) {
			$scope.currentIndex = 0;
			$("#items_table tbody tr td").keyup('input',function(e){
				$scope.currentIndex = e.currentTarget.parentElement.rowIndex - 1;

				if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){
					if(e.currentTarget.cellIndex == 2){
						$scope.$apply(function(){
							$scope.clear_stock_details_editmode(e);
							$scope.ShowCodeExistingError(false);
						});
					}
				}else if(e.which == 13 ){
					if(e.currentTarget.cellIndex == 2){
						if($scope.returnDtlData.items[$scope.returnDtlData.items.length-1].stock_item_id!="")
							$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+") td:nth-child("+(4)+")")
							.find("#damaged_qty").select();
					}
				}else if(e.which == 9 ){
					if(e.currentTarget.cellIndex == 2){
						{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+") td:nth-child("+(3)+")")
							.find(".acontainer input").focus();
						}
					}
					$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+") td:nth-child("+(1)+")")
					.find(".acontainer input").focus();
				}
			});
			$scope.element = [];
			$scope.table_itemsearch_rowindex = 0;
			$scope.tableClicked = function (index) {
				$scope.table_itemsearch_rowindex = index;
			};
			return $scope;
		},
		link: function(scope, elem, attrs ,controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns : [
					'id',
					'code',
					'name',
					'input_tax_id',
					'tax_percentage',
					'is_active',
					'is_manufactured',				
					'valuation_method',
					'uomcode',
					'uomname',	
					'unit_price'
					],
					hide : [ false, true,
						true, false,
						false, false,
						false, false,
						false, false,
						false],
						placeholder: "search ..",
						highlight: "hightlight-classname",
						norecord: "No Records Found",
						delay : 10,
						onchange: function () {
							var selected_item_data =  items.all();
							strl_scope.$apply(function(){
								var count = 0;
								for(var i=0;i<strl_scope.returnDtlData.items.length;i++){
									if(selected_item_data.id != ""){
										if(i != strl_scope.currentIndex){
											if(selected_item_data.id == strl_scope.returnDtlData.items[i].stock_item_id)
												count=1;								
										}
									}
								}
								if(count != 1){
									if(selected_item_data.id!="") {
										strl_scope.formData.department_id=2;
										strl_scope.returnDtlData.items[strl_scope.currentIndex].stock_item_id = selected_item_data.id;
										strl_scope.returnDtlData.items[strl_scope.currentIndex].stock_item_code = selected_item_data.code;
										strl_scope.returnDtlData.items[strl_scope.currentIndex].stock_item_name = selected_item_data.name;
										strl_scope.returnDtlData.items[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;
										strl_scope.returnDtlData.items[strl_scope.currentIndex].current_stock = strl_scope
										.getItemmasterCurrentStock(selected_item_data.id,strl_scope.formData.department_id,strl_scope.formData.return_date,strl_scope.currentIndex);
										strl_scope.returnDtlData.items[strl_scope.currentIndex].base_uom_code = strl_scope.setuomCode(selected_item_data.uomcode,strl_scope.currentIndex);

										strl_scope.ShowCodeExistingError(0);
										$timeout(function () {
											$("#items_table tr:nth-child("+(strl_scope.currentIndex+2)+")").find("#base_uom_code").focus();
										}, 1);
									}
								}else {
									elem[0].parentNode.lastChild.value = "";
									strl_scope.ShowCodeExistingError(1);
								}
							});
						},
						data: function () {

							var data = strl_scope.items_data;
							var filterData = [];
							var searchData = eval("/" + items.searchdata() + "/gi");

							$.each(data, function (i, v) {
								if ( v.NAME.search(new RegExp(searchData)) != -1) {
									filterData.push(v);
								}
							});
							return filterData;
						}
			});

			for(var i = 0; i<strl_scope.returnDtlData.items.length; i++){
				if(strl_scope.formData.id != undefined && strl_scope.formData.id != '' ){
					if(strl_scope.returnDtlData.items[i].flag == 0){
						elem[0].parentNode.lastChild.value = strl_scope.returnDtlData.items[i].stock_item_code;
						strl_scope.disable_search_text(elem[0].parentNode.lastChild);
						strl_scope.returnDtlData.items[i].flag=1;break;
					}
				}
			}
			$timeout(function () {
				$("#items_table tr:nth-child("+(strl_scope.returnDtlData.items.length+1)+") td:nth-child("+(2)+")").find(".acontainer input").select();
			}, 1); 
		}
	};
}]);