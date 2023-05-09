removeSearch();

// 상대시간 똑똑하게 나타내기 ~ 일 전~
$(document).ready(function () {
  $("time.timeago").timeago();
});

setFooterPosition();

var userId = document.getElementById("user-info").dataset.userId;
console.log(userId);

var socket = null;
$(document).ready(function () {
  console.log("시작. ");
  connectWs();
});

function connectWs() {
  console.log(window.location);
  //var socket = new SockJS("/notification");
  //let contextPath = window.location.host;
  //var socket = new WebSocket("ws://" + contextPath + "/notification");
  //var socket = new WebSocket("ws://" + location.host + "/notification");
  //  var socket = new WebSocket("ws://localhost:8080/notification");

  var socket = new WebSocket("wss://ws.postman-echo.com/raw");

  socket.onopen = () => {
    console.log("WebSocket 연결이 열렸습니다");
  };

  socket.onmessage = (event) => {
    console.log("\n WebSocket 메시지 수신:", event.data);

    socket.send("Hello, server!");
    
    var message = event.data;
  	console.log("서버로부터 메시지를 수신했습니다: " + message);
    const alarm = JSON.parse(event.data);
  	var message = alarm.contents;
  	alert(message);

    if (alarm.type === "comment") {
      const message = `${alarm.postTitle}에 새 댓글이 달렸습니다.`;
      alert(message);
    }
  };

  socket.onclose = (event) => {
    console.log("WebSocket 연결이 닫혔습니다.");
  };

  socket.onerror = (error) => {
      console.error("WebSocket 오류가 발생했습니다: " + error);
  };
} //소켓끝

// 알림 삭제
var hideDeleteModal = function () {
  $("body").removeClass("active");
  $(".deleteModal").hide("fast");
};

var showDeleteModal = function () {
  $(".deleteModal").show("fast").css("display", "grid");
  $(document)
    .off("mouseup")
    .on("mouseup", function (e) {
      /* 외부 영역 클릭 시 닫기 */ if (
        $(".deleteModal").has(e.target).length === 0
      ) {
        hideDeleteModal();
      }
    });
  $(document)
    .off("keydown")
    .on("keydown", function (e) {
      /* esc입력시 닫기 */ var code = e.keyCode || e.which;

      if (code == 27) {
        hideDeleteModal();
      }
    });
  $(".delcancle")
    .off("click")
    .on("click", function () {
      /* 취소 클릭 시 닫기  */ hideDeleteModal();
    });
};

$(document).ready(function () {
  $(".deletealarm").click(function () {
    var alarmId = $(this).data("alarmid");
    $("body").addClass("active");
    showDeleteModal();
    let url = "/notification/" + alarmId;
    let currentUrl = window.location.href;
    $(".del")
      .off("click")
      .on("click", function () {
        $.ajax({
          url: url,
          type: "DELETE",
          success: function (data) {
            hideDeleteModal();
            setTimeout(function () {
              window.location.replace(currentUrl);
            }, 700);
          },
          error: function () {
            hideDeleteModal();
            setTimeout(hideModal, 700);
          },
        });
      });
  });
});

// 알림읽음
function markNotificationAsRead(alarmId) {
  let currentUrl = window.location.href;

  $.ajax({
    type: "PATCH",
    url: "/notification/" + alarmId + "/read",
    success: function () {
      setTimeout(function () {
        window.location.replace(currentUrl);
      }, 700);
    },
    error: function () {
      alert("err");
    },
  });
}
