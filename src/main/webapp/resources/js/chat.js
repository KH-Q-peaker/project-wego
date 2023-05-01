
function scrollChatDisplay() {
		$(".display-chatting").scrollTop($(".display-chatting").prop("scrollHeight"));
}


$(() => {
	
	$(document).off('mouseup').on('mouseup', function(e) { /* 외부 영역 클릭 시 닫기 */
		if ($("#chat").has(e.target).length === 0) {
			$("#chat").hide("fast");
		}
	});
	$(document).off('keydown').on('keydown', function(e) {/* esc입력시 닫기 */
		var code = e.keyCode || e.which;

		if (code == 27) { $("#chat").hide("fast"); }
	});
	scrollChatDisplay();
});


function sendMessage() {
	
	if($("#inputChatting").val() != ""){

		const chatMessage = {
			"userId": sessionUserId,
			"chatRoomId": chatRoomId,
			"contents": $("#inputChatting").val()
		};
	chatSocket.send(JSON.stringify(chatMessage));
	$("#inputChatting").val("");
	}
}


$("#send").on('click', sendMessage);
$('#inputChatting').keypress(function (e) {
	
  if (e.keyCode == 13 && !e.shiftKey) { 
    e.preventDefault();
    sendMessage(); 
  }
});

chatSocket.onmessage = function(e) {
	
	console.log('onmessage');
    const chatMessage = JSON.parse(e.data); 
 
    const li = $("<li>");
    const p = $("<div>").addClass("chat").html(chatMessage.contents);
    const span = $("<span>").addClass("chatDate").text(getCurrentTime());

    if (chatMessage.userId == sessionUserId) {
        li.addClass("myChat").append(span, p);
    } else {
		let userInfo = $("<div>").html(chatMessage.userId);
		li.html(userInfo).append(p, span);
    }
    $(".display-chatting").append(li);
    scrollChatDisplay();
};

function getCurrentTime() {
	
	const now = new Date();
	const hours = now.getHours().toString().padStart(2, '0');
	const minutes = now.getMinutes().toString().padStart(2, '0');
	const seconds = now.getSeconds().toString().padStart(2, '0');
	
	return `${hours}:${minutes}:${seconds}`;
}
