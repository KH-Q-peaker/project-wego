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

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Component
public class KakaoOAuth {	// https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api 참조
 
	@Value("${kakao.rest.api.key}")
	private String clientId;	// REST key
//	@Value("${wego.aws.ip}")
//	private String wegoIP;	// aws ip 
	private String wegoIP = "3.25.135.222";	// aws ip 
	
	private final String AUTHORIZE_REQUEST_URL = "https://kauth.kakao.com/oauth/authorize?";	// 인가 코드 요청 URL
	private final String REDIRECT_URI = "/login/kakao/oauth";	// 인가 코드를 전달받을 서비스 서버의 URI >>>>>>>>>>>>>> 도메인 수정필요 kakao에서도 수정필요
	private final String RESPONSE_TYPE = "code";	// 인가 코드 요청시 param >>> code로 고정
	private final String GRANT_TYPE = "authorization_code"; // 토큰 요청시 param >>> authorization_code로 고정 
	private final String TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";	// 인가 코드를 전달받을 서비스 서버의 URI
	private final String USER_INFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";	// 사용자 정보 요청 URI


	public String getLoginURLToGetAuthorizationCode() {
		log.trace("getLoginURLToGetAuthorizationCode() invoked.");

		StringBuffer LoginURL = new StringBuffer(AUTHORIZE_REQUEST_URL);

		LoginURL	// 파라미터 설정
			.append("client_id=").append(clientId)
			.append("&").append("redirect_uri=").append("http://" + wegoIP + REDIRECT_URI)
			.append("&").append("response_type=").append(RESPONSE_TYPE);

		log.info("\t + {}", LoginURL.toString());
		
		return LoginURL.toString();
	} // getLoginURLToGetAuthorizationCode
	

	// 인가 코드로 Access Token 요청
	public ResponseEntity<String> requestAccessToken(String authorizationCode) {
		log.trace("requestAccessToken({}) invoked.", authorizationCode);

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);	// 해더 설정
																		// kakao 문서에는 utf-8로 설정까지 있는데 그건 web.xml설정으로 대체

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>(); // 바디에 파라미터 설정

		params.add("grant_type", GRANT_TYPE);
		params.add("client_id", clientId);
		params.add("redirect_uri", "http://" + wegoIP + REDIRECT_URI);
		params.add("code", authorizationCode);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(
				TOKEN_REQUEST_URL,
				request,
				String.class);

		return response;
	} // requestAccessToken

	
	// 요청을 DTO로 변환
	public KakaoOAuthTokenDTO parseOAuthTokenDTO(String accessToken) throws JsonProcessingException {
		log.trace("parseOAuthTokenDTO({}) invoked.", accessToken);

		ObjectMapper objectMapper = new ObjectMapper();

		KakaoOAuthTokenDTO kakaoOAuthTokenDTO = objectMapper.readValue(accessToken, KakaoOAuthTokenDTO.class);

		return kakaoOAuthTokenDTO;
	} // parseOAuthTokenDTO
	

	// Access Token으로 유저 정보 요청
	public ResponseEntity<String> requestUserInfo(KakaoOAuthTokenDTO OAuthTokenDTO) {
		log.trace("requestUserInfo({}) invoked.", OAuthTokenDTO);

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		
		headers.add("Authorization", "Bearer " + OAuthTokenDTO.getAccess_token());
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity(
				USER_INFO_REQUEST_URL,
				kakaoUserInfoRequest, 
				String.class);

		return response;
	} // requestUserInfo

	
	// 요청을 DTO로 변환
	public KakaoUserInfoDTO parseUserInfoDTO(String userInfo) throws JsonProcessingException {
		log.trace("parseUserInfoDTO({}) invoked.", userInfo);

		ObjectMapper objectMapper = new ObjectMapper();
		
		KakaoUserInfoDTO kakaoUserInfoDTO = objectMapper.readValue(userInfo, KakaoUserInfoDTO.class);

		return kakaoUserInfoDTO;		
	} // getUserInfo

} // end class
