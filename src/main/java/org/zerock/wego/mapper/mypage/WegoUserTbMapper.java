package org.zerock.wego.mapper.mypage;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.zerock.wego.domain.mypage.WegoUserTbDTO;
import org.zerock.wego.domain.mypage.WegoUserTbVO;

public interface WegoUserTbMapper {
	
	@Select("""
			SELECT * FROM tuser_tb
			""")
	abstract List<WegoUserTbVO> selectAll();
	abstract WegoUserTbVO select(String user_id);
	abstract Integer delete(String user_id);
	abstract Integer insert(WegoUserTbDTO dto);		// 4. 신규회원등록
	abstract Integer update(WegoUserTbDTO dto);
	
	@Update("""
			UPDATE tuser_tb
			SET nickname= #{nickname}
			WHERE user_id = #{user_id}
			""")
	abstract Integer updateNick(WegoUserTbDTO dto);

}//end class
