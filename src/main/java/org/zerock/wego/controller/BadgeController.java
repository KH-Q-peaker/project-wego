package org.zerock.wego.controller;

import java.util.Deque;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.config.BadgeConfig;
import org.zerock.wego.domain.badge.BadgeGetVO;
import org.zerock.wego.exception.NotFoundUserException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.service.badge.BadgeGetService;
import org.zerock.wego.service.common.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@RequestMapping("badge")
@RestController
public class BadgeController {
	private final UserService userService;
	private final BadgeConfig badgeConfig;
	private final BadgeGetService badgeGetService;

	
	@GetMapping("/{targetUserId}")
	public ModelAndView showCollection(@PathVariable("targetUserId") Integer targetUserId, ModelAndView mav) {
		log.trace("showCollection({},ModelAndView) invoked.", targetUserId);

		try {
			mav.setViewName("/badge/badge");

			String targetUserNickname = userService.getById(targetUserId).getNickname();

			mav.addObject("targetUserNickname", targetUserNickname);
			mav.addObject("badgeConfig", badgeConfig);
			mav.addObject("targetUserId", targetUserId);
			
			return mav;
		} catch (NotFoundUserException e) {
			mav.setViewName("/error/notFoundUser");

			return mav;
		} // try-catch
	} // showCollection

	@PostMapping(path = "/{targetUserId}")
	public Deque<BadgeGetVO> updatePickBadge(
			@PathVariable("targetUserId")Integer targetUserId,
			@RequestParam(value = "pickList[]") List<Integer> pickList
			) throws ServiceException {
		log.trace("updatePickBadge({}, {}) invoked.", targetUserId, pickList);

		badgeGetService.updatePickBadgeByUserIdAndPickList(targetUserId, pickList);

		Deque<BadgeGetVO> newBadgeGetList = badgeGetService.getAllByUserId(targetUserId);

		return newBadgeGetList;
	} // updatePickBadge


	@GetMapping(path = "/get-list/json/{targetUserId}")
	public Deque<BadgeGetVO> getBadgeGetListJson(@PathVariable("targetUserId")Integer targetUserId) {
		log.trace("getBadgeGetListJson({}) invoked.", targetUserId);

		Deque<BadgeGetVO> badgeGetList = badgeGetService.getAllByUserId(targetUserId);

		return badgeGetList;
	} // getBadgeGetListJson


	@GetMapping(path = "/pick-list/json/{targetUserId}")
	public Deque<BadgeGetVO> getPickBadgeListJson(@PathVariable("targetUserId")Integer targetUserId) {
		log.trace("getPickBadgeListJson({}) invoked.", targetUserId);

		Deque<BadgeGetVO> pickList = badgeGetService.getPickBadgeDequeByUserId(targetUserId);

		return pickList;
	} // getPickBadgeListJson


} // end class
