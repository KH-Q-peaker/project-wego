package org.zerock.wego.config;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Configuration
public class WegoConfig {

	@Bean
	public Properties getPropertiesBean() {
		log.trace("getPropertiyBean() invoked");
		
		String PROPERTIES_PATH = "config/wego.properties";
		Properties properties = new Properties();

		try {
			Reader reader = Resources.getResourceAsReader(PROPERTIES_PATH);

			properties.load(reader);
		} 
		catch (IOException e) {
			e.printStackTrace();
		} // try catch

		return properties;
	} // getPropertiyBean

} // end class
