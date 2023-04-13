package org.zerock.wego.domain;

import java.sql.Timestamp;

import lombok.Value;

@Value
public class BadgeGetVO {

	Long badgeGetId;
	Long userId;
	Long badgeId;
	String badgeName;
	Timestamp createdDt;
	Character status;
	
} // end class
