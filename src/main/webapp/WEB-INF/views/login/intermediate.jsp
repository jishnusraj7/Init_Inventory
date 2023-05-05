<%
	String companyPath = request.getContextPath();
%>
<jsp:directive.include file="../common/includes/page_directives.jsp" />




<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
.production_btn_div, .sale_btn_div {
    background: #ccc none repeat scroll 0 0;
    border-radius: 12px;
    float: left;
    font-size: 24px;
    font-weight: bold;
    margin-left: 15px;
    padding: 54px 0;
    text-align: center;
    width: 47%;
}
.production_btn_div:hover, .sale_btn_div:hover {
    color: #fff;
}
.production_btn_div {
    background: #fc6c3d;
    color: #fff;
}
.sale_btn_div {
    background: #3c8dbc;
    color: #fff;
}

.production_btn_div:hover {
    background: #e85829;
    color: #fff;
}
.sale_btn_div:hover {
    background: #3081b0;
    color: #fff;
}
.button_prd_sale_main_div {
    margin: 0 auto;
    width: 50%;
}
</style>

<head>



<!-- common css include below -->
<jsp:directive.include file="../common/includes/header.jsp" />



<!-- custom css can include below -->
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/supplier/css/supplier.css' />">
</head>


<jsp:directive.include
	file="../common/includes/common_module_header.jsp" />

<!-- design left template include below -->

<!-- Content Wrapper. Contains page content -->


<div class="content-wrapper header_second" ng-app="mrp_app"
	id="mrp_App_Id" ng-cloak style="background-color:#ecf0f5; margin-left:0">

	<!-- <div class="content-wrapper header_second" ng-app="supplier_app"
	id="supplier_app" ng-cloak> -->

	


	<!-- Main content -->
	<section class="content" id="module_content" >
		<div class="row" style="text-align: center;margin-bottom: 10px;">
			<div class="col-xs-12">
				<img src="resources/common/images/INventory2.png"
					style="width: 20%; margin-bottom: 1px;">
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">


				<div class="box_button-main_div">
					<div class="box-header with-border" style="background-color:#ecf0f5">
						<h3 class="box-title"></h3>
					</div>
					<!-- /.box-header -->

					<div class="box-body"  style="background-color:#ecf0f5">
						<div class="box-header"></div>
						<jsp:directive.include file="../common/includes/common_popup_form.jsp" />

						<div class="button_prd_sale_main_div" ng-controller="intermediatectrl as item">
		
	                        
							<a href="<%=companyPath%>/redirectToProd" class="production_btn_div">PRODUCTION</a>
								<a href="<%=companyPath%>/redirect" class="sale_btn_div">SALES</a>
							
							<!-- <button type="button" class="btn btn-primary " id="btn_sales">SALES</button> -->
							
							<!-- <button type="button" class="btn btn-primary " id="btn_prod"
							ng-click="goToProduction()">PRODUCTION</button>
							 -->


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

	<script src="resources/mrp/login/js/intermediate.js"></script>
<script
	src="<c:url value='/resources/common/js/views/ButtonController.js?n=1' />"></script>
<script> angular.bootstrap(document.getElementById("mrp_App_Id"), ['mrp_app']);
</script>

</body>
</html>