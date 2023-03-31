$(()=>{

  // footer fixed <-> relative converter
  footerSet = function () {
    let footerOffsetTop = $("footer").offset().top - parseInt($("footer").css("margin-top"));
    let windowInnerHeight = window.innerHeight;
    let footerOuterHeight = $("footer").outerHeight(true);

    let totalWrapHeight = $(".total-wrap").innerHeight();

    // console.log(footerOffsetTop);
    // console.log(windowInnerHeight - footerOuterHeight);
    // console.log(totalWrapHeight - footerOuterHeight);

    if (footerOffsetTop < windowInnerHeight - footerOuterHeight) {
        $("footer").css({
            "position": 'fixed',
        })
        console.log("fixed")
    } else if (totalWrapHeight > footerOffsetTop) {
        $("footer").css({
            "position": 'relative'
            
        })
        console.log("relative")
    }
}

footerSet();

$(window).resize(function () {
    footerSet();
});

}) // QJ