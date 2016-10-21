package com.crm.keymanager.exceptions;

import org.springframework.http.HttpStatus;

/**
 * This class will serve as the Exception class for any kind of Service level
 * exceptions thrown in the Service layer of the application.
 *
 * @author ManmeetFIL
 */
public class ServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8968570861374718731L;

	private HttpStatus status;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public ServiceException() {
		super("Some Service Exception occurred in the App ", null);
	}

	public ServiceException(HttpStatus status, String message, Throwable throwable) {
		super(message, throwable);
		this.status = status;

	}

	public ServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

	@Override
	public String toString() {
		return super.getMessage();
	}
}
