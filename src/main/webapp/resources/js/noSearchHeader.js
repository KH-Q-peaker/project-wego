const selector = (selector) => document.querySelector(selector);

// Header Event init
addEventListener("click", (e) => {
  // 회원 메뉴바가 열린상태에서 다른 곳을 클릭하면 메뉴바 숨기기
  if (e.target.id !== "userImg") {
    selector("#userImg").classList.remove("toggle");
    selector(".menubar").style.display = "none";
  } // if
});

// 회원 이미지 클릭 시 메뉴바 표시
selector("#userImg").addEventListener("click", () => {
  if (selector("#userImg").classList.toggle("toggle")) {
    selector(".menubar").style.display = "block";
  } else {
    selector(".menubar").style.display = "none";
  }
});
