package org.zerock.wego.domain.mypage;


import lombok.Getter;
import lombok.ToString;

//Criteria DTO(페이징처리 기준정보) + 총게시물 건수( totalAmount) =>
//나머지 게시판 목록 하단에 표시할 'Pagination'정보를 모두 계산해주는 클래스
@ToString
@Getter
public class APageDTO {
	
	private ACriteria acri;	//기준 정보는 기본으로 주어짐
	private int totalAmount;	//총 게시물 건수
	
	//자동 계산할 숫자들
	//현재 내가 있는 페이지 기준으로, 목록 하단뷰에 표시할 pagination의 첫페이지와 끝페이지번호
	private int startPage;
	private int endPage;
	private int realEndPage; //실제 끝 페이지 번호(예를들면 101개면, 5로 나눠지지만 6페이지가 필요)
	private int offset;//현재 currPage기준으로, OFFSET절에 저장할 레코드 번호.
	private boolean prev; //이전 pagination 목록으로 이동가능한가?(ex 11~20 > 1~10) 
	private boolean next;//이후 pagination목록으로 이동 가능한가?(ex 1~10 > 11~20) 
	
	
	public APageDTO(ACriteria acri, int totalAmount) {//두가지 정보로 아래를 계산함.
		
		this.acri = acri;
		this.totalAmount = totalAmount;
		
		//----------------------------------------------------------//
		//--Step.0 : 페이징 처리를 위한 공통변수 생성하기
		//----------------------------------------------------------//
		int currPage = acri.getACurrPage();
		int amount = acri.getAAmount();
		int pagesPerPage = acri.getPagesPerPage();

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
	
	
	
}//end class
