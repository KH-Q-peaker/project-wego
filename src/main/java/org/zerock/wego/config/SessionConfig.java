package org.zerock.wego.config;

import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor

@Configuration
public class SessionConfig {
	
	public static final String AUTH_KEY_NAME = "__AUTH__";
	public static final String SIGN_IN_STATE_NAME = "__STATE__";
	
} // end class
