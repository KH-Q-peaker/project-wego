package org.zerock.wego.domain.mypage;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TsanPartyDTO {
	
    private Integer sanPartyId;
    private Integer sanInfoId;
    private Integer userId;
    private Timestamp createdDt;
    private Timestamp modifiedDt;
    private String title;
    private String contents;
    private Timestamp partyDt;
    private Integer partyMax;
    private String items;
    private String condition;
    
    public TsanPartyDTO(
    		Integer sanPartyId,Integer sanInfoId,Integer userId,
    		String title,String contents,Timestamp partyDt,Integer partyMax
    		) {
    	this.sanPartyId = sanPartyId;
    	this.sanInfoId = sanInfoId;
    	this.userId = userId;
    	this.title = title;
    	this.contents = contents;
    	this.partyDt = partyDt;
    	this.partyMax = partyMax;
    }//Constructor

}//end class
