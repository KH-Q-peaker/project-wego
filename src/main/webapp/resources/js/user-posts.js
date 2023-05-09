
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
