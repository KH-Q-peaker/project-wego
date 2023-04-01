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
  <title>WeGo</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta name="description" content="등산멤버 모집 커뮤니티" />

  <link rel="stylesheet" href="${path}/resources/css/footer.css" />
  
  <script src="${path}/resources/js/footer.js" defer></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
</head>

<body>
    <!-- Footer Start -->
   <footer>
      <div class="footer">
        <ul>
          <li><a href="#">사이트 도움말</a></li>
          <li><a href="#">사이트 이용약관</a></li>
          <li>
            <a href="#"><strong>개인정보취급방침</strong></a>
          </li>
          <li><a href="#">책임의 한계와 법적고지</a></li>
          <li><a href="#">게시중단요청서비스</a></li>
          <li><a href="#">고객센터</a></li>
        </ul>
        <address>
          Copyright ©
          <a href="/"><strong>Wego</strong></a>
          All Rights Reserved.
        </address>
        <div class="support">
          <span class="link-bar">
            <a href="https://www.figma.com/file/4Gfranq8Iqz4pSebwzUc1W/Final-project?node-id=0%3A1&t=UILOPhDRyS6LJiQW-1"
              target="_blank"></a>
            <a href="https://github.com/KH-Q-peaker?tab=repositories" target="_blank"></a>
          </span>
        </div>
      </div>
    </footer>
  <!-- Footer End -->
</body>
    <!-- Footer End -->
</html>