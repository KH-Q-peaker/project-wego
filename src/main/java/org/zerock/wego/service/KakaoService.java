package org.zerock.wego.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.config.WegoConfig;
import org.zerock.wego.exception.ServiceException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class KakaoService {

	@Autowired
	private WegoConfig config;
	
	public String getAccessToken(String code) throws ServiceException{
		log.trace("getAccessToken({}) invoked.", code);
		
		try {
			
			Properties properties = config.getPropertiesBean();
			StringBuffer requestURL = new StringBuffer("https://kauth.kakao.com/oauth/token?");
			
			requestURL.append("grant_type=authorization_code");
			requestURL.append("&client_id=").append(properties.getProperty("KAKAO_CLIENT_ID"));  
			requestURL.append("&redirect_uri=http://localhost:8080/kakao/login"); 
			requestURL.append("&code=").append(code);
            
			URL url = new URL(requestURL.toString());
			

			@Cleanup("disconnect")
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			
//			@Cleanup
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			// conn = HTTP 연결을 열고 요청 전송 가능
			// conn.getOutputStream() = 출력스트림을 얻어냄. 서버로 데이터 전송에 사용
			// OutputStreamWriter = 출력 스트림을 문자 스트림으로 변환
			// 서버에 HTTP요청을 보내기 위해 출력 스트림을 가져오고,
			// 문자 스트림으로 변환한 후 서버로 데이터를 전송하기 위한 BufferedWriter 생성

//            bw.write(sb.toString());
//            bw.flush();
//			개쓸모없는 것이었따 
            
			conn.connect();
            
            int responseCode = conn.getResponseCode();
            log.info("responseCode = {}", responseCode);
            
            
            @Cleanup
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            requestURL = new StringBuffer();
           
            
            // 받아온 결과 읽기
            while((line = br.readLine()) != null) {
            	
            	requestURL.append(line);
            	log.info("line = {}", line);
            }// while
            	
            
            /* result
             * HTTP/1.1 200 OK
				Content-Type: application/json;charset=UTF-8
   
   			"token_type":"bearer",
   			"access_token":"${ACCESS_TOKEN}",
   			"expires_in":43199,
   			"refresh_token":"${REFRESH_TOKEN}",
   			"refresh_token_expires_in":25184000,
   			"scope":"account_email profile"
			}*/
            
            
            Gson gson = new Gson();
            
            JsonObject obj = gson.fromJson(requestURL.toString(), JsonObject.class);
            
            String accessToken = obj.get("access_token").getAsString();
            String refreshToken = obj.get("refresh_token").getAsString();
            
            log.info("\n::: accessToken = {} \n::: refreshToken = {}", accessToken, refreshToken);
			
            
            return accessToken;
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
		
	}// getAccessToken
	
	
	
	public Map<String, String> getUserInfo(String accessToken) throws ServiceException{
		log.trace("getUserInfo({}) invoked.", accessToken);
		
		Map<String, String> result = new HashMap<>();
		
		
		// 요청 보낼 url
		String requestURL = "https://kapi.kakao.com/v2/user/me"; // 사용자 정보 가져오기
		
		try {
			
			URL url = new URL(requestURL);
			
			@Cleanup("disconnect")
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			// 요청 헤더에 포함될 내용 
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			
			conn.connect();
			
			
			int responseCode = conn.getResponseCode();
			log.info("responseCode = {}", responseCode);
			
			
			@Cleanup
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			
			String line = null;
			StringBuffer sb = new StringBuffer();
			
			
			while((line = br.readLine()) != null){
				
				sb.append(line);
				log.info(line);
				
			}// while
			
			
			Gson gson = new Gson();
			JsonObject kakaoAccount = gson.fromJson(sb.toString(), JsonObject.class).get("kakao_account").getAsJsonObject();
									 
			log.info("kakao_account = {}", kakaoAccount);
			
			String nickname = kakaoAccount.getAsJsonObject("profile").get("nickname").getAsString();
			log.info(":::nickname = {}", nickname);
			
			String email = kakaoAccount.get("email").getAsString();
			log.info(":::email = {}", email);
			
			
			result.put("nickname", nickname);
			result.put("email", email); // 아니 이메일이 없어 왜없냐고 
			
			
			return result;
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}// getUserInfo
	
	
	
	
	public void unlinkKakao(String accessToken) {
		
		String requestURL = "https://kapi.kakao.com/v1/user/unlink";
		
		try {

			URL url = new URL(requestURL);

			@Cleanup("disconnect")
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();


			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			conn.connect();


			int responseCode = conn.getResponseCode();
			log.info("responseCode = {}", responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			log.info("unlink = {}", br.readLine());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// unlinkKakao
}// end class