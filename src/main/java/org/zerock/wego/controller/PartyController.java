package org.zerock.wego.controller;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

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
import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;
import org.zerock.wego.domain.PageInfo;
import org.zerock.wego.domain.PartyDTO;
import org.zerock.wego.domain.PartyViewVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.CommentService;
import org.zerock.wego.service.FileService;
import org.zerock.wego.service.JoinService;
import org.zerock.wego.service.PartyService;
import org.zerock.wego.service.SanInfoService;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/party") // BASE URL
@Controller
public class PartyController {
	
	private final PartyService partyService;
	private final CommentService commentService;
	private final JoinService joinService;
	private final SanInfoService mountainService;
	private final FileService fileService;

	
	
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
										@SessionAttribute("__AUTH__")Integer userId,
										PageInfo target) throws ControllerException{
		log.trace("showDetailById({}, {}) invoked.", partyId, target);
		
		try {
			target = this.createPageInfo();
			target.setTargetCd(partyId);
			
			
			ModelAndView mav = new ModelAndView();
			

			PartyViewVO party = this.partyService.getById(partyId);
			Objects.requireNonNull(party);
			
			boolean isJoin = this.joinService.isUserJoined(partyId, userId);
//			boolean isLike = this.likeService.isUserLiked(target, userId);
			
			int totalCnt = this.commentService.getCommentsCount(target);
			

			LinkedBlockingDeque<CommentViewVO> comments = commentService.getCommentsOffsetByTarget(target);

			
			mav.addObject("party", party);
			mav.addObject("isJoin", isJoin);
//			mav.addObject("isLike", isLike);
			mav.addObject("totalCnt", totalCnt);
//			mav.addObject("userPic", userPic);
//			mav.addObject("partyImg", partyImg);
			
			if(comments != null) {
				
				mav.addObject("comments", comments);
			}// if
			
			// 수정 예정 ***************************
			Gson gson = new Gson();
			String targetJson = gson.toJson(target);
			mav.addObject("target", targetJson);

			mav.setViewName("/party/party-detail");

			return mav;

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	}// viewReviewDetail
	
	
	@GetMapping(
			path = "/modify/{sanPartyId}"
			)
	public String modify(
			@PathVariable("partyId") Integer partyId, Model model) 
			throws ControllerException { // 모집글 수정 요청처리
		log.trace("detail({}, {}) invoked.", partyId, model);
		
		// TODO: 세션에 있는 __AUTH__ 객체를 얻어서 AUTH에 있는 아이디와 게시글 작성자의 ID의 일치여부 판단 필요
		//       일치하지 않으면, Error 접근권한없음(페이지 보여줘야됨)

		try {
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
										@SessionAttribute("__AUTH__") Integer userId,
										RedirectAttributes rttrs) throws ControllerException{
		log.trace("removePartyByPartyId({}, {}) invoked.", partyId, userId);
		
		try {
			boolean isSuccess = this.partyService.isRemovedById(partyId, userId);

			rttrs.addFlashAttribute("partyId", partyId); // 얘는 어따쓰지? 
			rttrs.addAttribute("result", isSuccess ? "success" : "failure");

			
			return "redirect:/party";// 목록이동으로 하기 **** 아마 신영님꺼로 이동 

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	}// removeReview
	

	@PostMapping("/modify")
	public String modify(Integer sanPartyId, // 모집글 번호
			String sanName, // 산이름
			MultipartFile imgFile, // 이미지
			String date, // 등반일
			String time, // 등반시간
			PartyDTO dto) 
					throws ControllerException { // 모집글 수정 요청처리
		log.trace("modify({}, {}) invoked.", dto);

		try {
			// TODO: 세션에 있는 __AUTH__ 객체를 얻어서 AUTH에 있는 아이디와 게시글 작성자의 ID의 일치여부 판단 필요
			//       일치하지 않으면, Error 접근권한없음(페이지 보여줘야됨)
			
			// 산이름으로 산ID 조회
			Integer sanId = this.mountainService.selectSanName(sanName);
			dto.setSanInfoId(sanId);

			// 등반일 + 등반시간(TIMESTAMP 형식에 맞게)
			String dateTime = date + " " + time + ":00";
			dto.setPartyDt(dateTime);

			// 업로드 이미지 파일이 존재하는 경우
			if (imgFile != null && !"".equals(imgFile.getOriginalFilename())) {

				// FILE_TB 테이블의 기존 이미지 파일에 덮어쓰기
				List<FileVO> fileVo = this.fileService.getList("SAN_PARTY", sanPartyId); // 기존 이미지 파일 정보 불러오기
				Integer oldFileId = fileVo.get(0).getFileId(); // 기존 파일ID

				imgFile.transferTo(new File(fileVo.get(0).getPath())); // 기존 경로에 덮어쓰기

				this.fileService.modify("SAN_PARTY", sanPartyId, oldFileId, imgFile.getOriginalFilename());
				log.trace("이미지 파일 수정 성공!!");
			} // if

			boolean success = this.partyService.modify(dto);
			log.info("modify- success: {}", success);

			return "redirect:/party/detail/" + dto.getSanPartyId();
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // modify

	@GetMapping("/register")
	public void register() { // 모집글 작성 요청처리
		log.trace("register() invoked.");
	} // write

	@PostMapping("/register")
	public String register(String sanName, // 산이름
			MultipartFile imgFile, // 이미지
			String date, // 등반일
			String time, // 등반시간
			PartyDTO dto, FileDTO fileDto, RedirectAttributes rttrs) throws ControllerException {
		log.trace("register({}, {}) invoked.", dto, rttrs);

		try {
			// 산이름으로 산ID 조회
			Integer sanId = this.mountainService.selectSanName(sanName);
			dto.setSanInfoId(sanId);

			// TODO: 테스트용 유저 ID로 소셜 로그인 구현 후 제거
			Integer userId = 9;
			dto.setUserId(userId);

			// 등반일 + 등반시간(TIMESTAMP 형식에 맞게)
			String dateTime = date + " " + time + ":00";
			dto.setPartyDt(dateTime);

			// 첨부파일을 제외한 데이터 저장
			boolean isSuccess = this.partyService.register(dto);
			log.info("success: {}", isSuccess);

			// 첨부 이미지가 있다면 저장
			if (imgFile != null && !"".equals(imgFile.getOriginalFilename())) {

				// 첨부파일 저장을 위한 디렉터리 생성
				String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

				// 첨부파일 Base경로 지정
				String basePath = "C:/upload/" + today;
				File Folder = new File(basePath); // C:/upload/20230327

				if (!Folder.exists()) { // 해당 디렉터리가 없을 경우 생성 => C:/upload까진 존재해야 함
					Folder.mkdir(); // 폴더 생성
					log.trace("----------폴더생성---------");
				} // if

				// DB에 저장할 원본파일명 및 UUID
				String originalName = imgFile.getOriginalFilename();
				String uuid = UUID.randomUUID().toString();

				// 이미지 경로 설정 => C:/upload/20230330/UUID
				String imgPath = basePath + "/" + uuid;
				imgFile.transferTo(new File(imgPath));

				log.trace("이미지 파일 로컬 저장 성공!!");

				// 첨부파일 테이블에 저장
				fileDto.setTargetGb("SAN_PARTY");
				// 모집글 테이블에 최근 저장한 게시물의 번호 가져오기
				fileDto.setTargetCd(dto.getSanPartyId());
				fileDto.setFileName(originalName);
				fileDto.setUuid(uuid);
				fileDto.setPath(imgPath);

				boolean fileUploadSuccess = this.fileService.register(fileDto);
				log.info("fileUploadSuccess: {}", fileUploadSuccess);
			} // if

			rttrs.addFlashAttribute("sanPartyId", dto.getSanPartyId());

			return "redirect:/party";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch

	} // recruitmentUpload

} // end class
