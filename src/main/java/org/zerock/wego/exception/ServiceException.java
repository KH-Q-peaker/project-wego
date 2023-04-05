package org.zerock.wego.exception;

public class ServiceException extends Exception {

	
	public ServiceException() {
		
		super();
	}// constructor 1
	
	
	public ServiceException(Exception originalException) {
		
		super(originalException);
	}// constructor 2
}// end class
