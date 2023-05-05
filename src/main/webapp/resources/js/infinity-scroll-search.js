// 변수 초기화
var page = 1; // 초기 페이지 번호
var sortNum = 0;
sortNum = $('#data-container').children().last().attr('sortNum');

var isLoading = false; // 현재 요청이 진행중인지 여부 (중복실행방지)

var url = window.location.href;  // 데이터 요청 URL

var query;



// 스크롤 이벤트 핸들러
$(window).scroll(function () {
  var scrollTop = $(window).scrollTop();
  var windowHeight = $(window).height();
  var documentHeight = $(document).height();


  // 검색 쿼리가 있는 경우와 없는 경우로 나누어 처리
      if (scrollTop + 1000 >= documentHeight - windowHeight) {
      isLoading = true;
      page++;
    
      next_load_search();
isLoading = false;
}
});


function next_load_search() {
  // AJAX 요청
  $.ajax({
    type: "POST",
    url: url,
    data: { page: page, sortNum: sortNum, query: query },
    success: function (data) {

      // 생성된 jsp 코드를 추가
      $('#data-container').append(data);

      // 마지막 아이템 ID 업데이트
      sortNum = $('#data-container').children().last().attr('sortNum');
    }

  })
}
