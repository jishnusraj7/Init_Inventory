
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
	href="<c:url value='/resources/mrp/purchaseorder/css/po.css' />">
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
				<spring:message code="purchaseorder.header"></spring:message>
			</h1>
			<div ng-hide="succ_alertMessageStatus" class="alert-box"
				id="succ_alertMessageId">{{ succ_alertMeaasge }}</div>
			<div ng-hide="err_alertMessageStatus" class="erroralert-box"
				id="err_alertMessageId">{{ err_alertMeaasge }}</div>
			<%-- <!-- <div ng-hide="saveAlertMessage" class="alert-box">{{msg}}</div>   -->
			<div ng-hide="saveAlertMessage" class="alert-box">
				<spring:message code="common.saveMessage"></spring:message>
			</div>
			<div ng-hide="suppliererrorcode" class="erroralert-box">
				<spring:message code="common.suppliererror"></spring:message>
			</div>

			<div ng-hide="dateerror" class="erroralert-box">
				<spring:message code="common.dateerror"></spring:message>
			</div>



			<div ng-hide="deleteAlertMessage" class="alert-box">
				<spring:message code="common.deleteMessage"></spring:message>
			</div>
			<div ng-hide="stockItemError" class="alert-box">
				<spring:message code="common.stockItemError"></spring:message>
			</div>

			<div ng-hide="stockitemqtyError" class="erroralert-box">
				<spring:message code="common.stockitemqtyError"></spring:message>
			</div>

			<div ng-hide="stockItemCodeError" class="erroralert-box">
				<spring:message code="common.stockItemCodeError"></spring:message>
			</div>

			<div ng-hide="stockItemCodeExistingError" class="erroralert-box">
				<spring:message code="common.stockItemCodeExistingError"></spring:message>
			</div>

 --%>

			<%-- <ol class="breadcrumb breadcrumb_position">
				<li><a href="#"><i class="fa fa-dashboard"></i> <spring:message
							code="common.menu.home"></spring:message></a></li>
				<li><a href="#"><spring:message code="common.menu.store"></spring:message></a></li>
				<li><a href="#"><spring:message
							code="common.menu.inventory.purchaseorder"></spring:message></a></li>
			</ol> --%>
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
							name="btnNext" id="btnNext" ng-click="fun_show_form()">
							<i class="fa fa-caret-right" aria-hidden="true"></i><i
								class="fa fa-caret-right" aria-hidden="true"></i> Next
						</button>


					</div>
				</div>
				<jsp:directive.include file="../common/includes/common_buttons.jsp" />
				<div class="dataTables_length alignselctbox"
					style="padding-left: 150px;" id="beforebtns">


					<div class="col-sm-9" style="margin-right: 15px;"></div>
					<div class="col-sm-1">
						<button class="btn  btn-sm " type="button" name="btnBack"
							id="btnBack" ng-click="fun_backTo_table()">
							<i class="fa fa-caret-left" aria-hidden="true"></i><i
								class="fa fa-caret-left" aria-hidden="true"></i> Back
						</button>
					</div>
					<div class="col-sm-1">

						<button class="btn  btn-sm btn-primary add_btn " type="button"
							name="createpo" id="createpo" ng-click="import_data()"
							ng-disabled="createpo">
							<i class="fa fa-plus"></i> Create Po
						</button>

					</div>

				</div>
			</div>
		</div>
		<div class="container" style="padding-left: 150px;" id="advsearchbox">
	<div class="row">
		<div class="col-md-3">
		<div id="SearchCriteria" style="     margin-top: -50px;
    position: absolute;
    top: 7px;
    left: 21px;
    z-index: 4;">
            
            </div>
            <div class="input-group" id="adv-search" >
            
<!--                 <input style="height: 31px;   "  type="text" id="SearchText" class="form-control" placeholder="" />
 --> <!-- <input type="text" name="search" style="width: 260px;" id="SearchText" value="" /> -->
                		<div id="SearchText"  name="search" contenteditable="true" style="width: 260px;"></div>
 
 <a href="" id="clear"></a>            
            
                <div class="input-group-btn">
                    <div class="btn-group searchbttn" role="group">
                        <div class="dropdown dropdown-lg">
                            <button type="button" class="btn btn-default dropdown-toggle"   style="
    height: 30px;
" aria-expanded="false" id="clearFeilds"><span class="caret"></span></button>
                            <div class="dropdown-menu dropdown-menu-right dropsize" role="menu" id="dropdownnew">
                                <form class="form-horizontal" role="form">
                                  <div class="form-group">
                                    <label for="filter">Status</label>
                                    <select class="form-control" id="ordertype">
                                      <option value="" selected>SELECT</option>
                                        <option value="0" >NEW</option>
                                        <option value="1">PRINTED</option>
                                    <option value="2">CLOSED</option>
                                    
                                    </select>
                                  </div>
                             
                                  	<div class="form-group">
													<div class="col-md-13">
														<label for="contain">PO Date</label>

														<div>
															  <input type="text" id="orderDate" class=" dtformData datepicker" >


														</div>
													</div>
												</div>
												
                             
                             <!--      <div class="form-group">
                                    <label for="contain">PO Date</label>

                                    <input type="text" id="orderDate" class=" dtformData datepicker" >

                                    <input id="orderDate" date-picker class="form-control" type="text" />
                                  </div> -->
                                  <div class="form-group">
                                    <label for="contain">PO No:</label>
                                    <input class="form-control" id ="reqNo" type="text" />
                                  </div>
                                  
                                  <div class="form-group">
                                    <label for="contain">Supplier Name:</label>
                                    <input class="form-control" id ="suppliername" type="text" />
                                  </div>
                                  <button style="float: right;  margin-left: 10px;" type="button" class="btn btn-default" data-dismiss="modal" data-toggle="dropdown" id="closebtnew" >Close</button>
                                  
                                                                    <button  style="float: right;" type="submit"  ng-click="advSearch();" class="btn btn-primary" data-dismiss="modal" data-toggle="dropdown"  style="  height: 29px;margin-top: 1px;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                                  
                                </form>
                            </div>
                        </div>
                        <button type="button" class="btn btn-primary"  id="searchNew"><span class="glyphicon glyphicon-search" aria-hidden="true" ng-click="Search();" data-dismiss="modal" data-toggle="dropdown"></span></button>
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

						<div ng-controller="poctrl as item">





							<div class="form-group" ng-show="show_table" id="div_table_show">
								<!-- <div class="checkbox">
									<label class="checkbox-inline"> <input type="checkbox"
										id="isall" ng-click="reloadtabledata($event);">ALL <span
										class="cr"><i class="cr-icon fa fa-check"></i></span>
									</label> <label class="checkbox-inline"> <input type="checkbox"
										checked="checked" class="primary" id="approved"
										ng-click="reloadtabledata($event);">New <span
										class="cr"><i class="cr-icon fa fa-check"></i></span>
									</label> <label class="checkbox-inline"> <input type="checkbox"
										checked="checked" class="primary" id="open"
										ng-click="reloadtabledata($event);">Printed <span
										class="cr"><i class="cr-icon fa fa-check"></i></span>

									</label><label class="checkbox-inline"> <input type="checkbox"
										class="primary" id="request"
										ng-click="reloadtabledata($event);">Closed <span
										class="cr"><i class="cr-icon fa fa-check"></i></span>

									</label>
								</div> -->

								<div class="checkbox">
									<label class="checkbox-inline"> <md-checkbox
											aria-label="Select All" ng-checked="isChecked()"
											md-indeterminate="isIndeterminate()"
											ng-click="toggleAllStatus()">All</md-checkbox>
									</label> <label class="checkbox-inline"> <md-checkbox
											ng-checked="exists(0, selectedStatus)"
											ng-click="toggle(0, selectedStatus)">New </md-checkbox>
									</label> <label class="checkbox-inline"> <md-checkbox
											ng-checked="exists(1,selectedStatus)"
											ng-click="toggle(1,selectedStatus)">Printed </md-checkbox>
									</label> <label class="checkbox-inline"> <md-checkbox
											ng-checked="exists(2, selectedStatus)"
											ng-click="toggle(2,selectedStatus)">Closed </md-checkbox>

									</label>
								</div>



								<table datatable="" dt-options="item.dtOptions"
									dt-columns="item.dtColumns" dt-instance="item.dtInstance"
									class="table dataClass"></table>
							</div>
							<div class="" id="div_table_show" ng-show="show_form2"
								style="width: 100%; height: 100%;">

								<div style="float: left; width: 50%; height: 100%">


									<div class="form-group" style="padding-bottom: 27px;">
										<div class="col-sm-2">
											<label for="po" class="alignlabel">Company</label>
										</div>
										<div class="col-sm-6">
											<select class="form-control" name="companyId" id="companyId"
												ng-click="search()">
												<c:forEach var="hqdata" items="${hqCompanyData}">
													<option value="${hqdata.code}"><c:out
															value="${hqdata.code}" /></option>
												</c:forEach>
											</select>



										</div>

									</div>
									<div
										style="overflow: scroll; height: 500px; overflow-x: hidden;">
										<table datatable="" dt-options="item.dtOptions1"
											dt-columns="item.dtColumns1" dt-instance="item.dtInstance1"
											class="table dataClass"></table>
									</div>
								</div>


								<div
									style="float: left; height: 100%; width: 50%; padding-top: 42px; padding-left: 10px;">


									<div class="form-group" style="height: 500px;">
										<div
											style="overflow: scroll; height: 500px; overflow-x: hidden;">
											<table
												class="dataTables_wrapper form-inline dt-bootstrap no-footer row-border hover table table-bordered table-striped"
												id="items_table">
												<tr class="active">
													<th style="width: 180px;">Company</th>

													<th>Name</th>
													<th style="width: 60px;">Qty</th>
													<th></th>

												</tr>

												<tr ng:repeat="item in remoteorderdtldata.items">
													<td style="display: none;">{{$index + 1}}</td>

													<td style="width: 180px; height: 29px;">{{item.request_company_name}}</td>
													<td>{{item.stock_item_name}}</td>

													<td style="width: 60px; height: 29px;">{{item.qty}}</td>
													<td style="width: 40px;"><a href
														ng:click="removeItem1($index)"
														style="width: 37px; height: 29px;" class="btn btn-small"><i
															class="fa fa-minus "></i> </a></td>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</div>

							<jsp:directive.include file="../purchaseorder/form.jsp" />



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
<script src="<c:url value='/resources/mrp/purchaseorder/js/po.js?n=1' />"></script>
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