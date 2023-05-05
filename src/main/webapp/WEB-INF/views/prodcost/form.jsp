<div class="" id="prodcost_form_div" ng-show="show_form">  

	<form class="form-horizontal" id="prodcost_form">
	<jsp:directive.include file="../common/includes/common_form.jsp" />
	
		<div class="form-group" id="form_div_cost_type">
		<label for="cost_type" class="col-sm-2 control-label"><spring:message
				code="prodcuctioncost.cost_type"></spring:message><i class="text-danger">*</i> </label>
			<div class="col-sm-5">
				<div class="input-group">
				 <select class="form-control selectpicker" id="cost_type" name="cost_type" ng-disabled="disable_all" ng-model="formData.cost_type">
					<option value=""  ng-disabled="true">select</option><c:forEach items="${enumValues}" var="enumValue">
					<option value="${enumValue.getProdCostTypeid()}">${enumValue.getName() }</option>
					</c:forEach>
				</select>
				<span class="input-group-addon"
		id="form_div_cost_type_error" style="display: none;"><i
		class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
		data-placement="bottom" title=""
		data-original-title="<spring:message code="prodcost.error.cost_type"></spring:message>"></i></span>
				</div>
		   </div>
	   </div>
</form>		
</div>	
	