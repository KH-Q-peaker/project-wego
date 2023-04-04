window.addEventListener("click", (e) => {
  // 클릭한 버튼이 좋아요 버튼이면
  if (
    e.target.className === "favorite" ||
    e.target.className === "favorite on"
  ) {
    e.preventDefault(); // 상위 a태그로 인한 페이지 이동 방지
    const formData = new FormData(); // FormDate
    // 버튼의 부모의 a태그 href 주소를 가져온다.
    const anchorOfParent = $(e.target).parent().parent().parent()[0].href;
    // href 주소를 사용해서 게시글의 카테고리와 게시글 번호를 추출한다.
    const postCategory = anchorOfParent.slice(
      anchorOfParent.indexOf("?") + 1,
      anchorOfParent.indexOf("=")
    );
    const postId = anchorOfParent.slice(
      anchorOfParent.indexOf("=") + 1,
      anchorOfParent.length
    );
    const favoriteCount = $(e.target).next()[0];

    switch (postCategory) {
      case "sanInfoId":
        formData.set("targetGb", "SAN_INFO");
        break;
      case "sanPartyId":
        formData.set("targetGb", "SAN_PARTY");
        break;
      case "sanReviewId":
        formData.set("targetGb", "SAN_REVIEW");
        break;
    } // switch

    formData.set("targetCd", postId);
    formData.set("userId", 9); // TEST용 USER_ID

    if (e.target.classList.toggle("on")) {
      formData.set("status", "Y");
      favoriteCount.innerText = Number(favoriteCount.innerText) + 1;
    } else {
      formData.set("status", "N");
      favoriteCount.innerText = Number(favoriteCount.innerText) - 1;
    } // if-else

    setTimeout(() => request(formData), 1000);
  } // if
});

function request(formData) {
  fetch("/favorite", {
    method: "POST",
    body: formData,
  });
}
