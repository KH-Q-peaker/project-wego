package org.zerock.wego.domain.openweather;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class OpenWeatherMapDTO {

	private Double lat;
	private Double lon;
	private String timezone;
	@JsonProperty("timezone_offset")
	private Long timezoneOffset;
	private Current current;
	private List<Hourly> hourly;
	private List<Daily> daily;
	private List<Alerts> alerts;
	
} // end class
	