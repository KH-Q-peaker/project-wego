package org.zerock.wego.service.common;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

	
	public boolean isImageRegister(List<MultipartFile> imgFiles, String targetGb, Integer targetCd, FileDTO fileDTO)
			throws ServiceException {
		log.trace("isImageRegister() invoked.");

		try {
			// DB에 저장할 이미지 정보 목록
			List<FileDTO> imageFiles = new ArrayList<>();
			
			// 이미지 업로드 날짜 폴더 생성
			String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

			String basePath = "/opt/upload/" + today;
			File Folder = new File(basePath);

			if (!Folder.exists()) {
				Folder.mkdir();
			} // if

			// DB에 저장하기 위한 이미지 정보 처리
			for (int order = 0; order < imgFiles.size(); order++) {
				if (!imgFiles.get(order).getOriginalFilename().equals("")) {
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

					imageFiles.add(dto);
				} // if
			} // for
			
			this.registerImageList(imageFiles);

			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isImageRegister

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
			if(!fileVO.isEmpty()) {
				this.removeNotMatchOldFileByFileVOAndOldFileList(fileVO, oldFiles);
			} // if
			
			// DB에 저장할 이미지 정보 목록
			List<FileDTO> imageFiles = new ArrayList<>();
			
			// 기존 이미지 정보 중 DB에 저장할 이미지 정보 추출
			if (oldFiles != null) {
				oldFiles.forEach(file -> {
					int fileOrder = order.indexOf(file);
					
					if (fileOrder != -1) {
						fileVO.forEach(imgInfo -> {
							if(file.equals(imgInfo.getFileName())) {
								FileDTO dto = FileDTO.builder()
										.targetGb(targetGb)
										.targetCd(targetCd)
										.fileName(imgInfo.getFileName())
										.uuid(imgInfo.getUuid())
										.path(imgInfo.getPath())
										.status(fileOrder + 1)
										.build();
								
								imageFiles.add(dto);
							} // if
						});
					} // if
				});
			} // if
			
			if(newFiles != null ) {
				// 이미지를 저장할 경로 지정(이전 경로 중 하나에서 날짜 추출)
				String basePath = this.getOldBasePath(fileVO.get(0).getPath());
				log.info("***** basePath: {}", basePath);
				
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
						
						// DB에 저장할 이미지 정보
						FileDTO dto = FileDTO.builder()
								.targetGb(targetGb)
								.targetCd(targetCd)
								.fileName(file.getOriginalFilename())
								.uuid(uuid)
								.path(imgPath)
								.status(fileOrder + 1)
								.build();
						
						imageFiles.add(dto);
					} // if
				});
			} // if

			this.registerImageList(imageFiles);
			
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isChangeImage
	
	// 기존 이미지 중 재업로드 된 이미지와 일치하지 않는 이미지를 
	// 파일 시스템에서 제거하는 메소드
	public void removeNotMatchOldFileByFileVOAndOldFileList(
			List<FileVO> fileVO, List<String> oldFiles) throws ServiceException {
		log.trace("removeNotMatchOldFileByFileVOAndOldFileList() invoked.");
		
		try {
			fileVO.forEach(item -> {
				if (!oldFiles.contains(item.getFileName())) {
					File file = new File(item.getPath());

					if (file.exists()) {
						file.delete();
					} // if
				} // if
			});
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // removeNotMatchOldFileByFileVOAndOldFileList
	
	// 이미지를 저장할 경로 지정(경로에서 날짜 추출)
	// ex) /opt/upload/date/uuid
	public String getOldBasePath(String path) {
		return path.substring(0, path.lastIndexOf("/"));
	} // getOldBasePath
	
	@Transactional
	public void registerImageList(List<FileDTO> dto) {
		log.trace("registerImageList(dto) invoked.");
		
		dto.forEach(item -> {
			this.isRegister(item);
		});
	} // registerImageList
	
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