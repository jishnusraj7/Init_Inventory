<!-- @gana 110320 -->
<div class="" id="department_wise_report_form_div">
	<form class="form-horizontal" id="department_wise_report_form">
		<div class="report_main_frm_div">
            	<div class="col-sm-12">
				<div id="date_div">
					<div class="form-group" id="form_div_startdate">
						<label for="startdate" class="col-sm-2 control-label lb"><spring:message
								code="departmentwisereport.date"></spring:message><i
							class="text-danger">*</i></label>
						<div class="col-sm-2">
							<div class="input-group col-sm-12">
								<div class="right-inner-addon" id="form_div_startdate_stockreg">
									<i class="fa fa-calendar" id="calender_icon"
										style="left: 81%; z-index: 4;"></i> <input type="text"
										class="form-control" daterange-picker1 name="startdate"
										id="startdate" ng-model="formData.startdate"
										ng-disabled="disable_all" placeholder=""
										ng-change="tableDatevalidation()" value="new Date()">
								</div>
								<span class="input-group-addon" min="0" max="99" number-mask=""
									id="form_div_startdate_error_stockreg" style="display: none;"><i
									class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="<spring:message code="stockregisterreport.startdate.error"></spring:message>"></i></span>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group" id="div_department_wise">
					<label for="department_code1" class="col-sm-2 control-label"><spring:message
							code="stockregisterreport.department"></spring:message><i class="text-danger">*</i></label>
					<div class="col-sm-6">
						 <div class="input-group col-sm-10 classItem1" id="div_code1">
							<input type="hidden" name="department_id" id="department_id"
								ng-model="formData.department_id"> <input type="text"
								class="form-control " name="department_wise"
								id="department_wise" ng-model="formData.department_code"
								ng-disabled="disable_all" placeholder="Code">
								<input type="text" style="margin-left: 6px;"
								id="department_wise_name" name="department_wise_name" class="form-control widthset"
								ng-model="formData.department_wise_name" disabled="disabled">
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

<!-- <script>
$(function() {
    $("#startdate").datepicker({
      dateFormat: "dd-mm-yy",
      numberOfMonths:[1,2],
      minDate: new Date(),
      // gotoCurrent: true
    });
}
</script> -->