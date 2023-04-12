// 기본 로드
window.onload = function () {
  userPosts();
};

removeSearch();

// Get the navigation menu items
const menuItems = document.querySelectorAll(".content-header-menu-item");

// 클릭한 부분의 id 값을 css 포인터 변경
const contentSection = document.querySelector("#content-section");
console.log(">>>>>>>>>>contentSection {}", contentSection);

menuItems.forEach((item, index) => {
  // index 매개변수 추가
  item.addEventListener("click", () => {
    console.log("네비가 클릭됨");

    // Remove the "item-point" id from all menu items
    menuItems.forEach((item) => item.removeAttribute("id"));

    // Add the "item-point" id to the clicked menu item
    item.setAttribute("id", "item-point");
    console.log(item);
    console.log("item이 변화되었습니다.");
  });
});

// var userId = 723;
var userId = document.querySelector("#userId").value;

// // ajax
function getUserPosts() {
  $.ajax({
    // url: "./written",
    url: "/profile/userposts?userId=" + userId,
    type: "GET",
    success: function (data) {
      if (data.length == 0) {
        $("#module").html("<h1>작성한 글이 없습니다.<h1>");
        console.log("모듈에 작성 글 데이터 넣기 실패 ");
      } else {
        $("#module").html(data);
        console.log("모듈에 작성 글 데이터 넣기 성공 ");
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
    // url: "./comment",
    type: "GET",
    success: function (data) {
      if (data.length == 0) {
        $("#module").html("<h1>작성한 댓글이 없습니다.<h1>");
        console.log("모듈에 작성 댓글 데이터 넣기 실패 ");
      } else {
        $("#module").html(data);
        console.log("모듈에 작성 댓글 데이터 넣기 성공 ");
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
