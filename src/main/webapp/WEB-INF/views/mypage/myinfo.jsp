<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">

<head>
   <!--<jsp:include page="./head.jsp" />-->
  <link rel="stylesheet" href="${path}/resources/css/myinfo.css" />
  <script src="${path}/resources/js/myinfo.js" defer></script>
</head>

        <!-- 해당 네비 내용물들 -->
        <form action="/mypage/infoset" method="post" id="setform">
          <!-- 내용물 시작  -->

          <div class="cotents">
            <div class="my-location">
              <h2>
                나의 위치 및 선호도
                <img src="${path}/resources/svg/location-dot-solid.svg" style="width: 20px" alt="" />
              </h2>
              <p>
                <span>나의 위치</span>
                <!--onchange에서 this.value 매개변수로 이용 option의 value값 불러오기-->
                <span class="my-city">
                  <label id="city1">시/도</label>
                  <label id="city2">시/군/구</label>
                </span>
                <span id="my-">
                  <select name="sido1" id="sido1" class="sido" onchange="sidoFirst(this.value)"></select>
                  <select name="gugun1" id="gugun1" class="gugun" onchange="gugunSecond(this.value)"></select></span>
              </p>
            </div>

            <div class="radio-items">
              <p>
                <span>선호도</span>
              </p>

              <div class="radio-items-on">
                <input type="radio" name="preferences" value="시내" id="radios1" class="only-one" /><label
                  for="radios1">시내</label>
                <input type="radio" name="preferences" value="도내" id="radios2" class="only-one" /><label
                  for="radios2">도내</label>
                <input type="radio" name="preferences" value="전국" id="radios3" class="only-one" /><label
                  for="radios3">전국</label>
              </div>
            </div>
            <br />
            <hr />

            <div class="my-taste">
              <h2>
                나의 취향
                <img src="${path}/resources/svg/heart-solid.svg" style="width: 20px" alt="" />
              </h2>
              <div class="my-tastein">
                <div class="my-taste-pick">
                  <input type="radio" name="user-taste-pick" value="가까운 산" id="taste1" class="userPick" />
                  <label for="taste1"><img src="https://picsum.photos/id/690/600/500" alt="img" />&#127794; 가까운 산
                    &#127794;</label>
                </div>
                <div class="my-taste-pick">
                  <input type="radio" name="user-taste-pick" value="어려운 산" id="taste2" class="userPick" />
                  <label for="taste2"><img src="https://picsum.photos/id/694/600/500" alt="img" />&#127794; 어려운 산
                    &#127794;</label>
                </div>
                <div class="my-taste-pick">
                  <input type="radio" name="user-taste-pick" value="인기많은 산" id="taste3" class="userPick" />
                  <label for="taste3"><img src="https://picsum.photos/id/684/600/500" alt="img" />
                  &#127794; 인기많은 산&#127794;</label>
                </div>
                <br />
                <hr />
              </div>
            </div>
            <!-- 위 설정한값을 form으로 한번에 전송하나? 각자 가두데나? -->
            <div class="myinfo">
              <button type="button" id="end">회원탈퇴</button>
              <button type="button" id="upload">변경내용저장</button>
            </div>

            <!-- 회원탈퇴 모달창-->
            <div class="withdraw-box">
              <div class="withdraw-text">
                <h2>탈퇴안내문구</h2>
                <p>
                  탈퇴시 모든 활동내역이 삭제됩니다.<br />탈퇴하시겠습니까?
                </p>
              </div>
              <div class="withdwaw-button">
                <button type="button" class="withdwaw-no">아니요</button>
                <button type="button" class="withdwaw-yes">예</button>
              </div>
              <div>
                <a href="#" class="closebutton3"><ion-icon name="close-outline"></ion-icon></a>
              </div>
            </div>
            <!-- 회원탈퇴2 모달창-->
            <div class="withdraw-box2">
              <div class="withdraw-text2">
                <h2>
                  탈퇴하시려면 아래 입력창에<br />
                  “ 회원탈퇴 ” 라고 입력해주세요.
                </h2>
                <input name="withdraw-input" id="tx" type="text" placeholder="회원탈퇴문구입력창" />
              </div>
              <div class="withdwaw-button">
                <button type="button" class="withdwaw-no2">아니요</button>
                <button type="button" class="withdwaw-yes2">
                  예
                </button>
              </div>
              <div>
                <a href="#" class="closebutton4"><ion-icon name="close-outline"></ion-icon></a>
              </div>
            </div>
            <!-- 회원탈퇴3 모달창-->
            <div class="withdraw-box3">
              <div class="withdraw-text3">
                <h2 id="withdraw3h2"></h2>
              </div>
              <div class="withdwaw-button3">
                <button type="button" class="withdwaw-yes3">확인</button>
                <a href="#" class="closebutton5"><ion-icon name="close-outline"></ion-icon></a>
              </div>
            </div>

            <!-- 프로필 수정  -->
            <div class="myinfo-update">
              <div class="myinfo-upload">
                <p>내 정보를 수정하시겠습니까?</p>
                <input type="reset" id="restart" value="아니오" />
                <input type="button" id="setButton" value="예" />
              </div>
            </div>
          </div>
        </form>

  <script>
  
  
  
  var _showPage = function() {
      var loader = $("div.loader");
      var container = $("div.container");
      loader.css("display","block");
      container.css("display","block");
      };
  
  
    // 나의 취향 submit
  	var setButton = document.querySelector("#setButton");
    var sido1 = document.querySelector("#sido1");
    var gugun1 = document.querySelector("#gugun1");
    var onlyone = document.querySelector(".only-one");
    var userPick = document.querySelector(".userPick");

    setButton.addEventListener('click', function(){
      var form = document.querySelector("#setform");

      var user_id = document.createElement("input");
      user_id.setAttribute("type","hidden");
      user_id.setAttribute("name","user_id");
      user_id.setAttribute("value", "${param.user_id}" );
      form.appendChild(user_id);

      var addressText = sido1.value+ " " + gugun1.value;
      var address = document.createElement("input");
      address.setAttribute("type","hidden");
      address.setAttribute("name","address");
      address.setAttribute("value", addressText );
      form.appendChild(address);

      var radioVal = $('input[name="preferences"]:checked').val();
      var san_range = document.createElement("input");
      san_range.setAttribute("type","hidden");
      san_range.setAttribute("name","san_range");
      san_range.setAttribute("value", radioVal );
      form.appendChild(san_range);

      var userPick = $('input[name="user-taste-pick"]:checked').val();
      var san_taste = document.createElement("input");
      san_taste.setAttribute("type","hidden");
      san_taste.setAttribute("name","san_taste");
      san_taste.setAttribute("value", userPick);
      form.appendChild(san_taste);

      form.submit();
    });
    
    
  
  </script>
  
  
</body>

</html>