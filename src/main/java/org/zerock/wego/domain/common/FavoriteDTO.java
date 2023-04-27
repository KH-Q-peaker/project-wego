package org.zerock.wego.domain.common;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FavoriteDTO {
	
	private Integer userId;
	private String targetGb;
	private Integer targetCd;
	private Timestamp modifiedDt;
	private Character status;

} // end class
