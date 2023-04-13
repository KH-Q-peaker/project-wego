package org.zerock.wego.domain.mypage;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import lombok.extern.log4j.Log4j2;


// 이 DTO는 페이징 처리와 관련된 전송파라미터만 수집용도
//기준정보는 항상 달고 다녀야해요.
@Log4j2
@Data
public class Criteria {
	private Integer currPage = 1;	//내가 있는 현재 페이지번호
	private Integer amount =  10;	//한 페이지에 보여줄 게시물개수
	
	//아래 페이지목록은 고정시킬지, 또는 사용자로부터 받을지는 구현에 따라 달라짐.
	private Integer pagesPerPage = 10; //한 pagination(페이지번호목록)의 크기
	
	//만약 검색기능을 추가한다고하면, 작성자 아이디로 조회할까, 내용으로 조회할까, 타이틀로 조회할까
	//검색기능이 들어가면, 출력되는 목록도 달라져요. 이에따라 페이지네이션이 되야하는거죠
	//그럴때는 검색조건이 기준정보로 추가될 필요가 있습니다
	private String type;
	private String keyword;
	
	//아래는 쿼리스트링을 만들어주는 메소드.(위의 정보를 가지고)
	// 오직 rttrs 를 이용하지않고, 직접 쿼리스트링을 만들어야하는 경우, 스프링의 유틸리티 클래스에 있습니다
	//rttrs는 단점이있어요. uri인코딩을 안해줍니다.
	//원래는 rttrs.addAttribute("currPage",1);~~ == return"redirect:/board/list?currPage";
	//그런데 rttrs.addAttribute("type",작성자)이렇게 한글로 하면 깨져버립니다(uri인코딩을 안해줌)
	//그래서 return"redirect:/board/list?currPage"; 이런식으로 처리하는게 나아요
 	public String getPagingUri() {
		log.trace("getPagingUri() invoked");
		
		//Spring Framework이 제공하는 Utilities를 이용해서 쿼리스트링 생성
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath(""); //전송파라미터로 uri를 만들어주는 빌더
		builder.queryParam("currPage", this.currPage);
		builder.queryParam("amount", this.amount);
		builder.queryParam("pagesPerPage", this.pagesPerPage);
//		builder.queryParam("type", this.type);
//		builder.queryParam("keyword", this.keyword);//알아서 uri 인코딩해줌.
		
		String queryString = builder.toUriString();
		log.info("\t+queryString:{} ",queryString);
		
		return queryString;
	}//getPagingUri
	
} // end class