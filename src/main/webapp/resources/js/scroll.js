// top (scroll이 100이상 일때 top버튼 노출)
$( window ).scroll( function() {
	if ( $( this ).scrollTop() > 150 ) {
		$( '.scrollToTop' ).fadeIn();
	} else {
		$( '.scrollToTop' ).fadeOut();
	}
} );

// 위로 올라가는 부드러운 애니메이션
$( '.scrollToTop' ).click( function() {
	$( 'html, body' ).animate( { scrollTop : 0 }, 400 );
	return false;
} );