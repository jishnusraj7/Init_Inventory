
<jsp:directive.include file="../../common/includes/page_directives.jsp" />



<!DOCTYPE html>
<html lang="en">
<head>
<!-- common css include below -->
<jsp:directive.include file="../../common/includes/header.jsp" />

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
	href="<c:url value="/resources/mrp/report/production/css/production.css"/>">
	
	
	
</head>
<!-- design header template include below -->
<jsp:directive.include file="../../common/includes/subheader.jsp" />
<jsp:directive.include
	file="../../common/includes/common_module_header.jsp" />

<!-- design left template include below -->
<!-- Left side column. contains the logo and sidebar -->
<jsp:directive.include file="../../common/includes/leftmenu.jsp" />
<!-- Content Wrapper. Contains page content -->

<div class="content-wrapper header_second" ng-app="mrp_app"
	id="mrp_App_Id" ng-cloak>
	<!-- Content Header (Page header) -->

	<div class="list-content-header" ng-controller="btn_ctrl">
		<input id="iscentralized" type='hidden'
			value="${currentuser.is_central_purchase}" />

		<section class="content-header content-header_second">
			<h1>
				PRODUCTION REPORT<%-- <spring:message code="purchaseorder.header"></spring:message> --%>
			</h1>
			<div ng-hide="succ_alertMessageStatus" class="alert-box"
				id="succ_alertMessageId">{{ succ_alertMeaasge }}</div>
			<div ng-hide="err_alertMessageStatus" class="erroralert-box"
				id="err_alertMessageId">{{ err_alertMeaasge }}</div>
			

			<ol class="breadcrumb breadcrumb_position">
			<%-- 	<li><a href="#"><i class="fa fa-dashboard"></i> <spring:message
							code="common.menu.home"></spring:message></a></li> --%>
				<li><a href="#">Report<%-- <spring:message code="common.menu.store"></spring:message> --%></a></li>
				<li><a href="#">Production Report<%-- <spring:message
							code="common.menu.inventory.purchaseorder"></spring:message> --%></a></li>
			</ol>
		</section>

		<div class="new_box sec_header_div">
			<div class="box cont_div_box">
<div ng-controller="productionCtrlr as productionCtrlr">
					<label for="select_report" class="report_lbl"> <spring:message
							code="itemstock.selectreport"></spring:message>
					</label>
					<div class="col-sm-3">
						<select class="form-control reper_select_box" id="select_prodreport"
						
							
							>
<!-- 							<option value="number:0" >Select</option>
 -->							<option value="number:1" selected="selected">Production Order Report</option>

							    <option value="number:2" >BOM Report</option>
								<option value="number:3">Profit Summary</option>
                                <option value="number:4">Bom Analysis Summary</option>
                                 <option value="number:5">Production Order Balance</option>
								<option value="number:6">Bom Rate Comparison</option>
								<option value="number:7">Stock Taking Summary</option>
							</select>
					</div>
				</div>
			<div class="header_rgt_show">
					<div class="pull-right">
						<div class="btn-group">
							<button class="btn  btn-sm btn-info" type="button"
								name="btnTools" data-toggle="dropdown" id="btnTools">
								<i class="fa fa-wrench" aria-hidden="false"></i> Export
							</button>
							<div class="dropdown-menu tool_btn_drpdwn_menu">
								<ul>

									<li><a class="dropdown-item" href="" id="exportpdf" >PDF</a></li>


									<li><a class="dropdown-item" id="excelView" href="" >EXCEL</a></li>
								</ul>
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

						<div ng-controller="productionCtrlr as productionCtrlr" id="prod_report_div">

							<jsp:directive.include file="../production/form.jsp" />

						</div>
						<div ng-controller="prodBomCntrlr as prodBomCntrlr" id="prod_bom_report_div">

							<jsp:directive.include file="../production/bomreport.jsp" />

						</div>
						<div ng-controller="profitSummaryCntrlr as profitSummaryCntrlr" id="profit_summary_report_div">

							<jsp:directive.include file="../production/profitsummary.jsp" />

						</div>
						
						<div ng-controller="bomAnalysisCntrlr as bomAnalysisCntrlr" id="bom_analysis_report_div">

							<jsp:directive.include file="../production/bomanalysis.jsp" />

						</div>
						
						<div ng-controller="prodbalcntrl as prodbalcntrl" id="prod_bal_report_div">

							<jsp:directive.include file="../production/prodbalancereport.jsp" />

						</div>
						
						<div ng-controller="bomratecomparisonCntrlr as bomratecomparisonCntrlr" id="bom_rate_comparison_div">

							<jsp:directive.include file="../production/bomratecomparison.jsp" />
						</div>
						
						<div  ng-controller="stockadjustmentsummaryCntrlr as stockadjustmentsummaryCntrlr" id="stock_adjustment_summary_div">

							<jsp:directive.include file="../production/stockadjustmentsummary.jsp" />
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
<jsp:directive.include file="../../common/includes/footer.jsp" />


<!-- page script -->

<!-- custom js include below -->
<script src="<c:url value='/resources/common/js/mrpApp.js?n=1' />"></script>
<script
	src="<c:url value="/resources/mrp/report/production/js/production.js?n=1"/>"></script>
<script
	src="<c:url value="/resources/mrp/report/prodbom/js/prodbom.js?n=1"/>"></script>
<script
	src="<c:url value="/resources/mrp/report/profitsummary/js/profitsummary.js?n=1"/>"></script>
	
<script
	src="<c:url value="/resources/mrp/report/bomanalysisrpt/js/bomanalysisrpt.js?n=1"/>"></script>
	
<script
	src="<c:url value="/resources/mrp/report/prodbalancereport/js/prodbalancereport.js?n=1"/>"></script>
	
<script
	src="<c:url value="/resources/mrp/report/bomratecomparison/js/bomratecomparison.js?n=1"/>"></script>
	
<script
	src="<c:url value="/resources/mrp/report/stockadjustmentsummary/js/stockadjustmentsummary.js?n=1"/>"></script> 
	
<script	src="<c:url value='/resources/common/js/views/ButtonController.js?n=1' />"></script>
<script>
	angular.bootstrap(document.getElementById("mrp_App_Id"), [ 'mrp_app' ]);
</script>
</body>
</html>