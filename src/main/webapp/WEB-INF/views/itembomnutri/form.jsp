<div class="" id="itembom_form_div" ng-show="show_form">
	<form class="form-horizontal frm_div_itembom" id="itembom_form">
<!-- 	<div class="row justify-end"> -->
<!-- 			<button type="button" class="btn btn-link refresh_btn" -->
<!-- 					ng-disabled="disable_bom" ng:click="syncNutri()" id="shownutri">Sync Db -->
<!-- 					<i class="fa fa-refresh btn-sync" aria-hidden="true"></i> -->
<!-- 				</button> -->
<!-- 	</div> -->
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group " id="form_div_stock_item_code">
					<label for="stock_item_code" class="col-sm-2 control-label"><spring:message
							code="itembom.stock_item_name"></spring:message> <i
						class="text-danger">*</i></label>
					<div class="col-sm-2 stok_item_div_con">
						<div class="input-group stock_item_div">
							<input type="hidden" name="stock_item_id" id="stock_item_id"
								ng-model="formData.stock_item_id"> <input type="text"
								class="form-control required" name="stock_item_code"
								id="stock_item_code" ng-model="formData.stock_item_code"
								ng-disabled="disable_item_code" placeholder="Code"><span
								class="input-group-addon" id="form_div_stock_item_code_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="itembom.error.stock_item_name"></spring:message>"></i></span>
						</div>
					</div>
					<div class="col-sm-4 stok_item_div_con_rgt">
						<div class="input-group">
							<input type="text" class="form-control " name="stock_item_name"
								id="stock_item_name" ng-model="formData.stock_item_name"
								ng-disabled="true" placeholder="">
						</div>
					</div>

					<div class="col-sm-2">
						<div class="form-group " id="form_div_uom">
							<div class="form_div_uom_div_con">
								<div class="input-group">
									<input type="text" class="form-control" name="uom"
										maxlength="20" id="uom" ng-model="formData.uom"
										ng-disabled="true" placeholder="">
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- bom master -->
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group " id="form_div_bom_master_code">
					<label for="base_bom_item_code" class="col-sm-2 control-label"><spring:message
							code="itembom.base_item_code"></spring:message></label>
					<div class="col-sm-2 stok_item_div_con">
						<div class="input-group base_bom_item_div">
							<input type="hidden" name="base_item_id" id="base_item_id"
								ng-model="formData.base_item_id"> <input type="text"
								class="form-control" name="base_bom_item_code"
								id="base_bom_item_code" ng-model="formData.base_bom_item_code"
								ng-disabled="disable_item_code" placeholder="Code">
							<%-- <input type="text" class="form-control" name="base_bom_itemId"
							id="base_bom_itemId" ng-model="formData.base_bom_itemId"
							ng-disabled="disable_item_code" placeholder="Code"> --%>
							<%-- <span
							class="input-group-addon" id="form_div_stock_item_code_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="itembom.error.stock_item_name"></spring:message>"></i></span> --%>
						</div>
					</div>
					<div class="col-sm-4">

						<div class="form_div_uom_div_con">
							<div class="input-group">
								<input type="text" class="form-control "
									name="base_bom_item_name" id="base_bom_item_name"
									ng-model="formData.base_bom_item_name" ng-disabled="true"
									placeholder="">
							</div>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="form-group " id="form_div_uom">
							<div class="form_div_uom_div_con">
								<div class="input-group">
									<input type="text" class="form-control" name="uom"
										maxlength="20" id="uom" ng-model="formData.uom"
										ng-disabled="true" placeholder="">
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- <div style="float: left; width: 25%; padding-right: 4px;">
										<input type="text" class="" id="ItemId" name="ItemId"
											autocompete-text ng-model="item.bom_item_code">
									</div>
									<div style="display: inline-block; width: 75%;">
										<input type="text" class="form-control itmname searchName"
											id="bom_item_name" name="bom_item_name" disabled="disabled"
											ng-model="item.bom_item_name"> <input type="hidden"
											class="prdCode" id="bom_item_id" name="bom_item_id"
											ng-model="item.bom_item_id"> <input type="hidden"
											id="id" name="id" ng-model="item.id">
									</div> -->



		<div class="row">
			<div class="col-sm-12">
				<div class="form-group " id="form_div_item_category_name">
					<label for="item_category_name" class="col-sm-2 control-label"><spring:message
							code="itembom.item_category_name"></spring:message> <i
						class="text-danger"></i></label>
					<div class="col-sm-3">

						<div class="form_div_uom_div_con">
							<div class="input-group">
								<input type="text" class="form-control"
									name="item_category_name" maxlength="20"
									id="item_category_name" ng-model="formData.item_category_name"
									ng-disabled="true" placeholder="">
							</div>
						</div>

					</div>
					<div class="form-group " id="form_div_dept_name">
						<label for="dept_name" class="col-sm-2 control-label">Department
							Name <i class="text-danger"></i>

						</label>
						<div class="col-sm-3">
							<div class="form_div_uom_div_con">
								<div class="input-group">
									<input type="text" class="form-control" name="dept_name"
										maxlength="20" id="dept_name" ng-model="formData.dept_name"
										ng-disabled="true" placeholder="">
								</div>
							</div>
						</div>

					</div>

				</div>
			</div>
		</div>




		<div class="row">
			<div class="col-sm-9">
				<div class="form-group" id="form_div_semifinished">

					<div class="col-sm-12">
						<div class="col-sm-3 control-label" id="semifinished">

							<md-checkbox type="checkbox" ng-model="formData.is_semi_finished"
								ng-true-value="true" ng-false-value="false" ng-disabled="true"
								aria-label="semifinished" class="chck_box_div"></md-checkbox>




						</div>

						<label for="semifinished" class="semi-fini"><spring:message
								code="itembom.semifinished"></spring:message></label>


						<div class="col-sm-1 control-label" id="finished">

							<md-checkbox type="checkbox" ng-model="formData.is_finished"
								ng-true-value="true" ng-false-value="false" ng-disabled="true"
								aria-label="finished" class="chck_box_div"></md-checkbox>




						</div>

						<label for="finished" class="semi-fini"><spring:message
								code="itembom.finished"></spring:message></label>

					</div>
				</div>
			</div>
		</div>



		<div class="form-group " id="form_div_bom_qty">
			<label for="bom_qty" class="col-sm-2 control-label"><spring:message
					code="itemmaster.standardquantity"></spring:message><i
				class="text-danger">*</i></label>
			<div class="col-sm-2">
				<div class="input-group">
					<input type="text" class="form-control algn_rgt" name="bom_qty"
						id="bom_qty" ng-model="formData.stock_item_qty" required
						ng-change="getBomItemWeight()" ng-disabled="disable_all"
						placeholder="" valid-number
						maxlength="${10+settings['decimalPlace']+1}"> <span
						class="input-group-addon" id="form_div_bom_qty_error"
						style="display: none;"><i
						class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
						data-placement="bottom" title=""
						data-original-title="<spring:message code="itembom.error.bom_qty"></spring:message>"></i></span>


				</div>

			</div>

			<label for="bom_qty" class="col-sm-2 control-label"><spring:message
					code="itembom.base_item_weight"></spring:message></label>
			<div class="col-sm-2">
				<div class="input-group">
					<input type="text" class="form-control algn_rgt"
						name="base_bom_qty" id="base_bom_qty"
						ng-model="formData.base_bom_qty"
						<%-- ng-disabled="disable_all" --%> placeholder="" valid-number
						disabled="disabled">
				</div>
			</div>

			<label for="bom_qty" class="col-sm-2 control-label"><spring:message
					code="itembom.bom_item_weight"></spring:message></label>
			<div class="col-sm-2">
				<div class="input-group">
					<input type="text" class="form-control algn_rgt"
						name="bom_item_weight" id="bom_item_weight"
						ng-model="formData.bom_item_weight"
						<%-- ng-disabled="disable_all" --%> placeholder="" valid-number
						disabled="disabled">
				</div>
			</div>
		</div>
		<div class="form-group" id="div_table_bom">
			<div class="cmmn_maain_tbl">
				<circle-spinner ng-show="prograssing"></circle-spinner>
				<div>
					<table id="stockHead" style="background-color: white;"
						class="table table-bordered tableSection scroll stock-tables">
						<thead style="background-color: #f1f1f1;" class="stockHead">

							<tr>
								<th>#</th>
								<th><spring:message code="itemmaster.itemcode"></spring:message></th>
								<th>Uom</th>
								<th><spring:message code="itemmaster.qty"></spring:message></th>
								<!--  <th>BOM Rate</th>-->
								<th>Rate</th>
								<th>Amount</th>
								<th class="itemnutrishow"></th>
								<th></th>
							</tr>
						</thead>
						<tr ng:repeat="item in bomList">

							<td>{{$index + 1}}</td>

							<td ng-keyup="sendId($index)" ng-click="tableClicked($index)"
								class=""><div style="width: 100%">
									<div style="float: left; width: 25%; padding-right: 4px;">
										<input type="text" class="" id="ItemId" name="ItemId"
											autocompete-text ng-model="item.bom_item_code">
									</div>
									<div style="display: inline-block; width: 75%;">
										<input type="text" class="form-control itmname searchName"
											id="bom_item_name" name="bom_item_name" disabled="disabled"
											ng-model="item.bom_item_name"> <input type="hidden"
											class="prdCode" id="bom_item_id" name="bom_item_id"
											ng-model="item.bom_item_id"> <input type="hidden"
											id="id" name="id" ng-model="item.id">
									</div></td>


							<td style="width: 160px;"><input type="text"
								ng-disabled="true" id="uomcode" ng-model="item.uomcode"
								name="uomcode" class="input-mini form-control"></td>
							<td class="itmqty" style="width: 110px"><input type="text"
								class="form-control algn_rgt" id="qty" name="qty"
								ng-model="item.qty" ng-disabled="disable_all" valid-number
								ng-style="validation_qty" ng-blur="stockProdQty($index,$event)"
								row-delete="removeBomRow($index)"></td>

							<!-- <td style="width: 110px"><input type="text"
								ng:model="item.unit_price"
								maxlength="${15+settings['decimalPlace']+1}" select-on-click
								valid-number id="unit_price" name="unit_price"
								row-delete="removeBomRow($index)" row-save="saveData()"
								class="input-mini form-control algn_rgt"
								ng-disabled="disable_all" ng-style="validation_unit_price" row-add="addBomRow($index)"  row-delete="removeBomRow($index)"></td> -->



							<td style="width: 140px"><input type="text"
								ng:model="item.last_unit_price"
								maxlength="${15+settings['decimalPlace']+1}" select-on-click
								valid-number id="last_unit_price" name="last_unit_price"
								row-delete="removeBomRow($index)" row-save="saveData()"
								class="input-mini form-control algn_rgt" ng-disabled="true"
								ng-style="validation_last_unit_price"></td>


							<td style="width: 110px"><input type="text"
								class="input-mini form-control amount algn_rgt" id="amount"
								name="amount" ng-disabled="true" value="{{amount(item)}}"></td>

							<!-- <td class="itemnutrishow"><button type="button"
									class="btn btn-link" ng-disabled="disable_bom"
									ng:click="selectMe($event)" id="shownutri">
									<i class="fa fa-cutlery" aria-hidden="true"></i>
								</button></td> -->


							<td ></td>

							<td class="itemrmv"><button type="button"
									class="btn btn-link" ng-disabled="disable_bom"
									ng:click="removeBomRow($index)" id="rowdel">
									<i class="fa fa-minus" aria-hidden="true"></i>
								</button></td>

						</tr>
						<tr>

							<td colspan="7" class="font-style algn_rgt"><label class=""></td>

							<td><a href ng:click="addBomRow($index)"
								class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
						</tr>


					</table>


				</div>
				<div>
					<table id="prodCost" style="background-color: white;"
						class="table table-bordered tableSection scroll stock-tables">
						<thead style="background-color: #f1f1f1;" class="">

							<tr>
								<th>#</th>
								<th>Production Cost Type</th>
								<th>Cost Type</th>
								<th ng-hide="true">Percentage</th>
								<th>Rate</th>

								<th></th>
							</tr>
						</thead>
						<tr ng:repeat="item in prodCostList">

							<td>{{$index + 1}}</td>

							<td ng-keyup="sendId($index)" ng-click="tableClicked2($index)"
								class=""><div style="width: 100%">
									<div style="float: left; width: 25%; padding-right: 4px;">
										<input type="text" class="" id="prodItemId" name="prodItemId"
											table-productioncost ng-model="item.prod_cost_code">
									</div>
									<div style="display: inline-block; width: 75%;">
										<input type="text" class="form-control itmname searchName"
											id="prod_cost_name" name="prod_cost_name" disabled="disabled"
											ng-model="item.prod_cost_name"> <input type="hidden"
											class="prdId" id="prod_cost_id" name="prod_cost_id"
											ng-model="item.prod_cost_id"> <input type="hidden"
											id="id" name="id" ng-model="item.id">
									</div></td>
							<td style="width: 120px;"><input type="text"
								ng-disabled="true" id="prdcosttype"
								ng-model="item.prod_cost_type" name="prdcosttype"
								class="input-mini form-control"></td>
							<td ng-hide="true" style="width: 70px"><input
								type="checkbox" ng-model="item.isPercentage"
								ng-change="selectPercntg()" /></td>

							<td style="width: 110px"><input type="text"
								ng:model="item.rate"
								maxlength="${15+settings['decimalPlace']+1}" select-on-click
								valid-number id="cost_rate" name="cost_rate"
								row-add="addProdCostRow($index)"
								row-delete="removeBomRow($index)" row-save="saveData()"
								class="input-mini form-control algn_rgt"
								ng-disabled="disable_all" ng-style="validation_unit_price"></td>



							<td class="itemrmv"><button type="button"
									class="btn btn-link" ng-disabled="disable_all"
									ng:click="removeProdCostRow($index)" id="rowdel">
									<i class="fa fa-minus" aria-hidden="true"></i>
								</button></td>

						</tr>
						<tr>

							<td colspan="4" class="font-style algn_rgt"><label class=""></td>

							<td><a href ng:click="addProdCostRow($index)"
								class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
						</tr>


					</table>


				</div>


			</div>


		</div>


		<div class="maindivdetail col-sm-12 new_no_pad">
			<div class="frstdiv col-sm-6 new_no_pad frstdvwidth">
				<p class="para">Sales Price Calculation</p>

				<div class="form-group " id="form_div_cost_unit">
					<label for="" class="col-sm-4 control-label"><spring:message
							code="itembom.cost_price"></spring:message></label>
					<div class="item_text_input ">
						<div class="">
							<input type="text" class="form-control algn_rgt"
								name="cost_per_unit" id="cost_per_unit"
								value="{{costPerUnit()}}" ng-disabled="true" placeholder=""
								valid-number maxlength="11">
						</div>
					</div>
				</div>


				<div class="form-group " id="form_div_margin">
					<label for="" class="col-sm-4 control-label"><spring:message
							code="itembom.margin"></spring:message></label>
					<div class=" item_text_input">
						<div class="">
							<input type="text" class="form-control algn_rgt" name="margin"
								id="margin" ng-model="formData.sales_margin"
								ng-disabled="disable_all" placeholder="" numbers-only
								maxlength="10">
						</div>


					</div>
					<div class="col-sm-1 control-label" id="margin_percent">
						<md-checkbox type="checkbox"
							ng-model="formData.is_sales_margin_percent" ng-true-value="true"
							ng-false-value="false" ng-disabled="disable_all"
							aria-label="margin_percent" class="chck_box_div"></md-checkbox>
					</div>

					<label for="margin_percent" class="margin_percent is_per_lab">is_percent</label>
				</div>

				<div class="form-group " id="form_div_total_sale_price">
					<label for="" class="col-sm-4 control-label"><spring:message
							code="itembom.total_sale_pri"></spring:message></label>
					<div class="item_text_input">
						<div class="">
							<input type="text" class="form-control algn_rgt"
								name="total_sale_price" id="total_sale_price"
								value="{{totalSalesPrice()}}" ng-disabled="true" placeholder=""
								valid-number maxlength="11">
						</div>
					</div>
				</div>



			</div>
			<div class="scnddiv col-sm-6 new_no_pad">
				<p class="para">Estimate tax calculation</p>
				<div class="form-group " id="form_div_tax_percent">
					<label for="" class="col-sm-4 control-label"><spring:message
							code="itembom.tax"></spring:message></label>
					<div class="item_text_input">
						<div class="">
							<select class="form-control" name="tax"
								ng-options="v.id as v.name for v in taxList" id="tax"
								ng-model="formData.tax_id" ng-disabled="disable_all"
								ng-change="taxAmountCal()"></select>
						</div>
					</div>
				</div>



				<div class="form-group " id="form_div_tax_amount">
					<label for="" class="col-sm-4 control-label "><spring:message
							code="itembom.tax_amount"></spring:message></label>
					<div class="item_text_input">
						<div class="">
							<input type="text" class="form-control algn_rgt " name="tax"
								id="tax" value="{{taxAmountCal()}}" ng-disabled="true"
								placeholder="" valid-number maxlength="11">
						</div>
					</div>


					<div class="">
						<div class=" col-sm-1 control-label" id="inclusive_tax">

							<md-checkbox type="checkbox"
								ng-model="formData.tax_calculation_method" ng-true-value="true"
								ng-false-value="false" ng-disabled="disable_all"
								aria-label="inclusive_tax" class="chck_box_div"></md-checkbox>




						</div>
					</div>
					<label for="inclusive_tax" class=" margin_percent is_per_lab"><spring:message
							code="itembom.inclusive_tax"></spring:message></label>

				</div>

				<div class="form-group " id="form_div_amount_after_tax">
					<label for="" class="col-sm-4 control-label"><spring:message
							code="itembom.amount_after_tax"></spring:message></label>
					<div class="">
						<div class="item_text_input">
							<input type="text" class="form-control algn_rgt"
								name="amount_after_tax" id="amount_after_tax"
								value="{{amountAfterTax()}}" ng-disabled="true" placeholder=""
								valid-number maxlength="11">
						</div>
					</div>
				</div>



			</div>

		</div>
</div>




</form>
</div>

