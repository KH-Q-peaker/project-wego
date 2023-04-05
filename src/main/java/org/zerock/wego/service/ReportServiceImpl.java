package org.zerock.wego.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.Target;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.ReportMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class ReportServiceImpl implements ReportService {

	
	@Setter(onMethod_= {@Autowired})
	private ReportMapper dao;


	// 신고 접수 
	@Override
	public boolean createReport(Long userId, Target cri, String reportGb) throws ServiceException {
		log.trace("createReport({}, {}, {}) invoked.", userId, cri, reportGb);
		
		try {
			
			return this.dao.insertReportByTargetUserId(cri, userId, reportGb) == 1;
			
		}catch(Exception e) {
			throw new ServiceException(e);
		}// try-catch
	}// modifyComment
	
	
	
}// end class
