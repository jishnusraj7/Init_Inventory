
mrpApp.controller('area_code', area_code);

function area_code($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,
		         MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES,FORM_MESSAGES,ITEM_TABLE_MESSAGES) {
	
	
/* ##  extend DatatableController ##  */
$controller('DatatableController', {$scope: $scope});

/*$("#form_div_description").hide();*/

      /*  set_sub_menu("#settings");						
        setMenuSelected("#area_code_left_menu");*/			//active leftmenu
        manageButtons("add");

    	$scope.itemsData = [];
    	$http({
    		url : '../itemmaster/json',
    		method : "GET",
    	}).then(function(response) {$scope.itemsData = response.data.data;});

    	
    	
        
        
        
//    	generate number
    	$scope.fun_get_pono=function(){
    		$http.get('getCounterPrefix')
    		.success(function (response) {
    			
    			$scope.formData.code = response;});}
        
		$scope.formData = {};
		
		$scope.show_table=true;
		$scope.show_form=false;
		$scope.hide_code_existing_er = true;
		$scope.Quetable={};
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
		$scope.pricesettings = {items: []};
		$scope.pricesettings.items.push({
			id:0,
			
			stock_item_id:'',
			stock_item_code:'',
			stock_item_name:'',
			qty:0,});
			
			
		
		var vm = this;	 
		$scope.formData = {};
		$scope.show_table=true;
		$scope.show_form=false;
		$scope.hide_code_existing_er = true;
		vm.reloadData = reloadData;
		
		vm.showTable  = showTable;
		vm.view_mode_aftr_edit = view_mode_aftr_edit;
		vm.code_existing_validation = code_existing_validation;
		$scope.formData.is_compound=false;
		vm.dtInstance = {};	
		var DataObj = {};		
		DataObj.sourceUrl = "getDataTableData";
		DataObj.infoCallback = infoCallback;
		DataObj.rowCallback = rowCallback;
		vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 				
	    vm.dtColumns = [
			          DTColumnBuilder.newColumn('code').withOption('width','250px').withTitle('CODE').renderWith(
					           function(data, type, full, meta) {
			               			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}

						              return urlFormater(data);  
					            }),
			          DTColumnBuilder.newColumn('name').withTitle('NAME').withOption('width','250px'),
			         DTColumnBuilder.newColumn('description').withTitle('DESCRIPTION').withOption('width','400px'),
			         DTColumnBuilder.newColumn('uom_id').withTitle('').notVisible().withOption('searchable','false'),
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

	
	
	$http({
		method: 'GET',
		url : "getDropDownData"
	}).success(function (result) {
		$scope.filluom=result.uom;
		$scope.filluom.splice(0,0,{id : "" ,name : "select"});
	}); 
	
	
	
	
	
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
		$scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,uom_id:row_data.uom_id,
				description:row_data.description}
		
		$scope.createdBy = row_data.created_by;
		$scope.createdAt =  row_data.created_at;
		$http({
			url : 'editData',
			method : "GET",
			params : {id:row_data.id},
		}).then(function(response) {
			$scope.pricesettings.items=response.data.data;

			for(var i = 0;i<$scope.pricesettings.items.length;i++){
				
				$scope.pricesettings.items[i].price_diff=parseFloat($scope.pricesettings.items[i].price_diff).toFixed(settings['decimalPlace']);
				$scope.pricesettings.items[i].qty=parseFloat($scope.pricesettings.items[i].qty).toFixed(settings['decimalPlace']);
				$scope.pricesettings.items[i].is_default=($scope.pricesettings.items[i].is_default==1)?true:false;
				}
			
		
		});
		$scope.disable_all = true;
		$scope.disable_code = true;
		$("#form_div_base_uom_id").removeClass("has-error");
		$("#form_div_base_uom_id_error").hide();
		$("#form_div_compound_unit").removeClass("has-error");
		$("#form_div_compound_unit_error").hide();
		$(".acontainer input").attr('disabled', true);
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
					
					$rootScope.$broadcast('on_AlertMessage_ERR','Area Code '+FORM_MESSAGES.ALREADY_USE);

				}else if(response.data==1){
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);

					$("#show_form").val(0);
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
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
	}
	
	$rootScope.$on('fun_save_data',function(event){		//Save Function
		if (code_existing_validation($scope.formData)) {
		
			$scope.ComboContentsList = [];
			for(var i = 0;i<$scope.pricesettings.items.length;i++){
				
				
				$scope.pricesettings.items[i].is_default=($scope.pricesettings.items[i].is_default==true)?1:0;
				$scope.ComboContentsList.push($scope.pricesettings.items[i]);}
				
			$scope.formData.ComboContentsList=JSON.stringify($scope.ComboContentsList);
				
				
			
			$scope.formData.created_at = $scope.createdAt;
			  $scope.formData.created_by =  $scope.createdBy;
			  var id= ($scope.formData.is_compound == true) ? 1:0;
				$scope.formData.is_compound=id;
				$scope.Quetable.shopId=settings['currentcompanyid1'];
				$scope.Quetable.origin=settings['currentcompanycode1'];
			$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
			$http({
				url : 'saveComBo',
				method : "POST",
				params : $scope.formData,
				data : {Quetable:$scope.Quetable,pricesettings:JSON.stringify($scope.ComboContentsList)},
			}).then(function(response) {
				
				if(response.data == 1){
				if ($scope.formData.id != undefined) {
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);

					for(var i = 0;i<$scope.pricesettings.items.length;i++){
						
						
						$scope.pricesettings.items[i].is_default=($scope.pricesettings.items[i].is_default==1)?true:false;
						}
					
					
					view_mode_aftr_edit();
				} else {
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
					$scope.formData = {};
					$scope.pricesettings = {items: []};
					$scope.pricesettings.items.push({
						substitution_sale_item_id:0,
						combo_content_id:0,
						stock_item_code:'',
						price_diff:'',
						qty:0,
						});
					
					
					$scope.fun_get_pono();
					
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
		if($scope.formData.id == undefined){
		
			$scope.formData ={};
			$scope.formData.uom_id=$scope.filluom[0].id;
			$scope.fun_get_pono();
		
			$scope.pricesettings = {items: []};
			$scope.pricesettings.items.push({
				id:0,
				
				stock_item_id:'',
				stock_item_code:'',
				stock_item_name:'',
				qty:0,});
			
			$scope.fun_get_pono();
			
			$scope.hide_code_existing_er = true;
		}else{
			var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
			edit(row_data[0],cur_row_index);
		}
		clearform();}
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
		$scope.formData = {};
		$scope.formData.uom_id=$scope.filluom[0].id;
		$scope.fun_get_pono();
	
		$scope.pricesettings = {items: []};
		$scope.pricesettings.items.push({
			id:0,
			
			stock_item_id:'',
			stock_item_code:'',
			stock_item_name:'',
			qty:0,});
			
		$scope.formData.is_compound=false;
		$("#form_div_base_uom_id").removeClass("has-error");
		$("#form_div_base_uom_id_error").hide();
		$("#form_div_compound_unit").removeClass("has-error");
		$("#form_div_compound_unit_error").hide();
	});
	
	
//	add items to stock item table	
	$scope.addItem = function(index) {
		$scope.pricesettings.items.push({
			id:0,
			
			stock_item_id:'',
			stock_item_code:'',
			stock_item_name:'',
			qty:0,
		});
		var date =get_date_format();
		if($scope.disable_all==false ){
			if(index<$scope.pricesettings.items.length-1){
    			$("#items_table tr:nth-child("+(index+3)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
    		}else{
			if($scope.pricesettings.items.length != 0){
				
				
				
				
			}else{
										$scope.pricesettings.items.push({
											id:0,
											
											stock_item_id:'',
											stock_item_code:'',
											stock_item_name:'',
											qty:0,
										});}}}};
										
										
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
													
													
													if($scope.pricesettings.items.length!=1){
													$scope.pricesettings.items.splice(index, 1);$("#items_table tr:nth-child("+($scope.pricesettings.items.length+1)+")").find("#itemqty").select();	}
												else{
													$rootScope.$broadcast('on_AlertMessage_ERR',"Atleast one item is required.");
												}});}};
												
												

	
	
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
		var flg_code=0;
		var flg = true;
		if(data.id == undefined || data.id == ""){
		
			
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#code").select();
			}
		}
		
		if(validation() == false){
			flg = false;
		}
		
		else if($scope.pricesettings.items.length > 0){
			var count=0;
			for(var i = 0;i<$scope.pricesettings.items.length;i++){

				if($scope.pricesettings.items[i].substitution_sale_item_id != undefined && $scope.pricesettings.items[i].substitution_sale_item_id != "" ){
					count=0;
					if($scope.pricesettings.items[i].price_diff == undefined || $scope.pricesettings.items[i].price_diff == "" || $scope.pricesettings.items[i].price_diff <= 0){
						$rootScope.$broadcast('on_AlertMessage_ERR','price difference cannot be blank');

						$("#items_table tr:nth-child("+(i+2)+") td:nth-child("+(3)+")").find("#price_diff").select();
						flg = false;break;}
					
					
					else if($scope.pricesettings.items[i].qty == "" || $scope.pricesettings.items[i].qty <= 0){
						$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.QTY_ERR);

						$("#items_table tr:nth-child("+(i+2)+") td:nth-child("+(4)+")").find("#itemqty").select();
						flg = false;break;}

					}else{
						$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.ITEM_CODE_ERR);
						count =1;$("#items_table tr:nth-child("+(i+2)+") td:nth-child("+(2)+")").find(".acontainer input").focus();
					flg = false;break;}}}
	
		
		
		if(flg==false)
		{
			focus();
		}
		return flg;
	}
	
	
	
	$scope.alert_for_codeexisting = function(flg){
		if(flg == 1){$rootScope.$broadcast('on_AlertMessage_ERR',ITEM_TABLE_MESSAGES.CODE_EXISIT);}}

	$scope.elementid=function(elemenvalue){
		if($scope.disable_all==true){$(elemenvalue).attr("disabled", true);}}
};




mrpApp.directive('autocompeteText', ['$timeout',function($timeout) {
	return {controller: function ($scope,$http) {
    	$scope.currentIndex = 0;

		$("#items_table tbody tr td").keyup('input',function(e){
			$scope.currentIndex = e.currentTarget.parentElement.rowIndex-1;
			/*if(e.which==9 && e.which ==16){
				$("#items_table tr:nth-child("+($scope.pricesettings .items.length+1)+")").find("#itemqty").select();
			}
			
			else*/ 
			if(e.shiftKey && e.keyCode == 9){
				$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find(".acontainer input").focus();
				}else if(  e.which != 9 && e.which != 13  && e.which != 109 && e.which == 8 && !(e.shiftKey && e.keyCode == 9)){
				if(e.currentTarget.cellIndex == 1){
					$scope.$apply(function(){
     					

						$scope.clear_stock_details_editmode(e);});}} else if(e.which == 13 ){
			     			if(e.currentTarget.cellIndex == 1){
			     				if($scope.pricesettings .items[e.currentTarget.parentElement.rowIndex-1].stock_item_id!=""){
			     				{$("#items_table tr:nth-child("+(e.currentTarget.parentElement.rowIndex+1)+")").find("#price_diff").focus();}
			     		}}
						}else if(e.which == 9 ){
			     			if(e.currentTarget.cellIndex == 1){
			     				/*if($scope.pricesettings .items[$scope.pricesettings .items.length-1].stock_item_id==undefined || $scope.pricesettings .items[$scope.pricesettings .items.length-1].stock_item_id==""){*/
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
					columns: ['id' , 'code', 'name' ,'uomcode'],
					hide: [false,true,true,false],
					placeholder: "search ..",
					highlight: "hightlight-classname",
					norecord: "No Records Found",
					delay : 10,
					onchange: function () {
						var selected_item_data =  items.all();
						strl_scope.$apply(function(){var count =0;
						for(var i=0;i<strl_scope.pricesettings.items.length;i++){
							if(selected_item_data.id != ""){
		    					if(i != strl_scope.currentIndex){
							if(selected_item_data.id == strl_scope.pricesettings .items[i].stock_item_id){
								count=1;}}}}
						if(count != 1){
							strl_scope.pricesettings .items[strl_scope.currentIndex].substitution_sale_item_id = selected_item_data.id;
							strl_scope.pricesettings .items[strl_scope.currentIndex].stock_item_code = selected_item_data.code;
							strl_scope.pricesettings .items[strl_scope.currentIndex].stock_item_name = selected_item_data.name;
		    				 strl_scope.pricesettings .items[strl_scope.currentIndex].uomcode = selected_item_data.uomcode;
		    				 $timeout(function () {$("#items_table tr:nth-child("+(strl_scope.currentIndex+2)+")").find("#price_diff").focus();}, 1); 
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
				for(var i = 0;i<strl_scope.pricesettings .items.length;i++){
					if(strl_scope.pricesettings .items[i].combo_content_id!=undefined && strl_scope.pricesettings .items[i].combo_content_id!='' ){
						if(strl_scope.pricesettings.items[i].flag==0){
							elem[0].parentNode.lastChild.value = strl_scope.pricesettings .items[i].stock_item_code;
							strl_scope.elementid(elem[0].parentNode.lastChild);
							strl_scope.pricesettings .items[i].flag=1;break;}}}
				$timeout(function () { 	if(strl_scope.formData.request_by!=undefined){$("#items_table tr:nth-child("+(strl_scope.pricesettings .items.length+1)+") td:nth-child("+(2)+")").find(".acontainer input").select();
				 }else{$('#request_by').focus();} }, 1); 	
			}};}]);









