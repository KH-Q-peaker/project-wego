
var hideJoinModal = function(){
	
	$(".joinModal").hide('fast');
	$(".modalbackground").remove();
}

var showJoinModal = function() {

	const backdrop =$('<div></div>').addClass('modalbackground');
	$(".total-wrap").append(backdrop);

	$(".joinModal").show('fast').css('display', 'flex');

	$(document).off('mouseup').on('mouseup', function(e) { /* 외부 영역 클릭 시 닫기 */
		if ($(".joinModal").has(e.target).length === 0) {
			hideJoinModal();
		}
	});
	$(document).off('keydown').on('keydown', function(e) {/* esc입력시 닫기 */

		var code = e.keyCode || e.which;
		if (code == 27) { // 27은 ESC 키번호
			hideJoinModal();
		}
	});
	$(".clsbtn").off('click').on('click', function() {
		hideJoinModal();
	});
}

$(() => { /* 참여하기 모달창 on/off  */

	$(".join").off('click').on('click', function(){ 
	
		if($(this).attr('id') === 'join'){
			
			$("#ment").text('참여하시겠습니까?');
			$(".clsbtn").val('취소');
			$(".joinbtn").val('참여하기').removeClass('clsjoinbtn');
			showJoinModal(); 
		
		$(".joinbtn").off('click').on('click', function(){
			
			$.ajax({
			url : "/party/" + target.targetCd + "/join" ,
			type : "POST",
			success : function(data){
		 		hideJoinModal();
				setMessage("🏃🏻‍♀️참여되었습니다.");
		 		showModal();
		 		setTimeout(hideModal, 700);
				$('#join').attr('id', 'clsjoin').val('취소하기');
				$('#currentCount').html(data);
			},
			error : function(data){
			 	hideJoinModal();
				setMessage(data.responseText);
				showModal();
			 	
				if (data.status == 403) {
					setTimeout(hideModal, 5000);
				}else{
		 			setTimeout(hideModal, 700);
				}
			}
			});
		});	
		}
		else{
			$("#ment").text('취소하시겠습니까?');
			$(".clsbtn").val('아니오');
			$(".joinbtn").val('예').addClass('clsjoinbtn');

			showJoinModal();

			$(".joinbtn").off('click').on('click', function() {

				$.ajax({
					url : "/party/" + target.targetCd + "/join" ,
					type : "POST",
					success: function(data) {
						hideJoinModal();
						setMessage("🙅🏻‍♀️ 취소되었습니다.");
						showModal();
						setTimeout(hideModal, 700);
						$('#clsjoin').attr('id', 'join').val('참여하기');
						$('#currentCount').html(data);
					},
					error: function(data) {
						hideJoinModal();
						
						if (data.status == 403) {
							setMessage(data.responseText);
							setTimeout(hideModal, 5000);
						} else {
							setMessage("⚠️ 취소할 수 없습니다. "); 
							setTimeout(hideModal, 700);
						}
						showModal();
					}
				});
			});
		}
	});
	
});

