<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c9dfa495780e1b0fdb9ce6347b76a75b"></script>
	<script>
		var container = document.getElementById('map');
		var options = {
			center: new kakao.maps.LatLng(33.450701, 126.570667),
			level: 3
		};

		var map = new kakao.maps.Map(container, options);
	</script>

<div class="contentsbox">

	<div>${sanInfoVO.reason}</div>
	<div>${sanInfoVO.height}</div>
	<div>${sanInfoVO.details}</div>

</div>

	
<div id="map" style="width:500px;height:400px;"></div>