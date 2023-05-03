<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<head>
<script type="text/javascript" src="/resources/js/join.js" defer></script>
<link type="text/css" rel="stylesheet" href="/resources/css/join.css"/>
</head>
<body>
	<div class="joinModal">
		<img src="${party.partyPic == null ? "/resources/img/leaf.png" : party.partyPic}"  alt="" class="" /><!-- 바꿔야됨  -->
		<div class="sanName">${party.sanName }</div>
		<ul class="joinInfo">
			<li>날짜 : <fmt:formatDate pattern="yy-MM-dd" value="${party.partyDt}"></fmt:formatDate></li>
			<li>시간 : <fmt:formatDate pattern="HH:mm" value="${party.partyDt}"></fmt:formatDate></li>
			<c:if test="${party.items != null }">
			<li>준비물 : ${party.items }</li>
			</c:if>
			<c:if test="${party.condition != null }">
			<li>등반조건 : ${party.condition }</li>
			</c:if>
		</ul>
			<div id="ment">참여하시겠습니까?</div>
			<div class="button">
				<input type="button" class="clsbtn" value="취소" />
				<input type="button" class="joinbtn" value="참여" />
			</div>
	</div>
</body>
</html>