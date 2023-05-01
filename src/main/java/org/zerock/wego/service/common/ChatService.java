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
	
	
	public List<ChatVO> enterChatRoom(Integer chatroomId, Integer userId) throws RuntimeException{

        boolean isJoin = chatMapper.isJoin(chatroomId, userId);  

        if(!isJoin) {
        	
        	return null; // 아 이거 참여자만 들어가게 어케막지
        }// if 
        
        return  chatMapper.selectAllChatById(chatroomId);
    }// enterChatRoom
}// end class