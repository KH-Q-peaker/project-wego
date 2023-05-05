// 변수 초기화
var page = 1; // 초기 페이지 번호
var sortNum = 0;
sortNum = $('#data-container').children().last().attr('sortNum');

var url = window.location.href;  // 데이터 요청 URL
var urlPathname = window.location.pathname;

var query;

var isPage = true; //존재하는 페이지(데이터가 있을 경우 스크롤 이벤트 반응)
var hasPage = true; // 데이터 존재 여부를 나타내는 변수


// 스크롤 이벤트 핸들러
$(window).scroll(function () {
  console.log(hasPage);
  if (isPage === hasPage) {

    var scrollTop = $(window).scrollTop();
    var windowHeight = $(window).height();
    var documentHeight = $(document).height();

    if (scrollTop + 1 >= documentHeight - windowHeight) {
      console.log(page);
      console.log(hasPage);
      page++;
      console.log(page);
      next_load_search();
    } // if  
  } else {
    console.log("else");
    return;
  } // if-else
});


function next_load_search() {
  // AJAX 요청
  $.ajax({
    type: "POST",
    url: url,
    data: { page: page, sortNum: sortNum, query: query },
    success: function (data) {
      if (data.hasPage === false) {
        hasPage = false;
        console.log(hasPage);

        return;
      } else {
        hasPage = true;
        console.log(hasPage);
        $('#data-container').append(data);

        sortNum = $('#data-container').children().last().attr('sortNum');
      } // if-else

    } // success

  })
}
