package org.zerock.wego.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.common.PageInfo;
import org.zerock.wego.domain.common.ReportDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.AccessBlindException;
import org.zerock.wego.exception.DuplicateKeyException;
import org.zerock.wego.service.common.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Controller
@RequestMapping("/report")
public class ReportController {

	private final ReportService reportService;

	// 신고 접수
	@PostMapping(path="/create", produces="text/plain; charset=UTF-8")
	ResponseEntity<String> createReport(ReportDTO report,
										@SessionAttribute(SessionConfig.AUTH_KEY_NAME)UserVO user, 
										String reportGb) throws Exception{
		log.trace("createReport() invoked.");
		
		try {
			report.setUserId(user.getUserId());
		
			this.reportService.create(report);
		
			Integer totalCount = this.reportService.getTotalCount(report);
		
			return ResponseEntity.ok().body(totalCount.toString());
		
		} catch(DuplicateKeyException e) {
			
			return ResponseEntity.badRequest().body("이미 접수된 신고입니다");
		}// try-catch
	}// createReport

}// end class
