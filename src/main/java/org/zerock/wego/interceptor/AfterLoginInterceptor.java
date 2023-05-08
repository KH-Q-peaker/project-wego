package org.zerock.wego.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.common.UserVO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Component("afterLoginInterceptor")
public class AfterLoginInterceptor 
implements HandlerInterceptor{

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
		
		if(modelAndView != null) {
			UserVO authUserVO = (UserVO) modelAndView.getModelMap().getAttribute(SessionConfig.AUTH_KEY_NAME);
			
			if(authUserVO != null) {

				modelAndView.getModelMap().remove(SessionConfig.AUTH_KEY_NAME);

				req.getSession().setAttribute(SessionConfig.AUTH_KEY_NAME,  authUserVO);
				
				log.trace("{} login.", authUserVO.getUserId());
				
				HttpSession session = req.getSession(false);
				
				if (session != null) {

					String state = (String)session.getAttribute(SessionConfig.SIGN_IN_STATE_NAME);

					if(state != null) {

						session.removeAttribute(SessionConfig.SIGN_IN_STATE_NAME);
					} // if

				} // if 
				
			} // if
			
		} // if

	} // postHandle
	

} // end class
