<div class="" id="item_category_form_div">
	<form class="form-horizontal" id="item_category_form">
		<div class="">

			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id">
			<div class="form-group" id="form_div_name">
				<label for="name" class="col-sm-2 control-label "> <spring:message
						code="company.profile.companyname"></spring:message> <i
					class="text-danger">*</i>
				</label>
				<div class="col-sm-5">
					<div class="input-group input-group-lg">
						<input type="text" class="form-control required code-font-size"
							name="name" id="name" ng-model="formData.company_name"
							ng-disabled="disable_all" maxlength="50" placeholder="Name">
						<span class="input-group-addon" id="form_div_name_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
					</div>
				</div>
			</div>

			<div class="form-group" id="form_div_address">
				<label for="address" class="col-sm-2 control-label"><spring:message
						code="supplier.address"></spring:message> <i class="text-danger">*</i>
				</label>
				<div class="col-sm-5">
					<div class="input-group">
						<textarea class="form-control required" name="address"
							maxlength="250" required id="address" ng-model="formData.address"
							ng-disabled="disable_all" placeholder="Address">{{formData.address}}					
					</textarea>
						<span class="input-group-addon" id="form_div_address_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="supplier.error.address"></spring:message>"></i></span>
					</div>
				</div>
			</div>

			<div class="form-group" id="form_div_tin_no">
				<label for="tin_no" class="col-sm-2 control-label"><spring:message
						code="supplier.tinNo"></spring:message> </label>
				<div class="col-sm-5">
					<div class="input-group suppliertext">
						<input type="text" class="form-control" name="tinNo" id="tinNo"
							maxlength="20" ng-model="formData.tin_no"
							ng-disabled="disable_all" placeholder="Tin No">
					</div>
				</div>
			</div>

			<div class="form-group ">
				<label for="billing_address" class="col-sm-2 control-label"><spring:message
						code="purchaseorder.billing_address"></spring:message> </label>
				<div class="col-sm-5" id="form_div_billing_address">
					<div class="input-group">
						<textarea class="form-control" maxlength="250"
							name="billing_address" id="billing_address"
							ng-model="formData.billing_address" ng-disabled="disable_all"
							placeholder="Billing Address">					
					</textarea>
					</div>
				</div>
			</div>


			<div class="form-group ">
				<label for="delivery_address" class=" col-sm-2 control-label"><spring:message
						code="purchaseorder.delivery_address"></spring:message> </label>
				<div class="col-sm-5" id="form_div_delivery_address">

					<div class="input-group">
						<textarea class="form-control" maxlength="250"
							name="delivery_address" id="delivery_address"
							ng-model="formData.shipping_address" ng-disabled="disable_all"
							placeholder="Delivery Address">
					
					</textarea>
					</div>
				</div>
			</div>

		</div>
	</form>
</div>