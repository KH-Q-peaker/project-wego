package org.zerock.wego.exception;

public class OperationFailException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public OperationFailException() {
		super();
	}// default constructor 
	
	public OperationFailException(String message) {
		super(message);
		
//		super.printStackTrace();
	} // Constructor#1
	
	public OperationFailException(Exception originalException) {
		super(originalException);
		
//		super.printStackTrace();
	} // Constructor#2
} // end class
