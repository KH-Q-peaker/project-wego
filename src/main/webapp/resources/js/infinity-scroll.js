var page = 2;
var sortNum = 0;
sortNum = $('#data-container').children().last().attr('sortNum');

var maxPage = parseInt(document.getElementById("maxPage").getAttribute("boardMaxPage"));

var url = window.location.href;
var urlPathname = window.location.pathname;

var orderBy;
if (urlPathname === '/info') {
  orderBy = 'abc';
} else if (urlPathname === '/review' || urlPathname === '/party') {
  orderBy = 'newest';
} // if-else

var isLoading = false;

// 스크롤 이벤트 핸들러
$(window).scroll(function () {
  var scrollTop = $(window).scrollTop();
  var windowHeight = $(window).height();
  var documentHeight = $(document).height();

  if (page <= maxPage) {
    if (scrollTop + 500 >= documentHeight - windowHeight && !isLoading) {
      isLoading = true;
      next_load();
    } // if  
  } else {
    return;
  } // if-else
});


function next_load() {
  // AJAX 요청
  $.ajax({
    type: "POST",
    url: url,
    data: { page: page, sortNum: sortNum, orderBy: orderBy },
    success: function (data) {
      $('#data-container').append(data);

      sortNum = $('#data-container').children().last().attr('sortNum');

    }, complete: function () {
      isLoading = false;
      setTimeout(function () { page++; }, 1000);
    }
  });
}


$('#sort-abc').data('orderBy', 'abc');
$('#sort-likes').data('orderBy', 'like');
$('#sort-newest').data('orderBy', 'newest');
$('#sort-oldest').data('orderBy', 'oldest');

// 정렬 버튼 클릭 이벤트 핸들러 등록
$('#sort-abc, #sort-likes, #sort-newest, #sort-oldest').on('click', function () {
  // 클릭한 버튼의 orderBy 값을 가져옵니다.
  orderBy = $(this).data('orderBy');
  page = 1;
  sortNum = 0;

  $.ajax({
    type: "POST",
    url: url,
    data: { page: page, sortNum: sortNum, orderBy: orderBy },
    success: function (data) {
	  page++;
      // 부모 요소 가져오기
      var parent = document.getElementById('data-container');

      // 부모 요소에서 자식 요소를 모두 제거
      while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
      } // while

      // 생성된 jsp 코드를 추가
      $('#data-container').append(data);

      // 마지막 아이템 ID 업데이트
      sortNum = $('#data-container').children().last().attr('sortNum');
    }
  });
})
