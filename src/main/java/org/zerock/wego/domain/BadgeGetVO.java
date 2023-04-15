package org.zerock.wego.domain;

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
