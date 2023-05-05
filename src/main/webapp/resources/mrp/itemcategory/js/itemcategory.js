
//Controller for Table and Form 
mrpApp.controller('item_category', item_category);
function item_category($compile,$controller,$scope,$interval,$timeout,$http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {

	$controller('DatatableController', {$scope: $scope});
	
/*	set_sub_menu("#settings");						
	setMenuSelected("#item_category_left_menu");*/			//active leftmenu
	manageButtons("add");
	
	
//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			
			$scope.formData.code = response;});}
	
	$scope.formData = {};
	$scope.show_table=true;
	$scope.show_form=false;
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
	$scope.hide_code_existing_er = true;
	$scope.ItemCategoryDetailsList =[];
	 $scope.parnetItem =[{ id : 0 , name : "select"}];
	 $scope.ItemCategoryDetailsList = [];
	    $http({url : 'json',method : "GET",
		}).then(function(response) {
			$scope.ItemCategoryDetailsList = response.data.data;
			for(var i=0;i<$scope.ItemCategoryDetailsList.length;i++){
				$scope.parnetItem.push($scope.ItemCategoryDetailsList[i]);
			}
		}, function(response) { // optional
			
		});

	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.formValidation = formValidation;
	vm.fun_reload_parentIdDDl_EDIT = fun_reload_parentIdDDl_EDIT;
	vm.loadItemDetailsTable = loadItemDetailsTable;
	vm.getItemCategoryName = getItemCategoryName;

	vm.dtInstance = {};	
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 				
	vm.dtColumns = [
	    			DTColumnBuilder.newColumn('code').withOption('width', '180px').withTitle('CODE').withOption('type', 'natural').renderWith(
					           function(data, type, full, meta) {
			               			if(full.quetableId!=""  && full.quetableId!=undefined){css="queTableInColor"}else{css="queTableOutColor"}

						              return urlFormater(data);  
					            }),
	    			DTColumnBuilder.newColumn('name').withTitle('NAME'),
	    			DTColumnBuilder.newColumn('description').withTitle('DESCRIPTION').withOption('width','300px'),
	    			DTColumnBuilder.newColumn('itemcategory_name').withOption('width', '150px').withTitle('PARENT ITEM'),
	    			DTColumnBuilder.newColumn('parent_id').withOption('width', '150px').withTitle('PARENT ITEM').notVisible(),
	    		 ];
   
   function urlFormater(data) {
	  return '<a id="rcd_edit" class="'+css+'"  ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
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
	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
	    var pageInfo = api.page.info();
	    if(pageInfo.pages == 0){
	    	return pageInfo.page +" / "+pageInfo.pages;
	    }else{
	    	return pageInfo.page+1 +" / "+pageInfo.pages;
	    }
	}
	
  function getItemCategoryName(itemctId){
		var itemName = "";
		for(var i=0;i<$scope.ItemCategoryDetailsList.length;i++){
			if(itemctId == $scope.ItemCategoryDetailsList[i].id){
				itemName = $scope.ItemCategoryDetailsList[i].name;
			}
		}
		return itemName;
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
	function loadItemDetailsTable(){
		 $scope.parnetItem =[{id : 0 ,name : "select",}];
		  var ItemCatDtlList = [];
		    $http({url : 'json',method : "GET",}).then(function(response) {
				ItemCatDtlList = response.data.data;
				$scope.ItemCategoryDetailsList = ItemCatDtlList;
				for(var i=0;i<$scope.ItemCategoryDetailsList.length;i++){
					$scope.parnetItem.push($scope.ItemCategoryDetailsList[i]);
				}
			}, function(response) { // optional
			});
	}
	function reloadData() {
        vm.dtInstance.reloadData(null, true);
    }
	function fun_reload_parentIdDDl_EDIT(cur_id){
		 $scope.parnetItem =[{id : 0 ,name : "select"}];
		for(var i=0;i<$scope.ItemCategoryDetailsList.length;i++){
			if(cur_id != $scope.ItemCategoryDetailsList[i].id){
				$scope.parnetItem.push($scope.ItemCategoryDetailsList[i]);
			}
		}
	}
	function edit(row_data,cur_row_index,event) {		
		fun_reload_parentIdDDl_EDIT(row_data.id);
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
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,
				description:row_data.description,parent_id:row_data.parent_id,
				created_at:row_data.created_at,created_by:row_data.created_by};
		if(row_data.parent_id == 0){
			$scope.formData.parent_id = $scope.parnetItem[0].id;
		}
		$scope.disable_all = true;
		$scope.disable_code = true;
		$scope.hide_code_existing_er = true;
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
		}}
		).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).ok(
						'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			$http({url : 'delete',method : "POST",
				params : {id:$scope.formData.id},
			}).then(function(response) {
				if(response.data==0)
				{
					$rootScope.$broadcast('on_AlertMessage_ERR',"Item Category "+FORM_MESSAGES.ALREADY_USE+"");
				}else if(response.data == 1){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
					$("#show_form").val(0);
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
					$scope.disable_all = true;
					$scope.disable_code = true;
					vm.dtInstance.reloadData(null, true);
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
		if (formValidation($scope.formData)) {
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
		 if($scope.formData.id!="" && $scope.formData.id!=undefined){
				$scope.formData.updated_at=today.getFullYear() + "-" + d2(parseInt(today.getMonth()+1)) + "-" + d2(today.getDate()) + " " + d2(today.getHours()) + ":" + d2(today.getMinutes()) + ":" + d2(today.getSeconds());
				$scope.formData.updated_by=parseInt(strings['userID']);
				  }
			$http({url : 'saveItmCat',method : "POST",
				params : $scope.formData,
				data : {Quetable:$scope.Quetable},
			}).then(function(response) {
				if(response.data == 1){
				if ($scope.formData.id != undefined) {
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
					view_mode_aftr_edit();
				} else {
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
					$scope.formData = {};
					$scope.fun_get_pono();
					loadItemDetailsTable();
					$scope.formData.parent_id = $scope.parnetItem[0].id;
					vm.dtInstance.DataTable.page('last');
				}
				 reloadData();
				$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

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
		}}
		).title(FORM_MESSAGES.DISCARD_WRNG).targetEvent(event).ok(
						'Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
			var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			if($scope.formData.id == undefined){
				$scope.formData ={};
				$scope.fun_get_pono();
				$scope.formData.parent_id = $scope.parnetItem[0].id;
				$scope.hide_code_existing_er = true;
				$("#code").focus();
			}else{
				var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
				edit(row_data[0],cur_row_index);
				$("#name").focus();
			}
			clearform();
		}, function() {
			
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
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		$scope.formData = {};
		$scope.fun_get_pono();
		$scope.formData.parent_id = $scope.parnetItem[0].id;
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
		function formValidation(data) {
			var flg = true;
			if (!validation()) {
				flg = false;
			}
			if (flg == false) {
				focus();
			}
			if (data.id == undefined || data.id == "") {
				if (!$scope.hide_code_existing_er) {
					flg = false;
					$("#code").select();
				}
			}
			return flg;
	}
	

		
}





