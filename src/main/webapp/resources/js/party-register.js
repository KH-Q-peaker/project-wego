const mountains = [
  "가리산",
  "가리왕산",
  "가야산",
  "가지산",
  "감악산",
  "강천산",
  "계룡산",
  "계방산",
  "공작산",
  "관악산",
  "구병산",
  "금산",
  "금수산",
  "금오산",
  "금정산",
  "깃대봉",
  "남산",
  "내연산",
  "내장산",
  "대둔산",
  "대암산",
  "대야산",
  "덕숭산(수덕산)",
  "덕유산(향적봉)",
  "덕항산",
  "도락산",
  "도봉산(자운봉)",
  "두륜산",
  "두타산",
  "마니산",
  "마이산",
  "명성산",
  "명지산",
  "모악산",
  "무등산",
  "무학산",
  "미륵산",
  "민주지산",
  "방장산",
  "방태산",
  "백덕산",
  "백암산",
  "백운산(광양)",
  "백운산(정선)",
  "백운산(포천)",
  "변산",
  "북한산",
  "비슬산",
  "삼악산",
  "서대산",
  "선운산",
  "설악산",
  "성인봉",
  "소백산",
  "소요산",
  "속리산",
  "신불산",
  "연화산",
  "오대산",
  "오봉산",
  "용문산",
  "용화산",
  "운문산",
  "운악산",
  "운장산",
  "월악산",
  "월출산",
  "유명산",
  "응봉산",
  "장안산",
  "재약산",
  "적상산",
  "점봉산",
  "조계산",
  "주왕산",
  "주흘산",
  "지리산",
  "지리산(통영)",
  "천관산",
  "천마산",
  "천성산",
  "천태산",
  "청량산",
  "추월산",
  "축령산",
  "치악산",
  "칠갑산",
  "태백산",
  "태화산",
  "팔공산",
  "팔봉산",
  "팔영산",
  "한라산",
  "화악산(중봉)",
  "화왕산",
  "황매산",
  "황석산",
  "황악산",
  "황장산",
  "희양산",
];
let imgPath; // 업로드 이미지 임시 저장 변수
const formData = new FormData(); // FormDate

// 통합검색바 제거
removeSearch();

// Enter키로 인한 submit 방지 이벤트
document.forms[0].addEventListener("keydown", (e) => {
  if (e.key === "Enter" && e.target.id !== "text") {
    e.preventDefault();
    return false;
  } // if
});

// select에 산 이름 출력(100대 명산)
mountains.map(
  (value, index) =>
    (selector(
      ".select-mountain select"
    ).innerHTML += `<option value="${value}">${value}</option>`)
);

// 모집글에서 날짜 지정 시 오늘 이전 날짜 지정 방지
const date = new Date();
const today = `${date.getFullYear()}-${
  date.getMonth() < 10 ? "0" + (date.getMonth() + 1) : date.getMonth()
}-${date.getDate() < 10 ? "0" + date.getDate() : date.getDate()}`;
selector("#date").min = today;

// 모집글에서 모집인원범위(2~45)를 벗어날 경우 빨간색 숫자로 변경
selector("input[name=partyMax]").addEventListener("input", (e) => {
  const member = e.target.value;
  if ((member >= 2 && member <= 45) || member === "") {
    e.target.style.color = "black";
  } else {
    e.target.style.color = "red";
  } // if-else
});

// ========================= 추가 이벤트

// 이미지 추가 버튼 클릭 이벤트
selector(".photo").addEventListener("click", (e) => {
  // 추가한 이미지 업로드 취소 시 이벤트
  if (e.target.className === "img-upload-cancle") {
    e.stopPropagation();
    uploadImgRemove();
  } else {
    selector(".drag-and-drop").innerHTML = `
  <div class="picture"></div>
  <p>
    여기로 이미지를 드래그하거나<br />
    파일을 업로드 하세요.
    (최대 20MB)
  </p>`;

    selector(".add-photo").style.display = "block";
    $(".add-photo").attr("tabindex", -1).focus();
  } // if
});

// ========================= 취소 이벤트

// 모집글 작성 취소 버튼 이벤트
selector("#cancle").addEventListener("click", () => {
  selector(".check-again .unload").style.display = "block";
  $(".check-again .unload").attr("tabindex", -1).focus();
});

// 등록 취소 버튼 클릭 이벤트
selector(".check-again .upload input[type=button]").addEventListener(
  "click",
  () => {
    selector(".check-again .upload").style.display = "none";
  }
);

// 이미지 추가 취소 버튼 클릭 이벤트
selector(".add-photo .cancel").addEventListener("click", () => {
  selector(".add-photo").style.display = "none";
  imgPath = null;
});

// 삭제 취소 버튼 클릭 이벤트
selector(".check-again .unload input[type=button]").addEventListener(
  "click",
  () => {
    selector(".check-again .unload").style.display = "none";
  }
);

// 작성 취소 -> 예(삭제, Red Button) 버튼 클릭 이벤트
selector(".check-again .unload input[type=reset]").addEventListener(
  "click",
  () => {
    if (selector("#upload").innerText == "수정") {
      self.location = `/party/${selector("input[name=sanPartyId]").value}`;
    } else {
      self.location = "/party";
    } /// if-else
  }
); // .addEventListener

// 업로드 이미지 삭제
const uploadImgRemove = () => {
  selector(".check-again .unload").style.display = "none";
  const elem = selector(".photo");

  while (elem.firstChild) {
    elem.removeChild(elem.firstChild);
  } // while
};

// ========================= 드래그 앤 드롭 이벤트

// 드래그 앤 드롭으로 파일 업로드 하기 위한 기본 이벤트 방지
selector(".drag-and-drop").ondragover = (e) => e.preventDefault();
selector(".drag-and-drop").ondragleave = (e) => e.preventDefault();

// 드래그 앤 드롭 이벤트 - 업로드 최대 용량: 20,971,520byte(20MB)
selector(".drag-and-drop").ondrop = (e) => {
  e.preventDefault();

  const files = [...e.dataTransfer?.files];

  // 업로드 파일 용량 체크
  if (isFileMaxSize(files)) {
    return false;
  } // if

  // 파일형식 체크
  if (isWrongFile(files)) {
    return false;
  } // if

  // 위 조건을 모두 통과할 경우 아래 코드 수행

  // 기존 이미지 파일이 있는 경우, 새 이미지로 덮어쓰기 위한 set 사용
  formData.set("imgFile", files[0]);

  selector(".drag-and-drop").innerHTML = `<p>${files[0].name}</p>`; // 파일명 표시

  handleUpdate([...files]);
};

// ========================= 업로드 파일 용량 및 형식 이벤트

// 업로드 파일 용량 체크
const isFileMaxSize = (file) => {
  if (file[0].size > 20971520) {
    selector(".drag-and-drop").innerHTML = `
    <p>최대 업로드 용량은 20MB입니다.<br>
    현재 파일의 용량은 ${Math.floor((file[0].size / 1048576) * 10) / 10}입니다.
    </p>`;

    return true;
  } // if
};

// 파일형식 체크
const isWrongFile = (file) => {
  if (
    file[0].type !== "image/jpeg" &&
    file[0].type !== "image/png" &&
    file[0].type !== "image/jpg"
  ) {
    selector(".drag-and-drop").innerHTML = `
    <p>업로드 가능한 파일 형식은<br>
    .jpg, .jpeg, .png입니다.
    </p>`;

    return true;
  } // if

  return false;
};

// 드래그 앤 드롭 대신 클릭으로 업로드 할 때
selector(".drag-and-drop").onclick = () => {
  const input = document.createElement("input");
  input.id = "test";
  input.type = "file";
  input.accept = ".jpg, .jpeg, .png";

  input.click();

  input.addEventListener("input", (e) => {
    const files = e.target.files;

    // 업로드 파일 용량 체크
    if (isFileMaxSize(files)) {
      return false;
    } // if

    // 파일형식 체크
    if (isWrongFile(files)) {
      return false;
    } // if

    // 기존 이미지 파일이 있는 경우, 새 이미지로 덮어쓰기 위한 set 사용
    formData.set("imgFile", files[0]);

    selector(".drag-and-drop").innerHTML = `<p>${files[0].name}</p>`; // 파일명 표시

    handleUpdate([...files]);
  });
};

// 업로드 이미지를 화면에 썸네일처럼 보여주기 위한 imgPath에 담기
const handleUpdate = (files) => {
  files.forEach((file) => {
    const reader = new FileReader();

    reader.addEventListener("load", (e) => {
      imgPath = `<img src="${e.target.result}" value="${e.target.result}" alt="${files[0].name}"></img>`;
    });

    reader.readAsDataURL(file);
  });
};

// 이미지 추가 -> 등록 버튼 클릭 이벤트
selector(".drag-and-drop + button").onclick = (e) => {
  if (imgPath === undefined || imgPath === null) {
    return false;
  } // if

  selector(".add-photo").style.display = "none";
  selector(".photo").innerHTML =
    imgPath + `<button type="button" class="img-upload-cancle"></button>`;

  imgPath = null;
};

// 등록 버튼 클릭 시 폼 검증 이벤트
selector("#upload").onclick = (e) => {
  e.preventDefault();

  if (formCheck()) {
    // 등록 여부를 재확인 하는 모달창을 보여주는 이벤트
    selector(".check-again .upload").style.display = "block";
    $(".check-again .upload").attr("tabindex", -1).focus();
  } // if
};

// 필수입력사항 안내 모달창 이벤트
const alert = () => {
  selector(".alert-window").style.display = "flex";
  $(".alert-window").attr("tabindex", -1).focus();
};

// 필수입력사항 안내창 확인 클릭 이벤트
const alertWindow = (item) =>
  (selector(".alert-window button").onclick = () => {
    selector(".alert-window").style.display = "none";
    item.focus();
  });

// 모집 글 작성 폼 검증
const formCheck = () => {
  const form = document.forms[0];
  const partyDt =
    selector("#date").value + " " + selector("#time").value + ":00";
  const now = new Date();
  const time = `${
    now.getHours() < 10 ? "0" + now.getHours() : now.getHours()
  }:${now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()}`;

  if (form.elements.sanName.value === "") {
    alert();
    alertWindow(selector("select[name=sanName]"));

    return false;
  } // if

  if (form.elements.title.value.length < 2) {
    alert();
    alertWindow(selector("#title"));

    return false;
  } // if

  if (form.elements.date.value === "" || form.elements.date.value < today) {
    alert();
    alertWindow(selector("#date"));

    return false;
  } // if
  
  if (
    form.elements.time.value === "" ||
    (form.elements.date.value === today && form.elements.time.value < time)
  ) {
    alert();
    alertWindow(selector("#time"));

    return false;
  } // if

  if (form.elements.partyMax.value < 2 || form.elements.partyMax.value > 45) {
    alert();
    alertWindow(selector("#member"));

    return false;
  } // if

  // 위 필수입력값이 모두 입력되었다면 폼 데이터에 저장
  // 이미지는 업로드와 동시에 바로 폼 데이터에 저장
  if (selector("#upload").innerText == "수정") {
    // input:hidden으로 수정할 모집글 번호를 전송
    formData.set("sanPartyId", form.elements.sanPartyId.value);
  } // if

  if (selector(".photo img") === null) {
    // 폼 데이터 전송 전 사용자가 이미지 추가를 취소했을 경우
    formData.delete("imgFile");
  } // if

  formData.set("sanName", form.elements.sanName.value);
  formData.set("title", form.elements.title.value);
  formData.set("contents", form.elements.contents.value);
  formData.set("partyDt", partyDt);
  formData.set("partyMax", form.elements.partyMax.value);
  formData.set("items", form.elements.items.value);
  formData.set("condition", form.elements.condition.value);

  return true;
};

// 등록 여부 재확인 -> 예(폼 전송) 이벤트
selector(".upload input[type=submit]").onclick = (e) => {
  e.preventDefault();

  e.target.disabled = true;

  fetch(
    selector("#upload").innerText == "등록"
      ? "/party/register"
      : "/party/modify",
    {
      method: "POST",
      body: formData,
    }
  )
    .then((res) => {
      if (res.url.includes("login")) {
        self.location = res.url;
      } // if
      return res.json();
    })
    .then((resBody) => {
      if (resBody.state === "failed") {
        selector(".check-again .upload").style.display = "none";
        e.target.disabled = false;

        return formCheck();
      } // if

      if (resBody.state === "successed") {
        self.location = resBody.redirectUrl;
      } // if
    })
    .catch((error) => {
      console.error("error: ", error);
    });
};
