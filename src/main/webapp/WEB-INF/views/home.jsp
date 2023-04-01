<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="org.zerock.wego.domain.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<meta charset="UTF-8" />
	<title>WeGo-n</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="등산멤버 모집 커뮤니티" />
	
	<link rel="shortcut icon" href="${path}/resources/ico/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="${path}/resources/ico/favicon.ico" type="image/x-icon" />
	
	<link rel="stylesheet" href="${path}/resources/css/header.css" />
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
	
	
</head>
<body>
	<div class="total-wrap">
		<jsp:include page="./common/header.jsp" />
	
	
	
	</div>

	<jsp:include page="./common/footer.jsp" />

	
</body>
</html>
