<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% request.getRequestURI(); %>
	<hr>
	<form method="GET" action="https://kauth.kakao.com/oauth/authorize">
	<input type="hidden" name="client_id" value="431c97c917ce5b2b20b6ae12238f5155"/>
	<input type="hidden" name="redirect_uri" value="http://localhost:8080/kakao/login"/>
	<input type="hidden" name="response_type" value="code"/>
	
	
	<input type="submit" value="카카오"/>
	</form>
	
	<form method="GET" action="https://nid.naver.com/oauth2.0/authorize">
	
	<input type="hidden" name="response_type" value="code"/>
	<input type="hidden" name="client_id" value="5ygil0yKvDAx0ZmfI5k3"/>
	<input type="hidden" name="state" value="STATE_STRING"/>
	<input type="hidden" name="redirect_uri" value="http://localhost:8080/naver/login"/>
	
	<input type="submit" value="네이버"/>
	</form>
	
</body>
</html>