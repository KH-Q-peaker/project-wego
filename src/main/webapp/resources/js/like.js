$(() =>{

	
    $(".like").click(function() {

		let isLike = $(this).hasClass('fill');
        $(this).toggleClass("fill");
        let status = isLike ? 'N' : 'Y';
        
        $.ajax({
				url : "/favorite",
				type : "POST",
				data : 
				{
					targetGb : target.targetGb,
					targetCd : target.targetCd,
					status : status
				},
				success : function(){
					let likeCnt = parseInt($(".likeCnt").children('label').text());
					if(isLike){
						$(".likeCnt").children('label').text(likeCnt - 1);
					} else{
						$(".likeCnt").children('label').text(likeCnt + 1);
					}
				},
				error : function(){
		 			setMessage("좋아요를 누를 수 없습니다."); 
		 			showModal();
		 			setTimeout(hideModal, 700);
				}
			});
    });
})