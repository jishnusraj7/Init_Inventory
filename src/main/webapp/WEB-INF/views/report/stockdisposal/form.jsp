<%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockDisposalReasonType"%>
<c:set var="enumValues" value="<%=stockDisposalReasonType.values()%>" />
<div class="" id="stock_disposal_report_form_div">
	<form class="form-horizontal" id="stock_disposal_report_form">
		<div class="report_main_frm_div">
			<%-- <jsp:directive.include file="../common/includes/common_form.jsp" /> --%>

			<div class="col-sm-12">
				<div class="form-group" id="form_div_rd_selectrpttype">
					<div class="col-sm-9">
						<div class="input-group radio_report">
							<label class="radio-inline"> <input type="radio"
								name="optradio" id="optnDateDisp">Date
							</label> <label class="radio-inline"> <input type="radio"
								name="optradio" id="optnMonthDisp">Month
							</label>
						</div>
					</div>

				</div>
				<div id="date_div_disp">
					<div class="form-group" id="form_div_startdate">
						<label for="startdate" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.startdate"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">
							<div class="input-group col-sm-12">
								<div class="right-inner-addon" id="form_div_startdate_disp">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="startdate"
										id="startdate" ng-model="formData.startdate"
										ng-disabled="disable_all" placeholder=""
										ng-change="tableDatevalidation()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_startdate_error_disp" style="display: none;"><i
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
								<div class="right-inner-addon" id="form_div_enddate_disp">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="enddate"
										id="enddate" ng-model="formData.enddate"
										ng-disabled="disable_all" placeholder=""
									    ng-change="tableDatevalidation()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_enddate_error_disp" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>"></i></span>
							</div>
							<div class="text-danger" id="divErrormsg5"></div>
						</div>
						
					</div>
				</div>

				<div id="month_div_disp">
					<div class="form-group" id="form_div_startmonth_disp">
						<label for="startdate" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.startmonth"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">

							<div class='input-group date' id='datetimepicker1'>
								<input type='text' id="startmonth_disp" class="form-control"
									ng-model="selectMonth" /> <span class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span> </span>
							</div>

						</div>
					</div>
					
				</div>

				<div class="form-group" id="div_department_code1_disposal">
					<label for="department_code1" class="col-sm-2 control-label"><spring:message
							code="stockregisterreport.department"></spring:message><i class="text-danger">*</i></label>
					<div class="col-sm-6">
						<div class="input-group col-sm-10 classItem1" id="div_code1">
							<input type="hidden" name="department_id" id="department_id"
								ng-model="formData.department_id"> <input type="text"
								class="form-control " name="department_code1"
								id="department_code1_disposal" ng-model="formData.department_code1"
								ng-disabled="disable_all" placeholder="Code">
								<input type="text" style="margin-left: 6px;"
								id="department_name1_disp" name="department_name1" class="form-control widthset"
								ng-model="formData.department_name1" disabled="disabled">
						</div>
					</div>

				</div> 

		
				<div class="form-group" id="form_div_transaction_type">
					<label for="transaction_type" class="col-sm-2 control-label lb"><spring:message
							code="stockregisterreport.transaction_type"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12">
							
							<select class="form-control selectpicker" style="width: 140px"
								id="reason_type" name="reason_type" ng-disabled="disable_all"
								ng-model="formData.trans_type" ng-change="changeReason($index)">
									<option value="" >select</option>
									<c:forEach items="${enumValues}" var="enumValue">
										<option value="${enumValue.getDisposalReasonTypeId()}">${enumValue.getDisposalReasonTypeName() }</option>
									</c:forEach>
							</select>
						</div>
					</div>
				</div>
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			</div>

		</div>
	</form>

</div>