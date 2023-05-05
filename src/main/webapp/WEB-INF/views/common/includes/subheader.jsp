
<%
	String company = request.getContextPath();
%>

<body class="hold-transition skin-blue-light sidebar-mini">
	<div ng-app="common_app" id="app1">
		<div ng-controller="common_ctrl"></div>
	</div>

	<header class="main-header">

		<!-- Logo -->
		
		<a href="#" class="logo"><p class="logotext" style="font-size:14px;padding-top:3px;"><img src="../resources/common/images/INventory2.png"style="margin-top:-7px;margin-right:10px;">PRODUCTION</p><%--  <!-- mini logo for sidebar mini 50x50 pixels -->
			<img src="<%=company%>/resources/common/images/logo.png">--%>
		</a> 
		<!-- Header Navbar: style can be found in header.less -->
		<nav class="navbar navbar-static-top" role="navigation">

			<div class="navbar-header">
				<!--   <a href="../../index2.html" class="navbar-brand"><b>Admin</b>LTE</a> -->
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar-collapse">
					<i class="fa fa-bars"></i>
				</button>
			</div>

			<!-- Sidebar toggle button-->

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse pull-left" id="navbar-collapse">
				<ul class="nav navbar-nav">


					<!-- 	<li><a href="/mrp/dashboard/list" id="dashboard">Dashboard</a></li>  -->
			<c:if test="${(combineMode==0)?true:false }">

					<c:if
						test="${mainStr.getCanView() && mainStr.getIsViewApplicable()}">
						<li class="${Store ? 'active' : ' '}"><a
							href="<%=company%>/purchaseorderhdr/list" id="store">Store</a></li>
					</c:if>
			</c:if>
			
						<c:if test="${(combineMode==1)?true:false }">
			
					<c:if
						test="${mainStr.getCanView() && mainStr.getIsViewApplicable()}">
						<li class="${Store ? 'active' : ' '}"><a
							href="<%=company%>/stockin/list" id="store">Store</a></li>
					</c:if>
					</c:if>
					
					
					
					<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
						<c:if
							test="${mainPrd.getCanView() && mainPrd.getIsViewApplicable()}">
							<li class="${Production ? 'active' : ' '}"><a
								href="<%=company%>/planning/list" id="production">Production</a></li>
						</c:if>
					</c:if>
					<%-- 	<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
						<c:if test="${mainSales.getCanView() && mainSales.getIsViewApplicable()}">
						<li><a href="#" id="sale">Sales</a></li>
						</c:if></c:if> --%>
					<%-- <c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
						<c:if test="${mainAcc.getCanView() && mainAcc.getIsViewApplicable()}">
						<li><a href="<%=company%>/dailyexpenses/list" id="account">Accounts</a></li>
						</c:if></c:if> --%>
					<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
						<c:if
							test="${mainRprt.getCanView() && mainRprt.getIsViewApplicable()}">
							<li class="${Report ? 'active' : ' '}"><a
								href="<%=company%>/itemstock/currentstock" id="report">Report</a></li>
						</c:if>
					</c:if>
			<%-- 		<c:if test="${(COMPANY_SESSION_INFO.id!=0) ? true : false}">
					<c:if test="${mainQuick.getCanView() && mainQuick.getIsViewApplicable()}">
						<li class="${QUICK_SEARCH ? 'active' : ' '}"><a href="#"
									id="quicksearch">Quick Search</a></li>
						</c:if>
						</c:if> --%>
					<c:if
						test="${mainSett.getCanView() && mainSett.getIsViewApplicable()}">
						<li class="${Settings ? 'active' : ' '}"><a
							href="<%=company%>/supplier/list" id="settings">Settings</a></li>
					</c:if>




				</ul>
			</div>
			<!-- /.navbar-collapse -->
			<div class="navbar-custom-menu">
				<ul class="nav navbar-nav">
					<!-- Messages: style can be found in dropdown.less-->

					<!-- User Account Menu -->
					<li class="dropdown user user-menu">
						<!-- Menu Toggle Button --> <a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <img
							src="<c:url value='/resources/common/template/dist/img/user2-160x160.jpg' />"
							class="user-image" alt="User Image"> <span
							class="hidden-xs"> <c:out
									value="${COMPANY_SESSION_INFO.name}" />


						</span>
					</a>
						<ul class="dropdown-menu">
							<!-- The user image in the menu -->
							<li class="user-header"><img
								src="<c:url value='/resources/common/template/dist/img/user2-160x160.jpg' />"
								class="img-circle" alt="User Image">

								<p>
									<c:out value="${user.name}" />
									<small>Last Login Date:<c:out value="${user.loginDate}" /></small>
								</p></li>
							<!-- Menu Footer-->
							<li class="user-footer lgn_btm"><a
								href="<%=company%>/logout" class="btn btn-danger logout_btn"><i
									class="fa fa-circle-o-notch"></i> Sign out</a></li>
						</ul>
					</li>
				</ul>
			</div>

			<!-- /.navbar-custom-menu -->
		</nav>

	</header>