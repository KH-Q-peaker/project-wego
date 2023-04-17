package org.zerock.wego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.wego.domain.common.FavoriteDTO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.common.FavoriteService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor

@RequestMapping("/")
@Controller
public class FavoriteController {
	private FavoriteService service;
	
	@PostMapping("/favorite")
	public void favorite(FavoriteDTO dto) throws ControllerException {
		log.trace("favorite({}) invoked.", dto);

		try {
			boolean isFavoriteInfo = this.service.isFavoriteInfo(dto);
			log.info("isFavoriteInfo: {}", isFavoriteInfo);
			
			if (isFavoriteInfo) { 
				this.service.modify(dto);
			} else { 
				this.service.register(dto);
			} // if-else

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // favorite

} // end class
