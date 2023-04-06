package org.zerock.wego.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zerock.wego.domain.oauth.kakao.KakaoOAuthTokenDTO;
import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Component
public class KakaoOAuth {
 
	@Value("${kakao.rest.api.key}")
	private String kakaoClientId;

	private String KAKAO_AUTHORIZE_REQUEST_URI = "https://kauth.kakao.com/oauth/authorize?";
	private String REDIRECT_URI = "http://localhost:8080/login/kakao/oauth"; // 도메인 수정필요
	private	String RESPONSE_TYPE = "code"; 
	private final String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
	private final String KAKAO_USER_INFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";


	public String getLoginURLToGetAuthorizationCode() {
		log.trace("getLoginURLToGetAuthorizationCode() invoked.");

		StringBuffer LoginURL = new StringBuffer();

		LoginURL.append(KAKAO_AUTHORIZE_REQUEST_URI)
		.append("client_id=").append(kakaoClientId)
		.append("&").append("redirect_uri=").append(REDIRECT_URI)
		.append("&").append("response_type=").append(RESPONSE_TYPE);

		return LoginURL.toString();
	} // getLoginURLToGetAuthorizationCode
	

	public ResponseEntity<String> requestAccessToken(String authorizationCode) {
		log.trace("requestAccessToken({}) invoked.", authorizationCode);

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

		params.add("grant_type", "authorization_code"); // authorization_code로 고정
		params.add("client_id", kakaoClientId); // 앱 REST API 키
		params.add("redirect_uri", REDIRECT_URI); // 인가 코드가 리다이렉트된 URI
		params.add("code", authorizationCode); // 인가 코드 받기 요청으로 얻은 인가 코드

		HttpEntity<MultiValueMap<String, String>> kakaoOAuthRequest = new HttpEntity<>(params, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(
				KAKAO_TOKEN_REQUEST_URL,
				kakaoOAuthRequest,
				String.class);

		return response;
	} // requestAccessToken

	
	public KakaoOAuthTokenDTO parseOAuthTokenDTO(ResponseEntity<String> response) throws JsonProcessingException {
		log.trace("parseOAuthTokenDTO({}) invoked.", response);

		ObjectMapper objectMapper = new ObjectMapper();

		KakaoOAuthTokenDTO kakaoOAuthTokenDTO = objectMapper.readValue(response.getBody(), KakaoOAuthTokenDTO.class);

		return kakaoOAuthTokenDTO;
	} // parseOAuthTokenDTO
	

	public ResponseEntity<String> requestUserInfo(KakaoOAuthTokenDTO oAuthToken) {
		log.trace("requestUserInfo({}) invoked.", oAuthToken);

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		
		headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity(
				KAKAO_USER_INFO_REQUEST_URL,
				kakaoUserInfoRequest, 
				String.class);

		return response;
	} // requestUserInfo

	public KakaoUserInfoDTO parseUserInfoDTO(ResponseEntity<String> response) throws JsonProcessingException {
		log.trace("parseUserInfoDTO({}) invoked.", response);

		ObjectMapper objectMapper = new ObjectMapper();
		
		KakaoUserInfoDTO kakaoUserInfoDTO = objectMapper.readValue(response.getBody(), KakaoUserInfoDTO.class);

		return kakaoUserInfoDTO;		
	} // getUserInfo

} // end class
