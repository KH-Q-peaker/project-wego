package org.zerock.wego.service;

import org.springframework.stereotype.Service;
import org.zerock.wego.domain.ReportDTO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ReportMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Service
public class ReportService {

	
	private final ReportMapper reportMapper;

	// 신고 생성  
	public boolean create(ReportDTO dto) throws ServiceException {
		log.trace("create({}) invoked.", dto);
		
		try {
			return (this.reportMapper.insert(dto) == 1);
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// modifyComment
	
	
	// 타겟 신고 총합 조회 
	public Integer getTotalCount(String targetGb, Integer targetCd) throws ServiceException{
		
		try {
			ReportDTO dto = ReportDTO.builder()
							.targetGb(targetGb)
							.targetCd(targetCd)
							.build();
			
			Integer totalCount = this.reportMapper.selectCountByTarget(dto);
			
			return (totalCount == null ? 0 : totalCount);
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// getTotalCount
}// end class
