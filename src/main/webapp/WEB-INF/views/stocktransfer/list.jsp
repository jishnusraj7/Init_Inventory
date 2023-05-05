<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%-- <%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockDisposalReasonType"%>
<c:set var="enumValues" value="<%=stockDisposalReasonType.values()%>" /> --%>


<!DOCTYPE html>
<html lang="en">
<head>

<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />

<c:set var="canAdd"
	value="${permission.getCanAdd()&& permission.getIsAddApplicable()}"></c:set>
<c:set var="canEdit"
	value="${permission.getCanEdit() && permission.getIsEditApplicable()}"></c:set>
<c:set var="CanDelete"
	value="${permission.getCanDelete()&& permission.getIsDeleteApplicable()}"></c:set>
<c:set var="CanExcute"
	value="${permission.getCanExecute() && permission.getIsExecuteApplicable()}"></c:set>
<c:set var="CanExport"
	value="${permission.getCanExport() && permission.getIsExportApplicable()}"></c:set>

<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/stocktransfer/css/stocktransfer.css' />">

</head>
<body>
	<!-- design header template include below -->
	<jsp:directive.include file="../common/includes/subheader.jsp" />
	<jsp:directive.include
		file="../common/includes/common_module_header.jsp" />

	<!-- design left template include below -->
	<!-- Left side column. contains the logo and sidebar -->
	<jsp:directive.include file="../common/includes/leftmenu.jsp" />
	<!-- Content Wrapper. Contains page content -->

	<input type="hidden" value="0" id="show_form">
	<div class="content-wrapper header_second" ng-app="mrp_app"
		id="mrp_App_Id" ng-cloak>
		<div class="list-content-header" ng-controller="btn_ctrl">
			<!-- Content Header (Page header) -->
			<section class="content-header content-header_second">
				<h1>
					<spring:message code="stocktransfer.header"></spring:message>
				</h1>

				<div ng-hide="succ_alertMessageStatus" class="alert-box"
					id="succ_alertMessageId">{{ succ_alertMeaasge }}</div>
				<div ng-hide="err_alertMessageStatus" class="erroralert-box"
					id="err_alertMessageId">{{ err_alertMeaasge }}</div>


				<ol class="breadcrumb breadcrumb_position" ng-hide="true">
					<li><a href="#"><i class="fa fa-dashboard"></i> <spring:message
								code="common.menu.home"></spring:message></a></li>
					<li><a href="#"><spring:message code="common.menu.store"></spring:message></a></li>
					<li><a href="#"><spring:message
								code="common.menu.store.stockdisposal"></spring:message></a></li>
				</ol>
			</section>


			<div class="new_box">

				<div class="box cont_div_box">
					<jsp:directive.include file="../common/includes/common_buttons.jsp" />

				</div>
			</div>



		</div>
		<!-- Main content -->
		<section class="content" id="module_content">
			<div class="row">
				<div class="col-xs-12">


					<div class="box">
						<div class="box-header with-border">
							<h3 class="box-title"></h3>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<div class="box-header"></div>
							<div ng-controller="stocktransfer as item">
							<circle-spinner ng-show="prograssing1"></circle-spinner>
								<div id="stocktransfer_form_div">
									<md-tabs ng-show="show_table" md-dynamic-height=""
										md-border-bottom=""> <md-tab label="STOCK TRANSFER"
										md-on-select=""> <md-content
										class="md-padding  production_process_main_tab_div_sub"
										style="padding-top: 30px;">
									<div class="row form-group" id="form_div_stock_transfer_date">
										<label for="stock_transfer_date"
											class="control-label lb delevery_date">Transfer Date:<i
											class="text-danger">*</i></label>
										<div class="stock_transfer_main_tab_div_input">
											<div class="input-group">
												<div class="right-inner-addon" id="form_div_scheduler_date">
													<i class="fa fa-calendar" id="calender_icon"
														style="left: 50%; z-index: 4;"></i> <input type="text"
														class="form-control" daterange-picker
														name="stock_transfer_date" id="stock_transferring_date"
														ng-model="formData.stock_transfer_date" placeholder=""
														ng-change="getTransferData()" style="width: 67%">
												</div>
												<span class="input-group-addon" min="0" max="99"
													number-mask="" id="form_div_scheduler_date_error"
													style="display: none;"><i
													class="fa fa-question-circle red-tooltip"
													data-toggle="tooltip" data-placement="bottom" title=""
													data-original-title="Transfer date cannot be left blank"></i>
												</span>
											</div>
										</div>
									</div>
									<div class="form-group" id="div_table_show">
										<circle-spinner ng-show="prograssing1"></circle-spinner>
										<table class="table table-striped table-hover"
											id="transfer_list" ng-hide="prograssing1">
											<thead>
												<tr>
													<th></th>
													<th ng-click="sort('order_id')">TRANSFER NO<span
														class="glyphicon sort-icon" ng-show="sortKey=='order_id'"
														ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
													</th>
													<th ng-click="sort('stock_item_name')">TRANSFER DATE<span
														class="glyphicon sort-icon"
														ng-show="sortKey=='stock_item_name'"
														ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
													</th>
													<th ng-click="sort('department')">FROM - TO<span
														class="glyphicon sort-icon"
														ng-show="sortKey=='department'"
														ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
													</th>
													<th ng-click="sort('qty')">DESTINATION COMPANY<span
														class="glyphicon sort-icon" ng-show="sortKey=='qty'"
														ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
													</th>
													<th ng-click="sort('timeslot')">STATUS<span
														class="glyphicon sort-icon" ng-show="sortKey=='timeslot'"
														ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
													</th>
												</tr>
											</thead>
											<tbody>
												<tr ng-if="transferList.length === 0">
													<td colspan="6">No data available</td>
												</tr>
												<tr
													ng-repeat="item in transferList|orderBy:sortKey:reverse|filter:search">
													<td>{{$index+1}}</td>
													<td><input type="hidden" ng-model="item.order_id"
														data-dtl-id="{{item.order_id}}"> <a href=""
														ng-click="transferDetails(item,$index)">{{item.stock_transfer_no}}</a></td>
													<td>{{item.stock_transfer_date}}</td>
													<td>{{item.source_department_name}} -
														{{item.dest_department_name}}</td>
													<td>{{item.dest_company_name}}</a></td>
													<td><span ng-if="item.req_status == 0"
														style='color: green;'>PENDING</span> <span
														ng-if="item.req_status == 1" style='color: #3c8dbc;'>FINALIZED</span>
														<span ng-if="item.req_status == 2" style='color: red;'>PRINTED<span>{{item.tranfer_status}}
														</td>
												</tr>
											</tbody>
										</table>
									</div>
									</md-content></md-tab> <md-tab label="ORDERS TO TRANSFER" md-on-select=""> <md-content
										class="md-padding" style="padding-top: 30px;">

									<div class="form-group" ng-show="show_table"
										id="div_table_show">
										<!-- <table datatable="" dt-options="item.dtOptions"
										dt-columns="item.dtColumns" dt-instance="item.dtInstance"
										class="table dataClass"></table> -->
										<div class="row form-group" id="form_div_delevery_date">
											<label for="delevery_date"
												class="control-label lb delevery_date">Delivery
												Date:<i class="text-danger">*</i>
											</label>
											<div class="stock_transfer_main_tab_div_input">
												<div class="input-group">
													<div class="right-inner-addon" id="form_div_scheduledate">
														<i class="fa fa-calendar" id="calender_icon"
															style="left: 80%; z-index: 4;"></i> <input type="text"
															class="form-control" daterange-picker
															name="delevery_date" id="delevery_date"
															ng-model="formData.closing_date" placeholder=""
															ng-change="getOrderstoTransfer()"
															ng-disabled="disable_all" style="width: 100%">
													</div>
													<span class="input-group-addon" min="0" max="99"
														number-mask="" id="form_div_scheduledate_error"
														style="display: none;"><i
														class="fa fa-question-circle red-tooltip"
														data-toggle="tooltip" data-placement="bottom" title=""
														data-original-title="<spring:message code="prodprocess.deliverydate.error"></spring:message>"></i>
													</span>
												</div>
											</div>
										</div>

										<div class="row form-group" id="form_div_type_of_order"
											style="margin-left: 0px;">
											<label for="name" class="col-sm-2 control-label"
												style="width: 143px;"> Type Of Order <!-- <spring:message code="common.name"></spring:message> -->
											</label>
											<div class="radioValue">
												<md-radio-group ng-model="type_of_order"
													ng-disabled="disable_all" ng-click="loadFillData();">
												<div class="col-sm-1">
													<div class="input-group">
														<md-radio-button value="0" class="md-primary">Shops</md-radio-button>
													</div>
												</div>

												<div class="col-sm-1" style="width: 123px;">
													<div class="input-group">
														<md-radio-button value="1"> Departments
														</md-radio-button>
													</div>
												</div>
												</md-radio-group>
											</div>
										</div>
										<div class="row form-group col-sm-12"
											style="margin-left: -45px;">
											<div id="form_div_order_from" class="col-sm-6">
												<label for="name" class="control-label lb delevery_date">
													Order From: <i class="text-danger">*</i>
												</label>
												<div class="">
													<div class="input-group">

														<select class="form-control" id="cust" name="customer_id"
															ng-disabled="disable_all" style="width: 215px;"
															ng-options="v.code as v.name for v in fillData"
															ng-model="customer_id" ng-change="getOrderstoTransfer()"></select><span
															class="input-group-addon" id="form_div_cust_error"
															style="display: none;"><i
															class="fa fa-question-circle red-tooltip"
															data-toggle="tooltip" data-placement="bottom" title=""
															data-original-title="Customer cannot be blank"></i></span>
													</div>
												</div>
											</div>
										</div>
										<circle-spinner ng-show="prograssing1"></circle-spinner>
										<table class="table table-striped table-hover"
											id="orders_list" ng-hide="prograssing1">
											<thead>
												<tr>
													<!-- <th><input ng-model="selectAll" ng-click="toggleAll(selectAll)" type="checkbox"></input></th> -->
													<th ng-click="sort('order_id')">ORDER NO<span
														class="glyphicon sort-icon" ng-show="sortKey=='order_id'"
														ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
													</th>
													<th ng-click="sort('stock_item_name')">ORDER DATE<span
														class="glyphicon sort-icon"
														ng-show="sortKey=='stock_item_name'"
														ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
													</th>
													<th ng-click="sort('department')">ORDER FROM<span
														class="glyphicon sort-icon"
														ng-show="sortKey=='department'"
														ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
													</th>
													<th ng-click="sort('qty')">DELIVERY TIME<span
														class="glyphicon sort-icon" ng-show="sortKey=='qty'"
														ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
													</th>
													<th ng-click="sort('timeslot')">ORDER TYPE<span
														class="glyphicon sort-icon" ng-show="sortKey=='timeslot'"
														ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
													</th>
												</tr>
											</thead>
											<tbody>
												<tr ng-if="itemList.length === 0">
													<td colspan="6">No data available</td>
												</tr>
												<tr
													ng-repeat="item in itemList|orderBy:sortKey:reverse|filter:search">
													<td><input type="hidden" ng-model="item.order_id"
														data-dtl-id="{{item.order_id}}"> <a href=""
														ng-click="orderDetails(item)">{{item.order_no}}</a></td>
													<td>{{item.order_date}}</td>
													<td>{{item.order_from}}</td>
													<td>{{item.closing_time}}</a></td>
													<td>{{item.order_type}}</td>
												</tr>


											</tbody>
										</table>
									</div>
									</md-content> </md-tab> </md-tabs>
								</div>
								<jsp:directive.include file="../stocktransfer/form.jsp" />
								<%-- <jsp:directive.include file="../common/includes/common_popup_form.jsp" /> --%>
							</div>

						</div>

						<!-- /.box-body -->
					</div>
					<!-- /.box -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</section>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

	<!-- include form-->

	<!-- common js include below -->
	<jsp:directive.include file="../common/includes/footer.jsp" />
	<jsp:directive.include file="../common/includes/stockitemdata.jsp" />
	<!-- page script -->

	<!-- custom js include below -->
	<script src="<c:url value='/resources/common/js/mrpApp.js?n=1' />"></script>

	<script
		src="<c:url value='/resources/mrp/stocktransfer/js/stocktransfer.js?n=1' />"></script>
	<script
		src="<c:url value='/resources/common/js/views/ButtonController.js?n=1' />"></script>
	<script>
		angular.bootstrap(document.getElementById("mrp_App_Id"), [ 'mrp_app' ]);
	</script>
	<script type="text/javascript">
		jQuery(document).ready(function($) {

			if (window.history && window.history.pushState) {

				window.history.pushState('forward', null, './list');

				$(window).on('popstate', function() {

					if ($('#show_form').val() == 1) {

						window.location.href = "list";
						$('#show_form').val(0);
					} else {
						window.history.back();
					}

				});

			}
		});
	</script>
</body>
</html>
