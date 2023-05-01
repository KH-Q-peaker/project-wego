

$(() => { /* 탑, 코멘트 버튼 스크롤 이벤트 */
	
		
	$(window).scroll(function(){
    
    if( $(this).scrollTop() < 200 ){
      $(".top").hide('normal');
    }
    else{
      $(".top").show('normal');
    }
  });
  
	$(".top").click(function() {
		
		window.scrollTo({top : 0, behavior: 'smooth'}); 
	});
	
	$(".cmt").click(function() {

		if ($('#chat').html() == "") {
			$.ajax({
				url: "/chat/room/" + target.targetCd,
				type: 'GET',
				success: function(data) {

					$('#chat').html(data);
				},
				error: function(){
					
					setMessage('참가자 전용 채팅입니다.');
					showModal();
					setTimeout(hideModal, 700);
				}
			});
		}
		if ($('#chat').css('display') == 'none') {
			$('#chat').show('fast');
			scrollChatDisplay();
		} else {
			$('#chat').hide('fast');
		}
	});
});

