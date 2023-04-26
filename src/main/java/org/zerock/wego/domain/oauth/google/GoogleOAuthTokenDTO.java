package org.zerock.wego.domain.oauth.google;

import lombok.Data;


@Data
public class GoogleOAuthTokenDTO {
    private String access_token; // 애플리케이션이 Google API 요청을 승인하기 위해 보내는 토큰
    private String expires_in;   // Access Token의 남은 수명
    private String scope;
    private String token_type;   // 반환된 토큰 유형(Bearer 고정)
    private String id_token; // Google에서 디지털 서명한 사용자에 대한 ID 정보가 포함된 JWT
    private String refresh_token;    // 새 액세스 토큰을 얻는 데 사용할 수 있는 토큰
	
} // end class