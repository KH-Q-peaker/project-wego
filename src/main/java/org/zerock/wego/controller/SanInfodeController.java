package org.zerock.wego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.domain.info.SanInfodeVO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.service.info.SanInfodeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/info")
@Controller
public class SanInfodeController {
   
   private final SanInfodeService sanInfodeService;
   
   @GetMapping("/{sanInfoId}")
   ModelAndView showDetail(@PathVariable("sanInfoId")Integer sanInfoId) {
      log.info("showDetail.......... ");
     
    	  ModelAndView mv1 = new ModelAndView();
          mv1.setViewName("/info/infode1"); // 뷰의 이름
          mv1.addObject("sanInfoId", sanInfoId); // 뷰로 보낼 데이터 값
          
          return mv1;
   }
    	 
   
   @GetMapping("/kakaomap")
   public String showkakaomap() {
	   
	   return "/info/infode4";
	   
   }
   
   @GetMapping("/weather")
   public String showWeather() {
	   
	   return "/info/infode3";
   }
   
}
