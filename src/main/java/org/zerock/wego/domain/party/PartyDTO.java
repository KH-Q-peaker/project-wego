package org.zerock.wego.domain.party;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;


@Data
public class PartyDTO {
	
	private Integer	sanPartyId;
	private Integer sanInfoId;
	private Integer userId;
	private Timestamp createdDt; 
	private Timestamp modifiedDt; 
	private String title; 
	private String contents;
	private Timestamp partyDt; 
	private Integer partyMax; 
	private String items; 
	private String condition;
	
} // end class
