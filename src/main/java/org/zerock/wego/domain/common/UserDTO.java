package org.zerock.wego.domain.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.zerock.wego.domain.oauth.google.GoogleUserInfoDTO;
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
	
	@Builder
	public static UserDTO updateUserPicByUserIdAndUserPic(Integer userId, String userPic) {
		UserDTO userDTO = UserDTO.builder()
				.userId(userId)
				.userPic(userPic)
				.build();
		return userDTO;
	} // constructor
	
	@Builder
	public static UserDTO changeNickByUserIdAndNickName(Integer userId, String nickname) {
		UserDTO userDTO = UserDTO.builder()
				.userId(userId)
				.nickname(nickname)
				.build();
		return userDTO;
	} // constructor
	
	public static UserDTO createByGoogle(GoogleUserInfoDTO googleInfo) {
		
		String nickname = getNowLocalDateTime() + "G";
		String socialId = googleInfo.getEmail();
		
		UserDTO userDTO = UserDTO.builder()
									.nickname(nickname)
									.socialId(socialId)
									.build();
		
		return userDTO;
	} // createByKakao
	
	
	public static UserDTO createByKakao(KakaoUserInfoDTO kakaoInfo) {
		
		String nickname = getNowLocalDateTime() + "K";
		String socialId = kakaoInfo.getKakao_account().getEmail();
		
		UserDTO userDTO = UserDTO.builder()
									.nickname(nickname)
									.socialId(socialId)
									.build();
		
		return userDTO;
	} // createByKakao
	
	
	public static UserDTO createByNaver(NaverUserInfoDTO naverInfo) {
		
		String nickname = getNowLocalDateTime() + "N";
		String socialId = naverInfo.getResponse().getEmail();
		
		UserDTO userDTO = UserDTO.builder()
									.nickname(nickname)
									.socialId(socialId)
									.build();
		
		return userDTO;
	} // createByKakao
	
	
	public static String getNowLocalDateTime() {
		LocalDateTime now = LocalDateTime.now();

		return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	} // getNowLocalDateTime
	
}// end class
