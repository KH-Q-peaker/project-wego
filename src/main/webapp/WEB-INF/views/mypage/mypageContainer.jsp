<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
    <div class="mypage-container">

            <!-- form 필수값 검증 후 값이 없는 경우 알림 -->
            
            <!-- <form action="#" method="post"> -->
            <div class="alert-window">
              <p>
                닉네임은 <br />
                필수사항 입니다.
              </p>
              <button type="button">확인</button>
            </div>

            <!--  닉네임 변경  -->
            <div class="nickname-box">
              <div class="nick-header">
                <span>사용할 닉네임을 입력해주세요</span>
              </div>

              <div class="nickname-input">
                <input id="nickname" type="text" name="nickname" placeholder="등산쟁이" value="${vo.nickname}" oninput="testtest()">
                <span class="ion"><ion-icon name="checkmark-circle-outline" id="ok-sign"></ion-icon>
                  <ion-icon name="close-circle-outline" id="no-sign"></ion-icon></span>
              </div>
              <div class="bottom">
                <span id="isRightText"></span>
              </div>
              <button type="button" class="nickname-send">변경</button>
              <div>
                <a href="#" class="closebutton2"><ion-icon name="close-outline"></ion-icon></a>
              </div>
            </div>
            <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
            <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

            <!--</form> -->
            <!--  프로필 메인  -->
            <form action="/profile/image" id="imageForm" method="post" enctype="multipart/form-data">
              <div class="profile">
                <div class="profile-image">
                  <img id="profileImage" src="/img${UserPicName}" alt="">
                </div>

                <div class="add-profile-image">
                  <div class="top">
                    <div class="folder-icon"></div>
                    <span>Upload a photo</span>
                    <span class="cancel" type="reset"></span>
                  </div>
                  <div class="drag-and-drop form-group file-upload" required="required">
                    <div class="picture"></div>

                    <div class="form-control" data-message="">
                      <input type="file" name="part" id="document_file" accept=".jpg, .jpeg, .png">
                      <p id="fileText">

                      </p>
                    </div>

                  </div>
                  <button type="button" id="partButton" >변경</button>
                </div>
            </form>

            
            <div class="main-wrap">
              <div class="profile-image-button">
                <img src="${path}/resources/img/profileset.png" alt="프로필사진변경" />
              </div>

              <!--  사진 변경 옆라인 -->
              <div class="name-badge-setting">
                <div class="div-name-setting">
                  <span class="nickname">${vo.nickname}</span>
                  <a class="name-setting" href="#"><img src="${path}/resources/img/nickname.png" alt="" /></a>
                </div>
                <!--뱃지모음라인===== -->
                <div class="badge-collection">
                  <div class="badge-header">
                    <span>Badge Collection</span>
                    <span>
                      <a class="badge-setting" href=""><img src="${path}/resources/img/badgeset.png" alt="" /></a>
                    </span>
                  </div>

                  <!--적용 -->
                  <div class="badge-item1" id="1003">
                    <div class="sanBadge"></div>
                  </div>
                  <div class="badge-item2" id="1">
                    <div class="sanBadge"></div>
                  </div>
                  <div class="badge-item3" id="4">
                    <div class="sanBadge"></div>
                  </div>
                  <div class="badge-item4" id="5">
                    <div class="sanBadge"></div>
                  </div>
                  <div class="badge-item5" id="93">
                    <div class="sanBadge"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          
          <script>
          
          var nicknameSend = document.querySelector('.nickname-send');
          var nicknameInput = document.querySelector('#nickname');
          var original_nickname = document.getElementById('nickname').value;
      	  var isRightText = document.querySelector("#isRightText");
      	  var isRightNickname = false;
          
          function testtest() {
        	  console.log("test");
        	  var nickname = document.getElementById('nickname').value;
        	  $.ajax({
			        url: '/profile/nicknameCheck',
			        data:{"nickname":nickname},
			        success: function(data){
			        	console.log("success function({}) invoked", data);
			        	nicknameCheck(nickname);
			        	if(isRightNickname &&(nickname==original_nickname)) {
			        		isRightText.innerText = "같은 닉네임입니다.";
			        		noSignByRed();
			        		isRightNickname = false;
			        	} else if(isRightNickname && (data > 0)){
			        		isRightText.innerText = "같은 닉네임이 있습니다.";
			        		noSignByRed();
			        		isRightNickname = false;
			        	} else if(isRightNickname){
			        		isRightText.innerText = "사용 가능한 닉네임입니다.";
			        		okSignByGreen();
			        		isRightNickname = true;
			        	}//if-else
			        }//success
			    });//ajax
          }
          
          function nicknameCheck(nickname) {	    
        	  console.log("nicknameCheck({}) invoked",nickname);
        	  var nickname = document.getElementById('nickname').value;
              var nickLength = 0;
              
              var engCheck = /[a-z]/;
              var numCheck = /[0-9]/;
              var isIsValidateName =/^[a-zA-Zㄱ-힣0-9*_]{2,20}$/;
              
              for(var i=0; i<nickname.length; i++){ //한글은 2, 영문은 1로 치환
                  nick = nickname.charAt(i);
                  if(escape(nick).length >4){
                     nickLength += 3;
                  } else {
                     nickLength += 1;
                  }//if-else
               }//for
               
             //닉네임 필수 입력
               if (nickname == null || nickname == "") {
            	   isRightText.innerText = "닉네임 입력은 필수입니다.";
            	   noSignByRed();
            	   isRightNickname = false;
               //닉네임 빈칸 포함 안됨
               } else if (nickname.search(/\s/) != -1) {
            	   isRightText.innerText = "닉네임은 빈 칸을 포함 할 수 없습니다.";
            	   noSignByRed();
            	   isRightNickname = false;
               //닉네임 한글 1~10자, 영문 및 숫자 2~20자   
               } else if (nickLength<2 || nickLength>20) {
            	   isRightText.innerText = "닉네임은 한글 1~6자, 영문 및 숫자 2~20자 입니다.";
            	   noSignByRed();
            	   isRightNickname = false;
               //닉네임 특수문자 포함 안됨   
               } else if (!(isIsValidateName.test(nickname))) {
            	   isRightText.innerText = "닉네임은 _ * 이외의 특수문자를 포함 할 수 없습니다.";
            	   noSignByRed();
            	   isRightNickname = false;
               } else {
            	   isRightText.innerText = "사용 가능한 닉네임입니다.";
            	   isRightNickname = true;
            	   okSignByGreen();
               }
            	   
          }//nicknameCheck
			    

          nicknameSend.addEventListener('click', function () {
        	  if(isRightNickname) {
        		var nickname = document.getElementById('nickname').value;
	        	$.ajax({
			        url: '/profile/changeNickname',
			        data:{"nickname":nickname},
			        success: function(data){
			        	console.log("내가 바꾼 닉네임은?"+ data);
			        	
			        	if(data != "") {
			        	var nicknameText = document.querySelector(".nickname");
			        	nicknameText.innerText = data;
			        	var nickbox = document.querySelector(".nickname-box");
				        nickbox.className = "nickname-box";
				        
				        original_nickname = nickname;
			        	}//if
			        }//success
			    });//ajax
          	   }
             });
          
          function okSignByGreen () {
        	  document.querySelector("input#nickname:focus").style.border = "3px solid #4ebc7a";
        	  document.querySelector("#isRightText").style.color = "#4ebc7a";
        	  document.querySelector("#ok-sign").style.display = "inline";
        	  document.querySelector("#ok-sign").style.color = "#4ebc7a";
        	  document.querySelector("#no-sign").style.display = "none";
        	  
          }
          function noSignByRed () {
        	  document.querySelector("input#nickname:focus").style.border = "3px solid #f14141";
        	  document.querySelector("#isRightText").style.color = "#f14141";
        	  document.querySelector("#no-sign").style.display = "inline";
        	  document.querySelector("#no-sign").style.color = "#f14141";
        	  document.querySelector("#ok-sign").style.display = "none";
          }

          
          </script>