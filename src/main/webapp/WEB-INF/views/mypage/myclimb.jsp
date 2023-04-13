<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>

<style>
	#pagination {
	    display: flex;
	    justify-content: center;
	}
	#pagination li {
        float: left;
        margin: 0px 10px;
        height: 30px;
        list-style: none;
        text-align: center;
        line-height: 30px;
        cursor: pointer;
    }
    #pagination li:hover {
        font-weight: bold;
    }
    li.aCurrPage {
        font-weight: bold;
        color: rgb(3, 250, 3);
    }
    li.pCurrPage {
    font-weight: bold;
    color: rgb(3, 250, 3);
    }
    h2 {
		float: none;
    }
</style>
                
</head>

		               
          <div class="cotents">
          
            <div class="content1">
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
                    <td class="t1"> ${YesParty.sanInfoId} </td>
                    <td class="t2">${YesParty.title}</td>
                    <td class="t3">${YesParty.partyDt}</td>
                    <td class="t4">?/${YesParty.partyMax}</td>
                    <td class="t5">
                      <button class="chatting">채팅입장</button>
                    </td>
                  </tr>
           		 </c:forEach>
           		 
                  
                </tbody>
              </table>
              
              <div id="pagination">
                <form action="paginationForm" id="paginationForm">
                
                
                  <ul>
                    <%-- PREV 표시 c:if --%>
                      <c:if test="${availPage.prev}" >
                              <li class="prev">
                              <span class="availPageNum" id="availPagePrev" data-temp = " ${availPage.acri.setACurrPage(availPage.startPage-1)}" onclick="selectClickAvailableCurrPagePrev()">PREV</span>
                             <input type="hidden" id="availcurrPagePrev" value="${availPage.startPage-1}">
                             
                              </li>
                      </c:if>
                  
                    <%-- 페이지번호목록  --%>
                      <c:forEach var="pageNum" begin="${availPage.startPage}" end="${availPage.endPage}">   <%--  begin end는 마치 between연산자와 비슷 --%>
                              <li class="${param.aCurrPage eq pageNum? 'aCurrPage' : ''}">
                              <span class="availPageNum" id="availPageNum" data-temp = " ${availPage.acri.setACurrPage(pageNum)}" onclick="selectClickAvailableCurrPage()">${pageNum}</span>
                              <input type="hidden" id="availcurrPageNum" value="">
                              </li>
                      </c:forEach>
                  
                    <%-- NEXT 표시 c:if  --%>
                      <c:if test="${availPage.next}" >
                          <!-- next 버튼 클릭시, 다음 페이지로 이동 -->
                              <li class= "next">
                                <span class="availPageNum" id="availPageNext" data-temp = " ${availPage.acri.setACurrPage(availPage.endPage+1)}" onclick="selectClickAvailableCurrPageNext()">NEXT</span>
                              <input type="hidden" id="availcurrPageNext" value="${availPage.endPage+1}">
                              </li>
                      </c:if>
                  </ul>
                  
              </div>
              
            </div>

            <div class="content2">
              <h2>지난 등산 일정 ⛰</h2>
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
                 <c:forEach items="${pastParty}" var="NoParty">
                  <tr>
                    <td class="t1"> ${NoParty.sanInfoId}</td>
                    <td class="t2">${NoParty.title}</td>
                    <td class="t3">${NoParty.partyDt}</td>
                    <td class="t4">?/${NoParty.partyMax}</td>
                    <td class="t5">
                      <button class="chatting-off">채팅입장</button>
                    </td>
                  </tr>
                  </c:forEach>
                </tbody>
              </table>


              <div id="pagination">
                
                  <ul>
                    <%-- PREV 표시 c:if --%>
                      <c:if test="${pastPage.prev}" >
                              <li class="prev">
                              <span class="pastPageNum" id="pastPagePrev" data-temp = " ${pastPage.pcri.setPCurrPage(pastPage.startPage-1)}" onclick="selectClickPastCurrPagePrev()">PREV</span>
                              <input type="hidden" id="pastcurrPagePrev" value="${pastPage.startPage-1}">
                              </li>
                      </c:if>
                  
                    <%-- 페이지번호목록  --%>
                      <c:forEach var="pageNum" begin="${pastPage.startPage}" end="${pastPage.endPage}"> 
                              <li class="${param.pCurrPage eq pageNum? 'pCurrPage' : ''}">
                              <span class="pastPageNum" id="pastPageNum" data-temp = " ${pastPage.pcri.setPCurrPage(pageNum)}" onclick="selectClickPastCurrPage()">${pageNum}</span>
                              </li>
                              <input type="hidden" id="pastCurrPage" value="">
                      </c:forEach>
                  
                    <%-- NEXT 표시 c:if  --%>
                      <c:if test="${pastPage.next}" >
                          <!-- next 버튼 클릭시, 다음 페이지로 이동 -->
                              <li class= "next">
                                <span class="pastPageNum" id="pastPageNext" data-temp = " ${pastPage.pcri.setPCurrPage(pastPage.endPage+1)}"  onclick="selectClickPastCurrPageNext()">NEXT</span>
                              <input type="hidden" id="pastcurrPageNext" value="${pastPage.endPage+1}">
                              </li>
                      </c:if>
                  </ul>
                  
                </form>
              </div>

            </div>
          </div>
          
          <script>

          function selectClickAvailableCurrPage() {
        	  var availPageNumList = document.querySelectorAll('#availPageNum');
        	  console.log("********",event.target.innerText);
              var aCurrPage = event.target.innerText;
              var pCurrPage = "${__pCurrPage__}";
              $.ajax({
                    type: 'get',
                    url: '/mypage/myclimb',
                    data:{"aCurrPage":aCurrPage,"aAmount":5,"pCurrPage":pCurrPage,"pAmount":5,"user_id":user_id},
                    success: function(data){
                    	console.log("amount?",aAmount);
                        $("#module").load("/mypage/myclimb?aCurrPage="+aCurrPage + "&aAmount="+aAmount+ "&pCurrPage="+pCurrPage + "&pAmount="+pAmount+"&user_id="+ user_id);
                    }//success
             	 });//ajax
          }
          
          function selectClickPastCurrPage() {
        	  var pastCurrPageList = document.querySelectorAll('#pastCurrPage');
        	  console.log("********",event.target.innerText);
              var aCurrPage = "${__aCurrPage__}";
              var pCurrPage = event.target.innerText;
              $.ajax({
                    type: 'get',
                    url: '/mypage/myclimb',
                    data:{"aCurrPage":aCurrPage,"aAmount":5,"pCurrPage":pCurrPage,"pAmount":5,"user_id":user_id},
                    success: function(data){
                        $("#module").load("/mypage/myclimb?aCurrPage="+aCurrPage + "&aAmount="+aAmount+ "&pCurrPage="+pCurrPage + "&pAmount="+pAmount+"&user_id="+ user_id);
                    }//success
            	 });//ajax
          }

         

          function selectClickAvailableCurrPagePrev () {
        	 console.log("**************");
        	 
            var aCurrPage = $( '#availcurrPagePrev' ).val();
            var pCurrPage = "${__pCurrPage__}";
            console.log(aCurrPage,pCurrPage);
            $.ajax({
                    type: 'get',
                    url: '/mypage/myclimb',
                    data:{"aCurrPage":aCurrPage,"aAmount":5,"pCurrPage":pCurrPage,"pAmount":5,"user_id":user_id},
                    success: function(data){
                        $("#module").load("/mypage/myclimb?aCurrPage="+aCurrPage + "&aAmount="+aAmount+ "&pCurrPage="+pCurrPage + "&pAmount="+pAmount+"&user_id="+ user_id);
                    }
              });
          }

          function selectClickAvailableCurrPageNext () {
	              var aCurrPage = $( '#availcurrPageNext' ).val();
	              var pCurrPage = "${__pCurrPage__}";
	            $.ajax({
	                    type: 'get',
	                    url: '/mypage/myclimb',
	                    data:{"aCurrPage":aCurrPage,"aAmount":5,"pCurrPage":pCurrPage,"pAmount":5,"user_id":user_id},
	                    success: function(data){
	                        $("#module").load("/mypage/myclimb?aCurrPage="+aCurrPage + "&aAmount="+aAmount+ "&pCurrPage="+pCurrPage + "&pAmount="+pAmount+"&user_id="+ user_id);
	                    }
	              });
         }
	         			
	         			
         function selectClickPastCurrPagePrev () {
			  	 console.log("**************");
			  	 
			      var aCurrPage = "${__aCurrPage__}";
			      var pCurrPage = $( '#pastcurrPagePrev' ).val();
			      console.log(aCurrPage,pCurrPage);
			      $.ajax({
			              type: 'get',
			              url: '/mypage/myclimb',
			              data:{"aCurrPage":aCurrPage,"aAmount":5,"pCurrPage":pCurrPage,"pAmount":5,"user_id":user_id},
			              success: function(data){
			                  $("#module").load("/mypage/myclimb?aCurrPage="+aCurrPage + "&aAmount="+aAmount+ "&pCurrPage="+pCurrPage + "&pAmount="+pAmount+"&user_id="+ user_id);
			              }
			        });
    	 }
	         			
	         			
	         			
         function selectClickPastCurrPageNext () {
	          var aCurrPage = "${__aCurrPage__}";
	          var pCurrPage = $( '#pastcurrPageNext' ).val();
	        $.ajax({
	                type: 'get',
	                url: '/mypage/myclimb',
	                data:{"aCurrPage":aCurrPage,"aAmount":5,"pCurrPage":pCurrPage,"pAmount":5,"user_id":user_id},
	                success: function(data){
	                    $("#module").load("/mypage/myclimb?aCurrPage="+aCurrPage + "&aAmount="+aAmount+ "&pCurrPage="+pCurrPage + "&pAmount="+pAmount+"&user_id="+ user_id);
	                }
	          });
 		 }
 
          </script>

          

          
