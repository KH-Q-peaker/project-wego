<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="imgBasePath" value="/img/" />
	<c:forEach var="item" items="${sanInfoSortList}">
		<a href="/info/${item.sanInfoId}">
			<div class="mountain-item" id="${item.sortNum}">
				<img class="mountain-img" src="/${item.img}" alt="img" />
				<h3 class="mountain-name" id="mountainName">${item.sanName}</h3>
				<p class="mountain-contents" id="text">${item.details}</p>
				<div class="mountain-like">
					<c:set var="doneLoop" value="false" />
					<c:set var="status" value="" />
					<c:forEach var="userFavorite" items="${favoriteList}">
						<c:if
							test="${userFavorite.targetGb == 'SAN_INFO' 
							&& userFavorite.targetCd == item.sanInfoId}">
							<c:set var="status" value=" on" />
						</c:if>
					</c:forEach>
					<button type="button" name="favorite" class="favorite${status}"></button>
					<span class="favorite-count">${item.likeCnt}</span>
				</div>
			</div>
		</a>
	</c:forEach>