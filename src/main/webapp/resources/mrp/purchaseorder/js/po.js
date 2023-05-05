

//Controller for Table and Form 
mrpApp.controller('poctrl', poctrl);

function poctrl($compile,$scope, $http,$timeout, $window,$mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,$controller,MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS) {
	$scope.disable_btn=true;

	
	var filterData = [];

	/*set_sub_menu("#store");						
	setMenuSelected("#po_left_menu");*/			//active leftmenu
	manageButtons("add");
	$controller('DatatableController', {$scope: $scope});
	$('#beforebtns').hide();

	if(companytype==0){$scope.centralized=true;}
	else{$scope.centralized=false;}

	$scope.purchaseorderdtldata = {items: []};
	$scope.formData = {};
	$scope.poStatus=true;
	$("#advsearchbox").show();

	$scope.show_table=true;
	$scope.show_form=false;
	$scope.show_form1=false;
	$scope.show_form2=false;
	$scope.hide_code_existing_er = true;			
	$scope.slctypreq = 1;
	$scope.stockItem_data = [];
	$scope.purchaseorderdtllist=[];
	$scope.supplier_data=[];
	$scope.itemsData = [];
	$scope.remoteorderdtldata = [];
	$scope.searchTxtItms={};
	$scope.enablePrint=false;

//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			$scope.formData.po_number = response;});}


//	all json  data 
	$http({
		url : 'formJsonData',
		method : "GET",
	}).then(function(response) {
		$scope.stockItem_data = response.data.stockItem_data;
		$scope.purchaseorderdtllist = response.data.purchaseorderdtllist;
		$scope.supplier_data = response.data.supplier_data;
		$scope.itemsData = response.data.itemsData;}, function(response) { // optional
		});


	$(document).on('keyup','#form_div_supplier_code input',function(e){
		if(e.which ===13){

			$("#billing_address").focus();

		}
		if(e.which == 9){
			$("#supplier_doc_no").focus();}
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$('#supplier_code').val("");
				$scope.formData.supplier_id =  "";
				$scope.formData.supplier_code =  "";
				$scope.formData.supplier_name = "";});}});

//	autocompelete suplier data  
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
			});},
			data: function () {
				var data = $scope.supplier_data;
				var filterData = [];
				var searchData = eval("/^" + supplieryData.searchdata() + "/gi");
				$.each(data, function (i, v) {
					if (v.code.search(new RegExp(searchData)) != -1 || v.name.search(new RegExp(searchData)) != -1) {
						filterData.push(v);}});
				return filterData;}});


	/*

//	filter data acc chkbx selected
	$scope.reloadtabledata=function(e){
		var filter_id = e.target.id;
		if(filter_id == "isall"){			
			$("#open,#request,#approved").prop("checked",false);
		}else if(filter_id == "open" || filter_id == "request" || filter_id == "approved"){
			$("#isall").prop("checked",false);
			if($("#open").is(':checked') == false && $("#request").is(':checked') == false && $("#approved").is(':checked') == false){
				$("#isall").prop("checked",true);}
		}
		$scope.reloadData();}*/


//	close satuts change	
	$scope.closestatus=function(ev){
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();}}).title('Are you sure to close the order?').targetEvent(ev).cancel('No').ok('Yes');
		$mdDialog.show(confirm).then(function() {
			if ($scope.code_existing_validation($scope.formData)) {	
				var date =get_date_format();
				$scope.formData.change_date=date.mindate;
				$scope.formData.po_status=2;
				$scope.disable_btn=true;
				$scope.status = 'Closed';
				$scope.closebttn=false;
				$scope.printlink=false;
				$scope.formData.po_close_date=getMysqlFormat(date.mindate);
				$scope.saveData(ev);
				var DataObj = {};
				DataObj.poStatus  = false;
				DataObj.divId  = "#po_status";
				DataObj.statusClass = "form_list_con closed_bg";
				DataObj.po_status = "CLOSED";
				$scope.setStatusLabel(event,DataObj);}});}

//	Status Label
	$scope.setStatusLabel=function(event,args){
		$(args.divId).removeClass();
		$(args.divId).addClass(args.statusClass);	
		$scope.poStatus  =  args.poStatus;
		$scope.po_status =  args.po_status;	}

//	close print  change	
	$scope.print=function(ev){
		$scope.postatus=$scope.formData.po_status;

		if($scope.postatus!=1){
			$('#confirmPrintModel').modal('toggle');
			/*	var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
				var $dialog = angular.element(document.querySelector('md-dialog'));
				var $actionsSection = $dialog.find('md-dialog-actions');
				var $cancelButton = $actionsSection.children()[0];
				var $confirmButton = $actionsSection.children()[1];
				angular.element($confirmButton).removeClass('md-focused');
				angular.element($cancelButton).addClass('md-focused');
				$cancelButton.focus();
			}}).title(' You cannot make any changes once you print it.'+FORM_MESSAGES.PRINT_WRNG).targetEvent(ev).cancel('No').ok('Yes');
			$mdDialog.show(confirm).then(function() {*/
			/*});*/		
		}else if($scope.postatus==1){
			$window.open("PoReport?id="+$scope.formData.id+"", '_blank');}}

	$scope.printPO1=function(ev)
	{
		if ($scope.code_existing_validation($scope.formData) ) {	
			var date =get_date_format();
			$scope.formData.po_print_date=new Date().toISOString().slice(0,10);
			$scope.formData.change_date=new Date().toISOString().slice(0,10);
			if($scope.formData.po_status!=2){$scope.formData.po_status=1;}
			$scope.status = 'Close PO';
			$scope.printstatus='Re-Print';
			$scope.formData.po_status=1;
			$scope.saveData(ev);
			var DataObj = {};
			DataObj.poStatus  = false;
			DataObj.divId  = "#po_status";
			DataObj.statusClass = "form_list_con print_bg";
			DataObj.po_status = "PRINTED";
			$scope.setStatusLabel(ev,DataObj);
			$scope.closebttn=true;					
			$('#btnEdit').hide();
			$('#btnDelete').hide();
			$window.open("PoReport?id="+$scope.formData.id+"", '_blank');}
	}

	$scope.statusFilters = [0,1,2];
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
		DataObj.adnlFilters=[{col:6,filters:statFilter}];
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

	/*	  $scope.toggleAllStatus = function() {
	    if ($scope.selectedStatus.length === $scope.statusFilters.length) {
	      $scope.selectedStatus = [];
	    } else if ($scope.selectedStatus.length === 0 || $scope.selectedStatus.length > 0) {
	      $scope.selectedStatus = $scope.statusFilters.slice(0);
	    }
	  };

	 */

	$scope.toggleAllStatus = function() {
		if ($scope.selectedStatus.length === $scope.statusFilters.length) {
			$scope.selectedStatus = [];
		} else if ($scope.selectedStatus.length === 0 || $scope.selectedStatus.length > 0) {
			$scope.selectedStatus = $scope.statusFilters.slice(0);
		}
		statFilter=$scope.selectedStatus.join();
		DataObj.adnlFilters=[{col:6,filters:statFilter}];
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
	DataObj.adnlFilters=[{col:6,filters:statFilter}];
	DataObj.order="desc";

	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	vm.dtColumns = [
		DTColumnBuilder.newColumn('terms').withTitle('').notVisible().withOption('searchable', false),
		DTColumnBuilder.newColumn('po_number').withTitle('PO NO').withOption('width','100px').withOption('type', 'natural').renderWith(
				function(data, type, full, meta) {
					data='<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
						+ data + '</a>';return data;}),
						DTColumnBuilder.newColumn('supplier_name').withTitle('SUPPLIER').withOption('width','300px'),
						DTColumnBuilder.newColumn('po_date').withTitle('PO DATE').withOption('width','120px').renderWith(
								function(data, type, full, meta) {if(data!=undefined && data!='' ){
									data =geteditDateFormat(data);	}else{data='';}return data;}),
									DTColumnBuilder.newColumn('po_print_date').withTitle('PRINT DATE').withOption('width','120px').renderWith(
											function(data, type, full, meta) {
												if(data!=undefined && data!='' ){data =geteditDateFormat(data);	
												}else{data='';}return data;}),
												DTColumnBuilder.newColumn('po_close_date').withTitle('CLOSE DATE').withOption('width','240px').renderWith(
														function(data, type, full, meta) {
															if(data!=undefined && data!='' ){data =geteditDateFormat(data);	
															}else{data='';}return data;}),
															DTColumnBuilder.newColumn('po_status').withTitle('STATUS').withOption('width','100px').renderWith(
																	function(data, type, full, meta) {
																		if(data==0){data='<a style="color:blue">'+RECORD_STATUS.NEW+'</a>';	}
																		else if(data==1){data='<a style="color:violet">'+RECORD_STATUS.PRINTED+'</a>';}           				
																		else if(data==2){data='<a style="color:brown">'+RECORD_STATUS.CLOSED+'</a>';}
																		else if(data==3){data='<a style="color:blue">'+RECORD_STATUS.NEW+'</a>';}
																		return data;}),

																		DTColumnBuilder.newColumn('total_amount').withTitle('AMOUNT('+settings['currencySymbol']+')').withClass("dt-body-right").withOption('width','120px').renderWith(
																				function(data, type, full, meta) {
																					data=parseFloat(data).toFixed(settings['decimalPlace']);
																					return data	}),
																					DTColumnBuilder.newColumn('supplier_id').withTitle('PO NO').notVisible().withOption('searchable', false),
																					DTColumnBuilder.newColumn('supplier_code').withTitle('PO NO').notVisible().withOption('searchable', false),
																					DTColumnBuilder.newColumn('po_type').withTitle('PO NO').notVisible().withOption('searchable', false),
																					DTColumnBuilder.newColumn('shipping_address').withTitle('PO NO').notVisible().withOption('searchable', false),
																					DTColumnBuilder.newColumn('billing_address').withTitle('PO NO').notVisible().withOption('searchable', false),
																					DTColumnBuilder.newColumn('remarks').withTitle('PO NO').notVisible().withOption('searchable', false),
																					DTColumnBuilder.newColumn('terms').withTitle('PO NO').notVisible().withOption('searchable', false),
																					DTColumnBuilder.newColumn('validity_days').withTitle('PO NO').notVisible().withOption('searchable', false),];

	$scope.filterRowData = [];
	function fnFooterCallback(nRow, aaData, iStart, iEnd, aiDisplay){
		$scope.filterRowData = [];
		$scope.filterRowData.push(aiDisplay);
	}



//	Rowcallback fun for Get Edit Data when clk on Code
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
						if(test[0][i] == vm.dtInstance.DataTable.row(".selected").index()){
							current_row_index = i;
						}
					}$scope.edit(rowData,current_row_index,e);
					$('#show_form').val(1);
				} });});
		return nRow;}

//	info of table
	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){return pageInfo.page +" / "+pageInfo.pages;}
		else{return pageInfo.page+1 +" / "+pageInfo.pages;}}



//	Remote Request daatable
	vm.dtInstance1 = {};
	vm.selected = {};
	vm.selectedAll = false;
	vm.dtOptions1 = DTOptionsBuilder
	.fromSource('remote_pr_request')
	.withOption('info', false)
	.withOption('paging', false)
	.withOption('lengthChange', false)
	.withDataProp('data')
	.withOption('rowCallback', false)
	.withOption('infoCallback', infoCallback)
	.withPaginationType('full_numbers')
	.withOption('order',[[1, "desc" ]])
	.withOption('lengthMenu',
			[ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] ])
			.withOption(
					'language',
					{"zeroRecords" : "No data available",
						"emptyTable":     "No data available",
						"sInfo" : "_END_ / _TOTAL_",
						"sInfoEmpty" : "0/0",
						"lengthMenu" : "Show _MENU_ ",
						"search" : '<div class="left-inner-addon "><i class="fa fa-search"></i>_INPUT_<div>',
						"searchPlaceholder" : "Search...",
						"paginate" : {
							"previous" : '<i class="fa fa-angle-left"></i><i class="fa fa-angle-left"></i>',
							"next" : '<i class="fa fa-angle-right"></i><i class="fa fa-angle-right"></i>',
						},
						"infoFiltered" : "Filtered from _MAX_",
					}).withPaginationType('simple').withDisplayLength(5)
					.withDOM('<"pull-left"l><"pull-left"f><"pull-left"p>it') .withOption('createdRow', function(row, data, dataIndex) {
						$compile(angular.element(row).contents())($scope);
					}) .withOption('headerCallback', function(header) {
						if (!vm.headerCompiled) {
							vm.headerCompiled = true;
							$compile(angular.element(header).contents())($scope);}});
	vm.dtColumns1 = [  
		DTColumnBuilder.newColumn(null).withTitle('Add').notSortable().withOption('width','50px').renderWith(function (data, type, full, meta) {
			vm.selected[full.id] = false;
			return '<div class="checkbox1"><label class="checkbox-inline"><input type="checkbox" ng-model="item.selected[' + full.id + ']" ng-click="toggleOne(item.selected)"><span class="cr"><i class="cr-icon fa fa-check"></i></span>	</label> </div>'; }),
			DTColumnBuilder.newColumn('id').notVisible(),
			DTColumnBuilder.newColumn('request_company_name').notVisible(),
			DTColumnBuilder.newColumn('request_number').withTitle('Req No').withOption('width','100px'),
			DTColumnBuilder.newColumn('stock_item_name').withTitle('Item Name'),
			DTColumnBuilder.newColumn('qty').withTitle('Qty').withOption('width','50px'),
			];				
	$scope.toggleOne=function(selectedItems) {
		for (var id in selectedItems) {
			if (selectedItems.hasOwnProperty(id)) {
				if (!selectedItems[id]) {
					vm.selectAll = false;
					$scope.update_table();
					return;}}
			$scope.update_table();}
		vm.selectAll = true;
		$scope.update_table();};




		$scope.update_table=function(){
			$scope.importdata = [];
			var row_data = vm.dtInstance1.DataTable.rows().data();
			for (var id in  vm.selected ) {
				if( vm.selected[id]==true){
					for(var i=0;i<row_data.length;i++){
						if(row_data[i].id==id){
							$scope.importdata.push(row_data[i]);}}}}
			$scope.remoteorderdtldata = {items: []};
			for(var i=0;i<$scope.importdata.length;i++){
				$scope.remoteorderdtldata.items.push({	
					id:$scope.importdata[i].id,
					request_company_name:$scope.importdata[i].request_company_name,
					stock_item_name:$scope.importdata[i].stock_item_name,
					qty: ($scope.importdata[i].qty),});}
			if($scope.remoteorderdtldata.items.length>0){
				$('#createpo').prop("disabled",false);
			}else{$('#createpo').prop("disabled",true);}}

		$scope.search=function()
		{vm.dtInstance1.DataTable.search($('#companyId').val()).draw();};


		$scope.removeItem1 = function(index) {
			vm.selected[$scope.remoteorderdtldata.items[index].id]=false;
			$scope.remoteorderdtldata.items.splice(index, 1);
			$scope.update_table();};
			$scope.remoterequsethdrdtllist=[];
			$http({
				url : '../remoterequestdtl/rpr_dtllist1',
				method : "GET",
			}).then(function(response) {

				var data = response.data;
				var data_len = data.length;	
				for(var l=0; l < data_len;l++){
					$scope.remoterequsethdrdtllist.push(data[l]);}});

			$rootScope.$on('import_data',function(event){	
				$('#beforebtns').hide();
				$scope.show_form=true;$scope.show_form1=false;$scope.show_form2=false;
				clearform();
				manageButtons("save");
				$("#btnBack").show();
				$scope.importdata = [];
				var row_data = vm.dtInstance1.DataTable.rows().data();
				for (var id in  vm.selected ) {
					if( vm.selected[id]==true){
						for(var i=0;i<row_data.length;i++){
							if(row_data[i].id==id){
								$scope.importdata.push(row_data[i]);}}}}
				$scope.purchaseorderdtldata = {items: []};
				var date =get_date_format();
				for(var i=0;i<$scope.importdata.length;i++){
					var flag=0;
					$scope.remote_request_status=1;
					for(var j=0;j<$scope.remoterequsethdrdtllist.length;j++){
						if($scope.remoterequsethdrdtllist[j].remote_request_hdr_id==$scope.importdata[i].remote_request_hdr_id && $scope.remoterequsethdrdtllist[j].request_status==1 && $scope.remoterequsethdrdtllist[j].id!=$scope.importdata[i].id){
							flag=2;
						}else if($scope.remoterequsethdrdtllist[j].remote_request_hdr_id==$scope.importdata[i].remote_request_hdr_id && $scope.remoterequsethdrdtllist[j].request_status==0 && $scope.remoterequsethdrdtllist[j].id!=$scope.importdata[i].id)
						{	flag=1;break;}}
					if(flag==2){
						$scope.remote_request_status=2;
					}else if(flag==0 ){
						$scope.remote_request_status=2;}
					$scope.purchaseorderdtldata.items.push({
						id:null,
						po_hdr_id:1,
						remote_request_status:$scope.remote_request_status,
						request_dtl_id:$scope.importdata[i].id,
						remote_request_hdr_id:$scope.importdata[i].remote_request_hdr_id,
						company_id:$scope.importdata[i].request_company_id,
						company_code:$scope.importdata[i].request_company_code,
						company_name:$scope.importdata[i].request_company_name,
						stock_item_id:$scope.importdata[i].stock_item_id,
						stock_item_code:$scope.importdata[i].stock_item_code,
						stock_item_name:$scope.importdata[i].stock_item_name,
						is_tax_inclusive:0,
						po_dtl_status:0,
						qty: ($scope.importdata[i].qty),
						rec_qty:0.0,
						unit_price:0.0,
						received_date:$scope.importdata[i].request_date ,
						expected_date:date.mindate ,
						flag:0});}
				for(var g=0;g<$scope.purchaseorderdtldata.items.length;g++){
					for(var l=g+1;l<$scope.purchaseorderdtldata.items.length;l++){
						if($scope.purchaseorderdtldata.items[g].company_id==$scope.purchaseorderdtldata.items[l].company_id && $scope.purchaseorderdtldata.items[g].stock_item_id==$scope.purchaseorderdtldata.items[l].stock_item_id){
							$scope.purchaseorderdtldata.items[g].qty=((parseInt($scope.purchaseorderdtldata.items[l].qty))+(parseInt($scope.purchaseorderdtldata.items[g].qty)));
							$scope.purchaseorderdtldata.items.splice(l, 1);l=0;g=0;}}}		
				$scope.formData.po_status=0;
				$scope.remoteorderdtldata = {items: []};
				vm.selected = {};
				vm.dtInstance1.reloadData();


			});
			/*	function import_data(){	
				};
			 */

			$rootScope.$on('reloadDatatable',function(event){					//reload Datatable
				$scope.reloadData();
			});

			$rootScope.$on('hide_table',function(event){
				$scope.showTable(event);});

			$rootScope.$on('hide_form',function(event){
				$('#show_form').val(0);
				$('#beforebtns').hide();
				$("#advsearchbox").show();

				$scope.show_table=true;
				$scope.show_form=false;
				$scope.show_form1=false;
				$scope.show_form2=false;
				$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
				if($('#approved').is(':checked')==true || $('#open').is(':checked')==true || $('#request').is(':checked')==true  )
				{$('#isall').prop('checked', false);}
				else{$('#isall').prop('checked', true);}
				//$scope.reloadData();
				//vm.dtInstance1.reloadData(null, true);
				$scope.purchaseorderdtldata = {items: []};
				$scope.remoteorderdtldata = {items: []};});


			$scope.showTable=function(event){
				$("#advsearchbox").show();

				$scope.show_table=true;
				$('#beforebtns').hide();
			}

			$scope.reloadData=function() {
				vm.dtInstance.reloadData(null, true);
			}

			 // for showing item modal
			  $("#itemLists").click(function(){
				  
				  $("#itemListsModal").modal('show');
			  });



////			edit data
			$scope.edit=function(row_data,cur_row_index,event) {		
				$scope.disable_btn=true;
				if(companytype==0){$scope.centralized=true;}
				else{$scope.centralized=false;}
				$rootScope.$emit("enable_prev_btn");
				$rootScope.$emit("enable_next_btn");//Edit function
				/*var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
				 */
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
				$scope.showTable();
				clearform();
				$rootScope.$emit("show_edit_btn_div",cur_row_index);

				var reqdate =row_data.request_date;
				if(reqdate!=undefined && reqdate!='' ){

					reqdate =geteditDateFormat(row_data.request_date);
				}
				var podate=row_data.po_date;
				if(podate!=undefined && podate!='' ){

					podate =geteditDateFormat(row_data.po_date);	
				}


				var poclosedate=row_data.po_close_date;
				if(poclosedate!=undefined && poclosedate!='' ){

					poclosedate =geteditDateFormat(row_data.po_close_date);	
				}

				var poprintdate=row_data.po_print_date;
				if(poprintdate!=undefined && poprintdate!='' ){

					poprintdate =geteditDateFormat(row_data.po_print_date);	
				}

				$scope.formData = {id:row_data.id,po_close_date:poclosedate,po_print_date:poprintdate,request_by:row_data.request_by,total_amount:row_data.totalamount,po_print_date:row_data.po_print_date,po_status:row_data.po_status,po_number:row_data.po_number,supplier_id:row_data.supplier_id,supplier_code:row_data.supplier_code,supplier_name:row_data.supplier_name,reqdate:reqdate,podate1: podate ,shipping_address:row_data.shipping_address,billing_address:row_data.billing_address,remarks:row_data.remarks,terms:row_data.terms};

				$('#po_status').val(row_data.po_status);
				$(".acontainer input").val(row_data.supplier_code);
				$scope.printlink=false;
				$scope.bttnstatus=false;
				$scope.closebttn=false;
				var DataObj = {};
				DataObj.poStatus  = false;
				DataObj.divId  = "#po_status";

				if(row_data.po_status==0){	DataObj.statusClass = "form_list_con pending_bg";
				DataObj.po_status = RECORD_STATUS.NEW;$scope.bttnstatus=false;
				$scope.disable_btn=false;
				$scope.printlink=true;
				$scope.printstatus='Print';
				$('#btnEdit').show();
				$('#btnDelete').show();}
				else if(row_data.po_status==1){	DataObj.statusClass = "form_list_con print_bg";
				DataObj.po_status = RECORD_STATUS.PRINTED;$scope.bttnstatus=false;				$scope.disable_btn=false;

				$scope.printlink=true;
				$scope.printstatus='Re-Print';
				$scope.closebttn=true;
				$scope.status="Close PO";$('#btnEdit').hide();
				$('#btnDelete').hide();}
				else if(row_data.po_status==2){	DataObj.statusClass = "form_list_con closed_bg";
				DataObj.po_status = RECORD_STATUS.CLOSED;$('#btnEdit').hide();
				$('#btnDelete').hide();}
				$scope.setStatusLabel(event,DataObj);




				$scope.purchaseorderdtldata = {items: []};



				$scope.po_hdr_id = {id:row_data.id};
				$scope.prograssing = true;

				$http({
					url :'../purchaseorderdtl/po_dtllist',
					method : "GET",
					params :  $scope.po_hdr_id,
				}).then(function(response) {

					var data = response.data.poDtl;
					var data_len = data.length;	
					for(var l=0; l < data_len;l++){

						data[l].qty=(parseFloat(data[l].qty)).toFixed(settings['decimalPlace']);
						data[l].unit_price=(parseFloat(data[l].unit_price)).toFixed(settings['decimalPlace']);

						data[l].remote_request_hdr_id=null;
						data[l].remote_request_status=null;
						if(data[l].request_dtl_id==undefined){data[l].request_dtl_id=null;}
						if(data[l].expected_date!=undefined ||data[l].expected_date!='' ){
							data[l].expected_date =geteditDateFormat(data[l].expected_date);}
						data[l].is_tax_inclusive=(data[l].is_tax_inclusive == 1) ? true:false;
						data[l].flag=0;					
						$scope.purchaseorderdtldata.items.push(data[l]);
					}
					$scope.prograssing = false;
				});
				$scope.disable_all = true;
				$(".acontainer input").attr('disabled', true);
				$("#table_validation_alert").removeClass("in");

				$scope.disable_code = true;
				$scope.disable_address=true;
				$scope.show_form=true;$scope.show_form1=false;$scope.show_form2=false;$scope.show_table=false;
				$("#advsearchbox").hide();

				$scope.disable_btn=false;}

//			Delete Function
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
						if(response.data == 1){
							$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
							$('#show_form').val(0);
							$scope.reloadData();
							$scope.show_table=true;
							$("#advsearchbox").show();

							$scope.show_form=false;
							manageButtons("add");
							$scope.disable_all = true;
							$(".acontainer input").attr('disabled', true);
							$scope.disable_code = true;}
						else{
							$mdDialog.show($mdDialog.alert()
									.parent(angular.element(document.querySelector('#dialogContainer')))
									.clickOutsideToClose(true)
									.textContent("Delete failed.")
									.ok('Ok!')
									.targetEvent(event)
							);
						}}, function(response) { // optional
							$mdDialog.show($mdDialog.alert()
									.parent(angular.element(document.querySelector('#dialogContainer')))
									.clickOutsideToClose(true)
									.textContent("Delete failed.")
									.ok('Ok!')
									.targetEvent(event)
							);
						});

				});
			});



			$scope.amount = function(amount) {    
				amount=parseFloat(amount).toFixed(settings['decimalPlace']);
				return amount;
			}

			$scope.tableDatevalidation=function(date,index){

				if(date<$scope.formData.podate1){
					$rootScope.$broadcast('on_AlertMessage_ERR',"Enter valid expected date");

				}}

			$scope.view_mode_aftr_edit=function(){

				var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
				$rootScope.$emit("show_edit_btn_div",cur_row_index);
				$scope.disable_all = true;
				$(".acontainer input").attr('disabled', true);
				$scope.disable_code = true;

			}
//			save
			$scope.saveData=function(event){
				if ($scope.code_existing_validation($scope.formData)) {	

					var date =get_date_format();


					$scope.totalamount=0.00;
					for(var i = 0;i<$scope.purchaseorderdtldata.items.length;i++){
						$scope.purchaseorderdtldata.items[i].amount = $scope.purchaseorderdtldata.items[i].qty * $scope.purchaseorderdtldata.items[i].unit_price;
						$scope.totalamount+=$scope.purchaseorderdtldata.items[i].amount;
						if($scope.purchaseorderdtldata.items[i].expected_date == undefined || $scope.purchaseorderdtldata.items[i].expected_date == "")
						{
							$scope.purchaseorderdtldata.items[i].received_date =date.mindate;
							$scope.purchaseorderdtldata.items[i].expected_date =date.mindate;
						}
					}

					/*	 $scope.fun_get_pono();*/
					$scope.formData.po_number = $("#PO_No").val();


					var date =get_date_format();

					var reqdate=getMysqlFormat(date.mindate);

					var received_date=getMysqlFormat($("#received_date").val());

					$scope.formData.request_date=reqdate;

					if($scope.formData.request_by	==null)		{

						$scope.formData.request_by=strings['name'];
					}


					if($scope.formData.po_status== null || $scope.formData.po_status== ''){
						$scope.formData.po_status=0;
						var date =get_date_format();

						$scope.formData.po_date=date.mindate;

					}

					/*	$scope.formData.remote_request_status= [];*/

					$scope.formData.stockdtlstsidno="0";
					/*	$scope.formData.stockidno = [];
					$scope.formData.request_dtl_id=[];*/
					$scope.formData.po_date=received_date;
					$scope.formData.total_amount=$scope.totalamount;
					$scope.formData.store_id=1;
					/*$scope.formData.stock_item_id = [];
					$scope.formData.stock_item_code = [];
					$scope.formData.stock_item_name = [];
					$scope.formData.rec_qty = [];
					$scope.formData.qty = [];
					$scope.formData.unit_price = [];
					$scope.formData.amount = [];
					$scope.formData.expected_date = [];
					$scope.formData.company_id=[];
					$scope.formData.company_code=[];
					$scope.formData.company_name=[];
					$scope.formData.remote_request_hdr_id=[];
					$scope.formData.deleteremoterequestdtlid=[];
					$scope.formData.is_tax_inclusive=[];
					$scope.formData.po_dtl_status=[];*/
					if($scope.remoterequest_dtl_id.length!=0){
						$scope.formData.deleteremoterequestdtlid=JSON.stringify($scope.remoterequest_dtl_id);

					}else{$scope.formData.deleteremoterequestdtlid=null;
					}


					/*for(var i = 0;i<$scope.purchaseorderdtldata.items.length;i++){
						$.each($scope.purchaseorderdtldata.items[i],function(key,value){

							if(key == "id"){
								if(value!=null){
									$scope.formData.stockidno.push(value);

								}
								else{
									value=0;
									$scope.formData.stockidno.push(value);

								}

							}else if(key == "remote_request_status"){
								if(value!=null){
									$scope.formData.remote_request_status.push(value);

								}
								else{
									value=0;
									$scope.formData.remote_request_status.push(value);

								}

							}else if(key == "remote_request_hdr_id"){
								if(value!=null){
									$scope.formData.remote_request_hdr_id.push(value);

								}
								else{
									value=0;
									$scope.formData.remote_request_hdr_id.push(value);

								}

							}

							else if(key == "request_dtl_id"){
								if(value!=null){
									$scope.formData.request_dtl_id.push(value);

								}
								else{
									value=0;
									$scope.formData.request_dtl_id.push(value);
								}
							}else if(key == "company_id"){
								if(value=="" ){
									value=	settings['currentcompanyid1'] ;

								}
								$scope.formData.company_id.push(value);
							}else if(key == "company_code"){				$("#advsearchbox").hide();

								if(value==""){
									value=	settings['currentcompanycode1'];
								}
								$scope.formData.company_code.push(value);
							}else if(key == "company_name"){
								if(value==""){
									value=	settings['currentcompanyname1']
								}
								$scope.formData.company_name.push(value);
							}else if(key == "stock_item_id"){
								$scope.formData.stock_item_id.push(value);
							}else if(key == "stock_item_code"){
								$scope.formData.stock_item_code.push(value);
							}else if(key == "stock_item_name"){
								$scope.formData.stock_item_name.push(value);
							}else if(key == "qty"){
								if(value==null || value=="" ){
									value=0.00;
								}
								$scope.formData.qty.push(value);
							}else if(key == "rec_qty"){
								if(value==null || value=="" ){
									value=0.00;
								}
								$scope.formData.rec_qty.push(value);
							}else if(key == "unit_price"){
								if(value==null || value=="" ){
									value=0.00;
								}
								$scope.formData.unit_price.push(value);

							}else if(key == "amount"){
								if(value==null || value=="" ){
									value=0.00;
								}
								$scope.formData.amount.push(value);
							}else if(key == "is_tax_inclusive"){

								if(value==false || value=="" || value==undefined  ){
									value=0;
								}else if(value==true ){
									value=1;
								}
								$scope.formData.is_tax_inclusive.push(value);
							}

							else if(key == "expected_date"){


								var reqdate=getMysqlFormat(value);

								$scope.formData.expected_date.push(reqdate);
							}else if(key == "po_dtl_status"){
								$scope.formData.po_dtl_status.push(0);		 
							}
						});
					}		*/

					$scope.formData.status=	$scope.formData.po_status;
					$scope.formData.change_date	=reqdate;	
					var reqdate1=getMysqlFormat($scope.formData.change_date);
					$scope.formData.change_date	=reqdate1;
					if($('#chkstatuschange').val()==null || $('#chkstatuschange').val()==''){
						$scope.formData.chkstatuschange=1;
					}else{
						$scope.formData.chkstatuschange=2;
					}
					$scope.formData.change_reason=$scope.change_reason;	


					$scope.poDtlList = [];
					for(var i = 0;i<$scope.purchaseorderdtldata.items.length;i++){

						if($scope.purchaseorderdtldata.items[i].company_id=="" ){
							$scope.purchaseorderdtldata.items[i].company_id=settings['currentcompanyid1'] ;}

						if($scope.purchaseorderdtldata.items[i].company_code==""){
							$scope.purchaseorderdtldata.items[i].company_code=	settings['currentcompanycode1'];
						}

						if($scope.purchaseorderdtldata.items[i].company_name==""){
							$scope.purchaseorderdtldata.items[i].company_name=	settings['currentcompanyname1'];
						}	
						$scope.purchaseorderdtldata.items[i].po_dtl_status=0;
						$scope.purchaseorderdtldata.items[i].expectedDate=getMysqlFormat($scope.purchaseorderdtldata.items[i].expected_date);
						$scope.poDtlList.push($scope.purchaseorderdtldata.items[i]);}

					$scope.formData.poDetailLists=JSON.stringify($scope.poDtlList);



					$http({
						url : 'saveStockItem',
						method : "POST",
						data :  $scope.formData,
					}).then(function(response) {
						if(response.data == 1){
							if($scope.formData.id !=undefined){
								$scope.disable_btn=false;

								$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);



								$scope.disable_address=true;

								if( $scope.formData.po_status==1){


									$scope.bttnstatus=false;
									$scope.printlink=true;
									$scope.printstatus='Re-Print';
									$scope.closebttn=true;
									$scope.status="Close PO";
								}else if( $scope.formData.po_status==0){
									$scope.bttnstatus=false;
									$scope.printlink=true;
									$scope.printstatus='Print';



								}


								$scope.view_mode_aftr_edit();

							}else{
								$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);

								$scope.fun_get_pono();
								var date =get_date_format();
								/*							$scope.formData = {total_amount:0,store_id:0,po_print_date:'',po_status:0,supplier_id:0,supplier_code:'',supplier_name:'',reqdate:date.mindate,podate1:date.mindate,shipping_address:'',billing_address:'',remarks:'',terms:''};
								 */



								$scope.formData = {total_amount:0,store_id:0,po_print_date:'',po_status:0,supplier_id:parseInt(strings['isDefSupplierid']),supplier_code:strings['isDefSuppliercode'],supplier_name:strings['isDefSuppliername'],reqdate:date.mindate,podate1:date.mindate,shipping_address:'',billing_address:'',remarks:'',terms:''};

								$("#form_div_supplier_code .acontainer").find("input").val(strings['isDefSuppliercode']);

								$scope.purchaseorderdtldata = {
										items: []
								};


								$scope.purchaseorderdtldata = {
										items: [{
											id:null,
											remote_request_hdr_id:null,
											request_dtl_id:null,
											remote_request_status:null,
											company_id:settings['currentcompanyid1'],
											company_code:settings['currentcompanycode1'],
											company_name:settings['currentcompanyname1'] ,
											qty: 0,
											rec_qty:0,
											is_tax_inclusive:0,
											po_dtl_status:0,
											unit_price:0,
											amount:0,
											received_date: date.mindate,
											expected_date: date.mindate}]
								};

								$("#table_validation_alert").removeClass("in");


							}$scope.reloadData();
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
			}



//			Save Function
			$rootScope.$on('fun_save_data',function(event){	


				$scope.saveData(event);


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

					/*	if($scope.formData.id==undefined){


						$scope.show_table=true;
						$scope.show_form=false;
						manageButtons("add");

					}else{*/
					var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
					if($scope.formData.id == undefined){
						$scope.fun_get_pono();

						var date =get_date_format();

						$scope.formData = {total_amount:0,store_id:0,po_print_date:'',po_status:0,supplier_id:parseInt(strings['isDefSupplierid']),supplier_code:strings['isDefSuppliercode'],supplier_name:strings['isDefSuppliername'],reqdate:date.mindate,podate1:date.mindate,shipping_address:'',billing_address:'',remarks:'',terms:''};
						$("#form_div_supplier_code .acontainer").find("input").val(strings['isDefSuppliercode']);

						$scope.disable_address=true;
						$scope.disable_btn=true;
						$scope.purchaseorderdtldata = {
								items: []
						};
						$scope.purchaseorderdtldata = {
								items: [{

									id:null,
									remote_request_status:null,

									remote_request_hdr_id:null,
									request_dtl_id:null,
									company_id:settings['currentcompanyid1'],
									company_code:settings['currentcompanycode1'],
									company_name:settings['currentcompanyname1'] ,
									qty: 0,
									rec_qty:0,
									unit_price:0,
									is_tax_inclusive:0,
									po_dtl_status:0,
									amount:0,
									received_date: date.mindate,
									expected_date: date.mindate}]
						};

						$("#table_validation_alert").removeClass("in");

						$scope.hide_code_existing_er = true;

					}else{
						var dataIndex = vm.dtInstance.DataTable.rows();
						var row_data = vm.dtInstance.DataTable.rows(dataIndex[0][cur_row_index]).data();
						$scope.edit(row_data[0],cur_row_index);

					}/*}*/
					clearform();
				});

			});




			$timeout(function () {$("#DataTables_Table_0_filter").hide(); }, 1); 		

			$rootScope.$on("advSearch",function(event){
				$("#dropdownnew").toggle();
				DataObj.adnlFilters=[{}];
				$('#SearchText').text("");
				vm.dtInstance.DataTable.search('').draw();	
				$scope.orderstatus=$('#ordertype').val();
				if($scope.orderstatus != "")
				{
					var orderstat=$('#ordertype').find(":selected").text();
				}
				$scope.date=$('#orderDate').val();
				if($scope.date !=null && $scope.date !=undefined && $scope.date !="")
				{
					$scope.date=getMysqlFormat($scope.date);
				}
				$scope.reqNo=$('#reqNo').val();
				$scope.suppliername=$('#suppliername').val();
				/*$("#SearchCriteria").append(" <span style=' padding-right:15px;border-radius: 10px; background-color:deepskyblue; color:white;'>Date:"+$scope.date+"  <a onclick='remove($this)'>X</a></span>");*/
				/*				$("#SearchCriteria").append("<span style='padding-right:15px; background-color:deepskyblue;  color:white;'>Req No:"+$scope.reqNo+"  X</span>");
				$("#SearchCriteria").append("<span style='padding-right:15px; background-color:deepskyblue; color:white;'>Status:"+$scope.orderstatus+"  X</span>");
				 */			/*	DataObj.adnlFilters=[{col:6,filters:$scope.orderstatus}];	*/
				$scope.searchTxtItms={1:orderstat,2:$scope.date,3:$scope.reqNo,4:$scope.suppliername};
				for (var key in $scope.searchTxtItms) {

					if ($scope.searchTxtItms.hasOwnProperty(key)) {
						// console.log(key + " -> " + $scope.searchTxtItms[key]);
						if($scope.searchTxtItms[key] !=null && $scope.searchTxtItms[key] !=undefined && $scope.searchTxtItms[key] !="")
						{

							angular.element(document.getElementById('SearchText')).append($compile("<div id="+key+"  class='advseacrh '  contenteditable='false'>"+$scope.searchTxtItms[key]+"<span class='close-thik' contenteditable='false'  ng-click='deleteOptn("+key+"); '></span></div>")($scope))
							$scope.deleteOptn = function (key) {
								// alert(key);
								delete $scope.searchTxtItms[key];	
								$('#'+key).remove();
								switch(key)
								{
								case 1:
									$scope.orderstatus="";
									$('#ordertype').val("");
									break;
								case 2:
									$scope.date="";
									$('#orderDate').val();
									break;
								case 3:
									$scope.reqNo="";
									$('#reqNo').val("");
									break;
								case 4:
									$scope.suppliername="";
									$('#suppliername').val("");
									break;
								}
								DataObj.adnlFilters=[{col:1,filters:$scope.reqNo},{col:2,filters:$scope.suppliername},{col:3,filters:$scope.date},{col:6,filters:$scope.orderstatus}];
								vm.dtInstance.reloadData(); 
							};


							//
							//	Object.keys(object).find(key => object[key] === value)

						}
					}
				}


				DataObj.adnlFilters=[{col:1,filters:$scope.reqNo},{col:2,filters:$scope.suppliername},{col:3,filters:$scope.date},{col:6,filters:$scope.orderstatus}];
				vm.dtInstance.reloadData();
				$scope.searchTxtItms={};


			});

			/*	function removeFilter(dt){
				alert("df");
				$("#date").remove();

			}*/

			/*
			$rootScope.$on("removeFilter",function(id){
				alert(id);
				$("#date").remove();
			});*/

			$rootScope.$on("Search",function(event){
				DataObj.adnlFilters=[{}];
				/*				vm.dtInstance.reloadData();
				 */				vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	
				 $scope.searchTxtItms={};

			});


			$("#clear").click(function(){
				DataObj.adnlFilters=[{}];
				$('#SearchText').text("");
				vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	

				$scope.searchTxtItms={};
			});

			$("#closebtnew").click(function(){
				$("#dropdownnew").toggle();

			});




			$("#clearFeilds").click(function(){
				//$(".dropdown-toggle").dropdown("toggle");
				$("#dropdownnew").toggle();
				$('#reqNo').val("");
				$('#suppliername').val("");
				$('#ordertype').val("");
				$('#orderDate').val("");


			});





			$scope.showForm=function(){
				$("#advsearchbox").hide();

				$scope.show_table=false;
				$('#beforebtns').hide();

				if(companytype==0){
					if($scope.show_form1==false)
					{manageButtons("next");$scope.show_form=false;$scope.show_form1=true;$scope.show_form2=false;$scope.slctypreq=1}
					else if($scope.show_form1==true && $scope.slctypreq==1){
						manageButtons("save");
						$scope.show_form=true;$scope.show_form1=false;$scope.show_form2=false;}
					else if($scope.show_form1==true && $scope.slctypreq==2){
						$('#isall').prop('checked', true);manageButtons("hideall");
						$('#beforebtns').show(); $('#createpo').prop("disabled",true);
						vm.dtInstance1.DataTable.search($('#companyId').val()).draw();
						$scope.show_form=false;$scope.show_form1=false;$scope.show_form2=true;}
				}else{$scope.show_form=true;$scope.show_form1=false;$scope.show_form2=false;}}	


			$rootScope.$on("fun_enable_inputs",function(){

				$scope.disable_btn=true;
				$scope.disable_code = false;
				$scope.printlink=false;
				$scope.bttnstatus=false;
				$scope.closebttn=false;
				$scope.disable_all = false;
				$(".acontainer input").attr('disabled', false);
				$('#show_form').val(1);
				/*$scope.formData.supplier_id = $scope.supplier_data[0].id;
				$scope.formData.supplier_code =$scope.supplier_data[0].code;
				$scope.formData.supplier_name = $scope.supplier_data[0].name;
				$("#form_div_supplier_code .acontainer").find("input").val($scope.supplier_data[0].code);*/
			});


			$rootScope.$on("fun_enable_inputs_code",function(){

				$scope.disable_code = false;
				$scope.printlink=false;
				$scope.bttnstatus=false;
				$scope.closebttn=false;

			});
			$rootScope.$on("fun_enable_inputs_address",function(){
				$scope.printlink=false;
				$scope.closebttn=false;
				$scope.disable_address = true;
				$scope.disable_btn=true;


			});

			$rootScope.$on("fun_clear_form",function(){

				$scope.disable_btn=true;
				$scope.poStatus=true;
				$scope.showForm();
				$scope.fun_get_pono();
				var date =get_date_format();
				$scope.changedate=$("#expected_date").val();	
				$scope.formData = {total_amount:0,store_id:0,po_print_date:'',po_status:0,supplier_id:parseInt(strings['isDefSupplierid']),supplier_code:strings['isDefSuppliercode'],supplier_name:strings['isDefSuppliername'],reqdate:date.mindate,podate1:date.mindate,shipping_address:'',billing_address:'',remarks:'',terms:''};

				$("#form_div_supplier_code .acontainer").find("input").val(strings['isDefSuppliercode']);


				$scope.disable_code = true;
				$scope.purchaseorderdtldata = {
						items: []
				};

				var date =get_date_format();

				$scope.purchaseorderdtldata = {
						items: [{
							id:null,
							remote_request_hdr_id:null,
							request_dtl_id:null,
							remote_request_status:null,

							company_id:settings['currentcompanyid1'],
							company_code:settings['currentcompanycode1'],
							company_name:settings['currentcompanyname1'] ,
							qty: 0,
							rec_qty:0,
							is_tax_inclusive:0,
							po_dtl_status:0,
							unit_price:0,
							amount:0,
							received_date: date.mindate,
							expected_date: date.mindate}]
				};
				$("#form_div_supplier_code .acontainer").find("input").focus();
				$scope.enablePrint=false;
			});


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
				}

			});

			// Validation 

			$scope.code_existing_validation=function(data){
				if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id==undefined && $scope.purchaseorderdtldata.items.length!=1 ){
					$scope.purchaseorderdtldata.items.splice($scope.purchaseorderdtldata.items.length-1, 1);
				}
				var flg = true;
				var date="";

				if(validation() == false){
					flg = false;
				}

				if($scope.formData.supplier_code==null || $scope.formData.supplier_code=="" || $scope.formData.supplier_code==undefined  ){

					$("#form_div_supplier_code .acontainer").find("input").focus();

					flg = false;
				}
				else if($scope.purchaseorderdtldata.items.length > 0){
					var count=0;
					for(var i = 0;i<$scope.purchaseorderdtldata.items.length;i++){

						if($scope.purchaseorderdtldata.items[i].stock_item_id != undefined && $scope.purchaseorderdtldata.items[i].stock_item_id != "" ){
							count=0;
							if($scope.purchaseorderdtldata.items[i].qty == "" || $scope.purchaseorderdtldata.items[i].qty <= 0){
								$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);

								$("#items_table tr:nth-child("+(i+2)+") td:nth-child("+(4)+")").find("#itemqty").select();
								flg = false;break;}

							else if($scope.purchaseorderdtldata.items[i].expected_date==null || $scope.purchaseorderdtldata.items[i].expected_date=="" || $scope.purchaseorderdtldata.items[i].expected_date== undefined )
							{date=null;
							flg=false;
							break;
							}else if($scope.purchaseorderdtldata.items[i].expected_date<$scope.formData.podate1){
								$rootScope.$broadcast('on_AlertMessage_ERR',"Enter valid expected date");
								flg=false;break;}}else{count =1;$("#items_table tr:nth-child("+(i+2)+") td:nth-child("+(3)+")").find(".acontainer input").focus();
								break;}}}


				if($scope.purchaseorderdtldata.items.length == 0){
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.TABLE_ERR);
					flg = false;
				}else if(count==1){
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);

					$(".acontainer").find("input").focus();
					flg = false;

				}else if(date==null)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.DATE_ERR);

					flg = false;
				}
				if(flg==false)
				{
					focus();
				}


				return flg;
			}


			function setFormDatas(){

			}



//			/add items to stock item table	
			$scope.addItem = function(index) {


				var date =get_date_format();
				if($scope.disable_all==false ){
					if(index<$scope.purchaseorderdtldata.items.length-1){
						$("#items_table tr:nth-child("+(index+3)+") td:nth-child("+(3)+")").find(".acontainer input").focus();
					}else{
						if($scope.purchaseorderdtldata.items.length != 0){
							if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id != undefined && $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id != ""){
								if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].qty == "" || $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].qty <=0){
									$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
									$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length+1)+")").find("#itemqty").select();
								}else{$scope.purchaseorderdtldata.items.push({
									id:null,
									remote_request_hdr_id:null,
									request_dtl_id:null,
									remote_request_status:null,
									company_id:settings['currentcompanyid1'],
									company_code:settings['currentcompanycode1'],
									company_name:settings['currentcompanyname1'] ,
									qty: 0,
									rec_qty:0,
									is_tax_inclusive:0,
									po_dtl_status:0,
									unit_price:0,
									amount:0,
									received_date: date.mindate,
									expected_date: date.mindate});}
							}else{
								$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);

								$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length+1)+") td:nth-child("+(3)+")").find(".acontainer input").focus();
								if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id == undefined && $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id ==""){
									$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);

									$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length+1)+") td:nth-child("+(3)+")").find(".acontainer input").focus();


								}
							}

						}else{
							$scope.purchaseorderdtldata.items.push({
								id:null,
								remote_request_hdr_id:null,
								request_dtl_id:null,
								remote_request_status:null,
								po_dtl_status:null,
								company_id:settings['currentcompanyid1'],
								company_code:settings['currentcompanycode1'],
								company_name:settings['currentcompanyname1'] ,
								qty: 0,
								rec_qty:0,
								unit_price:0,
								is_tax_inclusive:0,
								amount:0,
								received_date: date.mindate,
								expected_date: date.mindate,
								flag:0});


							/*							$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length+1)+") td:nth-child("+(3)+")").find(".acontainer input").select();
							 */							

						}

					}
				} },





				$scope.remoterequest_dtl_id=[];
				$scope.removeItem = function(index,event) {
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
								if($scope.purchaseorderdtldata.items[index].request_dtl_id!=null){
									$scope.remoterequest_dtl_id.push($scope.purchaseorderdtldata.items[index].request_dtl_id);}
								$scope.purchaseorderdtldata.items.splice(index,1);
								$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length-1+2)+")").find("#itemqty").select();	
							}else{
								$rootScope.$broadcast('on_AlertMessage_ERR',"Atleast one item is required.");
							}});}};


							$scope.alert_for_codeexisting = function(flg){			
								if(flg == 1){$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.CODE_EXISIT);
								}}

							$scope.total = function() {
								var total = 0.00;
								angular.forEach($scope.purchaseorderdtldata.items, function(item) {
									total += (item.qty * item.unit_price);
								});return total=(total).toFixed(settings['decimalPlace']);}

//							onchange stock item name
							$scope.updatestockdetails=function(rowid){
								$scope.stockid=$scope.purchaseorderdtldata.items[rowid].stock_item_id;
								for(var i=0; i < $scope.stockItem_data.length;i++){
									$scope.purchaseorderdtldata.items[rowid].curr_stock=0.00;
									$scope.purchaseorderdtldata.items[rowid].min=0.00;
									$scope.purchaseorderdtldata.items[rowid].max=0.00;
									if($scope.stockItem_data[i].stock_item_id==$scope.stockid){
										$scope.purchaseorderdtldata.items[rowid].curr_stock=$scope.stockItem_data[i].current_stock;
										$scope.purchaseorderdtldata.items[rowid].min=$scope.stockItem_data[i].min_stock;
										$scope.purchaseorderdtldata.items[rowid].max=$scope.stockItem_data[i].max_stock;}}}


							//clear stock item
							$scope.clear_stock_details_editmode =  function(event){
								$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_id = "";
								$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_code = "";
								$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_name = "";
								$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].uomcode = "";
								$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].unit_price="";
								$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].qty="";
								$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].is_tax_inclusive=false;


							}
							$scope.elementid=function(elemenvalue){
								if($scope.disable_all==true){

									$(elemenvalue).attr("disabled", true);

								}
							}


							// for showing item modal
							/*$("#itemLists").click(function(){

								$("#itemListsModal").modal('show');
							});*/

}
///filter datable
/*mrpApp.run(function(RECORD_STATUS) {
	$.fn.dataTable.ext.search.push(
			function(settings, data, dataIndex) {
				var ischeckeddata=false;
				if($('#open').is(':checked')){
					if(data[6]==RECORD_STATUS.PRINTED) {
						ischeckeddata= true;}}
				if($('#request').is(':checked')){
					if(data[6]==RECORD_STATUS.CLOSED) {
						ischeckeddata= true;}}				
				if($('#approved').is(':checked')){
					if(data[6]==RECORD_STATUS.NEW) {
						ischeckeddata= true;}}
				if($('#isall').is(':checked')){

					ischeckeddata= true;}

				return ischeckeddata;});


});*/



mrpApp.directive('autocompeteText', ['$timeout',function($timeout) {
	return {controller: function ($scope,$http) {
		$scope.currentIndex = 0;

		$("#items_table tbody tr td").keyup('.acontainer input',function(e){
			$scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;

			if(e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8){


				if(e.currentTarget.cellIndex == 2){

					$scope.clear_stock_details_editmode(e);


				}					
			}else if(e.which == 13 ){
				if(e.currentTarget.cellIndex == 2){
					if($scope.purchaseorderdtldata.items[e.currentTarget.parentElement.rowIndex-1].stock_item_id!=undefined){
						{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find("#itemqty").focus();}
					}}
			}else if(e.which == 9 ){
				if(e.currentTarget.cellIndex == 2){
					/*if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id==undefined || $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id==""){*/
					{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+") td:nth-child("+(3)+")").find(".acontainer input").focus();}/*}*/
				}
			}

			/*if(e.which==13 && e.currentTarget.cellIndex == 2){$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length-1+2)+")").find("#itemqty").select();}
			 */

		});	
		$scope.element = [];
		$scope.table_itemsearch_rowindex=0;
		$scope.tableClicked = function (index) {
			$scope.table_itemsearch_rowindex= index;
		};return $scope;},
		link: function(scope, elem, attrs ,controller) {
			var strl_scope = controller;
			var items = $(elem).tautocomplete({

				columns: ['id','code','name','input_tax_id','tax_percentage','valuation_method','is_active','is_manufactured','uomcode','uomname','unit_price'],
				hide: [false,true,true,false,false,false,false,false,false,false,false,false,false],
				placeholder: "search ..",
				highlight: "hightlight-classname",
				norecord: "No Records Found",
				delay : 10,
				onchange: function () {
					var selected_item_data =  items.all();
					strl_scope.$apply(function(){
						var count =0;
						for(var i=0;i<strl_scope.purchaseorderdtldata.items.length;i++){
							if(selected_item_data.id != ""){
								if(i != strl_scope.currentIndex){
									if(selected_item_data.id == strl_scope.purchaseorderdtldata.items[i].stock_item_id){
										count=1;
									}
								}}}
						if(count != 1){
							strl_scope.purchaseorderdtldata.items[strl_scope.currentIndex].stock_item_id = selected_item_data.id;
							strl_scope.purchaseorderdtldata.items[strl_scope.currentIndex].stock_item_code = selected_item_data.code;
							strl_scope.purchaseorderdtldata.items[strl_scope.currentIndex].stock_item_name = selected_item_data.name;
							strl_scope.purchaseorderdtldata.items[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;
							strl_scope.purchaseorderdtldata.items[strl_scope.currentIndex].unit_price=selected_item_data.unit_price;

							strl_scope.alert_for_codeexisting(0);
							/*	$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length-1+2)+")").find("#itemqty").select();*/
							$timeout(function () {$("#items_table tr:nth-child("+(strl_scope.currentIndex+2)+")").find("#itemqty").focus();}, 1); 


						}else{
							elem[0].parentNode.lastChild.value="";
							strl_scope.purchaseorderdtldata.items[strl_scope.currentIndex].uomcode = "";

							strl_scope.alert_for_codeexisting(1);
						}
					});
				},
				data: function () {
					var data = strl_scope.itemsData;
					var filterData = [];
					var searchData = eval("/^" + items.searchdata() + "/gi");
					$.each(data, function (i, v) {
						if ( v.name.search(new RegExp(searchData)) != -1) {
							filterData.push(v);}});

					return filterData;
				} 		   		
			});			
			for(var i = 0;i<strl_scope.purchaseorderdtldata.items.length;i++){
				if(strl_scope.formData.id!=undefined && strl_scope.formData.id!='' ){
					if(strl_scope.purchaseorderdtldata.items[i].flag==0){
						elem[0].parentNode.lastChild.value = strl_scope.purchaseorderdtldata.items[i].stock_item_code;
						strl_scope.elementid(elem[0].parentNode.lastChild);

						strl_scope.purchaseorderdtldata.items[i].flag=1;break;
					}
				}else{
					if(strl_scope.purchaseorderdtldata.items[i].flag==0){
						if(strl_scope.purchaseorderdtldata.items[i].po_hdr_id!=undefined && strl_scope.purchaseorderdtldata.items[i].po_hdr_id!='' ){

							elem[0].parentNode.lastChild.value = strl_scope.purchaseorderdtldata.items[i].stock_item_code;
							strl_scope.elementid(elem[0].parentNode.lastChild);

							strl_scope.purchaseorderdtldata.items[i].flag=1;break;
						}}
				}
			} $timeout(function () { $("#items_table tr:nth-child("+(strl_scope.purchaseorderdtldata.items.length+1)+") td:nth-child("+(3)+")").find(".acontainer input").select();
			}, 1); 
		}
	};
}]);





