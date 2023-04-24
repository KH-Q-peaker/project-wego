package org.zerock.wego.service.info;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.zerock.wego.domain.info.SanInfodeVO;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.SanInfodeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Component
@Service
public class SanInfodeService {
   
   private final SanInfodeMapper mapper;
   
   public SanInfodeVO select(Integer sanInfoId) throws ServiceException {
      
      log.trace("select() invoked");
      
      
   
      try {
			return this.mapper.select(sanInfoId);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
		
      
      
      
      
      
      
      
      
   }//select
   
   

   
} // end class