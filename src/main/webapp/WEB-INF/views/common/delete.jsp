<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript" src="${path}/resources/js/delete.js"  defer></script>
<link rel="stylesheet" type="text/css" href="${path}/resources/css/delete.css"  />


</head>
<body>
	<div class="deleteModal">
		<input type="hidden" id="targetGb" name="targetGb" value="${targetGb }">
		<input type="hidden" id="targetCd" name="targetCd" value="${targetCd }">
		<div class="delment">삭제하시겠습니까?</div>
		<input type="button" class="delcancle" value="취소" /> 
		<input type="button" class="del" name="del" value="삭제" />
	</div>
</body>
</html>