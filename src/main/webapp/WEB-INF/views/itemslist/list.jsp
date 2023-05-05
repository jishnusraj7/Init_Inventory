
<jsp:directive.include file="../common/includes/page_directives.jsp" />

<%-- <c:set var="formPageUrl" value="../../looksup/itemcategory/form.jsp"
	scope="request" /> --%>
<%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.enumitemType"%>

<c:set var="enumitemType" value="<%=enumitemType.values()%>"></c:set>
<%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.enumValuationMethod"%>

<c:set var="enumValuationMethod"
	value="<%=enumValuationMethod.values()%>"></c:set>

<!DOCTYPE html>
<html lang="en">
<head>

<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />
<%-- <c:set var="canAdd"
	value="${(permission.canAdd == 1 && permission.isAddApplicable == 1) ? true : false}"></c:set>
<c:set var="canEdit"
	value="${(permission.canEdit == 1 && permission.isEditApplicable == 1) ? true : false}"></c:set>
<c:set var="CanDelete"
	value="${(permission.canDelete == 1 && permission.isDeleteApplicable == 1) ? true : false}"></c:set>
<c:set var="CanExcute"
	value="${(permission.canExecute == 1 && permission.isExecuteApplicable == 1) ? true : false}"></c:set>
<c:set var="CanExport"
	value="${(permission.canExport == 1 && permission.isExportApplicable == 1) ? true : false}"></c:set>
 --%>


<!-- custom css can include below -->
 <link rel="stylesheet"
	href="<c:url value="/resources/mrp/itemslist/css/itemslist.css" />"> 
</head>

<!-- design header template include below -->
<jsp:directive.include file="../common/includes/subheader.jsp" />
<jsp:directive.include
	file="../common/includes/common_module_header.jsp" />

<!-- design left template include below -->
<!-- Left side column. contains the logo and sidebar -->
<jsp:directive.include file="../common/includes/leftmenu.jsp" />
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper header_second" ng-app="mrp_app"
	id="mrp_App_Id" ng-controller="items_list as showCase" ng-cloak>
	<!-- Content Header (Page header) -->
	<div class="list-content-header" >
		<section class="content-header content-header_second" ng-controller="btn_ctrl">
			<h1>
				<spring:message code="itemmaster.item.settings.header"></spring:message>
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
				<li><a href="#"><spring:message code="common.menu.settings"></spring:message></a></li>
				<li><a href="#"><spring:message
							code="common.menu.products"></spring:message></a></li>
				<li><a href="#"><spring:message
							code="common.menu.inventory.itemmaster"></spring:message></a></li>
			</ol>
		</section>

		<div class="new_box" style="
    background: white;
">
			
			<button ng-click="updateIsActiveAndSync()" type="button" class="btn btn-info btn-lg align-right pull-right backbtn">
             Update 
       		</button>
		</div>
		<div class="container" style="padding-left: 150px;" id="advsearchbox" ng-controller="btn_ctrl" ng-show="true">
	<div class="row">
		<div class="col-md-3">
            <div class="input-group" id="adv-search" >
                <!-- <input style="height: 31px;" type="text" id="SearchText" class="form-control" placeholder="Search ..." contenteditable="true"  /> -->
               		<!-- <input type="text" name="search" style="width: 260px;" id="SearchText" value="" ng-model="searchTxt"/> -->
               		<div id="SearchText"  name="search" contenteditable="true" style="width: 260px;"></div>
               		<a href="" id="clear"></a>
					<div class="input-group-btn">
                
                    <div class="btn-group searchbttn" role="group">
                  
                        <div class="dropdown dropdown-lg">
                            <button type="button" class="btn btn-default dropdown-toggle" style="height: 30px;" data-toggle="dropdown" aria-expanded="false"><span class="caret"></span></button>
                            <div class="dropdown-menu dropdown-menu-right dropsize" role="menu">
                                <form class="form-horizontal" role="form">
                                 
                                  <div class="form-group">
                                    <label for="contain">Code :</label>
                                    <input class="form-control" id ="itmCode" type="text" />
                                  </div>
                                  <div class="form-group">
                                    <label for="contain">Name :</label>
                                    <input class="form-control" id ="itmName" type="text" />
                                  </div>
                                 
                                  
                                  <div class="form-group"  >
					                <label for="filter">Item Category</label>
										<select id="itm_category_id"  class="form-control" 
											name="itm_category_id" 
											ng-options="v.id as v.name for v in itmCatgryList1"
											ng-model="itm_category_id1">
										</select>			
				                 </div>
                                  
                                  <button style="float: right;  margin-left: 10px;" type="button" class="btn btn-default" data-dismiss="modal" data-toggle="dropdown" >Close</button>
                                  
                                   <button  style="float: right;" type="submit"  ng-click="advSearch();" class="btn btn-primary" data-dismiss="modal" data-toggle="dropdown"  style="  height: 29px;margin-top: 1px;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                                  
                                </form>
                            </div>
                        </div>
                        <button type="button" class="btn btn-primary" ><span class="glyphicon glyphicon-search" aria-hidden="true" ng-click="Search();" data-dismiss="modal" data-toggle="dropdown"></span></button>
                    </div>
                </div>
            </div>
          </div>
        </div>
	</div>
		
	</div>
	
	


	<!-- Main content -->
	<section class="content" id="module_content" >
		<div class="row">
			<div class="col-xs-12">

				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title"></h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">

						<div>
											 
							<div class="form-group" ng-show="show_table" id="div_table_show">
								<table datatable="" dt-options="showCase.dtOptions" 
									dt-columns="showCase.dtColumns" dt-instance="showCase.dtInstance"
									class="table dataClass"></table> 
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

<script src="<c:url value='/resources/common/js/mrpApp.js' />"></script>
<script
	src="<c:url value="/resources/mrp/itemslist/js/itemslist.js" />"></script>
<script
	src="<c:url value='/resources/common/js/views/ButtonController.js' />"></script>
<script>
	angular.bootstrap(document.getElementById("mrp_App_Id"), [ 'mrp_app' ]);
	var settings = new Array();
	settings['currentcompanyid'] = '<c:out value="${currentcompanydetails.id}"/>';
</script>

</body>
</html>
