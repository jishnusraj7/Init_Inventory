
<jsp:directive.include file="../common/includes/page_directives.jsp" />



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
	href="<c:url value='/resources/mrp/purchasereturn/css/purchasereturn.css' />">
</head>
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
	<!-- Content Header (Page header) -->

	<div class="list-content-header" ng-controller="btn_ctrl">
		<input id="iscentralized" type='hidden'
			value="${currentuser.is_central_purchase}" />

		<section class="content-header content-header_second">
			<h1>
				<spring:message code="purchasereturn.header"></spring:message>
			</h1>
			<div ng-hide="succ_alertMessageStatus" class="alert-box"
				id="succ_alertMessageId">{{ succ_alertMeaasge }}</div>
			<div ng-hide="err_alertMessageStatus" class="erroralert-box"
				id="err_alertMessageId">{{ err_alertMeaasge }}</div>
		</section>

		<div class="new_box">
			<div class="box cont_div_box">
				<jsp:directive.include file="../common/includes/common_buttons.jsp" />
			</div>
		</div>
		<div class="container" style="padding-left: 150px;" id="advsearchbox">
			<div class="row">
				<div class="col-md-3">
					<div id="SearchCriteria"
						style="margin-top: -50px; position: absolute; top: 7px; left: 21px; z-index: 4;">

					</div>
					<div class="input-group" id="adv-search">
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
												<label for="filter">Status</label> <select
													class="form-control" id="returnStatus">
													<option value="" selected>SELECT</option>
													<!-- <option value="0">NEW</option> -->
													<option value="0">PENDING</option>
													<option value="1">FINALIZED</option>
												</select>
											</div>

											<div class="form-group">
												<div class="col-md-13">
													<label for="contain">Return Date</label>

													<div>
														<input type="text" id="returnDate"
															class=" dtformData datepicker">
													</div>
												</div>
											</div>

											<div class="form-group">
												<label for="contain">Return No:</label> <input
													class="form-control" id="returnNo" type="text" />
											</div>

											<div class="form-group">
												<label for="contain">Supplier Name:</label> <input
													class="form-control" id="supplierName" type="text" />
											</div>
											<button style="float: right; margin-left: 10px;"
												type="button" class="btn btn-default" data-dismiss="modal"
												data-toggle="dropdown" id="closebtnew">Close</button>

											<button style="float: right;" type="submit"
												ng-click="advSearch();" class="btn btn-primary"
												data-dismiss="modal" data-toggle="dropdown"
												style="  height: 29px;margin-top: 1px;">
												<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
											</button>

										</form>
									</div>
								</div>
								<button type="button" class="btn btn-primary" id="searchNew">
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

						<div ng-controller="purchasereturn as item">
							<div class="form-group" ng-show="show_table" id="div_table_show">

								<circle-spinner ng-show="prograssing1"></circle-spinner>

								<div class="form-group" ng-show="show_table" id="div_table_show">
									<table datatable="" dt-options="item.dtOptions"
										dt-columns="item.dtColumns" dt-instance="item.dtInstance"
										class="table dataClass"></table>
								</div>
							</div>
							<jsp:directive.include file="../purchasereturn/form.jsp" />
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
	src="<c:url value='/resources/mrp/purchasereturn/js/purchasereturn.js?n=1' />"></script>
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