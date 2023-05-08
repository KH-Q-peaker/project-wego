package org.zerock.wego.domain.openweather;

import lombok.Data;


@Data
public class FeelsLike {
	
	private Double day;
	private Double night;
	private Double eve;
	private Double morn;

} // end class
