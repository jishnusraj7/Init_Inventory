
<div class="" id="stocktransfer_form_div" ng-show="show_form">
<div class="{{ClassName}}" id="status_div_id">
		<p>{{status}}</p>
	</div>
	<form class="form-horizontal frm_div_stock_in" id="stocktransfer_form">
		<div class="">
		<div class="pull-right" id="div_finlize_print">
				<input type="hidden" id="finalized_by" name="finalized_by" value="0">
				<input type="hidden" id="finalized_date" name="finalized_date"
					value="{{cur_date_finalize | date:'yyyy/MM/dd'}}">
				<button type="button" class="btn btn-primary btn_status"
					id="btn_finalize" ng-click="filalize_stockin($event)">FINALIZE</button>

			</div>
			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id"> <input type="hidden"
				id="stockreg_id" name="stockreg_id" value=""
				ng-model="formData.stockreg_id">
			<div class="form-group " id="form_div_stock_transfer_no">

				<label for="stock_transfer_no" style="font-size: inherit !important;"
					class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="stocktransfer.stock_transfer_no">
					</spring:message><i class="text-danger"></i></label>

				<div class="col-sm-2">
					<div class="input-group ">
						<input type="text" class="form-control required code-font-size"
							style="width: 231px;" ng-change="isCodeExistis(formData.stock_transfer_no)"
							name="stock_transfer_no" id="stock_transfer_no"
							ng-model="formData.stock_transfer_no" maxlength="10"
							ng-disabled="disable_code" capitalize placeholder=""> <span
							class="input-group-addon" min="0" max="99" number-mask=""
							id="form_div_stock_transfer_no_error" style="display: none;"><i
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

			<div class="form-group" id="form_div_stock_transfer_date">
				<label for="name" class="col-sm-2 control-label "> <spring:message
						code="stocktransfer.transferdate"></spring:message> <i
					class="text-danger">*</i>
				</label>

				<div class="col-sm-2">
					<div class="input-group">
						<div class="right-inner-addon">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 80%; z-index: 4;"></i> <input type="text"
								class="form-control  required" daterange-picker
								name="form_div_stock_transfer_date" id="stock_transfer_date"
								ng-model="formData.stock_transfer_date1"
								ng-disabled="disable_all" placeholder="" style="width: 231px;">
						</div>

						<span class="input-group-addon" id="form_div_stock_transfer_date_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stocktransfer.error.date"></spring:message>"></i></span>
					</div>
				</div>
			</div>
			
		<div class="form-group" id="form_div_stock_transfer_date" style="text-align: center; padding-left: 81px;">
			   <div ng-disabled="disable_all"  class="input-group radio_report" style=" width: 44%;float: left;margin: 0 auto;">
			        <label class="radio-inline"> <input type="radio" name="stkoptradio" id="stkoptradioExternal">External  </label>
				<label class="radio-inline"> <input type="radio" name="stkoptradio" id="stkoptradioInternal">Internal  </label>
			   </div>
			</div> 



			<div class="form-group " id="form_div_dest_company">
				<label for="companyId" class="col-sm-2 control-label"><spring:message
						code="stockout.company"></spring:message> <i class="text-danger">*</i></label>
				<div class="col-sm-3">
					<div class="input-group">
						<select class="form-control" name="companyId" id="companyId" style="width: 231px;"
							ng-disabled="disable_all" required>
							<option value=" " id="select">select</option>
							<c:forEach var="hqCompany" items="${hqCompanyData}">
								<c:if test="${hqCompany.id !=0}">
									<option value="${hqCompany.id}" id="${hqCompany.code}" data-dr="${hqCompany.code}"><c:out
											value="${hqCompany.name}" /></option>
								</c:if>
							</c:forEach>
						</select><span class="input-group-addon" id="form_div_dest_company_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockin.error.company"></spring:message>"></i></span>

					</div>
				</div>
			</div>

			<div class="form-group" id="form_div_source_department_code">
				<label for="source_department_code" class="col-sm-2 control-label"><spring:message
						code="stockout.source"></spring:message> <i class="text-danger">*</i> </label>
				<%-- <div class="col-sm-3">
						<div class="input-group">
							<input type="hidden" name="source_department_id"
							id="source_department_id"
							ng-model="formData.source_department_id"> <input
							type="text" class="form-control " name="source_department_code"
							id="source_department_code"
							ng-model="formData.source_department_code"
							ng-disabled="disable_all" placeholder="Code"> <span
								class="input-group-addon" id="form_div_source_department_code_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stocktransfer.error.sourcedep"></spring:message>"></i></span>
						</div>
					</div> --%>
					
					<div class="col-sm-3">
						<div class="input-group">
							<select class="form-control" id="source_department_id"  class="required"
								name="source_department_id" ng-disabled="disable_all"  required
								ng-options="v.id as v.name for v in transfer_departments"
								ng-model="formData.source_department_id" ng-change="onChangeSourceDepartment()" style="width: 231px;"></select>							
							<span class="input-group-addon"  id="form_div_source_department_code_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip"
								data-toggle="tooltip" data-placement="bottom" title=""
								data-original-title="<spring:message code="stocktransfer.error.sourcedep"></spring:message>"></i></span>
						</div>
					</div>
					<!-- <div class="col-sm-5">
						<div class="input-group">
							<input
							type="text" id="source_department_name"
							name="source_department_name" class="form-control widthset"
							ng-model="formData.source_department_name" disabled="true">
						</div>
					</div> -->
			</div>

              <!-- commenting code for dest department autocomplete -->
			<%-- <div class="form-group" id="form_div_dest_department_code">
				<label for="dest_department_code" class="col-sm-2 control-label"><spring:message
						code="stockout.destination"></spring:message> <i class="text-danger">*</i> </label>
				<div class="col-sm-3">
						<div class="input-group">
							<input type="hidden" name="dest_department_id"
							id="dest_department_id" ng-model="formData.dest_department_id">
						<input type="text" class="form-control " name="department_code"
							id="dest_department_code"
							ng-model="formData.dest_department_code"
							ng-disabled="disable_all" placeholder="Code">  <span
								class="input-group-addon" id="form_div_dest_department_code_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stocktransfer.error.destdep"></spring:message>"></i></span>
						</div>
					</div>
					<div class="col-sm-5">
						<div class="input-group">
							<input
							type="text"  id="dest_department_name"
							name="dest_department_name" class="form-control"
							ng-model="formData.dest_department_name" disabled="true">
						</div>
					</div>
				
				

			</div> --%>
			
			<div class="form-group" id="form_div_dest_department_code">
				<label for="dest_department_code" class="col-sm-2 control-label"><spring:message
						code="stockout.destination"></spring:message> <i class="text-danger">*</i> </label>
			<div class="col-sm-3">
						<div class="input-group">
							<select class="form-control" id="dest_department_id"  class="required"
								name="dest_department_id" ng-disabled="disable_all"  required
								ng-options="v.id as v.name for v in transfer_departments"
								ng-model="formData.dest_department_id" style="width: 231px;"></select>							
							<span class="input-group-addon"  id="form_div_dest_department_code_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip"
								data-toggle="tooltip" data-placement="bottom" title=""
								data-original-title="<spring:message code="stocktransfer.error.sourcedep"></spring:message>"></i></span>
						</div>
					</div>
			
			
				</div>


			<div class="form-group " id="form_div_items_tbl" >
			<div class="cmmn_maain_tbl">
			<circle-spinner ng-show="prograssing"></circle-spinner>
				<table class="table table-bordered" id="items_table" ng-hide="prograssing">
					<thead>
						<tr class="active">
							<th>ID</th>
							<th>#</th>
							<th>Item Code & Name</th>
							<th>Unit</th>
							<th>Transfer Unit</th>
								<th ng-show="false">Compound Unit</th>
							<th style="width: 135px;">Current Stock</th>
								<th>Transfer Qty</th>
								<th>Unit Qty</th>
							<th>Unit Rate</th>
							<!-- <th>Tax %</th> -->
							<!-- <th> Tax Amt</th> -->
							<th>Total Amt(${systemSettings.currencySymbol})</th>
							<th></th>
						</tr>
						</thead>
						<tbody>
							<tr ng:repeat="item in invoice.items">
								<td><input type="text" ng-model="item.id"><input
									type="text" ng-model="item.stockRegDtl_id"></td>
								<td>{{$index+1}}</td>

								<td ng-click="tableClicked($index)" class="item_code_name"
									style="width: 25%;"><div style="width: 100%">
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
										</div></td>
								<td style="width: 10%;"><input type="text"
									ng-disabled="true" id="uomcode" ng-model="item.uomcode"
									name="uomcode" class="input-mini form-control"></td>

								<!-- @gana 22012020 -->
								<td style="width: 10%;"><select
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
									<input type="hidden" ng-disabled="false" id="compound_unit"
									ng-model="item.compound_unit" name="compoundUnit"
									class="input-mini form-control"></td>


								<td class="item-table-col-width" style="width: 10%;"><input
									type="text" ng:model="item.currentStock"
									class="input-mini form-control algn_rgt" id="currentStock"
									name="currentStock" ng-disabled="true"
									ng-style="validation_currentStock" valid-number></td>
								<td class="item-table-col-width"><input type="text"
									ng:model="item.request_qty" ng:required
									maxlength="${10+settings['decimalPlace']+1}" select-on-click
									valid-number class="input-mini form-control algn_rgt"
									row-delete="removeItem($index)" row-save="saveData()"
									id="request_qty" name="request_qty" row-add="addItem($index)"
									ng-disabled="disable_request_qty" ng-style="validation_po_qty"
									ng-change="selectPuomCode(formData.base_uom_code,item.request_qty,$index)"></td>
								<td class="item-table-col-width"><input type="text"
									ng:model="item.issued_qty"
									maxlength="${10+settings['decimalPlace']+1}" select-on-click
									valid-number id="issued_qty" name="issued_qty" id="issued_qty"
									row-add="addItem($index)" row-delete="removeItem($index)"
									row-save="saveData()" class="input-mini form-control algn_rgt"
									ng-disabled="true"></td>
								<td class="item-table-col-width"><input type="text"
									ng:model="item.cost_price" maxlength="13" select-on-click
									valid-number id="cost_price" name="cost_price"
									row-add="addItem($index)" row-delete="removeItem($index)"
									row-save="saveData()" class="input-mini form-control algn_rgt"
									ng-disabled="true" ng-style="validation_unit_price"></td>
								<!-- 							<td class="item-table-col-width"><input type="hidden"
								class="input-mini form-control amount algn_rgt" id="amount"
								name="amount" ng-disabled="true"
								value="{{amount(item)}}"><input type="text"
								ng:model="item.tax_pc" maxlength="13" select-on-click
								valid-number id="tax_pc" name="tax_pc"
								class="input-mini form-control algn_rgt"
								ng-disabled="disable_all" ng-style="validation_tax_per" row-add="addItem($index)" row-delete="removeItem($index)"></td>	 -->							
						<!-- 	<td class="item-table-col-width">						
							<input type="text"
								class="input-mini form-control amount algn_rgt" id="tax_amount"
								name="tax_amount" ng-disabled="true"
								value="{{taxAmt(item)}}"></td> -->
								
								
							<td style="width:113px;" ><input type="text"
								class="input-mini form-control amount algn_rgt" id="tax_amount"
								name="tax_amount" ng-disabled="true"
								value="{{taxAmtTtl(item)}}"></td>
							<td><a href ng:click="removeItem($index)" ng-show="disable_add_remove"
								class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
						</tr>
						<tr>
							<td></td>
							<td  colspan="7" class="font-style"><label class="col-sm-4  pull-right">Net Total:</label></td>
							<td colspan="1" class="font-style">${systemSettings.currencySymbol} {{total() }}</td>
							<td><a href ng:click="addItem()" class="btn btn-small" ng-show="disable_add_remove"><i
									class="fa fa-plus"></i> </a></td>
						</tr>
					<%-- 	<tr>
							<td></td>
							<td  colspan="8" class="font-style"><label class="col-sm-4  pull-right">Tax Total:</label></td>
							<td colspan="2" class="font-style">${systemSettings.currencySymbol} {{tax_total() }}</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td  colspan="8" class="font-style"><label class="col-sm-4  pull-right">Grand Total:</label></td>
							<td colspan="2" class="font-style">${systemSettings.currencySymbol} {{gendTotal() }}</td>
							<td></td>
						</tr> --%>
					</tbody>
					<tfoot></tfoot>
				</table>
				</div>
			</div>
		</div>
	</form>
</div>

