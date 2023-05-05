<div class="" id="sale_item_choices_form_div" ng-show="show_form">
	<form class="form-horizontal" id="sale_item_choices_form">
		<div class="">
		<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id">
				
				
	<%-- 	<div class="form-group" id="form_div_sale_item">
				<label for="sale_item" class="col-sm-2 control-label"><spring:message
						code="sale_item_choices.sale_item"></spring:message> <i class="text-danger">*</i>
				</label>
				<div class="col-sm-3">
					<div class="input-group">




						<select class="form-control selectpicker " id="sale_item"
							name="state"
							ng-options="v.id as v.name for v in saleItem" id="sale_item"
							ng-model="formData.sale_item_id" ng-disabled="disable_all">
						</select> <span class="input-group-addon" id="form_div_sale_item_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="sale_item_choices.error.saleItem"></spring:message>"></i></span>
					</div>
				</div>
			</div> --%>
			
			<div class="form-group" id="form_div_sale_item_code">
					<label for="sale_item_code" class="col-sm-2 control-label">Sale Item<i class="text-danger">*</i></label>
					<div class="col-sm-6">
						<div class="input-group col-sm-10 classItem1">
							<input type="hidden" name="sale_item_id" id="sale_item_id"
								ng-model="formData.sale_item_id"> <input type="text"
								class="form-control widthset1" name="sale_item_code"
								id="sale_item_code" ng-model="formData.sale_item_code"
								ng-disabled="disable_all" placeholder="Code">
								<input type="text" style="margin-left: 6px;"
								id="sale_item_name" name="source_item_name" class="form-control widthset"
								ng-model="formData.sale_item_name" disabled="disabled">
						</div>
					</div>

				</div> 

		<%-- 	<div class="form-group" id="form_div_choice_item">
				<label for="choice_item" class="col-sm-2 control-label"><spring:message
						code="sale_item_choices.choice_item"></spring:message> <i class="text-danger">*</i></label>
				<div class="col-sm-3">
					<div class="input-group">



						<select class=" form-control selectpicker " id="choice_item" name="choice_item" 
							ng-model="formData.choice_id"
							ng-options="v.id as v.name for v in choiceItem" 
							 ng-disabled="disable_all"
							
							
							>
						</select><span class="input-group-addon" id="form_div_choice_item_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="sale_item_choices.error.choiceItem"></spring:message>"></i></span>




					</div>
				</div>
			</div> --%>
			
			<div class="form-group" id="form_div_choice_item_code">
					<label for="choice_item_code" class="col-sm-2 control-label">Choice Item<i class="text-danger">*</i></label>
					<div class="col-sm-6">
						<div class="input-group col-sm-10 classItem1">
							<input type="hidden" name="choice_id" id="choice_id"
								ng-model="choice_id"> <input type="text"
								class="form-control widthset1" name="choice_item_code"
								id="choice_item_code" ng-model="formData.choice_item_code"
								ng-disabled="disable_all" placeholder="Code">
								<input type="text" style="margin-left: 6px;"
								id="choice_item_name" name="choice_item_name" class="form-control widthset "
								ng-model="formData.choice_item_name" disabled="disabled">
						</div>
					</div>

				</div> 





			<div class="form-group" id="form_div_free_items">
				<label for="free_items" class="col-sm-2 control-label"><spring:message
						code="sale_item_choices.free_items"></spring:message> <i class="text-danger">*</i></label>
				<div class="col-sm-3">
					<div class="input-group">
						<input valid-number type="text" class="form-control required" name="free_items"
							maxlength="9" id="free_items" ng-model="formData.free_items"
							ng-disabled="disable_all" placeholder="" numbers-only> 
							
							<span
							class="input-group-addon" id="form_div_free_items_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="sale_item_choices.error.free_items"></spring:message>"></i></span>
							
					</div>
				</div>
			</div>
			
			
			

			
			
			<div class="form-group" id="form_div_nolimit">
				<label for="nolimit" class="col-sm-2 control-label"><spring:message
						code="sale_item_choices.nolimit"></spring:message> </label>
				<div class="col-sm-5">
					<div class="input-group" id="nolimit">

						<md-checkbox type="checkbox" ng-model="nolimit" ng-click="clearMax()"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="nolimit" class="chck_box_div"></md-checkbox>




					</div>
				</div>
			</div>		
		
		<div class="form-group" id="form_div_max_items">
				<label for="max_items" class="col-sm-2 control-label"><spring:message
						code="sale_item_choices.maxtItem"></spring:message><i class="text-danger">*</i> </label>
				<div class="col-sm-3">
					<div class="input-group">
						<input numbers-only type="text" class="form-control required" name="max_items"
							maxlength="11" id="max_items" ng-model="formData.max_items"
							ng-disabled="disable_max" placeholder="" > <span
							class="input-group-addon" id="form_div_max_items_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="sale_item_choices.error.max_items"></spring:message>"></i></span>
					</div>
				</div>
			</div>
		
		

			
		</div>
	</form>
</div>