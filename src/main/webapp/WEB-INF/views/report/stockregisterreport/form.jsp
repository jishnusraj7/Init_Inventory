<div class="" id="stock_register_report_form_div">
	<form class="form-horizontal" id="stock_register_report_form">
		<div class="report_main_frm_div">
			<%-- <jsp:directive.include file="../common/includes/common_form.jsp" /> --%>

			<div class="col-sm-12">
				<div class="form-group" id="form_div_rd_selectrpttype">
					<div class="col-sm-9">
						<div class="input-group radio_report">
							<label class="radio-inline"> <input type="radio"
								name="optradio" id="optnDate">Date
							</label> <label class="radio-inline"> <input type="radio"
								name="optradio" id="optnMonth">Month
							</label>
						</div>
					</div>

				</div>
				<div id="date_div">
					<div class="form-group" id="form_div_startdate">
						<label for="startdate" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.startdate"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">
							<div class="input-group col-sm-12">
								<div class="right-inner-addon" id="form_div_startdate_stockreg">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="startdate"
										id="startdate" ng-model="formData.startdate"
										ng-disabled="disable_all" placeholder=""
										ng-change="tableDatevalidation()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_startdate_error_stockreg" style="display: none;"><i
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
								<div class="right-inner-addon" id="form_div_enddate_stockreg">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="enddate"
										id="enddate" ng-model="formData.enddate"
										ng-disabled="disable_all" placeholder=""
									    ng-change="tableDatevalidation()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_enddate_error_stockreg" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>"></i></span>
							</div>
							<div class="text-danger" id="divErrormsg2"></div>
						</div>
						
					</div>
				</div>

				<div id="month_div">
				<div class="form-group" id="form_div_startmonth">
						<label for="startdate" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.startmonth"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">
							<%-- <div class="input-group col-sm-12">
								<div class="right-inner-addon">
									<!-- <i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> --> <input type="text"
										class="form-control" daterange-picker2 name="startmonth"
										id="startmonth" ng-model="startmonth"
										ng-disabled="disable_all" placeholder=""
										ng-change="dateChange()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_startmonth_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockregisterreport.startmonth.error"></spring:message>"></i></span>
							</div> --%>
							<div class='input-group date' id='datetimepicker1'>
                    <input type='text' id="startmonth1" class="form-control" ng-model="selectMonth" />
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
							
						</div>
					</div>
				<%-- <div class="form-group" id="form_div_endmonth">
						<label for="form_div_endmonth" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.endmonth"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">
							<div class="input-group col-sm-12">
								<div class="right-inner-addon">
									<!-- <i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> --> <input type="text"
										class="form-control" daterange-picker2 name="endmonth"
										id="endmonth" ng-model="endmonth"
										ng-disabled="disable_all" placeholder=""
										ng-change="dateChange()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_endmonth_error" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockregisterreport.endmonth.error"></spring:message>"></i></span>
							</div>
						</div>
						<div class="text-danger" id="divErrormsg"></div>
					</div> --%>
				</div>

				<!-- <div class="form-group" id="form_div_department">
					<label for="department" class="col-sm-2 control-label lb"><spring:message
							code="stockregisterreport.department"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							<select class="form-control" id="department_id" style="width: 140px !important;"
								name="department_id"
								ng-options="v.id as v.name for v in sampleDepartment"
								ng-model="formData.department_id">
							</select>
						</div>
					</div>
				</div> -->
				<div class="form-group" id="div_department_code1">
					<label for="department_code1" class="col-sm-2 control-label"><spring:message
							code="stockregisterreport.department"></spring:message><i class="text-danger">*</i></label>
					<div class="col-sm-6">
						 <div class="input-group col-sm-10 classItem1" id="div_code1">
							<input type="hidden" name="department_id" id="department_id"
								ng-model="formData.department_id"> <input type="text"
								class="form-control " name="department_code1"
								id="department_code1" ng-model="formData.department_code1"
								ng-disabled="disable_all" placeholder="Code">
								<input type="text" style="margin-left: 6px;"
								id="department_name1" name="department_name1" class="form-control widthset"
								ng-model="formData.department_name1" disabled="disabled">
						</div></div>

				</div> 

				<div class="form-group" id="form_div_item_category_id">
					<label for="parent_id" class="col-sm-2 control-label lb"><spring:message
							code="itemmaster.item_category_id"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							<select class="form-control itmCat" id="item_category_id" style="width: 140px !important;"
								name="item_category_id" ng-change="filterStkitm()"
								ng-options="v.id as v.name for v in ItemCtgry"
								ng-model="formData.item_category_id">
							</select>
						</div>
					</div>
				</div>
		<!-- 		<div class="form-group" id="form_div_item_id">
					<label for="item_id" class="col-sm-2 control-label lb"><spring:message
							code="itemstock.stockitem"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 classItem">
							<input type="text" class="form-control" id="item_id"
								name="item_id" table-autocomplete><input type="text"
								id="itemname" name="itemname" class="form-control itmname"
								ng-model="formData.itemname" disabled="disabled"><input
								type="hidden" class="" id="stock_item_id" name="stock_item_id"
								ng-model="formData.stock_item_id">
						</div>
					</div>

				</div> -->
				<!-- 
						<div class="form-group" id="form_div_item_id">
					<label for="item_id" class="col-sm-2 control-label lb"><spring:message
							code="itemstock.stockitem"></spring:message></label>
					<div class="col-sm-6" id="form_itemData">
						<div class="input-group col-sm-10 classItem1">
							<input type="text" class="form-control" id="item_id" 
								name="item_id" table-autocomplete><input type="text" style="margin-left: 6px;"
								id="itemname" name="itemname" class="form-control"
								ng-model="formData.itemname" disabled="disabled"><input
								type="hidden" class="" id="stock_item_id" name="stock_item_id"
								ng-model="formData.stock_item_id">
						</div>
					</div>

				</div> -->
				<div class="form-group" id="form_div_transaction_type">
					<label for="transaction_type" class="col-sm-2 control-label lb"><spring:message
							code="stockregisterreport.transaction_type"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							<select class="form-control" id="trans_type" name="trans_type" style="width: 140px !important;"
								ng-model="formData.trans_type" ng-disabled="disable_all">
								<option value="" >Select</option>
								<c:forEach items="${transactionType}" var="transactionType">
									<option value="${transactionType.gettransactionTypeId()}">${transactionType.gettransactionTypeName() }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				
				
				
				
				
					<div class="main_list_div" >

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
								class="shop_div_rl_chkbx" ng-click="unselectstockItem()">
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
								data-checklist-value="role1" class="shop_div_rl_chkbx" ng-click="unselectstockItemSelect()">
							<div class="shop_rl_name">{{role1.name }}</div>
							</label>
						</div>

					</div>

				</div>

			</div>

		</div>
	</form>

</div>