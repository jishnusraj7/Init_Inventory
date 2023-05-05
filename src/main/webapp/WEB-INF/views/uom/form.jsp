<div class="" id="item_category_form_div" ng-show="show_form">
	<form class="form-horizontal" id="item_category_form">
			<div class="">
				<jsp:directive.include file="../common/includes/common_form.jsp" />
				
			</div>
			
	<div class="form-group" id="form_div_is_compound">
				<label for="is_compound" class="col-sm-2 control-label"><spring:message
						code="uom.is_compound"></spring:message></label>
				<div class="col-sm-3">
					<div class="input-group" id="is_active">

						<md-checkbox type="checkbox" ng-model="formData.is_compound"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="is_compound" ng-click="isCompoundSelected();" ></md-checkbox>




					</div>
				</div>
			</div>
			
			
	
			
			<div class="form-group" id="form_div_base_uom_id" ng-show="formData.is_compound">
					<label for="base_uom_id" class="col-sm-2 control-label "><spring:message
							code="uom.base_uom_id"></spring:message><i class="text-danger">*</i></label>
					<div class="col-sm-3">
						<div class="input-group">
							<select  class="form-control" id="baseUom"
								name="choices"
								ng-options="v.id as v.name for v in baseUom"
								 ng-model="formData.base_uom_id"
								ng-disabled="disable_all">
								<option value="" selected="selected">select</option>
							</select>
							 <span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_base_uom_id_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="uom.error.base_uom_id"></spring:message>"></i></span>
						</div>
					
					</div>
				</div> 
			
			
			
			
			<div class="form-group " id="form_div_compound_unit" ng-show="formData.is_compound">
            <label for="compound_unit" class="col-sm-2 control-label"><spring:message
                    code="uom.compound_unit"></spring:message><i class="text-danger">*</i></label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input type="text" class="form-control algn_rgt" name="compound_unit"
                        id="compound_unit" ng-model="formData.compound_unit" ng-disabled="disable_all"
                        maxlength="9" placeholder="" valid-number> 
                        <span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_compound_unit_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="uom.error.compound_unit"></spring:message>"></i></span>
                </div>
            </div>
        </div>
			
			
			
			<div class="form-group " id="form_div_uom_symbol">
            <label for="uom_symbol" class="col-sm-2 control-label"><spring:message
                    code="uom.uom_symbol"></spring:message></label>
            <div class="col-sm-2">
                <div class="input-group">
                    <input type="text" class="form-control required" name="uom_symbol"
                        id="uom_symbol" ng-model="formData.uom_symbol" ng-disabled="disable_all"
                        maxlength="3" placeholder="">  <span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_uom_symbol_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="UOM Symbol cannot be blank"></i></span>
				
                </div>
            </div>
        </div>
        
        
        
        <div class="form-group " id="form_div_decimal_places">
            <label for="decimal_places " class="col-sm-2 control-label"><spring:message
                    code="uom.decimal_places"></spring:message></label>
            <div class="col-sm-2">
                <div class="input-group small_ipt">
                    <input type="text" class="form-control algn_rgt required"
                        name="decimal_places" id="decimal_places"
                        ng-model="formData.decimal_places" ng-disabled="disable_all"
                        maxlength="2" placeholder="" valid-number><span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_decimal_places_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="Decimal Places cannot be blank"></i></span>
				
                </div>
            </div>
        </div> 
        
        
        
			
			
		
	</form>
</div>

<input type="hidden" id="noofimporteddata" value="">
