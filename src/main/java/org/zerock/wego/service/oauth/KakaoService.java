package org.zerock.wego.service.oauth;

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

	// 탈퇴하기?
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