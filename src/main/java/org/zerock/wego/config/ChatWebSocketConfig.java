package org.zerock.wego.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.zerock.wego.interceptor.ChatRoomInterceptor;
import org.zerock.wego.socket.ChatWebSocketHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Configuration
@EnableWebSocket
public class ChatWebSocketConfig implements WebSocketConfigurer {

	private final ChatWebSocketHandler chatWebSocketHandler;
	private final ChatRoomInterceptor chatRoomInterceptor;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		registry.addHandler(chatWebSocketHandler, "/chat/room")
				.addInterceptors(chatRoomInterceptor);
//				.setAllowedOrigins("/"); // ??
		
	}// registerWebSocketHandlers
} // end class
