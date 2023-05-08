package org.zerock.wego.service.info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zerock.wego.domain.oauth.kakao.KakaoOAuthTokenDTO;
import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;
import org.zerock.wego.domain.oauth.naver.NaverOAuthTokenDTO;
import org.zerock.wego.domain.oauth.naver.NaverUserInfoDTO;
import org.zerock.wego.domain.openweather.OpenWeatherMapDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Component
public class WeatherService { // https://openweathermap.org/api/one-call-3#how
	// sample
	// https://api.openweathermap.org/data/3.0/onecall?lat=33.3766655632143&lon=126.54222094512&appid={open.weather.api.key}&lang=kr&units=metric 

	@Value("${open.weather.api.key}")
	private String appId;	

	private final String REQUEST_URL = "https://api.openweathermap.org/data/3.0/onecall?";
	private final String[] EXCLUDE = {"current", "minutely", "hourly", "daily", "alerts"};
	private final String LANG = "kr";	// 언어설정
	private final String UNITS = "metric";	// 단위설정


	// 위도 경도로 현재 날씨만 조회
	public OpenWeatherMapDTO getCurrentByLatLon(double lat, double lon) throws JsonProcessingException {
		log.trace("getCurrent({}, {}) invoked.", lat, lon);

		StringBuffer requestURL = new StringBuffer(REQUEST_URL);

		requestURL	// 파라미터 설정
		.append("appid=").append(appId)
		.append("&").append("lang=").append(LANG)	
		.append("&").append("units=").append(UNITS)
		.append("&").append("exclude=").append(EXCLUDE[1] + "," + EXCLUDE[2] + "," + EXCLUDE[3] + "," + EXCLUDE[4])
		.append("&").append("lat=").append(lat)
		.append("&").append("lon=").append(lon);

		return parseDTO(request(requestURL.toString()).getBody());
	} // getCurrentByLatLon

	// 위도 경도로 현재 날씨만 조회
	public OpenWeatherMapDTO getForecastByLatLon(double lat, double lon) throws JsonProcessingException {
		log.trace("getForecastByLatLon({}, {}) invoked.", lat, lon);
		
		StringBuffer requestURL = new StringBuffer(REQUEST_URL);
		
		requestURL	// 파라미터 설정
		.append("appid=").append(appId)
		.append("&").append("lang=").append(LANG)	
		.append("&").append("units=").append(UNITS)
		.append("&").append("exclude=").append(EXCLUDE[0] + "," + EXCLUDE[1])
		.append("&").append("lat=").append(lat)
		.append("&").append("lon=").append(lon);
		
		return parseDTO(request(requestURL.toString()).getBody());
	} // getForecastByLatLon

	
	public ResponseEntity<String> request(String URL) {
		log.trace("requestUserInfo({}) invoked.", URL);

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(
				URL,
				HttpMethod.GET,
				request, 
				String.class);

		return response;
	} // request


	public OpenWeatherMapDTO parseDTO(String response) throws JsonProcessingException {
		log.trace("parseDTO({}) invoked.", response);

		ObjectMapper objectMapper = new ObjectMapper();

		OpenWeatherMapDTO openWeatherMapDTO = objectMapper.readValue(response, OpenWeatherMapDTO.class);

		log.info("\t + {}",openWeatherMapDTO);

		return openWeatherMapDTO;		
	} // parseDTO

} // end class
