package org.zerock.wego.domain.oauth.kakao;

import lombok.Data;


@Data
public class KakaoOAuthTokenDTO {
	
	private String token_type;
	private String access_token;
	private String id_token;
	private Integer expires_in;
	private String refresh_token;
	private Integer refresh_token_expires_in;
	private String scope;

} // end class