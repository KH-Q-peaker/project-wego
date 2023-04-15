package org.zerock.wego.exception;

public class DuplicateKeyException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DuplicateKeyException() {
		super();
	}// default constructor 
	
	public DuplicateKeyException(String message) {
		super(message);
		
//		super.printStackTrace();
	} // Constructor#1
	
	public DuplicateKeyException(Exception originalException) {
		super(originalException);
		
//		super.printStackTrace();
	} // Constructor#2
} // end class
