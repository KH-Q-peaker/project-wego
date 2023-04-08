package org.zerock.wego.domain.oauth.kakao;

import lombok.Data;


@Data
public class KakaoOAuthTokenDTO {	
	
	private String token_type;	// 토큰 타입, bearer로 고정
	private String access_token;	// 사용자 액세스 토큰 값
	private String id_token;	// ID 토큰 값
	private Integer expires_in; // 액세스 토큰과 ID 토큰의 만료 시간(초)
	private String refresh_token; // 사용자 리프레시 토큰 값
	private Integer refresh_token_expires_in; // 리프레시 토큰 만료 시간(초)
	private String scope;	// 인증된 사용자의 정보 조회 권한 범위 

} // end class