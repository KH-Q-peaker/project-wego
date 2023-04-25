$(() => {
    let isOpen = false;

    $(".rankers").children().on({
        mouseover: function () {
            // console.log("open");
            let boxWidth = $(this).parent().css("width");
            let totalWrapMargin = $(".total-wrap").css("marginLeft");
            let sectionMargin = $(".total-wrap").css("width");

            let boxMargin = parseInt(totalWrapMargin) + parseInt(sectionMargin) / 50;

            let openTest = $("#ranker ");

            for(let idx = 0; idx < openTest.length; idx++){
                if(openTest[idx].style.display === 'gird'){
                    isOpen = true;
                } // if
            } // for
            
            if (!isOpen) {
                $(this).children().children("#ranker").css("display", "grid");
                $(this).children().children("#ranker").css("width", boxWidth);
                $(this).children().children("#ranker").css("left", boxMargin);
                isOpen = true;
            } // if
        } // mouseover  
    })

    $("*").on({
        click: function () {
            rankersClose();
        }
    }) // click 발생시 ranker 상세창 닫기

    const rankersClose = function() {
        $("#ranker ").css("display", "none");
        isOpen = false;
    } // rankersClose


    $(window).resize(function () {
        rankersClose();
    });

}) // .jq