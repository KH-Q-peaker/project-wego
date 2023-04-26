package org.zerock.wego.domain.oauth.google;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class GoogleUserInfoDTO {
	private String azp; // 
	private String aud;	// (필수) ID 토큰의 대상입니다. 애플리케이션의 OAuth 2.0 클라이언트 ID 중 하나여야 합니다. 
	private String sub; // (필수) 사용자의 식별자로, 모든 Google 계정에서 고유하며 재사용되지 않습니다.
	private String scope;
	private String exp;	// (필수) ID 토큰을 수락해서는 안 되는 이후의 만료 시간입니다. Unix 시간 (정수 초)으로 표시됩니다.
	private String expires_in;	// (필수) ID 토큰을 수락해서는 안 되는 이후의 만료 시간입니다. Unix 시간 (정수 초)으로 표시됩니다.
	private String email; // 
	private String email_verified; // 
	private String access_type; // 
	
	
	private String iat;	// (필수) ID 토큰이 발급된 시간입니다. Unix 시간 (정수 초)으로 표시됩니다.
    private String iss;	// (필수) 응답 발급기관의 식별자입니다. Google ID 토큰의 경우 항상 https://accounts.google.com 또는 accounts.google.com입니다.
    private String at_hash;	// 
    private String family_name;
    private String given_name;
    private String hp;
    private String locale;
    private String name;
    private String nonce;
    private String picture;
    private String profile;
    
} // end class