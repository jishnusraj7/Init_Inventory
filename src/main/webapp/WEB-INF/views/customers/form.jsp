
<div class="" id="itemclass_form_div" ng-show="show_form" ng-cloak>  
<form class="form-horizontal" id="itemclass_form">
	
	<div class="">
	<div class="row">
	<div class="col-sm-12">
  <md-content>
    <md-tabs md-dynamic-height md-border-bottom md-selected="selectedIndexTab" >
      <md-tab label="GENERAL 1">
        <md-content class="md-padding">
         <div class="form-group " id="form_div_code" style="padding-top:10px;">
	                      <label for="code" class="col-sm-3 control-label code-lbl-font-size"><spring:message
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
						<label for="name" class="col-sm-3 control-label code-lbl-font-size"><spring:message
								code="common.name"></spring:message> <i class="text-danger">*</i></label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control required code-font-size" 
									name="name" id="name" ng-model="formData.name"
									ng-disabled="disable_all" maxlength="50" placeholder="Name">
								<span class="input-group-addon" id="form_div_name_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>
					 <div class="form-group" id="form_div_cust_type_class" >
					<label for="parent_id" class="col-sm-3 control-label lb">Customer Type<i class="text-danger">*</i></label>
					<div class="col-sm-3">
						
							<select class="form-control  code-font-size" id="customer_type_name" 
								name="customer_type_name" ng-disabled="disable_all"
								ng-options="v.id as v.name for v in custTypeList"
								ng-model="formData.customer_type" ng-change="filterSuperClass()">
							</select>
									<span class="input-group-addon" id="form_div_custmrtype_class_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemclass.error.superclass"></spring:message>"></i></span>
							
						
					</div>
				</div>
					<div class="form-group" id="form_div_card_number">
						<label for="name" class="col-sm-3 control-label lb">Card No <i class="text-danger">*</i></label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control  code-font-size" 
									name="card_no" id="card_no" ng-model="formData.card_no"
									ng-disabled="disable_all" maxlength="100" placeholder="" >
								<span class="input-group-addon" id="form_div_card_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Card number cannot be left blank"></i></span>
							</div>
						</div>
					
					</div>
					<div class="form-group" id="form_div_address">
	<label for="address" class="col-sm-3 control-label">Address<i class="text-danger">*</i></label>

	          <div class="col-sm-3">
		<div class="input-group">
			<textarea rows="" cols="" type="text" class="form-control "
				name="address" ng-model="formData.address"
				ng-disabled="disable_all" maxlength="100" id="address"
				placeholder="" > </textarea>
			<span class="input-group-addon" id="form_div_address_error"
				style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="common.error.description"></spring:message>"></i></span>

		</div>
	</div>

</div>
					<div class="form-group" id="form_div_subclass">
						<label for="fe" class="col-sm-3 control-label lb">Is AR Company</label>
					
						<div class="col-sm-1">
							
									<md-checkbox name="fe" ng-model="is_arCompany" 		
										aria-label="Checkbox 1" ng-change="arCodeChange()" ng-disabled="disable_all" >
									</md-checkbox>
																	
						</div>
						<div class="col-sm-3" ng-show="is_arCompany" style="margin-left: 20px">
						<input type="text" class="form-control code-font-size" 
									name="accnt_code" id="accnt_code" ng-model="formData.ar_code"
									ng-disabled="disable_all" maxlength="50" placeholder="ACCOUNT CODE">
						</div>
					
					</div>
			
				
					
				
	</md-content>		
	</md-tab>	
	
	
	
	
	
	<md-tab label="GENERAL 2">
        <md-content class="md-padding">
        		<div class="form-group" id="form_div_accum_points">
						<label for="name" class="col-sm-3 control-label lb">Accumulated Points <i class="text-danger">*</i></label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control  code-font-size" style="width:260px !important; height:40px"
									name="accum_points" id="accum_points" ng-model="formData.accumulated_points"
									ng-disabled="disable_all" maxlength="18" placeholder="Accumulated Points" numbers-only>
								<span class="input-group-addon" id="form_div_accum_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Accumulated points cannot be left blank"></i></span>
							</div>
						</div>
					
					</div>
					<div class="form-group" id="form_div_redeem_points">
						<label for="name" class="col-sm-3 control-label lb">Redeemed Points <i class="text-danger">*</i></label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control  code-font-size" style="width:260px !important; height:40px"
									name="redeem_points" id="redeem_points" ng-model="formData.redeemed_points"
									ng-disabled="disable_all" maxlength="18" placeholder="Redeemed Points" numbers-only>
								<span class="input-group-addon" id="form_div_redeem_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Redeemed points cannot be left blank"></i></span>
							</div>
						</div>
					
					</div>    
					
					
					
						 <div class="form-group" id="form_div_tin">
						<label for="tin" class="col-sm-3 control-label lb">GSTIN</label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="tin" id="tin" ng-model="formData.tin"
									ng-disabled="disable_all" maxlength="50" placeholder="Tin">
								<span class="input-group-addon" id="form_div_tin_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>  
					
					
					 <div class="form-group" id="form_div_cst_no">
						<label for="cst_no" class="col-sm-3 control-label lb">CST Number</label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="cst_no" id="cst_no" ng-model="formData.cst_no"
									ng-disabled="disable_all" maxlength="50" placeholder="cst_no">
								<span class="input-group-addon" id="form_div_cst_no_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>  
					
				
					
					
					 <div class="form-group" id="form_div_license_no">
						<label for="cst_no" class="col-sm-3 control-label lb">License No</label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="license_no" id="license_no" ng-model="formData.license_no"
									ng-disabled="disable_all" maxlength="50" placeholder="license_no">
								<span class="input-group-addon" id="form_div_license_no_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div> 
					
				  <div class="form-group" id="form_div_emailid">
					<label for="emailid" class="col-sm-3 control-label">
						<!-- <spring:message
						code="systemSetting.label.mailId"></spring:message>  -->
						Email
					</label>
					<div class="col-sm-3">
						<div class="input-group input-group-lg">
							<input type="text" class="form-control email" name="emailid"
								id="emailid" maxlength="250" ng-model="formData.email"
								ng-disabled="disable_all" placeholder="Email"><span
								class="input-group-addon" id="form_div_emailid_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="email not valid"></i></span>
						</div>
					</div>
				</div>  
					
					
					
				
	</md-content>		
	</md-tab>	
	
	
	
	
	
	
	
	
	
	
	<md-tab label="GENERAL 3" >
        <md-content class="md-padding">
         
       
				<div class="form-group" id="form_div_subclass">
						<label for="name" class="col-sm-3 control-label lb">Is Valid</label>
					
						<div class="col-sm-5">
							
									<md-checkbox name="fe" ng-model="isValid" 		
										aria-label="Checkbox 1" ng-change="changeToSubClss()" ng-disabled="disable_all" >
									</md-checkbox>
																	
						</div>
					</div>
				     <div class="form-group" id="form_div_street">
						<label for="street" class="col-sm-3 control-label lb">Street</label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="street" id="street" ng-model="formData.street"
									ng-disabled="disable_all" maxlength="50" placeholder="Street">
								<span class="input-group-addon" id="form_div_street_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>    
					<div class="form-group" id="form_div_city">
						<label for="city" class="col-sm-3 control-label lb">City</label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="city" id="city" ng-model="formData.city"
									ng-disabled="disable_all" maxlength="50" placeholder="City">
								<span class="input-group-addon" id="form_div_city_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>  
					<div class="form-group" id="form_div_state">
						<label for="state" class="col-sm-3 control-label lb">State</label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="state" id="state" ng-model="formData.state"
									ng-disabled="disable_all" maxlength="50" placeholder="State">
								<span class="input-group-addon" id="form_div_state_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>   
					<div class="form-group" id="form_div_state">
						<label for="country" class="col-sm-3 control-label lb">Country</label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="country" id="country" ng-model="formData.country"
									ng-disabled="disable_all" maxlength="50" placeholder="Country">
								<span class="input-group-addon" id="form_div_country_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>  
					<div class="form-group" id="form_div_zip_code">
						<label for="zip_code" class="col-sm-3 control-label lb">Zip Code</label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="zip_code" id="zip_code" ng-model="formData.zip_code"
									ng-disabled="disable_all" maxlength="20" placeholder="zip Code">
								<span class="input-group-addon" id="form_div_zipcode_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>  
					
					<div class="form-group" id="form_div_phone">
						<label for="name" class="col-sm-3 control-label lb">Phone No</label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control  code-font-size" 
									name="phone_no" id="phone_no" ng-model="formData.phone"
									ng-disabled="disable_all" maxlength="50" placeholder="Phone" numbers-only>
								<span class="input-group-addon" id="form_div_name_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div> 
					<div class="form-group" id="form_div_fax">
						<label for="fax" class="col-sm-3 control-label lb">Fax</label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="fax" id="fax" ng-model="formData.fax"
									ng-disabled="disable_all" maxlength="50" placeholder="Fax">
								<span class="input-group-addon" id="form_div_fax_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
							</div>
						</div>
					
					</div>  
				     
							

						
						
							
        </md-content>
      </md-tab>		
			
 </md-tabs>         
  </md-content>      
      
 </div>
 </div>
 </div>   
 </form>
 </div>    



