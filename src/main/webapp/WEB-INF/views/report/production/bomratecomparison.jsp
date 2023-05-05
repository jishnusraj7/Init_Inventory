
<div class="" id="bomratecomparison_form_div">
	<%-- <link rel="stylesheet"
		href="<c:url value="/resources/mrp/report/prodbom/css/prodbom.css"/>"> --%>

	<form class="form-horizontal " id="bomratecomparison_form">

		<div class="form-group" id="form_div_item_category_id">
			<label for="parent_id" class="col-sm-2 control-label lb"><spring:message
					code="itemmaster.item_category_id"></spring:message></label>
			<div class="col-sm-4">
				<div class="input-group col-sm-12">
					<select class="form-control itmCat"
								style="width: 300px !important;" id="item_category_id"
								name="item_category_id"
								ng-options="v.id as v.name for v in sampleItemCtgry"
								id="item_category_id" ng-model="formData.item_category_id">
							</select>
				</div>
			</div>
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
						class="shop_div_rl_chkbx" ng-click="unselectstockItem()">
						<div class="shop_rl_name">{{role.name }}</div>
					</label>
				</div>
			</div>



			<div id="LeftRightBtn" class="shp_main_btn">
				<input id="btnRight" type="button" value=">>" ng-click="btnRight()" />
				<input id="btnLeft" type="button" value="<<" ng-click=" btnLeft()" />
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

		<!-- <div id="msg" class="text-danger"></div> -->


	</form>
</div>





