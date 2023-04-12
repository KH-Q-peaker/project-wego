
function toggleBtn(inputElem, buttonElem) {
	
  if (inputElem.val() !== '') {
    buttonElem.prop('disabled', false).css({
      'color': 'white',
      'background-color': 'rgb(78, 197, 61)',
    });
  } else {
    buttonElem.prop('disabled', true).css({
      'color': 'gray',
      'background': 'buttonface'
    });
  }
}
function toggleMentionBtn(buttonElem) {
	
	if (buttonElem.parent().next().css('display') == 'none') {
		$('.mentionwrite, .mentionList, .mention').hide();
		buttonElem.val('Ⅹ닫기');
		buttonElem.parent().next('.mentionwrite').show('normal').css('display', 'grid');
		buttonElem.parent().next().next('.mentionList').show().children('.mention').show('fast');
	} else {
		buttonElem.val('↪︎답글');
		$(".mcontents").val('');
		$('.mentionwrite, .mentionList, .mention').hide('fast');
	}
}

$(() => { /* 새 댓글 post 전송  */
	
	$('textarea').off('keydown').on( 'keydown', function (){
   		$(this).css('height', 'auto');
    	$(this).height(this.scrollHeight);
  	});
  
	/* 입력내용에 따라 등록버튼 활성/비활성  */
	$("#contents").off('input').on('input', function(){
		
		toggleBtn($(this), $(this).next());
	});
	
	/* 취소 클릭시 내용 삭제 + 버튼 비활성 */
	$("#cmtwrite").children(".cancle").off('click').on('click', function(){
		$(this).prev().prev().val('');
		$(this).prev().prop('disabled', true).css({
				'color' : 'gray',
				'background' : 'buttonface' 
		});
	});
	$(".ncmt").off('click').on('click', function() {

		$.ajax({
			url: "/comment/register",
			type: "POST",
			data:
			{
				targetGb: target.targetGb,
				targetCd: target.targetCd,
				contents: $(this).prev().val()
			},
			success: function(data) {
				setMessage("💬 댓글이 등록되었습니다.");
				showModal();
				setTimeout(hideModal, 700);
				page=1;
				$(window).off('scroll').on('scroll', scrollCommentLoading);
				$("#contents").val('');
				$(".cmtcontainer").replaceWith(data);
		},
		error : function() {
			setMessage("⚠️ 댓글 등록 실패.");
			showModal();
			setTimeout(hideModal, 700);
		}
		});
	});
});


$(() => { /* 답글 관련 */
	
  /* 답글 버튼 클릭시 멘션 작성 창 on */
  	$(".mentionbtn").off('click').on('click', function () {
		
		$(".men").prop('disabled', true).css({
				'color' : 'gray',
				'background' : 'buttonface' 
		});
		let mentionId = $(this).siblings('#commentId').val();
		let mentionList = $(this).parent().next().next('.mentionList');
		let mentionbtn = $(this);
		
			$.ajax({
				url: "/comment/mention",
				type: "GET",
				data:
				{
					commentId: mentionId
				},
				success: function(data) {
					mentionList.html(data);
					toggleMentionBtn(mentionbtn);
				},
				error: function() {
					console.log('멘션로딩 실패');
				}
			});
//			toggleMentionBtn($(this));
			
		
			/* 등록버튼 클릭 시 멘션 등록 post전송   */
			$(".men").off('click').on('click', function() {

				$.ajax({
					url: "/comment/reply",
					type: "POST",
					data:
					{
						targetGb: target.targetGb,
						targetCd: target.targetCd,
						mentionId: mentionId,
						contents: $(this).prev().val()
					},
					success: function(data) {
						setMessage("️💬 답글이 등록되었습니다.");
						showModal();
						setTimeout(hideModal, 700);
						mentionList.append(data);
						$('.mcontents').val('');
					},
					error: function() {
						setMessage("⚠️ 답글 등록 실패."); // 이거 고쳐ㅕㅕㅕㅕㅕㅕㅕㅕㅕ
						showModal();
						setTimeout(hideModal, 700);
					}
				});
			});
  	});
  	/* 취소 클릭 시 멘션 작성 창 off  */
  	$(".mentionwrite").children(".cancle").off('click').on('click', function(){
		
		$(".mcontents").val('').height('80px');
	});
	
	/* 입력내용에 따라 등록버튼 활성/비활성  */
	$(".mcontents").off('input').on('input', function(){
		
		toggleBtn($(this), $(this).next());
	});
});


$(() => { /* 수정 관련 */
	
	/* 댓글 수정  */
	$(".modifycmt").off('click').on('click', function() {
		
		/* 댓글 수정중일 때 다른 수정버튼 비활성화 */
		$(".modifycmt").prop('disabled', true);
		
		let commentId = $(this).siblings('#commentId').val();
		let target = $(this).parent().next();
		let contents = target.text();
	
		/* 댓글 수정 폼 on */
		target.siblings().not('.cmtuser').not('.cmtuserPic').toggle('fast');
  		
  		let modifying = $("<textarea></textarea>").addClass("comment update")
  						.height(target.height() + 20 +'px')
  						.off('keydown').on('keydown', function(){
					   		$(this).css('height', 'auto');
    						$(this).height(this.scrollHeight - 30 + 'px');
  						});
  		modifying.val(contents);
  		target.replaceWith(modifying);

  		
		/* 댓글 수정 폼 off  */
		$("input[name='updatecls']").off('click').on('click', function() {

			/* 수정 중 취소 눌렀을 때 다른 수정버튼 재활성화  */
			$(".modifycmt").prop('disabled', false);
			
			modifying.replaceWith(target);
			target.siblings().not('.cmtuser').not('.cmtuserPic').toggle('fast');
		});
	
		/* 수정 post전송  */
		$("input[name='updatecmt']").off('click').on('click', function() {
				
			$.ajax({
				url : "/comment",
				type : "PATCH",
				data : 
				JSON.stringify({
					commentId : commentId,
					contents : $(this).parent().siblings(".update").val()
				}),
				contentType :'application/JSON',
				success : function(){
					setMessage("✏️ 댓글이 수정되었습니다.");
		 			showModal();
		 			setTimeout(hideModal, 700);
					modifying.replaceWith(target.text(modifying.val()));
					target.siblings().not('.cmtuser, .cmtuserPic').toggle('fast');
					
					$(".modifycmt").prop('disabled', false);
				},
				error : function(){
		 			setMessage("⚠️ 수정 실패."); // 이거 고쳐ㅕㅕㅕㅕㅕㅕㅕㅕㅕ
		 			showModal();
		 			setTimeout(hideModal, 700);
				}	
			});
		});
	});
});



