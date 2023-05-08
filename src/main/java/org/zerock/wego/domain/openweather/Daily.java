package org.zerock.wego.domain.openweather;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class Daily {

	private Timestamp dt;
	private Long sunrise;
	private Long sunset;
    private Long moonrise;
    private Long moonset;
    @JsonProperty("moon_phase")
    private Double moonPhase;
    private Temp temp;
    @JsonProperty("feels_like")
    private FeelsLike feelsLike;
    private Long pressure;
    private Long humidity;
    @JsonProperty("dew_point")
    private Double dewPoint;
    @JsonProperty("wind_speed")
    private Double windSpeed;
    @JsonProperty("wind_deg")
    private Long windDeg;
    @JsonProperty("wind_gust")
    private Double windGust;
    private Long clouds;
    private Double pop;
    private Double uvi;
    private Double rain;
	private Double snow;	
    private List<Weather> weather;
    
} // end class

