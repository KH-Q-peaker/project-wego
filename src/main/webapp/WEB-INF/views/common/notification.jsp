<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt"%> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <title>WeGo-notification</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      rel="shortcut icon"
      href="/resources/ico/favicon.ico"
      type="image/x-icon"
    />
    <link rel="icon" href="/resources/ico/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="/resources/css/header.css" />
    <link rel="stylesheet" href="/resources/css/footer.css" />
    <link rel="stylesheet" href="/resources/css/top.css" />
    <link rel="stylesheet" href="/resources/css/notification.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/delete.css"  />
    
    <script src="/resources/js/header.js" defer></script>
    <script src="/resources/js/top.js" defer></script>

    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="/resources/js/notification.js" defer></script>
    <script src="/resources/js/toggle.js" defer></script>
    
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timeago/1.6.5/jquery.timeago.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timeago/1.6.5/locales/jquery.timeago.ko.js"></script>
  </head>

  <body class>
    <div id="total-wrap">
      <!-- hearder start -->
      <%@include file="/WEB-INF/views/common/header.jsp"%>
      <!-- hearder end -->
      <div id="user-info" style="display: none;" data-user-id="${sessionScope.__AUTH__.userId}"></div>
      <!-- main start -->
      <section>
        <!-- Contents -->
        <div id="alarmHeader">
          <span class="title boxStyle">
            <div class="boxName">
              <span id="svg"></span>
              <span id="authUserName">${sessionScope.__AUTH__.nickname}</span>
              <span>의 알림</span>
              <span id="svg"></span>
            </div>
          </span>
          <button type="button" id="alarmSetbut">
            <span class="alarm-img"></span>
          </button>
          <!-- 토글존 -->
          <dialog id="alarm-set">
            <input type="checkbox" id="toggle" hidden />

            <div class="all">
              <span class="toggleIcon">
                <label for="toggle" class="toggleSwitch" onclick="toggleAll()">
                  <span class="toggleButton"></span>
                </label>
              </span>
              <span class="onOfftext"
                ><span class="highlight">전체</span> 알림 ON / OFF</span
              >
            </div>
            <div class="important">
              <span class="toggleIcon">
                <label
                  for="toggle"
                  class="toggleSwitch"
                  onclick="toggleImportant()"
                >
                  <span class="toggleButton"></span>
                </label>
              </span>
              <span class="onOfftext"
                ><span class="highlight">긴급</span> 알림 ON / OFF</span
              >
            </div>
            <div class="badge">
              <span class="toggleIcon">
                <label for="toggle" class="toggleSwitch">
                  <span class="toggleButton"></span>
                </label>
              </span>
              <span class="onOfftext"
                ><span class="highlight">뱃지획득</span> 알림 ON / OFF</span
              >
            </div>
            <div class="like">
              <span class="toggleIcon">
                <label for="toggle" class="toggleSwitch">
                  <span class="toggleButton"></span>
                </label>
              </span>
              <span class="onOfftext"
                ><span class="highlight">좋아요</span> 알림 ON / OFF</span
              >
            </div>
            <div class="comment">
              <span class="toggleIcon">
                <label for="toggle" class="toggleSwitch">
                  <span class="toggleButton"></span>
                </label>
              </span>
              <span class="onOfftext"
                ><span class="highlight">내가쓴글의 댓글</span> 알림 ON /
                OFF</span
              >
            </div>
            <div class="mozip">
              <span class="toggleIcon">
                <label for="toggle" class="toggleSwitch">
                  <span class="toggleButton"></span>
                </label>
              </span>
              <span class="onOfftext"
                ><span class="highlight">모집</span> 알림 ON / OFF</span
              >
            </div>
          </dialog>
        </div>

        <div class="alarmbox">
          <c:if test="${empty notificationList}">
            <div class="alarm noAlarm">새로운 알림이 없습니다.</div>
          </c:if>            
          <!-- 알림 메세지 종류별 상태값 출력 (상태 수정시 닫기버튼)  -->

          <c:forEach items="${notificationList}" var="notificationList">
            <c:if test="${notificationList.status == 'N' }">

              <c:if test="${notificationList.targetGb eq '댓글'}">
                <div class="alarm comment">
                  <h3>[댓글알림] ${notificationList.contents}
                    <input type="button" class="deletealarm remove" name="deletealarm" value="삭제" /> 
                  </h3>
                  <time class="timeago" datetime="${notificationList.createdDt}"
                    >${notificationList.createdDt}</time
                  >
                  <div class="message comment">
                    <img src="${userService.getById(notificationList.createdByUserId).userPic}" alt="User Pic">
                    <!-- <h4>${userService.getById(notificationList.createdByUserId).nickname}님이 단 댓글 보러가기 : </h4>   무엇으로 할지 선택해줘요-->
                    <h4>${userService.getById(notificationList.createdByUserId).nickname}님 : </h4> 
                    <c:if test="${fn:contains(notificationList.contents, '모집')}">
                      <p><a href="/party/${notificationList.targetCd}"  id="notification-link">${notificationList.title}</a></p></c:if>
                    <c:if test="${fn:contains(notificationList.contents, '후기')}">
                      <p><a href="/review/${notificationList.targetCd}"  id="notification-link">${notificationList.title}</a></p></c:if>
                  </div>
                </div>
              </c:if>
              <c:if test="${fn:contains(notificationList.contents, '좋아요')}">
                <div class="alarm profile">
                  <h3>${notificationList.contents}</h3>
                  <time class="timeago" datetime="${notificationList.createdDt}"
                    >${notificationList.createdDt}</time
                  >
                  <div class="message profile">
                    <img src="${userService.getById(notificationList.createdByUserId).userPic}" alt="User Pic">
                    <c:if test="${notificationList.targetGb eq 'SAN_PARTY'}">
                      <p><a href="/party/${notificationList.targetCd}"  id="notification-link">${notificationList.title}&#128149;</a></p></c:if>
                    <c:if test="${notificationList.targetGb eq 'SAN_REVIEW'}">
                      <p><a href="/review/${notificationList.targetCd}"  id="notification-link">${notificationList.title}&#128149;</a></p></c:if>
                  </div>
                </div>
              </c:if>
              <c:if test="${fn:contains(notificationList.contents, '취소')}">
                <div class="alarm mozip">
                  <span class="mozipimg">
                    <h3>[긴급알림] ${notificationList.contents}</h3>
                    <img class="attention" src="./img/Idea.png" alt="">
                  </span>
                  <time class="timeago" datetime="${notificationList.createdDt}"
                    >${notificationList.createdDt}</time
                  >
                  <div class="message mozip">
                    <p>모집 취소 사유: 주최자의 사정으로 모집이 취소되었어요.</p>
                  </div>
                </div>
              </c:if>
              <c:if test="${notificationList.targetGb eq '뱃지'}">
                <div class="alarm badge">
                      <h3>${notificationList.contents}</h3>
                      <time class="timeago" datetime="${notificationList.createdDt}"
                    >${notificationList.createdDt}</time
                  >
                      <div class="message badge">
                          <img src="./img/yellowBadge.png" alt="">
                          <p> ${notificationList.nickname}님~축하해요~ &#128149; 앞으로 더 많은 뱃지를 모아서 프로등산러에 도전해보세요-!!</p>
                      </div>
                </div>
              </c:if>
            </c:if>
			<!-- 아직 사용할지말지고민중 -->
          <div class="deleteModal">
            <input type="hidden" id="targetGb" name="targetGb" value="${targetGb }">
            <input type="hidden" id="targetCd" name="targetCd" value="${targetCd }">
            <div class="delment"> 알림을 삭제하시겠습니까?</div>
            <input type="button" class="delcancle" value="취소" /> 
            <input type="button" class="del" name="del" value="삭제" />
          </div>
          </c:forEach>
        </div>
      </section>

      <!-- main end -->
    </div>
    <!-- total-wrap end -->

    <a href="#" class="scrollToTop">
      <img src="/resources/svg/top.svg" />
    </a>
    <!-- Footer Start -->
    <%@include file="/WEB-INF/views/common/footer.jsp"%>
    <!-- Footer End -->
  </body>
</html>
