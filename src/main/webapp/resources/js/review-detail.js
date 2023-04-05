
$(window).scroll(function() {
   if($(window).scrollTop() + window.innerHeight >= ($(document).height() -1)) {
	  loadMoreComments();
	  }
});

let page = 2

function loadMoreComments() {

	$.ajax({
		url: "/comment/load",
		type: "GET",
		data: {
			targetGb: target.targetGb,
			targetCd: target.targetCd,
			currPage: page++,
			amount: target.amount
		}, // 댓글은 불러와짐

		success: function(data) {

			if (data.length != 0) {
				$(".cmtcontainer").append(data);
			}
		},
		error: () => {
			console.log('댓글로딩오류 ');
		}
	});
}



