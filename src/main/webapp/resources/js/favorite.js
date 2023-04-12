window.addEventListener("click", (e) => {
  // 클릭한 버튼이 좋아요 버튼이면
  if (
    e.target.className === "favorite" ||
    e.target.className === "favorite on"
  ) {
    e.preventDefault(); // 상위 a태그로 인한 페이지 이동 방지
    const formData = new FormData(); // FormDate
    
    // 유저 정보가 없을 경우 로그인 페이지로 이동
    if(typeof authJson === "undefined") {
		return self.location = "/login/"
	} // if
	
    formData.set("userId", authJson.userId);
    
    // 버튼의 부모의 a태그 href 주소를 가져온다.
    const anchorOfParent = $(e.target).parent().parent().parent()[0].href;
    // href 주소를 사용해서 게시글의 카테고리와 게시글 번호를 추출한다.
    let startIndex = 0;
<<<<<<< HEAD
    for (let count = 1; count <= 3; count++) {
      startIndex = anchorOfParent.indexOf("/", startIndex + 1);
    } // for
    const postCategory = anchorOfParent.slice(
      startIndex + 1,
      anchorOfParent.indexOf("/", startIndex + 1)
    );
=======
    
    for(let count = 1; count <= 3; count++) {
		startIndex = anchorOfParent.indexOf("/", startIndex + 1);
	} // for
	
    const postCategory = anchorOfParent.slice(startIndex + 1,  
    anchorOfParent.indexOf("/", startIndex + 1));
>>>>>>> 2d1f243221a28d959fa4ecad4cbf0eb8735e65d1
    const postId = anchorOfParent.slice(
      anchorOfParent.lastIndexOf("/") + 1,
      anchorOfParent.length
    );
    const favoriteCount = $(e.target).next()[0];

    switch (postCategory) {
      case "info":
        formData.set("targetGb", "SAN_INFO");
        break;
      case "party":
        formData.set("targetGb", "SAN_PARTY");
        break;
      case "review":
        formData.set("targetGb", "SAN_REVIEW");
        break;
    } // switch

    formData.set("targetCd", postId);
<<<<<<< HEAD
    formData.set("userId", authJson.userId);

    if (e.target.classList.toggle("on")) {
      formData.set("status", "Y");
      favoriteCount.innerText = Number(favoriteCount.innerText) + 1;
    } else {
      formData.set("status", "N");
      favoriteCount.innerText = Number(favoriteCount.innerText) - 1;
    } // if-else

    formData.forEach((value, key) =>
      console.log(`key: ${key}, value: ${value}`)
    );
=======

    if(e.target.classList.toggle("on")) {
		formData.set("status", "Y");
		favoriteCount.innerText = Number(favoriteCount.innerText) + 1
	} else {
		formData.set("status", "N");
		favoriteCount.innerText = Number(favoriteCount.innerText) - 1
	} // if-else
    
    formData.forEach((value, key) => console.log(`key: ${key}, value: ${value}`));
>>>>>>> 2d1f243221a28d959fa4ecad4cbf0eb8735e65d1
    setTimeout(() => request(formData), 1000);
  } // if
});

function request(formData) {
  fetch("/favorite", {
    method: "POST",
    body: formData,
  });
}
