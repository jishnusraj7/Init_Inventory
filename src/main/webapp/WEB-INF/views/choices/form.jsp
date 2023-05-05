<div class="" id="department_form_div" ng-show="show_form">  

	<form class="form-horizontal" id="department_form">
	<jsp:directive.include file="../common/includes/common_form.jsp" />
	
	<div class="form-group" id="form_div_setasinactive">
				<label for="setasinactive" class="col-sm-2 control-label">Is Global<!-- <spring:message
						code="supplier.setasinactive"></spring:message> --> </label>
				<div class="col-sm-5">
					<div class="input-group" id="is_active">

						<md-checkbox type="checkbox" ng-model="formData.is_global"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="setasinactive" class="chck_box_div"></md-checkbox>



					</div>
				</div>
			</div>
		
		
		
</form>		
</div>	
	