<div class="" id="purchase_order_form_div" ng-show="show_form1"
	style="width: 100%; height: 450px;">
	<form class="form-horizontal   " id="purchase_order_form">
		<div class="  centered">

			<h3>Select Type Of Purchase Order</h3>
			<div class="radioValue">

				<md-radio-group ng-model="slctypreq"> <md-radio-button
					value="1" class="md-primary">Direct Purchase</md-radio-button> <md-radio-button
					value="2">PO From Requests </md-radio-button> </md-radio-group>
			</div>
		</div>
	</form>
</div>

<div class="" id="purchase_order_form_div" ng-show="show_form">

	<div ng-hide="poStatus" class="form_list_con" id="po_status">{{
		po_status }}</div>





	<form class="form-horizontal  frm_div_stock_in"
		id="purchase_order_form">
		<div class="">



			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id"> <input type="hidden" id="po_status"
				name="id">


			<div class="form-group " id="form_div_po">

				<label for="po" class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="purchaseorder.po"></spring:message><i class="text-danger"></i></label>

				<div class="col-sm-2">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							name="PO_No" id="PO_No" value="{{formData.po_number}}"
							ng-disabled="true" placeholder="">
					</div>
				</div>

				<div class="col-sm-8" style="cursor: pointer;" align="right"
					ng-show="printlink">

					<button class="btn btn-info btn_status" ng-disabled="disable_btn"
						ng-click="print(event);">{{printstatus}}</button>

				</div>
			</div>


			<div class="form-group " id="form_div_po_date">

				<label for="po_date" class="col-sm-2 control-label"><spring:message
						code="purchaseorder.po_date"></spring:message><i
					class="text-danger"></i></label>

				<div class="col-sm-2">
					<div class="input-group">
						<input type="text" class="form-control" name="received_date"
							id="received_date" ng-disabled="true" ng-model="formData.podate1"
							placeholder="">
					</div>
				</div>

				<div class="col-sm-8">
					<!-- button showw -->
					<div align="right" ng-show="bttnstatus">

						<button class="btn btn-info btn_status" data-toggle="modal"
							data-target="#myModal" ng-disabled="disable_btn">
							&nbsp;{{status}}</button>
					</div>
					<div align="right" ng-show="closebttn">
						<button class="btn btn-info btn_status" ng-click="closestatus();"
							ng-disabled="disable_btn">&nbsp;{{status}}</button>
					</div>
					<!-- end button showw -->
				</div>



			</div>


			<div class="form-group " id="form_div_supplier_code">
				<label for="supplier_code" class="col-sm-2 control-label"><spring:message
						code="stockin.supl_name"></spring:message> <i class="text-danger">*</i></label>
				<div class="col-sm-1">
					<div class="input-group">
						<input type="hidden" name="supplier_id" id="supplier_id"
							ng-model="formData.supplier_id"> <input type="text"
							class="form-control required" name="supplier_code" style="margin-right: 54px;"
							id="supplier_code" ng-model="formData.supplier_code" row-save="saveData(event)"
							ng-disabled="disable_all" placeholder="Code"><span
							class="input-group-addon" id="form_div_supplier_code_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockin.error.supplier"></spring:message>"></i></span>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control " name="supplier_name"
							id="supplier_name" ng-model="formData.supplier_name" style="margin-left:80px;"
							ng-disabled="true" placeholder="">
					</div>
				</div>
			</div>



			<input type="hidden" ng-model="formData.print_date" /> <input
				type="hidden" ng-model="formData.po_status" /> <input type="hidden"
				ng-model="changedate" /> <input id="chkstatuschange" type="hidden">
			<input type="hidden" ng-model="dtlstatusid" /> <input type="hidden"
				ng-model="changeDate" />

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
				<table class="table table-bordered" id="items_table" ng-hide="prograssing">
						<tr class="active">
							<th>#</th>
							<th ng-show="centralized">Company Name</th>

							<th>Item Code/Item Name</th>
							<th>Unit</th>
							<th>Qty</th>
							<!-- 	<th>Rec Qty</th> -->
							<th>Rate </th>
							<th>Expected Date</th>
							<th>Total Amt(${systemSettings.currencySymbol})</th>
							<th></th>
						</tr>

						<tr ng:repeat="item in purchaseorderdtldata.items">
							<td style="width: 20px;">{{$index + 1}}</td>
							<td ng-show="centralized">{{item.company_name}}</td>


							<td ng-click="tableClicked($index)" style=""><div style="float:left;width:30%;padding-right:4px;"><input
								type="text" ng:model="item.stock_item_code" id="stock_item_code"
								name="stock_item_code" ng:required 
								class="input-mini form-control" ng-disabled="disable_all"
								autocompete-text> </div><div style="display: inline-block; width:70%;"><input type="hidden"
								ng:model="item.stock_item_id" id="stock_item_id"
								name="stock_item_id"> <input type="text"
								class="form-control " ng-disabled="true" style="height: 35px;"
								ng:model="item.stock_item_name" id="stock_item_name"
								name="stock_item_name"></div></td>
									<td style="width: 101px;"><input type="text" ng-disabled="true" 
								id="uomcode" ng-model="item.uomcode" name="uomcode" class="input-mini form-control "></td>
							<td style="width: 101px;"><input type="text"
								ng:model="item.qty" select-on-click id="itemqty"
								ng-disabled="disable_all" valid-number    row-delete="removeItem($index,$event)" row-save="saveData(event)"
								maxlength="${10+settings['decimalPlace']+1}"
								class="input-mini form-control algn_rgt"></td>


							<td style="width: 120px;"><div style="display: inline-block; padding-right:8px; width:90%;"><input type="text"
								ng:model="item.unit_price" select-on-click valid-number  row-save="saveData(event)" row-delete="removeItem($index,$event)"
								maxlength="${10+settings['decimalPlace']+1}" 
								   ng-disabled="true" 
								class="input-mini algn_rgt form-control"> </div><div style="display: inline-block; width:10%;" ng-show="false"> <input  row-save="saveData(event)" row-delete="removeItem($index)"
									type="checkbox" ng:model="item.is_tax_inclusive"
									ng-disabled="disable_all"> </div></td>

							<td style="width: 150px;">
								<div class="input-group">

									<div class="right-inner-addon">

										<i class="fa fa-calendar" id="calender_icon"
											style="left: 80%; z-index: 4;"></i> <input type="text"   row-add="addItem($index)"  row-save="saveData(event)" row-delete="removeItem($index)" 
											
											ng-change="tableDatevalidation(item.expected_date,$index)"
											class="form-control " daterange-picker name="request_date"
											id="request_date" ng-model="item.expected_date"
											placeholder="" ng-disabled="disable_all">

									</div>
								</div>
							</td>

							<td style="width: 150px;"><input type="text"
								value="{{amount(item.qty * item.unit_price)}} " valid-number row-save="saveData(event)" row-delete="removeItem($index)"
								class="input-mini algn_rgt form-control" ng-disabled="true"></td>
							<td style="width: 50px;"><a href
								ng:click="removeItem($index)" class="btn btn-small"><i
									class="fa fa-minus "></i> </a></td>
						</tr>


						<tr ng-show="centralized">
							<td></td>
							<td colspan="6" class="font-style algn_rgt"><label class="">Grand
									Total:</label></td>
							<td colspan="1" class="font-style algn_rgt"><label>
									${systemSettings.currencySymbol} {{total() }}</label></td>
							<td><a href ng:click="addItem()" class="btn btn-small"><i
									class="fa fa-plus"></i> </a></td>
						</tr>

						<tr ng-hide="centralized">
							<td></td>
							<td colspan="5" class="font-style algn_rgt"><label class="">Grand
									Total:</label></td>
							<td colspan="1" class="font-style algn_rgt"><label>
									${systemSettings.currencySymbol} {{total() }}</label></td>
							<td><a href ng:click="addItem()" class="btn btn-small"><i
									class="fa fa-plus"></i> </a></td>
						</tr>


						<!-- <tr>
						<td></td>
						<td></td>
						<td></td>
						<td ng-show="centralized"></td>

						<td></td>
						<td><label class="col-sm-2 control-label">Grand Total:</label></td>
						<td style="width: 200px;"><input type="text"
							class="input-mini algn_rgt form-control" id="totalamount"
							value="{{total() | cpmnycurrency}}" ng-disabled="true"
							valid-number></td>
						<td style="width: 50px;"><a href ng:click="addItem()"
							class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
					</tr> -->

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

<div class="modal fade" id="confirmPrintModel" role="dialog">
	<div class="modal-dialog modal-lg">
		<!-- Modal content-->
		<div class="modal-content">
			
			<div class="modal-body">
				<div class="popimglayoutdiv" style="text-align: center">
				You cannot make any changes once you print it.Are you sure to continue?
						
					</div>
				
			</div>

			<div class="modal-footer" >
		<div >
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				<button class="btn  btn-sm btn-primary save_button savebtn pull-right" type="button" name="btnOk" id="btnOk" 
				ng-click="printPO1($event)"  data-dismiss="modal" data-target="#importDataModal">
			     Ok
		</button> 
			</div>
			
			</div>
		</div>

	</div>
</div>



