package org.zerock.wego.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.Target;

public interface LikeMapper {


	// 타겟의 좋아요 수
	public abstract Integer selectLikeCountByTarget(Target cri);
	
	// 유저의 타겟 좋아요 여부 
	public abstract Integer selectLikedTargetByUserId(@Param("cri")Target cri, @Param("userId")Long userId);
	
	// 좋아요 
	public abstract Integer insertLike(@Param("cri")Target cri, @Param("userId")Long userId);
	
	// 안좋아요 
	public abstract Integer deleteLike(@Param("cri")Target cri, @Param("userId")Long userId);
	
}// end class
