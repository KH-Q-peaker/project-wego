package org.zerock.wego.exception;

public class LoginException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public LoginException(String message) {
		super(message);
		
		super.printStackTrace();
	} // Constructor#1
	
	public LoginException(Exception originalException) {
		super(originalException);
		
		super.printStackTrace();
	} // Constructor#2
	
	
} // end class
