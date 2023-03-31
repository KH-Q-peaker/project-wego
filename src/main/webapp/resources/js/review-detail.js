

/* 글 수정 ----- 이거 작성폼으로 이동해*/


$(() => { /* 글 수정  */
	
	$(".modifyRv").click(function() {

	})	
	
})

$(window).scroll(function() {
   if($(window).scrollTop() == ($(document).height() - window.innerHeight - 0.5)) {
	  loadMoreComments();
	  console.log('dddd');
  }
});

let page = 2
//const urlParams = new URLSearchParams(window.location.search);


function loadMoreComments() {
	
  
   $.ajax({
      url: "/comment/load",
      type: "GET",
      data: {
	
		targetGb: cri.targetGb,
		targetCd: cri.targetCd,
        currPage: page,
        amount: cri.amount
      }, // 댓글은 불러와짐
      
      success: function(data) {
//      	$.getScript('/resources/js/comment.js');
//      	$("body").append('<script src="/resources/js/comment.js"></script>');	
		
		if(data.length == 0){
			
			$(window).off('scroll');
		}else{
        	$(".cmtcontainer").append(data);
        	page++;
		}
      },
      error: ()=>{
		console.log('댓글로딩오류 ');
		}
    });
}



