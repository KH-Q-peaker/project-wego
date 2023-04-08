package org.zerock.wego.domain.oauth.kakao;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class KakaoUserInfoDTO {

	private Long id;	// 회원번호
	private String connected_id;	// 서비스에 연결 완료된 시각, UTC
	private KakaoUserInfoProperties properties;	// 사용자 프로퍼티
	private KakaoUserInfoKakaoAccount kakao_account;	// 카카오계정 정보
	private Date connected_at;	// 카카오싱크 간편가입을 통해 로그인한 시각, UTC
	
	
	
	
} // end class
