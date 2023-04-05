package org.zerock.wego.service;

import org.zerock.wego.domain.Target;
import org.zerock.wego.exception.ServiceException;

public interface ReportService {
	
	
	// 신고 접수 
	public abstract boolean createReport(Long userId, Target cri, String reportGb) throws ServiceException;
}// end interface

