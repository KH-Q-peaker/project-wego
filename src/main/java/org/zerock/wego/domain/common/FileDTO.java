package org.zerock.wego.domain.common;

import lombok.Builder;
import lombok.Data;


@Data
public class FileDTO {
	
	private Integer fileId; 
	private String targetGb; 
	private Integer targetCd; 
	private String fileName; 
	private String uuid; 
	private String path; 
	private Integer status;
	
	@Builder
	public FileDTO(String targetGb, Integer targetCd, String fileName, String uuid, String path, Integer status) {
		super();
		this.targetGb = targetGb;
		this.targetCd = targetCd;
		this.fileName = fileName;
		this.uuid = uuid;
		this.path = path;
		this.status = status;
	} // constructor
	
	public static FileDTO createByFile(FileDTO file) {
		return FileDTO.builder()
				.targetGb(file.getTargetGb())
				.targetCd(file.getTargetCd())
				.fileName(file.getFileName())
				.uuid(file.getUuid())
				.path(file.getPath())
				.status(file.getStatus())
				.build();
	} // createByFile

} // end class