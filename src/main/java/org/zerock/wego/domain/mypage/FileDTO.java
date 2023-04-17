package org.zerock.wego.domain.mypage;



import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FileDTO {
	
	private String path;
	private String filename;
	private String ori_filename;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Timestamp reg_date;
}
