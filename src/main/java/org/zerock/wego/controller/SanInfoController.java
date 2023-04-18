package org.zerock.wego.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.info.SanInfoViewVO;
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
	public String openSanInfo(Model model) throws ControllerException {
		log.trace("openSanInfo({}) invoked.", model);

		try {
			List<SanInfoViewVO> sanInfoList = this.sanInfoService.getList();

			model.addAttribute("sanInfoList", sanInfoList);

			return "info/info";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // openSanInfo
	

} // end class