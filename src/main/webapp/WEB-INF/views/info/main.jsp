<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="contentsbox">

	<div id="reason">
		<div class="column">100명산 선정 이유</div>
		<div class="value">${sanInfoVO.reason}</div>
	</div>
	<div id="height">
		<div class="column">높이</div>
		<div class="value">${sanInfoVO.height} M</div>
	</div>
	<div id="details">
		<div class="column">상세 설명</div>
		<div class="value">${sanInfoVO.details}</div>
	</div>

</div>