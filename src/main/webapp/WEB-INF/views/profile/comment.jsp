<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt"%> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" href="/resources/css/usercomment.css" />
<div class="cotents">
  <div class="content1">
    <c:forEach items="${getUserInfoList}" var="userProfile">
      <h2>${userProfile.nickname} 님의 작성 댓글 &#128204;</h2>
    </c:forEach>

    <table class="middle">
      <thead>
        <tr style="align-items: center">
          <th class="t1">게시판명</th>
          <th class="t2">내용</th>
          <th class="t3">작성일</th>
          <th class="t4">조회수</th>
          <th class="t5">멘션수</th>
        </tr>
      </thead>

      <tbody>
        <c:forEach items="${commentList}" var="profileCommentVO">
          <tr>
            <td class="t1">${profileCommentVO.targetGb}</td>
            <td class="t2">
              <c:choose>
                <c:when test="${profileCommentVO.targetGb eq '등산후기'}">
                  <c:set var="mappingURI" value="review"></c:set>
                </c:when>
                <c:otherwise>
                  <c:set var="mappingURI" value="party"></c:set>
                </c:otherwise>
              </c:choose>
              <c:if test="${not empty mappingURI}">
                <a href="/${mappingURI}/${profileCommentVO.targetCb}">
                  <c:choose>
                    <c:when
                      test="${fn:length(profileCommentVO.contents) > 30}"
                    >
                      ${fn:substring(profileCommentVO.contents, 0,30)}...
                    </c:when>
                    <c:otherwise>
                      ${profileCommentVO.contents}
                    </c:otherwise>
                  </c:choose></a
                >
              </c:if>
            </td>
            <td class="t3">
              <fmt:formatDate
                pattern="yyyy-MM-dd"
                value="${profileCommentVO.createdDt}"
              ></fmt:formatDate>
            </td>
            <td class="t4">100</td>
            <td class="t5">&#128140;${profileCommentVO.mentionCnt}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
  <!-- 페이지네이션 기준갑 들어가야함. -->
  <div id="pagination">
    <form action="paginationForm">
      <ul class="paginUl">
        <!-- Prev 표시 -->
        <c:if test="${pageMaker.prev}">
            <li class="prev">
	            <span
	              data-temp="${pageMaker.cri.setCurrPage(pageMaker.startPage - 1 )}" onclick="selectClickCurrPagePrev()">Prev</span>
	            <input type="hidden" id="currPagePrev" value="${pageMaker.startPage - 1}">
          </li>
        </c:if>

        <!-- 페이지 번호 목록 표시 -->
        <c:forEach
          var="pageNum"
          begin="${pageMaker.startPage}"
          end="${pageMaker.endPage}"
        >
          <!-- 조건문으로 지금 현재 페이지확인 : 전송파라미터중 현페이지번호가같니?그럼비워 -->
          <li class="${param.currPage eq pageNum ? 'currPage' : ''}" id="currPageNum" onclick="selectClickCurrPage()">
            <span data-temp="${pageMaker.cri.setCurrPage(pageNum)}">${pageNum}</span
            >
          </li>
          <!-- 숫자만 표시됨. -->
        </c:forEach>

        <!-- Next 표시 :  core 조건문 
            현재페이지 기준으로 계산되어야함. 다 모델속성에 PageMaker저장되어잇음-->
        <c:if test="${pageMaker.next}">
          <li class="next">
            <span
              data-temp="${pageMaker.cri.setCurrPage(pageMaker.endPage+1)}" onclick="selectClickCurrPageNext()">Next</span>
              <input type="hidden" id="currPageNext" value="${pageMaker.endPage+1}">
          </li>
        </c:if>
      </ul>
    </form>
  </div>
</div>



<script>
function selectClickCurrPage() {
    var currPage = event.target.innerText;
    var amount = 5;
    $.ajax({
          type: 'get',
          url: '/profile/comment',
          data:{"currPage":currPage,"amount":5,userId:"${userId}"},
          success: function(data){
              $(".cotents").load("/profile/comment?userId="+userId+"&currPage="+currPage+"&amount="+amount);
          }//success
   	 });//ajax
}//selectClickCurrPage

function selectClickCurrPagePrev () {
var currPage = $( '#currPagePrev' ).val();
var amount = 5;
$.ajax({
	   type: 'get',
    url: '/profile/comment',
    data:{"currPage":currPage,"amount":5,userId:"${userId}"},
    success: function(data){
        $(".cotents").load("/profile/comment?userId="+userId+"&currPage="+currPage+"&amount="+amount);
    }//success
 }); //ajax
} //selectClickCurrPagePrev

function selectClickCurrPageNext () {
var amount = 5;
var currPage = $( '#currPageNext' ).val();
$.ajax({
	   type: 'get',
       url: '/profile/comment',
       data:{"currPage":currPage,"amount":5,userId:"${userId}"},
       success: function(data){
           $(".cotents").load("/profile/comment?userId="+userId+"&currPage="+currPage+"&amount="+amount);
   	}//success
}); //ajax
} //selectClickCurrPageNext
</script>