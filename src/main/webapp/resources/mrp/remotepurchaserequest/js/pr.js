

//Controller for Table and Form 
mrpApp.controller('poctrl', poctrl);

function poctrl($scope,$window, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$controller,MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS) {

/*	set_sub_menu("#store");						
	setMenuSelected("#rpr_left_menu");	*/		//active leftmenu
	manageButtons("add");
	$("#btnAdd").hide();
	$controller('DatatableController', {$scope: $scope});
	var vm = this;
	$scope.purchaseorderdtldata = { items: []};
	$scope.formData = {};
	$scope.show_table=true;
	$scope.show_form=false;
	$scope.hide_code_existing_er = true;	 
	$scope.itemsData = [];

	$http({
		url : '../itemmaster/json',
		method : "GET",
	}).then(function(response) {
		$scope.itemsData = response.data.data;
	}, function(response) { // optional
	});

//	filter data acc chkbx selected
	$scope.reloadtabledata=function(e){
		var filter_id = e.target.id;
		if(filter_id == "isall"){
			$("#open,#request,#approved,#rejected").prop("checked",false);
		}else if(filter_id == "open" || filter_id == "request" || filter_id == "approved" || filter_id == "rejected"){
			$("#isall").prop("checked",false);
			if($("#open").is(':checked') == false && $("#request").is(':checked') == false && $("#approved").is(':checked') == false && $("#rejected").is(':checked') == false){
				$("#isall").prop("checked",true);
			}}$scope.reloadData();}

//	close satuts change	
	$scope.closestatus=function(ev){

		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title('Are you sure you want to reject request?').targetEvent(ev).cancel('No').ok('Yes');
		$mdDialog.show(confirm).then(function() {
			$scope.formData.change_date=new Date().toISOString().slice(0,10);
			$scope.formData.request_status=3;	
			$scope.saveData();
			$scope.closebttn=false;
			$('#btnEdit').hide();
			var DataObj = {};
			DataObj.rprStatus  = false;
			DataObj.divId  = "#rpr_status";
			DataObj.statusClass = "form_list_con rejected_bg";
			DataObj.rpr_status = RECORD_STATUS.REJECTED;
			$scope.setStatusLabel(event,DataObj);


		});

	}


//	Status Label
	$scope.setStatusLabel=function(event,args){
		$(args.divId).removeClass();
		$(args.divId).addClass(args.statusClass);	
		$scope.rprStatus  =  args.rprStatus;
		$scope.rpr_status =  args.rpr_status;	}

	/*//	list datable	
	vm.dtInstance = {};
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;*/

	$scope.statusFilters = [0,1,2,3];
	$scope.selectedStatus = [0,1];
	$scope.toggle = function (item, list) {
		var idx = list.indexOf(item);
		if (idx > -1) {
			list.splice(idx, 1);
		}
		else {
			list.push(item);
		}
		statFilter=list.join();
		DataObj.adnlFilters=[{col:4,filters:statFilter}];
		vm.dtInstance.reloadData();
	};

	$scope.exists = function (item, list) {
		return list.indexOf(item) > -1;
	};

	$scope.isIndeterminate = function() {
		return ($scope.selectedStatus.length !== 0 &&
				$scope.selectedStatus.length !== $scope.statusFilters.length);
	};

	$scope.isChecked = function() {
		return $scope.selectedStatus.length === $scope.statusFilters.length;
	};

	$scope.toggleAll = function() {
		if ($scope.selectedStatus.length === $scope.statusFilters.length) {
			$scope.selectedStatus = [];
		} else if ($scope.selectedStatus.length === 0 || $scope.selectedStatus.length > 0) {
			$scope.selectedStatus = $scope.statusFilters.slice(0);
		}
		statFilter=$scope.selectedStatus.join();
		DataObj.adnlFilters=[{col:4,filters:statFilter}];
		vm.dtInstance.reloadData();
	};

//	list datable	
var vm = this;
vm.dtInstance = {};
var DataObj = {};	
var statFilter="0,1";
DataObj.sourceUrl = "getDataTableData";
DataObj.infoCallback = infoCallback;
DataObj.rowCallback = rowCallback;
DataObj.adnlFilters=[{col:4,filters:statFilter}];
DataObj.order="desc";

vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
vm.dtColumns = [   DTColumnBuilder.newColumn('remarks').withTitle('REQ NO').notVisible().withOption('searchable', false),	 
                   DTColumnBuilder.newColumn('request_number').withTitle('REQ NO').withOption('width','150px').withOption('type', 'natural').renderWith(
                		   function(data, type, full, meta) {data='<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
                			   + data + '</a>'; return data; }),
                			   DTColumnBuilder.newColumn('request_company_name').withTitle('COMPANY'),
                			   DTColumnBuilder.newColumn('submit_date').withTitle('REQ DATE').withOption('width','150px').renderWith(
                					   function(data, type, full, meta) {	if(data!=undefined && data!='' ){
                						   data =geteditDateFormat(data); }else{data='';}return data;}),
                						   DTColumnBuilder.newColumn('request_status').withTitle('STATUS').withOption('width','120px').renderWith(
                								   function(data, type, full, meta) {
                									   if(data==0){data='<a style="color:blue">'+RECORD_STATUS.NEW+'</a>';	}
                									   else if(data==1){data='<a style="color:green">'+RECORD_STATUS.PROCESSING+'</a>';}
                									   else if(data==2){data='<a style="color:VIOLET">'+RECORD_STATUS.FINISHED+'</a>';}
                									   else if(data==3){data='<a style="color:red">'+RECORD_STATUS.REJECTED+'</a>';}
                									   else{ data='';}
                									   return data;}),
                									   DTColumnBuilder.newColumn('request_by').withTitle('REQUEST BY').withOption('width','250px'),
                									   DTColumnBuilder.newColumn('request_number').withTitle('REQ NO').notVisible().withOption('searchable', false),
                									   DTColumnBuilder.newColumn('request_company_id').withTitle('REQ NO').notVisible().withOption('searchable', false),
                									   DTColumnBuilder.newColumn('request_company_code').withTitle('REQ NO').notVisible().withOption('searchable', false),

                									   ];



function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {					//Rowcallback fun for Get Edit Data when clk on Code

	$('a', nRow).unbind('click');
	$('a', nRow).bind('click', function(e) {
		$scope.$apply(function(){
			$('tr.selected').removeClass('selected');
			if (e.target.id == "rcd_edit") {
				var rowData = aData;
				$(nRow).addClass('selected');	
				var current_row_index = vm.dtInstance.DataTable.row(".selected").index();
				var current_row_index = 0;
				var test = vm.dtInstance.DataTable.rows();
				for(var i = 0;i<test[0].length;i++){
					if(test[0][i] == vm.dtInstance.DataTable.row(".selected").index()){
						current_row_index = i;}}
				$scope.edit(rowData,current_row_index,e);
				 $('#show_form').val(1);} });});
	return nRow;}

//ino of table
function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
	var api = this.api();
	var pageInfo = api.page.info();
	if(pageInfo.pages == 0){
		return pageInfo.page +" / "+pageInfo.pages;
	}else{
		return pageInfo.page+1 +" / "+pageInfo.pages;
	}}


$rootScope.$on('reloadDatatable',function(event){					//reload Datatable
	$scope.reloadData();
});

$rootScope.$on('hide_table',function(event){
	$scope.showTable(event);
});

$rootScope.$on('hide_form',function(event){
	 $('#show_form').val(0);
	$scope.show_table=true;
	$scope.show_form=false;
	$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
});

$scope.showTable=function(event){
	$scope.show_table=false;
	$scope.show_form=true;
	$scope.disable_code = true;
	$scope.rprStatus =true;	}

$scope.reloadData=function() {
	vm.dtInstance.reloadData(null, true);
}


////edit data
$scope.edit=function(row_data,cur_row_index,event) {

	$rootScope.$emit("enable_prev_btn");
	$rootScope.$emit("enable_next_btn");//Edit function
	var row_count = vm.dtInstance.DataTable.rows().data();
	row_count=row_count.length;			if(row_count == 1){
		$rootScope.$emit("disable_next_btn");
		$rootScope.$emit("disable_prev_btn");
	}else{
		if(cur_row_index == 0){
			$rootScope.$emit("enable_next_btn");
			$rootScope.$emit("disable_prev_btn");
		}else if(row_count-1 == cur_row_index){
			$rootScope.$emit("disable_next_btn");
			$rootScope.$emit("enable_prev_btn");}}
	$scope.showTable();
	clearform();
	$rootScope.$emit("show_edit_btn_div",cur_row_index);
	$scope.formData = {id:row_data.id,request_number:row_data.request_number,request_company_id:row_data.request_company_id,request_company_code:row_data.request_company_code,request_company_name:row_data.request_company_name,submit_date:geteditDateFormat(row_data.submit_date),request_by:row_data.request_by,request_destination:row_data.request_destination,request_status:row_data.request_status,remarks:row_data.remarks};
	$(".acontainer input").val(row_data.supplier_code);
	var DataObj = {};
	DataObj.rprStatus  = false;
	DataObj.divId  = "#rpr_status";
	$scope.closebttn=false;
	$scope.disable_btn=true;
	if(row_data.request_status==0){DataObj.statusClass = "form_list_con pending_bg";
	DataObj.rpr_status = RECORD_STATUS.NEW;	$scope.disable_btn=false;$scope.closebttn=true;$('#btnEdit').show();$scope.status="Reject Request";}
	else if(row_data.request_status==1){DataObj.statusClass = "form_list_con approved_bg";
	DataObj.rpr_status = RECORD_STATUS.PROCESSING;$('#btnEdit').hide();}
	else if(row_data.request_status==2){DataObj.statusClass = "form_list_con print_bg";
	DataObj.rpr_status = RECORD_STATUS.FINISHED;$('#btnEdit').hide();}
	else if(row_data.request_status==3){DataObj.statusClass = "form_list_con rejected_bg";
	DataObj.rpr_status = RECORD_STATUS.REJECTED;$('#btnEdit').hide();}
	$scope.setStatusLabel(event,DataObj);
	$scope.purchaseorderdtldata = {items: []};
	$scope.remote_request_hdr_id = {id:row_data.id};
	 $scope.prograssing = true;

	$http({
		url :'../remoterequestdtl/rpr_dtllist',
		method : "GET",
		params :  $scope.remote_request_hdr_id,
	}).then(function(response) {
		
		var data = response.data.rprDtl;
		var data_len = data.length;	
		for(var l=0; l < data_len;l++){
			data[l].qty=(parseFloat(data[l].qty)).toFixed(settings['decimalPlace']);
			data[l].expected_date =geteditDateFormat(data[l].expected_date);
			data[l].flag=0;
			if(data[l].po_id==undefined){data[l].po_id=0};
			$scope.purchaseorderdtldata.items.push(data[l]);}
		 $scope.prograssing = false;
	
	}); 
	$scope.disable_all = true;
	$(".acontainer input").attr('disabled', true);
	$scope.disable_code = true;	}



//Delete Function
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
			params : {id:$scope.formData.id},
		}).then(function(response) {
			$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
			 $('#show_form').val(0);
			$scope.reloadData();
			$scope.show_table=true;
			$scope.show_form=false;
			manageButtons("add");
			$scope.disable_all = true;
			$scope.disable_code = true;
		});});});

$scope.view_mode_aftr_edit=function(){
	var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
	$rootScope.$emit("show_edit_btn_div",cur_row_index);
	$scope.disable_all = true;
	$(".acontainer input").attr('disabled', true);
	$scope.disable_code = true;}

//save
$scope.saveData=function(){

	if ($scope.code_existing_validation($scope.formData)) {	
		var date =get_date_format();
		if($scope.formData.submit_date!=undefined && $scope.formData.submit_date!=''){
			$scope.formData.submit_date=getMysqlFormat($scope.formData.submit_date);}
		/*$scope.formData.rpr_dtl_id= [];
		$scope.formData.stock_item_id= [];
		$scope.formData.stock_item_code= [];
		$scope.formData.stock_item_name= [];
		$scope.formData.qty= [];
		$scope.formData.expected_date= [];
		$scope.formData.request_status1= [];
		$scope.formData.po_id= [];
		for(var i = 0;i<$scope.purchaseorderdtldata.items.length;i++){
			$.each($scope.purchaseorderdtldata.items[i],function(key,value){
				if(key == "id"){
					if(value==null){value=0;}
					$scope.formData.rpr_dtl_id.push(value);}					
				else if(key == "stock_item_id"){
					$scope.formData.stock_item_id.push(value);
				}else if(key == "stock_item_code"){
					$scope.formData.stock_item_code.push(value);
				}else if(key == "stock_item_name"){
					$scope.formData.stock_item_name.push(value);
				}else if(key == "qty"){
					if(value==null || value=="" ){
						value=0.00;}
					$scope.formData.qty.push(value);
				}else if(key == "expected_date"){
					var reqdate=getMysqlFormat(value);
					$scope.formData.expected_date.push(reqdate);
				}else if(key == "request_status"){
					$scope.formData.request_status1.push(value);
				}else if(key == "po_id"){
					if(value==null){
						value=0;}$scope.formData.po_id.push(value);}});}	
		*/
		
		
		$scope.rprDtlList = [];
		for(var i = 0;i<$scope.purchaseorderdtldata.items.length;i++){
			$scope.purchaseorderdtldata.items[i].expectedDate=getMysqlFormat($scope.purchaseorderdtldata.items[i].expected_date);
			$scope.rprDtlList.push($scope.purchaseorderdtldata.items[i]);}
			$scope.formData.stockDetailLists=JSON.stringify($scope.rprDtlList);
		
		$http({
			url : 'saveStockItem',
			method : "POST",
			data :  $scope.formData,
		}).then(function(response) {
			$scope.formData.submit_date=geteditDateFormat($scope.formData.submit_date);
			$scope.disable_btn=true;
			if($scope.formData.id !=undefined)
			{ $rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);

			if($scope.formData.request_status==0){
				$scope.closebttn=true;
				$scope.disable_btn=false;}
			$scope.view_mode_aftr_edit();}
			else{
				$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);

				$scope.fun_get_pono();
				var date =get_date_format();
				$scope.formData = {id:null,request_company_id:settings['currentcompanyid1'],request_company_code:settings['currentcompanycode1'],request_company_name:settings['currentcompanyname'],submit_date:date.mindate,request_by:'',request_destination:'',request_status:0,remarks:''};
				$scope.purchaseorderdtldata = {items: []};
				$scope.purchaseorderdtldata.items.push({
					id:null,
					remote_request_hdr_id:null,
					stock_item_id:'',
					stock_item_code:'',
					stock_item_name:'',
					qty:0,
					expected_date:date.mindate,
					request_status:0,
					po_id:null});
				$(".acontainer input").val('');
				$("#table_validation_alert").removeClass("in");
			}$scope.reloadData();
			$scope.hide_code_existing_er = true;
		}, function(response) { // optional
			$mdDialog.show($mdDialog.alert()
					.parent(angular.element(document.querySelector('#dialogContainer')))
					.clickOutsideToClose(true)
					.textContent("Save failed.")
					.ok('Ok!')
					.targetEvent(event)
			);
		});}}


//Save Function
$rootScope.$on('fun_save_data',function(event){	
	$scope.saveData();});
//discard
$rootScope.$on("fun_discard_form",function(event){				//Discard function

	var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
		var $dialog = angular.element(document.querySelector('md-dialog'));
		var $actionsSection = $dialog.find('md-dialog-actions');
		var $cancelButton = $actionsSection.children()[0];
		var $confirmButton = $actionsSection.children()[1];
		angular.element($confirmButton).removeClass('md-focused');
		angular.element($cancelButton).addClass('md-focused');
		$cancelButton.focus();
	}}).title(FORM_MESSAGES.DISCARD_WRNG).targetEvent(event).cancel('No').ok('Yes');
	$mdDialog.show(confirm).then(function() {
		/*	if($scope.formData.id==undefined){
				$scope.show_table=true;
				$scope.show_form=false;
				manageButtons("add");
			}else{*/
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		if($scope.formData.id == undefined){
			$scope.disable_btn=true;
			$scope.fun_get_pono();
			var date =get_date_format();
			$scope.formData = {id:null,request_company_id:settings['currentcompanyid1'],request_company_code:settings['currentcompanycode1'],request_company_name:settings['currentcompanyname'],submit_date:date.mindate,request_by:'',request_destination:'',request_status:0,remarks:''};
			$scope.purchaseorderdtldata = {items: []};
			$scope.purchaseorderdtldata.items.push({
				id:null,
				remote_request_hdr_id:null,
				stock_item_id:'',
				stock_item_code:'',
				stock_item_name:'',
				qty:0,
				expected_date:date.mindate,
				request_status:0,
				po_id:null
			});
			$(".acontainer input").val('');
			$("#table_validation_alert").removeClass("in");
			$scope.hide_code_existing_er = true;
		}else{
			
			var dataIndex = vm.dtInstance.DataTable.rows();
			var row_data = vm.dtInstance.DataTable.rows(dataIndex[0][cur_row_index]).data();
			$scope.edit(row_data[0],cur_row_index);}
		clearform();}/*}*/);});


$rootScope.$on("fun_enable_inputs",function(){
	$scope.disable_all = false;
	$scope.disable_btn=true;
	$(".acontainer input").attr('disabled', false);
	$scope.closebttn=false;});

$rootScope.$on("fun_enable_inputs_code",function(){
	$scope.disable_code = false;});

$rootScope.$on("fun_clear_form",function(){
	$scope.disable_btn=true;
	$scope.fun_get_pono();
	var date =get_date_format();
	$scope.formData = {id:null,request_company_id:settings['currentcompanyid1'],request_company_code:settings['currentcompanycode1'],request_company_name:settings['currentcompanyname'],submit_date:date.mindate,request_by:'',request_destination:'',request_status:0,remarks:''};
	$scope.changedate=$("#expected_date").val();			
	$(".acontainer input").val('');
	$scope.disable_code = true;
	$scope.purchaseorderdtldata = {	items: []};
	$scope.purchaseorderdtldata.items.push({
		id:null,
		remote_request_hdr_id:null,
		stock_item_id:'',
		stock_item_code:'',
		stock_item_name:'',
		qty:0,
		expected_date:date.mindate,
		request_status:0,
		po_id:null});});


//Manupulate Formdata when Edit mode - Prev-Next feature add
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
		$scope.edit(selcted_row_data[0],next_row_index);
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
		$scope.edit(selcted_row_data[0],prev_row_index);
		$rootScope.$emit("next_formdata_set",prev_row_index);
	}
	if(current_row_index-1 == 0){
		$rootScope.$emit("disable_prev_btn");
	}});

//code Existing
$scope.code_existing_validation=function(data){
	if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id=="" && $scope.purchaseorderdtldata.items.length!=1 ){
		$scope.purchaseorderdtldata.items.splice($scope.purchaseorderdtldata.items.length-1, 1);
		}
	var flg = true;
	var date="";
	if(validation() == false){
		flg = false;}
	else if($scope.purchaseorderdtldata.items.length > 0){
		var count=0;
		for(var i = 0;i<$scope.purchaseorderdtldata.items.length;i++){
			if($scope.purchaseorderdtldata.items[i].stock_item_id != undefined && $scope.purchaseorderdtldata.items[i].stock_item_id != "" ){
				count=0;
				if($scope.purchaseorderdtldata.items[i].qty == "" || $scope.purchaseorderdtldata.items[i].qty <= 0){
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
					$("#items_table tr:nth-child("+(i+2)+")").find("#itemqty").select();
					flg = false;break;}
				else if($scope.purchaseorderdtldata.items[i].expected_date==null || $scope.purchaseorderdtldata.items[i].expected_date=="" || $scope.purchaseorderdtldata.items[i].expected_date== undefined )
				{date=null;flg=false;break;}}
			else{count =1;break;}}}
	if($scope.purchaseorderdtldata.items.length == 0){
		$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.TABLE_ERR);
		flg = false;}else if(count==1){
			$(".acontainer").find("input").focus();
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);			
			flg = false;}
		else if(date==null){$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.DATE_ERR);flg = false;}
	if(flg==false){focus();}return flg;}


//alert Message
$scope.alert_for_codeexisting = function(flg){
	if(flg == 1){$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.CODE_EXISIT);}}

///add items to stock item table	
$scope.addItem = function(index) {

	var date =get_date_format();
	if($scope.disable_all==false ){
		if(index<$scope.purchaseorderdtldata.items.length-1){
			$("#items_table tr:nth-child("+(index+3)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
		}else{
		if($scope.purchaseorderdtldata.items.length != 0){
			if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id != undefined && $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id != ""){
				if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].qty == "" || $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].qty =="0"){
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
					$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length-1+2)+")").find("#itemqty").select();
				}else{$scope.purchaseorderdtldata.items.push({
					id:null,
					remote_request_hdr_id:null,
					stock_item_id:'',
					stock_item_code:'',
					stock_item_name:'',
					qty:0,
					expected_date:date.mindate,
					request_status:0,
					po_id:null});}
			}else{
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$(".acontainer").find("input").focus();
				if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id == undefined && $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_code == undefined){

					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				}}}
		else{$scope.purchaseorderdtldata.items.push({
			id:null,
			remote_request_hdr_id:null,
			stock_item_id:'',
			stock_item_code:'',
			stock_item_name:'',
			qty:0,
			expected_date:date.mindate,
			request_status:0,
			po_id:null});}}}}	

//remove stock item
$scope.removeItem = function(index) {
	if($scope.disable_all==false ){
		
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title(FORM_MESSAGES.ROW_DELETE_WRNG).targetEvent(event).cancel('No').ok(
		'Yes')
		;
		$mdDialog.show(confirm).then(function() {
			
		
		if($scope.purchaseorderdtldata.items.length!=1){

		$scope.purchaseorderdtldata.items.splice(index, 1);
		$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length+1)+")").find("#itemqty").select();	
}
	else{
		$rootScope.$broadcast('on_AlertMessage_ERR',"Atleast one item is required.");
	}});}};


	$scope.clear_stock_details_editmode =  function(event){
		$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_id = "";
		$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_code = "";
		$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_name = "";
		$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_batch_id ="";
	}

	$scope.total = function() {
		var total = 0.00;
		angular.forEach($scope.purchaseorderdtldata.items, function(item) {
			total += (item.qty * item.unit_price);});return total;}

//	onchange stock item name
	$scope.elementid=function(elemenvalue){
		if($scope.disable_all==true){$(elemenvalue).attr("disabled", true);}}
	
	$("#request_date").keyup('input',function(e){
		if(e.which!=9 && e.which==13){
		$("#items_table tr:nth-child("+(2)+")").find(".acontainer input").focus();
		}});
														
				
}

/*mrpApp.run(function(RECORD_STATUS) {
	$.fn.dataTable.ext.search.push(
			function(settings, data, dataIndex) {
				var ischeckeddata=false;
				if($('#open').is(':checked')){
					if(data[4]==RECORD_STATUS.PROCESSING) {
						ischeckeddata= true;}}
				if($('#request').is(':checked')){
					if(data[4]==RECORD_STATUS.FINISHED) {
						ischeckeddata= true;}}				
				if($('#approved').is(':checked')){
					if(data[4]==RECORD_STATUS.NEW) {
						ischeckeddata= true;}}
				if($('#rejected').is(':checked')){
					if(data[4]==RECORD_STATUS.REJECTED) {
						ischeckeddata= true;}}
				if($('#isall').is(':checked')){
					ischeckeddata= true;}
				return ischeckeddata;});
});*/

mrpApp.directive('autocompeteText', ['$timeout',function($timeout) {
	return {
		controller: function ($scope,$http) {

			$scope.currentIndex = 0;

			$("#items_table tbody tr td").keyup('input',function(e){
				$scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;

				 if(e.which != 9 && e.which != 13 && e.which != 109 && e.which == 8 ){
					if(e.currentTarget.cellIndex == 1){
						$scope.$apply(function(){
	     					$scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;

							$scope.clear_stock_details_editmode(e);});}} else if(e.which == 13 ){
				     			if(e.currentTarget.cellIndex == 1){
				     				if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id!=""){
				     				{$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length+1)+")").find("#itemqty").select();}
				     		}}
							}else if(e.which == 9 ){
				     			if(e.currentTarget.cellIndex == 1){
				     				
				     				{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find(".acontainer input").focus();}}
				     		
							}});	
			$scope.element = [];
			$scope.table_itemsearch_rowindex=0;
			$scope.tableClicked = function (index) {
				$scope.table_itemsearch_rowindex= index;};
				return $scope;},
				link: function(scope, elem, attrs ,controller) {
					var strl_scope = controller;
					var items = $(elem).tautocomplete({

						columns: ['id' , 'code', 'name','uomcode'],
						hide: [false,true,true,false],
						placeholder: "search ..",
						highlight: "hightlight-classname",
						norecord: "No Records Found",
						delay : 10,
						onchange: function () {
							var selected_item_data =  items.all();
							strl_scope.$apply(function(){var count =0;
							for(var i=0;i<strl_scope.purchaseorderdtldata.items.length;i++){
								if(selected_item_data.id != ""){
			    					if(i != strl_scope.currentIndex){
								if(selected_item_data.id == strl_scope.purchaseorderdtldata.items[i].stock_item_id){
									count=1;}}}}
							if(count != 1){
								strl_scope.purchaseorderdtldata.items[strl_scope.currentIndex].stock_item_id = selected_item_data.id;
								strl_scope.purchaseorderdtldata.items[strl_scope.currentIndex].stock_item_code = selected_item_data.code;
								strl_scope.purchaseorderdtldata.items[strl_scope.currentIndex].stock_item_name = selected_item_data.name;
			    				 strl_scope.purchaseorderdtldata.items[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;

								strl_scope.alert_for_codeexisting(0);
							}else{	 strl_scope.invoice.items[strl_scope.currentIndex].uomcode = "";
									elem[0].parentNode.lastChild.value="";
									strl_scope.alert_for_codeexisting(1);}});},
									data: function () {
										var data = strl_scope.itemsData;
										var filterData = [];
										var searchData = eval("/^" + items.searchdata() + "/gi");
										$.each(data, function (i, v) {
											if ( v.name.search(new RegExp(searchData)) != -1) {
												filterData.push(v);}});return filterData;}});			
					for(var i = 0;i<strl_scope.purchaseorderdtldata.items.length;i++){
						if(strl_scope.purchaseorderdtldata.items[i].remote_request_hdr_id!=undefined && strl_scope.purchaseorderdtldata.items[i].remote_request_hdr_id!='' ){
							if(strl_scope.purchaseorderdtldata.items[i].flag==0){
								elem[0].parentNode.lastChild.value = strl_scope.purchaseorderdtldata.items[i].stock_item_code;
								strl_scope.elementid(elem[0].parentNode.lastChild);
								strl_scope.purchaseorderdtldata.items[i].flag=1;break;}}}
					$timeout(function () { $("#items_table tr:nth-child("+(strl_scope.purchaseorderdtldata.items.length+1)+") td:nth-child("+(2)+")").find(".acontainer input").select();
					 }, 1); 
				}};}]);



