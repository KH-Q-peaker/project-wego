if (typeof currPageNum1 == "undefined") {
  const currPageNum1 = document.getElementById("currPageNum");
  var commentCurrPage = currPageNum1.getAttribute("data-curr-page");
  if (commentCurrPage == 1) {
    $(".commentPage1").addClass("currPage");
  }

  function selectClickCurrPage1() {
    var currPage = event.target.innerText;
    $.ajax({
      type: "get",
      url: "/profile/comment",
      data: { currPage: currPage, userId: userId },
      success: function (data) {
        $(".cotentsbox").load(
          "/profile/comment?userId=" + userId + "&currPage=" + currPage
        );
      }, //success
    }); //ajax
  } //selectClickCurrPage

  function selectClickCurrPagePrev1() {
    var currPage = $("#currPagePrev").val();
    $.ajax({
      type: "get",
      url: "/profile/comment",
      data: { currPage: currPage, userId: userId },
      success: function (data) {
        $(".cotentsbox").load(
          "/profile/comment?userId=" + userId + "&currPage=" + currPage
        );
      }, //success
    }); //ajax
  } //selectClickCurrPagePrev

  function selectClickCurrPageNext1() {
    var currPage = $("#currPageNext").val();
    $.ajax({
      type: "get",
      url: "/profile/comment",
      data: { currPage: currPage, userId: userId },
      success: function (data) {
        $(".cotentsbox").load(
          "/profile/comment?userId=" + userId + "&currPage=" + currPage
        );
      }, //success
    }); //ajax
  } //selectClickCurrPageNext
}
