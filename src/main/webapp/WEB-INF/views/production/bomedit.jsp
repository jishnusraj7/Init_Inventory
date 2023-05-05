<div class="form-group " id="form_div_bom_qty">
					<label for="" class="col-sm-3 control-label"><spring:message
							code="itemmaster.standardquantity"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group">
							<!-- <input type="text" id="bom_qty" name="bom_qty"
								class="form-control"
								onkeypress="return NumericValidation(this, event, false, false);"> -->
							<input type="text" class="form-control algn_rgt" name="bom_qty"
								id="bom_qty" ng-model="bomQty"
								ng-disabled="disable_all" placeholder="" valid-number
								maxlength="11">
						</div>
					</div>
				</div>
				<div class="form-group" id="div_table_bom">
					<table id="stockHead"  style="background-color: white;"     

						class="table table-bordered tableSection scroll">
						<thead style="background-color: #f1f1f1;" class="stockHead">

							<tr>
								<th>#</th>
								<th><spring:message code="itemmaster.itemcode"></spring:message></th>
								<th>Uom</th>
								<th><spring:message code="itemmaster.qty"></spring:message></th>
								<th>Rate</th>

								<th ng-hide="true"></th>
							</tr>
						</thead>
						<tr ng:repeat="item in bomList">

							<td>{{$index + 1}}</td>

							<td ng-keyup="sendId($index)" ng-click="tableClicked3($index)"
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

							<td style="width: 100px;"><input type="text"
								ng-disabled="true" id="uomcode" ng-model="item.uomcode"
								name="uomcode" class="input-mini form-control"></td>
							<td class="itmqty" style="width: 110px"><input type="text"
								class="form-control algn_rgt" id="qty" name="qty"
								ng-model="item.qty" ng-disabled="disable_all" valid-number
								ng-style="validation_qty" maxlength="15" row-add="addBomRow($index)"></td>
							
							<td style="width: 110px"><input type="text" 
								ng:model="item.unit_price"
								maxlength="${15+settings['decimalPlace']+1}" select-on-click
								valid-number id="unit_price" name="unit_price"
								 row-delete="removeBomRow($index)"
								row-save="saveData()" class="input-mini form-control algn_rgt"
								ng-style="validation_unit_price" ng-disabled="true"></td>
							


							<td class="itemrmv" ng-hide="true"><button type="button"
									class="btn btn-link" ng-disabled="disable_all"
									ng:click="removeBomRow($index)" id="rowdel">
									<i class="fa fa-minus" aria-hidden="true"></i>
								</button></td>

						</tr>
						<tr>

							<td colspan="5" class="font-style algn_rgt"><label class=""></td>

							<td ng-hide="true"><a href ng:click="addBomRow($index)"
								class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
						</tr>


					</table>

					
 				<table id="prodCost"  style="background-color: white;"     

						class="table table-bordered tableSection scroll">
						<thead style="background-color: #f1f1f1;" class="">

							<tr>
								<th>#</th>
								<th>Production Cost Type</th>
								<th>Cost Type</th>
								 <th ng-hide="true">Percentage</th> 
								<th>Rate</th>

								<th ng-hide="true"></th>
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
								ng-disabled="true" id="prdcosttype" ng-model="item.prod_cost_type"
								name="prdcosttype" class="input-mini form-control"></td>
							<td ng-hide="true" style="width:70px"><input type="checkbox" ng-model="item.isPercentage" ng-change="selectPercntg()"/></td>
							
							<td style="width: 110px"><input type="text"
								ng:model="item.rate"
								maxlength="${15+settings['decimalPlace']+1}" select-on-click
								valid-number id="cost_rate" name="cost_rate"
								row-add="addProdCostRow($index)" row-delete="removeBomRow($index)"
								row-save="saveData()" class="input-mini form-control algn_rgt"
								ng-disabled="disable_all" ng-style="validation_unit_price"></td>
							


							<td class="itemrmv" ng-hide="true"><button type="button"
									class="btn btn-link" ng-disabled="disable_all"
									ng:click="removeProdCostRow($index)" id="rowdel">
									<i class="fa fa-minus" aria-hidden="true"></i>
								</button></td>

						</tr>
						<tr>

							<td colspan="4" class="font-style algn_rgt"><label class=""></td>

							<td ng-hide="true"><a href ng:click="addProdCostRow($index)"
								class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
						</tr>


					</table>
				</div>
				
					