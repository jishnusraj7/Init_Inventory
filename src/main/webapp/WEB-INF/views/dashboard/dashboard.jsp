<jsp:directive.include file="../common/includes/page_directives.jsp" />
<jsp:directive.include
	file="../common/includes/common_setpermission.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
<!-- common css include below -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Orange</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- custom css can include below -->

<!-- Bootstrap core CSS -->
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/dashboard/css/bootstrap.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/dashboard/css/bootstrap-reset.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/dashboard/css/style.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/dashboard/css/style-responsive.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/mrp/dashboard/font/font-awesome/css/font-awesome.css' />">


<!--external css-->



<!-- Custom styles for this template -->

<!-- <link href="/resources/mrp/dashboard/css/style.css" rel="stylesheet">
<link href="/resources/mrp/dashboard/css/style-responsive.css"
	rel="stylesheet" /> -->

</head>

<body>

	<section id="container">

		<!--header start-->
		<header class="header white-bg">
			<!--  <div class="sidebar-toggle-box">
                <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
            </div -->
			<!--logo start-->
			<a href="#" class="logo"><img
				src="/mrp/resources/mrp/dashboard/logo.png"></a>
			<!--logo end-->
			<div class="top_nav_menu_div">
	
				<ul class="nav navbar-nav">
				
						
						<li class="active"><a href="/mrp/dashboard/list" id="dashboard">Dashboard</a></li>
						<c:if test="${mainStr.getCanView()&& mainStr.getIsViewApplicable()}">
						<li><a href="/mrp/purchaseorderhdr/list" id="store">Store</a></li>
						</c:if>
						<c:if test="${mainPrd.getCanView() && mainPrd.getIsViewApplicable()}">
						<li><a href="/mrp/planning/list" id="production">Production</a></li>
						</c:if>
						<c:if test="${mainSales.getCanView() && mainSales.getIsViewApplicable()}">
						<li><a href="/mrp/dailysale/list" id="sale">Sales</a></li>
						</c:if>
						<c:if test="${mainAcc.getCanView() && mainAcc.getIsViewApplicable()}">
						<li><a href="/mrp/dailyexpenses/list" id="account">Accounts</a></li>
						</c:if>
						<c:if test="${mainRprt.getCanView() && mainRprt.getIsViewApplicable()}">
						<li><a href="/mrp/itemstock/currentstock" id="report">Report</a></li>
						</c:if>
						<%-- <c:if test="${mainQuick.getCanView() && mainQuick.getIsViewApplicable()}">
						<li ><a href="#"
									id="quicksearch">Quick Search</a></li>
						</c:if> --%>
						<c:if test="${mainSett.getCanView() && mainSett.getIsViewApplicable()}">
						<li ><a href="/mrp/supplier/list"
									id="settings">Settings</a></li>
						</c:if>
						

							



				</ul>
			</div>
			<div class="top-nav ">
				<!--search & user info start-->
				<ul class="nav pull-right top-menu">
					<!-- user login dropdown start-->
					<li class="dropdown"><a data-toggle="dropdown"
						class="dropdown-toggle login_image_div" href="#"> <img alt=""
							src="/mrp/resources/mrp/dashboard/avatar1_small.jpg"> <span
							class="username">	
								 <c:out value="${COMPANY_SESSION_INFO.name}"/>
						
								</span> <b
							class="caret"></b>
					</a>
						<ul class="dropdown-menu extended logout">
							<div class="log-arrow-up"></div>
							<li class="user_admin"><img
								src="/mrp/resources/mrp/dashboard/user2-160x160.jpg"
								class="img-circle" width="90px">
								<p>
									<span class="usernm"><c:out value="${user.name}" /></span> <small>Last
										Login Date:<c:out value="${user.loginDate}" />
									</small>
								</p></li>
							<li class="lgn_btm"><a href="/mrp/logout"
								class="btn btn-danger logout_btn"><i
									class="fa fa-circle-o-notch"></i> Sign out</a></li>
						</ul></li>
					<!-- user login dropdown end -->
				</ul>
				<!--search & user info end-->
			</div>
		</header>
		<!--header end-->
		<!--sidebar start-->
		<aside>
			<div id="sidebar" class="nav-collapse ">
				<!-- sidebar menu start-->
				<ul class="sidebar-menu" id="nav-accordion">
					<li id="sammary"><a class="active" href="#"> <i
							class="fa fa-file-text"></i> <span>Summary</span>
					</a></li>
					<!-- <li>
                        <a href="#">
                            <i class="fa fa-file-text"></i> 
							<span>Store</span>
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-file-text"></i> 
							<span>Production</span>
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-file-text"></i> 
							<span>Sales</span>
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-file-text"></i> 
							<span>Purchase</span>
                        </a>
                    </li>-->

				</ul>
				<!-- sidebar menu end-->
			</div>
		</aside>
		<!--sidebar end-->

		<section id="main-content">
			<section class="wrapper">
				<!--state overview start-->
				<div class="row state-overview">
					<div class="col-lg-3 col-sm-6">
						<section class="panel">
							<div class="symbol terques">
								<i class="fa fa-money"></i>
							</div>
							<div class="value">
								<strong class="rup_icon">&#8377;</strong>
								<h1 class="count Counting_value">0</h1>
								<p class="count_value">Current Stock Value</p>
							</div>
						</section>
					</div>
					<div class="col-lg-3 col-sm-6">
						<section class="panel">
							<div class="symbol red">
								<i class="fa fa-briefcase"></i>
							</div>
							<div class="value">
								<strong class="rup_icon">&#8377;</strong>
								<h1 class="count2 Counting_value">0</h1>
								<p class="count_value">This Month Production</p>
							</div>
						</section>
					</div>
					<div class="col-lg-3 col-sm-6">
						<section class="panel">
							<div class="symbol yellow">
								<i class="fa fa-shopping-cart"></i>
							</div>
							<div class="value">
								<strong class="rup_icon">&#8377;</strong>
								<h1 class="count3 Counting_value">0</h1>
								<p class="count_value">This Month Sales</p>
							</div>
						</section>
					</div>
					<div class="col-lg-3 col-sm-6">
						<section class="panel">
							<div class="symbol blue">
								<i class="fa fa-bar-chart-o"></i>
							</div>
							<div class="value">
								<strong class="rup_icon">&#8377;</strong>
								<h1 class="count4 Counting_value">0</h1>
								<p class="count_value">This Month Purchase</p>
							</div>
						</section>
					</div>
				</div>
				<!--state overview end-->

				<div class="row">
					<div class="col-lg-8">
						<!--custom chart start-->
						<div class="border-head">
							<h3>Sales & Purchase</h3>
						</div>
						<div class="custom-bar-chart">
							<ul class="y-axis">
								<li><span>100000</span></li>
								<li><span>80000</span></li>
								<li><span>60000</span></li>
								<li><span>40000</span></li>
								<li><span>20000</span></li>
								<li><span>0</span></li>
							</ul>
							<div class="bar">
								<div class="title">JAN</div>
								<div class="value tooltips" data-original-title="80%"
									data-toggle="tooltip" data-placement="top">80%</div>
								<div class="value tooltips purchase" data-original-title="55%"
									data-toggle="tooltip" data-placement="top">55%</div>
							</div>
							<div class="bar ">
								<div class="title">FEB</div>
								<div class="value tooltips" data-original-title="60%"
									data-toggle="tooltip" data-placement="top">60%</div>
								<div class="value tooltips purchase" data-original-title="20%"
									data-toggle="tooltip" data-placement="top">20%</div>
							</div>
							<div class="bar ">
								<div class="title">MAR</div>
								<div class="value tooltips" data-original-title="72%"
									data-toggle="tooltip" data-placement="top">72%</div>
								<div class="value tooltips purchase" data-original-title="65%"
									data-toggle="tooltip" data-placement="top">65%</div>
							</div>
							<div class="bar ">
								<div class="title">APR</div>
								<div class="value tooltips" data-original-title="80%"
									data-toggle="tooltip" data-placement="top">80%</div>
								<div class="value tooltips purchase" data-original-title="56%"
									data-toggle="tooltip" data-placement="top">56%</div>
							</div>
							<div class="bar">
								<div class="title">MAY</div>
								<div class="value tooltips" data-original-title="40%"
									data-toggle="tooltip" data-placement="top">40%</div>
								<div class="value tooltips purchase" data-original-title="30%"
									data-toggle="tooltip" data-placement="top">30%</div>
							</div>
							<div class="bar ">
								<div class="title">JUN</div>
								<div class="value tooltips" data-original-title="39%"
									data-toggle="tooltip" data-placement="top">39%</div>
								<div class="value tooltips purchase" data-original-title="30%"
									data-toggle="tooltip" data-placement="top">30%</div>
							</div>
							<div class="bar">
								<div class="title">JUL</div>
								<div class="value tooltips" data-original-title="75%"
									data-toggle="tooltip" data-placement="top">75%</div>
								<div class="value tooltips purchase" data-original-title="30%"
									data-toggle="tooltip" data-placement="top">30%</div>
							</div>
							<div class="bar ">
								<div class="title">AUG</div>
								<div class="value tooltips" data-original-title="45%"
									data-toggle="tooltip" data-placement="top">45%</div>
								<div class="value tooltips purchase" data-original-title="30%"
									data-toggle="tooltip" data-placement="top">30%</div>
							</div>
							<div class="bar ">
								<div class="title">SEP</div>
								<div class="value tooltips" data-original-title="50%"
									data-toggle="tooltip" data-placement="top">50%</div>
								<div class="value tooltips purchase" data-original-title="30%"
									data-toggle="tooltip" data-placement="top">30%</div>
							</div>
							<div class="bar ">
								<div class="title">OCT</div>
								<div class="value tooltips" data-original-title="42%"
									data-toggle="tooltip" data-placement="top">42%</div>
								<div class="value tooltips purchase" data-original-title="28%"
									data-toggle="tooltip" data-placement="top">28%</div>
							</div>
							<div class="bar ">
								<div class="title">NOV</div>
								<div class="value tooltips" data-original-title="60%"
									data-toggle="tooltip" data-placement="top">60%</div>
								<div class="value tooltips purchase" data-original-title="68%"
									data-toggle="tooltip" data-placement="top">68%</div>
							</div>
							<div class="bar ">
								<div class="title">DEC</div>
								<div class="value tooltips" data-original-title="90%"
									data-toggle="tooltip" data-placement="top">90%</div>
								<div class="value tooltips purchase" data-original-title="30%"
									data-toggle="tooltip" data-placement="top">30%</div>
							</div>
						</div>
						<!--custom chart end-->
					</div>
					<div class="col-lg-4">
						<!--new earning start-->
						<div class="panel terques-chart">
							<div class="panel-body chart-texture">
								<div class="chart">
									<div class="heading">
										<span>December 2016</span> <strong>Production
											Variation</strong>
									</div>
									<div class="sparkline" data-type="line" data-resize="true"
										data-height="75" data-width="90%" data-line-width="1"
										data-line-color="#fff" data-spot-color="#fff"
										data-fill-color="" data-highlight-line-color="#fff"
										data-spot-radius="4"
										data-data="[200,135,667,333,526,996,564,123,890,564,455]"></div>
								</div>
							</div>
							<!---<div class="chart-tittle">
								<span class="title">New Earning</span>
								<span class="value">
									  <a href="#" class="active">Market</a>
									  |
									  <a href="#">Referal</a>
									  |
									  <a href="#">Online</a>
								  </span>
							</div>--->
						</div>
						<!--new earning end-->

						<!--total earning start-->
						<section class="panel">
							<div class="panel-body progress-panel">
								<div class="task-progress">
									<h1>Reorder Item Details</h1>

								</div>
								<!--<div class="task-option"> <p>Anjelina Joli</p>
									<select class="styled hasCustomSelect" style="width: 112px; position: absolute; opacity: 0; height: 39px; font-size: 12px;">
										<option>Anjelina Joli</option>
										<option>Tom Crouse</option>
										<option>Jhon Due</option>
									</select><span class="customSelect styled" style="display: inline-block;"><span class="customSelectInner" style="width: 90px; display: inline-block;">Anjelina Joli</span></span>
								</div>-->
							</div>


							<table class="table table-hover personal-task item_detils_tbl"
								id="reorderdtlTbl">
								<thead>
									<tr>
										<th>id</th>
										<!-- <th>Sl.</th> -->
										<th>Item</th>
										<th>Minimum Stock</th>
										<th>Current Stock</th>
									</tr>

								</thead>
								<tbody>
									<tr>
										<td>stock_item_id</td>
										<!-- <td>code</td> -->
										<td>name</td>
										<td>min_stock</td>
										<td>current_stock</td>
									</tr>

									<!--<tr>
										<td>1</td>
										<td>
											Rava
										</td>
										<td>
											<span class="badge bg-info">160kg</span>
										</td>
										<td>
											<span class="badge bg-important">140Kg</span>
										</td>
									</tr>
									 <tr>
										<td>2</td>
										<td>
											Maida
										</td>
										<td>
											<span class="badge bg-info">300Kg</span>
										</td>
										<td>
											<span class="badge bg-important">210Kg</span>
										</td>
									</tr>
									<tr>
										<td>3</td>
										<td>
											Sugar
										</td>
										<td>
											<span class="badge bg-info">170kg</span>
										</td>
										<td>
											<span class="badge bg-important">135Kg</span>
										</td>
									</tr>
									<tr>
										<td>4</td>
										<td>
											Ghee
										</td>
										<td>
											<span class="badge bg-info">60kg</span>
										</td>
										<td>
											<span class="badge bg-important">40Kg</span>
										</td>
									</tr> -->
								</tbody>
							</table>
							<!-- <div class="btm_btn">
								<div class="btn_lft">
									<a href="#" class="disabled"><i class="fa fa-angle-left"></i></a>
								</div>
								<div class="btn_rgt">
									<a href="#"><i class="fa fa-angle-right"></i></a>
								</div>
							</div> -->
						</section>
						<!--total earning end-->
					</div>
				</div>

			</section>
		</section>
	</section>


	<!-- AdminLTE App -->
	<script src="<c:url value='/resources/mrp/dashboard/js/jquery.js' />"></script>
	<script
		src="<c:url value='/resources/mrp/dashboard/js/bootstrap.min.js' />"></script>
	<script
		src="<c:url value='/resources/common/template/plugins/datatables/jquery.dataTables.min.js' />"></script>
	<script
		src="<c:url value='/resources/common/template/plugins/datatables/dataTables.bootstrap.min.js' />"></script>
	<script
		src="<c:url value='/resources/mrp/dashboard/js/jquery.dcjqaccordion.2.7.js' />"></script>
	<script
		src="<c:url value='/resources/mrp/dashboard/js/jquery.nicescroll.js' />"></script>
	<script
		src="<c:url value='/resources/mrp/dashboard/js/jquery.scrollTo.min.js' />"></script>
	<script
		src="<c:url value='/resources/mrp/dashboard/js/jquery.sparkline.js' />"></script>
	<script
		src="<c:url value='/resources/mrp/dashboard/js/respond.min.js' />"></script>
	<script
		src="<c:url value='/resources/mrp/dashboard/js/sparkline-chart.js' />"></script>
	<script
		src="<c:url value='/resources/mrp/dashboard/js/html5shiv.js' />"></script>
	<script src="<c:url value='/resources/mrp/dashboard/js/count.js' />"></script>
	<script
		src="<c:url value='/resources/mrp/dashboard/js/common-scripts.js' />"></script>

	<script
		src="<c:url value="/resources/mrp/dashboard/js/dashboard.js" />"></script>

	<script>
		//owl carousel

		$(document).ready(function() {
			$("#owl-demo").owlCarousel({
				navigation : true,
				slideSpeed : 300,
				paginationSpeed : 400,
				singleItem : true,
				autoPlay : true

			});
		});

		//custom select box

		$(function() {
			$('select.styled').customSelect();
		});
	</script>
</body>
</html>
