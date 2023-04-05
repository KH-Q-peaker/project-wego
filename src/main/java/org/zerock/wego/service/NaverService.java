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
public class NaverService {

	@Autowired
	private WegoConfig config;
	
	
	public String getAccessToken(String code, String state) throws ServiceException{
		log.trace("getAccessToken({}) invoked.", code);
		
		try {
			Properties properties = config.getPropertiesBean();
			
			// 카카오 토큰 얻어낼 URL
			StringBuffer requestURL = new StringBuffer("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&");
			
			requestURL.append("&client_id=").append(properties.getProperty("NAVER_CLIENT_ID"));
			requestURL.append("&client_secret=").append(properties.getProperty("NAVER_CLIENT_SECRET"));
			requestURL.append("&redirect_uri=http://localhost:8080/naver/login"); 
			requestURL.append("&code=").append(code);
			requestURL.append("&state=").append(state);
            
			
			URL url = new URL(requestURL.toString());
			

			@Cleanup("disconnect")
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
            
			conn.connect();
            

			int responseCode = conn.getResponseCode();
            log.info("responseCode = {}", responseCode);

            
            BufferedReader br;
            
            if(responseCode == 200) {
            	br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }else {
            	br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }// if-else
            
            
            String line = null;
            StringBuffer result = new StringBuffer();
           
            
            while((line = br.readLine()) != null) {
            	
            	result.append(line);
            	log.info("line = {}", line);
            }// while
            	
            br.close();

            
            Gson gson = new Gson();
            
            JsonObject obj = gson.fromJson(result.toString(), JsonObject.class);
            
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
		String requestURL = "https://openapi.naver.com/v1/nid/me"; // 사용자 정보 가져오기
		
		try {
			
			URL url = new URL(requestURL);
			
			@Cleanup("disconnect")
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
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
			JsonObject naverAccount = gson.fromJson(sb.toString(), JsonObject.class).get("response").getAsJsonObject();
			log.info("response = {}", naverAccount);
			
			String nickname = naverAccount.get("nickname").getAsString();
			log.info(":::nickname = {}", nickname);
			
			String email = naverAccount.get("email").getAsString();
			log.info(":::email = {}", email);
			
			
			result.put("nickname", nickname);
			result.put("email", email); 
			
			
			return result;
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}// getUserInfo
	
	
	
	
	public void unlinkNaver(String accessToken) {
		
		
		try {
			StringBuffer requestURL = new StringBuffer("https://nid.naver.com/oauth2.0/token");
			
			requestURL.append("&client_id=5ygil0yKvDAx0ZmfI5k3"); 
			requestURL.append("&client_secret=qJUAseVwDy");
			requestURL.append("&access_token=").append(accessToken); 
			requestURL.append("&grant_type=delete");

			URL url = new URL(requestURL.toString());

			@Cleanup("disconnect")
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.connect();
// 아 모르겠다 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// unlinkKakao
}// end class