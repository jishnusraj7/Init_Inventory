
<div class="" id="itemclass_form_div" ng-show="show_form" ng-cloak>  
<form class="form-horizontal" id="itemclass_form">
	
	<div class="">
	<div class="row">
	<div class="col-sm-12">
  <md-content>
    <md-tabs md-dynamic-height md-border-bottom md-selected="selectedIndexTab" >
      <md-tab label="GENERAL">
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
					<div class="form-group" id="form_div_description">
	<label for="description" class="col-sm-3 control-label"><spring:message
			code="common.description"></spring:message> <i class="text-danger"></i></label>

	<div class="col-sm-3">
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
					 <div class="form-group" id="form_div_permitted_for" >
					<label for="parent_id" class="col-sm-3 control-label lb">Permitted For<i class="text-danger">*</i></label>
					<div class="col-sm-3">
						<div class="input-group input-group-lg">
							<select class="form-control" id="permitted_for" ng-disabled="disable_all" ng-model="formData.permitted_for" ng-change="filterPermit()">
                                        <option value="" selected>SELECT</option>
                                        <option value="0" >ITEM DISCOUNT</option>
                                        <option value="1">BILL DISCOUNT</option>
                                    </select> 
									<span class="input-group-addon" id="form_div_permitted_for_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Please Select Permitted For"></i></span>
							</div>
						
					</div>
				</div>
				
					<div class="form-group" id="form_div_account_code">
						<label for="name" class="col-sm-3 control-label lb">Account Code </label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control  code-font-size" 
									name="account_code" id="account_code" ng-model="formData.account_code"
									ng-disabled="disable_all" maxlength="100" placeholder="" >
								<span class="input-group-addon" id="form_div_account_code_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Please select Account Code"></i></span>
							</div>
						</div>
					
					</div>
					
					
							<div class="form-group" id="form_div_startdate">
						<label for="startdate" class="col-sm-3 control-label lb">Date From<i
							class="text-danger">*</i></label>
						<div class="col-sm-3">
							<div class="input-group col-sm-12">
								<div class="right-inner-addon" id="form_div_startdate_discount">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="startdate"
										id="startdate" ng-model="formData.date_from"
										ng-disabled="disable_all" placeholder=""
										ng-change="dateChange()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_startdate_discount_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockregisterreport.startdate.error"></spring:message>"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group" id="form_div_enddate">
						<label for="form_div_enddate" class="col-sm-3 control-label lb">Date To</label>
						<div class="col-sm-3">
							<div class="input-group col-sm-12">
								<div class="right-inner-addon" id="form_div_enddate_discount">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="enddate"
										id="enddate" ng-model="formData.date_to"
										ng-disabled="disable_all" placeholder=""
									    ng-change="tableDatevalidation()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_enddate_discount_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>"></i></span>
							</div>
							<div class="text-danger" id="divErrormsg2"></div>
						</div>
						
					</div>
					<div class="form-group" id="form_div_subclass">
						<label for="fe" class="col-sm-3 control-label lb">Is valid</label>
					
						<div class="col-sm-1">
							
									<md-checkbox name="fe" ng-model="is_Valid1" 		
										aria-label="Checkbox 1" ng-change="arCodeChange()" ng-disabled="disable_all" >
									</md-checkbox>
																	
						</div>
						
					
					</div>
					
					
				
	</md-content>		
	</md-tab>	
	<md-tab label="EDITING" >
        <md-content class="md-padding">
         		
         		 <div class="form-group" id="form_div_allow_editing" ng-show="showEditing">
					<label for="parent_id" class="col-sm-3 control-label lb">Allow Editing<i
							class="text-danger">*</i></label>
					<div class="col-sm-3">
						<div class="input-group input-group-lg">
						<select class="form-control" id="edittyp" ng-model="formData.allow_editing"  ng-disabled="disable_all"  ng-change="filterEditing()">
                                      <option value="0" selected>NONE</option>
                                        <option value="1" >UNIT PRICE</option>
                                        <option value="2">NET PRICE</option>
                                    </select> 
									<span class="input-group-addon" id="form_div_allow_editing_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Please select edit type"></i></span>
							
						</div>
					</div>
				</div>
				
       
				<div class="form-group" id="form_div_item_specific" ng-show="showItemSpecific">
						<label for="name" class="col-sm-3 control-label lb">Item Specific</label>
					<div></div>
						<div class="col-sm-1">
							
									<md-checkbox name="fe" ng-model="itemSpecific" 		
										aria-label="Checkbox 1" ng-change="clearDiscountValue()" ng-disabled="disable_all" >
									</md-checkbox>
																	
						</div>
						<div class="col-sm-3"> 
						     <button type="button" id="viewBtn1" ng-click="addItemSpecificDiscount()"  
						      ng-show="itemSpecific" ng-disabled="disable_all">View</button></div>
					</div>
				     <div ng-show="showDiscount">
					<div class="form-group" id="form_div_discount" ng-show="!itemSpecific">
						<label for="name" class="col-sm-3 control-label lb">Discount<i class="text-danger">*</i></label>
					
						<div class="col-sm-3">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control  code-font-size" 
									name="discount" id="discount" ng-model="formData.price"
									ng-disabled="disable_all" maxlength="${11+settings['decimalPlace']+1}" placeholder="" valid-number >
								<span class="input-group-addon" id="form_div_discount_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Please Enter Discount Price"></i></span>
							</div>
						</div>
					
					</div></div>
					
				<div class="form-group" id="form_div_percentage" ng-show="showPercentAndOverrde">
						<label for="name" class="col-sm-3 control-label lb">Percentage</label>
					
						<div class="col-sm-1">
							
									<md-checkbox name="fe" ng-model="percentageVal" 		
										aria-label="Checkbox 1" ng-change="percentChnge()" ng-disabled="disable_all" >
									</md-checkbox>
																	
						</div>
						<label for="name" class="col-sm-2 overridable_label control-label lb">Overridable</label>
					
						<div class="col-sm-1">
							
									<md-checkbox name="fe" ng-model="isOverridble" 		
										aria-label="Checkbox 1" ng-change="changeOverride()" ng-disabled="disable_all" >
									</md-checkbox>
																	
						</div>
					</div>
				     <div class="form-group" id="form_div_group_qty" ng-show="isAllowEditingNone">
						<label for="name" class="col-sm-3 control-label lb">Grouping Quantity<i class="text-danger">*</i></label>
					
						<div class="col-sm-2">
							<div class="input-group input-group-lg">
								<input type="text" class="form-control  code-font-size" 
									name="grouping_quantity" id="grouping_quantity" ng-model="formData.grouping_quantity"
									ng-disabled="disable_all" maxlength="10" placeholder="" numbers-only >
								<span class="input-group-addon" id="form_div_group_qty_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Card number cannot be left blank"></i></span>
							</div>
						</div>
						<div class=col-sm-1">Qty
						</div>
					
					</div>	
					<div class="form-group" id="form_div_enable_pass">
						<label for="name" class="col-sm-3 control-label lb">Enable Change Password</label>
					
						<div class="col-sm-3">
							
									<md-checkbox name="fe" ng-model="enablePass" 		
										aria-label="Checkbox 1" ng-change="changeToSubClss()" ng-disabled="disable_all" >
									</md-checkbox>
																	
						</div>
					</div>
						
					<div class="form-group" id="form_div_passwrd">
						<label for="name" class="col-sm-3 control-label lb">Password<i class="text-danger">*</i></label>
					
						<div class="col-sm-2">
							<div class="input-group input-group-lg">
								<input type="password" class="form-control" ng-model="formData.disc_password" 
						id="password1" name="password1" ng-disabled="!enablePass">
								<span class="input-group-addon" id="form_div_passwrd_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Discount Password cannot be left blank"></i></span>
							</div>
						</div>
						
					
					</div>	
				<div class="form-group" id="form_div_confirm_pass">
						<label for="name" class="col-sm-3 control-label lb">Confirm Password</label>
					
						<div class="col-sm-2">
							<div class="input-group input-group-lg">
								<input type="password" class="form-control" 
						id="password2" name="password2" ng-disabled="!enablePass" ng-model="formData.conf_pass">
								<span class="input-group-addon" id="form_div_confirm_pass_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="Passwords do not match"></i></span>
							</div>
						</div>
						
					
					</div>	
					
							

						
						
							
        </md-content>
      </md-tab>	
            <md-tab label="PROMOTION" ng-if="isAllowEditingNone">
        <md-content class="md-padding">
          <div class="form-group" id="form_div_subclass">
						<label for="name" class="col-sm-3 control-label lb">Is Promotion</label>
					
						<div class="col-sm-3">
							
									<md-checkbox name="fe" ng-model="obj.isPromotion" 	ng-disabled="disable_all"	
										aria-label="Checkbox 1" ng-change="changePromotion()"  >
									</md-checkbox>
																	
						</div>
					</div>
					
					 <div class="form-group" id="form_div_subclass" >
						<label for="name" class="col-sm-3 control-label lb">Time From</label>
					
						<div class="col-sm-3">
					
					<div class="col-sm-1 timebox" >		  
               <select name="start_time[]" id="start_time" ng-disabled="disabledByPromo" class="form-control">
                                    <option value="00">00</option>
                                      <option value="01">01</option>
                                      <option value="02">02</option>
                                      <option value="03">03</option>
                                      <option value="04">04</option>
                                      <option value="05">05</option>
                                      <option value="06">06</option>
                                      <option value="07">07</option>
                                      <option value="08">08</option>
                                      <option value="09">09</option>
                                      <option value="10">10</option>
                                      <option value="11">11</option>
                                      <option value="12">12</option>
                                      <option value="13">13</option>
                                      <option value="14">14</option>
                                      <option value="15">15</option>
                                      <option value="16">16</option>
                                      <option value="17">17</option>
                                      <option value="18">18</option>
                                      <option value="19">19</option>
                                      <option value="20">20</option>
                                      <option value="21">21</option>
                                      <option value="22">22</option>
                                      <option value="23">23</option>
                        
               </select>
            </div>
           <div class="col-sm-1" style="display:inline">
              <select name="start_time[]" id="start_time_min" ng-disabled="disabledByPromo" class="form-control">
                                    <option value="00">00</option>
                                      <option value="01">01</option>
                                      <option value="02">02</option>
                                      <option value="03">03</option>
                                      <option value="04">04</option>
                                      <option value="05">05</option>
                                      <option value="06">06</option>
                                      <option value="07">07</option>
                                      <option value="08">08</option>
                                      <option value="09">09</option>
                                      <option value="10">10</option>
                                      <option value="11">11</option>
                                      <option value="12">12</option>
                                      <option value="13">13</option>
                                      <option value="14">14</option>
                                      <option value="15">15</option>
                                      <option value="16">16</option>
                                      <option value="17">17</option>
                                      <option value="18">18</option>
                                      <option value="19">19</option>
                                      <option value="20">20</option>
                                      <option value="21">21</option>
                                      <option value="22">22</option>
                                      <option value="23">23</option>
                                      <option value="24">24</option>
                                      <option value="25">25</option>
                                      <option value="26">26</option>
                                      <option value="27">27</option>
                                      <option value="28">28</option>
                                      <option value="29">29</option>
                                      <option value="30">30</option>
                                      <option value="31">31</option>
                                      <option value="32">32</option>
                                      <option value="33">33</option>
                                      <option value="34">34</option>
                                      <option value="35">35</option>
                                      <option value="36">36</option>
                                      <option value="37">37</option>
                                      <option value="38">38</option>
                                      <option value="39">39</option>
                                      <option value="40">40</option>
                                      <option value="41">41</option>
                                      <option value="42">42</option>
                                      <option value="43">43</option>
                                      <option value="44">44</option>
                                      <option value="45">45</option>
                                      <option value="46">46</option>
                                      <option value="47">47</option>
                                      <option value="48">48</option>
                                      <option value="49">49</option>
                                      <option value="50">50</option>
                                      <option value="51">51</option>
                                      <option value="52">52</option>
                                      <option value="53">53</option>
                                      <option value="54">54</option>
                                      <option value="55">55</option>
                                      <option value="56">56</option>
                                      <option value="57">57</option>
                                      <option value="58">58</option>
                                      <option value="59">59</option>
                        
                </select>
            </div>
																	
						</div>
					</div>
					
					<div class="form-group" id="form_div_subclass">
						<label for="name" class="col-sm-3 control-label lb">Time To</label>
					
						<div class="col-sm-3">
							
										<div class="col-sm-1 timebox" >		  
               <select name="end_time[]" id="end_time" ng-disabled="disabledByPromo" class="form-control">
                                    <option value="00">00</option>
                                      <option value="01">01</option>
                                      <option value="02">02</option>
                                      <option value="03">03</option>
                                      <option value="04">04</option>
                                      <option value="05">05</option>
                                      <option value="06">06</option>
                                      <option value="07">07</option>
                                      <option value="08">08</option>
                                      <option value="09">09</option>
                                      <option value="10">10</option>
                                      <option value="11">11</option>
                                      <option value="12">12</option>
                                      <option value="13">13</option>
                                      <option value="14">14</option>
                                      <option value="15">15</option>
                                      <option value="16">16</option>
                                      <option value="17">17</option>
                                      <option value="18">18</option>
                                      <option value="19">19</option>
                                      <option value="20">20</option>
                                      <option value="21">21</option>
                                      <option value="22">22</option>
                                      <option value="23">23</option>
                        
               </select>
            </div>
           <div class="col-sm-1" style="display:inline">
              <select name="end_time[]" id="end_time_min" ng-disabled="disabledByPromo" class="form-control">
                                    <option value="00">00</option>
                                      <option value="01">01</option>
                                      <option value="02">02</option>
                                      <option value="03">03</option>
                                      <option value="04">04</option>
                                      <option value="05">05</option>
                                      <option value="06">06</option>
                                      <option value="07">07</option>
                                      <option value="08">08</option>
                                      <option value="09">09</option>
                                      <option value="10">10</option>
                                      <option value="11">11</option>
                                      <option value="12">12</option>
                                      <option value="13">13</option>
                                      <option value="14">14</option>
                                      <option value="15">15</option>
                                      <option value="16">16</option>
                                      <option value="17">17</option>
                                      <option value="18">18</option>
                                      <option value="19">19</option>
                                      <option value="20">20</option>
                                      <option value="21">21</option>
                                      <option value="22">22</option>
                                      <option value="23">23</option>
                                      <option value="24">24</option>
                                      <option value="25">25</option>
                                      <option value="26">26</option>
                                      <option value="27">27</option>
                                      <option value="28">28</option>
                                      <option value="29">29</option>
                                      <option value="30">30</option>
                                      <option value="31">31</option>
                                      <option value="32">32</option>
                                      <option value="33">33</option>
                                      <option value="34">34</option>
                                      <option value="35">35</option>
                                      <option value="36">36</option>
                                      <option value="37">37</option>
                                      <option value="38">38</option>
                                      <option value="39">39</option>
                                      <option value="40">40</option>
                                      <option value="41">41</option>
                                      <option value="42">42</option>
                                      <option value="43">43</option>
                                      <option value="44">44</option>
                                      <option value="45">45</option>
                                      <option value="46">46</option>
                                      <option value="47">47</option>
                                      <option value="48">48</option>
                                      <option value="49">49</option>
                                      <option value="50">50</option>
                                      <option value="51">51</option>
                                      <option value="52">52</option>
                                      <option value="53">53</option>
                                      <option value="54">54</option>
                                      <option value="55">55</option>
                                      <option value="56">56</option>
                                      <option value="57">57</option>
                                      <option value="58">58</option>
                                      <option value="59">59</option>
                        
                </select>
            </div>
																	
						</div>
					</div>
				
					
			<div class="form-group" id="form_div_subclass">
						<label for="name" class="col-sm-3 control-label lb">Week Days</label>
					
						<div class="col-sm-3">
							
									 <div ng-repeat="day in days" style="float: left;padding:2px;">
          <md-checkbox ng-model="formData.selectedDays[day.name]" ng-change="checkboxChanged()" ng-required="!someSelected" 
          ng-disabled="disabledByPromo" /> {{day.name}}
       </md-checkbox> </div>
																	
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
<div class="modal fade" id="itemSpecificModal" role="dialog">
	<div class="modal-dialog modal-md">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Item Specific Discount</h4>
			</div>
			<div class="modal-body">
				
						
							 <div class="form-group" ng-if="table2">
									<table datatable="" dt-options="item.dtOptions1"
										dt-columns="item.dtColumns1" dt-instance="item.dtInstance1" class="table dataClass"></table>
								</div>  
								
							
			</div>

			<div class="modal-footer" >
		   <div>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

				<button class="btn  btn-sm btn-primary save_button savebtn"
					type="button" name="btnSave" ng-click="updateItemSpecificList();" id="btnItemUpdate"
					data-dismiss="modal" data-target="#importDataModal">Update
				</button>
				<!-- <button class="btn  btn-sm btn-primary save_button savebtn" type="button" name="btnSave" id="btnSave" ng-click="fun_save_form()" style="display: block;">
			<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
		</button> -->
			</div>
			
			</div>
		</div>

	</div>
</div>


