package org.zerock.wego.socket;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.zerock.wego.service.common.NotificationService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@AllArgsConstructor
//@NoArgsConstructor
@RequestMapping("/notification")
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    private final NotificationService notificationService;

   
    // Add a no-argument constructor
    public NotificationWebSocketHandler() {
        this.notificationService = null;
    }
    
  //서버에 접속이 성공 했을때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception { //클라이언트와 서버가 연결
        super.afterConnectionEstablished(session);
        String userId = (String) session.getAttributes().get("userId");
        notificationService.addSession(session);
    	log.info("userId에 대해 설정된 WebSocket 연결"+ userId);
    }  // afterConnectionEstablished

    
    //메시지 수신과 관련된 핸들러를 추가 : 소켓에 메세지를 보냈을때 즉 JS에서 메세지 받을 때.
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	
    	
    	// Get the message payload
        String payload = message.getPayload();
        log.debug("수신된 메세지: " + payload);
        // 메세지 처리
        //

        // 응답 메시지 보내기
        session.sendMessage(new TextMessage("받은 메세지: " + payload));
    } // handleTextMessage
    
	//연결 해제될때

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception { //연결 해제
    	log.info("Socket 끊음" + session.getId());
    	//웹 소켓이 종료될 때마다 리스트에서 뺀다.
    	notificationService.removeSession(session);
    } // afterConnectionClosed
}
