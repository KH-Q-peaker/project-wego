package org.zerock.wego.domain.common;

import lombok.Value;


@Value
public class FileVO {
	
	private Integer fileId; 
	private String targetGb; 
	private Integer targetCd; 
	private String fileName; 
	private String uuid; 
	private String path; 
	private Integer status;

} // end class