package org.zerock.wego.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.JoinDTO;
import org.zerock.wego.domain.PageInfo;
import org.zerock.wego.domain.ReportDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.exception.DuplicateKeyException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.service.JoinService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Controller
@RequestMapping("/join")
public class JoinController {

	private final JoinService joinService;

	// 참여 신청/취소 토글
	@PostMapping("/{partyId}")
	ResponseEntity<String> offerJoin(@PathVariable Integer partyId, 
									@SessionAttribute("__AUTH__") UserVO user) throws ControllerException {
//		log.trace("offerJoin({}, {}) invoked.", partyId, user);

		try {
			Integer userId = user.getUserId();

			JoinDTO join = new JoinDTO();
			join.setSanPartyId(partyId);
			join.setUserId(userId);

			this.joinService.createOrCancle(join);

			return ResponseEntity.ok().build();

		} catch (OperationFailException e) {
			return ResponseEntity.badRequest().body("참여할 수 없는 모집입니다.");
		} // try-catch
	}// offerJoin

	// 참여 삭제
//		@PostMapping("/cancle")
//		@DeleteMapping("/join/{partyId}")
//		ResponseEntity<String> cancleJoin(@PathVariable Integer partyId, 
//										 @SessionAttribute("__AUTH__") UserVO user) throws ControllerException {
//			log.trace("cancleJoin({}, {}) invoked.", partyId, user);
//
//			try {
//				Integer userId = user.getUserId();
//				Objects.nonNull(userId);
//				
//				JoinDTO join = new JoinDTO();
//				join.setSanPartyId(partyId);
//				join.setUserId(userId);
//				
//				if (this.joinService.isJoinCancled(join)) {
//
//					return new ResponseEntity<>("OK", HttpStatus.OK);
//				} // if
//
//				return new ResponseEntity<>("XX", HttpStatus.BAD_REQUEST);
//
//			} catch (Exception e) {
//				throw new ControllerException(e);
//			} // try-catch
//		}// cancleJoin

}// end class
