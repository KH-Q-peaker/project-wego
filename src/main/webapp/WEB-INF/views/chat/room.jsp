<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<head>

<link rel="stylesheet" type="text/css" href="/resources/css/chat.css" />
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script>
	let sessionUserId = "${sessionUserId}";
    let chatRoomId = "${chatRoomId}";
    console.log(window.location);
	let contextPath = window.location.host;

    let chatSocket = new WebSocket("ws:"+ contextPath + "/chat/room");
</script>
<script type="text/javascript" src="/resources/js/chat.js?ver=1" ></script>
</head>

<body>
	<div class="chatting-area">
		<div id="exit-area">
			<div id="roomTitle">${roomTitle }</div>
		</div>
		<ul class="display-chatting">
			<c:forEach items="${chatList }" var="chat">
				<fmt:formatDate var="chatDate" value="${chat.createdDt }"
					pattern="HH:mm:ss" />
				<%--내꺼  --%>
				<c:if test="${chat.userId == sessionUserId }">
					<li class="myChat"><span class="chatDate">${chatDate }</span>
						<div class="chat">${chat.contents }</div></li>
				</c:if>
				<c:if test="${chat.userId != sessionUserId}">
					<li>
						<div class="userId">${chat.userId }</div>
						<div class="chat">${chat.contents }</div> <span class="chatDate">${chatDate }</span>
					</li>
				</c:if>
			</c:forEach>
		</ul>

		<div class="input-area">
			<textarea id="inputChatting" rows="3"></textarea>
			<button id="send">보내기</button>
		</div>
	</div>
 </body>
</html>