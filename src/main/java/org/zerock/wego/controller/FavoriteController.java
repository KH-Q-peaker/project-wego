package org.zerock.wego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.FavoriteService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor

@RequestMapping("/") // BASE URL
@Controller
public class FavoriteController {
	private FavoriteService service;
	
	@PostMapping("/favorite")
	public void favorite(FavoriteDTO dto) throws ControllerException {
		log.trace("favorite({}) invoked.", dto);

		try {
			// 기존에 좋아요 정보가 있는지 체크
			Integer resultCount = this.service.getCount(dto);
			log.info("resultCount: {}", resultCount);
			
			if (resultCount == 1) { // 있는 경우 수정
				this.service.modify(dto);
			} else { // 없는 경우 등록
				this.service.register(dto);
			} // if-else

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // favorite

} // end class
