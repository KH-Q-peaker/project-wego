package org.zerock.wego.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.domain.SanInfoViewVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.SearchService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor

@RequestMapping("/") // BASE URL
@Controller
public class SearchController {
	private SearchService service;

	@GetMapping("/search")
	public void searchResult(String search, Model model) throws ControllerException {
		log.trace("searchResult({}) invoked.", search);

		try {
			// =========== 산정보 ===========
			Set<SanInfoViewVO> mountainInfoList = this.service.selectSearchMountainInfo(search);
			model.addAttribute("mountainInfoList", mountainInfoList);

			// =========== 모집글 ===========
			Set<PartyViewVO> recruitmentList = this.service.selectSearchRecruitment(search);
			model.addAttribute("recruitmentList", recruitmentList);

			// =========== 후기글 ===========
			Set<ReviewViewVO> reviewList = this.service.selectSearchReview(search);
			model.addAttribute("reviewList", reviewList);
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch

	} // searchResult
} // end class
