
mrpApp.controller('sale_item_choices', sale_item_choices);

function sale_item_choices($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,
		         MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {
	
	
/* ##  extend DatatableController ##  */
$controller('DatatableController', {$scope: $scope});

/*$("#form_div_description").hide();*/

        /*set_sub_menu("#settings");						
        setMenuSelected("#sale_item_choices_left_menu");*/			//active leftmenu
        manageButtons("add");

//    	generate number
    /*	$scope.fun_get_pono=function(){
    		$http.get('getCounterPrefix')
    		.success(function (response) {
    			
    			$scope.formData.code = response;});}*/
        
		$scope.formData = {};
		$scope.nolimit=true;
		$scope.show_table=true;
		$scope.disable_max=true;
		$scope.show_form=false;
		$scope.hide_code_existing_er = true;
		$scope.Quetable={};
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
		$scope.choiceItem=[];
		$scope.saleItem=[];
		
		var vm = this;	 
		$scope.formData = {};
		$scope.show_table=true;
		$scope.show_form=false;
		$scope.hide_code_existing_er = true;
		vm.reloadData = reloadData;
		
		vm.showTable  = showTable;
		vm.view_mode_aftr_edit = view_mode_aftr_edit;
		vm.code_existing_validation = code_existing_validation;
		/*$scope.formData.is_compound=false;*/
		vm.dtInstance = {};	
		var DataObj = {};		
		DataObj.sourceUrl = "getDataTableData";
		DataObj.infoCallback = infoCallback;
		DataObj.rowCallback = rowCallback;
		vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 				
	    vm.dtColumns = [
			          DTColumnBuilder.newColumn('saleItemName').withOption('width','250px').withTitle('Sale Item Name').renderWith(
					           function(data, type, full, meta) {
			               			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}

						              return urlFormater(data);  
					            }),
			          DTColumnBuilder.newColumn('choiceItemName').withTitle('Choice Item Name').withOption('width','250px'),
			          DTColumnBuilder.newColumn('free_items').withTitle('Free Item Count').withOption('width','250px'),

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
		$("#show_form").val(0);
		$scope.show_table=true;
		$scope.show_form=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
	});
	
	function showTable(event){
		$scope.show_table=false;
		$scope.show_form=true;
	}
	
	
	$http({
		method: 'GET',
		url : "getDropDownData"
	}).success(function (result) {
		
		$scope.choiceItem = result.choiceItem;
		$scope.saleItem=result.saleItem;
		
		/*$scope.saleItem.splice(0,0,{id : "" ,name : "select"});*/
		/*$scope.choiceItem.splice(0,0,{id : "" ,name : "select"});*/
		
		
		
	});
	
	
	$scope.isCompoundSelected=function()
	{
		$scope.formData.compound_unit="";
		$scope.formData.base_uom_id="";
		
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
		
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		
		//get data to edit
		
		
		 $http({
				url : 'editData',
				method : "GET",
				params : {id:row_data.id},
			}).then(function(response) {
		    
			$scope.formData = {id:response.data.editDet.id,sale_item_id:response.data.editDet.sale_item_id,
					choice_id:response.data.editDet.choice_id,free_items:response.data.editDet.free_items,
					max_items:response.data.editDet.max_items};
			setSaleAndChoiceData($scope.formData.sale_item_id,$scope.formData.choice_id);
			$scope.createdBy = response.data.editDet.created_by;
			$scope.createdAt =  response.data.editDet.created_at;
			});
		
		$scope.disable_max=true;
		$scope.disable_all = true;
		$scope.disable_code = true;
		$(".acontainer input").attr('disabled', true);
		$("#form_div_choice_item_code").removeClass("has-error");
		$("#form_div_choice_item_code_error").hide();
		$("#form_div_sale_item_code").removeClass("has-error");
		$("#form_div_sale_item_code_error").hide();
	}

	function setSaleAndChoiceData(saleItmId,choceId)
	{
		for(var i=0;i<$scope.saleItem.length;i++)
			{
			if($scope.saleItem[i].id == saleItmId)
				{
				$scope.formData.sale_item_code=$scope.saleItem[i].code;
				$("#form_div_sale_item_code").find(".acontainer input").val($scope.formData.sale_item_code);
				$scope.formData.sale_item_name=$scope.saleItem[i].name;
				break;
				}
			}
		for(var i=0;i<$scope.choiceItem.length;i++)
			{
			if($scope.choiceItem[i].id == choceId)
			{
			$scope.formData.choice_item_code=$scope.choiceItem[i].code;
			$("#form_div_choice_item_code").find(".acontainer input").val($scope.formData.choice_item_code);
			$scope.formData.choice_item_name=$scope.choiceItem[i].name;
			break;
			}
			}
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
					
					$rootScope.$broadcast('on_AlertMessage_ERR','Uom'+FORM_MESSAGES.ALREADY_USE);

				}else if(response.data==1){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);

					$("#show_form").val(0);
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
					$scope.disable_max=true;
					$scope.disable_all = true;
					$scope.disable_code = true;
					vm.dtInstance.reloadData(null, true);
					vm.dtInstance1.reloadData(null, true);
				}else
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
		$scope.disable_max=true;
		$(".acontainer input").attr('disabled', true);
	}
	
	$rootScope.$on('fun_save_data',function(event){		//Save Function
		if (code_existing_validation($scope.formData)) {
			$scope.formData.created_at = $scope.createdAt;
			  $scope.formData.created_by =  $scope.createdBy;
			
				
				$scope.Quetable.shopId=settings['currentcompanyid1'];
				$scope.Quetable.origin=settings['currentcompanycode1'];
			$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
			$http({
				url : 'saveSaleItemChoice',
				method : "POST",
				params : $scope.formData,
				data : {Quetable:$scope.Quetable},
			}).then(function(response) {
				
				if(response.data == 1){
				if ($scope.formData.id != undefined && $scope.formData.id!="" ) {
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);

					view_mode_aftr_edit();
					
				} else {
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
					$scope.formData = {};
					/*$scope.fun_get_pono();*/
					$scope.formData = {id:"",sale_item_id:"",choice_id:"",free_items:"",max_items:-1};
					$scope.nolimit=true;
					$scope.disable_max=true;
					 $(".acontainer").find('input').val('');
					
				}
				 reloadData();
					$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

				 $scope.hide_code_existing_er = true;
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
	
	$rootScope.$on("fun_discard_form",function(event){		
		
		//Discard function
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
		if($scope.formData.id == undefined || $scope.formData.id ==""){
			$scope.formData = {id:"",sale_item_id:"",choice_id:"",free_items:"",max_items:-1};
			/*$scope.fun_get_pono();*/
			$scope.hide_code_existing_er = true;
			$(".acontainer").find('input').val('');
		}else{
			var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
			edit(row_data[0],cur_row_index);
		}
		clearform();
		$("#form_div_choice_item_code").removeClass("has-error");
		$("#form_div_choice_item_code_error").hide();
		$("#form_div_sale_item_code").removeClass("has-error");
		$("#form_div_sale_item_code_error").hide();
		}
		/*}*/);
	});
	
	$rootScope.$on("fun_enable_inputs",function(){
		$("#show_form").val(1);
		$scope.disable_all = false;
		$(".acontainer input").attr('disabled', false);
	});
	
	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});
	
	
	
	
	
	
	$rootScope.$on("fun_clear_form",function(){
		$scope.hide_code_existing_er = true;
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		$scope.formData = {id:"",sale_item_id:"",choice_id:"",free_items:"",max_items:-1};
		$scope.nolimit=true;
/*		$scope.fun_get_pono();
*/	
		
		$("#form_div_choice_item_code").removeClass("has-error");
		$("#form_div_choice_item_code_error").hide();
		$("#form_div_sale_item_code").removeClass("has-error");
		$("#form_div_sale_item_code_error").hide();
		
		 $(".acontainer").find('input').val('');
		 $("#sale_item_name").val('');
         $("#choice_item_name").val('');
	     
	});
	
$scope.clearMax=function()
{
	if($scope.nolimit==true)
		{
		$scope.formData.max_items="";
		$scope.disable_max=false;
		}
	else
		{
		$scope.formData.max_items=-1;
		$scope.disable_max=true;
		}
	
	
	}
	
	
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
	
	function code_existing_validation(data){
		/*var flg_code=0;*/
		var flg = true;
		/*if(data.id == undefined || data.id == ""){
		
			
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#code").select();
			}
		}*/
		
		if($scope.formData.sale_item_id=="")
		{
			$("#form_div_sale_item_code").addClass("has-error");
		    $("#form_div_sale_item_code_error").show();
		   flg = false;
	}else{
		
		$("#form_div_sale_item_code").removeClass("has-error");
		$("#form_div_sale_item_code_error").hide();
	}
		
		

		if($scope.formData.choice_id=="")
			{
			$("#form_div_choice_item_code").addClass("has-error");
		    $("#form_div_choice_item_code_error").show();
		   flg = false;
	}else{
		
		$("#form_div_choice_item_code").removeClass("has-error");
		$("#form_div_choice_item_code_error").hide();
		
	}
		
		if(validation() == false){
			flg = false;
		}
	
		
		
		if(flg==false)
		{
			focus();
		}
		return flg;
	}
	
	//autocompelete sale item 
	var saleItemData = $("#sale_item_code").tautocomplete({
		columns: ['id','code','name'],
		hide: [true,true,true],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay : 10,
		onchange: function () {
			var selected_saleitem_data =  saleItemData.all();
			$scope.$apply(function(){
				$scope.formData.sale_item_id =  selected_saleitem_data.id;
				$scope.formData.sale_item_code =  selected_saleitem_data.code;
				$scope.formData.sale_item_name =  selected_saleitem_data.name;
			});},
			data: function () {
				var data = $scope.saleItem;
				var filterData = [];
				var searchData = eval("/^" + saleItemData.searchdata() + "/gi");
				$.each(data, function (i, v) {
					if (v.name.search(new RegExp(searchData)) != -1) {
						filterData.push(v);}});
				return filterData;}});
	
	//autocompelete choice item 
	var choiceItemData = $("#choice_item_code").tautocomplete({
		columns: ['id','code','name'],
		hide: [true,true,true],
		placeholder: "search..",
		highlight: "hightlight-classname",
		norecord: "No Records Found",
		delay : 10,
		onchange: function () {
			var selected_choiceitem_data =  choiceItemData.all();
			$scope.$apply(function(){
				$scope.formData.choice_id   =  selected_choiceitem_data .id;
				$scope.formData.choice_item_code =  selected_choiceitem_data.code;
				$scope.formData.choice_item_name =  selected_choiceitem_data.name;
			});},
			data: function () {
				var data = $scope.choiceItem;
				var filterData = [];
				var searchData = eval("/^" + choiceItemData.searchdata() + "/gi");
				$.each(data, function (i, v) {
					if (v.name.search(new RegExp(searchData)) != -1) {
						filterData.push(v);}});
				return filterData;}});
	
	$(document).on('keyup','#form_div_sale_item_code input',function(e){
		
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$('#sale_item_code').val("");
				$scope.formData.sale_item_code=  "";
				$scope.formData.sale_item_name =  "";
				$scope.formData.sale_item_id = "";});}});
	
	$(document).on('keyup','#form_div_choice_item_code input',function(e){
		
		if(e.which != 9 && e.which != 13){
			$scope.$apply(function(){
				$('#choice_item_code').val("");
				$scope.formData.choice_item_code=  "";
				$scope.formData.choice_item_name =  "";
				$scope.formData.choice_id = "";});}});
};








