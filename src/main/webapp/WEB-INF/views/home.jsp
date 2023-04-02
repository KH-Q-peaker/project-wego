<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="org.zerock.wego.domain.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<meta charset="UTF-8" />
	<title>Wego</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="등산멤버 모집 커뮤니티" />
	
	<link rel="shortcut icon" href="${path}/resources/ico/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="${path}/resources/ico/favicon.ico" type="image/x-icon" />
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
	
	<link rel="stylesheet" href="${path}/resources/css/header.css" />
	<link rel="stylesheet" href="${path}/resources/css/footer.css" />
	
	<script src="${path}/resources/js/header.js" defer></script>
	<script src="${path}/resources/js/footer.js" defer></script>
	
</head>
	<body>
		<div class="total-wrap">
			<!-- hearder start -->
			<jsp:include page="./common/header.jsp" />
	   		<!-- hearder end -->
	   		
			<!-- main start -->
			 <section>
			 
			 </section>
			<!-- main end -->
		</div>
			<!-- Footer Start -->
			<jsp:include page="./common/footer.jsp" />
			<!-- Footer End -->
	</body>
</html>
