angular.module("sbrpr.filters", [])
.filter('groupBy', function () {
    return function (data, key) {
        if (!(data && key)) return;
        var result = {};
        for (var i=0;i<data.length;i++) {
            if (!result[data[i][key]])
                result[data[i][key]]=[];
            result[data[i][key]].push(data[i])
        }
        return result;
    };
});

// Controller for Buttons 
mrpApp.controller('btn_ctrl', function($scope,$timeout,$rootScope,$http,$window) {
	
	var vm = this;
	vm.form_next_data = form_next_data;
	vm.fun_show_addmode_btn = fun_show_addmode_btn;
	$scope.saveAlertMessage=true; 
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
		$("#div_btn_SET").hide();
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
	$scope.fun_discard_form2 = function(){				//Discard Formfileds values in edit or view mode
		$rootScope.$emit("fun_discard_form2");
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
});
//Controller for Table and Form 
mrpApp.controller('user_group', user_group);

function user_group($parse,$filter,$controller,$scope, $interval, $http, $mdDialog ,$rootScope, DTOptionsBuilder,
		DTColumnBuilder ,MRP_CONSTANT,DATATABLE_CONSTANT, $timeout, $q , $window,$compile,FORM_MESSAGES) {
	
	$controller('DatatableController', {$scope: $scope});
	/*set_sub_menu("#settings");						
	setMenuSelected("#user_group_left_menu");	*/		//active leftmenu
	manageButtons("add");
	//$scope.formData2 = {};
	$scope.formData = {};
	$scope.show_table=true;
	$scope.show_form=false;
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
	
	$scope.hide_code_existing_er = true;
	
	 //confirm box function
    $scope.showConfirm = function(title,sourse,event) {
    	var confirm = $mdDialog.confirm({onComplete: function afterShowAnimation() {
            var $dialog = angular.element(document.querySelector('md-dialog'));
            var $actionsSection = $dialog.find('md-dialog-actions');
            var $cancelButton = $actionsSection.children()[0];
            var $confirmButton = $actionsSection.children()[1];
            angular.element($confirmButton).removeClass('md-focused');
            angular.element($cancelButton).addClass('md-focused');
            $cancelButton.focus();}}).title(title).targetEvent(event).ok('Yes').cancel('No');
		$mdDialog.show(confirm).then(function() {
            	if(sourse=="DELETE"){ 
            		$scope.deleteData();
            	}else if(sourse=="DISCARD_FORM1"){	
            		$scope.discardDataForm1();
            	}else if(sourse == "DISCARD_FORM2"){
            		$scope.discardDataForm2();
            	}
               }, function() { 
            });
      };
      
      //alert box function
      $scope.alertBox = function(msg,event){
	    	$mdDialog.show($mdDialog.alert()
                    .parent(angular.element(document.querySelector('#dialogContainer')))
                    .clickOutsideToClose(true).textContent(msg).ok('Ok!').targetEvent(event)
              );
	    }
	
     //generate user-group code 
	$scope.fun_get_usrGrpCode=function(){
		$http.get('getCounterPrefix')
		.success(function (response) {
			$scope.formData.code = response;});}
	
	var vm = this;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.code_existing_validation = code_existing_validation;
	
	vm.dtInstance = {};	
	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 	
	vm.dtColumns = [
			DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').renderWith(
					function(data, type, full, meta) {
               			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}

						return '<a id="rcd_edit" class="'+css+'" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
						+ data + '</a>';
					}),
			DTColumnBuilder.newColumn('name').withTitle('NAME'),
			DTColumnBuilder.newColumn('name').withTitle('SETTINGS').withOption('width','180px').notSortable().withOption('searchable','false').renderWith(
					function(data, type, full, meta) {
						data='SET PERMISSION';
						return '<a id="set_permit" style="cursor:pointer;" ng-click="show_table = false;show_form=false;" ng-href="#">'
						+ data + '</a>';
					}),
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
					var rowDtl = vm.dtInstance.DataTable.rows();
					for(var i = 0;i<rowDtl[0].length;i++){
						if(rowDtl[0][i] == vm.dtInstance.DataTable.row(".selected").index()){
							current_row_index = i;
						}
					}
					edit(rowData,current_row_index,e);
					 $("#show_form").val(1);
				} else if(e.target.id=="set_permit"){
					var rowData = aData;
					show_form2(rowData.id);
					 $("#show_form").val(1);
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
		reloadData();
	});
	
	$rootScope.$on('hide_table',function(event){
		showTable(event);
	});
	
	$rootScope.$on('hide_form',function(event){
		 $("#show_form").val(0);
		$scope.show_table=true;
		$scope.show_form=false;
		$scope.show_form2=false;
		$(vm.dtInstance.DataTable.row(".selected").node()).removeClass('selected');	
	});
	
	function showTable(event){
		$scope.show_table=false;
		$scope.show_form=true;
	}
	function reloadData() {
        vm.dtInstance.reloadData(null, true);
    }
	
	$scope.createdBy = strings['userID'];
	$scope.createdAt = moment().format("YYYY-MM-DD");
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
		$scope.createdAt = row_data.created_at;
		$scope.createdBy = row_data.created_by;
		$scope.disable_all = true;
		$scope.disable_code=true;
		$scope.hide_code_existing_er = true;
	}

	//Delete Function
	$rootScope.$on("fun_delete_current_data",function(event){	
		$scope.showConfirm(FORM_MESSAGES.DELETE_WRNG,"DELETE",event);
	});

	$scope.deleteData = function(event){
		var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$http({
			url : 'delete',method : "POST",params : {id:$scope.formData.id},
		}).then(function(response) {
			if(response.data==0){
				$rootScope.$broadcast('on_AlertMessage_ERR',"UserGroup "+FORM_MESSAGES.ALREADY_USE+"");
			}
			if(response.data==11 || response.data==10){
				$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
				
				  $("#show_form").val(0);
				vm.dtInstance.reloadData(null, true);
				reloadData();
				$scope.show_table=true;
				$scope.show_form=false;
				manageButtons("add");
				$scope.disable_all = true;
				$scope.disable_code = true;
			}	
		}, function(response) { // optional
			$scope.alertBox("Delete failed.");});
	}

	function view_mode_aftr_edit(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
		$scope.disable_all = true;
		$scope.disable_code = true;
	}
	
	$rootScope.$on('fun_save_data',function(event){		//Save Function
		if($scope.show_form==true){
		  if (code_existing_validation($scope.formData)) {
			  $scope.formData.created_at = $scope.createdAt;
			  $scope.formData.created_by =  $scope.createdBy;
			  $scope.Quetable.shopId=settings['currentcompanyid1'];
				$scope.Quetable.origin=settings['currentcompanycode1'];
			$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
			$http({
				url : 'saveUsrGrp',method : "POST",params : $scope.formData,
				data : {Quetable:$scope.Quetable},
			}).then(function(response) {
				if(response.data == 1){
					if($scope.formData.id !=undefined){
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
					}else{
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
					}
					 if($scope.formData.id !=undefined){
						view_mode_aftr_edit();
					 }else{
						 $scope.formData ={};
						 $scope.fun_get_usrGrpCode();
					 }
					 reloadData();
					$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

					 $scope.hide_code_existing_er = true;
				}else{
					$scope.alertBox("Save failed.");
				}
			}, function(response) { // optional
				$scope.alertBox("Save failed.");
			});
		}
		}else if($scope.show_form2==true){
			$scope.setSystemGroupPermission();
			 $scope.Quetable.shopId=settings['currentcompanyid1'];
				$scope.Quetable.origin=settings['currentcompanycode1'];
			//$scope.Quetable.curdAction=($scope.groupPermissions[0].id=="" || $scope.groupPermissions[0].id==undefined )?"C":"U";
			$scope.Quetable.curdAction=($scope.groupPermissions[0].usergrouppermissionId!=null && $scope.groupPermissions[0].usergrouppermissionId!="")?"U":"C";

			  $http({
					url : '../usergrouppermission/savePermission',
					method : "POST",
					params : $scope.Quetable,
					data : {groupPermissions:$scope.groupPermissions}
				}).then(function(response) {
					if($scope.groupPermissions[0].usergrouppermissionId!=null){
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
					}else{
						$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
					}
				}, function(response) { // optional
					$scope.alertBox("Save failed.");
				});
		   }
	});
	
	$rootScope.$on("fun_discard_form",function(event){				//Discard function
		$scope.showConfirm(FORM_MESSAGES.DISCARD_WRNG,"DISCARD_FORM1",event);
	});
	
	$scope.discardDataForm1 = function(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		if($scope.formData.id == undefined){
			$scope.formData ={};
			$scope.fun_get_usrGrpCode();
			$scope.hide_code_existing_er = true;
		}else{
			var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
			edit(row_data[0],cur_row_index);
		}
		clearform();
	}
	
	$rootScope.$on("fun_discard_form2",function(event){				//Discard function
		$scope.showConfirm(FORM_MESSAGES.DISCARD_WRNG,"DISCARD_FORM2",event);
	});

	$scope.discardDataForm2 = function(){
		var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
		var row_data =$scope.grpId;
		show_form2(row_data);
	}
	
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
		$scope.fun_get_usrGrpCode();
		$scope.hide_code_existing_er = true;
	});
	
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
	
	function code_existing_validation(data){
		var flg = true;
		var row_data = vm.dtInstance.DataTable.rows().data();
		if (data.id == undefined || data.id == "") {
			if (!$scope.hide_code_existing_er) {
				flg = false;
				$("#code").select();
			}
		}
		if (validation() == false) {
			flg = false;
		}
		if (flg == false) {
			focus();
		}
		return flg;
	}
	
//  *************************************************************************************************************************************************

	$scope.groupPermissions =  [];
	$scope.groupVariable    = {};	
	/** 
	 * permission list
	 * 
	 */		
	function show_form2(usergrpid){
		$scope.grpId= usergrpid;
		$http({
			url : '../sysdefpermission/permission',
			method : "GET",
			params:{userGroupId:$scope.grpId}
		}).then(
			function(response) {
				$scope.groupPermissions =  response.data;			
				$scope.isview    = true;
				$scope.isadd     = true;
				$scope.isedit    = true;
				$scope.isdelete  = true;
				$scope.isexport  = true;
				$scope.isexecute = true;			
				/*   groupPermissions loop and set toggle checkbox */
				angular.forEach($scope.groupPermissions, function(value, key) {				 		  		  
					  if(value.systemGroup=="MAIN"){		  
						   var the_string = value.code;
						   $scope.groupVariable[the_string] = value;   
					   }
					   /*  toggleAll set value     */	  
					    if(value.canView==false && value.isViewApplicable != false){						  
						   $scope.isview = false;					   
					    }
					    if(value.canAdd==false && value.isAddApplicable != false ){						  
						   $scope.isadd = false;					   
						}
					    if(value.canEdit==false && value.isEditApplicable != false){						  
						   $scope.isedit = false;					   
						}
					    if(value.canDelete==false && value.isDeleteApplicable != false){						  
						  $scope.isdelete = false;					   
						}
					    if(value.canExport==false && value.isExportApplicable != false){						  
						   $scope.isexport = false;					   
						}
					    if(value.canExecute==false && value.isExecuteApplicable != false){						  
						   $scope.isexecute = false;					   
						}				 			  
					});
				 $scope.callGroupRowCheck();	
			}, function(response){		
			});

		$scope.show_table=false;
		$scope.show_form2=true;	    
		$("#div_btn_add").hide();
		$("#div_btn_new").hide();
		$("#div_btn_edit").hide();
		$("#div_btn_SET").show();			
	}
	/** 
	 * callGroupRowCheck()
	 * 
	 */
	$scope.callGroupRowCheck = function(){
		   $scope.grouppedPermission = $filter('groupBy')($scope.groupPermissions, 'systemGroup');
		   angular.forEach($scope.grouppedPermission, function(value, key) {	 
			   if(key != "MAIN"){			   
				   var grpViewBollean = true;
				   var grpAddBollean  = true;
				   var grpEditBollean = true;
				   var grpDeleteBollean = true;
				   var grpExportBollean = true;
				   var grpExecuteBollean = true;	
				   
				   angular.forEach(value, function(value2, key2) {
					   if(value2.canView == false && value2.isViewApplicable != false){
						   grpViewBollean = false; 
					   }					   
					   if(value2.canAdd == false && value2.isAddApplicable != false){
						     grpAddBollean  = false; 
					   }
					   if(value2.canEdit == false && value2.isEditApplicable != false){
						     grpEditBollean = false; 
					   }
					   if(value2.canDelete == false && value2.isDeleteApplicable != false){
						     grpDeleteBollean = false; 
					   }
					   if(value2.canExport == false && value2.isExportApplicable != false){
						     grpExportBollean = false; 
					   }
					   if(value2.canExecute == false && value2.isExecuteApplicable != false){
						    grpExecuteBollean = false; 
					   }   
				   });   			   
				  if(grpViewBollean == false || grpAddBollean==false || grpEditBollean==false || grpDeleteBollean==false || grpExportBollean== false || grpExecuteBollean==false ){	   
					   
					  $scope.single_object = $filter('filter')($scope.groupPermissions, function (d) {return d.code === key;})[0];
					    
					    if($scope.single_object !=null){
					   	$scope.single_object.canView= grpViewBollean;
					   	$scope.single_object.canAdd= grpAddBollean;
					   	$scope.single_object.canEdit= grpEditBollean;
					   	$scope.single_object.canDelete= grpDeleteBollean;
					   	$scope.single_object.canExport= grpExportBollean;
					   	$scope.single_object.canExecute= grpExecuteBollean;	   	 
					    }		   				   
				   }				   			   
			   }
			}); 
	}
	 
	/** 
	 * setSystemGroupPermission()   for save
	 * 
	 */
	$scope.setSystemGroupPermission = function(){
		   $scope.grouppedPermission = $filter('groupBy')($scope.groupPermissions, 'systemGroup');
		   angular.forEach($scope.grouppedPermission, function(value, key) {	 
			   if(key != "MAIN"){			   
				   var grpViewBollean = false;
				   var grpAddBollean  = false;
				   var grpEditBollean = false;
				   var grpDeleteBollean = false;
				   var grpExportBollean = false;
				   var grpExecuteBollean = false;			   
				   angular.forEach(value, function(value2, key2) {			   ;
					   if(value2.canView == true ){
						   grpViewBollean = true; 
					   }				   
					   if(value2.canAdd == true ){
						     grpAddBollean  = true; 
					   }				   
					   if(value2.canEdit == true ){
						     grpEditBollean = true; 
					   }				   
					   if(value2.canDelete == true ){
						     grpDeleteBollean = true; 
					   }				   
					   if(value2.canExport == true ){
						     grpExportBollean = true; 
					   }				   
					   if(value2.canExecute == true){
						    grpExecuteBollean = true; 
					   }			   
				   });
				   
				  $scope.single_object = $filter('filter')($scope.groupPermissions, function (d) {return d.code === key;})[0];				    
				  if($scope.single_object !=null){
					   	$scope.single_object.canView= grpViewBollean;
					   	$scope.single_object.canAdd= grpAddBollean;
					   	$scope.single_object.canEdit= grpEditBollean;
					   	$scope.single_object.canDelete= grpDeleteBollean;
					   	$scope.single_object.canExport= grpExportBollean;
					   	$scope.single_object.canExecute= grpExecuteBollean;	   	 
				  }		   				   
		   
			     }   
			    }); 
	}

	/** 
	 * toggleAll()
	 * 
	 */
	$scope.toggleAll = function (id,booleanValue) {   
		  
		if(id==1){
		   $scope.toggleAllFromView(booleanValue);
		}
		angular.forEach($scope.groupPermissions, function(value, key) {
			if(booleanValue==true && value.isViewApplicable != false){
		    	 value.canView=true;
		    	 $scope.isview=true;
		    }
			if(id==1 && value.isViewApplicable != false ){		   
	 		  value.canView=booleanValue;	  
	 	    }
	 	    else if(id==2 && value.isAddApplicable != false ){
	 	      value.canAdd=booleanValue;	      	            
	 	    }
	 	    else if(id==3 && value.isEditApplicable != false ){
	 	      value.canEdit=booleanValue;
	 	    }
	 	    else if(id==4 && value.isDeleteApplicable != false ){
	 	      value.canDelete=booleanValue;
	 	    }
	 	    else if(id==5 && value.isExportApplicable != false ){
	 	      value.canExport=booleanValue;
	 	    }
	 	    else if(id==6 && value.isExecuteApplicable != false ){
	 	      value.canExecute=booleanValue;
	 	    }
	   });
	};
	   
	/** toggleAllFromView()
	 *  dessable all codes
	 * 
	 */
	 $scope.toggleAllFromView = function (booleanValue) {
		   if(booleanValue==false){
			    $scope.isview     = false;
				$scope.isadd     = false;
				$scope.isedit    = false;
				$scope.isdelete  = false;
				$scope.isexport  = false;
				$scope.isexecute = false;			
			   angular.forEach($scope.groupPermissions, function(value, key) {			  
					   value.canView = false;	   
					   value.canAdd  = false;		    
					   value.canEdit  = false;		   
					   value.canDelete  = false;		   
					   value.canExport  = false;		   
					   value.canExecute  = false;		    		   		   		   
			   });
		   }   
	   }
	 
	 // ******************************************************************* 
	/** 
	 * viewChange()
	 * 
	 */
	$scope.viewChange = function(field,index,rowFrom){	
	    var boolean    = true ; 
	    var grpBollean = true;
	    angular.forEach($scope.groupPermissions, function(value, key) {	  
	   	  if(rowFrom=='g'){		     
	   		  if(field.code==value.systemGroup    ){		  
	   			  if(value.isViewApplicable != false){
	   				value.canView= field.canView;	
	   			  }	  
	   			  if(field.canView==false){
	   				   if(value.isViewApplicable != false){
	   					 $scope.isview = false;
	   					 field.canView = false;
	   				   } 				  
	   				   if(value.isAddApplicable != false){
	   					 $scope.isadd = false;
	   					 field.canAdd  = false;		
	  				   }  				   
	   				   if(value.isEditApplicable != false){
	   				      $scope.isedit = false;
	   				      field.canEdit  = false;	
	   				      value.canEdit  = false;	
	  				   }
	   				   if(value.isDeleteApplicable != false){
	   				      $scope.isdelete = false;
	   				      field.canDelete  = false;
	   				      value.canDelete  = false; 
	     			   }
	   				   if(value.isExportApplicable != false){
	   				      $scope.isexport = false;
	   				      field.canExport  = false;
	   				      value.canExport  = false;	 
	      			   }
	   				   if(value.isExecuteApplicable != false){
	   					  $scope.isexecute = false;     				  
	      				  field.canExecute  = false; 
	      				  value.canExecute  = false;    				 
	         		   }  				   		   				   
	   				   value.canView = false;	   
	   				   value.canAdd  = false;		       
	   			   }	  
	   		  }  
	   	   }else{  // come from row 'r'	  				  
	   		  if(field.systemGroup==value.systemGroup && value.isViewApplicable != false){			  
	   			  if(field.canView==false){	   				  			  
	   				  grpBollean = false;			  
	   				  // ---------------------------   				  
	   				   $scope.isview = false;
					   $scope.isadd = false;
					   $scope.isedit = false;
					   $scope.isdelete = false;
					   $scope.isexport = false;
					   $scope.isexecute = false; 
	   				  // -----------			  	   
	   				   field.canAdd  = false;		    
	   				   field.canEdit  = false;		   
	   				   field.canDelete  = false;		   
	   				   field.canExport  = false;		   
	   				   field.canExecute  = false;	  
	   			  }		  
	   		  } 	  
	   	  } 	  
	      }); 
	            
	    $scope.single_object = $filter('filter')($scope.groupPermissions, function (d) {return d.code === field.systemGroup;})[0];
	    if($scope.single_object !=null){
	   	 $scope.single_object.canView= grpBollean
	    }
	    angular.forEach($scope.groupPermissions, function(value, key) {	 
	   	   if(value.canView==false && value.isViewApplicable != false){	       
	   		  boolean = false;
	        }		 
	    });
	    
		    $scope.isview  = boolean;  // set toggleAll canView 
		    $scope.callGroupRowCheck();
	    }
	   
	   /** 
	    * addChange()
	    * 
	    */
	   $scope.addChange = function(field,index,rowFrom){	
	    var boolean    = true ; 
	    var grpBollean = true;    
	    var vBoolean   = true ; 
	    if(rowFrom=='r' && field.canAdd==true ){ 	
	    	  if(field.isViewApplicable != false){
	    		  field.canView  = true;
	  		      $scope.callGroupRowCheck();
	    	  }	        
	     }
	    
	   angular.forEach($scope.groupPermissions, function(value, key) {	  
	   	  if(rowFrom=='g'){	  		  
	   		  if(field.code==value.systemGroup && value.isAddApplicable != false){		  
	   			  value.canAdd= field.canAdd;				  
	   			   if(field.canAdd==false){   
	   				   field.canAdd  = false;		       
	   				   value.canAdd  = false;		    	   
	   			   }else{
	   				  if( value.isViewApplicable != false){
	   					  field.canView  = true;		       
	   					  value.canView  = true;
	   				   } 
	      		   }	  
	   		  } 	  
	   	  }else{  // come from row 'r'	  		  
	   		  if(field.systemGroup==value.systemGroup && value.isAddApplicable != false){	  
	   			if(value.canAdd==false){			
	   				grpBollean = false;			
	   			}  					 
	   		  } 	  
	   	  } 	  
	    }); 
	        
	    $scope.single_object = $filter('filter')($scope.groupPermissions, function (d) {return d.code === field.systemGroup;})[0];
	    
	    if($scope.single_object !=null){
	   	 $scope.single_object.canAdd= grpBollean;
	    }

	    angular.forEach($scope.groupPermissions, function(value, key) {	 
	   	  if(value.canAdd==false && value.isAddApplicable != false){	       
	   		 boolean = false;
	      }	
	      if(value.canView==false && value.isViewApplicable != false){	       
	   		vBoolean = false;
	      }
	    });
	    
	    	$scope.isadd  = boolean;  // set toggleAll canView
	    	$scope.isview  = vBoolean;
	    } 
	   
	/** 
	 * editChange()
	 * 
	 */
	$scope.editChange = function(field,index,rowFrom){	
	    var boolean    = true ; 
	    var grpBollean = true;    
	    var vBoolean   = true ;
	    
	    if(rowFrom=='r' && field.canEdit==true ){ 	
	    	if(field.isViewApplicable != false){
	  		  field.canView  = true;
			      $scope.callGroupRowCheck();
	  	    }
	     }
	    
	   angular.forEach($scope.groupPermissions, function(value, key) {	  
	   	  if(rowFrom=='g'){	  
	   		  if(field.code==value.systemGroup && value.isEditApplicable != false){		  
	   			  value.canEdit= field.canEdit;				  
	   			   if(field.canEdit==false){   
	   				   field.canEdit  = false;		       
	   				   value.canEdit  = false;		    	   
	   			   }else{
	   				  if( value.isViewApplicable != false){
	   					field.canView  = true;		       
	   					value.canView  = true;
	   				   } 
	      		    }	  
	   		  }  
	   	  }else{  // come from row 'r'	 
	   		  
	   		  if(field.systemGroup==value.systemGroup && value.isEditApplicable != false){	  
	   			if(value.canEdit==false){			
	   				grpBollean = false;			
	   			}  					 
	   		  } 	  
	   	  } 	  
	    }); 
	        
	    $scope.single_object = $filter('filter')($scope.groupPermissions, function (d) {return d.code === field.systemGroup;})[0];
	    
	    if($scope.single_object !=null){
	   	 $scope.single_object.canEdit= grpBollean;
	    }

	    angular.forEach($scope.groupPermissions, function(value, key) {	 
	   	  if(value.canEdit==false && value.isEditApplicable != false){	       
	   		 boolean = false;
	      }	
	      if(value.canView==false && value.isViewApplicable != false){	       
	   		vBoolean = false;
	      }
	    });
	    
	    $scope.isedit  = boolean;  // set toggleAll canView
	    $scope.isview  = vBoolean;
	    }  
	   
	 /** 
	  *deleteChange()
	  * 
	  */
	$scope.deleteChange = function(field,index,rowFrom){	
	    var boolean    = true ; 
	    var grpBollean = true;    
	    var vBoolean   = true ;
	    
	    if(rowFrom=='r' && field.canDelete==true ){ 	
	    	if(field.isViewApplicable != false){
	  		  field.canView  = true;
			  $scope.callGroupRowCheck();
	  	    }
	    }
	    
	   angular.forEach($scope.groupPermissions, function(value, key) {	  
	   	  if(rowFrom=='g'){	  
	   		  if(field.code==value.systemGroup && value.isDeleteApplicable != false){		  
	   			  value.canDelete= field.canDelete;				  
	   			   if(field.canDelete==false){   
	   				   field.canDelete  = false;		       
	   				   value.canDelete  = false;		    	   
	   			   }else{
	   				  if( value.isViewApplicable != false){
	   					  field.canView  = true;		       
	   					  value.canView  = true;
	   				   }
	   				       
	               }	  
	   		  }  
	   	  }else{  // come from row 'r'	 	 
	   		  if(field.systemGroup==value.systemGroup && value.isDeleteApplicable != false){	  
	   			if(value.canDelete==false){			
	   				grpBollean = false;			
	   			}  					 
	   		  } 	  
	   	   } 
	   	  
	    }); 
	        
	    $scope.single_object = $filter('filter')($scope.groupPermissions, function (d) {return d.code === field.systemGroup;})[0];
	    
	    if($scope.single_object !=null){
	   	 $scope.single_object.canDelete= grpBollean;
	    }

	    angular.forEach($scope.groupPermissions, function(value, key) {	 
	   	    if(value.canDelete==false && value.isDeleteApplicable != false){	       
	   		   boolean = false;
	        }	
	   	    if(value.canView==false && value.isViewApplicable != false){	       
	   		  vBoolean = false;
	        }	    	  
	    });   
	    $scope.isdelete  = boolean;  // set toggleAll canView
	    $scope.isview    = vBoolean;

	    }  
	
	/**
	 *  exportChange()
	 * 
	 */
	   
	$scope.exportChange = function(field,index,rowFrom){	
	   var boolean    = true ; 
	   var grpBollean = true;    
	   var vBoolean   = true ;  
	   if(rowFrom=='r' && field.canExport==true ){ 	
	    	if(field.isViewApplicable != false){
	  		  field.canView  = true;
			      $scope.callGroupRowCheck();
	  	  }
	   }
	   
	   angular.forEach($scope.groupPermissions, function(value, key) {	  
	   	  if(rowFrom=='g'){	  
	   		  if(field.code==value.systemGroup && value.isExportApplicable != false){		  
	   			   value.canExport= field.canExport;				  
	   			   if(field.canExport==false){   
	   				   field.canExport  = false;		       
	   				   value.canExport = false;		    	   
	   			   }else{
	   				   if( value.isViewApplicable != false){
	   					 field.canView  = true;		       
	   					 value.canView  = true;
	   				    }  
	      		   }	  
	   		   }  
	   	      }else{  // come from row 'r'	 	 
	   		     if(field.systemGroup==value.systemGroup && value.isExportApplicable != false){	  
	   			    if(value.canExport==false){			
	   				grpBollean = false;			
	   			    }  					 
	   		     } 	  
	   	      } 	  
	    }); 
	   
	    $scope.single_object = $filter('filter')($scope.groupPermissions, function (d) {return d.code === field.systemGroup;})[0]; 
	    if($scope.single_object !=null){
	   	 $scope.single_object.canExport= grpBollean;
	    }
	    angular.forEach($scope.groupPermissions, function(value, key) {	 
	   	  if(value.canExport==false && value.isExportApplicable != false){	       
	   		 boolean = false;
	      }	
	   	  if(value.canView==false && value.isViewApplicable != false){	       
	   		vBoolean = false;
	      }
	    });
	    
	    	$scope.isexport  = boolean;  // set toggleAll canView
	    	$scope.isview  = vBoolean;
	    
	    }
	
	/** 
	 * exportChange()
	 * 
	 */
	$scope.executeChange = function(field,index,rowFrom){	
	    var boolean    = true ; 
	    var grpBollean = true;    
	    var vBoolean   = true ;
	    
	    if(rowFrom=='r' && field.canExecute==true ){ 	
	    	if(field.isViewApplicable != false){
	  		   field.canView  = true;
			   $scope.callGroupRowCheck();
	  	     }
	     }
	    
	    angular.forEach($scope.groupPermissions, function(value, key) {	  
	   	    if(rowFrom=='g'){	  
	   		     if(field.code==value.systemGroup && value.isExecuteApplicable != false){		  
	   			     value.canExecute= field.canExecute;				  
	   			     if(field.canExecute==false){   
	   				   field.canExecute  = false;		       
	   				   value.canExecute = false;		    	   
	   			     }else{
	   				         if( value.isViewApplicable != false){
	   					        field.canView  = true;		       
	   					        value.canView  = true;
	   				          }
	   				       }	  
	   		      }  
	   	    }else{  // come from row 'r'	 	 
	   		  if(field.systemGroup==value.systemGroup && value.isExecuteApplicable != false){	  
	   			 if(value.canExecute==false){			
	   				grpBollean = false;			
	   			 }  					 
	   		  } 	  
	   	    }  	  
	    });   // forEach close
	        
	    $scope.single_object = $filter('filter')($scope.groupPermissions, function (d) {return d.code === field.systemGroup;})[0];
	    
	    if($scope.single_object !=null){
	   	 $scope.single_object.canExecute= grpBollean;
	    }

	    angular.forEach($scope.groupPermissions, function(value, key) {	 
	   	  if(value.canExecute==false && value.isExecuteApplicable != false){	       
	   		 boolean = false;
	      }	
	   	  if(value.canView==false && value.isViewApplicable != false){	       
	   		vBoolean = false;
	      }
	    });
	    
	    	$scope.isexecute  = boolean;  // set toggleAll canView
	    	$scope.isview  = vBoolean;
	    
	    }
}



