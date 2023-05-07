<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">

<head>

	<jsp:include page="../head.jsp" />

	<link rel="stylesheet" href="/resources/css/sanInfo-detail.css" />

	<script src="/resources/js/favorite.js" defer></script>
	<script src="/resources/js/sanInfo-detail.js" defer></script>	

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

				<input type="hidden" id="sanInfo" value="${sanInfoVO.sanInfoId}">

				<div class="sanImg">
					<img class="mountain-img" src="/${sanInfoVO.img}" alt="img" />
				</div>

				<div class="sanName">
					<p class="mimoname">산이름: ${sanInfoVO.sanName}</p>
				</div>

				<div class="weather">
					<img class="weather-image" src="./svg/sun.svg">
				</div>

				<div class="sanAddress">
					<p class="mimoaddress">주소: ${sanInfoVO.address}</p>
				</div>

				<div class="like">
					<img class="heart" src="./svg/heart.svg">
					<p class="likenum">${sanInfoVO.likeCnt}</p>
				</div>

				<div class="button">
					<button id="party" onclick="location.href='/party/search?query=${sanInfoVO.sanName}'">등산모집</button>
					<button id="review" onclick="location.href='/review/search?query=${sanInfoVO.sanName}'">등산후기</button>
				</div>

			</div>

			<div id="content-section">
				<ul class="content-header-menu">
					<li class="content-header-menu-item" id="item-point">
						<a>소개</a>
					</li>
					<li class="content-header-menu-item" id="in">
						<a>등산로 정보</a>
					</li>
					<li class="content-header-menu-item" id="wea">
						<a>날씨</a>
					</li>
					<li class="content-header-menu-item active" id="food">
						<a>주변맛집</a>
					</li>
				</ul>
			</div>

			<div id="module">


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