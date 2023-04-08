package org.zerock.wego.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.PageInfo;
import org.zerock.wego.domain.ReportDTO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.service.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Controller
@RequestMapping("/report")
public class ReportController {

	
	private final ReportService reportService;
	
	
	// 신고 접수  
	@PostMapping("/create")
	ResponseEntity<String> createReport(PageInfo target,
										@SessionAttribute("__AUTH__")UserVO user, 
										String reportGb) throws Exception{
		log.trace("createReport({}, {}, {}) invoked.", user, target, reportGb);

		Integer userId = user.getUserId();
		
		ReportDTO report = new ReportDTO();
		report.setUserId(userId);
		report.setTargetGb(target.getTargetGb());
		report.setTargetCd(target.getTargetCd());
		report.setReportGb(reportGb);
		
		boolean isCreated = (this.reportService.create(report));
		
		if(isCreated) {
			
			return new ResponseEntity<>("OK", HttpStatus.OK);
			/*접수 완료되고 신고수 카운트해서 블라인드 처리될지말지 해야함
			 * 만약 블라인드 처리 된다면 이동을 해당 페이지가아니라 목록으로 가야할 듯 
			 * 아니면 그냥 둘다 목록으로 이동시키기 
			 * 목록 불러올 때 신고수 확인하는 작업 필요함 
			 * */
		}// if
		
		return new ResponseEntity<String>("XX", HttpStatus.NOT_FOUND);
	}// createReport
	
	
}// end class
