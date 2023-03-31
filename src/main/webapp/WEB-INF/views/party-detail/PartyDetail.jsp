
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<jsp:include page="../common/report.jsp"></jsp:include><%-- 신고 --%>
<jsp:include page="../common/delete.jsp"></jsp:include><%-- 삭제 --%>
<jsp:include page="../common/alert.jsp"></jsp:include><%-- 알림 --%>
<jsp:include page="../party-detail/join.jsp"></jsp:include><%-- 참여 --%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>PartyDetail</title>
<%-- <script type="text/javascript" src="${path}/resources/js/blind.js"  defer></script> --%>
<%-- <link rel="stylesheet" type="text/css"  href="${path}/resources/css/blind.css"/> --%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>

<script type="text/javascript" src="${path}/resources/js/default.js"  defer></script>
<script type="text/javascript" src="${path}/resources/js/scroll.js"  defer></script>
<script type="text/javascript" src="${path}/resources/js/like.js"  defer></script>
<script type="text/javascript" src="${path}/resources/js/party-detail.js"  defer></script>

<link rel="stylesheet" type="text/css"  href="${path}/resources/css/default.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/css/like.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/css/party-detail.css"/>
<script>
	var cri = JSON.parse('${cri}');
</script>
</head>
<body>
		<%@ include file="../common/header.jsp"%>

		<section>
				<div class="rheader">
					<!--  산 이름  -->
					<div class="mnt">🌱 ${party.sanName} 🌱</div>
					<!-- 글 제목 -->
					<div class="title">${party.title }</div>
					<!--  작성일  -->
					<div class="dt">
						<fmt:formatDate pattern="yy-MM-dd HH:mm" value="${party.createdDt}"></fmt:formatDate>
					</div>
				</div>

				<div class="contents">
					<!-- 유저 정보  -->
					<img
						src="data:image/jpeg;base64,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"
						alt="" class="userpic" />
					<!-- 바꿔야됨 -->
					<div class="username">${party.nickname}</div>
					<!--  좋아요 버튼  -->
					<div class="likeCnt">
						<input class="like" type="button" value="" />${party.likeCnt }
					</div>
					<!--  글 사진  -->
<!-- 					src="https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Golde33443.jpg/560px-Golde33443.jpg" -->
					<img src="data:image/png;base64, ${img }" alt="" class="partyImg" />
					<!-- 모집 정보  -->
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
					<!--  글 내용  -->
					<!--  수정, 삭제, 신고 버튼  -->
					<div class="btns">
						<c:if test="${review.userId == sessionScope.userId}"> <!--  이거 조건 바꿔야된다!!!! 세션에서 유저정보 얻어서 넘거야됨 -->
						<input type="button" class="modifyRv" name="modifyRv" value="수정" /> 
						<input type="button" class="deleteRv" name="deleteRv" value="삭제" /> 
						</c:if>
						<input type="button" class="reportRv" name="reportRv" value="신고" />
					</div>
					<!--  여기서 세션유저정보랑, 모집글코드로 된 참여기록 있는지 확인한값을 받아서?
						 이미 참여됐으면 취소하기로 나오게 해야됨  -->
					<c:choose>
						<c:when test="${isJoin == false }">
							<input type="button" class="join" id="join" name="join" value="참여하기" />
						</c:when>
						<c:otherwise>
							<input type="button" class="join" id="clsjoin" name="join" value="취소하기" />
						</c:otherwise>
					</c:choose>
				</div>
				<!--  댓글 수  -->
				<div class="cnt">댓글 (${totalCnt})</div>
			<!--   댓글 컨테이너   !-->
				<c:set var="comments" value="${comments}" />
				<!--  댓글 목록  -->
				<!--  새 댓글 작성 폼  -->
				<%@ include file="../comment/comment.jsp"%>

			<!-- 탑버튼이랑 댓글쓰기버튼  -->
			<div class="to top">top</div>
			<div class="to cmt">cmt</div>
		</section>
	</div>
			<%@ include file="../common/footer.jsp"%>
</body>

</html>



