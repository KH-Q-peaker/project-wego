<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<header>
	<div class="header">
		<div class="logoDiv">
			<a href="/"> <span class="wegoLogo"></span> <span
				class="wogoTitle"></span>
			</a>
		</div>
		<div>
			<nav>
				<ul class="navbar">
					<li><a href="/info">산정보</a></li>
					<li><a href="/review">등산후기</a></li>
					<li><a href="#">랭킹</a></li>
					<li><a href="/party">모집</a></li>
				</ul>
			</nav>
		</div>
		<div class="user">
			
			<c:choose>
				<c:when test="${sessionScope.__AUTH__ == null}">
				<!-- 비로그인 상태 -->
					<button class="login-btn" onclick="location.href='/login'"></button>
				</c:when>
				<c:when test="${sessionScope.__AUTH__.userPic != null}">
				<!-- 로그인 상태 -->
					<img id="userImg" class="user-profile-img"
						src="${sessionScope.__AUTH__.userPic}" alt="회원 프로필 이미지" />
				</c:when>
				<c:otherwise>
				<!-- 로그인 상태 -->
					<img id="userImg" class="user-profile-img"
						src="/resources/img/leaf.jpg" alt="회원 프로필 이미지" />
				</c:otherwise>
			</c:choose>
			
			<div class="menubar">
				<ul>
					<li><a href="../profile/${sessionScope.__AUTH__.userId}">마이페이지</a></li>
					<li><a href="#">알림</a></li>
					<li><a href="#">문의하기</a></li>
					<li id="logout"><a href="/login/logout">로그아웃</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="search-bar">
		<div>
			<form action="/search" method="get">
				<button class="search-icon" type="submit"></button>
				<input type="text" name="query" id="search" placeholder="Search" />
				<button class="cancel" type="reset"></button>
			</form>
		</div>
	</div>
</header>
