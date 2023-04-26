// 변수 초기화
var page = 1; // 초기 페이지 번호
var lastItemId = 0;
var isLoading = false; // 현재 요청이 진행중인지 여부 (중복실행방지)
var url = '/info'; // 데이터 요청 URL
var orderBy = 'abc'; // 초기 정렬 기준
var amount = 50;

lastItemId = $('#data-container').children().last().attr('sortNum');


// 스크롤 이벤트 핸들러
$(window).scroll(function () {
  var scrollTop = $(window).scrollTop();
  var windowHeight = $(window).height();
  var documentHeight = $(document).height();


  if (scrollTop + 1000 >= documentHeight - windowHeight) {
    isLoading = true;
    // 페이지 번호 증가
    page++;
    next_load();
    isLoading = false;
  }


  function next_load() {
    // AJAX 요청
    $.ajax({
      type: "POST",
      url: "/info",
      data: { page: page, lastItemId: lastItemId, orderBy: orderBy },
      success: function (data) {

        // 생성된 jsp 코드를 추가
        $('#data-container').append(data);

    
        // 마지막 아이템 ID 업데이트
        lastItemId = $('#data-container').children().last().attr('sortNum');
      }
      
    })
  }
})



