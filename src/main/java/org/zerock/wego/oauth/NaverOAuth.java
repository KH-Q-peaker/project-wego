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
public class NaverOAuth {	// https://developers.naver.com/docs/login/api/api.md 참조
 
	@Value("${naver.client.id}")
	private String clientId;	// Client ID : 애플리케이션 등록 시 발급받은 Client ID 값
	@Value("${naver.client.secret}")
	private String clientSecret;	// Client ID : 애플리케이션 등록 시 발급받은 Client ID 값

	private final String AUTHORIZE_REQUEST_URL = "https://nid.naver.com/oauth2.0/authorize?";	// 네이버 로그인 인증 요청
	private final String REDIRECT_URI = "http://localhost:8080/login/naver/oauth";	// 애플리케이션을 등록 시 입력한 Callback URL 값 >>>>>>>>>>>>>> 도메인 수정필요 naver에서도 수정필요
	private final String RESPONSE_TYPE = "code";	// 인증 과정에 대한 내부 구분값으로 'code'로 전송해야 함
	private final String[] GRANT_TYPE = {"authorization_code", "refresh_token", "delete"}; // 인증 과정에 대한 구분값 {발급, 갱신, 삭제}
	private final String TOKEN_REQUEST_URL = "https://nid.naver.com/oauth2.0/token";	// 인가 코드를 전달받을 서비스 서버의 URI
	private final String USER_INFO_REQUEST_URL = "https://openapi.naver.com/v1/nid/me";	// 사용자 정보 요청 URI


	public String getLoginURLToGetAuthorizationCode(String state) {
		log.trace("getLoginURLToGetAuthorizationCode({}) invoked.", state);
;
		StringBuffer LoginURL = new StringBuffer(AUTHORIZE_REQUEST_URL);

		LoginURL	// 파라미터 설정
			.append("response_type=").append(RESPONSE_TYPE)
			.append("&").append("client_id=").append(clientId)	
			.append("&").append("redirect_uri=").append(REDIRECT_URI)
			.append("&").append("state=").append(state); // 사이트 간 요청 위조(cross-site request forgery) 공격을 방지하기 위해 애플리케이션에서 생성한 상태 토큰값

		return LoginURL.toString();
	} // getLoginURLToGetAuthorizationCode
	

	// 인가 코드로 Access Token 요청
	public ResponseEntity<String> requestAccessToken(String authorizationCode, String state) {
		log.trace("requestAccessToken(authorizationCode) invoked.");

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);	// 해더 설정
																		// kakao 문서에는 utf-8로 설정까지 있는데 그건 web.xml설정으로 대체

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // 바디에 파라미터 설정

		params.add("grant_type", GRANT_TYPE[0]);
		params.add("client_id", clientId);
		params.add("client_secret", clientSecret);
		params.add("code", authorizationCode);
		params.add("state", state);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(
				TOKEN_REQUEST_URL,
				request,
				String.class);

		return response;
	} // requestAccessToken

	
	// 요청을 DTO로 변환
	public NaverOAuthTokenDTO parseOAuthTokenDTO(String accessToken) throws JsonProcessingException {
		log.trace("parseOAuthTokenDTO({}) invoked.", accessToken);

		ObjectMapper objectMapper = new ObjectMapper();

		NaverOAuthTokenDTO naverOAuthTokenDTO = objectMapper.readValue(accessToken, NaverOAuthTokenDTO.class);

		return naverOAuthTokenDTO;
	} // parseOAuthTokenDTO
	

	// Access Token으로 유저 정보 요청
	public ResponseEntity<String> requestUserInfo(NaverOAuthTokenDTO oAuthTokenDTO) {
		log.trace("requestUserInfo({}) invoked.", oAuthTokenDTO);

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		
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
	public NaverUserInfoDTO parseUserInfoDTO(String userInfo) throws JsonProcessingException {
		log.trace("parseUserInfoDTO({}) invoked.", userInfo);

		ObjectMapper objectMapper = new ObjectMapper();
		
		NaverUserInfoDTO naverUserInfoDTO = objectMapper.readValue(userInfo, NaverUserInfoDTO.class);

		return naverUserInfoDTO;		
	} // getUserInfo

} // end class
