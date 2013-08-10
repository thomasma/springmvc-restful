package com.tryout.rest.exception;

import org.springframework.validation.BindingResult;

public class ApplicationSystemException extends ApplicationBaseException {

	public ApplicationSystemException(BindingResult fieldErrors) {
		super(fieldErrors);
	}

	public ApplicationSystemException(String code) {
		super(code);
	}

	public ApplicationSystemException(String code, Throwable cause) {
		super(code, cause);
	}
}
