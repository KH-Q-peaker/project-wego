

$(() => { /* 탑, 코멘트 버튼 스크롤 이벤트 */
	
//	$("#cmtwrite").hide();
		
	$(window).scroll(function(){
    
    if( $(this).scrollTop() < 200 ){
      $(".top").hide('normal');
    }
    else{
      $(".top").show('normal');
    }
    
  });

	$(".cmt").click(function() {
	
//	 document.getElementById('cmtwrite').scrollIntoView({behavior : 'smooth'});
	 	if($("#cmtwrite").css('display') == 'none'){
			$("#cmtwrite").show('normal');
	 	}else{
			$("#cmtwrite").hide('fast');
			$("#contents").val('').height('80px');
		}
	});
	
	$(".top").click(function() {
		
		window.scrollTo({top : 0, behavior: 'smooth'}); 
	});
});

