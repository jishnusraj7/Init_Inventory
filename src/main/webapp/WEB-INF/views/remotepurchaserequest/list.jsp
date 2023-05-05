
<jsp:directive.include file="../common/includes/page_directives.jsp" />



<!DOCTYPE html>
<html lang="en">
<head>
<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />
<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/remotepurchaserequest/css/pr.css' />">
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

<input type="hidden" id="show_form" value="0">
<div class="content-wrapper header_second" ng-app="mrp_app"
	id="mrp_App_Id" ng-cloak>

	<!-- Content Header (Page header) -->

	<div class="list-content-header" ng-controller="btn_ctrl">
		<section class="content-header content-header_second">
			<h1>
				<spring:message code="remoterequest.header"></spring:message>
			</h1>
			<div ng-hide="succ_alertMessageStatus" class="alert-box" id="succ_alertMessageId">
				 {{ succ_alertMeaasge }}
			</div>
			<div ng-hide="err_alertMessageStatus" class="erroralert-box" id="err_alertMessageId">
				 {{ err_alertMeaasge }}
			</div>

			<ol class="breadcrumb breadcrumb_position">
				<li><a href="#"><i class="fa fa-dashboard"></i> <spring:message
							code="common.menu.home"></spring:message></a></li>
				<li><a href="#"><spring:message code="common.menu.store"></spring:message></a></li>
				<li><a href="#"><spring:message code="remoterequest.header"></spring:message></a></li>
			</ol>
		</section>

		<div class="new_box" ng-controller="btn_ctrl">
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

						<div ng-controller="poctrl as item">
							<div class="form-group" ng-show="show_table" id="div_table_show">
							
							<div class="checkbox">
									<label class="checkbox-inline"> 
									<md-checkbox aria-label="Select All"
                         ng-checked="isChecked()"
                         md-indeterminate="isIndeterminate()"
                         ng-click="toggleAll()">All</md-checkbox>
									
									<!-- <input type="checkbox"
										id="isall" ng-click="reloadtabledata($event);">ALL <span
										class="cr"><i class="cr-icon fa fa-check"></i></span> -->
									</label> 
									
									<label class="checkbox-inline">
									
									 <md-checkbox ng-checked="exists(0, selectedStatus)" ng-click="toggle(0, selectedStatus)">New </md-checkbox>
									 <!-- <input type="checkbox"
										checked="checked" class="primary" id="approved"
										ng-click="reloadtabledata($event);">New <span
										class="cr"><i class="cr-icon fa fa-check"></i></span> -->
									</label> 
									
									
									<label class="checkbox-inline">
									
									<md-checkbox ng-checked="exists(1,selectedStatus)" ng-click="toggle(1,selectedStatus)">Processing </md-checkbox>
									 <!-- <input type="checkbox"
										checked="checked" class="primary" id="open"
										ng-click="reloadtabledata($event);">Printed <span
										class="cr"><i class="cr-icon fa fa-check"></i></span> -->

									</label>
									
									
									
									<label class="checkbox-inline"> <!-- <input type="checkbox"
										class="primary" id="request"
										ng-click="reloadtabledata($event);">Closed <span
										class="cr"><i class="cr-icon fa fa-check"></i></span> -->
										
										<md-checkbox ng-checked="exists(2, selectedStatus)" ng-click="toggle(2,selectedStatus)">Finished</md-checkbox>

									</label>
									
									<label class="checkbox-inline"> <!-- <input type="checkbox"
										class="primary" id="request"
										ng-click="reloadtabledata($event);">Closed <span
										class="cr"><i class="cr-icon fa fa-check"></i></span> -->
										
										<md-checkbox ng-checked="exists(3, selectedStatus)" ng-click="toggle(3,selectedStatus)">Rejected</md-checkbox>

									</label>
								</div>
							
							<!-- 	<div class="checkbox">
									<label class="checkbox-inline"> <input type="checkbox"
										id="isall" ng-click="reloadtabledata($event);">ALL <span
										class="cr"><i class="cr-icon fa fa-check"></i></span>
									</label> </label> <label class="checkbox-inline"> <input
										type="checkbox" checked="checked" class="primary"
										id="approved" ng-click="reloadtabledata($event);">New
										<span class="cr"><i class="cr-icon fa fa-check"></i></span>
									</label> <label class="checkbox-inline"> <input type="checkbox"
										checked="checked" class="primary" id="open"
										ng-click="reloadtabledata($event);">Processing <span
										class="cr"><i class="cr-icon fa fa-check"></i></span>
									</label> <label class="checkbox-inline"> <input type="checkbox"
										class="primary" id="request"
										ng-click="reloadtabledata($event);">Finished <span
										class="cr"><i class="cr-icon fa fa-check"></i></span>
									</label> <label class="checkbox-inline"> <input type="checkbox"
										class="primary" id="rejected"
										ng-click="reloadtabledata($event);">Rejected <span
										class="cr"><i class="cr-icon fa fa-check"></i></span>
									</label>
								</div> -->
								<table datatable="" dt-options="item.dtOptions"
									dt-columns="item.dtColumns" dt-instance="item.dtInstance"
									class="table dataClass"></table>
							</div>
							<jsp:directive.include file="../remotepurchaserequest/form.jsp" />
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

<script src="<c:url value='/resources/common/js/mrpApp.js?n=1' />"></script>
<script
	src="<c:url value='/resources/mrp/remotepurchaserequest/js/pr.js?n=1' />">
	
</script>
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
		  
	
		if($('#show_form').val()==1){  
			
			window.location.href = "list";
			$('#show_form').val(0); 
		}else{
			 window.history.back();
		 }  

	});

	}
	});

</script>

</body>
</html>