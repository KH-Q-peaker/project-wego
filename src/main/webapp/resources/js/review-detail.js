
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


$(() => {
	
	let idx = 0;
		console.log('시작!');
	$('#content').children().each(function(index, item){
			console.log('선택****!');
		if($(item).is("img")){
				console.log('IMG찾음!');
			
			$(item).attr({
				src : fileList[idx],
				alt : fileAltList[idx]
			});
			idx++;
		}
	});
});

