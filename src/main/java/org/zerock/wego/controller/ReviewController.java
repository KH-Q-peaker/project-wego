package org.zerock.wego.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.zerock.wego.domain.ReviewDTO;
import org.zerock.wego.domain.ReviewVO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.service.CommentService;
import org.zerock.wego.service.FileService;
import org.zerock.wego.service.ReviewService;
import org.zerock.wego.service.SanInfoService;

import com.google.gson.Gson;

import lombok.NoArgsConstructor;
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
//	private final UserService userService;
//	private final LikeService likeService;
	
	
	
	@ModelAttribute("target")
	PageInfo createPageInfo() {
		log.trace("createPageInfo() invoked.");
		
		PageInfo target = new PageInfo();

		target.setTargetGb("SAN_REVIEW");
		
		return target;
	}// createdBoardDTO
	
	
	
	@GetMapping("/{reviewId}")
	public ModelAndView showDetailById(@PathVariable("reviewId")Integer reviewId,
									@SessionAttribute("__AUTH__")Integer userId,
									PageInfo target) throws ControllerException{
		log.trace("showDetail({}, {}) invoked.", reviewId, target);
		
		try {
			
			target = this.createPageInfo();
			target.setTargetCd(reviewId);
			
			
			ModelAndView mav = new ModelAndView();

			
			ReviewViewVO review = this.reviewService.getById(reviewId);
			
//			boolean isLike = this.likeService.isUserLike(target, userId);
			int totalCnt = this.commentService.getCommentsCount(target);

//			String userPic = this.userService.getUserPic(review.getUserPic());
			String userPic = review.getUserPic();
			
			if(userPic == null) {
				userPic = "/resources/img/default-profile.png";
			}// if
			
			LinkedBlockingDeque<CommentViewVO> comments = commentService.getCommentsOffsetByTarget(target);

			Objects.requireNonNull(review);

			/*후기글 사진 넣는거 필요함 */
			mav.addObject("review", review);
//			mav.addObject("isLike", isLike);
			mav.addObject("totalCnt", totalCnt);
			mav.addObject("userPic", userPic);
			
			if(comments != null) {
				
				mav.addObject("comments", comments);
			}// if
			
			/* 수정 예정 */
			Gson gson = new Gson();
			String targetJson = gson.toJson(target);
			mav.addObject("target", targetJson);

			mav.setViewName("/review/review-detail");

			
			return mav;

		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	}// viewReviewDetail
	
	
	
	
	@DeleteMapping("/{reviewId}")
	public String removeById(@PathVariable("reviewId")Integer reviewId, 
							RedirectAttributes rttrs) throws ControllerException{
		log.trace("remove({}) invoked.", reviewId);

		try {

			boolean isSuccess = this.reviewService.isRemoved(reviewId);

			rttrs.addFlashAttribute("reviewId", reviewId);
			rttrs.addAttribute("result", isSuccess ? "success" : "failure");

			return "redirect:/review";

		} catch (Exception e) {
			throw new ControllerException(e);
		}// try-catch
	}// removeReview
	
	@GetMapping(	
			path = "/modify/{reviewId}"
			)
	public String modify(@SessionAttribute("__AUTH__")UserVO auth,
			@PathVariable("reviewId") Integer reviewId, Model model)
					throws ControllerException  {
		log.trace("modify({}, {}, {}) invoked.", auth, reviewId, model);
		
		ReviewViewVO reviewVO = this.reviewService.getById(reviewId);
		Integer postUserId = reviewVO.getUserId();
		
		if(auth == null || !auth.getUserId().equals(postUserId)) {
			return "error";
		} // if
		
		List<FileVO> fileVO = this.fileService.getList("SAN_REVIEW", reviewId);
		
		model.addAttribute("review", reviewVO);
		model.addAttribute("fileList", fileVO);
		
		return "/review/modify";		
	} // modify
	
	@PostMapping("/modify")
	public String modify(
			@SessionAttribute("__AUTH__")UserVO auth, 
			Integer sanReviewId, String sanName, List<MultipartFile> imgFiles, 
			ReviewDTO dto, FileDTO fileDto) throws ControllerException { 
		log.trace("modify({}, {}, {}, {}, {}, {}) invoked.", auth, sanReviewId, sanName, imgFiles, dto, fileDto);

		try {
			if(auth.getUserId() == dto.getUserId()) {
				return "error";
			} // if
			
			ReviewViewVO vo = this.reviewService.getById(sanReviewId);
			
			Integer sanId = this.sanInfoService.selectSanName(sanName);
			dto.setSanInfoId(sanId);
			
			boolean isSuccess = this.reviewService.isModified(dto);
			log.info("isSuccess: {}", isSuccess);
			
			if (imgFiles != null) {
				boolean isRemoveSuccess = this.fileService.remove("SAN_REVIEW", sanReviewId);
				log.trace("isRemoveSuccess: {}", isRemoveSuccess);
				
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				String createdDate = dateFormat.format(vo.getCreateDt());
				log.info("getCreateDt: {}", createdDate);

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

						try {
							boolean isFileUploadSuccess = this.fileService.register(fileDto);
							log.info("isFileUploadSuccess: {}", isFileUploadSuccess);
						} catch (ServiceException e) {
							e.printStackTrace();
						} // try-catch
					} // if
				});
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
	public String register(
			@SessionAttribute("__AUTH__")UserVO auth, String sanName, List<MultipartFile> imgFiles, 
			ReviewDTO dto, FileDTO fileDto) throws ControllerException {
		log.trace("register({}, {}, {}, {}) invoked.", sanName, imgFiles, dto, fileDto);

		try {
			if(auth == null) {
				return "error";
			} // if
			
			Integer sanId = this.sanInfoService.selectSanName(sanName);
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
