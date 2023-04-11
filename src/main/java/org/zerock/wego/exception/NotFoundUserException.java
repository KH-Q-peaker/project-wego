package org.zerock.wego.exception;

public class NotFoundUserException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NotFoundUserException(String message) {
		super(message);
		
		super.printStackTrace();
	} // Constructor#1
	
	public NotFoundUserException(Exception originalException) {
		super(originalException);
		
		super.printStackTrace();
	} // Constructor#2
	
	
} // end class
