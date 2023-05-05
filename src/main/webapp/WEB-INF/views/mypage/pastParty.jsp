<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="/resources/js/my-past-party.js" defer></script>
<div id="pastPartyModule">

	<h2>지난 등산 일정 ⛰</h2>
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
			<c:forEach items="${pastParty}" var="NoParty">
				<tr>
					<td class="t1">${NoParty.sanName}</td>
					<td class="t2"><a href="/party/${NoParty.sanPartyId}">
								${NoParty.title}</a>
					</td>
					<td class="t3"><fmt:formatDate pattern="yyyy-MM-dd"
						value="${NoParty.partyDt}"></fmt:formatDate>
					</td>
					<td class="t4">${NoParty.partyCount}/${NoParty.partyMax}</td>
					<td class="t5">
						<button class="chatting-off">채팅입장</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div id="pagination">
		<ul class="paginUl">
			<%-- PREV 표시 c:if --%>
			<c:if test="${pastPage.prev}">
				<li class="prev"><span class="pastPageNum" id="pastPagePrev"
					data-temp=" ${pastPage.cri.setCurrPage(pastPage.startPage-1)}"
					onclick="selectClickPastCurrPagePrev()">PREV</span> <input
					type="hidden" id="pastcurrPagePrev" value="${pastPage.startPage-1}">
				</li>
			</c:if>

			<%-- 페이지번호목록  --%>
			<c:forEach var="pageNum" begin="${pastPage.startPage}"
				end="${pastPage.endPage}">
				<li
					class="${param.currPage eq pageNum? 'currPage' : ''} pastPartyPage${pageNum}"
					data-past-page="${__pCurrPage__}" id="pastPageNum"
					onclick="selectClickPastCurrPage()"><span
					data-temp=" ${pastPage.cri.setCurrPage(pageNum)}">${pageNum}</span>
					<input
					type="hidden" id="pastcurrPage" value="${__pCurrPage__}">
				</li>
			</c:forEach>

			<%-- NEXT 표시 c:if  --%>
			<c:if test="${pastPage.next}">
				<!-- next 버튼 클릭시, 다음 페이지로 이동 -->
				<li class="next"><span class="pastPageNum" id="pastPageNext"
					data-temp=" ${pastPage.cri.setCurrPage(pastPage.endPage+1)}"
					onclick="selectClickPastCurrPageNext()">NEXT</span> <input
					type="hidden" id="pastcurrPageNext" value="${pastPage.endPage+1}">
				</li>
			</c:if>
		</ul>
	</div>
</div>
