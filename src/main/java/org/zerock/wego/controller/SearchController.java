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

@RequestMapping("/")
@Controller
public class SearchController {
	private SearchService service;

	@GetMapping("/search")
	public void searchResult(String search, Model model) throws ControllerException {
		log.trace("searchResult({}) invoked.", search);

		try {
			Set<SanInfoViewVO> sanInfoList = this.service.selectSearchSanInfo3(search);
			model.addAttribute("sanInfoList", sanInfoList);

			Set<PartyViewVO> partyList = this.service.selectSearchParty3(search);
			model.addAttribute("partyList", partyList);

			Set<ReviewViewVO> reviewList = this.service.selectSearchReview3(search);
			model.addAttribute("reviewList", reviewList);
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch

	} // searchResult
} // end class
