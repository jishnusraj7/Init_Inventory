<div class="" id="shops_form_div" ng-show="show_form">  

	<form class="form-horizontal" id="department_form">
	<jsp:directive.include file="../common/includes/common_form.jsp" />
	 <div class="form-group " id="form_div_department_table">
	 <div class="col-sm-12">
	<label for="deprtmnt_table" class="col-sm-2 control-label code-lbl-font-size">Department</label>
	<div class="col-sm-5">
		<div class="form-group" id="div_table_department">
			<table datatable="" dt-options="item.dtOptions1"
			dt-columns="item.dtColumns1" dt-instance="item.dtInstance1"
			class="table dataClass "></table>
	</div>
	<div class="text-danger" id="divErrormsg2"></div>
	</div>
	<div class="col-sm-1"></div>
	
	<div class="col-md-4 settingsdiv" ng-if="!showAreaTxt">
                <h5 class="settinghead">Database Settings</h5>
                <div class="col-md-12 ">
                  <div class="col-md-5">Server Name</div>
                  <div class="col-md-7">
                      <input name="db_server" id="db_server" ng-disabled="true" class="form-control" ng-model="dbServer">
                  </div>
                </div>
                <div class="col-md-12 " ng-if="isEdit"  style="padding:10px">
                  <div class="col-md-5">Database Name</div>
                  <div class="col-md-7">
                      <input name="db_database" id="db_database" ng-disabled="true" class="form-control" ng-model="dbDatabase">
                  </div>
                 </div>
                <div class="col-md-12" style="padding:10px">
                  <div class="col-md-5">Username</div>
                  <div class="col-md-7">
                    <input  name="db_user" id="db_user"  ng-disabled="true" class="form-control" ng-model="dbUser">
                        
                         <input id="client_name" type="hidden"  value="<%=company%>" >
                        
                  </div>
                </div>
      </div>
	
	</div>
</div> 

<div class="form-group" id="form_div_area" >
					<label for="parent_id" class="col-sm-2 control-label lb">Area<i class="text-danger">*</i></label>
					<div class="col-sm-5">
						<div class="input-group input-group-lg">
							<select class="form-control  code-font-size" id="area_id" ng-show="!showAreaTxt"
								name="area_id" ng-model="formData.area_id"
								  ng-disabled="disable_all" >
								<option ng-repeat="vlist in areaList" value="{{vlist.id}}">{{vlist.name}}</option>
							</select>
							<p ng-show="showAreaTxt">{{areaName}}</p>
									<span class="input-group-addon" id="form_div_area_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Please select an area"></i></span></div>
							
						
 </div>
 </div>
 <div class="form-group" id="form_div_address">
	<label for="description" class="col-sm-2 control-label">Address<i class="text-danger">*</i></label>

	<div class="col-sm-5">
		<div class="input-group">
			<textarea rows="" cols="" type="text" class="form-control required "
				name="address" ng-model="formData.address"
				ng-disabled="disable_all" maxlength="100" id="address"
				placeholder=""> </textarea>
			<!-- <span class="input-group-addon" id="form_div_address_error"
				style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="Please enter the address"></i></span> -->

		</div>
	</div>

</div>
			<div class="form-group" id="form_div_city">
						<label for="city" class="col-sm-2 control-label lb">City</label>
					
						<div class="col-sm-5">
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
						<label for="state" class="col-sm-2 control-label lb">State</label>
					
						<div class="col-sm-5">
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
						<label for="country" class="col-sm-2 control-label lb">Country</label>
					
						<div class="col-sm-5">
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
						<label for="zip_code" class="col-sm-2 control-label lb">Zip Code</label>
					
						<div class="col-sm-5">
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
					<div class="form-group" id="form_div_license">
						<label for="city" class="col-sm-2 control-label lb">License No</label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="company_license_no" id="company_license_no" ng-model="formData.company_license_no"
									ng-disabled="disable_all" maxlength="50" placeholder="License No">
								<span class="input-group-addon" id="form_div_license_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Please Enter License No"></i></span>
							</div>
						</div>
					
					</div>  
						<div class="form-group" id="form_div_tax">
						<label for="city" class="col-sm-2 control-label lb">GSTIN</label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="company_tax_no" id="company_tax_no" ng-model="formData.company_tax_no"
									ng-disabled="disable_all" maxlength="50" placeholder="Tax No">
								
							</div>
						</div>
					
					</div>  
					<div class="form-group" id="form_div_cst">
						<label for="city" class="col-sm-2 control-label lb">CST Number</label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size"  ng-model="formData.cst_no"
									name="company_cst_no" id="company_cst_no"
									ng-disabled="disable_all" maxlength="50" >
							
							</div>
						</div>
					
					</div>  
					
					
						<div class="form-group" id="form_div_phone">
						<label for="phone" class="col-sm-2 control-label lb">Phone Number</label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control code-font-size" 
									name="phone" id="phone" valid-number ng-model="formData.phone"
									ng-disabled="disable_all" maxlength="50" >
									<span class="input-group-addon" id="form_div_phone_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Phone number cannot be blank"></i></span>
							
							</div>
						</div>
					
					</div> 
					
					
						<div class="form-group" id="form_div_email">
						<label for="phone" class="col-sm-2 control-label lb">Email</label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control email  code-font-size" 
									name="email" id="email" ng-model="formData.email"
									ng-disabled="disable_all" maxlength="50" >
									<span
							class="input-group-addon" id="form_div_email_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="comon.email.error"></spring:message>"></i></span>
							
							</div>
						</div>
					
					</div> 
					
						<div class="form-group" id="form_div_email_subscribe">
				<label for="email_subscribe" class="col-sm-2 control-label">Subscribe for daily Email report<!-- <spring:message
						code="supplier.setasinactive"></spring:message> --> </label>
				<div class="col-sm-5">
					<div class="input-group" id="is_active">

						<md-checkbox type="checkbox" ng-model="formData.email_subscribe"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="email_subscribe" class="chck_box_div"></md-checkbox>




					</div>
				</div>
			</div>
					
					<c:if test="${(companytype==0) ? true : false}">

						
						
						<div class="form-group" id="form_div_is_customer">
				<label for="is_customer" class="col-sm-2 control-label">Is Customer<!-- <spring:message
						code="supplier.setasinactive"></spring:message> --> </label>
				<div class="col-sm-5">
					<div class="input-group" id="is_active">

						<md-checkbox type="checkbox" ng-model="is_customer"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="is_customer" class="chck_box_div" ng-change="changeCustmr()"></md-checkbox>




					</div>
				</div>
			</div>
			
			
			<div class="col-md-12" style="" id="customertype_div" ng-show="is_customer">
              <div class="col-md-2"></div>
              <div class="col-md-10 addmargin">                
                  <div class="form-group col-md-2" style="padding: 0">
                      <label class="addtextallign">AR Customer</label><br>
                         <label id="arcom" class="">
                          <div class="icheckbox_flat-green" aria-checked="false" aria-disabled="false" style="position: relative;"><md-checkbox type="checkbox" ng-model="CustData.is_ar"
							ng-true-value="true" ng-false-value="false" ng-change="changeARCustmr()"
						 aria-label="is_ar" class="chck_box_div" ng-disabled="disable_all"></md-checkbox></div>          
                         </label>
                  </div>
                <div class="col-md-3" id="form_div_ar_code">
                      <label>Account Code <span  class="text-danger">*</span></label><br>
                      <input maxlength="20" name="ar_code" id="ar_code" ng-model="CustData.ar_code" class="form-control"
                       placeholder="Account Code"  ng-disabled="CustData.is_ar==false || disable_all==true" 
                       value="">
                </div>
                <div class="spanerror4 spanerrors" id="alert_ar_code"></div>

                
               <div class="col-md-4" id="form_div_custtype"><label>Customer Type <span class="text-danger">*</span></label>
                 
                 
                 <select class="form-control  code-font-size" id="customer_type" 
								name="customer_type_name" ng-disabled="disable_all"
								ng-options="v.id as v.name for v in custTypeList"
								ng-model="CustData.customer_type" >
							</select>
							
							
                  <!--   <select name="customer_type" id="customer_type" class="form-control" style="border: 1px solid rgb(204, 204, 204);">
                     <option value="">Select</option>
                                              <option value="0">Walk-in</option>
                                              <option value="101">Value Oriented Customers</option>
                                              <option value="102">regular</option>
                                              <option value="103">walkin</option>
                          
                    </select> -->
                  </div> 
                   <div class="spanerrors" id="alert_customer_type"></div>
              
                      
            </div>
            
            	<div class="form-group" id="form_div_card_no">
				<label for="card_no" class="col-sm-2 control-label">Card Number<!-- <spring:message
						code="supplier.setasinactive"></spring:message> --> </label>
				<div class="col-sm-5">
					<div class="input-group" id="is_active">
			<input type="text" class="form-control   code-font-size" 
									name="card_no" id="card_no" ng-model="CustData.card_no"
									ng-disabled="disable_all" maxlength="100" >
						




					</div>
				</div>
			</div>
			
            </div>
			
					</c:if>
					<!-- <div class="form-group" id="form_div_logo" ng-show="showAreaTxt">
						<label for="city" class="col-sm-2 control-label lb">Upload Logo(70*49)</label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<input class="form-control" id="shpImg" name="shpImg" type="file">	
							</div>
						</div>
					
					</div>   -->
					<div class="form-group" id="form_div_item_thumb" style="padding-top:10px" ng-show="showAreaTxt">
						<label for="uom_id" class="col-sm-2 control-label lb">Upload Logo(70*49)</label>
					<div class="col-sm-6 img_upload_div">
					
					
					<div id="imgdiv" data-provides="fileupload" class="fileupload fileupload-new"><input type="hidden">
                                                  <div id="defaultImage" style="width: 200px; height: 150px;" class="fileupload-new thumbnail" >
                                                   <!--    <img alt="" src="{{formData.itemThumb}}"> -->
                                                  </div>
                                                  <div style="border-color: rgb(210, 214, 222);" id="imgshw" style="max-width: 200px; max-height: 150px; line-height: 10px;" class="fileupload-preview fileupload-exists thumbnail">
                                                 </div>
                                                 
                                                
                                                  <div>
                                                   <span class="btn btn-white btn-file">
                                                   <span class="fileupload-new" ><i class="fa fa-paper-clip" ></i> Select image</span>
                                                   <span class="fileupload-exists" ng-disabled="disable_all"><i class="fa fa-undo"></i> Change</span>
                                                   <input type="file" class="default"  name="item_thumb"
							                            id="item_thumb" ng-disabled="disable_all" file-model="myFile" >
                                                   </span>
                                                      <a data-dismiss="fileupload" ng-disabled="disable_all" class="btn btn-danger fileupload-exists" href="#" ng-click="removeImage()"><i class="fa fa-trash"></i> Remove</a>
                                                  </div>
                                              </div>
					
					
				
						</div>
					
					</div>
					<div class="form-group" id="form_div_sync" ng-show="showAreaTxt">
						<label for="city" class="col-sm-2 control-label lb">Is Syncable</label>
					
						<div class="col-sm-5">
							<div class="input-group input-group-lg">
								<md-checkbox name="isSyncable" id="isSyncable" ng-model="isSyncable"		
								aria-label="Checkbox 2"  ng-disabled="disable_all" >
						</md-checkbox>
							</div>
						</div>
					
					</div>  
		
</form>		
</div>	


	