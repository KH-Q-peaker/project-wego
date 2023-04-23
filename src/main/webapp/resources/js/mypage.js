
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
	  
	  if(!(document.getElementById('document_file').files[0])){
		fileText.innerHTML="여기로 이미지를 드래그하거나 <br> 파일을 업로드 하세요. (최대 20MB)";
	} else {
	
      const selectedFile = document.getElementById('document_file').files[0];
      console.log("fileName:{}",event.target.files[0].name)
      selectedFile.type = "file";
      selectedFile.accept = ".jpg, .jpeg, .png";
    
    
    	// 파일형식 체크
	    var check2 =isRightFile(event);
	    console.log("check:{}", check);
	    
	    // 업로드 파일 용량 체크
	    var check = isFileMaxSize(event);
	
	    // 위 조건을 모두 통과할 경우
	    isReadyUpload = true;
	    if( (check && check2)){
          console.log("check : fileName:{}",event.target.files[0].name);
          var fileName = event.target.files[0].name;

          if (fileName) {
            fileText.innerHTML=`<p>${fileName}</p>`;
          }
          
          var formData = new FormData();
          imgPath = document.getElementById('document_file').files[0];
          console.log("imgPath:" +imgPath);
		  formData.append("part",imgPath);
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
			     }
				})
		    });//ajax
          }//click function
		 }	//if-else
		});//change function
  selector(".add-profile-image").style.display = "block";
});//addEventListener


// 이미지 추가 취소 버튼 클릭 이벤트
selector(".add-profile-image .cancel").addEventListener("click", () => {
  selector(".add-profile-image").style.display = "none";
  imgPath=null;
});

// 업로드 파일 용량 체크
const isFileMaxSize = (event) => {
  console.log("isFileMaxSize() invoked");
  if (event.target.files[0].size < 20971520) {
    return true;
  } else {
    fileText.innerHTML=`
   <p>최대 업로드 용량은 20MB입니다.<br>
    현재 파일의 용량은 ${Math.floor((file[0].size / 1048576) * 10) / 10}입니다.
    </p>`;
    console.log("event.target.files[0].size:{}",event.target.files[0].size);
    isReadyUpload = false;
    return false;
  }
};

// 파일형식 체크
const isRightFile = (event) => {
  console.log("isRightFile() invoked");
  console.log(event.target.files[0].type);
  if (
    event.target.files[0].type == "image/jpeg" ||
    event.target.files[0].type == "image/png" ||
    event.target.files[0].type == "image/jpg"
  ) {
	return true;
    
  } else {
   	fileText.innerHTML=`
    <p>업로드 가능한 파일 형식은<br>
    .jpg, .jpeg, .png입니다.
    </p>`;
    console.log("event.target.files[0].type:{}",event.target.files[0].type);
    isReadyUpload = false;
    return false;
  }
};


var userId = document.querySelector("#userId").value;

//메인 페이지 로드
window.onload = function(){
	console.log("loadload");
		
		$.ajax({
        type: 'get',
        url: '/profile/partyList?userId='+userId,
        success: function(data){
            $("#content1").load("/profile/partyList?userId="+ userId);
	            
        }
	   });
	   $.ajax({
		        type: 'get',
		        url: '/profile/pastPartyList?userId='+userId,
		        success: function(data){
				console.log("111loadload");
		            $("#content2").load("/profile/pastPartyList?userId="+ userId);
		       	 }
	   	});
	   	document.getElementById("module").style.opacity = "0";
		$("#module").animate({
			opacity : 1
		});
};




$('#climb').click(function(){
		currPage = 1;
		amount = 10;
		var module1 = document.querySelector("#module");
		module1.innerHTML = '<div class="cotents"> \
	    <div class="content1" id="content1"> </div>\
	    <div class="content2" id="content2"> </div></div>';
		$.ajax({
        type: 'get',
        url: '/profile/partyList?userId='+userId,
        success: function(data){
            $("#content1").load("/profile/partyList?userId="+ userId);
        }
	   });
	   $.ajax({
	        type: 'get',
	        url: '/profile/pastPartyList?userId='+userId,
	        success: function(data){
			console.log("111loadload");
	            $("#content2").load("/profile/pastPartyList?userId="+ userId);
	       	 	}
	   	});
	   	document.getElementById("module").style.opacity = "0";
		$("#module").animate({
			opacity : 1
		});
});

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


$('#mypost').click(function(){
		currPage = 1;
		amount = 10;
		var module1 = document.querySelector("#module");
		module1.innerHTML = '<div class="cotents"> \
	    <div class="content1" id="content1"> </div>\
	    <div class="content2" id="content2"> </div></div>';
	    
		var module1 = document.querySelector("#module");
		module1.innerHTML = '<div class="cotents"> \
	    <div class="content1" id="content1"> </div>\
	    <div class="content2" id="content2"> </div></div>';
		$.ajax({
        type: 'get',
        url: '/profile/mypost?userId='+userId,
        success: function(data){
		console.log("mypost loadload");
            $("#content1").load("/profile/mypost?userId="+ userId);
        }
	   });
	   $.ajax({
	        type: 'get',
	        url: '/profile/mycomment?userId='+userId,
	        success: function(data){
			console.log("mycomment loadload");
	            $("#content2").load("/profile/mycomment?userId="+ userId);
	       	 	}
	   	});
	   	document.getElementById("module").style.opacity = "0";
		$("#module").animate({
			opacity : 1
		});
});

$('#mylike').click(function(){
	var module1 = document.querySelector("#module");
		module1.innerHTML = '<div class="my-like cotents"> \
	    <div class="content1" id="content1"> </div>\
	    <div class="content2" id="content2"> </div></div>';
	$.ajax({
		async : true,
        type: 'get',
        url: '/profile/mylike',
        success: function(data){
			document.getElementById("module").style.opacity = "0";
			$("#module").animate({
				opacity : 1
			});
        	$(".cotents").load("/profile/mylike");
 		}
    });
});




