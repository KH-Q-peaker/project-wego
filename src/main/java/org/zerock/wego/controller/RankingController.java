package org.zerock.wego.controller;

import java.util.Deque;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.ranking.RankingVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.ranking.RankingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/ranking")
@RestController
public class RankingController {

	private final RankingService rankingService;


	@GetMapping(path = "/one-way")
	public ModelAndView showOneWay(@SessionAttribute(SessionConfig.AUTH_KEY_NAME)UserVO authUser) throws ControllerException{
		log.trace("showOneWay(authUser) invoked.");

		Integer userId = authUser.getUserId();

		Deque<RankingVO> ranking1to3List = this.rankingService.getOneWay1To3();
		Deque<RankingVO> ranking4to30List = this.rankingService.getOneWay4To30();
		RankingVO myUseRankingVO = this.rankingService.getOneWayUserRankingByUserId(userId);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("ranking/detail");

		mav.addObject("rankingName", "한우물왕");
		mav.addObject("ranking1to3List", ranking1to3List);
		mav.addObject("ranking4to30List", ranking4to30List);
		mav.addObject("myRanking", myUseRankingVO);

		return mav;
	}// showOneWay

	
	@GetMapping(path = "/highest")
	public ModelAndView showHighest(@SessionAttribute(SessionConfig.AUTH_KEY_NAME)UserVO authUser) throws ControllerException{
		log.trace("showHighest(authUser) invoked.");

		Integer userId = authUser.getUserId();

		Deque<RankingVO> ranking1to3List = this.rankingService.getHighest1To3();
		Deque<RankingVO> ranking4to30List = this.rankingService.getHighest4To30();
		RankingVO myUseRankingVO = this.rankingService.getHighestUserRankingByUserId(userId);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("ranking/detail");

		mav.addObject("rankingName", "제일높왕");
		mav.addObject("ranking1to3List", ranking1to3List);
		mav.addObject("ranking4to30List", ranking4to30List);
		mav.addObject("myRanking", myUseRankingVO);

		return mav;
	}// showHighest
	
	
	@GetMapping(path = "/party-king")
	public ModelAndView showPartyKing(@SessionAttribute(SessionConfig.AUTH_KEY_NAME)UserVO authUser) throws ControllerException{
		log.trace("showPartyKing(authUser) invoked.");

		Integer userId = authUser.getUserId();

		Deque<RankingVO> ranking1to3List = this.rankingService.getPartyKing1To3();
		Deque<RankingVO> ranking4to30List = this.rankingService.getPartyKing4To30();
		RankingVO myUseRankingVO = this.rankingService.getPartyKingUserRankingByUserId(userId);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("ranking/detail");

		mav.addObject("rankingName", "참대왕");
		mav.addObject("ranking1to3List", ranking1to3List);
		mav.addObject("ranking4to30List", ranking4to30List);
		mav.addObject("myRanking", myUseRankingVO);

		return mav;
	}// showPartyKing
	
	
	@GetMapping(path = "/review-king")
	public ModelAndView showReviewKing(@SessionAttribute(SessionConfig.AUTH_KEY_NAME)UserVO authUser) throws ControllerException{
		log.trace("showOneWay(authUser) invoked.");

		Integer userId = authUser.getUserId();

		Deque<RankingVO> ranking1to3List = this.rankingService.getReviewKing1To3();
		Deque<RankingVO> ranking4to30List = this.rankingService.getReviewKing4To30();
		RankingVO myUseRankingVO = this.rankingService.getReviewKingUserRankingByUserId(userId);

		ModelAndView mav = new ModelAndView();

		mav.setViewName("ranking/detail");

		mav.addObject("rankingName", "후기왕");
		mav.addObject("ranking1to3List", ranking1to3List);
		mav.addObject("ranking4to30List", ranking4to30List);
		mav.addObject("myRanking", myUseRankingVO);

		return mav;
	}// showOneWay


}// end class

