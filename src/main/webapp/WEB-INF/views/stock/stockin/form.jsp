<div class="" id="stockin_form_div" ng-show="show_form">
	<div class="{{ClassName}}" id="status_div_id">
		<p>{{status}}</p>
	</div>
	<form class="form-horizontal frm_div_stock_in" id="stockin_form">
		<div class="">
			
			<div class="pull-right" id="div_finlize_print">
				<input type="hidden" id="finalized_by" name="finalized_by" value="0">
				<input type="hidden" id="finalized_date" name="finalized_date"
					value="{{cur_date_finalize | date:'yyyy/MM/dd'}}">
				<button type="button" class="btn btn-primary btn_status"
					id="btn_finalize" ng-click="filalize_stockin($event)">FINALIZE</button>

			</div>
			<div class="pull-right" id="div_normal_print" style="margin-right: 5px;">
				<input type="hidden" id="finalized_by" name="finalized_by" value="0">
				<input type="hidden" id="finalized_date" name="finalized_date"
					value="{{cur_date_finalize | date:'yyyy/MM/dd'}}">
				<button type="button" class="btn btn-primary btn_status"
					id="btn_finalize" ng-click="normal_print_stockin($event)">PRINT PO</button>

			</div>
			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id"> <input type="hidden"
				id="stockreg_id" name="stockreg_id" value=""
				ng-model="formData.stockreg_id">

			<div class="col-sm-9">
				<div class="form-group " id="form_div_grn_code">

					<label for="grn_code" id="grnCode_label"
						class="col-sm-3 control-label code-lbl-font-size" style="display:none;"><spring:message
							code="stockin.grn.code"></spring:message> <i class="text-danger"></i></label>

					<!-- <div class="col-sm-3">
						<div class="input-group">
							<input type="text" class="form-control code-font-size" 
								name="GRN_No" id="GRN_No" value="{{GRNNO}}" ng-disabled="disable_all"
								placeholder="">
						</div>
					</div>-->

					<div class="col-sm-3">
						<div class="input-group ">
							<input type="text" class="form-control code-font-size" style="display:none;"
								ng-change="isCodeExistis(formData.grnNo)" name="grn_code"
								id="grn_code" ng-model="formData.grnNo" maxlength="10"
								ng-disabled="disable_grn" capitalize placeholder=""> <span
								class="input-group-addon" min="0" max="99" number-mask=""
								id="form_div_grn_code_error" style="display: none;"><i
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




				<div class="form-group " id="form_div_received_date">

					<label for="grn_date"
						class="col-sm-3 control-label code-lbl-font-size"><spring:message
							code="stockin.grn.date"></spring:message> <i class="text-danger"></i></label>

					<div class="col-sm-3">
						<div class="input-group">
							<div class="right-inner-addon">
								<i class="fa fa-calendar" id="calender_icon"
									style="left: 81%; z-index: 4;"></i> <input type="text"
									class="form-control code-font-size required"
									daterange-pickerbft name="received_date" id="received_date"
									ng-model="CurrentDate" ng-disabled="disable_all" placeholder="">
							</div>
							<span class="input-group-addon" id="form_div_received_date_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockout.error.grn_date"></spring:message>"></i></span>
						</div>
					</div>
				</div>

				<c:if test="${(combineMode==0)?true:false }">

					<div class="form-group " id="form_div_stockin_type">
						<label for="stockin_type" class="col-sm-3 control-label"><spring:message
								code="stockin.stockin_type"></spring:message> <i
							class="text-danger">*</i></label>
						<div class="col-sm-4">
							<div class="input-group">
								<select class="form-control" id="stockin_type"
									name="stockin_type" ng-focus="true"
									ng-model="formData.stockInType" ng-disabled="disable_all"
									ng-change="selectStockIntype()">
									<option value="" ng-disabled="true">Select</option>
									<c:forEach items="${stockintypes}" var="stockintypes">
										<option value="${stockintypes.getStockInTypeId()}">${stockintypes.getName() }</option>
									</c:forEach>
								</select><span class="input-group-addon" id="form_div_stockin_type_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.stockintype"></spring:message>"></i></span>

							</div>
						</div>
					</div>

				</c:if>

				<div class="form-group " id="form_div_source_company"
					ng-hide="isStoreToStore">
					<label for="companyId" class="col-sm-3 control-label"><spring:message
							code="stockin.company"></spring:message> <i class="text-danger">*</i></label>
					<div class="col-sm-4">
						<div class="input-group">
							<select class="form-control" name="companyId" id="companyId"
								ng-disabled="disable_all">
								<option value="0" id="select">select</option>
								<c:forEach var="hqCompany" items="${hqCompanyData}">
									<c:if test="${currentcompanydetails.id != hqCompany.id}">
										<option value="${hqCompany.id}" id="${hqCompany.code}"><c:out
												value="${hqCompany.name}" /></option>
									</c:if>
								</c:forEach>
							</select><span class="input-group-addon"
								id="form_div_source_company_error" style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockin.error.company"></spring:message>"></i></span>

						</div>
					</div>
				</div>


				<div class="form-group " id="form_div_department_code">
					<label for="department_code" class="col-sm-3 control-label"><spring:message
							code="stockin.department"></spring:message> <i
						class="text-danger">*</i></label>
					<div class="col-sm-3">
						<div class="input-group">
							<input type="hidden" name="department_id" id="department_id"
								ng-model="formData.departmentId"> <input type="text"
								class="form-control required" name="department_code"
								id="department_code" ng-model="department_code"
								ng-disabled="disable_all" placeholder="Code"><span
								class="input-group-addon" id="form_div_department_code_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockin.error.dep"></spring:message>"></i></span>
						</div>
					</div>
					<div class="col-sm-5">
						<div class="input-group">
							<input type="text" class="form-control required"
								name="department_name" id="department_name"
								ng-model="department_name" ng-disabled="true" placeholder="">
						</div>
					</div>
				</div>

				<div class="form-group " id="form_div_supplier_code">
					<!-- ng-show="show_suppler" -->
					<label for="supplier_code" class="col-sm-3 control-label"><spring:message
							code="stockin.supl_name"></spring:message> <i class="text-danger">*</i></label>
					<div class="col-sm-3">
						<div class="input-group">
							<input type="hidden" name="supplier_id" id="supplier_id"
								ng-model="formData.supplierId"> <input type="text"
								class="form-control required" name="supplier_code"
								id="supplier_code" ng-model="formData.supplierCode"
								ng-disabled="disable_all" placeholder="Code"><span
								class="input-group-addon" id="form_div_supplier_code_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockin.error.supplier"></spring:message>"></i></span>
						</div>
					</div>
					<div class="col-sm-5">
						<div class="input-group">
							<input type="text" class="form-control required"
								name="supplier_name" id="supplier_name"
								ng-model="formData.supplierName" ng-disabled="true"
								placeholder="">
						</div>
					</div>
				</div>


				<c:if test="${(combineMode==1)?true:false }">

					<div class="form-group" id="form_div_supplier_address">
						<label for="supplier_address" class="col-sm-3 control-label"><spring:message
								code="stockin.supplier_address"></spring:message> <i
							class="text-danger"></i> </label>
						<div class="col-sm-8">
							<div class="input-group">
								<textarea class="form-control required" name="address"
									style="overflow: hidden" maxlength="250" required id="address"
									ng-disabled="true" placeholder="">{{supplier_address}}
					
					</textarea>
								<span class="input-group-addon" id="form_div_address_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="supplier.error.address"></spring:message>"></i></span>


							</div>
						</div>
					</div>

				</c:if>




				<!-- for cash&credit gana-->
				<div class="form-group " id="form_div_supplier_payment">
					<label for="supplier_payment" class="col-sm-3 control-label"><spring:message
							code="stockin.supl_payment"></spring:message><i
						class="text-danger">*</i></label>
					<div class="col-sm-8">
						<div class="input-group">
							<select class="form-control" id="stockin_payment"
								name="stockin_payment" ng-focus="true"
								ng-model="formData.stockInPaymentType" ng-disabled="disable_all"
								ng-change="selectstockInPaymentType()">
								<option value="" ng-disabled="true">Select</option>
								<c:forEach items="${paymentMode}" var="paymentMode">
									<option value="${paymentMode.getPaymentModeTypeId()}">${paymentMode.getPaymentModeTypeName()}</option>

								</c:forEach>
							</select> <span class="input-group-addon" id="form_div_payment_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockin.error.payment"></spring:message>"></i></span>
						</div>
					</div>

				</div>



				<div class="form-group " id="form_div_supplier_ref">
					<label for="supplier_doc_no" class="col-sm-3 control-label"><spring:message
							code="stockin.supl_ref"></spring:message> <i class="text-danger">*</i></label>
					<div class="col-sm-8">
						<div class="input-group">
							<input type="text" class="form-control" name="supplier_doc_no"
								maxlength="20" id="supplier_doc_no"
								ng-model="formData.supplierRefNo" ng-disabled="disable_all"
								placeholder="">
						</div>
					</div>

				</div>




			</div>

			<div class="col-sm-3 po-hdr-table">
				<div class=" " id="form_div_po_table" ng-hide="ispoTableHide">
					<div class="table_main_div">
						<div class="table_div">
							<div class="thead">
								<div class="table_div_tr">
									<!-- <div class="table_div_th1">
											<div class="checkbox">
												<label class="checkbox-inline">
													<input id="selectall" type="checkbox" ng-model="item.selectAll"	ng-click="toggleAll(item.selectAll)">
													<span class="cr"><i class="cr-icon fa fa-check"></i></span> 
												</label>
											</div>
										</div> -->
									<div class="table_div_th">Purchase Orders</div>
								</div>
							</div>
							<div class="tbody_main_div">
								<div class="tbody">
									<div class="table_div_tr" ng:repeat="items in poHder.items">
										<div class="table_div_td1">
											<div class="checkbox">
												<label class="checkbox-inline"> <input
													type="checkbox" ng-model="selected[items.po_id]"
													ng-click="toggleOne(selected,items.po_no)"> <span
													class="cr"> <i class="cr-icon fa fa-check"></i>
												</span>
												</label>
											</div>
										</div>
										<div class="table_div_td">{{items.po_no}}</div>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>

			</div>




			<div class="form-group " id="form_div_items_tbl">
				<div class="cmmn_maain_tbl">
					<circle-spinner ng-show="prograssing"></circle-spinner>
					<table class="table table-bordered" id="items_table"
						ng-hide="prograssing">
						<thead>
							<tr class="active">
								<th>ID</th>
								<th>#</th>
								<th ng-show="false">PO No</th>
								<th>Item Code & Name</th>
								<th>Unit</th>
								<th>PUnit</th>
								<th ng-show="false">CUnit</th>
								<th>Stock</th>
								<th ng-show="false">Order Qty</th>
								<th>Rec Qty</th>
								<th>Unit Qty</th>
								<th>Rate</th>
								<th>Tax %</th>
								<th>Tax Amt</th>
								<th>Total Amt(${systemSettings.currencySymbol})</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<tr ng:repeat="item in invoice.items" ng-hide='item.isDeleted'>
								<td><input type="text" ng-model="item.id"><input
									type="text" ng-model="item.stockRegDtl_id"></td>
								<td>{{item.sino}}</td>
								<td class="item-table-col-pur-or-width" ng-show="false"><input
									type="hidden" ng:model="item.po_id"><input
									type="hidden" ng:model="item.poDtl_id"><input
									type="text" ng:model="item.po_no" ng-style="validation"
									id="po_no" name="po_no" class="input-small form-control"
									ng-disabled="true" ng-disabled="true"></td>
								<td ng-click="tableClicked($index)" class="item_code_name"><div
										style="width: 100%">
										<div style="float: left; width: 25%; padding-right: 4px;">
											<input type="text" ng:model="item.stock_item_code"
												id="stock_item_code" maxlength="10" name="stock_item_code"
												ng:required class="input-mini form-control" autocompete-text
												ng-disabled="disable_all"
												ng-style="validation_Sstock_item_code">
										</div>
										<div style="display: inline-block; width: 75%;">
											<input type="hidden" ng:model="item.stock_item_id"
												id="stock_item_id" name="stock_item_id"><input
												type="text" ng:model="item.stock_item_name"
												id="stock_item_name" name="stock_item_name"
												class="input-mini form-control" ng-disabled="true">
											<input type="hidden" ng:model="item.stock_item_batch_id"
												id="stock_item_batch_id" name="stock_item_batch_id">
										</div></td>


								<td style="width: 100px;"><input type="text"
									ng-disabled="true" id="uomcode" ng-model="item.uomcode"
									name="uomcode" class="input-mini form-control"
									ng-blur="selectPomCode()"></td>

								<!-- @gana 20012020  for drop down uom -->
								<td style="width: 100px;"><select class="input-mini form-control"
									id="base_uom_code" name="base_uom_code" ng-focus="true"
									ng-model="item.base_uom_code" ng-disabled="disable_all"
									ng-options="v.base_uom_code as v.base_uom_code for v in BindUomCode[$index]"
									ng-change="selectPuomCode(item.base_uom_code,item.received_qty,$index)">
								</select>
								<span class="input-group-addon" id="form_div_payment_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.payment"></spring:message>"></i></span>

								<!-- <td style="width: 100px;"> --><input type="hidden"
									ng-disabled="false" id="compound_unit" 
									ng-model="item.compound_unit" name="compoundUnit"
									class="input-mini form-control"></td>
								<!-- end -->


								<td style="width: 100px;"><input type="text"
									ng-disabled="true" id="current_stock"
									ng-model="item.current_stock" name="current_stock"
									class="input-mini form-control">


								<!-- <td class="item-table-col-width"> --><input type="hidden"
									ng:model="item.po_qty" ng:required
									maxlength="${10+settings['decimalPlace']+1}" select-on-click
									valid-number class="input-mini form-control algn_rgt"
									row-delete="removeItem($index)" row-save="saveData()"
									id="po_qty" name="po_qty" ng-disabled="disable_all"
									ng-style="validation_po_qty"></td>
								<td class="item-table-col-width"><input type="text"
									ng:model="item.received_qty"
									maxlength="${10+settings['decimalPlace']+1}" select-on-click
									valid-number id="received_qty" name="received_qty"
									row-delete="removeItem($index)" row-save="saveData()"
									class="input-mini form-control algn_rgt"
									ng-disabled="disable_all" ng-style="validation_received_qty" 
									ng-change="selectPuomCode(formData.base_uom_code,item.received_qty,$index)"></td>
								<td class="item-table-col-width"><input type="text"
									ng:model="item.pack_qty"
									maxlength="${10+settings['decimalPlace']+1}" select-on-click
									valid-number id="pack_qty" name="pack_qty"
									row-delete="removeItem($index)" row-save="saveData()"
									class="input-mini form-control algn_rgt"
									ng-disabled="true" ng-style="validation_pack_qty"></td>
								<td class="item-table-col-width"><input type="text"
									ng:model="item.unit_price"
									maxlength="${15+settings['decimalPlace']+1}" select-on-click
									valid-number id="unit_price" name="unit_price"
									row-save="saveData()" class="input-mini form-control algn_rgt"
									ng-disabled="disable_all" ng-style="validation_unit_price"></td>
								<td class="item-table-col-width"><input type="hidden"
									class="input-mini form-control amount algn_rgt" id="amount"
									name="amount" ng-disabled="true" value="{{amount(item)}}"><input
									type="text" ng:model="item.tax_pc"
									maxlength="${10+settings['decimalPlace']+1}" select-on-click
									valid-number id="tax_pc" name="tax_pc"
									class="input-mini form-control algn_rgt"
									ng-disabled="disable_all" ng-style="validation_tax_per"
									row-save="saveData()" row-add="addItem($index)"
									row-delete="removeItem($index)"><input type="hidden"
									ng:model="item.tax_id" valid-number id="tax_id" name="tax_id"
									class="input-mini form-control algn_rgt"
									ng-disabled="disable_all" ng-style="validation_unit_price"></td>

								<td class="item-table-col-width"><input type="text"
									class="input-mini form-control amount algn_rgt" id="tax_amount"
									name="tax_amount" ng-disabled="true" value="{{taxAmt(item)}}"></td>


								<td style="width: 113px;"><input type="text"
									class="input-mini form-control amount algn_rgt" id="tax_amount"
									name="tax_amount" ng-disabled="true"
									value="{{taxAmtTtl(item)}}"></td>
								<td><a href ng:click="removeItem($index)"
									class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
							</tr>
							<tr>
								<td></td>
								<td colspan="9" class="font-style"><label
									class="col-sm-4  pull-right">Net Total:</label></td>
								<td colspan="2" class="font-style">${systemSettings.currencySymbol}
									{{total(item) }}</td>
								<td><a href ng:click="addItem()" class="btn btn-small"><i
										class="fa fa-plus"></i> </a></td>
							</tr>
							<tr>
								<td></td>
								<td colspan="9" class="font-style"><label
									class="col-sm-4  pull-right">Tax Total:</label></td>
								<td colspan="2" class="font-style">${systemSettings.currencySymbol}
									{{tax_total(item) }}</td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td colspan="9" class="font-style"><label
									class="col-sm-4  pull-right">Grand Total:</label></td>
								<td colspan="2" class="font-style">${systemSettings.currencySymbol}
									{{gendTotal(item) }}</td>
								<td></td>
							</tr>
						</tbody>
						<tfoot></tfoot>
					</table>
				</div>
			</div>




			<%-- <c:if test="${(combineMode==1)?true:false }">

			<div class="form-group " id="form_div_items_tbl" >
			<div class="cmmn_maain_tbl">
			<circle-spinner ng-show="prograssing"></circle-spinner>
				<table class="table table-bordered" id="items_table" ng-hide="prograssing">
					<thead>
						<tr class="active">
							<th>ID</th>
							<th>#</th>
							<th ng-show="false">PO No</th>
							<th>Item Code & Name</th>
							<th>Unit</th>
							<th>Order Qty</th>
							<th>Rec Qty</th>
							<th>Rate</th>
							<th></th>
							
					 </tr>
					</thead>
					<tbody>
						<tr ng:repeat="item in invoice.items" ng-hide='item.isDeleted'>
							<td><input type="text" ng-model="item.id"><input
								type="text" ng-model="item.stockRegDtl_id"></td>
							<td>{{item.sino}}</td>
							<td class="item-table-col-pur-or-width" ng-show="false"><input type="hidden" ng:model="item.po_id"><input type="hidden" ng:model="item.poDtl_id"><input type="text"
								ng:model="item.po_no" ng-style="validation" id="po_no" name="po_no"
								class="input-small form-control" ng-disabled="true"
								ng-disabled="true"></td>
							<td ng-click="tableClicked($index)" class="item_code_name"><div  style="width: 100%"><div style="float:left;width:25%; padding-right:4px;"><input
								type="text" ng:model="item.stock_item_code" id="stock_item_code"
								maxlength="10" name="stock_item_code" ng:required
								class="input-mini form-control" autocompete-text
								ng-disabled="disable_all" ng-style="validation_Sstock_item_code">
								</div><div style="display: inline-block; width:75%;"><input type="hidden" ng:model="item.stock_item_id"
								id="stock_item_id" name="stock_item_id"><input
								type="text" ng:model="item.stock_item_name" id="stock_item_name"
								name="stock_item_name" class="input-mini form-control"
								ng-disabled="true"> <input type="hidden"
								ng:model="item.stock_item_batch_id" id="stock_item_batch_id"
								name="stock_item_batch_id"></div></td>
							<td style="width: 100px;"><input type="text" ng-disabled="true"  
								id="uomcode" ng-model="item.uomcode" name="uomcode" class="input-mini form-control"></td>
							<td class="item-table-col-width"><input type="text"
								ng:model="item.po_qty" ng:required maxlength="${10+settings['decimalPlace']+1}" select-on-click
								valid-number class="input-mini form-control algn_rgt"   row-delete="removeItem($index)" row-save="saveData()"
								id="po_qty" name="po_qty" ng-disabled="disable_all"
								ng-style="validation_po_qty"></td>
							<td class="item-table-col-width"><input type="text"
								ng:model="item.received_qty" maxlength="${10+settings['decimalPlace']+1}" select-on-click
								valid-number id="received_qty" name="received_qty"   row-delete="removeItem($index)" row-save="saveData()"
								class="input-mini form-control algn_rgt"
								ng-disabled="disable_all" ng-style="validation_received_qty"></td>
							<td class="item-table-col-width"><input type="text"
								ng:model="item.unit_price" maxlength="${15+settings['decimalPlace']+1}" select-on-click
								valid-number id="unit_price" name="unit_price" row-add="addItem($index)" row-delete="removeItem($index)"  row-save="saveData()"
								class="input-mini form-control algn_rgt"
								ng-disabled="disable_all" ng-style="validation_unit_price"></td>
							
								
															
								
							
							<td><a href ng:click="removeItem($index)"
								class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
						</tr>
						<tr>
							<td></td>
							<td  colspan="6" class="font-style"><label class="col-sm-4  pull-right"></label></td>
							<td><a href ng:click="addItem()" class="btn btn-small"><i
									class="fa fa-plus"></i> </a></td>
						</tr>
					</tbody>
					<tfoot></tfoot>
				</table>
				</div>
			</div>
			
		</c:if>	
		 --%>





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
