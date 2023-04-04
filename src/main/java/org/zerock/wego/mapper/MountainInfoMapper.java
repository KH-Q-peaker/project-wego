package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.MountainInfoViewVO;


public interface MountainInfoMapper {

	@Select("SELECT * FROM san_info_v")
	public abstract List<MountainInfoViewVO> selectAll(); // 전체목록조회
	
	public abstract Set<MountainInfoViewVO> selectRandom10(); // 10개의 게시글 랜덤 조회
	public abstract MountainInfoViewVO select(Integer sanPartyId); // 상세조회
	public abstract Integer selectSanName(String sanName); // 산이름으로 산ID 조회
	
} // end interface
