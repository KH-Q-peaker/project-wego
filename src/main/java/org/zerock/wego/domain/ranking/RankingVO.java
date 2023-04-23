package org.zerock.wego.domain.ranking;

import lombok.Value;


@Value
public class RankingVO {
	private Integer ranking;
	private Integer userId;
	private String userPic;
	private String nickname;
	private Integer value;
}
