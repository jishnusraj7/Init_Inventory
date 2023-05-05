
<div class="" id="production_form_div" ng-show="show_form">
	<form class="form-horizontal frm_div_stock_in" id="production_form">
		<div class="">
			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id">


			<!-- <div class="form-group status_bar" ng-hide="prodStatus">
									<div class="form_list_con approved_bg col-sm-12"
										id="prod_status">{{ prod_status }}</div>
								</div> -->
			<div class="form-group " id="form_div_ref_no">

				<label for="refNo" class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="dailyproduction.refNo">
					</spring:message><i class="text-danger"></i></label>

				<div class="col-sm-2">
					<div class="input-group">
						<input type="text" class="form-control code-font-size"
							name="REf_No" id="REf_No" value="{{REFNO}}" ng-disabled="true"
							placeholder="">
					</div>
				</div>
				<div class="col-sm-8" style="cursor: pointer;" align="right"
					ng-show="showFinalize">
					<button class="btn btn-info btn_status" ng-disabled="disable_btn"
						ng-click="finalize();">Finalize</button>
				</div>
			</div>





			<div class="form-group" id="form_div_date_prod">
				<label for="startdate_prod" class="col-sm-2 control-label lb">Prod
					Date:<i class="text-danger">*</i>
				</label>
				<div class="col-sm-2">
					<div class="input-group col-sm-12">
						<div class="right-inner-addon"
							id="form_div_scheduledate_planning_prod">
							<input type="text" class="form-control" name="prod_date"
								id="prod_date" ng-model="formData.prodDate" placeholder=""
								ng-disabled="true">
						</div>


					</div>

				</div>

			</div>

			<div class="form-group" id="form_div_time">
				<label for="time" class="col-sm-2 control-label lb"> Prod
					Time: <i class="text-danger">*</i>
				</label>

				<div class="col-sm-3">
					<div class="input-group" id="divProdTime">
						<input type="text" class="form-control" name="prod_date"
								id="prod_time_div" value="{{Time | date:filterTimes()}}" placeholder=""
								ng-disabled="true"><!-- <div class="form-control prod_time_div" ng-disabled="true"></div> -->
					</div>
				</div>
			</div>
			<circle-spinner ng-show="prograssing"></circle-spinner>
			<div ng-hide="prograssing">
				<div class="form-group" id="form_div_items_tbl">


					<div class="cmmn_maain_tbl" ng-hide="prograssing">

						<table class="table table-bordered" id="items_table">
							<thead>
								<tr class="active">
									<th id="codehdr" style="width: 160px;">ITEM CODE/ITEM NAME</th>
									<th class="textcntr">UNIT</th>
									<th style="width: 220px">DEPARTMENT</th>
									<th style="width: 100px">ORDERS</th>
									<th>QTY</th>
									<th>DAMAGE</th>
									<th style="width: 120px">COST</th>

									<th class="textcntr">REMARKS</th>
								<!-- 	<th style="width: 50px">VIEW BOM</th> -->
									<th ng-hide="true"></th>
								</tr>
							</thead>
							<tbody>
								<tr ng:repeat="item in daily_production.items">

									<td id="stkAuto" ng-click="tableClicked($index)"
										style="width: 385px"><div style="width: 21%; float: left"
											class="item_stock_input">
											<input type="text" ng:model="item.stock_item_code"
												id="stock_item_code" name="stock_item_code" ng:required
												class="input-mini form-control" ng-disabled="disable_all"
												table-autocomplete01>
										</div>
										<div class="item_stock_input_id setmargin"
											style="width: 75%; float: right; margin: 0px 5px;">
											<input type="hidden" ng-model="item.stock_item_id"
												id="stock_item_id" name="stock_item_id"> <input
												type="text" class="form-control " ng-disabled="true"
												style="height: 35px;" ng-model="item.stock_item_name"
												id="stock_item_name" name="stock_item_name">
										</div></td>
									<td align="center" id="div_uomcode"><div
											class="div_uomcode">
											<input type="text" ng-disabled="true" style="" id="uomcode"
												ng-model="item.uomcode" name="uomcode"
												class="input-mini form-control">
										</div></td>

									<td id="depId1" ng-click="tableClicked1($index)"
										style="width: 220px"><div class="item_stock_input"
											style="float: left; width: 28%; margin-right: 5px;">

											<input type="text" class="form-control" name="source_code"
												id="source_code" ng-model="item.source_code"
												ng-disabled="disable_all" placeholder="Code"
												table-department>


										</div>
										<div class="item_stock_input_id setmargin"
											style="float: left; width: 69%;">
											<input type="hidden" name="source_department_id"
												id="source_department_id" ng-model="item.department_id">
											<input type="text" class="form-control"
												name="to_department_name" id="to_department_name"
												ng-model="item.source_name" ng-disabled="true"
												placeholder="">
										</div></td>

									<td style="width: 85px;">
										<div>

											<input type="text" ng-disabled="true"
												style="text-align: right; float: left; width: 90px; margin-right: 10px;"
												id="daily_production_newOrder" ng-model="item.order_qty"
												class=" form-control daily_production_newOrder setmargin">
										<!-- 	<i class="fa fa-info-circle tooltiptext image2"
												ng-click="showShopwiseOrderDetails($index,item)"
												data-toggle="tooltip" title="order info"
												ng-disabled="disable_all"></i> -->
										</div>
									</td>
									<td style="width: 80px; !important"><div>
											<input type="text" ng-model="item.prod_qty" select-on-click
												id="prod_qty" maxlength="${15+settings['decimalPlace']+1}"
												class="daily_production_totalProduction " valid-number
												ng-disabled="disable_all" row-delete="removeItem($index)"
												row-save="save_data(event)">
										</div></td>
									<td style="width: 85px;">
										<div>
											<input type="text"
												style="text-align: right; float: left; width: 100%; margin-right: 10px;"
												ng-disabled="true" id="damageqty" ng-model="item.damageqty">
											<!-- <i class="fa fa-pencil img2" aria-hidden="true" ng-click="editDamageQty($index,item)" ng-disabled="disable_all" ></i> -->
										</div>
									</td>
									<td style="width: 80px;">
										<div>
											<input type="text" class="setmargin"
												style="text-align: right; float: left; width: 72px; margin-right: 10px;"
												ng-disabled="true" value="{{setTotalCost(item)}}"
												id="setTotalCost"><i
												class="fa fa-info-circle tooltiptext"
												ng-click="showCostDataDetails($index,item)"
												data-toggle="tooltip" title="cost info"></i>
										</div>
									</td>





									<td style="width: 80px"><div>
											<input type="text" ng-model="item.remarks"
												class="daily_production_remarksItm"
												ng-disabled="disable_all" row-add="addItem($index)"
												row-delete="removeItem($index)" row-save="save_data(event)">
										</div></td>
							<!-- 		<td><i class="fa fa-pencil-square-o fa-lg"
										ng-click="editBomData($index,item);" aria-hidden="false"
										ng-disabled="disable_all"></i></td>
									<td style="width: 30px" ng-hide="true"><a href
										ng:click="removeItem($index)" class="btn btn-small"><i
											class="fa fa-minus " ng-disabled="disable_all"></i> </a></td> -->
								</tr>
								<%-- 		<tr>

								<td colspan="8" class="font-style"><label
									class="col-sm-4 pull-right">Total Stock Value:</label></td>
								<td colspan="2" class="font-style">${systemSettings.currencySymbol}
									{{total()}}</td>
								<td style="width: 30px" ng-hide="true"><a href ng-click="addItem($parent.$index)"
									class="btn btn-small"><i class="fa fa-plus"
										ng-disabled="disable_all"></i> </a></td>
							</tr> --%>
								<%-- 	<tr ng-hide="true">
		                    
		                     <td  colspan="8" class="font-style"><label class="col-sm-4  pull-right">Total Sales Value:</label></td>
		                     <td colspan="2" class="font-style" style="width:200px">
		                     ${systemSettings.currencySymbol} {{netSalesAmount() }}</td>
		                     <td ng-hide="true"></td>
	                        </tr> --%>
								<%--  <tr>
		                    
		                     <td  colspan="8" class="font-style"><label class="col-sm-4  pull-right">Total Damage Value:</label></td>
		                     <td colspan="2" class="font-style" style="width:200px">
		                     ${systemSettings.currencySymbol} {{netDamageAmount() }}</td>
		                     <td ng-hide="true"></td>
	                        </tr> --%>

							</tbody>
							<tfoot></tfoot>
						</table>
					</div>
				</div>
				<div class="form-group daily_prdct_remarks col-sm-8"
					id="form_div_remarks">
					<label for="remarks" class="col-sm-1 control-label"><spring:message
							code="stockin.remarks"></spring:message> <i class="text-danger"></i></label>
					<div class="col-sm-11">
						<div class="input-group">
							<textarea rows="4" cols="20" class="form-control" name="remarks"
								id="remarks" ng-model="formData.remarks"
								ng-disabled="disable_all" placeholder=""
								row-save="save_data(event)"></textarea>
						</div>
					</div>
				</div>

			</div>
		</div>
	</form>
</div>
<div class="modal fade" id="orderData" role="dialog">
	<div class="modal-dialog modal-md">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<div ng-hide="succ_alertMessageStatusModal" class="alert-box"
					id="succ_alertMessageId">{{ succ_alertMeaasgeModal }}</div>
				<div ng-hide="err_alertMessageStatusModal" class="erroralert-box"
					id="err_alertMessageId">{{ err_alertMeaasgeModal }}</div>
				<!-- <h4 class="modal-title">Edit Bom And Cost</h4> -->
				<h4 class="modal-title">{{selectedStkItemName}}</h4>
				<!-- <p>{{selectedStkItemName}}</p> -->
			</div>
			<div class="modal-body">
				<jsp:directive.include file="../production/bomedit.jsp" />
			</div>

			<div class="modal-footer">

				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

				<button class="btn  btn-sm btn-primary save_button savebtn"
					type="button" name="btnUpdte" ng-click="updateBomData();"
					id="btnUpdte" ng-disabled="disable_all"
					data-target="#importDataModal" ng-show="!disable_all">
					<i class="fa fa-floppy-o" aria-hidden="true"></i> Update
				</button>
				<!-- <button class="btn  btn-sm btn-primary save_button savebtn" type="button" name="btnSave" id="btnSave" ng-click="fun_save_form()" style="display: block;">
			<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
		</button> -->

			</div>
		</div>

	</div>
</div>


<div class="modal fade" id="costDataSplit" role="dialog">
	<div class="modal-dialog modal-md" style="width: 355px !important;">
		<!-- Modal content-->
		<div class="modal-content" style="width: 100%; float: left;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Cost Details</h4>
			</div>
			<div class="modal-body" style="width: 100%; float: left;">

				<div class="model_form_div">
					<div class="modal_box_div">
						<div class="label_div">
							<label>MATERIAL COST </label>
						</div>
						<div class="input_txt_box">
							<input type="text" ng-model="materialCost1"
								style="text-align: right" maxlength="15" ng-disabled="true"
								valid-number ng-disabled="true">
						</div>
					</div>
					<div class="modal_box_div">
						<div class="label_div">
							<label>OTHER COST </label>
						</div>
						<div class="input_txt_box">
							<input type="text" ng-model="otherCost1"
								style="text-align: right" maxlength="15" ng-disabled="true"
								valid-number ng-disabled="true">
						</div>
					</div>
					<div class="modal_box_div">
						<div class="label_div">
							<label>TOTAL COST</label>
						</div>
						<div class="input_txt_box">
							<input type="text" style="text-align: right" ng-disabled="true"
								ng-model="showTotalCost" id="setTotalCost">
						</div>
					</div>
				</div>
			</div>


		</div>

	</div>
</div>

<!--Damage Qty Pop up  -->

<div class="modal fade" id="damageQtyPopup" role="dialog">
	<div class="modal-dialog modal-md" style="width: 355px !important;">
		<!-- Modal content-->
		<div class="modal-content" style="width: 100%; float: left;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Add/Edit Damage Qty</h4>
			</div>
			<div class="modal-body" style="width: 100%; float: left;">

				<div class="model_form_div">
					<div class="modal_box_div">
						<div class="label_div">
							<label>DAMAGE Qty </label>
						</div>
						<div class="input_txt_box">
							<input type="text" ng-model="damageqty" style="text-align: right"
								maxlength="${15+settings['decimalPlace']+1}" valid-number
								id="damageqty1" ng-change="addDamageQty();">
						</div>
					</div>


				</div>
			</div>
			<div class="modal-footer">

				<button type="button" class="btn btn-success" data-dismiss="modal">OK</button>


			</div>

		</div>

	</div>
</div>

<!-- ShopWise Order Data pop Up -->

<div class="modal fade" id="orderDataSplit" role="dialog">
	<div class="modal-dialog modal-md" style="width: 500px !important;">
		<!-- Modal content-->
		<div class="modal-content" style="width: 100%; float: left;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Order Details</h4>
			</div>
			<div class="modal-body" style="width: 100%; float: left;">

				<div class="model_form_div " ng-repeat="order in orderSplitData ">
					<div class="modal_box_div ">
						<div class="label_div1 ">
							<label>{{order.customer_name}} </label>
						</div>
						<div class="input_txt_box ">
							<input type="text" ng-model="order.orderqty"
								style="text-align: right" maxlength="15" ng-disabled="true"
								valid-number ng-disabled="true">
						</div>
					</div>

				</div>
				<div class="model_form_div ">
					<div class="modal_box_div ">
						<div class="label_div1">
							<label>PREVIOUS BALANCE</label>
						</div>
						<div class="input_txt_box ">
							<input type="text" ng-model="previousBalance"
								style="text-align: right" maxlength="15" ng-disabled="true"
								valid-number ng-disabled="true">
						</div>
					</div>

				</div>
			</div>


		</div>

	</div>
</div>
