
mrpApp.controller('timeslotctrl', timeslotctrl);

function timeslotctrl($controller, $compile, $timeout, $scope, $http, $mdDialog,
		$rootScope, ITEM_TABLE_MESSAGES, $window, DTOptionsBuilder,
		DTColumnBuilder, MRP_CONSTANT, DATATABLE_CONSTANT, FORM_MESSAGES,$filter) {
	
	
/* ##  extend DatatableController ##  */
$controller('DatatableController', {$scope: $scope});


        manageButtons("add");

//    	generate number
	    	$scope.fun_get_pono = function() {
		$http.get('getCounterPrefix').success(function(response) {
			$scope.formData.code = response;
		});
	}
	    	$scope.end_time="";
	    	$scope.start_time="";
    	//$scope.filterTimes1 = filterTime();
	    	$scope.filterTimes1 ="h:mm a";
    	$scope.formData = {};
    	var dt = new Date();
		var time = dt.getHours() + ":" + dt.getMinutes() + ":"
		+ dt.getSeconds();
		$scope.Time = time;
	
		
	
		/*$scope.end_time=$scope.Time;
		$scope.start_time=$scope.Time;*/
		
		
		$scope.end_time=$filter('date')(new Date(),$scope.filterTimes1 );
		$scope.start_time=$filter('date')(new Date(),$scope.filterTimes1 );
		
		$scope.formData.name=$filter('date')(new Date(),"h:mm a")+"-"+$filter('date')(new Date(),"h:mm a")

		
	$scope.$watch("start_time", function(newValue, oldValue) {
		
		if($scope.formData.id=="" || $scope.formData.id==undefined)
			{
		$scope.formData.name=$scope.start_time+"-"+$scope.end_time;
			}
		
			
});
		$scope.$watch("end_time", function(newValue, oldValue) {
			
			if($scope.formData.id=="" || $scope.formData.id==undefined)
			{
			$scope.formData.name=$scope.start_time+"-"+$scope.end_time;
			}
	});
	    
		
		
		
		$scope.show_table=true;
		$scope.show_form=false;
		$scope.hide_code_existing_er = true;
		$scope.Quetable={};
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
		//getDropdown();
		
		var vm = this;	 
		
		$scope.show_table=true;
		$scope.show_form=false;
		$scope.hide_code_existing_er = true;
		vm.reloadData = reloadData;
		//$scope.getDropdown=getDropdown;
		vm.showTable  = showTable;
		vm.view_mode_aftr_edit = view_mode_aftr_edit;
		vm.code_existing_validation = code_existing_validation;
	
		vm.dtInstance = {};	
		var DataObj = {};		
		DataObj.sourceUrl = "getDataTableData";
		DataObj.infoCallback = infoCallback;
		DataObj.rowCallback = rowCallback;
		vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 				
	    vm.dtColumns = [
			          DTColumnBuilder.newColumn('code').withOption('width','250px').withTitle('CODE').renderWith(
					           function(data, type, full, meta) {
			               			if(full.quetableId!="" && full.quetableId!=undefined){css="queTableInColor"}else{css="queTableOutColor"}

						              return urlFormater(data);  
					            }),
			          DTColumnBuilder.newColumn('name').withTitle('NAME').withOption('width','250px'),
			       //   DTColumnBuilder.newColumn('description').withTitle('DESCRIPTION').withOption('width','400px'),
			
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
		//getDropdown();
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$http({
			url : 'editData',
			method : "GET",
			params : {id:row_data.id},
		}).then(function(response) {
		$scope.formData = {id:response.data.editDet.id,name:response.data.editDet.name,code:response.data.editDet.code,
				description:response.data.editDet.description};		
		
		
		$scope.createdBy = response.data.editDet.created_by;
		$scope.createdAt =  response.data.editDet.created_at;
		
		
		var startTime =  response.data.editDet.start_time;
		var first = startTime.split(".")[0];
		var starttimepart = first.split(" ")[1];
		$scope.start_time=starttimepart;
		
		
		var endTime =  response.data.editDet.end_time;
		var first1 = endTime.split(".")[0];
		var endtimepart = first1.split(" ")[1];
		$scope.end_time=endtimepart;
		
		$scope.formData.name=response.data.editDet.name;
		
		});
		$scope.disable_all = true;
		$scope.disable_code = true;
		
		
		
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
					
					$rootScope.$broadcast('on_AlertMessage_ERR','Time slot '+FORM_MESSAGES.ALREADY_USE);

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
			$scope.formData.created_at = $scope.createdAt;
			  $scope.formData.created_by =  $scope.createdBy;
			  
			  var start_date_time = $filter('date')(new Date(),"yyyy-MM-dd" )+ ' ' + getMySqlTimeFormatThis($scope.start_time);
			  $scope.formData.start_time=start_date_time;
			  var end_date_time= $filter('date')(new Date(),"yyyy-MM-dd" )+ ' ' + getMySqlTimeFormatThis($scope.end_time);
			  $scope.formData.end_time=end_date_time;
			  
			
				$scope.Quetable.shopId=settings['currentcompanyid1'];
				$scope.Quetable.origin=settings['currentcompanycode1'];
			$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
			$http({
				url : 'savetimeslot',
				method : "POST",
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
					$scope.end_time=$filter('date')(new Date(),$scope.filterTimes1 );
					$scope.start_time=$filter('date')(new Date(),$scope.filterTimes1 );
					
					$scope.formData.name=$filter('date')(new Date(),"h:mm a")+"-"+$filter('date')(new Date(),"h:mm a")
					//getDropdown();
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
			$scope.fun_get_pono();
			//getDropdown();
			$scope.hide_code_existing_er = true;
			
			$scope.end_time=$filter('date')(new Date(),$scope.filterTimes1 );
			$scope.start_time=$filter('date')(new Date(),$scope.filterTimes1 );
			
			$scope.formData.name=$filter('date')(new Date(),"h:mm a")+"-"+$filter('date')(new Date(),"h:mm a")
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
	});
	
	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});
	
	

	
	$rootScope.$on("fun_clear_form",function(){
		$scope.hide_code_existing_er = true;
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		$scope.formData = {};
		$scope.fun_get_pono();
		$scope.formData.name=$filter('date')(new Date(),"h:mm a")+"-"+$filter('date')(new Date(),"h:mm a")
		$scope.end_time=$filter('date')(new Date(),$scope.filterTimes1 );
		$scope.start_time=$filter('date')(new Date(),$scope.filterTimes1 );
		//getDropdown();
	
		$("#form_div_base_uom_id").removeClass("has-error");
		$("#form_div_base_uom_id_error").hide();
		$("#form_div_compound_unit").removeClass("has-error");
		$("#form_div_compound_unit_error").hide();
	});
	
/*function getDropdown()
{
	
	
	$http({
		method: 'GET',
		url : "getDropDownData"
	}).success(function (result) {
		$scope.baseUom = result.baseUom;
		
	});
	
}	*/
	
	
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
	
		
		
		if(flg==false)
		{
			focus();
		}
		return flg;
	}
};


function getMySqlTimeFormatThis(time) {
	AMPM = time.split(" ")[1];
	hours = parseInt(time.split(" ")[0].split(":")[0]);
	hours = getTwentyFourFormat(AMPM, hours);
	formatTime = hours.toString() + ":" + time.split(" ")[0].split(":")[1]
	+ ":00";
	
	return formatTime;
}




