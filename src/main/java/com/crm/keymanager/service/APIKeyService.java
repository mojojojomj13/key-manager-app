package com.crm.keymanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.crm.keymanager.dao.APIKeyDAO;
import com.crm.keymanager.exceptions.DatabaseException;
import com.crm.keymanager.exceptions.ServiceException;
import com.crm.keymanager.vo.APIKeyVO;

@Service
public class APIKeyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(APIKeyService.class);

	@Autowired
	private APIKeyDAO dao;

	public APIKeyVO getApiKey(APIKeyVO apiKeyVO) throws ServiceException {
		try {
			return dao.getApiKeyVO(apiKeyVO);
		} catch (DatabaseException e) {
			LOGGER.error("Some error while getting API key from Service  :: " + e.toString());
			throw new ServiceException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage(), e);
		}
	}

	public APIKeyVO saveApiKey(APIKeyVO apiKeyVO) throws ServiceException {
		try {
			return dao.saveApiKeyVO(apiKeyVO);
		} catch (DatabaseException e) {
			LOGGER.error("Some error while saving API key from Service  :: " + e.toString());
			throw new ServiceException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage(), e);
		}
	}

	public int updateApiKey(APIKeyVO apiKeyVO) throws ServiceException {
		try {
			return dao.updateApiKeyVO(apiKeyVO);
		} catch (DatabaseException e) {
			LOGGER.error("Some error while updating API key from Service  :: " + e.toString());
			throw new ServiceException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage(), e);
		}
	}

	public int deleteApiKey(APIKeyVO apiKeyVO) throws ServiceException {
		try {
			return dao.deleteApiKeyVO(apiKeyVO);
		} catch (DatabaseException e) {
			LOGGER.error("Some error while updating API key from Service  :: " + e.toString());
			throw new ServiceException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage(), e);
		}
	}

}
