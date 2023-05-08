package org.zerock.wego.domain.openweather;

import lombok.Data;


@Data
public class Temp {
	
	private Double day;
	private Double min;
	private Double max;
	private Double night;
	private Double eve;
	private Double morn;
	
} // end class
