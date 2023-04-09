package org.zerock.wego.controller;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.wego.domain.CommentViewVO;
import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;
import org.zerock.wego.domain.JoinDTO;
import org.zerock.wego.domain.PageInfo;
import org.zerock.wego.domain.PartyDTO;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.CommentService;
import org.zerock.wego.service.FavoriteService;
import org.zerock.wego.service.FileService;
import org.zerock.wego.service.JoinService;
import org.zerock.wego.service.PartyService;
import org.zerock.wego.service.SanInfoService;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/party")
@Controller
public class PartyController {
	
	private final PartyService partyService;
	private final CommentService commentService;
	private final JoinService joinService;
	private final SanInfoService mountainService;
	private final FileService fileService;
	private final FavoriteService fatoriteService;
  
	
	@ModelAttribute("target")
	PageInfo createPageInfo() {
		log.trace("createPageInfo() invoked.");
		
		PageInfo target = new PageInfo();

		target.setTargetGb("SAN_PARTY");
		target.setCurrPage(1);
		target.setAmount(5);
		
		return target;
	}// createdBoardDTO
	
	
	
	// 모집글 상세 조회 
	@GetMapping("/{partyId}") 
	public ModelAndView showDetailById(@PathVariable("partyId")Integer partyId, 
										@SessionAttribute("__AUTH__")UserVO user,
										PageInfo target) throws ControllerException{
		log.trace("showDetailById({}, {}) invoked.", partyId, user);
		
		try {
			target = this.createPageInfo();
			target.setTargetCd(partyId);
			
			
			ModelAndView mav = new ModelAndView();
			

			PartyViewVO party = this.partyService.getById(partyId);
			
			Integer userId = user.getUserId();
			
			JoinDTO join = new JoinDTO();
			join.setSanPartyId(partyId);
			join.setUserId(userId);
			
			boolean isJoin = this.joinService.isUserJoined(join);
			
//			boolean isLike = this.likeService.isUserLiked(target, userId);
			FavoriteDTO favorite = new FavoriteDTO();
			favorite.setTargetGb("SAN_PARTY");
			favorite.setTargetCd(partyId);
			favorite.setUserId(userId);
			
			boolean isFavorite = this.fatoriteService.isFavoriteInfo(favorite);
			
			int commentCount = this.commentService.getCommentsCount(target);
			

			LinkedBlockingDeque<CommentViewVO> comments = commentService.getCommentOffsetByTarget(target, 0);

			
			mav.addObject("party", party);
			mav.addObject("isJoin", isJoin);
			mav.addObject("isFavorite", isFavorite);
			mav.addObject("commentCount", commentCount);
//			mav.addObject("userPic", userPic);
//			mav.addObject("partyImg", partyImg);
			
			if(comments != null) {
				
				mav.addObject("comments", comments);
			}// if
			
			// 수정 예정 ***************************
			Gson gson = new Gson();
			String targetJson = gson.toJson(target);
			mav.addObject("target", targetJson);

			mav.setViewName("/party/detail");

			return mav;

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	}// viewReviewDetail
	
	
	@GetMapping(
			path = "/modify/{partyId}"
			)
	public String modify(
			@SessionAttribute("__AUTH__")UserVO auth,
			@PathVariable("partyId") Integer partyId, Model model) 
			throws ControllerException { 
		log.trace("modify({}, {}, {}) invoked.", auth, partyId, model);

		try {
			Integer postUserId = this.partyService.selectUserIdByPartyId(partyId);
			
			if(auth == null || !auth.getUserId().equals(postUserId)) {
				return "error";
			} // if
			
			PartyViewVO vo = this.partyService.getById(partyId);
			model.addAttribute("party", vo);
			
			return "/party/modify";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // detailAndModify

	

	// 모집글 삭제 
	@DeleteMapping("/{partyId}")
	public String removeById(@PathVariable Integer partyId,
							 @SessionAttribute("__AUTH__") UserVO user,
							 RedirectAttributes rttrs) throws ControllerException{
			log.trace("removePartyByPartyId({}, {}) invoked.", partyId, user);
		
		try {
			Integer userId = user.getUserId();

			boolean isPartyRemoved = this.partyService.isRemovedById(partyId, userId);
			boolean isImgRemoved = this.fileService.remove("SAN_PARTY", partyId);
//			boolean isJoinRemoved = this.joinService.isJoinCancled(partyId, userId);


			boolean isSuccess = (isPartyRemoved && isImgRemoved);

			rttrs.addFlashAttribute("partyId", partyId); // 얘는 어따쓰지? 
			rttrs.addAttribute("result", isSuccess ? "success" : "failure");

			
			return "redirect:/party";// 목록이동으로 하기 **** 아마 신영님꺼로 이동 

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	}// removeReview
	

	@PostMapping("/modify")
	public String modify(
			@SessionAttribute("__AUTH__")UserVO auth,
			Integer sanPartyId, String sanName, MultipartFile imgFile, 
			String date, String time, PartyDTO dto) throws ControllerException { 
		log.trace("modify({}, {}, {}, {}, {}, {}, {}) invoked.", auth, sanPartyId, sanName, imgFile, date, time, dto);

		try {	
			if(auth.getUserId() == dto.getUserId()) {
				return "error";
			} // if
			
			Integer sanId = this.mountainService.selectSanName(sanName);
			dto.setSanInfoId(sanId);

			Timestamp dateTime = Timestamp.valueOf(date + " " + time + ":00");
			dto.setPartyDt(dateTime);


			if (imgFile != null && !"".equals(imgFile.getOriginalFilename())) {
				List<FileVO> fileVo = this.fileService.getList("SAN_PARTY", sanPartyId); 
				Integer oldFileId = fileVo.get(0).getFileId();

				imgFile.transferTo(new File(fileVo.get(0).getPath()));

				this.fileService.modify("SAN_PARTY", sanPartyId, oldFileId, imgFile.getOriginalFilename());
			} // if

			boolean isSuccess = this.partyService.modify(dto);
			log.info("isSuccess: {}", isSuccess);

			return "redirect:/party/detail/" + dto.getSanPartyId();
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // modify

	@GetMapping("/register")
	public void register() { 
		log.trace("register() invoked.");
	} // register

	@PostMapping("/register")
	public String register(
			@SessionAttribute("__AUTH__")UserVO auth,
			String sanName, MultipartFile imgFile, String date, String time,	
			PartyDTO dto, FileDTO fileDto) throws ControllerException {
		log.trace("register({}, {}, {}, {}, {}, {}, {}) invoked.", auth, sanName, imgFile, date, time, dto, fileDto);

		try {
			if(auth == null) {
				return "error";
			} // if
			
			dto.setUserId(auth.getUserId());
			
			Integer sanId = this.mountainService.selectSanName(sanName);
			dto.setSanInfoId(sanId);

			Timestamp dateTime = Timestamp.valueOf(date + " " + time + ":00");
			dto.setPartyDt(dateTime);

			boolean isSuccess = this.partyService.register(dto);
			log.info("success: {}", isSuccess);

			if (imgFile != null && !"".equals(imgFile.getOriginalFilename())) {

				String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

				String basePath = "C:/upload/" + today;
				File Folder = new File(basePath); 

				if (!Folder.exists()) {
					Folder.mkdir(); 
				} // if

				String originalName = imgFile.getOriginalFilename();
				String uuid = UUID.randomUUID().toString();

				String imgPath = basePath + "/" + uuid;
				imgFile.transferTo(new File(imgPath));

				fileDto.setTargetGb("SAN_PARTY");
				fileDto.setTargetCd(dto.getSanPartyId());
				fileDto.setFileName(originalName);
				fileDto.setUuid(uuid);
				fileDto.setPath(imgPath);

				boolean isFileUploadSuccess = this.fileService.register(fileDto);
				log.info("isFileUploadSuccess: {}", isFileUploadSuccess);
			} // if

			return "redirect:/party";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // register
	
	// 참여 신청/취소 토글 
		@PostMapping("/join/{partyId}")
		ResponseEntity<String> offerJoin(@PathVariable Integer partyId, 
										 @SessionAttribute("__AUTH__") UserVO user) throws ControllerException {
			log.trace("offerJoin({}, {}) invoked.", partyId, user);

			try {
				Integer userId = user.getUserId();

				JoinDTO join = new JoinDTO();
				join.setSanPartyId(partyId);
				join.setUserId(userId);
				
				if (this.joinService.isJoinCreatedOrCancled(join)) {

					return new ResponseEntity<>("OK", HttpStatus.OK);
				} // if

				return new ResponseEntity<>("XX", HttpStatus.BAD_REQUEST);

			} catch (Exception e) {
				throw new ControllerException(e);
			} // try-catch
		}// offerJoin

		// 참여 삭제
//		@PostMapping("/cancle")
//		@DeleteMapping("/join/{partyId}")
//		ResponseEntity<String> cancleJoin(@PathVariable Integer partyId, 
//										 @SessionAttribute("__AUTH__") UserVO user) throws ControllerException {
//			log.trace("cancleJoin({}, {}) invoked.", partyId, user);
//
//			try {
//				Integer userId = user.getUserId();
//				Objects.nonNull(userId);
//				
//				JoinDTO join = new JoinDTO();
//				join.setSanPartyId(partyId);
//				join.setUserId(userId);
//				
//				if (this.joinService.isJoinCancled(join)) {
//
//					return new ResponseEntity<>("OK", HttpStatus.OK);
//				} // if
//
//				return new ResponseEntity<>("XX", HttpStatus.BAD_REQUEST);
//
//			} catch (Exception e) {
//				throw new ControllerException(e);
//			} // try-catch
//		}// cancleJoin
} // end class
