package com.reporting.bi.exception;

public class UnsupportedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnsupportedException() {
		super();
	}
	
	public UnsupportedException(final String message) {
		super(message);
	}
	
	public UnsupportedException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public UnsupportedException(final Throwable cause) {
		super(cause);
	}
}