package org.zerock.wego.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.wego.domain.common.Criteria;
import org.zerock.wego.domain.common.FavoriteVO;
import org.zerock.wego.domain.common.FileDTO;
import org.zerock.wego.domain.common.PageDTO;
import org.zerock.wego.domain.common.UserDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.info.SanInfoViewVO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.domain.profile.MyPartyVO;
import org.zerock.wego.domain.profile.ProfileCommentVO;
import org.zerock.wego.domain.profile.ProfileVO;
import org.zerock.wego.domain.review.ReviewViewVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.service.common.FavoriteService;
import org.zerock.wego.service.info.SanInfoService;
import org.zerock.wego.service.party.PartyService;
import org.zerock.wego.service.profile.ProfileService;
import org.zerock.wego.service.review.ReviewService;

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
	public String getUserInfo(
			@PathVariable("userId") Integer userId,
			@SessionAttribute("__AUTH__")UserVO user,
			Model model) throws ControllerException {
		
	    log.trace("getUserInfo({}) invoked.", userId);

	    try {
	    	//세션의 아이디와 쿼리스트링으로 전달된 아이디를 비교해서,
	    	// 나의 프로필과 유저 프로필을 보여주기.
	    	Integer sessionUserId = user.getUserId();
	    	log.info("sessionUserId: {}",sessionUserId);
	    	log.info("userId:{}",userId);
	    	log.info("sessionUserId.equals(userId) : {}", sessionUserId.equals(userId));
	    	if(sessionUserId.equals(userId)) {
	    		UserVO vo = service.getUserById(userId);
	    		String path = vo.getUserPic();
				
				if(! ( path == null || path.isEmpty())) {
					log.info("path: "+ path);
					if(path.contains("upload")) {
						String [] pathArray = path.split("upload");
						log.info("pathArray[1]:"+pathArray[1]);
						model.addAttribute("UserPicName", pathArray[1]);
					}
					log.info("path: {} " , path);
				}
				model.addAttribute("vo",vo);
				return "mypage/mypage";
	    	} else {
		    	// 유저 정보 먼저 만들어놓기. 
		    	UserVO getUserInfoList = this.service.getUserById(userId);
		        model.addAttribute("getUserInfoList", getUserInfoList);
				model.addAttribute("userId", userId);
				return "profile/userpage";
	    	}
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
	        cri.setAmount(10);
			
			// Step 1. 페이징 처리된 현제 currPage에 해당하는 게시글목록을 받아옴 
			List<ProfileVO> writtenList = this.service.getListByUserId(userId,cri);
			model.addAttribute("writtenList", writtenList); 
			model.addAttribute("userId", userId);

			// Step2. Pagination 위한 각종 변수 값을 계산하기
			int totalAmount = this.service.getTotalAmountByUserId(userId);
			PageDTO pageDTO = new PageDTO(cri,totalAmount);
			log.info("\t+ pageDTO: {}", pageDTO);

			model.addAttribute("pageMaker",pageDTO);
			model.addAttribute("__UserPostsCurrPage__",cri.getCurrPage());
			
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
	        cri2.setAmount(10);
			
			List<ProfileCommentVO> commentList = this.service.getListComment(userId,cri2);
			model.addAttribute("commentList", commentList); 
			model.addAttribute("userId", userId);
			
			int totalAmount = this.service.getTotalAmountComment(userId);
			PageDTO pageDTO2 = new PageDTO(cri2,totalAmount);
			log.info("\t+ pageDTO: {}", pageDTO2);

			model.addAttribute("pageMaker",pageDTO2);
			model.addAttribute("__CommentCurrPage__",cri2.getCurrPage());
			
			return "profile/comment";
		} catch(Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // commentByProfile
	
	@GetMapping("/changeNick")
	public String changeNick(
			@RequestParam("userId")Integer userId,
			@RequestParam("nickname") String nickname,
			Model model,HttpServletRequest req) throws ServiceException {
		try {
			UserDTO dto = new UserDTO(userId,null,nickname,null,null,null,null);
			model.addAttribute("dto",dto);
			boolean is = service.modifyNickByUserDTO(dto) == 1;
			
			//ajax 닉네임jsp로 모델도 같이 전달
			return "/mypage/nickname";
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}//try-catch
	}
	
	@GetMapping("/info")
	public String showMyInfo(
			@RequestParam("userId") Integer userId, Model model) throws ServiceException {
		log.trace("showMyInfo({},{}) invoked ",userId, model);
		try {
			UserVO vo = service.getUserById(userId);
			model.addAttribute("__VO__",vo);
			return "mypage/myinfo";
		}catch(Exception e) {
			throw new ServiceException(e);
		}//try-catch
	}//selectInfo
	
	
	@GetMapping("/partyList")
	public String showMyAvailableParty(Criteria acri,
			@RequestParam("userId")Integer userId, Model model) throws ControllerException{
		try {
			List<MyPartyVO> vo = new ArrayList<>();
			Integer.valueOf(userId);
			acri.setAmount(10);
			vo = this.service.showAvailablePartyByUserIdAndAcri(Integer.valueOf(userId), acri);
			
			assert vo != null;
			
			log.info("~~~~!!!!! vo:{} ", vo);
			model.addAttribute("availableParty",vo);

			
			int totalAvailablePartyAmount = this.service.getTotalAmountAvailablePartyByUserId(Integer.valueOf(userId));
			PageDTO availPageDTO = new PageDTO(acri,totalAvailablePartyAmount);
			log.info("*************************pageDTO:{}", availPageDTO);
			model.addAttribute("availPage",availPageDTO);
			model.addAttribute("__aCurrPage__",acri.getCurrPage());
			model.addAttribute("userId",userId);
			
			return "mypage/availableParty";
			
		} catch(Exception e) {
			throw new ControllerException(e);
		} //try-catch
	}//showMyClimbPage
	
	
	@GetMapping("/pastPartyList")
	public String showMyPastParty(Criteria pcri,
			@RequestParam("userId")Integer userId, Model model) throws ControllerException{
		try {
			pcri.setAmount(10);
			List<MyPartyVO> vo2 = new ArrayList<>();
			vo2 = this.service.showPastPartyByUserIdAndPcri(Integer.valueOf(userId),pcri);
	
			assert vo2 != null;
			log.info("~~~~!!!!! vo2:{}", vo2);
			model.addAttribute("pastParty",vo2);
			
			int totalPastPartyAmount = this.service.getTotalAmountPastPartyByUserId(Integer.valueOf(userId));
			PageDTO pastPageDTO = new PageDTO(pcri,totalPastPartyAmount);
			log.info("*************************pageDTO:{}", pastPageDTO);
			model.addAttribute("pastPage",pastPageDTO);
			model.addAttribute("__pCurrPage__",pcri.getCurrPage());
			model.addAttribute("userId",userId);
			
			return "mypage/pastParty";
			
		} catch(Exception e) {
			throw new ControllerException(e);
		} //try-catch
	}//showMyClimbPage
	
	
	@PostMapping("/infoset")
	public String infoSet(Integer userId, String address, 
			String sanRange, String sanTaste, Model model) throws ControllerException{
		log.trace("infoSet({},{},{},{}) invoked",userId,address,sanRange,sanTaste);
		try {
			UserDTO dto = new UserDTO(userId,null,null,address,sanRange,sanTaste,null);
			service.setMyInfoByUserDTO(dto);
			log.info("계정의 취향정보가 업데이트 되었습니다");
			
		}catch(Exception e) {
			throw new ControllerException(e);
		}
		return "redirect:/profile/" + userId;
	}//infoSet

	@PostMapping("/image")
	String saveProfileImage(@RequestParam(value="part", required=false) MultipartFile part, @RequestParam("userId") Integer userId, HttpServletRequest req) {
		
		log.trace("fileSave({}) invoked.",part);
		log.info("part.getName():{}",part.getName());
		
		//폴더 생성하기
		Date date = new Date();
		Timestamp ts=new Timestamp(date.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		str = str.replace("-", "");
		
		String ori_filename=part.getOriginalFilename();
		String uuid = UUID.randomUUID().toString();
        File uploadPath = new File("C:\\upload", str);
        File saveFile = new File(uploadPath, uuid);
        log.info("~~~~~~ saveFile.getPath: {}",saveFile.getPath());
        String path = "C:\\upload\\" + str + "\\" + uuid;
        Path directoryPath = Paths.get("C:\\upload\\" + str);
    	if (!uploadPath.exists()) {
    		try{
    			Files.createDirectories(directoryPath);
    	        } 
    	     catch(Exception e){
    		 e.getStackTrace();
    	     }    
    	}
        try {
          part.transferTo(saveFile);
        } catch (Exception e) {
          log.error(e.getMessage());
        } // end catch

		FileDTO dto = FileDTO.builder()
				.targetGb("USER_PROFILE")
				.targetCd(userId)
				.fileName(ori_filename)
				.uuid(uuid)
				.path(path)
				.build();
		
		service.saveUserPictureInFileTbByFileDTO(dto);
		log.info("**********userId:{}",userId);
		
		UserDTO dto2 = new UserDTO(userId,null,null,null,null,null,path);
		service.updateUserPicByUserDTO(dto2);
	
		return "redirect: /profile/" + userId;
	}//saveProfileImage
	
	@GetMapping("/mypost")
	public String myPostsByProfile(@RequestParam("userId") Integer userId,Criteria cri, Model model) throws ControllerException {	// 게시판전체목록조회 요청처리 핸들러
		log.trace("postsByProfile({}, {}, {}) invoked.", userId,cri,model);
		
		try {
			log.info(">>>>>>>>>>>>>>>>!!!!!!!!!myPostsByProfile이 실행되었습니다.");

	        // Step 1. 현재 페이지 번호를 가져와서 null이거나 1보다 작은 경우 1로 설정
	        int currPage = cri.getCurrPage() == null || cri.getCurrPage() < 1 ? 1 : cri.getCurrPage();
	        cri.setCurrPage(currPage);
	        cri.setAmount(10);
			// Step 1. 페이징 처리된 현제 currPage에 해당하는 게시글목록을 받아옴 
			List<ProfileVO> writtenList = this.service.getListByUserId(userId,cri);
			model.addAttribute("writtenList", writtenList); 
			model.addAttribute("userId", userId);

			// Step2. Pagination 위한 각종 변수 값을 계산하기
			int totalAmount = this.service.getTotalAmountByUserId(userId);
			PageDTO pageDTO = new PageDTO(cri,totalAmount);
			log.info("\t+ pageDTO: {}", pageDTO);

			model.addAttribute("pageMaker",pageDTO);
			model.addAttribute("__MyPostCurrPage__",cri.getCurrPage());
			
			return "mypage/myPost";
		} catch(Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // myPostsByProfile
	
	@GetMapping("/mycomment")
	public String myCommentByProfile(@RequestParam("userId") Integer userId,Criteria cri2, Model model) throws ControllerException {	
		log.trace("comment({}, {}, {}) invoked.", userId,cri2,model);
		
		try {
			log.info(">>>>>>>>>>>>>>>>!!!!!!!!!myCommentByProfile이 실행되었습니다.");

	        int currPage = cri2.getCurrPage() == null || cri2.getCurrPage() < 1 ? 1 : cri2.getCurrPage();
	        cri2.setCurrPage(currPage);
	        cri2.setAmount(10);
	        
			List<ProfileCommentVO> commentList = this.service.getListComment(userId,cri2);
			model.addAttribute("commentList", commentList); 
			model.addAttribute("userId", userId);
			
			int totalAmount = this.service.getTotalAmountComment(userId);
			PageDTO pageDTO2 = new PageDTO(cri2,totalAmount);
			log.info("\t+ pageDTO: {}", pageDTO2);

			model.addAttribute("pageMaker",pageDTO2);
			model.addAttribute("__MyCommentCurrPage__",cri2.getCurrPage());
			
			return "mypage/myComment";
		} catch(Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // myCommentByProfile
	
	
	private SanInfoService sanInfoService;
	private PartyService partyService;
	private ReviewService reviewService;
	private FavoriteService favoriteService;
	
	
	@GetMapping("/mylike")
	public String main(
			@SessionAttribute(value = "__AUTH__", required = false)UserVO auth, Model model) 
					throws ControllerException {
		log.trace("main({}, {}) invoked.", auth, model);

		try {
			if(auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			Set<SanInfoViewVO> sanInfoList = this.sanInfoService.getRandom10List();
			model.addAttribute("sanInfoList", sanInfoList);

			Set<PartyViewVO> partyList = this.partyService.getRandom10List();
			model.addAttribute("partyList", partyList);

			Set<ReviewViewVO> reviewList = this.reviewService.getRandom10List();
			model.addAttribute("reviewList", reviewList);

			return "mypage/myLike";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // main
	
	
} // end class