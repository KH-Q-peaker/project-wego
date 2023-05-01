
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
		let mentionList = targetComment.parent();
		let commentId = $(this).siblings("#commentId").val();
		/* 댓글 삭제 post 전송 */
		$(".del").off('click').on('click', function(){
			
			$.ajax({
				url : "/comment/" + commentId,
				type : "DELETE",
				success : function(){
					setMessage("🗑️ 댓글이 삭제되었습니다.");
		 			showModal();
		 			setTimeout(hideModal, 700);
		 			hideDeleteModal();
					
					if(targetComment.children('#mentionId').length > 0){
						
						$.ajax({
						url: "/comment/mention",
						type: "GET",
						data: {
							targetGb: target.targetGb,
							targetCd: target.targetCd,
							commentId : targetComment.children('#mentionId').val()
						},
						success: function(data) {
							mentionList.html(data);
							mentionList.prev().prev().find('#mentionCnt').text(loadCnt);
							$('#cmtcnt').html(commentCnt);
							
							if(loadCnt == 0){
								mentionList.prev().remove();
							}
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
								$('#cmtcnt').html(commentCnt);
								$(window).off('scroll').on('scroll', scrollCommentLoading);
						},
						error: () => {
							console.log('댓글로딩오류 ');/* 바꿔야됨  */
						}
					});
					}
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
					window.location.replace('http://localhost:8080' + url);		 			
					hideDeleteModal();
					setMessage(data);
					showModal();
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
