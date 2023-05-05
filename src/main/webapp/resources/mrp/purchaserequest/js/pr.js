

//Controller for Table and Form 
mrpApp.controller('poctrl', poctrl);

function poctrl($scope,$window, $http, $mdDialog ,$rootScope, DTOptionsBuilder,$timeout,$compile,
		DTColumnBuilder,$controller,MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS) {
	
	
	$scope.disable_btn=true;
/*	set_sub_menu("#store");						
	setMenuSelected("#pr_left_menu");*/			//active leftmenu
	manageButtons("add");
	$controller('DatatableController', {$scope: $scope});
	var vm = this;
	$scope.purchaseorderdtllist=[];
	$scope.purchaseorderdtldata = {items: []};
	$scope.formData = {};
	$scope.show_table=true;
	$("#advsearchbox").show();
	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
	$scope.itemsData = [];
	$scope.searchTxtItms={};
	
//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			myArray = response.split(/(\d+)/)
			var prodId = myArray[1];
			$scope.formData.pr_number = response;});}

//	item master list
	$http({
		url : 'formJsonData',
		method : "GET",
	}).then(function(response) {$scope.itemsData = response.data.itemsData;
	console.log(response.data.itemsData);
	});

//	Submit  change	
	$scope.submit=function(ev){
		var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
			var $dialog = angular.element(document.querySelector('md-dialog'));
			var $actionsSection = $dialog.find('md-dialog-actions');
			var $cancelButton = $actionsSection.children()[0];
			var $confirmButton = $actionsSection.children()[1];
			angular.element($confirmButton).removeClass('md-focused');
			angular.element($cancelButton).addClass('md-focused');
			$cancelButton.focus();
		}}).title('You cannot make any changes on submit. Are you sure you want to submit?').targetEvent(ev).cancel('No').ok('Yes');
		$mdDialog.show(confirm).then(function() {
			var date =get_date_format();
			$scope.formData.submit_date=date.mindate;
			$scope.formData.change_date=date.mindate;
			$scope.formData.status=1;
			$scope.save_data();
			var DataObj = {};
			DataObj.prStatus  = false;
			DataObj.divId  = "#pr_status";
			DataObj.statusClass = "form_list_con approved_bg";
			DataObj.pr_status = "SUBMITTED";
			$scope.setStatusLabel(ev,DataObj);
			$scope.printlink=false;
			$('#btnEdit').hide();
			$('#btnDelete').hide();});}

	
	
//	Status Label
	$scope.setStatusLabel=function(event,args){
		$(args.divId).removeClass();
		$(args.divId).addClass(args.statusClass);	
		$scope.prStatus  =  args.prStatus;
		$scope.pr_status =  args.pr_status;	}

	
	

	
	
//	list datable	
	vm.dtInstance = {};
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.order="desc";
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 				
	vm.dtColumns = [	             DTColumnBuilder.newColumn('company_id').withTitle('PO NO').notVisible().withOption('searchable', false),

	                	             DTColumnBuilder.newColumn('pr_number').withTitle('REQ NO').withOption('width','100px').renderWith(
	                	            		 function(data, type, full, meta) {
	                	            			 data='<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
	                	            				 + data + '</a>';
	                	            			 return data;
	                	            		 }),
	                	            		 DTColumnBuilder.newColumn('create_date').withTitle('CREATE DATE').withOption('width','150px').renderWith(
	                	            				 function(data, type, full, meta) {	
	                	            					 if(data!=undefined && data!='' ){data =geteditDateFormat(data);	
	                	            					 }else{data='';}		
	                	            					 return data
	                	            				 }),  
	                	            				 

	                	            						 DTColumnBuilder.newColumn('status').withTitle('STATUS').withOption('width','150px').renderWith(
	                	            								 function(data, type, full, meta) {
	                	            									 if(data==0){data='<a style="color:blue">'+RECORD_STATUS.NEW+'</a>';	}
	                	            									 else if(data==1){data='<a style="color:green">'+RECORD_STATUS.SUBMITTED+'</a>';}return data;}),
	                	            									 
	                	            									 DTColumnBuilder.newColumn('submit_date').withTitle('SUBMIT DATE').withOption('width','150px').renderWith(
	                	                	            						 function(data, type, full, meta) {	
	                	                	            							 if(data!=undefined && data!='' ){data =geteditDateFormat(data);	
	                	                	            							 }else{data='';}
	                	                	            							 return data
	                	                	            						 }),
	                	            									 
	                	            									 DTColumnBuilder.newColumn('request_by').withTitle('REQUEST BY').withOption('width','200px'),


	                	            									 DTColumnBuilder.newColumn('remarks').withTitle('PO NO').notVisible().withOption('searchable', false),
	                	            									 ];

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
						}}$scope.edit(rowData,current_row_index,e);} });});
		$('#show_form').val(1);
		return nRow;}

//	info of table
	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){return pageInfo.page +" / "+pageInfo.pages;}
		else{return pageInfo.page+1 +" / "+pageInfo.pages;}}

//	reload Datatable	
	$rootScope.$on('reloadDatatable',function(event){					
		$scope.reloadData();
	});
	$rootScope.$on('hide_table',function(event){
		$scope.showTable(event);
	});
	$rootScope.$on('hide_form',function(event){
		$('#show_form').val(0);
		$scope.prStatus=true;
		$scope.show_table=true;
		$("#advsearchbox").show();
		$scope.show_form=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
	});
	$scope.showTable=function(event){
		$("#advsearchbox").hide();
		$scope.show_table=false;
		$scope.show_form=true;
		$scope.disable_code = true;
	}
//	reload table
	$scope.reloadData=function() {
		vm.dtInstance.reloadData(null, true);
	}
//	edit data
	$scope.edit=function(row_data,cur_row_index,event) {
		$("#advsearchbox").hide();
		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function
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
				$rootScope.$emit("enable_prev_btn");}}
		$scope.showTable();	clearform();
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.formData = {id:row_data.id,pr_number:row_data.pr_number,company_id:row_data.company_id,status:row_data.status,request_by:row_data.request_by,createdate:geteditDateFormat(row_data.create_date),submit_date:row_data.submit_date,remarks:row_data.remarks};
		$(".acontainer input").val(row_data.supplier_code);
		var DataObj = {};
		DataObj.prStatus  = false;
		DataObj.divId  = "#pr_status";
		$scope.printlink=false;
		if(row_data.status==0){
			DataObj.statusClass = "form_list_con pending_bg";
			DataObj.pr_status = RECORD_STATUS.NEW;
			$scope.printlink=true;
			$('#btnDelete').show();
			$('#btnEdit').show();}
		else if(row_data.status==1){
			DataObj.statusClass   = "form_list_con approved_bg";
			DataObj.pr_status = RECORD_STATUS.SUBMITTED;
			$('#btnEdit').hide();
			$('#btnDelete').hide();}
		$scope.setStatusLabel(event,DataObj);		
		$scope.purchaseorderdtldata = {items: []};
		$scope.pr_hdr_id = {id:row_data.id};
		 $scope.prograssing = true;

		$http({
			url : '../purchaserequestdtl/pr_dtllist',
			method : "GET",
			params :  $scope.pr_hdr_id,
		}).then(function(response) {
			var data = response.data.prDtl;
			var data_len = data.length;	
			for(var l=0; l < data_len;l++){
				data[l].qty=(parseFloat(data[l].qty)).toFixed(settings['decimalPlace']);
				data[l].expected_date = geteditDateFormat(data[l].expected_date);
				data[l].flag=0;

				$scope.purchaseorderdtldata.items.push(data[l]);}
			 $scope.prograssing = false;
	
		});	
		$scope.disable_all = true;
		$(".acontainer input").attr('disabled', true);
		$scope.disable_code = true;
		$scope.disable_btn=false;
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
		'Yes');
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
				$scope.show_form=false;
				manageButtons("add");
				$scope.disable_all = true;
				$(".acontainer input").attr('disabled', true);
				$scope.disable_code = true;
				}
				else{
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

	$scope.view_mode_aftr_edit=function(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$(".acontainer input").attr('disabled', true);
		$scope.disable_code = true;

	}
//	save
	$scope.save_data=function(){

		if ($scope.code_existing_validation($scope.formData)) {	
			var date =get_date_format();
			if($scope.formData.createdate!=undefined && $scope.formData.createdate!=''){
				$scope.formData.create_date=getMysqlFormat($scope.formData.createdate);
			}if($scope.formData.submit_date!=undefined && $scope.formData.submit_date!=''){
				$scope.formData.submit_date=getMysqlFormat($scope.formData.submit_date);}
			/*$scope.formData.pr_dtl_id= [];
			$scope.formData.stock_item_id= [];
			$scope.formData.stock_item_code= [];
			$scope.formData.stock_item_name= [];
			$scope.formData.qty= [];
			$scope.formData.expected_date= [];
			$scope.formData.request_status= [];*/
			$scope.formData.request_company_code=settings['currentcompanycode1'];
			$scope.formData.request_company_name=settings['currentcompanyname1'];
			
			
			
		/*	for(var i = 0;i<$scope.purchaseorderdtldata.items.length;i++){
				$.each($scope.purchaseorderdtldata.items[i],function(key,value){
					if(key == "id"){
						if(value==null){value=0;}
						$scope.formData.pr_dtl_id.push(value);
					}			
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
						$scope.formData.request_status.push(value);
					}});}*/	
			
			
			$scope.prDtlList = [];
			for(var i = 0;i<$scope.purchaseorderdtldata.items.length;i++){
				$scope.purchaseorderdtldata.items[i].expectedDate=getMysqlFormat($scope.purchaseorderdtldata.items[i].expected_date);
				$scope.prDtlList.push($scope.purchaseorderdtldata.items[i]);}
				$scope.formData.prDetailLists=JSON.stringify($scope.prDtlList);

			$http({
				url : 'saveStockItem',
				method : "POST",
				data :  $scope.formData,
			}).then(function(response) {

				if(response.data==1){
				if($scope.formData.id !=undefined){
					$scope.disable_btn=true;
					if( $scope.formData.status==0){
						$scope.printlink=true;
						$scope.disable_btn=false;
					}
					//$scope.formData.create_date=geteditDateFormat($scope.formData.create_date);
					if( $scope.formData.status==0){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
					}
					$scope.view_mode_aftr_edit();
				}else{
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);

					$scope.fun_get_pono();
					var date =get_date_format();
					$scope.formData = {id:null,company_id:settings['currentcompanyid1'],status:0,createdate:date.mindate,submit_date:''};
					$scope.purchaseorderdtldata = {
							items: []
					};
					$scope.purchaseorderdtldata.items.push({
						id:0,
						pr_hdr_id:null,
						stock_item_id:'',
						stock_item_code:'',
						stock_item_name:'',
						qty:0,
						expected_date:date.mindate,
						request_status:0,});
					$(".acontainer input").val('');
				}$scope.reloadData();
				$scope.hide_code_existing_er = true;}else{
					
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Save failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
				}}
			, function(response) { // optional
					$mdDialog.show($mdDialog.alert()
							.parent(angular.element(document.querySelector('#dialogContainer')))
							.clickOutsideToClose(true)
							.textContent("Save failed.")
							.ok('Ok!')
							.targetEvent(event)
					);
				});}}

//	Save Function
	$rootScope.$on('fun_save_data',function(event){		
		$scope.save_data();
	});
//	Discard function
	$rootScope.$on("fun_discard_form",function(event){				

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
			if($scope.formData.id==undefined){
				$scope.fun_get_pono();
				var date =get_date_format();
				$scope.formData = {id:null,company_id:settings['currentcompanyid1'],status:0,createdate:date.mindate,submit_date:''};
				$scope.changedate=$("#expected_date").val();	
				$(".acontainer input").val('');
				$scope.disable_code = true;
				$scope.purchaseorderdtldata = {items: []};
				$scope.purchaseorderdtldata.items.push({
					id:0,
					pr_hdr_id:null,
					stock_item_id:'',
					stock_item_code:'',
					stock_item_name:'',
					qty:0,
					expected_date:date.mindate,
					request_status:0,});
			}else{
				var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);			
				var dataIndex = vm.dtInstance.DataTable.rows();
				var row_data = vm.dtInstance.DataTable.rows(dataIndex[0][cur_row_index]).data();
				$scope.edit(row_data[0],cur_row_index);
				clearform();
			}});});
	$rootScope.$on("fun_enable_inputs",function(){
		$scope.disable_btn=false;
		$scope.disable_all = false;
		$(".acontainer input").attr('disabled', false);
		$scope.printlink=false;
		$scope.disable_btn=true;
		$('#show_form').val(1);
	});
	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
		$scope.printlink=false;

	});


	$rootScope.$on("fun_clear_form",function(){
		$scope.disable_btn=true;
		$scope.fun_get_pono();
		var date =get_date_format();
		$scope.formData = {id:null,company_id:settings['currentcompanyid1'],status:0,createdate:date.mindate,submit_date:''};
		$scope.changedate=$("#expected_date").val();	
		$(".acontainer input").val('');
		$scope.disable_code = true;
		$scope.purchaseorderdtldata = {items: []};
		$scope.purchaseorderdtldata.items.push({
			id:0,
			pr_hdr_id:null,
			stock_item_id:'',
			stock_item_code:'',
			stock_item_name:'',
			qty:0,
			expected_date:date.mindate,
			request_status:0,});});

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




//	common 	validation

	$scope.code_existing_validation=function(data){
		if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id=="" && $scope.purchaseorderdtldata.items.length!=1 ){
			$scope.purchaseorderdtldata.items.splice($scope.purchaseorderdtldata.items.length-1, 1);
			}
		var flg = true;
		var date="";
		if(validation() == false){
			flg = false;}
		else if($scope.formData.createdate==null || $scope.formData.createdate=="" || $scope.formData.createdate==undefined ){
			flg = false;
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.DATE_ERR);
		}
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
					{ date=null;flg=false;break;}else if($scope.purchaseorderdtldata.items[i].expected_date<$scope.formData.createdate && $scope.purchaseorderdtldata.items[i].expected_date!=$scope.formData.createdate ){
						$("#items_table tr:nth-child("+(i+2)+") td:nth-child("+(5)+")").find(".acontainer input").focus();
						
						$rootScope.$broadcast('on_AlertMessage_ERR',"Enter valid expected date");
						flg=false;break;}}else{count =1;break;}	}}
		if($scope.purchaseorderdtldata.items.length == 0){
			$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.TABLE_ERR);

			flg = false;}else if(count==1){
				$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
				$(".acontainer").find("input").focus();
				flg = false;}else if(date==null){
					$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.DATE_ERR);
					flg = false;}
		if(flg==false){focus();}return flg;}







	$scope.tableDatevalidation=function(date,index){

		if(date<$scope.formData.createdate){
			$rootScope.$broadcast('on_AlertMessage_ERR',"Enter valid expected date");

		}
	}



//	alert code existing
	$scope.alert_for_codeexisting = function(flg){
		if(flg == 1){$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.CODE_EXISIT);}}


//	add items to stock item table	
	$scope.addItem = function(index) {

		var date =get_date_format();
		if($scope.disable_all==false ){
			if(index<$scope.purchaseorderdtldata.items.length-1){
    			$("#items_table tr:nth-child("+(index+3)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
    		}else{
			if($scope.purchaseorderdtldata.items.length != 0){
				if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id != undefined && $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id != ""){
					if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].qty == "" || $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].qty <=0){
						$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);
						$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length-1+2)+")").find("#itemqty").select();
					}else{
						$scope.purchaseorderdtldata.items.push({
							id:0,
							pr_hdr_id:null,
							stock_item_id:'',
							stock_item_code:'',
							stock_item_name:'',
							qty:0,
							expected_date:date.mindate,
							request_status:0,});}}else{
								$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
								$(".acontainer").find("input").focus();
								if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id == undefined && $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_code == undefined){
									$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);}}}else{
										$scope.purchaseorderdtldata.items.push({
											id:0,
											pr_hdr_id:null,
											stock_item_id:'',
											stock_item_code:'',
											stock_item_name:'',
											qty:0,
											expected_date:date.mindate,
											request_status:0,});}}}};
//											remove stock item
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
													$scope.purchaseorderdtldata.items.splice(index, 1);$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length+1)+")").find("#itemqty").select();	}
												else{
													$rootScope.$broadcast('on_AlertMessage_ERR',"Atleast one item is required.");
												}});}};


//													clear stock item
													$scope.clear_stock_details_editmode =  function(event){
														$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_id = "";
														$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_code = "";
														$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_name = "";
														$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].stock_item_batch_id ="";
														$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].uomcode="";
														$scope.purchaseorderdtldata.items[event.currentTarget.parentElement.rowIndex-1].qty="";
		
														}

//													return total amount of stock item
													$scope.total = function() {
														var total = 0.00;
														angular.forEach($scope.purchaseorderdtldata.items, function(item) {
															total += (item.qty * item.unit_price);});return total;}

//													onchange stock item name
													$scope.elementid=function(elemenvalue){
														if($scope.disable_all==true){$(elemenvalue).attr("disabled", true);}}

$("#form_div_create_date").keyup('input',function(e){
	if(e.which!=9 && e.which==13){
	$("#items_table tr:nth-child("+(2)+")").find(".acontainer input").focus();
	}});
													
$timeout(function () {$("#DataTables_Table_0_filter").hide(); }, 1); 		

$rootScope.$on("advSearch",function(event){
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
	//$("#SearchText").append("<span class='label label-important'>"+$scope.date+'</span>');

	$scope.searchTxtItms={1:orderstat,2:$scope.date,3:$scope.reqNo};
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
	                       $('#orderDate').val("");
	                       break;
	                   case 3:
	                	   $scope.reqNo="";
	                	    $('#reqNo').val("");
	                	    break;
	                  
	                   }
	                   DataObj.adnlFilters=[{col:2,filters:$scope.date},{col:1,filters:$scope.reqNo},{col:3,filters:$scope.orderstatus}];
	                   vm.dtInstance.reloadData(); 
	                 };
				   
	   				
	   				//
	   			//	Object.keys(object).find(key => object[key] === value)
	   				
				   }
		   }
		 }
	   
	    DataObj.adnlFilters=[{col:2,filters:$scope.date},{col:1,filters:$scope.reqNo},{col:3,filters:$scope.orderstatus}];
		vm.dtInstance.reloadData();
		$scope.searchTxtItms={};
	
});
$rootScope.$on("Search",function(event){
	DataObj.adnlFilters=[{}];
/*	vm.dtInstance.reloadData();
*/		vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	
		$scope.searchTxtItms={};
		
		
});
$("#clear").click(function(){
	DataObj.adnlFilters=[{}];
	$('#SearchText').text("");
	vm.dtInstance.DataTable.search($('#SearchText').text()).draw();	
	$scope.searchTxtItms={};

});




}

mrpApp.directive('autocompeteText', ['$timeout',function($timeout) {
	return {controller: function ($scope,$http) {
    	$scope.currentIndex = 0;

		$("#items_table tbody tr td").keyup('input',function(e){
			$scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;
			/*if(e.which==9 && e.which ==16){
				$("#items_table tr:nth-child("+($scope.purchaseorderdtldata.items.length+1)+")").find("#itemqty").select();
			}
			
			else*/ 
			if(e.shiftKey && e.keyCode == 9){
				$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find(".acontainer input").focus();
				}else if(  e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8 && !(e.shiftKey && e.keyCode == 9)){
				if(e.currentTarget.cellIndex == 1){
					$scope.$apply(function(){
     					

						$scope.clear_stock_details_editmode(e);});}} else if(e.which == 13 ){
			     			if(e.currentTarget.cellIndex == 1){
			     				if($scope.purchaseorderdtldata.items[e.currentTarget.parentElement.rowIndex-1].stock_item_id!=""){
			     				{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find("#itemqty").focus();}
			     		}}
						}else if(e.which == 9 ){
			     			if(e.currentTarget.cellIndex == 1){
			     				/*if($scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id==undefined || $scope.purchaseorderdtldata.items[$scope.purchaseorderdtldata.items.length-1].stock_item_id==""){*/
			     				{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find(".acontainer input").focus();/*}*/}
			     		}
						}});		
		$scope.element = [];
		$scope.table_itemsearch_rowindex=0;
		$scope.tableClicked = function (index) {
			$scope.table_itemsearch_rowindex= index;};
			return $scope;},
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
		    				 $timeout(function () {$("#items_table tr:nth-child("+(strl_scope.currentIndex+2)+")").find("#itemqty").focus();}, 1); 
							strl_scope.alert_for_codeexisting(0);
						}else{
							elem[0].parentNode.lastChild.value="";
							 strl_scope.invoice.items[strl_scope.currentIndex].uomcode = "";
							strl_scope.alert_for_codeexisting(1);}});},
							data: function () {
								var data = strl_scope.itemsData;
								var filterData = [];
								var searchData = eval("/^" + items.searchdata() + "/gi");
								$.each(data, function (i, v) {
									if ( v.name.search(new RegExp(searchData)) != -1) {
										filterData.push(v);}});return filterData;}});			
				for(var i = 0;i<strl_scope.purchaseorderdtldata.items.length;i++){
					if(strl_scope.purchaseorderdtldata.items[i].pr_hdr_id!=undefined && strl_scope.purchaseorderdtldata.items[i].pr_hdr_id!='' ){
						if(strl_scope.purchaseorderdtldata.items[i].flag==0){
							elem[0].parentNode.lastChild.value = strl_scope.purchaseorderdtldata.items[i].stock_item_code;
							strl_scope.elementid(elem[0].parentNode.lastChild);
							strl_scope.purchaseorderdtldata.items[i].flag=1;break;}}}
				$timeout(function () { 	if(strl_scope.formData.request_by!=undefined){$("#items_table tr:nth-child("+(strl_scope.purchaseorderdtldata.items.length+1)+") td:nth-child("+(2)+")").find(".acontainer input").select();
				 }else{$('#request_by').focus();} }, 1); 	
			}};}]);



