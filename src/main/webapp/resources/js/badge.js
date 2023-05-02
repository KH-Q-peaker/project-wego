
$(() => {

    // ===============================================================================
    removeSearch(); // header.js

    if (authUserId === targetUserId) {
        showAllBadge();
    } else {
        removePickSetting()
    } // if-else

    showGetPickStack();
    removeAndAddPickBadge();

    // ===========================================================================init

    // 모달창 열림
    $(".pick5Setting > button").on({
        click: function () {
            document.querySelector(".pick5Setting > button + dialog").showModal();
        }
    }) // showModal

    // 모달창 닫으면 대표선택 초기화
    // 서밋
    $(".pick5Setting > button + dialog").on({
        close: function (e) {
            e.preventDefault();

            let modalButton = document.querySelector(".pick5Setting > button + dialog").returnValue

            // list 가 1개이상일때만 서밋 가능
            if (pickList.length > 0) {
                if (modalButton === "submit") {
                    let targetURL = `modify`;

                    $.ajax({
                        type: "POST",
                        url: targetURL,
                        data: {
                            pickList: pickList
                        },

                        error: function () {
                            alert("err");

                            clearPick(pickList);
                        },

                        success: function () {

                            removeAndAddClassPickByGetList();
                            removeAndAddPickBadge();
                            clearPick(pickList);
                        }
                    });
                } else {
                    clearPick(pickList);
                } // if-else submit or cancle
            } // if
        }
    }) // modal close

    // 대표로 설정할 뱃지의 id를 담을 리스트
    let pickList = Array();

    // 모달에서 대표뱃지선택
    $(document).on(
        'click', "dialog .item", function () {
            if ($(this).hasClass("pick")) {
                $(this).removeClass("pick");
                $(this).children(".sanBadge").children("span").remove();

                // 리스트에서 삭제
                let pickItem = $(this).prop("id");

                for (let idx = 0; idx < pickList.length; idx++) {
                    if (pickList[idx] === parseInt(pickItem)) {
                        pickList.splice(idx, 1);
                    } // if
                } //for
            } else {
                // 5개 초과부터 선택안되고 에러메세지 출력
                if ($("dialog .item.pick").length == 5) {
                    $(".settingError").css("display", "block")
                } else {
                    $(this).addClass("pick");

                    // id값을 뱃지리스트에 저장
                    let pickItem = $(this).prop("id");

                    pickList.push(parseInt(pickItem));

                    $(this).children(".sanBadge").append("<span class='dot'></span>");
                } // if-else
            } // if- else

            if (pickList.length > 0) {
                $("dialog form button[value='submit']").css("background-color", "rgba(78, 197, 61, 1)");
                $("dialog form button[value='submit']").css("color", "#fff")
            }
            else {
                $("dialog form button[value='submit']").css("background-color", "#fff")
                $("dialog form button[value='submit']").css("color", "#000")
            }

            for (let idx = 0; idx < pickList.length; idx++) {
                let pickNum = idx + 1;
                let selectedBadgeId = '#' + pickList[idx];

                $(selectedBadgeId).children(".sanBadge").children("span").text(pickNum);
            } //for
        }
    ); // item in modal onClick

    // 누적치 on/off
    $(document).on(
        'click', '.dot', function () {

            if ($(this).next().css('display') == 'block') {
                $(this).next().css('display', 'none');
            } else {
                $(this).next().css('display', 'block');
            } // if-else
        }
    );// stackDot onClick

    $(document).on(
        'click', '.dot + .stackList', function () {

            if ($(this).css('display') == 'block') {
                $(this).css('display', 'none');
            } else {
                $(this).css('display', 'block');
            } // if-else
        }
    ); // stackList onClick

}) // .js

var showAllBadge = () => {
    $("#rank .item").css("display", "block");
    $("#san .item").css("display", "block");
} // showAllBadge

var removePickSetting = () => {
    $(".pick5Setting").remove();
} // showAllBadge

var showGetPickStack = () => {
    $.ajax({
        type: "GET",
        url: `get-list/json/${targetUserId}`,
        error: function () {

        }, // error

        success: function (badgeGetList) {

            addClassGetPickByBadgeGetList(badgeGetList);
            addDocumentStackDotAndList(badgeGetList)
            setFooterPosition(); // footer.js
        } // success
    });
}

var resetGetPick = () => {
    $.ajax({
        type: "GET",
        url: `get-list/json/${targetUserId}`,
        error: function () {

        }, // error

        success: function (badgeGetList) {

            addClassGetPickByBadgeGetList(badgeGetList);
            setFooterPosition(); // footer.js
        } // success
    });
}

var addClassGetPickByBadgeGetList = (badgeGetList) => {
    let preBadgeId = 0;

    for (let idx = 0; idx < badgeGetList.length; idx++) {

        let nowBadgeGetVO = badgeGetList[idx];

        let nowBadgeId = nowBadgeGetVO.badgeId;
        let nowBadgeName = nowBadgeGetVO.badgeName;
        let nowPickBadgeimg = nowBadgeGetVO.img;

        if (preBadgeId != nowBadgeGetVO.badgeId) {
            let getBadgeHTML = `<div class='item' id = '${nowBadgeId}' ><div class="sanBadge" style="background-image: url(${nowPickBadgeimg});"></div><div class='badgeName'>${nowBadgeName}</div></div>`;

            preBadgeId = nowBadgeId;

            $('#rank .item#' + nowBadgeId).css("display", "block");
            $('#rank .item#' + nowBadgeId).addClass("get");
            $('#san .item#' + nowBadgeId).css("display", "block");
            $('#san .item#' + nowBadgeId).addClass("get");

            $(".pick5Setting > button + dialog > .badgeBox").append(getBadgeHTML)

            if (nowBadgeGetVO.status != 'N') {
                $('#rank .item#' + nowBadgeId).addClass("pick");
                $('#san .item#' + nowBadgeId).addClass("pick");
            } // if

        } // if

    } // for
}

var removeAndAddClassPickByGetList = () => {

    $.ajax({
        type: "GET",
        url: `get-list/json/${targetUserId}`,
        error: function () {

        }, // error

        success: function (badgeGetList) {
            let preBadgeId = 0;

            for (let idx = 0; idx < badgeGetList.length; idx++) {

                let nowBadgeGetVO = badgeGetList[idx];
                let nowBadgeId = nowBadgeGetVO.badgeId;

                if (preBadgeId != nowBadgeGetVO.badgeId) {

                    preBadgeId = nowBadgeId;

                    $('#rank .item#' + nowBadgeId).removeClass("pick");
                    $('#san .item#' + nowBadgeId).removeClass("pick");

                    if (nowBadgeGetVO.status != 'N') {
                        $('#rank .item#' + nowBadgeId).addClass("pick");
                        $('#san .item#' + nowBadgeId).addClass("pick");
                    } // if

                } // if

            } // for
        } // success
    });


}



var removeAndAddPickBadge = () => {
    $.ajax({
        type: "GET",
        url: `pick-list/json/${targetUserId}`,
        error: function () {

        }, // error

        success: function (badgePickList) {
            removePickBadge();

            addPickBadge(badgePickList);

            setFooterPosition(); // footer.js
        } // success
    });
}
var removePickBadge = () => {
    $(".badgeBox#pick5").children("div.item").fadeOut(1000, function () {

        $(this).remove();
    });
}
var addPickBadge = (badgePickList) => {
    let preBadgeId = 0;

    for (let idx = 0; idx < badgePickList.length; idx++) {

        let nowPickBadge = badgePickList[idx];
        let nowPickBadgeId = nowPickBadge.badgeId;
        let nowPickBadgeName = nowPickBadge.badgeName;
        let nowPickBadgeimg = nowPickBadge.img;

        if (preBadgeId != nowPickBadgeId) {

            let pcikBadgeHTML = `<div class='item pick' id = '${nowPickBadgeId}' style = 'display : none' > <div class="sanBadge"  style="background-image: url(${nowPickBadgeimg});"></div><div class='badgeName'>${nowPickBadgeName}</div></div>`;

            preBadgeId = nowPickBadgeId;

            $(".badgeBox#pick5").append(pcikBadgeHTML);
            $(".badgeBox#pick5").children("div.item").css("display", "block");
        }


    }
}

var addDocumentStackDotAndList = (badgeGetList) => {
    let preBadgeId = 0;

    let createdDtList = Array();

    for (let idx = 0; idx < badgeGetList.length; idx++) {

        let nowBadgeGetVO = badgeGetList[idx];
        
        let nowBadgeId = nowBadgeGetVO.badgeId;
        let nowCreatedDt = getDateToTimeStamp(nowBadgeGetVO.createdDt);

        if(nowBadgeGetVO.ranking != null){
            nowCreatedDt += ` (${nowBadgeGetVO.ranking})`;
        } // if
        if (preBadgeId != nowBadgeId) {
            
            preBadgeId = nowBadgeId;
            
            createdDtList = Array();
            
            createdDtList.push(nowCreatedDt);

        } else {

            createdDtList.push(nowCreatedDt);

            if (idx == badgeGetList.length - 1 || nowBadgeId != badgeGetList[idx + 1].badgeId) {
                let dotHTML = "<span class='dot'>" + createdDtList.length + "</span>";
                let stackListHTML = "<div class='stackList'><ul><li class=stackTitle>획득내역</li></ul></div>";

                $('#rank .item#' + nowBadgeId).children(".sanBadge").append(dotHTML);
                $('#rank .item#' + nowBadgeId).children(".sanBadge").append(stackListHTML);
                $('#san .item#' + nowBadgeId).children(".sanBadge").append(dotHTML);
                $('#san .item#' + nowBadgeId).children(".sanBadge").append(stackListHTML);


                for (createdDtListIdx = 0; createdDtListIdx < createdDtList.length; createdDtListIdx++) {
                    let sanStackContentsHTML = "<li class='stackContents'>" + createdDtList[createdDtListIdx] + "</li>";
                    let rankingStackContentsHTML = "<li class='stackContents'>" + createdDtList[createdDtListIdx] + "</li>";

                    $('#rank .item#' + nowBadgeId).children(".sanBadge").children(".stackList").children("ul").append(rankingStackContentsHTML);
                    $('#san .item#' + nowBadgeId).children(".sanBadge").children(".stackList").children("ul").append(sanStackContentsHTML);
                } // for

            } // if

        } // if-else

    } // for
}

var getDateToTimeStamp = (timeStamp) => {

    let date = new Date(timeStamp);


    let yyyy = date.getFullYear();
    let MM = date.getMonth() + 1;
    let dd = date.getDate();

    return yyyy + "-" + MM + "-" + dd;
} // getDateToTimeStamp


var clearPick = (pickList) => {
    $("dialog .item").removeClass("pick");
    $(".settingError").css("display", "none");
    $("dialog .item").children(".sanBadge").children("span").remove();
    pickList.length = 0;
    $("dialog form button[value='submit']").css("background-color", "#fff")
    $("dialog form button[value='submit']").css("color", "#000")
};

