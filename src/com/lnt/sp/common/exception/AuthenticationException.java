package com.lnt.sp.common.exception;

import com.lnt.sp.common.exception.ServiceApplicationException;

public class AuthenticationException extends ServiceApplicationException {

	private static final long serialVersionUID = 1L;
	
	private int code;

	public int getCode() {
		return code;
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(String message, int code) {
		super(message);
		this.code = code;
	}

}
