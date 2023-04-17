// const mountains = [];

let imgPath; // 업로드 이미지 임시 저장 변수
let isReadyUpload = false; // 파일 업로드 가능여부

//닉네임 변경
var nickbox = document.querySelector(".nickname-box");
var nameset = document.querySelector(".name-setting");
nameset.onclick = function () {
  nickbox.classList.toggle("active");
};
var closebutton2 = document.querySelector(".closebutton2");
closebutton2.onclick = function () {
  nickbox.className = "nickname-box";
};

//파일첨부 text 표시 관련
var document_file = document.querySelector("#document_file");
var fileText = document.querySelector("#fileText");
var profileimagebutton = document.querySelector(".profile-image-button");
profileimagebutton.addEventListener('click', function(){
  fileText.innerHTML="여기로 이미지를 드래그하거나 <br> 파일을 업로드 하세요. (최대 20MB)";
  $('#document_file').on('change', function(e){
    fileText.innerHTML="";
  });
});


// 이미지 추가 버튼 클릭 이벤트
selector(".profile-image-button").addEventListener("click", () => {
  $('#document_file').on('change', function(event){
      const selectedFile = document.getElementById('document_file').files[0];
      console.log("fileName:{}",event.target.files[0].name)
      selectedFile.type = "file";
      selectedFile.accept = ".jpg, .jpeg, .png";
    
        // 업로드 파일 용량 체크
        var check = isFileMaxSize(event);
    
        // 파일형식 체크
        var check2 =isRightFile(event);
        console.log("check:{}", check);

        // 위 조건을 모두 통과할 경우
        isReadyUpload = true;
        if(! (check && check2)){
          console.log("check : fileName:{}",event.target.files[0].name)
          processFile(event.target.files[0]);
          var fileName = event.target.files[0].name;

          if (fileName) {
            $(event.target).parent().attr('data-message', fileName);
          }
        }
        
       
          var formData = new FormData();
		  formData.append("part",document.getElementById('document_file').files[0]);
		  formData.append("userId",document.getElementById('userId').value);
		   $('#partButton').on('click', function(event){
		  $.ajax({
		    url: '/profile/image',
		      processData: false,
		      contentType: false,
		      data: formData,
		      type: 'POST',
		      success: function(result){
				//로딩 함수
				_showPage();
				//이미지가 로딩될때까지 기다림
    			
    			setTimeout(function(){
    			//전송 성공시, 메인페이지로 이동
				const userId = document.getElementById('userId').value;
			     location.href = '/profile/' + userId;
				}, 1000);
			     //});
		      }
		  });
		});
        
        
        
  });
  selector(".add-profile-image").style.display = "block";
});



// 이미지 추가 취소 버튼 클릭 이벤트
selector(".add-profile-image .cancel").addEventListener("click", () => {
  selector(".add-profile-image").style.display = "none";
  window.location.reload();
});


const processFile = (file) => {
  const reader = new FileReader();

  reader.readAsDataURL(file);

  reader.onload = function () {
    selector(".drag-and-drop").innerHTML = `<p>${file.name}</p>`;

    imgPath = `<img name="ImagePath" src="${reader.result}" value="${reader.result}" alt="프로필 이미지"></img>
    <input type="hidden" name="imagePath" id="imagePath" value="${reader.result}">`;
  };
};

// 업로드 파일 용량 체크
const isFileMaxSize = (event) => {
  console.log("isFileMaxSize() invoked");
  if (event.target.files[0].size > 20971520) {
    selector(".drag-and-drop").innerHTML = 
    "<p>최대 업로드 용량은 20MB입니다.<br>"
    +"현재 파일의 용량은 ${Math.floor((event.target.files[0].size / 1048576) * 10) / 10}입니다."
    +"</p>";
    console.log("event.target.files[0].size:{}",event.target.files[0].size);
    isReadyUpload = false;
    return true;
  } else {
    return false;
  }
};

// 파일형식 체크
const isRightFile = (event) => {
  console.log("isRightFile() invoked");
  if (
    event.target.files[0].type !== "image/jpeg" &&
    event.target.files[0].type !== "image/png" &&
    event.target.files[0].type !== "image/jpg"
  ) {
    selector(".drag-and-drop").innerHTML = `
    <p>업로드 가능한 파일 형식은<br>
    .jpg, .jpeg, .png입니다.
    </p>`;
    console.log("event.target.files[0].type:{}",event.target.files[0].type);
    isReadyUpload = false;
    return true;
  } else {
    return false;
  }
};


var userId = document.querySelector("#userId").value;

//메인 페이지 로드
window.onload = function(){
	console.log("loadload");
		currPage = 1;
		amount = 10;
		
		$.ajax({
        type: 'get',
        url: '/profile/partyList',
        data:{"acri.currPage":currPage,"acri.amount":amount,"userId":userId},
        success: function(data){
            $("#content1").load("/profile/partyList?currPage="+currPage + "&amount="+amount+"&userId="+ userId);
	            
        }
	   });
	   $.ajax({
		        type: 'get',
		        url: '/profile/pastPartyList',
		        data:{"pcri.currPage":currPage,"pcri.amount":amount,"userId":userId},
		        success: function(data){
				console.log("111loadload");
		            $("#content2").load("/profile/pastPartyList?currPage="+currPage + "&amount="+amount+"&userId="+ userId);
		       	 }
	   	});
	   	document.getElementById("module").style.opacity = "0";
		$("#module").animate({
			opacity : 1
		});
};




$('#climb').click(function(){
//function showPartyList() {	
		currPage = 1;
		amount = 10;
		var module1 = document.querySelector("#module");
		module1.innerHTML = '<div class="cotents"> \
	    <div class="content1" id="content1"> </div>\
	    <div class="content2" id="content2"> </div></div>';
		$.ajax({
        type: 'get',
        url: '/profile/partyList',
        data:{"acri.currPage":currPage,"acri.amount":amount,"userId":userId},
        success: function(data){
            $("#content1").load("/profile/partyList?currPage="+currPage + "&amount="+amount+"&userId="+ userId);
        }
	   });
	   $.ajax({
	        type: 'get',
	        url: '/profile/pastPartyList',
	        data:{"pcri.currPage":currPage,"pcri.amount":amount,"userId":userId},
	        success: function(data){
			console.log("111loadload");
	            $("#content2").load("/profile/pastPartyList?currPage="+currPage + "&amount="+amount+"&userId="+ userId);
	       	 	}
	   	});
	   	document.getElementById("module").style.opacity = "0";
		$("#module").animate({
			opacity : 1
		});
});
//};
//function partyList() {
//	showPartyList();
//}

$('#info').click(function(){
	var module1 = document.querySelector("#module");
		module1.innerHTML = '<div class="cotents"> \
	    <div class="content1" id="content1"> </div>\
	    <div class="content2" id="content2"> </div></div>';
	$.ajax({
		async : true,
        type: 'get',
        url: '/profile/info',
        data:{"userId":userId},
        success: function(data){
					document.getElementById("module").style.opacity = "0";
					$("#module").animate({
						opacity : 1
					});
        	$("#module").load("/profile/info?userId=" + userId);
 		}
    });
});



