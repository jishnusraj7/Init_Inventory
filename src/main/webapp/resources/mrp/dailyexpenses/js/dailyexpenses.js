var myApp = angular.module('uom_app', [ 'datatables', 'ngMaterial', "common_app"]);

// Controller for Buttons 
myApp.controller('btn_ctrl', function($scope,$rootScope,$http,$window) {
	
	set_sub_menu("#account");						
	setMenuSelected("#dailyexpenses_left_menu");			//active leftmenu
	manageButtons("add");
	
	var vm = this;
	vm.form_next_data = form_next_data;
	vm.fun_show_addmode_btn = fun_show_addmode_btn;

	
	$scope.fun_show_form = function() {			
		
		$rootScope.$emit('hide_table');
		$rootScope.$emit("fun_enable_inputs");			
		$rootScope.$emit("fun_enable_inputs_code");
		$rootScope.$emit("fun_clear_form");					//clear and enable form fileds
		clearform();
		manageButtons("save");
		$("#btnBack").show();
	}
	
	$scope.fun_save_form = function() {						//fun for save or update Data - click on SAVE
		$rootScope.$emit('fun_save_data');
	}
	
	$scope.fun_backTo_table = function(){					//Button Back 
		fun_show_addmode_btn();
	}
	
	$rootScope.$on("show_addmode_aftr_edit",function(event,id){	
		fun_show_addmode_btn();
	});
	
	function fun_show_addmode_btn(){
		$rootScope.$emit('hide_form');
		manageButtons("add");
	}
	
	$scope.fun_discard_form = function(data){				//Discard Formfileds values in edit or view mode
		
		$rootScope.$emit("fun_discard_form");
	}
	
	$scope.fun_delete_form = function(){					// func for Delete
		$rootScope.$emit("fun_delete_current_data");
	}
	
	$scope.fun_edit_form = function(){						// When EDIT btn clk disable all fileds
		$rootScope.$emit("fun_enable_inputs");
		manageButtons("save");
		$("#btnBack").hide();
	}
	
	$rootScope.$on("show_edit_btn_div",function(event,id){			//Show Edit btn group in Edit mode
		manageButtons("view");
		form_next_data(id);
	});
	
	
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
	
	
});

//Controller for Table and Form 
myApp.controller('uomctrl', uomctrl);


function uomctrl($scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder) {
	$scope.formData = {};
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
	

	vm.dtInstance = {};

	vm.dtOptions = DTOptionsBuilder
			.fromSource('json')
			.withOption('rowCallback', rowCallback)
			.withOption('infoCallback', infoCallback)
			.withPaginationType('full_numbers')
			.withDataProp('data')
			.withOption('lengthMenu',
					[ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "All" ] ])
			.withOption(
					'language',
					{
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
					}).withPaginationType('simple').withDisplayLength(10)
			.withDOM('<"pull-left"l><"pull-left"f><"pull-left"p>it');
	;

	vm.dtColumns = [
			DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
			DTColumnBuilder.newColumn('code').withTitle('CODE').renderWith(
					function(data, type, full, meta) {
						return '<a id="rcd_edit" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
						+ data + '</a>';
					}),
			DTColumnBuilder.newColumn('name').withTitle('NAME'),
			
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
					edit(rowData,current_row_index,e);
				} 
			});
		});
		return nRow;
	}

	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
	    var pageInfo = api.page.info();
	    
	    return pageInfo.page+1 +" / "+pageInfo.pages;
	    
	}

	
	$rootScope.$on('reloadDatatable',function(event){					//reload Datatable
		reloadData();
	});
	
	$rootScope.$on('hide_table',function(event){
		showTable(event);
	});
	
	$rootScope.$on('hide_form',function(event){
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
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
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
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,description:row_data.description};
		$scope.disable_all = true;
		$scope.disable_code = true;
	}

	$rootScope.$on("fun_delete_current_data",function(event){			//Delete Function

		var confirm = $mdDialog.confirm().title(
				'Are you sure to delete the record?').targetEvent(event).ok(
				'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			$http({
				url : 'delete',
				method : "POST",
				params : {id:$scope.formData.id},
			}).then(function(response) {
				 var index = select_next_prev_row(current_row_index);
				 $scope.disable_all = true;
				 $scope.disable_code = true;
				var alertbox = $mdDialog.alert()
                .parent(angular.element(document.querySelector('#dialogContainer')))
                .clickOutsideToClose(true)
                .textContent('Record deleted successfully!')
                .ok('Ok!')
                .targetEvent(event);
				 $mdDialog.show(alertbox).then(function() {
					 vm.dtInstance.reloadData(null, true);
				});
			}, function(response) { // optional

			});

		}, function() {
			
		});
	});

	
function select_next_prev_row(index){										//set NEXT-PREV Data In Form Atfer Deleteion
		
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		if(index == row_count-1){
			index = index-1;
		}else{
			index = index+1;
		}
		var selcted_row_data = vm.dtInstance.DataTable.rows(index).data();
		edit(selcted_row_data[0],index);
		$rootScope.$emit("next_formdata_set",index);
		if(index-1 == 0){
			$rootScope.$emit("disable_prev_btn");
		}else if(index+1 == row_count-1){
			$rootScope.$emit("disable_next_btn");
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
	}
	
	$rootScope.$on('fun_save_data',function(event){		//Save Function
		if (code_existing_validation($scope.formData)) {
			$http({
				url : 'save',
				method : "POST",
				params : $scope.formData,
			}).then(function(response) {
				 $mdDialog.show($mdDialog.alert()
		                     .parent(angular.element(document.querySelector('#dialogContainer')))
		                     .clickOutsideToClose(true)
		                     .textContent('Record Save successfully!')
		                     .ok('Ok!')
		                     .targetEvent(event)
		               );
				
				 if($scope.formData.id !=undefined){
					view_mode_aftr_edit();
				 }else{
					 $scope.formData ={};
				 }
				
				 reloadData();
				 $scope.hide_code_existing_er = true;
			}, function(response) { // optional

			});
		}
	});
	
	$rootScope.$on("fun_discard_form",function(event){				//Discard function
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		if($scope.formData.id == undefined){
			$scope.formData ={};
			
			$scope.hide_code_existing_er = true;
			
		}else{
			var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
			edit(row_data[0],cur_row_index);
		}
		clearform();
	});
	
	$rootScope.$on("fun_enable_inputs",function(){
		$scope.disable_all = false;
	});
	
	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});
	
	$rootScope.$on("fun_clear_form",function(){
		$scope.formData = {};
	});
	
	
	//Manupulate Formdata when Edit mode - Prev-Next feature add
	$rootScope.$on("fun_next_rowData",function(e,id){
		
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(current_row_index == 0){
			$rootScope.$emit("enable_prev_btn");
		}
		var next_row_index = current_row_index+1;
		if(row_data[next_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(next_row_index).data();
			edit(selcted_row_data[0],next_row_index);
			$rootScope.$emit("next_formdata_set",next_row_index);
		}
		if(row_count-1 == current_row_index+1){
			$rootScope.$emit("disable_next_btn");
		}
	});
	
	$rootScope.$on("fun_prev_rowData",function(e,id){
		var row_count = vm.dtInstance.DataTable.page.info().recordsDisplay;
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(row_count-1 == current_row_index){
			$rootScope.$emit("enable_next_btn");
		}
		var prev_row_index = current_row_index-1;
		if(row_data[prev_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(prev_row_index).data();
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
		 for (i=0;i<row_data.length;i++){
			 angular.forEach(row_data[i], function(value,key) {
				 if(key == "code"){
					if(data.code == row_data[i].code){
						flg = false;
						$scope.hide_code_existing_er = false;
						$scope.existing_code = '"'+row_data[i].code+'" Existing';
					}
				 }
				});
		 }
		}
			 
		if(validation() == false){
			flg = false;
		}
			
		return flg;
	}
}

angular.bootstrap(document.getElementById("uom_app"), ['uom_app']);




