package org.zerock.wego.domain;

import java.sql.Timestamp;

import lombok.Value;


@Value
public class ReviewVO {

	private Integer sanReviewId;
	private String sanName;
	private Integer userId;
	private String nickname;
	private String userPic;
	private Timestamp createdDt;
	private Timestamp modifiedDt;
	private String title;
	private String contents;
	private Integer likeCnt;
	private Integer reportCnt;
	
}// end class
