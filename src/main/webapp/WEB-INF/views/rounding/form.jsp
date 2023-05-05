<div class="" id="item_category_form_div" ng-show="show_form">
	<form class="form-horizontal" id="item_category_form">
			<div class="">
				<jsp:directive.include file="../common/includes/common_form.jsp" />
				
			</div>	
		
			<div class="form-group" id="form_div_rounding_to">
	<label for="card_no" class="col-sm-2 control-label"><spring:message
			code="rounding.roundTo"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="text"   maxlength="8" ng-disabled="disable_all" class="form-control required algn_rght" name="" id="rounding_to" valid-number ng-model="formData.round_to" >
                    <span class="input-group-addon" id="form_div_rounding_to_error" style="display:none">
		              <i class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
		                    data-placement="bottom" title=""
		                    data-original-title="<spring:message code="rounding.error.roundingto"></spring:message>"></i>
                     </span>

		</div>
	</div>

</div>		
		
		
	</form>
</div>