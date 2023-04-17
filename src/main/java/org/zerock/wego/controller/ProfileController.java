package org.zerock.wego.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.wego.domain.common.Criteria;
import org.zerock.wego.domain.common.PageDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.profile.ProfileCommentVO;
import org.zerock.wego.domain.profile.ProfileVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.profile.ProfileService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor
@RequestMapping("/profile") // base URI
@Controller
public class ProfileController {
	private ProfileService service;
	
    // 1. 클릭한 userId에 해당하는 유저 페이지를 보여주는 로직

	@GetMapping("/{userId}") 
	public String getUserInfo(@PathVariable("userId") Integer userId, Model model) throws ControllerException {
	    log.trace("getUserInfo({}) invoked.", userId);

	    try {
	    	// 유저 정보 먼저 만들어놓기. 
	    	List<UserVO> getUserInfoList = this.service.getUserById(userId);
	        model.addAttribute("getUserInfoList", getUserInfoList);
			model.addAttribute("userId", userId);
			return "profile/userpage";
	    } catch(Exception e) {
	        throw new ControllerException(e);
	    }
	} // getUserInfo

	@GetMapping("/userposts")
	public String postsByProfile(@RequestParam("userId") Integer userId,Criteria cri, Model model) throws ControllerException {	// 게시판전체목록조회 요청처리 핸들러
		log.trace("postsByProfile({}, {}, {}) invoked.", userId,cri,model);
		
		try {
			log.info(">>>>>>>>>>>>>>>>writtenByProfile이 실행되었습니다.");

	        // Step 1. 현재 페이지 번호를 가져와서 null이거나 1보다 작은 경우 1로 설정
	        int currPage = cri.getCurrPage() == null || cri.getCurrPage() < 1 ? 1 : cri.getCurrPage();
	        cri.setCurrPage(currPage);
			
			// Step 1. 페이징 처리된 현제 currPage에 해당하는 게시글목록을 받아옴 
			List<ProfileVO> writtenList = this.service.getListByUserId(userId,cri);
			model.addAttribute("writtenList", writtenList); 
			model.addAttribute("userId", userId);

			// Step2. Pagination 위한 각종 변수 값을 계산하기
			int totalAmount = this.service.getTotalAmountByUserId(userId);
			PageDTO pageDTO = new PageDTO(cri,totalAmount);
			log.info("\t+ pageDTO: {}", pageDTO);

			model.addAttribute("pageMaker",pageDTO);
			
			return "profile/userposts";
		} catch(Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // postsByProfile
	
	@GetMapping("/comment")
	public String commentByProfile(@RequestParam("userId") Integer userId,Criteria cri2, Model model) throws ControllerException {	
		log.trace("comment({}, {}, {}) invoked.", userId,cri2,model);
		
		try {
			log.info(">>>>>>>>>>>>>>>>commentByProfile이 실행되었습니다.");

	        int currPage = cri2.getCurrPage() == null || cri2.getCurrPage() < 1 ? 1 : cri2.getCurrPage();
	        cri2.setCurrPage(currPage);
			
			List<ProfileCommentVO> commentList = this.service.getListComment(userId,cri2);
			model.addAttribute("commentList", commentList); 
			model.addAttribute("userId", userId);
			
			int totalAmount = this.service.getTotalAmountComment(userId);
			PageDTO pageDTO2 = new PageDTO(cri2,totalAmount);
			log.info("\t+ pageDTO: {}", pageDTO2);

			model.addAttribute("pageMaker",pageDTO2);
			
			return "profile/comment";
		} catch(Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // commentByProfile
	

} // end class