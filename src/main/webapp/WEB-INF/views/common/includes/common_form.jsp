<input type="hidden" id="id" name="id" value="" ng-model="formData.id">
<div class="form-group " id="form_div_code">
	<label for="code" class="col-sm-2 control-label code-lbl-font-size"><spring:message
			code="common.code"></spring:message> <i class="text-danger">*</i></label>
	<div class="col-sm-3">
		<div class="input-group input-group-lg">
			<input type="text" class="form-control required code-font-size" ng-change="isCodeExistis(formData.code)"
				name="code" id="code" ng-model="formData.code" maxlength="10"
				ng-disabled="disable_code" capitalize placeholder=""> <span
				class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_code_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="common.error.code"></spring:message>"></i></span>
		</div>
	</div>
	<div class="col-sm-4">
		<span ng-bind="existing_code" class="existing_code_lbl"
				ng-hide="hide_code_existing_er"></span>
	</div>
</div>
<div class="form-group" id="form_div_name">
	<label for="name" class="col-sm-2 control-label code-lbl-font-size"><spring:message
			code="common.name"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-5">
		<div class="input-group input-group-lg">
			<input type="text" class="form-control required code-font-size"
				name="name" id="name" ng-model="formData.name"
				ng-disabled="disable_all" maxlength="50" placeholder="">
			<span class="input-group-addon" id="form_div_name_error"
				style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
		</div>
	</div>

</div>
<div class="form-group" id="form_div_description">
	<label for="description" class="col-sm-2 control-label"><spring:message
			code="common.description"></spring:message> <i class="text-danger"></i></label>

	<div class="col-sm-5">
		<div class="input-group">
			<textarea rows="" cols="" type="text" class="form-control "
				name="description" ng-model="formData.description"
				ng-disabled="disable_all" maxlength="250" id="description"
				placeholder=""> </textarea>
			<span class="input-group-addon" id="form_div_description_error"
				style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="common.error.description"></spring:message>"></i></span>

		</div>
	</div>

</div>
