package org.zerock.wego.domain.oauth.naver;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class Response {

	private String id; // 동일인 식별 정보(네이버 아이디마다 고유하게 발급되는 값)
	private String nickname; // 사용자 별명
//	private String name; // 사용자 이름
	private String email; // 사용자 메일 주소
//	private String gender; // 성별(F: 여성, M: 남성, U: 확인불가)
//	private String age; // 사용자 연령대
//	private String birthday; // 사용자 생일(MM-DD 형식)
//	private String profile_image; // 사용자 프로필 사진 URL
//	private String birthyear; // 출생연도
//	private String mobile; // 휴대전화번호
	
} // end class
