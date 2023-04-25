package org.zerock.wego.service.common;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.wego.domain.common.FileDTO;
import org.zerock.wego.domain.common.FileVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Service
public class FileService {

	private final FileMapper fileMapper;

	@Transactional
	public boolean isImageRegister(List<MultipartFile> imgFiles, String targetGb, Integer targetCd, FileDTO fileDTO)
			throws ServiceException {
		log.trace("isImageRegister() invoked.");

		try {
			String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

			String basePath = "C:/upload/" + today;
			File Folder = new File(basePath);

			if (!Folder.exists()) {
				Folder.mkdir();
			} // if

			for (int order = 0; order < imgFiles.size(); order++) {
				if (!"".equals(imgFiles.get(order).getOriginalFilename())) {
					String originalName = imgFiles.get(order).getOriginalFilename();
					String uuid = UUID.randomUUID().toString();

					String imgPath = basePath + "/" + uuid;

					imgFiles.get(order).transferTo(new File(imgPath));
					
					FileDTO dto = FileDTO.builder()
							.targetGb(targetGb)
							.targetCd(targetCd)
							.fileName(originalName)
							.uuid(uuid)
							.path(imgPath)
							.status(order + 1)
							.build();

					boolean isFileUploadSuccess = this.isRegister(dto);
					log.info("isFileUploadSuccess: {}", isFileUploadSuccess);
				} // if
			} // for

			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isImageRegister

	@Transactional
	public boolean isChangeImage(List<MultipartFile> newFiles, List<String> oldFiles, List<String> order,
			String targetGb, Integer targetCd, FileDTO fileDTO) throws ServiceException {
		log.trace("isChangeImage() invoked.");
		try {
			// 기존 이미지 정보 불러오기
			List<FileVO> fileVO = this.getList(targetGb, targetCd);

			// 기존 이미지 정보가 없으면 새로 등록으로 이동
			if (fileVO.isEmpty() && newFiles != null) {
				return this.isImageRegister(newFiles, targetGb, targetCd, fileDTO);
			} // if

			// 기존 이미지 정보를 DB에서 삭제
			boolean removeSuccess = this.isRemoveByTarget(targetGb, targetCd);
			log.info("removeSuccess: {}", removeSuccess);

			// 기존 이미지 중 oldFiles에 없는 이미지를 파일시스템에서 제거
			// 메소드로 빼면 파라미터는 List<FileVO> fileVO
			if(!fileVO.isEmpty()) {
				fileVO.forEach(item -> {
					if (!oldFiles.contains(item.getFileName())) {
						File file = new File(item.getPath());

						if (file.exists()) {
							file.delete();
						} // if
					} // if
				});
			} // if
			
			// 이미지 순서대로 DB에 이미지 정보 저장
			if (oldFiles != null) {
				// 1. 기존 이미지
				oldFiles.forEach(file -> {
					int fileOrder = order.indexOf(file);
					if (fileOrder != -1) {
						// VO에서 일치하는 파일명의 정보를 가져온다.
						fileVO.forEach(imgInfo -> {
							if(file.equals(imgInfo.getFileName())) {
								// 기존 파일 시스템에 존재하므로 DB에만 저장
								FileDTO dto = FileDTO.builder()
										.targetGb(targetGb)
										.targetCd(targetCd)
										.fileName(imgInfo.getFileName())
										.uuid(imgInfo.getUuid())
										.path(imgInfo.getPath())
										.status(fileOrder + 1)
										.build();
								
								this.fileMapper.insert(dto);
							} // if
						});
					} // if
				});
			} // if
			
			if(newFiles != null ) {
				// 이미지를 저장할 경로 지정(이전 경로 중 하나에서 날짜 추출)
				String prevPath = fileVO.get(0).getPath();
				int start = fileVO.get(0).getPath().lastIndexOf("/") - 8;
				String date = prevPath.substring(start, start + 8);
				String basePath = "C:/upload/" + date;
				
				// 2. 신규 이미지
				newFiles.forEach(file -> {
					int fileOrder = order.indexOf(file.getOriginalFilename());
					if (fileOrder != -1) {
						String uuid = UUID.randomUUID().toString();
						String imgPath = basePath + "/" + uuid;
						
						// 파일 시스템에 저장
						try {
							file.transferTo(new File(imgPath));
						} catch (Exception e) {
							e.printStackTrace();
						} // try-catch
						
						// DB에 저장
						FileDTO dto = FileDTO.builder()
								.targetGb(targetGb)
								.targetCd(targetCd)
								.fileName(file.getOriginalFilename())
								.uuid(uuid)
								.path(imgPath)
								.status(fileOrder + 1)
								.build();
						
						this.fileMapper.insert(dto);
					} // if
				});
			} // if

			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isChangeImage
	

	public List<FileVO> getList(String targetGb, Integer targetCd) throws ServiceException {
		log.trace("getList() invoked.");

		try {
			return this.fileMapper.selectByTargetGbAndTargetCd(targetGb, targetCd);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	public boolean isRegister(FileDTO dto) throws ServiceException {
		log.trace("isRegistered({}) invoked.");

		try {
			return this.fileMapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isRegistered

	public boolean isModify(String targetGb, Integer targetCd, Integer fileId, String fileName)
			throws ServiceException {
		log.trace("isModified({}) invoked.");

		try {
			return this.fileMapper.update(targetGb, targetCd, fileId, fileName) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isModified

	public boolean isRemoveByTarget(String targetGb, Integer targetCd) throws ServiceException {
		log.trace("isRemoveByTarget({}, {}) invoked.", targetGb, targetCd);

		try {

			int totalCount = this.fileMapper.selectTotalCountByTarget(targetGb, targetCd);

			int deleteCount = this.fileMapper.deleteByTarget(targetGb, targetCd);

			return totalCount == deleteCount;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isRemoveByTarget

	public boolean isRemove(String targetGb, Integer targetCd, String uuid) throws ServiceException {
		log.trace("isRemoved({}, {}) invoked.", targetGb, targetCd, uuid);

		try {
			return this.fileMapper.deleteByTargetGbAndTargetCdAndUuid(targetGb, targetCd, uuid) == 1;

		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isRemoved

} // end class