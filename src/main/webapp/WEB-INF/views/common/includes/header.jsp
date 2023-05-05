<%
	String contextPath = request.getContextPath();
   String title = contextPath.substring(1,contextPath.length() ).toUpperCase();
%>

<jsp:directive.include
	file="../../common/includes/common_setpermission.jsp" />

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><%=title%></title>
<link rel="shortcut icon" href="/mrp/resources/common/images/orange.ico">
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.5 -->
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/bootstrap/css/bootstrap.css' />">
	<link rel="stylesheet"
	href="<c:url value='/resources/common/template/bootstrap/css/bootstrap-fileupload.css' />">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/bootstrap/css/font-awesome.min.css' />">
<!-- Ionicons -->
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/bootstrap/css/ionicons.min.css' />">
<!-- daterange picker -->
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/daterangepicker/daterangepicker-bs3.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/datepicker/datepicker3.css' />">
<!-- DataTables -->
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/datatables/dataTables.bootstrap.css' />">
<!-- Theme style -->
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/dist/css/AdminLTE.css' />">

<!-- angular -->

<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/angular/css/angular-material.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/angular/css/angular_autocomplete.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/tautocompelte/tautocomplete.css' />">

<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/dist/css/skins/_all-skins.css' />">
<!-- iCheck -->
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/iCheck/square/_all.css' />">

<link rel="stylesheet"
	href="<c:url value='/resources/common/css/tooltip.css' />">

<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/bootstrap-datetimepicker-master/build/css/bootstrap-datetimepicker.min.css' />">
<%-- <link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/colorpicker/bootstrap-colorpicker.css' />"> --%>
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/colorpicker/bootstrap-colorpicker.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/duallistbox/bootstrap-duallistbox.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/common/template/plugins/bootstrap-notify-master/animate.css' />">
<!-- common css include below -->
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/mrp_common.css' />">
<link rel="stylesheet"
	href="<c:url value="/resources/common/template/plugins/angular/css/jquery.minicolors.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/common/template/plugins/angular/css/jquery-ui.css" />">
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	
<%-- <jsp:directive.include file="../../quicksearch/list.jsp" />  --%>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
