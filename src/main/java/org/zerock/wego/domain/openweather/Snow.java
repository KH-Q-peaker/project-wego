package org.zerock.wego.domain.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class Snow {
	
	@JsonProperty("1h")
	private Double h;

} // end class
