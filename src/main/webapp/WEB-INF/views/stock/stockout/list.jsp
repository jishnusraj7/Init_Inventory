
<jsp:directive.include file="../../common/includes/page_directives.jsp" />
<%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockOutType"%>
<c:set var="stockouttypes" value="<%=stockOutType.values()%>"></c:set>

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

<input type="hidden" id="finalizepermission"
	value="${stockoutFinalizepermission.getCanEdit() }">

<!-- custom css can include below -->

<link rel="stylesheet"
	href="<c:url value='/resources/mrp/stock/stockout/css/stockout.css' />">

</head>

<!-- design header template include below -->
<jsp:directive.include file="../../common/includes/subheader.jsp" />
<jsp:directive.include
	file="../../common/includes/common_module_header.jsp" />

<!-- design left template include below -->
<!-- Left side column. contains the logo and sidebar -->
<jsp:directive.include file="../../common/includes/leftmenu.jsp" />
<!-- Content Wrapper. Contains page content -->

<input type="hidden" value="0" id="show_form">
<div class="content-wrapper header_second" ng-app="mrp_app" ng-cloak
	id="mrp_App_Id">
	<div class="list-content-header" ng-controller="btn_ctrl">
		<!-- Content Header (Page header) -->
		<section class="content-header content-header_second"
			ng-show="show_stockrequest_form">
			<h1>
				<spring:message code="stockout.header"></spring:message>
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
							code="common.menu.store.stockout"></spring:message></a></li>

			</ol>
		</section>
		<section class="content-header content-header_second"
			ng-hide="show_stockrequest_form">
			<h1>STOCK REQUEST</h1>
			<div ng-hide="succ_alertMessageStatus" class="alert-box"
				id="succ_alertMessageId">{{ succ_alertMeaasge }}</div>
			<div ng-hide="err_alertMessageStatus" class="erroralert-box"
				id="err_alertMessageId">{{ err_alertMeaasge }}</div>
			<ol class="breadcrumb breadcrumb_position" ng-hide="true">
				<li><a href="#"><i class="fa fa-dashboard"></i> <spring:message
							code="common.menu.home"></spring:message></a></li>
				<li><a href="#"><spring:message code="common.menu.store"></spring:message></a></li>
				<li><a href="#">Stock Request</a></li>

			</ol>
		</section>

		<div class="new_box">
			<div class="box cont_div_box">

				<div class="box-header rgt_header_btn" id="div_btn_next">
					<div class="pull-right">

						<button class="btn  btn-sm " type="button" name="btnBack"
							id="btnBack" ng-click="fun_backTo_table()">
							<i class="fa fa-caret-left" aria-hidden="true"></i><i
								class="fa fa-caret-left" aria-hidden="true"></i> Back
						</button>
						<button class="btn  btn-sm btn-primary add_btn " type="button"
							name="btnNext" id="btnNext" ng-click="fun_show_form1()">
							<i class="fa fa-caret-right" aria-hidden="true"></i><i
								class="fa fa-caret-right" aria-hidden="true"></i> Next
						</button>


					</div>
				</div>

				<jsp:directive.include
					file="../../common/includes/common_buttons.jsp" />

			</div>
		</div>
		<div class="container" style="padding-left: 150px;" id="advsearchbox">
			<div class="row">
				<div class="col-md-3">
					<div class="input-group" id="adv-search">
						<!-- <input style="height: 31px;" type="text" id="SearchText"
							class="form-control" placeholder="Search ..." /> -->
						<!-- <input type="text" name="search" style="width: 260px;" id="SearchText" value="" /> -->
						<div id="SearchText" name="search" contenteditable="true"
							style="width: 260px;"></div>
						<a href="" id="clear"></a>
						<div class="input-group-btn">
							<div class="btn-group searchbttn" role="group">
								<div class="dropdown dropdown-lg">
									<button type="button" class="btn btn-default dropdown-toggle"
										style="height: 30px;" aria-expanded="false" id="clearFeilds">
										<span class="caret"></span>
									</button>
									<div class="dropdown-menu dropdown-menu-right dropsize"
										role="menu" id="dropdownnew">
										<form class="form-horizontal" role="form">
											<div class="form-group">
												<div class="col-md-13">
													<label for="contain">Request Date:</label>

													<div>
														<input type="text" id="reqDate1"
															class=" dtformData datepicker">


													</div>
												</div>
											</div>

											<!-- 		<div class="form-group">
												<label for="contain">Request Date:</label>
												      <input type="text" id="reqDate1" class=" dtformData datepicker" >
												
												 <input
													id="reqDate1" type="date" class="form-control" type="text" />
											</div> -->

											<div class="form-group">
												<div class="col-md-13">
													<label for="contain">Delivered Date:</label>

													<div>
														<input type="text" id="delDate1"
															class=" dtformData datepicker">


													</div>
												</div>
											</div>

											<!-- 	<div class="form-group">
												<label for="contain">Delivered Date:</label> <input
													type="text" id="delDate1" class=" dtformData datepicker">
												<input
													id="delDate1" type="date" class="form-control" type="text" />
											</div> -->
											<div class="form-group">
												<label for="contain">Reference No:</label> <input
													class="form-control" id="refrNo" type="text" />
											</div>

											<div class="form-group" id="form_div_depFilter_category_id">
												<label for="filter">Department:</label> <select
													id="dep_filter_id" class="form-control"
													name="dep_filter_id" ng-change="filterDeprtmnt()"
													ng-model="filerDeprtmnt">
													<option value="" selected>Select</option>
													<option ng-repeat=" v in dep_list" value="{{v.id}}">
														{{v.name}}</option>
												</select>

												<%-- 	<select class="form-control" name="depId" id="depId"
													>
													<option value="0" id="select">select</option>
													<c:forEach var="deprtmntList" items="${deprtmntList}">
														
															<option value="${deprtmntList.id}" id="${deprtmntList.id}"><c:out
																	value="${hqCompany.name}" /></option>
														
													</c:forEach>
												</select> --%>

											</div>




											<button style="float: right; margin-left: 10px;"
												type="button" class="btn btn-default" data-dismiss="modal"
												data-toggle="dropdown" id="closebtnew">Close</button>

											<button style="float: right;" type="submit"
												ng-click="advSearch();" class="btn btn-primary"
												data-dismiss="modal" data-toggle="dropdown"
												style="  height: 29px;margin-top: 1px;">
												<span class="glyphicon glyphicon-search" aria-hidden="true">
												</span>
											</button>

										</form>
									</div>
								</div>
								<button type="button" class="btn btn-primary">
									<span class="glyphicon glyphicon-search" aria-hidden="true"
										ng-click="Search();" data-dismiss="modal"
										data-toggle="dropdown"></span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- <div>
		
	</div> -->


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

						<div ng-controller="stockout as stock" id="stockcontrlr_div">
							<circle-spinner ng-show="prograssing1"></circle-spinner>

							<div class="form-group" ng-show="show_table" id="div_table_show">
								<div class="checkbox">
									<label class="checkbox-inline"> <md-checkbox
											aria-label="Select All" ng-checked="isChecked()"
											md-indeterminate="isIndeterminate()"
											ng-click="toggleAllStatus()">All</md-checkbox>
									</label> <label class="checkbox-inline"> <md-checkbox
											ng-checked="exists(0, selectedStatus)"
											ng-click="toggle(0, selectedStatus)">New </md-checkbox> <!-- </label> <label class="checkbox-inline"> <md-checkbox
											ng-checked="exists(1,selectedStatus)"
											ng-click="toggle(1,selectedStatus)">Approved </md-checkbox> -->

									</label> <label class="checkbox-inline"> <md-checkbox
											ng-checked="exists(2, selectedStatus)"
											ng-click="toggle(2,selectedStatus)">Rejected </md-checkbox>

									</label> <label class="checkbox-inline"> <md-checkbox
											ng-checked="exists(3, selectedStatus)"
											ng-click="toggle(3,selectedStatus)">Issued </md-checkbox>

									</label> <label class="checkbox-inline"> <md-checkbox
											ng-checked="exists(4, selectedStatus)"
											ng-click="toggle(4,selectedStatus)">Printed </md-checkbox>

									</label>
								</div>


								<table datatable="" dt-options="stock.dtOptions"
									dt-columns="stock.dtColumns" dt-instance="stock.dtInstance"
									class="table dataClass"></table>
							</div>

						<jsp:directive.include file="../stockout/form.jsp" />
																<jsp:directive.include file="../stockout/type.jsp" />

						
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
 <jsp:directive.include file="../../common/includes/stockitemdata.jsp" />
<script>
	var stockOutStrings = new Array();
	stockOutStrings['isRequest'] = '<c:out value="${isRequest}"/>';
</script>

<!-- page script -->

<!-- custom js include below -->


<script src="<c:url value='/resources/common/js/mrpApp.js?n=1' />"></script>
<script
	src="<c:url value='/resources/mrp/stock/stockout/js/stockout.js?n=1' />"></script>

<script>
	angular.bootstrap(document.getElementById("mrp_App_Id"), [ 'mrp_app' ]);
</script>
<script type="text/javascript">
	jQuery(document).ready(
			function($) {

				if (window.history && window.history.pushState) {
					//alert(stockOutStrings['isRequest']);
					if (stockOutStrings['isRequest'] == 0)
						window.history.pushState('forward', null,
								'./list?isStockOut=0');
					if (stockOutStrings['isRequest'] == 1)
						window.history.pushState('forward', null,
								'./list?isStockOut=1');
					$(window).on(
							'popstate',
							function() {

								if ($('#show_form').val() == 1
										&& stockOutStrings['isRequest'] == 0) {
									window.location.href = "list?isStockOut=0";
									$('#show_form').val(0);
								} else if ($('#show_form').val() == 1
										&& stockOutStrings['isRequest'] == 1) {
									window.location.href = "list?isStockOut=1";
									$('#show_form').val(0);

								} else if ($('#show_form').val() == 2
										&& stockOutStrings['isRequest'] == 1) {
									getBackPage();
									$('#show_form').val(1);
								} else {
									window.history.back();
								}

							});

				}
				function getBackPage() {
					var scope = angular.element(
							document.getElementById("stockcontrlr_div"))
							.scope();

					scope.$apply(function() {
						scope.goBackToTypePage();
						//scope.fun_backTo_table();
					})
				}
			});
</script>
</body>
</html>
