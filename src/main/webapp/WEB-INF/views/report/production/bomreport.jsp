
<div class="" id="prodBom_report_form_div">
	<link rel="stylesheet"
		href="<c:url value="/resources/mrp/report/prodbom/css/prodbom.css"/>">

	<form class="form-horizontal " id="prodBom_form">



		<md-radio-group ng-model="option" layout="row" ng-change="clearErr()"
			style=" padding-left: 200px;padding-bottom: 30px;">
		<md-radio-button value="1" class="md-primary" >Date</md-radio-button> <md-radio-button
			value="2" >Month </md-radio-button> </md-radio-group>

		<div ng-show="option==1">

			<div class="form-group" id="bom_form_div_startdate">
				<label for="startdate" class="col-sm-2 control-label lb"><spring:message
						code="stockregisterreport.startdate"></spring:message><i
					class="text-danger">*</i></label>
				<div class="col-sm-2">
					<div class="input-group col-sm-12">
						<div class="right-inner-addon" id="form_div_startdate_stkIn">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control" daterange-picker01 name="startdate"
								id="bom_startdate" ng-model="bom_startdate"
								ng-disabled="disable_all" placeholder=""
								ng-change="tableDatevalidation()"">
						</div>
						<span class="input-group-addon" min="0" max="99" number-mask=""
							id="form_div_startdate_error_bom" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockregisterreport.startdate.error"></spring:message>"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group" id="bom_form_div_enddate">
				<label for="form_div_enddate" class="col-sm-2 control-label lb"><spring:message
						code="stockregisterreport.enddate"></spring:message><i
					class="text-danger">*</i></label>
				<div class="col-sm-2">
					<div class="input-group col-sm-12">
						<div class="right-inner-addon" id="form_div_enddate_bom">
							<i class="fa fa-calendar" id="calender_icon"
								style="left: 81%; z-index: 4;"></i> <input type="text"
								class="form-control" daterange-picker01 name="bom_enddate"
								id="bom_enddate" ng-model="bom_enddate"
								ng-disabled="disable_all" placeholder=""
								ng-change="tableDatevalidation()">
						</div>
						<span class="input-group-addon" min="0" max="99" number-mask=""
							id="form_div_enddate_error_bom" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>"></i></span>
					</div>
				</div>
				<div class="text-danger" id="divErrormsg1_bom"></div>
			</div>

		</div>
		<div ng-show="option==2">


			<!-- <div class="form-group" id="form_div_startmonth">
				<label for="startdate" class="col-sm-2 control-label lb"><spring:message
						code="stockregisterreport.startmonth"></spring:message><i
					class="text-danger">*</i></label>
				<div class="col-sm-2">
					<div class='input-group date' id='datetimepicker1'>
						<input type='text' id="startmonth1" class="form-control"
							ng-model="selectMonth" daterange-picker2 /> <span
							class="input-group-addon"><span
							class="glyphicon glyphicon-calendar"></span> </span>
					</div>

				</div>
			</div> -->
			
				<div id="month_div">
				<div class="form-group" id="form_div_startmonth">
						<label for="startdate" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.startmonth"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">
							
							<div class='input-group date' id='datetimepicker1'>
                    <input type='text' id="startmonth2" class="form-control" ng-model="selectMonth1" />
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
							
						</div>
					</div>
			
				</div>
			

		</div>

		<div class="form-group " id="form_div_source_code">
			<label for="department_code" class="col-sm-2 control-label">Department<i
				class="text-danger">*</i></label>

			<div class="col-sm-2">
				<div class="input-group">
					<input type="hidden" name="source_department_id"
						id="source_department_id" ng-model="source_department_id">
					<input type="text" class="form-control" name="source_code"
						id="source_code" ng-model="source_code" ng-disabled="disable_all"
						placeholder="Code"><span class="input-group-addon"
						id="form_div_source_code_error" style="display: none;"><i
						class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
						data-placement="bottom" title=""
						data-original-title="<spring:message code="stockin.error.dep"></spring:message>"></i></span>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<input type="text" class="form-control" name="to_department_name"
						id="to_department_name" ng-model="source_name" ng-disabled="true"
						placeholder="">
				</div>
			</div>

		</div>
	<div id="msg" class="text-danger"></div>


	</form>
</div>





