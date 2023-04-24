package org.zerock.wego.domain.info;

import lombok.Value;

@Value
public class SanInfodeVO {
   

   private Integer sanInfoId;// 산 정보 글 코드 시퀀스
   private Integer mountianCd;//산림청 산 코드
   private String sanName;//산 이름
   private String height;//산 높이
   private String address;//산 주소
   private String reason;//산 선정이유
   private String overview;//산 개관정보
   private String details;//산 설명
   private Integer lon;//위도
   private Integer lat;//경도
   private String img;//산 이미지
   private Integer baseDate;//자신이 조회하고 싶은 날짜
   private Integer baseTime;//자신이 조회하고 싶은 시간대

}