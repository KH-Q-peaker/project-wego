package org.zerock.wego.controller;

import java.util.Deque;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.common.FavoriteVO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.info.SanInfoViewVO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.domain.ranking.RankingVO;
import org.zerock.wego.domain.review.ReviewViewVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.common.FavoriteService;
import org.zerock.wego.service.info.SanInfoService;
import org.zerock.wego.service.party.PartyService;
import org.zerock.wego.service.ranking.RankingService;
import org.zerock.wego.service.review.ReviewService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@RequestMapping("/")
@Controller
public class MainController {
	
	private RankingService rankingService ;
	private SanInfoService sanInfoService;
	private PartyService partyService;
	private ReviewService reviewService;
	private FavoriteService favoriteService;

	
	@GetMapping("")
	public String main(
			@SessionAttribute(value = "__AUTH__", required = false)UserVO auth, Model model) 
					throws ControllerException {
		log.trace("main({}, {}) invoked.", auth, model);

		try {
			if(auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if
			
			Deque<RankingVO> oneWayList = this.rankingService.getOneWay1To3();
			model.addAttribute("oneWayList", oneWayList);
			
			Deque<RankingVO> highestList = this.rankingService.getHighest1To3();
			model.addAttribute("highestList", highestList);
			
			Deque<RankingVO> partyKingList = this.rankingService.getPartyKing1To3();
			model.addAttribute("partyKingList", partyKingList);
			
			Deque<RankingVO> reviewKingList = this.rankingService.getReviewKing1To3();
			model.addAttribute("reviewKingList", reviewKingList);

			Set<SanInfoViewVO> sanInfoList = this.sanInfoService.getRandom10List();
			model.addAttribute("sanInfoList", sanInfoList);

			Set<PartyViewVO> partyList = this.partyService.getRandom10List();
			model.addAttribute("partyList", partyList);

			Set<ReviewViewVO> reviewList = this.reviewService.getRandom10List();
			model.addAttribute("reviewList", reviewList);

			return "main";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // main

} // end class
