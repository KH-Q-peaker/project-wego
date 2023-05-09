<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- <script type="text/javascript" src="/resources/js/food.js"></script> -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script>
let sanName = "${sanInfoVO.sanName}";
let lat = ${sanInfoVO.lat};
let lon = ${sanInfoVO.lon};
</script>
<script src="/resources/js/sanInfo-food.js" defer></script>	
</head>
<div class="contentsbox">
	<div class="map_wrap">
		<div id="map"
			style="width: 100%; height: 100%; position: relative; overflow: hidden;"></div>

		<div id="menu_wrap" class="bg_white">
			<div class="option">
				<div>
						키워드 : <input type="text" value= "${sanInfoVO.sanName } 맛집" id="keyword" size="15">
				</div>
			</div>
			<hr>
			<ul id="placesList"></ul>
<!-- 			<div id="pagination"></div> -->
		</div>
	</div>
</div>

