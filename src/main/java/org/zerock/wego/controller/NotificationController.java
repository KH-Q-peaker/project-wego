package org.zerock.wego.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.domain.common.NotificationVO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.service.common.NotificationService;
import org.zerock.wego.service.common.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
//@RequiredArgsConstructor
@RequestMapping("/notification") // base URI
@Controller
//@RestController
public class NotificationController { // 알림 컨트롤러
	
	private final NotificationService service;
	private final UserService userService;
	
    @GetMapping("/{userId}")
    public ModelAndView showNotificationPage(@PathVariable("userId") Integer userId) {
    	log.trace("showNotificationPage({}) invoked.", userId);
    	

    	List<NotificationVO> notificationList = this.service.getAllByUserId(userId);
        List<UserVO> createdAlarmUsers = userService.getByIds(
                notificationList.stream()
                        .map(NotificationVO::getCreatedByUserId)
                        .distinct()
                        .collect(Collectors.toList())
        ); // 알림생성유저의 개체요소 가져오기
		ModelAndView mav = new ModelAndView("common/notification");
		mav.addObject("userId", userId);
		mav.addObject("notificationList", notificationList);
		mav.addObject("userService", userService);
		mav.addObject("createdAlarmUsers", createdAlarmUsers);
		return mav;
    } // showNotificationPage
    
    
} // end class 