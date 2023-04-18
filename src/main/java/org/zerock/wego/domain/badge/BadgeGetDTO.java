package org.zerock.wego.domain.badge;

import lombok.Data;


@Data
public class BadgeGetDTO {

	Integer badgeId;
	Integer userId;
	Character status;
	
} // end class
