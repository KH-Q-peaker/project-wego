package org.zerock.wego.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.zerock.wego.domain.Criteria;
import org.zerock.wego.domain.ProfileCommentVO;
import org.zerock.wego.domain.ProfileVO;
import org.zerock.wego.domain.UserVO;


public interface ProfileMapper {
	
	// 유저 정보 가져오기 
	public abstract List<UserVO> selectUserId(Integer userId);
	
	// 1. 작성 글에 대한 전체목록조회 : 페이징 처리적용
	@Select("""
			SELECT * 
			FROM (
				SELECT
					'등산후기' "boardName",
					SAN_REVIEW_ID "srtId",
					USER_ID "user",
					TITLE,
					CREATED_DT,
					LIKE_CNT "likeCnt"
				FROM 
					SAN_REVIEW_V srt 
				WHERE 
					USER_ID = #{userId}
					UNION 
				SELECT
					'등산모집' "boardName",
					san_party_id "srtId",
					USER_ID "user",
					TITLE,
					CREATED_DT,
					LIKE_CNT "likeCnt"
				FROM 
					SAN_PARTY_V  srt 
				WHERE 
					USER_ID = #{userId} )
			ORDER BY CREATED_DT DESC
			OFFSET (#{cri.currPage} - 1 ) * #{cri.amount} ROWS
			FETCH NEXT #{cri.amount} ROWS ONLY""")
	public abstract List<ProfileVO> selectAll(Integer userId,Criteria cri);

	// 2. 교집합에 있는 게시물 건수 반환( SAN_PARTY_V 테이블만 게시물 수를 계산)
	@Select("""
	            SELECT count(*)
				FROM (
				SELECT SAN_REVIEW_ID AS id
				FROM SAN_REVIEW_V
				WHERE USER_ID = #{userId}
				UNION
				SELECT SAN_PARTY_ID AS id
				FROM SAN_PARTY_V
				WHERE USER_ID = #{userId}
				) post""")
	public abstract Integer selectTotalCount(Integer userId);	


	// 3. 작성 댓글에 대한 전체목록조회 : 페이징 처리적용
	@Select("""
			SELECT * FROM (
			    SELECT
			        '등산후기' "targetGb",
					TARGET_CD "targetCb",
			        COMMENT_ID "commentId",
			        USER_ID "userId",
			        CONTENTS "contents",
			        "CREATED_DT" "CREATED_DT",
			        "MENTION_CNT" "MENTION_CNT"
			    FROM 
			        COMMENT_V ctv
			    WHERE 
			        USER_ID = #{userId} AND 
			        TARGET_GB = 'SAN_REVIEW'
			    UNION 
			    SELECT
					'등산모집' "targetGb",
					TARGET_CD "targetCb",
			        COMMENT_ID "commentId",
			        USER_ID "userId",
			        CONTENTS "contents",
			        "CREATED_DT" "CREATED_DT",
			        "MENTION_CNT" "MENTION_CNT"
			    FROM 
			        COMMENT_V ctv 
			    WHERE 
			        USER_ID = #{userId} AND 
			        TARGET_GB = 'SAN_PARTY'
			)
			ORDER BY "CREATED_DT" DESC
			OFFSET ( #{cri2.currPage} - 1 ) * #{cri2.amount} ROWS
			FETCH NEXT #{cri2.amount} ROWS ONLY
			""")
	public abstract List<ProfileCommentVO> selectAllComment(Integer userId,Criteria cri2); 

	// 4. 댓글 게시물 건수 반환
	@Select("""
			SELECT count(COMMENT_ID)
			FROM COMMENT_V
			WHERE USER_ID = #{userId} AND COMMENT_ID > 0
			""") //인덱스 타려고 WHERE절에 넣어줌.
	public abstract Integer selectTotalCountComment(Integer userId); 		
} // end interface
