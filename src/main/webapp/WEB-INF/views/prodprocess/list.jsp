<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@page
	import="com.indocosmo.mrp.utils.core.persistence.enums.EnumMatsers.stockDisposalReasonType"%>
<c:set var="enumValues" value="<%=stockDisposalReasonType.values()%>" />
<%-- <c:set var="formPageUrl" value="../../looksup/itemcategory/form.jsp"
	scope="request" /> --%>


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
	href="<c:url value='/resources/mrp/prodprocess/css/prod_processing.css' />">

</head>
<body>
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
		<div ng-controller="btn_ctrl"><h3>Production Processing</h3>
		
			<div ng-hide="succ_alertMessageStatus" class="alert-box"
					id="succ_alertMessageId">{{ succ_alertMeaasge }}</div>
				<div ng-hide="err_alertMessageStatus" class="erroralert-box"
					id="err_alertMessageId">{{ err_alertMeaasge }}</div>
		
		</div>
		
	<div ng-controller="prodprcess" >	
	<!-- <div ng-hide="succ_alertMessageStatusModal" class="alert-box"
		id="succ_alertMessageId">{{ succ_alertMeaasgeModal }}</div>
	<div ng-hide="err_alertMessageStatusModal" class="erroralert-box"
		id="err_alertMessageId">{{ err_alertMeaasgeModal }}</div> -->
	
	<jsp:directive.include file="../prodprocess/form.jsp" />
	
	
	
	
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
		src="<c:url value='/resources/mrp/prodprocess/js/prod_processing.js?n=1' />"></script> 
		
		<script
		src="<c:url value='/resources/common/js/views/ButtonController.js?n=1' />"></script>
	
	<script> angular.bootstrap(document.getElementById("mrp_App_Id"), ['mrp_app']);</script>
	<!-- <script type="text/javascript">

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

</script> -->
</body>
</html>
