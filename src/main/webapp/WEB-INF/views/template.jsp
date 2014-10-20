<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<script src="../../js/jquery-1.11.0.js"></script>
	<script src="http://code.jquery.com/jquery.js"></script>
	<script src="../../js/bootstrap.min.js"></script>
<!-- 	<script src="js/bootstrap-table.js"></script> -->
	<script src="../../js/tit4tat.js"></script>
	<link href="../../css/bootstrap.min.css" rel="stylesheet" media="screen">
<!-- 	<link rel="stylesheet" href="css/bootstrap-table.css"> -->
	<title><spring:message code="app.title.home" /></title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="${partial}" />
	<jsp:include page="footer.jsp" />
</body>
</html>