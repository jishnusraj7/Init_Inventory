<div class="" id="currency_details_form_div" ng-show="show_form">
    <form class="form-horizontal" id="currency_details_form">

        <jsp:directive.include file="../common/includes/common_form.jsp" />



        <input type="hidden" id="noofimporteddata" value="">

        <div class="form-group " id="form_div_fraction_name">
            <label for="fraction_name" class="col-sm-2 control-label"><spring:message
                    code="currencydetails.fraction_name"></spring:message> <i
                class="text-danger">*</i></label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input type="text" class="form-control required"
                        name="fraction_name" id="fraction_name"
                        ng-model="formData.fraction_name" ng-disabled="disable_all"
                        placeholder="" maxlength="50"> <span
                        class="input-group-addon" min="0" max="99" number-mask=""
                        id="form_div_fraction_name_error" style="display: none;"><i
                        class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                        data-placement="bottom" title=""
                        data-original-title="<spring:message code="currencydetails.error.fraction_name"></spring:message>"></i></span>
                </div>
            </div>
        </div>


        <div class="form-group " id="form_div_symbol">
            <label for="symbol" class="col-sm-2 control-label"><spring:message
                    code="currencydetails.symbol"></spring:message> <i
                class="text-danger">*</i></label>
            <div class="col-sm-1">
                <div class="input-group small_ipt">
                    <input type="text" class="form-control required" name="symbol"
                        id="symbol" ng-model="formData.symbol" ng-disabled="disable_all"
                        maxlength="5" placeholder=""> <span
                        class="input-group-addon" min="0" max="99" number-mask=""
                        id="form_div_symbol_error" style="display: none;"><i
                        class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                        data-placement="bottom" title=""
                        data-original-title="<spring:message code="currencydetails.error.symbol"></spring:message>"></i></span>
                </div>
            </div>
        </div>




        <div class="form-group " id="form_div_fraction_symbol">
            <label for="symbol" class="col-sm-2 control-label"><spring:message
                    code="currencydetails.fraction_symbol"></spring:message> <i
                class="text-danger">*</i></label>
            <div class="col-sm-1">
                <div class="input-group small_ipt">
                    <input type="text" class="form-control required" name="symbol"
                        id="fraction_symbol" ng-model="formData.fraction_symbol"
                        ng-disabled="disable_all" placeholder="" maxlength="2"> <span
                        class="input-group-addon" min="0" max="99" number-mask=""
                        id="form_div_fraction_symbol_error" style="display: none;"><i
                        class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                        data-placement="bottom" title=""
                        data-original-title="<spring:message code="currencydetails.error.fraction_symbol"></spring:message>"></i></span>
                </div>
            </div>
        </div>

        <div class="form-group " id="form_div_decimal_places">
            <label for="decimal_places " class="col-sm-2 control-label"><spring:message
                    code="currencydetails.decimal_places"></spring:message> <i
                class="text-danger">*</i></label>
            <div class="col-sm-1">
                <div class="input-group small_ipt">
                    <input type="text" class="form-control algn_rgt required"
                        name="decimal_places" id="decimal_places"
                        ng-model="formData.decimal_places" ng-disabled="disable_all"
                        maxlength="2" placeholder="" numbers-only> <span
                        class="input-group-addon" min="0" max="99" number-mask=""
                        id="form_div_decimal_places_error" style="display: none;"><i
                        class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                        data-placement="bottom" title=""
                        data-original-title="<spring:message code="currencydetails.error.decimal_places"></spring:message>"></i></span>
                </div>
            </div>
        </div>
        
    
       <div class="form-group " id="form_div_rounding_id">
            <label for="decimal_places " class="col-sm-2 control-label">Rounding Method <i
                class="text-danger">*</i></label>
            <div class="col-sm-1">
                <div class="input-group small_ipt">
                   <select class="form-control selectpicker " id="rounding_id"
							 name="rounding_id"
							ng-options="v.id as v.name for v in roundingList" id="parent_id"
							ng-model="formData.rounding_id" ng-disabled="disable_all">
						</select>
				<span class="input-group-addon" id="form_div_rounding_id_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Please Select Rounding Method"></i></span>
                </div>
            </div>
        </div>
    
    <div class="form-group" id="form_div_is_base_currency" ng-hide="chkbox">
				<label for="is_base_currency" class="col-sm-2 control-label"><spring:message
						code="currencydetails.is_base_currency"></spring:message></label>
				<div class="col-sm-3">
					<div class="input-group" id="is_base_currency">

						<md-checkbox type="checkbox" ng-model="formData.is_base_currency"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="isbasecurrency" class="chck_box_div" ng-click="setEchgRate()"></md-checkbox>




					</div>
				</div>
			</div>
    
        
        
        
        <div class="form-group " id="form_div_exchange_rate">
            <label for="exchange_rate" class="col-sm-2 control-label"><spring:message
                    code="currencydetails.exchange_rate"></spring:message> <i
                class="text-danger"></i></label>
            <div class="col-sm-2">
                <div class="input-group">
                    <input type="text" class="form-control algn_rgt"
                        name="exchange_rate" id="exchange_rate"
                        ng-model="formData.exchange_rate" ng-disabled="disable_echgRate"
                        maxlength="4" valid-number>
                </div>
            </div>
        </div>
        <div class="form-group " id="form_div_exchange_rate_at">
            <label for="exchange_rate_at" class="col-sm-2 control-label"><spring:message
                    code="currencydetails.exchange_rate_at"></spring:message> <i
                class="text-danger"></i></label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input type="text" class="form-control" name="exchange_rate_at"
                        id="exchange_rate_at" ng-model="formData.exchange_rate_at"
                        ng-disabled="disable_all" daterange-picker calender-up-down="up">
                </div>
            </div>
        </div>

    </form>
</div>