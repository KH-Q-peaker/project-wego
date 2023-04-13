
var reportModalcls = function(){
	
	$(".reportModal").hide('fast');
	$(".modalbackground").remove();
}
/* 신고 모달창 on 메소드  */
var reportModal = function(){
	
	const backdrop =$('<div></div>').addClass('modalbackground');
	$(".total-wrap").append(backdrop);
		
		$(".reportModal").show('fast').css('display', 'flex');/* 신고 모달창 on */
		$('input[name=check]').prop('checked', false); /* 체크 초기화 */
		
		
		/* 신고 모달창 off  */
		
		$(document).off('mouseup').on('mouseup', function (e){ /* 외부 영역 클릭 시 닫기 */
			if($(".reportModal").has(e.target).length === 0){
				reportModalcls();
			}
		});
		
		$(document).off('keydown').on('keydow', function(e){/* esc입력시 닫기 */
		
    		var code = e.keyCode || e.which;
 
   			if (code == 27) { reportModalcls(); }
		});
		
		$(".mdcancle").off('click').on('click', function() { /* 취소 버튼 클릭 시 닫기 */
			reportModalcls();
		});
};


$(() => { /* 사유 클릭 시 체크표시  */

      $(".reason").off('click').on('click', function() {
            $(this).prev().prop('checked', true);
      });
});

$(() => {
	
	let targetGb;
	let targetCd;
	
	/* 신고 버튼 클릭 시 신고 모달창 on */
	$(".reportcmt").off('click').on('click', function() {
		
		reportModal();
		
		targetGb = 'COMMENT';
		targetCd = $(this).parent().children('#commentId').val();
	});
	
	$(".report").off('click').on('click', function() {
		
		reportModal();
		
		targetGb = target.targetGb;
		targetCd = target.targetCd;
	});

	$(".mdreport").off('click').on('click', function(){
		
		/* 신고 사유 선택됐는지 확인 */
		var radios = document.getElementsByName("check");
   		var checked = false;
   	
   		for (var i = 0; i < radios.length; i++) {
   			if (radios[i].checked) {
       			checked = true;
       			break;
   			}
  	 	}
   		if (!checked) {
   			alert("신고 사유를 선택해주세요.");
   		}
   		else{ /* 선택됐을 경우 post 전송  */
			   
		$.ajax({
			url : "/report/create",
			type : "POST",
			data : 
			{
				targetGb : targetGb,
				targetCd : targetCd,
				reportGb : $("input[name='check']:checked").val()
			},

			success: function(data) {
				console.log(data);
				if (data >= 5) {
					window.location.replace("http://localhost:8080");
				} else {
					reportModalcls();
					setMessage("🚨 신고가 접수되었습니다. 🚨");
					showModal();
					setTimeout(hideModal, 700);
				}
			},
			error : function(){
				reportModalcls();
		 		setMessage("⚠️ 이미 접수된 신고입니다.");
		 		showModal();
		 		setTimeout(hideModal, 700);
			}
		});
	   }
	});
});
