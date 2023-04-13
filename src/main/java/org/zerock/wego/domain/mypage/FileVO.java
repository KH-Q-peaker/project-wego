package org.zerock.wego.domain.mypage;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Value;

@Value

public class FileVO {
	
	private String path;
	private String filename;
	private String ori_filename;
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date reg_date;
}//end class
