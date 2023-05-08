if (typeof currPageNum1 == "undefined") {
  const currPageNum1 = document.getElementById("currPageNum");
  var userPostsCurrPage = currPageNum1.getAttribute("data-curr-page");
  console.log("userPostsCurrPage:{}", userPostsCurrPage);
  if (userPostsCurrPage == 1) {
    $(".userPostsPage1").addClass("currPage");
  }

  function selectClickCurrPage() {
    var currPage = event.target.innerText;
    $.ajax({
      type: "get",
      url: "/profile/userposts",
      data: { currPage: currPage, userId: userId },
      success: function (data) {
        $(".cotentsbox").load(
          "/profile/userposts?userId=" + userId + "&currPage=" + currPage
        );
      }, //success
    }); //ajax
  } //selectClickCurrPage

  function selectClickCurrPagePrev() {
    var currPage = $("#currPagePrev").val();
    $.ajax({
      type: "get",
      url: "/profile/userposts",
      data: { currPage: currPage, userId: userId },
      success: function (data) {
        $(".cotentsbox").load(
          "/profile/userposts?userId=" + userId + "&currPage=" + currPage
        );
      }, //success
    }); //ajax
  } //selectClickCurrPagePrev

  function selectClickCurrPageNext() {
    var currPage = $("#currPageNext").val();
    $.ajax({
      type: "get",
      url: "/profile/userposts",
      data: { currPage: currPage, userId: userId },
      success: function (data) {
        $(".cotentsbox").load(
          "/profile/userposts?userId=" + userId + "&currPage=" + currPage
        );
      }, //success
    }); //ajax
  } //selectClickCurrPageNext
}
