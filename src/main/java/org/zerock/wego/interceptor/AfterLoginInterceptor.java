package org.zerock.wego.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.UserVO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Component("afterLoginInterceptor")
public class AfterLoginInterceptor 
implements HandlerInterceptor{

	@Override
	public void postHandle(
			HttpServletRequest req,
			HttpServletResponse res,
			Object handler,
			ModelAndView modelAndView
			) throws Exception {
		if(modelAndView != null) {
			UserVO authUserVO = (UserVO) modelAndView.getModelMap().getAttribute(SessionConfig.AUTH_KEY_NAME);

			if(authUserVO != null) {
				log.trace("postHandle(req,res,handler,modelAndView) invoked.");

				modelAndView.getModelMap().remove(SessionConfig.AUTH_KEY_NAME);

				req.getSession().setAttribute(SessionConfig.AUTH_KEY_NAME,  authUserVO);
			} // if
		} // if

	} // postHandle


} // end class
