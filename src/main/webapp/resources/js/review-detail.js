
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


$(() =>{
	let idx = 0;
	console.log('1'+ $('.content').html());
	console.log('2' + $('.content').innerHTML);
	console.log(aaa);
	$('.content').find('img').each(function() {
		
		console.log(fileList.get(idx).get(path));
		$(this).attr('src', fileList.get(idx++).get(path));
  });
});
