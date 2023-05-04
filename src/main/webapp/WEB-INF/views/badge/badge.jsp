<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@page import="java.util.*" %>
  <%@page import="org.zerock.wego.domain.*" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

	<link rel="shortcut icon" href="/resources/ico/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="/resources/ico/favicon.ico" type="image/x-icon" />
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
	  
 	<link rel="stylesheet" href="/resources/css/badge.css">
	<link rel="stylesheet" href="/resources/css/header.css" />
 	<link rel="stylesheet" href="/resources/css/footer.css" />
 	
 	<script src="/resources/js/header.js" defer></script>
	<script src="/resources/js/footer.js" defer></script>
  
	<script>
		var targetUserId = ${targetUserId};
		var authUserId = ${sessionScope.__AUTH__.userId};
	</script>
	
	<script src="/resources/js/badge.js" defer></script>
	
</head>

<body>
  <div class="total-wrap">
    <!-- hearder start -->
    <jsp:include page="../common/header.jsp" />
    <!-- hearder end -->

    <!-- main start -->
    <section>
      <!-- Contents -->
      <div class="container">
        <div class="badge">
          <div class="head">
            <span class="title boxStyle">
              <div class="boxName">
                <span id="svg"></span>
                <span id="targetUserName">${targetUserNickname}</span>
                <span>의 뱃지</span>
                <span id="svg"></span>
              </div>
            </span>
            <span class="pick5Setting">
              <button type="button">
                <span id="svg"></span>
              </button>
              <dialog id="setting">
                <div class="settingTitle">
                  대표선택
                </div>
                <div class="settingError">
                  대표뱃지는 5개까지만 설정 가능합니다
                </div>
                <!-- badge pick setting -->
                <div class="badgeBox" id="setting">
                </div>
                <div class="modalBtn">
                  <form method="dialog">
                    <button value="close">닫기</button>
                    <button value="submit">저장</button>
                  </form>
                </div>
              </dialog>
            </span>
          </div>

          <!-- rank badge -->
          <div class="badgeBox boxStyle" id="pick5">
            <div class="boxName">
              <span id="svg"></span>
            </div>
          </div>

          <div class="badgeBox boxStyle collection" id="rank">
            <div class="boxName">
              <span id="svg"></span>
            </div>
            <c:forEach items="${rankingBadgeList}" var="badgeVO">
              <div class="item" id = "${badgeVO.badgeId}" style="display: none;">
                <div class="sanBadge"  style="background-image: url(${badgeVO.img});"></div>
                <div class="badgeName">${badgeVO.badgeName}</div>
              </div>
            </c:forEach>
          </div> 
          <!-- san badge -->
          <div class="badgeBox boxStyle collection" id="san">
            <div class="boxName">
              <span id="svg"></span>
            </div>
            <c:forEach items="${sanBadgeList}" var="badgeVO">
              <div class="item" id = "${badgeVO.badgeId}" style="display: none;">
                <div class="sanBadge"  style="background-image: url(${badgeVO.img});"></div>
                <div class="badgeName">${badgeVO.badgeName}</div>
              </div>
            </c:forEach>
          </div>
        </div>
      </div>
      
      <a href="#" class="scrollToTop"> <img src="/resources/svg/top.svg" /> </a>
      
    </section>
    <!-- main end -->

  </div>
  <!-- Footer Start -->
  <jsp:include page="../common/footer.jsp" />
  <!-- Footer End -->
</body>

</html>