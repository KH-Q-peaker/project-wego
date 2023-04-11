package org.zerock.wego.exception;

public class NotFoundPageException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NotFoundPageException(String message) {
		super(message);
		
		super.printStackTrace();
	} // Constructor#1
	
	public NotFoundPageException(Exception originalException) {
		super(originalException);
		
		super.printStackTrace();
	} // Constructor#2
	
	
} // end class
