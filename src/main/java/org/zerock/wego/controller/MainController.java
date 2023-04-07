package org.zerock.wego.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.FavoriteVO;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.domain.SanInfoViewVO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.FavoriteService;
import org.zerock.wego.service.PartyService;
import org.zerock.wego.service.ReviewService;
import org.zerock.wego.service.SanInfoService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@RequestMapping("/")
@Controller
public class MainController {
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
