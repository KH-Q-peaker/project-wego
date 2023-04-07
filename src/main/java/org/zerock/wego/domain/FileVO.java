package org.zerock.wego.domain;

import lombok.Value;


@Value
public class FileVO {
	
	private Integer fileId; 
	private String targetGb; 
	private Integer targetCd; 
	private String fileName; 
	private String uuid; 
	private String path; 

} // end class
