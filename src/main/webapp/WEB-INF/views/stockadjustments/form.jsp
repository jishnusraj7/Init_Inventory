
<div class="" id="stockdisposal_form_div" ng-show="show_form">
	<form class="form-horizontal frm_div_stock_in" id="stockdisposal_form">
	<div class="">
	<input type="hidden" id="id" name="id" value="" ng-model="formData.id">
<div class="pull-right" id="div_approve" ng-show="approve">
				<input type="hidden" id="Approve_by" name="Approve_by" value="0">
				<input type="hidden" id="Approve_date" name="Approve_date"
					value="{{cur_date_finalize | date:'yyyy/MM/dd'}}">
				<button type="button" class="btn btn-primary btn_status"
					id="btn_finalize" ng-click="aproveData($event)">APPROVE</button>

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
 
 
 
 		<div class="form-group " id="form_div_dispose_date">

				<label for="dispose_date"
					class="col-sm-2 control-label "><!-- <spring:message
						code="stockdisposal.disposlDate"></spring:message> --> Adjust Date <i class="text-danger">*</i></label>

				<div class="col-sm-3">
		
			<div class="input-group">
						<div class="right-inner-addon">
						<i class="fa fa-calendar" id="calender_icon" style="left: 81%;z-index: 4;"></i>
						<input type="text" class="form-control code-font-size required" daterange-pickerbft 
							name="received_date" id="received_date" ng-model="formData.adjust_date"
							ng-disabled="disable_all"
							placeholder="">
							</div><span class="input-group-addon"
							id="form_div_received_date_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="Adjust date cannot be blank"></i></span>
					</div>
			<!-- 		<div class="input-group">
					<input
											       ng-model="input"
											       placeholder="Select a date and time..."
											       moment-picker="input">
						<input type="text" class="form-control code-font-size"
							name="disposal_date" id="disposal_date" value="{{CurrentDate | cpmnyDateformat }}"
							ng-disabled="disable_all" placeholder="">
							
					</div> -->
				</div>
			</div>
			
			<%-- 			<div class="form-group " id="form_div_grn_date">

				<label for="grn_date"
						class="col-sm-3 control-label code-lbl-font-size"><spring:message
							code="stockin.grn.date"></spring:message> <i class="text-danger"></i></label>

				<div class="col-sm-3">
					<div class="input-group">
						<div class="right-inner-addon">
						<i class="fa fa-calendar" id="calender_icon" style="left: 81%;z-index: 4;"></i>
						<input type="text" class="form-control code-font-size required" daterange-picker 
							name="received_date" id="received_date" ng-model="CurrentDate"
							ng-disabled="disable_all"
							placeholder="">
							</div><span class="input-group-addon"
							id="form_div_received_date_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockout.error.req_date"></spring:message>"></i></span>
					</div>
				</div>
			</div> --%>
			
			
			
			
 
   <div class="form-group " id="form_div_department_code">
				<label for="department_id" class="col-sm-2 control-label ">
				<spring:message code="stockdisposal.department_id"></spring:message> <i class="text-danger">*</i></label>
				<div class="col-sm-2">
					<div class="input-group">
						<input type="hidden" name="department_id"
							id="department_id" ng-model="formData.department_id">
						<input type="text" class="form-control required"
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
 
	

			


			<div class="form-group " id="form_div_adjust_by">

				<label for="dispose_by" class="col-sm-2 control-label "> Adjust By<!-- <spring:message
						code="stockdisposal.dispose_by">
					</spring:message> --><i class="text-danger">*</i></label>

				<div class="col-sm-3">
					<div class="input-group">
						<input type="text" class="form-control code-font-size required" maxlength="50"
							name="adjust_by" id="adjust_by" value="" ng-disabled="disable_all" row-save="saveData(event)"
							placeholder=""  ng-model="formData.adjust_by"><span class="input-group-addon"
							id="form_div_adjust_by_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="Adjust by cannot be blank"></i></span>
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
						<th>System Stock</th>
						<th>Actual Qty</th>
						<th>Adjust Qty</th>
						<th>Rate</th>
				<%-- 		<th>Amount(${systemSettings.currencySymbol})</th> --%>
						<th></th>
					</tr>
					<tr ng:repeat="item in invoice.items">
					<td><input type="text" ng-model="item.stock_adjust_id"></td>
				
						<td>{{$index + 1}}</td>
						<td ng-click="tableClicked($index)"><div  style="width: 100%"><div style="float:left;width:30%; padding-right:4px;"><input
							type="hidden" ng-model="item.stockRegDtl_id"><input type="text"
							ng:model="item.stock_item_code" id="stock_item_code"
							name="stock_item_code" ng:required row-save="saveData(event)"
							class="input-mini form-control" autocompete-text 
							ng-disabled="disable_all" ng-style="validation_Sstock_item_code" maxlength="10">
							</div><div style="display: inline-block; width:65%;"><input type="hidden" ng:model="item.stock_item_id"
							id="stock_item_id" name="stock_item_id"> <input
							type="text"  class="input-mini form-control"  ng-model="item.stock_item_name"
							id="stock_item_name" name="stock_item_name" ng-disabled="true"><input
							type="hidden" ng:model="item.stock_item_batch_id"
							id="stock_item_batch_id" name="stock_item_batch_id"><input
							type="hidden" ng:model="item.stock_item_batch_stock"
							id="stock_item_batch_id" name="stock_item_batch_stock">	</div></td> 
						<td style="width:109px"><input type="text" ng-disabled="true" 
								id="uomcode" ng-model="item.uomcode" name="uomcode" class="input-mini form-control"></td>
						<td class="item-table-col-width" ><input type="text"
							ng:model="item.currentStock" class="input-mini form-control algn_rgt"
							id="currentStock" name="currentStock" ng-disabled="true"
							ng-style="validation_currentStock"  valid-number>
					
					</td>
							<td class="item-table-col-width"><input type="text" ng-model="item.actual_qty" maxlength="10" select-on-click   
							ng:required valid-number class="input-mini form-control algn_rgt" 
							id="damaged_qty" name="damaged_qty" ng-disabled="disable_all" row-delete="removeItem($index)" row-save="saveData(event)"
							ng-style="validation_damaged_qty" max-length="10"></td>
						
						<td class="item-table-col-width"><input type="text"  value="{{differenceView(item.currentStock,item.actual_qty)}}"  maxlength="10"  select-on-click
							 id="" name="rate" valid-number  row-delete="removeItem($index)" row-save="saveData(event)"
							class="input-mini form-control algn_rgt"
							ng-disabled="true" ></td>
					
						<td class="item-table-col-width"><input type="text" value=""  maxlength="10"  select-on-click
							 id="" name="rate" ng:model="item.rate" row-add="addItem($index)" row-delete="removeItem($index)" row-save="saveData(event)" 
							class="input-mini form-control algn_rgt"
							ng-disabled="disable_all" ></td>
					<!-- 	<td >
						
						<textarea rows="1" cols="15" ng-model="item.stock_disposal_reason"id="reason" name="reason" row-add="addItem($index)" row-delete="removeItem($index)" row-save="saveData(event)" 
							class="input-mini form-control" ng-disabled="disable_all" ng-style="validation_reason" maxlength="250"></textarea>
						</td> -->
					<!-- 	<td class="item-table-col-width"><input type="text"
							class="input-mini form-control amount algn_rgt" id="amount"
							name="amount" ng-disabled="true"
							value="{{amount(item.damaged_qty * item.rate)}}"></td> -->
						<td><a href ng:click="removeItem($index)"
							class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
					</tr>
					
					<tr >
								<td></td>
								<!-- <td colspan="5" class="font-style algn_rgt"><label class="">Grand
										Total:</label></td> -->
								<td colspan="7" class="font-style algn_rgt"><%-- <label>
										${systemSettings.currencySymbol} {{total() }}</label> --%></td>
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
	
