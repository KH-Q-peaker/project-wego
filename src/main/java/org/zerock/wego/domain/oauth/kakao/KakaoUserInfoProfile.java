package org.zerock.wego.domain.oauth.kakao;

import lombok.Data;


@Data
public class KakaoUserInfoProfile {

	private String nickname;
	private String thumbnail_image;
	private String profile_image;
	private Boolean is_dafault_image;

} // end class