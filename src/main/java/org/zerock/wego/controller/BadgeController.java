package org.zerock.wego.controller;

import java.util.Deque;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.config.BadgeConfig;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.badge.BadgeGetVO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.NotFoundUserException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.service.badge.BadgeGetService;
import org.zerock.wego.service.common.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@RequestMapping("badge")
@Controller
public class BadgeController {
	private final UserService userService;
	private final BadgeConfig badgeConfig;
	private final BadgeGetService badgeGetService;

	
	@GetMapping(path = "/{targetUserId}")
	public String showCollection(@PathVariable("targetUserId") Integer targetUserId, Model model) 
		throws RuntimeException{
		log.trace("showCollection({}) invoked.", targetUserId);

		try {
			String targetUserNickname = userService.getById(targetUserId).getNickname();

			model.addAttribute("targetUserNickname", targetUserNickname);
			model.addAttribute("sanBadgeList", badgeConfig.getAllSan());
			model.addAttribute("rankingBadgeList", badgeConfig.getAllRanking());
			model.addAttribute("targetUserId", targetUserId);
			
			return "/badge/badge";
		} catch (NotFoundUserException e) {

			throw new RuntimeException(e);
		} // try-catch
	} // showCollection

	@PostMapping(path = "/modify")
	public ResponseEntity<String> modifyPickBadge(
			@SessionAttribute(SessionConfig.AUTH_KEY_NAME)UserVO authUserVO,
			@RequestParam(value = "pickList[]") List<Integer> pickList
			) {
		log.trace("updatePickBadge(authUserVO, {}) invoked.", pickList);
		
		try {
			int targetUserId = authUserVO.getUserId();
			
			badgeGetService.modifyPickBadgeByUserIdAndPickList(targetUserId, pickList);
			
		} catch (NotFoundUserException e) {

			throw new RuntimeException(e);
		} catch (OperationFailException e) {
			
			throw new RuntimeException(e);
		} // try-catch
		
		return ResponseEntity.ok().build();
	} // modifyPickBadge


	@GetMapping(path = "/get-list/json/{targetUserId}")
	@ResponseBody
	public Deque<BadgeGetVO> getBadgeGetListJson(@PathVariable("targetUserId")Integer targetUserId) {
		log.trace("getBadgeGetListJson({}) invoked.", targetUserId);

		Deque<BadgeGetVO> badgeGetList = badgeGetService.getAllByUserId(targetUserId);

		return badgeGetList;
	} // getBadgeGetListJson


	@GetMapping(path = "/pick-list/json/{targetUserId}")
	@ResponseBody
	public Deque<BadgeGetVO> getPickBadgeListJson(@PathVariable("targetUserId")Integer targetUserId) {
		log.trace("getPickBadgeListJson({}) invoked.", targetUserId);

		Deque<BadgeGetVO> pickList = badgeGetService.getPickBadgeDequeByUserId(targetUserId);

		return pickList;
	} // getPickBadgeListJson


} // end class
