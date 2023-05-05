
//Controller for Table and Form 
mrpApp.controller('itemclass', itemclass);


function itemclass($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,
		MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {

	$controller('DatatableController', {$scope: $scope});

	$scope.is_subclass = false;
//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {

			$scope.formData.code = response;});}

/*	set_sub_menu("#settings");		
	setMenuSelected("#item_class_left_menu");*/			//active leftmenu
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
	vm.selected={};
	$scope.selectedIndexTab =0;

	vm.dtInstance = {};	
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	vm.dtColumns = [
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('width','250px').withOption('type', 'natural').renderWith(
	                		function(data, type, full, meta) {

	                			if(full.super_class_id != null && full.super_class_id !=undefined && full.super_class_id!="")
	                			{
	                				/*$scope.formData.is_subclass=true;*/
	                				vm.selected[full.id] = true;
	                			}
	                			else 
	                			{
	                				vm.selected[full.id] = false;
	                			}
	                			return urlFormater(data);  
	                		}),

	                		DTColumnBuilder.newColumn('name').withTitle('NAME'),
	                		DTColumnBuilder.newColumn('alternative_name').withTitle('ALTERNATIVE NAME'),
	                		DTColumnBuilder.newColumn('super_class_name').withTitle('SUPER CLASS'),
	                		DTColumnBuilder.newColumn('dept_name').withTitle('DEPARTMENT').withOption('width','250px'),
	                		DTColumnBuilder.newColumn('hsn_code').withTitle('').notVisible().withOption('searchable','false'),
	                		DTColumnBuilder.newColumn('print_order').withTitle('').notVisible().withOption('searchable','false'),
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
					$scope.formData.itemThumb!="";
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
		$scope.formData.itemThumb="";
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
		$scope.is_subclass=vm.selected[row_data.id];
		//alert($scope.formData.is_subclass);
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
		$scope.formData.itemThumb="";
		$("#form_div_department_code").find(".acontainer input").val(dpartmntList.code);
		$('#department_name').val(dpartmntList.name);
		$scope.formData = {id:row_data.id,print_order:row_data.print_order,hsn_code:row_data.hsn_code,name:row_data.name,code:row_data.code,alternative_name:row_data.alternative_name,description:row_data.description,account_code:row_data.account_code,bgColor:row_data.bg_color,textColor:row_data.fg_color,super_class_id:row_data.super_class_id,departmentId:row_data.department_id,
				departmentId:row_data.department_id,department_code:dpartmntList.code,department_name:dpartmntList.name,
				created_at:row_data.created_at,created_by:row_data.created_by};
		$scope.formData.itemThumb=row_data.item_thumb;
		console.log($scope.formData.itemThumb);
		if($scope.formData.itemThumb!="")
		{
			var time=new Date().getTime() / 1000;

			$("#imgshw").empty();
			//$("#imgshw").html('<img data-ng-src="data:image/jpg;base64,'+$scope.formData.itemThumb+'" style="max_height:150px">');
			
			angular.element(document.getElementById('imgshw')).html($compile('<img data-ng-src="data:image/jpg;base64,'+$scope.formData.itemThumb+'" style="max_height:150px">')($scope))
			
			$("#imgdiv").addClass('fileupload-exists').removeClass('fileupload-new');


			/* $("span.fileupload-new").css('display', 'none');
           $("span.fileupload-exists").css('display', 'inline');
         $("a.btn.btn-danger.fileupload-exists").css('display', 'inline');*/

		}
		else
		{
			$("#imgshw").empty();
			$("#imgdiv").addClass('fileupload-new').removeClass('fileupload-exists');

		}

		$scope.disable_all = true;
		$scope.disable_code=true;
		$scope.hide_code_existing_er = true;
	}

	$scope.removeImage=function()
	{
		$scope.myFile="";
		$scope.formData.itemThumb="";
		$("#imgshw img").remove();
		$("#defaultImage img").remove();
		$("#item_thumb").addClass('fileupload-new').removeClass('fileupload-exists')

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
					$rootScope.$broadcast('on_AlertMessage_ERR',"Item class "+FORM_MESSAGES.ALREADY_USE+"");
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
			}
			else if(cur_index == 0){

				if(index-1 == 0){

					$rootScope.$emit("disable_prev_btn");

				}
				else if(index+1 == row_count-1){
					$rootScope.$emit("disable_next_btn");

				}
			}

		}
		else if(row_count == 1){
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
	//var sDate = today.getFullYear() + "-" + d2(parseInt(today.getMonth()+1)) + "-" + d2(today.getDate()) + " " + d2(today.getHours()) + ":" + d2(today.getMinutes()) + ":" + d2(today.getSeconds());

	//alert(sDate);

	//alert($scope.formData.created_by);
	$rootScope.$on('fun_save_data',function(event){		//Save Function

		if (code_existing_validation($scope.formData)) {

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

			var file = $scope.myFile;
			var fdata = new FormData();
			var data = JSON.stringify({itemData:$scope.formData,file:$scope.myFile});
			fdata.append("imgFile",file);
			fdata.append("data",data);

			$http.post("saveItem",fdata,{
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : undefined
				}
			}).success(function(response) {

				if(response == "status:success"){
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
						$scope.fun_get_pono();
						$scope.is_subclass=false;
						$("#form_div_department_code").find(".acontainer input").val('');
						$('#department_name').val('');
						$scope.formData.bgColor="";
						$scope.formData.textColor=""; 
						$scope.is_apply_theme=false;
						$("#imgdiv").addClass('fileupload-new').removeClass('fileupload-exists');
						$("#imgshw").empty();
					}

					reloadData();
					
					$("#name").focus();

					function_clear_select();
					$scope.selectedIndexTab =0;
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
		$(".acontainer input").attr('disabled', false);
		$("#name").focus();
		$scope.selectedIndexTab =0;

	});

	$rootScope.$on("fun_enable_inputs_code",function(){

		$scope.disable_code = false;

	});




	$rootScope.$on("fun_clear_form",function(){

		function_clear_select();
		$scope.formData = {super_class_id:""};
		$("#form_div_department_code").find(".acontainer input").val('');
		$('#department_name').val('');
		$scope.fun_get_pono();
		$scope.hide_code_existing_er = true;
		$scope.is_subclass=false;
		$(".acontainer input").attr('disabled', false);
		$("#imgdiv").addClass('fileupload-new').removeClass('fileupload-exists');
		$("#imgshw").empty();
		$scope.formData.bgColor="";
		$scope.formData.textColor=""; 
		$scope.is_apply_theme=false;

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

		}if($scope.is_subclass == false)
		{
			if($scope.formData.departmentId == "" || $scope.formData.departmentId == undefined || $scope.formData.departmentId == null)
			{
				flg = false;
				$("#form_div_department_code").addClass("has-error");
				$scope.selectedIndexTab =0;
				$("#form_div_department_code_error").show();
				$("#form_div_department_code").find(".acontainer input").select();
			}
			else{
				$("#form_div_department_code").removeClass("has-error");
				$("#form_div_department_code_error").hide();
			}
		}else if($scope.is_subclass == true)
		{
			if($scope.formData.super_class_id == "" || $scope.formData.super_class_id == undefined || $scope.formData.super_class_id == null || $scope.formData.super_class_id== -1)
			{
				flg=false;
				$("#form_div_supr_class").addClass("has-error");
				$scope.selectedIndexTab =1;

			}else{
				$("#form_div_supr_class").removeClass("has-error");
			}
		}
		if( $scope.myFile!=undefined)
		{
			if($scope.myFile.size>500*1024)
			{
				$("#imgshw").css("border-color","red");
				$rootScope.$broadcast('on_AlertMessage_ERR','Select image size less than 500kb');
				flg = false;
			}
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
		$("#form_div_department_code").removeClass("has-error");
		$("#form_div_department_code_error").hide();
		$("#form_div_supr_class").removeClass("has-error");
		$("#form_div_super_class_error").hide();


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
	$http({	url : 'depList',	method : "GET",async:false,
	}).then(function(response) {
		department_data = response.data.data;
	}, function(response) { // optional
	});




	$timeout(function () { 
		var departmentData = $("#department_code").tautocomplete({
			columns: ['id','code' , 'name'],
			hide: [false,true,true],
			placeholder: "search..",
			highlight: "hightlight-classname",
			norecord: "No Records Found",
			delay : 10,
			onchange: function () {
				var selected_department_data =  departmentData.all();
				$scope.$apply(function(){
					$scope.formData.departmentId =  selected_department_data.id;
					$scope.department_code =  selected_department_data.code;
					$scope.department_name =  selected_department_data.name;
				});

			},
			data: function () {
				var data = department_data;
				var filterData = [];
				var searchData = eval("/^" + departmentData.searchdata() + "/gi");
				$.each(data, function (i, v) {
					if ( v.name.search(new RegExp(searchData)) != -1) {
						filterData.push(v);
					}
				});

				return filterData;
			},
		});

	}, 1500); 


	/*$scope.filterDepartment=function(){		
		alert($scope.formData.department_id);
	}*/



	$http({
		method: 'GET',
		async: false,
		url : "superclassList",
		data: { applicationId: 1 }
	}).success(function (result) {

		$scope.superClassList = result.superClassList;
		$scope.superClassList.splice(0,0,{id:"",name:"select"});

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

	$(document).on('keyup','#form_div_department_code input',function(e){
		if( e.which==13){
			$("#form_div_desti_code input").focus();
		}

		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$scope.formData.departmentId =  "";
				$scope.formData.department_code =  "";
				$scope.formData.department_name = "";
				$('#department_name').val('');


			});
		}
	});

}
mrpApp.directive('fileModel', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function(){
				scope.$apply(function(){
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]);


