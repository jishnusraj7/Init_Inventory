

//Controller for Table and Form 
mrpApp.controller('supplierctrl', supplierctrl);

function supplierctrl($compile,$scope,$timeout,$http, $mdDialog ,$rootScope, DTOptionsBuilder,$q,
		DTColumnBuilder,$controller,MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {

	$controller('DatatableController', {$scope: $scope});

/*	set_sub_menu("#settings");						
	setMenuSelected("#supplier_left_menu");	*/		//active leftmenu
	manageButtons("add");

	$scope.formData = {};
	$scope.show_table=true;
	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
	$scope.countryList=[];
	$scope.cityitem=[];
	$scope.cityList=[];
	$scope.stateitem=[];
	$scope.stateList=[];

	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

	
//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			
			$scope.formData.code = response;});}

	$http({
		url : 'formJsonData',
		method : "GET",
	}).then(function(response) {
		
		
		//$scope.countryList.push({id:"",name:'Select'});
		$scope.cityList.push({id:"",name:'Select'});
		$scope.stateList.push({id:"",name:'Select'});
		
		$scope.stateitem=response.data.state;
		//$scope.cityitem=response.data.city;

		
		for(var i=0;i<response.data.state.length;i++){
			$scope.stateList.push(response.data.state[i]);
			}
		
		/*for(var i=0;i<$scope.cityitem.length;i++){
			$scope.cityList.push($scope.cityitem[i]);
			}*/

		for(var i=0;i<response.data.country.length;i++){
			$scope.countryList.push(response.data.country[i]);}
		$scope.formData.country=$scope.countryList[0].id;
		
		
	});


	$scope.countrychange=function(){
		$scope.statedata=$scope.stateitem;
		$scope.stateList=[];
		$scope.stateList.push({id:"",name:'Select'});
		for(var i=0; i < $scope.statedata.length;i++){
			if($scope.formData.country==$scope.statedata[i].country_id){
				$scope.stateList.push($scope.statedata[i]);
			}
		}
		if($scope.formData.country=="")
			{
			$scope.formData.state="";
			$scope.formData.city="";
			}
	}


	$scope.statechange=function(){
		//$scope.citydata=$scope.cityitem;
		$scope.cityList=[];
		$scope.cityList.push({id:"",name:'Select'});
		/*for(var i=0; i < $scope.citydata.length;i++){
			if($scope.formData.state==$scope.citydata[i].state_id){
				$scope.cityList.push($scope.citydata[i]);
			}
		}*/
		
		if($scope.formData.state!="")
			{
		
		$http({
			url : 'getCity',
			method : "GET",
			params:{state_id:$scope.formData.state}
		}).then(function(response) {
			$scope.cityList=[];
			$scope.cityList=response.data.city;
			$scope.cityList.splice(0,0,{id : "" ,name : "select"});
			$scope.formData.city="";
			
		});
			}
		
		
		
	}




	var vm = this;
	vm.dtInstance = {};
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 		

	vm.dtColumns = [
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('width','150px').renderWith(
	                		function(data, type, full, meta) {
	                			
	                			if(full.quetableId!="" && full.quetableId!=undefined){css="queTableInColor"}else{css="queTableOutColor"}
	                			
	                			return '<a id="rcd_edit" class='+css+' ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
	                			+ data + '</a>';
	                		}),
	                		DTColumnBuilder.newColumn('name').withTitle('NAME'),
	                		DTColumnBuilder.newColumn('contact_email').withOption('width','280px').withTitle('CONTACT EMAIL'),
	                		DTColumnBuilder.newColumn('contact_no').withOption('width','200px').withTitle('CONTACT NO'),
	                		DTColumnBuilder.newColumn('state').withOption('width','200px').withTitle('').notVisible(),
	                		];




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
					$scope.edit(rowData,current_row_index,e);
				} 
			});
		});
		return nRow;
	}

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
		$scope.reloadData();
	});

	$rootScope.$on('hide_table',function(event){
		$scope.showTable(event);
	});
	$rootScope.$on('hide_form',function(event){
		  $("#show_form").val(0);
		$scope.show_table=true;
		$scope.show_form=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
	});
	$scope.showTable=function(event){
		$scope.show_table=false;
		$scope.show_form=true;
		$("#contact_no").css('border-color','#cccccc');
	}

	$scope.reloadData=function() {
		vm.dtInstance.reloadData(null, true);
	}


	$scope.edit=function(row_data,cur_row_index,event) {									

		//$scope.stateList=$scope.stateitem;
		//$scope.cityList=$scope.cityitem;
		//$scope.cityList.splice(0,0,{id : "" ,name : "select"});
		//var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
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
				$rootScope.$emit("enable_prev_btn");
			}
		}
		$scope.hide_code_existing_er = true;
		$scope.function_clear_select();
		$scope.showTable();
		clearform();
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		
		$scope.sup_id = {id:row_data.id};
		$scope.formData.state=row_data.state;
		$scope.statechange();
		
		
		$http({
			url :'supplierList',
			method : "GET",
			params :  $scope.sup_id,
		}).then(function(response) {

			$scope.formData = response.data[0];
		
			
		
		
/*		$scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,address:row_data.address,tin_no:row_data.tin_no,abbreviation:row_data.abbreviation,city:row_data.city,state:row_data.state,country:row_data.country,pin_code:row_data.pin_code,contact_person:row_data.contact_person,contact_no:row_data.contact_no,contact_email:row_data.contact_email,back_office_ref:row_data.back_office_ref,comments:row_data.comments,is_active:row_data.is_active};
*/		$scope.formData.is_active= ($scope.formData.is_active == 0) ? true:false;
		$scope.disable_all = true;
		$scope.disable_code = true;
		
		
		
		});
	}

	$rootScope.$on("fun_delete_current_data",function(event){			//Delete Function

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
			$http({
				url : 'delete',
				method : "POST",
				params : {id:$scope.formData.id},
			}).then(function(response) {
				
				if(response.data==0)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR','Supplier'+FORM_MESSAGES.ALREADY_USE);

				}else if(response.data ==1){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
					$("#show_form").val(0);
					$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
					$scope.disable_all = true;
					$scope.disable_code = true;
					$scope.reloadData();		
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
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
				
			});});});





	$scope.view_mode_aftr_edit=function(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$scope.disable_code = true;
	}

	
	
	
	
	
	$rootScope.$on('fun_save_data',function(event){		//Save Function
		if ($scope.code_existing_validation($scope.formData)) {
			
			
			var id= ($scope.formData.is_active == true) ? 0:1;

			$scope.formData.is_active  = id;
			
			
		   
			
				$scope.Quetable.shopId=settings['currentcompanyid1'];
				$scope.Quetable.origin=settings['currentcompanycode1'];
				
				
			
			$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
				  

			$http({
				url : 'saveSupplier',
				method : "POST",
				params : $scope.formData,
				data : {Quetable:$scope.Quetable},
			}).then(function(response) {
				
				if(response.data==1)
				{	
				if($scope.formData.id !=undefined)
				{					$scope.view_mode_aftr_edit();

					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
				}
				else
				{$scope.formData ={};
				$scope.fun_get_pono();
				$scope.formData.city=$scope.cityList[0].id;
				$scope.formData.state=$scope.stateList[0].id;
				$scope.formData.country=$scope.countryList[0].id;
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
				}
				$scope.reloadData();
				$scope.hide_code_existing_er = true;
				$scope.formData.is_active= ($scope.formData.is_active == 0) ? true:false;
				$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

			}else
				{
				$mdDialog.show($mdDialog.alert()
						.parent(angular.element(document.querySelector('#dialogContainer')))
						.clickOutsideToClose(true)
						.textContent("Save failed.")
						.ok('Ok!')
						.targetEvent(event)
				);
				}}, function(response) { // optional
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
					
					$scope.cityList=[];
					$scope.cityList.push({id:"",name:'Select'});
					$scope.formData.city="";
					//$scope.formData.city=$scope.cityList[0].id;
					//$scope.formData.state=$scope.stateList[0].id;
					$scope.formData.state="";
					$scope.formData.country=$scope.countryList[0].id;
					$scope.hide_code_existing_er = true;
					$("#contact_no").css('border-color','#cccccc');

				}else{
					var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
					$scope.edit(row_data[0],cur_row_index);
				}
				$scope.function_clear_select();

				clearform();}
		);
	});

	$rootScope.$on("fun_enable_inputs",function(){
		$scope.disable_all = false;
        $("#show_form").val(1);
	});

	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});

	$rootScope.$on("fun_clear_form",function(){
		$scope.function_clear_select();
		$scope.formData = {};
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

		$scope.fun_get_pono();
		//$scope.formData.city=$scope.cityList[0].id;
		//$scope.formData.state=$scope.stateList[0].id;
		$scope.formData.state="";
		$scope.statechange();
		$scope.formData.city="";
		$scope.formData.country=$scope.countryList[0].id;
		$scope.focus=true;
		$scope.hide_code_existing_er = true;


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
	
	$("#contact_no").on("keypress keyup blur",function (event) {    
        $(this).val($(this).val().replace(/[^\d].+/, ""));
        if ((event.which < 48 || event.which > 57)) {
            event.preventDefault();
        }
    });

	// Validation 

	$scope.code_existing_validation=function(data){
		var flg_code=0;
		var flg = true;

		var row_data = vm.dtInstance.DataTable.rows().data();
		if(data.id == undefined || data.id == ""){	
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#code").select();
			}
			
			for(var i=0; i<row_data.length; i++){
				if(row_data[i].code == data.code){
					$scope.hide_code_existing_er = false;
					$scope.existing_code = '"' + data.code + '" Existing';
					flg = false;
					return false;
				}
			}
		}

		
		if(validation() == false){
			flg = false;

		}

		if(validateEmail()==false){
			flg = false;

		}
		if($scope.formData.country == undefined || $scope.formData.country == ""){
			$("#form_div_country").addClass("has-error");
			$("#form_div_country_type_error").show();
			flg = false;
		}else{
			$("#form_div_country").removeClass("has-error");
			$("#form_div_country_type_error").hide();
		}
		if($scope.formData.city == undefined || $scope.formData.city == "" ){
			$("#form_div_city").addClass("has-error");
			$("#form_div_city_type_error").show();
			flg = false;
		}else{
			$("#form_div_city").removeClass("has-error");
			$("#form_div_city_type_error").hide();
		}

		if($scope.formData.state == undefined || $scope.formData.state == ""){
			$("#form_div_state").addClass("has-error");
			$("#form_div_state_type_error").show();
			flg = false;
		}else{
			
			$("#form_div_state").removeClass("has-error");
			$("#form_div_state_type_error").hide();
		}

		if($("#emailid").val() == null || $("#emailid").val() == ""){
			$("#form_div_emailid").removeClass("has-error");
			$("#form_div_emailid_error").hide();

		}
		
		if(($("#contact_no").val()).length < 10){
			$("#contact_no").prop('border-color','#dd4b39');
			flg = false;
		}
		
		var filter = /([0-9]{10})|(\([0-9]{3}\)\s+[0-9]{3}\-[0-9]{4})/;
		
		if(filter.test($("#contact_no").val())){
			$("#contact_no").css('border-color','#cccccc');
			flg = true;
		}else{
			$("#contact_no").css('border-color','#dd4b39');
			flg = false;
		}
		
		if(flg==false)
		{
			focus();
		}

		return flg;
	}

	$scope.function_clear_select=function(){
		$("#form_div_emailid").removeClass("has-error");
		$("#form_div_emailid_error").hide();
		$("#form_div_country").removeClass("has-error");
		$("#form_div_country_type_error").hide();
		$("#form_div_city").removeClass("has-error");
		$("#form_div_city_type_error").hide();
		$("#form_div_state").removeClass("has-error");
		$("#form_div_state_type_error").hide();
	}

}










