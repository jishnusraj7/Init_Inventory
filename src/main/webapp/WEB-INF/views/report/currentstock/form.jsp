<div class="" id="item_stock_form_div">
	<form class="form-horizontal" id="item_stock_form">
		<div class="">
			<%-- <jsp:directive.include file="../common/includes/common_form.jsp" /> --%>

			<div class="report_main_frm_div">
				<div class="form-group" id="form_div_rd_selectrpt">
					<!-- <label for="parent_id"
						class="col-sm-2 control-label  code-lbl-font-size"><spring:message
							code="itemstock.stockitem"></spring:message></label> -->
					<div class="col-sm-2"></div>
					<div class="col-sm-9">
						<div class="input-group radio_report"
							style="width: 44%; float: left; margin: 0 auto;">
							<label class="radio-inline"> <input type="radio"
								name="optradio" id="optradio0">All
							</label> <label class="radio-inline"> <input type="radio"
								name="optradio" id="optradio2">Item Category
							</label> <label class="radio-inline"> <input type="radio"
								name="optradio" id="optradio1">Stock Item
							</label>
						</div>
					</div>

				</div>

				<!-- <div class="form-group" id="form_div_department_code">
					<label for="department_code" class="col-sm-2 control-label">Department<spring:message
							code="stockout.source"></spring:message> <i class="text-danger">*</i></label>
					<div class="col-sm-6">
						<div class=" col-sm-10 classItem1" style="padding-left: 0px;">
							<input type="hidden" name="department_id" id="department_id"
								ng-model="formData.department_id"> <input type="text"
								class="form-control " name="department_code"
								id="department_code" ng-model="formData.department_code"
								ng-disabled="disable_all" placeholder="Code">
								<input type="text" style="margin-left: 26px;"
								id="department_name" name="department_name" class="form-control widthset"
								ng-model="formData.department_name" disabled="disabled">
						</div>
					</div>

				</div>  -->



				<div class="form-group" id="form_div_item_category_id">
					<label for="parent_id"
						class="col-sm-2 control-label lb itmcatclass"><spring:message
							code="itemmaster.item_category_id"></spring:message><i
						class="text-danger">*</i></label>
					<div class="col-sm-4">
						<div class="input-group">
							<select class="form-control select_itemtype1"
								style="width: 300px !important;" id="item_category_id"
								name="item_category_id"
								ng-options="v.id as v.name for v in sampleItemCtgry"
								id="item_category_id" ng-model="formData.item_category_id">
							</select>
						</div>
					</div>
					<div id="msg" class="text-danger"></div>

				</div>




				<div id="form_div_department" class="main_list_div">

					<div class="col-sm-2 shps1">
						<label for="name" class="control-label "> Department </label>
					</div>

					<div class="shops_div">
						<div class="shop_div_hdr">


							<input type="checkbox" ng-model="is_active_dep"
								class="chck_box_div" ng-click="moveDep()"> <input
								type="search" class="shop_div_hdr_input" placeholder="Search..."
								aria-controls="DataTables_Table_0" ng-model="serchDep"
								ng-change="getFilteredDept(department, serchDep)">
						</div>
						<div class="shop_div_cnt">
							<label
								ng-repeat="role in department | filter:serchDep | orderBy: 'name'"
								class="shop_div_cnt_label"> <input type="checkbox"
								ng-click="unselectDept();" data-checklist-model="userDep.roles"
								data-checklist-value="role" class="shop_div_rl_chkbx">
								<div class="shop_rl_name">{{role.name }}</div>
							</label>
						</div>
					</div>



					<div id="LeftRightBtn" class="shp_main_btn">
						<input id="btnRight" type="button" value=">>"
							ng-click="btnRightDept()" /> <input id="btnLeft" type="button"
							value="<<" ng-click=" btnLeftDept()" />
					</div>


					<div class="selected_shop_div shops_div">
						<div class="shop_div_hdr">
							<label class="shop_div_cnt_label"> <input type="checkbox"
								ng-model="is_active_select_dept" class="chck_box_div"
								ng-click="moveDepSelect()"></label>

						</div>
						<div class="shop_div_cnt">
							<label ng-repeat="role1 in selectedDepList | orderBy: 'name'"
								class="shop_div_cnt_label"> <input type="checkbox"
								data-checklist-model="userSelectDep.roles"
								data-checklist-value="role1" class="shop_div_rl_chkbx"
								ng-click="unselectDeptSelect();">
								<div class="shop_rl_name">{{role1.name }}</div>
							</label>
						</div>

					</div>

				</div>











				<div id="form_div_stock_item" class="main_list_div" ng-show="hideit">

					<div class="col-sm-2 shps1">
						<label for="name" class="control-label "> Stock Item <i
							class="text-danger">*</i></label>
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
								class="shop_div_rl_chkbx" ng-click="unselectstockItem()">
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
							<label class="shop_div_cnt_label"> <input type="checkbox"
								ng-model="is_active_select" class="chck_box_div"
								ng-click="moveItemSelect()"></label>

						</div>
						<div class="shop_div_cnt">
							<label ng-repeat="role1 in selectedItemList | orderBy: 'name'"
								class="shop_div_cnt_label"> <input type="checkbox"
								data-checklist-model="userSelect.roles"
								data-checklist-value="role1" class="shop_div_rl_chkbx"
								ng-click="unselectstockItemSelect()">
								<div class="shop_rl_name">{{role1.name }}</div>
							</label>
						</div>

					</div>

				</div>

			</div>


		</div>

	</form>

</div>
