package org.zerock.wego.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.wego.domain.FavoriteVO;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.domain.SanInfoViewVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.FavoriteService;
import org.zerock.wego.service.PartyService;
import org.zerock.wego.service.ReviewService;
import org.zerock.wego.service.SanInfoService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@RequestMapping("/") // BASE URL
@Controller
public class MainController {
	private SanInfoService mountainInfoService;
	private PartyService recruitmentService;
	private ReviewService reviewService;
	private FavoriteService favoriteService;

	@GetMapping("")
	public String main(Model model) throws ControllerException {
		log.trace("main() invoked.");

		try {
			// === TEST 유저ID 9의 좋아요 목록 ===
			Set<FavoriteVO> favoriteList = this.favoriteService.getList(9L);
			model.addAttribute("favoriteList", favoriteList);

			// =========== 산정보 ===========
			Set<SanInfoViewVO> mountainInfoList = this.mountainInfoService.getRandom10List();
			model.addAttribute("mountainInfoList", mountainInfoList);

			// =========== 모집글 ===========
			Set<PartyViewVO> recruitmentList = this.recruitmentService.getRandom10List();
			model.addAttribute("recruitmentList", recruitmentList);

			// =========== 후기글 ===========
			Set<ReviewViewVO> reviewList = this.reviewService.getRandom10List();
			model.addAttribute("reviewList", reviewList);

			return "main";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // main

} // end class
