<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="imgBasePath" value="/img/" />
<div class="rank">

	<!-- <div class="head">
		<div class="title boxStyle">
			<span id="svg"></span> <span id="userName">RANKING</span> <span
				id="svg"></span>
		</div>
	</div> -->

	<div class="rankers">

		<div class="boxStyle" id="rankBox one-way">
			<a href="/ranking/one-way"> <!-- ranker detaile -->
				<div class="boxStyle" id="ranker" style="display: none">

					<div class="title">
						<span id="svg"></span> <span id="userName">한우물왕</span> <span
							id="svg"></span>
					</div>

					<c:forEach items="${oneWayList}" var="rankingVO">

						<c:choose>
							<c:when test="${rankingVO.ranking == 1}">
								<!-- 1등 -->
								<div class="item" id="gold">
							</c:when>
							<c:when test="${rankingVO.ranking == 2}">
								<!-- 2등 -->
								<div class="item" id="silver">
							</c:when>
							<c:otherwise>
								<!-- 3등 -->
								<div class="item" id="bronze">
							</c:otherwise>
						</c:choose>

							<div class="rankerMedal">
								<span id="svg"></span>
							</div>
							<div class="rankerProfile" id="${rankingVO.userId}">
								<div class="rankerPic">
									<img class="user-img" src="${empty rankingVO.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(rankingVO.userPic, 12, 57)}" alt="img" />
								</div>
								<div class="rankerName">${rankingVO.nickname}</div>
							</div>
							<div class="rankerVal">${rankingVO.value}</div>
							<div class="ranker-img"></div>
						</div> 
						
					</c:forEach>
					
				</div>
		<!-- ranker -->
		<div class="item" id="one-way">
			<div class="title">한우물왕</div>
			<div class="ranker-img"></div>
		</div>

		</a>
	</div>
	<div class="boxStyle" id="rankBox highest">
		<a href="/ranking/highest"> <!-- ranker detaile -->
			<div class="boxStyle" id="ranker" style="display: none">
				<div class="title">
					<span id="svg"></span> <span id="userName">제일높왕</span> <span
						id="svg"></span>
				</div>
				
				<c:forEach items="${highestList}" var="rankingVO">

						<c:choose>
							<c:when test="${rankingVO.ranking == 1}">
								<!-- 1등 -->
								<div class="item" id="gold">
							</c:when>
							<c:when test="${rankingVO.ranking == 2}">
								<!-- 2등 -->
								<div class="item" id="silver">
							</c:when>
							<c:otherwise>
								<!-- 3등 -->
								<div class="item" id="bronze">
							</c:otherwise>
						</c:choose>

							<div class="rankerMedal">
								<span id="svg"></span>
							</div>
							<div class="rankerProfile" id="${rankingVO.userId}">
								<div class="rankerPic">
									<img class="user-img" src="${empty rankingVO.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(rankingVO.userPic, 12, 57)}" alt="img" />
								</div>
								<div class="rankerName">${rankingVO.nickname}</div>
							</div>
							<div class="rankerVal">${rankingVO.value}</div>
							<div class="ranker-img"></div>
						</div> 
						
					</c:forEach>
				
			</div> 
			<!-- ranker -->
			<div class="item" id="highest">
				<div class="title">제일높왕</div>
				<div class="ranker-img"></div>
			</div>

		</a>
	</div>

	<div class="boxStyle" id="rankBox party-king">
		<a href="/ranking/party-king"> <!-- ranker detaile -->
			<div class="boxStyle" id="ranker" style="display: none">
				<div class="title">
					<span id="svg"></span>
					<span id="userName">참대왕</span>
					<span id="svg"></span>
				</div>
				
				<c:forEach items="${partyKingList}" var="rankingVO">

						<c:choose>
							<c:when test="${rankingVO.ranking == 1}">
								<!-- 1등 -->
								<div class="item" id="gold">
							</c:when>
							<c:when test="${rankingVO.ranking == 2}">
								<!-- 2등 -->
								<div class="item" id="silver">
							</c:when>
							<c:otherwise>
								<!-- 3등 -->
								<div class="item" id="bronze">
							</c:otherwise>
						</c:choose>

							<div class="rankerMedal">
								<span id="svg"></span>
							</div>
							<div class="rankerProfile" id="${rankingVO.userId}">
								<div class="rankerPic">
									<img class="user-img" src="${empty rankingVO.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(rankingVO.userPic, 12, 57)}" alt="img" />
								</div>
								<div class="rankerName">${rankingVO.nickname}</div>
							</div>
							<div class="rankerVal">${rankingVO.value}</div>
							<div class="ranker-img"></div>
						</div> 
						
					</c:forEach>
				
			</div> 
			<!-- ranker -->
			<div class="item" id="party-king">
				<div class="title">참대왕</div>
				<div class="ranker-img"></div>
			</div>
		</a>
	</div>


	<div class="boxStyle" id="rankBox review-king">
		<a href="/ranking/review-king"> <!-- ranker detaile -->
			<div class="boxStyle" id="ranker" style="display: none">
				<div class="title">
					<span id="svg"></span>
					<span id="userName">후기왕</span>
					<span id="svg"></span>
				</div>
				
				<c:forEach items="${reviewKingList}" var="rankingVO">

						<c:choose>
							<c:when test="${rankingVO.ranking == 1}">
								<!-- 1등 -->
								<div class="item" id="gold">
							</c:when>
							<c:when test="${rankingVO.ranking == 2}">
								<!-- 2등 -->
								<div class="item" id="silver">
							</c:when>
							<c:otherwise>
								<!-- 3등 -->
								<div class="item" id="bronze">
							</c:otherwise>
						</c:choose>

							<div class="rankerMedal">
								<span id="svg"></span>
							</div>
							<div class="rankerProfile" id="${rankingVO.userId}">
								<div class="rankerPic">
									<img class="user-img" src="${empty rankingVO.userPic ? "/resources/img/default-user.jpg" : imgBasePath += fn:substring(rankingVO.userPic, 12, 57)}" alt="img" />
								</div>
								<div class="rankerName">${rankingVO.nickname}</div>
							</div>
							<div class="rankerVal">${rankingVO.value}</div>
							<div class="ranker-img"></div>
						</div> 
						
					</c:forEach>
				
			</div> 
			
			<!-- ranker -->
			<div class="item" id="review-king">
				<div class="title">후기왕</div>
				<div class="ranker-img"></div>
			</div>
		</a>
	</div>

</div>

</div>



