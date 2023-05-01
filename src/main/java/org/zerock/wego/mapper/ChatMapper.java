package org.zerock.wego.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.chat.ChatDTO;
import org.zerock.wego.domain.chat.ChatRoomDTO;
import org.zerock.wego.domain.chat.ChatRoomVO;
import org.zerock.wego.domain.chat.ChatVO;

public interface ChatMapper {

	public List<ChatRoomVO> selectChatRoomList();
	// 채팅방 조회 
	public ChatRoomVO selectChatRoomById(@Param("chatRoomId")int chatRoomId);

	// 채팅메세지 목록 조회
	public List<ChatVO> selectAllChatById(@Param("chatRoomId")int chatRoomId);
	
	// 채팅방 참여 여부
	public boolean isJoin(@Param("chatRoomId")int chatRoomId, @Param("userId")int userId);
	
	// 채팅방 생성 
	public int insertChatRoom(ChatRoomDTO room);
	// 채팅 메시지 생성 
	public int insertChat(ChatDTO chat);

}// end interface
