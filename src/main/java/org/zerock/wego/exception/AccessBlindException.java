package org.zerock.wego.exception;

public class AccessBlindException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public AccessBlindException() {
		super();
	}// default constructor 
	
	public AccessBlindException(String message) {
		super(message);
		
//		super.printStackTrace();
	} // Constructor#1
	
	public AccessBlindException(Exception originalException) {
		super(originalException);
		
//		super.printStackTrace();
	} // Constructor#2
} // end class
