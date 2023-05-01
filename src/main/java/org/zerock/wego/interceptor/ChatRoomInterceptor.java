package org.zerock.wego.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.common.UserVO;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Component("ChatRoomInterceptor")
public class ChatRoomInterceptor implements HandshakeInterceptor {
    
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    	log.info(">>>>>>>>>>>> ChatroomInterceptor");
    	
    	HttpSession session = ((ServletServerHttpRequest) request).getServletRequest().getSession();
    	
    	Integer chatRoomId = (Integer) session.getAttribute("chatRoomId");
    	UserVO user = (UserVO) session.getAttribute(SessionConfig.AUTH_KEY_NAME);
    	
        if (chatRoomId != null && user != null) {
        	
            attributes.put("chatRoomId", chatRoomId);
            attributes.put("userId", user.getUserId());
            
            return true;
        }// if
        return false;
    }// beforeHandshake
    
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    	;;
    }// afterHandshake
}// end class
