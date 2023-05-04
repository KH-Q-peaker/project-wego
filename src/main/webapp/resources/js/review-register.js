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

// select에 산 이름 출력(100대 명산)
mountains.map(
  (value, index) =>
    (selector(
      ".select-mountain select"
    ).innerHTML += `<option value="${value}">${value}</option>`)
);

// Enter키로 인한 submit 방지 이벤트
document.forms[0].addEventListener("keydown", (e) => {
  if (e.key === "Enter" && e.target.id !== "contents") {
    e.preventDefault();
    return false;
  } // if
});

// 통합검색바 제거
removeSearch();

// ========================= 취소 이벤트

// 모집글 작성 취소 버튼 이벤트
selector("#cancle").addEventListener("click", () => {
  selector(".check-again .unload").style.display = "block";
  $(".check-again .unload").attr("tabindex", -1).focus();
});

// 작성 취소 -> 예(삭제, Red Button) 버튼 클릭 이벤트
selector(".check-again .unload input[type=reset]").addEventListener(
  "click",
  () => {
    if (selector("#upload").innerText == "수정") {
      self.location = `/review/${selector("input[name=sanReviewId]").value}`;
    } else {
      self.location = "/review";
    } /// if-else
  }
); // .addEventListener

// 삭제 취소 버튼 클릭 이벤트
selector(".check-again .unload input[type=button]").addEventListener(
  "click",
  () => {
    selector(".check-again .unload").style.display = "none";
  }
);

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

// ========================= 추가 이벤트

// 이미지 추가 버튼 클릭 이벤트(후기는 최대 5장 추가 가능)
selector(".photo").addEventListener("click", () => {
  // TODO: 현재 이미지 개수를 체크하는 로직 필요
  if (imgCount() < 5) {
    selector(".drag-and-drop").innerHTML = `
    <div class="picture"></div>
    <p>
    여기로 이미지를 드래그하거나<br />
    파일을 업로드 하세요.
    (최대 20MB)
    </p>`;
  } else {
    selector(".drag-and-drop").innerHTML = `
    <div class="picture"></div>
    <p>
    최대 업로드 가능한<br />
    이미지는 5개입니다.
    </p>`;

    selector(".drag-and-drop + button").innerText = "확인";
  } // if-else

  selector(".add-photo").style.display = "block";
  $(".add-photo").attr("tabindex", -1).focus();
});

// ========================= 이미지 개수 체크
function imgCount() {
  let count = 0;

  selector("#contents").childNodes.forEach((item) => {
    if (item.nodeName == "IMG") {
      count++;
    } // if
  });

  return count;
} // imgCount

// ========================= 1500자까지 입력
document.querySelector("#contents").addEventListener("input", (e) => {
  if (e.target.innerText.length > 1500) {
    document.getSelection().removeAllRanges();
    cutText(e.target.innerText, 1500);
    // return false;
  } // if
});

async function cutText(text, length) {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(text.slice(0, length));
    }, 1000);
  });
}

// ========================= 이미지 업로드 이벤트(드래그 드롭 & 클릭)

// ========================= 드래그 앤 드롭 이벤트

// 드래그 앤 드롭으로 파일 업로드 하기 위한 기본 이벤트 방지
selector(".drag-and-drop").ondragover = (e) => e.preventDefault();
selector(".drag-and-drop").ondragleave = (e) => e.preventDefault();

// 드래그 앤 드롭 이벤트 - 업로드 최대 용량: 20,971,520byte(20MB)
selector(".drag-and-drop").ondrop = (e) => {
  e.preventDefault();

  if (imgCount() < 5) {
    const files = [...e.dataTransfer?.files];

    // 업로드 파일 용량 체크
    if (isFileMaxSize(files)) {
      return false;
    } // if

    // 파일형식 체크
    if (isWrongFile(files)) {
      return false;
    } // if

    selector(".drag-and-drop").innerHTML = `<p>${files[0].name}</p>`; // 파일명 표시

    handleUpdate([...files]);
  } // if
};

// 드래그 앤 드롭 대신 클릭으로 업로드 할 때
selector(".drag-and-drop").onclick = () => {
  if (imgCount() < 5) {
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

      selector(".drag-and-drop").innerHTML = `<p>${e.target.files[0].name}</p>`; // 파일명 표시

      handleUpdate([...files]);
    });
  } // if
};

// 이미지 추가 -> 등록 버튼 클릭 이벤트
selector(".drag-and-drop + button").onclick = (e) => {
  if (
    (imgPath === undefined || imgPath === null) &&
    e.target.innerText !== "확인"
  ) {
    return false;
  } // if

  selector(".add-photo").style.display = "none";
  selector("#contents").innerHTML += `${imgPath}<div><br></div>`;

  imgPath = null;
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

// 업로드 이미지를 화면에 썸네일처럼 보여주기 위한 imgPath에 담기
const imgFiles = {}; // 업로드 이미지들을 저장할 객체
let imgPath; // 업로드 이미지 임시 저장 변수
const handleUpdate = (files) => {
  imgFiles[`${files[0].name}`] = files[0];

  files.forEach((file) => {
    const reader = new FileReader();

    reader.addEventListener("load", (e) => {
      imgPath = `<img src="${e.target.result}" alt="${files[0].name}"></img><br>`;
    });

    reader.readAsDataURL(file);
  });
};

// ========================= 폼 데이터
const formData = new FormData(); // FormDate

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

  if (selector("#contents").innerText.length < 2) {
    alert();
    alertWindow(selector("#contents"));

    return false;
  } // if

  // 위 필수입력값이 모두 입력되었다면 폼 데이터에 저장
  // 내용을 순회하면서 img태그의 위치에 <img>삽입
  const contentChildNodes = selector("#contents").childNodes;
  let contentResult = ""; // formData에 contents로 담을 텍스트
  const imgFileNames = []; // 이미지 파일명 저장(중간 제거된 이미지 유무를 위함)
  const oldImgFiles = []; // 기존 이미지 파일 체크
  const imgOrder = [];

  for (let index = 0; index < contentChildNodes.length; index++) {
    if (contentChildNodes[index].childNodes.length > 0) {
      write(contentChildNodes[index].childNodes);
    } else {
      write(new Array(contentChildNodes[index]));
    } // if-else
  } // for

  function write(nodes) {
    for (let index = 0; index < nodes.length; index++) {
      if (nodes[index].nodeName === "IMG") {
        contentResult += "<img>";

        if (nodes[index].src.slice(0, 4) === "data") {
          // 신규 이미지로 판단
          imgFileNames.push(nodes[index].alt);
          imgOrder.push(nodes[index].alt);
        } else {
          // 기존 이미지로 판단
          oldImgFiles.push(nodes[index].alt);
          imgOrder.push(nodes[index].alt);
        } // if-else
      } // if

      if (nodes[index].nodeName === "#text") {
        contentResult += nodes[index].nodeValue + "\n";
        continue;
      } // if

      if (nodes[index].nodeName === "BR") {
        contentResult += "\n";
      } // if
    } // for
  } // write

  // 폼 데이터 저장(산이름, 제목, 내용)
  if (selector("#upload").innerText == "수정") {
    // input:hidden으로 수정할 후기글 번호를 전송
    formData.set("sanReviewId", form.elements.sanReviewId.value);
    // 수정할 때만 기존 이미지 정보 및 이미지 순서를 보냄
    formData.set("oldImgFiles", oldImgFiles);
    formData.set("imgOrder", imgOrder);
  } // if

  formData.set("sanName", form.elements.sanName.value);
  formData.set("title", form.elements.title.value);
  formData.set("contents", contentResult);

  for (let key in imgFiles) {
    if (imgFileNames.includes(imgFiles[key].name)) {
      formData.append("imgFiles", imgFiles[key]);
    } // if
  } // for

  return true;
};

// 등록 여부 재확인 -> 예(폼 전송) 이벤트
selector(".upload input[type=submit]").onclick = (e) => {
  e.preventDefault();

  e.target.disabled = true;

  fetch(
    selector("#upload").innerText == "등록"
      ? "/review/register"
      : "/review/modify",
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

// 후기글 수정 시 이미지 경로 삽입
window.onload = function () {
  if (selector("#upload").innerText == "수정") {
    let order = 0;
    selector("#contents").childNodes.forEach((item) => {
      if (item.nodeName === "IMG") {
        item.src = fileList[order];
        item.alt = fileAltList[order];
        order++;
      } // if
    });
  }
};
