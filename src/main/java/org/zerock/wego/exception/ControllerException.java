package org.zerock.wego.exception;

public class ControllerException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ControllerException(String message) {
		super(message);
	} // Constructor#1
	
	public ControllerException(Exception originalException) {
		super(originalException);
	} // Constructor#2
	
	
} // end class
