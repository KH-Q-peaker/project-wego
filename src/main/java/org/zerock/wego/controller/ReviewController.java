package org.zerock.wego.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.common.BoardSearchDTO;
import org.zerock.wego.domain.common.CommentViewVO;
import org.zerock.wego.domain.common.FavoriteDTO;
import org.zerock.wego.domain.common.FavoriteVO;
import org.zerock.wego.domain.common.FileDTO;
import org.zerock.wego.domain.common.FileVO;
import org.zerock.wego.domain.common.PageInfo;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.review.ReviewDTO;
import org.zerock.wego.domain.review.ReviewViewSortVO;
import org.zerock.wego.domain.review.ReviewViewVO;
import org.zerock.wego.exception.AccessBlindException;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.service.badge.BadgeGetService;
import org.zerock.wego.service.common.CommentService;
import org.zerock.wego.service.common.FavoriteService;
import org.zerock.wego.service.common.FileService;
import org.zerock.wego.service.info.SanInfoService;
import org.zerock.wego.service.review.ReviewService;
import org.zerock.wego.verification.ReviewValidator;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	private final ReviewValidator reviewValidator;
	private final BadgeGetService badgeGetService;

	@GetMapping("")
	public String showReview(@SessionAttribute(value = SessionConfig.AUTH_KEY_NAME, required = false) UserVO auth, BoardDTO dto,
			Model model) throws ControllerException {
		log.trace("showReview(auth, dto, model) invoked.");

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			List<ReviewViewSortVO> reviewSortList = this.reviewService.getSortNewestList(dto);
			model.addAttribute("reviewSortList", reviewSortList);

			double pageCount = Math.ceil(this.sanInfoService.getTotalCount() / dto.getAmount());
			int maxPage = (int)pageCount;
			model.addAttribute("maxPage", maxPage);
			
			return "review/review";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // showReview

	@PostMapping("")
	public String addReview(@SessionAttribute(value = SessionConfig.AUTH_KEY_NAME, required = false) UserVO auth, BoardDTO dto,
			Model model) throws ControllerException {
		log.trace("addReview(auth, dto, model) invoked.", model);

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			if (dto.getOrderBy().equals("like")) {
				List<ReviewViewSortVO> reviewSortList = this.reviewService.getSortLikeList(dto);
				model.addAttribute("reviewSortList", reviewSortList);
			} else if (dto.getOrderBy().equals("oldest")) {
				List<ReviewViewSortVO> reviewSortList = this.reviewService.getSortOldestList(dto);
				model.addAttribute("reviewSortList", reviewSortList);
			} else {
				List<ReviewViewSortVO> reviewSortList = this.reviewService.getSortNewestList(dto);
				model.addAttribute("reviewSortList", reviewSortList);
			} // else-if
			
			return "review/reviewItem";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // addReview

	@GetMapping("/search")
	public String showReviewSearchResult(@SessionAttribute(value = SessionConfig.AUTH_KEY_NAME, required = false) UserVO auth,
			BoardSearchDTO dto, Model model) throws ControllerException {
		log.trace("showReviewSearchResult(auth, dto, model) invoked.");

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			String query = dto.getQuery();
			List<ReviewViewSortVO> reviewSortList = this.reviewService.getSearchSortNewestList(dto);
			if (reviewSortList == null || reviewSortList.isEmpty() || query == null || query.isEmpty() || query.trim().isEmpty()) {
				List<ReviewViewSortVO> reviewSuggestion = this.reviewService.getReviewSuggestion();
				model.addAttribute("reviewSuggestion", reviewSuggestion);
				
				return "review/reviewSearchFail";
			} else {
				model.addAttribute("reviewSortList", reviewSortList);
				
				double pageCount = Math.ceil(this.sanInfoService.getTotalCountByQuery(query) / dto.getAmount());
				int maxPage = (int)pageCount;
				model.addAttribute("maxPage", maxPage);
				
				return "review/reviewSearch";
			} // if-else
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // showReviewSearchResult

	@PostMapping("/search")
	public String addReviewSearchResult(@SessionAttribute(value = SessionConfig.AUTH_KEY_NAME, required = false) UserVO auth,
			BoardSearchDTO dto, Model model) throws ControllerException {
		log.trace("addReviewSearchResult(auth, dto, model) invoked.");

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			List<ReviewViewSortVO> reviewSortList = this.reviewService.getSearchSortNewestList(dto);
			model.addAttribute("reviewSortList", reviewSortList);

			return "review/reviewItem";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // addReviewSearchResult

	@GetMapping(path = "/{reviewId}")
	public String showDetailById(@PathVariable("reviewId") Integer reviewId,
			@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO user, PageInfo pageInfo, Model model,
			FavoriteDTO favorite) throws RuntimeException, JsonProcessingException {
		log.trace("showDetail(reviewId, user, pageInfo, model, favorite) invoked.");

		pageInfo.setTargetGb("SAN_REVIEW");
		pageInfo.setTargetCd(reviewId);

		ReviewViewVO review = this.reviewService.getById(reviewId);
		Integer userId = user.getUserId();

		if ((review.getReportCnt() >= 5) && (!userId.equals(review.getUserId()))) {
			throw new AccessBlindException();
		} // if

		List<FileVO> fileList = this.fileService.getList("SAN_REVIEW", reviewId);

		favorite.setTargetGb("SAN_REVIEW");
		favorite.setTargetCd(reviewId);
		favorite.setUserId(userId);

		boolean isFavorite = this.favoriteService.isFavorite(favorite);

		LinkedBlockingDeque<CommentViewVO> comments = this.commentService.getCommentOffsetByTarget(pageInfo, 0);

		model.addAttribute("review", review);
		model.addAttribute("isFavorite", isFavorite);
		model.addAttribute("fileList", fileList);
		model.addAttribute("comments", comments);

		ObjectMapper objectMapper = new ObjectMapper();
		String pageInfoJson = objectMapper.writeValueAsString(pageInfo);

		model.addAttribute("target", pageInfoJson);

		return "/review/detail";
	}// showDetailById

	@DeleteMapping(path = "/{reviewId}", produces = "text/plain; charset=UTF-8")
	public ResponseEntity<String> removeById(@PathVariable("reviewId") Integer reviewId) throws ControllerException {
		log.trace("removeById(reviewId) invoked.");

		try {
			this.reviewService.removeById(reviewId);

			return ResponseEntity.ok("후기글이 삭제되었습니다.️");

		} catch (NotFoundPageException e) {
			return ResponseEntity.notFound().build();

		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().build();
		} // try-catch
	}// removeReview

	@GetMapping(path = "/modify/{reviewId}")
	public String modify(
			@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO auth,
			@PathVariable("reviewId") Integer reviewId, Model model) throws Exception {
		log.trace("modify(auth, reviewId, model) invoked.");

		try {
			ReviewViewVO reviewVO = this.reviewService.getById(reviewId);
			Integer postUserId = reviewVO.getUserId();

			if (!auth.getUserId().equals(postUserId)) {
				throw new ControllerException("잘못된 접근입니다.");
			} // if

			List<FileVO> fileVO = this.fileService.getList("SAN_REVIEW", reviewId);

			model.addAttribute("review", reviewVO);
			model.addAttribute("fileList", fileVO);

			return "/review/modify";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // modify

	@PostMapping("/modify")
	public ResponseEntity<Map<String, String>> modify(
			@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO auth,
			Integer sanReviewId, String sanName,
			@RequestParam(value = "imgFiles", required = false) List<MultipartFile> newImageFiles,
			@RequestParam(value = "oldImgFiles", required = false) String oldImageFiles,
			@RequestParam(value = "imgOrder", required = false) String imageOrder, ReviewDTO reviewDTO,
			BindingResult bindingResult, FileDTO fileDTO) throws ControllerException {
		log.trace(
				"modify(auth, sanReviewId, sanName, newImageFiles, oldImageFiles, imageOrder, reviewDTO, bindingResult, fileDTO) invoked.");

		try {
			Integer sanId = this.sanInfoService.getIdBySanName(sanName);

			reviewDTO.setSanInfoId(sanId);

			reviewValidator.validate(reviewDTO, bindingResult);

			Map<String, String> state = new HashMap<>();

			if (bindingResult.hasFieldErrors()) {
				log.info("***** FieldErrors *****: {}", bindingResult.getAllErrors());

				state.put("state", "failed");
				state.put("errorField", bindingResult.getFieldError().getField());

				return new ResponseEntity<>(state, HttpStatus.BAD_REQUEST);
			} // if

			this.reviewService.modify(reviewDTO);

			List<String> oldFiles = Arrays.asList(oldImageFiles.split(","));
			List<String> order = Arrays.asList(imageOrder.split(","));

			this.fileService.isChangeImage(newImageFiles, oldFiles, order, "SAN_REVIEW", sanReviewId, fileDTO);

			state.put("state", "successed");
			state.put("redirectUrl", "/review/" + reviewDTO.getSanReviewId());

			return new ResponseEntity<>(state, HttpStatus.OK);
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // modify

	@GetMapping("/register")
	public String register(@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO auth) {
		log.trace("register(auth) invoked.");

		return "/review/register";
	} // register

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(
			@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO auth, String sanName,
			@RequestParam(value = "imgFiles", required = false) List<MultipartFile> imageFiles, ReviewDTO reviewDTO,
			BindingResult bindingResult, FileDTO fileDTO) throws ControllerException {
		log.trace("register(auth, sanName, imageFiles, reviewDTO, bindingResult, fileDTO) invoked.");

		try {
			Map<String, String> state = new HashMap<>();

			Integer sanId = this.sanInfoService.getIdBySanName(sanName);

			reviewDTO.setSanInfoId(sanId);
			reviewDTO.setUserId(auth.getUserId());

			reviewValidator.validate(reviewDTO, bindingResult);

			if (bindingResult.hasFieldErrors()) {
				log.info("***** FieldErrors *****: {}", bindingResult.getAllErrors());
				state.put("state", "failed");
				state.put("errorField", bindingResult.getFieldError().getField());

				return new ResponseEntity<>(state, HttpStatus.BAD_REQUEST);
			} // if

			this.reviewService.register(reviewDTO);

			if (imageFiles != null) {
				boolean isImageUploadSuccess = this.fileService.isImageRegister(imageFiles, "SAN_REVIEW",
						reviewDTO.getSanReviewId(), fileDTO);
				log.info("isImageUploadSuccess: {}", isImageUploadSuccess);
			} // if

			badgeGetService.register(sanId, auth.getUserId());

			state.put("state", "successed");
			state.put("redirectUrl", "/review/" + reviewDTO.getSanReviewId());

			return new ResponseEntity<>(state, HttpStatus.OK);
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // register
}// end class