package org.zerock.wego.domain;

import lombok.Data;


@Data
public class FileDTO {
	
	private Integer fileId; // 식별자 시퀀스
	private String targetGb; // 소속 게시판
	private Integer targetCd; // 소속 게시글
	private String fileName; // 원본명
	private String uuid; // UUID
	private String path; // 절대경로

} // end class
