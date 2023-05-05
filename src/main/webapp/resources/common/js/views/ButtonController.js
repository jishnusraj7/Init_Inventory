//Controller for Buttons 
mrpApp.controller('btn_ctrl', function($scope,$timeout,$rootScope,$http,$window) {

	var vm                   = this;
	vm.form_next_data        = form_next_data;
	vm.fun_show_addmode_btn  = fun_show_addmode_btn;

	$scope.alertMessageStatus=true;
	$scope.alertMeaasge      ="";

	$scope.openmodal= function() {		
		$rootScope.$emit('Showpopup');
	}
	$scope.fun_show_form = function() {			
		manageButtons("save");
	//	$('#itemLists').hide();
		$rootScope.$emit('hide_table');
		$rootScope.$emit("fun_enable_inputs");			
		$rootScope.$emit("fun_enable_inputs_code");
		$rootScope.$emit("fun_clear_form");					//clear and enable form fileds
		clearform();
		$("#btnBack").show();
	}
	

	
	$scope.fun_save_form = function() {						//fun for save or update Data - click on SAVE
		$rootScope.$emit('fun_save_data');
	}
	
	$scope.Search=function(){
		$rootScope.$emit('Search');
	}
	
	$scope.advSearch=function(){
		$rootScope.$emit('advSearch');
	}
	
	$scope.fun_backTo_table = function(){					//Button Back 
		fun_show_addmode_btn();
		//$('#itemLists').show();
	}
	
	$rootScope.$on("show_addmode_aftr_edit",function(event,id){	
		fun_show_addmode_btn();
	});
	
	function fun_show_addmode_btn(){
		manageButtons("add");
		$rootScope.$emit('hide_form');
		
	}
	
	$scope.fun_discard_form = function(data){				//Discard Formfileds values in edit or view mode
		
		$rootScope.$emit("fun_discard_form");
	}
	
	$scope.fun_delete_form = function(){					// func for Delete
		$rootScope.$emit("fun_delete_current_data");
		//$('#itemLists').hide();
	}
	
	$scope.fun_edit_form = function(){						// When EDIT btn clk disable all fileds
		$rootScope.$emit("fun_enable_inputs");
		manageButtons("save");
		$("#btnBack").hide();
	//	$('#itemLists').hide();
	}
	
	$rootScope.$on("show_edit_btn_div",function(event,id){			//Show Edit btn group in Edit mode
		manageButtons("view");
		form_next_data(id);
	});
	
	
	$scope.import_data = function(){					//Button Back 
		$rootScope.$emit("import_data");
}
	
	
	$scope.prev_formData = function(event){					//functions for NEXT-PREV button actions
		$rootScope.$emit("fun_prev_rowData",event.target.id);
	}
	
	$scope.next_formData = function(event){
		$rootScope.$emit("fun_next_rowData",event.target.id);
	}
	
	function form_next_data(id){									
		 $scope.row_id = id;
	}
	
	$rootScope.$on("next_formdata_set",function(event,id){
		form_next_data(id);
	});
	
	$rootScope.$on("disable_next_btn",function(event){
		$scope.disable_next_btn=true;
	});
	
	$rootScope.$on("disable_prev_btn",function(event){
		$scope.disable_prev_btn=true;
	});
	
	$rootScope.$on("enable_next_btn",function(event){
		$scope.disable_next_btn=false;
	});
	
	$rootScope.$on("enable_prev_btn",function(event){
		$scope.disable_prev_btn=false;
	});

   $rootScope.$on("on_AlertMessage_SUCC",function(event,msg){
		setSuc_AlertMessage(event,msg);	
	});
   $rootScope.$on("on_AlertMessage_ERR",function(event,msg){	
		setErr_AlertMessage(event,msg);	
	});
		
	function setSuc_AlertMessage(event,msg){
        $scope.succ_alertMessageStatus=false; 
        $scope.succ_alertMeaasge    =  msg;
        $timeout(function () { $scope.succ_alertMessageStatus = true; }, 1500); 
	}
	
	function setErr_AlertMessage(event,msg){
		 $scope.err_alertMessageStatus=false; 
	     $scope.err_alertMeaasge    =  msg;
	     $timeout(function () { $scope.err_alertMessageStatus = true; }, 1500); 		 	
	}
	
	$scope.removeFilter = function (){
		
		alert("df");
		//$("#date").remove();
		
	}
	$rootScope.$on('get_Dept_list', function (event, args) {
		 $scope.departmentListPrd = args.dep;
		 $scope.department_id_prd= $scope.departmentListPrd[0].id;
		 });
	
	$rootScope.$on('get_itmCtgry_list', function (event, args) {
		 $scope.itmCatgryList1 = args.dep;
		 $scope.itm_category_id= $scope.itmCatgryList1[0].id;
		 });
	$rootScope.$on('setItmcategory', function (event) {
		 $scope.itm_category_id= "";
		 });
	
	$scope.fun_show_summary = function() {			
		$rootScope.$emit('show_summary');
	}
	
	$scope.fun_go_prod = function() {			
		$rootScope.$emit('goto_prod');
	}
	
});

