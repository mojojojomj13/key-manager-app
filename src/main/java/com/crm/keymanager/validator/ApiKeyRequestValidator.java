package com.crm.keymanager.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.crm.keymanager.vo.APIKeyVO;

@Component
public class ApiKeyRequestValidator implements Validator {

	@Override
	public boolean supports(Class<?> target) {
		return APIKeyVO.class.isAssignableFrom(target);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// APIKeyVO apiKeyVO = (APIKeyVO) target;

	}

}
