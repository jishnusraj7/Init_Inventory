<div class="" id="department_form_div" ng-show="show_form">
	<div class="col-md-12 spanerrors spanerrorstab1" id="alert_required"
		style="display: none; color: red;">*Fill the required field*</div>

	<form class="form-horizontal" id="department_form">

		<div class="">
			<div class="row">
				<div class="col-sm-12">


					<md-content> <md-tabs md-dynamic-height
						md-border-bottom md-selected="selectedIndexTab"> <md-tab
						label="GENERAL "> <md-content class="md-padding">
					<div class="col-md-12 employeetabsdiv" id="employeetabs1div"
						style="display: block;">


						<div class="col-md-12 addmargin" id="form_div_code">

							<div class="col-md-2 addstyle">
								<label for="code" class="control-label code-lbl-font-size">Code
									<span class="mandatory">*</span>
								</label>
							</div>
							<div class="col-md-4">
								<div class="input-group">
									<input class="form-control required "
										ng-disabled="disable_code" ng-model="formData.code"
										ng-change="isCodeExistis(formData.code)" type="text" id="code"
										name="code" placeholder="Code" capitalize maxlength="10">
									<span class="input-group-addon" id="form_div_code_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Code cannot be left blank"></i></span>
								</div>
							</div>
							<!--        <div class="col-md-1 spanerrors spanerrorstab1" id="form_div_code_error" style="display: none;"><span><i class="fa fa-exclamation-circle"  title="Code cannot be left blank"></i></span></div>
 -->
							<div class="col-sm-4">
								<span ng-bind="existing_code" class="existing_code_lbl"
									ng-hide="hide_code_existing_er"></span>
							</div>
						</div>

						<div class="col-md-12 addmargin">
							<div class="col-md-2 addstyle">
								<label for="f_name" class="control-label code-lbl-font-size">Name<span
									class="mandatory">*</span></label>
							</div>
							<div class="col-md-4" id="form_div_f_name">
								<!-- <label>First Name <span class="mandatory">*</span></label> -->
								<div class="input-group">
									<input class="form-control required" maxlength="50"
										ng-model="formData.f_name" ng-disabled="disable_all"
										type="text" id="f_name" name="f_name" placeholder="First Name"">
									<span class="input-group-addon" id="form_div_f_name_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Name cannot be left blank"></i></span>

								</div>
							</div>
							<div class="col-md-1 spanerrors spanerrorstab1" id="alert_f_name"
								style="width: 1%; display: none;">
								<label>&nbsp;</label><span><i
									class="fa fa-exclamation-circle" aria-hidden="true"
									title="Name cannot be left blank"></i></span>
							</div>
						<!-- 	<div class="col-md-3">
								<label for="m_name">Middle Name</label> <input
									class="form-control" ng-disabled="disable_all" type="text"
									ng-model="formData.m_name" id="m_name" name="m_name"
									placeholder="Middle Name" maxlength="50">
							</div>

							<div class="col-md-2" style="padding-right: 0">
								<label for="l_name">Last Name</label> <input
									class="form-control" ng-disabled="disable_all"
									ng-model="formData.l_name" type="text" id="l_name"
									name="l_name" placeholder="Last Name" maxlength="50">
							</div> -->
						</div>




<div class="col-md-12 addmargin">
							<div class="col-md-2 addstyle">
								<label for="m_name">Middle Name</label>
							</div>
							<div class="col-md-4" id="form_div_f_name">
								<!-- <label>First Name <span class="mandatory">*</span></label> -->
								<div class="input-group">
										 <input
									class="form-control" ng-disabled="disable_all" type="text"
									ng-model="formData.m_name" id="m_name" name="m_name"
									placeholder="Middle Name" maxlength="50">
									<span class="input-group-addon" id="form_div_f_name_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Name cannot be left blank"></i></span>

								</div>
							</div>
							<div class="col-md-1 spanerrors spanerrorstab1" id="alert_f_name"
								style="width: 1%; display: none;">
								<label>&nbsp;</label><span><i
									class="fa fa-exclamation-circle" aria-hidden="true"
									title="Name cannot be left blank"></i></span>
							</div>
					

							
						</div>






<div class="col-md-12 addmargin">
							<div class="col-md-2 addstyle">
								<label for="l_name">Last Name</label> 
							</div>
							<div class="col-md-4" id="form_div_f_name">
								<!-- <label>First Name <span class="mandatory">*</span></label> -->
								<div class="input-group">
									<input
									class="form-control" ng-disabled="disable_all"
									ng-model="formData.l_name" type="text" id="l_name"
									name="l_name" placeholder="Last Name" maxlength="50">
									<span class="input-group-addon" id="form_div_f_name_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Name cannot be left blank"></i></span>

								</div>
							</div>
							<div class="col-md-1 spanerrors spanerrorstab1" id="alert_f_name"
								style="width: 1%; display: none;">
								<label>&nbsp;</label><span><i
									class="fa fa-exclamation-circle" aria-hidden="true"
									title="Name cannot be left blank"></i></span>
							</div>
					

						</div>









						<!-- <div class="col-md-12 addmargin">
   <div class="col-md-2 addstyle"><label>Category <span class="mandatory">*</span></label></div> 
   <div class="col-md-4">
      <select name="employee_category_id" ng-model="formData.employee_category_id"  ng-disabled="disable_all" id="employee_category_id" class="form-control" ">
        <option value="">Select</option>        
                                      
            <option value="1">WAITER</option>  
                  
        </select>
   </div>
    <div class="col-md-1 spanerrors spanerrorstab1" id="alert_employee_category_id" style="display: none;"> <label>&nbsp;</label><span><i class="fa fa-exclamation-circle" aria-hidden="true" title="Category  cannot be left blank"></i></span></div>
</div> -->



						<div class="col-md-12 addmargin"
							id="form_div_employee_category_code">
							<div class="col-md-2 addstyle">
								<label for="employee_category_id">Category<span
									class="mandatory">*</span></label>
							</div>
							<div class="col-md-3">

								<div class="input-group">
									<input type="hidden" name="employee_category_id"
										id="employee_category_id"
										ng-model="formData.employee_category_id"> <input
										type="text" class="form-control required "
										name="employee_category_code" id="employee_category_code"
										ng-model="formData.employee_category_code"
										ng-disabled="disable_all" placeholder="Code"> <span
										class="input-group-addon"
										id="form_div_employee_category_code_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Category cannot be left blank"></i></span>

								</div>
							</div>
							<div class="col-md-4">
								<input type="text" id="employee_category_name"
									name="employee_category_name" class="form-control widthset"
									ng-model="formData.employee_category_name" disabled="disabled">
							</div>

						</div>




						<div class="col-md-12 addmargin" id="form_div_department_code">
							<div class="col-md-2 addstyle">
								<label for="department_id">Department</label>
							</div>
							<div class="col-md-3" id="form_div_department_code">
								<div class="input-group">
									<input type="hidden" name="department_id" id="department_id"
										ng-model="formData.department_id"> <input type="text"
										class="form-control required" name="department_code"
										id="department_code" ng-model="formData.department_code"
										ng-disabled="disable_all" placeholder="Code"> <span
										class="input-group-addon" id="form_div_department_code_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Department cannot be left blank"></i></span>
								</div>
							</div>
							<div class="col-md-4">
								<input type="text" id="source_department_name"
									name="department_name" class="form-control widthset"
									ng-model="formData.department_name" disabled="disabled">
							</div>

						</div>


						<div class="col-md-12 addmargin" id="form_div_status">
							<div class="col-md-2 addstyle">
								<label for="status">Status <span class="mandatory">*</span></label>
							</div>
							<div class="col-md-4">
								<div class="input-group">
									<select name="status" id="status" ng-disabled="disable_all"
										ng-model="formData.status" class="form-control required"">
										<option value="">select</option>
										<option value="2">Resigned</option>
										<option value="1">Active</option>

									</select> <span class="input-group-addon" id="form_div_status_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Status cannot be left blank"></i></span>
								</div>
							</div>
						</div>

						<div class="col-md-12 addmargin">
							<div class="col-md-2 addstyle">
								<label for="sex">Gender</label>
							</div>

							<div class="col-md-4">
								<md-radio-group ng-model="formData.sex" layout="row"
									ng-disabled="disable_all"> <md-radio-button
									value="1" class="md-primary">Male</md-radio-button> <md-radio-button
									value="2">Female </md-radio-button> </md-radio-group>

							</div>
						</div>

						<div class="col-md-12 addmargin" id="form_div_request_date">
							<div class="col-md-2 addstyle">
								<label for="dob">DOB<span class="mandatory">*</span></label>
							</div>
							<div class="col-md-4">



								<div class="right-inner-addon">
									<div class="input-group">
										<i class="fa fa-calendar" id="calender_icon"
											style="left: 80%; z-index: 4;"></i> <input type="text"
											ng-model="formData.dob"
											ng-change="tableDatevalidation(item.expected_date,$index)"
											class="form-control required" date-picker name="request_date"
											id="dob" placeholder="" ng-disabled="disable_all"> <span
											class="input-group-addon" id="form_div_request_date_error"
											style="display: none;"><i class=" red-tooltip"
											data-toggle="tooltip" data-placement="bottom" title=""
											data-original-title="Date of birth cannot be left blank"></i></span>

									</div>
								</div>
							</div>
						</div>


					</div>
					</md-content> </md-tab> <md-tab label="JOB"> <md-content class="md-padding">


					<div class="col-md-12 employeetabsdiv" id="employeetabs2div"
						style="display: block;">

						<div class="col-md-12 addmargin">
							<div class="col-md-4 addstyle">
								<label for="doj">Date Of Joining </label>
							</div>
							<div class="col-md-4">
								<!-- <div class="input-group date">
          <div class="input-group-addon">
            <i class="fa fa-calendar"></i>
          </div>
             <input type="text" class="form-control" name="doj" id="doj" placeholder="Date Of Joining" readonly="readonly" ">
         </div> -->

								<div class="input-group">

									<div class="right-inner-addon">

										<i class="fa fa-calendar" id="calender_icon"
											style="left: 80%; z-index: 4;"></i> <input type="text"
											row-add="addItem($index)" row-save="saveData(event)"
											row-delete="removeItem($index)"
											ng-change="tableDatevalidation(item.expected_date,$index)"
											class="form-control " date-picker name="request_date"
											id="doj" ng-model="formData.doj" placeholder=""
											ng-disabled="disable_all">

									</div>
								</div>
							</div>
						</div>

						<div class="col-md-12 addmargin" id="form_div_wage_type">
							<div class="col-md-4 addstyle">
								<label for="wage_type">Wage Type <span class="mandatory">*</span></label>
							</div>
							<div class="col-md-4">
								<div class="input-group">

									<select name="wage_type" id="wage_type"
										class="form-control required" ng-model="formData.wage_type"
										ng-disabled="disable_all"">
										<option value="">Select</option>

										<option value="1">Hourly</option>

										<option value="2">Daily</option>

										<option value="3">Weekly</option>

										<option value="4">Monthly</option>

									</select><span class="input-group-addon" id="form_div_wage_type_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Wage type  cannot be left blank"></i></span>
								</div>
							</div>

						</div>

						<div class="col-md-12 addmargin" id="form_div_cost_per_hour">
							<div class="col-md-4 addstyle">
								<label for="cost_per_hour">Cost per Hour <span
									class="mandatory">*</span></label>
							</div>
							<div class="col-md-4">
								<div class="input-group">
									<input maxlength="8"
										ng-model="formData.cost_per_hour" ng-disabled="disable_all"
										valid-number name="cost_per_hour" id="cost_per_hour"
										class="form-control required" placeholder="Cost per Hour"">
									<span class="input-group-addon"
										id="form_div_cost_per_hour_error" style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Cost type  cannot be left blank"></i></span>
								</div>
							</div>
						</div>

						<div class="col-md-12 addmargin">
							<div class="col-md-4 addstyle">
								<label for="over_time_pay_rate">Over Time Pay Rate</label>
							</div>
							<div class="col-md-4">
								<input maxlength="${8+settings['decimalPlace']+1}"
									ng-disabled="disable_all"
									ng-model="formData.over_time_pay_rate"
									name="over_time_pay_rate" id="over_time_pay_rate"
									class="form-control" valid-number
									placeholder="Over Time Pay Rate"">
							</div>
						</div>

						<div class="col-md-12 addmargin">
							<div class="col-md-4 addstyle">
								<label for="work_permit">Permit Number</label>
							</div>
							<div class="col-md-4">
								<input maxlength="255" ng-disabled="disable_all"
									ng-model="formData.work_permit" size="20" valid-number
									name="work_permit" class="form-control" id="work_permit"
									placeholder="Permit Number"">
							</div>
						</div>

						<div class="col-md-12 addmargin" id="form_div_card_no">
							<div class="col-md-4 addstyle">
								<label for="card_no">Card Number <span class="mandatory">*</span></label>
							</div>
							<div class="col-md-4">
								<div class="input-group">

									<input maxlength="50" ng-disabled="disable_all"
										ng-model="formData.card_no" valid-number size="20"
										id="card_no" name="card_no" class="form-control required">

									<span class="input-group-addon" id="form_div_card_no_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Card number cannot be left blank"></i></span>
								</div>
							</div>



						</div>


					</div>





					</md-content> </md-tab> <md-tab label="PERSONNEL"> <md-content
						class="md-padding">


					<div class="col-md-12 employeetabsdiv" id="employeetabs3div"
						style="display: block;">

						<div class="col-md-12 addmargin" id="form_div_address">
							<div class="col-md-3 addstyle">
								<label for="address">Permanent Address<span
									class="mandatory">*</span></label>
							</div>
							<div class="col-md-7">
								<div class="input-group">
									<textarea name="address" ng-model="formData.address"
										maxlength="100" id="address" class="form-control required"
										ng-disabled="disable_all"></textarea>
									<span class="input-group-addon" id="form_div_address_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Address  cannot be left blank"></i></span>
								</div>
							</div>

						</div>

						<div class="col-md-12 addmargin">
							<div class="col-md-3 addstyle">
								<label for="country">Country</label>
							</div>
							<div class="col-md-3">
								<input maxlength="50" ng-disabled="disable_all" size="20"
									ng-model="formData.country" name="country" id="country"
									class="form-control" placeholder="Country"">
							</div>
							<div class="col-md-1">
								<label for="phone">Phone</label>
							</div>
							<div class="col-md-3">
								<input maxlength="20" ng-disabled="disable_all"
									ng-model="formData.phone" size="20" name="phone" id="phone"
									class="form-control" valid-number placeholder="Phone"
									onkeypress="return NumericValidation(this, event, false, false);"">
							</div>
						</div>


						<div class="col-md-12 addmargin">

							<div class="col-md-3 addstyle">
								<label for="zip_code">Zip Code</label>
							</div>
							<div class="col-md-3">
								<input maxlength="20" ng-disabled="disable_all"
									ng-model="formData.zip_code" size="20" name="zip_code"
									id="zip_code" class="form-control" placeholder="Zip Code"
									onkeypress="return NumericValidation(this, event, false, false);"">
							</div>

							<div class="col-md-1">
								<label for="fax">Fax</label>
							</div>
							<div class="col-md-3">
								<input maxlength="20" size="20" ng-disabled="disable_all"
									ng-model="formData.fax" name="fax" id="fax"
									class="form-control" placeholder="Fax"">
							</div>
						</div>

						<div class="col-md-12 addmargin" id="form_div_email">

							<div class="col-md-3 addstyle">
								<label for="email">Email</label>
							</div>
							<div class="col-md-7">
								<div class="input-group">
									<input maxlength="20" size="20" ng-disabled="disable_all"
										name="email" id="email" ng-model="formData.email"
										class="form-control email" type="text"
										placeholder="Example@abc.com""> <span
										class="input-group-addon" id="form_div_email_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="<spring:message code="comon.email.error"></spring:message>"></i></span>
								</div>
							</div>
						</div>

						<div class="col-md-12 addmargin">
							<div class="col-md-3 addstyle">
								<strong>Present Address Details</strong>
							</div>
							<div class="col-md-7">
								<input type="checkbox" ng-disabled="disable_all"
									ng-model="formData.sameasabove" name="sameasabove"
									id="sameasabove" value="checkbox" ng-click="shipsame();"">
								Same as above
							</div>

						</div>

						<div class="col-md-12 addmargin" id="form_div_loc_address">
							<div class="col-md-3 addstyle">
								<label for="loc_address">Present Address<span class="mandatory">*</span></label>
							</div>
							<div class="col-md-7">
								<div class="input-group">

									<textarea name="loc_address" ng-model="formData.loc_address"
										id="loc_address" ng-disabled="disable_all"
										class="form-control required"></textarea>
									<span class="input-group-addon" id="form_div_loc_address_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="Present address  cannot be left blank"></i></span>
								</div>
							</div>


						</div>


						<div class="col-md-12 addmargin">
							<div class="col-md-3 addstyle">
								<label for="loc_country">Country</label>
							</div>
							<div class="col-md-3">
								<input maxlength="20" size="20" name="loc_country"
									ng-model="formData.loc_country" ng-disabled="disable_all"
									id="loc_country" class="form-control"
									placeholder="Local Country"">
							</div>
							<div class="col-md-1">
								<label for="loc_phone">Phone</label>
							</div>
							<div class="col-md-3">
								<input maxlength="20" size="20" name="loc_phone" valid-number
									ng-model="formData.loc_phone" id="loc_phone"
									ng-disabled="disable_all" class="form-control"
									placeholder="Local Phone"
									onkeypress="return NumericValidation(this, event, false, false);"">
							</div>
						</div>

						<div class="col-md-12 addmargin">
							<div class="col-md-3 addstyle">
								<label for="loc_zip_code">Zip Code</label>
							</div>
							<div class="col-md-3">
								<input maxlength="20" size="20" name="loc_zip_code"
									ng-model="formData.loc_zip_code" ng-disabled="disable_all"
									id="loc_zip_code" class="form-control"
									placeholder="Local Zip Code"
									onkeypress="return NumericValidation(this, event, false, false);"">
							</div>
							<div class="col-md-1">
								<label for="loc_fax">Fax</label>
							</div>
							<div class="col-md-3">
								<input maxlength="20" size="20" ng-disabled="disable_all"
									ng-model="formData.loc_fax" name="loc_fax" id="loc_fax"
									class="form-control" placeholder="Local fax"">
							</div>
						</div>

						<div class="col-md-12 addmargin"></div>
					</div>




					</md-content> </md-tab> </md-tabs> </md-content>

				</div>
			</div>
		</div>




	</form>
</div>
