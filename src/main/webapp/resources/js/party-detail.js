
function scrollCommentLoading() {

   if($(window).scrollTop() + window.innerHeight >= ($(document).height() -1)) {
	  loadMoreComments();
  }
}
 
$(window).off('scroll').on('scroll', scrollCommentLoading);

let page = target.currPage;
//const urlParams = new URLSearchParams(window.location.search);


function loadMoreComments() {

//	console.log('loading');
//	console.log(page);
//	console.log(commentCount);
//	console.log(target.amount);

		let lastCommentId = $('.comments:last #commentId').val();
		let lastMentionId = $('.mention:last #mentionId').val();

		let lastComment = (lastMentionId == null ? lastCommentId : lastMentionId);
		
		$.ajax({
			url: "/comment/load",
			type: "GET",
			data: {
				targetGb: target.targetGb,
				targetCd: target.targetCd,
				currPage: page,
				amount: target.amount,
				lastComment: lastComment
			},

			success: function(data) {
				if (data.length != 0) {
					$(".cmtcontainer").append(data);
				}else{
					$(window).off('scroll');
				}
			},
			error: () => {
				console.log('댓글로딩오류 ');/* 바꿔야됨  */
			}
		});
}

$(".modify").off('click').on('click', function(){
	
	let currentUrl = window.location.href;
	newURL = currentUrl.replace('/party/', '/party/modify/');
	
	window.location.replace(new URL(newURL));
});
