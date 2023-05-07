<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>

	<jsp:include page="./head.jsp" />
	
	<link rel="stylesheet" href="/resources/css/main.css" />
	<link rel="stylesheet" href="/resources/css/ranking.css" />
	
	<script src="/resources/js/main.js" defer></script>
	<script src="/resources/js/ranking.js" defer></script>
	<script src="/resources/js/favorite.js" defer></script>

</head>
<body>
	<div class="total-wrap">
		<%@include file="/WEB-INF/views/common/header.jsp"%>
		<c:set var="imgBasePath" value="/img/" />
		
		<section>
		
			<%@include file="/WEB-INF/views/ranking/ranking.jsp"%>
			
			<div class="mountain-info">
				<h2>❤️ 산 ❤️</h2>
				<div class="wrap">
					<c:forEach var="item" items="${sanInfoList}">
						<a href="/info/${item.sanInfoId}">
							<div class="mountain-item">
							  <img class="mountain-img" src="${item.img}" alt="img" />
								<h3 class="mountain-name" id="mountainName">${item.sanName}</h3>
								<p class="mountain-contents" id="text">${item.details}</p>
								<div class="mountain-like">
									<c:set var="doneLoop" value="false" />
									<c:set var="status" value="" />
									<c:forEach var="userFavorite" items="${favoriteList}">
										<c:if
											test="${userFavorite.targetGb == 'SAN_INFO' 
											&& userFavorite.targetCd == item.sanInfoId}">
											<c:set var="status" value=" on" />
										</c:if>
									</c:forEach>
									<button type="button" name="favorite" class="favorite${status}"></button>
									<span class="favorite-count">${item.likeCnt}</span>
								</div>
							</div> <!-- mountain-item -->
						</a>
					</c:forEach>
				</div>
			</div>
			<div class="recruit">
				<h2>❤️ 모집 글 ❤️</h2>
				<div class="wrap">
					<c:forEach var="item" items="${partyList}">
						<a href="/party/${item.sanPartyId}">
							<div class="recruit-item">
								<img class="user-img" src="${empty item.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(item.userPic, 12, 57)}" alt="img" />
								<p class="user-name" id="userName">${item.nickName}</p>
								<p class="mountain-name" id="mountainName">${item.sanName}</p>
								<img class="recruit-img" src="${empty item.partyPic ? "/resources/img/default-party.jpg" : imgBasePath += fn:substring(item.partyPic, 12, 57)}" alt="img" />
								<p class="recruit-title" id="title">${item.title}</p>
								<p class="recruit-schedule-schedule">날짜:</p>
								<p class="recruit-schedule" id="schedule">
									<fmt:formatDate value="${item.partyDt}" pattern="yyyy-MM-dd" />
								</p>
								<p class="recruit-schedule-time">시간:</p>
								<p class="recruit-time" id="time">
									<fmt:formatDate value="${item.partyDt}" pattern="HH:mm" />
								</p>
								<p class="recruit-schedule-member">참여 인원:</p>
								<p class="recruit-member" id="memberCount">${item.userCnt}/
									${item.partyMax}</p>
								<div class="recruit-like">
									<c:set var="doneLoop" value="false" />
									<c:set var="status" value="" />
									<c:forEach var="userFavorite" items="${favoriteList}">
										<c:if
											test="${userFavorite.targetGb == 'SAN_PARTY' 
											&& userFavorite.targetCd == item.sanPartyId}">
											<c:set var="status" value=" on" />
										</c:if>
									</c:forEach>
									<button type="button" name="favorite" class="favorite${status}"></button>
									<span class="favorite-count">${item.likeCnt}</span>
								</div>
								<p class="recruit-date" id="date">
									<fmt:formatDate value="${item.createdDt}" pattern="yyyy-MM-dd" />
								</p>
							</div> <!-- item -->
						</a>
					</c:forEach>
				</div>
				<!-- wrap -->
			</div>
			<!-- recruit -->
			<div class="review">
				<h2>❤️ 후기 글 ❤️</h2>
				<div class="wrap">
					<c:forEach var="item" items="${reviewList}">
						<a href="/review/${item.sanReviewId}">
							<div class="review-item">
								<img class="user-img" src="${empty item.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(item.userPic, 12, 57)}" alt="img" />
								<p class="user-name" id="userName">${item.nickName}</p>
								<p class="mountain-name" id="mountainName">${item.sanName}</p>
								<img class="review-img" src="${empty item.reviewPic ? 
								"/resources/img/default-review.jpg" : imgBasePath += fn:substring(item.reviewPic, 12, 57)}" alt="img" />
								<h3 class="review-title" id="title">${item.title}</h3>
								<p class="review-contents" id="text">${item.contents}</p>
								<div class="review-like">
									<c:set var="doneLoop" value="false" />
									<c:set var="status" value="" />
									<c:forEach var="userFavorite" items="${favoriteList}">
										<c:if
											test="${userFavorite.targetGb == 'SAN_REVIEW' 
											&& userFavorite.targetCd == item.sanReviewId}">
											<c:set var="status" value=" on" />
										</c:if>
									</c:forEach>
									<button type="button" name="favorite" class="favorite${status}"></button>
									<span class="favorite-count">${item.likeCnt}</span>
								</div>
								<p class="review-date" id="date">
									<fmt:formatDate value="${item.createdDt}" pattern="yyyy-MM-dd" />
								</p>
							</div> <!-- review-item -->
						</a>
					</c:forEach>
				</div>
			</div>
			<a href="#" class="scrollToTop"> <img src="/resources/svg/top.svg" /> </a>
		</section>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>