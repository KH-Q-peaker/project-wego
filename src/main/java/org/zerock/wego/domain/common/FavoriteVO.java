package org.zerock.wego.domain.common;

import java.sql.Timestamp;

import lombok.Value;

@Value
public class FavoriteVO {
	
	private Integer userId;
	private String targetGb;
	private Integer targetCd;
	private Timestamp modifiedDt;
	private Character status;

} // end class
