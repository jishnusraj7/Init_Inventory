<div class="" id="department_form_div" ng-show="show_form">  

	<form class="form-horizontal" id="department_form">
	<jsp:directive.include file="../common/includes/common_form.jsp" />
	
		<div class="form-group" id="form_div_dept_type">
		<label for="dept_type" class="col-sm-2 control-label"><spring:message
				code="department.dept_type"></spring:message><i class="text-danger">*</i> </label>
			<div class="col-sm-5">
				<div class="input-group">
				 <select class="form-control selectpicker" id="dept_type" name="dept_type" ng-disabled="disable_all" ng-model="formData.dept_type">
					<option value=""  ng-disabled="true">select</option><c:forEach items="${enumValues}" var="enumValue">
					<option value="${enumValue.getStoreLocid()}">${enumValue.getName() }</option>
					</c:forEach>
				</select>
				<span class="input-group-addon"
		id="form_div_dept_type_error" style="display: none;"><i
		class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
		data-placement="bottom" title=""
		data-original-title="<spring:message code="department.error.dept_type"></spring:message>"></i></span>
				</div>
		   </div>
	   </div>
</form>		
</div>	
	