package com.crm.keymanager.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.google.gson.JsonObject;

public class ErrorHelper {

	public static ResponseEntity<?> handleValidationError(BindingResult errors) {
		JsonObject respObj = new JsonObject();
		respObj.addProperty("status", HttpStatus.BAD_REQUEST.value());
		StringBuffer buff = new StringBuffer();
		for (ObjectError err : errors.getAllErrors()) {
			buff.append(err.getDefaultMessage() + ", ");
		}
		respObj.addProperty("message", buff.toString());
		ResponseEntity<?> response = new ResponseEntity<String>(respObj.toString(), HttpStatus.BAD_REQUEST);
		return response;
	}

}
