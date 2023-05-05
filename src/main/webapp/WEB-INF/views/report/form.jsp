
<jsp:directive.include file="../common/includes/page_directives.jsp" />

<%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.transactionType"%>

<c:set var="transactionType" value="<%=transactionType.values()%>"></c:set>

<!DOCTYPE html>
<html lang="en">
<head>

<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />
<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/currentStock/css/itemstock.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/stockregisterreport/css/stockregisterreport.css"/>">

<!-- Done by anandu on 21-01-2020-->
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/stockexcisereport/css/stockexcisereport.css"/>">

<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/stockregisterreport/css/stockregisterdetailsreport.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/purchaseorder/css/purchasereport.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/stockin/css/stockinreport.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/stockout/css/stockoutreport.css"/>">
	<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/purchasereport/css/purchasereport.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/nonmovingitems/css/nonmovingitems.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/stockdisposal/css/disposalreport.css"/>">
	
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/report/stocktransfer/css/stocktransferrpt.css"/>">	
</head>

<!-- design header template include below -->
<jsp:directive.include file="../common/includes/subheader.jsp" />
<jsp:directive.include
	file="../common/includes/common_module_header.jsp" />

<!-- design left template include below -->
<!-- Left side column. contains the logo and sidebar -->
<jsp:directive.include file="../common/includes/leftmenu.jsp" />
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper header_second">
	<div class="col-sm-12">
		<div class="list-content-header" ng-app="report_ctrlr_app" id="app2"
			style="margin-left: -15px;" ng-cloak>

			<section class="content-header content-header_second">
				<h1>REPORT</h1>


			</section>
			<div class="new_box ng-scope sec_header_div">
				<div class="box cont_div_box">
					<div ng-controller="report_ctrlr as reportCtrlr">
						<label for="select_report" class="col-sm-2 control-label"
							style="text-align: right;"> <spring:message
								code="itemstock.selectreport"></spring:message>
						</label>
						<div class="col-sm-3">
							<c:if test="${(combineMode==1)?true:false }">

								<select class="form-control reper_select_box" id="select_report"
									name="select_report"
									ng-options="v.id as v.name for v in fillselectReport | filter: { id: '!2' }"
									ng-model="formData.select_report" ng-change="selectreport()"></select>
							</c:if>
							<c:if test="${(combineMode==0)?true:false }">

								<select class="form-control reper_select_box" id="select_report"
									name="select_report"
									ng-options="v.id as v.name for v in fillselectReport "
									ng-model="formData.select_report" ng-change="selectreport()"></select>
							</c:if>
						</div>
					</div>
					<!-- 
					<button type="button"
						class="btn btn-success breadcrumb_position header_rgt_show_btn"
						id="btn_finalize" ng-click="itemMaster_report($event)">SHOW</button>
				</div>
				
				 -->

					<div ng-hide="err_alertMessageStatus" class="erroralert-box"
						id="err_alertMessageId"></div>

					<div class="header_rgt_show">
						<div class="pull-right">
							<div class="btn-group">
								<button class="btn  btn-sm btn-info" type="button"
									name="btnTools" data-toggle="dropdown" id="btnTools">
									<i class="fa fa-wrench" aria-hidden="false"></i> Export
								</button>
								<div class="dropdown-menu tool_btn_drpdwn_menu">
									<ul>

									<li><a class="dropdown-item" href="" id="btn_finalize" >PDF</a></li>


										<li><a class="dropdown-item" id="excelView" href="">EXCEL</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>

                   <!-- for tally Export -->
                   <div class=" header_rgt_show">  <!-- header_rgt_show -->
												<div class="pull-right">
													<div class="btn-group">
														<button class="btn  btn-sm btn-info" type="button"
															name="btnTools" data-toggle="dropdown" id="btnTally">
															<i class="fa fa-wrench" aria-hidden="false"></i> Export
														</button>
														<div class="dropdown-menu tool_btn_drpdwn_menu">
															<ul>
							
																<li><a class="dropdown-item" href="" id="btn_cash" >CASH</a></li>
							
							
																 <li><a class="dropdown-item" id="btn_credit" href="" >CREDIT</a></li>
																 
																  <li><a class="dropdown-item" id="btn_all" href="" >ALL</a></li> 
															</ul>
														</div>
													</div>
												</div>
											</div>
				</div>
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

						<div class=" " ng-app="stock_report_app" id="app3" ng-cloak>

							<div ng-controller="stock_report_ctrlr as stock_report_ctrlr">

								<jsp:directive.include file="../report/currentstock/form.jsp" />

							</div>

						</div>

						<div class=" " ng-app="stock-register_report_app " ng-cloak
							id="app_stock-register">

							<div ng-controller="stockregisterCtrlr as stockregisterCtrlr">

								<jsp:directive.include
									file="../report/stockregisterreport/form.jsp" />

							</div>
						</div>

						<!-- Done by anandu on 21-01-2020-->
						<div class=" " ng-app="stock-excise_report_app" ng-cloak
							id="app_stock-excise">

							<div ng-controller="stockexciseCtrlr as stockexciseCtrlr">

								<jsp:directive.include
									file="../report/stockexcisereport/form.jsp" />
							</div>
						</div>


						<div class=" " ng-app="stockin_report_app" id="stockin_report_app"
							ng-cloak>

							<div ng-controller="stockinCtrlr as stockinCtrlr">

								<jsp:directive.include file="../report/stockin/form.jsp" />

							</div>

						</div>

						<div class=" " ng-app="purchaseorder_report_app" ng-cloak
							id="app_purchaseorder">

							<div ng-controller="purchaseorderCtrlr as purchaseorderCtrlr">

								<jsp:directive.include file="../report/purchaseorder/form.jsp" />

							</div>

						</div>
						
						<div class=" " ng-app="stockout_report_app" ng-cloak
							id="stockout_report_app">

							<div ng-controller="stockoutCtrlr as stockoutCtrlr">

								<jsp:directive.include file="../report/stockout/form.jsp" />

							</div>

						</div>
						<div class=" " ng-app="purchase_return_report_app" ng-cloak
							id="purchase_return_report_app">

							<div ng-controller="purchaseReturnCtrlr as purchaseReturnCtrlr">

								<jsp:directive.include file="../report/purchasereturn/form.jsp" />

							</div>

						</div>
						<div class=" " ng-app="non_moving_report_app" id="app5" ng-cloak>

							<div
								ng-controller="non_moving_report_ctrlr as non_moving_report_ctrlr">

								<jsp:directive.include file="../report/nonmovingitems/form.jsp" />

							</div>

						</div>
						<div class=" " ng-app="disposal_report_app" ng-cloak
							id="app_disposal_report">

							<div
								ng-controller="disposal_report_ctrlr as disposal_report_ctrlr">

								<jsp:directive.include file="../report/stockdisposal/form.jsp" />

							</div>

						</div>

						<div class=" " ng-app="stock_transfer_report_app" ng-cloak
							id="app_transfer_report">

							<div
								ng-controller="transfer_report_ctrlr as transfer_report_ctrlr">

								<jsp:directive.include file="../report/stocktransfer/form.jsp" />

							</div>

						</div>
						<%-- 
						<div class=" " ng-app="stock-register_details_report_app "
							ng-cloak id="app_stock-register_details">

							<div
								ng-controller="stockregisterDetailsCtrlr as stockregisterDetailsCtrlr">

								<jsp:directive.include
									file="../report/stockregisterdetailsreport/form.jsp" />

							</div>

						</div> --%>

						<div class=" " ng-app="department_wise_report_app " ng-cloak
							id="app_department_wise_report">

							<div ng-controller="departmentWiseCtrlr as departmentWiseCtrlr">

								<jsp:directive.include
									file="../report/departmentwisereport/form.jsp" />

							</div>
						</div>
						
						<div class=" " ng-app="tally_export_report_app " ng-cloak
							id="tally_export_report_app">

							<div ng-controller="tallyExportCtrlr as tallyExportCtrlr">

								<jsp:directive.include
									file="../report/tallyexport/form.jsp" />

							</div>
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
<jsp:directive.include file="../report/tallyexport/target.jsp" />
<!-- page script
<!-- custom js include below -->
<script src="<c:url value="/resources/mrp/report/report.js?n=1" />"></script>
<script
	src="<c:url value="/resources/mrp/report/currentStock/js/itemstock.js?n=1"/>"></script>
<script
	src="<c:url value="/resources/mrp/report/stockregisterreport/js/stockregisterreport.js?n=1"/>"></script>
<script
	src="<c:url value="/resources/mrp/report/stockin/js/stockinreport.js?n=1"/>"></script>
<script
	src="<c:url value="/resources/mrp/report/purchaseorder/js/purchaseorder.js?n=1"/>"></script>
<script
	src="<c:url value="/resources/mrp/report/stockout/js/stockoutreport.js?n=1"/>"></script>
<script
	src="<c:url value="/resources/mrp/report/purchasereturn/js/purchasereturnreport.js?n=1"/>"></script>

<!-- Done by anandu on 21-01-2020-->
<script
	src="<c:url value="/resources/mrp/report/stockexcisereport/js/stockexcisereport.js?n=1"/>"></script>

<script
	src="<c:url value="/resources/mrp/report/nonmovingitems/js/nonmovingitems.js?n=1"/>"></script>
<script
	src="<c:url value="/resources/mrp/report/stockdisposal/js/disposalreport.js?n=1"/>"></script>
	
<script
	src="<c:url value="/resources/mrp/report/stocktransfer/js/stocktransferrpt.js?n=1"/>"></script>
<script
	src="<c:url value="/resources/mrp/report/stockregisterdetailsreport/js/stockregisterdetailsreport.js?n=1"/>"></script>
<script
	src="<c:url value="/resources/mrp/report/departmentwise/js/departmentwisereport.js?n=1"/>"></script>
	<script
	src="<c:url value="/resources/mrp/report/tallyexport/js/tallyexportreport.js?n=1"/>"></script>
</body>
</html>

