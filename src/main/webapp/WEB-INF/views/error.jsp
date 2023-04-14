<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/error.css" />
</head>
<body>
	<div><%= exception.getMessage() %></div>
	 <!--  이걱 앞에 클래스명 안나오게 하는 방법 아는사람 구함  -->
	 <!-- 현재상태 = org.zerock.wego.exception.NotFoundPageException: 해당 글을 찾을 수 없습니다. -->
	<a href="http://localhost:8080/">...메인으로 갈게요</a>
</body>
</html>