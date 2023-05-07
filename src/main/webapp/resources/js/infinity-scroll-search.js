var page = 2;
var sortNum = 0;
sortNum = $('#data-container').children().last().attr('sortNum');

var maxPage = parseInt(document.getElementById("maxPage").getAttribute("boardMaxPage"));

var url = window.location.href;
var urlPathname = window.location.pathname;

var query;

var isLoading = false;

// 스크롤 이벤트 핸들러
$(window).scroll(function () {
  var scrollTop = $(window).scrollTop();
  var windowHeight = $(window).height();
  var documentHeight = $(document).height();

  if (page <= maxPage) {
    if (scrollTop + 500 >= documentHeight - windowHeight && !isLoading) {
      isLoading = true;
      next_load_search();
    } // if  
  } else {
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
      $('#data-container').append(data);

      sortNum = $('#data-container').children().last().attr('sortNum');

    }, complete: function () {
      isLoading = false;
      setTimeout(function () { page++; }, 1000);
    }
  });
}
