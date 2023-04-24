<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript" src="/resources/js/delete.js"  defer></script>
<link rel="stylesheet" type="text/css" href="/resources/css/delete.css"  />

	<div class="deleteModal">
		<input type="hidden" id="targetGb" name="targetGb" value="${targetGb }">
		<input type="hidden" id="targetCd" name="targetCd" value="${targetCd }">
		<div class="delment">삭제하시겠습니까?</div>
		<input type="button" class="delcancle" value="취소" /> 
		<input type="button" class="del" name="del" value="삭제" />
	</div>
