
var joinModalcls = function(){
	
	$(".joinModal").hide('fast');
	$(".modalbackground").remove();
}

var joinModal = function() {

	const backdrop =$('<div></div>').addClass('modalbackground');
	$(".total-wrap").append(backdrop);

	$(".joinModal").show('fast').css('display', 'flex');

	$(document).off('mouseup').on('mouseup', function(e) { /* 외부 영역 클릭 시 닫기 */

		if ($(".joinModal").has(e.target).length === 0) {
			joinModalcls();
		}
	});

	$(document).off('keydown').on('keydown', function(e) {/* esc입력시 닫기 */

		var code = e.keyCode || e.which;

		if (code == 27) { // 27은 ESC 키번호
			joinModalcls();
		}
	});

	$(".clsbtn").off('click').on('click', function() {
		joinModalcls();
	});
}



$(() => { /* 참여하기 모달창 on/off  */

	$(".join").off('click').on('click', function(){ 
	
		if($(this).attr('id') === 'join'){
			
			$("#ment").text('참여하시겠습니까?');
			$(".clsbtn").val('취소');
			$(".joinbtn").val('참여하기').removeClass('clsjoinbtn');
			joinModal(); 
		
		$(".joinbtn").off('click').on('click', function(){
			
			$.ajax({
			url : "/party/" + cri.targetCd + "/join",
			type : "POST",
//			data : 
//			{
//				userId : 160 /*수정해야 됨 */
//			},
			success : function(){
		 		joinModalcls();
				setMessage("🏃🏻‍♀️참여되었습니다.");
		 		showModal();
		 		setTimeout(hideModal, 500);
//				setTimeout(()=>{location.reload()}, 600);
				$('#join').attr('id', 'clsjoin').val('취소하기');
			},
			error : function(){
			 	joinModalcls();
		 		setMessage("⚠️ 이미 참여됐습니다."); // 이거 고쳐ㅕㅕㅕㅕㅕㅕㅕㅕㅕ
		 		showModal();
		 		setTimeout(hideModal, 500);
			}
			});
		});	
		}
		else{
			
			$("#ment").text('취소하시겠습니까?');
			$(".clsbtn").val('아니오');
			$(".joinbtn").val('예').addClass('clsjoinbtn');

			joinModal();


			$(".joinbtn").off('click').on('click', function() {

				$.ajax({
					url: "/party/" + cri.targetCd + "/join",
					type: "DELETE",
//					data:
//					{
//						userId: 160 /*수정해야 됨 */
//					},
					success: function() {
						joinModalcls();
						setMessage("🙅🏻‍♀️취소되었습니다.");
						showModal();
						setTimeout(hideModal, 500);
						$('#clsjoin').attr('id', 'join').val('참여하기');
					},
					error: function() {
						joinModalcls();
						setMessage("⚠️ 취소안됨 "); // 이거 고쳐ㅕㅕㅕㅕㅕㅕㅕㅕㅕ
						showModal();
						setTimeout(hideModal, 500);
					}
				});
			});
		}
	});
	
});

