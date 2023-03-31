
var deleteModalcls = function(){
	$(".deleteModal").hide('fast');
	$(".modalbackground").remove();
}

var deleteModal = function(){

	const backdrop =$('<div></div>').addClass('modalbackground');
	$(".total-wrap").append(backdrop);
	
	$(".deleteModal").show('fast').css('display', 'grid');
			
		$(document).off('mouseup').on('mouseup', function (e){ /* 외부 영역 클릭 시 닫기 */
			if($(".deleteModal").has(e.target).length === 0){
				deleteModalcls();
			}
		});
		
		$(document).off('keydown').on('keydown', function(e){/* esc입력시 닫기 */
	   		var code = e.keyCode || e.which;

	   		if (code == 27) { deleteModalcls(); }
		});		
		$(".delcancle").off('click').on('click', function() { /* 취소 클릭 시 닫기  */
			deleteModalcls();
		});
};


$(() => { /* 삭제 관련 */
	
	$(".deletecmt").off("click").on('click', function() {
		
		deleteModal();
	
		let targetComment = $(this).parent().parent();
		let commentId = $(this).siblings("#commentId").val();
//		// *******************************************************
//		var includeElement = $('include[src$="../comment/comment.jsp"]');
//		var includeUrl = includeElement.attr('src');
		// *******************************************************
		/* 댓글 삭제 post 전송 */
		$(".del").off('click').on('click', function(){
			
			$.ajax({
				url : "/comment/" + commentId,
				type : "DELETE",
				data : 
				{
					commentId : commentId
				},
				success : function(){
					setMessage("🗑️ 댓글이 삭제되었습니다.");
		 			showModal();
		 			setTimeout(hideModal, 500);
		 			deleteModalcls();

//					setTimeout(()=>{location.reload()}, 550); // 아 새로고침이 필요해 어쩔수없어 그런데도 ajax를 쓴다? 이게맞냐 
//		 			targetComment.remove(); // 아 엄마댓글 남아야돼서 비동기로 날리수도 없음 .................
				},
				error : function(){
		 			deleteModalcls();
		 			setMessage("⚠️ 삭제실패."); // 이거 고쳐ㅕㅕㅕㅕㅕㅕㅕㅕㅕ
		 			showModal();
		 			setTimeout(hideModal, 500);
				}
			});
//			$.ajax({
//				url : "/comment/load",
//				type : "GET",
//				data : {
//					targetGb : cri.targetGb,
//					targetCd : cri.targetCd
//				},
//				success : function(data){
//					$("#loadtest").replaceWith(data);
//				}
//			}) 아 이건 아닌거같아 
		});
	});
	
	// 이거 해야되는거임 한거아님 
	$(".deleteRv").off('click').on('click', function() {
		
		deleteModal();
		
		$(".del").off('click').on('click', function(){
		
			$.ajax({
				url : "/Delete",
				type : "POST",
				data : 
				{	
					targetGb : cri.targetGb,
					targetCd : cri.targetCd
				},
				success : function(){
					setMessage("🗑️ 글이 삭제되었습니다.🚨");
		 			showModal();
		 			setTimeout(hideModal, 800);
					setTimeout(()=>{location.reload()}, 1000); // 이거 목록페이지로 넘어가게해야됨 
				},
				error : function(){
		 			$(".mdcontainer").hide('fast');
					$(".modalbackground").remove();
		 			setMessage("⚠️ 삭제실패."); // 이거 고쳐ㅕㅕㅕㅕㅕㅕㅕㅕㅕ
		 			showModal();
		 			setTimeout(hideModal, 900);
				}
			});
		
		});
	});
});
