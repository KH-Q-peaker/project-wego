package org.zerock.wego.controller;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.FavoriteVO;
import org.zerock.wego.domain.SanInfoViewVO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.FavoriteService;
import org.zerock.wego.service.SanInfoService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@RequestMapping("/info")
@Controller
public class SanInfoController {
	private SanInfoService sanInfoService;
	private FavoriteService favoriteService;

	@GetMapping("")
	public String sanInfo(@SessionAttribute(value = "__AUTH__", required = false)UserVO auth, Model model) throws ControllerException {
		log.trace("sanInfo({}, {}) invoked.", auth, model);

		try {
			if(auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			Objects.requireNonNull(sanInfoService);
			log.trace("@@ this.SanInfoService: {}", this.sanInfoService);

			List<SanInfoViewVO> sanInfoList = this.sanInfoService.getList();

			model.addAttribute("sanInfoList", sanInfoList);

			return "info/info";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // sanInfo

} // end class