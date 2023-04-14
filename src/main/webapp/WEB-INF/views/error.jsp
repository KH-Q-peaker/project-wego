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
	<h3><%= exception.getMessage() %></h3>
	<div>하하 안타깝게도 페이지를 찾을 수 없습니다!</div>
	<a href="http://localhost:8080/">...메인으로 갈게요</a>
</body>
</html>