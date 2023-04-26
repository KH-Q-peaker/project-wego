package org.zerock.wego.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.info.SanInfoViewVO;
import org.zerock.wego.domain.info.SanInfodeVO;



public interface SanInfodeMapper {
   
   //public abstract SanInfodeVO select(String SanName);
   //산의 정보를 산 아이디로 가지고 옴
   public abstract List<SanInfodeVO> selectById(@Param("sanInfoId")Integer sanInfoId);
	//public abstract SanInfodeVO selectById(Integer sanInfoId); 

   //public abstract WeatherVO selectAll
   
   
   

}