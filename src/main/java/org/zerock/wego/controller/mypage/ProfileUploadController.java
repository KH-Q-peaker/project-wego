package org.zerock.wego.controller.mypage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.wego.domain.mypage.FileDTO;
import org.zerock.wego.domain.mypage.WegoUserTbDTO;
import org.zerock.wego.service.mypage.ProfileUploadService;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/file")
@Controller
public class ProfileUploadController {
	
		@Inject
		ProfileUploadService service;
		
		@PostMapping("/upload")
		String fileSave(@RequestParam(value="part", required=false) MultipartFile part, @RequestParam("user_id") String user_id, HttpServletRequest req) {
			
			log.trace("fileSave({}) invoked.",part);
			log.info("part.getName():{}",part.getName());
			
			//폴더 생성하기
			Date date = new Date();
			Timestamp ts=new Timestamp(date.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String str = sdf.format(date);
			str = str.replace("-", File.separator);
			
			String ori_filename=part.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
	        String fileName = uuid.toString() + "_" + ori_filename;
	        File uploadPath = new File("C:\\temp\\upload\\profileImage", str);
	        File saveFile = new File(uploadPath, fileName);
	        log.info("~~~~~~ saveFile.getPath: {}",saveFile.getPath());
	        Path directoryPath = Paths.get("C:\\temp\\upload\\profileImage\\" + str);

	    	if (!uploadPath.exists()) {
	    		try{
	    			Files.createDirectories(directoryPath);
	    	        } 
	    	     catch(Exception e){
	    		 e.getStackTrace();
	    	     }    
	    	}
	        try {
	          part.transferTo(saveFile);
	        } catch (Exception e) {
	          log.error(e.getMessage());
	        } // end catch

			
			FileDTO dto = new FileDTO();
			dto.setFilename(fileName);
			dto.setOri_filename(ori_filename);
			dto.setPath("C:\\temp\\upload\\profileImage\\" + str + "\\" + fileName);
			dto.setReg_date(ts);
			service.fileSave(dto);
			log.info("**********user_id:{}",user_id);
			
			WegoUserTbDTO dto2 = new WegoUserTbDTO();
			dto2.setUser_id(user_id);
			dto2.setFilename(fileName);
			service.updateProfile(dto2);
		
			
			return "redirect: http://localhost:8080/mypage/mypage" + "?user_id="+user_id;
		}

}//end class
