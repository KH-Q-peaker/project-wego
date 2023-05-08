<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">

<head>

	<jsp:include page="../head.jsp" />

	<link rel="stylesheet" href="/resources/css/sanInfo-detail.css" />
	<link rel="stylesheet" href="/resources/css/sanInfo-main.css" />
	<link rel="stylesheet" href="/resources/css/sanInfo-weather.css" />
	<link rel="stylesheet" href="/resources/css/sanInfo-food.css" />
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c9dfa495780e1b0fdb9ce6347b76a75b&libraries=services"></script>
	<script>
		var sanInfoId = ${sanInfoVO.sanInfoId};
	</script>

	<script src="/resources/js/favorite-info-detail.js" defer></script>
	<script src="/resources/js/sanInfo-detail.js" defer></script>	
<!-- 	<script src="/resources/js/sanInfo-food.js" defer></script>	 -->

</head>

<body>
	<div class="total-wrap">
		<!-- hearder start -->
		<%@include file="/WEB-INF/views/common/header.jsp" %>
			<!-- hearder end -->
			<!-- main start -->
		<section>
			<!-- Contents -->

			<div class="container">

				<div class="sanImg">
					<img class="mountain-img" src="/${sanInfoVO.img}" alt="img" />
				</div>

				<div class="sanName">
					<span class="column">산이름</span>
					<span class="value">${sanInfoVO.sanName}</span>
				</div>

				<div class="weather">
					<span>현재 날씨</span>
					<img class="weather-image" src="https://openweathermap.org/img/wn/${currentWeatherIcon}.png">
				</div>

				<div class="sanAddress">
					<span class="column">주소</span>
					<span class="value">${sanInfoVO.address}</span>
				</div>

				<input id="sanInfoId" type="hidden" value="${sanInfoVO.sanInfoId}">
				<div class="like">
					<c:if test="${isFavorite}">
						<c:set var="status" value=" on" />
					</c:if>
					<button type="button" name="favorite" class="favorite${status}"></button>
					<span class="likenum">${sanInfoVO.likeCnt}</span>
				</div>

				<div class="button">
					<button id="review" onclick="location.href='/review/search?query=${sanInfoVO.sanName}'">등산후기</button>
					<button id="party" onclick="location.href='/party/search?query=${sanInfoVO.sanName}'">등산모집</button>
				</div>

			</div>
			<div id="content-section">
				<ul class="content-header-menu">
					<li class="content-header-menu-item selected" id="overview">
						<input id="content-link-written" type="button" class="sanInfoMenu" value="개요"/>
					</li>
					<li class="content-header-menu-item" id="main">
						<input id="content-link-wsritten" type="button" class="sanInfoMenu" value="상세정보"/>
					</li>
					<li class="content-header-menu-item" id="weather">
						<input id="content-link-written" type="button" class="sanInfoMenu" value="날씨"/>
					</li>
					<li class="content-header-menu-item active" id="food">
						<input id="content-link-written" type="button" class="sanInfoMenu" value="주변맛집"/>
					</li>
				</ul>
			</div>

			<div id="module">
				<!-- add contents -->
			</div>

		</section>
			<!-- main end -->
	</div>
	<!-- total-wrap end -->
	<a href="#" class="scrollToTop">
		<img src="/resources/svg/top.svg" />
	</a>

	<!-- Footer Start -->
	<%@include file="/WEB-INF/views/common/footer.jsp" %>
		<!-- Footer End -->

</body>

</html>
