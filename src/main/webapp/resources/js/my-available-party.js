
   function selectClickAvailableCurrPage() {
        console.log("********", event.target.innerText);
        var currPage = event.target.innerText;
        $.ajax({
            type: 'get',
            url: '/profile/partyList',
            data: { "acri.currPage": currPage },
            success: function (data) {
                $("#availablePartyModule").load("/profile/partyList?currPage=" + currPage);
            }
        });//ajax
    }//selectClickAvailableCurrPage

    function selectClickAvailableCurrPagePrev() {
        var currPage = $('#availcurrPagePrev').val();
        $.ajax({
            type: 'get',
            url: '/profile/partyList',
            data: { "acri.currPage": currPage },
            success: function (data) {
                $("#availablePartyModule").load("/profile/partyList?currPage=" + currPage);
            }
        });//ajax
    }

    function selectClickAvailableCurrPageNext() {
        var currPage = $('#availcurrPageNext').val();
        $.ajax({
            type: 'get',
            url: '/profile/partyList',
            data: { "acri.currPage": currPage },
            success: function (data) {
                $("#availablePartyModule").load("/profile/partyList?currPage=" + currPage);
            }
        });//ajax
    }
