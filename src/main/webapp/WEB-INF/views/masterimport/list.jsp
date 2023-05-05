
<jsp:directive.include file="../common/includes/page_directives.jsp" />

<!DOCTYPE html>
<html lang="en">
<head>

<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />


<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value="/resources/mrp/masterimport/css/masterimport.css" />">
</head>

<!-- design header template include below -->
<jsp:directive.include file="../common/includes/subheader.jsp" />
<jsp:directive.include
	file="../common/includes/common_module_header.jsp" />

<!-- design left template include below -->
<!-- Left side column. contains the logo and sidebar -->
<jsp:directive.include file="../common/includes/leftmenu.jsp" />
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper header_second" ng-app="master_import_app"
	id="masterImportApp" ng-cloak>
	<!-- Content Header (Page header) -->
	<div class="list-content-header" ng-controller="btn_ctrl">
		<section class="content-header content-header_second">
			<h1>
				<spring:message code="masterimport.header"></spring:message>
			</h1>

			<ol class="breadcrumb breadcrumb_position">
				<li><a href="#"><i class="fa fa-dashboard"></i> <spring:message
							code="common.menu.home"></spring:message></a></li>
				<li><a href="#"><spring:message code="common.menu.settings"></spring:message></a></li>
				<li><a href="#"><spring:message
							code="common.menu.inventory"></spring:message></a></li>
				<li><a href="#"><spring:message
							code="common.menu.inventory.itemmaster"></spring:message></a></li>
			</ol>
		</section>

		<div class="new_box">
			<div class="box cont_div_box">
				<%-- <jsp:directive.include file="../common/includes/common_buttons.jsp" /> --%>
				<div class="col-sm-12">
					<button ng-disabled="disableButtons" id="mstBtnImport"
						ng-click="import()">Import</button>
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
						<div class="master-import-body">
							<div class="box-header"></div>

							<div ng-controller="master_import as mstImp">
								<div class="masterButtonsDiv">
									<div class="master_import_head_btn">
										<ul>
											<li class="list_btn" id="001">
												<button id="mstBtnItem" ng-disabled="disableButtons" 
													ng-click="loadImportDetails('item');getSelected($event);">Item</button>
											</li>
											<li class="list_btn" id="002">
												<button id="mstBtnitemCategory" ng-disabled="disableButtons" 
													ng-click="loadImportDetails('itemCategory');getSelected($event)">Item
													Category</button>
											</li>
											<li class="list_btn" id="003">
												<button id="mstBtnprofitCategory"
													ng-disabled="disableButtons" 
													ng-click="loadImportDetails('profitCategory');getSelected($event)">Profit
													Category</button>
											</li>
											<li class="list_btn" id="004">
												<button id="mstBtnsupplier" ng-disabled="disableButtons"
													ng-click="loadImportDetails('supplier');getSelected($event)">Supplier</button>
											</li>
											<!-- <li class="list_btn" id="005">
												<button id="mstBtncurrency" ng-disabled="disableButtons" 
													ng-click="loadImportDetails('currency');getSelected($event)">Currency</button>
											</li> -->
											<!-- <li class="list_btn" id="006">
												<button id="mstBtnuom" ng-disabled="disableButtons" 
													ng-click="loadImportDetails('uom');getSelected($event)">UOM</button>
											</li> -->
											<!-- <li class="list_btn" id="007">
												<button id="mstBtndepartment" ng-disabled="disableButtons" 
													ng-click="loadImportDetails('department');getSelected($event)">Department</button>
											</li> -->
											<!-- <li class="list_btn" id="008">
												<button id="mstBtntax" ng-disabled="disableButtons" 
													ng-click="loadImportDetails('tax');getSelected($event)">Tax</button>
											</li> -->
										</ul>

									</div>
								</div>

								<div id="dtlsDiv">
									<div class="col-sm-12">

										<md-content> <md-tabs md-selected="selectedIndex"
											md-dynamic-height md-border-bottom> <md-tab
											label="Updated Record" id="tab1"> <md-content
											class="md-padding">

										<table id="entry-grid" datatable=""
											dt-options="mstImp.dtOptions1"
											dt-instance="mstImp.dtInstance1"
											dt-columns="mstImp.dtColumns1" style="width: 100%;"
											class="angDataTable table table-hover"></table>

										</md-content> </md-tab> <md-tab label="New Record" id="tab2"> <md-content
											class="md-padding">
										<table id="entry-grid" datatable=""
											dt-options="mstImp.dtOptions2"
											dt-instance="mstImp.dtInstance2"
											dt-columns="mstImp.dtColumns2" style="width: 100%;"
											class="angDataTable table table-hover"></table>

										</md-content> </md-tab> </md-tabs> </md-content>
									</div>
								</div>

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


<!-- page script -->

<!-- custom js include below -->

<script
	src="<c:url value="/resources/mrp/masterimport/js/masterimport.js" />"></script>
</body>
</html>