package org.zerock.wego.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.domain.CommentViewVO;
import org.zerock.wego.domain.FavoriteDTO;
import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;
import org.zerock.wego.domain.PageInfo;
import org.zerock.wego.domain.ReviewDTO;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.domain.UserVO;
import org.zerock.wego.exception.AccessBlindException;
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



	@GetMapping(path="/{reviewId}")
	public ModelAndView showDetailById(@PathVariable("reviewId")Integer reviewId,
									@SessionAttribute("__AUTH__")UserVO user,
									PageInfo target) throws Exception{
		log.trace("showDetail({}, {}) invoked.", reviewId, target);

			target.setTargetGb("SAN_REVIEW");
			target.setTargetCd(reviewId);
			
			ModelAndView mav = new ModelAndView();
			
			ReviewViewVO review = this.reviewService.getById(reviewId);
			Integer userId = user.getUserId();
			
			// TO_DO : 내글이면 블라인드 되도 보여야되는데 왜 막히냐 ? 
			if((review.getReportCnt() >= 5) && review.getUserId() != userId) {
				throw new AccessBlindException();
			}// if
			
			// TO_DO : 좋아요 바뀌면 바꿔야됨 
			FavoriteDTO favorite = new FavoriteDTO();
			favorite.setTargetGb("SAN_REVIEW");
			favorite.setTargetCd(reviewId);
			favorite.setUserId(userId);
			
			boolean isFavorite = this.favoriteService.isFavoriteInfo(favorite);

			int commentCount = this.commentService.getTotalCountByTarget(target);
			
			LinkedBlockingDeque<CommentViewVO> comments 
							= this.commentService.getCommentOffsetByTarget(target, 0);

			/*후기글 사진 넣는거 필요함 */
			mav.addObject("review", review);
			mav.addObject("isFavorite", isFavorite);
			mav.addObject("commentCount", commentCount);
//			mav.addObject("userPic", userPic);
			
			if(comments != null) {
				
				mav.addObject("comments", comments);
			}// if
			
			ObjectMapper objectMapper = new ObjectMapper();
			String targetJson = objectMapper.writeValueAsString(target);
			mav.addObject("target", targetJson);

			mav.setViewName("/review/detail");
			
			return mav;
	}// viewReviewDetail
	
	@DeleteMapping(path= "/{reviewId}", produces= "text/plain; charset=UTF-8")
	public ResponseEntity<String> removeById(@PathVariable("reviewId")Integer reviewId) throws ControllerException{
		log.trace("removeById({}) invoked.", reviewId);

		try {
			this.reviewService.removeById(reviewId);
//			this.fileService.isRemoveByTarget("SAN_REVIEW", reviewId); 
			return ResponseEntity.ok("🗑 후기글이 삭제되었습니다.️"); // 인코딩해서 넣던가 안넣던가 

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}// try-catch
	}// removeReview
	
	
	@GetMapping(	
			path = "/modify/{reviewId}"
			)
	public String modify(@SessionAttribute("__AUTH__")UserVO auth,
			@PathVariable("reviewId") Integer reviewId, Model model)
					throws Exception  {
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
			ReviewDTO reviewDTO, FileDTO fileDTO) throws ControllerException { 
		log.trace("modify(auth, sanReviewId, sanName, imgFiles, reviewDTO, fileDTO) invoked.");

		try {
			if(auth.getUserId() == reviewDTO.getUserId()) {
				return "error";
			} // if
			
			ReviewViewVO vo = this.reviewService.getById(sanReviewId);

			Integer sanId = this.sanInfoService.getIdBySanName(sanName);
      
			reviewDTO.setSanInfoId(sanId);
			
			this.reviewService.modify(reviewDTO);
			
			if (imgFiles != null) {
				boolean isRemoveSuccess = this.fileService.isRemoveByTarget("SAN_REVIEW", sanReviewId);
				log.trace("isRemoveSuccess: {}", isRemoveSuccess);
				
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

						fileDTO.setTargetGb("SAN_REVIEW");
						fileDTO.setTargetCd(reviewDTO.getSanReviewId());
						fileDTO.setFileName(originalName);
						fileDTO.setUuid(uuid);
						fileDTO.setPath(imgPath);

						try {
							boolean isFileUploadSuccess = this.fileService.isRegister(fileDTO);
							log.info("isFileUploadSuccess: {}", isFileUploadSuccess);
						} catch (ServiceException e) {
							e.printStackTrace();
						} // try-catch
					} // if
				});
			} // if
			
			return "redirect:/review/" + reviewDTO.getSanReviewId();
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
			@SessionAttribute("__AUTH__")UserVO auth, String sanName, List<MultipartFile> imageFiles, 
			ReviewDTO reviewDTO, FileDTO fileDTO,
			@CookieValue(value="posted", required=false)boolean posted,
			HttpServletResponse response) throws ControllerException {
		log.trace("register(auth, sanName, imageFiles, reviewDTO, fileDTO, posted, response) invoked.");

		try {
			if(auth == null) {
				return "error";
			} // if

			if(!posted) { // false
				Cookie cookie = new Cookie("posted", "true");				
	            cookie.setMaxAge(30);
	            response.addCookie(cookie);
			} // if
			
			Integer sanId = this.sanInfoService.getIdBySanName(sanName);

			reviewDTO.setSanInfoId(sanId);

			reviewDTO.setUserId(auth.getUserId());

			this.reviewService.register(reviewDTO);
	
			if (imageFiles != null) {
				String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

				String basePath = "C:/upload/" + today;
				File Folder = new File(basePath);

				if (!Folder.exists()) { 
					Folder.mkdir();
				} // if

				imageFiles.forEach(imgFile -> {
					if (!"".equals(imgFile.getOriginalFilename())) {
						String originalName = imgFile.getOriginalFilename();
						String uuid = UUID.randomUUID().toString();

						String imgPath = basePath + "/" + uuid;
						try {
							imgFile.transferTo(new File(imgPath));
						} catch (Exception e) {
							e.printStackTrace();
						} // try-catch

						fileDTO.setTargetGb("SAN_REVIEW");
						fileDTO.setTargetCd(reviewDTO.getSanReviewId());
						fileDTO.setFileName(originalName);
						fileDTO.setUuid(uuid);
						fileDTO.setPath(imgPath);

						try {
							boolean isFileUploadSuccess = this.fileService.isRegister(fileDTO);
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

		return "redirect:/review/" + reviewDTO.getSanReviewId();
	} // register
}// end class
