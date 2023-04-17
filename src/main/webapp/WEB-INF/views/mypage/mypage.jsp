<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <c:set var="path" value="${pageContext.request.contextPath}" />
    <!DOCTYPE html>
    <html lang="ko">

    <head>
      <jsp:include page="./head.jsp" />
      
    </head>

    <body>
      <div class="total-wrap">
        <!-- hearder start -->
        <jsp:include page="./header.jsp" />
        
        <!-- hearder end -->

		<input type="hidden" id="userId" value="${userId}" />

        <!-- main start -->
        <section>
          <!-- Contents -->
          
          <!-- 마이페이지 공통:프로필,뱃지,닉네임 -->
          <jsp:include page="./mypageContainer.jsp" />


          <!-- 프로필 아래  네비  -->
          <div id="content-section">
            <ul class="content-header-menu">
              <li class="content-header-menu-item" id="climb">
                <span>등산 일정</span>
              </li>
              <li class="content-header-menu-item" id="info">
                <span>내 정보</span>
              </li>
              <li class="content-header-menu-item" id="mypost">
                <span>내가 쓴 글, 댓글</span>
              </li>
              <li class="content-header-menu-item active" id="mylike">
                <span>나의 좋아요</span>
              </li>
            </ul>
          </div>

          <!-- 내용물  -->
          <div id=module>
          </div>

      </div>
      
      <!-- 로딩 -->
      <div class="loader">
	  </div>
	  <div class="container" style="display:none;">
	  <div>
	
	
      </section>
      <!-- main end -->
      </div>
      <!-- total-wrap end -->
      <a href="#" class="scrollToTop">
        <img src="${path}/resources/svg/top.svg" />
      </a>
      
      <!-- Footer Start -->
      <jsp:include page="./footer.jsp"></jsp:include>
      <!-- Footer End -->

      <script>
      
      //닉네임 ajax로 전송
        var nicknameSend = document.querySelector('.nickname-send');
      
        nicknameSend.addEventListener('click', function () {
          //var urlSearch = new URLSearchParams(location.search);
          //var user_id = urlSearch.get('user_id');
          var userId = document.querySelector("#userId").value;
          const nickname = document.getElementById('nickname').value;
          var nickLength = 0;
          
          var engCheck = /[a-z]/;
          var numCheck = /[0-9]/;
          var specialCheck = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
          var isIsValidateName =/^[a-zA-Zㄱ-힣0-9*_]{2,20}$/;
          
          for(var i=0; i<nickname.length; i++){ //한글은 2, 영문은 1로 치환
              nick = nickname.charAt(i);
              if(escape(nick).length >4){
                 nickLength += 2;
              } else {
                 nickLength += 1;
              }//if-else
           }//for
         //닉네임 필수 입력
           if (nickname == null || nickname == "") {
              alert("닉네임 입력은 필수입니다.");

           //닉네임 빈칸 포함 안됨
           } else if (nickname.search(/\s/) != -1) {
              alert("닉네임은 빈 칸을 포함 할 수 없습니다.");

           //닉네임 한글 1~10자, 영문 및 숫자 2~20자   
           } //else if (nickname.length<2 || nickname.length>20) {
           else if (nickLength<2 || nickLength>20) {
              alert("닉네임은 한글 1~6자, 영문 및 숫자 2~20자 입니다.");

           //닉네임 특수문자 포함 안됨   
           } else if (!(isIsValidateName.test(nickname))) {
     		 alert("닉네임은 _ * 이외의 특수문자를 포함 할 수 없습니다.");
     		 
           //닉네임 중복확인
           } else {

	        	$.ajax({
					async : true,
			        type: 'get',
			        url: '/profile/changeNick',
			        data:{"userId":userId,"nickname":nickname},
			        success: function(data){
			        	$(".nickname").load("/profile/changeNick?userId="+userId + "&nickname=" + nickname);
			        	var nickbox = document.querySelector(".nickname-box");
			        	nickbox.className = "nickname-box";
			 		}//success
			    });//ajax
           }//else
        });//addEventListener
          
      
        
        //프로필 변경시 로딩 화면
        var _showPage = function() {
        var loader = $("div.loader");
        var container = $("div.container");
        loader.css("display","block");
        container.css("display","block");
        };
        
      </script>
    </body>

    </html>