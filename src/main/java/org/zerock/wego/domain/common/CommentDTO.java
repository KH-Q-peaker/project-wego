package org.zerock.wego.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

	private Integer commentId;			// 댓글 식별자
	private String targetGb;			// 소속 게시판   *
	private Integer targetCd;			// 소속 글 식별자 *
	private String commentGb;			// 댓글 구분  
	private Integer mentionId;			// 멘션 코드  
	private Integer userId;				// 유저 코드 *
	private String contents;			// 댓글 내용 * 
	private String status;				// 삭제 상태값 
	

	public static CommentDTO convertCommentViewVOToCommentDTO(CommentViewVO vo) {
		
		return CommentDTO.builder().commentId(vo.getCommentId())
									  .targetGb(vo.getTargetGb())
									  .targetCd(vo.getTargetCd())
									  .commentGb(vo.getCommentGb())
									  .mentionId(vo.getMentionId())
									  .userId(vo.getUserId())
									  .contents(vo.getContents())
									  .status(vo.getStatus())
									  .build(); 
		
	}// convertCommentViewVOToCommentDTO 
}// end class
