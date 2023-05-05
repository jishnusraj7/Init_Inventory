<div class="" id="item_category_form_div" ng-show="show_form">
	<form class="form-horizontal" id="item_category_form">
		<div class="">
			<jsp:directive.include file="../common/includes/common_form.jsp" />

			<div class="form-group" id="form_div_parent_id">
				<label for="parent_id" class="col-sm-2 control-label"><spring:message
						code="item_category.parent_id"></spring:message> </label>
				<div class="col-sm-5">
					<div class="input-group">
						<select class="form-control selectpicker" id="parent_id"
							name="parent_id" ng-options="v.id as v.name for v in parnetItem"
							id="parent_id" ng-model="formData.parent_id"
							ng-disabled="disable_all">
						</select>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>