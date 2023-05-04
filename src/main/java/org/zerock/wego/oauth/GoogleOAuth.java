package org.zerock.wego.oauth;

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
import org.zerock.wego.domain.oauth.google.GoogleOAuthTokenDTO;
import org.zerock.wego.domain.oauth.google.GoogleUserInfoDTO;
import org.zerock.wego.domain.oauth.kakao.KakaoOAuthTokenDTO;
import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;
import org.zerock.wego.domain.oauth.naver.NaverOAuthTokenDTO;
import org.zerock.wego.domain.oauth.naver.NaverUserInfoDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Component
public class GoogleOAuth {	// https://developers.google.com/identity/openid-connect/openid-connect?hl=ko#obtaininguserprofileinformation 참조
 
	@Value("${google.client.id}")
	private String clientId;	// Client ID : 애플리케이션 등록 시 발급받은 Client ID 값
	@Value("${google.client.secret}")
	private String clientSecret;	// Client secret : 애플리케이션 등록 시 발급받은 Client secret 값

	private final String AUTHORIZE_REQUEST_URL = "https://accounts.google.com/o/oauth2/v2/auth?";	// 로그인 인증 요청
	private final String REDIRECT_URI = "http://localhost:8080/login/google/oauth";	// Google에서 응답을 수신하는 서버의 HTTP 엔드포인트여야 합니다.  >>>>>>>>>>>>>> 도메인 수정필요 naver에서도 수정필요
	private final String RESPONSE_TYPE = "code";	// 기본 승인 코드 흐름 요청에서 code이어야 합니다
	private final String SCOPE = "openid email";	// 기본 요청에서 openid email이어야 합니다
	private final String[] GRANT_TYPE = {"authorization_code", "refresh_token", "delete"}; // 인증 과정에 대한 구분값 {발급, 갱신, 삭제}
	private final String TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";	// 액세스 토큰 요청 URL
	private final String USER_INFO_REQUEST_URL = "https://oauth2.googleapis.com/tokeninfo";	// 사용자 정보 요청 URL


	public String getLoginURLToGetAuthorizationCode(String state) {
		log.trace("getLoginURLToGetAuthorizationCode({}) invoked.", state);

		StringBuffer LoginURL = new StringBuffer(AUTHORIZE_REQUEST_URL);

		LoginURL	// 파라미터 설정
			.append("response_type=").append(RESPONSE_TYPE)
			.append("&").append("scope=").append(SCOPE)	
			.append("&").append("client_id=").append(clientId)	
			.append("&").append("redirect_uri=").append(REDIRECT_URI)
			.append("&").append("state=").append(state);

		return LoginURL.toString();
	} // getLoginURLToGetAuthorizationCode
	

	// 인가 코드로 Access Token 요청
	public ResponseEntity<String> requestAccessToken(String authorizationCode) {
		log.trace("requestAccessToken(authorizationCode) invoked.");

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);	// 해더 설정

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // 바디에 파라미터 설정

		params.add("code", authorizationCode);
		params.add("client_id", clientId);
		params.add("client_secret", clientSecret);
		params.add("redirect_uri", REDIRECT_URI);
		params.add("grant_type", GRANT_TYPE[0]);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(
				TOKEN_REQUEST_URL,
				request,
				String.class);

		return response;
	} // requestAccessToken

	
	// 요청을 DTO로 변환
	public GoogleOAuthTokenDTO parseOAuthTokenDTO(String accessToken) throws JsonProcessingException {
		log.trace("parseOAuthTokenDTO({}) invoked.", accessToken);

		ObjectMapper objectMapper = new ObjectMapper();

		GoogleOAuthTokenDTO googleOAuthTokenDTO = objectMapper.readValue(accessToken, GoogleOAuthTokenDTO.class);

		return googleOAuthTokenDTO;
	} // parseOAuthTokenDTO
	

	// Access Token으로 유저 정보 요청
	public ResponseEntity<String> requestUserInfo(GoogleOAuthTokenDTO oAuthTokenDTO) {
		log.trace("requestUserInfo(GoogleOAuthTokenDTO) invoked.");

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		
		headers.add("Authorization", "Bearer " + oAuthTokenDTO.getAccess_token());
		headers.add("Authorization", "Bearer " + oAuthTokenDTO.getAccess_token());

		HttpEntity<MultiValueMap<String, String>> userInfoRequest = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				USER_INFO_REQUEST_URL,
				HttpMethod.GET,
				userInfoRequest, 
				String.class);

		return response;
	} // requestUserInfo

	
	// 요청을 DTO로 변환
	public GoogleUserInfoDTO parseUserInfoDTO(String userInfo) throws JsonProcessingException {
		log.trace("parseUserInfoDTO({}) invoked.", userInfo);

		ObjectMapper objectMapper = new ObjectMapper();
		
		GoogleUserInfoDTO googleUserInfoDTO = objectMapper.readValue(userInfo, GoogleUserInfoDTO.class);

		return googleUserInfoDTO;		
	} // getUserInfo

} // end class
