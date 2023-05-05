
<jsp:directive.include file="../common/includes/page_directives.jsp" />




<!DOCTYPE html>
<html lang="en">
<head>




<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />
<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/bomanalysis/css/bomanalysis.css' />">
<!-- <link href="vendor/angular-moment-picker/angular-moment-picker.min.css"
	rel="stylesheet"> -->
<link rel="stylesheet"
	href="<c:url value="/resources/common/template/plugins/calendar/fullcalendar.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/common/template/plugins/calendar/colorpicker.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/common/template/plugins/calendar/angular-bootstrap-calendar.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/common/template/plugins/momentpicker/angular-moment-picker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/common/template/plugins/angular/css/angular-material.min.css" />">

<link rel="stylesheet"
	href="<c:url value="/resources/common/template/plugins/multipledatepicker/multipleDatePicker.css" />">
</head>

<!-- design header template include below -->
<jsp:directive.include file="../common/includes/subheader.jsp" />
<jsp:directive.include
	file="../common/includes/common_module_header.jsp" />

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

<!-- design left template include below -->
<!-- Left side column. contains the logo and sidebar -->
<jsp:directive.include file="../common/includes/leftmenu.jsp" />
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper header_second" ng-app="mrp_app"
	id="mrp_App_Id" ng-cloak>
	<div class="list-content-header" ng-controller="btn_ctrl">

		<!-- Content Header (Page header) -->
		<section class="content-header content-header_second">
			<h1><spring:message code="bomanalysis.header"></spring:message>
			</h1>
				<div ng-hide="succ_alertMessageStatus" class="alert-box" id="succ_alertMessageId">
				 {{ succ_alertMeaasge }}
			</div>
			<div ng-hide="err_alertMessageStatus" class="erroralert-box" id="err_alertMessageId">
				 {{ err_alertMeaasge }}
			</div>
			
		</section>

	<%-- 	<div class="new_box" ng-controller="btn_ctrl">
			<div class="box cont_div_box">
				<jsp:directive.include file="../common/includes/common_buttons.jsp" />

			</div>
		</div> --%>

	</div>


	<!-- Main content -->
	<section class="content" id="module_content">
		<div class="row" id="bom_row">
			<div class="col-xs-12">


				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title"></h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<div class="box-header"></div>

						<div ng-controller="bomanalysisctrl as item">

							<jsp:directive.include file="../bomanalysis/form.jsp" />
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


<script src="<c:url value='/resources/common/js/mrpApp.js' />"></script>
<script
	src="<c:url value='/resources/mrp/bomanalysis/js/bomanalysis.js' />"></script>
<script
	src="<c:url value='/resources/common/js/views/ButtonController.js' />"></script>
<script>
	angular.bootstrap(document.getElementById("mrp_App_Id"), [ 'mrp_app' ]);
</script>


</body>
</html>