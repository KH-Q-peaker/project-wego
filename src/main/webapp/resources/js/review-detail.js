
function scrollCommentLoading() {

   if($(window).scrollTop() + window.innerHeight >= ($(document).height() -1)) {
	  loadMoreComments();
  }
}
 
$(window).off('scroll').on('scroll', scrollCommentLoading);




$(".modify").off('click').on('click', function(){
	
	let currentUrl = window.location.href;
	newURL = currentUrl.replace('/review/', '/review/modify/');
	
	window.location.replace(new URL(newURL));
});


