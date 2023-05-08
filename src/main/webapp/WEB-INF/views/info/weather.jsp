<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="org.zerock.wego.domain.openweather.*" %>

<div class="contentsbox" id="weather">
    <div class="hourly">
        <div class="title">시간별 일기 예보</div>
        <div class="items">
            <c:forEach var="hourly" items="${forecastHourlys}">
                <div class="item">
                    <div>
                    	<fmt:formatDate pattern="HH시" value="${hourly.dt}"></fmt:formatDate>
                       
                    </div>
                    <div>
                        <img class="weather-image" src="https://openweathermap.org/img/wn/${hourly.weather.get(0).icon}.png">
                    </div>
                    <div>${hourly.temp}</div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="daily">
        <div class="title">7일간 일기 예보</div>
        <div class="items">
            <c:forEach var="daily" items="${forecastDailys}">
                <div class="item">
                    <span>${daily.dt}</span>
                    <span>
                        <img class="weather-image" src="https://openweathermap.org/img/wn/${daily.weather.get(0).icon}.png">
                    </span>
                    <span>${daily.temp.min}</span>
                    <span>${daily.temp.max}</span>
                    <span>${daily.pop}</span>
                </div>
            </c:forEach>
        </div>
    </div>

</div>