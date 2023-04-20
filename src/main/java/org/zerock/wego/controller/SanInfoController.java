package org.zerock.wego.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.wego.domain.common.Criteria;
import org.zerock.wego.domain.common.PageDTO;
import org.zerock.wego.domain.info.SanInfoViewVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.common.SearchService;
import org.zerock.wego.service.info.SanInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/info")
@Controller
public class SanInfoController {
	private final SanInfoService sanInfoService;
	private SearchService searchService;

	@GetMapping("")
	public String openSanInfo(Criteria cri,Model model) throws ControllerException {
		log.trace("openSanInfo({}) invoked.", model);

		try {
//			Set<SanInfoViewVO> sanInfoList = this.sanInfoService.getList();
//			model.addAttribute("sanInfoList", sanInfoList);
//
//			return "info/info";
			
			// Step1. 페이징처리된 현재 currPage에 해당하는 게시글목록 받아옴
			Set<SanInfoViewVO> sanInfoList = this.sanInfoService.getList(cri);			
			model.addAttribute("sanInfoList", sanInfoList);
			
			// Step2. Pagination 위한 각종 변수값을 계산
			int totalAmount = this.sanInfoService.getTotalAmount();
			PageDTO pageDTO = new PageDTO(cri, totalAmount);
			log.info("\t+ pageDTO : {}", pageDTO);
			
			model.addAttribute("pageMaker", pageDTO);
			
			return "info/info";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // openSanInfo

//	@GetMapping("")
//	public String showSanInfo(@RequestParam(value = "query", required = false) String query, Model model) {
//		if (query != null && query.contains("query")) {
//			Set<SanInfoViewVO> sanInfoList = this.searchService.selectSearchSanInfo(query);
//			model.addAttribute("sanInfoList", sanInfoList);
//			
//			// "query" parameter exists and contains "query" string
//			// return "info/search" view
//			return "info/infoSearch";
//
//		} else {
//			Set<SanInfoViewVO> sanInfoList = this.sanInfoService.getList();
//			model.addAttribute("sanInfoList", sanInfoList);
//			
//			// "query" parameter does not exist or does not contain "query" string
//			// return "info" view
//			return "info/info";
//		}
//	}

} // end class