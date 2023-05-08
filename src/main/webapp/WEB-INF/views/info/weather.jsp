<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="contentsbox">

    <c:forEach var="hourly" items="${forecastHourlys}"></c:forEach>
    <c:forEach var="daily" items="${forecastDailys}"></c:forEach>

</div>