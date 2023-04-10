
<%@ page language="java" contentType="text/html; charset=UTF-8" 
		pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="org.zerock.wego.domain.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<jsp:include page="../common/report.jsp"></jsp:include><%-- 신고 --%>
<jsp:include page="../common/delete.jsp"></jsp:include><%-- 삭제 --%>
<jsp:include page="../common/alert.jsp"></jsp:include><%-- 알림 --%>
<jsp:include page="../common/join.jsp"></jsp:include><%-- 참여 --%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
	<title>Wego</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="등산멤버 모집 커뮤니티" />

	<link rel="shortcut icon" href="${path}/resources/ico/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="${path}/resources/ico/favicon.ico" type="image/x-icon" />
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
	
<%-- 	<link rel="stylesheet" type="text/css"  href="${path}/resources/css/default.css"/> --%>
	<link rel="stylesheet" type="text/css" href="${path}/resources/css/header.css"/>
	<link rel="stylesheet" type="text/css" href="${path}/resources/css/footer.css"/>
	<link rel="stylesheet" type="text/css" href="${path}/resources/css/like.css"/>
	<link rel="stylesheet" type="text/css" href="${path}/resources/css/comment.css"/>
	<link rel="stylesheet" type="text/css" href="${path}/resources/css/party-detail.css"/>
	
	<script type="text/javascript" src="${path}/resources/js/header.js"  defer></script>
	<script type="text/javascript" src="${path}/resources/js/footer.js"  defer></script>
	<script type="text/javascript" src="${path}/resources/js/default.js"  defer></script>
	<script type="text/javascript" src="${path}/resources/js/like.js"  defer></script>
	<script type="text/javascript" src="${path}/resources/js/scroll.js"  defer></script>
	<script type="text/javascript" src="${path}/resources/js/comment.js"  defer></script>
	<script type="text/javascript" src="${path}/resources/js/party-detail.js"  defer></script>

	<script> 
		var target = JSON.parse('${target}');
		var commentCount = ${commentCount};
	</script>
</head>
<body>
	<div class="total-wrap">
		<jsp:include page="../common/header.jsp" />
			<section>
				<div class="rheader">
					<div class="mnt">🌱 ${party.sanName} 🌱</div>
					<div class="title">${party.title }</div>
					<div class="dt">
						<fmt:formatDate pattern="yy-MM-dd HH:mm" 
						value="${party.modifiedDt == null? party.createdDt : party.modifiedDt}"/>
						${party.modifiedDt != null ? '수정됨' : ''}
					</div>
				</div>
				<div class="contents">
					<img src=" ${party.userPic }" alt="" class="userpic" />
					<div class="username">${party.nickName}</div>
					<div class="likeCnt">
						<input class="like ${isLike ? 'fill' : '' }" type="button" value="" /><label>${party.likeCnt }</label>
					</div>
<%-- 					<img src="data:image/png;base64, ${partyImg }" alt="" class="partyImg" /> --%>
					<img src="${party.partyPic}" alt="" class="partyImg" />
					<div class="partyInfo">
						<div class="info">
 							<span class="list">날짜</span> 
 							<span><fmt:formatDate pattern="yy년 MM월 dd일" value="${party.partyDt}"></fmt:formatDate></span>
						</div>
						<div class="info">
							<span class="list">시간</span> 
							<span><fmt:formatDate pattern="HH시 mm분" value="${party.partyDt}"></fmt:formatDate></span> 
						</div>
						<div class="info">
							<span class="list">참여인원</span> <span>${party.userCnt} / ${party.partyMax }</span>
						</div>
						<c:if test="${party.items != null}">
							<div class="info">
								<span class="list">준비물</span> <span>${party.items }</span>
							</div>
						</c:if>
						<c:if test="${party.condition != null}">
							<div class="info">
								<span class="list">등반조건</span> <span>${party.condition }</span>
							</div>
						</c:if>
						<div class="info">
							<span class="list">내용</span>
						</div>
	 					<div class="content">${party.contents}</div>
					</div>
					<div class="btns">
						<c:if test="${party.userId == sessionScope.__AUTH__.userId}"> 
<%-- 						<a href="/party/modify/${party.sanPartyId }" class="modify" name="modify" value="수정" />  --%>
						<input type="button" class="modify" name="modify" value="수정" /> 
						<input type="button" class="delete" name="delete" value="삭제" /> 
						</c:if>
						<input type="button" class="report" name="report" value="신고" />
					</div>
					<c:choose>
						<c:when test="${isJoin == false }">
							<input type="button" class="join" id="join" name="join" value="참여하기" />
						</c:when>
						<c:otherwise>
							<input type="button" class="join" id="clsjoin" name="join" value="취소하기" />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="cnt">댓글 (${commentCount})</div>
				<c:set var="comments" value="${comments}" />
				<%@ include file="../comment/comment.jsp"%>
				<div class="to top">top</div>
				<div class="to cmt">cmt</div>
			</section>
		</div>
		<jsp:include page="../common/footer.jsp" />
	</body>
</html>



