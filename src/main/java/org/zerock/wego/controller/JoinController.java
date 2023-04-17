package org.zerock.wego.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.common.PageInfo;
import org.zerock.wego.domain.common.ReportDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.party.JoinDTO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.exception.DuplicateKeyException;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.service.party.JoinService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Controller
@RequestMapping("/join")
public class JoinController {

	private final JoinService joinService;

	// 참여 신청/취소 토글
	@PostMapping(path="/{partyId}", produces="text/plain; charset=UTF-8")
	ResponseEntity<String> joinOrCancleByPartyId(@PathVariable Integer partyId, 
												@SessionAttribute("__AUTH__") UserVO user) throws Exception {
		log.trace("joinOrCancleByPartyId() invoked.");

		try {
			Integer userId = user.getUserId();

			JoinDTO join = new JoinDTO();
			join.setSanPartyId(partyId);
			join.setUserId(userId);

			this.joinService.createOrCancle(join);
			Integer currentCount = this.joinService.getCurrentCount(join);
			
			return ResponseEntity.ok(currentCount.toString());

		} catch (OperationFailException e) {
			
			return ResponseEntity.badRequest().body("모집 인원이 가득 찼습니다.");
			
		} catch (NotFoundPageException e) {
			
			return ResponseEntity.badRequest().body("해당 모집글을 찾을 수 없습니다.");
		} // try-catch
	}// offerJoin

}// end class
