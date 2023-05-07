

$(() => { /* 탑, 코멘트 버튼 스크롤 이벤트 */
	
		
	$(window).scroll(function(){
    
    if( $(this).scrollTop() < 200 ){
      $(".top").hide('normal');
    }
    else{
      $(".top").show('normal');
    }
  });
  
	$(".top").click(function() {
		
		window.scrollTo({top : 0, behavior: 'smooth'}); 
	});
});

