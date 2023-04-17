package org.zerock.wego.domain.profile;

import java.sql.Timestamp;

import lombok.Value;


@Value
public class ProfileVO {

	private String boardName;		// 카테고리 이름 
	private Integer srtId; 			// 게시글 ID
	private String user; 			// 유저아이디  
	private String title; 			// 제목 
	private Timestamp createDt;		// 생성일 
	private Integer likeCnt; 		// 좋아요	
	private Integer visitCount; 	// 조회수	
} // end class
