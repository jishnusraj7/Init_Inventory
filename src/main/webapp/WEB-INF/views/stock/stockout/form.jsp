<div class="" id="stockout_form_div" ng-show="show_form">
	<div class="{{ClassName}}" id="status_div_id">
		<p>{{status}}</p>
	</div>
	<form class="form-horizontal frm_div_stock_in" id="stockout_form">
		<div class="">


			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id"> <input type="hidden"
				id="stockreg_id" name="stockreg_id" value=""
				ng-model="formData.stockreg_id">
			<div class="pull-right" id="div_finlize_print">

				<input type="hidden" id="change_by" name="change_by" value="0">
				<input type="hidden" id="change_date" name="change_date"
					value="{{cur_date_finalize | date:'yyyy/MM/dd'}}">

				<button type="button"
					class="btn btn-info btn_status pull-right btn_status"
					id="btn_status" ng-click="fun_status($event)">{{statusBtnText}}</button>
			</div>


			<div class="form-group " id="form_div_ref_code">

				<label for="grn_code" style="display:none;" id="grnCode_label"
					class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="stockout.ref.code"></spring:message> <i class="text-danger"></i></label>

				<div class="col-sm-2">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							ng-change="isCodeExistis(formData.stockTransfeNo)"
							name="stock_transfer_no" id="ref_code" style="display:none;"
							placeholder="" ng-model="formData.stockTransfeNo" ng-disabled="disable_grn"> <span
							class="input-group-addon" min="0" max="99" number-mask=""
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title=""></i></span>
					</div>
				</div>
				<div class="col-sm-4">
					<span ng-bind="existing_code" class="existing_code_lbl"
						ng-hide="hide_code_existing_er"></span>
				</div>
			</div>




			<div class="form-group " id="form_div_stock_request_date">

				<label for="stock_transfer_date"
					class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="stockout.req.date"></spring:message> <i class="text-danger">*</i></label>

				<div class="col-sm-2">
					<div class="input-group">
						<div class="right-inner-addon">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 69%; z-index: 4;"></i> <input type="text"
								class="form-control code-font-size required" daterange-pickerbft
								name="stock_request_date" id="stock_request_date"
								ng-model="formData.stock_request_date" ng-disabled="disable_all_date"
								placeholder="">
						</div>
						<span class="input-group-addon"
							id="form_div_stock_request_date_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockout.error.req_date"></spring:message>"></i></span>
					</div>
				</div>
			</div>

			<div class="form-group " id="form_div_deliv_date"
				ng-show="show_stockrequest_form">

				<label for="stock_transfer_date" class="col-sm-2 control-label"><spring:message
						code="stockout.dlvry.date"></spring:message> <i
					class="text-danger"></i></label>

				<div class="col-sm-2">
					<div class="input-group">
						<div class="right-inner-addon">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control" daterange-pickerbft name="stock_transfer_date"
								id="stock_transfer_date" ng-model="formData.stock_transfer_date"
								ng-disabled="disable_all_date" placeholder="" ng-change="getChangeStock()">
						</div>
					</div>
				</div>
				<div class="col-sm-4">
					<span class="existing_code_lbl" style="padding: 0px;"
						ng-hide="(formData.stock_transfer_date != formData.stock_request_date) ? formData.stock_transfer_date > formData.stock_request_date : true">must
						be greater than req date</span>
				</div>
			</div>

			<div class="form-group " id="form_div_dest_company"
				ng-hide="isStoreToStore">
				<label for="companyId" class="col-sm-2 control-label"><spring:message
						code="stockout.company"></spring:message> <i class="text-danger">*</i></label>
				<div class="col-sm-3">
					<div class="input-group">
						<select class="form-control" name="companyId" id="companyId"
							ng-disabled="disable_all">
							<option value="0" id="select">select</option>
							<c:forEach var="hqCompany" items="${hqCompanyData}">
								<c:if test="${COMPANY_SESSION_INFO.id != hqCompany.id}">
									<option value="${hqCompany.id}" id="${hqCompany.code}"><c:out
											value="${hqCompany.name}" /></option>
								</c:if>
							</c:forEach>
						</select><span class="input-group-addon" id="form_div_source_company_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockin.error.company"></spring:message>"></i></span>

					</div>
				</div>
			</div>

			<div class="form-group " id="form_div_source_code"
				ng-show="show_stockrequest_form">
				<label for="department_code" class="col-sm-2 control-label"><spring:message
						code="stockout.source"></spring:message> <i class="text-danger">*</i>
				</label>
				<div class="col-sm-2">
					<div class="input-group">
						<input type="hidden" name="source_department_id"
							id="source_department_id"
							ng-model="formData.source_department_id"> <input
							type="text" class="form-control " name="source_code"
							id="source_code" ng-model="formData.source_code"
							ng-disabled="disable_all" placeholder="Code" ><span
							class="input-group-addon" id="form_div_source_code_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockin.error.dep"></spring:message>"></i></span>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control "
							name="from_department_name" id="from_department_name"
							ng-model="formData.source_name" ng-disabled="true" placeholder="">
					</div>
				</div>
			</div>

			<div class="form-group " id="form_div_desti_code">
				<label for="department_code" class="col-sm-2 control-label"
					ng-show="show_stockrequest_form"><spring:message
						code="stockout.destination"></spring:message> <i
					class="text-danger">*</i></label> <label for="department_code"
					class="col-sm-2 control-label" ng-hide="show_stockrequest_form">Requested
					From <i class="text-danger">*</i>
				</label>
				<div class="col-sm-2">
					<div class="input-group">
						<input type="hidden" name="dest_department_id"
							id="dest_department_id" ng-model="formData.dest_department_id">
						<input type="text" class="form-control" name="dest_code"
							id="dest_code" ng-model="formData.dest_code"
							ng-disabled="disable_all" placeholder="Code">
							<span
								class="input-group-addon" id="form_div_supplier_code_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockin.error.supplier"></spring:message>"></i></span>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control" name="to_department_name"
							id="to_department_name" ng-model="formData.desti_name"
							ng-disabled="true" placeholder="">
					</div>
				</div>
			</div>


			<c:if test="${(combineMode==0)?true:false }">

				<div class="form-group " id="form_div_items_tbl">

					<div class="cmmn_maain_tbl">
						<circle-spinner ng-show="prograssing"></circle-spinner>
						<table class="table table-bordered" id="items_table"
							ng-hide="prograssing">
							<tr class="active">
								<th>ID</th>
								<th>#</th>
								<th style="width: 325px;">Item Code & Name</th>
								<th>Unit</th>
								<th style="width: 135px;" ng-show="show_stockrequest_form">
									Current Stock</th>
								<th style="width: 135px;" ng-show="show_stockrequest_form">
									Pending Qty</th>
									
								<th style="width: 135px;">Request Qty</th>
								<th ng-show="show_stockrequest_form" style="width: 135px;">Deliver
									Qty</th>

								<th ng-show="show_stockrequest_form" style="width: 135px;">Unit
									Rate</th>
								<th ng-show="show_stockrequest_form" style="width: 200px;">Amount
									(${systemSettings.currencySymbol})</th>
								<th></th>
							</tr>
							<tr ng:repeat="item in invoice.items">
								<td><input type="text" ng-model="item.id"><input
									type="text" ng-model="item.stockRegDtl_id"></td>
								<td>{{$index + 1}}</td>
								<td ng-click="tableClicked($index)"><div
										style="width: 100%">
										<div style="float: left; width: 25%; padding-right: 4px;">
											<input type="text" ng:model="item.stock_item_code"
												id="stock_item_code" name="stock_item_code" ng:required
												class="input-mini form-control" autocompete-text
												ng-disabled="disable_all"
												ng-style="validation_Sstock_item_code" maxlength="10">
										</div>
										<div style="display: inline-block; width: 73%;">
											<input type="hidden" ng:model="item.stock_item_id"
												id="stock_item_id" name="stock_item_id"> <input
												type="text" class="input-mini form-control"
												ng:model="item.stock_item_name" id="stock_item_name"
												name="stock_item_name" ng-disabled="true"><input
												type="hidden" ng:model="item.stock_item_batch_id"
												id="stock_item_batch_id" name="stock_item_batch_id"><input
												type="hidden" ng:model="item.stock_item_batch_stock"
												id="stock_item_batch_id" name="stock_item_batch_stock">
										</div></td>
								<td style="width: 300px"><input type="text"
									ng-disabled="true" id="uomcode" ng-model="item.uomcode"
									class="input-mini form-control" name="uomcode"></td>

								<td class="item-table-col-width"
									ng-show="show_stockrequest_form"><input type="text"
									ng:model="item.currentStock"
									class="input-mini form-control algn_rgt" id="currentStock"
									name="currentStock" ng-disabled="true"
									ng-style="validation_currentStock" valid-number></td>
									
											<td class="item-table-col-width"
									ng-show="show_stockrequest_form"><input type="text"
									ng:model="item.pending_qty"
									class="input-mini form-control algn_rgt" id="currentStock1"
									name="currentStock1" ng-disabled="true"
									ng-style="validation_currentStock" valid-number></td>
									
									
								<td class="item-table-col-width"><input type="text"
									ng:model="item.request_qty" maxlength="10" select-on-click
									ng:required valid-number
									class="input-mini form-control algn_rgt"
									row-add="addItem($index,0)" row-delete="removeItem($index)"
									row-save="saveData()" id="request_qty" name="request_qty"
									ng-disabled="disable_all" ng-style="validation_request_qty_qty"></td>
								<td class="item-table-col-width"
									ng-show="show_stockrequest_form"><input type="text"
									ng:model="item.delivered_qty" maxlength="10" select-on-click
									valid-number id="delivered_qty" name="delivered_qty"
									row-add="addItem($index,1)" row-delete="removeItem($index)"
									row-save="saveData()" class="input-mini form-control algn_rgt"
									ng-disabled="disable_all" ng-style="validation_delivered_qty"></td>


								<td class="item-table-col-width"
									ng-show="show_stockrequest_form"><input type="text"
									ng:model="item.unit_price" maxlength="10" select-on-click
									valid-number id="unit_price" name="unit_price"
									row-add="addItem()" row-delete="removeItem($index)"
									row-save="saveData()" class="input-mini form-control algn_rgt"
									ng-disabled="true" ng-style="validation_unit_price"
									ng-show="show_stockrequest_form"></td>
								<td ng-show="show_stockrequest_form"><input type="text"
									class="input-mini form-control amount algn_rgt" id="amount"
									name="amount" ng-disabled="true"
									value="{{amount(item.delivered_qty * item.unit_price )}}"></td>
								<td><a href ng:click="removeItem($index)"
									class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
					        	<td ng-show="show_stockrequest_form"></td> 
							
								<td ng-show="show_stockrequest_form"></td>
								<td ng-show="show_stockrequest_form"></td>
								<td ng-show="show_stockrequest_form"><label
									class="col-sm-2 control-label">Total:</label></td>
								<td ng-show="show_stockrequest_form"><input type="text"
									class="input-mini form-control algn_rgt"
									value="${systemSettings.currencySymbol} {{total() }}"
									ng-disabled="true"></td>
								<td><a href ng:click="addItem()" class="btn btn-small"><i
										class="fa fa-plus"></i> </a></td>
							</tr>
						</table>
					</div>
				</div>

			</c:if>


			<c:if test="${(combineMode==1)?true:false }">

				<div class="form-group " id="form_div_items_tbl">

					<div class="cmmn_maain_tbl">
						<circle-spinner ng-show="prograssing"></circle-spinner>
						<table class="table table-bordered" id="items_table"
							ng-hide="prograssing">
							<tr class="active">
								<th>ID</th>
								<th>#</th>
								<th style="width: 68%;">Item Code & Name</th>
								<th style="width: 100px;">Unit</th>
								<th>Out Unit</th>
								<th ng-show="false">CUnit</th>
								<th style="width: 125px;" ng-show="show_stockrequest_form">
									Current Stock</th>
								<th style="width: 125px;" ng-show="show_stockrequest_form">
									Pending Qty</th>
								<th style="width: 125px;">Request Qty</th>
								<th ng-show="show_stockrequest_form" style="width: 135px;">Deliver
									Qty</th>

								<th></th>
							</tr>
							<tr ng:repeat="item in invoice.items">
								<td><input type="text" ng-model="item.id"><input
									type="text" ng-model="item.stockRegDtl_id"></td>
								<td>{{$index + 1}}</td>
								<td ng-click="tableClicked($index)"><div
										style="width: 100%">
										<div style="float: left; width: 25%; padding-right: 4px;">
											<input type="text" ng:model="item.stock_item_code"
												id="stock_item_code" name="stock_item_code" ng:required
												class="input-mini form-control" autocompete-text
												ng-disabled="disable_all"
												ng-style="validation_Sstock_item_code" maxlength="10">
										</div>
										<div style="display: inline-block; width: 73%;">
											<input type="hidden" ng:model="item.stock_item_id"
												id="stock_item_id" name="stock_item_id"> <input
												type="text" class="input-mini form-control"
												ng:model="item.stock_item_name" id="stock_item_name"
												name="stock_item_name" ng-disabled="true"><input
												type="hidden" ng:model="item.stock_item_batch_id"
												id="stock_item_batch_id" name="stock_item_batch_id"><input
												type="hidden" ng:model="item.stock_item_batch_stock"
												id="stock_item_batch_id" name="stock_item_batch_stock">
										</div></td>
								<td style=""><input type="text" ng-disabled="true"
									id="uomcode2" ng-model="item.uomcode"
									class="input-mini form-control" name="uomcode"></td>

								<!-- @gana 20012020  for drop down uom -->
								<td style="width: 100px;"><select
									class="input-mini form-control" id="base_uom_code"
									name="base_uom_code" ng-focus="true"
									ng-model="item.base_uom_code" ng-disabled="disable_all"
									ng-options="v.base_uom_code as v.base_uom_code for v in baseUomCode[$index]"
									ng-change="selectPuomCode(item.base_uom_code,item.request_qty,$index)">
								</select> <span class="input-group-addon" id="form_div_payment_error"
									style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="<spring:message code="stockin.error.payment"></spring:message>"></i></span>
								<!-- <td style="width: 100px;"> --><input type="hidden"
									ng-disabled="false" id="compound_unit"
									ng-model="item.compound_unit" name="compoundUnit"
									class="input-mini form-control"></td>
								<!-- end -->

								<td class="item-table-col-width"
									ng-show="show_stockrequest_form"><input type="text"
									ng:model="item.currentStock"
									class="input-mini form-control algn_rgt" id="currentStock2"
									name="currentStock" ng-disabled="true"
									ng-style="validation_currentStock" valid-number></td>
									
								<td class="item-table-col-width"
									ng-show="show_stockrequest_form"><input type="text"
									ng:model="item.pending_qty"
									class="input-mini form-control algn_rgt" id="pendingQty"
									name=""pendingQty"" ng-disabled="true"
									ng-style="validation_currentStock" valid-number></td>
									
									
								<td class="item-table-col-width"><input type="text"
									ng:model="item.request_qty" maxlength="10" select-on-click
									ng:required valid-number
									class="input-mini form-control algn_rgt"
									row-add="addItem($index,1)" row-delete="removeItem($index)"
									row-save="saveData()" id="request_qty" name="request_qty"
									ng-disabled="disable_all" ng-style="validation_request_qty_qty"
									ng-change="selectPuomCode(formData.base_uom_code,item.request_qty,$index)"></td>
								<td class="item-table-col-width"
									ng-show="show_stockrequest_form"><input type="text"
									ng:model="item.delivered_qty" maxlength="10" select-on-click
									valid-number id="delivered_qty" name="delivered_qty"
									row-add="addItem($index,1)" row-delete="removeItem($index)"
									row-save="saveData()" class="input-mini form-control algn_rgt"
									ng-disabled="true" ng-style="validation_delivered_qty"></td>




								<td><a href ng:click="removeItem($index)"
									class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td ng-show="show_stockrequest_form"></td>
								<td ng-show="show_stockrequest_form"></td>
								<td></td>
								<td ng-show="show_stockrequest_form"></td>

								<td><a href ng:click="addItem()" class="btn btn-small"><i
										class="fa fa-plus"></i> </a></td>
							</tr>
						</table>
					</div>
				</div>



			</c:if>






			<div class="form-group " id="form_div_remarks">
				<label for="remarks" class="col-sm-1 control-label"><spring:message
						code="stockin.remarks"></spring:message> <i class="text-danger"></i></label>
				<div class="col-sm-11">
					<div class="input-group">
						<textarea rows="4" cols="20" class="form-control" name="remarks"
							id="remarks" ng-model="formData.remarks"
							ng-disabled="disable_all" placeholder=""></textarea>
					</div>
				</div>
			</div>

		</div>

	</form>
</div>

<!-- modal -->
<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Manage request</h4>
			</div>
			<div class="modal-body " id="changedivstatus">
				<textarea class="form-control " name="change_reason"
					ng-focus="isFocused" ng-focus-lost="loseFocus()" id="change_reason"
					ng-model="change_reason" ng-disabled="" placeholder="Reason">
					
					</textarea>
			</div>
			<div class="box-footer">
				<button type="button" class="btn btn-success" ng-click="accept()">Approve</button>

				<button type="button" class="btn btn-danger" ng-click="reject()">Reject</button>

				<button type="button" class="btn btn-default pull-right"
					data-dismiss="modal">Cancel</button>
			</div>
		</div>

	</div>
</div>

<!--end modal -->


