<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%-- <%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockDisposalReasonType"%> 
<c:set var="enumValues" value="<%=stockDisposalReasonType.values()%>" />
 <c:set var="formPageUrl" value="../../looksup/itemcategory/form.jsp"
	scope="request" /> --%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
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
	href="<c:url value='/resources/mrp/stocksummary/css/stocksummary.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/stockitemdata.css'/>">
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
					<spring:message code="stocksummary.header"></spring:message>
				</h1>

				<div ng-hide="succ_alertMessageStatus" class="alert-box"
					id="succ_alertMessageId">{{ succ_alertMeaasge }}</div>
				<div ng-hide="err_alertMessageStatus" class="erroralert-box"
					id="err_alertMessageId">{{ err_alertMeaasge }}</div>
			</section>
		</div>
		<!-- Main content -->
		<section class="content" id="module_content" style="padding-top: 70px">
			<div class="row">
				<div class="col-xs-12">

					<div class="box">
						<div class="box-header with-border">
							<h3 class="box-title"></h3>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<div class="box-header"></div>
							<div ng-controller="stocksummary as item">


								<!--   Done by anandu on 25-01-2020 -->

								<div class="page-content-header">
									<div class="row">
										<div class="form-group col-sm-12" id="form_div_start_date">
											<div class="col-sm-2">
												<label for="start_date" class="col-md-12 control-label">
													<b>Date From</b><i class="text-danger">*</i>
												</label>
												<div class="input-group col-md-12">
													<div class="right-inner-addon" id="form_div_from_date">
														<i class="fa fa-calendar" id="calender_icon"
															style="left: 81%; z-index: 4;"></i> <input type="text"
															class="form-control" daterange-picker name="start_date"
															id="start_date" ng-model="formData.start_date"
															placeholder="" ng-change="getStockSumaryTable()"
															style="width: 100%;">
													</div>
													<span class="input-group-addon" min="0" max="99"
														number-mask="" id="form_div_from_date_error"
														style="display: none;"><i
														class="fa fa-question-circle red-tooltip"
														data-toggle="tooltip" data-placement="bottom" title=""
														data-original-title="<spring:message code="prodprocess.deliverydate.error"></spring:message>"></i>
													</span>
												</div>
											</div>


											<div class="col-sm-2">
												<label for="end_date" class=" col-md-12 control-label"><b>Date
														To </b><i class="text-danger">*</i> </label>
												<div class="input-group  col-md-12">
													<div class="right-inner-addon" id="form_div_to_date">
														<i class="fa fa-calendar" id="calender_icon"
															style="left: 81%; z-index: 4;"></i> <input type="text"
															class="form-control" daterange-picker name="end_date"
															id="end_date" ng-model="formData.end_date" placeholder=""
															ng-change="getStockSumaryTable()"
															ng-disabled="disable_all" style="width: 100%;">
													</div>
													<span class="input-group-addon" min="0" max="99"
														number-mask="" id="form_div_to_date_error"
														style="display: none;"><i
														class="fa fa-question-circle red-tooltip"
														data-toggle="tooltip" data-placement="bottom" title=""
														data-original-title="<spring:message code="prodprocess.deliverydate.error"></spring:message>"></i>
													</span>
												</div>
											</div>

											<div id="form_div_department">
												<div class="col-sm-2">
													<label for="end_date" class=" col-md-12 control-label"><b>Department
													</b><i class="text-danger">*</i> </label>
													<div class=" col-md-12 input-group">
														<select class="form-control" id="department_id"
															class="required" name="department_id"
															ng-disabled="disable_all" required
															ng-options="v.id as v.name for v in departments"
															ng-model="formData.department_id"
															ng-change="getStockSumaryTable()"></select> <span
															class="input-group-addon"
															id="form_div_source_department_code_error"
															style="display: none;"><i
															class="fa fa-question-circle red-tooltip"
															data-toggle="tooltip" data-placement="bottom" title=""
															data-original-title="<spring:message code="stocktransfer.error.sourcedep"></spring:message>"></i></span>
													</div>
												</div>
											</div>


											<div id="form_div_department">
												<div class="col-sm-2">
													<label for="end_date" class=" col-md-12 control-label"><b>Category
													</b><i class="text-danger">*</i> </label>
													<div class=" col-md-12 input-group">
														<select class="form-control" id="category_id"
															class="required" name="category_id"
															ng-disabled="disable_all" required
															ng-options="v.id as v.name for v in category_id_list"
															ng-model="formData.category_id"
															ng-change="getStockSumaryTable()"></select> <span
															class="input-group-addon"
															id="form_div_source_department_code_error"
															style="display: none;"><i
															class="fa fa-question-circle red-tooltip"
															data-toggle="tooltip" data-placement="bottom" title=""
															data-original-title="<spring:message code="stocktransfer.error.sourcedep"></spring:message>"></i></span>
													</div>

												</div>
												<!-- 	<button type="button" class="btn btn-success"
												ng-click="printStockSummary()">PRINT</button> -->

												<!-- gana -->
												<div class=" col-md-2 col-md-offset-2">
													<!-- header_rgt_show -->
													<div class="pull-right">
														<div class="btn-group top-m-30">
															<button class="btn  btn btn-success" type="button"
																name="btnTools" data-toggle="dropdown" id="btnTools">
																<i class="fa fa-wrench" aria-hidden="false"></i> Export
															</button>
															<div class="dropdown-menu tool_btn_drpdwn_menu">
																<ul>

																	<li><a class="dropdown-item" href=""
																		id="btn_finalize">PDF</a></li>


																	<li><a class="dropdown-item" id="excelView"
																		href="">EXCEL</a></li>
																</ul>
															</div>
														</div>
													</div>
												</div>














											</div>
										</div>
									</div>
								</div>
								<div class="form-group" id="div_table_show">
									<div style="width: 100%;" class="div_table_div">
										<div class="div_table table table-bordered table-fixed"
											id="stock_summary_table">
											<div class="div_table_thead">
												<div class="div_table_tr active">
													<div class="div_table_th col-xs-1" ng-hide="true">ID</div>
													<div class="div_table_th col-xs-2">Item</div>
													<div class="div_table_th col-xs-1 text-align-right">Opening</div>
													<div class="div_table_th col-xs-1 text-align-right">Stockin</div>
													<div class="div_table_th col-xs-1 text-align-right">Stockout</div>
													<div class="div_table_th col-xs-1 text-align-right">Return</div>
													<div class="div_table_th col-xs-1 text-align-right">Production</div>
													<div class="div_table_th col-xs-1 text-align-right">Bom
														Out</div>
													<div class="div_table_th col-xs-1 text-align-right">Transfer</div>
													<div class="div_table_th col-xs-1 text-align-right">Adjustment</div>
													<div class="div_table_th col-xs-1 text-align-right">Disposal</div>
													<div class="div_table_th col-xs-1 text-align-right">Closing</div>
												</div>
											</div>
											<div class="div_table_tbody" ng-hide="prograssing">
												<div class="div_table_tr"
													ng-if="summary_details.length === 0">
													<div class="div_table_td" colspan="10"
														style="text-align: left;">No data available</div>
												</div>
												<div class="div_table_tr"
													ng-repeat="item in summary_details"
													ng-class="tr_{{$index}}" class="stock">
													<div class="div_table_td col-xs-1" ng-hide="true">{{item.stock_item_id}}</div>
													<div class="div_table_td col-xs-2">{{item.stock_item_name}}</div>
													<div class="div_table_td col-xs-1 text-align-right">{{item.opening_stock}}</div>
													<div class="div_table_td col-xs-1 text-align-right">{{item.stock_in}}</div>
													<div class="div_table_td col-xs-1 text-align-right">{{item.stock_out}}</div>
													<div class="div_table_td col-xs-1 text-align-right">{{item.return_stock}}</div>
													<div class="div_table_td col-xs-1 text-align-right">{{item.production}}</div>
													<div class="div_table_td col-xs-1 text-align-right">{{item.stock_out_BOM}}</div>
													<div class="div_table_td col-xs-1 text-align-right">{{item.stock_transfer}}</div>
													<div class="div_table_td col-xs-1 text-align-right">{{item.stock_adjustment}}</div>
													<div class="div_table_td col-xs-1 text-align-right">{{item.stock_disposal}}</div>
													<div class="div_table_td col-xs-1 text-align-right">{{item.closing_stock}}</div>
												</div>
											</div>
										</div>
									</div>
									<circle-spinner ng-show="prograssing"></circle-spinner>
								</div>


							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	<!-- common js include below -->
	<jsp:directive.include file="../common/includes/footer.jsp" />
	<jsp:directive.include file="../common/includes/stockitemdata.jsp" />
	<!-- page script -->

	<!-- custom js include below -->
	<script src="<c:url value='/resources/common/js/mrpApp.js?n=1' />"></script>

	<script
		src="<c:url value='/resources/mrp/stocksummary/js/stocksummary.js?n=1' />"></script>
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
					} else
						window.history.back();
				});
			}
		});

		/* 	
		 var t=0; // the height of the highest element (after the function runs)
		 var t_elem;  // the highest element (after the function runs)
		 $("*",elem).each(function () {
		 $this = $(this);
		 if ( $this.outerHeight() > t ) {
		 t_elem=this;
		 t=$this.outerHeight();
		 }
		 }); */
	</script>

	
</body>
</html>