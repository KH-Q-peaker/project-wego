// 토글존

const toggleList = document.querySelectorAll(".toggleSwitch");
const toggleIcon = document.querySelectorAll(".toggleIcon");

toggleList.forEach(($toggle) => {
  $toggle.onclick = () => {
    var motherNode = $toggle.parentElement;
    if (motherNode.className == $(".all")) {
    } else {
      $toggle.classList.toggle("active");
      motherNode.classList.toggle("active");
    }
  };
});

// 전체 알림 ON / OFF toggleSwitch를 눌렀을 때 모든 toggleSwitch가 동일하게 상태를 변경

$(document).ready(function () {
  $(".all .toggleIcon")
    .off("click")
    .on("click", function () {
      var isActiveAll = $(".all .toggleIcon").hasClass("active");
      $(".toggleIcon").toggleClass("active", isActiveAll);
      $(".toggleSwitch").toggleClass("active", isActiveAll);
      $(".onOfftext").toggleClass("active", isActiveAll);
    });
});

var alarmSet = document.querySelector("#alarm-set");
var alarmImg = document.querySelector(".alarm-img");

$(".alarmSetbut")
  .off("click")
  .on("click", function () {
    alarmSet.style.display = "block";
    $("body").addClass("active"); // 모달이 열릴 때 body에 .active 클래스 추가
    $("#alarm-set").animate({ opacity: "1" }, 400);
    $(".toggleSwitch").animate({ opacity: "1" }, 400);
    $(".onOfftext").animate({ opacity: "1" }, 1200);
  });

alarmImg.onclick = function () {
  $("body").toggleClass("active"); // 모달이 닫힐 때 body의 .active 클래스 토글
  alarmSet.classList.toggle("active");
};

alarmImg.addEventListener("click", function (event) {
  // 클릭된 요소와 이벤트 객체 가져오기
  const clickedElement = event.target;
  const eventPosition = {
    x: event.clientX,
    y: event.clientY,
  };

  // #alarm-set 위치 계산하기
  const alarmSetHeight = alarmSet.getBoundingClientRect().height;
  const windowHeight = window.innerHeight;
  const positionTop = Math.min(
    eventPosition.y + 10,
    windowHeight - alarmSetHeight - 10
  );

  // #alarm-set 위치 지정하기
  alarmSet.style.top = positionTop - 100 + "px";
  alarmSet.style.left = "auto";
  alarmSet.style.display = "block";
});

function clickOutsideHandler(event) {
  if (!alarmSet.contains(event.target) && event.target !== alarmImg) {
    alarmSet.style.display = "none";
    alarmSet.classList.remove("active");
    $("body").removeClass("active");
  } else {
    $("body").removeClass("active");
  }
}
document.addEventListener("click", clickOutsideHandler);

function closeAlarmSet() {
  alarmSet.style.display = "none";
  alarmSet.classList.remove("active");
  document.removeEventListener("click", clickOutsideHandler);
  $("body").removeClass("active");
}
// 그외 클릭시 나가기.
$(".closeButton")
  .off("click")
  .on("click", function () {
    closeAlarmSet();
  });
