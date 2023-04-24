package org.zerock.wego.domain.common;

import org.zerock.wego.domain.oauth.kakao.KakaoUserInfoDTO;
import org.zerock.wego.domain.oauth.naver.NaverUserInfoDTO;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDTO {

	private Integer userId;
	private String socialId;
	private String nickname;
	private String address;
	private String sanRange;
	private String sanTaste;
	private String userPic;
	
	@Builder
	public UserDTO(Integer userId, String socialId, String nickname, String address, String sanRange, String sanTaste, String userPic) {
		super();
		this.userId = userId;
		this.socialId = socialId;
		this.nickname = nickname;
		this.address = address;
		this.sanRange = sanRange;
		this.sanTaste = sanTaste;
		this.userPic = userPic;
	} // constructer
	
	
	public static UserDTO createByKakao(KakaoUserInfoDTO kakaoInfo) {
		UserDTO userDTO = UserDTO.builder()
									.nickname(kakaoInfo.getId() + "K")
									.socialId(kakaoInfo.getKakao_account().getEmail())
									.build();
		
		return userDTO;
	} // createByKakao
	
	
	public static UserDTO createByNaver(NaverUserInfoDTO naverInfo) {
		UserDTO userDTO = UserDTO.builder()
									.nickname(naverInfo.getResponse().getId() + "N")
									.socialId(naverInfo.getResponse().getEmail())
									.build();
		
		return userDTO;
	} // createByKakao
	
	
	
}// end class
