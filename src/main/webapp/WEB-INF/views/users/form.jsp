

<c:if test="${(companytype!=0) ? true : false}">
<div class="" id="users_form_div" ng-show="show_form">

<div class="" id="users_form_div" ng-show="show_form">
	<form class="form-horizontal" id="users_form">
		
			<input type="hidden" id="id" name="id" value="" ng-model="formData.id">


<div class="form-group " id="form_div_code">
<label for="code" class="col-sm-2 control-label"><spring:message
			code="users.code"></spring:message> <i class="text-danger">*</i></label>
   <div class="col-sm-3">
		<div class="input-group">
			<input type="text" class="form-control required code-font-size" name="code" id="code" ng-model="formData.code" ng-change="isCodeExistis(formData.code)" maxlength="10"  ng-disabled="disable_code"
				placeholder="" capitalize> <span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_code_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.code"></spring:message>"></i></span>
						<span ng-bind="existing_code" class="existing_code_lbl" ng-hide="hide_code_existing_er"></span>
		
		</div>
	</div>
</div>
	
<div class="form-group " id="form_div_name" ng-show="namehide">
<label for="name" class="col-sm-2 control-label"><spring:message
			code="users.name"></spring:message> <i class="text-danger">*</i></label>
   <div class="col-sm-3">
		<div class="input-group">
			<input type="text" class="form-control required " name="name" id="name" ng-model="formData.name"  maxlength="50"  ng-disabled="disable_all"
				placeholder=""> <span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_name_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.name"></spring:message>"></i></span>
						
		</div>
	</div>
</div>	


<div class="form-group " id="" ng-hide="namehide">
<label for="name" class="col-sm-2 control-label"><spring:message
			code="users.name"></spring:message> <i class="text-danger">*</i></label>
   <div class="col-sm-3">
		<div class="input-group">
		{{formData.name}}
		
					</div>
	</div>
</div>	


<div class="form-group " id="" ng-hide="namehide">
<label for="user_group_id" class="col-sm-2 control-label"><spring:message
			code="users.user_group_id"></spring:message> <i class="text-danger">*</i></label>
   <div class="col-sm-3">
		<div class="input-group">
{{userGroup}}		
					</div>
	</div>
</div>	





<div class="form-group" id="form_div_user_group_id" ng-show="namehide">
	<label for="employee_id" class="col-sm-2 control-label"> Employee</label>

	<div class="col-sm-3">
		<div class="input-group">
			<select class=" form-control selectpicker " id="employee_id"
							name="employee_id" ng-options="v.id as v.f_name for v in employeeList"
							 ng-model="formData.employee_id"
							ng-disabled="disable_all">
						</select> <span class="input-group-addon"
				id="form_div_employee_id_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.user_group_id"></spring:message>"></i></span>
		</div>
	</div>

</div>






<div class="form-group" id="form_div_user_group_id" ng-show="namehide">
	<label for="user_group_id" class="col-sm-2 control-label"><spring:message
			code="users.user_group_id"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<select class=" form-control selectpicker " id="user_group_id"
							name="user_group_id" ng-options="v.id as v.name for v in userGroupList"
							 ng-model="formData.user_group_id"
							ng-disabled="disable_all">
						</select> <span class="input-group-addon"
				id="form_div_user_group_id_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.user_group_id"></spring:message>"></i></span>
		</div>
	</div>

</div>					




	
<div class="form-group" id="form_div_password">
	<label for="password" class="col-sm-2 control-label"><spring:message
			code="users.password"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="password" class="form-control" name="name" id="password" ng-model="formData.password"  maxlength="40" ng-disabled="disable_all"
				placeholder=""> <span class="input-group-addon"
				id="form_div_password_error" ><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.password"></spring:message>"></i></span>
		</div>
	</div>

</div>	





<div class="form-group" id="form_div_confirm_password">
	<label for="confirm_password" class="col-sm-2 control-label"><spring:message
			code="users.confirm_password"></spring:message></label>

	<div class="col-sm-4">
		<div class="input-group">
			<input type="password" class="form-control" name="confirm_password" id="confirm_password"  ng-model="cnfrm_password" maxlength="50" ng-disabled="disable_all"
				placeholder=""> 
				<span class="passmismatch" ng-show="pass_miss"> password mismatch</span>
		</div>
	</div>

</div>	




			
		
		<div class="form-group" id="form_div_card_no">
	<label for="card_no" class="col-sm-2 control-label"><spring:message
			code="users.cardno"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="text" ng-disabled="disable_code" class="form-control required" name="" id="card_no" valid-number ng-model="formData.card_no" maxlength="50">
                    <span class="input-group-addon" id="form_div_card_no_error" style="display:none">
		              <i class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
		                    data-placement="bottom" title=""
		                    data-original-title="<spring:message code="users.error.cardNo"></spring:message>"></i>
                     </span>

		</div>
	</div>

</div>		
		
	

	

		
	
<div class="form-group" id="form_div_email">
	<label for="email" class="col-sm-2 control-label"><spring:message
			code="users.email"></spring:message></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="text" class="form-control email " name="email" id="email" ng-model="formData.email"  maxlength="100" ng-disabled="disable_all"
				placeholder="" > <span class="input-group-addon"
				id="form_div_email_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.email"></spring:message>"></i></span>
		</div>
	</div>

</div>		


<div class="form-group" id="form_div_valid_from">
	<label for="valid_from" class="col-sm-2 control-label"><spring:message
			code="users.valid_from"></spring:message></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="text" class="form-control " daterange-picker name="valid_from" id="valid_from" ng-model="formData.valid_from"  maxlength="100" ng-disabled="disable_all"
				placeholder=""> 
		<span class="passmismatch" ng-show="date">valid from less than vailid to</span>
				
		</div>
	</div>

</div>		




<div class="form-group" id="form_div_valid_to">
	<label for="valid_to" class="col-sm-2 control-label"><spring:message
			code="users.valid_to"></spring:message></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="text" class="form-control " daterange-picker name="valid_to" id="valid_to" ng-model="formData.valid_to"  maxlength="100" ng-disabled="disable_all"
				placeholder=""> 
		</div>
	</div>

</div>	

<div class="form-group" id="form_div_setasactive">
				<label for="setasactive" class="col-sm-2 control-label"><spring:message
						code="users.setasactive"></spring:message></label>
				<div class="col-sm-3">
					<div class="input-group" id="is_active">

						<md-checkbox type="checkbox" ng-model="formData.is_active"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="setasactive" class="chck_box_div"></md-checkbox>




					</div>
				</div>
			</div>



 <div id="chgpass" ng-show="chgpass">
<button ng-disabled="disable_all" id="chgbtn" type="button" class="btn btn-info" data-toggle="collapse" data-target="#passwordChangediv" ng-click="popup();" >Change Password</button>
</div> 




			
			
	
        <div class="collapse" id="passwordChangediv">
          
          <input type="hidden" id="id_modal" name="id_modal" value=""
				ng-model="formData1.id">
   <div class="form-group oldPassword" id="form_div_old_password">
	<label for="old_password" class="col-sm-2 control-label"><spring:message
			code="users.old_password"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="password" class="form-control" name="old_password" id="old_password" ng-model="formData1.old_password"  maxlength="40"
				placeholder=""> 
				
				<span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_old_password_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.old_password"></spring:message>"></i></span>
				
	
 			
		</div>
		
	</div>
	<span class="passmismatch" ng-show="old_pass_miss2"> Old Password Incorrect</span>
	

</div>	


 <div class="form-group" id="form_div_new_password">
	<label for="new_password" class="col-sm-2 control-label"><spring:message
			code="users.new_password"></spring:message><i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="password" class="form-control" name="new_password" id="new_password" ng-model="formData1.new_password"  maxlength="40"
				placeholder=""> 
				
				<span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_new_password_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.password"></spring:message>"></i></span>
		</div>
	</div>
	
	

</div>	




<div class="form-group" id="form_div_confirm_password2">
	<label for="confirm_password2" class="col-sm-2 control-label"><spring:message
			code="users.confirm_password"></spring:message></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="password" class="form-control" name="confirm_password" id="confirm_password2"  ng-model="formData1.cnfrm_password" maxlength="40" 
				placeholder=""> 
		</div>
		
	</div>
	<span class="passmismatch" ng-show="cnfrm_pass_miss2"> password mismatch</span>

</div>	


		<div class="col-sm-2"></div>
		<div class="col-sm-3">
			<div class="btnToggle">
		  		 <button type="button" class="btn btn-primary"  ng-click="fun_reset_password()" >Update</button>
	     		  <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#passwordChangediv">Cancel</button>
 			</div>
 		</div>
          
          
        </div>
        
       
           
        
   
     
	    
			
</form>		
</div>	
</c:if>

<c:if test="${(companytype==0) ? true : false}">
<div class="" id="users_form_div" ng-show="show_form">

		<div class="col-md-12 spanerrors spanerrorstab1" id="alert_required" ng-show="tableShwerr"  style="color: red;" > *select atleat one shop*</div>

	<form class="form-horizontal" id="users_form">
		
		
		<div class="">
			<div class="row">
				<div class="col-sm-12">
					<md-content > <md-tabs md-dynamic-height
						md-border-bottom md-selected="selectedIndexTab"> <md-tab
						label="GENERAL " > <md-content class="md-padding">
			<input type="hidden" id="id" name="id" value="" ng-model="formData.id">


<div class="form-group " id="form_div_code">
<label for="code" class="col-sm-2 control-label"><spring:message
			code="users.code"></spring:message> <i class="text-danger">*</i></label>
   <div class="col-sm-3">
		<div class="input-group">
			<input type="text" class="form-control required code-font-size" name="code" id="code" ng-model="formData.code" ng-change="isCodeExistis(formData.code)" maxlength="10"  ng-disabled="disable_code"
				placeholder="" capitalize> <span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_code_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.code"></spring:message>"></i></span>
						<span ng-bind="existing_code" class="existing_code_lbl" ng-hide="hide_code_existing_er"></span>
		
		</div>
	</div>
</div>
	
<div class="form-group " id="form_div_name" ng-show="namehide">
<label for="name" class="col-sm-2 control-label"><spring:message
			code="users.name"></spring:message> <i class="text-danger">*</i></label>
   <div class="col-sm-3">
		<div class="input-group">
			<input type="text" class="form-control required " name="name" id="name" ng-model="formData.name"  maxlength="50"  ng-disabled="disable_all"
				placeholder=""> <span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_name_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.name"></spring:message>"></i></span>
						
		</div>
	</div>
</div>	


<div class="form-group " id="" ng-hide="namehide">
<label for="name" class="col-sm-2 control-label"><spring:message
			code="users.name"></spring:message> <i class="text-danger">*</i></label>
   <div class="col-sm-3">
		<div class="input-group">
		{{formData.name}}
		
					</div>
	</div>
</div>	


<div class="form-group " id="" ng-hide="namehide">
<label for="user_group_id" class="col-sm-2 control-label"><spring:message
			code="users.user_group_id"></spring:message> <i class="text-danger">*</i></label>
   <div class="col-sm-3">
		<div class="input-group">
{{userGroup}}		
					</div>
	</div>
</div>	





<div class="form-group" id="form_div_user_group_id" ng-show="namehide">
	<label for="employee_id" class="col-sm-2 control-label"> Employee</label>

	<div class="col-sm-3">
		<div class="input-group">
			<select class=" form-control selectpicker " id="employee_id"
							name="employee_id" ng-options="v.id as v.f_name for v in employeeList"
							 ng-model="formData.employee_id"
							ng-disabled="disable_all">
						</select> <span class="input-group-addon"
				id="form_div_employee_id_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.user_group_id"></spring:message>"></i></span>
		</div>
	</div>

</div>






<div class="form-group" id="form_div_user_group_id" ng-show="namehide">
	<label for="user_group_id" class="col-sm-2 control-label"><spring:message
			code="users.user_group_id"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<select class=" form-control selectpicker " id="user_group_id"
							name="user_group_id" ng-options="v.id as v.name for v in userGroupList"
							 ng-model="formData.user_group_id"
							ng-disabled="disable_all">
						</select> <span class="input-group-addon"
				id="form_div_user_group_id_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.user_group_id"></spring:message>"></i></span>
		</div>
	</div>

</div>					




	
<div class="form-group" id="form_div_password">
	<label for="password" class="col-sm-2 control-label"><spring:message
			code="users.password"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="password" class="form-control" name="name" id="password" ng-model="formData.password"  maxlength="40" ng-disabled="disable_all"
				placeholder=""> <span class="input-group-addon"
				id="form_div_password_error" ><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.password"></spring:message>"></i></span>
		</div>
	</div>

</div>	





<div class="form-group" id="form_div_confirm_password">
	<label for="confirm_password" class="col-sm-2 control-label"><spring:message
			code="users.confirm_password"></spring:message></label>

	<div class="col-sm-4">
		<div class="input-group">
			<input type="password" class="form-control" name="confirm_password" id="confirm_password"  ng-model="cnfrm_password" maxlength="50" ng-disabled="disable_all"
				placeholder=""> 
				<span class="passmismatch" ng-show="pass_miss"> password mismatch</span>
		</div>
	</div>

</div>	




			
		
		<div class="form-group" id="form_div_card_no">
	<label for="card_no" class="col-sm-2 control-label"><spring:message
			code="users.cardno"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="text" ng-disabled="disable_code" class="form-control required" name="" id="card_no" valid-number ng-model="formData.card_no" maxlength="50">
                    <span class="input-group-addon" id="form_div_card_no_error" style="display:none">
		              <i class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
		                    data-placement="bottom" title=""
		                    data-original-title="<spring:message code="users.error.cardNo"></spring:message>"></i>
                     </span>

		</div>
	</div>

</div>		
		
	

	

		
	
<div class="form-group" id="form_div_email">
	<label for="email" class="col-sm-2 control-label"><spring:message
			code="users.email"></spring:message></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="text" class="form-control email " name="email" id="email" ng-model="formData.email"  maxlength="100" ng-disabled="disable_all"
				placeholder="" > <span class="input-group-addon"
				id="form_div_email_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.email"></spring:message>"></i></span>
		</div>
	</div>

</div>		


<div class="form-group" id="form_div_valid_from">
	<label for="valid_from" class="col-sm-2 control-label"><spring:message
			code="users.valid_from"></spring:message></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="text" class="form-control " daterange-picker name="valid_from" id="valid_from" ng-model="formData.valid_from"  maxlength="100" ng-disabled="disable_all"
				placeholder=""> 
		<span class="passmismatch" ng-show="date">valid from less than vailid to</span>
				
		</div>
	</div>

</div>		




<div class="form-group" id="form_div_valid_to">
	<label for="valid_to" class="col-sm-2 control-label"><spring:message
			code="users.valid_to"></spring:message></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="text" class="form-control " daterange-picker name="valid_to" id="valid_to" ng-model="formData.valid_to"  maxlength="100" ng-disabled="disable_all"
				placeholder=""> 
		</div>
	</div>

</div>	

<div class="form-group" id="form_div_setasactive">
				<label for="setasactive" class="col-sm-2 control-label"><spring:message
						code="users.setasactive"></spring:message></label>
				<div class="col-sm-3">
					<div class="input-group" id="is_active">

						<md-checkbox type="checkbox" ng-model="formData.is_active"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="setasactive" class="chck_box_div"></md-checkbox>




					</div>
				</div>
			</div>



 <div id="chgpass" ng-show="chgpass">
<button ng-disabled="disable_all" id="chgbtn" type="button" class="btn btn-info" data-toggle="collapse" data-target="#passwordChangediv" ng-click="popup();" >Change Password</button>
</div> 




			
			
	
        <div class="collapse" id="passwordChangediv">
          
          <input type="hidden" id="id_modal" name="id_modal" value=""
				ng-model="formData1.id">
   <div class="form-group oldPassword" id="form_div_old_password">
	<label for="old_password" class="col-sm-2 control-label"><spring:message
			code="users.old_password"></spring:message> <i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="password" class="form-control" name="old_password" id="old_password" ng-model="formData1.old_password"  maxlength="40"
				placeholder=""> 
				
				<span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_old_password_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.old_password"></spring:message>"></i></span>
				
	
 			
		</div>
		
	</div>
	<span class="passmismatch" ng-show="old_pass_miss2"> Old Password Incorrect</span>
	

</div>	


 <div class="form-group" id="form_div_new_password">
	<label for="new_password" class="col-sm-2 control-label"><spring:message
			code="users.new_password"></spring:message><i class="text-danger">*</i></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="password" class="form-control" name="new_password" id="new_password" ng-model="formData1.new_password"  maxlength="40"
				placeholder=""> 
				
				<span class="input-group-addon" min="0" max="99" number-mask=""
				id="form_div_new_password_error" style="display: none;"><i
				class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
				data-placement="bottom" title=""
				data-original-title="<spring:message code="users.error.password"></spring:message>"></i></span>
		</div>
	</div>
	
	

</div>	




<div class="form-group" id="form_div_confirm_password2">
	<label for="confirm_password2" class="col-sm-2 control-label"><spring:message
			code="users.confirm_password"></spring:message></label>

	<div class="col-sm-3">
		<div class="input-group">
			<input type="password" class="form-control" name="confirm_password" id="confirm_password2"  ng-model="formData1.cnfrm_password" maxlength="40" 
				placeholder=""> 
		</div>
		
	</div>
	<span class="passmismatch" ng-show="cnfrm_pass_miss2"> password mismatch</span>

</div>	


		<div class="col-sm-2"></div>
		<div class="col-sm-3">
			<div class="btnToggle">
		  		 <button type="button" class="btn btn-primary"  ng-click="fun_reset_password()" >Update</button>
	     		  <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#passwordChangediv">Cancel</button>
 			</div>
 		</div>
          
          
        </div>
        
       	</md-content> </md-tab> <md-tab label="SHOP INFO" > <md-content
						class="md-padding">
       
           
        
<!-- Main content -->
	<section class="content" id="module_content">
		<div class="row">
			<div class="col-xs-12">


				<div class="box" id="msgtop">
					<div class="box-header with-border">
						<h3 class="box-title"></h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<div class="box-header"></div>

						
							<div class="form-group">
		  <table id="table2" datatable="" dt-options="item.dtOptions1"
		   dt-columns="item.dtColumns1" dt-instance="item.dtInstance1" 
		   class=" table dataClass"></table>
		</div>
     	



						

					</div>

					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</section>
	<!-- /.content -->

     	
        
        
        
        
        
        
        
        
        
        
   
   	</md-content> </md-tab> </md-tabs> </md-content>

				</div>
			</div>
		</div>  
	    
			
</form>	
</div>	
</c:if>	
