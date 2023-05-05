
//Controller for Table and Form 
mrpApp.controller('choices', choices);


function choices($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,
		MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {

	$controller('DatatableController', {$scope: $scope});


//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {

			$scope.formData.code = response;});}

/*	set_sub_menu("#settings");		
	setMenuSelected("#employee_list_left_menu");*/			//active leftmenu
	manageButtons("add");

	$scope.formData = {};

	$scope.show_table=true;

	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
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
	DataObj.order="desc";

	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	vm.dtColumns = [
	                DTColumnBuilder.newColumn('id').withTitle('ID').notVisible().withOption('searchable', false),
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','250px').renderWith(
	                		function(data, type, full, meta) {
	                			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}

	                			return urlFormater(data);  
	                		}),

	                		DTColumnBuilder.newColumn('f_name').withTitle('NAME'),

	                		DTColumnBuilder.newColumn('sex').withTitle('GENDER').withOption('width','250px').renderWith(
	                				function(data,type,full,meta){
	                					if(data=="1")
	                					{
	                						data='Male';
	                					}
	                					else if(data=="2")
	                					{
	                						data='Female';
	                					}
	                					return data;
	                				}),
	                				DTColumnBuilder.newColumn('dob').withTitle('DATE OF BIRTH').withOption('width','250px').renderWith(
	                						function(data, type, full, meta) {
	                							data = geteditDateFormat(data);
	                							return data;
	                						}),
	                						DTColumnBuilder.newColumn('card_no').withTitle('CARD NUMBER').withOption('width','250px'),
	                						DTColumnBuilder.newColumn('status').withTitle('STATUS').withOption('width','250px').renderWith(
	                								function(data,type,full,meta){
	                									if(data=="2")
	                									{
	                										data='<i class="fa fa-user-times empresigned" data-toggle="tooltip" data-placement="bottom" title="" aria-hidden="true" data-original-title="Active"></i>';
	                									}
	                									else if(data=="1")
	                									{
	                										data='<i class="fa  fa-user empactive" data-toggle="tooltip" data-placement="bottom" title="" aria-hidden="true" data-original-title="inactive"></i>';
	                									}
	                									return data;
	                								}),

	                								];

	function urlFormater(data) {

		return '<a id="rcd_edit" class="'+css+'" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
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
		$rootScope.$emit("show_edit_btn_div",cur_row_index);





		$http({
			url : 'getEmployeeData',method : "GET",params:{id:row_data.id},async:false,
		}).then(function(response) {

			$scope.formData = response.data.employee[0];
			$scope.formData.dob=geteditDateFormat(response.data.employee[0].dob);
			$scope.formData.doj=geteditDateFormat(response.data.employee[0].doj)
			
			$scope.formData.department_id=response.data.employee[0].department_ids;
			$scope.formData.department_code=response.data.employee[0].department_code;
			$scope.formData.department_name=response.data.employee[0].department_name;
			$("#form_div_department_code").find(".acontainer input").val(response.data.employee[0].department_code);
			$("#form_div_employee_category_code").find(".acontainer input").val(response.data.employee[0].employee_category_code);
			
			$scope.formData.created_at=response.data.employee[0].created_at;
			
			$scope.formData.employee_category_id =response.data.employee[0].employee_categories_id;
			$scope.formData.employee_category_code =response.data.employee[0].employee_category_code;
			$scope.formData.employee_category_name =response.data.employee[0].employee_category_name;
		});









		/*		$scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,description:row_data.description,dept_type:row_data.dept_type};
		 */		$scope.disable_all = true;
		 $(".acontainer input").attr('disabled', true);

		 $scope.disable_code=true;
		 $scope.hide_code_existing_er = true;

		 $scope.formData.is_global= (row_data.is_global == 0) ? false:true;
		 $scope.selectedIndexTab=0;
	}


	$(document).on('keyup','#form_div_department_code input',function(e){
		if( e.which==13){
			$("#form_div_department_code input").focus();
		}
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$scope.formData.departmentId ="";
				$scope.formData.department_name ="";
				$scope.formData.department_code ="";
			});
		}
	});

	$(document).on('keyup','#form_div_employee_category_code input',function(e){
		if( e.which==13){
			$("#form_div_employee_code input").focus();
		}
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$scope.formData.employee_category_id ="";
				$scope.formData.employee_category_code ="";
				$scope.formData.employee_category_name ="";
			});
		}
	});

	var employee_category_data="";
	$http({
		url : 'formJsonData',
		method : "GET",
		async:false,
	}).then(function(response) {

		department_data = response.data.depData;
		employee_category_data=response.data.employee_category_data;

	}, function(response) { // optional

	});

	$timeout(function () { 

		var DepsourceData = $("#department_code").tautocomplete({
			columns: ['id','code' , 'name'],
			hide: [true,true,true,false],
			placeholder: "search..",
			highlight: "hightlight-classname",
			norecord: "No Records Found",
			delay : 10,
			onchange: function () {
				var selected_department_data =  DepsourceData.all();
				$scope.$apply(function(){
					$scope.formData.department_id =  selected_department_data.id;
					$scope.formData.department_code =  selected_department_data.code;
					$scope.formData.department_name =  selected_department_data.name;
				});

			},
			data: function () {

				var data = department_data;
				var filterData = [];
				var searchData = eval("/" + DepsourceData.searchdata() + "/gi");
				$.each(data, function (i, v) {
					if (v.code.search(new RegExp(searchData)) != -1 || v.name.search(new RegExp(searchData)) != -1) {

						filterData.push(v);

					}
				});

				return filterData;
			},
		});




		var EmsourceData = $("#employee_category_code").tautocomplete({
			columns: ['id','code' , 'name'],
			hide: [true,true,true,false],
			placeholder: "search..",
			highlight: "hightlight-classname",
			norecord: "No Records Found",
			delay : 10,
			onchange: function () {
				var selected_employee_data =  EmsourceData.all();
				$scope.$apply(function(){
					$scope.formData.employee_category_id =  selected_employee_data.id;
					$scope.formData.employee_category_code =  selected_employee_data.code;
					$scope.formData.employee_category_name =  selected_employee_data.name;
				});

			},
			data: function () {

				var data = employee_category_data;
				var filterData = [];
				var searchData = eval("/" + EmsourceData.searchdata() + "/gi");
				$.each(data, function (i, v) {
					if (v.code.search(new RegExp(searchData)) != -1 || v.name.search(new RegExp(searchData)) != -1) {

						filterData.push(v);

					}
				});

				return filterData;
			},
		});



	}, 1); 





	$scope.shipsame=function(){
		if($scope.formData.sameasabove==true){
			$scope.formData.loc_address=$scope.formData.address;
			$scope.formData.loc_country=$scope.formData.country;
			$scope.formData.loc_phone=$scope.formData.phone;
			$scope.formData.loc_zip_code=$scope.formData.zip_code;
			$scope.formData.loc_fax=$scope.formData.fax;	
		}else{
			$scope.formData.loc_address="";
			$scope.formData.loc_country="";
			$scope.formData.loc_phone="";
			$scope.formData.loc_zip_code="";
			$scope.formData.loc_fax="";	


		}
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
					$(".acontainer input").attr('disabled', true);

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
		$(".acontainer input").attr('disabled', true);
		$scope.disable_all = true;
		$scope.disable_code = true;
	}

	$rootScope.$on('fun_save_data',function(event){		//Save Function


		if (code_existing_validation($scope.formData)) {
			

			var currentdate = new Date(); 


			var datetime = 	+ currentdate.getFullYear() + "-"
			+ (currentdate.getMonth()+1)  + "-"
			+ currentdate.getDate() + " "
			+ currentdate.getHours() + ":"  
			+ currentdate.getMinutes() + ":" 
			+ currentdate.getSeconds();

			$scope.formData.is_global= ($scope.formData.is_global == true) ? 1:0;

			if($scope.formData.id==undefined){
				$scope.formData.created_at=datetime;
			}
			
			$scope.formData.created_by=strings['userID'];
			$scope.formData.dob= ($scope.formData.dob != undefined || $scope.formData.dob != "") ? getMysqlFormat($scope.formData.dob):""; 
			$scope.formData.doj= ($scope.formData.doj != "") ? getMysqlFormat($scope.formData.doj):$scope.formData.doj; 
			$scope.Quetable.shopId=settings['currentcompanyid1'];
			$scope.Quetable.origin=settings['currentcompanycode1'];
			$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";	
			
			$http({
				url : 'saveEmp',
				method : "POST",
				params : $scope.formData,
				data : {Quetable:$scope.Quetable},

			}).then(function(response){
				if(response.data == 1){

					$scope.formData.is_global= ($scope.formData.is_global == 0) ? false:true;
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
						
						
						$scope.formData.dob= geteditDateFormat($scope.formData.dob); 
						$scope.formData.doj= geteditDateFormat($scope.formData.doj); 
					}else{
						$scope.formData ={};
						var date =get_date_format();
						$scope.formData.doj=date.mindate;
						$scope.formData.dob=date.mindate;
						$scope.fun_get_pono();
						$("#form_div_department_code").find(".acontainer input").val("");
						$("#form_div_employee_category_code").find(".acontainer input").val("");
					}
					reloadData();
					$scope.hide_code_existing_er = true;
					$scope.selectedIndexTab=0;
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
				$("#form_div_department_code").find(".acontainer input").val("");
				$("#form_div_employee_code").find(".acontainer input").val("");

			}else{
				var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
				edit(row_data[0],cur_row_index);
			}
			clearform();
			function_clear_select();
			$scope.selectedIndexTab=0;});
	});

	$rootScope.$on("fun_enable_inputs",function(){
		 $("#show_form").val(1);
		$scope.disable_all = false;
		$('#f_name').focus();
		$(".acontainer input").attr('disabled', false);


	});

	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});

	$rootScope.$on("fun_clear_form",function(){
		$("#form_div_department_code").find(".acontainer input").val("");
		$("#form_div_employee_category_code").find(".acontainer input").val("");

		$('#f_name').focus();
		$scope.selectedIndexTab=0;
		function_clear_select();
		$scope.formData = {};
		$scope.fun_get_pono();
		$scope.hide_code_existing_er = true;
		$scope.formData.sex=1;
		var date =get_date_format();
		$scope.formData.doj=date.mindate;
		$scope.formData.dob=date.mindate;

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
			flg = false;	
			$('#alert_required').show();	
		}

		if(validateEmail()==false){
			flg = false;

		}



		if(flg==false)
		{
			focus();
		}else{
			$('#alert_required').hide();	
		}

		return flg;

	}


	function function_clear_select(){
		$("#form_div_dept_type").removeClass("has-error");
		$("#form_div_dept_type_error").hide();
		$("#form_div_email").removeClass("has-error");
		$("#form_div_email_error").hide();


	}

	$('#dob').datepicker({
		format: "yyyy-mm-dd",
		autoclose: true
	});



}


