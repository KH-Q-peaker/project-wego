

$(() => {
    // 모달창 열림
    $(".pick5Setting > button").on({
        click: function () {
            // $(this).next().showModal();
            // $(".pick5Setting > button + dialog").showModal(); 
            // 왜 jq로 show만 되냐?
            // document.querySelector(".pick5Setting > button + dialog").showModal();
            document.querySelector(".pick5Setting > button + dialog").showModal();
        }
    }) // showModal

    var clearPick = function () {
        $("dialog .item").removeClass("pick");
        $(".settingError").css("display", "none");
        $("dialog .item").children(".sanBadge").children("span").remove();
        pickList.length = 0;
        $("dialog form button[value='submit'").css("background-color", "#fff")
        $("dialog form button[value='submit'").css("color", "#000")
    };

    // 모달창 닫으면 대표선택 초기화
    // 서밋
    $(".pick5Setting > button + dialog").on({
        close: function (e) {
            e.preventDefault();

            console.log(e);

            console.log(document.querySelector(".pick5Setting > button + dialog").returnValue);

            // list 가 1개이상일때만 서밋 가능
            if (pickList.length > 0) {
                if (document.querySelector(".pick5Setting > button + dialog").returnValue === "submit") {
                    let targetURL = `${targetUserId}`;

                    console.log(pickList)

                    $.ajax({
                        type: "POST",
                        url: targetURL,
                        data: {
                            pickList: pickList
                        },

                        error: function () {
                            // location.reload();
                        },

                        success: function () {
                            let mesg = "";

                            for (let idx = 0; idx < pickList.length; idx++) {
                                mesg += idx + 1 + " : " + pickList[idx] + "\n";
                            }

                            mesg += "저장완료"

                            alert(mesg);

                            // clearPick();

                            location.reload(); 
                        }
                    });
                } else {
                    clearPick();
                } // if-else submit or cancle
            } // if
        }
    }) // modal close

    // 대표로 설정할 뱃지의 id를 담을 리스트
    let pickList = Array();
    // console.log(pickList);

    // 모달에서 대표뱃지선택
    $("dialog .item").on({
        click: function () {
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

                    console.log(pickItem);

                    pickList.push(parseInt(pickItem));

                    console.log(pickList);

                    $(this).children(".sanBadge").append("<span class='dot'></span>");
                } // if-else
            } // if- else

            if (pickList.length > 0) {
                $("dialog form button[value='submit'").css("background-color", "rgba(78, 197, 61, 1)");
                $("dialog form button[value='submit'").css("color", "#fff")
            }
            else {
                $("dialog form button[value='submit'").css("background-color", "#fff")
                $("dialog form button[value='submit'").css("color", "#000")
            }

            for (let idx = 0; idx < pickList.length; idx++) {
                let pickNum = idx + 1;
                let selectedBadgeId = "#" + pickList[idx];

                // console.log(selectedBadgeId);
                // console.log($(selectedBadgeId));

                $(selectedBadgeId).children(".sanBadge").children("span").text(pickNum);
            } //for
        }
    }); // item in modal onClick

    // 누적치 on/off
    $(".dot").on({
        click: function () {
            if ($(this).next().css('display') == 'block') {
                $(this).next().css('display', 'none');
            } else {
                $(this).next().css('display', 'block');
            } // if-else
        }
    });// stackDot onClick

    $(".dot + .stackList ").on({
        click: function () {
            if ($(this).css('display') == 'block') {
                $(this).css('display', 'none');
            } else {
                $(this).css('display', 'block');
            } // if-else
        }
    }); // stackList onClick

    

    // =====================================ajax 비동기 호출
    
    $.ajax({
        type: "GET",
        url: "/auth/json/session",
        error: function () {
            console.log("err");
        },

        success: function (auth) {
            authJson = auth;

            console.log(authJson);

            if(authJson.userId === targetUserId){
                $("#rank .item").css("display","block");
                $("#san .item").css("display","block");
                $(".pick5Setting").css("display","block");
            } // if
        }
    });


    $.ajax({
        type: "GET",
        url: `get-list/json/${targetUserId}`,
        error: function () {
            console.log("err");
        },

        success: function (badgeGetList) {
            badgeGetListJson = badgeGetList;

            console.log(badgeGetListJson);

            let preBadgeId = 0;

            let createdDtList = Array();
 
            for(let idx = 0; idx < badgeGetListJson.length; idx ++){

                let nowBadgeGet = badgeGetListJson[idx];
                
                if(preBadgeId != nowBadgeGet.badgeId){
                    preBadgeId = nowBadgeGet.badgeId;

                    console.log(nowBadgeGet.badgeId);
                    
                    createdDtList = Array();

                    createdDtList.push(nowBadgeGet.createdDt);

                    $('#rank .item#' + nowBadgeGet.badgeId).css("display","block");
                    $('#rank .item#' + nowBadgeGet.badgeId).addClass("get");
                    $('#san .item#' + nowBadgeGet.badgeId).css("display","block");
                    $('#san .item#' + nowBadgeGet.badgeId).addClass("get");

                    console.log($('#collection .item#' + nowBadgeGet.badgeId))

                    console.log(nowBadgeGet.badgeId + " <<<<<<<<<<<<<<<<get");



                    if(nowBadgeGet.status != 'N'){
                        console.log(preBadgeId + " <<<<<<<<<<<<<<<<pick");

                        $('#rank .item#' + nowBadgeGet.badgeId).addClass("pick");
                        $('#san .item#' + nowBadgeGet.badgeId).addClass("pick");
                    }
                }
                else{
                    createdDtList.push(nowBadgeGet.createdDt);
                    

                    console.log(createdDtList + " <<<<<<<<<<<<<<createdDtList");
                    console.log(createdDtList.length + " <<<<<<<<<<<<<createdDtList.length");
                } // if-else

               
            } // for

            footerSet();
        }
    });

     // 대표 뱃지는 항상 픽 클래스
     $(".badgeBox#pick5 .item").addClass("pick");

    $.ajax({
        type: "GET",
        url: `/user/json/${targetUserId}`,
        error: function () {
            console.log("err");
        },

        success: function (targetUserVO) {
            targetUserVOJson = targetUserVO;

            console.log(targetUserVOJson);

            $("#targetUserName").append(targetUserVOJson.nickname);
        }
    });

}) // .js

