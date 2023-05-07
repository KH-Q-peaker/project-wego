
<%@ page language="java" contentType="text/html; charset=UTF-8" 
		pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="org.zerock.wego.domain.party.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

	<link rel="shortcut icon" href="/resources/ico/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="/resources/ico/favicon.ico" type="image/x-icon" />
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="/resources/css/header.css"/>
	<link rel="stylesheet" type="text/css" href="/resources/css/footer.css"/>
	<link rel="stylesheet" type="text/css" href="/resources/css/like.css"/>
	<link rel="stylesheet" type="text/css" href="/resources/css/comment.css"/>
	<link rel="stylesheet" type="text/css" href="/resources/css/party-detail.css"/>
	
	<script type="text/javascript" src="/resources/js/header.js"  defer></script>
	<script type="text/javascript" src="/resources/js/footer.js"  defer></script>
	<script type="text/javascript" src="/resources/js/like.js"  defer></script>
	<script type="text/javascript" src="/resources/js/scroll.js"  defer></script>
	<script type="text/javascript" src="/resources/js/comment.js"  defer></script>
	<script type="text/javascript" src="/resources/js/party-detail.js"  defer></script>

	<script> 
		var target = JSON.parse('${target}');
	</script>
</head>
<body>
<c:set var="imgBasePath" value="/img/" />
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
					<img src=" ${empty party.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(party.userPic, 12, 57)}" alt="" class="userpic" />
					<a class="username" href="/profile/${party.userId}">${party.nickName}</a>
					<div class="likeCnt">
						<input class="like ${isFavorite ? 'fill' : '' }" type="button" value="" /><label>${party.likeCnt }</label>
					</div>
					<img src="${empty party.partyPic ? "/resources/img/default-party.jpg" : imgBasePath += fn:substring(party.partyPic, 12, 57) }" alt="" class="partyImg" />
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
							<span class="list">참여인원</span> <span id="currentCount">${party.userCnt}</span><span>/</span><span>${party.partyMax }</span>
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
						<input type="button" class="modify" name="modify" value="수정" /> 
						<input type="button" class="delete" name="delete" value="삭제" /> 
						</c:if>
						<input type="button" class="report" name="report" value="신고" />
					</div>
					<% 
						Date now = new Date();
						PartyViewVO party = (PartyViewVO)request.getAttribute("party");
						boolean after = now.after(party.getPartyDt());
						request.setAttribute("after", after);
					%>
						<c:choose>
							<c:when test="${after }">
								<input type="button" class="join" style="background-color: #727272" disabled value="모집종료" />
							</c:when>
							<c:when test="${isJoin == true && sessionScope.__AUTH__.userId != party.userId }">
								<input type="button" class="join" id="clsjoin" name="join" value="취소하기" />
							</c:when>
							<c:when test="${party.userCnt >= party.partyMax }">
								<input type="button" class="join" style="background-color: rgb(252, 170, 64)" disabled value="모집완료" />
							</c:when>
							<c:when test="${isJoin == false && sessionScope.__AUTH__.userId != party.userId}">
								<input type="button" class="join" id="join" name="join" value="참여하기" />
							</c:when>
						</c:choose>
				</div>
				<div class="cnt">댓글 (<span id="cmtcnt">${party.commentCnt }</span>)</div>
				<c:set var="comments" value="${comments}" />
				<%@ include file="../comment/comment.jsp"%>
				<div id="chat"></div>
				<div class="scrollToTop top"></div>
				<div class="add-item cmt"></div>
			</section>
		</div>
		<jsp:include page="../common/footer.jsp" />
	</body>
</html>



