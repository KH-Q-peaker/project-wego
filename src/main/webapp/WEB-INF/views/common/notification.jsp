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
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      rel="shortcut icon"
      href="/resources/ico/favicon.ico"
      type="image/x-icon"
    />
    <link rel="icon" href="/resources/ico/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="/resources/css/header.css" />
    <link rel="stylesheet" href="/resources/css/footer.css" />
    <link rel="stylesheet" href="/resources/css/notification.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/delete.css"  />
    
    <script src="/resources/js/header.js" defer></script>
    <script src="/resources/js/top.js" defer></script>
   	<script src="/resources/js/footer.js" defer></script>
    
	<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
	<script>
    console.log(window.location);
	let contextPath = window.location.host;
	var socket = new WebSocket("ws:"+ contextPath + "/notification");
	</script>
    <script src="/resources/js/notification.js" defer></script>
    <script src="/resources/js/toggle.js" defer></script>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timeago/1.6.5/jquery.timeago.min.js"defer></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timeago/1.6.5/locales/jquery.timeago.ko.js"defer></script>
  </head>

  <body>
  <c:set var="imgBasePath" value="/img/" />
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
          <!-- 토글 -->
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
          <c:forEach items="${notificationList}" var="notificationVO">
            <c:if test="${notificationVO.status == 'N' }">
              <c:if test="${notificationVO.targetGb eq '댓글'}">
                <div class="alarm comment">
                  <h3>[댓글알림] ${notificationVO.contents}
                    <input type="button" class="deletealarm remove" name="deletealarm" value="삭제" data-alarmId="${notificationVO.alarmId}"/> 
                  </h3>
                  <time class="timeago" datetime="${notificationVO.createdDt}"
                    >${notificationVO.createdDt}</time
                  >
                  <div class="message comment">
                    <!-- <img src="${empty notificationVO.userPic ? '/resources/img/leaf.png' : imgBasePath + notificationVO.userPic.substring(12, 57)}" alt="User Pic"> -->
                    <img src="${empty createdAlarmUsers[notificationVO.createdByUserId].userPic ? '/resources/img/default_user.png' : imgBasePath += fn:substring(createdAlarmUsers[notificationVO.createdByUserId].userPic, 12, 57)}" alt="User Pic">
                    <h4>${createdAlarmUsers[notificationVO.createdByUserId].nickname}님 : </h4> 
                    <c:choose>
	                    <c:when test="${notificationVO.commentStatus eq 'Y' }" >
                        <p><h3>삭제된 댓글입니다.</h3></p></c:when>
                      <c:otherwise>
                          <c:if test="${fn:contains(notificationVO.contents, '모집')}">
                            <p><a href="/party/${notificationVO.targetCd}"  id="notification-link" onclick="markNotificationAsRead(${notificationVO.alarmId})">${notificationVO.content}</a></p></c:if>
                          <c:if test="${fn:contains(notificationVO.contents, '후기')}">
                            <p><a href="/review/${notificationVO.targetCd}"  id="notification-link" onclick="markNotificationAsRead(${notificationVO.alarmId})">${notificationVO.content}</a></p></c:if>
                          <c:if test="${fn:contains(notificationVO.contents, '단 댓글')}">
                            <p><a href="/review/${notificationVO.targetCd}"  id="notification-link" onclick="markNotificationAsRead(${notificationVO.alarmId})">${notificationVO.content}</a></p></c:if>
                      </c:otherwise>
					</c:choose>
                  </div>
                </div>
              </c:if>
              <c:if test="${notificationVO.targetGb eq '좋아요'}">
                <div class="alarm profile">
                  <h3>${notificationVO.contents}
                    <input type="button" class="deletealarm remove" name="deletealarm" value="삭제" data-alarmId="${notificationVO.alarmId}"/> 
                  </h3>
                  <time class="timeago" datetime="${notificationVO.createdDt}"
                    >${notificationVO.createdDt}</time
                  >
                  <div class="message profile">
                  
					 <img src="${empty createdAlarmUsers[notificationVO.createdByUserId].userPic ? '/resources/img/leaf.png' : imgBasePath += fn:substring(createdAlarmUsers[notificationVO.createdByUserId].userPic, 12, 57)}" alt="User Pic">                    
					 <h4>${createdAlarmUsers[notificationVO.createdByUserId].nickname}님이 좋아요한 글&#128149;: </h4> 
                    <c:if test="${fn:contains(notificationVO.contents, '모집')}">
                      <p><a href="/party/${notificationVO.targetCd}"  id="notification-link" onclick="markNotificationAsRead(${notificationVO.alarmId})">${notificationVO.title}</a></p></c:if>
                    <c:if test="${fn:contains(notificationVO.contents, '후기')}">
                      <p><a href="/review/${notificationVO.targetCd}"  id="notification-link" onclick="markNotificationAsRead(${notificationVO.alarmId})">${notificationVO.title}</a></p></c:if>
                  </div>
                </div>
              </c:if>
              <c:if test="${fn:contains(notificationVO.contents, '취소')}">
                <div class="alarm mozip">
                    <h3>[긴급알림] ${notificationVO.contents}
                      <input type="button" class="deletealarm remove" name="deletealarm" value="삭제" data-alarmId="${notificationVO.alarmId}"/> 
                    </h3>                 
                  <time class="timeago" datetime="${notificationVO.createdDt}"
                    >${notificationVO.createdDt}</time
                  >
                  <div class="message mozip">
					  <div>
					    <img class="attention" src="resources/img/Idea.png" alt="">
					    <p>모집 취소 사유: 주최자의 사정으로 모집이 취소되었어요.</p>
					  </div>
				  </div>
                </div>
              </c:if>
              <c:if test="${notificationVO.targetGb eq '뱃지'}">
                <div class="alarm badge">
                      <h3>${notificationVO.contents}
                        <input type="button" class="deletealarm remove" name="deletealarm" value="삭제" data-alarmId="${notificationVO.alarmId}"/> 
                      </h3>
                      <time class="timeago" datetime="${notificationVO.createdDt}"
                    >${notificationVO.createdDt}</time
                  >
                      <div class="message badge">
                          <img src="resources/img/yellowBadge.png" alt="">
                          <p> ${notificationVO.nickname}님~축하해요~ &#128149; 앞으로 더 많은 뱃지를 모아서 프로등산러에 도전해보세요-!!</p>
                      </div>
                </div>
              </c:if>
            </c:if>

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
