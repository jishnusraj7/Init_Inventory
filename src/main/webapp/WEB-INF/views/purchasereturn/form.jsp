<div class="" id="purchase_return_form_div" ng-show="show_form">
<div class="{{ClassName}}" id="status_div_id">
		<p>{{status}}</p>
	</div>
	<!-- <div ng-hide="isHideReturnStatus" class="form_list_con"
		id="return_status">{{ status }}</div> -->

	<form class="form-horizontal  frm_div_stock_in"
		id="purchase_return_form">
		<div class="">
			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id"> <input type="hidden"
				id="return_status" name="return_status"
				ng-model="formData.return_status">

			<div class="form-group " id="form_div_return_no"
				ng-show="show_return_no">

				<label for="return_no"
					class="col-sm-2 control-label code-lbl-font-size">Return
					No.</label>

				<div class="col-sm-2">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							name="return_No" id="return_No" value="{{formData.return_no}}"
							ng-disabled="true" placeholder="">
					</div>
				</div>

				<div class="col-sm-8" style="cursor: pointer;" align="right"
					ng-show="show_finalize_btn">
					<button class="btn btn-info btn_status"
						ng-click="finalizeReturn(event);">FINALIZE</button>
				</div>
			</div>


			<div class="form-group " id="form_div_return_date">

				<label for="po_date" class="col-sm-2 control-label">Return
					Date<i class="text-danger"></i>
				</label>

				<div class="col-sm-2">
					<div class="input-group">
						<div class="right-inner-addon">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control code-font-size required" daterange-pickerbft
								name="return_date" id="return_date"
								ng-model="formData.return_date" ng-disabled="disable_all"
								placeholder="">
						</div>
						<span class="input-group-addon" id="form_div_received_date_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockout.error.grn_date"></spring:message>"></i></span>
					</div>
				</div>
			</div>


			<div class="form-group " id="form_div_department_code">
				<label for="department_code" class="col-sm-2 control-label"><spring:message
						code="stockin.department"></spring:message> <i class="text-danger">*</i></label>
				<div class="col-sm-2">
					<div class="input-group">
						<input type="hidden" name="department_id" id="department_id"
							ng-model="formData.departmentId"> <input type="text"
							class="form-control required" name="department_code"
							id="department_code" ng-model="department_code"
							ng-disabled="true" placeholder="Code"><span
							class="input-group-addon" id="form_div_department_code_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockin.error.dep"></spring:message>"></i></span>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control required"
							name="department_name" id="department_name"
							ng-model="department_name" ng-disabled="true" placeholder="">
					</div>
				</div>
			</div>


			<div class="form-group " id="form_div_supplier_code">
				<label for="supplier_code" class="col-sm-2 control-label"><spring:message
						code="stockin.supl_name"></spring:message> <i class="text-danger">*</i></label>
				<div class="col-sm-1">
					<div class="input-group">
						<input type="hidden" name="supplier_id" id="supplier_id"
							ng-model="formData.supplier_id"> <input type="text"
							class="form-control required" name="supplier_code"
							style="margin-right: 120px;" id="supplier_code"
							ng-model="formData.supplier_code" row-save="saveData(event)"
							ng-disabled="disable_all" placeholder="Code"><span
							class="input-group-addon" id="form_div_supplier_code_error"
							style="display: none;" ><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockin.error.supplier"></spring:message>"></i></span>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control " name="supplier_name"
							id="supplier_name" ng-model="formData.supplier_name"
							style="margin-left: 80px;" ng-disabled="true" placeholder="">
					</div>
				</div>
			</div>



			<input type="hidden" ng-model="formData.print_date" /> <input
				type="hidden" ng-model="formData.po_status" /> <input type="hidden"
				ng-model="changedate" /> <input id="chkstatuschange" type="hidden">
			<input type="hidden" ng-model="dtlstatusid" /> <input type="hidden"
				ng-model="changeDate" />

			<div class="form-group " id="form_div_items_tbl">

				<div class="cmmn_maain_tbl">
					<circle-spinner ng-show="prograssing"></circle-spinner>
					<table class="table table-bordered" id="items_table"
						ng-hide="prograssing">
						<tr class="active">
							<th>#</th>
							<th ng-hide="true">id</th>
							<th>Item Code/Item Name</th>
							<th>Unit</th>
							<th>R.Unit</th>
							<th ng-show="false">CUnit</th>
							<th>Current Stock</th>
							<th>Return Qty</th>
							<th >Unit Qty</th>
							<th></th>
						</tr>

						<tr ng:repeat="item in returnDtlData.items">
							<td style="width: 20px;">{{$index + 1}}</td>
							<td ng-hide="true"><input type="text" ng:model="item.id"></td>
							<td ng-click="tableClicked($index)" style=""><div
									style="float: left; width: 30%; padding-right: 4px;" class="form_div_stockin_type_error_id">
									<input type="text" ng:model="item.stock_item_code"
										id="stock_item_code" name="stock_item_code" ng:required
										class="input-mini form-control stock_item_code_return" ng-disabled="disable_all"
										autocompete-text>
										<span class="input-group-addon" id="form_div_stockin_type_error"
									style="display: none;"><%-- <i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.stockintype"></spring:message>"></i> --%></span>
								</div>
								<div style="display: inline-block; width: 70%;" >
									<input type="hidden" ng:model="item.stock_item_id"
										id="stock_item_id" name="stock_item_id" class="form-control stock_item_id"> <input
										type="text" class="form-control " ng-disabled="true"
										style="height: 35px;" ng:model="item.stock_item_name"
										id="stock_item_name" name="stock_item_name">
										<span class="input-group-addon" 
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.stockintype"></spring:message>"></i></span>
								</div></td>

							<td style="width: 101px;"><input type="text"
								ng-disabled="true" id="uomcode" ng-model="item.uomcode"
								name="uomcode" class="input-mini form-control ">
								<span class="input-group-addon" id="form_div_stockin_type_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.stockintype"></spring:message>"></i></span>
								</td>

							<td style="width: 100px;"><select
								class="input-mini form-control" id="base_uom_code"
								name="base_uom_code" ng-focus="true"
								ng-model="item.base_uom_code" ng-disabled="disable_all"
								ng-options="v.base_uom_code as v.base_uom_code for v in baseUomCode[$index]"
								ng-change="selectPuomCode(item.base_uom_code,item.return_qty,$index)">
							</select> <span class="input-group-addon" id="form_div_payment_error"
								style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.payment"></spring:message>"></i></span>
								<!-- <td style="width: 100px;"> -->
							<input type="hidden" ng-disabled="false"
								id="compound_unit" ng-model="item.compound_unit"
								name="compoundUnit" class="input-mini form-control"></td>


							<td class="item-table-col-width" style="width: 10%;"><input
								type="text" ng:model="item.current_stock"
								class="input-mini form-control algn_rgt" id="currentStock"
								name="currentStock" ng-disabled="true"
								ng-style="validation_currentStock" valid-number>
								<span class="input-group-addon" id="form_div_stockin_type_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.stockintype"></spring:message>"></i></span>
								</td>
							<td style="width: 101px;"><input type="text"
								ng:model="item.return_qty" ng-keyup="selectPuomCode(item.base_uom_code,item.return_qty,$index)"
								select-on-click id="return_qty" ng-disabled="disable_all"
								row-add="addItem($index,1)"
								valid-number row-delete="removeItem($index,$event)"
								row-save="saveData(event)"
								maxlength="${10+settings['decimalPlace']+1}"
								class="input-mini form-control algn_rgt">
								<span class="input-group-addon" id="form_div_stockin_type_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.stockintype"></spring:message>"></i></span>
								</td>

							<td style="width: 101px;" class="item-table-col-width"><input
								type="text" ng:model="item.unit_qty" maxlength="10"
								select-on-click valid-number id="unit_qty"
								name="unit_qty" row-add="addItem($index,1)"
								row-delete="removeItem($index)" row-save="saveData()"
								class="input-mini form-control algn_rgt" ng-disabled="true"
								ng-style="validation_delivered_qty">
								<span class="input-group-addon" id="form_div_stockin_type_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.stockintype"></spring:message>"></i></span>
								</td>
							<td style="width: 50px;"><a href
								ng:click="removeItem($index)" class="btn btn-small"><i
									class="fa fa-minus "></i> </a></td>
						</tr>
						<tr>
							<td colspan="7"></td>
							<td><a href ng:click="addItem()" class="btn btn-small"><i
									class="fa fa-plus"></i> </a></td>
						</tr>
					</table>
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
			</div>
	</form>
</div>