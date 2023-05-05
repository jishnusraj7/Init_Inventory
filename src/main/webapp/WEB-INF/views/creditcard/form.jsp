<div class="" id="credit_card_form_div" ng-show="show_form">
	<form class="form-horizontal" id="credit_card_form">
		<div class="">
			<jsp:directive.include file="../common/includes/common_form.jsp" />

			
		</div>
		
		<div class="form-group" id="form_div_IIN_Prefix_Range">
				<label for="IIN_Prefix_Range" class="col-sm-2 control-label "><spring:message
						code="creditcard.IIN_Prefix_Range"></spring:message> </label>
		      <div class="col-sm-6">
		      
		       <div class="col-sm-4 leftdiv">
					    <div class="col-sm-12 remvpadding">
					   
						<input type="text" class="form-control   "
							name="name" id="name" ng-model="formData.iin_prefix_range_from"
							ng-disabled="disable_all" maxlength="19" placeholder="" numbers-only>
						 </div>
				</div>
				
				<div class="col-sm-1 ">
				<strong>-</strong>
				</div>
				
				<div class="col-sm-4 leftdiv">
				    	<div class="col-sm-12 remvpadding">	
				    
						   <input type="text" class="form-control   "
							name="name" id="name" ng-model="formData.iin_prefix_range_to"
							ng-disabled="disable_all" maxlength="19" placeholder="" numbers-only>
							</div>
			</div>
	  </div>				
	</div>	
	
	
	
	
	     <div class="form-group" id="form_div_account_code">
				<label for="account_code" class="col-sm-2 control-label"><spring:message
						code="creditcard.account_code"></spring:message> </label>
				<div class="col-sm-3">
					<div class="input-group">
						<input  type="text" class="form-control " name="account_code"
							maxlength="250" id="account_code" ng-model="formData.account_code"
							ng-disabled="disable_all" placeholder="" > 
							
					</div>
				</div>
	     </div>
	     
	     
	     <div class="form-group" id="form_div_is_refundable" >
				<label for="is_refundable" class="col-sm-2 control-label"><spring:message
						code="creditcard.is_refundable"></spring:message> </label>
				<div class="col-sm-5">
					<div class="input-group" id="is_refundable">

						<md-checkbox type="checkbox" ng-model="formData.is_refundable" ng-click="refundMethod()"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="is_refundable" class="chck_box_div"></md-checkbox>




					</div>
				</div>
			</div>		
			
			
			
			
			<div class="form-group" id="form_div_alternative_refund_method" ng-show="selectShow">
				<label for="alternative_refund_method" class="col-sm-2 control-label"><spring:message
						code="creditcard.alternative_refund_method"></spring:message> <i class="text-danger">*</i>
				</label>
				<div class="col-sm-3">
					<div class="input-group">
						<!-- <select class="form-control selectpicker " id="alternative_refund_method"
							name="alternative_refund_method"
							ng-options="v.id as v.name for v in saleItem" id="alternative_refund_method"
							ng-model="formData.alternative_refund_method" ng-disabled="disable_all">
						</select> --> 
						
				 <select class="form-control selectpicker" id="alternative_refund_method" name="alternative_refund_method" ng-disabled="disable_all" ng-model="formData.alternative_refund_method">
					<option value="" selected>select</option>
					<option value="1">Cash</option>
				
				</select>
						
						<span class="input-group-addon" id="form_div_alternative_refund_method_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="sale_item_choices.error.saleItem"></spring:message>"></i></span>
					</div>
				</div>
			 </div>
			
			
			
			
			<div class="form-group" id="form_div_is_valid">
				<label for="is_valid" class="col-sm-2 control-label"><spring:message
						code="creditcard.is_valid"></spring:message> </label>
				<div class="col-sm-5">
					<div class="input-group" id="is_valid">

						<md-checkbox type="checkbox" ng-model="formData.is_valid" 
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="is_valid" class="chck_box_div"></md-checkbox>




					</div>
				</div>
			</div>		
					
		
	</form>
</div>