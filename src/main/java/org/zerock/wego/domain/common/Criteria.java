package org.zerock.wego.domain.common;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2

@Data
public class Criteria {
	
	private String targetGb;
	private Integer targetCd;
	private Integer userId;
	private Integer currPage = 1; // 페이지 기본 값 
	private Integer amount = 5; // 불러올 데이터 기본 값
	
	private Integer pagesPerPage = 5; // 한 Pagination(페이지번호목록)의 크기(길이)

	public String getPagingUri() {
		log.trace("getPagingUri() invoked.");
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath(""); 
		builder.queryParam("currPage", this.currPage);
		builder.queryParam("amount", this.amount);
		builder.queryParam("pagesPerPage", this.pagesPerPage);

		
		String queryString = builder.toUriString();
		log.info("\t+ queryString: {}", queryString);
		
		return null;
	} // getPagingUri
}// end class
