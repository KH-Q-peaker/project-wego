<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>통합검색결과페이지</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="shortcut icon" href="resources/ico/favicon.ico"
	type="image/x-icon" />
<link rel="icon" href="resources/ico/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="resources/css/header.css" />
<link rel="stylesheet" href="resources/css/footer.css" />
<link rel="stylesheet" href="resources/css/search.css" />
<script src="resources/js/header.js" defer></script>
<script src="resources/js/search.js" defer></script>
<script src="/resources/js/favorite.js" defer></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
</head>
<body>
	<div class="total-wrap">
		<%@include file="/WEB-INF/views/common/header.jsp"%>
		<c:set var="imgBasePath" value="/img/" />
		<section>
			<div class="container">
				<div class="mountain-info">
					<div class="info-title">
						<h2>❤️ 산 ❤️</h2>
						<a href="/info/search?query=${query}" class="more">더보기</a>
					</div>
					<div class="wrap">
						<c:forEach var="item" items="${sanInfoList}">
							<div class="item">
								<a id="itemPath" href="/info/${item.sanInfoId}">
									<div class="item-info">
										<h3 id="itemTitle">${item.sanName}</h3>
										<div class="itemDetail">
											<p id="itemContent">${item.details}</p>
											<img class="mountain-img" src="${item.img}" alt="img" />
										</div>
									</div>
								</a>
							</div>
							<!-- item -->
						</c:forEach>
					</div>
				</div>
				<hr />
				<div class="recruit">
					<div class="info-title">
						<h2>❤️ 모집 글 ❤️</h2>
						<a href="/party/search?query=${query}" class="more">더보기</a>
					</div>
					<div class="wrap-recruit">
						<c:forEach var="item" items="${partyList}">
							<a href="/party/${item.sanPartyId}">
							<div class="recruit-item">
								<img class="user-img" src="${empty item.userPic ? 
								"/resources/img/default-user.jpg" : imgBasePath += fn:substring(item.userPic, 12, 57)}" alt="img" />
								<p class="user-name" id="userName">${item.nickName}</p>
								<p class="mountain-name" id="mountainName">${item.sanName}</p>
								<img class="recruit-img" src="${empty item.partyPic ? "/resources/img/default-party.jpg" : imgBasePath += fn:substring(item.partyPic, 12, 57)}
								" alt="img" />
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
				</div>
				<hr />
				<div class="review">
					<div class="info-title">
						<h2>❤️ 후기 글 ❤️</h2>
						<a href="/review/search?query=${query}" class="more">더보기</a>
					</div>
					<div class="wrap">
						<c:forEach var="item" items="${reviewList}">
							<div class="item">
								<a id="itemPath"
									href="/review/${item.sanReviewId}">
									<div class="item-info">
										<h3 id="itemTitle">${item.title}</h3>
										<div class="itemDetail">
											<p id="itemContent">${item.contents}</p>
											<img id="itemImg" src="${empty item.reviewPic ? "/resources/img/default-review.jpg" : imgBasePath += fn:substring(item.reviewPic, 12, 57)}
											" alt="img" />
										</div>
									</div>
								</a>
							</div>
						</c:forEach>
					</div>
				</div>
				<a href="#" class="scrollToTop"> <img
					src="/resources/svg/top.svg" />
				</a> <a href="#" class="chat"> <img src="/resources/svg/chat.svg" />
				</a>
			</div>
		</section>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
