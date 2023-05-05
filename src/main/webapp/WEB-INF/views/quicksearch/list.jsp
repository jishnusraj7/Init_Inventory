<link rel="stylesheet"
	href="<c:url value='/resources/common/css/stockitemdata.css'/>">
	
<div class=" " ng-app="stock_item_app" id="stock_item_app" ng-cloak>
	<div ng-controller="stock_item_ctrl as stock_item_ctrl">

		<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
			tabindex="-1" id="itemListsModal" class="modal fade">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button aria-hidden="true" data-dismiss="modal"
							ng-click="cancelBtn()" class="close" type="button"
							style="font-size: 15px;">X</button>
						<h4 class="modal-title">ITEM SEARCH</h4>
					</div>
					<div class="modal-body" id="itemModalBody">

						<div class="form-group item_stock_table_popup"
							id="form_div_load_items">

							<label for="itemsName" class="col-sm-2 control-label"><spring:message
									code="stockin.item_name"></spring:message> <i
								class="text-danger">*</i></label>
							<div class="row" style="padding-bottom: 20px;">
								<div class="col-sm-6">
									<%-- 							<input type="text" class="form-control" name="itemsName"
										id="itemsName" ng-model="itemsName" placeholder="Search...."
										ng-keydown="getItemData()"><span
										class="input-group-addon" id="formDivItemDataError"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="<spring:message code="stockin.error.itemData"></spring:message>"></i></span> --%>

									<!-- 	<div ng:repeat="item in invoice.items" ng-hide="item.isDeleted" ng-click="tableClicked($index)" class="item_code_name"> -->
									<div id="search_items_data">
										<div style="float: left; width: 25%; padding-right: 4px;" id="form_stock_item_data">
											<input type="text" ng:model="stock_item_code"
												id="stock_item_data" name="stock_item_code"
												class="input-mini form-control stk_item_data"
												ng-keydown="getItemData()" ng-mouseleave="getItemData()"
												style="cursor: pointer;">
										</div>
										<div style="display: inline-block; width: 75%;">
											<!-- 
											<input type="hidden" ng:model="stock_item_id"
												id="stock_item_id" name="stock_item_id"> -->
											<input type="text" ng:model="stock_item_name"
												id="stock_item_name" name="stock_item_name"
												class="input-mini form-control" ng-disabled="true">

										</div>
									</div>
								</div>
								<!-- <div class="col-sm-3" align="left">
										<button class="search_btn btn"  ng-click="getItemData()"><i class="fa fa-search" aria-hidden="true"></i></button>
										</div> -->

								<!-- </div> -->

								<%-- 	<label for="department_id" class="col-sm-2 control-label">Department</label>
								<div class="col-sm-3" align="left">
									<select class="form-control" id="department_id"
										class="required" name="department_id" required
										ng-options="v.id as v.name for v in department_data"
										ng-model="formData.department_id" ng-change="getItemData()"></select>
									<span class="input-group-addon"
										id="form_div_source_department_code_error"
										style="display: none;"><i
										class="fa fa-question-circle red-tooltip"
										data-toggle="tooltip" data-placement="bottom" title=""
										data-original-title="<spring:message code="stockin.error.depName"></spring:message>"></i></span>
									<!--  <select class="form-control" id="departmentName"  ng-model="formData.department_id">
						   <option ng-repeat="department in department_data" ng-value="department.name">{{department.name}}</option>
						   </select> -->
								</div> --%>
							</div>
							<div class="form-group" id="div_table_show">
								<table class="table table-bordered" id="itemsTable"
									ng-hide="prograssing">
									<thead>
										<tr class="active">
											<th style="width: 35px">#</th>
											<th style="width: 35px">CODE</th>
											<th style="width: 195px">ITEM NAME</th>
											<th style="width: 195px">DEPARTMENT</th>
											<th style="width: 95px">CURRENT STOCK</th>
										</tr>
									</thead>
									<tbody ng-hide="prograssing" id="itemTableBody">
										<tr ng-if="itemDataOnDepartment.length === 0">
											<td colspan="10" style="text-align: left;">No data
												available</td>
										</tr>
										<tr ng:repeat="item in itemDataOnDepartment"
											value="{{$index+1}}">
											<td>{{$index+1}}</td>
											<td class="amount algn_lft">{{item.code}}</td>
											<td>{{item.name}}</td>
											<td>{{item.department_name}}</td>
											<td class="amount algn_rgt">{{item.current_stock}}</td>
										</tr>

										<tr ng-show="showTotal">
											<td colspan="4" class="font-style"><label
												class="col-sm-12 amount algn_rgt"> TOTAL:</label></td>
											<td colspan="1" class="font-style amount algn_rgt"><b>{{currentStockTotal()}}</b></td>

										</tr>

									</tbody>
								</table>
								<circle-spinner ng-show="prograssing"></circle-spinner>
							</div>
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default rbtnClose"
							data-dismiss="modal" ng-click="cancelBtn();">CLOSE</button>

					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- common js include below -->
	<script src="<c:url value='/resources/common/js/stock_item_data.js?n=1' />"></script>
</div>