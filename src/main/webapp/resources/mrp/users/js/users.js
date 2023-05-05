/**
 * 
 */

//Controller for Table and Form 
mrpApp.controller('usersctrl', usersctrl);


function usersctrl($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,$q,
        MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {
	$controller('DatatableController', {$scope: $scope});

	/*set_sub_menu("#settings");		
	setMenuSelected("#users_left_menu");*/			//active leftmenu
	manageButtons("add");
	
	$("#form_div_description").hide();
	$scope.userGroup="";
	$scope.formData = {};
	$scope.show_table=true;
	$scope.show_form=false;
	$scope.hide_code_existing_er = true;
	$scope.formData.is_active=true;
	$scope.namehide=true;
	$scope.cnfrm_password="";
	$scope.pass_miss=false;
	$scope.date=false;
	$scope.chgpass=false;
	$scope.changpassword="";
	$scope.Quetable={};
	$scope.shopIdList=[];
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
	var vm = this;
	vm.showTable1=showTable1;
	vm.edit = edit;
	vm.reloadData = reloadData;
	vm.showTable = showTable;
	vm.select_next_prev_row = select_next_prev_row;
	vm.view_mode_aftr_edit = view_mode_aftr_edit;
	vm.code_existing_validation = code_existing_validation;
	vm.function_clear_select=function_clear_select;
	
	//vm.fun_change_password=fun_change_password;
	
	vm.popup_form_validation=popup_form_validation;
	//vm.getDepList = getDepList;
	
		
	 $scope.userGroupList =[ {id : 0 ,
	                		 name : "select",}];
	 	             	     
	$http({
		url : 'usergroup/json',
		method : "GET",
	}).then(function(response) {

		var data = response.data.data;
		var data_len = data.length;
		for(var i=0; i < data_len;i++){
			$scope.userGroupList.push(data[i]);
			
		}
	});

	
	
	$scope.employeeList=[];   
	$http({
		url : 'employee/json',
		method : "GET",
	}).then(function(response) {

		$scope.employeeList = response.data.data;
		$scope.employeeList.splice(0,0,{id : "" ,f_name  : "select"});
		/*var data_len = data.length;
		for(var i=0; i < data_len;i++){
			$scope.employeeList.push(data[i]);
			
		}*/
	
			
	});
	
	
	
	

	vm.dtInstance = {};

	var DataObj = {};		
	DataObj.sourceUrl = "getDataTableData";
	DataObj.infoCallback = infoCallback;
	DataObj.rowCallback = rowCallback;
	vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj); 		
	vm.dtColumns = [
			//DTColumnBuilder.newColumn('id').withTitle('ID').notVisible(),
			DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').renderWith(
			           function(data, type, full, meta) {
	               			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}

				              return urlFormater(data);  
			            }),
			DTColumnBuilder.newColumn('name').withTitle('NAME'),
			DTColumnBuilder.newColumn('card_no').withTitle('CARD NUMBER'),
			DTColumnBuilder.newColumn('usergroup_name').withTitle('USER GROUP NAME'),
			DTColumnBuilder.newColumn('isActive').withTitle('STATUS').renderWith(
					function(data,type,full,meta){
						if(data=="active")
							{
							data='<i class="fa fa-user empactive" data-toggle="tooltip" data-placement="bottom" title="" aria-hidden="true" data-original-title="Active"></i>';
							}
						else if(data=="inactive")
							{
							data='<i class="fa fa-user empainactive" data-toggle="tooltip" data-placement="bottom" title="" aria-hidden="true" data-original-title="inactive"></i>';
							}
						return data;
					}),
			
			
		
			

					

			
			
		 ];
	
	
	
	//shop table
    
    vm.selectedShop = {};
	$scope.departmentIdList=[];
	
	 
	
		
	    
	    
		
		function showTable1(usrId)
		{
			var usrId=usrId;
			
			urlString="shopuser/shpdata?usrId="+usrId;
			vm.dtInstance1.reloadData(null, true);
		}
		
		
	
	
	
    var urlString="shopuser/shpdata?usrId=0";
	vm.dtInstance1={};
	vm.dtOptions1 = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		$http.get(urlString).then(function(result) {
			defer.resolve(result.data);
		});
		return defer.promise;
	}).withPaginationType('full_numbers')
	.withDataProp('data')
    .withOption('paging', true)
    .withOption('info', false)
    .withOption('searching', true)
   // .withOption('drawCallback', drawCallback)
    // .withOption('rowCallback', rowCallback_table1)
    .withOption('createdRow', function(row, data, dataIndex) {
	              		$compile(angular.element(row).contents())($scope);
	              	})
	              	.withOption('headerCallback', function(header) {
	              		if (!vm.headerCompiled) {
	              			vm.headerCompiled = true;
	              			$compile(angular.element(header).contents())($scope);
	              		}
	              	})
    // .withOption('drawCallback', drawCallback)
	
	
	 vm.dtColumns1 = [
	 DTColumnBuilder.newColumn('id').withTitle('ID'),
	  DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').withOption('width','250px'),
      DTColumnBuilder.newColumn('name').withTitle('NAME'),
      DTColumnBuilder.newColumn(null).withTitle('').notSortable()
      .renderWith(function(data, type, full, meta) {
      	vm.selectedShop[full.id] = false;
      	if(full.spusrId!="")
      		{
      		vm.selectedShop[full.id] = true;
      		}

          return '<input ng-model="item.selectedShop[' + full.id + ']"  ng-click="toggleOne(item.selectedShop)" type="checkbox" ng-disabled="disable_all">';
      }).withOption('width','100px'),
	 
        		
	
	 ];
	
    
    
    
	 $scope.toggleOne=function(selectedItems) {
			$scope.seletedIds=selectedItems;
			      
			
				
				}    
	       
    
    
    
    
	
	
	
	
	
	
	
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
	
	$scope.createdBy = strings['userID'];
	$scope.createdAt = moment().format("YYYY-MM-DD");

	function edit(row_data,cur_row_index,event) {									
		$("#passwordChangediv").removeClass('in');
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
		function_clear_select();
		$rootScope.$emit("show_edit_btn_div",cur_row_index);
	    $("#form_div_password").hide();
	    $("#form_div_confirm_password").hide();
	    $scope.changpassword="";
	    $http({
			url : 'editData',
			method : "GET",
			params : {id:row_data.id},
		}).then(function(response) {
	    
			
		$scope.formData = {id:response.data.editDet.id,employee_id:response.data.editDet.employee_id,code:response.data.editDet.code,user_group_id:response.data.editDet.user_group_id,name:response.data.editDet.name,email:response.data.editDet.email,is_active:response.data.editDet.is_active,password:response.data.editDet.password,card_no:response.data.editDet.card_no,valid_from:response.data.editDet.valid_from,valid_to:response.data.editDet.valid_to};
		
		$scope.createdBy = response.data.editDet.created_by;
		$scope.createdAt =  response.data.editDet.created_at;
		
		if($scope.formData.valid_from!="")
			{
			$scope.formData.valid_from=geteditDateFormat($scope.formData.valid_from);
			}
		if($scope.formData.valid_to!="")
		{
		$scope.formData.valid_to=geteditDateFormat($scope.formData.valid_to);
		}

		if($scope.formData.is_active==1){
			$scope.formData.is_active=true;}
		else{
			$scope.formData.is_active=false;
		}
		
		if(response.data.editDet.is_admin==1)
			{
			$scope.namehide=false;
			for(var i=1;i<$scope.userGroupList.length;i++)
				{
				if($scope.userGroupList[i].id==$scope.formData.user_group_id)
					{
					$scope.userGroup=$scope.userGroupList[i].name;
                   break;
					}
				}
			
			}
		$scope.chgpass=true;
		if((companytype==0))
		   {
		
		  $scope.shpdata=response.data.shopData;
		   vm.selectedShop;
		   
		   
		   for (var id in vm.selectedShop) {
	           if (vm.selectedShop.hasOwnProperty(id)) {
	        	   
	        	   for(var i=0;i<$scope.shpdata.length;i++)
	        		   {
	        		   if(id==$scope.shpdata[i].shop_id) {
	                      	
	        			   vm.selectedShop[id]=true;
	        			   $scope.shopIdList.push($scope.shpdata[i].shop_id);
	                       
	                   }
	        		   }
	               
	           }
	       }
		   $scope.tableShwerr=false;
		   
		   showTable1(response.data.editDet.id);
			
		   	$('#table2_filter').find('input').attr('disabled',true);
		   	$('#table2_length ').find("select").attr('disabled',true);
	  
			   }
		
		});
		
		
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
		
		$scope.pass_miss=false;
		$scope.date=false;
		$scope.disable_all = true;
		$scope.disable_code=true;
		$scope.hide_code_existing_er = true;
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
			 $rootScope.$broadcast('on_AlertMessage_ERR',"User "+FORM_MESSAGES.ALREADY_USE+"");
				}else{
				$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
				
				    $("#show_form").val(0);
					$scope.show_table=true;
					$scope.show_form=false;
					manageButtons("add");
					$scope.disable_all = true;
					$scope.disable_code = true;
					vm.dtInstance.reloadData(null, true);
					if(companytype==0)
						{
					vm.dtInstance1.reloadData(null, true);
						}
				}
				
				
			}, function(response) { // optional

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
		$scope.disable_all = true;
		$scope.disable_code = true;
	}
	
	$rootScope.$on('fun_save_data',function(event){		//Save Function
		
		
		if (companytype == 0) {
		
		if($scope.seletedIds!=undefined)
		{
		$scope.shopIdList=[];
		for (var id in $scope.seletedIds) {
		    if ($scope.seletedIds.hasOwnProperty(id)) {
		    	if($scope.seletedIds[id] == true)
		    		{
		        	$scope.shopIdList.push(parseInt(id));}
		       
		        }
		}
		
		}
		
	}
		if (code_existing_validation($scope.formData)) {
			var id= ($scope.formData.is_active == true) ? 1:0;

			if($scope.formData.valid_from!="" && $scope.formData.valid_from!=undefined)
				{
			$scope.formData.valid_from = getMysqlFormat($scope.formData.valid_from);
				}
			if($scope.formData.valid_to!="" && $scope.formData.valid_to!=undefined)
			{
		$scope.formData.valid_to = getMysqlFormat($scope.formData.valid_to);
			}
			
			$scope.formData.is_active  = id;
			$scope.formData.created_at = $scope.createdAt;
			  $scope.formData.created_by =  $scope.createdBy;
			if($scope.formData.id==undefined)
				{
			
			
			var strVal = $('#password').val();
		       
	    	var strMD5 = $.md5(strVal);
            $scope.formData.password=strMD5;
				}
			
			 if($scope.changpassword!="")
         	{
         	  $scope.formData.password=$scope.changpassword;
         	}
			
			 $scope.Quetable.shopId=settings['currentcompanyid1'];
				$scope.Quetable.origin=settings['currentcompanycode1'];
			$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
			 
			  var cmpData = JSON.stringify({shopData:$scope.shopIdList,Quetable:$scope.Quetable});
			 
			$http({
				url : 'saveUser',
				method : "POST",
				params : $scope.formData,
				data : cmpData
			}).then(function(response) {
				if($scope.formData.id !=undefined)
				{
					$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
					$("#passwordChangediv").removeClass('in');
					$scope.changpassword="";
					$scope.formData.valid_to=geteditDateFormat($scope.formData.valid_to);
					$scope.formData.valid_from=geteditDateFormat($scope.formData.valid_from);
				}
			else
				{
				$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
				$scope.tableShwerr=false;
				}
				
				 if($scope.formData.id !=undefined){
					view_mode_aftr_edit();
					$('#table2_filter').find('input').attr('disabled',true);
				   	$('#table2_length ').find("select").attr('disabled',true);
				 }else{
					 $scope.formData ={};
					 $scope.cnfrm_password="";
					 $scope.formData.user_group_id = $scope.userGroupList[0].id;
					 $scope.shopIdList=[];
					 $scope.formData.employee_id='';
				 }
				  reloadData();
                if(companytype==0)
                	{
  				  vm.dtInstance1.reloadData(null, true);

                	}
					 $scope.selectedIndexTab=0;
				$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
				 $scope.hide_code_existing_er = true;
				 $scope.formData.is_active= ($scope.formData.is_active == 1) ?true:false;

			}, function(response) { // optional

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
			$scope.formData.user_group_id = $scope.userGroupList[0].id;
			//$scope.formData.department_id = $scope.departmentList[0].id;
			$scope.formData.is_active=true;
			$scope.hide_code_existing_er = true;
			$scope.formData.is_active=true;
			$("#passwordChangediv").removeClass('in');
			
		}else{
			var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
			edit(row_data[0],cur_row_index);
			$("#passwordChangediv").removeClass('in');
		}
		clearform();
		function_clear_select();
		
		});
	});
	
	$rootScope.$on("fun_enable_inputs",function(){
		$("#show_form").val(1);
		$scope.disable_all = false;
	 	$('#table2_filter').find('input').attr('disabled',false);
	 	$('#table2_length ').find("select").attr('disabled',false);
	});
	
	$rootScope.$on("fun_enable_inputs_code",function(){
		$scope.disable_code = false;
	});
	
	$rootScope.$on("fun_clear_form",function(){
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

		
		function_clear_select();
		$scope.formData = {};
		$scope.cnfrm_password="";
		$scope.formData.employee_id='';
		$scope.namehide=true;
		$("#form_div_password").show();
		$("#form_div_confirm_password").show();
		$scope.formData.is_active=true;
		$scope.formData.user_group_id= $scope.userGroupList[0].id;

		$scope.hide_code_existing_er = true;
		$scope.pass_miss=false;
		$scope.data=false;
		$("#passwordChangediv").removeClass('in');
		$scope.chgpass=false;
		
if(companytype==0)
	{
	urlString="shopuser/shpdata?usrId=0";
	   
	  vm.dtInstance1.reloadData(null, true);
	}
		   
		$scope.selectedIndexTab=0;
		$scope.tableShwerr=false;
	});
	
	
	
	
	
	//popup function
	
$scope.popup=function(){
	
	
	$("#form_div_old_password").removeClass("has-error");
	$("#form_div_old_password_error").hide();
	$("#form_div_new_password").removeClass("has-error");
	$("#form_div_new_password_error").hide();
	$scope.old_pass_miss2=false;
	$scope.cnfrm_pass_miss2=false;	
	$scope.cnfrm_pass_miss2=false;
	$scope.formData1={id:"",old_password:"",new_password:"",confirm_password2:""}
	$scope.formData1.id=$scope.formData.id;
	$scope.old_pass_miss2=false;
	$scope.cnfrm_pass_miss2=false;
	
	$http({
		url : 'checkpass',
		method : "POST",
		params : {id:$scope.formData1.id},
	}).then(function(response) {
		$scope.old_pass=response.data.checkPassword.password;
	});
	
}	

$scope.fun_reset_password=function()
{
if(popup_form_validation()==true)	
	{
	
	var strValnew = $('#new_password').val();

	var strMD5new = $.md5(strValnew);
	$scope.changpassword=strMD5new;
	$scope.formData1.new_password=strMD5new;
	$http({
		url : 'updatepassword',
		method : "POST",
		params : $scope.formData1,
	}).then(function(response) {
		
		if(response.data==1)
		{
			$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_PASS);
			
		}
		else
			{
			$rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_PASSERR);
			}
	
		$("#passwordChangediv").removeClass('in');
		
	
	});
}
}


/*function fun_change_password()
{
var flg=true;
var strValold = $('#old_password').val();

var strMD5old = $.md5(strValold);
$scope.old_password=strMD5old;
$http({
	url : 'checkpass',
	method : "POST",
	params : {id:$scope.formData1.id},
}).then(function(response) {
	if(response.data.checkPassword.password!=$scope.old_password)
		{
		$scope.old_pass_miss2=true;
		flg=false;
		}
	else
		{
		$scope.old_pass_miss2=false;
       flg=true
		}
	
	return flg;
});
	

}*/


function popup_form_validation()
{
	flag=true;
	if($scope.formData1.old_password=="")
		{
		$("#form_div_old_password").addClass("has-error");
		$("#form_div_old_password_error").show();
		flag = false;
		}
	else
		{
		$("#form_div_old_password").removeClass("has-error");
		$("#form_div_old_password_error").hide();
		}
	if($scope.formData1.new_password=="")
	{
	$("#form_div_new_password").addClass("has-error");
	$("#form_div_new_password_error").show();
	flag = false;
	}
else
	{
	$("#form_div_new_password").removeClass("has-error");
	$("#form_div_new_password_error").hide();
	}
	
	if($scope.formData1.new_password!=$scope.formData1.cnfrm_password && $scope.formData.password!=undefined && $scope.formData1.new_password!="")

		{
		$scope.cnfrm_pass_miss2=true;
		flag=false;
		}
	else
		{
		$scope.cnfrm_pass_miss2=false;

		}
	
	var strValold = $('#old_password').val();

	var strMD5old = $.md5(strValold);
	$scope.old_password=strMD5old;
	if($scope.old_pass!=$scope.old_password && $scope.formData1.old_password!="")
			{
			$scope.old_pass_miss2=true;
			flag=false;
			}
		else
			{
			$scope.old_pass_miss2=false;
			
			}
		
		
	
	
	return flag;
}


	
	
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
		if(!$scope.hide_code_existing_er){
			flg=false;
			$("#code").select();
			
			}
	}
			 
		if(validation() == false){
		flg = false;	}
		if($scope.formData.user_group_id == ""){
			$("#form_div_user_group_id").addClass("has-error");
			$("#form_div_user_group_id_error").show();
			flg = false;
		}else{
			$("#form_div_user_group_id").removeClass("has-error");
			$("#form_div_user_group_id_error").hide();
		}
		if($scope.formData.password !=$scope.cnfrm_password && $scope.formData.password!=undefined && $scope.formData.id==undefined){
			$scope.pass_miss=true;
			flg = false;
		}else{
			$scope.pass_miss=false;
		}
		
		if($("#email").val() == null || $("#email").val() == ""){
			$("#form_div_email").removeClass("has-error");
			$("#form_div_email_error").hide();
		}
		/*if(validation() == false){
			flg = false;
		}*/
		
		if( $scope.formData.valid_from > $scope.formData.valid_to){
			$scope.date=true;
			flg = false;
		}else{
			$scope.date=false;
		}
		if($scope.formData.id==undefined){
			if($("#password").val()=="")
				{
				$("#form_div_password").addClass("has-error");
				$("#form_div_password_error").show();
				flg = false;
				}
			else
				{
				$("#form_div_password").removeClass("has-error");
				$("#form_div_password_error").hide();
				}
			
			
			
		}
		
		
		if(validateEmail()==false)
			{
			flg = false;

			}
		if((companytype==0))
		   {	
		 if($scope.shopIdList.length==0)
			{
			$scope.tableShwerr=true;
			flg = false;
			//$scope.selectedIndexTab=1;
			}
		   }
		if(flg==false)
			{
			focus();
			}
	
	return flg;

}

	
	
	
	function function_clear_select(){
		$scope.cnfrm_password="";
		$scope.namehide=true;
		$("#form_div_user_group_id").removeClass("has-error");
		$("#form_div_user_group_id_error").hide();
		$("#form_div_password").removeClass("has-error");
		$("#form_div_password_error").hide();
		$("#form_div_email").removeClass("has-error");
		$("#form_div_email_error").hide();
		//$("#form_div_department_id").removeClass("has-error");
		//$("#form_div_department_id_error").hide();
		$scope.chgpass=false;
	}
	
	
	
}

	
	
	