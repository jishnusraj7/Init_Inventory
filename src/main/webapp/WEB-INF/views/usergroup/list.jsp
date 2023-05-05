
<jsp:directive.include file="../common/includes/page_directives.jsp" />



<!DOCTYPE html>
<html lang="en">
<head>
<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />
<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/usergroup/css/usergroup.css' />">
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

<input type="hidden" value="0" id="show_form">
<div class="content-wrapper header_second" ng-app="mrp_app"
	id="mrp_App_Id" ng-cloak>
	<div class="list-content-header" ng-controller="btn_ctrl">

		<!-- Content Header (Page header) -->
		<section class="content-header content-header_second">
			<h1>
				<spring:message code="usergroup.header"></spring:message>
			</h1>
			<div ng-hide="succ_alertMessageStatus" class="alert-box" id="succ_alertMessageId">
				 {{ succ_alertMeaasge }}
			</div>
			<div ng-hide="err_alertMessageStatus" class="erroralert-box" id="err_alertMessageId">
				 {{ err_alertMeaasge }}
			</div>

			<%-- <div ng-hide="saveAlertMessage" class="alert-box">
				<spring:message code="common.saveMessage"></spring:message>
			</div>
			<div ng-hide="updateAlertMessage" class="alert-box">
				<spring:message code="common.updateMessage"></spring:message>
			</div>
			<div ng-hide="usergroupinuse" class="erroralert-box">
				<spring:message code="usergroup.error.delete"></spring:message>
			</div>
			<div ng-hide="deleteAlertMessage" class="alert-box">
				<spring:message code="common.deleteMessage"></spring:message>
			</div> --%>


			<ol class="breadcrumb breadcrumb_position">
				<li><a href="#"><i class="fa fa-dashboard"></i> <spring:message
							code="common.menu.home"></spring:message></a></li>
				<li><a href="#"><spring:message code="common.menu.settings"></spring:message></a></li>
				<li><a href="#"><spring:message
							code="common.menu.inventory"></spring:message></a></li>
				<li><a href="#"><spring:message
							code="common.menu.inventory.usergroup"></spring:message></a></li>
			</ol>
		</section>

		<div class="new_box">
			<div class="box cont_div_box">

				<jsp:directive.include file="../common/includes/common_buttons.jsp" />
				<!-- 			<div class="box-header">
 -->
				<div class="box-header rgt_header_btn" id="div_btn_SET">
					<div class="user_groud_per_head">
						<span id="usergroupname"></span>
					</div>
					<div class="pull-right">
						<button class="btn  btn-sm " type="button" name="btnBack"
							id="btnBack" ng-click="fun_backTo_table()">
							<i class="fa fa-caret-left" aria-hidden="true"></i><i
								class="fa fa-caret-left" aria-hidden="true"></i> Back
						</button>
						<button class="btn  btn-sm btn-danger discard_btn discard_button"
							type="button" name="btnDiscard" id="btnDiscard"
							ng-click="fun_discard_form2()">
							<i class="fa fa-recycle" aria-hidden="true"></i> Discard
						</button>

						<button class="btn  btn-sm btn-primary save_button savebtn" type="button"
							name="btnSave" id="btnSave" ng-click="fun_save_form()">
							<i class="fa fa-floppy-o" aria-hidden="true"></i> Save
						</button>


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
<jsp:directive.include file="../common/includes/common_popup_form.jsp" />
						<div ng-controller="user_group as item">
							<div class="form-group" ng-show="show_table" id="div_table_show">
								<table datatable="" dt-options="item.dtOptions"
									dt-columns="item.dtColumns" dt-instance="item.dtInstance"
									class="table dataClass"></table>
							</div>
							<jsp:directive.include file="../usergroup/form.jsp" />
							<jsp:directive.include file="../usergroup/form2.jsp" />
							<%-- 					        <jsp:directive.include file="../usergroup/form3.jsp" />
 --%>



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
<script src="<c:url value='/resources/mrp/usergroup/js/usergroup.js?n=1' />"></script>
<script> angular.bootstrap(document.getElementById("mrp_App_Id"), ['mrp_app']);
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
