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
<link rel="stylesheet" href="/resources/css/searchFail.css?after" />
<link rel="stylesheet" href="/resources/css/sort.css?after" />

<script src="/resources/js/header.js" defer></script>
<script src="/resources/js/footer.js" defer></script>
<script src="/resources/js/sort.js" defer></script>
<script src="/resources/js/top.js" defer></script>
<script src="/resources/js/favorite.js" defer></script>
<script src="/resources/js/board-type.js" defer></script>
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


			<div class="container">

				<div class="search-results">
				
					<div class="result-line">
						<div class="hr-sect">검색결과</div>
					</div>
				
					<div class="guide">
						<p class="none">검색결과가 없습니다.</p>
						<p>
							검색어를 다시 한번 더 확인해 주세요. <br>
							검색어의 단어를 하나로 줄이거나, 다른 검색어로 검색해 주세요. <br>
						</p>
						<br>
					</div>
				</div>


				<div class="suggestion">
				
					<div class="suggestion-line">
						<div class="hr-sect">추천</div>
					</div>
				
				</div>
				
				<div class="suggestion-item">
					
					<!-- Item -->
					<c:forEach var="item" items="${partySuggestion}">
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

			</div>
		</section>
		<!-- main end -->
	</div>
	<!-- total-wrap end -->

	<!-- 리모컨 -->
	<a href="#" class="scrollToTop"> <img src="/resources/svg/top.svg"></a>

	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>

</html>