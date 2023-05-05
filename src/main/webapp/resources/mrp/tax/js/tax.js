
//Controller for Table and Form 
mrpApp.controller('taxctrl', taxctrl);


function taxctrl($compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder,$controller,MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS) {
	$controller('DatatableController', {$scope: $scope});
	$scope.formData = {};
	$scope.show_table=true;
	$scope.show_form=false;
 $scope.tax1_name="";
 $scope.tax2_name="";
 $scope.tax3_name="";
 $scope.formData.is_tax1_applicable=false;
 $scope.formData.is_tax2_applicable=false;
 $scope.formData.is_tax3_applicable=false;
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	

	$scope.hide_code_existing_er = true;
	/*set_sub_menu("#settings");						
	setMenuSelected("#tax_left_menu");	*/		//active leftmenu
	manageButtons("add");
	
	var vm = this;
	vm.code_existing_validation = code_existing_validation;
	vm.dtInstance = {};
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	DataObj.order="desc";
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	vm.dtColumns = [
	                DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
	                DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').renderWith(
	                		function(data, type, full, meta) {
		               			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}

	                			return '<a id="rcd_edit" class="'+css+'" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
	                			+ data + '</a>';}),
	                			DTColumnBuilder.newColumn('name').withTitle('NAME'),
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
					var current_row_index = vm.dtInstance.DataTable.row(".selected").index();
					var current_row_index = 0;
					var test = vm.dtInstance.DataTable.rows();
					for(var i = 0;i<test[0].length;i++){
						if(test[0][i] == vm.dtInstance.DataTable.row(".selected").index()){
							current_row_index = i;}}
					
					$scope.edit(rowData,current_row_index,e);
					$("#show_form").val(1);
				} });});return nRow;}

	function infoCallback(settings, start, end, max, total, pre){    //function for get  page Info
		var api = this.api();
		var pageInfo = api.page.info();
		if(pageInfo.pages == 0){
			return pageInfo.page +" / "+pageInfo.pages;
		}else{
			return pageInfo.page+1 +" / "+pageInfo.pages;
		}}


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
	
	$scope.isTax1Applicable=function()
	{
		
			$scope.formData.tax1_percentage="";
	}
	$scope.isTax2Applicable=function()
	{
		
			$scope.formData.tax2_percentage="";
	}
	$scope.isTax3Applicable=function()
	{
		
			$scope.formData.tax3_percentage="";
	}
	
		
		
		
	
	

	$scope.showTable=function(event){
		$scope.show_table=false;
		$scope.show_form=true;
	}
//	generate number
	$scope.fun_get_pono=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			
			$scope.formData.code = response;});}

	$scope.reloadData=function() {
		vm.dtInstance.reloadData(null, true);
	}
	
	$scope.fun_get_taxParam=function()
	{
		$http({
			url : '../taxparam/json',
			method : "GET",
		}).then(function(response) {
			$scope.taxparamData=response.data.data;
			$scope.tax1_name=$scope.taxparamData[0].tax1_name;
			 $scope.tax2_name=$scope.taxparamData[0].tax2_name;
			 $scope.tax3_name=$scope.taxparamData[0].tax3_name;
			if($scope.taxparamData[0].tax1_name=="")
				{
				$scope.tax1hide=true;
				 $scope.formData.is_tax1_applicable="";
				 $scope.formData.tax1_percentage="";
				}
			if($scope.taxparamData[0].tax2_name=="")
			{
				$scope.tax2hide=true;
				 $scope.formData.is_tax2_applicable="";
				 $scope.formData.tax2_percentage="";

			}
			if($scope.taxparamData[0].tax3_name=="")
			{
				$scope.tax3hide=true;
				 $scope.formData.is_tax3_applicable="";
				 $scope.formData.tax3_percentage="";
			}
			
		});
	}

	$scope.edit=function(row_data,cur_row_index,event) {									
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
				$rootScope.$emit("enable_prev_btn");}}
		$scope.showTable();
		clearform();
		 $("#tax1_percentage").css("border-color","#d2d6de");
		 $("#tax2_percentage").css("border-color","#d2d6de");
		 $("#tax3_percentage").css("border-color","#d2d6de");
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		
		$http({
			url : 'editData',
			method : "GET",
			params : {id:row_data.id},
		}).then(function(response) {
	    
		$scope.formData = {id:response.data.editDet.id,code:response.data.editDet.code,description:response.data.editDet.description,name:response.data.editDet.name,tax1_percentage:response.data.editDet.tax1_percentage,tax2_percentage:response.data.editDet.tax2_percentage,tax3_percentage:response.data.editDet.tax3_percentage,tax1_refund_rate:response.data.editDet.tax1_refund_rate,tax2_refund_rate:response.data.editDet.tax2_refund_rate,tax3_refund_rate:response.data.editDet.tax3_refund_rate};
		
		$scope.createdBy = response.data.editDet.created_by;
		$scope.createdAt =  response.data.editDet.created_at;

	 $scope.formData.is_tax1_applicable= (response.data.editDet.is_tax1_applicable == 1) ? true:false;
	 $scope.formData.is_tax2_applicable= (response.data.editDet.is_tax2_applicable == 1) ? true:false;
	 $scope.formData.is_tax3_applicable= (response.data.editDet.is_tax3_applicable == 1) ? true:false;
	 $scope.fun_get_taxParam();
		});
/*		$scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,description:row_data.description,tax_indicator:row_data.tax_indicator};
*/		
		$scope.disable_all = true;
		$scope.disable_code = true;
		$scope.showTable();
		clearform();		
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
		}}).title(FORM_MESSAGES.DELETE_WRNG).targetEvent(event).ok('Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
			var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
			$http({
				url : 'delete',
				method : "POST",
				params : {id:$scope.formData.id},
			}).then(function(response) {

				if(response.data==0)
				{$rootScope.$broadcast('on_AlertMessage_SUCC',"Tax in Use");

				}else{					
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);

					$("#show_form").val(0);
					$scope.reloadData();
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
					$scope.disable_all = true;
					$scope.disable_code = true;}});});});


	$scope.view_mode_aftr_edit=function(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$scope.disable_code = true;}

//	Save Function
	$rootScope.$on('fun_save_data',function(event){	
		if (code_existing_validation($scope.formData))
			{
			
			
		$scope.formData.tax3_refund_rate=$scope.formData.tax3_percentage;
		$scope.formData.tax2_refund_rate=$scope.formData.tax2_percentage;
		$scope.formData.tax1_refund_rate=$scope.formData.tax1_percentage;
		$scope.formData.created_at = $scope.createdAt;
		  $scope.formData.created_by =  $scope.createdBy;
		  $scope.formData.is_tax1_applicable= ($scope.formData.is_tax1_applicable == true) ? 1:0;
		  $scope.formData.is_tax2_applicable= ($scope.formData.is_tax2_applicable == true) ? 1:0;
		  $scope.formData.is_tax3_applicable= ($scope.formData.is_tax3_applicable == true) ? 1:0;
		  $scope.formData.is_sc_applicable=0;
		  if($scope.formData.tax3_refund_rate=="" || $scope.formData.tax3_refund_rate==undefined)
			  {
			  $scope.formData.tax3_refund_rate=0.00;
			  }
		  if($scope.formData.tax2_refund_rate=="" || $scope.formData.tax2_refund_rate==undefined)
		  {
		  $scope.formData.tax2_refund_rate=0.00;
		  }
		  if($scope.formData.tax1_refund_rate=="" || $scope.formData.tax1_refund_rate==undefined)
		  {
		  $scope.formData.tax1_refund_rate=0.00;
		  }
		  
		  
		  $scope.Quetable.shopId=settings['currentcompanyid1'];
			$scope.Quetable.origin=settings['currentcompanycode1'];
		$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
		  
			$http({
				url : 'saveTax',
				method : "POST",
				params : $scope.formData,
				data : {Quetable:$scope.Quetable},
			}).then(function(response) {

				if($scope.formData.id !=undefined)
				{$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
				$scope.view_mode_aftr_edit();
				 $scope.formData.is_tax1_applicable= ($scope.formData.is_tax1_applicable == 1) ? true:false;
				  $scope.formData.is_tax2_applicable= ($scope.formData.is_tax2_applicable == 1) ? true:false;
				  $scope.formData.is_tax3_applicable= ($scope.formData.is_tax3_applicable == 1) ? true:false;
				}
				else
				{$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
				var date =get_date_format();
				$scope.formData ={};
				$scope.fun_get_pono();
				$scope.fun_get_taxParam();
				}
				$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
				$scope.reloadData();
				$scope.hide_code_existing_er = true;
			});
			}
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
		/*		$scope.show_table=true;
				$scope.show_form=false;
				manageButtons("add");*/
				$scope.formData ={};
				$scope.fun_get_pono();
				$scope.fun_get_taxParam();
				$scope.hide_code_existing_er = true;
				$scope.clearform();
			}else{
				var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
				var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
				$scope.edit(row_data[0],cur_row_index);				
				clearform();
				}
			});
		});

	
	 $scope.clearform=function() {
		$(".required").each(function(){
			var filedId = $(this).attr("id");
			$("#form_div_"+filedId+"").removeClass("has-error");
			$("#form_div_"+filedId+"_error").hide();
			$scope.formData.is_tax1_applicable=false;
			 $scope.formData.is_tax2_applicable=false;
			 $scope.formData.is_tax3_applicable=false;
			

		});
	
	}
	
	$rootScope.$on("fun_enable_inputs",function(){
		$("#show_form").val(1);
		$scope.Dateerror=false;
		$scope.disable_all = false;});
	


	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;});

	$rootScope.$on("fun_clear_form",function(){
		 $("#tax1_percentage").css("border-color","#d2d6de");
		 $("#tax2_percentage").css("border-color","#d2d6de");
		 $("#tax3_percentage").css("border-color","#d2d6de");
		$scope.Dateerror=false;
		$scope.hide_code_existing_er = true;
		$scope.formData = {};
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		$scope.fun_get_pono();
		$scope.fun_get_taxParam();
		var date =get_date_format();
		$scope.formData.is_tax1_applicable=false;
		 $scope.formData.is_tax2_applicable=false;
		 $scope.formData.is_tax3_applicable=false;

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
		var row_data = vm.dtInstance.DataTable.rows().data();
		var current_row_index = parseInt(id.split("_")[1]);
		if(row_count-1 == current_row_index){
			$rootScope.$emit("enable_next_btn");
		}
		var prev_row_index = current_row_index-1;
		if(row_data[prev_row_index] != undefined){
			var selcted_row_data = vm.dtInstance.DataTable.rows(prev_row_index).data();
			$scope.edit(selcted_row_data[0],prev_row_index);
			$rootScope.$emit("next_formdata_set",prev_row_index);
		}
		if(current_row_index-1 == 0){
			$rootScope.$emit("disable_prev_btn");
		}});

/*//	date validation
	$scope.commonDateError=function(date,index){

		date=getMysqlFormat(date);
		
	for(i=0;i<$scope.taxdata.items.length;i++){
		valid_from=getMysqlFormat($scope.taxdata.items[i].valid_from);
		if( i!=index && new Date(date).getFullYear() < (new Date(valid_from).getFullYear())){
				$scope.Dateerror=true;				
					$("#valid_from_"+index).select();
					$rootScope.$broadcast('on_AlertMessage_ERR',"Date not valid.");				
				break;				
			}
		}


	

		
		$scope.datevalid = $scope.taxdata.items.map(function(item){ return item.valid_from });
		$scope.Dateerror = $scope.datevalid.some(function(item, idx){ 
			return $scope.datevalid.indexOf(item) != idx   });
		if($scope.Dateerror==true){
			
			$("#valid_from_"+index).select();
			$rootScope.$broadcast('on_AlertMessage_ERR',"Date cannot be same.");
		}}*/





//			Validation 
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
		 $("#tax1_percentage").css("border-color","#d2d6de");
		 $("#tax2_percentage").css("border-color","#d2d6de");
		 $("#tax3_percentage").css("border-color","#d2d6de");
		if($scope.formData.is_tax1_applicable==true)
			{
			
			if($scope.formData.tax1_percentage=="")
				{
				 $("#tax1_percentage").css("border-color","red");
				 flg = false;
				}
			}
		if($scope.formData.is_tax2_applicable==true)
		{
		
		if($scope.formData.tax2_percentage=="")
			{
			 $("#tax2_percentage").css("border-color","red");
			 flg = false;
			}
		}
		
		if($scope.formData.is_tax3_applicable==true)
		{
		
		if($scope.formData.tax3_percentage=="")
			{
			 $("#tax3_percentage").css("border-color","red");
			 flg = false;
			}
		}
						
		if(flg==false)
		{
			focus();
			} 
			
		return flg;		
						
			}
			
 };








