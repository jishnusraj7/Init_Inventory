
<div class="" id="stockdisposal_form_div" ng-show="show_form">
	<form class="form-horizontal frm_div_stock_in" id="stockdisposal_form">
	<div class="">
	<input type="hidden" id="id" name="id" value="" ng-model="formData.id">
	<input type="hidden" id="stockreg_id" name="stockreg_id" value="" ng-model="formData.stockreg_id">
			<div class="form-group " id="form_div_ref_no">

				<label for="refNo" class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="stockdisposal.refNo">
					</spring:message><i class="text-danger"></i></label>

				<div class="col-sm-3">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							name="REf_No" id="REf_No" value="{{REFNO}}" ng-disabled="true"
							placeholder="">
					</div>
				</div>
			</div>


			<%-- <div class="form-group" id="form_div_department_id">
				<label for="department_id" class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="stockdisposal.department_id"></spring:message> <i
					class="text-danger">*</i></label>

				<div class="col-sm-3">
					<div class="input-group">
						<select class=" form-control selectpicker " id="department_id"
							name="department_id"
							ng-options="v.id as v.name for v in departmentList"
							ng-model="formData.department_id" ng-disabled="disable_all">
						</select><span class="input-group-addon" id="form_div_department_id_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockdisposal.error.department_id"></spring:message>"></i></span>
					</div>
				</div>

			</div>
 --%>
 
   <div class="form-group " id="form_div_department_code">
				<label for="department_id" class="col-sm-2 control-label ">
				<spring:message code="stockdisposal.department_id"></spring:message> <i class="text-danger">*</i></label>
				<div class="col-sm-3">
					<div class="input-group">
						<input type="hidden" name="department_id"
							id="department_id" ng-model="formData.department_id">
						<input type="text" class="form-control"
							name="department_code" id="department_code"
							ng-model="formData.department_code" ng-disabled="disable_all" row-save="saveData(event)"
							placeholder="Code"><span class="input-group-addon"
							id="form_div_department_code_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockdisposal.error.dep"></spring:message>"></i></span>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control "
							name="department_name" id="department_name"
							ng-model="formData.department_name" ng-disabled="true" placeholder="">
					</div>
				</div>
			</div>
 
			<div class="form-group " id="form_div_dispose_date">

				<label for="dispose_date"
					class="col-sm-2 control-label "><spring:message
						code="stockdisposal.disposlDate"></spring:message> <i
					class="text-danger"></i></label>

				<div class="col-sm-3">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							name="disposal_date" id="disposal_date" value="{{CurrentDate | cpmnyDateformat }}"
							ng-disabled="true" placeholder="">
					</div>
				</div>
			</div>

			


			<div class="form-group " id="form_div_dispose_by">

				<label for="dispose_by" class="col-sm-2 control-label "><spring:message
						code="stockdisposal.dispose_by">
					</spring:message><i class="text-danger"></i></label>

				<div class="col-sm-3">
					<div class="input-group">
						<input type="text" class="form-control code-font-size" maxlength="50"
							name="dispose_by" id="dispose_by" value="" ng-disabled="disable_all" row-save="saveData(event)"
							placeholder=""  ng-model="formData.disposal_by">
					</div>
				</div>
			</div>
			
			
			<div class="form-group " id="form_div_items_tbl">
				<!-- <div class="alert alert-danger alert-dismissable fade"
					id="table_validation_alert">
					<strong>Note !</strong>&nbsp;&nbsp;{{table_validation_alert_msg}}
				</div> -->
				<div class="cmmn_maain_tbl">
				<circle-spinner ng-show="prograssing"></circle-spinner>
				<table class="table table-bordered" id="items_table" ng-hide="prograssing">
					<tr class="active">
					    <th>ID</th>
						<th>#</th>
						<th>Item Code & Name</th>
						<th>Unit</th>
						<th>Current Stock</th>
						<th>Damaged Qty</th>
						<th>Rate</th>
						<th>Reason</th>
						<th>Amount(${systemSettings.currencySymbol})</th>
						<th></th>
					</tr>
					<tr ng:repeat="item in invoice.items">
					<td><input type="text" ng-model="item.stock_disposal_id"></td>
				
						<td>{{$index + 1}}</td>
						<td ng-click="tableClicked($index)"><div  style="width: 100%"><div style="float:left;width:30%; padding-right:4px;"><input
							type="hidden" ng-model="item.stockRegDtl_id"><input type="text"
							ng:model="item.stock_item_code" id="stock_item_code"
							name="stock_item_code" ng:required row-save="saveData(event)"
							class="input-mini form-control" autocompete-text 
							ng-disabled="disable_all" ng-style="validation_Sstock_item_code" maxlength="10">
							</div><div style="display: inline-block; width:65%;"><input type="hidden" ng:model="item.stock_item_id"
							id="stock_item_id" name="stock_item_id"> <input
							type="text"  class="input-mini form-control"  ng:model="item.stock_item_name"
							id="stock_item_name" name="stock_item_name" ng-disabled="true"><input
							type="hidden" ng:model="item.stock_item_batch_id"
							id="stock_item_batch_id" name="stock_item_batch_id"><input
							type="hidden" ng:model="item.stock_item_batch_stock"
							id="stock_item_batch_id" name="stock_item_batch_stock">	</div></td> 
						<td style="width:70px"><input type="text" ng-disabled="true" 
								id="uomcode" ng-model="item.uomcode" name="uomcode" class="input-mini form-control"></td>	
						<td class="item-table-col-width" ><input type="text"
							ng:model="item.currentStock" class="input-mini form-control algn_rgt"
							id="currentStock" name="currentStock" ng-disabled="true"
							ng-style="validation_currentStock"  valid-number>
					
					</td>
							<td class="item-table-col-width"><input type="text" ng:model="item.damaged_qty" maxlength="10" select-on-click  row-delete="removeItem($index)" 
							ng:required valid-number class="input-mini form-control algn_rgt"
							id="damaged_qty" name="damaged_qty" ng-disabled="disable_all" row-save="saveData(event)" 
							ng-style="validation_damaged_qty" max-length="10"></td>
						
						<td class="item-table-col-width"><input type="text" ng:model="item.rate" maxlength="10"  select-on-click
							valid-number id="rate" name="rate"
							class="input-mini form-control algn_rgt"
							ng-disabled="true" ng-style="validation_rate"></td>
						<td  style="width:150px">
						
						<!-- <textarea rows="1" cols="15" ng-model="item.stock_disposal_reason"id="reason" name="reason" row-add="addItem($index)" row-delete="removeItem($index)" row-save="saveData(event)" 
							class="input-mini form-control" ng-disabled="disable_all" ng-style="validation_reason" maxlength="250"></textarea> -->
							
							<select class="form-control selectpicker"
								id="reason_type" name="reason_type" ng-disabled="disable_all"
								ng-model="item.reason_type" ng-change="changeReason($index)"  row-add="addItem($index)">
									<option value="" >select</option>
									<c:forEach items="${enumValues}" var="enumValue">
										<option value="${enumValue.getDisposalReasonTypeId()}">${enumValue.getDisposalReasonTypeName() }</option>
									</c:forEach>
							</select>
							</td>
						<td class="item-table-col-width"><input type="text"
							class="input-mini form-control amount algn_rgt" id="amount"
							name="amount" ng-disabled="true"
							value="{{amount(item.damaged_qty * item.rate)}}"></td>
						<td><a href ng:click="removeItem($index)"
							class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
					</tr>
					
					<tr >
								<td></td>
								<td colspan="7" class="font-style algn_rgt"><label class="">Grand
										Total:</label></td>
								<td colspan="1" class="font-style algn_rgt"><label>
										${systemSettings.currencySymbol} {{total() }}</label></td>
								<td><a href ng:click="addItem()" class="btn btn-small"><i
										class="fa fa-plus"></i> </a></td>
							</tr>
				<!-- 	<tr>
					
						<td></td>
						<td></td>
						<td></td>
						<td ></td>
						<td></td>
						<td><label class="col-sm-2 control-label">Total:</label></td>
						<td><input type="text"
							class="input-mini form-control algn_rgt"
							value="{{total()}}" ng-disabled="true"></td>
						<td><a href ng:click="addItem()" class="btn btn-small"><i
								class="fa fa-plus"></i> </a></td>
					</tr> -->
				</table>
				</div>
			</div>
			



		</div>
	</form>		
</div>	
	
