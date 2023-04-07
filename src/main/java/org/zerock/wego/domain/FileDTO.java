package org.zerock.wego.domain;

import lombok.Data;


@Data
public class FileDTO {
	
	private Integer fileId; 
	private String targetGb; 
	private Integer targetCd; 
	private String fileName; 
	private String uuid; 
	private String path; 

} // end class
