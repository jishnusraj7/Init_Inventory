<div class="" id="department_form_div" ng-show="show_form">  

	<form class="form-horizontal" id="department_form">
	<jsp:directive.include file="../common/includes/common_form.jsp" />
	
<div class="form-group" id="form_div_type" >
				<label for="state" class="col-sm-2 control-label">Service Type <i class="text-danger">*</i>
				</label>
				<div class="col-sm-3" >
					<div class="input-group">

<select name="type" id="type" class="form-control required" style="border: 1px solid rgb(204, 204, 204);" ng-model="formData.type" ng-disabled="disable_all">
      <option value="">Select</option>
                      <option value="1">Wholesale/Retail</option>
                      <option value="2">Restaurant</option>
                      <option value="3">KOT Tab</option>
                  
        </select>       <span class="input-group-addon" id="form_div_type_error" style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Type cannot be left blank"></i></span>


					</div>
				</div>
			</div>
		
		
</form>		
</div>	
	