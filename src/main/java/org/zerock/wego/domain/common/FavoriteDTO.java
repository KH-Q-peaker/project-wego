package org.zerock.wego.domain.common;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
public class FavoriteDTO {
	
	private Integer userId;
	private String targetGb;
	private Integer targetCd;
	private Timestamp modifiedDt;
	private Character status;
	
	
	@Builder
	public FavoriteDTO(Integer userId, String targetGb, Integer targetCd) {
		super();
		
		this.userId = userId;
		this.targetGb = targetGb;
		this.targetCd = targetCd;
	} // default constructor
	
	
	public static FavoriteDTO findBySanInfoIdAndUserId(Integer sanInfoId, Integer userId) {
		
		return FavoriteDTO.builder()
					.targetGb("SAN_INFO")
					.targetCd(sanInfoId)
					.userId(userId)
					.build();
	} // findSanInfoBySanInfoIdAndUserId

} // end class
