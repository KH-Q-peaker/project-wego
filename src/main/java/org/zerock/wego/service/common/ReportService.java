package org.zerock.wego.service.common;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.common.ReportDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.exception.DuplicateKeyException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ReportMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Service
public class ReportService {

	
	private final ReportMapper reportMapper;
	private final UserService userService;
	

	// 타겟 신고 총합 조회 
	public int getTotalCount(ReportDTO dto) throws ServiceException{
		
		int totalCount 
			= this.reportMapper.selectCountByTarget(dto.getTargetGb(), dto.getTargetCd());
		
		return totalCount;
	}// getTotalCount
	
	
	// 신고 생성  
	public void create(ReportDTO dto) throws RuntimeException {
//		log.trace("create({}) invoked.", dto);
		
		if(this.reportMapper.isExist(dto)) {
			throw new DuplicateKeyException();
		}// if
		
		this.reportMapper.insert(dto);
		
		if(dto.getTargetGb().equals("USER")) {
			this.checkAndModifyUserStatus(dto);
		}// if
	}// modifyComment
	
	// 신고 삭제 
	public void removeAllByTarget(String targetGb, Integer targetCd) throws RuntimeException{
//		log.trace("removeAllByTarget({}, {})", targetGb, targetCd);
		
		this.reportMapper.deleteAllByTarget(targetGb, targetCd);
		
		ReportDTO report = ReportDTO.builder()
									.targetGb(targetGb)
									.targetCd(targetCd)
									.build();
		
		boolean isExist = this.reportMapper.isExist(report);
		
		if(isExist) {
			throw new OperationFailException();
		}// if
	}// isRemovedByTarget
	
	// 유저 활동권한 확인 
	public void checkAndModifyUserStatus(ReportDTO dto) throws RuntimeException{
		
		int reportCnt = this.reportMapper.selectCountByTarget("USER", dto.getTargetCd());
		
		if(reportCnt >= 5) {
			
			this.userService.modifyStatus(dto.getTargetCd(), "N");
		}// if
		
	}// checkUserStatus
}// end class
