<div class="" id="stock_in_report_form_div">
	<form class="form-horizontal" id="stock_in_report_form">
		<div class="report_main_frm_div">
			<%-- <jsp:directive.include file="../common/includes/common_form.jsp" /> --%>

			<div class="col-sm-12">
			
			<div class="form-group" id="form_div_radio_slctrprt">			
					<div class="col-sm-9">
						<div class="input-group radio_report">
							<label class="radio-inline"> <input type="radio"
								name="optradio" id="optradioSum">Item Wise
							</label> <label class="radio-inline"> <input type="radio"
								name="optradio" id="optradioDetail">Date Wise
							</label>
						</div>
					</div>

				</div>
			
				<div class="form-group" id="form_div_startdate">
					<label for="startdate" class="col-sm-2 control-label lb"><spring:message
							code="stockregisterreport.startdate"></spring:message><i
						class="text-danger">*</i></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12" >
							<div class="right-inner-addon" id="form_div_startdate_stkIn">
								<i class="fa fa-calendar" id="calender_icon"
									style="left: 81%; z-index: 4;"></i> <input type="text"
									class="form-control" daterange-picker1 name="startdate"
									id="startdate" ng-model="formData.postartdate"
									ng-disabled="disable_all" placeholder=""
									ng-change="podateChange()">
							</div>
							<span class="input-group-addon" min="0" max="99" number-mask=""
								id="form_div_startdate_error_stkIn" style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockregisterreport.startdate.error"></spring:message>"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group" id="form_div_enddate">
					<label for="form_div_enddate" class="col-sm-2 control-label lb"><spring:message
							code="stockregisterreport.enddate"></spring:message><i
						class="text-danger">*</i></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<div class="right-inner-addon" id="form_div_enddate_stkIn" >
								<i class="fa fa-calendar" id="calender_icon"
									style="left: 81%; z-index: 4;"></i> <input type="text"
									class="form-control" daterange-picker1 name="enddate"
									id="enddate" ng-model="formData.poenddate"
									ng-disabled="disable_all" placeholder=""
									ng-change="podateChange()">
							</div>
							<span class="input-group-addon" min="0" max="99" number-mask=""
								id="form_div_enddate_error_stkIn" style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>"></i></span>
						</div>
					</div>
					<div class="text-danger" id="divErrormsg1_stkIn"></div>
				</div>


				

<!-- 		<div class="form-group" id="form_div_department_code">
					<label for="department_code" class="col-sm-2 control-label"><spring:message
							code="stockin.department"></spring:message> </label>
					<div class="col-sm-8">
						<div class="input-group col-sm-8 classItem1">
							<input type="hidden" name="department_id" id="department_id"
								ng-model="formData.departmentId"> <input type="text"
								class="form-control " name="department_code"
								id="department_code" ng-model="department_code"
								ng-disabled="disable_all" placeholder="Code">
								<input type="text" style="margin-left: 6px;"
								id="department_name" name="department_name" class="form-control"
								ng-model="department_name" disabled="disabled">
								
						
						</div>
					</div>

				</div> -->
				
				
				
<div class="form-group" id="form_div_supplier_code1">
				<label for="supplier_code" class="col-sm-2 control-label"><spring:message
							code="stockin.supl_name"></spring:message> <i class="text-danger"></i></label>
					<div class="col-sm-6">
						<div class="input-group col-sm-10 classItem1">
							<input type="text"
								class="form-control required" name="supplier_code1"
								id="supplier_code1" ng-model="formData.supplier_code1"
								ng-disabled="disable_all" placeholder="Code"><input type="text" class="form-control " name="supplier_name1" style="margin-left: 6px;"
								id="supplier_name1" style="margin-left: 6px;" ng-model="formData.supplier_name1"
								ng-disabled="true" placeholder=""><input type="hidden" name="supplier_id1" id="supplier_id1"
								ng-model="formData.supplier_id1">
						</div>
					</div>

				</div>


			<!-- 	<div class="form-group" id="form_div_item_id1">
					<label for="item_id" class="col-sm-2 control-label lb"><spring:message
							code="itemstock.stockitem"></spring:message></label>
					<div class="col-sm-6">
						<div class="input-group col-sm-10 classItem1">
							<input type="text" class="form-control" id="item_id" 
								name="item_id" table-autocomplete1><input type="text" style="margin-left: 6px;"
								id="itemname1" name="itemname" class="form-control"
								ng-model="formData.itemname1" disabled="disabled"><input
								type="hidden" class="" id="itemid" name="itemid"
								ng-model="formData.itemid1">
						</div>
					</div>

				</div> -->
				
				
				
				<div class="main_list_div" ng-show="hideit">

					<div class="col-sm-2 shps1">
						<label for="name" class="control-label "> Stock Item </label>
					</div>

					<div class="shops_div">
						<div class="shop_div_hdr">


							<input type="checkbox" ng-model="is_active" class="chck_box_div"
								ng-click="moveItem()"> <input type="search"
								class="shop_div_hdr_input" placeholder="Search..."
								aria-controls="DataTables_Table_0" ng-model="serch" ng-change="dataFetch()">
								
								<!-- <button  ng-click="dataFetch()" class="clickbtn">clk</button> -->
						</div>
						<div class="shop_div_cnt">
							<label ng-repeat="role in filterItemData| orderBy: 'name'"
								class="shop_div_cnt_label"> <input type="checkbox"
								data-checklist-model="user.roles" data-checklist-value="role"
								class="shop_div_rl_chkbx" ng-click="unselectstockItem();">
							<div class="shop_rl_name">{{role.name }}</div>
							</label>
						</div>
					</div>



					<div id="LeftRightBtn" class="shp_main_btn" >
						<input id="btnRight" type="button" value=">>"
							ng-click="btnRight()" /> <input id="btnLeft" type="button"
							value="<<" ng-click=" btnLeft()" />
					</div>


					<div class="selected_shop_div shops_div">
						<div class="shop_div_hdr">
							<label class="shop_div_cnt_label"> <input type="checkbox"
								ng-model="is_active_select" class="chck_box_div"
								ng-click="moveItemSelect()"></label>

						</div>
						<div class="shop_div_cnt">
							<label ng-repeat="role1 in selectedItemList | orderBy: 'name'"
								class="shop_div_cnt_label"> <input type="checkbox"
								data-checklist-model="userSelect.roles"
								data-checklist-value="role1" class="shop_div_rl_chkbx" ng-click="unselectstockItemSelect();">
							<div class="shop_rl_name">{{role1.name }}</div>
							</label>
						</div>

					</div>

				</div>
				
				
				
			<%-- 	<div class="form-group" id="form_div_poStatus">
					<label for="poStatus" class="col-sm-2 control-label lb"> <!-- <spring:message
							code="stockregisterreport.transaction_type"></spring:message> -->Status
					</label>
					<div class="col-sm-4"><div class="input-group col-sm-12">
							<select class="form-control" id="stkStatus" style="width: 140px !important;"
								name="transaction_type" ng-model="formData.stkStatus"
								ng-disabled="disable_all">
								<option value="-1" selected="selected">Select</option>
							
								
								<c:forEach items="${stockinStatus}" var="stockinStatus">
									<option value="${stockinStatus.getStockInStatusId()}">${stockinStatus.getStockInStatusName() }</option>
								</c:forEach>
							</select>
						</div>
					
					</div>
				</div> --%>
			</div>
		</div>
	</form>

</div>
