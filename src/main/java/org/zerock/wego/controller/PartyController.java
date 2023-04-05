package org.zerock.wego.controller;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.wego.domain.CommentVO;
import org.zerock.wego.domain.Target;
import org.zerock.wego.domain.PartyVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.CommentService;
import org.zerock.wego.service.LikeService;
import org.zerock.wego.service.PartyService;
import org.zerock.wego.service.UserService;

import com.google.gson.Gson;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/party")
@Controller
public class PartyController {

	@Autowired
	private PartyService partyService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LikeService likeService;
	
	
	@ModelAttribute("target")
	Target createTarget() {
		log.trace("createTarget() invoked.");
		
		Target target = new Target();

		target.setTargetGb("SAN_PARTY");
		target.setCurrPage(1);
		target.setAmount(5);
		
		return target;
	}// createdBoardDTO
	
	
	
	// 모집글 상세 조회 
	@GetMapping("/{partyId}") 
	public ModelAndView viewPartyDetailByPartyId(@PathVariable("partyId")Long partyId, 
										@SessionAttribute("__AUTH__")Long userId,
										Target target) throws ControllerException{
		log.trace("viewPartyDetail({}, {}) invoked.", partyId, target);
		
		try {
			target = this.createTarget();
			target.setTargetCd(partyId);
			
			
			ModelAndView mav = new ModelAndView();
			

			PartyVO party = this.partyService.getPartyByPartyId(target.getTargetCd());
			
			boolean isJoin = this.partyService.isUserJoin(partyId, userId);
			boolean isLike = this.likeService.isUserLike(target, userId);
			
			int totalCnt = this.commentService.getCommentsCount(target);
			
//			String userPic = this.userService.getUserPic(party.getUserPic());
			String userPic = party.getUserPic();
			

			LinkedBlockingDeque<CommentVO> comments = commentService.getCommentsOffsetByTarget(target);

			Objects.requireNonNull(party);
			
 			String partyImg = this.partyService.getPartyImgByPartyId(partyId);
 			
			
			mav.addObject("party", party);
			mav.addObject("isJoin", isJoin);
			mav.addObject("isLike", isLike);
			mav.addObject("totalCnt", totalCnt);
			mav.addObject("userPic", userPic);
			mav.addObject("partyImg", partyImg);
			
			if(comments != null) {
				
				mav.addObject("comments", comments);
			}
			
			Gson gson = new Gson();
			String targetJson = gson.toJson(target);
			mav.addObject("target", targetJson);

			mav.setViewName("/party/party-detail");

			return mav;

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	}// viewReviewDetail
	
	
	
	// 모집글 삭제 
	@DeleteMapping("/{partyId}")
	public String removePartyByPartyId(@PathVariable Long partyId,
										@SessionAttribute("__AUTH__") Long userId,
										RedirectAttributes rttrs) throws ControllerException{
		log.trace("removePartyByPartyId({}, {}) invoked.", partyId, userId);
		
		try {
			boolean isSuccess = this.partyService.isRemoveByPartyId(partyId, userId);

			rttrs.addFlashAttribute("partyId", partyId); // 얘는 어따쓰지? 
			rttrs.addAttribute("result", isSuccess ? "success" : "failure");

			
			return "redirect:/party";// 목록이동으로 하기 ****

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	}// removeReview
	
	
	
	// 참여 신청 
	@PostMapping("/join/{partyId}")	
	ResponseEntity<String> createJoinByPartyId (@PathVariable Long partyId, 
												@SessionAttribute("__AUTH__") Long userId) throws ControllerException{
		log.trace("createJoinByPartyId({}, {}) invoked.", partyId, userId);
		
		try {
			
			if(this.partyService.isJoinCreate(partyId, userId)) {
				
				return new ResponseEntity<>("OK", HttpStatus.OK);
			}// if
			
			return new ResponseEntity<>("XX", HttpStatus.BAD_REQUEST);
			
		}catch(Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// offerJoin
	
	
	// 참여 취소 
//	@PostMapping("/cancle")
	@DeleteMapping("/join/{partyId}")
	ResponseEntity<String> cancleJoinByPartyId(@PathVariable Long partyId, 
												@SessionAttribute("__AUTH__") Long userId) throws ControllerException{
		log.trace("cancleJoinByPartyId({}, {}) invoked.", partyId, userId);
		
		try {
			
			if(this.partyService.isJoinCancle(partyId, userId)) {
				
				return new ResponseEntity<>("OK", HttpStatus.OK);
			}// if
			
			return new ResponseEntity<>("XX", HttpStatus.BAD_REQUEST);
			
		}catch(Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// offerJoin
	
}// end class
