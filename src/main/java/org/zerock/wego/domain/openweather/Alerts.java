package org.zerock.wego.domain.openweather;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class Alerts {
	
	@JsonProperty("sender_name")
	private String senderName;
	private String event;
	private Long start;
	private Long end;
	private String description;
	private List<String> tags;

} // end class
