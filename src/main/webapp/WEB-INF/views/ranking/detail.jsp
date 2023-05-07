<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="org.zerock.wego.domain.ranking.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8" />
<title>Wego</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="등산멤버 모집 커뮤니티" />

<link rel="shortcut icon" href="/resources/ico/favicon.ico"
	type="image/x-icon" />
<link rel="icon" href="/resources/ico/favicon.ico" type="image/x-icon" />

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>

<link rel="stylesheet" href="/resources/css/ranking-detail.css">

<link rel="stylesheet" href="/resources/css/header.css" />
<link rel="stylesheet" href="/resources/css/footer.css" />

<script src="/resources/js/header.js" defer></script>
<script src="/resources/js/footer.js" defer></script>

<script src="/resources/js/ranking-detail.js" defer></script>

</head>

<body>
<c:set var="imgBasePath" value="/img/" />
	<div class="total-wrap">
		<!-- hearder start -->
		<jsp:include page="../common/header.jsp" />
		<!-- hearder end -->

		<!-- main start -->

		<section>
			<!-- Contents -->
			<div class="container">
				<div class="rankSelect">
					<div class="head">
						<div class="title boxStyle">
							<span id="svg"></span>
							<span>${rankingName}</span>
							<span id="svg"></span>
						</div>
					</div>  

					<div class="boxStyle" id="rankers">

						<c:forEach items="${ranking1to3List}" var="rankingVO">

							<c:choose>
								<c:when test="${rankingVO.ranking == 1}">
									<!-- 1등 -->
									<div class="item" id="gold">
								</c:when>
								<c:when test="${rankingVO.ranking == 2}">
									<!-- 2등 -->
									<div class="item" id="silver">
								</c:when>
								<c:otherwise>
									<!-- 3등 -->
									<div class="item" id="bronze">
								</c:otherwise>
							</c:choose>

							<div class="rankerMedal">
								<span id="svg"></span>
							</div>
							<a href="/profile/${rankingVO.userId}">
								<div class="userProfile">
									<div class="rankerPic">
										<img class="user-img" src="${empty rankingVO.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(rankingVO.userPic, 12, 57)}" alt="img" />
									</div>
									<div class="rankerName">${rankingVO.nickname}</div>
								</div>
							</a>
							<div class="rankerVal">${rankingVO.value}</div>
							<div class="ranker-img"></div>
					</div>

					</c:forEach>

				</div>

				<div class="boxStyle" id="rankingAll">
					<div class="rankingList">
						<div class="item column">
							<div class="ranking">
								<span> 순위 </span>
							</div>
							<div class="userProfile">
								<span id="pic"></span> <span id="name"> 유저 </span>
							</div>
							<div class="val">
								<span> 수치 </span>
							</div>
						</div>

						<c:forEach items="${ranking4to30List}" var="rankingVO">
							<div class="item row">
								<div class="ranking">
									<span> ${rankingVO.ranking} </span>
								</div>
                <a href="/profile/${rankingVO.userId}">
                  <div class="userProfile">
                    <span id="pic"> 
                      <img class="user-img" src="${empty rankingVO.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(rankingVO.userPic, 12, 57)}" alt="img" />
                    </span> 
                    <span <c:if test="${rankingVO.userId == sessionScope.__AUTH__.userId}" > class="me" </c:if> id="name"> ${rankingVO.nickname} </span>
                  </div>
                </a>
								<div class="val">
									<span> ${rankingVO.value} </span>
								</div>
							</div>
						</c:forEach>

						<div id="me" class="item row">
							<div class="title">내 순위</div>
							<div class="ranking">
								<span>${myRanking.ranking}</span>
							</div>
							<a href="/profile/${rankingVO.userId}">
								<div class="userProfile">
									<span id="pic"> 
										<img class="user-img" src="${empty rankingVO.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(rankingVO.userPic, 12, 57)}" alt="img" />
									</span> 
									<span class="me" id="name"> ${myRanking.nickname} </span>
								</div>
							</a>
							<div class="val">
								<span>${myRanking.value}</span>
							</div>
						</div>

					</div>
				</div>

			</div>
	</div>
	</section>

	<!-- main end -->

	</div>
	<!-- Footer Start -->
	<jsp:include page="../common/footer.jsp" />
	<!-- Footer End -->
</body>

</html>