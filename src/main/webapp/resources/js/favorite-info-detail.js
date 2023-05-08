const favoriteBtn = document.querySelector(".favorite");

favoriteBtn.addEventListener("click", (e) => {
  // 클릭한 버튼이 좋아요 버튼이면
  if (
    e.target.className === "favorite" ||
    e.target.className === "favorite on"
  ) {
	const formData = new FormData();
	
    formData.set("targetGb", "SAN_INFO");
      
	const sanInfoId = document.getElementById("sanInfoId").value;
    formData.set("targetCd", sanInfoId);

	const favoriteCount = $(e.target).next()[0];
	
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
  }).then((res) => {
    if (res.url.includes("login")) {
      self.location = res.url;
    } // if
  });
}
