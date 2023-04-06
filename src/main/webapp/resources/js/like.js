$(() =>{

    $(".like").click(function() {

        $(this).toggleClass("fill");
        
        $.ajax({
				url : "/like/toggle",
				type : "GET",
				data : 
				{
					targetGb : target.targetGb,
					targetCd : target.targetCd
				},
				success : function(data){
					console.log(data);
					$(".likeCnt").children('label').html(data);
				},
				error : function(){
		 			setMessage("⚠️ "); // 이거 고쳐ㅕㅕㅕㅕㅕㅕㅕㅕㅕ
		 			showModal();
		 			setTimeout(hideModal, 500);
				}
			});
    });
})