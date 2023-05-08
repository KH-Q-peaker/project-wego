
	function selectClickCurrPage() {
		var currPage = event.target.innerText;
		$.ajax({
			type: 'get',
			url: '/profile/mypost',
			success: function (data) {
				$(".myPostModule").load("/profile/mypost?currPage=" + currPage);
			}//success
		});//ajax
	}//selectClickCurrPage

	function selectClickCurrPagePrev() {
		var currPage = $('#currPagePrev').val();
		$.ajax({
			type: 'get',
			url: '/profile/mypost',
			success: function (data) {
				$(".myPostModule").load("/profile/mypost?currPage=" + currPage);
			}//success
		});//ajax
	} //selectClickCurrPagePrev

	function selectClickCurrPageNext() {
		var currPage = $('#currPageNext').val();
		$.ajax({
			type: 'get',
			url: '/profile/mypost',
			success: function (data) {
				$(".myPostModule").load("/profile/mypost?currPage=" + currPage);
			}//success
		});//ajax
	} //selectClickCurrPageNext