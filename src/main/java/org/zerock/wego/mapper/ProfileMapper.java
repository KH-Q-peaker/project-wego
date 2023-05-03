package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.zerock.wego.domain.common.Criteria;
import org.zerock.wego.domain.common.FileDTO;
import org.zerock.wego.domain.common.UserDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.info.SanInfoViewVO;
import org.zerock.wego.domain.party.PartyViewVO;
import org.zerock.wego.domain.profile.MyPartyVO;
import org.zerock.wego.domain.profile.ProfileCommentVO;
import org.zerock.wego.domain.profile.ProfileVO;
import org.zerock.wego.domain.review.ReviewViewVO;


public interface ProfileMapper {
	
//	// 1. 유저 정보 가져오기 
//	public abstract List<UserVO> selectUserId(@Param("userId")Integer userId);
	
	// 1. 유저 정보 가져오기 
	public abstract UserVO selectUserId(@Param("userId")Integer userId);
	
	// 2. 작성 글에 대한 전체목록조회 : 페이징 처리적용
	public abstract List<ProfileVO> selectAll(@Param("userId")Integer userId, @Param("cri")Criteria cri);

	// 3. 교집합에 있는 게시물 건수 반환( SAN_PARTY_V 테이블만 게시물 수를 계산)
	public abstract Integer selectTotalCount(@Param("userId")Integer userId);	

	// 4. 작성 댓글에 대한 전체목록조회 : 페이징 처리적용
	public abstract List<ProfileCommentVO> selectAllComment(@Param("userId")Integer userId,@Param("cri2")Criteria cri2); 

	// 5. 댓글 게시물 건수 반환
	public abstract Integer selectTotalCountComment(@Param("userId")Integer userId); 		
	
	// 6. 프로필사진을 파일테이블에 저장하기
	public abstract Integer insertProfilePictureInFileDTO(FileDTO dto); 		
	
	// 7. 나의 프로필사진을 업데이트 하기.
	public abstract Integer updateProfile(UserDTO dto); 		
	
	// 8. user_tb테이블의 user_pic 패쓰 경로를 통해 프로필 사진을 보여주기.
	public abstract String selectProfilePicturePath(@Param("userId")Integer userId); 		
	
	//9. 닉네임 변경
	abstract Integer updateNick(UserDTO dto);
	
	//10. 신청가능한 모집일정 리스트 보여주기
	public abstract List<MyPartyVO> selectAvailableParty(@Param("userId")Integer userId,@Param("currPage")Integer currPage,@Param("amount")Integer amount);
	
	//11. 지난 등산일정 리스트 보여주기
	public abstract List<MyPartyVO> selectPastParty(@Param("userId")Integer userId,@Param("currPage")Integer currPage,@Param("amount")Integer amount);
	
	//12. 참여가능한 나의모집 페이지 총 카운트
	public abstract Integer selectTotalCountAvailableParty(Integer userId);		
	
	//13. 마감된 나의모집 페이지 총 카운트
	public abstract Integer selectTotalCountPastParty(Integer userId);		
	
	//14. 나의 취향정보 업데이트
	public abstract Integer updateMyInfo(UserDTO dto);
	
	//15. 동일한 닉네임이 존재하는지 count
	public abstract Integer countEqualNickname(String nickname);
	
	//16. 내가 좋아요 누른 산정보 게시판 게시물 리스트 가져오기.
	public abstract Set<SanInfoViewVO> selectLikeSanInfoList(@Param("userId")Integer userId, @Param("targetGb")String targetGb);
	
	//17. 내가 좋아요 누른 산모집 게시판 게시물 리스트 가져오기.
	public abstract Set<PartyViewVO> selectLikeSanPartyList(@Param("userId")Integer userId, @Param("targetGb")String targetGb);
	
	//18. 내가 좋아요 누른 산리뷰 게시판 게시물 리스트 가져오기.
	public abstract Set<ReviewViewVO> selectLikeSanReviewList(@Param("userId")Integer userId, @Param("targetGb")String targetGb);
	
	//15. 회원탈퇴: delete user
	//public abstract Integer deleteMyAccount(Integer user_id);
	
} // end interface
