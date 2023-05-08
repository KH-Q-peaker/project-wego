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

// Get the navigation menu items
const menuItems = document.querySelectorAll(".content-header-menu-item");
menuItems.forEach((item, index) => {
	// index 매개변수 추가
	item.addEventListener("click", () => {
		// Remove the "item-point" id from all menu items
		menuItems.forEach((item) => item.classList.remove("item-point"));
		// Add the "item-point" id to the clicked menu item
		item.classList.add("item-point");
	});
});


//프로필 변경시 로딩 화면
var _showPage = function () {
	console.log("_showPage invoked");
	var loader = $("div.loader");
	var container = $("div.container");
	loader.css("display", "block");
	container.css("display", "block");
};

//파일첨부 text 표시 관련
var document_file = document.querySelector("#document_file");
var fileText = document.querySelector("#fileText");
var profileimagebutton = document.querySelector(".profile-image-button");
profileimagebutton.addEventListener('click', function () {
	fileText.innerHTML = "여기로 이미지를 드래그하거나 <br> 파일을 업로드 하세요. (최대 20MB)";
	$('#document_file').on('change', function (e) {
		fileText.innerHTML = "";
	});
});


// 이미지 추가 버튼 클릭 이벤트
selector(".profile-image-button").addEventListener("click", () => {
	$('#document_file').on('change', function (event) {

		//imgPath=null;
		//console.log("parseInt($fileUpload.get(0).files.length): "+ parseInt($fileUpload.get(0).files.length));

		if (!(document.getElementById('document_file').files[0])) {
			fileText.innerHTML = "여기로 이미지를 드래그하거나 <br> 파일을 업로드 하세요. (최대 20MB)";
		} else {

			const selectedFile = document.getElementById('document_file').files[0];
			console.log("fileName:{}", event.target.files[0].name);
			selectedFile.type = "file";
			selectedFile.accept = ".jpg, .jpeg, .png";


			// 파일형식 체크
			var check2 = isRightFile(event);
			console.log("check:{}", check);

			// 업로드 파일 용량 체크
			var check = isFileMaxSize(event);

			// 위 조건을 모두 통과할 경우
			isReadyUpload = true;
			if ((check && check2)) {
				console.log("check : fileName:{}", event.target.files[0].name);
				var fileName = event.target.files[0].name;

				if (fileName) {
					fileText.innerHTML = `<p>${fileName}</p>`;
				}
				$('#partButton').on('click', function (event) {
					setTimeout(function () {
						_showPage();
						var formData = document.getElementById('imageForm');
						formData.submit();
					}, 1000);
				})
			}//click function
		}	//if-else
	});//change function
	selector(".add-profile-image").style.display = "block";
});//addEventListener


// 이미지 추가 취소 버튼 클릭 이벤트
selector(".add-profile-image .cancel").addEventListener("click", () => {
	selector(".add-profile-image").style.display = "none";
	imgPath = null;
});

// 업로드 파일 용량 체크
const isFileMaxSize = (event) => {
	console.log("isFileMaxSize() invoked");
	if (event.target.files[0].size < 20971520) {
		return true;
	} else {
		fileText.innerHTML = `
   <p>최대 업로드 용량은 20MB입니다.<br>
    현재 파일의 용량은 ${Math.floor((file[0].size / 1048576) * 10) / 10}입니다.
    </p>`;
		console.log("event.target.files[0].size:{}", event.target.files[0].size);
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
		fileText.innerHTML = `
    <p>업로드 가능한 파일 형식은<br>
    .jpg, .jpeg, .png입니다.
    </p>`;
		console.log("event.target.files[0].type:{}", event.target.files[0].type);
		isReadyUpload = false;
		return false;
	}
};


//메인 페이지 로드
window.onload = function () {

	$.ajax({
		type: 'get',
		url: '/profile/partyList',
		success: function (data) {
			$("#content1").load("/profile/partyList");
		}
	});

	$.ajax({
		type: 'get',
		url: '/profile/pastPartyList',
		success: function (data) {
			$("#content2").load("/profile/pastPartyList");
		}
	});
	document.getElementById("module").style.opacity = "0";
	$("#module").animate({
		opacity: 1
	});
};


$('#climb').click(function () {
	var module1 = document.querySelector("#module");
	module1.innerHTML = '<div class="cotents"> \
	    <div class="content1" id="content1"> </div>\
	    <div class="content2" id="content2"> </div></div>';
	$.ajax({
		type: 'get',
		url: '/profile/partyList',
		success: function (data) {
			$("#content1").load("/profile/partyList");
		}
	});
	$.ajax({
		type: 'get',
		url: '/profile/pastPartyList',
		success: function (data) {
			$("#content2").load("/profile/pastPartyList");
		}
	});
	document.getElementById("module").style.opacity = "0";
	$("#module").animate({
		opacity: 1
	});
	setFooterPosition();
	console.log("setFooterPosition() 실행됨.");
});

$('#info').click(function () {
	var module1 = document.querySelector("#module");
	module1.innerHTML = '<div class="cotents"> \
	    <div class="content1" id="content1"> </div>\
	    <div class="content2" id="content2"> </div></div>';
	$.ajax({
		async: true,
		type: 'get',
		url: '/profile/info',
		success: function (data) {
			document.getElementById("module").style.opacity = "0";
			$("#module").animate({
				opacity: 1
			});
			$("#module").load("/profile/info");
		}
	});
	setFooterPosition();
	console.log("setFooterPosition() 실행됨.");
});


$('#mypost').click(function () {
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
		url: '/profile/mypost',
		success: function (data) {
			$("#content1").load("/profile/mypost");
		}
	});
	$.ajax({
		type: 'get',
		url: '/profile/mycomment',
		success: function (data) {
			$("#content2").load("/profile/mycomment");
		}
	});
	document.getElementById("module").style.opacity = "0";
	$("#module").animate({
		opacity: 1
	});
	setFooterPosition();
	console.log("setFooterPosition() 실행됨.");
});

$('#mylike').click(function () {
	var module1 = document.querySelector("#module");
	module1.innerHTML = '<div class="my-like cotents"> \
	    <div class="content1" id="content1"> </div>\
	    <div class="content2" id="content2"> </div></div>';
	$.ajax({
		async: true,
		type: 'get',
		url: '/profile/mylike',
		success: function (data) {
			document.getElementById("module").style.opacity = "0";
			$(".cotents").load("/profile/mylike");
			setTimeout(function () {
				$("#module").animate({
					opacity: 1
				})
			}, 200);
		}
	});
	setFooterPosition();
	console.log("setFooterPosition() 실행됨.");
});

//닉네임변경 및 닉네임 유효성 검사
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
		data: { "nickname": nickname },
		success: function (data) {
			console.log("success function({}) invoked", data);
			nicknameCheck(nickname);
			if (isRightNickname && (nickname == original_nickname)) {
				isRightText.innerText = "같은 닉네임입니다.";
				noSignByRed();
				isRightNickname = false;
			} else if (isRightNickname && (data > 0)) {
				isRightText.innerText = "같은 닉네임이 있습니다.";
				noSignByRed();
				isRightNickname = false;
			} else if (isRightNickname) {
				isRightText.innerText = "사용 가능한 닉네임입니다.";
				okSignByGreen();
				isRightNickname = true;
			}//if-else
		}//success
	});//ajax
}

function nicknameCheck(nickname) {
	console.log("nicknameCheck({}) invoked", nickname);
	var nickname = document.getElementById('nickname').value;
	var nickLength = 0;

	var engCheck = /[a-z]/;
	var numCheck = /[0-9]/;
	var isIsValidateName = /^[a-zA-Zㄱ-힣0-9*_]{2,20}$/;
	var hangleCount = 0;

	for (var i = 0; i < nickname.length; i++) { //한글은 3, 영문은 1로 치환
		nick = nickname.charAt(i);
		if (escape(nick).length > 4) {
			nickLength += 3;
			hangleCount ++;
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
		//닉네임 1자인 경우   
	} else if (hangleCount <= 1 && nickname.length <= 1) {
		isRightText.innerText = "닉네임은 2자 이상 입력하여야 합니다.";
		noSignByRed();
		isRightNickname = false;
		//닉네임 한글 1~10자, 영문 및 숫자 2~20자   
	} else if (nickLength < 2 || nickLength > 20) {
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
	if (isRightNickname) {
		var nickname = document.getElementById('nickname').value;
		$.ajax({
			url: '/profile/changeNickname',
			data: { "nickname": nickname },
			success: function (data) {
				console.log("내가 바꾼 닉네임은?" + data);

				if (data != "") {
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

function okSignByGreen() {
	document.querySelector("input#nickname").style.border = "3px solid #4ebc7a";
	document.querySelector("#isRightText").style.color = "#4ebc7a";
	document.querySelector("#ok-sign").style.display = "inline";
	document.querySelector("#ok-sign").style.color = "#4ebc7a";
	document.querySelector("#no-sign").style.display = "none";

}
function noSignByRed() {
	document.querySelector("input#nickname").style.border = "3px solid #f14141";
	document.querySelector("#isRightText").style.color = "#f14141";
	document.querySelector("#no-sign").style.display = "inline";
	document.querySelector("#no-sign").style.color = "#f14141";
	document.querySelector("#ok-sign").style.display = "none";
}



