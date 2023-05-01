package org.zerock.wego.socket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.zerock.wego.domain.chat.ChatDTO;
import org.zerock.wego.service.common.ChatService;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
	
	private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());

	@Autowired
	private ChatService chatService;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		log.info(">>>>>>>>>>>>>>>>>>> 연결됨");
		log.info(session.toString());
        
        sessions.add(session);
	}// afterConnectionEstablished 
    
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.info(">>>>>>>>>>>>>>>>>>> 종료됨");
		
		sessions.remove(session);
	}// afterConnectionClosed
    
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		
		log.info("message = {}", message.getPayload()); 
		
		ObjectMapper objectMapper = new ObjectMapper();
        
		ChatDTO chatMessage = objectMapper.readValue(message.getPayload(), ChatDTO.class);
		log.info(chatMessage);
        
		int result = chatService.sendChat(chatMessage);
		
		if(result > 0) {

			for(WebSocketSession s : sessions) {
				
				int chatRoomId = (Integer)s.getAttributes().get("chatRoomId");

				if(chatMessage.getChatRoomId() == chatRoomId) {
					
					s.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(chatMessage)));
				}// if
			}// for
		}// if
    }// handleTextMessage
}// end class
