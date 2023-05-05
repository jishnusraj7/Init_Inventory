<!--
	 *  Done by anandu on 21-01-2020 
 -->
<div class="" id="stock_excise_report_form_div">
	<form class="form-horizontal" id="stock_excise_report_form">
		<div class="report_main_frm_div">

			<div class="col-sm-12">
				
				<div id="date_div">
					<div class="form-group" id="form_div_startdate">
						<label for="startdate" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.startdate"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">
							<div class="input-group col-sm-12">
								<div class="right-inner-addon"
									id="form_div_startdate_stockexcise">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="startdate"
										id="startdate" ng-model="formData.startdate"
										ng-disabled="disable_all" placeholder=""
										ng-change="tableDatevalidation()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_startdate_error_stockexcise"
									style="display: none;"><i
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
								<div class="right-inner-addon" id="form_div_enddate_stockexcise">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="enddate"
										id="enddate" ng-model="formData.enddate"
										ng-disabled="disable_all" placeholder=""
										ng-change="tableDatevalidation()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_enddate_error_stockexcise" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=" "
									data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>"></i></span>
							</div>
							<div class="text-danger" id="divErrormsg2"></div>
						</div>

					</div>
					
				</div>

				<div class="col-sm-6">
					<div class="input-group col-sm-8" id="cat_wise">
						<font size="3">Category wise</font> <input type="checkbox"
							checked="checked" id="category">

					</div>
				</div>



				<!-- <div id="month_div">
				<div class="form-group" id="form_div_startmonth">
						<label for="startdate" class="col-sm-2 control-label lb"><spring:message
								code="stockregisterreport.startmonth"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">
							
							<div class='input-group date' id='datetimepicker1'>
                    <input type='text' id="startmonth1" class="form-control" ng-model="selectMonth" />
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
							
						</div>
					</div>
				
				</div> -->

			</div>

		</div>
	</form>

</div>