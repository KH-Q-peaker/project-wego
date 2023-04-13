package org.zerock.wego.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
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
		
//		ReportDTO report = new ReportDTO();
//		report.setUserId(userId);
//		report.setTargetGb(target.getTargetGb());
//		report.setTargetCd(target.getTargetCd());
//		report.setReportGb(reportGb);
		
		ReportDTO report = ReportDTO.builder()
							.userId(userId)
							.targetGb(target.getTargetGb())
							.targetCd(target.getTargetCd())
							.reportGb(reportGb)
							.build();
		
		boolean isCreated = (this.reportService.isCreate(report));
		
		if(isCreated) {
			
			Integer totalCount 
					= this.reportService.getTotalCount(target.getTargetGb(), target.getTargetCd());
			
			return ResponseEntity.ok().body(totalCount.toString());
			
		}// if
		
		return ResponseEntity.badRequest().body(null);
	}// createReport
//	// 신고 접수  
//		@PostMapping("/create")
//		ModelAndView createReport(PageInfo target,
//								@SessionAttribute("__AUTH__")UserVO user, 
//								String reportGb) throws Exception{
//			log.trace("createReport({}, {}, {}) invoked.", user, target, reportGb);
//			
//			ModelAndView mav = new ModelAndView();
//			mav.addObject("targetGb", target.getTargetGb());
//			mav.addObject("targetCd", target.getTargetCd());
//			
//			if(target.getTargetGb().equals("SAN_PARTY")) {
//				
//				mav.setViewName("party/detail");
//			}// if
//			
//			Integer userId = user.getUserId();
//			
//			ReportDTO report = new ReportDTO();
//			report.setUserId(userId);
//			report.setTargetGb(target.getTargetGb());
//			report.setTargetCd(target.getTargetCd());
//			report.setReportGb(reportGb);
//			
//			boolean isCreated = (this.reportService.create(report));
//			
//			if(isCreated) {
//				return mav;
//			}// if
//			return mav;
//		}// createReport
//	
	
}// end class
