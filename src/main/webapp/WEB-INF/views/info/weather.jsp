<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<div class="contentsbox" id="weather">
    <div class="hourly">
        <div class="title">시간별 일기 예보</div>
        <div class="items">
        	<c:set var="hour" value="0" />
            <c:forEach var="hourly" items="${forecastHourlys}">
                <div class="item">
                    <div id="hour">
                       <c:set var="currentHour" value="${LocalDateTime.now().plusHours(hour).getHour()}" />
            			${currentHour} 시 
            			<c:set var="hour" value="${hour + 1}" />
                    </div>
                    <div id="icon">
                        <img class="weather-image" src="https://openweathermap.org/img/wn/${hourly.weather.get(0).icon}.png">
                    </div>
                    <div id="temp">${hourly.temp}&deg;</div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="daily">
        <div class="title">7일간 일기 예보</div>
        <div class="column">
            <span id="day">날짜</span>
            <span id="icon"></span>
            <span id="min">최저</span>
            <span id="max">최고</span>
            <span id="pop">강수 확률</span>
        </div>
        <div class="items">
        	<c:set var="day" value="0" />
            <c:set var="formatter" value='${DateTimeFormatter.ofPattern("MM월 dd일")}' />
            <c:forEach var="daily" items="${forecastDailys}">
                <div class="row">
                    <span id="day">
                    	<c:set var="currentDay" value="${LocalDateTime.now().plusDays(day).format(formatter)}" />
            			${currentDay}
            			 <c:set var="day" value="${day + 1}" />
                    </span>
                    <span id="icon">
                        <img class="weather-image" src="https://openweathermap.org/img/wn/${daily.weather.get(0).icon}.png">
                    </span>
                    <span id="min">${daily.temp.min}</span>
                    <span id="max">${daily.temp.max}</span>
                    <span id="pop">${daily.pop}</span>
                </div>
            </c:forEach>
        </div>
    </div>

</div>