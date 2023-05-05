<div class="" id="non_moving_form_div">
	<form class="form-horizontal" id="item_stock_form">
		<div class="">
			<%-- <jsp:directive.include file="../common/includes/common_form.jsp" /> --%>

			<div class="report_main_frm_div">

				<div class="form-group " id="form_div_duration">
					<label for="non_mov_duration"
						class="col-sm-2 control-label code-lbl-font-size"><spring:message
							code="nonmoving_report.duration">
						</spring:message><i class="text-danger">*</i></label>
					<div class="col-sm-2">
						<div class="input-group" id="daysInput">
							<input type="text" class="form-control code-font-size"
								name="non_moving_duration" maxlength="3"
								id="non_moving_duration" value="" placeholder=""
								ng-model="formData.days" numbers-only >
						</div>
					</div>
					<label for="days" class="col-sm-1 control-label "><spring:message
							code="nonmoving_report.days">
						</spring:message></label>
				</div>				
				<div class="form-group" id="form_div_nonmov_item_category_id">
					<label for="parent_id" class="col-sm-2 control-label lb"><spring:message
							code="itemmaster.item_category_id"></spring:message></label>
					<div class="col-sm-10">
						<div class="input-group">
							<select class="form-control"
								style="width: 190px !important;" id="item_category_id1"
								name="item_category_id1" 
								ng-options="v.id as v.name for v in listItemCtgry"
								 ng-model="formData.item_category_id"><option value="" selected>select</option>
							</select>
						</div>
					</div>
				</div>

				<div id="showMsg" class="text-danger">
				</div>
			</div>
		</div>
	</form>

</div>
