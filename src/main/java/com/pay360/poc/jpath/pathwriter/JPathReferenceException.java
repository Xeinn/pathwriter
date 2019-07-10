package com.pay360.poc.jpath.pathwriter;

public class JPathReferenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JPathReferenceException(String message) {
        super(message);
    }
    
    public JPathReferenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
