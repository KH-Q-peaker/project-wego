package org.zerock.wego.controller;

import java.util.Deque;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.config.BadgeConfig;
import org.zerock.wego.domain.BadgeGetVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.service.BadgeGetService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@RequestMapping("badge")	// base URI
@RestController
public class BadgeController {
	private final BadgeGetService badgeGetService;
	private final BadgeConfig badgeConfig;

	@GetMapping(
			path = "/{targetUserId}"
			)
	public ModelAndView showCollection(
			@PathVariable("targetUserId") Long targetUserId, 
			HttpSession session
			) throws ServiceException {
		log.trace("openBadgeCollection({}, {}) invoked.", targetUserId, session);

		// 번호 확인 절차 필요
//		Deque<BadgeGetVO> badgeGetList = this.badgeGetService.getBadgeGetList(targetUserId);

		ModelAndView mv = new ModelAndView("/badge/badge");

		mv.addObject("badgeConfig", badgeConfig);
		mv.addObject("targetUserId", targetUserId);

		return mv;
	} // badgeCollection

	@PostMapping(
			path = "/{targetUserId}",
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			})
	public ModelAndView mofifyRepresentiveBadge(
			@PathVariable("targetUserId")Long targetUserId,
			@RequestParam(value = "pickList[]") List<Long> pickList,
			Model model
			) throws ServiceException {
		log.trace("changeRepresentiveBadge({}) invoked.", targetUserId);

		log.info("\t + userId : {}", targetUserId);
		log.info("\t + pickedBadgeList : {}", pickList);

		this.badgeGetService.changeRepresentiveBadge(targetUserId, pickList);

		Deque<BadgeGetVO> badgeGetList = this.badgeGetService.getBadgeGetList(targetUserId);

		ModelAndView mv = new ModelAndView("/badge/badge");

		mv.addObject("badgeGetList", badgeGetList);

		return mv;
	} // pickUpdate

		@GetMapping(
				path = "/get-list/json/{targetUserId}",
				produces = {
						MediaType.APPLICATION_JSON_VALUE
				})
		public Deque<BadgeGetVO> getBadgeGetList(
				@PathVariable("targetUserId")Long targetUserId,
				Model model)
						throws ServiceException {
			log.trace("getBadgeGetList({}) invoked.", targetUserId);
	
			Deque<BadgeGetVO> badgeGetList = this.badgeGetService.getBadgeGetList(targetUserId);

			return badgeGetList;
		} // pickUpdate


} // end class
