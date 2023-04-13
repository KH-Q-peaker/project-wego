package org.zerock.wego.config;

import java.util.Deque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zerock.wego.domain.BadgeVO;
import org.zerock.wego.service.BadgeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Configuration
public class BadgeConfig {

	private final BadgeService badgeService;



	
	@Bean
	public Deque<BadgeVO> getSanBadgeList() {
		log.trace("getBadgeList() invoked");

		return this.badgeService.getSanBadgeData();

	} // getSanBadgeList
	
	@Bean
	public Deque<BadgeVO> getRankingBadgeList() {
		log.trace("getBadgeList() invoked");

		return this.badgeService.getRankingBadgeData();

	} // getRankingBadgeList

} // end class