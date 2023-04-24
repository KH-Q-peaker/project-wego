<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<jsp:include page="../common/report.jsp"></jsp:include><%-- 신고 --%>
<jsp:include page="../common/delete.jsp"></jsp:include><%-- 삭제 --%>
<jsp:include page="../common/alert.jsp"></jsp:include><%-- 알림 --%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="shortcut icon" href="${path}/resources/ico/favicon.ico" type="image/x-icon" />
<link rel="icon" href="${path}/resources/ico/favicon.ico" type="image/x-icon" />

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>

<link rel="stylesheet" type="text/css" href="/resources/css/default.css">
<link rel="stylesheet" type="text/css" href="/resources/css/header.css"/>
<link rel="stylesheet" type="text/css" href="/resources/css/footer.css"/>
<link rel="stylesheet" type="text/css" href="/resources/css/like.css"/>
<link rel="stylesheet" type="text/css" href="/resources/css/comment.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/css/review-detail.css">

<script type="text/javascript" src="${path}/resources/js/header.js"  defer></script>
<script type="text/javascript" src="${path}/resources/js/footer.js"  defer></script>
<script type="text/javascript" src="${path}/resources/js/default.js"  defer></script>
<script type="text/javascript" src="${path}/resources/js/like.js"  defer></script>
<script type="text/javascript" src="${path}/resources/js/scroll.js"  defer></script>
<script type="text/javascript" src="${path}/resources/js/comment.js"  defer></script>
<script type="text/javascript" src="${path}/resources/js/review-detail.js" defer></script>
	

<script> 
	var target = JSON.parse('${target}');
</script>
</head>
<body>
	<div class="total-wrap">		
		<jsp:include page="../common/header.jsp" />
			<section>
				<div class="rheader">
					<div class="mnt">🌱 ${review.sanName} 🌱</div>
					<div class="title">️${review.title}</div>
					<div class="dt">
						<fmt:formatDate pattern="yy-MM-dd HH:mm" value="${review.createdDt}"></fmt:formatDate>
					</div>
				</div>
				<div class="contents">
					<img src="${userPic}" alt="" class="userpic" />
					<a class="username" href="http://localhost:8080/profile/${review.userId}">${review.nickName}</a>
					<div class="likeCnt">
						<input class="like ${isLike ? 'fill' : '' }" type="button" value="" />︎<label> ${review.likeCnt }</label>
					</div>
					<div class="content">${review.contents}</div>
					<div class="btns">
						<c:if test="${review.userId == sessionScope.__AUTH__.userId}"> 
						<input type="button" class="modify" name="modify" value="수정" />
						<input type="button" class="delete" name="delete" value="삭제" />
						</c:if>
						<input type="button" class="report" name="report" value="신고" />
					</div>
				</div>

				<div class="cnt">댓글 (<span id="cmtcnt">${review.commentCnt }</span>)</div>
				<c:set var="comments" value="${comments}" />
				<jsp:include page="../comment/comment.jsp" />

				<div class="to top">top</div>
				<div class="to cmt">cmt</div>
			</section>
		</div>
		<jsp:include page="../common/footer.jsp" />
	</body>
</html>