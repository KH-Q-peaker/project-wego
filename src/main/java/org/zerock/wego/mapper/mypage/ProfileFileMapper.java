package org.zerock.wego.mapper.mypage;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.zerock.wego.domain.mypage.FileDTO;
import org.zerock.wego.domain.mypage.WegoUserTbDTO;


public interface ProfileFileMapper {
	
	
	@Insert("""
			INSERT INTO tfile (path,filename,ori_filename,reg_date) VALUES(#{path},#{filename},#{ori_filename},#{reg_date})
			""")
	public abstract Integer fileInsert(FileDTO dto);

	@Update("""
		update tuser_tb set filename = #{filename}
		where user_id = #{user_id}
			""")
	public abstract Integer updateProfile(WegoUserTbDTO dto);

	
	@Select("""
		SELECT path
		FROM tfile
		WHERE filename = (
			SELECT filename
			FROM tuser_tb
			WHERE user_id = #{user_id}
			)
			""")
	public abstract String selectProfile(String user_id);
	
}// FileMapper
