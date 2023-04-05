package org.zerock.wego.controller;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.wego.domain.FileDTO;
import org.zerock.wego.domain.FileVO;
import org.zerock.wego.domain.RecruitmentDTO;
import org.zerock.wego.domain.RecruitmentViewVO;
import org.zerock.wego.exception.ControllerException;
import org.zerock.wego.service.FileService;
import org.zerock.wego.service.MountainInfoService;
import org.zerock.wego.service.RecruitmentService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@RequestMapping("/recruit") // BASE URL
@Controller
public class RecruitController {
	private RecruitmentService service;
	private MountainInfoService mountainService;
	private FileService fileService;

	@GetMapping(
			path = "/modify/{sanPartyId}"
			)
	public String modify(
			@PathVariable("sanPartyId") Integer sanPartyId, Model model) 
			throws ControllerException { // 모집글 수정 요청처리
		log.trace("detail({}, {}) invoked.", sanPartyId, model);

		try {
			RecruitmentViewVO vo = this.service.get(sanPartyId);
			model.addAttribute("recruitment", vo);
			
			return "/recruit/modify";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // detailAndModify

//	@GetMapping("/remove")
//	public String remove(Integer sanPartyId, RedirectAttributes rttrs) 
//			throws ControllerException { // 모집글 삭제 요청처리
//		log.trace("remove({}, {}) invoked.", sanPartyId, rttrs);
//
//		try {
//			boolean success = this.service.remove(sanPartyId);
//			boolean fileRemoveSuccess = fileService.remove("SAN_PARTY", sanPartyId);
//			log.info("fileRemoveSuccess: {}", fileRemoveSuccess);
//
//			rttrs.addAttribute("result", success ? "success" : "failure");
//
//			return "redirect:/recruit";
//
//		} catch (Exception e) {
//			throw new ControllerException(e);
//		} // try-catch
//	} // remove

	@PostMapping("/modify")
	public String modify(Integer sanPartyId, // 모집글 번호
			String sanName, // 산이름
			MultipartFile imgFile, // 이미지
			String date, // 등반일
			String time, // 등반시간
			RecruitmentDTO dto) 
					throws ControllerException { // 모집글 수정 요청처리
		log.trace("modify({}, {}) invoked.", dto);

		try {
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

			boolean success = this.service.modify(dto);
			log.info("modify- success: {}", success);

			return "redirect:/recruit/detail/" + dto.getSanPartyId();
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
	} // modify

	@GetMapping("/write")
	public void write(RedirectAttributes rttrs) { // 모집글 작성 요청처리
		log.trace("write() invoked.");
	} // write

	@PostMapping("/register")
	public String recruitmentUpload(String sanName, // 산이름
			MultipartFile imgFile, // 이미지
			String date, // 등반일
			String time, // 등반시간
			RecruitmentDTO dto, FileDTO fileDto, RedirectAttributes rttrs) throws ControllerException {
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
			boolean success = this.service.register(dto);
			log.info("success: {}", success);

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

			return "redirect:/recruit";
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch

	} // recruitmentUpload

} // end class
