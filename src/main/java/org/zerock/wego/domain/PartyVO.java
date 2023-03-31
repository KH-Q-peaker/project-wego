package org.zerock.wego.domain;

import java.sql.Timestamp;

import lombok.Value;

@Value
public class PartyVO {

	private Integer sanPartyId;		// 모집글 식별자 
	private String sanName;
	private Integer userId;
	private String nickname;
	private String userPic;
	private Timestamp createdDt;
	private Timestamp modifiedDt;
	private String title;
	private String contents;
	private Timestamp partyDt;
	private Integer partyMax;
	private Integer userCnt;
	private String items;
	private String condition;
	private String partyPic;
	private Integer likeCnt;
	private Integer reportCnt;
	
}// end class
