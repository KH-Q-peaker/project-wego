selector(".recruit .wrap-recruit").addEventListener("wheel", (e) => {
  if (window.innerWidth < 1000 && window.innerWidth > 480) {
    e.preventDefault();
    e.currentTarget.scrollLeft += e.deltaY;
  } // if
});

// top (scroll이 200이상 일때 top버튼 노출)
$(window).scroll(function () {
  if ($(this).scrollTop() > 100) {
    $(".scrollToTop, .chat").fadeIn();
  } else {
    $(".scrollToTop, .chat").fadeOut();
  } // if-else
});

// 위로 올라가는 부드러운 애니메이션
$(".scrollToTop, .chat").click(function () {
  $("html, body").animate({ scrollTop: 0 }, 400);
  return false;
});