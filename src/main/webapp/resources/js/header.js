const selector = (selector) => document.querySelector(selector);

// All Click EventListener
window.addEventListener("click", (e) => {
  // 회원 메뉴바가 열린상태에서 다른 곳을 클릭하면 메뉴바 숨기기
  if (e.target.id !== "userImg" && selector("#userImg") !== null) {
    selector("#userImg").classList.remove("toggle");
    selector(".menubar").style.display = "none";
  } // if

  // 검색바에 포커스가 없을 때 입력테스트 삭제버튼 숨기기
  // 글 작성 폼에는 검색바가 없으므로 if로 null 체크
  if (e.target.id !== "search" && selector(".search-bar .cancel") != null) {
    selector(".search-bar .cancel").style.display = "none";
  } // if

  // 검색바에 포커스가 있을 때 입력테스트 삭제버튼 표시
  if(e.target.id === "search") {
    selector(".cancel").style.display = "block";
  } // if
})

selector("#userImg")?.addEventListener("click", () => {
  if (selector("#userImg").classList.toggle("toggle")) {
    selector(".menubar").style.display = "block";
  } else {
    selector(".menubar").style.display = "none";
  }
})

// header에서 검색바를 제거하는 함수
const removeSearch = () => selector(".search-bar").remove();

$(() => {

  $("nav li#ranking").on({
    mouseenter: function () {
      $(this).children(".rankMenu").css("display","block")
    },
    mouseleave:function (){
      $(this).children(".rankMenu").css("display","none")
    }
  })

})




