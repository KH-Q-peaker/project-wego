package org.zerock.wego.controller;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.wego.domain.CommentVO;
import org.zerock.wego.domain.PageInfo;
import org.zerock.wego.domain.ReviewViewVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.CommentService;
import org.zerock.wego.service.LikeService;
import org.zerock.wego.service.ReviewService;

import com.google.gson.Gson;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/review")
@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CommentService commentService;

//	@Autowired
//	private UserService userService;
	
//	@Autowired
//	private LikeService likeService;
	
	
	
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
			
			LinkedBlockingDeque<CommentVO> comments = commentService.getCommentsOffsetByTarget(target);

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
}// end class
