package org.zerock.wego.service.common;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
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

					fileDTO.setTargetGb(targetGb);
					fileDTO.setTargetCd(targetCd);
					fileDTO.setFileName(originalName);
					fileDTO.setUuid(uuid);
					fileDTO.setPath(imgPath);

					boolean isFileUploadSuccess = this.isRegister(fileDTO);
					log.info("isFileUploadSuccess: {}", isFileUploadSuccess);
				} // if
			});

			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // isImageRegister

	public boolean isChangeImage(List<MultipartFile> newFiles, String targetGb, Integer targetCd, FileDTO fileDTO) 
			throws ServiceException {
		try {
			List<FileVO> fileVo = this.getList(targetGb, targetCd);
			log.info("*****isChangeImage - fileVo: {}", fileVo);
			
			if(fileVo.isEmpty()) {
				return this.isImageRegister(newFiles, targetGb, targetCd, fileDTO);
			} // if
			Integer oldFileId = fileVo.get(0).getFileId();

			// TODO: forEach로 변경 필요
			newFiles.get(0).transferTo(new File(fileVo.get(0).getPath()));

			return this.isModify(targetGb, targetCd, oldFileId, newFiles.get(0).getOriginalFilename());
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
