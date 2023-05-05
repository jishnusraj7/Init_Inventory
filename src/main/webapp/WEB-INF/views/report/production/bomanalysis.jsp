
<div class="" id="prodBom_report_form_div">
	<link rel="stylesheet"
		href="<c:url value="/resources/mrp/report/bomanalysisrpt/css/bomanalysisrpt.css"/>">

	<form class="form-horizontal " id="bomanalysis_form">



	<!-- 	<md-radio-group ng-model="summaryOption" layout="row"
			style=" padding-left: 200px;padding-bottom: 30px;" ng-click="clearErr()">
		<md-radio-button value="1" class="md-primary" ng-click="clearErr()">Shop</md-radio-button> <md-radio-button
			value="2">Production </md-radio-button> </md-radio-group> -->

		<div>

			<div class="form-group" id="bomanalysis_startdate">
				<label for="startdate" class="col-sm-2 control-label lb"><spring:message
						code="stockregisterreport.startdate"></spring:message><i
					class="text-danger">*</i></label>
				<div class="col-sm-2">
					<div class="input-group col-sm-12">
						<div class="right-inner-addon" id="form_div_startdate_stkIn">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control" daterange-picker19 name="startdate"
								id="bom_startdate" ng-model="bomanalysis_startdate"
								ng-disabled="disable_all" placeholder=""
								ng-change="tableDatevalidation()"">
						</div>
						<span class="input-group-addon" min="0" max="99" number-mask=""
							id="bomanalysis_startdate_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockregisterreport.startdate.error"></spring:message>"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group" id="bomanalysis_enddate">
				<label for="form_div_enddate" class="col-sm-2 control-label lb"><spring:message
						code="stockregisterreport.enddate"></spring:message><i
					class="text-danger">*</i></label>
				<div class="col-sm-2">
					<div class="input-group col-sm-12">
						<div class="right-inner-addon" id="form_div_enddate_bom">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control" daterange-picker19 name="bom_enddate"
								id="bom_enddate" ng-model="bomanalysis_enddate"
								ng-disabled="disable_all" placeholder=""
								ng-change="tableDatevalidation()">
						</div>
						<span class="input-group-addon" min="0" max="99" number-mask=""
							id="bomanalysis_enddate_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>"></i></span>
					</div>
				</div>
				<div class="text-danger" id="divErrormsg1_bomanalysis"></div>
			</div>

		</div>
		

		</div>



	</form>
</div>





