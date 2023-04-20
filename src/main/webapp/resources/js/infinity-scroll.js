// 변수 초기화
var page = 1; // 초기 페이지 번호
var isLoading = false; // 현재 요청이 진행중인지 여부
var url = '/info'; // 데이터 요청 URL

// 스크롤 이벤트 핸들러
$(window).scroll(function() {
  var scrollTop = $(window).scrollTop();
  var windowHeight = $(window).height();
  var documentHeight = $(document).height();

  // 스크롤이 끝에 다다르면 데이터를 추가로 불러옴
  if (scrollTop + windowHeight == documentHeight && !isLoading) {
    isLoading = true;
    // 페이지 번호 증가
    page++;

    // AJAX 요청
    $.ajax({
      url: url,
      data: { page: page },
      success: function(data) {
        // 데이터 추가
        $('#data-container').append(data);

        // 로딩 중 상태 초기화
        isLoading = false;
      }
    });
  }
});