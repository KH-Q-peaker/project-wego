package org.zerock.wego.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.oauth.kakao.KakaoOAuthTokenDTO;
import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.oauth.KakaoOAuth;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Service
public class KakaoService {

	private final KakaoOAuth kakaoOAuth; 


	public KakaoOAuthTokenDTO getAccessToken(String authorizationCode) throws JsonProcessingException {
		log.trace("getAccessToken({}) invoked.", authorizationCode);

			ResponseEntity<String> responseEntity = kakaoOAuth.requestAccessToken(authorizationCode);

			int responseCode = responseEntity.getStatusCodeValue();

			log.info("responseCode = {}", responseCode);

			// 200이면 성공
			if(responseCode == 200) {

				KakaoOAuthTokenDTO kakaoOAuthTokenDTO = kakaoOAuth.parseOAuthTokenDTO(responseEntity.getBody());

				return kakaoOAuthTokenDTO;
			}
			else {
				throw new ServiceException("kakao getAccessToken");
			} // if-else

	}// getAccessToken


	public KakaoUserInfoDTO getUserInfo(KakaoOAuthTokenDTO OAuthToken) throws ServiceException{
		log.trace("getUserInfo({}) invoked.", OAuthToken);

		try {
			ResponseEntity<String> responseEntity = kakaoOAuth.requestUserInfo(OAuthToken);

			int responseCode = responseEntity.getStatusCodeValue();

			log.info("responseCode = {}", responseCode);

			// 200이면 성공
			if(responseCode == 200) {

				KakaoUserInfoDTO kakaoUserInfoDTO = kakaoOAuth.parseUserInfoDTO(responseEntity.getBody());

				return kakaoUserInfoDTO;
			}
			else {
				throw new ServiceException("kakao getAccessToken");
			} // if-else
		}
		catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch

	}// getUserInfo


	public void unlinkKakao(String accessToken) {

		String requestURL = "https://kapi.kakao.com/v1/user/unlink";

		try {

			URL url = new URL(requestURL);

			@Cleanup("disconnect")
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// 요청 헤더에 포함될 내용
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			conn.connect();

			// 응답 코드
			int responseCode = conn.getResponseCode();
			log.info("responseCode = {}", responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			log.info("unlink = {}", br.readLine());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// unlinkKakao
	
	
}// end class