
<jsp:directive.include file="../common/includes/page_directives.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />
<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/planning/css/planning.css' />">
<link href="vendor/angular-moment-picker/angular-moment-picker.min.css"
	rel="stylesheet">
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
<input type="hidden" id="show_form" value="0">
<div class="content-wrapper header_second " ng-app="mrp_app"
	id="mrp_App_Id" ng-cloak>
	<div class="mrp_header">&nbsp;</div>
	<div class="mrpappcntdiv">
		<div class="col-sm-3 calenderdiv" ng-controller="clndr_ctrl"
			ng-show="clndrshow">



			<div class="box1">
				<!--  <circle-spinner ng-show="prograssing"></circle-spinner> -->



				<multiple-date-picker calendar-id="'myId2'" ng-model="selectedDays3"
					day-click="oneDaySelectionOnly" highlight-days="highlightDays" />





			</div>
			<div class="box2">
				<div class="orderlabel cust_ordr">
					<label style="color: black">CUSTOMER ORDER:</label><span>{{totalorderList[0].cust_order}}</span>
				</div>
				<div class="orderlabel shp_ordr">
					<label style="color: black">SHOP ORDER:</label><span>{{totalorderList[0].shop_order}}</span>
				</div>
				<div class="orderlabel totl_ordr">
					<label style="color: black">TOTAL ORDER:</label><span>{{totalorderList[0].total_order}}</span>
				</div>
			</div>
			<div class="box3">
				<div class="orderlabel">
					<label style="color: green">PENDING ORDER:</label><span
						style="background: green">{{totalorderList[0].pending}}</span>
				</div>
				<div class="orderlabel">
					<label style="color: #3c8dbc">VERIFIED ORDER:</label><span
						style="background: #3c8dbc">{{totalorderList[0].finalized}}</span>
				</div>
				<div class="orderlabel" ng-hide="true">
					<label style="color: #e313d3">PRODUCTION ORDER:</label><span
						style="background: #bf4ab6">{{totalorderList[0].production}}</span>
				</div>
				<div class="orderlabel" ng-hide="true">
					<label style="color: red">FINISHED ORDER:</label><span
						style="background: #e64747">{{totalorderList[0].finished}}</span>
				</div>
			</div>

		</div>
		<div class="col-sm-9" id="dataTabform">
			<div class="full_width_div">



				<div class="list-content-header" ng-controller="btn_ctrl">

					<!-- Content Header (Page header) -->
					<section class="content-header content-header_second">
						<h1>
							PRODUCTION ORDER
							<%-- <spring:message code="uom.header"></spring:message> --%>
						</h1>

						<div ng-hide="succ_alertMessageStatus" class="alert-box alertmsg"
							id="succ_alertMessageId">{{ succ_alertMeaasge }}</div>
						<div ng-hide="err_alertMessageStatus"
							class="erroralert-box alertmsg" id="err_alertMessageId">{{
							err_alertMeaasge }}</div>


						<ol class="breadcrumb breadcrumb_position" ng-hide="true">
							<li><a href="#"><i class="fa fa-dashboard"></i> <spring:message
										code="common.menu.home"></spring:message></a></li>
							<li><a href="#"><spring:message code="common.menu.store"></spring:message></a></li>
							<li><a href="#">Sales Order<spring:message
										code="common.menu.inventory.purchaseorder"></spring:message></a></li>
						</ol>

					</section>

					<div class="new_box">

						<div class="box cont_div_box">
							<jsp:directive.include
								file="../common/includes/common_buttons.jsp" />

						</div>



					</div>





					<div class="container advcsrch" style="padding-left: 150px;"
						id="advsearchbox">
						<!-- <div class="row"> -->
						<!-- <div class="col-md-3"> -->
						<div class="input-group advncinput" id="adv-search">
							<!--                 <input style="height: 31px;" type="text" id="SearchText" class="form-control" placeholder="" />
 -->
							<!--  <input type="text" name="search" style="width: 260px;" id="SearchText" value="" /> -->

							<div id="SearchText" name="search" contenteditable="true"
								style="width: 260px;"></div>
							<a href="" id="clear"></a>
							<div class="input-group-btn">
								<div class="btn-group searchbttn" role="group">
									<div class="dropdown dropdown-lg">
										<button type="button" style="height: 30px;"
											class="btn btn-default dropdown-toggle"
											data-toggle="dropdown" aria-expanded="false">
											<span class="caret"></span>
										</button>
										<div class="dropdown-menu dropdown-menu-right dropsize"
											role="menu">
											<form class="form-horizontal" role="form">
												<div class="form-group">
													<label for="filter">Order Type</label> <select
														class="form-control" id="ordertype">
														<option value="" selected>Select</option>
														<option value="2">Shops</option>
														<option value="1">Customer</option>
													</select>
												</div>
												<!--  <div class="form-group">
                                    <label for="contain">Order Placed On</label>
                                    <input id="orderDate" type="date" class="form-control" type="text" />
                                  </div> -->
												<div class="form-group">
													<label for="contain">Order No:</label> <input id="ordernum"
														class="form-control" type="text" />
												</div>

												<div class="form-group">
													<label for="filter">Status:</label> <select
														class="form-control" id="orderStatus">
														<option value="" selected>ALL</option>
														<option value="1">VERIFIED</option>
														<option value="4">DELIVERED</option>
														<!-- <option value="2">ON PRODUCTION</option> -->


													</select>
												</div>

												<button style="float: right; margin-left: 10px;"
													type="button" class="btn btn-default" data-dismiss="modal"
													data-toggle="dropdown" id="closebtnew">Close</button>

												<button style="float: right;" type="submit"
													ng-click="advSearch();" class="btn btn-primary"
													style="  height: 29px;margin-top: 1px;"
													data-toggle="dropdown">
													<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
												</button>

											</form>
										</div>
									</div>
									<button type="button" class="btn btn-primary">
										<span class="glyphicon glyphicon-search" aria-hidden="true"
											ng-click="Search();"></span>
									</button>
								</div>
							</div>
						</div>
						<!-- </div> -->
						<!-- </div> -->
					</div>
				</div>
				<!-- <div>
		
	</div> -->


				<!-- Main content -->
				<section class="content" id="module_content">
					<div class="row">
						<div class="col-xs-12">


							<div class="box" style="margin-top: -50px;">
								<div class="box-header with-border">
									<h3 class="box-title"></h3>
								</div>
								<!-- /.box-header -->
								<div class="box-header"></div>

								<div id="stockcontrlr_div" ng-controller="uomctrl as item">

									<!-- 	
						<div class="col-sm-3 calenderdiv" ng-show="show_table" >	

                         

                                <div class="box1">
                                <circle-spinner ng-show="prograssing"></circle-spinner>


                                             
                                 <multiple-date-picker calendar-id="'myId2'" ng-model="selectedDays3" day-click="oneDaySelectionOnly"/>

                                  



								</div>
                               <div class="box2">
                               <div class="orderlabel">
                               <label>CUSTOMER ORDER:</label><span>{{totalorderList[0].cust_order}}</span></div>
                              <div class="orderlabel">
                                <label>SHOP ORDER:</label><span>{{totalorderList[0].shop_order}}</span></div>
                                 <div class="orderlabel"><label>TOTAL ORDER:</label><span>{{totalorderList[0].total_order}}</span></div>
                                </div>
                                  <div class="box3">
                                  <div class="orderlabel"> <label>PENDING ORDER:</label><span>{{totalorderList[0].pending}}</span></div>
                                <div class="orderlabel"><label>FINALIZED ORDER:</label><span>{{totalorderList[0].finalized}}</span></div>
                                <div class="orderlabel"> <label>PRODUCTION ORDER:</label><span>{{totalorderList[0].production}}</span></div>
                                 <div class="orderlabel"> <label>FINISHED ORDER:</label><span>{{totalorderList[0].finished}}</span></div>
                            </div>
 
                           </div> -->

									<!-- 	<div class="col-sm-9"> -->

									<div class="form-group" ng-show="show_table"
										id="div_table_show">

										<table datatable="" dt-options="item.dtOptions"
											dt-columns="item.dtColumns" dt-instance="item.dtInstance"
											class="table dataClass"></table>
									</div>
									<circle-spinner ng-show="prograssing1"></circle-spinner>

									<!--   </div> -->


									<jsp:directive.include file="../planning/form.jsp" />
								
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
		</div>
		<!-- <div class="col-sm-12"></div> -->
	</div>

</div>
<!-- /.content-wrapper -->





<!-- include form-->

<!-- common js include below -->
<jsp:directive.include file="../common/includes/footer.jsp" />
<jsp:directive.include file="../common/includes/stockitemdata.jsp" />

<!-- page script -->

<!-- custom js include below -->
<script src="<c:url value='/resources/common/js/mrpApp.js?n=1' />"></script>
<script
	src="<c:url value='/resources/mrp/planning/js/planning.js?n=1' />"></script>
<script
	src="<c:url value='/resources/common/js/views/ButtonController.js?n=1' />"></script>
<script>
	angular.bootstrap(document.getElementById("mrp_App_Id"), [ 'mrp_app' ]);
</script>

<script type="text/javascript">
	jQuery(document).ready(
			function($) {

				var newwidth = $(window).width() - 245;
				$(".content-wrapper").width(newwidth);

				if (window.history && window.history.pushState) {

					window.history.pushState('forward', null, './list');

					$(window).on('popstate', function() {

						if ($('#show_form').val() == 1) {

							window.location.href = "list";
							$('#show_form').val(0);

						} else

						if ($('#show_form').val() == 2) {

							getBackPage();
							manageButtons("add");

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
					})
				}

			});
</script>

</body>
</html>
