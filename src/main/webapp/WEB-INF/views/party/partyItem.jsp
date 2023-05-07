<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="imgBasePath" value="/img/" />
	<c:forEach var="item" items="${partySortList}">
		<a href="/party/${item.sanPartyId}">
			<div class="recruit-item" id="${item.sortNum}">
				<img class="user-img" id="${item.userId}" src="${empty item.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(item.userPic, 12, 57)}" alt="img" />
				<p class="user-name"  id="${item.userId}">${item.nickName}</p>
				<p class="mountain-name" id="mountainName">${item.sanName}</p>
				<img class="recruit-img" src="${empty item.partyPic ? "/resources/img/default-party.jpg" : imgBasePath += fn:substring(item.partyPic, 12, 57)}" alt="img" />
				<p class="recruit-title" id="title">${item.title}</p>
				<p class="recruit-schedule-schedule">날짜:</p>
				<p class="recruit-schedule" id="schedule">
					<fmt:formatDate value="${item.partyDt}" pattern="yyyy-MM-dd" />
				</p>
				<p class="recruit-schedule-time">시간:</p>
				<p class="recruit-time" id="time">
					<fmt:formatDate value="${item.partyDt}" pattern="HH:mm" />
				</p>
				<p class="recruit-schedule-member">참여 인원:</p>
				<p class="recruit-member" id="memberCount">${item.userCnt}/
					${item.partyMax}</p>
				<div class="recruit-like">
					<c:set var="doneLoop" value="false" />
					<c:set var="status" value="" />
					<c:forEach var="userFavorite" items="${favoriteList}">
						<c:if
							test="${userFavorite.targetGb == 'SAN_PARTY' 
							&& userFavorite.targetCd == item.sanPartyId}">
							<c:set var="status" value=" on" />
						</c:if>
					</c:forEach>
					<button type="button" name="favorite" class="favorite${status}"></button>
					<span class="favorite-count">${item.likeCnt}</span>
				</div>
				<p class="recruit-date" id="date">
					<fmt:formatDate value="${item.createdDt}" pattern="yyyy-MM-dd" />
				</p>
			</div>
		</a>
	</c:forEach>