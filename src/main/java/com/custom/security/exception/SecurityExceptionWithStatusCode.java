package com.custom.security.exception;

import java.io.Serial;

public class SecurityExceptionWithStatusCode extends SecurityException {
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -7067834858086911143L;
	private final int statusCode;

	public SecurityExceptionWithStatusCode(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}
}
