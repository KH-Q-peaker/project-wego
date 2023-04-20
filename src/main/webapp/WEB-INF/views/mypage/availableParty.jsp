<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="content1">

		<h2>신청한 등산 일정 🚩</h2>
              
              <table class="middle">
                <thead>
                  <tr style="align-items: center">
                    <th class="t1" width="70">말머리</th>
                    <th class="t2" width="300">제목</th>
                    <th class="t3" width="120">등반날짜</th>
                    <th class="t4" width="120">참여인원</th>
                    <th class="t5" width="70">채팅방</th>
                  </tr>
                </thead>
                <tbody>
                
                 <c:forEach items="${availableParty}" var="YesParty">
                  <tr>
                    <td class="t1"> ${YesParty.sanName} </td>
                    <td class="t2">${YesParty.title}</td>
                    <td class="t3">${YesParty.partyDt}</td>
                    <td class="t4">${YesParty.partyCount}/${YesParty.partyMax}</td>
                    <td class="t5">
                      <button class="chatting">채팅입장</button>
                    </td>
                  </tr>
           		 </c:forEach>
           		 
                  
                </tbody>
              </table>
              
              <div id="pagination">
                <form action="paginationForm" id="paginationForm">
                
                <input type="hidden" id="userId" value="${userId}" />
                
                  <ul class="paginUl">
                    <%-- PREV 표시 c:if --%>
                      <c:if test="${availPage.prev}" >
                              <li class="prev">
                              <span class="availPageNum" id="availPagePrev" data-temp = " ${availPage.cri.setCurrPage(availPage.startPage-1)}" onclick="selectClickAvailableCurrPagePrev()">PREV</span>
                             <input type="hidden" id="availcurrPagePrev" value="${availPage.startPage-1}">
                             
                              </li>
                      </c:if>
                  
                    <%-- 페이지번호목록  --%>
                      <c:forEach var="pageNum" begin="${availPage.startPage}" end="${availPage.endPage}">   <%--  begin end는 마치 between연산자와 비슷 --%>
                              <li class="${param.currPage eq pageNum? 'currPage' : ''} availablePartyPage${pageNum}">
                              <span class="availPageNum" id="availPageNum" data-temp = " ${availPage.cri.setCurrPage(pageNum)}" onclick="selectClickAvailableCurrPage()">${pageNum}</span>
                              <input type="hidden" id="availcurrPageNum" value="">
                              </li>
                      </c:forEach>
                  
                    <%-- NEXT 표시 c:if  --%>
                      <c:if test="${availPage.next}" >
                          <!-- next 버튼 클릭시, 다음 페이지로 이동 -->
                              <li class= "next">
                                <span class="availPageNum" id="availPageNext" data-temp = " ${availPage.cri.setCurrPage(availPage.endPage+1)}" onclick="selectClickAvailableCurrPageNext()">NEXT</span>
                              <input type="hidden" id="availcurrPageNext" value="${availPage.endPage+1}">
                              </li>
                      </c:if>
                  </ul>
                  
              </div>
              
  		  </div>          
              
          <script>
          
          var aCurrPage = "${__aCurrPage__}";
	      if(aCurrPage == 1) {
	       	$('.availablePartyPage1').addClass("currPage");
	      }

          var userId = document.querySelector("#userId").value;
          var amount = 10;
          
          function selectClickAvailableCurrPage() {
        	  console.log("********",event.target.innerText);
              var currPage = event.target.innerText;
              $.ajax({
                    type: 'get',
                    url: '/profile/partyList',
                    data:{"acri.currPage":currPage,"acri.amount":amount,"userId":userId},
                    success: function(data){
                        $("#content1").load("/profile/partyList?currPage="+currPage + "&amount="+amount+"&userId="+ userId);
                    }
             	 });//ajax
          }//selectClickAvailableCurrPage
          
          function selectClickAvailableCurrPagePrev () {
            var currPage = $( '#availcurrPagePrev' ).val();
            $.ajax({
                type: 'get',
                url: '/profile/partyList',
                data:{"acri.currPage":currPage,"acri.amount":amount,"userId":userId},
                success: function(data){
                    $("#content1").load("/profile/partyList?currPage="+currPage + "&amount="+amount+"&userId="+ userId);
                }
         	 });//ajax
          }

          function selectClickAvailableCurrPageNext () {
	              var currPage = $( '#availcurrPageNext' ).val();
	              $.ajax({
	                  type: 'get',
	                  url: '/profile/partyList',
	                  data:{"acri.currPage":currPage,"acri.amount":amount,"userId":userId},
	                  success: function(data){
	                      $("#content1").load("/profile/partyList?currPage="+currPage + "&amount="+amount+"&userId="+ userId);
	                  }
	           	 });//ajax
         	}
          </script>
              