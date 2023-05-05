
//Controller for Table and Form 
mrpApp.controller('systemsettingsctrl', systemsettingsctrl);


function systemsettingsctrl($scope, $http, $mdDialog ,$rootScope,$controller,MRP_CONSTANT,FORM_MESSAGES,ITEM_TABLE_MESSAGES,RECORD_STATUS) {
	$controller('DatatableController', {$scope: $scope});
/*	set_sub_menu("#settings");						
	setMenuSelected("#systemsettings_left_menu");*/
	manageButtons("view");
	$('.position_prev_next_btn_form').hide();
	$(".btnBack").hide();
	$(".btnDiscard").hide();
	
	
	$scope.Quetable={};
	$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};
	
	
	$scope.disable_all=true;
	$scope.formData = {};
	$scope.formData2 = {};

	
	$scope.shopSub=(settings['currentcompanyid1']!=0)?false:true;
	$scope.stock_entry=(settings['currentcompanyid1']==0)?false:true;
	$scope.paymentModes={};
	$scope.envSerttings={};
	$scope.billParameters={};

	$scope.billParameters.show_tax_summary=0;
	//$scope.formData2.default_taxation_method=0;
	
	/*$scope.currencyDetails=[];
	$http({
		url : '../currencydetails/json',
		method : "GET",
	}).then(function(response) {
		$scope.currencyDetails = response.data.data;});	*/
	
	$scope.envSerttings.is_publish=0;
	$scope.createdBy = strings['userID'];
	$scope.createdAt = moment().format("YYYY-MM-DD hh:mm:ss");
	
	
     $scope.cashAct=false;
     $scope.cashRefund=false;
     $scope.cashRefundAct=false;
     $scope.cashRound=false;
     $scope.cashLabel=false;
     
     $scope.paymentModes.can_pay_by_cash=false;
     $scope.paymentModes.can_cash_refundable=false;
     $scope.paymentModes.can_cash_round=false;
     
   
	
	$scope.showCashDet=function()
	{
		if($scope.paymentModes.can_pay_by_cash==false)
			{
			$scope.cashAct=true;
			$scope.cashRefund=true;
			$scope.cashRound=true;
		    $scope.paymentModes.can_cash_round=true;
			}
		else
			{
			$scope.cashAct=false;
			$scope.cashRefund=false;
			$scope.cashRound=false;
			$scope.paymentModes.can_cash_round=false;
			 $scope.paymentModes.can_cash_refundable=false;
			$scope.cashRefundAct=false;
			$scope.cashLabel=false;
			$scope.paymentModes.cash_account_no="";
			$scope.paymentModes.cash_refund_account_no="";
			
			}
		
	}
	
	$scope.showCashRefundDet=function()
	{
		if($scope.paymentModes.can_cash_refundable==false)
		{
		$scope.cashRefundAct=true;
		$scope.cashLabel=true;
		
		}
	else
		{
		$scope.cashRefundAct=false;
		$scope.cashLabel=false;
		$scope.paymentModes.cash_refund_account_no="";
		
		}
		
	}
	
	
	 $scope.companyRefund=false;
     $scope.companyRound=false;
     $scope.companyLabel=false;
     
     $scope.paymentModes.can_pay_by_company=false;
     $scope.paymentModes.can_company_refundable=false;
     $scope.paymentModes.can_company_round=false;
     
     
     
	
	$scope.showCompanyDet=function()
	{
		if($scope.paymentModes.can_pay_by_company==false)
			{
			
			$scope.companyRound=true;
			$scope.companyRefund=true;
		    $scope.paymentModes.can_company_round=true;
			}
		else
			{
			$scope.companyRound=false;
			$scope.companyRefund=false;
			 $scope.companyLabel=false;
		    $scope.paymentModes.can_company_round=false;
		    $scope.paymentModes.can_company_refundable=false;
			}
		
	}
	
	$scope.showCompanyRefundDet=function()
	{
		if($scope.paymentModes.can_company_refundable==false)
		{
			 $scope.companyLabel=true;
		
		}
	else
		{
		 $scope.companyLabel=false;
		
		}
		
	}
	
	
	 $scope.voucherRefund=false;
     $scope.voucherRound=false;
     $scope.voucherLabel=false;
     
     $scope.paymentModes.can_pay_by_vouchers=false;
     $scope.paymentModes.can_voucher_refundable=false;
     $scope.paymentModes.can_voucher_round=false;
     
	
	$scope.showVoucherDet=function()
	{
		if($scope.paymentModes.can_pay_by_vouchers==false)
			{
			
			 $scope.voucherRefund=true;
		     $scope.voucherRound=true;
		    $scope.paymentModes.can_voucher_round=true;
			}
		else
			{
			$scope.voucherRefund=false;
		     $scope.voucherRound=false;
		     $scope.voucherLabel=false;
		     $scope.paymentModes.can_voucher_refundable=false;
		     $scope.paymentModes.can_voucher_round=false;
		     
			}
		
	}
	
	$scope.showVoucherRefundDet=function()
	{
		if($scope.paymentModes.can_voucher_refundable==false)
		{
			 $scope.voucherLabel=true;
		
		}
	else
		{
		 $scope.voucherLabel=false;
		
		}
		
	}
	
	
	
	
	
	
	 $scope.cardRefund=false;
     $scope.cardRound=false;
     $scope.cardLabel=false;
     
     $scope.paymentModes.can_pay_by_card=false;
     $scope.paymentModes.can_card_refundable=false;
     $scope.paymentModes.can_card_round=false;
     
	
	$scope.showCardDet=function()
	{
		if($scope.paymentModes.can_pay_by_card==false)
			{
			
			 $scope.cardRefund=true;
		     $scope.cardRound=true;
		    $scope.paymentModes.can_card_round=false;
		    $scope.Card_disable=true;
			}
		else
			{
			$scope.cardRefund=false;
		     $scope.cardRound=false;
		     $scope.cardLabel=false;
		     $scope.paymentModes.can_card_refundable=false;
		     $scope.paymentModes.can_card_round=true;
		     $scope.Card_disable=true;
		     
			}
		
	}
	
	$scope.showCardRefundDet=function()
	{
		if($scope.paymentModes.can_card_refundable==false)
		{ 	     $scope.paymentModes.can_card_round=true;
			 $scope.cardLabel=true;
			 $scope.Card_disable=false;
		}
	else
		{
		 $scope.cardLabel=false;
	     $scope.paymentModes.can_card_round=false;
		 $scope.Card_disable=true;
		}
		
	}

	$scope.customer_types=[];
	$scope.formData.default_customer_type=" ";
	$scope.formData.is_direct_stock_entry=0;

	$http({
		url : 'getDropdown',
		method : "GET",
	}).then(function(response) {
		
		$scope.customer_types=response.data.customer_types;
		$scope.customer_types.splice(0,0,{id : " " ,name : "select"});
		$scope.formData.default_customer_type=" ";
		
		
	});
	
	
	
	 $scope.companyprofileListItem=function()
	{$scope.formData = {};
	$http({
		url : 'getFormData',
		method : "GET",
	}).then(function(response) {
		 if(response.data.editDetsys.length!=0)
	    	{
			 $scope.formData = response.data.editDetsys[0];
	    	}
		 if(response.data.editDetTaxParam.length!=0)
	    	{
			  $scope.formData2=response.data.editDetTaxParam[0];
			  }
	  
	    if(response.data.billparameters.length!=0)
	    	{
	    	 $scope.billParameters=response.data.billparameters[0];
/*	 		$scope.billParameters.show_tax_summary=($scope.billParameters.show_tax_summary==1)?true:false;
*/	    	}
	    
	    if(response.data.paymentModes.length!=0)
	    	{
	    
	    
	    $scope.paymentModes=response.data.paymentModes[0];
	 	 
	 	$scope.paymentModes.can_pay_by_cash=($scope.paymentModes.can_pay_by_cash==1)?true:false;
		$scope.paymentModes.can_pay_by_company=($scope.paymentModes.can_pay_by_company==1)?true:false;
		$scope.paymentModes.can_pay_by_vouchers=($scope.paymentModes.can_pay_by_vouchers==1)?true:false;
		$scope.paymentModes.can_pay_by_card=($scope.paymentModes.can_pay_by_card==1)?true:false;
		$scope.paymentModes.can_cash_refundable=($scope.paymentModes.can_cash_refundable==1)?true:false;
		$scope.paymentModes.can_company_refundable=($scope.paymentModes.can_company_refundable==1)?true:false;
		$scope.paymentModes.can_voucher_refundable=($scope.paymentModes.can_voucher_refundable==1)?true:false;
		$scope.paymentModes.can_card_refundable=($scope.paymentModes.can_card_refundable==1)?true:false;
		$scope.paymentModes.can_cash_round=($scope.paymentModes.can_cash_round==1)?true:false;
		$scope.paymentModes.can_company_round=($scope.paymentModes.can_company_round==1)?true:false;
		$scope.paymentModes.can_voucher_round=($scope.paymentModes.can_voucher_round==1)?true:false;
		$scope.paymentModes.can_card_round=($scope.paymentModes.can_card_round==1)?true:false;
		
		if($scope.paymentModes.can_pay_by_cash==true)
			{
			$scope.cashAct=true;
			$scope.cashRefund=true;
			$scope.cashRound=true;
			}
		if($scope.paymentModes.can_pay_by_company==true)
			{
			$scope.companyRound=true;
			$scope.companyRefund=true;
			}
	        
		if($scope.paymentModes.can_pay_by_vouchers==true)
			{
			$scope.voucherRefund=true;
		     $scope.voucherRound=true;
			}
		
		if($scope.paymentModes.can_pay_by_card==true)
		{
			$scope.cardRefund=true;
		     $scope.cardRound=true;
		}
		
		if($scope.paymentModes.can_cash_refundable==true)
		{
			$scope.cashRefundAct=true;
			$scope.cashLabel=true;
		}
		if($scope.paymentModes.can_company_refundable==true)
		{
			$scope.companyLabel=true;

		}
		if($scope.paymentModes.can_voucher_refundable==true)
		{
			 $scope.voucherLabel=true;
		}
		if($scope.paymentModes.can_card_refundable==true)
		{
			$scope.cardLabel=true;
		}
		
		
		
	    
	   }
	    
	    
	    
	    
	    
	    if(settings['currentcompanyid1']==0)
	    	{
	    if(response.data.envSerttings.length!=0)
    	{
		  $scope.envSerttings=response.data.envSerttings[0];
	 		$scope.envSerttings.is_publish=($scope.envSerttings.is_publish==1)?true:false;

		  }

	   	}
	});}
	 $scope.companyprofileListItem();	
//	Save Function	
	$rootScope.$on('fun_save_data',function(event){		
		if (code_existing_validation($scope.formData)) {	
			
			$("#msg").hide();
			$("#form_div_decimal_places").removeClass("has-error");
			$("#form_div_emailid").removeClass("has-error");
			
			
			
			$scope.Quetable.shopId=settings['currentcompanyid1'];
			$scope.Quetable.origin=settings['currentcompanycode1'];
		    $scope.Quetable.curdAction=($scope.formData.id=="" || $scope.formData.id==undefined )?"C":"U";
			  
			
			
/*			$scope.billParameters.show_tax_summary=($scope.billParameters.show_tax_summary==true)?"1":"0";
*/			$scope.envSerttings.is_publish=($scope.envSerttings.is_publish==true)?"1":"0";
			
			$scope.paymentModes.can_pay_by_cash=($scope.paymentModes.can_pay_by_cash==true)?"1":"0";
			$scope.paymentModes.can_pay_by_company=($scope.paymentModes.can_pay_by_company==true)?"1":"0";
			$scope.paymentModes.can_pay_by_vouchers=($scope.paymentModes.can_pay_by_vouchers==true)?"1":"0";
			$scope.paymentModes.can_pay_by_card=($scope.paymentModes.can_pay_by_card==true)?"1":"0";
			$scope.paymentModes.can_cash_refundable=($scope.paymentModes.can_cash_refundable==true)?"1":"0";
			$scope.paymentModes.can_company_refundable=($scope.paymentModes.can_company_refundable==true)?"1":"0";
			$scope.paymentModes.can_voucher_refundable=($scope.paymentModes.can_voucher_refundable==true)?"1":"0";
			$scope.paymentModes.can_card_refundable=($scope.paymentModes.can_card_refundable==true)?"1":"0";
			$scope.paymentModes.can_cash_round=($scope.paymentModes.can_cash_round==true)?"1":"0";
			$scope.paymentModes.can_company_round=($scope.paymentModes.can_company_round==true)?"1":"0";
			$scope.paymentModes.can_voucher_round=($scope.paymentModes.can_voucher_round==true)?"1":"0";
			$scope.paymentModes.can_card_round=($scope.paymentModes.can_card_round==true)?"1":"0";

		    
		    if($scope.billParameters.id!="" && $scope.billParameters.id!=undefined)
	    	{
	    	$scope.billParameters.updated_at =$scope.createdAt;
	    	$scope.billParameters.updated_by =$scope.createdBy;
	    	}
	    else
	    	{
	    	$scope.billParameters.created_at = $scope.createdAt;
		    $scope.billParameters.created_by =  $scope.createdBy;
	    	}
			
		    
		    if($scope.formData.id!="" && $scope.formData.id!=undefined)
		    	{
		    	$scope.formData.updated_at =$scope.createdAt;
		    	$scope.formData.updated_by =$scope.createdBy;
		    	}
		    else
		    	{
		    	$scope.formData.created_at = $scope.createdAt;
			    $scope.formData.created_by =  $scope.createdBy;
		    	}
		    
		    if($scope.paymentModes.id!="" && $scope.paymentModes.id!=undefined)
	    	{
	    	$scope.paymentModes.updated_at =$scope.createdAt;
	    	$scope.paymentModes.updated_by =$scope.createdBy;
	    	}
	    else
	    	{
	    	$scope.paymentModes.created_at = $scope.createdAt;
		    $scope.paymentModes.created_by =  $scope.createdBy;
	    	}
		    
		    
			$scope.formData.stockDetailLists=$scope.formData2;
			$scope.formData.billParameters=$scope.billParameters;
			$scope.formData.envSerttings=$scope.envSerttings;
			$scope.formData.paymentModes=$scope.paymentModes;
			$scope.formData.Quetable=$scope.Quetable;
			
			$scope.formData=JSON.stringify($scope.formData);
			
			$http({
				url : 'saveItem',
				method : "POST",
				data : $scope.formData,async:false,
			}).then(function(response) {
				 $rootScope.$broadcast('on_AlertMessage_SUCC',FORM_MESSAGES.UPDATE_SUC);

				manageButtons("view");					
				$scope.disable_all = true;				
				$('.btnDiscard').hide();
				$scope.Quetable={id:"",dateTime:"",syncNow:0,shopId:"",origin:"",curdAction:""};

                $scope.companyprofileListItem();
			});}});
	


//		Discard function	
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
				manageButtons("view");
				$('.position_prev_next_btn_form').hide();
				$(".btnDiscard").hide();
				$("#msg").hide();
				$scope.disable_all=true;
				$scope.companyprofileListItem();	
				clearform();
				$scope.function_clear_select();
			});});
		

		$rootScope.$on("fun_enable_inputs",function(){
			$scope.disable_all = false;
			$(".btnBackr").hide();
			$(".btnDiscard").show();
			
		});

// Validation 

		function code_existing_validation(data){
			var flg = true;			 
			if(validation() == false){
				flg = false;}
			if(validateEmail() == false){
					flg = false;}	
			if($scope.formData.decimal_places > 5)
				{
				$("#msg").text('Please enter decimal place less than 6');
				$("#msg").show();
				$("#form_div_decimal_places").addClass("has-error");
				flg=false;
				}
			
	
			if($scope.formData.decimal_places > 5)
			{
			$("#msg").text('Please enter decimal place less than 6');
			$("#msg").show();
			$("#form_div_decimal_places").addClass("has-error");
			flg=false;
			}
			
			
			
			return flg;
			
		
		}


	
	$scope.function_clear_select=function(){
		$("#form_div_emailid").removeClass("has-error");
		$("#form_div_emailid_error").hide();
		$("#form_div_decimal_places").removeClass("has-error");
		$("#msg").hide();
		
		
	}
	

}





