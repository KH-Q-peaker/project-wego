package org.zerock.wego.domain.review;

import java.util.Date;

import lombok.Value;

@Value
public class ReviewViewSortVO {

	private Integer sanReviewId; 
	private String sanName; 
	private Integer userId; 
	private String nickName; 
	private String userPic; 
	private Date createdDt; 
	private String modifiedDt; 
	private String title; 
	private String contents; 
	private String reviewPic;
	private Integer likeCnt; 
	private Integer reportCnt; 
	private Integer visitCnt;
	private Integer commentCnt;
	private Integer sortNum;

	
} // end class
