package org.zerock.wego.controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.common.BoardSearchDTO;
import org.zerock.wego.domain.common.FavoriteVO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.info.SanInfoViewSortVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.common.FavoriteService;
import org.zerock.wego.service.info.SanInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/info")
@Controller
public class SanInfoController {
	private final SanInfoService sanInfoService;
	private final FavoriteService favoriteService;

	@GetMapping("")
	public String showSanInfo(@SessionAttribute(value = "__AUTH__", required = false) UserVO auth, BoardDTO dto,
			Model model) throws ControllerException {
		log.trace("showSanInfo(dto, model) invoked.{}", dto);

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			List<SanInfoViewSortVO> sanInfoSortList = this.sanInfoService.getSortAbcList(dto);
			model.addAttribute("sanInfoSortList", sanInfoSortList);

			double pageCount = Math.ceil(this.sanInfoService.getTotalCount() / dto.getAmount());
			int maxPage = (int)pageCount;
			model.addAttribute("maxPage", maxPage);
			
			return "info/info";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // showSanInfo

	@PostMapping("")
	public String addSanInfo(@SessionAttribute(value = "__AUTH__", required = false) UserVO auth, BoardDTO dto,
			Model model) throws ControllerException {
		log.trace("addSanInfo(dto, model) invoked.", model);

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			if (dto.getOrderBy().equals("like")) {
				List<SanInfoViewSortVO> sanInfoSortList = this.sanInfoService.getSortLikeList(dto);
				model.addAttribute("sanInfoSortList", sanInfoSortList);
			} else {
				List<SanInfoViewSortVO> sanInfoSortList = this.sanInfoService.getSortAbcList(dto);
				model.addAttribute("sanInfoSortList", sanInfoSortList);
			} // if-else
			
			return "info/infoItem";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // addSanInfo

	@GetMapping("/search")
	public String showSanInfoSearchResult(@SessionAttribute(value = "__AUTH__", required = false) UserVO auth,
			BoardSearchDTO dto, Model model) throws ControllerException {
		log.trace("showSanInfoSearchResult(dto, model) invoked.");

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			List<SanInfoViewSortVO> sanInfoSortList = this.sanInfoService.getSearchSortAbcList(dto);
			if (sanInfoSortList == null || sanInfoSortList.isEmpty()) {
				List<SanInfoViewSortVO> sanInfoSuggestion = this.sanInfoService.getSanInfoSuggestion();
				model.addAttribute("sanInfoSuggestion", sanInfoSuggestion);
			
				return "info/infoSearchFail";
			} else {
				model.addAttribute("sanInfoSortList", sanInfoSortList);

				String query = dto.getQuery();
				double pageCount = Math.ceil(this.sanInfoService.getTotalCountByQuery(query) / dto.getAmount());
				int maxPage = (int)pageCount;
				model.addAttribute("maxPage", maxPage);
				
				return "info/infoSearch";
			} // if-else
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // showSanInfoSearchResult

	@PostMapping("/search")
	public String addSanInfoSearchResult(@SessionAttribute(value = "__AUTH__", required = false) UserVO auth,
			BoardSearchDTO dto, Model model) throws ControllerException {
		log.trace("addSanInfoSearchResult(dto, model) invoked.");

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			List<SanInfoViewSortVO> sanInfoSortList = this.sanInfoService.getSearchSortAbcList(dto);
			model.addAttribute("sanInfoSortList", sanInfoSortList);
			
			return "info/infoItem";			
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // addSanInfoSearchResult

} // end class