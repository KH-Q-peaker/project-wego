package org.zerock.wego.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.common.FavoriteDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.common.FavoriteService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor

@RequestMapping("/")
@RestController
public class FavoriteController {
	private FavoriteService service;
	
	@PostMapping("/favorite")
	public Map<String, String> favorite(
			@SessionAttribute(name = SessionConfig.AUTH_KEY_NAME, required = false) UserVO auth,
			FavoriteDTO dto
			) throws ControllerException {
		log.trace("favorite({}) invoked.", dto);

		try {
			Map<String, String> state = new HashMap<>();
			
			if (auth == null) {
				state.put("state", "failed");
				
				return state;
			} // if
			
			dto.setUserId(auth.getUserId());
			
			boolean isFavoriteInfo = this.service.isFavoriteInfo(dto);
			log.info("isFavoriteInfo: {}", isFavoriteInfo);
			
			if (isFavoriteInfo) { 
				this.service.modify(dto);
			} else { 
				this.service.register(dto);
			} // if-else
			
			state.put("state", "successed");

			return state;
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // favorite

} // end class
