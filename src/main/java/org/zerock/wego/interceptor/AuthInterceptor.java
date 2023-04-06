package org.zerock.wego.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Component("authInterceptor")
public class AuthInterceptor 
implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws Exception {
		log.trace("preHandle({}, {}, {}) invoked", req, res, handler);

		HttpSession session = req.getSession(false);

		//		===================================================== 로그인 확인.
		if (session == null) {
			session = req.getSession();

			this.authNullLogic(req, res, session);

			return false;
		} else {
			String authKey = "__AUTH__";
			Object auth = session.getAttribute(authKey);

			if (auth == null) {
				this.authNullLogic(req, res, session);

				return false;
			} // if-else
		} // if-else

		// return 값에 의해서, interceptor chain 또는 컨트롤러의 핸들러 에게
		// 가로챈 요청을 넘기게 됩니다.
		//				return true;	// 그 다음으로 넘김
		//				return false;	// 그 다음으로 넘기지 않고, 여기서 응답으로 보내서 끝냄

		return true;	// 기본값은 true로 그 다음으로 요청을 넘김(위임)
	} // preHandle

	private void authNullLogic(HttpServletRequest req, HttpServletResponse res, HttpSession session) 
			throws IOException {
		log.trace("authNullLogic({}, {}, {}}) invoked", req, res, session);
		String requestURI = req.getRequestURI().toString();
		String queryString = req.getQueryString();


		if (queryString != null ) { requestURI += queryString; } // if

		session = req.getSession();

		session.setAttribute("LOGIN_CALL_BACK_URI", requestURI);

		res.sendRedirect("/login");
	} // authNullLogic

	@Override
	public void postHandle(
			HttpServletRequest req,
			HttpServletResponse res,
			Object handler,
			ModelAndView modelAndView
			) throws Exception {
		
		if(modelAndView != null) {
			log.trace("postHandle(req,res,handler,{}) invoked.", modelAndView);

			HttpSession session = req.getSession();

			session.removeAttribute("LOGIN_CALL_BACK_URI");

			} // if

	} // postHandle



} // end class
