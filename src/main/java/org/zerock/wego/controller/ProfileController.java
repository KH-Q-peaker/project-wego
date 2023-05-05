package org.zerock.wego.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import org.zerock.wego.service.profile.ProfileService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@RequestMapping("/profile") // base URI
@Controller
public class ProfileController {
	private ProfileService profileService;
	private FavoriteService favoriteService;

	@GetMapping("")
	public String getMypage(@SessionAttribute("__AUTH__") UserVO user, Model model) throws ControllerException {

		log.trace("getMypage({}) invoked.", user);

		try {
			Integer userId = user.getUserId();
			UserVO vo = profileService.getUserById(userId);
			String path = vo.getUserPic();

			if (!(path == null || path.isEmpty())) {
				log.info("path: " + path);
				if (path.contains("upload")) {
					String[] pathArray = path.split("upload");
					log.info("pathArray[1]:" + pathArray[1]);
					model.addAttribute("UserPicName", pathArray[1]);
				}//if
			}//if
			model.addAttribute("vo", vo);
			return "mypage/mypage";
		} catch (Exception e) {
			throw new ControllerException(e);
		}//try-catch
	} // getUserInfo

	// 클릭한 userId에 해당하는 유저 페이지를 보여주는 로직
	@GetMapping("/{userId}")
	public String getUserInfo(@PathVariable("userId") Integer userId, Model model) throws ControllerException {

		log.trace("getUserInfo({}) invoked.", userId);

		try {
			log.info("userId:{}", userId);

			UserVO getUserInfoList = this.profileService.getUserById(userId);
			model.addAttribute("getUserInfoList", getUserInfoList);
			model.addAttribute("userId", userId);
			return "profile/userpage";
		} catch (Exception e) {
			throw new ControllerException(e);
		}//try-catch
	} // getUserInfo

	@GetMapping("/userposts")
	public String postsByProfile(@RequestParam("userId") Integer userId, Criteria cri, Model model)
			throws ControllerException { // 게시판전체목록조회 요청처리 핸들러
		
		log.trace("postsByProfile({}, {}, {}) invoked.", userId, cri, model);

		try {
			log.info(">>>>>>>>>>>>>>>>writtenByProfile이 실행되었습니다.");

			// Step 1. 현재 페이지 번호를 가져와서 null이거나 1보다 작은 경우 1로 설정
			int currPage = cri.getCurrPage() == null || cri.getCurrPage() < 1 ? 1 : cri.getCurrPage();
			cri.setCurrPage(currPage);
			cri.setAmount(10);

			// Step 1. 페이징 처리된 현제 currPage에 해당하는 게시글목록을 받아옴
			List<ProfileVO> writtenList = this.profileService.getListByUserId(userId, cri);
			model.addAttribute("writtenList", writtenList);
			model.addAttribute("userId", userId);

			// Step2. Pagination 위한 각종 변수 값을 계산하기
			int totalAmount = this.profileService.getTotalAmountByUserId(userId);
			PageDTO pageDTO = new PageDTO(cri, totalAmount);
			log.info("\t+ pageDTO: {}", pageDTO);

			model.addAttribute("pageMaker", pageDTO);
			model.addAttribute("__UserPostsCurrPage__", cri.getCurrPage());

			return "profile/userposts";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // postsByProfile

	@GetMapping("/comment")
	public String commentByProfile(@RequestParam("userId") Integer userId, Criteria cri2, Model model)
			throws ControllerException {
		
		log.trace("commentByProfile({}, {}, {}) invoked.", userId, cri2, model);

		try {
			log.info(">>>>>>>>>>>>>>>>commentByProfile이 실행되었습니다.");

			int currPage = cri2.getCurrPage() == null || cri2.getCurrPage() < 1 ? 1 : cri2.getCurrPage();
			cri2.setCurrPage(currPage);
			cri2.setAmount(10);

			List<ProfileCommentVO> commentList = this.profileService.getListComment(userId, cri2);
			model.addAttribute("commentList", commentList);
			model.addAttribute("userId", userId);

			int totalAmount = this.profileService.getTotalAmountComment(userId);
			PageDTO pageDTO2 = new PageDTO(cri2, totalAmount);
			log.info("\t+ pageDTO: {}", pageDTO2);

			model.addAttribute("pageMaker", pageDTO2);
			model.addAttribute("__CommentCurrPage__", cri2.getCurrPage());

			return "profile/comment";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // commentByProfile

	@ResponseBody
	@RequestMapping(value = "/changeNickname", produces = "application/text;charset=UTF-8")
	public String changeNick(@SessionAttribute("__AUTH__") UserVO user, String nickname) throws ServiceException {
		
		log.trace("changeNick({}, {}, {}) invoked.", user, nickname);
		
		try {
			Integer userId = user.getUserId();
			if (nickname == null || nickname == "") {
				return "";
			} else if (nickname.contains("\\s")) {
				return "";
			} else if ((nickname.length() < 2) || (nickname.length() > 20)) {
				return "";
			} else if (!(nickname.matches("^[a-zA-Zㄱ-힣0-9*_]{2,20}$"))) {
				return "";
			} else {
				UserDTO dto = UserDTO.changeNickByUserIdAndNickName(userId, nickname);
				boolean is = profileService.modifyNickByUserDTO(dto) == 1;//중복 닉네임이 없다면 != 1
				if (is) {
					log.info("================= 닉네임 바꾸기 성공!!! ================= ");
					return dto.getNickname();
				} else {
					log.info("=================  닉네임 바꾸기 실패... ================= ");
					return "";
				}//if-else
			}//if-else
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}//changeNick

	@ResponseBody
	@RequestMapping(value = "/nicknameCheck", produces = "application/text;charset=UTF-8")
	public String equalNicknameCheck(String nickname) throws ServiceException {
		
		log.trace("equalNicknameCheck({}) invoked", nickname);
		try {
			int equalNicknameCount = profileService.countEqualNicknameByNickname(nickname);
			return String.valueOf(equalNicknameCount);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// equalNicknameCheck

	@GetMapping("/info")
	public String showMyInfo(@SessionAttribute("__AUTH__") UserVO user, Model model) throws ServiceException {
		
		log.trace("showMyInfo({},{}) invoked ", user, model);
		try {
			Integer userId = user.getUserId();
			UserVO vo = profileService.getUserById(userId);
			
			model.addAttribute("__VO__", vo);
			
			return "mypage/myinfo";
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}// selectInfo

	@GetMapping("/partyList")
	public String showMyAvailableParty(Criteria acri, @SessionAttribute("__AUTH__") UserVO user, Model model)
			throws ControllerException {
		
		try {
			Integer userId = user.getUserId();
			List<MyPartyVO> vo = new ArrayList<>();
			acri.setAmount(10);
			vo = this.profileService.showAvailablePartyByUserIdAndAcri(userId, acri);

			assert vo != null;
			model.addAttribute("availableParty", vo);

			int totalAvailablePartyAmount = this.profileService
					.getTotalAmountAvailablePartyByUserId(userId);
			PageDTO availPageDTO = new PageDTO(acri, totalAvailablePartyAmount);
			
			model.addAttribute("availPage", availPageDTO);
			model.addAttribute("__aCurrPage__", acri.getCurrPage());
			
			return "mypage/availableParty";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	}// showMyClimbPage

	@GetMapping("/pastPartyList")
	public String showMyPastParty(Criteria pcri, @SessionAttribute("__AUTH__") UserVO user, Model model)
			throws ControllerException {
		
		log.trace("showMyPastParty({},{},{},{}) invoked", pcri, user, model);
		try {
			Integer userId = user.getUserId();
			pcri.setAmount(10);
			List<MyPartyVO> vo2 = new ArrayList<>();
			vo2 = this.profileService.showPastPartyByUserIdAndPcri(userId, pcri);

			assert vo2 != null;
			model.addAttribute("pastParty", vo2);

			int totalPastPartyAmount = this.profileService.getTotalAmountPastPartyByUserId(userId);
			PageDTO pastPageDTO = new PageDTO(pcri, totalPastPartyAmount);
			
			model.addAttribute("pastPage", pastPageDTO);
			model.addAttribute("__pCurrPage__", pcri.getCurrPage());

			return "mypage/pastParty";

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	}// showMyClimbPage

	@PostMapping("/infoset")
	public String infoSet(@SessionAttribute("__AUTH__") UserVO user, String address, String sanRange, String sanTaste,
			Model model) throws ControllerException {
		
		log.trace("infoSet({},{},{},{}) invoked", user, address, sanRange, sanTaste);
		try {
			Integer userId = user.getUserId();
			UserDTO dto = new UserDTO(userId, null, null, address, sanRange, sanTaste, null);
			profileService.setMyInfoByUserDTO(dto);
			log.info("계정의 취향정보가 업데이트 되었습니다");
			
			return "redirect:/profile/";
		} catch (Exception e) {
			throw new ControllerException(e);
		}
	}// infoSet

	@PostMapping("/image")
	String saveProfileImage(@RequestParam(value = "part", required = false) MultipartFile part,
			@SessionAttribute("__AUTH__") UserVO user, HttpServletRequest req) throws ControllerException {

		log.trace("saveProfileImage({},{},req) invoked.", part,user);

		// 폴더 생성하기
		try {
			Integer userId = user.getUserId();
			Date date = new Date();
			//Timestamp ts = new Timestamp(date.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String str = sdf.format(date);
			str = str.replace("-", "");
			String ori_filename = part.getOriginalFilename();
			String uuid = UUID.randomUUID().toString();
			File uploadPath = new File("/opt/upload", str);
			File saveFile = new File(uploadPath, uuid);
			String path = "/opt/upload/" + str + "/" + uuid;
			Path directoryPath = Paths.get("/opt/upload/" + str);
			
			if (!uploadPath.exists()) {
				Files.createDirectories(directoryPath);
			} // if
			
			String fileType = FilenameUtils.getExtension(ori_filename);
			List<String> yesFileTypeList = new ArrayList<>(Arrays.asList("jpeg", "png", "jpg"));
			log.info("파일 확장자명: {}", fileType);
			
			if (yesFileTypeList.contains(fileType)) {
				part.transferTo(saveFile);
				FileDTO dto = FileDTO.builder().targetGb("USER_PROFILE").targetCd(userId).fileName(ori_filename)
						.uuid(uuid).path(path).build();
				profileService.saveUserPictureInFileTbByFileDTO(dto);
				UserDTO dto2 = UserDTO.updateUserPicByUserIdAndUserPic(userId, path);
				profileService.updateUserPicByUserDTO(dto2);
			}//if
			
			return "redirect: /profile/";
		} catch (Exception e) {
			throw new ControllerException(e);
		}
	}// saveProfileImage

	@GetMapping("/mypost")
	public String myPostsByProfile(@SessionAttribute("__AUTH__") UserVO user, Criteria cri, Model model)
			throws ControllerException { // 게시판전체목록조회 요청처리 핸들러
		
		log.trace("myPostsByProfile({}, {}, {}) invoked.", user, cri, model);

		try {
			Integer userId = user.getUserId();

			// Step 1. 현재 페이지 번호를 가져와서 null이거나 1보다 작은 경우 1로 설정
			int currPage = cri.getCurrPage() == null || cri.getCurrPage() < 1 ? 1 : cri.getCurrPage();
			cri.setCurrPage(currPage);
			cri.setAmount(10);
			
			// Step 2. 페이징 처리된 현제 currPage에 해당하는 게시글목록을 받아옴
			List<ProfileVO> writtenList = this.profileService.getListByUserId(userId, cri);
			model.addAttribute("writtenList", writtenList);
			model.addAttribute("userId", userId);

			// Step 3. Pagination 위한 각종 변수 값을 계산하기
			int totalAmount = this.profileService.getTotalAmountByUserId(userId);
			PageDTO pageDTO = new PageDTO(cri, totalAmount);

			model.addAttribute("pageMaker", pageDTO);
			model.addAttribute("__MyPostCurrPage__", cri.getCurrPage());

			return "mypage/myPost";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // myPostsByProfile

	@GetMapping("/mycomment")
	public String myCommentByProfile(@SessionAttribute("__AUTH__") UserVO user, Criteria cri2, Model model)
			throws ControllerException {
		
		log.trace("myCommentByProfile({}, {}, {}) invoked.", user, cri2, model);

		try {
			Integer userId = user.getUserId();

			int currPage = cri2.getCurrPage() == null || cri2.getCurrPage() < 1 ? 1 : cri2.getCurrPage();
			cri2.setCurrPage(currPage);
			cri2.setAmount(10);

			List<ProfileCommentVO> commentList = this.profileService.getListComment(userId, cri2);
			model.addAttribute("commentList", commentList);
			model.addAttribute("userId", userId);

			int totalAmount = this.profileService.getTotalAmountComment(userId);
			PageDTO pageDTO2 = new PageDTO(cri2, totalAmount);

			model.addAttribute("pageMaker", pageDTO2);
			model.addAttribute("__MyCommentCurrPage__", cri2.getCurrPage());

			return "mypage/myComment";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // myCommentByProfile

	@GetMapping("/mylike")
	public String myLikeList(@SessionAttribute(value = "__AUTH__", required = false) UserVO auth, Model model)
			throws ControllerException {
		
		log.trace("myLikeList({}, {}) invoked.", auth, model);

		try {
			Integer userId = auth.getUserId();
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			Set<SanInfoViewVO> sanInfoList = this.profileService.getLikeSanInfoListByUserIdAndTargetGb(userId,
					"SAN_INFO");
			model.addAttribute("sanInfoList", sanInfoList);

			Set<PartyViewVO> partyList = this.profileService.getLikeSanPartyListByUserIdAndTargetGb(userId,
					"SAN_PARTY");
			model.addAttribute("partyList", partyList);

			Set<ReviewViewVO> reviewList = this.profileService.getLikeSanReviewListByUserIdAndTargetGb(userId,
					"SAN_REVIEW");
			;
			model.addAttribute("reviewList", reviewList);

			return "mypage/myLike";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // main

} // end class