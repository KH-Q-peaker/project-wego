package org.zerock.wego.domain.oauth.kakao;

import lombok.Data;


@Data
public class KakaoUserInfoKakaoAccount { // 사용자 정보
	
	private Boolean profile_nickname_needs_agreement; //  사용자 동의 시 닉네임 제공 가능
	private KakaoUserInfoProfile profile; // 프로필 정보
	private Boolean has_email;
	private Boolean email_needs_agreement; // 사용자 동의 시 카카오계정 대표 이메일 제공 가능
	private Boolean is_email_valid; // 이메일 유효 여부(true: 인증된 이메일 / false: 인증되지 않은 이메일)
	private Boolean is_email_verified; // 이메일 인증 여부(true: 인증된 이메일 / false: 인증되지 않은 이메일)
	private String email; // 카카오계정 대표 이메일
	
//	private Boolean profile_needs_agreement; 
//	private Boolean profile_image_needs_agreement; // 사용자 동의 시 프로필 사진 제공 가능(필요한 동의 항목: 프로필 사진)
//	private Boolean name_needs_agreement; // 사용자 동의 시 카카오계정 이름 제공 가능(필요한 동의 항목: 이름)
	
} // end class
