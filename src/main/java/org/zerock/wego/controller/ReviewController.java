package org.zerock.wego.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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
import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;
import org.zerock.wego.domain.PageInfo;
import org.zerock.wego.domain.ReviewDTO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.service.CommentService;
import org.zerock.wego.service.FavoriteService;
import org.zerock.wego.service.FileService;
import org.zerock.wego.service.ReviewService;
import org.zerock.wego.service.SanInfoService;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/review")
@Controller
public class ReviewController {
	private final SanInfoService sanInfoService;
	private final ReviewService reviewService;
	private final CommentService commentService;
	private final FileService fileService;
	private final FavoriteService favoriteService;
//	private final UserService userService;
//	private final LikeService likeService;

	
	@GetMapping("")
	public String openReview(Model model) throws ControllerException {
		log.trace("openReview({}) invoked.", model);

		try {
			List<ReviewViewVO> reviewList = this.reviewService.getList();

			model.addAttribute("reviewList", reviewList);

			return "review/review";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // openReview
	
	
	@ModelAttribute("target")
	PageInfo createPageInfo(Integer reviewId) {
		log.trace("createPageInfo() invoked.");

		PageInfo target = PageInfo.builder().targetGb("SAN_REVIEW").targetCd(reviewId).build();

		return target;
	}// createdBoardDTO

	@GetMapping("/{reviewId}")
	public ModelAndView showDetailById(@PathVariable("reviewId") Integer reviewId,
			@SessionAttribute("__AUTH__") UserVO user, PageInfo target) throws Exception {
		log.trace("showDetail({}, {}) invoked.", reviewId, target);

		target = this.createPageInfo(reviewId);

		ModelAndView mav = new ModelAndView();

		ReviewViewVO review = this.reviewService.getById(reviewId);

		Integer userId = user.getUserId();

		FavoriteDTO favorite = new FavoriteDTO();
		favorite.setTargetGb("SAN_REVIEW");
		favorite.setTargetCd(reviewId);
		favorite.setUserId(userId);

		boolean isFavorite = this.favoriteService.isFavoriteInfo(favorite);
//			boolean isLike = this.likeService.isUserLike(target, userId);

		int commentCount = this.commentService.getCommentsCount(target);

		LinkedBlockingDeque<CommentViewVO> comments = commentService.getCommentOffsetByTarget(target, 0);

		/* 후기글 사진 넣는거 필요함 */
		mav.addObject("review", review);
		mav.addObject("isFavorite", isFavorite);
		mav.addObject("commentCount", commentCount);
//			mav.addObject("userPic", userPic);

		if (comments != null) {

			mav.addObject("comments", comments);
		} // if

		/* 수정 예정 */
		ObjectMapper objectMapper = new ObjectMapper();
		String targetJson = objectMapper.writeValueAsString(target);
		mav.addObject("target", targetJson);

		mav.setViewName("/review/detail");

		return mav;

	}// viewReviewDetail

	@DeleteMapping("/{reviewId}")
	public String removeById(@PathVariable("reviewId") Integer reviewId, RedirectAttributes rttrs)
			throws ControllerException {
		log.trace("removeById({}) invoked.", reviewId);

		try {

			boolean isSuccess = this.reviewService.isRemoved(reviewId);

			rttrs.addFlashAttribute("reviewId", reviewId);
			rttrs.addAttribute("result", isSuccess ? "success" : "failure");

			return "redirect:/review";

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	}// removeReview

	@GetMapping(path = "/modify/{reviewId}")
	public String modify(@SessionAttribute("__AUTH__") UserVO auth, @PathVariable("reviewId") Integer reviewId,
			Model model) throws Exception {
		log.trace("modify({}, {}, {}) invoked.", auth, reviewId, model);

		ReviewViewVO reviewVO = this.reviewService.getById(reviewId);
		Integer postUserId = reviewVO.getUserId();

		if (auth == null || !auth.getUserId().equals(postUserId)) {
			return "error";
		} // if

		List<FileVO> fileVO = this.fileService.getList("SAN_REVIEW", reviewId);

		model.addAttribute("review", reviewVO);
		model.addAttribute("fileList", fileVO);

		return "/review/modify";
	} // modify

	@PostMapping("/modify")
	public String modify(@SessionAttribute("__AUTH__") UserVO auth, Integer sanReviewId, String sanName,
			List<MultipartFile> imgFiles, 
			String oldImgFiles, 
			ReviewDTO dto, FileDTO fileDto)
			throws ControllerException {
		log.trace("modify({}, {}, {}, {}, {}, {}) invoked.", auth, sanReviewId, sanName, imgFiles, 
				oldImgFiles, 
				dto,
				fileDto);
		log.trace("oldImgFiles: {}", oldImgFiles);

		try {
			if (auth.getUserId() == dto.getUserId()) {
				return "error";
			} // if

			ReviewViewVO vo = this.reviewService.getById(sanReviewId);

			Integer sanId = this.sanInfoService.getIdBySanName(sanName);
			dto.setSanInfoId(sanId);

			boolean isModifiedSuccess = this.reviewService.isModified(dto);
			log.info("isModifiedSuccess: {}", isModifiedSuccess);
			
			// 기존 이미지 중 남은 이미지만 거르기
			if(!oldImgFiles.equals("")) {
				List<FileVO> files = this.fileService.getList("SAN_REVIEW", sanReviewId);
				List<String> oldFiles = Arrays.asList(oldImgFiles.split(","));
				files.forEach(item -> {
					if(!oldFiles.contains(item.getPath())) { // 삭제 대상이라면
						log.info("***** item: {}", item);
						// 파일시스템 상에서 이미지 제거
						File file = new File(item.getPath());
						
						if(file.delete()) { // 파일 삭제 시도
							log.info("파일 시스템에서 이미지 삭제 성공", file);
						} else {
							log.info("파일 시스템에서 이미지 삭제 실패", file);
						} // if-else
						
						// 수정된 후기글에서 제거된 이미지 파일을 제거
						boolean isRemoveSuccess = this.fileService.remove("SAN_REVIEW", sanReviewId, item.getUuid());
						log.trace("isRemoveSuccess: {}", isRemoveSuccess);
					} // if
				});
			} // if

			if (imgFiles != null) { // 신규 추가 이미지가 있다면
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				String createdDate = dateFormat.format(vo.getCreatedDt());
				log.info("getCreatedDt: {}", createdDate);

				String basePath = "C:/upload/" + createdDate;
				File Folder = new File(basePath);

				if (!Folder.exists()) {
					Folder.mkdir();
				} // if

				imgFiles.forEach(imgFile -> {
					if (!"".equals(imgFile.getOriginalFilename())) {
						String originalName = imgFile.getOriginalFilename();
						String uuid = UUID.randomUUID().toString();

						String imgPath = basePath + "/" + uuid;
						try {
							imgFile.transferTo(new File(imgPath));
						} catch (Exception e) {
							e.printStackTrace();
						} // try-catch

						fileDto.setTargetGb("SAN_REVIEW");
						fileDto.setTargetCd(dto.getSanReviewId());
						fileDto.setFileName(originalName);
						fileDto.setUuid(uuid);
						fileDto.setPath(imgPath);

						boolean isFileUploadSuccess = this.fileService.register(fileDto);
						log.info("isFileUploadSuccess: {}", isFileUploadSuccess);
					} // if
				});
			} // if

			if (imgFiles == null && oldImgFiles.equals("")) { // 신규 추가 이미지가 없고, 기존 이미지도 없다면 기존 이미지 삭제
				boolean isRemoveSuccess = this.fileService.removeAll("SAN_REVIEW", sanReviewId);
				log.trace("isRemoveSuccess: {}", isRemoveSuccess);

				return "redirect:/review";
			} // if
			
			return "redirect:/review";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // modify

	@GetMapping("/register")
	public void register() {
		log.trace("register() invoked.");
	} // register

	@PostMapping("/register")
	public String register(@SessionAttribute("__AUTH__") UserVO auth, String sanName, List<MultipartFile> imgFiles,
			ReviewDTO dto, FileDTO fileDto) throws ControllerException {
		log.trace("register({}, {}, {}, {}) invoked.", sanName, imgFiles, dto, fileDto);

		try {
			if (auth == null) {
				return "error";
			} // if

			Integer sanId = this.sanInfoService.getIdBySanName(sanName);
			dto.setSanInfoId(sanId);

			dto.setUserId(auth.getUserId());

			boolean success = this.reviewService.isRegistered(dto);
			log.info("success: {}", success);

			if (imgFiles != null) {
				String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

				String basePath = "C:/upload/" + today;
				File Folder = new File(basePath);

				if (!Folder.exists()) {
					Folder.mkdir();
				} // if

				imgFiles.forEach(imgFile -> {
					if (!"".equals(imgFile.getOriginalFilename())) {
						String originalName = imgFile.getOriginalFilename();
						String uuid = UUID.randomUUID().toString();

						String imgPath = basePath + "/" + uuid;
						try {
							imgFile.transferTo(new File(imgPath));
						} catch (Exception e) {
							e.printStackTrace();
						} // try-catch

						fileDto.setTargetGb("SAN_REVIEW");
						fileDto.setTargetCd(dto.getSanReviewId());
						fileDto.setFileName(originalName);
						fileDto.setUuid(uuid);
						fileDto.setPath(imgPath);

						try {
							boolean isFileUploadSuccess = this.fileService.register(fileDto);
							log.info("isFileUploadSuccess: {}", isFileUploadSuccess);
						} catch (ServiceException e) {
							e.printStackTrace();
						} // try-catch
					} // if
				});
			} // if
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch

		return "redirect:/review";
	} // register
}// end class
