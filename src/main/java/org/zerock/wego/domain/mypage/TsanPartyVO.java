package org.zerock.wego.domain.mypage;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Value;

@Value
public class TsanPartyVO {
	
    private Integer sanPartyId;
    private Integer sanInfoId;
    private Integer userId;
    private Timestamp createdDt;
    private Timestamp modifiedDt;
    private String title;
    private String contents;
    private String partyDt;
    private Integer partyMax;
    private String items;
    private String condition;
}//end class
