package com.crm.keymanager.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -865133528704393478L;

	private String msg;

	private HttpStatus status;

	public InvalidRequestException() {
	}

	public InvalidRequestException(String msg, HttpStatus status) {
		super(msg, null);
		this.msg = msg;
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
