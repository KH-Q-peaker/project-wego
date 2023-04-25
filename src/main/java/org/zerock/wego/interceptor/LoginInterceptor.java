package org.zerock.wego.interceptor;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.config.SessionConfig;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Component("loginInterceptor")
public class LoginInterceptor 
implements HandlerInterceptor{


	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("preHandle(req, res, handler) invoked");

		HttpSession session = req.getSession(false);

		if (session == null) {

			session = req.getSession();
		} // if

		// 사이트 간 요청 위조(cross-site request forgery) 공격을 방지하기 위해 애플리케이션에서 생성한 상태 토큰값
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();

		session.setAttribute(SessionConfig.SIGN_IN_STATE_NAME, state);

		return true;
	} // preHandle

} // end class
