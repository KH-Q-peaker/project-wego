<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<jsp:include page="./head.jsp" />
</head>

<body>
	<div class="total-wrap">
		<!-- hearder start -->
		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<!-- hearder end -->

		<!-- main start -->
		<section>
			<!-- Contents -->

			<!-- 마이페이지 공통:프로필,뱃지,닉네임 -->
			<jsp:include page="./mypageContainer.jsp" />


			<!-- 프로필 아래  네비  -->
			<div id="content-section">
				<ul class="content-header-menu">
					<li class="content-header-menu-item" id="climb"><span>등산
							일정</span></li>
					<li class="content-header-menu-item" id="info"><span>내
							정보</span></li>
					<li class="content-header-menu-item" id="mypost"><span>내가
							쓴 글, 댓글</span></li>
					<li class="content-header-menu-item active" id="mylike"><span>나의
							좋아요</span></li>
				</ul>
			</div>

			<!-- 내용물  -->
			<div id=module>
				<div class="cotents">
					<div class="content1" id="content1"></div>
					<div class="content2" id="content2"></div>
				</div>
			</div>

			<!-- 로딩 -->
			<div class="loader"></div>
			<div class="container" style="display: none"><div>
		</section>
		<!-- main end -->
	</div>
	<!-- total-wrap end -->
	
	<a href="#" class="scrollToTop"> <img src="/resources/svg/top.svg" /></a>

	<%@include file="/WEB-INF/views/common/footer.jsp"%>

</body>
</html>

