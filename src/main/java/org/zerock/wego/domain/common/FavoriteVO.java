package org.zerock.wego.domain.common;

import java.sql.Timestamp;

import lombok.Value;

@Value
public class FavoriteVO {
	
	private long userId;
	private String targetGb;
	private long targetCd;
	private Timestamp modifiedDt;
	private Character status;

} // end class
