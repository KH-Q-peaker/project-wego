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
import org.zerock.wego.domain.chat.ChatRoomDTO;
import org.zerock.wego.domain.common.BoardDTO;
import org.zerock.wego.domain.common.BoardSearchDTO;
import org.zerock.wego.domain.common.CommentViewVO;
import org.zerock.wego.domain.common.FavoriteDTO;
import org.zerock.wego.domain.common.FavoriteVO;
import org.zerock.wego.domain.common.FileDTO;
import org.zerock.wego.domain.common.PageInfo;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.party.JoinDTO;
import org.zerock.wego.domain.party.PartyDTO;
import org.zerock.wego.domain.party.PartyViewSortVO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.exception.AccessBlindException;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.service.common.ChatService;
import org.zerock.wego.service.common.CommentService;
import org.zerock.wego.service.common.FavoriteService;
import org.zerock.wego.service.common.FileService;
import org.zerock.wego.service.info.SanInfoService;
import org.zerock.wego.service.party.JoinService;
import org.zerock.wego.service.party.PartyService;
import org.zerock.wego.verification.PartyValidator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private final SanInfoService sanInfoService;
	private final FileService fileService;
	private final FavoriteService favoriteService;
	private final PartyValidator partyValidator;
	private final ChatService chatService;

	@GetMapping("")
	public String showParty(@SessionAttribute(value = SessionConfig.AUTH_KEY_NAME, required = false) UserVO auth, BoardDTO dto,
			Model model) throws ControllerException {
		log.trace("showParty(auth, dto, model) invoked.", model);

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			List<PartyViewSortVO> partySortList = this.partyService.getSortNewestList(dto);
			model.addAttribute("partySortList", partySortList);

			double pageCount = Math.ceil(this.sanInfoService.getTotalCount() / dto.getAmount());
			int maxPage = (int)pageCount;
			model.addAttribute("maxPage", maxPage);
			
			return "party/party";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // showParty

	@PostMapping("")
	public String addParty(@SessionAttribute(value = SessionConfig.AUTH_KEY_NAME, required = false) UserVO auth, BoardDTO dto,
			Model model) throws ControllerException {
		log.trace("addParty(auth, dto, model) invoked.", model);

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			if (dto.getOrderBy().equals("like")) {
				List<PartyViewSortVO> partySortList = this.partyService.getSortLikeList(dto);
				model.addAttribute("partySortList", partySortList);
			} else if (dto.getOrderBy().equals("oldest")) {
				List<PartyViewSortVO> partySortList = this.partyService.getSortOldestList(dto);
				model.addAttribute("partySortList", partySortList);
			} else {
				List<PartyViewSortVO> partySortList = this.partyService.getSortNewestList(dto);
				model.addAttribute("partySortList", partySortList);
			} // else-if

			return "party/partyItem";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // addParty

	@GetMapping("/search")
	public String showPartySearchResult(@SessionAttribute(value = SessionConfig.AUTH_KEY_NAME, required = false) UserVO auth,
			BoardSearchDTO dto, Model model
			) throws ControllerException {
		log.trace("showPartySearchResult(auth, dto, model) invoked.");

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			String query = dto.getQuery();
			List<PartyViewSortVO> partySortList = this.partyService.getSearchSortNewestList(dto);
			if (partySortList == null || partySortList.isEmpty() || query == null || query.isEmpty() || query.trim().isEmpty()) {
				List<PartyViewSortVO> partySuggestion = this.partyService.getPartySuggestion();
				model.addAttribute("partySuggestion", partySuggestion);
				
				return "party/partySearchFail";
			} else {
				model.addAttribute("partySortList", partySortList);
				
				double pageCount = Math.ceil(this.sanInfoService.getTotalCountByQuery(query) / dto.getAmount());
				int maxPage = (int)pageCount;
				model.addAttribute("maxPage", maxPage);
				
				return "party/partySearch";
			} // if-else
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // showPartySearchResult

	@PostMapping("/search")
	public String addPartySearchResult(@SessionAttribute(value = SessionConfig.AUTH_KEY_NAME, required = false) UserVO auth,
			BoardSearchDTO dto, Model model) throws ControllerException {
		log.trace("addPartySearchResult(auth, dto, model) invoked.");

		try {
			if (auth != null) {
				Set<FavoriteVO> favoriteList = this.favoriteService.getUserFavoriteOnList(auth.getUserId());
				model.addAttribute("favoriteList", favoriteList);
			} // if

			List<PartyViewSortVO> partySortList = this.partyService.getSearchSortNewestList(dto);
			model.addAttribute("partySortList", partySortList);
			
			return "party/partyItem";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // addPartySearchResult

	// 모집글 상세 조회
	@GetMapping("/{partyId}")
	public String showDetailById(@PathVariable("partyId") Integer partyId,
			@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO user, PageInfo pageInfo, Model model, JoinDTO join,
			FavoriteDTO favorite) throws RuntimeException, JsonProcessingException {
		log.trace("showDetailById(partyId, user, pageInfo, model, join, favorite) invoked.");

		pageInfo.setTargetGb("SAN_PARTY");
		pageInfo.setTargetCd(partyId);

		PartyViewVO party = this.partyService.getById(partyId);
		Integer userId = user.getUserId();

		if ((party.getReportCnt() >= 5) && (!userId.equals(party.getUserId()))) {
			throw new AccessBlindException();
		} // if

		join.setSanPartyId(partyId);
		join.setUserId(userId);

		boolean isJoin = this.joinService.isJoin(join);

		favorite.setTargetGb("SAN_PARTY");
		favorite.setTargetCd(partyId);
		favorite.setUserId(userId);

		boolean isFavorite = this.favoriteService.isFavorite(favorite);

		LinkedBlockingDeque<CommentViewVO> comments = commentService.getCommentOffsetByTarget(pageInfo, 0);

		model.addAttribute("party", party);
		model.addAttribute("isJoin", isJoin);
		model.addAttribute("isFavorite", isFavorite);
		model.addAttribute("comments", comments);

		ObjectMapper objectMapper = new ObjectMapper();
		String pageInfoJson = objectMapper.writeValueAsString(pageInfo);

		model.addAttribute("target", pageInfoJson);

		return "/party/detail";
	}// showDetailById

	// 모집글 삭제
	@DeleteMapping(path = "/{partyId}", produces = "text/plain; charset=UTF-8")
	public ResponseEntity<String> removeById(@PathVariable("partyId") Integer partyId) throws RuntimeException {
		log.trace("removeById(partyId) invoked.");

		try {
			this.partyService.removeById(partyId);

			return ResponseEntity.ok("모집글이 삭제되었습니다.️");

		} catch (NotFoundPageException e) {
			return ResponseEntity.notFound().build();

		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().build();// try-catch
		} // try-catch
	}// removeById

	@GetMapping(path = "/modify/{partyId}")
	public String modify(@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO auth,
			@PathVariable("partyId") Integer partyId, Model model) throws ControllerException {
		log.trace("modify(auth, partyId, model) invoked.");

		try {
			Integer postUserId = this.partyService.getUserIdByPartyId(partyId);

			if (!auth.getUserId().equals(postUserId)) {
				throw new ControllerException("잘못된 접근입니다.");
			} // if

			PartyViewVO vo = this.partyService.getById(partyId);
			model.addAttribute("party", vo);

			return "/party/modify";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // modify

	@PostMapping("/modify")
	public ResponseEntity<Map<String, String>> modify(
			@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO auth,
			Integer sanPartyId, String sanName,
			@RequestParam(value = "imgFile", required = false) List<MultipartFile> imageFiles, PartyDTO partyDTO,
			BindingResult bindingResult, FileDTO fileDTO) throws ControllerException {
		log.trace("modify(auth, sanPartyId, sanName, imageFiles, partyDTO, bindingResult, fileDTO) invoked.");

		try {
			Integer sanId = this.sanInfoService.getIdBySanName(sanName);
			partyDTO.setSanInfoId(sanId);

			partyValidator.validate(partyDTO, bindingResult);

			Map<String, String> state = new HashMap<>();

			if (bindingResult.hasFieldErrors()) {
				log.info("***** FieldErrors *****: {}", bindingResult.getAllErrors());

				state.put("state", "failed");
				state.put("errorField", bindingResult.getFieldError().getField());

				return new ResponseEntity<>(state, HttpStatus.BAD_REQUEST);
			} // if

			boolean isModifySuccess = this.partyService.modify(partyDTO);
			log.info("isModifySuccess: {}", isModifySuccess);

			if (imageFiles != null) {
				List<String> oldImageFiles = Arrays
						.asList(this.fileService.getList("SAN_PARTY", partyDTO.getSanPartyId()).get(0).getFileName());
				List<String> order = Arrays.asList(imageFiles.get(0).getOriginalFilename());
				boolean isChangeImgeSuccess = this.fileService.isChangeImage(imageFiles, oldImageFiles, order,
						"SAN_PARTY", partyDTO.getSanPartyId(), fileDTO);
				log.info("isChangeImgeSuccess: {}", isChangeImgeSuccess);
			} // if

			state.put("state", "successed");
			state.put("redirectUrl", "/party/" + partyDTO.getSanPartyId());

			return new ResponseEntity<>(state, HttpStatus.OK);
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // modify

	@GetMapping("/register")
	public String register(@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO auth) {
		log.trace("register(auth) invoked.");

		return "/party/register";
	} // register

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(
			@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO auth,
			String sanName, @RequestParam(value = "imgFile", required = false) List<MultipartFile> imageFiles,
			PartyDTO partyDTO, BindingResult bindingResult, FileDTO fileDTO, JoinDTO joinDTO, ChatRoomDTO roomDTO)
			throws ControllerException {
		log.trace("register(auth, sanName, imageFiles, partyDTO, bindingResult, fileDTO, joinDTO, roomDTO) invoked.");

		try {
			partyDTO.setUserId(auth.getUserId());

			Integer sanId = this.sanInfoService.getIdBySanName(sanName);
			partyDTO.setSanInfoId(sanId);

			partyValidator.validate(partyDTO, bindingResult);

			Map<String, String> state = new HashMap<>();

			if (bindingResult.hasFieldErrors()) {
				log.info("***** FieldErrors *****: {}", bindingResult.getAllErrors());

				state.put("state", "failed");
				state.put("errorField", bindingResult.getFieldError().getField());

				return new ResponseEntity<>(state, HttpStatus.BAD_REQUEST);
			} // if

			boolean isSuccess = this.partyService.register(partyDTO);
			log.info("isSuccess: {}", isSuccess);

			if (imageFiles != null) {
				boolean isImageUploadSuccess = this.fileService.isImageRegister(imageFiles, "SAN_PARTY",
						partyDTO.getSanPartyId(), fileDTO);
				log.info("isImageUploadSuccess: {}", isImageUploadSuccess);
			} // if

			joinDTO.setSanPartyId(partyDTO.getSanPartyId());
			joinDTO.setUserId(auth.getUserId());
			this.joinService.create(joinDTO);

			roomDTO.setChatRoomId(partyDTO.getSanPartyId());
			roomDTO.setTitle(partyDTO.getTitle());
			roomDTO.setUserId(auth.getUserId());
			this.chatService.createChatRoom(roomDTO);

			state.put("state", "successed");
			state.put("redirectUrl", "/party/" + partyDTO.getSanPartyId());

			return new ResponseEntity<>(state, HttpStatus.OK);
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // register

	// 참여 신청/취소 토글
	@PostMapping(path = "/{partyId}/join", produces = "text/plain; charset=UTF-8")
	ResponseEntity<String> toggleJoinOrCancleById(@PathVariable Integer partyId,
			@SessionAttribute(SessionConfig.AUTH_KEY_NAME) UserVO user, JoinDTO join) throws Exception {
		log.trace("toggleJoinOrCancleById(partyId, user, join) invoked.");

		try {
			Integer userId = user.getUserId();

			join.setSanPartyId(partyId);
			join.setUserId(userId);

			this.joinService.createOrCancle(join);
			Integer currentCount = this.joinService.getCurrentCount(join);

			return ResponseEntity.ok(currentCount.toString());

		} catch (OperationFailException e) {
			return ResponseEntity.badRequest().body("모집 인원이 가득 찼습니다.");

		} catch (NotFoundPageException e) {
			return ResponseEntity.badRequest().body("해당 모집글을 찾을 수 없습니다.");
		} // try-catch
	}// joinOrCancleByPartyId
} // end class
