package com.tryout.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorBean {
	public static final String SECURITY_NOT_AUTHENTICATED = "security.notauthenticated";

	public static final String SECURITY_NOT_AUTHORIZED = "security.notauthorized";

	public static final String SYSTEM_ERROR = "system.error";

	private String code;
	private String message;

	@JsonIgnore
	private Object[] params;

	public ErrorBean(final String code, final String message) {
		this.code = code;
		this.message = message;
	}

	public ErrorBean(final String code) {
		this.code = code;
	}

	public ErrorBean(final String code, final Object[] params) {
		this.code = code;
		this.params = params;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}