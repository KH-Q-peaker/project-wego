package org.zerock.wego.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.wego.domain.info.SanInfoViewVO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.domain.review.ReviewViewVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.common.SearchService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor

@RequestMapping("/")
@Controller
public class SearchController {
	private SearchService service;

	@GetMapping("/search")
	public void searchResult(String query, Model model) throws ControllerException {
		log.trace("searchResult(query, model) invoked.");

		try {
			Set<SanInfoViewVO> sanInfoList = this.service.getSearchSanInfo3(query);
			model.addAttribute("sanInfoList", sanInfoList);

			Set<PartyViewVO> partyList = this.service.getSearchParty3(query);
			model.addAttribute("partyList", partyList);

			Set<ReviewViewVO> reviewList = this.service.getSearchReview3(query);
			model.addAttribute("reviewList", reviewList);
			
			model.addAttribute("query", query);
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch

	} // searchResult
	
} // end class
