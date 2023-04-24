
var hideDeleteModal = function(){
	$(".deleteModal").hide('fast');
	$(".modalbackground").remove();
}

var showDeleteModal = function(){

	const backdrop =$('<div></div>').addClass('modalbackground');
	$(".total-wrap").append(backdrop);
	
	$(".deleteModal").show('fast').css('display', 'grid');
			
		$(document).off('mouseup').on('mouseup', function (e){ /* 외부 영역 클릭 시 닫기 */
			if($(".deleteModal").has(e.target).length === 0){
				hideDeleteModal();
			}
		});
		$(document).off('keydown').on('keydown', function(e){/* esc입력시 닫기 */
	   		var code = e.keyCode || e.which;

	   		if (code == 27) { hideDeleteModal(); }
		});		
		$(".delcancle").off('click').on('click', function() { /* 취소 클릭 시 닫기  */
			hideDeleteModal();
		});
};

$(() => { /* 삭제 관련 */
	
	$(".deletecmt").off("click").on('click', function() {
		
		showDeleteModal();
	
		let targetComment = $(this).parent().parent();
		let commentId = $(this).siblings("#commentId").val();
		/* 댓글 삭제 post 전송 */
		$(".del").off('click').on('click', function(){
			
			$.ajax({
				url : "/comment/" + commentId,
				type : "DELETE",
				success : function(data){
					setMessage("🗑️ 댓글이 삭제되었습니다.");
		 			showModal();
		 			setTimeout(hideModal, 500);
		 			hideDeleteModal();
					
					if(targetComment.children('#mentionId').length > 0){
						
						$.ajax({
						url: "/comment/mention",
						type: "GET",
						data: {
							commentId : targetComment.children('#mentionId').val()
						},
						success: function(data) {
							targetComment.parent().html(data);
						},
						error: () => {
							console.log('댓글로딩오류 ');/* 바꿔야됨  */
						}
					});
					}else{
						
					$.ajax({
						url: "/comment/load",
						type: "GET",
						data: {
							targetGb: target.targetGb,
							targetCd: target.targetCd,
							amount: target.amount,
							lastComment: 0
						},
						success: function(data) {
								$('#cmtwrite').nextAll().remove().end().after(data);
								$(window).off('scroll').on('scroll', scrollCommentLoading);
						},
						error: () => {
							console.log('댓글로딩오류 ');/* 바꿔야됨  */
						}
					});
					}
					$('#cmtcnt').html(data);
					
//		 			if(targetComment.find('#mentionCnt').html() > 0 ){
//						targetComment.children().not('.comment, .mentionbtn, #commentId, .mentionCnt').remove();
//						targetComment.children('.comment').html('삭제된 댓글입니다.');
//					}else{
//		 				targetComment.remove();
//					}
				},
				error : function(){
		 			hideDeleteModal();
		 			setMessage("⚠️ 삭제실패."); 
		 			showModal();
		 			setTimeout(hideModal, 700);
				}
			});
		});
	});
	
	$(".delete").off('click').on('click', function() {
		
		showDeleteModal();
		
		let url;
		
		if(target.targetGb === 'SAN_REVIEW'){
			url	= '/review/';
		}else if(target.targetGb === 'SAN_PARTY'){
			url = '/party/';
		}
		$(".del").off('click').on('click', function(){
		
			$.ajax({
				url : url + target.targetCd,
				type : "DELETE",
				success : function(data){
					hideDeleteModal();
					setMessage(data);
					showModal();
					setTimeout(function(){ window.location.replace('http://localhost:8080' + url) }, 700);		 			
				},
				error : function(){
		 			hideDeleteModal();
		 			setMessage("⚠️ 삭제실패."); // 이거 고쳐ㅕㅕㅕㅕㅕㅕㅕㅕㅕ
		 			showModal();
		 			setTimeout(hideModal, 700);
				}
			});
		});
	});
});
