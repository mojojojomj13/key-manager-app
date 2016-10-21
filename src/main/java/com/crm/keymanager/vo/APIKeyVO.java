package com.crm.keymanager.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class APIKeyVO {

	private Long id = -1L;

	@NotNull(message = " the 'app' property cannot be null")
	@NotEmpty(message = " the 'app' property cannot be empty")
	private String app;

	@NotNull(message = " the 'key' property cannot be null")
	@NotEmpty(message = " the 'key' property cannot be empty")
	@Length(max = 36, min = 36, message = "the 'key' property must be 16 chars in length")
	private String key;

	@NotNull(message = " the 'apiName' property cannot be null")
	@NotEmpty(message = " the 'apiName' property cannot be empty")
	private String apiName;

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public APIKeyVO(String app, String apiName) {
		this.app = app;
		this.apiName = apiName;
	}

	public APIKeyVO(String app, String key, String apiName) {
		this.app = app;
		this.key = key;
		this.apiName = apiName;
	}

	public APIKeyVO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
