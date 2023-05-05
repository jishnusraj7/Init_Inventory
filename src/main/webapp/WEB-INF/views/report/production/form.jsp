
<div class="" id="purchaseorder_report_form_div">

	<form class="form-horizontal  frm_div_stock_in"
		id="purchase_order_form">



		<md-radio-group ng-model="option" layout="row" ng-change="hideFun()"
			style="
    padding-left: 200px;
    padding-bottom: 30px;
">
		<md-radio-button value="2" class="md-primary">Shop</md-radio-button> <md-radio-button
			value="1">Customer </md-radio-button> <md-radio-button value="3">Summary
		</md-radio-button> </md-radio-group>


		<!-- 
		<div class="form-group" id="form_div_radio_slctrprt">
			<div class="col-sm-9">
				<div class="input-group radio_report">
					<label class="radio-inline"> <input type="radio"
						name="optradio" id="optradioSum">Summary
					</label> <label class="radio-inline"> <input type="radio"
						name="optradio" id="optradioDetail">Detail
					</label>
				</div>
			</div>

		</div> -->

		<div class="form-group" id="form_div_startdate">
			<label for="startdate" class="col-sm-2 control-label lb shps1"><spring:message
					code="stockregisterreport.startdate"></spring:message><i
				class="text-danger">*</i></label>
			<div class="col-sm-2">
				<div class="input-group col-sm-12">
					<div class="right-inner-addon" id="form_div_startdate_stkIn">
						<i class="fa fa-calendar" id="calender_icon"
							style="left: 81%; z-index: 4;"></i> <input type="text"
							class="form-control" daterange-picker1 name="startdate"
							id="startdate" ng-model="startdate" ng-disabled="disable_all"
							placeholder="" ng-change="podateChange()">
					</div>
					<span class="input-group-addon" min="0" max="99" number-mask=""
						id="form_div_startdate_error_stkIn" style="display: none;"><i
						class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
						data-placement="bottom" title=""
						data-original-title="<spring:message code="stockregisterreport.startdate.error"></spring:message>"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group" id="form_div_enddate">
			<label for="form_div_enddate" class="col-sm-2 control-label lb shps1"><spring:message
					code="stockregisterreport.enddate"></spring:message><i
				class="text-danger">*</i></label>
			<div class="col-sm-2">
				<div class="input-group col-sm-12">
					<div class="right-inner-addon" id="form_div_enddate_stkIn">
						<i class="fa fa-calendar" id="calender_icon"
							style="left: 81%; z-index: 4;"></i> <input type="text"
							class="form-control" daterange-picker1 name="enddate" id="enddate"
							ng-model="enddate" ng-disabled="disable_all" placeholder=""
							ng-change="podateChange()">
					</div>
					<span class="input-group-addon" min="0" max="99" number-mask=""
						id="form_div_enddate_error_stkIn" style="display: none;"><i
						class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
						data-placement="bottom" title=""
						data-original-title="<spring:message code="stockregisterreport.enddate.error"></spring:message>"></i></span>
				</div>
			</div>
			<div class="text-danger" id="divErrormsg2"></div>
		</div>





		<div class="main_list_div" ng-show="summary">

			<div class="col-sm-2 shps1">
				<label for="name" class="control-label "> {{labelname}} </label>
			</div>

			<div class="shops_div">
				<div class="shop_div_hdr">

					<!-- <md-checkbox type="checkbox" ng-model="is_active"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="setasactive" class="chck_box_div" ng-click="hai()"></md-checkbox> -->
					<input type="checkbox" ng-model="is_active" class="chck_box_div"
						ng-click="moveShop()"> <input type="search"
						class="shop_div_hdr_input" placeholder="Search..."
						aria-controls="DataTables_Table_0" ng-model="serch" 
						ng-change="getFilteredShops(shops, serch)">
				</div>
				<div class="shop_div_cnt">
					<label ng-repeat="role in shops | filter:serch | orderBy: 'name'"
						class="shop_div_cnt_label"> <input type="checkbox" ng-click="unselectshopItem();"
						data-checklist-model="user.roles" data-checklist-value="role"
						class="shop_div_rl_chkbx">
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
						ng-click="moveShopSelect()"></label>
					<!-- <md-checkbox type="checkbox" ng-model="is_active_select"
							ng-true-value="true" ng-false-value="false"
							ng-disabled="disable_all" aria-label="setasactive" class="chck_box_div" ng-click="haiSelect()"></md-checkbox> -->
				</div>
				<div class="shop_div_cnt">
					<label ng-repeat="role1 in selectedShopList | orderBy: 'name'"
						class="shop_div_cnt_label"> <input type="checkbox"  
						data-checklist-model="userSelect.roles"
						data-checklist-value="role1" class="shop_div_rl_chkbx"
						ng-click="unselectshopItemSelect();">
					<div class="shop_rl_name">{{role1.name }}</div>
					</label>
				</div>

			</div>

		</div>
		
		
		
		
				<div class="main_list_div botmargin" ng-show="ItmShw">

					<div class="col-sm-2 shps1">
						<label for="name" class="control-label "> Stock Item </label>
					</div>

					<div class="shops_div">
						<div class="shop_div_hdr">


							<input type="checkbox" ng-model="is_active_stock" class="chck_box_div"
								ng-click="moveItemStock()"> <input type="search"
								class="shop_div_hdr_input" placeholder="Search..."
								aria-controls="DataTables_Table_0" ng-model="serchStock" ng-change="dataFetchStock()">
								
								<!-- <button  ng-click="dataFetch()" class="clickbtn">clk</button> -->
						</div>
						<div class="shop_div_cnt">
							<label ng-repeat="role in filterItemData| orderBy: 'name'"
								class="shop_div_cnt_label"> <input type="checkbox" ng-click="unselectstockItem();"
								data-checklist-model="userItem.roles" data-checklist-value="role"
								class="shop_div_rl_chkbx">
							<div class="shop_rl_name">{{role.name }}</div>
							</label>
						</div>
					</div>



					<div id="LeftRightBtn" class="shp_main_btn" >
						<input id="btnRight" type="button" value=">>"
							ng-click="btnRightStock()" /> <input id="btnLeft" type="button"
							value="<<" ng-click=" btnLeftStock()" />
					</div>


					<div class="selected_shop_div shops_div">
						<div class="shop_div_hdr">
							<label class="shop_div_cnt_label"> <input type="checkbox"
								ng-model="is_active_select_stock" class="chck_box_div"
								ng-click="moveItemSelectStock()"></label>

						</div>
						<div class="shop_div_cnt">
							<label ng-repeat="role1 in selectedItemList | orderBy: 'name'"
								class="shop_div_cnt_label"> <input type="checkbox"
								data-checklist-model="userSelectItem.roles"
								data-checklist-value="role1" class="shop_div_rl_chkbx"
								ng-click="unselectstockItemSelect();">
							<div class="shop_rl_name">{{role1.name }}</div>
							</label>
						</div>

					</div>

				</div>

		
		
		
		
		
		
		

		<!-- <div class="form-group" id="form_div_item_id" ng-show="ItmShw">
			<label for="item_id" class="col-sm-2 control-label lb shps1"><spring:message
					code="itemstock.stockitem"></spring:message></label>
			<div class="" id="form_itemData">
				<div class="input-group classItem1 item_view">
					<input type="text" class="form-control" id="item_id" name="item_id"
						ng-model="itemList.code" table-autocomplete><input
						type="text" style="margin-left: 6px;" id="itemname"
						name="itemname" class="form-control" ng-model="itemList.itemname"
						disabled="disabled"><input type="hidden" class=""
						id="stock_item_id" name="stock_item_id"
						ng-model="itemList.stock_item_id"> <i
						class="fa fa-plus-circle pos" aria-hidden="true"
						id="createsortrow" ng-click="addNewItem()" role="button"
						tabindex="0" ng-disabled="disable_all"></i>
				</div>
				<div class="col-sm-3" id="msgDiv">{{errMsg}}</div>


			</div>

		</div> -->

		<!-- <div class="main_choice" style="float:left">
			<div ng-repeat='choItm in choicesItem' class="item_view chice" style="float: left">
				
		<label for="item_id" class="col-sm-2 control-label lb shps1">&nbsp;</label>
				<input type="hidden" name="choice_id" id="choice_id"
					ng-model="choItm.id" ng-disabled="true"> <input type="text"
					name="choice_code" id="choice_code" ng-model="choItm.code"
					ng-disabled="true"> <input type="text" name="choice_name"
					id="choice_name" ng-model="choItm.name" ng-disabled="true">

				<i ng-disabled="disable_all" class="fa fa-minus-circle"
					aria-hidden="true" ng-click='removeItem($index)'></i>

			</div>
		</div> -->



	</form>
</div>




