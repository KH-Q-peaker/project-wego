package org.zerock.wego.domain;

import java.sql.Timestamp;

import lombok.Value;

@Value
public class PartyVO {

	private Long sanPartyId;		// 모집글 식별자 
	private String sanName;
	private Long userId;
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
	private Long likeCnt;
	private Long reportCnt;
	
}// end class
