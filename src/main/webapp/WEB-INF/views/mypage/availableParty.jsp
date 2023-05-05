<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="/resources/js/my-available-party.js" defer></script>
<div id="availablePartyModule">

	<h2>신청한 등산 일정 🚩</h2>
	<table class="middle">
		<thead>
			<tr style="align-items: center">
				<th class="t1">말머리</th>
				<th class="t2">제목</th>
				<th class="t3">등반날짜</th>
				<th class="t4">참여인원</th>
				<th class="t5">채팅방</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${availableParty}" var="YesParty">
				<tr>
					<td class="t1">${YesParty.sanName}</td>
					<td class="t2">
							<a href="/party/${YesParty.sanPartyId}">
								${YesParty.title}</a>
					</td>
					<td class="t3"><fmt:formatDate pattern="yyyy-MM-dd"
						value="${YesParty.partyDt}"></fmt:formatDate>
					</td>
					<td class="t4">${YesParty.partyCount}/${YesParty.partyMax}</td>
					<td class="t5">
						<button class="chatting">채팅입장</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div id="pagination">
			<ul class="paginUl">
				<%-- PREV 표시 c:if --%>
				<c:if test="${availPage.prev}">
					<li class="prev"><span
						data-temp=" ${availPage.cri.setCurrPage(availPage.startPage-1)}"
						onclick="selectClickAvailableCurrPagePrev()">PREV</span> <input
						type="hidden" id="availcurrPagePrev"
						value="${availPage.startPage-1}"></li>
				</c:if>

				<%-- 페이지번호목록  --%>
				<c:forEach var="pageNum" begin="${availPage.startPage}"
					end="${availPage.endPage}">
					<%--  begin end는 마치 between연산자와 비슷 --%>
					<li
						class="${param.currPage eq pageNum? 'currPage' : ''} availablePartyPage${pageNum}"
						data-availPage-page="${__aCurrPage__}" id="availPageNum"
						onclick="selectClickAvailableCurrPage()">
						<span data-temp= " ${availPage.cri.setCurrPage(pageNum)}">${pageNum}</span>
						<input
						type="hidden" id="availcurrPage"
						value="${__aCurrPage__}">
					</li>
				</c:forEach>

				<%-- NEXT 표시 c:if  --%>
				<c:if test="${availPage.next}">
					<!-- next 버튼 클릭시, 다음 페이지로 이동 -->
					<li class="next"><span
						data-temp=" ${availPage.cri.setCurrPage(availPage.endPage+1)}"
						onclick="selectClickAvailableCurrPageNext()">NEXT</span> <input
						type="hidden" id="availcurrPageNext"
						value="${availPage.endPage+1}"></li>
				</c:if>
			</ul>
	</div>
</div>

