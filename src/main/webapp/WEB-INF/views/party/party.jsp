<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8" />
<title>WeGo-Party</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="등산멤버모집 게시판" />

<link rel="shortcut icon" href="./ico/favicon.ico" type="image/x-icon" />
<link rel="icon" href="./ico/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" href="/resources/css/header.css" />
<link rel="stylesheet" href="/resources/css/footer.css" />
<link rel="stylesheet" href="/resources/css/partyItem.css?after" />
<link rel="stylesheet" href="/resources/css/partyFrame.css?after" />
<link rel="stylesheet" href="/resources/css/sort.css?after" />

<script src="/resources/js/header.js" defer></script>
<script src="/resources/js/footer.js" defer></script>
<script src="/resources/js/sort.js" defer></script>
<script src="/resources/js/top.js" defer></script>
<script src="/resources/js/favorite.js" defer></script>
<script src="/resources/js/board-type.js" defer></script>
<script src="/resources/js/infinity-scroll.js" defer></script>
<script src="/resources/js/board-href.js" defer></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
</head>

<body>
<c:set var="imgBasePath" value="/img/" />
	<!-- total-wrap start -->
	<div class="total-wrap">

		<%@ include file="/WEB-INF/views/common/header.jsp"%>

		<!-- main start -->
		<section>

			<!-- Contents -->


			<div class="container" id="container">

				<div class="recruit">

					<div class="recruit-board">
						<h2>❤️ 모집 글 ❤️</h2>
					</div>

					<!-- 정렬 -->
					<div class="select-sort">
						<button class="btn-select">정렬 기준</button>
						<ul class="sortBy">  
							<li class="sortByItem"><button id="sort-newest" type="button">최신순</button></li>
							<li class="sortByItem"><button id="sort-oldest" type="button">오래된순</button></li>
							<li class="sortByItem"><button id="sort-likes" type="button">좋아요순</button></li>
						</ul>
					</div>
				</div>


				<div class="data-container" id="data-container">
					<!-- Item -->
					<c:forEach var="item" items="${partySortList}">
						<a href="/party/${item.sanPartyId}">
							<div class="recruit-item" id="${item.sortNum}">
								<img class="user-img" id="${item.userId}" src="${empty item.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(item.userPic, 12, 57)}" alt="img" />
								<p class="user-name"  id="${item.userId}">${item.nickName}</p>
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
							</div>
						</a>
					</c:forEach>
  				</div>
				<div id="maxPage" boardMaxPage="${maxPage}" style="display:none;"></div>
			</div>
		</section>
		<!-- main end -->
	</div>
	<!-- total-wrap end -->

	<!-- 리모컨 -->
	<a href="#" class="scrollToTop"> <img src="/resources/svg/top.svg"></a>
	<a href="/party/register" class="add-item"> <img src="/resources/svg/add-item.svg">
	</a>


	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>

</html>