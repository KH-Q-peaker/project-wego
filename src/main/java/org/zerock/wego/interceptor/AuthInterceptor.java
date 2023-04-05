package org.zerock.wego.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Component("AuthInterceptor")
public class AuthInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("preHandle({}, {}, {}) invoked", req, res, handler);
		
		
		HttpSession session = req.getSession(false);

		String requestURL = req.getRequestURL().toString();
		String queryString = req.getQueryString();

		if (queryString != null) { requestURL += queryString; }

		if (session == null) { session = req.getSession(); }

		
		
		if (session.getAttribute("__AUTH__") == null) {

			session.setAttribute("requestURL", requestURL);

			res.sendRedirect("/kakao/ddd");

			
			return false;
		}else {
			
			return true;
		}// if-else
	} // preHandle
	

	
	
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, 
								Object handler, Exception ex) throws Exception {
		
		HttpSession session = req.getSession(false);
		
		if(session != null) {
			session.removeAttribute("requestURL");
		}// if
		
	}// afterCompletion
	
} // end class




//
//
//
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username + " not found");
//        }
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(), user.getPassword(),
//                AuthorityUtils.createAuthorityList(user.getRole().name()));
//    }
//}
//
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                .anyRequest().authenticated()
//                .and().formLogin()
//                .and().logout();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
//}


