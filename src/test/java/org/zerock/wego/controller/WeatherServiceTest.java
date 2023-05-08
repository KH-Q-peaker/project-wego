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
import org.springframework.web.servlet.ModelAndView;
import org.zerock.wego.service.info.WeatherService;

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

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WeatherServiceTest {
	
	@Setter(onMethod_= {@Autowired})
	private WeatherService weatherService;

//	@Disabled
	@Test
	@Order(1)
	@DisplayName("testOpenWeather")
	@Timeout(value=5, unit=TimeUnit.SECONDS)
	void testOpenWeather() throws Exception {
		log.trace("testOpenWeather() invoked.");

		System.out.println(weatherService.getCurrentByLatLon(33.3766655632143, 126.54222094512));
	} // testOpenWeather
} // end class
