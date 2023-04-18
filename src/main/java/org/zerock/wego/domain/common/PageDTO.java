package org.zerock.wego.domain.common;


import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
public class PageDTO {

	private Criteria cri;			// 페이징 처리를 위한 기준정보객체 
	
	private Integer totalAmount;		// 총 레코드 건수

	// Pagination 모든 변수 계산~ 
	private Integer startPage;			// 한 페이지당 페이지목록의 시작번호
	private Integer endPage;			// 한 패이지당 페이지목록의 끝번호
	private Integer realEndPage;		// 총 레코드 건수에 기반한 총 페이지 수
	
	private Integer offset;				// 현재 페이지에 해당하는 레코드의 시작번호
	
	private Boolean prev;			// 다음 페이지목록의 존재여부
	private Boolean next;			// 이전 페이지목록의 존재여부
	
	
	
	public PageDTO(Criteria cri, Integer totalAmount) {
		this.cri = cri;
		
		this.totalAmount = totalAmount;
		
		//----------------------------------------------------------//
		//--Step.0 : 페이징 처리를 위한 공통변수 생성하기
		//----------------------------------------------------------//
		int currPage = cri.getCurrPage();
		int amount = cri.getAmount();
		int pagesPerPage = cri.getPagesPerPage();

		//----------------------------------------------------------//
		//--Step.1 : 현재 페이지에서 보여줄 페이지목록의 끝페이지번호 구하기
		//----------------------------------------------------------//
		// (공식) 끝페이지번호 = (int) Math.ceil( (double) 현재페이지번호 / 페이지목록길이 ) x 페이지목록길이
		//----------------------------------------------------------//
		this.endPage = (int) Math.ceil( (currPage * 1.0) / pagesPerPage ) * pagesPerPage;

		//----------------------------------------------------------//
		//--Step.3 : 현재 페이지의 페이지번호목록의 시작번호 구하기
		//----------------------------------------------------------//
		// (공식) 시작페이지번호 = 끝페이지번호 - ( 페이지목록길이 - 1 )
		//----------------------------------------------------------//
		this.startPage = this.endPage - ( pagesPerPage - 1 );

		//----------------------------------------------------------//
		//--Step.4 : 총페이지수 구하기
		//----------------------------------------------------------//
		// (공식) 총페이지수 = (int) Math.ceil( (double) 모든행의개수 / 한페이지당행의수 )
		//----------------------------------------------------------//
		this.realEndPage = (int) Math.ceil( (totalAmount * 1.0) / amount );
		
		if(this.realEndPage < this.endPage) {
			this.endPage = this.realEndPage;
		} // if

		//----------------------------------------------------------//
		//--Step.5 : 이전 페이지번호목록으로 이동가능여부(prev) 구하기
		//----------------------------------------------------------//
		// (공식) 이전페이지목록이동가능여부 = 시작페이지번호 > 1
		//----------------------------------------------------------//
		this.prev = this.startPage > 1;

		//----------------------------------------------------------//
		//--Step.6 : 다음 페이지번호목록으로 이동가능여부(next) 구하기
		//----------------------------------------------------------//
		// (공식) 다음페이지목록이동가능여부 = 끝페이지번호 < 총페이지수
		//----------------------------------------------------------//
		this.next = this.endPage < realEndPage;

		//----------------------------------------------------------//
		//--Step.7 : 현재 페이지에 표시할 목록의 시작 offset 구하기
		//----------------------------------------------------------//
		// (공식) 시작 offset = (현재페이지번호 - 1) x 한페이지당행의수
		//----------------------------------------------------------//
		this.offset = ( currPage - 1 ) * amount;
	} // constructor
	

} // end class
