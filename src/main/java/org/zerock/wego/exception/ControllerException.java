package org.zerock.wego.exception;

public class ControllerException extends Exception{

	public ControllerException() {
		
		super();
	}// constructor1
	
	
	public ControllerException(Exception originalException) {
		
		super(originalException);
	}// constuctor2
	
}// end class
