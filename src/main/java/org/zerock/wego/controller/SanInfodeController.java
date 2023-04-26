package org.zerock.wego.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.domain.info.SanInfodeVO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.exception.ControllerException;
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
   public String showDetail(@PathVariable("sanInfoId")Integer sanInfoId, Model model) throws ControllerException {
      log.info("showDetail.......... ");
      
    try {
      	List<SanInfodeVO> sanInfodeList = this.sanInfodeService.getById(sanInfoId);
//    	ModelAndView mv1 = new ModelAndView("info/infode1");
      	model.addAttribute("sanInfodeList", sanInfodeList);
      	
      	return "/info/infode1";
    }catch (Exception e) {
		throw new ControllerException(e);
	} // try-catch
      	  
      
      //ModelAndView mv1 = new ModelAndView("info/infode1");
//    	  
//          mv1.addObject("sanInfodeList", sanInfodeList); // 뷰로 보낼 데이터 값
          
//          return mv1;
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
