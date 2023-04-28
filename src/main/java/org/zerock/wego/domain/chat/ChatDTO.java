package org.zerock.wego.domain.chat;

import java.util.Date;

import lombok.Data;

@Data
public class ChatDTO {

	private Integer chatId;
	private Integer chatRoomId;
	private Integer userId;
	private Date createdDt;
	private String contents;
}
