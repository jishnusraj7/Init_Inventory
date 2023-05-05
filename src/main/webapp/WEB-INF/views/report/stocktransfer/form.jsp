
<div class="" id="stock_transfer_report_form_div">
	<form class="form-horizontal" id="stock_transfer_report_form">
		<div class="report_main_frm_div">


			<div class="col-sm-12">
				<!-- <div class="form-group" id="form_div_rd_selectrpttype">
					<div class="col-sm-9">
						<div class="input-group radio_report">
							<label class="radio-inline"> <input type="radio"
								name="optradio" id="optnDateTrans">Date
							</label> <label class="radio-inline"> <input type="radio"
								name="optradio" id="optnMonthTrans">Month
							</label>
						</div>
					</div>

				</div> -->
				<div id="date_div_trans">

					<div class="form-group" id="form_div_startdate_trans"
						style="text-align: center; padding-left: 81px;">
						<div class="input-group radio_report"
							style="width: 44%; float: left; margin: 0 auto;">
							<label class="radio-inline"> <input type="radio"
								name="stkoptradioRep" id="stkoptradioExternalRep">External
							</label> <label class="radio-inline"> <input type="radio"
								name="stkoptradioRep" id="stkoptradioInternalRep">Internal
							</label>
						</div>
					</div>


				<%-- 	<div class="form-group " id="form_div_dest_company">
						<label for="companyId" class="col-sm-2 control-label"><spring:message
								code="stockout.company"></spring:message> <i class="text-danger">*</i></label>
						<div class="col-sm-3">
							<div class="input-group">
								<select class="form-control" name="companyId" id="companyId"
									style="width: 231px;" ng-disabled="disable_all" required>
									<option value=" " id="select">select</option>
									<c:forEach var="hqCompany" items="${hqCompanyData}">
										<c:if test="${hqCompany.id !=0}">
											<option value="${hqCompany.id}" id="${hqCompany.code}"
												data-dr="${hqCompany.code}"><c:out
													value="${hqCompany.name}" /></option>
										</c:if>
									</c:forEach>
								</select><span class="input-group-addon" id="form_div_dest_company_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.company"></spring:message>"></i></span>

							</div>
						</div>
					</div>


					<div class="form-group" id="form_div_dest_department">
						<label for="dest_department_code" class="col-sm-2 control-label"><spring:message
								code="stockout.destination"></spring:message> <i
							class="text-danger">*</i> </label>
						<div class="col-sm-3">

							<div class="input-group">
								<select class="form-control" name="companyId" id="companyId"
									style="width: 231px;" ng-disabled="disable_all" required>
									<option value=" " id="select">select</option>
									<c:forEach var="department" items="${departmentProd}">
										<c:if test="${department.id !=0}">
											<option value="${department.id}" id="${department.code}"
												data-dr="${department.code}"><c:out
													value="${department.name}" /></option>
										</c:if>
									</c:forEach>
								</select><span class="input-group-addon" id="form_div_dest_company_error"
									style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockin.error.company"></spring:message>"></i></span>

							</div>
						</div>
					</div> --%>




					<div class="form-group" id="form_div_startdate_trans">
						<label for="startdate" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.startdate"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">
							<div class="input-group col-sm-12">
								<div class="right-inner-addon" id="form_div_startdate_trans">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="startdate"
										id="startdate" ng-model="formData.startdate"
										ng-disabled="disable_all" placeholder=""
										ng-change="tableDatevalidation()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_startdate_error_trans" style="translay: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockregisterreport.startdate.error"></spring:message>"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group" id="form_div_enddate_trans">
						<label for="form_div_enddate" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.enddate"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">
							<div class="input-group col-sm-12">
								<div class="right-inner-addon" id="form_div_enddate_trans">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="enddate"
										id="enddate" ng-model="formData.enddate"
										ng-disabled="disable_all" placeholder=""
										ng-change="tableDatevalidation()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_enddate_error_trans" style="translay: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>"></i></span>
							</div>

						</div>
						<div class="text-danger" id="divErrormsg6"></div>
					</div>




					<div class="main_list_div">

						<div class="col-sm-2 shps1">
							<label for="name" class="control-label "> Stock Item </label>
						</div>

						<div class="shops_div">
							<div class="shop_div_hdr">


								<input type="checkbox" ng-model="is_active" class="chck_box_div"
									ng-click="moveItem()"> <input type="search"
									class="shop_div_hdr_input" placeholder="Search..."
									aria-controls="DataTables_Table_0" ng-model="serch"
									ng-change="dataFetch()">

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



						<div id="LeftRightBtn" class="shp_main_btn">
							<input id="btnRight" type="button" value=">>"
								ng-click="btnRight()" /> <input id="btnLeft" type="button"
								value="<<" ng-click=" btnLeft()" />
						</div>


						<div class="selected_shop_div shops_div">
							<div class="shop_div_hdr">
								<label class="shop_div_cnt_label"> <input
									type="checkbox" ng-model="is_active_select"
									class="chck_box_div" ng-click="moveItemSelect()"></label>

							</div>
							<div class="shop_div_cnt">
								<label ng-repeat="role1 in selectedItemList | orderBy: 'name'"
									class="shop_div_cnt_label"> <input type="checkbox"
									data-checklist-model="userSelect.roles"
									data-checklist-value="role1" class="shop_div_rl_chkbx"
									ng-click="unselectstockItemSelect();">
									<div class="shop_rl_name">{{role1.name }}</div>
								</label>
							</div>

						</div>

					</div>



				</div>

				<!-- <div id="month_div_trans">
					<div class="form-group" id="form_div_startmonth_trans">
						<label for="startdate" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.startmonth"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">

							<div class='input-group date' id='datetimepicker1'>
								<input type='text' id="startmonth_trans" class="form-control"
									ng-model="selectMonth" /> <span class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span> </span>
							</div>

						</div>
					</div>
					
				</div> -->



			</div>

		</div>
	</form>

</div>