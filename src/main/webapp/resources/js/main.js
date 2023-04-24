// 메인페이지 - 산정보, 모집, 후기 글 목록 위에서
// 휠 스크롤 시 가로 슬라이드 구현
selector(".mountain-info .wrap").addEventListener("wheel", (e) => {
  e.preventDefault();
  e.currentTarget.scrollLeft += e.deltaY;
});

selector(".recruit .wrap").addEventListener("wheel", (e) => {
  e.preventDefault();
  e.currentTarget.scrollLeft += e.deltaY;
});

selector(".review .wrap").addEventListener("wheel", (e) => {
  e.preventDefault();
  e.currentTarget.scrollLeft += e.deltaY;
});

// top (scroll이 200이상 일때 top버튼 노출)
$(window).scroll(function () {
  if ($(this).scrollTop() > 100) {
    $(".scrollToTop").fadeIn();
    $(".chat").fadeIn();
  } else {
    $(".scrollToTop").fadeOut();
    $(".chat").fadeOut();
  }
});

// 위로 올라가는 부드러운 애니메이션
$(".scrollToTop, .chat").click(function () {
  $("html, body").animate({ scrollTop: 0 }, 400);
  return false;
});
