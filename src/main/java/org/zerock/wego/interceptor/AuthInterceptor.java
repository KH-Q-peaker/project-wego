package org.zerock.wego.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zerock.wego.config.SessionConfig;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Component("authInterceptor")
public class AuthInterceptor 
	implements HandlerInterceptor{
	

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("preHandle(req, res, handler) invoked \n \t\t\t\t>>>>>>>> requestURI : {}", req.getRequestURI());

		HttpSession session = req.getSession(false);
		
		//		===================================================== 로그인 확인.
		if (session == null && req.getMethod().equals("GET")) {
			
			res.sendRedirect("/login");
			
			return false;
		} else {
			
			Object auth = session.getAttribute(SessionConfig.AUTH_KEY_NAME);

			if (auth == null) {
				
				res.sendRedirect("/login");

				return false;
			} // if-else
		} // if-else

		return true;
	} // preHandle


} // end class
