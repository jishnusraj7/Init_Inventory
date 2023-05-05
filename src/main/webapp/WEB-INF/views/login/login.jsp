<%
	String contextPath = request.getContextPath();
   String title = contextPath.substring(1,contextPath.length() ).toUpperCase();
   
%>

<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="formAction" value="login" />
<c:set var="formCommandName" value="userForm" />

<!DOCTYPE html>
<html lang="en" ng-app="login">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">

<title> <%=title%></title>
<link rel="shortcut icon" href="/mrp/resources/common/images/orange.ico">
<!-- Bootstrap core CSS -->
<link href="resources/mrp/login/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/mrp/login/css/bootstrap-reset.css"
	rel="stylesheet">
<!--external css-->
<link href="resources/mrp/login/fonts/css/font-awesome.css"
	rel="stylesheet" />
<!-- Custom styles for this template -->
<link href="resources/mrp/login/css/style.css" rel="stylesheet">
<link href="resources/mrp/login/css/responsive.css" rel="stylesheet" />


<!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="login-body" ng-controller="login_controller">

	

	<div class="container" 
		id="login_app">
		<form:form class="form-signin" action="login" autocomplete="on" style="text-align: center;" method="post" commandName="${formCommandName}">
			<img src="resources/common/images/INventory2.png" style="height:89px;margin-bottom: -7px;">
			<!-- <h2 class="form-signin-heading">sign in now</h2> -->
			<div class="login-wrap">
			
			<c:if test="${(version==0) ? true : false}">
			
				<div class="rai_btn_div">
					<div class="rai_btn_div_lft">
						<input type="radio" name="cmpnyType" value="0" id="isHq" disabled="true"> <label for="isHq">HQ</label>
					</div>
					<div class="rai_btn_div_rgt">
						<input type="radio" name="cmpnyType" value="1" id="isCmpny"  disabled="true"> <label for="isCmpny">Company</label>
					</div>
				</div>
				</c:if>
				
				<div class="select_div inpt_div">
					<select class="form-control" name="companyId" id="companyId" style="display: none;">
						<c:forEach var="hqCompany" items="${hqCompany}">
							<option value="${hqCompany.id}"><c:out
									value="${hqCompany.name}" /></option>
						</c:forEach>
					</select>
					<!-- <div class="validation_label" style="right: 25px;"><label ng-bind="usernamestatus"></label></div> -->
				</div>
				<div class="inpt_div">
					<input type="text" class="form-control" id="loginId"
						placeholder="User ID" autofocus name="name" >
					<div class="validation_label">
						<label id="usernamestatus"></label>
					</div>
				</div>
				<div class="inpt_div">
					<input type="password" class="form-control" placeholder="Password"
						id="password" name="password">
					<div class="validation_label">
						<label id="passwordstatus"></label>
					</div>
				</div>
			
				<!-- <label class="checkbox rember_me_lbl_input"> <span
					class="pull-right"> <a data-toggle="modal" href="#myModal">
							Forgot Password?</a>

				</span>
				</label> -->
				<button class="btn btn-lg btn-login btn-block" type="submit"
					id="signIn">Sign in</button>

			</div>

			<!-- Modal -->
			<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
				tabindex="-1" id="myModal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title">Forgot Password ?</h4>
						</div>
						<div class="modal-body">
							<p>Enter your e-mail address below to reset your password.</p>
							<input type="text" name="email" placeholder="Email"
								autocomplete="off" class="form-control placeholder-no-fix">

						</div>
						<div class="modal-footer">
							<button data-dismiss="modal" class="btn btn-default"
								type="button">Cancel</button>
							<button class="btn btn-success" type="button">Submit</button>
						</div>
					</div>
				</div>
			</div>
			<!-- modal -->

		</form:form>


	</div>



	<!-- js placed at the end of the document so the pages load faster -->
	<script src="resources/mrp/login/js/jquery.js?n=1"></script>
	<script src="resources/mrp/login/js/bootstrap.min.js"></script>
	 <jsp:directive.include file="../common/includes/footer.jsp" /> 
	<script src="resources/mrp/login/js/login.js"></script>
<script type="text/javascript">
var err_msg = '<c:out value="${err_msg}"/>';
var cmpny='<c:out value="${hqCompany}"/>';
var version = '<c:out value="${version}"/>';
 /* var err_uname='<c:out value="${err_uname}"/>';
var cmpnytype = '<c:out value="${cmpnytype}"/>';
var cmpnyid = '<c:out value="${companyId}"/>';  */

</script>


</body>

</html>