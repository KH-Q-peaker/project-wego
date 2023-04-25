package org.zerock.wego.domain.party;

import java.util.Date;

import lombok.Value;


@Value
public class PartyViewVO {
	
	private Integer	sanPartyId; 
	private String 	sanName; 
	private Integer userId; 
	private String	nickName; 
	private String 	userPic; 
	private Date 	createdDt;
	private Date 	modifiedDt; 
	private String 	title; 
	private String 	contents; 
	private Date 	partyDt; 
	private Integer partyMax; 
	private Integer userCnt; 
	private String 	items; 
	private String 	condition; 
	private String 	partyPic; 
	private Integer likeCnt; 
	private Integer reportCnt; 
	private Integer visitCnt;
	private Integer commentCnt;
	
} // end class
