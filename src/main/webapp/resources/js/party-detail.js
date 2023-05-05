
function scrollCommentLoading() {

   if($(window).scrollTop() + window.innerHeight >= ($(document).height() -1)) {
	  loadMoreComments();
  }
}

$(() => {
	
$(window).off('scroll').on('scroll', scrollCommentLoading);


$(".modify").off('click').on('click', function(){
	
	let currentUrl = window.location.href;
	modifyURL = currentUrl.replace('/party/', '/party/modify/');
	
	window.location.replace(new URL(modifyURL));
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