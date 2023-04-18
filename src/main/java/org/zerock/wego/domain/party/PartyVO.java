package org.zerock.wego.domain.party;

import java.util.Date;

import lombok.Value;


@Value
public class PartyVO {
	
	private Integer	sanPartyId; 
	private Integer sanInfoId; 
	private Integer userId; 
	private String createdDt; 
	private String modifiedDt; 
	private String title; 
	private String contents; 
	private Date partyDt; 
	private Integer partyMax; 
	private String items; 
	private String condition; 
	
} // end class
