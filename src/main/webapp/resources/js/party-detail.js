
function scrollCommentLoading() {

   if($(window).scrollTop() + window.innerHeight >= ($(document).height() -1)) {
	  loadMoreComments();
  }
}
 
$(window).off('scroll').on('scroll', scrollCommentLoading);


$(".modify").off('click').on('click', function(){
	
	let currentUrl = window.location.href;
	modifyURL = currentUrl.replace('/party/', '/party/modify/');
	
	window.location.replace(new URL(modifyURL));
});
