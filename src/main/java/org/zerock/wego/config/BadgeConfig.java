package org.zerock.wego.config;

import java.util.Deque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zerock.wego.domain.badge.BadgeVO;
import org.zerock.wego.service.badge.BadgeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Configuration
public class BadgeConfig {

	private final BadgeService badgeService;

	
	@Bean
	public Deque<BadgeVO> getAllSan() {
		log.trace("getBadgeList() invoked");

		return badgeService.getAllSan();
	} // getAllSan
	
	
	@Bean
	public Deque<BadgeVO> getAllRanking() {
		log.trace("getBadgeList() invoked");

		return badgeService.getAllRanking();
	} // getAllRanking

	
} // end class