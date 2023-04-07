package org.zerock.wego.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReportDTO {
	
	private Integer userId;
	private String targetGb;
	private Integer targetCd;
	private Timestamp createdDt;
	private String reportGb;

} // end class
