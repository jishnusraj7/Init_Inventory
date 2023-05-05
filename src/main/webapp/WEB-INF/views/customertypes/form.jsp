<div class="" id="customertype_form_div" ng-show="show_form">  

	<form class="form-horizontal" id="customertype_form">
	<jsp:directive.include file="../common/includes/common_form.jsp" />
	
		<div class="form-group" id="form_div_price_var">
	<label for="price_var" class="col-sm-2 control-label"><spring:message
			code="customertypes.price"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-5">
		<div class="input-group input-group-lg">
			<input type="text" class="form-control required code-font-size"
				name="price_var" id="price_var" ng-model="formData.price_var"
				ng-disabled="disable_all"  maxlength="${7+settings['decimalPlace']+1}" 
				placeholder="" ng:required select-on-click valid-number>
				<span class="input-group-addon" id="form_div_price_var_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Price variance cannot be left blank"></i></span>
							
		</div>
		<div class="text-danger" id="divErrormsg2"></div>
	</div>
	
</div>
</form>		
</div>	
	