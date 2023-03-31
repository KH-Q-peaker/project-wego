package org.zerock.wego.domain;

import lombok.Data;


@Data
public class ReviewDTO {

	private Integer sanReviewId;
//	private String sanName;
	private Integer userId;
//	private String nickname;
	private String userPic;
//	private Timestamp createdDt;
//	private Timestamp modifiedDt;
	private String title;
	private String contents;
//	private Integer likeCnt;
//	private Integer reportCnt;
	
}// end class
