package org.zerock.wego.controller;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {	
		"file:src/main/webapp/WEB-INF/spring/root-*.xml",
		"file:src/main/webapp/WEB-INF/spring/**/servlet-*.xml" 
})

@WebAppConfiguration

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SanInfoControllerTests {
	
	@Setter(onMethod_= {@Autowired})
	private WebApplicationContext ctx;

//	@Disabled
	@Test
	@Order(1)
	@DisplayName("testShowSanInfo")
	@Timeout(value=5, unit=TimeUnit.SECONDS)
	void testShowSanInfo() throws Exception {
		log.trace("testShowSanInfo() invoked.");

		// MockMvc를 지어줄 "건설사(Builder)"부터 획득
		MockMvcBuilder mockMvcBuilder = MockMvcBuilders.webAppContextSetup(ctx);
		MockMvc mockMvc = mockMvcBuilder.build();
		
		// BoardController의 /board/register, GET 핸들러 메소드
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/info");
		
		// 이제 가상의 MVC환경에서, BoardController에 요청생성 및 전송
		ModelAndView modelAndView = mockMvc.perform(requestBuilder).andReturn().getModelAndView();
		
		log.info("\t+++ modelAndView: {}", modelAndView);
	} // testShowSanInfo
} // end class
