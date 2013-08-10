package com.tryout.rest.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorsWrapper implements Serializable {
	private List<ErrorBean> errors = new ArrayList<ErrorBean>();

	public ErrorsWrapper() {
	}

	public ErrorsWrapper(List<ErrorBean> errors) {
		this.errors.addAll(errors);
	}

	public void addError(ErrorBean error) {
		this.errors.add(error);
	}

	public List<ErrorBean> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorBean> errors) {
		this.errors = errors;
	}

}