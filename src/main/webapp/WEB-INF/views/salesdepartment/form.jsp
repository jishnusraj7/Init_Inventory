<div class="" id="sales_department_form_div" ng-show="show_form">
	<form class="form-horizontal" id="sales_department_form">
		<div class="">
			<jsp:directive.include file="../common/includes/common_form.jsp" />

			
		</div>
		
			<div class="form-group" id="form_div_accounts_code">
				<label for="accounts_code" class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="salesdepartment.accounts_code"></spring:message> </label>

				<div class="col-sm-6">
					<div class="col-sm-4 ">
					    <div class="col-sm-12 remvpadding">
					    <label for="accounts_code" class="col-sm-2 control-label "><spring:message
						code="salesdepartment.sales"></spring:message> </label>
						<input type="text" class="form-control   "
							name="name" id="name" ng-model="formData.sales_account_code"
							ng-disabled="disable_all" maxlength="20" placeholder="Sales">
						 </div>
						 
						
						 
					
						
						<div class="col-sm-12 remvpadding">	
						  <label for="accounts_code" class="col-sm-2 control-label "><spring:message
						code="salesdepartment.Stock"></spring:message> </label>
							<input type="text" class="form-control   "
							name="name" id="name" ng-model="formData.stock_account_code"
							ng-disabled="disable_all" maxlength="20" placeholder="Stock">
						</div>
						
						
						
						 
						 	<div class="col-sm-12 remvpadding">	
				    	<label for="accounts_code" class="col-sm-12 adjst "><spring:message
						code="salesdepartment.Wages"></spring:message> </label>
						   <input type="text" class="form-control   "
							name="name" id="name" ng-model="formData.wages_account_code"
							ng-disabled="disable_all" maxlength="20" placeholder="Wages">
							
							
						</div>	
						
						 
						  <div class="col-sm-12 remvpadding">	
						  <label for="accounts_code" class="col-sm-12 adjst "><spring:message
						code="salesdepartment.GST_paid"></spring:message> </label>
							<input type="text" class="form-control   "
							name="name" id="name" ng-model="formData.gst_paid_account_code"
							ng-disabled="disable_all" maxlength="20" placeholder="GST paid">
						</div>
						 
						
					</div>
					<div class="col-sm-4 ">
				    
				    
				    
				    	  <div class="col-sm-12 remvpadding">	
						  <label for="accounts_code" class="col-sm-2 control-label "><spring:message
						code="salesdepartment.Purchase"></spring:message> </label>
							<input type="text" class="form-control   "
							name="name" id="name" ng-model="formData.purchase_account_code"
							ng-disabled="disable_all" maxlength="20" placeholder="Purchase">
						</div>
						
				    
				    
				    
					
					
					<div class="col-sm-12 remvpadding">	
						  <label for="accounts_code" class="col-sm-2 control-label "><spring:message
						code="salesdepartment.COGS"></spring:message> </label>
							<input type="text" class="form-control   "
							name="name" id="name" ng-model="formData.cogs_account_code"
							ng-disabled="disable_all" maxlength="20" placeholder="COGS">
						</div>
						
					
					
					
					
						 
						
							<div class="col-sm-12 remvpadding">	
							<label for="accounts_code" class="col-sm-12 adjst"><spring:message
						code="salesdepartment.GST_Collected"></spring:message> </label>
							  <input type="text" class="form-control   "
							  name="name" id="name" ng-model="formData.gst_collected_account_code"
							   ng-disabled="disable_all" maxlength="20" placeholder="GST Collected">
						 </div>
						  
						
						
						
					</div>
					
					
					
				</div>

			</div>
	</form>
</div>