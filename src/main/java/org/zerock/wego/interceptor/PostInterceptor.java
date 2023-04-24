package org.zerock.wego.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zerock.wego.domain.common.ReportDTO;
import org.zerock.wego.domain.common.UserVO;
import org.zerock.wego.service.common.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Component("postInterceptor")
public class PostInterceptor implements HandlerInterceptor {

	private final ReportService reportService;
	

		/*
		 * 글이나 댓글을 작성하려고 할 때 해당 요청을 가로채서 
		 * 세션의 유저 신고 총합이 5회 이상인지 확인 
		 * 5회 이상이라면 신고당해서 활동이 제한되었다는 안내 띄우기
		 * 
		 * /party/register 
		 * /party/modify 
		 * 
		 * requestURI데려와서 뒤에 /register, modify 자르고 redirect...?
		 * 그렇다고 예외페이지를 던질 순 없잖아
		 * 그렇다고 인터셉터가 뷰를 조작할 순 없잖아 
		 * 
		 * 만약 응답을 반환할 경
		 * 
		 */

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {

			String[] requestURI = request.getRequestURI().split("/");

			UserVO user = (UserVO) request.getSession(false).getAttribute("__AUTH__");
			Integer targetCd = user.getUserId();
			
			ReportDTO report = ReportDTO.builder().targetGb("USER").targetCd(targetCd).build();
			
			int totalCount = reportService.getTotalCount(report);
			
			
			if (totalCount >= 5) {
				
				if (requestURI[1].equals("comment") || 
					(requestURI.length >= 4 && requestURI[3].equals("join"))) {

//				String message = "너 벤";
//				response.sendRedirect("/" + targetGb);
//				response.setContentType("text/html;charset=UTF-8");
//		        PrintWriter out = response.getWriter();
//		        out.println("<script> setMessage(\"너 벤\"); showModal(); </script>"); // 안됨 
//		        out.println("<script> alert('신고로 인해 활동이 제한되었습니다. 이메일로 문의 하렴'); </script>"); // 됨 근데 구림
//		        out.flush();
					
					response.setContentType("application/json;charset=UTF-8");
					response.setStatus(HttpStatus.FORBIDDEN.value());
//					response.setStatus(403); // 뭐로하지?
					response.getWriter().write("신고로 인해 활동이 제한되었습니다.");
					
				} else {
					
					response.sendRedirect("/" + requestURI[1]); // 일단 목록으로 이동...원래 목록이었는데 목록으로 이동...하...
				}// if-else
				
				return false;
			} // if

			return true;
		}// preHandle
}// end class
