package org.zerock.wego.domain.badge;

import java.sql.Timestamp;

import lombok.Value;

@Value
public class BadgeGetVO {

	Integer badgeGetId;
	Integer userId;
	Integer badgeId;
	String badgeName;
	Timestamp createdDt;
	Character status;
	
} // end class
