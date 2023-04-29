package org.zerock.wego.domain.chat;

import java.util.Date;

import lombok.Value;

@Value
public class ChatVO {

	private Integer chatId;
	private Integer chatRoomId;
	private Integer userId;
	private Date createdDt;
	private String contents;
	
}// end class
