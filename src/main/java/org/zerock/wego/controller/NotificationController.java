package org.zerock.wego.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.common.NotificationVO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.common.NotificationService;
import org.zerock.wego.service.common.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/notification")
@Controller
public class NotificationController { // 알림 컨트롤러
	
	private final NotificationService notificationService;
	private final UserService userService;
	
	@GetMapping("")
    public String showPage(@SessionAttribute("__AUTH__")UserVO user, Model model) throws ControllerException {
    	log.trace("showNotificationPage() invoked.");
    	
    	Integer userId = user.getUserId();
    	log.info("userId:{}",userId);

    	List<NotificationVO> notificationList = this.notificationService.getAllByUserId(userId);
        // 알림은 생성 날짜를 기준으로 역순으로 표시되므로 최신 알림이 목록 맨 위에 나타내기 
	    Comparator<NotificationVO> compareByCreatedDt = Comparator.comparing(NotificationVO::getCreatedDt);
	    Collections.sort(notificationList, compareByCreatedDt.reversed());
    	    
    	List<UserVO> createdAlarmUsers = userService.getByIds(
                notificationList.stream()
                        .map(NotificationVO::getCreatedByUserId)
                        .distinct()
                        .collect(Collectors.toList())
        ); // 알림생성유저의 개체요소 가져오기
        Map<Integer, Map<String, Object>> userMapList = createdAlarmUsers.stream()
                .collect(Collectors.toMap(
                        UserVO::getUserId,
                        u -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("nickname", u.getNickname());
                            map.put("userPic", u.getUserPic());
                            return map;
                        }
                ));
        // createdAlarmUsers 목록을  뷰에 전달되는 모델 속성의 userMap 개체로 대체
        model.addAttribute("userId", userId);
        model.addAttribute("notificationList", notificationList);
        model.addAttribute("createdAlarmUsers", userMapList); 
        return "common/notification";
        
    } // showPage      
    
	// 알림 읽음
	@PatchMapping(path="{alarmId}/read")
	ResponseEntity<Integer> updateAlarm(@PathVariable("alarmId")Integer alarmId,@SessionAttribute("__AUTH__")UserVO user) throws Exception{
		log.trace("updateAlarm({}) invoked.", alarmId, user);
    	Integer userId = user.getUserId();
    	log.info("userId:{}",userId);
		try {
			this.notificationService.modifyStatusByAlarmId(alarmId,userId);
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.notFound().build();
			
		}// try-catch
	}// updateAlarm
		
	// 알림 삭제 
	@DeleteMapping(path="{alarmId}")
	ResponseEntity<Integer> deleteAlarm(@PathVariable("alarmId")Integer alarmId) throws Exception{
		log.trace("deleteAlarm({}) invoked.", alarmId);
		
		try {
			this.notificationService.removeAlarm(alarmId);
			
			return ResponseEntity.ok().build();
			
		} catch(Exception e) {
			return ResponseEntity.notFound().build();
			
		}// try-catch
	}// deleteAlarm
} // end class 