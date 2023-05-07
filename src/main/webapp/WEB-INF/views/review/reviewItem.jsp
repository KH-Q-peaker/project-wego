<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<c:set var="imgBasePath" value="/img/" />
	<c:forEach var="item" items="${reviewSortList}">
		<a href="/review/${item.sanReviewId}">
			<div class="review-item" id="${item.sortNum}">
				<img class="user-img" id="${item.userId}" src="${empty item.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(item.userPic, 12, 57)}" alt="img" />
				<p class="user-name" id="${item.userId}">${item.nickName}</p>
				<p class="mountain-name" id="mountainName">${item.sanName}</p>
				<img class="review-img" src="${empty item.reviewPic ? "/resources/img/default-review.jpg" : imgBasePath += fn:substring(item.reviewPic, 12, 57)}" alt="img" />
				<h3 class="review-title" id="title">${item.title}</h3>
				<p class="review-contents" id="text">${item.contents}</p>
				<div class="review-like">
					<c:set var="doneLoop" value="false" />
					<c:set var="status" value="" />
					<c:forEach var="userFavorite" items="${favoriteList}">
						<c:if
							test="${userFavorite.targetGb == 'SAN_REVIEW' 
							&& userFavorite.targetCd == item.sanReviewId}">
							<c:set var="status" value=" on" />
						</c:if>
					</c:forEach>
					<button type="button" name="favorite" class="favorite${status}"></button>
					<span class="favorite-count">${item.likeCnt}</span>
				</div>
				<p class="review-date" id="date">
					<fmt:formatDate value="${item.createdDt}" pattern="yyyy-MM-dd" />
				</p>
			</div>
		</a>
	</c:forEach>