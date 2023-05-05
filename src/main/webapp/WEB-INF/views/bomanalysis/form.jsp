<div class="" id="bomanalysis_form_div">
	<form class="form-horizontal" id="bomanalysis_form">
		<div class="">

			<div class="col-sm-12">
				<div class="col-sm-6" id="form_div_scheduledate_planning">
				<label for="department"
						class="col-sm-3  control-label dprt_ment_label  ">
						Date Range </label>
					<div class="col-sm-4">		
					
						<div class="right-inner-addon" id="form_div_scheduledate">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control ng-valid ng-touched ng-not-empty ng-dirty ng-valid-parse"
								daterange-picker="" name="start_date" id="'myId2'"
								ng-model="startDate" placeholder=""
								highlight-days="highlightDays" ng-change="oneDaySelectionOnly()"
								aria-invalid="false" style="">
						</div>
					</div>
					
					<div class="col-sm-4">
						<div class="right-inner-addon" id="form_div_scheduledate">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control ng-valid ng-touched ng-not-empty ng-dirty ng-valid-parse"
								daterange-picker="" name="start_date" id="'myId2'"
								ng-model="endDate" placeholder="" highlight-days="highlightDays"
								ng-change="oneDaySelectionOnly()" aria-invalid="false" style="">
						</div>
					</div>
				</div>


				<div class="col-sm-5 form-group prod_date delevery_date_div"
					id="form_div_department">
					<label for="department"
						class="col-sm-3  control-label dprt_ment_label department ">
						Department: </label>

					<div
						class="col-sm-7 production_process_main_tab_div_input dprt_ment_label">
						<div class="input-group">

							<select
								class="form-control ng-pristine ng-valid ng-empty ng-touched"
								id="department" name="department" ng-disabled="disable_all"
								ng-options="v.id as v.name for v in departments"
								ng-model="department_inBom" ng-change="oneDaySelectionOnly()"
								aria-invalid="false"></select> <span
								class="input-group-addon" id="form_div_department_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Department cannot be blank"></i></span>
						</div>
					</div>

				</div>


				<div class="col-sm-1 pull-right print_btn" id="div_finlize_print"
					ng-show="itemsList.length">
					<button class="btn  btn-sm btn-info" type="button"
								name="btnTools" data-toggle="dropdown" id="btnTools">
								<i class="fa fa-wrench" aria-hidden="false"></i> Export
							</button>
							<div class="dropdown-menu tool_btn_drpdwn_menu">
								<ul>
									<li><a class="dropdown-item" id="pdfView">PDF</a></li>
									 <li><a class="dropdown-item" id="excelView">EXCEL</a></li> 
								</ul>
							</div>
				</div>
			</div>


			<%-- <div class="form-group" id="form_div_date">
				
				<div class="col-sm-6">
				<label for="startdate" class="col-sm-1 control-label lb">DATE:<i
					class="text-danger">*</i></label>
					<!-- <div class="input-group col-sm-10"> -->
						<div class="right-inner-addon" id="seleted_date">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								ng-change="DatewiseList()" class="form-control required  "
								name="name" id="name" ng-model="selectedDate"
								ng-disabled="disable_all" maxlength="50" placeholder=""
								daterange-picker>
						</div>



						<span class="input-group-addon" id="form_div_name_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
					<!-- </div> -->
				</div>
				
		
				
				
				 <div class="form-group prod_date delevery_date_div" id="form_div_department" >
					<label for="department" class="  control-label dprt_ment_label  "> Department:
					</label>

					<div class="col-sm-6 production_process_main_tab_div_input dprt_ment_label">
						<div class="input-group">

							<select class="form-control" id="department"
								name="department" ng-disabled="disable_all"
								ng-options="v.id as v.name for v in departments"
								ng-model="department_inBom" ng-change="DatewiseList()"></select>
							

							
							<span class="input-group-addon" id="form_div_department_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Department cannot be blank"></i></span>
						</div>
					</div>

				</div>
				
			</div> --%>


			<!-- 
				<div class="col-sm-3">
				
					<div class="box1">
			 <circle-spinner ng-show="prograssing"></circle-spinner>

						<multiple-date-picker calendar-id="'myId2'" ng-model="selectedDays3"
						day-click="oneDaySelectionOnly" highlight-days="highlightDays"/>

					</div>
				</div> -->




			<!-- <div  ng-repeat="(key, value) in Customers | groupBy: 'department'">
			<table class="table table-bordered">
			<thead>
			<tr>{{ key }}</tr>
			<tr>
			<th>#</th>
			<th>id</th>
			<th>name</th>
			<th>country</th>
			</thead>
			
			
			<tbody>
			<tr  ng:repeat="Customers in value">
			<td>{{ $index+1}}</td>
							<td><input type="text" ng-model="Customers.CustomerId"></td>
							<td><input type="text" ng:model="Customers.Name"
								id="opening_stock" name="opening_stock"
								class="input-mini form-control algn_rgt" ng-disabled="true"></td>
							<td><input type="text" ng:model="Customers.Country"
								id="opening_stock" name="opening_stock"
								class="input-mini form-control algn_rgt" ng-disabled="true"></td>
			</tr>
			
			
			</tbody>
			
			</table>
			
			</div> -->





			<!-- <div ng-repeat="(key, value) in itemsList | groupBy: 'department_name'"> -->
				<div>
				<table class="table table-bordered" id="bomAnalysis_table">
					<thead>
						<!-- <tr>
							<b>{{ key }}</b>
						</tr> -->
						<tr>
							<th ng-hide="true">stock_item_id</th>
							<th>#</th>
							<th>Item</th>
							<th>Opening Stock</th>
							<th>Stock In</th>
							<th>Total Stock</th>
							<th>BOM consumption</th>
							<th>Closing Stock</th>
							<th style="display: none;"></th>
						</tr>
					</thead>
					
					<tbody>
						<tr ng-if="itemsList.length === 0">
							<td colspan="7" style="text-align: left;">No data available
							</td>
						</tr>
						<tr ng:repeat="itemsList in itemsList">
							<td ng-hide="true"><input type="text"
								ng-model="itemsList.stock_item_id"></td>
							<td>{{ $index+1}}</td>
							<td><input type="text" ng:model="itemsList.stock_item_name"
								id="stock_item_name" name="stock_item_name"
								class="input-mini form-control algn_rgt" ng-disabled="true"></td>
							<td><input type="text" ng:model="itemsList.opening_stock"
								id="opening_stock" name="opening_stock"
								class="input-mini form-control algn_rgt" ng-disabled="true"></td>
							<td><input type="text" ng:model="itemsList.stock_in"
								id="stock_in" name="stock_in"
								class="input-mini form-control algn_rgt" ng-disabled="true"></td>
							<td><input type="text" ng:model="itemsList.total" id="total"
								name="total" class="input-mini form-control algn_rgt"
								ng-disabled="true"></td>
							<td><input type="text" ng:model="itemsList.bom_consumption"
								id="bom_consumption" name="bom_consumption"
								class="input-mini form-control algn_rgt" ng-disabled="true">
								<i class="fa fa-info-circle  "
								ng-click="bomConsumptionDetails(itemsList)" data-toggle=""
								title="" ng-disabled="disable_all" role="button" tabindex="0"
								aria-disabled="true" disabled="disabled"></i></td>
							<td><input type="text" ng:model="itemsList.closing_stock"
								id="closing_stock" name="closing_stock"
								class="input-mini form-control algn_rgt" ng-disabled="true">
							</td>
							<td style="display: none;"><input type="text"
								id="remarsText" name="rem"
								class="input-mini form-control algn_rgt" ng-model="remarks"></td>
						</tr>
					</tbody>
				</table>


			</div>
			<!-- <div id="updatediv">
			<button ng-click="updateData()" ng-show="showbtn()"
				id="update" ng-disabled="stockregData" class="btn btn-success">update</button>
			</div> -->
			<!-- <div ng-hide="itemsList.length">No items found</div> -->
		</div>
	</form>
</div>



<!-- Item wise Bom Data pop Up -->

<div class="modal fade" id="orderDataSplit" role="dialog">
	<div class="modal-dialog modal-md" style="width: 500px !important;">
		<!-- Modal content-->
		<div class="modal-content" style="width: 100%; float: left;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Item Details</h4>
			</div>
			<div class="modal-body" style="width: 100%; float: left;">


				<div ng-repeat="(key, value) in bomData | groupBy: 'bom_item_id'">

					<table class="table table-bordered" id="{{ key }}_table">
						<thead>
							<tr>
								<th colspan="5" style="text-align: left">{{itmName}}</th>
							</tr>
							<tr>
								<th>#</th>
								<th>Product</th>
								<th>Product Qty</th>
								<th>BOM consumption</th>
								<th>Status</th>


							</tr>
						</thead>
						<tbody>


							<tr ng:repeat="bomData in value">

								<td>{{ $index+1}}</td>
								<!-- <td><input type="text" ng:model="bomData.stock_item_name"
								id="stock_item_name" name="stock_item_name"
								class="input-mini form-control algn_rgt" ng-disabled="true"></td>
							<td><input type="text" ng:model="bomData.prod_qty"
								id="prod_qty" name="prod_qty"
								class="input-mini form-control algn_rgt" ng-disabled="true"></td> -->

								<td>{{bomData.stock_item_name}}</td>
								<td class="algn_rgt">{{bomData.prod_qty}}</td>
								<td class="algn_rgt">{{bomData.bom_quantity}}</td>
								
								<td ng-if="bomData.is_deleted == '0' " class="algn_rgt bomActiveStaus">Current</td>
								<td ng-if="bomData.is_deleted == '1' " class="algn_rgt bomInActiveStaus">Deleted</td>
							</tr>
							<tr>
								<td colspan="3"></td>
								<td class="algn_rgt">{{totalBom}}</td>
								<td></td>
							</tr>

						</tbody>
					</table>


				</div>






			</div>


		</div>

	</div>
</div>






<!-- Item wise Bom Remarks Data pop Up -->

<div class="modal fade" id="itemsRemarks" role="dialog">
	<div class="modal-dialog modal-md" style="width: 500px !important;">
		<!-- Modal content-->
		<div class="modal-content" style="width: 100%; float: left;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Actual Stock Remarks</h4>
			</div>
			<div class="modal-body" style="width: 100%; float: left;">



				<!-- <input type="text" id="remarks" name="remarks" class="input-mini form-control algn_rgt"
								ng-change="copyRemarksToHidanTxtBox($event)"  > -->

				<input type="text" ng-model="remarksPoUp"
					onchange="angular.element(this).scope().copyRemarksToHidanTxtBox(this)"
					class="form-control required  " name="remarksPoUp" id="remarksPoUp"
					maxlength="50">



			</div>


		</div>

	</div>
</div>









