package org.zerock.wego.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zerock.wego.config.SessionConfig;
import org.zerock.wego.domain.common.ReportDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.service.common.ReportService;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Component("postInterceptor")
public class PostInterceptor implements HandlerInterceptor {

	private final ReportService reportService;
	private final String message = "신고로 인해 활동이 제한되었습니다";
	
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {

			String[] requestURI = request.getRequestURI().split("/");

			UserVO user = (UserVO) request.getSession(false).getAttribute(SessionConfig.AUTH_KEY_NAME);
			
			boolean isActive = user.getStatus().equals("Y");
			
			if (!isActive) {
				
//				if (requestURI[1].equals("comment") || 
//					(requestURI.length >= 4 && requestURI[3].equals("join"))) {
				if(isAjax(request)) {
					
					response.setContentType("application/json;charset=UTF-8");
					response.setStatus(HttpStatus.FORBIDDEN.value());
					response.getWriter().write(message);
					
				} else {

					response.setContentType("text/html;charset=UTF-8");
					PrintWriter out = response.getWriter();
					
					// 이게 최선인가? 글쓰기 버튼 비동기 해줘 !!!!!!!!!!1
					out.println("<script>");
					out.println("alert('" + message + "');");
					out.println("location.href='" + "/" + requestURI[1] + "';");
					out.println("</script>");
				}// if-else
				
				return false;
			} // if

			return true;
		}// preHandle
		
		private boolean isAjax(HttpServletRequest request) {
			
			return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		}// isAjax
		
}// end class
