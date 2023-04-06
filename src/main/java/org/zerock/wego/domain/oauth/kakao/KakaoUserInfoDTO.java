package org.zerock.wego.domain.oauth.kakao;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class KakaoUserInfoDTO {

	private Long id;
	private String connected_id;
	private KakaoUserInfoProperties properties;
	private KakaoUserInfoKakaoAccount kakao_account;
	private Date connected_at;
	
	
	
	
} // end class
