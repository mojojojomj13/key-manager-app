package com.crm.keymanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.crm.keymanager.constants.CommonConstants;
import com.crm.keymanager.constants.RequestMappingConstants;
import com.crm.keymanager.exceptions.ServiceException;
import com.crm.keymanager.helper.ErrorHelper;
import com.crm.keymanager.service.APIKeyService;
import com.crm.keymanager.validator.ApiKeyRequestValidator;
import com.crm.keymanager.vo.APIKeyVO;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/")
public class HomeController {

	@Autowired
	private ApiKeyRequestValidator validator;

	@Autowired
	private APIKeyService service;

	@RequestMapping(value = "")
	public String home() {
		return "home";
	}

	/**
	 * On Authentication will take the User to the Admin page
	 * 
	 * @param msg
	 *            Any message that is to be displayed on the page
	 * @param error
	 *            In case there's an error, 'true', 'false' otherwise
	 * @return the view name of the Admin Page
	 */
	@RequestMapping(value = RequestMappingConstants.ADMIN_URL, method = RequestMethod.GET)
	public ModelAndView adminPage(@ModelAttribute(CommonConstants.MODEL_ATTR_MSG) String msg,
			@ModelAttribute(CommonConstants.MODEL_ATTR_ERR) String error) {
		ModelAndView model = new ModelAndView();
		model.addObject(CommonConstants.MODEL_ATTR_MSG, "Welcome to Admin Page");
		model.addObject(CommonConstants.MODEL_ATTR_ERR, error);
		model.setViewName(RequestMappingConstants.ADMIN_VIEW);
		return model;
	}

	/**
	 * This method redirects the default calls to Login Form page.
	 * 
	 * @param error
	 *            In case if there is problem while logging in, user name or
	 *            password incorrect
	 * @param logout
	 *            in case the user has logged out after logging in.
	 * @return the view name where the login form exists
	 */
	@RequestMapping(value = RequestMappingConstants.LOGIN, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = CommonConstants.MODEL_ATTR_ERR, required = false) String error,
			@RequestParam(value = CommonConstants.LOGOUT, required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null)
			model.addObject(CommonConstants.MODEL_ATTR_ERR, "Invalid username and password!");
		if (logout != null)
			model.addObject(CommonConstants.MODEL_ATTR_MSG, "You've been logged out successfully.");
		model.setViewName(RequestMappingConstants.LOGIN_VIEW);
		return model;
	}

	// @Autowired
	// private HttpServletResponse response;

	/**
	 * get the Token for the env/app/api
	 * 
	 * @return
	 */
	@RequestMapping(value = RequestMappingConstants.API_ROOT+"token/{app}/{apiName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getToken(@PathVariable String app, @PathVariable String apiName) {
		JsonObject respObject = new JsonObject();
		HttpStatus status = HttpStatus.OK;
		respObject.addProperty("status", HttpStatus.OK.value());
		try {
			APIKeyVO apiKeyVO = new APIKeyVO(app, apiName);
			service.getApiKey(apiKeyVO);
			if (null == apiKeyVO.getKey())
				throw new ServiceException(HttpStatus.NOT_FOUND, "No Such APP or API Name", null);
			respObject.addProperty("apiName", apiKeyVO.getApiName());
			respObject.addProperty("app", apiKeyVO.getApp());
			respObject.addProperty("token", apiKeyVO.getKey());
		} catch (ServiceException e) {
			respObject = new JsonObject();
			status = e.getStatus();
			respObject.addProperty("message", e.getMessage());
			respObject.addProperty("status", e.getStatus().value());
		}
		return new ResponseEntity<String>(respObject.toString(), status);
	}

	@RequestMapping(value = RequestMappingConstants.API_ROOT+"token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> pushToken(@RequestBody @Valid APIKeyVO apiKeyVO, BindingResult errors) {
		validator.validate(apiKeyVO, errors);
		if (errors.hasErrors())
			return ErrorHelper.handleValidationError(errors);
		JsonObject respObject = new JsonObject();
		HttpStatus status = HttpStatus.CREATED;
		respObject.addProperty("status", HttpStatus.CREATED.value());
		try {
			service.saveApiKey(apiKeyVO);
			if (null == apiKeyVO.getId()) {
				throw new ServiceException(HttpStatus.OK, "Object could not be created", null);
			}
			respObject.addProperty("message", "Object created successfully");
		} catch (ServiceException e) {
			respObject = new JsonObject();
			status = e.getStatus();
			respObject.addProperty("message", e.getMessage());
			respObject.addProperty("status", e.getStatus().value());
		}
		return new ResponseEntity<String>(respObject.toString(), status);

	}

	@RequestMapping(value = RequestMappingConstants.API_ROOT+"token", method = RequestMethod.PUT)
	public ResponseEntity<?> updateToken(@RequestBody @Valid APIKeyVO apiKeyVO, BindingResult errors) {
		validator.validate(apiKeyVO, errors);
		if (errors.hasErrors())
			return ErrorHelper.handleValidationError(errors);
		JsonObject respObject = new JsonObject();
		HttpStatus status = HttpStatus.OK;
		respObject.addProperty("status", HttpStatus.OK.value());
		try {
			int updated = service.updateApiKey(apiKeyVO);
			if (updated <= 0) {
				throw new ServiceException(HttpStatus.NO_CONTENT, "Object could not be updated", null);
			}
			respObject.addProperty("message", "Object updated successfully");
		} catch (ServiceException e) {
			respObject = new JsonObject();
			status = e.getStatus();
			respObject.addProperty("message", e.getMessage());
			respObject.addProperty("status", e.getStatus().value());
		}
		return new ResponseEntity<String>(respObject.toString(), status);
	}

	@RequestMapping(value = RequestMappingConstants.API_ROOT+"token/{app}/{apiName}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteToken(@PathVariable String app, @PathVariable String apiName) {
		JsonObject respObject = new JsonObject();
		HttpStatus status = HttpStatus.OK;
		respObject.addProperty("status", HttpStatus.OK.value());
		try {
			APIKeyVO apiKeyVO = new APIKeyVO(app, apiName);
			int updated = service.deleteApiKey(apiKeyVO);
			if (updated <= 0) {
				throw new ServiceException(HttpStatus.NO_CONTENT, "Object could not be deleted", null);
			}
			respObject.addProperty("message", "Object deleted successfully");
		} catch (ServiceException e) {
			respObject = new JsonObject();
			status = e.getStatus();
			respObject.addProperty("message", e.getMessage());
			respObject.addProperty("status", e.getStatus().value());
		}
		return new ResponseEntity<String>(respObject.toString(), status);
	}
}
