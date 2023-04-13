package org.zerock.wego.controller.mypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.wego.domain.mypage.ACriteria;
import org.zerock.wego.domain.mypage.APageDTO;
import org.zerock.wego.domain.mypage.PCriteria;
import org.zerock.wego.domain.mypage.PPageDTO;
import org.zerock.wego.domain.mypage.TsanPartyVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.mypage.MyClimbService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Controller
@RequestMapping("/mypage")
public class MyClimbController {
	
	@Setter(onMethod_={@Autowired})
	private MyClimbService service;
	
	
	@GetMapping("/myclimb")
	public String showMyClimbPage(ACriteria acri,PCriteria pcri,
			@RequestParam("user_id")String userId, Model model) throws ControllerException{
		try {
			List<TsanPartyVO> vo = new ArrayList<>();
			Integer.valueOf(userId);
			vo = this.service.availableParty(Integer.valueOf(userId),acri);
			
			List<TsanPartyVO> vo2 = new ArrayList<>();
			vo2 = this.service.pastParty(Integer.valueOf(userId),pcri);
			
			assert vo != null;
			assert vo2 != null;
			log.info("~~~~!!!!! vo:{} / vo2:{}", vo, vo2);
			model.addAttribute("availableParty",vo);
			model.addAttribute("pastParty",vo2);

			
			int totalAvailablePartyAmount = this.service.getTotalAmountByMyAvailableParty(Integer.valueOf(userId));
			int totalPastPartyAmount = this.service.getTotalAmountByMyPastParty(Integer.valueOf(userId));
			APageDTO availPageDTO = new APageDTO(acri,totalAvailablePartyAmount);
			PPageDTO pastPageDTO = new PPageDTO(pcri,totalPastPartyAmount);
			log.info("*************************pageDTO:{}", availPageDTO);
			log.info("*************************pageDTO:{}", pastPageDTO);
			model.addAttribute("availPage",availPageDTO);
			model.addAttribute("pastPage",pastPageDTO);
			model.addAttribute("__aCurrPage__",acri.getACurrPage());
			model.addAttribute("__pCurrPage__",pcri.getPCurrPage());
			
			return null;
			
		} catch(Exception e) {
			throw new ControllerException(e);
		}
		
	}//showMyClimbPage

}//end class
