package org.zerock.wego.service.common;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.chat.ChatDTO;
import org.zerock.wego.domain.chat.ChatRoomDTO;
import org.zerock.wego.domain.chat.ChatRoomVO;
import org.zerock.wego.domain.chat.ChatVO;
import org.zerock.wego.mapper.ChatMapper;
import org.zerock.wego.service.party.JoinService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatMapper chatMapper;
	
	public ChatRoomVO getChatRoomById(int chatRoomId) {
		
		return this.chatMapper.selectChatRoomById(chatRoomId);
	}// getChatRoomById
	
	
	public int createChatRoom(ChatRoomDTO room) {
		
		return chatMapper.insertChatRoom(room);
	}// createChatRoom
	
	
	public int sendChat(ChatDTO chat) {
		
		return chatMapper.insertChat(chat);
	}// sendChat
	
	
	public List<ChatVO> enterChatRoom(Integer chatRoomId, Integer userId) throws RuntimeException{
		
		boolean isJoin = this.chatMapper.isJoin(chatRoomId, userId);
		
		if(!isJoin) {
			return null;
		}
        return  chatMapper.selectAllChatById(chatRoomId);
    }// enterChatRoom
}// end class