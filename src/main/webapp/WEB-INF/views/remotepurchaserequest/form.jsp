

<div class="" id="purchase_order_form_div" ng-show="show_form">
	<div ng-hide="rprStatus" class="form_list_con" id="rpr_status">{{
		rpr_status }}</div>

	<form class="form-horizontal  frm_div_stock_in"
		id="purchase_order_form">
		<div class="">

			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id"> <input type="hidden" id="po_status"
				name="id">

			<div class="form-group " id="form_div_pr">

				<label for="po" class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="remoterequest.reqno"></spring:message> <i
					class="text-danger"></i> </label>

				<div class="col-sm-2">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							id="pr_number" name="pr_number"
							ng-model="formData.request_number" ng-disabled="true"> <input
							type="hidden" class="form-control" name="PR_No" id="PR_No"
							value="{{formData.request_number}}" ng-disabled="true"
							placeholder="">
					</div>
				</div>
				<div class="col-sm-8">

					<div align="right" ng-show="closebttn">
						<button class="btn btn-info btn_status" ng-click="closestatus();"
							ng-disabled="disable_btn">&nbsp;{{status}}</button>
					</div>
				</div>

			</div>
			<div class="form-group " id="form_div_request_by">
				<label for="request_by"
					class="col-sm-2 control-label code-lbl-font-size">Request
					By<i class="text-danger">*</i>
				</label>

				<div class="col-sm-3">
					<div class="input-group">
						<input type="text" class="form-control  required"
							name="request_by" maxlength="50" id="request_by"
							ng-model="formData.request_by" ng-disabled="disable_all"
							placeholder=""> <span class="input-group-addon"
							id="form_div_request_by_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="Request By cannot be null "></i></span>
					</div>
				</div>
			</div>
			<div class="form-group " id="form_div_request_date">

				<label for="create_date" class="col-sm-2 control-label "><spring:message
						code="purchaseorder.request_date"></spring:message> <i
					class="text-danger">*</i></label>

				<div class="col-sm-2">
					<div class="input-group">
						<div class="right-inner-addon">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control  required" daterange-picker
								name="request_date" id="request_date"
								ng-model="formData.submit_date" ng-disabled="disable_all"
								placeholder="">
						</div>
						<span class="input-group-addon" id="form_div_request_date_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="Request Date cannot be null "></i></span>
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
				<table class="table table-bordered" id="items_table" ng-hide="prograssing">
					<tr class="active">
						<th>SINO</th>
						<th>Item Code/Item Name</th>
						<th>Qty</th>
						<th>Expected Date</th>
						<th></th>
					</tr>
					<tr ng:repeat="item in purchaseorderdtldata.items">
						<td style="width: 50px;"><input type="hidden"
							ng-model="item.id" />{{$index + 1}}</td>
						<td ng-click="tableClicked($index)" style=""><div  style="width: 100%"><div style="float:left;width:25%; padding-right:4px;"><input
							type="text" ng:model="item.stock_item_code" id="stock_item_code"
							name="stock_item_code" ng:required
							class="input-mini form-control" ng-disabled="disable_all"
							autocompete-text> <input type="hidden"
							ng:model="item.stock_item_id" id="stock_item_id"
							name="stock_item_id"> </div><div style="display: inline-block; width:50%;"> <input type="text"
							class="form-control " ng-disabled="true" style="height: 35px;"
							ng:model="item.stock_item_name" id="stock_item_name"
							name="stock_item_name"></div><div style="padding-left:4px; display: inline-block; width:25%;height: 34px;"><input type="text" ng-disabled="true" style="width: 100%; height: 100%" 
								id="uomcode" ng-model="item.uomcode" name="uomcode" class="input-mini form-control"></div></td>
						<td style="width: 101px;"><input type="text"
							ng:model="item.qty" select-on-click id="itemqty" row-add="addItem($index)"  row-delete="removeItem($index)"  row-save="saveData()"
							ng-disabled="disable_all" valid-number maxlength="${10+settings['decimalPlace']+1}"
							class="input-mini form-control algn_rgt"></td>
						<td style="width: 145px;">
							<div class="input-group">
								<div class="right-inner-addon">

									<i class="fa fa-calendar" id="calender_icon"
										style="left: 80%; z-index: 4;"></i> <input type="text" row-add="addItem($index)" row-delete="removeItem($index)"  row-save="saveData()"
										class="form-control " daterange-picker name="expected_date"
										id="expected_date" ng-model="item.expected_date"
										placeholder="" ng-disabled="disable_all">
								</div>
							</div>
						</td>
						<td style="width: 101px; display: none;"><input type="text"
							ng:model="item.request_status" ng-disabled="true" valid-number
							maxlength="10" class="input-mini form-control algn_rgt"></td>

						<td style="width: 50px;"><a href
							ng:click="removeItem($index)" class="btn btn-small"><i
								class="fa fa-minus "></i> </a></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td style="width: 50px;"><a href ng:click="addItem()"
							class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
					</tr>
				</table>
			</div>
		</div>

		</div>
	</form>
</div>


