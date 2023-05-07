$(() => {
    setFooterPosition();

    $(window).resize(function () {
        setFooterPosition();
    });

}) // QJ

// footer fixed <-> relative converter
var setFooterPosition = () => {
    let footerOffsetTop = $("footer").offset().top - parseInt($("footer").css("margin-top"));
    let windowInnerHeight = window.innerHeight;
    let footerOuterHeight = $("footer").outerHeight(true);

    let totalWrapHeight = $(".total-wrap").innerHeight();

    // console.log(footerOffsetTop);
    // console.log(windowInnerHeight - footerOuterHeight);
    // console.log(totalWrapHeight - footerOuterHeight);

    setTimeout(500)

    if (footerOffsetTop < windowInnerHeight - footerOuterHeight) {
        $("footer").css({
            "position": 'fixed',
        })
    } else if (totalWrapHeight > footerOffsetTop) {
        $("footer").css({
            "position": 'relative'

        })
    }
}