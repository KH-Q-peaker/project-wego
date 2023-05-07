package org.zerock.wego.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WithdrawMapper {
	
	// 회원 탈퇴 1. mypage-profile에서 올린 파일 조회
	public abstract List<String> seleteMyProfileImagePath(Integer userId);

	// 회원 탈퇴 2. 모집게시판에서 내가 올린 파일 조회
	public abstract List<String> seleteMyPartyImagePath(Integer userId);
	
	// 회원 탈퇴 3. 리뷰게시판에서 내가 올린 파일 조회
	public abstract List<String> seleteMyReviewImagePath(Integer userId);
	
	// 회원 탈퇴 4. mypage-profile에서 올린 파일 테이블데이터 삭제
	public abstract Integer deleteMyProfileImageFileList(Integer userId);
	
	// 회원 탈퇴 5. 모집게시판에서 내가 올린 파일 테이블데이터 삭제
	public abstract Integer deleteMyPartyImageFileList(Integer userId);
	
	// 회원 탈퇴 6. 리뷰게시판에서 내가 올린 파일 테이블데이터 삭제
	public abstract Integer deleteMyReviewImageFileList(Integer userId);
	
	// 회원 탈퇴 7. 나와 관련된 데이터 테이블 삭제
	// #{tableName}에 들어갈 목록: ALARM_TB, BADGE_GET_TB, CHAT_TB, COMMENT_TB, JOIN_TB, LIKE_TB,
    // REPORT_TB, SAN_PARTY_TB, SAN_REVIEW_TB, USER_TB
	public abstract Integer deleteAllTableByMe(@Param("tableName")String tableName, @Param("userId")Integer userId);

} // end interface
