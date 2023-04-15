<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@page import="java.util.*" %>
  <%@page import="org.zerock.wego.domain.*" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="path" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8" />
	<title>Wego</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="등산멤버 모집 커뮤니티" />

	<link rel="shortcut icon" href="${path}/resources/ico/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="${path}/resources/ico/favicon.ico" type="image/x-icon" />
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
	  
 	<link rel="stylesheet" href="${path}/resources/css/badge.css">

	<link rel="stylesheet" href="${path}/resources/css/header.css" />
 	<link rel="stylesheet" href="${path}/resources/css/footer.css" />
 	
 	<script src="${path}/resources/js/header.js" defer></script>
	<script src="${path}/resources/js/footer.js" defer></script>
  
	<script>
		var targetUserId = ${targetUserId};
		var authUserId = ${sessionScope.__AUTH__.userId};
	</script>
	
	<script src="${path}/resources/js/badge.js" defer></script>
	
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
                <div class="badgeBox" id="setting">
                  <!-- <div class="item" id="1003">
                    <div class="sanBadge"></div>
                    <div class="badgeName">참대장</div>
                  </div>
                  <div class="item" id="1">
                    <div class="sanBadge"></div>
                    <div class="badgeName">가리산</div>
                  </div>
                  <div class="item" id="2">
                    <div class="sanBadge"></div>
                    <div class="badgeName">가리왕산</div>
                  </div>
                  <div class="item" id="4">
                    <div class="sanBadge"></div>
                    <div class="badgeName">가지산</div>
                  </div>
                  <div class="item" id="5">
                    <div class="sanBadge"></div>
                    <div class="badgeName">감악산</div>
                  </div>
                  <div class="item" id="93">
                    <div class="sanBadge"></div>
                    <div class="badgeName">한라산</div>
                  </div> -->
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
            <c:forEach items="${badgeConfig.getRankingBadgeList()}" var="badgeVO">
              <div class="item" id = "${badgeVO.badgeId}" style="display: none;">
                <div class="sanBadge"></div>
                <div class="badgeName">${badgeVO.badgeName}</div>
              </div>
            </c:forEach>
          </div> 
          <!-- san badge -->
          <div class="badgeBox boxStyle collection" id="san">
            <div class="boxName">
              <span id="svg"></span>
            </div>
            <!-- (div.item#$>(div.sanBadge+div.badgeName{~~산}))*100 -->
            <!-- 산 100 샘플 생성 -->
            <c:forEach items="${badgeConfig.getSanBadgeList()}" var="badgeVO">
              <div class="item" id = "${badgeVO.badgeId}" style="display: none;">
                <div class="sanBadge"></div>
                <div class="badgeName">${badgeVO.badgeName}</div>
              </div>
            </c:forEach>
            <!--
            <div class="item get pick" id="1">
              <div class="sanBadge"></div>
              <div class="badgeName">가리산</div>
            </div>
            <div class="item get" id="2">
              <div class="sanBadge"></div>
              <div class="badgeName">가리왕산</div>
            </div>
            <div class="item" id="3">
              <div class="sanBadge"></div>
              <div class="badgeName">가야산</div>
            </div>
            <div class="item get pick" id="4">
              <div class="sanBadge"></div>
              <div class="badgeName">가지산</div>
            </div>
            <div class="item get pick" id="5">
              <div class="sanBadge"></div>
              <div class="badgeName">감악산</div>
            </div>
            <div class="item" id="6">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="7">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="8">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="9">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="10">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="11">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="12">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="13">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="14">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="15">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="16">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="17">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="18">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="19">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="20">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="21">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="22">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="23">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="24">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="25">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="26">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="27">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="28">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="29">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="30">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="31">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="32">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="33">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="34">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="35">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="36">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="37">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="38">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="39">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="40">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="41">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="42">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="43">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="44">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="45">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="46">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="47">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="48">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="49">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="50">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="51">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="52">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="53">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="54">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="55">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="56">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="57">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="58">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="59">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="60">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="61">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="62">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="63">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="64">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="65">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="66">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="67">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="68">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="69">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="70">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="71">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="72">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="73">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="74">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="75">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="76">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="77">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="78">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="79">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="80">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="81">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="82">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="83">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="84">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="85">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="86">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="87">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="88">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="89">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="90">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="91">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="92">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item get pick">
              <div class="sanBadge">
                <span class="dot">3</span>
                <div class="stackList">
                  <ul>
                    <li class=stackTitle>
                      획득내역
                    </li>
                    <li class="stackContents">
                      2020-03-22
                    </li>
                    <li class="stackContents">
                      2021-01-02
                    </li>
                    <li class="stackContents">
                      2022-03-26
                    </li>
                  </ul>
                </div>
              </div>
              <div class="badgeName">한라산</div>
            </div>
            <div class="item" id="94">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="95">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="96">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="97">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="98">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="99">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            <div class="item" id="100">
              <div class="sanBadge"></div>
              <div class="badgeName">~~산</div>
            </div>
            -->
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