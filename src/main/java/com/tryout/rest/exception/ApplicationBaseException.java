package com.tryout.rest.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;

public abstract class ApplicationBaseException extends RuntimeException {
	private BindingResult fieldErrors;

	private List<ErrorBean> errorsList = new ArrayList<ErrorBean>();

	public ApplicationBaseException(List<ErrorBean> errorsList) {
		this.errorsList = errorsList;
	}

	public ApplicationBaseException(BindingResult fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public ApplicationBaseException(String code) {
		super(code);
	}

	public ApplicationBaseException(String code, Throwable cause) {
		super(code, cause);
	}

	public void setFieldErrors(BindingResult fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public List<ErrorBean> getErrorsList() {
		return errorsList;
	}

	public void setErrorsList(List<ErrorBean> errorsList) {
		this.errorsList = errorsList;
	}

	public BindingResult getFieldErrors() {
		return fieldErrors;
	}

}
