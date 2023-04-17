package org.zerock.wego.domain.review;

import lombok.Value;

@Value
public class ReviewVO {

	private Integer sanReviewId; 
	private Integer sanInfoId; 
	private Integer userId; 
	private String createdDt; 
	private String modifiedDt; 
	private String title;
	private String contents; 
	
} // end class
