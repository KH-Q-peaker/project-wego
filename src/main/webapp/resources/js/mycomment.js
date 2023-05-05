if (typeof comment_data_page == 'undefined') {
	const comment_data_page = document.getElementById('commentCurrPageNum');
		var myCommentCurrPage = comment_data_page.getAttribute('data-comment-page');
		if (myCommentCurrPage == 1) {
			$('.myCommentPage1').addClass("currPage");
		}

	function selectClickCurrPage1() {
		var currPage = event.target.innerText;

		$.ajax({
			type: 'get',
			url: '/profile/mycomment',
			success: function (data) {
				$(".mycommentModule").load("/profile/mycomment?currPage=" + currPage);
			}//success
		});//ajax
	}//selectClickCurrPage

	function selectClickCurrPagePrev1() {
		var currPage = $('#currPagePrev').val();
		$.ajax({
			type: 'get',
			url: '/profile/mycomment',
			success: function (data) {
				$(".mycommentModule").load("/profile/mycomment?currPage=" + currPage);
			}//success
		});//ajax
	} //selectClickCurrPagePrev

	function selectClickCurrPageNext1() {
		var currPage = $('#currPageNext').val();
		$.ajax({
			type: 'get',
			url: '/profile/mycomment',
			success: function (data) {
				$(".mycommentModule").load("/profile/mycomment?currPage=" + currPage);
			}//success
		});//ajax
	} //selectClickCurrPageNext
}