if (typeof area0 == 'undefined') {
  $("document").ready(function () {
    var area0 = [
      "시/도 선택",
      "서울특별시",
      "인천광역시",
      "대전광역시",
      "광주광역시",
      "대구광역시",
      "울산광역시",
      "부산광역시",
      "경기도",
      "강원도",
      "충청북도",
      "충청남도",
      "전라북도",
      "전라남도",
      "경상북도",
      "경상남도",
      "제주도",
    ];
    var area1 = [
      "강남구",
      "강동구",
      "강북구",
      "강서구",
      "관악구",
      "광진구",
      "구로구",
      "금천구",
      "노원구",
      "도봉구",
      "동대문구",
      "동작구",
      "마포구",
      "서대문구",
      "서초구",
      "성동구",
      "성북구",
      "송파구",
      "양천구",
      "영등포구",
      "용산구",
      "은평구",
      "종로구",
      "중구",
      "중랑구",
    ];
    var area2 = [
      "계양구",
      "남구",
      "남동구",
      "동구",
      "부평구",
      "서구",
      "연수구",
      "중구",
      "강화군",
      "옹진군",
    ];
    var area3 = ["대덕구", "동구", "서구", "유성구", "중구"];
    var area4 = ["광산구", "남구", "동구", "북구", "서구"];
    var area5 = [
      "남구",
      "달서구",
      "동구",
      "북구",
      "서구",
      "수성구",
      "중구",
      "달성군",
    ];
    var area6 = ["남구", "동구", "북구", "중구", "울주군"];
    var area7 = [
      "강서구",
      "금정구",
      "남구",
      "동구",
      "동래구",
      "부산진구",
      "북구",
      "사상구",
      "사하구",
      "서구",
      "수영구",
      "연제구",
      "영도구",
      "중구",
      "해운대구",
      "기장군",
    ];
    var area8 = [
      "고양시",
      "과천시",
      "광명시",
      "광주시",
      "구리시",
      "군포시",
      "김포시",
      "남양주시",
      "동두천시",
      "부천시",
      "성남시",
      "수원시",
      "시흥시",
      "안산시",
      "안성시",
      "안양시",
      "양주시",
      "오산시",
      "용인시",
      "의왕시",
      "의정부시",
      "이천시",
      "파주시",
      "평택시",
      "포천시",
      "하남시",
      "화성시",
      "가평군",
      "양평군",
      "여주군",
      "연천군",
    ];
    var area9 = [
      "강릉시",
      "동해시",
      "삼척시",
      "속초시",
      "원주시",
      "춘천시",
      "태백시",
      "고성군",
      "양구군",
      "양양군",
      "영월군",
      "인제군",
      "정선군",
      "철원군",
      "평창군",
      "홍천군",
      "화천군",
      "횡성군",
    ];
    var area10 = [
      "제천시",
      "청주시",
      "충주시",
      "괴산군",
      "단양군",
      "보은군",
      "영동군",
      "옥천군",
      "음성군",
      "증평군",
      "진천군",
      "청원군",
    ];
    var area11 = [
      "계룡시",
      "공주시",
      "논산시",
      "보령시",
      "서산시",
      "아산시",
      "천안시",
      "금산군",
      "당진군",
      "부여군",
      "서천군",
      "연기군",
      "예산군",
      "청양군",
      "태안군",
      "홍성군",
    ];
    var area12 = [
      "군산시",
      "김제시",
      "남원시",
      "익산시",
      "전주시",
      "정읍시",
      "고창군",
      "무주군",
      "부안군",
      "순창군",
      "완주군",
      "임실군",
      "장수군",
      "진안군",
    ];
    var area13 = [
      "광양시",
      "나주시",
      "목포시",
      "순천시",
      "여수시",
      "강진군",
      "고흥군",
      "곡성군",
      "구례군",
      "담양군",
      "무안군",
      "보성군",
      "신안군",
      "영광군",
      "영암군",
      "완도군",
      "장성군",
      "장흥군",
      "진도군",
      "함평군",
      "해남군",
      "화순군",
    ];
    var area14 = [
      "경산시",
      "경주시",
      "구미시",
      "김천시",
      "문경시",
      "상주시",
      "안동시",
      "영주시",
      "영천시",
      "포항시",
      "고령군",
      "군위군",
      "봉화군",
      "성주군",
      "영덕군",
      "영양군",
      "예천군",
      "울릉군",
      "울진군",
      "의성군",
      "청도군",
      "청송군",
      "칠곡군",
    ];
    var area15 = [
      "거제시",
      "김해시",
      "마산시",
      "밀양시",
      "사천시",
      "양산시",
      "진주시",
      "진해시",
      "창원시",
      "통영시",
      "거창군",
      "고성군",
      "남해군",
      "산청군",
      "의령군",
      "창녕군",
      "하동군",
      "함안군",
      "함양군",
      "합천군",
    ];
    var area16 = ["서귀포시", "제주시", "남제주군", "북제주군"];

    var addressValue = document.querySelector("#address").value;
    const addressArray = addressValue.split(' ');
    console.log("시/도: " + addressArray[0] + " 시/구/군: " + addressArray[1]);

    // 시/도 선택 박스 초기화
    $("select[name=sido1]").each(function () {
      $selsido = $(this);
      $.each(eval(area0), function () {
        $selsido.append("<option value='" + this + "'>" + this + "</option>");
      }); //each
      if (addressArray[1]) { //만약, user에게 저장된 주소가 있다면. (시/구/군)
        $selsido.next().append("<option value=''>" + addressArray[1] + "</option>");
      } else { //만약, user에게 저장된 주소가 없다면. (시/구/군)
        $selsido.next().append("<option value=''>시/구/군 선택</option>");
      } //if-else
      if (addressArray[0] && area0.includes(addressArray[0])) {//저장된 주소가 있고, 그 주소가 유효한 주소라면
        $selsido.val(addressArray[0]).prop("selected", true); //디포트 선택값 설정
      }//if
    });//each

    // 시/도 선택시 구/군 설정

    $("select[name=sido1]").change(function () {
      var area =
        "area" + $("option", $(this)).index($("option:selected", $(this))); // 선택지역의 구군 Array
      var $gugun = $(this).next(); // 선택영역 군구 객체
      $("option", $gugun).remove(); // 구군 초기화

      if (area == "area0") $gugun.append("<option value=''>구/군 선택</option>");
      else {
        $.each(eval(area), function () {
          $gugun.append("<option value='" + this + "'>" + this + "</option>");
        });
      }
    });
  });

  // 변경한 내용최종 전송
  let modal1 = document.querySelector(".myinfo-update");
  let upload = document.querySelector("#upload");
  let restart = document.querySelector("#restart");



  restart.addEventListener("click", () => {
    modal1.style.display = "none";
  });

  upload.addEventListener("click", () => {
    modal1.style.display = "block";

    //로그 test
    console.log(
      "My Info 위치 submit test : ",
      document.querySelector("select[name=sido1]").value
    );
    console.log(
      "My Info 시군구 submit test : ",
      document.querySelector("select[name=gugun1]").value
    );
  });


  // 사용자 info정보 나타내기
  const data_san_range = document.querySelector('.radio-items-on');
  var sanRange1 = data_san_range.getAttribute('data-san-range');
  const data_san_taste = document.querySelector('.my-taste');
  var sanTaste1 = data_san_taste.getAttribute('data-san-taste');

  //해당속성을 가지고 있다면 checked로 바꿔주는 함수
  $.fn.radioSelect = function (val) {
    this.each(function () {
      var $this = $(this);
      if ($this.val() == val)
        $this.attr('checked', true);
    });
    return this;
  };

  $(":radio[name='preferences']").radioSelect(sanRange1);
  $(":radio[name='user-taste-pick']").radioSelect(sanTaste1);


  console.log(sanRange1, sanTaste1);

  // 나의 취향 submit
  var setButton = document.querySelector("#setButton");
  var sido1 = document.querySelector("#sido1");
  var gugun1 = document.querySelector("#gugun1");
  var onlyone = document.querySelector(".only-one");
  var userPick = document.querySelector(".userPick");

  setButton.addEventListener('click', function () {
    var form = document.querySelector("#setform");
    var addressText = sido1.value + " " + gugun1.value;

    //주소로 유효하지 않은 값을 선택한 경우, 빈문자열을 주소로 저장. 
    if (addressText.includes("선택")) {
      var addressText = "";
    }

    var address = document.createElement("input");
    address.setAttribute("type", "hidden");
    address.setAttribute("name", "address");
    address.setAttribute("value", addressText);
    form.appendChild(address);

    var radioVal = $('input[name="preferences"]:checked').val();
    var sanRange = document.createElement("input");
    sanRange.setAttribute("type", "hidden");
    sanRange.setAttribute("name", "sanRange");
    sanRange.setAttribute("value", radioVal);
    form.appendChild(sanRange);

    var userPick = $('input[name="user-taste-pick"]:checked').val();
    var sanTaste = document.createElement("input");
    sanTaste.setAttribute("type", "hidden");
    sanTaste.setAttribute("name", "sanTaste");
    sanTaste.setAttribute("value", userPick);
    form.appendChild(sanTaste);

    form.submit();
  });


  //회원탈퇴 모달창
  var withdrawbox = document.querySelector(".withdraw-box");
  var withdrawset = document.querySelector(".myinfo button");
  withdrawset.onclick = function () {
    withdrawbox.classList.toggle("active");
  };
  var closebutton3 = document.querySelector(".closebutton3");
  closebutton3.onclick = function () {
    withdrawbox.className = "withdraw-box";
  };
  var withdwawno = document.querySelector(".withdwaw-no");
  withdwawno.onclick = function () {
    withdrawbox.className = "withdraw-box";
  };

  //회원탈퇴 모달창2
  var withdrawbox2 = document.querySelector(".withdraw-box2");
  var withdwawyes = document.querySelector(".withdwaw-yes");
  withdwawyes.onclick = function () {
    withdrawbox.className = "withdraw-box";
    setTimeout(function () {
      withdrawbox2.classList.toggle("active");
    }, 200);
  };
  var closebutton4 = document.querySelector(".closebutton4");
  closebutton4.onclick = function () {
    withdrawbox2.className = "withdraw-box2";
  };
  var withdwawno2 = document.querySelector(".withdwaw-no2");
  withdwawno2.onclick = function () {
    withdrawbox2.className = "withdraw-box2";
  };

  var tx;
  // 회원탈퇴 입력시 회원탈퇴처리완료/잘못되었다는 문구 출력
  document.querySelector(".withdwaw-yes2").addEventListener("click", function () {
    txObj = document.querySelector("#tx");
    tx = txObj.value;
    var withdraw3h2 = document.getElementById("withdraw3h2");
    if (tx == "회원탈퇴") {
      withdraw3h2.innerHTML = "We go 회원탈퇴 처리가<br>완료되었습니다.";

    } else {
      withdraw3h2.innerHTML = "입력하신 문구가 일치하지 않습니다.<br>다시 입력해주세요.";
    }
  })

  //회원탈퇴 모달창3
  var withdrawbox3 = document.querySelector(".withdraw-box3");
  var withdwawyes2 = document.querySelector(".withdwaw-yes2");
  withdwawyes2.onclick = function () {
    withdrawbox2.className = "withdraw-box2";
    setTimeout(function () {
      withdrawbox3.classList.toggle("active");
    }, 200);
  };
  var closebutton5 = document.querySelector(".closebutton5");
  closebutton5.onclick = function () {
    withdrawbox3.className = "withdraw-box3";
  };
  var withdwawyes3 = document.querySelector(".withdwaw-yes3");
  withdwawyes3.onclick = function () {
    withdrawbox3.className = "withdraw-box3";
  };

}