$(() => {

    loadOverview();
    removeSearch();

    $('li#overview').on({
        click: function () {
            if (!$(this).hasClass("selected")) {
                changeSelected(this);
                loadOverview();
            }
        }

    })
    $('li#main').on({
        click: function () {
            if (!$(this).hasClass("selected")) {
                changeSelected(this);
                loadMain();
            }
        }
    })
    $('li#weather').on({
        click: function () {
            if (!$(this).hasClass("selected")) {
                changeSelected(this);
                loadWeather();
            }
        }
    })
    $('li#food').on({
        click: function () {
            if (!$(this).hasClass("selected")) {
                changeSelected(this);
                loadFood();
            }
        }
    })

}) // .jquery
var changeSelected = (clickElement) => {
    $(".content-header-menu-item").removeClass("selected");
    $(clickElement).addClass("selected");
}

var loadOverview = () => {
    $.ajax({
        type: "GET",
        url: `overview/${sanInfoId}`,
        error: function () {

        }, // error

        success: function (data) {
            document.querySelector("#module").style.opacity = "0";
            $("#module").animate({
                opacity: 1,
            });
            $("#module").html(data);
            console.log("모듈에 작성 글 데이터 넣기 성공 ");
            setTimeout(setFooterPosition(), 500);
        } // success
    });
}
var loadMain = () => {
    $.ajax({
        type: "GET",
        url: `main/${sanInfoId}`,
        error: function () {

        }, // error

        success: function (data) {
            document.querySelector("#module").style.opacity = "0";
            $("#module").animate({
                opacity: 1,
            });
            $("#module").html(data);
            console.log("모듈에 작성 글 데이터 넣기 성공 ");
            setTimeout(setFooterPosition(), 500);
        } // success
    });
}
var loadWeather = () => {
    $.ajax({
        type: "GET",
        url: `weather/${sanInfoId}`,
        error: function () {

        }, // error

        success: function (data) {
            document.querySelector("#module").style.opacity = "0";
            $("#module").animate({
                opacity: 1,
            });
            $("#module").html(data);
            console.log("모듈에 작성 글 데이터 넣기 성공 ");
            setTimeout(setFooterPosition(), 500);
        } // success
    });
}
var loadFood = () => {
    $.ajax({
        type: "GET",
        url: `food/${sanInfoId}`,
        error: function () {

        }, // error

        success: function (data) {
            document.querySelector("#module").style.opacity = "0";
            $("#module").animate({
                opacity: 1,
            });
            $("#module").html(data);
            console.log("모듈에 작성 글 데이터 넣기 성공 ");
            setTimeout(setFooterPosition(), 500);
        } // success
    });
}