package org.zerock.wego.domain.review;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReviewDTO {

	private Integer sanReviewId; 
	private Integer sanInfoId; 
	private Integer userId; 
	private Timestamp createdDt; 
	private Timestamp modifiedDt; 
	private String title; 
	private String contents;
	
} // end class
