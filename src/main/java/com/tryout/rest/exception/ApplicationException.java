package com.tryout.rest.exception;

import java.util.List;

import org.springframework.validation.BindingResult;

public class ApplicationException extends ApplicationBaseException {

	public ApplicationException(BindingResult fieldErrors) {
		super(fieldErrors);
	}

	public ApplicationException(String code) {
		super(code);
	}

	public ApplicationException(String code, Throwable cause) {
		super(code, cause);
	}

	public ApplicationException(List<ErrorBean> errorsList) {
		super(errorsList);
	}
}
