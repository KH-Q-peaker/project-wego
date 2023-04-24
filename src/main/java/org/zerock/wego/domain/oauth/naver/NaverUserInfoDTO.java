package org.zerock.wego.domain.oauth.naver;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class NaverUserInfoDTO {

	private String resultcode; // API 호출 결과 코드
	private String message; // 호출 결과 메시지
	private Response response; // 프로필 정보
	
} // end class
