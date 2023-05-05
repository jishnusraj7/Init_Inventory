<div class="" id="stockadjustment_report_div">
	<link rel="stylesheet"
		href="<c:url value="/resources/mrp/report/prodbom/css/prodbom.css"/>">

	<form class="form-horizontal " id="stock_adjutment_summary_form">
		<div>
			<div class="form-group" id="stockadjustment_form_div_startdate">
				<label for="startdate" class="col-sm-2 control-label lb"><spring:message
						code="stockregisterreport.startdate"></spring:message><i
					class="text-danger">*</i></label>
				<div class="col-sm-2">
					<div class="input-group col-sm-12">
						<div class="right-inner-addon"
							id="form_div_stockadjustment_startdate">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control" daterange-picker01
								name="stockadjustment_startdate" id="stockadjustment_startdate"
								ng-model="stockadjustment_startdate" ng-disabled="disable_all"
								placeholder="" ng-change="tableDatevalidation()"">
						</div>
						<span class="input-group-addon" min="0" max="99" number-mask=""
							id="stockadjustment_form_div_startdate_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockregisterreport.startdate.error">
							</spring:message>"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group" id="stockadjustment_form_div_enddate">
				<label for="form_div_enddate" class="col-sm-2 control-label lb"><spring:message
						code="stockregisterreport.enddate"></spring:message><i
					class="text-danger">*</i></label>
				<div class="col-sm-2">
					<div class="input-group col-sm-12">
						<div class="right-inner-addon"
							id="form_div_stockadjustment_enddate">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control" daterange-picker01
								name="stockadjustment_enddate" id="stockadjustment_enddate"
								ng-model="stockadjustment_enddate" ng-disabled="disable_all"
								placeholder="" ng-change="tableDatevalidation()">
						</div>
						<span class="input-group-addon" min="0" max="99" number-mask=""
							id="form_div_stockadjustment_enddate_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>">
							</i></span>
					</div>
				</div>
				<div class="text-danger" id="div_stockadjustment_error_msg"></div>
			</div>
		</div>
	</form>
</div>