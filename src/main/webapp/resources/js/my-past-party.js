if (typeof data_past_page == 'undefined') {
	const data_past_page = document.getElementById('pastPageNum');
	var pCurrPage = document.querySelector('#pastcurrPage').value;
		if (pCurrPage == 1) {
			$('.pastPartyPage1').addClass("currPage");
		}

	function selectClickPastCurrPage() {
		console.log("********", event.target.innerText);
		var currPage = event.target.innerText;
		$.ajax({
			type: 'get',
			url: '/profile/pastPartyList',
			data: { "pcri.currPage": currPage },
			success: function (data) {
				$("#pastPartyModule").load("/profile/pastPartyList?currPage=" + currPage);
			}
		});//ajax
	}

	function selectClickPastCurrPagePrev() {
		var currPage = $('#pastcurrPagePrev').val();
		$.ajax({
			type: 'get',
			url: '/profile/pastPartyList',
			data: { "pcri.currPage": currPage },
			success: function (data) {
				$("#pastPartyModule").load("/profile/pastPartyList?currPage=" + currPage);
			}
		});//ajax
	}

	function selectClickPastCurrPageNext() {
		var currPage = $('#pastcurrPageNext').val();
		$.ajax({
			type: 'get',
			url: '/profile/pastPartyList',
			data: { "pcri.currPage": currPage },
			success: function (data) {
				$("#pastPartyModule").load("/profile/pastPartyList?currPage=" + currPage);
			}
		});//ajax
	}
}