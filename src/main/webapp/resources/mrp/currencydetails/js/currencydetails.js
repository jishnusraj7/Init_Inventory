//Controller for Table and Form
mrpApp.controller('currency_details', item_category);

function item_category($controller,$compile,$timeout,$scope, $http, $mdDialog ,$rootScope, DTOptionsBuilder,DTColumnBuilder,
        MRP_CONSTANT,DATATABLE_CONSTANT,FORM_MESSAGES) {
   
    $controller('DatatableController', {$scope: $scope});
    $("#form_div_description").hide();

   /* set_sub_menu("#settings");                       
    setMenuSelected("#currency_details_left_menu");      */      //active leftmenu
    manageButtons("add");
   
//    generate number
    $scope.fun_get_currencyCode=function(){
        $http.get('getCounterPrefix')
        .success(function (response) {
            $scope.formData.code = response;});}
    
    
    $scope.roundingList=[];
    
 //check base currency exist
    
    $http({
		url : 'formJsonData',
		method : "GET",
		  
	}).then(function(response) {
		
		 $scope.roundingList=response.data.roundingData;
			$scope.roundingList.splice(0,0,{id : "" ,name : "select"});
	});
    
    $scope.fun_get_baseCurrency=function(){
        $http.get('basecurrencyExisting')
        .success(function (response) {
            $scope.isBaseCurrncy = response;
            if(response>0)
            	{
            //	$scope.formData.is_base_currency=false;
            	$scope.chkbox=true;
            	}
          
        	  if($scope.formData.is_base_currency==true)
        		  {
        		  $scope.chkbox=false;
        		  }
        	  
            
        });}
    
   
    $scope.formData = {};
    $scope.show_table=true;
    $scope.show_form=false;
    $scope.hide_code_existing_er = true;
    $scope.decimalPlace = settings['decimalPlace'];
    $scope.formData.is_base_currency=false;
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
    var vm = this;
    vm.edit = edit;
    vm.reloadData = reloadData;
    vm.showTable = showTable;
    vm.view_mode_aftr_edit = view_mode_aftr_edit;
    vm.code_existing_validation = code_existing_validation;
   
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
                }else if(sourse=="DISCARD"){   
                    $scope.discardData();
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

    vm.dtInstance = {};   
    var DataObj = {};       
    DataObj.sourceUrl = "getDataTableData";
    DataObj.infoCallback = infoCallback;
    DataObj.rowCallback = rowCallback;
    vm.dtOptions = $scope.getMRP_DataTable_dtOptions(vm,DataObj);    
    vm.dtColumns = [
            //DTColumnBuilder.newColumn('id').withTitle('ID').notVisible().withOption('searchable','false'),
            DTColumnBuilder.newColumn('code').withTitle('CODE').withOption('type', 'natural').renderWith(
                       function(data, type, full, meta) {
	               			if(full.quetableId!=""){css="queTableInColor"}else{css="queTableOutColor"}

                              return urlFormater(data); 
                        }),
            DTColumnBuilder.newColumn('name').withTitle('NAME').renderWith(
                    function(data, type, full, meta) {
                         return type === 'display' && data.length > 10 ?
                                    data.substr( 0, 20 ) +'…' :
                                    data;
                    }),
            DTColumnBuilder.newColumn('symbol').withTitle('SYMBOL').withOption('width','85px'),
            DTColumnBuilder.newColumn('fraction_name').withTitle('').notVisible().withOption('searchable','false'),
            DTColumnBuilder.newColumn('fraction_symbol').withTitle('').notVisible().withOption('searchable','false'),
            DTColumnBuilder.newColumn('decimal_places').withTitle('').notVisible().withOption('searchable','false'),
            DTColumnBuilder.newColumn('is_base_currency').withTitle('').notVisible().withOption('searchable','false'),
            DTColumnBuilder.newColumn('exchange_rate').withTitle('').notVisible().withOption('searchable','false'),
            DTColumnBuilder.newColumn('exchange_rate_at').withTitle('').notVisible().withOption('searchable','false'),
            DTColumnBuilder.newColumn('rounding_id').withTitle('').notVisible().withOption('searchable','false'),
            
            

         ];
   
    function urlFormater(data) {
          return '<a id="rcd_edit" class="'+css+'" ng-click="show_table = false;show_form=true;" style="cursor:pointer;"  ng-href="#">'
          + data + '</a>';  
        }
   
    function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {                    //Rowcallback fun for Get Edit Data when clk on Code
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

   
    $rootScope.$on('reloadDatatable',function(event){                    //reload Datatable
        reloadData();
    });
   
    $rootScope.$on('hide_table',function(event){
        showTable(event);
    });
   
    
    $scope.setEchgRate=function()
	{
	
    if($scope.formData.is_base_currency==true)
    	{
    	$scope.formData.exchange_rate="";
		$scope.disable_echgRate=false;
    	
    	}
    else
    	{
    	$scope.formData.exchange_rate=1;
		$scope.disable_echgRate=true;

    	}
		
	}
	
    
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
 
        $scope.formData = {id:row_data.id,name:row_data.name,code:row_data.code,rounding_id:row_data.rounding_id,symbol:row_data.symbol,fraction_name:row_data.fraction_name,fraction_symbol:row_data.fraction_symbol,decimal_places:row_data.decimal_places,exchange_rate:row_data.exchange_rate,exchange_rate_at:row_data.exchange_rate_at};
        $scope.createdAt = row_data.created_at;
        $scope.createdBy = row_data.created_by;
        $scope.fun_get_baseCurrency();
        if(row_data.is_base_currency==1)
        	{
        	$scope.formData.is_base_currency=true;
        	$scope.chkbox=false;
        	}
        if( $scope.formData.exchange_rate_at!="")
        	{
        	$scope.formData.exchange_rate_at=geteditDateFormat($scope.formData.exchange_rate_at);
        	}
        $scope.disable_all = true;
        $scope.disable_echgRate=true;
        $scope.disable_code=true;
        $scope.hide_code_existing_er = true;
        $scope.function_clear_select();
    }

//    Delete Function
    $rootScope.$on("fun_delete_current_data",function(event){           
        $scope.showConfirm(FORM_MESSAGES.DELETE_WRNG,"DELETE",event);
    });
   
    $scope.deleteData = function(event){

        var current_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
        $http({
            url : 'delete',method : "POST",params : {id:$scope.formData.id},
        }).then(function(response) {
            if(response.data==0){
                $rootScope.$broadcast('on_AlertMessage_ERR',"Currency Details "+FORM_MESSAGES.ALREADY_USE+"");
            }else if(response.data == 1){
                $rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.DELETE_SUC);
$("show_form").val(0);
                $scope.show_table=true;
                $scope.show_form=false;
                manageButtons("add");
                $scope.disable_all = true;
                $scope.disable_echgRate=true;
                $scope.disable_code = true;
                vm.dtInstance.reloadData(null, true);
               /* vm.dtInstance1.reloadData(null, true);*/
                $(".acontainer input").attr('disabled', true);
                reloadData();
            }else{
                $scope.alertBox("Delete failed.");
                }

        }, function(response) { // optional
            $scope.alertBox("Delete failed.");
        });
    }

    function view_mode_aftr_edit(){
        var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
        $rootScope.$emit("show_edit_btn_div",cur_row_index);
        $scope.disable_all = true;
        $scope.disable_echgRate=true;
        $scope.disable_code = true;
    }
   
    $rootScope.$on('fun_save_data',function(event){        //Save Function
        if (code_existing_validation($scope.formData)) {
             $scope.formData.created_at = $scope.createdAt;
             $scope.formData.created_by =  $scope.createdBy;
             if($scope.formData.is_base_currency==true)
            	 {
            	 $scope.formData.is_base_currency=1;
            	 }
             else
            	 {
            	 $scope.formData.is_base_currency="";
            	 }
             if($scope.formData.exchange_rate_at!=undefined && $scope.formData.exchange_rate_at!="")
            	 {
              	$scope.formData.exchange_rate_at = getMysqlFormat($scope.formData.exchange_rate_at);
            	 }
             
             $scope.Quetable.shopId=settings['currentcompanyid1'];
				$scope.Quetable.origin=settings['currentcompanycode1'];
			$scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
             
             
            $http({
                url : 'saveCur',
                method : "POST",
                params : $scope.formData,
                data : {Quetable:$scope.Quetable},
            }).then(function(response) {
                if(response.data == 1){
                if($scope.formData.id !=undefined){
                    $rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);
                    $scope.formData.exchange_rate_at=geteditDateFormat($scope.formData.exchange_rate_at);
                    
                }else{
                    $rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.SAVE_SUC);
                }
                 if($scope.formData.id !=undefined){
                    view_mode_aftr_edit();
                 }else{
                     $scope.formData ={};
                     $scope.fun_get_currencyCode();
                     $scope.fun_get_baseCurrency();
                 }
                 reloadData();
				$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

                 $scope.hide_code_existing_er = true;
                 if($scope.formData.is_base_currency==1)
            	 {
            	 $scope.formData.is_base_currency=true;
            	 }
                
                
                
                }else{
                    $scope.alertBox("Save failed.");
                }}, function(response) { // optional
                    $scope.alertBox("Save failed.");
            });
        }
    });
   
    $rootScope.$on("fun_discard_form",function(event){                //Discard function
        $scope.showConfirm(FORM_MESSAGES.DISCARD_WRNG,"DISCARD",event);
    });
   
    $scope.discardData = function(event){
    	$("#form_div_rounding_id").removeClass("has-error");
		$("#form_div_rounding_id_error").hide();
        var cur_row_index = parseInt($(".btn_prev").attr("id").split("_")[1]);
        if($scope.formData.id == undefined){
            $scope.formData ={};
            $scope.fun_get_currencyCode();
            $scope.fun_get_baseCurrency();
            $scope.hide_code_existing_er = true;
            $scope.formData.rounding_id="";
        }else{
            var row_data = vm.dtInstance.DataTable.rows(cur_row_index).data();
            edit(row_data[0],cur_row_index);
        }
        clearform();
        $scope.function_clear_select();
       
    }
   
    $rootScope.$on("fun_enable_inputs",function(){
$("#show_form").val(1);
        $scope.disable_all = false;
        $scope.disable_echgRate=false;
    });
   
    $rootScope.$on("fun_enable_inputs_code",function(){
        $scope.disable_code = false;
    });
   
    $rootScope.$on("fun_clear_form",function(){
        $scope.formData = {};
        $scope.fun_get_currencyCode();
        $scope.fun_get_baseCurrency();
        $scope.hide_code_existing_er=true;
        $scope.formData.is_base_currency=false;
		$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
		$scope.formData.rounding_id="";
		$scope.function_clear_select();

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
            if (!$scope.hide_code_existing_er) {
                flg = false;
                $("#code").select();
            }
        }
             
        if(validation() == false){
            flg = false;
        }
        if($scope.formData.rounding_id == "" )
        	{
        	$("#form_div_rounding_id").addClass("has-error");
        	$("#form_div_rounding_id_error").show();
        	flg=false;
        	}else
        		{
        		$("#form_div_rounding_id").removeClass("has-error");
        		$("#form_div_rounding_id_error").hide();
        		}
        if(flg==false){
        focus();
        }
        return flg;
    }
    
    $scope.function_clear_select=function(){
    	$("#form_div_rounding_id").removeClass("has-error");
		$("#form_div_rounding_id_error").hide();
	}

}