
//Controller for Table and Form 
mrpApp.controller('vouchers', vouchers);


function vouchers($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,
		MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {

	$controller('DatatableController', {$scope: $scope});

	
//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			
			$scope.formData.code = response;});}

	set_sub_menu("#settings");		
	setMenuSelected("#vouchers_leftmenu");			//active leftmenu
	manageButtons("add");

	$scope.formData = {};
	$scope.show_table=true;

	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
	$scope.is_balance_cash=false;
	$scope.showAddBtn=true;
	$scope.showAddFooter=true;
	$scope.showEditFooter=false;
	$scope.enableType=true;
	$scope.setUpdateFlag=0;
	
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.code_existing_validation = code_existing_validation;
	vm.function_clear_select=function_clear_select;

	vm.dtInstance = {};	
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	$scope.voucherTypeData={};
	vm.dtColumns = [
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','250px').renderWith(
	                		function(data, type, full, meta) {

	                			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}
	                			
	                			return '<a id="rcd_edit" class='+css+' ng-click="show_table = false;show_form=true;showEditBtn=true;showAddBtn=false;" style="cursor:pointer;"  ng-href="#">'
	                			+ data + '</a>';
	                		
	                			//return urlFormater(data);  
	                		}),
	                		DTColumnBuilder.newColumn('name').withTitle('NAME'),
	                		/*DTColumnBuilder.newColumn('').withTitle('VOUCHER TYPE'),*/

	                		DTColumnBuilder.newColumn('value').withTitle('VOUCHER VALUE').withOption('width','250px'),

	                		];

	function urlFormater(data) {

		return '<a id="rcd_edit" ng-click="show_table = false;show_form=true;showEditBtn=true;showAddBtn=false;" style="cursor:pointer;"  ng-href="#">'
		+ data + '</a>';   
	}

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {	//Rowcallback fun for Get Edit Data when clk on Code
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
						if(test[0][i] == vm.dtInstance.DataTable.row(".selected").index()){
							current_row_index = i;
						}
					}
					$("#show_form").val(1);
					edit(rowData,current_row_index,e);
				} 
			});
		});
		return nRow;
	}

	/*function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
	    var pageInfo = api.page.info();

	    return pageInfo.page+1 +" / "+pageInfo.pages;

	}*/

	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){
			return pageInfo.page +" / "+pageInfo.pages;
		}else{
			return pageInfo.page+1 +" / "+pageInfo.pages;
		}


	}


	$rootScope.$on('reloadDatatable',function(event){					//reload Datatable
		reloadData();
	});

	$rootScope.$on('hide_table',function(event){
		showTable(event);
	});

	$rootScope.$on('hide_form',function(event){
		$("#show_form").val(0);
		$scope.show_table=true;
		$scope.show_form=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
	});

	function showTable(event){
		$scope.show_table=false;
		$scope.show_form=true;
	}


	function reloadData() {
		vm.dtInstance.reloadData(null, true);
	}


	function edit(row_data,cur_row_index,event) {									

		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function
		/*var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;*/
		var row_count = vm.dtInstance.DataTable.rows().data();
		row_count=row_count.length;	
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
		showTable();
		clearform();
		function_clear_select();
		$scope.showAddBtn=false;
		
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,description:row_data.description,value:row_data.value,
				account_code:row_data.account_code,voucher_type:row_data.voucher_type};
		if(row_data.is_change_payable == 1){$scope.is_balance_cash=true;}
		else{$scope.is_balance_cash=false;}
		$scope.disable_all = true;
		$scope.disable_code=true;
		$scope.hide_code_existing_er = true;
	}

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
		}}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).cancel('No').ok(
		'Yes')
		;
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			$http({
				url : 'delete',
				method : "POST",
				params : {id:$scope.formData.id},
			}).then(function(response) {
				if(response.data==0)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Department "+FORM_MESSAGES.ALREADY_USE+"");
				}

				else if(response.data == 1)
				{
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);

					$("#show_form").val(0);
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
					$scope.disable_all = true;
					$scope.disable_code = true;
					vm.dtInstance.reloadData(null, true);
					vm.dtInstance1.reloadData(null, true);
					$(".acontainer input").attr('disabled', true);
					reloadData();
				}
				else{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event)
					);}

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


	function select_next_prev_row(index){										//set NEXT-PREV Data In Form Atfer Deleteion

		var cur_index=index;
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		if(row_count != 1){
			var row_data = vm.dtInstance.DataTable.rows().data();
			if(index == row_count-1){
				index = index-1;
			}else{
				index = index+1;
			}
			var selcted_row_data = vm.dtInstance.DataTable.rows(index).data();
			edit(selcted_row_data[0],index);
			$rootScope.$emit("next_formdata_set",index);
			if(cur_index !=0){
				if(cur_index-1 == 0){
					$rootScope.$emit("disable_prev_btn");

				}else if(index+1 == row_count-1){
					$rootScope.$emit("disable_next_btn");

				}
			}else if(cur_index == 0){
				if(index-1 == 0){
					$rootScope.$emit("disable_prev_btn");

				}else if(index+1 == row_count-1){
					$rootScope.$emit("disable_next_btn");

				}
			}


		}else if(row_count == 1){
			$scope.show_table=true;
			$scope.show_form=false;
			manageButtons("add");
		}
		return index;

	}


	function view_mode_aftr_edit(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$scope.disable_code = true;
	}
	
	function d2(n) {
		if(n<9) return "0"+n;
		return n;
	}
	today = new Date();

	$rootScope.$on('fun_save_data',function(event){		//Save Function
		if (code_existing_validation($scope.formData)) {
		$scope.Quetable.shopId=settings['currentcompanyid1'];
		$scope.Quetable.origin=settings['currentcompanycode1'];
		
		
	
	$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
		if($scope.formData.created_by == null || $scope.formData.created_by ==undefined ||$scope.formData.created_by =="")
		{
	$scope.formData.created_by=parseInt(strings['userID']);
		}
	if($scope.formData.created_at == null || $scope.formData.created_at ==undefined ||$scope.formData.created_at =="")
		{
	$scope.formData.created_at = today.getFullYear() + "-" + d2(parseInt(today.getMonth()+1)) + "-" + d2(today.getDate()) + " " + d2(today.getHours()) + ":" + d2(today.getMinutes()) + ":" + d2(today.getSeconds());
		}
	$scope.formData.updated_at=today.getFullYear() + "-" + d2(parseInt(today.getMonth()+1)) + "-" + d2(today.getDate()) + " " + d2(today.getHours()) + ":" + d2(today.getMinutes()) + ":" + d2(today.getSeconds());
	$scope.formData.updated_by=parseInt(strings['userID']);
	if($scope.is_balance_cash == true){$scope.formData.is_change_payable =1;}
	else{$scope.formData.is_change_payable =0;}
			$http({
				url : 'saveVoucher',
				method : "POST",
				params : $scope.formData,
				data : {Quetable:$scope.Quetable}
			}).then(function(response){
				if(response.data == 1){
				var DataObj = {};	
				DataObj.alertClass   = "alert-box";
				DataObj.alertStatus  = false;
				DataObj.divId        = "#alertMessageId";	
				if($scope.formData.id !=undefined)
				{
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
				}
				else
				{
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);

				}




				if($scope.formData.id !=undefined){
					view_mode_aftr_edit();
				}else{
					$scope.formData ={};
					$scope.is_balance_cash=false;
					$scope.fun_get_pono();
					$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
				}
				reloadData();
				$scope.hide_code_existing_er = true;
			}else{
				
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Save failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
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
	});

	$rootScope.$on("fun_discard_form",function(event){				//Discard function

		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(FORM_MESSAGES.DISCARD_WRNG).targetEvent(event).cancel('No').ok(
		'Yes')
		;
		$mdDialog.show(confirm).then(function() {
			var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			if($scope.formData.id == undefined){
				$scope.formData ={};
				$scope.fun_get_pono();
				//$scope.formData.parent_id = $scope.sample[0].id;
				$scope.hide_code_existing_er = true;

			}else{
				var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
				edit(row_data[0],cur_row_index);
			}
			clearform();
			function_clear_select();
		});
	});

	$rootScope.$on("fun_enable_inputs",function(){
		$("#show_form").val(1);
		$scope.disable_all = false;
	});

	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});
	
	
	

	$rootScope.$on("fun_clear_form",function(){
		function_clear_select();
		$scope.formData = {};
		$scope.voucherTypeData={};
		$scope.voucher_class_name="";
		$scope.is_balance_cash=false;
		$scope.showAddBtn=true;
		$scope.enableType=true;
		$scope.fun_get_pono();
		$scope.hide_code_existing_er = true;
		$scope.showAddFooter=true;
		$scope.showEditFooter=false;
		$scope.formData.voucher_type='0';
		$scope.setUpdateFlag=0;
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		//$scope.formData.parent_id = $scope.sample[0].id;
	});

	$rootScope.$on("fun_next_rowData",function(e,id){

		var current_row_index = 0;
		var dataIndex = vm.dtInstance.DataTable.rows();
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(current_row_index == 0){
			$rootScope.$emit("enable_prev_btn");
		}

		if(row_data.length == current_row_index+1){
			$rootScope.$emit("disable_next_btn");
		}
		var next_row_index = current_row_index+1;
		if(row_data[next_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(dataIndex[0][next_row_index]).data();
			edit(selcted_row_data[0],next_row_index);
			$rootScope.$emit("next_formdata_set",next_row_index);
		}

	});

	$rootScope.$on("fun_prev_rowData",function(e,id){
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

	// Validation 

	function code_existing_validation(data){
		var flg = true;

		var row_data = vm.dtInstance.DataTable.rows().data();
		if(data.id == undefined || data.id == ""){
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#code").select();
			}


		}

		if(validation() == false){
			flg = false;	}
		if($scope.formData.voucher_type == undefined || $scope.formData.voucher_type == '0'){
			$("#form_div_voucher_type").addClass("has-error");
			$("#form_div_voucher_type_error").show();

			flg = false;
		}else{
			$("#form_div_voucher_type").removeClass("has-error");
			$("#form_div_voucher_type_error").hide();
		}
		
		if($scope.formData.value == undefined || $scope.formData.value == 0){
			$("#form_div_voucher_value").addClass("has-error");
			$("#form_div_voucher_value_error").show();

			flg = false;
		}else{
			$("#form_div_voucher_value").removeClass("has-error");
			$("#form_div_voucher_value_error").hide();
		}
		if($scope.formData.account_code == undefined || $scope.formData.account_code == " "){
			$("#form_div_account_code").addClass("has-error");
			$("#form_div_account_code_error").show();

			flg = false;
		}else{
			$("#form_div_account_code").removeClass("has-error");
			$("#form_div_account_code_error").hide();
		}
		


		if(flg==false)
		{
			focus();
		}

		return flg;

	}


	function function_clear_select(){
		$("#form_div_voucher_type").removeClass("has-error");
		$("#form_div_voucher_type_error").hide();
		$("#form_div_account_code").removeClass("has-error");
		$("#form_div_account_code_error").hide();
		$("#form_div_voucher_value").removeClass("has-error");
		$("#form_div_voucher_value_error").hide();
		

	}
	$http({
		method: 'GET',
		url : "getVouchrDropDown"
	}).success(function (result) {
		$scope.voucherTypeList = result.voucherTypeList;

		$scope.voucherTypeList.splice(0,0,{id : 0 ,name : "select"});
		$scope.formData.voucher_type='0';
		console.log($scope.voucherTypeList);
		
	}); 

	$scope.addVoucherClass=function()
	{
	$('#addVchrClassModal').modal('toggle');
	$scope.voucher_class_name="";
	}
	
	$scope.saveVoucherType=function(event)
	{
		$scope.codeExist=false;
		for(i=0;i< $scope.voucherTypeList.length;i++){
			if($scope.voucherTypeList[i].name==$scope.voucher_class_name){
				$scope.codeExist=true;
				$rootScope.$broadcast('on_AlertMessage_ERR',"Voucher Type Already exists");
				$scope.voucher_class_name="";
				break;
			}
		}

	if($scope.codeExist==false){	
		$scope.Quetable.shopId=settings['currentcompanyid1'];
		$scope.Quetable.origin=settings['currentcompanycode1'];
		
		
	$scope.voucherTypeData.name=$scope.voucher_class_name;
	$scope.voucherTypeData.created_at=today.getFullYear() + "-" + d2(parseInt(today.getMonth()+1)) + "-" + d2(today.getDate()) + " " + d2(today.getHours()) + ":" + d2(today.getMinutes()) + ":" + d2(today.getSeconds());
	$scope.voucherTypeData.created_by=parseInt(strings['userID']);
	if($scope.voucherTypeData.id !=undefined && $scope.voucherTypeData.id !="" && $scope.voucherTypeData.id!=0){$scope.setUpdateFlag=1;}
	$scope.Quetable.curdAction=($scope.setUpdateFlag!=1 )?"C":"U";
	
	var data = JSON.stringify({
		voucherTypeData : $scope.voucherTypeData,
		Quetable:$scope.Quetable
	});

	var fdata = new FormData();
	fdata.append("data", data);
	$http.post("../vouchertype/saveData", fdata, {
		transformRequest : angular.identity,
		headers : {
			'Content-Type' : undefined
		}
	}).success(
			function(response){
	/*$http({
		url : '../vouchertype/saveData',
		method : "POST",
		data:{Quetable:$scope.Quetable,voucherTypeData:$scope.voucherTypeData}
	}).then(function(response){*/
		/*if(response.data != null){
			if($scope.setUpdateFlag ==1)
				{
				$scope.removeUpdatedFromTypeList();
				}
			$scope.voucherTypeList.push(response.data);
			$scope.formData.voucher_type=response.data.id.toString();*/
		if(response.id != null){
			if($scope.setUpdateFlag ==1)
				{
				$scope.removeUpdatedFromTypeList();
				}
			$scope.voucherTypeList.push(response);
			$scope.formData.voucher_type=response.id.toString();
			$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		
	}else{
		
		$mdDialog.show($mdDialog.alert()
				.parent(angular.element(document.querySelector('#dialogContainer')))
				.clickOutsideToClose(true)
				.textContent("Save failed.")
				.ok('Ok!')
				.targetEvent(event)
		);
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
	
	$scope.filterVoucherType=function()
	{
		//alert($scope.formData.voucher_type);
		if($scope.formData.voucher_type !='0')
			{
			$scope.showAddBtn=false;
			}else
				{
				$scope.showAddBtn=true;
				}
	}
	$scope.editVoucherClass=function()
	{
		
		$('#addVchrClassModal').modal('toggle');
		$scope.showAddFooter=false;
		$scope.showEditFooter=true;
		$scope.enableType=false;
		$scope.voucher_class_name=getVoucherTypeName();
		$scope.voucherTypeData.id=$scope.formData.voucher_type;
	}
	$scope.editModalData=function(event){
		
		$scope.enableType=true;
		$scope.showEditFooter=false;
		$scope.showAddFooter=true;
		//$('#addVchrClassModal').modal('toggle');
	}
	function getVoucherTypeName(){
		var typ_nme="";
		for(var i=0;i<$scope.voucherTypeList.length;i++)
			{
			if($scope.voucherTypeList[i].id==$scope.formData.voucher_type)
				{
				typ_nme=$scope.voucherTypeList[i].name;
				break;
				}
			}
		return typ_nme;
	}
	$scope.removeUpdatedFromTypeList=function()
	{
		
		for(var i=0;i<$scope.voucherTypeList.length;i++)
		{
		if($scope.voucherTypeList[i].id==$scope.voucherTypeData.id)
			{
			$scope.voucherTypeList.splice(i,1);
			break;
			}
		}
		
	}
	$scope.deleteVoucherType=function()
	{
		
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).cancel('No').ok(
		'Yes')
		;
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			$http({
				url : '../vouchertype/delete',
				method : "POST",
				params : {id:$scope.voucherTypeData.id},
			}).then(function(response) {
				if(response.data==0)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Voucher Type "+FORM_MESSAGES.ALREADY_USE+"");
				}

				else if(response.data == 1)
				{
					$scope.removeUpdatedFromTypeList();
					$scope.formData.voucher_type="0";
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
					//reloadData();
				}
				else{
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Delete failed.")
							.ok('Ok!')
							.targetEvent(event)
					);}

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
	
	}
	
}


