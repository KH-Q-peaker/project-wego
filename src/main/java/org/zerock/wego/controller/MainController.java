package org.zerock.wego.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.FavoriteVO;
import org.zerock.wego.domain.MountainInfoViewVO;
import org.zerock.wego.domain.RecruitmentViewVO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.FavoriteService;
import org.zerock.wego.service.MountainInfoService;
import org.zerock.wego.service.RecruitmentService;
import org.zerock.wego.service.ReviewService;
import org.zerock.wego.service.SearchService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@RequestMapping("/") // BASE URL
@Controller
public class MainController {
	private MountainInfoService mountainInfoService;
	private RecruitmentService recruitmentService;
	private ReviewService reviewService;
	private SearchService searchService;
	private FavoriteService favoriteService;

	@GetMapping("")
	public String main(Model model) throws ControllerException {
		log.trace("main() invoked.");

		try {
			// === TEST 유저ID 9의 좋아요 목록 ===
			Set<FavoriteVO> favoriteList = this.favoriteService.getList(9L);
			model.addAttribute("favoriteList", favoriteList);

			// =========== 산정보 ===========
			Set<MountainInfoViewVO> mountainInfoList = this.mountainInfoService.getRandom10List();
			model.addAttribute("mountainInfoList", mountainInfoList);

			// =========== 모집글 ===========
			Set<RecruitmentViewVO> recruitmentList = this.recruitmentService.getRandom10List();
			model.addAttribute("recruitmentList", recruitmentList);

			// =========== 후기글 ===========
			Set<ReviewViewVO> reviewList = this.reviewService.getRandom10List();
			model.addAttribute("reviewList", reviewList);

			return "main";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // main

	@GetMapping("/search")
	public void searchResult(String search, Model model) throws ControllerException {
		log.trace("searchResult({}) invoked.", search);

		try {
			// =========== 산정보 ===========
			Set<MountainInfoViewVO> mountainInfoList = this.searchService.selectSearchMountainInfo(search);
			model.addAttribute("mountainInfoList", mountainInfoList);

			// =========== 모집글 ===========
			Set<RecruitmentViewVO> recruitmentList = this.searchService.selectSearchRecruitment(search);
			model.addAttribute("recruitmentList", recruitmentList);

			// =========== 후기글 ===========
			Set<ReviewViewVO> reviewList = this.searchService.selectSearchReview(search);
			model.addAttribute("reviewList", reviewList);
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch

	} // searchResult

	@PostMapping("/favorite")
	public void favorite(FavoriteDTO dto) throws ControllerException {
		log.trace("favorite({}) invoked.", dto);

		try {
			// 기존에 좋아요 정보가 있는지 체크
			Integer resultCount = this.favoriteService.getCount(dto);
			log.info("resultCount: {}", resultCount);
			
			if (resultCount == 1) { // 있는 경우 수정
				this.favoriteService.modify(dto);
			} else { // 없는 경우 등록
				this.favoriteService.register(dto);
			} // if-else

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // favorite

} // end class
