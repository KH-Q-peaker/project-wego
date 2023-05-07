package org.zerock.wego.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.zerock.wego.service.common.NotificationService;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    private Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    private Map<Integer, WebSocketSession> userSessionsMap = new HashMap<>();

    @Autowired
    private NotificationService notificationService;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketsession) throws Exception {
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>소캣서버연결");
        log.info("Socket 연결" + webSocketsession);
//        sessions.add(webSocketsession);
//
//        // 세션에 사용자 ID 정보가 있다고 가정하고 가져옵니다.
//        Integer userId = getCurrentUserId(webSocketsession);
//
//        if (userId != null) {
//            addSession(webSocketsession, userId);
//        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession webSocketsession, TextMessage message) throws Exception {
        log.info("Session: {}", webSocketsession.getId());
        log.info("Received message: {}", message.getPayload());
        String[] values = message.getPayload().split(",");
        if (values.length != 2) {
            log.error("Invalid message format.");
            return;
        }
        
        Integer userId = Integer.parseInt(values[0]); // 알림을 수신할 사용자 ID
        String contents = values[1]; // 알림 내용

        // 알림 생성 후에 userId와 contents를 사용하여 알림을 전송
        this.notificationService.sendNotification(userId, contents);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketsession, CloseStatus status) throws Exception {
        log.info("Socket 끊음");
        sessions.remove(webSocketsession);

        // 세션에서 사용자 ID 정보가 있다고 가정하고 가져옵니다.
        Integer userId = getCurrentUserId(webSocketsession);

        if (userId != null) {
            removeSession(webSocketsession);
        }
    }

    public void addSession(WebSocketSession webSocketsession, Integer userId) {
        userSessionsMap.put(userId, webSocketsession);
    }

    public void removeSession(WebSocketSession session) {
        try {
            session.close();
        } catch (IOException e) {
            // 세션 종료 중 발생한 예외 처리
        }
    }

    public void sendMessageToUser(Integer userId, String message) {
        WebSocketSession webSocketsession = userSessionsMap.get(userId);
        if (webSocketsession != null && webSocketsession.isOpen()) {
            try {
            	webSocketsession.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                log.error("Failed to send message to user: {}", userId, e);
            }
        } else {
            log.warn("User session not found or closed for user: {}", userId);
        }
    }
    // userId로 WebSocket 세션을 찾을 수 있도록 함
    public WebSocketSession getSessionByUserId(Integer userId) {
        for (WebSocketSession session : sessions) {
            Integer sessionUserId = (Integer) session.getAttributes().get("userId");
            if (sessionUserId != null && sessionUserId.equals(userId)) {
                return session;
            }
        }
        return null;
    }

    private Integer getCurrentUserId(WebSocketSession webSocketsession) {
       
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        
        // 세션에서 "userId"라는 이름으로 저장된 사용자 ID 정보를 가져옵니다.
        Integer userId = (Integer) session.getAttribute("userId");
        
        return userId;
    }
}
