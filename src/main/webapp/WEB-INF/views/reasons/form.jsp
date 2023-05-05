<div class="" id="department_form_div" ng-show="show_form">  

	<form class="form-horizontal" id="department_form">
	<jsp:directive.include file="../common/includes/common_form.jsp" />
	
		<div class="form-group" id="form_div_context">
		<label for="dept_type" class="col-sm-2 control-label">Context<i class="text-danger">*</i> </label>
			<div class="col-sm-5">
				<div class="input-group">
				<select class="form-control" id="contexttyp" ng-model="formData.context" ng-change="filterContext()" ng-disabled="disable_all">
                                      <option value="" selected>SELECT</option>
                                        <option value="0" >DISCOUNT</option>
                                        <option value="1">REFUND</option>
                                    </select> 

				<span class="input-group-addon"
		id="form_div_context_error" style="display: none;"><i
		class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
		data-placement="bottom" title=""
		data-original-title="Please select context"></i></span>
				</div>
		   </div>
	   </div>
	   
	    
</form>		
</div>	
	