package org.zerock.wego.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinDTO {
	
	private Integer sanPartyId;
	private Integer userId;

} // end class
