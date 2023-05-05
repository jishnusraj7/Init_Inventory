
<div class="" id="stockdisposal_form_div" ng-show="show_form">
	<form class="form-horizontal frm_div_stock_in" id="stockdisposal_form">
		<div class="">
		<div ng-hide="false" class="form_list_con pending_bg" id="po_status">PENDING</div>
			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id">
			<div class="form-group " id="form_div_ref_no">

				<label for="refNo" class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="stockdisposal.refNo">
					</spring:message><i class="text-danger"></i></label>

				<div class="col-sm-3">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							name="REf_No" id="REf_No" value="SL_200" ng-disabled="true"
							placeholder="">
					</div>
				</div>
			</div>



			<div class="form-group " id="form_div_dispose_date">

				<label for="dispose_date"
					class="col-sm-2 control-label code-lbl-font-size"> <!-- <spring:message
						code="stockdisposal.disposlDate"></spring:message> --> Date <i
					class="text-danger">*</i>
				</label>

				<div class="col-sm-3">

					<div class="input-group">
						<div class="right-inner-addon">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control code-font-size required" daterange-picker
								name="received_date" id="received_date"
								ng-model="formData.saledate" ng-disabled="disable_all"
								placeholder="">
						</div>
						<span class="input-group-addon" id="form_div_received_date_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="Adjust date cannot be blank"></i></span>
					</div>

				</div>
			</div>


			<div class="form-group " id="form_div_department_code">
				<label for="department_id"
					class="col-sm-2 control-label code-lbl-font-size"> <spring:message
						code="stockdisposal.department_id"></spring:message> <i
					class="text-danger">*</i></label>
				<div class="col-sm-2">
					<div class="input-group">
						<input type="hidden" name="department_id" id="department_id"
							ng-model="formData.department_id"> <input type="text"
							class="form-control required" name="department_code"
							id="department_code" ng-model="formData.department_code"
							ng-disabled="disable_all" row-save="saveData(event)"
							placeholder="Code"><span class="input-group-addon"
							id="form_div_department_code_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockdisposal.error.dep"></spring:message>"></i></span>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control " name="department_name"
							id="department_name" ng-model="formData.department_name"
							ng-disabled="true" placeholder="">
					</div>
				</div>
			</div>


			<div class="form-group " id="form_div_ref_no">

				<label for="refNo" class="col-sm-2 control-label code-lbl-font-size">Biller<!-- <spring:message
						code="stockdisposal.refNo">
					</spring:message> -->
					<i class="text-danger">*</i></label>

				<div class="col-sm-3">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							name="REf_No" id="REf_No" value="{{REFNO}}" ng-disabled="false"
							placeholder="">
					</div>
				</div>
			</div>

			<div class="form-group " id="form_div_ref_no">

				<label for="refNo" class="col-sm-2 control-label code-lbl-font-size">Customer<!-- <spring:message
						code="stockdisposal.refNo">
					</spring:message> -->
					<i class="text-danger">*</i></label>

				<div class="col-sm-3">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							name="REf_No" id="REf_No" value="{{REFNO}}" ng-disabled="false"
							placeholder="">
					</div>
				</div>
			</div>




			<div class="form-group ">

				<div class="col-sm-6" id="form_div_billing_address">
					<label for="billing_address" class="control-label"><spring:message
							code="purchaseorder.billing_address"></spring:message> </label>
					<div class="input-group">

						<textarea class="form-control" name="billing_address"
							id="billing_address" ng-model="formData.billing_address"
							ng-disabled="disable_all" placeholder="Billing Address">
					
					</textarea>
					</div>
				</div>
				<div class="col-sm-6" id="form_div_delivery_address">
					<label for="delivery_address" class="control-label"><spring:message
							code="purchaseorder.delivery_address"></spring:message> </label>
					<div class="input-group">
						<textarea class="form-control" name="delivery_address"
							id="delivery_address" ng-model="formData.shipping_address"
							ng-disabled="disable_all" placeholder="Delivery Address">
					
					</textarea>
					</div>
				</div>
			</div>

			<div class="form-group " id="form_div_items_tbl">

				<div class="cmmn_maain_tbl">
					<circle-spinner ng-show="prograssing"></circle-spinner>
					<table class="table table-bordered" id="items_table"
						ng-hide="prograssing">
						<tr class="active">
							<th>#</th>
							<th>Item Code & Name</th>
							<th>Unit Rate</th>
							<th>Quantity</th>
							<th>Tax</th>
							<th>Discount</th>

							<th>Total</th>
							<th></th>
						</tr>
						<tr ng:repeat="item in invoice.items">
							

							<td>{{$index + 1}}</td>
							<td ng-click="tableClicked($index)"><div style="width: 100%">
									<div style="float: left; width: 30%; padding-right: 4px;">
										<input type="hidden" ng-model="item.stockRegDtl_id"><input
											type="text" ng:model="item.stock_item_code"
											id="stock_item_code" name="stock_item_code" ng:required
											row-save="saveData(event)" class="input-mini form-control"
											autocompete-text ng-disabled="disable_all"
											ng-style="validation_Sstock_item_code" maxlength="10">
									</div>
									<div style="display: inline-block; width: 43%;">
										<input type="hidden" ng:model="item.stock_item_id"
											id="stock_item_id" name="stock_item_id"> <input
											type="text" class="input-mini form-control"
											ng-model="item.stock_item_name" id="stock_item_name"
											name="stock_item_name" ng-disabled="true"><input
											type="hidden" ng:model="item.stock_item_batch_id"
											id="stock_item_batch_id" name="stock_item_batch_id"><input
											type="hidden" ng:model="item.stock_item_batch_stock"
											id="stock_item_batch_id" name="stock_item_batch_stock">
									</div>
									<div
										style="padding-left: 4px; display: inline-block; width: 25%; height: 34px;">
										<input type="text" ng-disabled="true"
											style="width: 100%; height: 100%" id="uomcode"
											ng-model="item.uomcode" name="uomcode"
											class="input-mini form-control">
									</div></td>
							<td class="item-table-col-width"><input type="text"
								ng:model="item.currentStock"
								class="input-mini form-control algn_rgt" id="currentStock"
								name="currentStock" ng-disabled="true"
								ng-style="validation_currentStock" valid-number></td>
							<td class="item-table-col-width"><input type="text"
								ng-model="item.actual_qty" maxlength="10" select-on-click
								ng:required valid-number
								class="input-mini form-control algn_rgt" id="damaged_qty"
								name="damaged_qty" ng-disabled="disable_all"
								row-save="saveData(event)" ng-style="validation_damaged_qty"
								max-length="10"></td>

							<td class="item-table-col-width"><input type="text"
								value="{{diff(item.currentStock,item.actual_qty)}}"
								maxlength="10" select-on-click id="" name="rate"
								class="input-mini form-control algn_rgt" ng-disabled="true"></td>

							<td class="item-table-col-width"><input type="text" value=""
								maxlength="10" select-on-click id="" name="rate"
								ng:model="item.rate" class="input-mini form-control algn_rgt"
								ng-disabled="disable_all"></td>

<td class="item-table-col-width"><input type="text" value=""
								maxlength="10" select-on-click id="" name="rate"
								ng:model="item.rate" class="input-mini form-control algn_rgt"
								ng-disabled="disable_all"></td>
							<td><a href ng:click="removeItem($index)"
								class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
						</tr>

						<tr>
							<td colspan="3" class="font-style algn_rgt"><label class="">Grand
									Total:</label></td>
							<td class="font-style algn_rgt">100<%-- <label>
										${systemSettings.currencySymbol} {{total() }}</label> --%></td>
							<td class="font-style algn_rgt">100<%-- <label>
										${systemSettings.currencySymbol} {{total() }}</label> --%></td>
							<td class="font-style algn_rgt">100<%-- <label>
										${systemSettings.currencySymbol} {{total() }}</label> --%></td>

							<td class="font-style algn_rgt">100<%-- <label>
										${systemSettings.currencySymbol} {{total() }}</label> --%></td>
							<td><a href ng:click="addItem()" class="btn btn-small"><i
									class="fa fa-plus"></i> </a></td>
						</tr>

					</table>
				</div>
			</div>

			<div class="form-group" id="form_div_item_category_id">
				<label for="parent_id" class="col-sm-2 control-label lb">Tax<!-- <spring:message
							code="itemmaster.item_category_id"></spring:message> --></label>
				<div class="col-sm-10">
					<div class="input-group">
						<select class="form-control" style="width: 230px !important;">
							<option value="VAT@(20%)">VAT@(20%)</option>
							<option value="GST@(10%)">GST@(10%)</option>
							<option value="VAT@(30%)">VAT@(30%)</option>
							<option value="GST@(40%)">GST@(40%)</option>
						</select>
						
						
					</div>
				</div>
			</div>
			
			

	<div class="form-group " id="form_div_ref_no">

				<label for="refNo" class="col-sm-2 control-label code-lbl-font-size">Discount<!-- <spring:message
						code="stockdisposal.refNo">
					</spring:message> -->
					<i class="text-danger"></i></label>

				<div class="col-sm-3">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							name="REf_No" id="REf_No" value="{{REFNO}}" ng-disabled="false"
							placeholder="">
					</div>
				</div>
			</div>

	<div class="form-group" id="form_div_item_category_id">
				<label for="parent_id" class="col-sm-2 control-label lb">Payment Status<!-- <spring:message
							code="itemmaster.item_category_id"></spring:message> --></label>
				<div class="col-sm-10">
					<div class="input-group">
						<select class="form-control" style="width: 230px !important;">
							<option value="Pending">Pending</option>
							<option value="Completed">Completed</option>
						
						</select>
						
						
					</div>
				</div>
			</div>
			<div class="form-group ">

				<div class="col-sm-6" id="form_div_remarks">
					<label for="remarks" class="control-label"><spring:message
							code="purchaseorder.remarks"></spring:message> </label>
					<div class="input-group">
						<textarea class="form-control" name="remarks" id="remarks"
							maxlength="250" ng-model="formData.remarks"
							ng-disabled="disable_all" placeholder="Remarks">
					</textarea>
					</div>
				</div>
				<div class="col-sm-6" id="form_div_terms">
					<label for="terms" class="control-label"><spring:message
							code="purchaseorder.terms"></spring:message> </label>
					<div class="input-group">
						<textarea class="form-control" name="terms" id="terms"
							maxlength="250" ng-model="formData.terms"
							ng-disabled="disable_all" placeholder="Terms">	
					</textarea>
					</div>
				</div>
			</div>

		</div>
	</form>
</div>

