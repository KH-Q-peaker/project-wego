<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="org.zerock.wego.domain.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8" />
  <title>WeGo-n</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta name="description" content="등산멤버 모집 커뮤니티" />

  <link rel="stylesheet" href="${path}/resources/css/header.css" />
  
  <script src="${path}/resources/js/header.js" defer></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
</head>

<body>
    <!-- hearder start -->
    <header>
      <div class="header">
      <div class="logoDiv">
        <a href="#">
          <span class="wegoLogo"></span>
          <span class="wogoTitle"></span>
        </a>
      </div>
      <div>
        <nav>
          <ul class="navbar">
            <li><a href="#">산정보</a></li>
            <li><a href="#">등산후기</a></li>
            <li><a href="#">랭킹</a></li>
            <li><a href="#">모집</a></li>
          </ul>
        </nav>
      </div>
      <div class="user">
        <!-- 로그인 상태 => 현재 display: none; 상태 -->
        <img id="userImg" class="user-profile-img" src="https://picsum.photos/id/684/600/400" alt="회원 프로필 이미지" />
        <!-- 비로그인 상태 -->
        <button class="login-btn"></button>
        <div class="menubar">
          <ul>
            <li><a href="../mypage/mypage.html">마이페이지</a></li>
            <li><a href="#">알림</a></li>
            <li><a href="#">문의하기</a></li>
            <li class="text-red"><a href="#">로그아웃</a></li>
          </ul>
        </div>
      </div>
    </div>
    <!--
       <div class="search-bar">
        <div>
          <form action="#" method="get">
            <button class="search-icon" type="submit"></button>
            <input
              type="text"
              name="search"
              id="search"
              value=""
              placeholder="Search"
            />
            <button class="cancel" type="reset"></button>
          </form>
        </div>
      </div>
    -->
    </header>
    <!-- hearder end -->
</body>

</html>