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

$('#sort-abc').data('orderBy', 'abc');
$('#sort-likes').data('orderBy', 'like');


// 정렬 버튼 클릭 이벤트 핸들러 등록
$('#sort-abc, #sort-likes').on('click', function () {
  // 클릭한 버튼의 orderBy 값을 가져옵니다.
  orderBy = $(this).data('orderBy');

  console.log(orderBy);

  // 서버로 GET 요청 보내기
  $.ajax({
    type: "POST",
    url: "/info",
    data: { page: page, lastItemId: lastItemId, orderBy: orderBy },
    success: function (data) {

      // 부모 요소 가져오기
      const parent = document.getElementById('data-container');

      // 부모 요소에서 자식 요소를 모두 제거
      while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
      }
      // 생성된 jsp 코드를 추가
      $('#data-container').append(data);


      // 마지막 아이템 ID 업데이트
      lastItemId = $('#data-container').children().last().attr('sortNum');
    }
  });



})
