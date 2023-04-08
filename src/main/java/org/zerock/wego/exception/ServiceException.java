package org.zerock.wego.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ServiceException(String message) {
		super(message);
		
		super.printStackTrace();
	} // Constructor#1
	
	public ServiceException(Exception originalException) {
		super(originalException);
		
		super.printStackTrace();
	} // Constructor#2
	
	
} // end class
