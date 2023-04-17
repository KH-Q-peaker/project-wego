package org.zerock.wego.domain.common;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FavoriteDTO {
	
	private long userId;
	private String targetGb;
	private long targetCd;
	private Timestamp modifiedDt;
	private Character status;

} // end class
