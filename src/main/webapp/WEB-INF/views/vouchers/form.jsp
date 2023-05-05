<div class="" id="vouchers_form_div" ng-show="show_form">

	<form class="form-horizontal" id="vouchers_form">
		<jsp:directive.include file="../common/includes/common_form.jsp" />

		<div class="form-group" id="form_div_voucher_type">
			<label for="parent_id" class="col-sm-2 control-label lb">Voucher
				Type<i class="text-danger">*</i>
			</label>
			<div class="col-sm-5">
				<div class="input-group input-group-lg">
					<select class="form-control  code-font-size" id="voucher_type"
						name="voucher_type" ng-model="formData.voucher_type"
						ng-change="filterVoucherType()" ng-disabled="disable_all">
						<option ng-repeat="vlist in voucherTypeList" value="{{vlist.id}}">{{vlist.name}}</option>
					</select> <span class="input-group-addon" id="form_div_voucher_type_error"
						style="display: none;"><i
						class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
						data-placement="bottom" title=""
						data-original-title="Please Select a Voucher Type"></i></span>
				</div>


			</div>
			<div class="col-sm-2">
				<button type="button" ng-click="addVoucherClass()"
					ng-show="showAddBtn" ng-disabled="disable_all" id="addTypeBtn">Add</button>

				<button type="button" ng-show="!showAddBtn"
					ng-click="editVoucherClass()" ng-disabled="disable_all"
					id="editTypeBtn">Edit</button>
			</div>
		</div>

		<div class="form-group" id="form_div_voucher_value">
			<label for="value" class="col-sm-2 control-label ">Voucher
				Value<i class="text-danger">*</i>
			</label>

			<div class="col-sm-5">
				<div class="input-group input-group-lg">
					<input type="text" class="form-control required code-font-size"
						name="value" id="value" ng-model="formData.value"
						ng-disabled="disable_all"
						maxlength="${8+settings['decimalPlace']+1}" placeholder=""
						valid-number> <span class="input-group-addon"
						id="form_div_voucher_value_error" style="display: none;"><i
						class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
						data-placement="bottom" title=""
						data-original-title="Please enter voucher value"></i></span>
				</div>
			</div>

		</div>
		<div class="form-group" id="form_div_balance_cash">
			<label for="is_change_payable" class="col-sm-2 control-label">Balance
				In Cash</label>
			<div class="col-sm-5">
				<md-checkbox name="apply_theme" ng-model="is_balance_cash"
					aria-label="Checkbox 2" ng-disabled="disable_all"> </md-checkbox>
			</div>

		</div>
		<div class="form-group" id="form_div_account_code">
			<label for="value" class="col-sm-2 control-label ">Account
				Code<i class="text-danger">*</i>
			</label>

			<div class="col-sm-5">
				<div class="input-group input-group-lg">
					<input type="text" class="form-control required code-font-size"
						name="account_code" id="account_code"
						ng-model="formData.account_code" ng-disabled="disable_all"
						maxlength="250" placeholder=""> <span
						class="input-group-addon" id="form_div_account_code_error"
						style="display: none;"><i
						class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
						data-placement="bottom" title=""
						data-original-title="Please Enter Account Code"></i></span>
				</div>
			</div>

		</div>


	</form>
</div>
<div class="modal fade" id="addVchrClassModal" role="dialog">
	<div class="modal-dialog modal-md">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Voucher Class</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" id="item_category_form">
					<div class="">
						<div class="form-group">


							<div class="form-group" id="form_div_name">
								<label for="name" class="col-sm-4 control-label "> Name:
									<!-- <spring:message
						code="common.name"></spring:message> --> <i class="text-danger"></i>
								</label>

								<div class="col-sm-7">
									<div class="input-group">
										<input type="text" class="form-control " name="type_name"
											ng-disabled="!enableType" id="type_name" maxlength="50"
											ng-model="voucher_class_name" placeholder="Name"> <span
											class="input-group-addon" id="form_div_name_error"
											style="display: none;"><i
											class="fa fa-question-circle red-tooltip"
											data-toggle="tooltip" data-placement="bottom" title=""
											data-original-title="<spring:message code="common.error.name"></spring:message>"></i></span>
									</div>
								</div>

							</div>




						</div>
					</div>
				</form>
			</div>

			<div class="modal-footer">
				<div ng-show="showAddFooter">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

					<button class="btn  btn-sm btn-primary save_button savebtn"
						type="button" name="btnSave" ng-click="saveVoucherType();"
						id="btnSave" data-dismiss="modal" data-target="#importDataModal">
						 Save
					</button>
					<!-- <button class="btn  btn-sm btn-primary save_button savebtn" type="button" name="btnSave" id="btnSave" ng-click="fun_save_form()" style="display: block;">
			<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
		</button> -->
				</div>
				<div ng-show="showEditFooter">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

					<button class="btn  btn-sm btn-primary save_button savebtn"
						type="button" name="btnEdit1" id="btnEdit1" ng-click="editModalData(event)">
						 Edit
					</button>
					<button class="btn  btn-sm btn-danger save_button savebtn"
						type="button" name="btnDelete1" id="btnDelete1"
						data-dismiss="modal" data-target="#importDataModal"
						ng-click="deleteVoucherType();">
						Delete
					</button>
					<!-- <button class="btn  btn-sm btn-primary save_button savebtn" type="button" name="btnSave" id="btnSave" ng-click="fun_save_form()" style="display: block;">
			<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
		</button> -->
				</div>
			</div>
		</div>

	</div>
</div>
