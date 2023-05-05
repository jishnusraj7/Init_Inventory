<div class="" id="item_category_form_div" ng-show="show_form">
	<form class="form-horizontal" id="item_category_form">
		<div class="">
			<jsp:directive.include file="../common/includes/common_form.jsp" />

			<%-- <div class="form-group" id="form_div_indicator">
				<label for="contactperson" class="col-sm-2 control-label"><spring:message
						code="tax.indicator"></spring:message> </label>
				<div class="col-sm-5">
					<div class="input-group">
						<input type="text" class="form-control" name="indicator"
							maxlength="2" id="indicator" ng-model="formData.tax_indicator"
							ng-disabled="disable_all" placeholder=""> <span
							class="input-group-addon" id="form_div_indicator_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="tax.error.indicator"></spring:message>"></i></span>
					</div>
				</div>
			</div> --%>
			
 <div class="col-sm-12" id="taxMain">
		<div class="col-sm-12" id="taxhead">
		    <div class="col-sm-2"></div>
	<!-- 	    <div class="col-sm-2"><label>Name</label></div> -->
		    <div class="col-sm-3"><label>Percentage(%)</label></div>
		    <div class="col-sm-3"><label>Refund Rate</label></div>
		 
		</div>
		
		<div class="col-sm-12" >
		   <div class="col-sm-12" id="tax1" ng-hide="tax1hide">
		    <div class="col-sm-2"><md-checkbox type="checkbox" ng-model="formData.is_tax1_applicable"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="is_compound" ng-click="isTax1Applicable();" ></md-checkbox>
		    <label>{{tax1_name}}</label> </div>
<!-- 		    <div class="col-sm-2" ng-show="formData.is_tax1_applicable"><label>{{tax1_name}}</label></div>
 -->		    <div class="col-sm-3"  ng-show="formData.is_tax1_applicable"><div class="col-sm-7"><input type="text" id="tax1_percentage" ng-model="formData.tax1_percentage" class="iptwidth" valid-number ng-disabled="disable_all" maxlength="3"></div>
		    </div>
		    <div class="col-sm-3" ng-show="formData.is_tax1_applicable"><div class="col-sm-7"><input type="text"  ng-model="formData.tax1_percentage" class="iptwidth" ng-disabled="true" maxlength="3">
		    </div></div>
		 
		</div>
		
		<div class="col-sm-12" id="tax2" ng-hide="tax2hide">
		    <div class="col-sm-2"><md-checkbox type="checkbox" ng-model="formData.is_tax2_applicable"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="is_compound" ng-click="isTax2Applicable();" id="is_tax2_applicable"></md-checkbox>
		    <label>{{tax2_name}}</label> </div>
<!-- 		    <div class="col-sm-2" ng-show="formData.is_tax2_applicable"><label>{{tax2_name}}</label></div>
 -->		    <div class="col-sm-3" ng-show="formData.is_tax2_applicable"><div class="col-sm-7"><input type="text" id="tax2_percentage" ng-model="formData.tax2_percentage"  valid-number class="iptwidth" ng-disabled="disable_all" maxlength="3"></div></div>
		    <div class="col-sm-3" ng-show="formData.is_tax2_applicable"><div class="col-sm-7"><input type="text" ng-model="formData.tax2_percentage" class="iptwidth" ng-disabled="true"></div></div>
		 
	
		</div>
		
		<div class="col-sm-12" id="tax3" ng-hide="tax3hide">
		    <div class="col-sm-2"><md-checkbox type="checkbox" ng-model="formData.is_tax3_applicable"
							ng-true-value="true" ng-false-value="false"
							id="is_tax3_applicable" ng-disabled="disable_all" aria-label="is_compound" ng-click="isTax3Applicable();" ></md-checkbox>
		    <label>{{tax3_name}}</label> </div>
<!-- 		    <div class="col-sm-2" ng-show="formData.is_tax3_applicable"><label>{{tax3_name}}</label></div>
 -->		    <div class="col-sm-3" ng-show="formData.is_tax3_applicable"><div class="col-sm-7"><input type="text" id="tax3_percentage" ng-model="formData.tax3_percentage"  valid-number class="iptwidth" ng-disabled="disable_all" maxlength="3"></div></div>
		    <div class="col-sm-3" ng-show="formData.is_tax3_applicable"><div class="col-sm-7"><input type="text" ng-model="formData.tax3_percentage" class="iptwidth" ng-disabled="true"></div></div>

		
		
		
</div>


		</div>

	</form>
</div>

