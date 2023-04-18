package org.zerock.wego.service.profile;

import java.util.List;
import java.util.Objects;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.Criteria;
import org.zerock.wego.domain.common.FileDTO;
import org.zerock.wego.domain.common.UserDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.domain.party.PartyVO;
import org.zerock.wego.domain.profile.ProfileCommentVO;
import org.zerock.wego.domain.profile.ProfileVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ProfileMapper;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


@ToString
@Log4j2
@RequiredArgsConstructor

@Service("profileService")
public class ProfileService {	// POJO

	private final ProfileMapper mapper;
		
	// 1. 클릭한 유저 정보가져오기 
	public UserVO getUserById(Integer userId) throws ServiceException {
		log.debug("getUserById({}) invoked.", userId);
		try {
	        Objects.requireNonNull(this.mapper);

			return this.mapper.selectUserId(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} //getUserById
	
	// 2. 게시물 전체목록 조회(LIST)
	public List<ProfileVO> getListByUserId(Integer userId, Criteria cri) throws ServiceException {
		log.debug("getListByUserId({}, {}) invoked.", userId, cri);
		try {
	        // Step 1. Get the current page number and set it to 1 if it is null or less than 1
	        int currPage = cri.getCurrPage() == null || cri.getCurrPage() < 1 ? 1 : cri.getCurrPage();
	        cri.setCurrPage(currPage);
	        
	        Objects.requireNonNull(this.mapper);

			return this.mapper.selectAll(userId, cri);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} //getListByUserId

	// 3. 현재 총 게시물 건수 반환 
	public Integer getTotalAmountByUserId(Integer userId) throws ServiceException {
		log.debug("getTotalAmountByUserId({}) invoked.", userId);
		try {
			Objects.requireNonNull(this.mapper);
			return this.mapper.selectTotalCount(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} //getTotalAmountByUserId

	// 4. 댓글 전체 목록조회 (LIST)
	public List<ProfileCommentVO> getListComment(Integer userId,Criteria cri2) throws ServiceException {
		log.trace("getListComment({},{}) invoked.", userId,cri2);
		
		try {
	        // Step 1. Get the current page number and set it to 1 if it is null or less than 1
	        int currPage = cri2.getCurrPage() == null || cri2.getCurrPage() < 1 ? 1 : cri2.getCurrPage();
	        cri2.setCurrPage(currPage);
	        Objects.requireNonNull(this.mapper);

			return this.mapper.selectAllComment(userId,cri2);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList

	// 5. 현재 총 댓글 게시물의 건수 반환 
	public Integer getTotalAmountComment(Integer userId) throws ServiceException {
		log.trace("getTotalAmountComment() invoked.");
		
		try {
	        Objects.requireNonNull(this.mapper);

			return this.mapper.selectTotalCountComment(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getTotalAmountComment
	
	
	// 6. 프로필사진을 파일테이블에 저장하기
	public Integer saveUserPictureInFileTbByFileDTO(FileDTO dto) throws ServiceException {
		log.trace("saveUserPicByFileDTO({}) invoked", dto);
		try {
	        Objects.requireNonNull(this.mapper);
	        return mapper.insertProfilePictureInFileDTO(dto);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}//saveUserPicByFileDTO 		
		
	// 7. 나의 프로필사진을 업데이트 하기.
	public Integer updateUserPicByUserDTO(UserDTO dto) throws ServiceException {
		log.trace("updateUserPicByUserDTO({}) invoked",dto);
		try {
	        Objects.requireNonNull(this.mapper);
	        return mapper.updateProfile(dto);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}//updateUserPicByUserDTO		
	
	// 8. user_tb테이블의 user_pic 패쓰 경로를 통해 프로필 사진을 보여주기.
	public String showUserPicbyUserId(@Param("userId")Integer userId) throws ServiceException {
		log.trace("showUserPicbyUserId({}) invoked", userId);
		try {
	        Objects.requireNonNull(this.mapper);
	        return this.mapper.selectProfilePicturePath(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}//showUserPicbyUserId	
	
	//9. 닉네임 변경하기
	public Integer modifyNickByUserDTO(UserDTO dto) throws ServiceException {
		log.trace("modifyNickByUserDTO({}) invoked",dto);
		try {
			Objects.requireNonNull(this.mapper);
			return mapper.updateNick(dto);
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}//modify
	
	//10. 내가 신청한 등산모집 리스트 보기(신청가능한)
	public List<PartyVO> showAvailablePartyByUserIdAndAcri(Integer userId, Criteria cri) throws ServiceException {
		log.trace("showAvailablePartyByUserIdAndAcri({}) invoked",userId);
		try {
			Objects.requireNonNull(this.mapper);
			return this.mapper.selectAvailableParty(userId,cri.getCurrPage(),cri.getAmount());
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}//availableParty
	
	//11. 내가 신청한 등산모집 리스트 보기(마감된)
	public List<PartyVO> showPastPartyByUserIdAndPcri(Integer userId,Criteria cri) throws ServiceException {
		log.trace("showPastPartyByUserIdAndPcri({}) invoked",userId);
		try {
			Objects.requireNonNull(this.mapper);
			return this.mapper.selectPastParty(userId,cri.getCurrPage(),cri.getAmount());
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}
	
	//12. 내가 신청한 등산모집 리스트 총 카운트하기(신청가능한)
	public Integer getTotalAmountAvailablePartyByUserId(Integer userId) throws ServiceException {
		log.trace("getTotalAmountAvailablePartyByUserId({}) invoked.",userId);
		try {
			Objects.requireNonNull(this.mapper);
			return this.mapper.selectTotalCountAvailableParty(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}
	
	//13. 내가 신청한 등산모집 리스트 총 카운트하기(마감된)
	public Integer getTotalAmountPastPartyByUserId(Integer userId) throws ServiceException {
		log.trace("getTotalAmountPastPartyByUserId({}) invoked.",userId);
		try {
			Objects.requireNonNull(this.mapper);
			return this.mapper.selectTotalCountPastParty(userId);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}//pastParty
	
	// 14. 나의 취향정보 저장하기
	public Integer setMyInfoByUserDTO(UserDTO dto) {
		log.trace("setMyInfoByUserDTO({}) invoked.",dto);
		try {
			Objects.requireNonNull(this.mapper);
			return this.mapper.updateMyInfo(dto);
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	}//setMyInfoByUserDTO

	
//	public Integer withDrawMyAccount(Integer userId) {
//		log.trace("withDrawMyAccount({}) invoked.",userId);
//		try {
//			Objects.requireNonNull(this.mapper);
//		return this.mapper.deleteMyAccount(userId) ;
//		} catch(Exception e) {
//			throw new ServiceException(e);
//		} // try-catch
//	}//withDrawMyAccount


} // end class
