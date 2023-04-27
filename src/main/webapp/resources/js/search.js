selector(".recruit .wrap-recruit").addEventListener("wheel", (e) => {
  if (window.innerWidth < 1000 && window.innerWidth > 480) {
    e.preventDefault();
    e.currentTarget.scrollLeft += e.deltaY;
  } // if
});

// top (scroll이 200이상 일때 top버튼 노출)
$(window).scroll(function () {
  if ($(this).scrollTop() > 100) {
    $(".scrollToTop, .chat").fadeIn();
  } else {
    $(".scrollToTop, .chat").fadeOut();
  } // if-else
});

// 위로 올라가는 부드러운 애니메이션
$(".scrollToTop, .chat").click(function () {
  $("html, body").animate({ scrollTop: 0 }, 400);
  return false;
});


// URI에서 검색어 추출
const search = window.location.search;
const decode = decodeURI(search);
const searchWord = decode.slice(search.indexOf("=") + 1);

// 산정보 및 후기 내용에서 검색어와 일치하는 단어 강조(bold)처리
const mountainInfo = document.querySelectorAll(".mountain-info #itemContent");
const review = document.querySelectorAll(".review #itemContent");

function cutSearchWord(contents, searchWord) {
  contents.forEach(item => {

  let findIndex = item.innerText.indexOf(searchWord);
  
  if(item.innerText.length > 200) {
    let cutContent = item.innerText.slice(findIndex / 2);
    item.innerHTML = cutContent.replace(searchWord, `<strong>${searchWord}</strong>`);
  } else {
    item.innerHTML = item.innerText.replace(searchWord, `<strong>${searchWord}</strong>`);
  } // if-else
});
} // cutSearchWord

cutSearchWord(mountainInfo, searchWord);
cutSearchWord(review, searchWord);

// 모집글은 자르기 없이 보여지는 제목에 강조(bold)처리
const recruitTitle = document.querySelectorAll(".recruit-title");
recruitTitle.forEach(item => {
    item.innerHTML = item.innerText.replace(searchWord, `<strong>${searchWord}</strong>`)
});