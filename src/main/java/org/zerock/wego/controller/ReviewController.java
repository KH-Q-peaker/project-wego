package org.zerock.wego.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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



	@ModelAttribute("target")
	PageInfo createPageInfo(Integer reviewId) {
		log.trace("createPageInfo() invoked.");
		
		PageInfo target = new PageInfo();

		target.setTargetGb("SAN_REVIEW");
		target.setTargetCd(reviewId);
		
		return target;
	}// createdBoardDTO
	
	
  
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



	@GetMapping(path= "/{reviewId}")
	public ModelAndView showDetailById(@PathVariable("reviewId")Integer reviewId,
									@SessionAttribute("__AUTH__")UserVO user,
									PageInfo target) throws Exception{
//		log.trace("showDetail({}, {}) invoked.", reviewId, target);
		
			ModelAndView mav = new ModelAndView();

			target = this.createPageInfo(reviewId);
			
			ReviewViewVO review = this.reviewService.getById(reviewId);
			
			Integer userId = user.getUserId();
			
			FavoriteDTO favorite = new FavoriteDTO();
			favorite.setTargetGb("SAN_REVIEW");
			favorite.setTargetCd(reviewId);
			favorite.setUserId(userId);
			
			boolean isFavorite = this.favoriteService.isFavoriteInfo(favorite);
//			boolean isLike = this.likeService.isUserLike(target, userId);

			int commentCount = this.commentService.getCommentsCount(target);

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
	
	
	
	@DeleteMapping(path= "/{reviewId}",
				  produces= "text/plain; charset=UTF-8")
	public ResponseEntity<String> removeById(@PathVariable("reviewId")Integer reviewId) throws ControllerException{
		log.trace("removeById({}) invoked.", reviewId);

		boolean isReviewRemove = this.reviewService.remove(reviewId);
		boolean isFileRemove = this.fileService.isRemoveByTarget("SAN_REVIEW", reviewId);
		
		boolean isSuccess = isReviewRemove && isFileRemove;
		
		if (isSuccess) {
			return ResponseEntity.ok("🗑 후기글이 삭제되었습니다.️");

		} else {
			return ResponseEntity.badRequest().build();
		}// if-else
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
			ReviewDTO dto, FileDTO fileDto) throws ControllerException { 
		log.trace("modify({}, {}, {}, {}, {}, {}) invoked.", auth, sanReviewId, sanName, imgFiles, dto, fileDto);

		try {
			if(auth.getUserId() == dto.getUserId()) {
				return "error";
			} // if
			
			ReviewViewVO vo = this.reviewService.getById(sanReviewId);

			Integer sanId = this.sanInfoService.getIdBySanName(sanName);
      
			dto.setSanInfoId(sanId);
			
			boolean isSuccess = this.reviewService.modify(dto);
			log.info("isSuccess: {}", isSuccess);
			
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

						fileDto.setTargetGb("SAN_REVIEW");
						fileDto.setTargetCd(dto.getSanReviewId());
						fileDto.setFileName(originalName);
						fileDto.setUuid(uuid);
						fileDto.setPath(imgPath);

						try {
							boolean isFileUploadSuccess = this.fileService.isRegister(fileDto);
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
			ReviewDTO dto, FileDTO fileDto,
			  @CookieValue(value="posted", required=false)boolean posted,
			HttpServletResponse response) throws ControllerException {
		log.trace("register({}, {}, {}, {}) invoked.", sanName, imgFiles, dto, posted, fileDto, response);

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

			dto.setSanInfoId(sanId);

			dto.setUserId(auth.getUserId());

			boolean success = this.reviewService.register(dto);
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
							boolean isFileUploadSuccess = this.fileService.isRegister(fileDto);
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