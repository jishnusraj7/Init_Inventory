<div class="" id="area_code_form_div" ng-show="show_form">
	<form class="form-horizontal" id="area_code_form">
		<div class="">
			<jsp:directive.include file="../common/includes/common_form.jsp" />
			<div class="form-group" id="form_div_uom_id">
						<label for="uom_id" class="col-sm-2 control-label lb"><spring:message
								code="itemmaster.uom_id"></spring:message> <i
							class="text-danger">*</i></label>
						<div class="col-sm-4">
							<div class="input-group col-sm-12">
								<select class="form-control required" id="uom_id" name="uom_id"
									ng-options="v.id as v.name for v in filluom" id="uom_id"
									ng-model="formData.uom_id" ng-disabled="disable_all">
								</select> <span class="input-group-addon" id="form_div_uom_id_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="itemmaster.uom_id.error"></spring:message>"></i></span>
							</div>
						</div>
					</div>
					
					
					
					
					<div class="form-group " id="form_div_items_tbl">
						<label for="uom_id" class="col-sm-2 control-label lb" style="float: left;"> PRICE SETTINGS</label>
							
			<div class="cmmn_maain_tbl">
				<circle-spinner ng-show="prograssing"></circle-spinner>
				<table class="table table-bordered" id="items_table" ng-hide="prograssing">

					<tr class="active">
						<th>Default</th>
						<th>Item Code/Item Name</th>
						<th>Price Difference</th>
						<th>Qty</th>
						<th></th>
					</tr>
					<tr ng:repeat="item in pricesettings.items">
						<td style="width: 20px;"><input type="checkbox"
							ng-model="item.is_default" />    </td>
						<td ng-click="tableClicked($index)" style="width:403px"><div style="float:left;width:25%; padding-right:4px;"><input
							type="text" ng:model="item.stock_item_code" id="stock_item_code"
							name="stock_item_code" ng:required
							class="input-mini form-control" ng-disabled="disable_all"
							autocompete-text> </div><div style="display: inline-block; width:75%;"> <input type="hidden"
							ng:model="item.substitution_sale_item_id" id="substitution_sale_item_id"
							name="substitution_sale_item_id"> <input type="text"
							class="form-control " ng-disabled="true" style="height: 35px;"
							ng:model="item.stock_item_name" id="stock_item_name"
							name="stock_item_name"></div></td>
							<td style="width: 109px;"><input type="text" ng-disabled="disable_all"  valid-number
								id="price_diff" ng-model="item.price_diff" name="uomcode" class="input-mini form-control"></td>
						<td style="width: 96px;"><input type="text"
							ng:model="item.qty" select-on-click id="itemqty"  row-delete="removeItem($index)"  row-save="save_data()"
							ng-disabled="disable_all" valid-number maxlength="${10+settings['decimalPlace']+1}" 
							class="input-mini form-control algn_rgt"></td>

					
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