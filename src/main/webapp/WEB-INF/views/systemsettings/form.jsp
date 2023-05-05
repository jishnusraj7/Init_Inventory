<div class="" id="item_category_form_div">
	<form class="form-horizontal" id="item_category_form">
		<div class="">

			<input type="hidden" id="id" name="id" value=""
				ng-model="formData.id">

			<div class="form_list_con  " style="background-color: #C1C1C1;">
				<p>
					<spring:message code="company.profile.regional"></spring:message>
				</p>
			</div>

			<div class="form-group" id="form_div_date_format">
				<label for="date_format" class="col-sm-2 control-label"><spring:message
						code="company.profile.dateformat"></spring:message> <i
					class="text-danger">*</i></label>

				<div class="col-sm-3">
					<div class="input-group ">
						<select name="date_format" id="date_format"
							ng-model="formData.date_format" ng-disabled="disable_all"
							class="form-control required">
							<option value="" selected>select</option>
							<option value="0">DD-MM-YYYY</option>
							<option value="1">MM-DD-YYYY</option>
							<option value="2">YYYY-MM-DD</option>
						</select><span class="input-group-addon" id="form_div_date_format_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="company.profile.error.dateformat"></spring:message>"></i></span>
					</div>
				</div>
			</div>

			<div class="form-group" id="form_div_date_separator">
				<label for="date_separator" class="col-sm-2 control-label"><spring:message
						code="company.profile.dateseparator"></spring:message> <i
					class="text-danger">*</i></label>

				<div class="col-sm-3">
					<div class="input-group field_div">
						<select name="date_separator" id="date_separator"
							ng-disabled="disable_all" ng-model="formData.date_separator"
							class="form-control required">
							<option value="" selected>select</option>

							<option value="/">/</option>
							<option value=".">.</option>
							<option value="-">-</option>
						</select><span class="input-group-addon" id="form_div_date_separator_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="company.profile.error.dateseparator"></spring:message>"></i></span>
					</div>
				</div>

			</div>
			<div class="form-group" id="form_div_time_format">
				<label for="time_format" class="col-sm-2 control-label"><spring:message
						code="company.profile.timeformat"></spring:message> <i
					class="text-danger">*</i></label>

				<div class="col-sm-3">
					<div class="input-group field_div">
						<select name="time_format" id="time_format"
							ng-disabled="disable_all" ng-model="formData.time_format"
							class="form-control required">
							<option value="" selected>select</option>
							<option value="0">HH:mm:ss</option>
							<option value="1">H:mm:ss</option>
							<option value="2">HH:mm</option>
							<!-- 							<option value="3">HH'h'mm</option>
 -->
						</select><span class="input-group-addon" id="form_div_time_format_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="company.profile.error.timeformat"></spring:message>"></i></span>
					</div>
				</div>

			</div>
			<div class="form-group" id="form_div_timezone">
				<label for="time_zone" class="col-sm-2 control-label"><spring:message
						code="company.profile.timezone">
					</spring:message> <!--<i
					class="text-danger">*</i>--></label>

				<div class="col-sm-3">
					<div class="input-group field_div">
						<select name="time_zone" id="time_zone" ng-disabled="disable_all"
							ng-model="formData.time_zone" class="form-control">
							<option value="" selected>select</option>
							<option value="1">IST UTC + 5:30</option>
							<option value="2">JST UTC + 9</option>
						</select><span class="input-group-addon" id="form_div_timezone_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="company.profile.error.timezone"></spring:message>"></i></span>
					</div>
				</div>

			</div>

			<div class="form-group" id="form_div_Week_start">
				<label for="default_currency_id" class="col-sm-2 control-label"><spring:message
						code="company.profile.weekstartson"></spring:message><i
					class="text-danger">*</i></label>

				<div class="col-sm-3">
					<div class="input-group field_div col-sm-3">

						<select name="Week_starts" id="Week_start"
							ng-model="formData.week_start" ng-disabled="disable_all"
							class="form-control required">
							<option value="" selected>select</option>
							<option value="1">Monday</option>
							<option value="2">Tuesday</option>
							<option value="3">Wednesday</option>
							<option value="4">Thursday</option>
							<option value="5">Friday</option>
							<option value="6">Saturday</option>
							<option value="7">Sunday</option>

						</select> <span class="input-group-addon" id="form_div_Week_start_error"
							style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="week cannot be blank"></i></span>
					</div>
				</div>


			</div>
			<%-- <div class="form-group" id="form_div_default_currency_id">
				<label for="default_currency_id" class="col-sm-2 control-label"><spring:message
						code="company.profile.def_currency"></spring:message> <i
					class="text-danger">*</i></label>

				<div class="col-sm-3">
					<div class="input-group field_div">
						<select name="default_currency_id" id="default_currency_id"
							ng-options="v.id as v.name for v in currencyDetails"
							ng-disabled="disable_all" ng-model="formData.default_currency_id"
							class="form-control">
						</select><span class="input-group-addon"
							id="form_div_default_currency_id_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="company.profile.error.def_currency"></spring:message>"></i></span>
					</div>
				</div>


			</div> --%>

			<div class="form-group " id="form_div_decimal_places">
				<label for="decimal_places " class="col-sm-2 control-label"><spring:message
						code="currencydetails.decimal_places"></spring:message> <i
					class="text-danger">*</i></label>
				<div class="col-sm-1">
					<div class="input-group small_ipt">
						<input type="text" class="form-control algn_rgt required"
							name="decimal_places" id="decimal_places"
							ng-model="formData.decimal_places" ng-disabled="disable_all"
							maxlength="1" placeholder="" numbers-only> <span
							class="input-group-addon" min="0" max="4" number-mask=""
							id="form_div_decimal_places_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="<spring:message code="currencydetails.error.decimal_places"></spring:message>"></i></span>
					</div>
				</div>
			</div>
			
			<div class="form-group" id="form_div_default_customer_type">
				<label for="default_customer_type" class="col-sm-2 control-label"><spring:message
						code="company.profile.default_customer_type"></spring:message> </label>
				<div class="col-sm-3">
					<div class="input-group">
						<select class="form-control selectpicker" id="parent_id"
							name="default_customer_type" ng-options="v.id as v.name for v in customer_types"
							id="default_customer_type" ng-model="formData.default_customer_type"
							ng-disabled="disable_all">
						</select>
					</div>
				</div>
			</div>
			
			
			
			<div class="form-group" id="form_div_is_direct_stock_entry" ng-show="stock_entry">
				<label for="is_direct_stock_entry" class="col-sm-2 control-label">Stock Entry</i></label>

				<div class="col-sm-3">
					<div class="input-group field_div col-sm-3">

						<select name="is_direct_stock_entry" id="is_direct_stock_entry"
							ng-model="formData.is_direct_stock_entry" ng-disabled="disable_all"
							class="form-control ">
							
							<option value="0">PO</option>
							<option value="1">Direct</option>
							<option value="2">Both</option>
							

						</select>
					</div>
				</div>


			</div>
			
			<div id="msg" class="text-danger"></div>


			<!-- 		<div class="form-group" id="form_div_decimal_places">
				<label for="default_decimal_places" class="col-sm-2 control-label">
					<spring:message code="company.profile.decimalplaces"></spring:message><i
					class="text-danger">*</i>
				</label>

				<div class="col-sm-1">
					<div class="input-group field_div" style="width: 100px;">
						<input type="text" class="form-control required"
							name="decimal_places" ng-disabled="disable_all" maxlength="3"
							id="decimalplaces" ng-model="formData.decimal_places"
							ng-disabled="disable_all" placeholder="Decimal Places"
							valid-number> <span class="input-group-addon"
							id="form_div_decimalplaces_error" style="display: none;"><i
							class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="Decimal Place cannot be Blank"></i></span>
					</div>
				</div>

			</div> -->

			<div>


				<div class="form_list_con  " style="background-color: #C1C1C1;">
					<p>
						<spring:message code="company.profile.finacialyear"></spring:message>
					</p>
				</div>


				<div class="form-group " id="form_div_financial_month">
					<label for="financial_month" class="col-sm-2 control-label">
						<spring:message code="company.profile.businessyear"></spring:message>
						<i class="text-danger">*</i>
					</label>
					<!-- <label for="supplier" class="col-sm-1	 control-label"> <spring:message
							code="company.profile.month"></spring:message> 
					</label> -->
					<div class="col-sm-2">
						<div class="input-group">

							<select name="time_format" id="financial_month"
								ng-model="formData.financial_month" ng-disabled="disable_all"
								class="form-control required">
								<option value="" selected>select</option>
								<option value="1">January</option>
								<option value="2">february</option>
								<option value="3">March</option>
								<option value="4">April</option>
								<option value="5">May</option>
								<option value="6">June</option>
								<option value="7">July</option>
								<option value="8">August</option>
								<option value="9">September</option>
								<option value="10">October</option>
								<option value="11">November</option>
								<option value="12">December</option>

							</select><span class="input-group-addon"
								id="form_div_financial_month_error" style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="company.profile.error.financial_month"></spring:message>"></i></span>

						</div>

					</div>

				</div>



			</div>

			<div>
				<div class="form_list_con  " style="background-color: #C1C1C1;">
					<p>
						<spring:message code="systemSetting.label.mailSettings"></spring:message>

					</p>
				</div>






				<div class="form-group" id="form_div_smtp_server">
					<label for="tin_no" class="col-sm-2 control-label"><spring:message
							code="systemSetting.label.smtpServer"></spring:message></label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<input type="text" class="form-control" name="smtp_server"
								id="smtp_server" maxlength="100" ng-model="formData.smtp_server"
								ng-disabled="disable_all" placeholder="SMTP Server">
						</div>
					</div>
				</div>


				<div class="form-group" id="form_div_smtp_port">
					<label for="tin_no" class="col-sm-2 control-label"><spring:message
							code="systemSetting.label.smtpPort"></spring:message> </label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<input type="text" class="form-control" name="smtp_port"
								id="smtp_port" maxlength="10" ng-model="formData.smtp_port"
								ng-disabled="disable_all" placeholder="SMTP Port">
						</div>
					</div>
				</div>

				<div class="form-group" id="form_div_emailid">
					<label for="tin_no" class="col-sm-2 control-label"> <!-- <spring:message
						code="systemSetting.label.mailId"></spring:message>  --> <spring:message
							code="systemSetting.label.userid"></spring:message>
					</label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<input type="text" class="form-control email" name="tinNo"
								id="emailid" maxlength="50" ng-model="formData.smtp_mailid"
								ng-disabled="disable_all" placeholder="Mail ID"><span
								class="input-group-addon" id="form_div_emailid_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="email not valid"></i></span>
						</div>
					</div>
				</div>





				<div class="form-group" id="form_div_smtp_password">
					<label for="tin_no" class="col-sm-2 control-label"><spring:message
							code="users.password"></spring:message> </label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<input type="text" class="form-control" name="smtp_password"
								id="smtp_password" maxlength="50"
								ng-model="formData.smtp_password" ng-disabled="disable_all"
								placeholder="Password">
						</div>
					</div>
				</div>






			</div>





			<div>
				<div class="form_list_con  " style="background-color: #C1C1C1;">
					<p>
						<spring:message code="systemSetting.label.smsSettings"></spring:message>

					</p>
				</div>






				<div class="form-group" id="form_div_sms_web_service">
					<label for="tin_no" class="col-sm-2 control-label"><spring:message
							code="systemSetting.label.webService"></spring:message> </label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<input type="text" class="form-control" name="sms_web_service"
								id="sms_web_service" maxlength="100"
								ng-model="formData.sms_web_service" ng-disabled="disable_all"
								placeholder="WEB Service">
						</div>
					</div>
				</div>




				<div class="form-group" id="form_div_sms_userid">
					<label for="tin_no" class="col-sm-2 control-label"><spring:message
							code="systemSetting.label.userid"></spring:message></label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<input type="text" class="form-control" name="sms_userid"
								id="sms_userid" maxlength="50" ng-model="formData.sms_userid"
								ng-disabled="disable_all" placeholder="User ID">
						</div>
					</div>
				</div>

				<div class="form-group" id="form_div_sms_password">
					<label for="tin_no" class="col-sm-2 control-label"><spring:message
							code="users.password"></spring:message></label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<input type="text" class="form-control" name="sms_password"
								id="sms_password" maxlength="50"
								ng-model="formData.sms_password" ng-disabled="disable_all"
								placeholder="Password">
						</div>
					</div>
				</div>
			</div>

			<div>
				<div class="form_list_con  " style="background-color: #C1C1C1;">
					<p>
						<spring:message code="systemSetting.label.paymentModes"></spring:message>

					</p>
				</div>

				<input type="hidden" name="paymentId" model="paymentModes.id">
				<div class="form-group tablpaddTop">
					<div class="col-sm-12 ">

						<table id="paymentdetails"
							class="table table-bordered table-striped">
							<tbody>
								<tr>
									<th>Payment Modes</th>
									<th>Account Code</th>
									<th>Refundable</th>
									<th>Refund Account Code</th>
									<th>Refund Method</th>
									<th>Rounding</th>
								</tr>
								<tr>
									<td>
										<div class="form-group" id="form_div_can_pay_by_cash">

											<div class="col-sm-3">
												<div class="input-group" id="can_pay_by_cash">

													<md-checkbox type="checkbox"
														ng-model="paymentModes.can_pay_by_cash"
														ng-true-value="true" ng-false-value="false"
														ng-disabled="disable_all" aria-label="can_pay_by_cash"
														class="chck_box_div" ng-click="showCashDet()"></md-checkbox>




												</div>
											</div>
											<label for="can_pay_by_cash" class="col-sm-3 control-label">Cash</label>
										</div>

									</td>
									<td>

										<div class="col-sm-12" ng-show="cashAct">
											<div class="input-group">
												<input  type="text" class="form-control "
													name="cash_account_no" maxlength="50" id="cash_account_no"
													ng-model="paymentModes.cash_account_no"
													ng-disabled="disable_all" placeholder="">
											</div>
										</div>







									</td>
									<td>
										<!-- <div class="form-group" id="form_div_can_pay_by_card"> -->

										<div class="col-sm-3" ng-show="cashRefund">
											<div class="input-group" id="can_cash_refundable">

												<md-checkbox type="checkbox"
													ng-model="paymentModes.can_cash_refundable"
													ng-true-value="true" ng-false-value="false"
													ng-disabled="disable_all" aria-label="can_cash_refundable"
													class="chck_box_div" ng-click="showCashRefundDet()"></md-checkbox>




											</div>
										</div> <!-- </div> -->

									</td>
									<td>

										<div class="col-sm-12" ng-show="cashRefundAct">
											<div class="input-group">
												<input  type="text" class="form-control "
													name="cash_refund_account_no" maxlength="50"
													id="cash_refund_account_no"
													ng-model="paymentModes.cash_refund_account_no"
													ng-disabled="disable_all" placeholder="">
											</div>
										</div>

									</td>
									<td><label ng-show="cashLabel">Cash</label></td>
									<td>

										<div class="col-sm-3" ng-show="cashRound">
											<div class="input-group" id="can_cash_round">

												<md-checkbox type="checkbox"
													ng-model="paymentModes.can_cash_round" ng-true-value="true"
													ng-false-value="false" ng-disabled="true"
													aria-label="can_cash_round" class="chck_box_div"></md-checkbox>




											</div>
										</div>

									</td>
								</tr>
								<tr>
									<td>
										<div class="form-group" id="form_div_can_pay_by_company">

											<div class="col-sm-3">
												<div class="input-group" id="can_pay_by_company">

													<md-checkbox type="checkbox"
														ng-model="paymentModes.can_pay_by_company"
														ng-true-value="true" ng-false-value="false"
														ng-disabled="disable_all" aria-label="can_pay_by_company"
														class="chck_box_div" ng-click="showCompanyDet()"></md-checkbox>




												</div>
											</div>
											<label for="can_pay_by_company"
												class="col-sm-3 control-label">Company</label>
										</div>


									</td>
									<td></td>
									<td>

										<div class="col-sm-3" ng-show="companyRefund">
											<div class="input-group" id="can_company_refundable">

												<md-checkbox type="checkbox"
													ng-model="paymentModes.can_company_refundable"
													ng-true-value="true" ng-false-value="false"
													ng-disabled="disable_all"
													aria-label="can_company_refundable" class="chck_box_div" ng-click="showCompanyRefundDet()"></md-checkbox>




											</div>
										</div>

									</td>
									<td></td>
									<td><label ng-show="companyLabel">Company</label></td>
									<td>

										<div class="col-sm-3" ng-show="companyRound">
											<div class="input-group" id="can_company_round">

												<md-checkbox type="checkbox"
													ng-model="paymentModes.can_company_round"
													ng-true-value="true" ng-false-value="false"
													ng-disabled="disable_all" aria-label="can_company_round"
													class="chck_box_div"></md-checkbox>




											</div>
										</div>


									</td>
								</tr>
								<tr>
									<td>
										<div class="form-group" id="form_div_can_pay_by_vouchers">

											<div class="col-sm-3">
												<div class="input-group" id="can_pay_by_vouchers">

													<md-checkbox type="checkbox"
														ng-model="paymentModes.can_pay_by_vouchers"
														ng-true-value="true" ng-false-value="false"
														ng-disabled="disable_all" aria-label="can_pay_by_vouchers"
														class="chck_box_div" ng-click="showVoucherDet()"></md-checkbox>




												</div>
											</div>
											<label for="can_pay_by_vouchers"
												class="col-sm-3 control-label">Vouchers </label>
										</div>


									</td>
									<td></td>
									<td>

										<div class="col-sm-3" ng-show="voucherRefund">
											<div class="input-group" id="can_voucher_refundable">

												<md-checkbox type="checkbox"
													ng-model="paymentModes.can_voucher_refundable"
													ng-true-value="true" ng-false-value="false"
													ng-disabled="disable_all"
													aria-label="can_voucher_refundable" class="chck_box_div" ng-click="showVoucherRefundDet()"></md-checkbox>




											</div>
										</div>



									</td>
									<td></td>
									<td><label ng-show="voucherLabel">Cash </label></td>
									<td>

										<div class="col-sm-3" ng-show="voucherRound">
											<div class="input-group" id="can_voucher_round">

												<md-checkbox type="checkbox"
													ng-model="paymentModes.can_voucher_round"
													ng-true-value="true" ng-false-value="false"
													ng-disabled="true" aria-label="can_voucher_round"
													class="chck_box_div"></md-checkbox>




											</div>
										</div>

									</td>
								</tr>
								<tr>
									<td>

										<div class="form-group" id="form_div_can_pay_by_card">

											<div class="col-sm-3">
												<div class="input-group" id="can_pay_by_card">

													<md-checkbox type="checkbox"
														ng-model="paymentModes.can_pay_by_card"
														ng-true-value="true" ng-false-value="false"
														ng-disabled="disable_all" aria-label="can_pay_by_card"
														class="chck_box_div" ng-click="showCardDet()"></md-checkbox>




												</div>
											</div>
											<label for="can_pay_by_card" class="col-sm-7 control-label">Credit
												Card/Debit Card</label>
										</div>


									</td>
									<td></td>
									<td>
										<div class="col-sm-3" ng-show="cardRefund">
											<div class="input-group" id="can_card_refundable">

												<md-checkbox type="checkbox"
													ng-model="paymentModes.can_card_refundable"
													ng-true-value="true" ng-false-value="false"
													ng-disabled="disable_all" aria-label="can_card_refundable"
													class="chck_box_div" ng-click="showCardRefundDet()"></md-checkbox>




											</div>
										</div>
									</td>
									<td></td>
									<td><label ng-show="cardLabel">Card</label></td>
									<td>
										<div class="col-sm-3" ng-show="cardRound">
											<div class="input-group" id="can_card_round">

												<md-checkbox type="checkbox"
													ng-model="paymentModes.can_card_round" ng-true-value="true"
													ng-false-value="false" ng-disabled="Card_disable"
													aria-label="can_card_round" class="chck_box_div"></md-checkbox>




											</div>
										</div>
									</td>
								</tr>

							</tbody>
						</table>




					</div>
				</div>





			</div>

















			<div>
				<div class="form_list_con  " style="background-color: #C1C1C1;">
					<p>
						<spring:message code="systemSetting.label.taxSettings"></spring:message>

					</p>
				</div>

				<input type="hidden" name="taxParamId" model="formData2.id">




				<div class="form-group" id="form_div_tax1_name">
					<label for="tax1_name" class="col-sm-2 control-label"><spring:message
							code="systemSetting.label.tax1_name"></spring:message> </label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<input type="text" class="form-control required" name="tax1_name"
								id="tax1_name" maxlength="10" ng-model="formData2.tax1_name"
								ng-disabled="disable_all" placeholder="Tax Name1">
								<span class="input-group-addon"
								id="form_div_tax1_name_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="Tax Name1 cannot be blank "></i></span>
						</div>
					</div>
				</div>

				<div class="form-group" id="form_div_tax2">
					<label for="tax2_name" class="col-sm-2 control-label"><spring:message
							code="systemSetting.label.tax2_name"></spring:message> </label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<input type="text" class="form-control" name="tax2_name"
								id="tax2_name" maxlength="10" ng-model="formData2.tax2_name"
								ng-disabled="disable_all" placeholder="Tax Name2">
						</div>
					</div>
				</div>


				<div class="form-group" id="form_div_tax3">
					<label for="tax3_name" class="col-sm-2 control-label"><spring:message
							code="systemSetting.label.tax3_name"></spring:message> </label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<input type="text" class="form-control" name="tax3_name"
								id="tax3_name" maxlength="10" ng-model="formData2.tax3_name"
								ng-disabled="disable_all" placeholder="Tax Name3">
						</div>
					</div>
				</div>




				<div class="form-group" id="form_div_default_taxation_method">
					<label for="default_taxation_method"
						class="col-sm-2 control-label "><spring:message
							code="systemSetting.label.default_taxation_method"></spring:message></label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<select class="form-control required"
								id="default_taxation_method" name="default_taxation_method"
								ng-model="formData2.default_taxation_method"
								ng-disabled="disable_all">
								<option value="" selected>select</option>
								<option value="0">Inclusive of Tax</option>
								<option value="1">Exclusive of Tax</option>

							</select> <span class="input-group-addon"
								id="form_div_default_taxation_method_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="company.profile.error.default_taxation_method"></spring:message>"></i></span>


						</div>
					</div>
				</div>

				<div class="form-group"
					id="form_div_default_purchase_taxation_method">
					<label for="default_purchase_taxation_method"
						class="col-sm-2 control-label "><spring:message
							code="systemSetting.label.default_purchase_taxation_method"></spring:message></label>
					<div class="col-sm-3">
						<div class="input-group suppliertext">
							<select class="form-control required"
								id="default_purchase_taxation_method"
								name="default_purchase_taxation_method"
								ng-model="formData2.default_purchase_taxation_method"
								ng-disabled="disable_all">
								<option value="" selected>select</option>
								<option value="0">Inclusive of Tax</option>
								<option value="1">Exclusive of Tax</option>

							</select> <span class="input-group-addon"
								id="form_div_default_purchase_taxation_method_error"
								style="display: none;"><i
								class="fa fa-question-circle red-tooltip" data-toggle="tooltip"
								data-placement="bottom" title=""
								data-original-title="<spring:message code="company.profile.error.default_purchase_taxation_method"></spring:message>"></i></span>


						</div>
					</div>
				</div>



			</div>






			<div ng-show="shopSub">
				<div class="form_list_con  " style="background-color: #C1C1C1;">
					<p>
						<spring:message code="systemSetting.label.shopSubscription"></spring:message>

					</p>
				</div>

				<input type="hidden" name="shopSubscriptionId"
					model="envSerttings.id">

				<div class="form-group" id="form_div_subscription">
					<div class="col-sm-12 addmargin">
						<div class="col-sm-2 addstyle">
							<label for="subscription">Enable Shop Subscription</label>
						</div>

						<div class="col-sm-4">
							<md-radio-group ng-model="envSerttings.is_publish" layout="row"
								ng-disabled="disable_all"> <md-radio-button
								value="0" class="md-primary">On</md-radio-button> <md-radio-button
								value="1">Off </md-radio-button> </md-radio-group>

						</div>
					</div>
				</div>



			</div>







			<div>
				<div class="form_list_con  " style="background-color: #C1C1C1;">
					<p>
						<spring:message code="systemSetting.label.billParameters"></spring:message>

					</p>
				</div>

				<input type="hidden" name="billParametersId"
					model="billParameters.id">


				<div class="form-group"
					id="form_show_tax_summary">
				
									
									<label for="show_tax_summary"
						class="col-sm-2 control-label ">Show tax summary</label>
							
							
				<div class="col-sm-3">
						<div class="input-group suppliertext">
						<select name="show_tax_summary" class="form-control" id="show_tax_summary" ng-model="billParameters.show_tax_summary" >
                            <option value="">Select</option>
                                                           <option value="0" selected="selected">Summary</option>
                                                            <option value="1">Tax Table</option>
                                                            <option value="2">None</option>
                                    
                      </select> 


						</div>
					</div>
				</div>
		

				<div class="col-sm-12">
					<div class="col-sm-6">
						<div class="col-sm-2"></div>
						<div class="col-sm-10">
							<div class="col-sm-12">
								<label>Bill Headers</label>
							</div>
							<div class="col-sm-12 paddingTp">


								<div class="col-sm-3 paddingTpH">
									<label>Bill Hdr1*</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group" id="form_div_bill_hdr1">
										<input type="text" class="form-control required "
											name="bill_hdr1" id="bill_hdr1"
											ng-model="billParameters.bill_hdr1" maxlength="40"
											ng-disabled="disable_all" placeholder=""> <span
											class="input-group-addon" min="0" max="99" number-mask=""
											id="form_div_bill_hdr1_error" style="display: none;"><i
											class="fa fa-question-circle red-tooltip"
											data-toggle="tooltip" data-placement="bottom" title=""
											data-original-title="<spring:message code="common.error.billhd1"></spring:message>"></i></span>
									</div>
								</div>
							</div>


							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Hdr2</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_hdr2"
											id="bill_hdr2" ng-model="billParameters.bill_hdr2"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>


							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Hdr3</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_hdr3"
											id="bill_hdr3" ng-model="billParameters.bill_hdr3"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Hdr4</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_hdr4"
											id="bill_hdr4" ng-model="billParameters.bill_hdr4"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>


							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Hdr5</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_hdr5"
											id="bill_hdr5" ng-model="billParameters.bill_hdr5"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>


							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Hdr6</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_hdr6"
											id="bill_hdr6" ng-model="billParameters.bill_hdr6"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>


							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Hdr7</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_hdr7"
											id="bill_hdr7" ng-model="billParameters.bill_hdr7"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Hdr8</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_hdr8"
											id="bill_hdr8" ng-model="billParameters.bill_hdr8"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Hdr9</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_hdr9"
											id="bill_hdr9" ng-model="billParameters.bill_hdr9"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Hdr9</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_hdr10"
											id="bill_hdr10" ng-model="billParameters.bill_hdr10"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>



						</div>



					</div>
					<div class="col-sm-6">
						<div class="col-sm-1"></div>
						<div class="col-sm-11">
							<div class="col-sm-12">
								<label>Bill Footers</label>
							</div>
							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Footer1*</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group" id="form_div_bill_footer1">
										<input type="text" class="form-control required "
											name="bill_footer1" id="bill_footer1"
											ng-model="billParameters.bill_footer1" maxlength="40"
											ng-disabled="disable_all" placeholder=""> <span
											class="input-group-addon" min="0" max="99" number-mask=""
											id="form_div_bill_footer1_error" style="display: none;"><i
											class="fa fa-question-circle red-tooltip"
											data-toggle="tooltip" data-placement="bottom" title=""
											data-original-title="<spring:message code="common.error.footer1"></spring:message>"></i></span>
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Footer2</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_footer2"
											id="bill_footer2" ng-model="billParameters.bill_footer2"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>


							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Footer3</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_footer2"
											id="bill_footer3" ng-model="billParameters.bill_footer3"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Footer4</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_footer4"
											id="bill_footer4" ng-model="billParameters.bill_footer4"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Footer5</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_footer5"
											id="bill_footer5" ng-model="billParameters.bill_footer5"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Footer6</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_footer6"
											id="bill_footer6" ng-model="billParameters.bill_footer6"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Footer7</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_footer7"
											id="bill_footer7" ng-model="billParameters.bill_footer7"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Footer8</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_footer8"
											id="bill_footer8" ng-model="billParameters.bill_footer8"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Footer9</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_footer2"
											id="bill_footer9" ng-model="billParameters.bill_footer9"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>

							<div class="col-sm-12 paddingTp">
								<div class="col-sm-3 paddingTpH">
									<label>Bill Footer10</label>
								</div>
								<div class="col-sm-9">
									<div class="input-group">
										<input type="text" class="form-control  " name="bill_footer2"
											id="bill_footer10" ng-model="billParameters.bill_footer10"
											maxlength="40" ng-disabled="disable_all" placeholder="">
									</div>
								</div>
							</div>





						</div>
					</div>



				</div>

			</div>



		</div>




	</form>
</div>