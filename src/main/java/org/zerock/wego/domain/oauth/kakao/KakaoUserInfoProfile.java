package org.zerock.wego.domain.oauth.kakao;

import lombok.Data;


@Data
public class KakaoUserInfoProfile {

	private String nickname;	// 닉네임 : 우리 로직에 이데이터 필요
	private String thumbnail_image_url;	// 프로필 미리보기 이미지 URL
	private String profile_image_url;	// 프로필 사진 URL
	private Boolean is_dafault_image;	// 프로필 사진 URL이 기본 프로필 사진 URL인지 여부

} // end class