// 기본 로드
window.onload = function () {
  userPosts();
  removeSearch();
  addPickBadge();
};

const menuItems = document.querySelectorAll(".content-header-menu-item");

// 클릭한 부분의 id 값을 css 포인터 변경
const contentSection = document.querySelector("#content-section");

menuItems.forEach((item, index) => {
  // index 매개변수 추가
  item.addEventListener("click", () => {
    menuItems.forEach((item) => item.removeAttribute("id"));
    item.setAttribute("id", "item-point");
  });
});

var userId = document.querySelector("#userId").value;

// ajax
function getUserPosts() {
  $.ajax({
    url: "/profile/userposts?userId=" + userId,
    type: "GET",
    success: function (data) {
      if (data.length == 0) {
        $("#module").html("<h1>작성한 글이 없습니다.<h1>");
      } else {
        document.querySelector("#module").style.opacity = "0";
        $("#module").animate({
          opacity: 1,
        });
        $("#module").html(data);
        setTimeout(setFooterPosition(), 500);
      }
    },
    error: function (jqXHR, textStatus, errorThrown) {
      console.error("Error:", textStatus, errorThrown);
    },
  });
}

function userPosts() {
  getUserPosts();
}

function getUserComments() {
  $.ajax({
    url: "/profile/comment?userId=" + userId,
    type: "GET",
    success: function (data) {
      if (data.length == 0) {
        $("#module").html("<h1>작성한 댓글이 없습니다.<h1>");
      } else {
        document.querySelector("#module").style.opacity = "0";
        $("#module").animate({
          opacity: 1,
        });
        $("#module").html(data);
        setTimeout(setFooterPosition(), 500);
      }
    },
    error: function (jqXHR, textStatus, errorThrown) {
      console.error("Error:", textStatus, errorThrown);
    },
  });
}

function userComments() {
  getUserComments();
}


  var addPickBadge = () => {
	$.ajax({
		type: "GET",
		url: `/badge/pick-list/json/${userId}`,
		error: function () {
  
		}, // error
  
		success: function (badgePickList) {
  
			addPickBadgeItem(badgePickList);
		} // success
	});
  }
  
  var addPickBadgeItem = (badgePickList) => {
	let preBadgeId = 0;
  
	for (let idx = 0; idx < badgePickList.length; idx++) {
  
		let nowPickBadge = badgePickList[idx];
		let nowPickBadgeId = nowPickBadge.badgeId;
		let nowPickBadgeName = nowPickBadge.badgeName;
		let nowPickBadgeimg = nowPickBadge.img;
  
		if (preBadgeId != nowPickBadgeId) {
  
			let pcikBadgeHTML = `<div class='item pick' id = '${nowPickBadgeId}' > <div class="sanBadge"  style="background-image: url(${nowPickBadgeimg});"></div></div>`;
  
			preBadgeId = nowPickBadgeId;
  
			$(".badge-collection").append(pcikBadgeHTML);
		} // if
  
	}
  }