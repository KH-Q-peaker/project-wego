package org.zerock.wego.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.info.SanInfoViewSortVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.info.SanInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/info")
@Controller
public class SanInfoController {
	private final SanInfoService sanInfoService;
	
	@GetMapping("")
	public String showSanInfo(BoardDTO dto, Model model) throws ControllerException {
		log.trace("showSanInfo({}) invoked.", model);
		log.info("DTO: {}", dto);

		try {
			List<SanInfoViewSortVO> sanInfoSortList = this.sanInfoService.getSortAbcList(dto);
			model.addAttribute("sanInfoSortList", sanInfoSortList);				
			log.info(model);
			return "info/info";				
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // showSanInfo
	
	
	@PostMapping("")
	public String addSanInfo(BoardDTO dto, Model model) throws ControllerException {
		log.trace("addSanInfo({}) invoked.", model);

		try {
			if(dto.getOrderBy().equals("like")) {
				List<SanInfoViewSortVO> sanInfoSortList = this.sanInfoService.getSortLikeList(dto);
				model.addAttribute("sanInfoSortList", sanInfoSortList);				
				
				return "info/infoItem";					
			} else {
				List<SanInfoViewSortVO> sanInfoSortList = this.sanInfoService.getSortAbcList(dto);
				model.addAttribute("sanInfoSortList", sanInfoSortList);				
				
				return "info/infoItem";			
			} 
			
			
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // addSanInfo
	
//	@GetMapping("")
//	public String showSanInfo(	        
//			@RequestParam("page") int page,
//	        @RequestParam("lastItemId") long lastItemId,
//	        @RequestParam("orderBy") String orderBy, Model model) throws ControllerException {
//		log.trace("openSanInfo({}) invoked.", model);
//
//		try {
//				if (orderBy.equals("abc")) {
//				    // abc에 대한 로직
//					Set<SanInfoViewVO> sanInfoList = this.sanInfoService.getList();
//					model.addAttribute("sanInfoList", sanInfoList);				
//
//					return "info/info";
//				} else if (orderBy.equals("likes")) {
//				    // likes에 대한 로직
//					Set<SanInfoViewVO> sanInfoList = this.sanInfoService.getList();
//					model.addAttribute("sanInfoList", sanInfoList);				
//
//					return "info/info";
//				} else {
//				    // orderBy 값이 abc 또는 likes가 아닌 경우에 대한 예외 처리
//				    throw new ControllerException("Invalid orderBy value");
//				} // if-else
//
//		} catch (Exception e) {
//			throw new ControllerException(e);
//		} // try-catch
//	} // openSanInfo
//	
//	@GetMapping("/search")
//	public String resultSanInfo(
//			@RequestParam("page") int page,
//	        @RequestParam("lastItemId") long lastItemId,
//	        @RequestParam("orderBy") String orderBy, 
//	        @RequestParam("query") String query, Model model) throws ControllerException {
//		log.trace("openSanInfo({}) invoked.", model);
//
//		try {
//			Set<SanInfoViewVO> sanInfoList = this.sanInfoService.getList();
//			model.addAttribute("sanInfoList", sanInfoList);
//
//			return "info/infoSearch";
//		} catch (Exception e) {
//			throw new ControllerException(e);
//		} // try-catch
//	} // openSanInfo
	
	
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