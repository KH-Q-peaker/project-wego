package org.zerock.wego.mapper.mypage;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Parameters;
import org.zerock.wego.domain.mypage.ACriteria;
import org.zerock.wego.domain.mypage.TsanPartyDTO;
import org.zerock.wego.domain.mypage.TsanPartyVO;

public interface MyClimbMapper {
	
	
	@Select("""
			SELECT san_Party_Id, san_Info_Id, user_Id, created_Dt, modified_Dt,
					title, contents, TO_CHAR(party_Dt,'yyyy.MM.dd'), party_Max, items, condition
			FROM TSAN_Party
			WHERE TO_CHAR(SYSDATE, 'yyyy/MM/dd HH:MM') <= TO_CHAR(PARTY_DT, 'yyyy/MM/dd HH:MM') AND user_id=#{userId}
			OFFSET ( #{aCurrPage} - 1 ) * #{aAmount} ROWS
			FETCH NEXT #{aAmount} ROWS ONLY
			""")
	public abstract List<TsanPartyVO> availablePartySelect(@Param("userId")Integer userId,@Param("aCurrPage")Integer aCurrPage,@Param("aAmount")Integer aAmount);
	
	
	@Select("""
			SELECT san_Party_Id, san_Info_Id, user_Id, created_Dt, modified_Dt,
					title, contents, TO_CHAR(party_Dt,'yyyy.MM.dd'), party_Max, items, condition
			FROM TSAN_Party
			WHERE TO_CHAR(SYSDATE, 'yyyy/MM/dd HH:MM') > TO_CHAR(PARTY_DT, 'yyyy/MM/dd HH:MM') AND user_id=#{userId}
			OFFSET ( #{pCurrPage} - 1 ) * #{pAmount} ROWS
			FETCH NEXT #{pAmount} ROWS ONLY
			""")
	public abstract List<TsanPartyVO> pastPartySelect(@Param("userId")Integer userId,@Param("pCurrPage")Integer pCurrPage,@Param("pAmount")Integer pAmount);
	
	
	@Insert("""
			INSERT INTO TSAN_Party(    SAN_PARTY_ID,
									   SAN_INFO_ID,
									   USER_ID,
									   CREATED_DT,
									   TITLE,
									   CONTENTS,
									   PARTY_DT,
									   PARTY_MAX)
			VALUES(#{sanPartyId},#{sanInfoId},#{userId},current_date,#{title},#{contents},#{partyDt},#{partyMax})
			""")
	public abstract Integer insert(TsanPartyDTO dto);
	
	@Select("""
			SELECT count(san_party_id)
			FROM TSAN_Party
			WHERE TO_CHAR(SYSDATE, 'yyyy/MM/dd HH:MM') <= TO_CHAR(PARTY_DT, 'yyyy/MM/dd HH:MM') AND user_id=#{userId}
			""")
	public abstract Integer selectTotalCountByMyAvailableParty(Integer userId);		// 참여가능한 나의모집 페이지 총 카운트
	
	@Select("""
			SELECT count(san_party_id)
			FROM TSAN_Party
			WHERE TO_CHAR(SYSDATE, 'yyyy/MM/dd HH:MM') > TO_CHAR(PARTY_DT, 'yyyy/MM/dd HH:MM') AND user_id=#{userId}
			""")
	public abstract Integer selectTotalCountByMyPastParty(Integer userId);		// 마감된 나의모집 페이지 총 카운트

}//end class
