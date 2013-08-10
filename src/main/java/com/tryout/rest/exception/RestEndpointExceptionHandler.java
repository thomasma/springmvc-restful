package com.tryout.rest.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestEndpointExceptionHandler extends
		ResponseEntityExceptionHandler {
	protected final static Logger LOGGER = LoggerFactory
			.getLogger(RestEndpointExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	ErrorsWrapper handleAccessDeniedException(AccessDeniedException ex) {
		LOGGER.error("", ex);
		List<ErrorBean> errors = new ArrayList<ErrorBean>();
		errors.add(resolveLocalizedErrorMessage(
				ErrorBean.SECURITY_NOT_AUTHORIZED, null));
		return new ErrorsWrapper(errors);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorsWrapper handleApplicationExceptions(ApplicationException ex) {
		LOGGER.error("{}", ex);
		List<ErrorBean> errors = ex.getErrorsList();

		// if list is null, create an empty one
		if (errors == null) {
			errors = new ArrayList<ErrorBean>();
		}

		// if list is empty and a single error code was specified, then process
		// that one
		if (errors.isEmpty() && StringUtils.isNotEmpty(ex.getMessage())) {
			errors.add(new ErrorBean(ex.getMessage()));
		}

		// resolve any errors collected so far
		if (errors.size() > 0) {
			List<ErrorBean> resolvedErrs = new ArrayList<ErrorBean>();
			for (ErrorBean errorBean : errors) {
				resolvedErrs.add(resolveLocalizedErrorMessage(
						errorBean.getCode(), errorBean.getParams()));
			}
			errors.clear();
			errors.addAll(resolvedErrs);
		}

		// if errors were found using Spring validator framework
		if (ex.getFieldErrors() != null) {
			errors.addAll(resolveErrorMessages(ex.getFieldErrors()
					.getAllErrors()));
		}

		// if no errors were found but hey this is still an error so lets send
		// default system error message
		if (errors.size() == 0) {
			errors = new ArrayList<ErrorBean>();
			errors.add(resolveLocalizedErrorMessage(ErrorBean.SYSTEM_ERROR,
					null));
		}

		return new ErrorsWrapper(errors);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	ErrorsWrapper handleAllOtherExceptions(Exception ex) {
		LOGGER.error("", ex);
		List<ErrorBean> errors = new ArrayList<ErrorBean>();
		errors.add(resolveLocalizedErrorMessage(ErrorBean.SYSTEM_ERROR, null));
		return new ErrorsWrapper(errors);
	}

	/**
	 * Resolve SpringMVC validation errors using a resource bundle.
	 * 
	 * @param fieldErrors
	 * @return
	 */
	private List<ErrorBean> resolveErrorMessages(List<ObjectError> fieldErrors) {
		List<ErrorBean> errors = new ArrayList<ErrorBean>();
		for (ObjectError fieldError : fieldErrors) {
			String localizedErrorMessage = fieldError.getCode();
			try {
				localizedErrorMessage = messageSource.getMessage(fieldError,
						LocaleContextHolder.getLocale());
			} catch (NoSuchMessageException ex) {
				// default the message to same as the code
				LOGGER.error(
						"Could not locate error message in resource bundle messages.properties for code {}. Message will default to same as the code.",
						fieldError.getCode());
				localizedErrorMessage = fieldError.getCode();
			}
			errors.add(new ErrorBean(fieldError.getCode(),
					localizedErrorMessage));
		}
		return errors;
	}

	/**
	 * Resolve simple error codes to their equivalent error messages from the
	 * resource bundle.
	 * 
	 * @param fieldErrors
	 * @return
	 */
	private ErrorBean resolveLocalizedErrorMessage(final String code,
			final Object[] params) {
		try {
			return new ErrorBean(code, messageSource.getMessage(code,
					(params != null ? params : new Object[] {}),
					LocaleContextHolder.getLocale()));
		} catch (Exception ex) {
			// default the message to same as the code
			LOGGER.error(
					"Could not locate error message in resource bundle messages.properties for code {}. Message will default to same as the code.",
					code);
			return new ErrorBean(code, code);
		}
	}

}