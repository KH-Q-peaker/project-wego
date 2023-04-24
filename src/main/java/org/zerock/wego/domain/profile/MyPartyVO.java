package org.zerock.wego.domain.profile;

import java.util.Date;

import lombok.Value;


@Value
public class MyPartyVO {
	
	private Integer	sanPartyId; 
	private String sanName; 
	private Integer userId; 
	private String createdDt; 
	private String modifiedDt; 
	private String title; 
	private String contents; 
	private Date partyDt; 
	private Integer partyCount; 
	private Integer partyMax; 
	private String items; 
	private String condition; 
	
} // end class
