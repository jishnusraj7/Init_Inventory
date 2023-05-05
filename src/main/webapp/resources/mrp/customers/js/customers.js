
//Controller for Table and Form 
mrpApp.controller('customers', customers);


function customers($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,
		MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS) {
	
	$controller('DatatableController', {$scope: $scope});

	$scope.is_subclass = false;
//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			
			$scope.formData.code = response;});}

/*	set_sub_menu("#settings");		
	setMenuSelected("#customers_leftmenu");	*/		//active leftmenu
	manageButtons("add");

	$scope.formData = {is_subclass:false};
	$scope.show_table=true;

	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
	

	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.code_existing_validation = code_existing_validation;
	vm.function_clear_select=function_clear_select;
	vm.ar_check={};
	$scope.selectedIndexTab =0;
	$scope.custTypeList=[];
	
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

	
	vm.dtInstance = {};	
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	vm.dtColumns = [
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','250px').renderWith(
	                		function(data, type, full, meta) {	
	                			if(full.is_ar==1)
	                				{ vm.ar_check[full.id]=true;}
	                			else 
	                				{vm.ar_check[full.id]=false;} 
	                			if(full.is_valid==1){$scope.isValid=true;}
	                			else{{$scope.isValid=false;}}

	                			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}
	                			
	                			return '<a id="rcd_edit" class='+css+' ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
	                			+ data + '</a>';
	                		
	                			
	                		}),

	                		DTColumnBuilder.newColumn('name').withTitle('NAME'),
	                		DTColumnBuilder.newColumn('card_no').withTitle('CARD NUMBER'),
	                		

	                		];

	function urlFormater(data) {

		return '<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
		+ data + '</a>';   
	}

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {					//Rowcallback fun for Get Edit Data when clk on Code
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
		$scope.selectedIndexTab =0;
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
		
		$scope.selectedIndexTab =0;
		$scope.is_arCompany=vm.ar_check[row_data.id];
		$rootScope.$emit("enable_prev_btn");
		$rootScope.$emit("enable_next_btn");//Edit function
		/*var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;*/
		var row_count = vm.dtInstance.DataTable.rows().data();
		$(".acontainer input").attr('disabled', true);
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
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		var dpartmntList = fun_get_dep_name(row_data.department_id);
		$("#form_div_department_code").find(".acontainer input").val(dpartmntList.code);
		$('#department_name').val(dpartmntList.name);
	
		$scope.formData = {id:row_data.id,name:row_data.name,tin:row_data.tin,license_no:row_data.license_no,cst_no:row_data.cst_no,code:row_data.code,card_no:row_data.card_no,address:row_data.address,
				           accumulated_points:row_data.accumulated_points,redeemed_points:row_data.redeemed_points,ar_code:row_data.ar_code,        
				           street:row_data.street,city:row_data.city,state:row_data.state,country:row_data.country,zip_code:row_data.zip_code,
				           phone:row_data.phone,fax:row_data.fax,email:row_data.email,created_at:row_data.created_at,
				           created_by:row_data.created_by,customer_type:row_data.customer_type,is_valid:row_data.is_valid};
	
		if(row_data.is_valid == 1){
			$scope.isValid=true;
		}else
			{
			$scope.isValid=false;
			}

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
					$rootScope.$broadcast('on_AlertMessage_ERR',"customer "+FORM_MESSAGES.ALREADY_USE+"");
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
		$(".acontainer input").attr('disabled', true);
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
		if($scope.is_arCompany == true){$scope.formData.is_ar=1;}
		else{$scope.formData.is_ar=0;}
		if($scope.isValid==true){$scope.formData.is_valid=1;}else{$scope.formData.is_valid=0;}
		  if($scope.formData.id!="" && $scope.formData.id!=undefined){
		$scope.formData.updated_at=today.getFullYear() + "-" + d2(parseInt(today.getMonth()+1)) + "-" + d2(today.getDate()) + " " + d2(today.getHours()) + ":" + d2(today.getMinutes()) + ":" + d2(today.getSeconds());
		$scope.formData.updated_by=parseInt(strings['userID']);
		  }
		$("#form_div_emailid").removeClass("has-error");
		
			$http({
				url : 'saveCustomers',
				method : "POST",
				params : $scope.formData,
				data : {Quetable:$scope.Quetable},
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
					$scope.is_arCompany=false;
					$scope.isValid=false;
					$scope.fun_get_pono();
					$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
				}
				reloadData();
				$scope.selectedIndexTab =0;
				$scope.hide_code_existing_er = true;
				function_clear_select();
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
		$(".acontainer input").attr('disabled', false);
		$("#name").focus();
		
	});

	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
		
	});
	
	

	
	

	$rootScope.$on("fun_clear_form",function(){
		function_clear_select();
		$scope.formData = {customer_type:""};
		$scope.fun_get_pono();
		$scope.hide_code_existing_er = true;
		$scope.is_subclass=false;
		$scope.is_arCompany=false;
		$scope.isValid=false;
		$(".acontainer input").attr('disabled', false);
		$("#imgdiv").addClass('fileupload-new').removeClass('fileupload-exists');
		 $("#imgshw").empty();
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
				$scope.selectedIndexTab =0;
			}


		}

		if(validation() == false){
			flg = false;
			$scope.selectedIndexTab =0;
			
		} if($scope.formData.customer_type == "" || $scope.formData.customer_type==undefined || $scope.formData.customer_type == null )
			{ 
				 flg = false;
				 $("#form_div_cust_type_class").addClass("has-error");
				 $scope.selectedIndexTab =0;
			}else
				{
				 $("#form_div_cust_type_class").removeClass("has-error");
				 $scope.selectedIndexTab =0;
				}
		if($scope.formData.card_no == "" || $scope.formData.card_no == undefined || $scope.formData.card_no ==null)
			{
			flg=false;
			$("#form_div_card_number").addClass("has-error");
			$("#form_div_card_error").show();
			$scope.selectedIndexTab =0;
			}else{
				$("#form_div_card_number").removeClass("has-error");
				$("#form_div_card_error").hide();
			}
		
		if($scope.formData.address == "" || $scope.formData.address==undefined || $scope.formData.address == null )
		       { 
					 flg = false;
					 $("#form_div_address").addClass("has-error");
					 $scope.selectedIndexTab =0;
				}else{
					 $("#form_div_address").removeClass("has-error");
				}
			if($scope.formData.accumulated_points == "" || $scope.formData.accumulated_points == undefined || $scope.formData.accumulated_points ==null)
				{
				flg=false;
				$("#form_div_accum_points").addClass("has-error");
				$("#form_div_accum_error").show();
				 $scope.selectedIndexTab =0;
				}else
					{
					$("#form_div_accum_points").removeClass("has-error");
					$("#form_div_accum_error").hide();
					}
		
		
		 if($scope.formData.redeemed_points == "" || $scope.formData.redeemed_points == undefined || $scope.formData.redeemed_points ==null)
		{
		flg=false;
		$("#form_div_redeem_points").addClass("has-error");
		$("#form_div_redeem_error").show();
		 $scope.selectedIndexTab =0;
		}else{
			$("#form_div_redeem_points").removeClass("has-error");
			$("#form_div_redeem_error").hide();
		}
	  if(validateEmail() == false){
				flg = false;
				$("#form_div_emailid_error").show();
				$scope.selectedIndexTab =1;
				}	
		if(flg==false)
		{
			focus();
		}

		return flg;

	}


	function function_clear_select(){
		//$("#form_div_dept_type").removeClass("has-error");
		//$("#form_div_dept_type_error").hide();
		 $("#form_div_cust_type_class").removeClass("has-error");
		 $("#form_div_address").removeClass("has-error");
		 $("#form_div_accum_points").removeClass("has-error");
		 $("#form_div_redeem_points").removeClass("has-error");
		 $("#form_div_card_number").removeClass("has-error");
			$("#form_div_emailid").removeClass("has-error");
			$("#form_div_emailid_error").hide();
			$("#form_div_card_error").hide();
			$("#form_div_accum_error").hide();
			$("#form_div_redeem_error").hide();
	}
	
	$scope.changeToSubClss=function()
	{
		
		if($scope.is_subclass==true)
			{
		       $scope.formData.departmentId =  "";
		       $scope.formData.department_code =  "";
		       $scope.formData.department_name = "";
		       $("#form_div_department_code").find(".acontainer input").val('');
				$('#department_name').val('');
			}else if($scope.is_subclass == false)
				{
				$scope.formData.super_class_id="";
				$scope.formData.bgColor="";
				$scope.formData.textColor="";
				}
		
	}
	
	var department_data = [];
	$http({	url : '../stockin/depList',	method : "GET",async:false,
	}).then(function(response) {
		department_data = response.data.data;
	}, function(response) { // optional
	});
	 
	

	/*$scope.filterDepartment=function(){		
		alert($scope.formData.department_id);
	}*/
	
	/*var super_list=[];
	$scope.customerTypeList=[{id:-1,name:"select"}];
	$http({
		method: 'GET',
		async: false,
		url : "../customertypes/customerTypeList",
		data: { applicationId: 1 }
	}).success(function (result) {
		super_list = result.data;
		$scope.suprList = super_list;
		for(var i=0; i<result.data.length;i++){
			$scope.customerTypeList.push(result.data[i]);

		}

	});
	if($scope.customerTypeList[0].id != -1 && $scope.customerTypeList[0].id !=undefined && $scope.customerTypeList[0].id != null)
		{
	
	$scope.formData.customer_type = $scope.customerTypeList[0].id;
		}
	$scope.filterSuperClass=function(){		
	
	}*/
	
	$http({
		method: 'GET',
		async: false,
		url : "../customertypes/customerTypeList",
		data: { applicationId: 1 }
	}).success(function (result) {
		
		$scope.custTypeList=result.customTypeList;
		$scope.custTypeList.splice(0,0,{id:"",name:"select"});
	});
	function fun_get_dep_name(id){
		var depList =[];
		for(var i=0;i < department_data.length;i++){
			if(department_data[i].id == id){
				depList = department_data[i];
			}
		}

		return depList;
	}

	$scope.arCodeChange=function()
	{
		if($scope.is_arCompany == false)
			{
			$scope.formData.ar_code="";
			}
	}
	
	
}


