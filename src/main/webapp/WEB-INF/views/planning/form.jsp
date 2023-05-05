<div class="" id="item_category_form_div" ng-show="show_form">
	<form class="form-horizontal" id="item_category_form">
		<div class="">



			<div class="form-group" id="form_div_name" ng-show="show_addForm">
				<label for="name" class="col-sm-2 control-label code-lbl-font-size"
					id="ordernumber"> Order No: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger"></i>
				</label>

				<div class="col-sm-2">
					<div class="input-group ordno">
						<input type="text" class="form-control required  " name="name"
							id="name" ng-model="PRDNO" ng-disabled="true" maxlength="50"
							placeholder=""> <span class="input-group-addon"
							id="form_div_name_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
					</div>
				</div>
				<button type="button" class="btn btn-primary btn_status"
					ng-show="finalizebttn" id="btn_finalize"
					ng-click="Finalize($event)" style="float: right;">FINALIZE</button>
			</div>



			<div class="form-group" id="form_div_name" ng-show="show_addForm">
				<label for="name" class="col-sm-2 control-label "> Order
					Date: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger"></i>
				</label>

				<div class="col-sm-2">
					<div class="input-group">
						<div class="right-inner-addon">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control required  " name="name" id="name"
								ng-model="selectedDate" min="minDate" ng-disabled="false" maxlength="50"
								placeholder="" daterange-picker>

						</div>



						<span class="input-group-addon" id="form_div_name_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
					</div>
				</div>
				<div ng-show="show_addForm">

					<label for="name" class="col-sm-2 control-label "> Order
						Time: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger"></i>
					</label>

					<div class="col-sm-2">

						<div class="right-inner-addon" moment-picker="Time"
							format="{{filterTimes1}}">
							<i class="fa " id="calender_icon" style="left: 81%; z-index: 6;"></i>
							<input class="form-control" placeholder="Select a time"
								ng-model="Time" ng-disabled="true">
						</div>


					</div>
				</div>



			</div>



			<div class="pull-right printdiv" style="margin-right: 16px;"
				id="summaryBttn">
				<button class="btn  btn-sm btn-info" type="button"
					style="margin-right: 10px;font-size: 15px" name="btnTools" data-toggle="dropdown"
					id="btnTools" ng-disabled="disable_btn">
					<i class="fa fa-wrench" aria-hidden="false"></i> Print
				</button>
				<div class="dropdown-menu tool_btn_drpdwn_menu">
					<ul>

						<li><a class="dropdown-item" href="#" ng-click="print();">Daily
								Order</a></li>


						<li><a class="dropdown-item" ng-click="printRaw();" href="#">Raw
								Material</a></li>
					</ul>
				</div>
			</div>




			<div class="form-group" id="form_div_name">
				<label for="name" class="col-sm-2 control-label "> Delivery
					Date: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger">*</i>
				</label>

				<div class="col-sm-2">
					<div class="input-group">
						<div class="right-inner-addon">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control  required" daterange-picker
								name="create_date" id="create_date" ng-model="closing_date"
								placeholder="" ng-change="orderedData()">
						</div>

						<span class="input-group-addon" id="form_div_name_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
					</div>
				</div>
				<div id="form_div_time_slot">

					<label for="name" class="col-sm-2  control-label"
						ng-show="show_addForm"> Delivery Time: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger">*</i>
					</label>

					<div class="col-sm-2" ng-show="show_addForm">


						<div class="input-group col-sm-12">

							<select class="form-control  " id="time_slot" name="time_slot"
								ng-disabled="disable_all"
								ng-options="v.id as v.name for v in timeSlot"
								ng-model="time_slot_id""></select> <span
								class="input-group-addon" id="form_div_time_slot_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Customer cannot be blank"></i></span>
						</div>



					</div>
				</div>

			</div>






			<div ng-show="show_addForm">
				<div class="form-group" id="form_div_shop">
					<label for="name" class="col-sm-2 control-label"
						style="width: 177px;"> Type Of Order<!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger"></i>
					</label>



					<div class="radioValue">

						<md-radio-group ng-model="slctypreq" ng-disabled="disable_all"
							ng-click="clearfields();">
						<div class="col-sm-1">
							<div class="input-group radio-font">
								<md-radio-button value="2" class="md-primary">Shops</md-radio-button>
							</div>
						</div>

						<div class="col-sm-1" style="width: 123px;">
							<div class="input-group radio-font">
								<md-radio-button value="1">Customers </md-radio-button>
							</div>
						</div>

						<!-- 
						//commenting departments gana new requirements
						<div class="col-sm-1">
							<div class="input-group radio-font">
								<md-radio-button value="0">Departments </md-radio-button>
							</div>
						</div> -->

						</md-radio-group>

					</div>
				</div>

				<div class="form-group" id="form_div_shop" ng-show="slctypreq==2">
					<label for="name" class="col-sm-2 control-label "> Order
						From: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger">*</i>
					</label>

					<div class="col-sm-3">
						<div class="input-group">

							<select class="form-control reper_select_box" id="companyId"
								name="select_report" ng-disabled="disable_all"
								ng-options="v.code as v.name for v in shops"
								ng-model="companyId" ng-change="getShopData()"></select> <span
								class="input-group-addon" id="form_div_shop_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Shop cannot be blank"></i></span>
						</div>
					</div>

				</div>
				<div class="form-group" id="form_div_cust" ng-show="slctypreq==1">
					<label for="name" class="col-sm-2 control-label "> Order
						From: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger">*</i>
					</label>

					<div class="col-sm-3">
						<div class="input-group">

							<select class="form-control " id="customerIds" name="customerIds"
								ng-disabled="disable_all"
								ng-options="v.id as v.name for v in customerIds"
								ng-model="customerIds1" ng-change="getCustomerData1()"></select>


							<span class="input-group-addon" id="form_div_cust_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Customer cannot be blank"></i></span>
						</div>
					</div>

				</div>

				<div class="form-group" id="form_div_cust" ng-show="slctypreq==0">

					<label for="name" class="col-sm-2 control-label">
						Department: <i class="text-danger">*</i>
					</label>

					<div class="col-sm-3">

						<div class="input-group">
							<select
								class="form-control  ng-pristine ng-valid ng-empty ng-touched"
								id="deptIds" name="deptIds" ng-disabled="disable_all"
								ng-options="v.id as v.name for v in departmentList"
								ng-model="departmentIds" ng-change="getDepartmentData()"
								aria-invalid="false" style=""><option value="?"
									selected="selected"></option></select> <span class="input-group-addon"
								id="form_div_cust_error" style="display: none;"> <i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Customer cannot be blank"></i>
							</span>
						</div>
					</div>

				</div>


				<div class="form-group" id="form_div_customer_type"
					ng-show="slctypreq==1">
					<label for="customerTypes" class="col-sm-2 control-label ">
						Customer Type: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger">*</i>
					</label>

					<div class="col-sm-3">
						<select class="form-control " id="customerTypes"
							name="customerTypes" ng-disabled="disable_all"
							ng-options="v.id as v.code for v in customerTypes"
							ng-model="customerType"></select> <span
							class="input-group-addon" id="form_div_cust_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="Customer cannot be blank"></i></span>
					</div>

				</div>




				<div class="form-group" id="form_div_shop"
					ng-show="slctypreq==1 && customerIds1==''">
					<label for="name" class="col-sm-2 control-label "> Code: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger">*</i>
					</label>

					<div class="col-sm-3">
						<div class="input-group">
							<input type="text" class="form-control required  " name="name"
								ng-change="isCodeExistisCust(cust_code)"
								ng-disabled="disable_all" id="cust_code" capitalize
								ng-model="cust_code" maxlength="50" placeholder=""> <span
								class="input-group-addon" id="form_div_name_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>

						</div>



					</div>
					<div class="col-sm-4">

						<span ng-bind="existing_code" class="existing_code_lbl"
							ng-hide="hide_code_existing_er"></span>
					</div>

				</div>














				<div class="form-group" id="form_div_shop" ng-show="slctypreq==1">
					<label for="name" class="col-sm-2 control-label "> Name: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger">*</i>
					</label>

					<div class="col-sm-5">
						<div class="input-group">
							<input type="text" class="form-control required  " name="name"
								ng-disabled="disable_all" id="name" ng-model="custname"
								maxlength="50" placeholder=""> <span
								class="input-group-addon" id="form_div_name_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>

						</div>
					</div>

				</div>




				<div class="form-group" id="form_div_shop" ng-show="slctypreq==1">
					<label for="name" class="col-sm-2 control-label "> Address:
						<!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger">*</i>
					</label>

					<div class="col-sm-5">
						<div class="input-group">
							<textarea rows="4" cols="20" class="form-control" name="remarks"
								ng-disabled="disable_all" id="remarks" ng-model="address"
								ng-disabled="disable_all" placeholder=""></textarea>
						</div>
					</div>

				</div>





				<div class="form-group" id="form_div_shop" ng-show="slctypreq==1">
					<label for="name" class="col-sm-2 control-label "> Contact
						1: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger">*</i>
					</label>

					<div class="col-sm-3">
						<div class="input-group">
							<input type="text" class="form-control required  " name="name"
								ng-disabled="disable_all" valid-number id="name"
								ng-model="contact1" maxlength="15" placeholder=""> <span
								class="input-group-addon" id="form_div_name_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
						</div>
					</div>

				</div>





				<div class="form-group" id="form_div_shop" ng-show="slctypreq==1">
					<label for="name" class="col-sm-2 control-label "> Contact
						2: <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger"></i>
					</label>

					<div class="col-sm-3">
						<div class="input-group">
							<input type="text" class="form-control required  " name="name"
								ng-disabled="disable_all" valid-number id="name"
								ng-model="contact2" maxlength="15" placeholder=""> <span
								class="input-group-addon" id="form_div_name_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>

						</div>
					</div>

				</div>




			</div>



			<div class="form-group" id="form_div_cat" ng-hide="show_addForm">


				<div>
					<label for="name" class="col-sm-2 control-label "> Item
						Category: <i class="text-danger">*</i>
					</label>

					<div class="col-sm-3">
						<div class="input-group">
							<select class="form-control" id="item_category_id"
								name="item_category_id"
								ng-options="v.id as v.name for v in ItemCtgry"
								ng-model="item_category_id" aria-invalid="false" style=""
								ng-change="filterStkitm()"></select> <span
								class="input-group-addon" id="form_div_cat_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Category cannot be blank"></i></span>
						</div>
					</div>
				</div>


			</div>

			<div class="selectOrderType" ng-hide="show_addForm">
				<label for="name" class="col-sm-2 control-label "> Order
					Type: <i class="text-danger">*</i>
				</label>

				<div class="col-sm-3">
					<div class="input-group">

						<select class="form-control" id="order_type_new"
							name="order_type_new"
							ng-options="v.id as v.name for v in versions"
							ng-model="shoporder" aria-invalid="false" style=""
							ng-change="orderedData();"></select> <span
							class="input-group-addon" id="form_div_cat_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="Category cannot be blank"></i></span>
					</div>
				</div>
			</div>

			<div class="form-group" id="form_div_items_tbl" style="margin: 1px;"
				ng-show="show_addForm">
				<div class="cmmn_maain_tbl">
					<table class="table table-bordered" id="items_table">
						<thead>
							<tr class="active">
								<th>#</th>
								<th style="text-align: center">ITEM CODE/ITEM NAME</th>
								<th style="text-align: center">UNIT</th>
								<th style="text-align: center">CURRENT BALANCE</th>
							<!-- 	<th style="text-align: center">ADJUST</th> -->
								<th style="text-align: center">QUANTITY</th>
								<th ng-hide="true" style="text-align: center">DATE OF ORDER</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<tr ng:repeat="item in customer_orders.items">
								<td style="font: bold;">{{$index+1}}</td>
								<td ng-click="tableClicked($index)" style=""><div
										style="float: left; width: 25%; padding-right: 4px;">
										<input type="text" ng:model="item.stock_item_code"
											id="stock_item_code" name="stock_item_code" ng:required
											class="input-mini form-control" ng-disabled="disable_all"
											autocompete-text>
									</div>
									<div style="display: inline-block; width: 60%;">
										<input type="hidden" ng-model="item.stock_item_id"
											id="stock_item_id" name="stock_item_id"> <input
											type="text" class="form-control " ng-disabled="true"
											style="height: 35px;" ng-model="item.stock_item_name"
											id="stock_item_name" name="stock_item_name">
									</div> <i
									class="fa fa-pencil-square-o fa-lg form-control col-sm-1 editord"
									ng-click="editOrderData($index);" ng-disabled="disable_all"
									aria-hidden="false"
									ng-class="{greenColor: item.getColor == true}"></i></td>


								<td width="100px;"><input type="text" id="uomcode"
									ng-model="item.uomcode" name="uomcode" ng-disabled="true"
									class="input-mini form-control"></td>
								<td width="100px;"><input type="text" id="balanceqty"
									ng-disabled="true" ng-model="item.balanceqty" name="balanceqty"
									valid-number class="input-mini form-control"></td>
								<%-- <c:if test="${(dailyprodview==0)?true:false }">
									
								<td width="100px;"><input type="text" id="adjustqty"
									ng-model="item.adjustqty" name="adjustqty" valid-number 
									class="input-mini form-control"  select-on-click style="text-align: right;"
									ng-disabled="!finalizebttn"></td>
									</c:if> --%>

<!-- 
								<td width="100px;"><input type="text" id="adjustqty"
									ng-model="item.adjustqty" name="adjustqty" valid-number
									class="input-mini form-control" select-on-click
									style="text-align: right;"
									ng-disabled="makeEnable(item.is_adjst)"></td> -->



								<td width="150px;"><input type="text"
									maxlength="${10+settings['decimalPlace']+1}"
									ng-disabled="disable_all" ng-model="item.quantity" id="qty"
									row-add="addItem($index)"
									row-delete="removeItem($event,$index)" row-save="saveData()"
									select-on-click valid-number style="text-align: right;"
									class="input-mini form-control"></td>
								<td width="150px;" ng-hide="true"><input type="text"
									class="form-control required  " name="name" id="name"
									ng-model="item.dtlorder_date" ng-disabled="true" maxlength="50"
									placeholder=""></td>
								<td><a href ng:click="removeItem($event,$index)"
									ng-disabled="disable_all" class="btn btn-small"><i
										class="fa fa-minus "></i> </a></td>
							</tr>
							<tr>

								<td colspan="5"></td>
								<td><a href ng:click="addItem()" ng-disabled="disable_all"
									class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
							</tr>

						</tbody>
						<tfoot></tfoot>
					</table>
				</div>
			</div>




			<div id="div_table_items " style="border: 5px solid lightgrey;"
				ng-hide="show_addForm">

				<div style="border: thin;">
					<h4
						style="margin: initial; padding-top: 2px; background-color: lightgrey;">FINISHED
						GOODS</h4>

					<table class="table table-striped" style="border: thin;" id="summaryDataTable"
						ng-show="orderTotal">

						<tbody ng-repeat="item in stkfilterData ">
							<tr
								ng-show=" item.orderfrom !=stkfilterData[$index-1].orderfrom  ">
								<td colspan="3"><h5>{{item.orderfrom}}</h5></td>
							</tr>
							<tr
								ng-show=" item.orderfrom !=stkfilterData[$index-1].orderfrom  ">
								<td></td>
								<td style="width: 400px;"><h5>ITEM CODE/ITEM NAME</h5></td>
								<td><h5>QUANTITY</h5></td>
							</tr>
							<tr>
								<td style="font: bold;"
									ng-show="item.item_category_id == item_category_id || item_category_id == 0   "></td>
								<td
									ng-show="item.item_category_id == item_category_id || item_category_id == 0   ">{{item.stock_item_name}}</td>

								<td
									ng-show="item.item_category_id == item_category_id || item_category_id == 0   ">{{item.qty}}</td>
							</tr>
						</tbody>

						<tfoot ng-show="orderTotal">
							<tr>
								<td colspan="3"><h5>SUMMARY</h5></td>
							</tr>

							<tr>
								<td></td>
								<td style="width: 400px;"><h5>ITEM
										CODE/ITEM NAME</h5></td>
								<td><h5>TOTAL</h5></td>
							</tr>

							<tr ng-repeat="item1 in orderitemtotal  ">

								<td style="font: bold;"
									ng-show="item1.item_category_id == item_category_id || item_category_id == 0   "></td>
								<td 
									ng-show="item1.item_category_id == item_category_id || item_category_id == 0   ">{{item1.stock_item_name}}</td>

								<td 
									ng-show="item1.item_category_id == item_category_id || item_category_id == 0   ">{{item1.qty}}</td>
							</tr>
						</tfoot>
						<p ng-hide="orderTotal" style="margin: 0 7px 5px;">NO ITEM
							AVAILABLE</p>
					</table>

				</div>
			</div>







			<div class="form-group " id="form_div_remarks" ng-show="show_addForm"
				style="margin-top: 12px;">
				<label for="remarks" class="col-sm-1 control-label"><spring:message
						code="stockin.remarks"></spring:message> <i class="text-danger"></i></label>
				<div class="col-sm-11">
					<div class="input-group">
						<textarea rows="4" cols="20" class="form-control" name="remarks"
							id="remarks" ng-model="remarks" ng-disabled="disable_all"
							placeholder=""></textarea>
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
				<h4 class="modal-title">Add Remarks</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" id="item_category_form">
					<div class="">
						<div class="form-group">




							<div class="form-group " id="form_div_remarks">
								<label for="remarks" class="col-sm-2 control-label"><spring:message
										code="stockin.remarks"></spring:message> <i
									class="text-danger"></i></label>
								<div class="col-sm-9">
									<div class="input-group">
										<textarea rows="4" cols="20" class="form-control"
											name="remarks" id="remarks" ng-model="dtlremarks"
											ng-disabled="disable_all" placeholder=""></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>

			<div class="modal-footer">

				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

				<button class="btn  btn-sm btn-primary save_button savebtn"
					type="button" name="btnSave" ng-click="Addremarks();" id="btnSave"
					data-dismiss="modal" data-target="#importDataModal">
					<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
				</button>


			</div>
		</div>

	</div>
</div>
