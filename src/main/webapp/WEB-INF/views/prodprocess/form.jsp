
<div class="production_process_form_div" id="tabsdemoDynamicHeight"  >
	<form class="form-horizontal frm_div_production_process" id="production_process_form">

 <!-- <md-content class="tab_prod_process production_process_main_tab_div"> -->
    <md-tabs md-dynamic-height="" md-border-bottom="" class="production_process_tab">
      <md-tab label="VERIFIED ORDERS" md-on-select="myClickEvent1()">
        <md-content class="md-padding  production_process_main_tab_div_sub">
        
        <div class="left_delivery">
        
        
	        <div class="delevery_date_div" id="form_div_delevery_date">
						<label for="delevery_date"
							class="control-label lb delevery_date">Delivery Date:<i
							class="text-danger">*</i></label>
						<div class=" production_process_main_tab_div_input">
							<div class="input-group">
								<div class="right-inner-addon"
									id="form_div_scheduledate_planning">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker name="delevery_date"
										id="delevery_date" ng-model="formData.closing_date"
										placeholder="" ng-change="totalItemOrders()" ng-disabled="disable_all"
										>
								</div>
								<span class="input-group-addon" min="0" max="99"
									number-mask="" id="form_div_scheduledate_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip"
									data-toggle="tooltip" data-placement="bottom" title=""
									data-original-title="<spring:message code="prodprocess.deliverydate.error"></spring:message>"></i></span>
	
							</div>
	
						</div>
						
					</div>
				</div>
				<div class="right_delivery">
				 <%-- <div class="prod_date delevery_date_div" id="form_div_prod_date">
					<label for="prod_date"
						class="delevery_date control-label lb">Production Date:<i
						class="text-danger">*</i></label>
					<div class="production_process_main_tab_div_input">
						<div class="input-group">
							<div class="right-inner-addon"
								id="form_div_scheduledate_planning">
								<i class="fa fa-calendar" id="calender_icon"
									style="left: 81%; z-index: 4;"></i> <input type="hidden"
									class="form-control" daterange-picker name="prod_date"
									id="prod_date" ng-model="formData.prod_date"
									placeholder=""  ng-disabled="disable_all"
									>
							</div>
							<span class="input-group-addon" min="0" max="99"
								number-mask="" id="form_div_prddate_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip"
								data-toggle="tooltip" data-placement="bottom" title=""
								data-original-title="<spring:message code="dailyproduction.proddate.error"></spring:message>"></i></span>

						</div>

					</div>
					
				</div> --%>
        
     	<button type="button" class="btn btn-success" ng-click="sendToProduction()" >SEND TO PRODUCTION</button>  
        
         <button type="button" class="btn btn-success" ng-click="printAndSendToProduction()" >PRINT</button>
        
        </div>
<!--         <div class="custome_prdt_div">
      <div class="form-group prod_date delevery_date_div" id="form_div_cust" >
					<label for="name" class="control-label delevery_date "> Customers:
						<i class="text-danger">*</i>
					</label>

					<div class="production_process_main_tab_div_input">
						<div class="input-group">

							<select class="form-control " id="cust"
								name="customerIds" ng-disabled="disable_all"
								ng-options="v.id as v.name for v in customerIds"
								ng-model="customerIds1" ng-change="totalItemOrders()"></select>
							

							<span class="input-group-addon" id="form_div_cust_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Customer cannot be blank"></i></span>
						</div>
					</div>

				</div>
				
				
					 <div class="form-group prod_date delevery_date_div" id="form_div_department" >
					<label for="department" class="  control-label dprt_ment_label  "> Department:
						 <spring:message
						code="common.name"></spring:message>  <i class="text-danger">*</i>
					</label>

					<div class="production_process_main_tab_div_input dprt_ment_label">
						<div class="input-group">

							<select class="form-control" id="department"
								name="department" ng-disabled="disable_all"
								ng-options="v.id as v.name for v in departments"
								ng-model="department_id" ng-change="totalItemOrders()"></select>
							

							
							<span class="input-group-addon" id="form_div_department_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Department cannot be blank"></i></span>
						</div>
					</div>

				</div>
				
				
				
				
				
</div> -->

<div class="form-group custome_prdt_div" id="form_div_shop" >
					<label for="name" class="col-sm-2 control-label" style="width: 143px;" > Type Of
						Order<!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger"></i>
					</label>



					<div class="radioValue">

						<md-radio-group ng-model="slctypreq" ng-disabled="disable_all"
							ng-click="loadData();">
						<div class="col-sm-1">
							<div class="input-group">
								<md-radio-button value="0" class="md-primary" >Shops</md-radio-button>



							</div>
						</div>

						<div class="col-sm-1" style="width: 123px;">
							<div class="input-group">
								<md-radio-button value="1"> Customers </md-radio-button>
							</div>
						</div>
						
						<!-- <div class="col-sm-1">
							<div class="input-group">
								<md-radio-button value="2">Departments </md-radio-button>
							</div>
						</div> -->
						
						</md-radio-group>

					</div>

				</div>
				
				<div class="form-group prod_date delevery_date_div"
				id="form_div_cust">
				<label for="name" class="control-label delevery_date ">
					Order From: <i class="text-danger">*</i>
				</label>

				<div class="production_process_main_tab_div_input">
					<div class="input-group">
							
						<select class="form-control " id="cust" name="customerIds"
							ng-disabled="disable_all" ng-model="customerIds1"
							ng-options="v.code as v.name for v in fillData"
							 ng-change="itemWithDeptChange()">
							  </option></select><span
							class="input-group-addon" id="form_div_cust_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="Customer cannot be blank"></i></span>
					</div>
				</div>

			</div>
	<!-- 	<div class="form-group col-sm-7" id="form_div_department">
		<div class="col-sm-2">
			<label for="departmentIds" class="control-label">
				Department: <i class="text-danger"></i>
			</label></div>

			<div class="col-sm-4">
				<div class="input-group">
					<select class="form-control" id="department_id" style="width: 208px;"
								name="department" ng-disabled="disable_all"
								ng-options="v.id as v.name for v in departments"
								ng-model="departments_id" ng-change="totalItemOrders()"></select>
				</div>
			</div>
		</div> -->

		<div class="table_list search_div">
			<!-- <div class="form-group">
				<label class="control-label delevery_date ">Search:</label> <input type="text" ng-model="search"
					class="form-control" id="input_prd" placeholder="Search">
			</div> -->
			
			<table class="table table-striped table-hover" id="orders_list">
				<thead>
					<tr>
						<th><input ng-model="selectAll" ng-click="toggleAll(selectAll)" type="checkbox"></input></th>
						<th ng-click="sort('order_id')">ORDER NO<span
							class="glyphicon sort-icon" ng-show="sortKey=='order_id'"
							ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
						</th>
						<th ng-click="sort('stock_item_name')">ITEM<span
							class="glyphicon sort-icon" ng-show="sortKey=='stock_item_name'"
							ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
						</th>
					<!-- 	<th ng-click="sort('department')">DEPARTMENT <span
							class="glyphicon sort-icon" ng-show="sortKey=='department'"
							ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
						</th> -->
						<th ng-click="sort('department')">SHOP <span
							class="glyphicon sort-icon" ng-show="sortKey=='shop'"
							ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
						</th> 
						<th ng-click="sort('qty')">TOTAL QTY <span
							class="glyphicon sort-icon" ng-show="sortKey=='qty'"
							ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
						</th>
						<th ng-click="sort('timeslot')">DELIVERY SLOT <span
							class="glyphicon sort-icon" ng-show="sortKey=='timeslot'"
							ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
						</th>
					</tr>
				</thead>
				<tbody>
				  <tr ng-if="itemList.length === 0"><td colspan="6"> No data available </td></tr>
					<tr ng-repeat="item in itemList|orderBy:sortKey:reverse|filter:search" >
						<td><input ng-model="item.check_val" ng-click="toggleOne(item)" type="checkbox" data-prd-id="{{ item.sale_item_id}}"></input></td>
						<td><input type= "hidden" ng-model="item.order_no" data-dtl-id="{{item.order_no}}">{{item.order_no}}</td>
						<td>{{item.stock_item_name}}</td>
					     <td>{{item.shop_code}}</td> 
					<!--     <td>{{item.department}}</td> -->
						<td><a href="" ng-click="orderDetails(item)">{{item.qty}}</a></td>
						<td>{{item.timeslot}}</td>
					</tr>				
					
				</tbody>
			</table>
       
		</div>









		</md-content>
      </md-tab>
      <md-tab label="IN PRODUCTION" md-on-select="clickOnInProductionTab()">
        <md-content class="md-padding prod_process_tab_insert_div">
        
        
        <div class="inprod_div">
        
        <div class="form-group" id="form_div_prodDate">
					<label for="startdate"
						class="control-label lb">Production Date :<i
						class="text-danger">*</i></label>
					<div class="input">
						<div class="input-group">
							<div class="right-inner-addon"
								id="form_div_production_date">
								<i class="fa fa-calendar" id="calender_icon"
									style="left: 81%; z-index: 4;"></i> <input type="text"
									class="form-control" daterange-pickerbft name="prodDate"
									id="prod_date" ng-model="prodDate"
									placeholder=""  ng-disabled="disable_all"
									>
							</div>
							<span class="input-group-addon" min="0" max="99"
								number-mask="" id="form_div_prodDate_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip"
								data-toggle="tooltip" data-placement="bottom" title=""
								data-original-title="<spring:message code="dailyproduction.proddate.error"></spring:message>"></i></span>

						</div>

					</div>
					
				</div>
				
				
				
				
				 <div class="form-group" id="form_div_time_slot">
					<label for="time_slot"
						class="control-label lb">Delivery Time :<i
						class="text-danger">*</i></label>
					<div class="">
						<div class="input-group">
							<select class="form-control  " id="time_slot"
								name="time_slot" 
								ng-disabled="disable_all"
								ng-model="time_slot_id" 
								ng-options="v.id as v.name for v in timeSlot" style="height:32px;"></select>
							<span class="input-group-addon" id="form_div_time_slot_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Customer cannot be blank"></i></span>
						</div>

					</div>
					
				</div>
				
				
				 <div class="form-group" id="form_div_in_production_department" >
					<label for="department" class=" control-label "> Department:
						 <!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger">*</i>
					</label>

					<div class="">
						<div class="input-group">

							<select class="form-control reper_select_box" id="in_production_department"
								name="department" 
								ng-disabled="disable_all"
								ng-model="department_id" 
								ng-options="v.id as v.name for v in departments"
								ng-change="onChangeSourceDepartment()"></select>							
							<span class="input-group-addon"  id="form_div_in_production_department_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip"
								data-toggle="tooltip" data-placement="bottom" title=""
								data-original-title="<spring:message code="common.departmenterror"></spring:message>"></i></span>
						</div>
					</div>

				</div>
				
				
        
        
        </div>
        <div class="inprod_button">
       	<button class="but_inprod print_button" ng-click="printMarkFinished()">PRINT</button>
         <button class="but_inprod save_button" ng-click="MarkFinished()">MARK FINISHED</button>
        </div>





		<div class="form-group" id="form_div_items_tbl">
			<circle-spinner ng-show="prograssing"></circle-spinner>
			<div class="cmmn_maain_tbl">

				<table class="table table-bordered" id="items_table">
					<thead>
						<tr class="active">
							<th>#</th>
							<th>ITEM</th>
							<th>UNIT</th>
							<!-- <th>DEPARTMENT</th> -->
							<!-- <th>DELIVERY SLOT</th> -->
							<th>PROD. QTY</th>
							<th>CURRENT STOCK</th>
							<th>TOTAL QTY</th>
							<th>DAMAGE</th>
							<th>UNIT COST</th>
							<th>REMARKS</th>
							<th></th>
							<!-- <th>EDIT BOM</th> -->
						</tr>
					</thead>
					<tbody>
						
						<tr ng:repeat="prod_item in ProductionList">
							<td>{{$index+1}}</td>
								
							<!-- <td style="width: 200px;">{{proditem.stock_item_name}}</td>	 -->
							<td ng-click="tableClicked($index)" class="item_code_name" style=" width: 30%;"><div  style="width: 100%">
							    <div style="float:left;width:25%; padding-right:4px;"><input
								type="text" ng:model="prod_item.stock_item_code" id="stock_item_code"
								maxlength="10" name="stock_item_code" ng:required
								class="input-mini form-control" autocompete-text
								ng-disabled="disable_all" ng-style="validation_Sstock_item_code">
								</div>
								<div style="display: inline-block; width:75%;"><input type="hidden" ng:model="prod_item.stock_item_id"
								id="stock_item_id" name="stock_item_id"><input
								type="text" ng:model="prod_item.stock_item_name" id="stock_item_name"
								name="stock_item_name" class="input-mini form-control"
								ng-disabled="true"></div></td>
							
							<!-- <td>{{proditem.uomcode}}</td> -->
							<td width="100px;"><input type="text" id="uomcode"
									ng-model="prod_item.uomcode" name="uomcode" ng-disabled="true"
									class="input-mini form-control"></td>
							
							<!-- <td><input type="text" id="department_name" ng-disabled="true"
									ng-model="prod_item.department_name" name="department_name"
									class="input-mini form-control">{{prod_item.department_name}}</td> -->
							
							<!--  <td>{{proditem.time_slot_name}}</td> -->
								
							<!-- <td><input type="text" ng-model="proditem.prod_qty"
								id="prod_qty"></td> -->
							<td width="100px;"><input type="text" id="produced_qty" ng-disabled="false"
									ng-model="prod_item.prod_qty" name="produced_qty" valid-number
									style=" text-align: right; " maxlength="${15+settings['decimalPlace']+1}" 
									class="input-mini form-control" select-on-click row-delete="removeItem($index)"></td>								
								
							<td class="item-table-col-width"><input type="text"
									ng:model="prod_item.currentStock"
									class="input-mini form-control algn_rgt" id="currentStock"
									name="currentStock" ng-disabled="true"
									ng-style="validation_currentStock" valid-number></td>
								
							<td style="text-align: center;">
							<div style="margin-top: 6px;"><a href=""
								ng-click="showShopwiseOrderDetails($index,prod_item)">{{prod_item.order_qty}}</a>
								</div> </td>
								
							<td style="width: 91px;"><div>
									<input type="text"
										style="text-align: right; float: left; width: 60px;padding: 4px;"
										ng-disabled="false" class="damageqty"
										ng-model="prod_item.damageqty" 
										maxlength="${15+settings['decimalPlace']+1}" valid-number
										row-delete="removeItem($index)"><!-- <i
										class="fa fa-pencil img2" aria-hidden="true"
										ng-click="editDamageQty($index,proditem)"
										ng-disabled="disable_all"></i> -->
								</div></td>
								
							<td style="width: 100px;">
								<div>
									<input type="text" class="setmargin"
										style="text-align: right; float: left; width: 58px; margin-right: 4px; padding: 4px;"
										ng-disabled="true" ng-model="prod_item.totalCost"
										id="setTotalCost"><i style="margin-top: 10px;"
										class="fa fa-info-circle tooltiptext"
										ng-click="showCostDataDetails($index,prod_item)"
										data-toggle="tooltip" title="cost info"></i>
								</div>
							</td>
							
							<td style="width: 106px;"><div>
									<input type="text" ng-model="prod_item.remarks" id="remarks"
										class="daily_production_remarksItm" ng-disabled="disable_all"
										row-add="addItem($index)" row-delete="removeItem($index)">
								</div></td>
								
							<td><a href ng:click="removeItem($index,$event)"
									ng-disabled="disable_all" class="btn btn-small"><i
										class="fa fa-minus "></i> </a></td>	
							<!-- <td><i class="fa fa-pencil-square-o fa-lg"
								ng-click="editBomData($index,proditem);" aria-hidden="false"
								ng-disabled="disable_all"></i></td> -->
						</tr>
						<tr>

								<td colspan="9"></td>
								<td><a href ng:click="addItem()" ng-disabled="disable_all"
									class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
							</tr>
					</tbody>

				</table>
			</div>
		</div>
		</md-content>
      </md-tab>

    <md-tab label="MATERIAL REQUISITION" md-on-select="myClickEvent3()" ng-if="showMaterialRequisition">

		<md-content class="md-padding"> <!-- <div class="inprod_div"> -->

		<div class="">


			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id"> <input type="hidden"
				id="stockreg_id" name="stockreg_id" value=""
				ng-model="formData.stockreg_id">
			<!-- <div class="pull-right" id="div_finlize_print">

				<input type="hidden" id="change_by" name="change_by" value="0">
				<input type="hidden" id="change_date" name="change_date"
					value="{{cur_date_finalize | date:'yyyy/MM/dd'}}">

				<button type="button"
					class="btn btn-info btn_status pull-right btn_status"
					id="btn_status" ng-click="fun_status($event)">{{statusBtnText}}</button>
			</div> -->

			<div class="pull-right" id="div_finlize_print">

				<!-- <button class="btn  btn-sm btn-primary save_button savebtn"
					type="button" name="btnSave" id="btnSave"
					ng-click="fun_save_form()">
					<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
				</button> -->

				<div class="inprod_button">
					<button class="but_inprod print_button" id="printstore1">SAVE & PRINT</button>
				</div>

			</div>

			<div class="form-group " id="form_div_ref_code">

				<label for="grn_code"
					class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="stockout.ref.code"></spring:message> <i class="text-danger"></i></label>

				<div class="col-sm-2">
					<div class="input-group">
						<input type="text" class="form-control code-font-size required"
							ng-change="isCodeExistis(formData.stockTransfeNo)"
							name="stock_transfer_no" id="ref_code" ng-disabled="disable_grn"
							placeholder="" ng-model="formData.stockTransfeNo"> <span
							class="input-group-addon" min="0" max="99" number-mask=""
							id="form_div_ref_code_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="common.error.code"></spring:message>"></i></span>
					</div>
				</div>
				<div class="col-sm-4">
					<span ng-bind="existing_code" class="existing_code_lbl"
						ng-hide="hide_code_existing_er"></span>
				</div>
			</div>




			<div class="form-group " id="form_div_stock_request_date">

				<label for="stock_transfer_date"
					class="col-sm-2 control-label code-lbl-font-size"><spring:message
						code="stockout.req.date"></spring:message> <i class="text-danger">*</i></label>

				<div class="col-sm-2">
					<div class="input-group">
						<div class="right-inner-addon">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control code-font-size required" daterange-pickerbft
								name="stock_request_date" id="stock_request_date"
								ng-model="formData.stock_request_date"
								ng-disabled="disable_all_date" placeholder="">
						</div>
						<span class="input-group-addon"
							id="form_div_stock_request_date_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockout.error.req_date"></spring:message>"></i></span>
					</div>
				</div>
			</div>

			<div class="form-group " id="form_div_deliv_date"
				ng-show="show_stockrequest_form">

				<label for="stock_transfer_date" class="col-sm-2 control-label"><spring:message
						code="stockout.dlvry.date"></spring:message> <i
					class="text-danger"></i></label>

				<div class="col-sm-2">
					<div class="input-group">
						<div class="right-inner-addon">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control" daterange-pickerbft
								name="stock_transfer_date" id="stock_transfer_date"
								ng-model="formData.stock_transfer_date"
								ng-disabled="disable_all_date" placeholder=""
								ng-change="getChangeStock()">
						</div>
					</div>
				</div>
				<div class="col-sm-4">
					<span class="existing_code_lbl" style="padding: 0px;"
						ng-hide="(formData.stock_transfer_date != formData.stock_request_date) ? formData.stock_transfer_date > formData.stock_request_date : true">must
						be greater than req date</span>
				</div>
			</div>

			<div class="form-group " id="form_div_source_code">
				<label for="department_code" class="col-sm-2 control-label"><spring:message
						code="stockout.source"></spring:message> <i class="text-danger">*</i>
				</label>
				<div class="col-sm-2">
					<div class="input-group">
						<input type="hidden" name="source_department_id"
							id="source_department_id"
							ng-model="formData.source_department_id"> <input
							type="text" class="form-control " name="source_code"
							id="source_code" ng-model="formData.source_code"
							ng-disabled="disable_all" placeholder="Code"><span
							class="input-group-addon" id="form_div_source_code_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockin.error.dep"></spring:message>"></i></span>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control "
							name="from_department_name" id="from_department_name"
							ng-model="formData.source_name" ng-disabled="true" placeholder="">
					</div>
				</div>
			</div>

			<div class="form-group " id="form_div_desti_code">
				<label for="department_code" class="col-sm-2 control-label"
					ng-show="show_stockrequest_form"><spring:message
						code="stockout.destination"></spring:message> <i
					class="text-danger">*</i></label> <label for="department_code"
					class="col-sm-2 control-label" ng-hide="show_stockrequest_form">Requested
					From<i class="text-danger">*</i>
				</label>
				<div class="col-sm-2">
					<div class="input-group">
						<input type="hidden" name="dest_department_id"
							id="dest_department_id" ng-model="formData.dest_department_id">
						<input type="text" class="form-control" name="dest_code"
							id="dest_code" ng-model="formData.dest_code"
							ng-disabled="disable_all" placeholder="Code">
					</div>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control" name="to_department_name"
							id="to_department_name" ng-model="formData.desti_name"
							ng-disabled="true" placeholder="">
					</div>
				</div>
			</div>

			<div class="form-group" id="form_div_pending">
				<label for="pending" class="control-label col-sm-2">Show Pending</label>
				<div class="col-sm-1">
					<md-checkbox type="checkbox" ng-model="formData.pending"
						ng-true-value="true" ng-false-value="false" ng-disabled="false"
						aria-label="pending"
						class="chck_box_div ng-pristine ng-valid ng-empty ng-touched"
						tabindex="-1" role="checkbox" aria-disabled="true"
						disabled="disabled" aria-checked="false" aria-invalid="false"
						style="" ng-change="showPending(formData.pending)"> <!-- <div class="md-container md-ink-ripple" md-ink-ripple=""
					md-ink-ripple-checkbox="">
					<div class="md-icon"></div>
					<div class="md-ripple-container"></div>
				</div> --> <!-- <div ng-transclude="" class="md-label"></div> --> </md-checkbox>
				</div>



			</div>






			<input type="hidden" id="cmbn_mode" value="${combineMode}">
			<c:if test="${(combineMode==0)?true:false }">
				<div class="form-group " id="form_div_items_tbl">

					<div class="cmmn_maain_tbl">
						<circle-spinner ng-show="prograssing"></circle-spinner>
						<table class="table table-bordered tableStyle" id="items_table1"
							ng-hide="prograssing">
							<tr class="active">
								<th>ID</th>
								<th>#</th>
								<th style="width: 350px;">Item Code & Name</th>
								<th>Unit</th>
								<th style="width: 135px;" ng-show="show_stockrequest_form">
									Current Stock</th>
								<th style="width: 135px;">Pending Qty</th>
								<th style="width: 135px;">Request Qty</th>
								<th ng-show="show_stockrequest_form" style="width: 135px;">Deliver
									Qty</th>

								<th ng-show="show_stockrequest_form" style="width: 135px;">Unit
									Rate</th>
								<th ng-show="show_stockrequest_form" style="width: 200px;">Amount
									(${systemSettings.currencySymbol})</th>
								<th></th>
							</tr>
							<tr ng:repeat="item in invoice.items">
								<td><input type="text" ng-model="item.id"><input
									type="text" ng-model="item.stockRegDtl_id"></td>
								<td>{{$index + 1}}</td>
								<!-- <td><input ng-model="item.check_valIn"
									ng-click="toggleOneIn(item)" type="checkbox"></td> -->
								<td ng-click="tableClicked($index)"><div
										style="width: 100%">
										<div style="float: left; width: 25%; padding-right: 4px;">
											<input type="text" ng:model="item.stock_item_code"
												id="stock_item_code" name="stock_item_code" ng:required
												class="input-mini form-control" autocompete-text
												ng-disabled="disable_all"
												ng-style="validation_Sstock_item_code" maxlength="10">
										</div>
										<div style="display: inline-block; width: 73%;">
											<input type="hidden" ng:model="item.stock_item_id"
												id="stock_item_id" name="stock_item_id"> <input
												type="text" class="input-mini form-control"
												ng:model="item.stock_item_name" id="stock_item_name"
												name="stock_item_name" ng-disabled="true"><input
												type="hidden" ng:model="item.stock_item_batch_id"
												id="stock_item_batch_id" name="stock_item_batch_id"><input
												type="hidden" ng:model="item.stock_item_batch_stock"
												id="stock_item_batch_id" name="stock_item_batch_stock">
										</div></td>
								<td style="width: 300px"><input type="text"
									ng-disabled="true" id="uomcode" ng-model="item.uomcode"
									class="input-mini form-control" name="uomcode"></td>

								<td class="item-table-col-width"
									ng-show="show_stockrequest_form"><input type="text"
									ng:model="item.currentStock"
									class="input-mini form-control algn_rgt" id="currentStock"
									name="currentStock" ng-disabled="true"
									ng-style="validation_currentStock" valid-number></td>
								
								<td class="item-table-col-width"><input type="text"
									ng:model="item.pendingStock"
									class="input-mini form-control algn_rgt" id="pendingStock"
									name="pendingStock" ng-disabled="true"
									ng-style="validation_currentStock" valid-number></td>
									
								<td class="item-table-col-width"><input type="text"
									ng:model="item.request_qty" maxlength="10" select-on-click
									ng:required valid-number
									class="input-mini form-control algn_rgt"
									row-add="addItem($index,0)" row-delete="removeItem($index)"
									row-save="saveData()" id="request_qty" name="request_qty"
									ng-disabled="disable_all" ng-style="validation_request_qty_qty"></td>
								<td class="item-table-col-width"
									ng-show="show_stockrequest_form"><input type="text"
									ng:model="item.delivered_qty" maxlength="10" select-on-click
									valid-number id="delivered_qty" name="delivered_qty"
									row-add="addItem($index,1)" row-delete="removeItem($index)"
									row-save="saveData()" class="input-mini form-control algn_rgt"
									ng-disabled="disable_all" ng-style="validation_delivered_qty"></td>


								<td class="item-table-col-width"
									ng-show="show_stockrequest_form"><input type="text"
									ng:model="item.unit_price" maxlength="10" select-on-click
									valid-number id="unit_price" name="unit_price"
									row-add="addItem()" row-delete="removeItem($index)"
									row-save="saveData()" class="input-mini form-control algn_rgt"
									ng-disabled="true" ng-style="validation_unit_price"
									ng-show="show_stockrequest_form"></td>
								<td ng-show="show_stockrequest_form"><input type="text"
									class="input-mini form-control amount algn_rgt" id="amount"
									name="amount" ng-disabled="true"
									value="{{amount(item.delivered_qty * item.unit_price )}}"></td>
								<td><a href ng:click="removeRowItem($index)"
									class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<!-- <td></td> -->
								<td><a href ng:click="addItem()" class="btn btn-small"><i
										class="fa fa-plus"></i> </a></td>
							</tr>
						</table>
					</div>
				</div>
			</c:if>
			<c:if test="${(combineMode==1)?true:false }">

				<div class="form-group " id="form_div_items_tbl">

					<div class="cmmn_maain_tbl">
						<circle-spinner ng-show="prograssing"></circle-spinner>
						<table class="table table-bordered tableStyle" id="items_table1"
							ng-hide="prograssing">
							<tr class="active">
								<th>ID</th>
								<th>#</th>
								<th style="width: 380px;">Item Code & Name</th>
								<th style="width: 120px;">Unit</th>
								<th style="width: 155px;">Current Stock</th>
								<th style="width: 155px;">Pending Qty</th>
								<th style="width: 155px;">Request Qty</th>
								<%--<th style="width: 135px;">Deliver Qty</th>
								 <th style="width: 135px;">Unit Rate</th>
								<th style="width: 135px;">Amount
									(${systemSettings.currencySymbol})</th> --%>
								<th></th>
							</tr>
							<tr ng:repeat="item in invoice.items">
								<td><input type="text" ng-model="item.id"><input
									type="text" ng-model="item.stockRegDtl_id"></td>
								<td>{{$index + 1}}</td>
								<td ng-click="tableClicked($index)"><div
										style="width: 100%">
										<div style="float: left; width: 25%; padding-right: 4px;">
											<input type="text" ng:model="item.stock_item_code"
												id="stock_item_code" name="stock_item_code" ng:required
												class="input-mini form-control" autocompete-text
												ng-disabled="disable_all"
												ng-style="validation_Sstock_item_code" maxlength="10">
										</div>
										<div style="display: inline-block; width: 73%;">
											<input type="hidden" ng:model="item.stock_item_id"
												id="stock_item_id" name="stock_item_id"> <input
												type="text" class="input-mini form-control"
												ng:model="item.stock_item_name" id="stock_item_name"
												name="stock_item_name" ng-disabled="true"><input
												type="hidden" ng:model="item.stock_item_batch_id"
												id="stock_item_batch_id" name="stock_item_batch_id"><input
												type="hidden" ng:model="item.stock_item_batch_stock"
												id="stock_item_batch_id" name="stock_item_batch_stock">
										</div></td>
								<td style=""><input type="text" ng-disabled="true"
									id="uomcode2" ng-model="item.uomcode"
									class="input-mini form-control" name="uomcode"></td>

								<td class="item-table-col-width"><input type="text"
									ng:model="item.currentStock"
									class="input-mini form-control algn_rgt" id="currentStock2"
									name="currentStock" ng-disabled="true"
									ng-style="validation_currentStock" valid-number></td>
								<td class="item-table-col-width"><input type="text"
									ng:model="item.pendingStock"
									class="input-mini form-control algn_rgt" id="pendingStock"
									name="pendingStock" ng-disabled="true"
									ng-style="validation_currentStock" valid-number></td>
								<td class="item-table-col-width"><input type="text"
									ng:model="item.request_qty" maxlength="10" select-on-click
									ng:required valid-number
									class="input-mini form-control algn_rgt"
									row-add="addItem($index,0)" row-delete="removeItem($index)"
									row-save="saveData()" id="request_qty" name="request_qty"
									ng-disabled="disable_all" ng-style="validation_request_qty_qty"></td>
								<!-- <td class="item-table-col-width"><input type="text"
									ng:model="item.delivered_qty" maxlength="10" select-on-click
									valid-number id="delivered_qty" name="delivered_qty"
									row-add="addItem($index,1)" row-delete="removeItem($index)"
									row-save="saveData()" class="input-mini form-control algn_rgt"
									ng-disabled="disable_all" ng-style="validation_delivered_qty"></td> -->

								<!-- <td class="item-table-col-width"><input type="text"
									ng:model="item.unit_price" maxlength="10" select-on-click
									valid-number id="unit_price" name="unit_price"
									row-add="addItem()" row-delete="removeItem($index)"
									row-save="saveData()" class="input-mini form-control algn_rgt"
									ng-disabled="true" ng-style="validation_unit_price"></td>
								<td><input type="text"
									class="input-mini form-control amount algn_rgt" id="amount"
									name="amount" ng-disabled="true"
									value="{{amount(item.delivered_qty * item.unit_price )}}"></td> -->


								<td><a href ng:click="removeRowItem($index)"
									class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
							</tr>
							 <tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<!--<td></td>
								<td></td>
								<td></td> -->
								<td><!-- <label class="col-sm-2 control-label">Total:</label> --></td>
								<td><%-- <input type="text"
									class="input-mini form-control algn_rgt"
									value="${systemSettings.currencySymbol} {{total() }}"
									ng-disabled="true">--%></td>
								<td><a href ng:click="addItem()" class="btn btn-small"><i
										class="fa fa-plus"></i> </a> </td>
							</tr>
						</table>
					</div>
				</div>



			</c:if>

		</div>

		<!-- </div> --> <!-- <div class="inprod_button">
        <button class="but_inprod print_button" ng-click="printStore()">PRINT STORE </button>
         <button class="but_inprod save_button" ng-click="MarkFinished()">MARK FINISHED</button>
        </div> --> </md-content> </md-tab>
    </md-tabs>
  <!-- </md-content> -->
  </form>
  </div>
  
  
  <!-- bom Details pop up-->
  
  
  
  <div class="modal fade " id="orderData" role="dialog">
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
			<div class="modal-body col-sm-12">
				<jsp:directive.include file="../prodprocess/bomedit.jsp" />
			</div>

			<div class="modal-footer">

				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

				<button class="btn  btn-sm btn-primary save_button savebtn"
					type="button" name="btnUpdte" ng-click="updateBomData();" id="btnUpdte" ng-disabled="disable_all"
					 data-target="#importDataModal"  ng-show="!disable_all">
					<i class="fa fa-floppy-o" aria-hidden="true"></i> Update
				</button>
				<!-- <button class="btn  btn-sm btn-primary save_button savebtn" type="button" name="btnSave" id="btnSave" ng-click="fun_save_form()" style="display: block;">
			<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
		</button> -->

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
										 	<div class="label_div"><label>DAMAGE Qty </label></div>
										 	<div class="input_txt_box"><input type="text" ng-model="damageqty" style="text-align:right"
															    maxlength="${15+settings['decimalPlace']+1}"
															    valid-number id="damageqty1"
																ng-change="addDamageQty();"
																 ></div>
																 </div>
										 	
										 	
										 </div>
									</div>
									<div class="modal-footer">

				                   <button type="button" class="btn btn-success" data-dismiss="modal">OK</button>

				
			                       </div>
									
								</div>
						
							</div>
						</div>
						
						
<!--unit cost-->				




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
										 	<div class="modal_box_div col-sm-12">
										 	<div class="label_div col-sm-6"><label>MATERIAL COST </label></div>
										 	<div class="input_txt_box col-sm-6"><input type="text" ng-model="materialCost1" style="text-align:right"
															    maxlength="15" 
																ng-disabled="true"
															    valid-number
																ng-disabled="true"
																 ></div>
																 </div>
										 	<div class="modal_box_div col-sm-12">
											<div class="label_div col-sm-6"><label>OTHER COST </label></div>
											<div class="input_txt_box col-sm-6"><input type="text" ng-model="otherCost1" style="text-align:right"
																maxlength="15" 
																ng-disabled="true"
															    valid-number
																ng-disabled="true"
																 ></div>
																 </div>
										 	<div class="modal_box_div col-sm-12">
											<div class="label_div col-sm-6"><label>TOTAL COST</label></div>
										 	<div class="input_txt_box col-sm-6"><input type="text"  style="text-align:right"
													           ng-disabled="true" 
													           ng-model="showTotalCost" id="setTotalCost"></div>
										 </div>
										 </div>
									</div>
						
									
								</div>
						
							</div>
						</div>	

		
						


<!-- pop up -->

<div class="modal fade" id="orderDataSplit" role="dialog">
	<div class="modal-dialog modal-md" style="width: 500px !important;">
		<!-- Modal content-->
		<div class="modal-content" style="width: 100%; float: left;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Order Details</h4>
			</div>
			<div class="modal-body" style="width: 100%; float: left;">



    

				

					<table class="table table-bordered" id="">
						<thead>
							<tr>
								<th colspan="4" style="text-align: left">{{itmName}}</th>
							</tr>
							<tr>
								<th>#</th>
								<th>Shop Name</th>
								<th>Order Qty</th>
								



							</tr>
						</thead>
						<tbody>


							<tr ng:repeat="shop in shopOrder">

								<td>{{ $index+1}}</td>
								<td>{{shop.customer_name}}</td>
								<td class="algn_rgt">{{shop.qty}}</td>
							</tr>
							<tr>
								<td colspan="2" class="algn_rgt">Total Qty:</td>
								<td class="algn_rgt">{{totalorder}}</td>
							</tr>

						</tbody>
					</table>


				
 





			</div>


		</div>

	</div>
</div>


<!-- ShopWise Order Data pop Up -->
 	
						<div class="modal fade" id="orderDataSplitprod" role="dialog">
							<div class="modal-dialog modal-md" style="width: 500px !important;">
								<!-- Modal content-->
								<div class="modal-content" style="width: 100%; float: left;">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">Order Details</h4>
									</div>
									<div class="modal-body" style="width: 100%; float: left;">
										
										 <div class="model_form_div " ng-repeat="order in orderSplitData ">
										 	<div class="modal_box_div col-sm-12 ">
										 	<div class="label_div1 col-sm-6"><label>{{order.customer_name}} </label></div>
										 	<div class="input_txt_box col-sm-6"><input type="text" ng-model="order.orderqty" style="text-align:right"
															    maxlength="15" 
																ng-disabled="true"
															    valid-number
																ng-disabled="true"
																 ></div>
																 </div>
										 
									    </div>
									     <div class="model_form_div " >
										 	<div class="modal_box_div col-sm-12">
										 	<div class="label_div1 col-sm-6"><label>PREVIOUS BALANCE</label></div>
										 	<div class="input_txt_box col-sm-6"><input type="text" ng-model="previousBalance" style="text-align:right"
															    maxlength="15" 
																ng-disabled="true"
															    valid-number
																ng-disabled="true"
																 ></div>
																 </div>
										 
									    </div>
									</div>
						
									
								</div>
						
							</div>
						</div>
						
						
<!-- Insufficient BOM Data List -->	

<!-- Item wise Bom Data pop Up -->

<div class="modal fade" id="insufficientItemsList" role="dialog">
	<div class="modal-dialog modal-md" style="width: 70% !important;height:100%;">
		<!-- Modal content-->
		<div class="modal-content" style="width: 100%; float: left; max-height:80%;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Insufficient items to produce the following products</h4>
			</div>
			<div class="modal-body" style="width: 100%; float: left;">


				<div ng-repeat="(key, value) in insufficientList | groupBy: 'bom_item_id'">

					<table class="table table-bordered" id="insufficientList_table">
						<thead>
							<!-- <tr>
								<th colspan="5" style="text-align: left">{{itmName}}</th>
							</tr> -->
							<tr>
								<th>#</th>
								
								<th>PRODUCT</th>
								<th>ITEM NAME</th>
								<th>CURRENT STOCK</th>
								<th>REQUIRED QUANTITY</th>

							</tr>
						</thead>
						<tbody>


							<tr ng:repeat="insufficientList in value">

								<td>{{ $index+1}}</td>
								<!-- <td><input type="text" ng:model="bomData.stock_item_name"
								id="stock_item_name" name="stock_item_name"
								class="input-mini form-control algn_rgt" ng-disabled="true"></td>
							<td><input type="text" ng:model="bomData.prod_qty"
								id="prod_qty" name="prod_qty"
								class="input-mini form-control algn_rgt" ng-disabled="true"></td> -->

								<td>{{insufficientList.product_name}}</td>
								<td class="algn_rgt" id="missingBom">{{insufficientList.bom_name}}</td>
								<td class="algn_rgt">{{insufficientList.current_stock}}</td>
								<td class="algn_rgt">{{insufficientList.required_qty}}</td>
								
							</tr>
							<tr>
								<td colspan="5" class="confirmText">Do you want to produce anyway?</td>
							</tr>
							<tr>
								<td colspan="3" class="algn_left"><button class="proceedProduction" >YES</button>
								<td colspan="2" class="algn_rgt"><button class="cancelProduction" data-dismiss="modal">NO</button>
								
								</td>
							</tr>

						</tbody>
					</table>


				</div>






			</div>


		</div>

	</div>
</div>					
						

<div class="modal fade" id="nobomProductModal" role="dialog">
	<div class="modal-dialog modal-md" style="width: 70% !important;height:100%;">
		<!-- Modal content-->
		<div class="content" style="width: 100%; float: left; max-height:80%;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Please add item bom for this product</h4>
				<button>OK!</button>
			</div>

		</div>

	</div>
</div>	



