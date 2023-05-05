
<jsp:directive.include file="../common/includes/page_directives.jsp" />
<!DOCTYPE html>
<html lang="en" >
<head>
<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />
<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/dailyexpenses/css/dailyexpenses.css' />">
</head>

<!-- design header template include below -->
<jsp:directive.include file="../common/includes/subheader.jsp" />
<jsp:directive.include
	file="../common/includes/common_module_header.jsp" />

<!-- design left template include below -->
<!-- Left side column. contains the logo and sidebar -->
<jsp:directive.include file="../common/includes/leftmenu.jsp" />
<!-- Content Wrapper. Contains page content -->

<div class="content-wrapper header_second"  ng-app="uom_app" id="uom_app" ng-cloak>
		<div class="list-content-header">

	<!-- Content Header (Page header) -->
	<section class="content-header content-header_second">
		<h1><%-- <spring:message code=""></spring:message> --%>Daily Expenses</h1>
		<ol class="breadcrumb breadcrumb_position">
			<li><a href="#"><i class="fa fa-dashboard"></i> <spring:message
						code="common.menu.home"></spring:message></a></li>
			<li><a href="#"><spring:message code="common.menu.settings"></spring:message></a></li>
			<li><a href="#"><spring:message code="common.menu.inventory"></spring:message></a></li>
			<li><a href="#"><spring:message code="common.menu.inventory.uom"></spring:message></a></li>
		</ol>
	</section>

	<div class="new_box" ng-controller="btn_ctrl">
		<div class="box cont_div_box">
			<jsp:directive.include
				file="../common/includes/common_buttons.jsp" />
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
						<div class="box-header">

						</div>

						<div ng-controller="uomctrl as item">
							<div class="form-group" ng-show="show_table" id="div_table_show"> 
								<!-- <table datatable="" dt-options="item.dtOptions"
									dt-columns="item.dtColumns" dt-instance="item.dtInstance"
									class="row-border hover table table-bordered table-striped"></table> -->
							</div>
					<jsp:directive.include file="../dailyexpenses/form.jsp" />
						
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
	src="<c:url value='/resources/mrp/dailyexpenses/js/dailyexpenses.js' />"></script>
</body>
</html>
