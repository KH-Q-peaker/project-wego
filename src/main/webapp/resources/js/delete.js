
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
		let firstMention = targetComment.nextAll('.mention').first();
		let commentId = $(this).siblings("#commentId").val();

		/* 댓글 삭제 post 전송 */
		$(".del").off('click').on('click', function(){
			
			$.ajax({
				url : "/comment/" + commentId,
				type : "DELETE",
				success : function(){
					setMessage("🗑️ 댓글이 삭제되었습니다.");
		 			showModal();
		 			setTimeout(hideModal, 500);
		 			deleteModalcls();
		
		 			if(firstMention.children('#mentionId').val() == commentId){
						targetComment.children().not('.comment').remove();
						targetComment.children('.comment').html('삭제된 댓글입니다.');
//					}else if(targetComment.next('.mention').children('#mentionId').val() != commentId &&
//							 targetComment.prev('.mention').children('#mentionId').val() != commentId){
//							console.log('dfdfdfdf');
//							targetComment.prev('.comments').remove();
//							targetComment.remove();
					}else{
		 				targetComment.remove();
					}
				},
				error : function(){
		 			deleteModalcls();
		 			setMessage("⚠️ 삭제실패."); // 이거 고쳐ㅕㅕㅕㅕㅕㅕㅕㅕㅕ
		 			showModal();
		 			setTimeout(hideModal, 500);
				}
			});
		});
	});
	
	// 이거 해야되는거임 한거아님 
	$(".delete").off('click').on('click', function() {
		
		deleteModal();
		
		let url;
		
		if(cri.targetGb === 'SAN_REVIEW'){
			url	= '/review/';
		}else if(cri.targetGb === 'SAN_PARTY'){
			url = '/party/';
		}
		
		$(".del").off('click').on('click', function(){
		
			$.ajax({
				url : url + cri.targetCd,
				type : "DELETE",
				success : function(){
					setMessage("🗑️ 글이 삭제되었습니다.🚨");
		 			showModal();
		 			setTimeout(hideModal, 800);
				},
				error : function(){
		 			deleteModalcls();
		 			setMessage("⚠️ 삭제실패."); // 이거 고쳐ㅕㅕㅕㅕㅕㅕㅕㅕㅕ
		 			showModal();
		 			setTimeout(hideModal, 900);
				}
			});
		
		});
	});
});
