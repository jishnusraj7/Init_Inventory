<div class="" id="supplier_form_div" ng-show="show_form">
    <div id="form_supplier_div">
        <form class="form-horizontal" id="supplier_form">
            <div class="" >
                <input type="hidden" id="id" name="id" value=""
                    ng-model="formData.id">
                <div class="form-group col-sm-8" id="form_div_code">
                    <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="code">
							<div class="row">
                                <spring:message code="common.code"></spring:message>
                                <i class="text-danger">*</i>
								
							</div>
                        </label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control required code-font-size" ng-change="isCodeExistis(formData.code)"
                                name="code" id="code" ng-model="formData.code" maxlength="10"
                                ng-disabled="disable_code" capitalize placeholder="">
                            <span
                                class="input-group-addon" min="0" max="99" number-mask=""
                                id="form_div_code_error" style="display: none;"><i
                                class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                data-placement="bottom" title=""
                                data-original-title="<spring:message code="common.error.code"></spring:message>"></i></span>
                            <span ng-bind="existing_code" class="existing_code_lbl"
                                ng-hide="hide_code_existing_er"></span>
                        </div>
                    </div>
                    <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                                <spring:message
                            code="common.name"></spring:message>
                        <i class="text-danger">*</i>
                    </div>
                        </label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control required code-font-size "
                                name="name" id="name" ng-model="formData.name"
                                ng-disabled="disable_all" maxlength="50" placeholder="">
                            <span class="input-group-addon" id="form_div_name_error"
                                style="display: none;"><i
                                class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                data-placement="bottom" title=""
                                data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
                        </div>
                    </div>
					
                    <input type="hidden" id="noofimporteddata" value="">
					
					
					
                    <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                                <spring:message
                            code="supplier.abbrevation"></spring:message>
                    </div>
                        </label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="Abbrevation"
                                maxlength="3" id="Abbrevation" ng-model="formData.abbreviation"
                                ng-disabled="disable_all" placeholder="">
                        </div>
                    </div>
					
                    <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                                <spring:message
                            code="supplier.tinNo"></spring:message>
                    </div>
                        </label>
                        <div class="col-sm-9">
                             <input type="text" class="form-control" name="tinNo" id="tinNo"
                                maxlength="20" ng-model="formData.tin_no"
                                ng-disabled="disable_all" placeholder="">
                        </div>
                    </div>
					
					
					 <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                              <spring:message
                            code="supplier.address"></spring:message>
                        <i class="text-danger">*</i>
                    </div>
                        </label>
                        <div class="col-sm-9">
                             <textarea class="form-control required supplier_address" name="address"
                                maxlength="250" required id="address" ng-model="formData.address"
                                ng-disabled="disable_all" placeholder="">{{formData.address}}
                            </textarea>
                            <span class="input-group-addon" id="form_div_address_error"
                                style="display: none;"><i
                                class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                data-placement="bottom" title=""
                                data-original-title="<spring:message code="supplier.error.address"></spring:message>"></i></span>
                        </div>
                    </div>
					
                    
                    <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                               <spring:message
                            code="supplier.country"></spring:message>
                        <i class="text-danger">*</i>
                    </div>
                        </label>
                        <div class="col-sm-9">
                             <select class="form-control selectpicker " id="country"
                                ng-change="countrychange()" name="country"
                                ng-options="v.id as v.name for v in countryList" id="parent_id"
                                ng-model="formData.country" ng-disabled="disable_all">
                            </select> <span class="input-group-addon" id="form_div_country_type_error"
                                style="display: none;"><i
                                class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                data-placement="bottom" title=""
                                data-original-title="<spring:message code="supplier.error.country"></spring:message>"></i></span>
                        </div>
                    </div>
                 
                    
                 
                       <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                                <spring:message
                                code="supplier.state"></spring:message>
                            <i class="text-danger">*</i>
                    </div>
                        </label>
                        <div class="col-sm-9">
                             <select class="form-control selectpicker " id="state"
                                    ng-change="statechange()" name="state"
                                    ng-options="v.id as v.name for v in stateList" id="state"
                                    ng-model="formData.state" ng-disabled="disable_all">
                                </select> <span class="input-group-addon" id="form_div_state_type_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="supplier.error.state"></spring:message>"></i></span>
                        </div>
                    </div>
                    
                    <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                                <spring:message
                                code="supplier.city"></spring:message>
                            <i class="text-danger">*</i>
                    </div>
                        </label>
                        <div class="col-sm-9">
                           <select class=" form-control selectpicker " id="city" name="city" 
                                    ng-model="formData.city"
                                    ng-options="v.id as v.name for v in cityList" id="city"
                                    ng-disabled="disable_all"
                                    >
                                </select><span class="input-group-addon" id="form_div_city_type_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="supplier.error.city"></spring:message>"></i></span>
                        </div>
                    </div>
                    
                    
                    <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                                 <spring:message
                                code="supplier.pincode"></spring:message>
                    </div>
                        </label>
                        <div class="col-sm-9">
<input valid-number type="text" class="form-control " name="pincode"
                                    maxlength="6" id="pincode" ng-model="formData.pin_code"
                                    ng-disabled="disable_all" placeholder=""> <span
                                    class="input-group-addon" id="form_div_name_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
                        </div>
                    </div>
                    
                     <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                               <spring:message
                                code="supplier.contactperson"></spring:message>
                        <i class="text-danger">*</i>
                    </div>
                        </label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="contactperson"
                                    maxlength="50" id="contactperson"
                                    ng-model="formData.contact_person" ng-disabled="disable_all"
                                    placeholder=""> <span
                                    class="input-group-addon" id="form_div_contactperson_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
                        </div>
                    </div>
                    
                     <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                              <spring:message
                                code="supplier.contactnumber"></spring:message>
                            <i
                                class="text-danger">*</i>
                    </div>
                        </label>
                        <div class="col-sm-9">
                            <input valid-number type="text" class="form-control required " name="contact_no"
                                    maxlength="10" id="contact_no" ng-model="formData.contact_no"
                                    ng-disabled="disable_all" placeholder=""> <span
                                    class="input-group-addon" id="form_div_contact_no_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="supplier.error.contactno"></spring:message>"></i></span>
                        </div>
                    </div>
                    
                     <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                              <spring:message
                                code="supplier.emailid"></spring:message>
                    </div>
                        </label>
                        <div class="col-sm-9">
                           <input type="text" class="form-control email" name="emailid"
                                    maxlength="250" id="emailid" ng-model="formData.contact_email"
                                    ng-disabled="disable_all" placeholder=""> <span
                                    class="input-group-addon" id="form_div_emailid_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="comon.email.error"></spring:message>"></i></span>
                        </div>
                    </div>
                    
                  
                   <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                             <spring:message
                                code="supplier.backofficeref"></spring:message>
                    </div>
                        </label>
                        <div class="col-sm-9">
                          <input type="text" class="form-control" name="backofficeref"
                                    maxlength="250" id="backofficeref"
                                    ng-model="formData.back_office_ref" ng-disabled="disable_all"
                                    placeholder=""> <span
                                    class="input-group-addon" id="form_div_backofficeref_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
                        </div>
                    </div>
					
					 <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                              <spring:message
                                code="supplier.setasinactive"></spring:message>
                    </div>
                        </label>
                        <div class="col-sm-9">
                           <md-checkbox type="checkbox" ng-model="formData.is_active"
                                    ng-true-value="true" ng-false-value="false"
                                    ng-disabled="disable_all" aria-label="setasinactive" class="chck_box_div"></md-checkbox>
                        </div>
                    </div>
					
					 <div class="col-sm-6 supplier_mrg_btm">
                        <label class="col-sm-3 control-label" for="name" id="lbl_name">
							<div class="row">
                              <spring:message
                                code="supplier.remarks"></spring:message>
                    </div>
                        </label>
                        <div class="col-sm-9">
                           <textarea class="form-control" name="remarks" maxlength="250"
                                    id="remarks" ng-model="formData.comments"
                                    ng-disabled="disable_all" placeholder="">{{formData.comments}}
                                </textarea>
                                <span class="input-group-addon" id="form_div_remarks_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
                        </div>
                    </div>
                    
					</div>
                    
                    <!-- <div class="col-sm-2">
                        <span ng-bind="existing_code" class="existing_code_lbl"
                        		ng-hide="hide_code_existing_er"></span>
                        </div> --> 
                    <%-- 		<div class="form-group " id="form_div_code">
                        <label for="code" class="col-sm-2 control-label code-lbl-font-size"><spring:message
                        		code="common.code"></spring:message> <i class="text-danger">*</i></label>
                        <div class="col-sm-3">
                        	<div class="input-group input-group-lg input_code">
                        		<input type="text" class="form-control required code-font-size "
                        			name="code" id="code" ng-model="formData.code" maxlength="10"
                        			ng-disabled="disable_code" capitalize placeholder="Code">
                        		<span class="input-group-addon" min="0" max="99" number-mask=""
                        			id="form_div_code_error" style="display: none;"><i
                        			class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                        			data-placement="bottom" title=""
                        			data-original-title="<spring:message code="common.error.code"></spring:message>"></i></span>
                        		<span ng-bind="existing_code" class="existing_code_lbl"
                        			ng-hide="hide_code_existing_er"></span>
                        	</div>
                        </div>
                        </div> --%>
                    <!-- <div class="form-group" id="form_div_name"> -->
                    
                    <!--  </div>	 -->		
                    <!-- <div class="form-group" id="form_div_abbrevation"> -->
                    
                    <!-- </div> -->
                    <!-- <div class="form-group" id="form_div_tin_no"> -->
                    
               <!--  </div> -->
  <%--               <div class="form-group  col-sm-12" id="form_div_address">
                    <label for="address" class="col-sm-2 control-label" id="lbl_address">
                        <spring:message
                            code="supplier.address"></spring:message>
                        <i class="text-danger">*</i>
                    </label>
                    <div class="col-sm-5">
                        <div class="input-group">
                            <textarea class="form-control required" name="address"
                                maxlength="250" required id="address" ng-model="formData.address"
                                ng-disabled="disable_all" placeholder="">{{formData.address}}
                            </textarea>
                            <span class="input-group-addon" id="form_div_address_error"
                                style="display: none;"><i
                                class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                data-placement="bottom" title=""
                                data-original-title="<spring:message code="supplier.error.address"></spring:message>"></i></span>
                        </div>
                    </div>
                    <!-- </div> -->
                    <!-- </div> -->
                    <!-- <div class="form-group col-sm-12" id="form_div_country"> -->
                    <label for="country" class="col-sm-2 control-label" id="lbl_country">
                        <spring:message
                            code="supplier.country"></spring:message>
                        <i class="text-danger">*</i>
                    </label>
                    <div class="col-sm-2">
                        <div class="input-group">
                            <select class="form-control selectpicker " id="country"
                                ng-change="countrychange()" name="country"
                                ng-options="v.id as v.name for v in countryList" id="parent_id"
                                ng-model="formData.country" ng-disabled="disable_all">
                            </select> <span class="input-group-addon" id="form_div_country_type_error"
                                style="display: none;"><i
                                class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                data-placement="bottom" title=""
                                data-original-title="<spring:message code="supplier.error.country"></spring:message>"></i></span>
                        </div>
                    </div>
                    <div class="form-group col-sm-12" id="form_div_state">
                        <label for="state" class="col-sm-1 control-label" id="lbl_state">
                            <spring:message
                                code="supplier.state"></spring:message>
                            <i class="text-danger">*</i>
                        </label>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <select class="form-control selectpicker " id="state"
                                    ng-change="statechange()" name="state"
                                    ng-options="v.id as v.name for v in stateList" id="state"
                                    ng-model="formData.state" ng-disabled="disable_all">
                                </select> <span class="input-group-addon" id="form_div_state_type_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="supplier.error.state"></spring:message>"></i></span>
                            </div>
                        </div>
                        <!-- </div> -->
                        <!-- <div class="form-group" id="form_div_city"> -->
                        <label for="city" class="col-sm-2 control-label">
                            <spring:message
                                code="supplier.city"></spring:message>
                            <i class="text-danger">*</i>
                        </label>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <select class=" form-control selectpicker " id="city" name="city" 
                                    ng-model="formData.city"
                                    ng-options="v.id as v.name for v in cityList" id="city"
                                    ng-disabled="disable_all"
                                    >
                                </select><span class="input-group-addon" id="form_div_city_type_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="supplier.error.city"></spring:message>"></i></span>
                            </div>
                        </div>
                        <!-- </div> -->
                        <!-- <div class="form-group col-sm-12" id="form_div_pincode"> -->
                        <label for="pincode" class="col-sm-2 control-label">
                            <spring:message
                                code="supplier.pincode"></spring:message>
                        </label>
                        <div class="col-sm-2">
                            <div class="input">
                                <input valid-number type="text" class="form-control " name="pincode"
                                    maxlength="6" id="pincode" ng-model="formData.pin_code"
                                    ng-disabled="disable_all" placeholder=""> <span
                                    class="input-group-addon" id="form_div_name_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-sm-12" id="form_div_contactperson">
                        <label for="contactperson" class="col-sm-1 control-label" id="lbl_contact_person" >
                            <spring:message
                                code="supplier.contactperson"></spring:message>
                        </label>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <input type="text" class="form-control" name="contactperson"
                                    maxlength="50" id="contactperson"
                                    ng-model="formData.contact_person" ng-disabled="disable_all"
                                    placeholder=""> <span
                                    class="input-group-addon" id="form_div_contactperson_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
                            </div>
                        </div>
                        <!-- </div> -->
                        <!-- <div class="form-group" id="form_div_contact_no"> -->
                        <label for="contact_no" class="col-sm-2 control-label" id="lbl_contact_no ">
                            <spring:message
                                code="supplier.contactnumber"></spring:message>
                            <i
                                class="text-danger">*</i>
                        </label>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <input valid-number type="text" class="form-control required " name="contact_no"
                                    maxlength="10" id="contact_no" ng-model="formData.contact_no"
                                    ng-disabled="disable_all" placeholder=""> <span
                                    class="input-group-addon" id="form_div_contact_no_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="supplier.error.contactno"></spring:message>"></i></span>
                            </div>
                        </div>
                        <!-- </div> -->
                        <!-- 	<div class="form-group col-sm-12" id="form_div_emailid"> -->
                        <label for="emailid" class="col-sm-2 control-label">
                            <spring:message
                                code="supplier.emailid"></spring:message>
                        </label>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <input type="text" class="form-control email" name="emailid"
                                    maxlength="250" id="emailid" ng-model="formData.contact_email"
                                    ng-disabled="disable_all" placeholder=""> <span
                                    class="input-group-addon" id="form_div_emailid_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="comon.email.error"></spring:message>"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-sm-12" id="form_div_backofficeref">
                        <label for="backofficeref" class="col-sm-2 control-label" id="lbl_back_officer">
                            <spring:message
                                code="supplier.backofficeref"></spring:message>
                        </label>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <input type="text" class="form-control" name="backofficeref"
                                    maxlength="250" id="backofficeref"
                                    ng-model="formData.back_office_ref" ng-disabled="disable_all"
                                    placeholder=""> <span
                                    class="input-group-addon" id="form_div_backofficeref_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
                            </div>
                        </div>
                        <label for="setasinactive" class="col-sm-1 control-label">
                            <spring:message
                                code="supplier.setasinactive"></spring:message>
                        </label>
                        <div class="col-sm-5">
                            <div class="input-group" id="is_active">
                                <md-checkbox type="checkbox" ng-model="formData.is_active"
                                    ng-true-value="true" ng-false-value="false"
                                    ng-disabled="disable_all" aria-label="setasinactive" class="chck_box_div"></md-checkbox>
                            </div>
                        </div>
                        <!-- </div> -->
                    </div>
                    <div class="form-group col-sm-12" id="form_div_remarks">
                        <label for="remarks" class="col-sm-1 control-label" id="lbl_remarks">
                            <spring:message
                                code="supplier.remarks"></spring:message>
                        </label>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <textarea class="form-control" name="remarks" maxlength="250"
                                    id="remarks" ng-model="formData.comments"
                                    ng-disabled="disable_all" placeholder="">{{formData.comments}}
                                </textarea>
                                <span class="input-group-addon" id="form_div_remarks_error"
                                    style="display: none;"><i
                                    class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
                                    data-placement="bottom" title=""
                                    data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
                            </div>
                        </div>
                        <!-- <div class="form-group" id="form_div_setasinactive"> -->
                        <!-- </div> -->
                    </div>
                </div> --%>
            </div>
        </form>
    </div>
</div>