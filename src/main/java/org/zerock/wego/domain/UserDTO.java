package org.zerock.wego.domain;

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
	
}// end class
