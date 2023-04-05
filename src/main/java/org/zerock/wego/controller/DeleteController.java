package org.zerock.wego.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.zerock.wego.domain.Target;
import org.zerock.wego.service.ReportService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@NoArgsConstructor
//
//@Controller
//@RequestMapping("/delete")
//public class DeleteController {
//
//	@Setter(onMethod_= {@Autowired})
//	private ReportService service;
//	
//	
//	// 삭
//	@PostMapping("/{targetGb}/{targetCd}")
//	ResponseEntity<String> removeTarget(@SessionAttribute("__AUTH__")Long userId, 
//										Target target, String reportGb) throws Exception{
//
//		if(target.getTargetGb().equals("SAN_REVIEW))
//		if(this.service.createReport(userId, target, reportGb)) {
//			
//			return new ResponseEntity<>("OK", HttpStatus.OK);
//	
//		}// if
//		
//		return new ResponseEntity<String>("XX", HttpStatus.NOT_FOUND);
//	}// loadcomment
//	
//	
//}// end class
