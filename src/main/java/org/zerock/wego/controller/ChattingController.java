package org.zerock.wego.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.chat.ChatRoomVO;
import org.zerock.wego.domain.chat.ChatVO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.service.common.ChatService;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("/chat")
@SessionAttributes("chatRoomId")
public class ChattingController {

	@Autowired
	private ChatService chatService;
	
	//채팅방 목록 조회
//	@GetMapping
//	public String showChatRoomList(@SessionAttribute("__AUTH__")UserVO user, Model model) {
//		
//		List<ChatRoomVO> chatroomList = chatService.getChatRoomList();
//
//		model.addAttribute("chatRoomList", chatroomList);
//
//		return "chat/list";
//	}// showChatRoomList
    
	
	// 채팅방 입장
	@GetMapping("/room/{chatRoomId}")
	public String enterChatRoom(@PathVariable("chatRoomId") int chatRoomId, 
							@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO user,
							Model model ) throws RuntimeException, JsonProcessingException {
		
		String roomTitle = chatService.getChatRoomById(chatRoomId).getTitle();
		
		List<ChatVO> chatList = chatService.enterChatRoom(chatRoomId, user.getUserId());
		
		if(chatList == null) {
			return null; 
		}// if 
		
		model.addAttribute("roomTitle", roomTitle);
		model.addAttribute("chatList", chatList);
		model.addAttribute("sessionUserId", user.getUserId());
		model.addAttribute("chatRoomId", chatRoomId);

		return "chat/room";
	}// enterChatRoom
}// end class