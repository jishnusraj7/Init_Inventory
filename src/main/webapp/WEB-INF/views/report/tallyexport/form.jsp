<div class="" id="tally_export_report_form_div">
	<form class="form-horizontal" id="tally_export_report_form">
		<div class="report_main_frm_div">
			<div class="col-sm-12">
			
			
				<div class="form-group" id="form_div_stockout_startdate">
					<label for="stockout_startdate" class="col-sm-2 control-label lb"><spring:message
							code="stockregisterreport.startdate"></spring:message><i
						class="text-danger">*</i></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<div class="right-inner-addon">
								<i class="fa fa-calendar" id="calender_icon"
									style="left: 81%; z-index: 4;"></i> <input type="text"
									class="form-control" daterange-picker1 name="stockout_startdate"
									id="stockout_startdate" ng-model="formData.Deliverystartdate"
									ng-disabled="disable_all" placeholder=""
									ng-change="deliveryDateChange()">
							</div>
							<span class="input-group-addon" min="0" max="99" number-mask=""
								id="form_div_stockout_startdate_error" style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockregisterreport.startdate.error"></spring:message>"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group" id="form_div_stockout_enddate">
					<label for="form_div_stockout_enddate" class="col-sm-2 control-label lb"><spring:message
							code="stockregisterreport.enddate"></spring:message><i
						class="text-danger">*</i></label>
					<div class="col-sm-2">
						<div class="input-group col-sm-12">
							<div class="right-inner-addon">
								<i class="fa fa-calendar" id="calender_icon"
									style="left: 81%; z-index: 4;"></i> <input type="text"
									class="form-control" daterange-picker1 name="stockout_enddate"
									id="stockout_enddate" ng-model="formData.Deliveryenddate"
									ng-disabled="disable_all" placeholder=""
									ng-change="deliveryDateChange()">
							</div>
							<span class="input-group-addon" min="0" max="99" number-mask=""
								id="form_div_stockout_enddate_error" style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>"></i></span>
						</div>
					</div>
					<div class="text-danger" id="divErrormsgStkout"></div>
				</div>
			</div>
		</div>
	</form>



<!-- modal -->
   <div class="modal fade" id="tallyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content"> 
      <!--   <div class="modal-header">
          <h4 class="modal-title">Modal Heading</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
       -->
        <div class="modal-body">
         No Data Available
        </div>
        <div class="modal-footer">
          <button  type="button"  data-dismiss="modal" class="btn btn-danger">Close</button>
        </div>
        
      </div>
    </div>
  </div> 
  	
	
</div>
</div>

<%-- 	<script
	src="<c:url value='/resources/common/template/plugins/jQuery/jQuery-2.1.4.min.js?n=1' />"></script>
		<script
	src="<c:url value='/resources/common/template/bootstrap/js/bootstrap.min.js?n=1' />"></script> --%>