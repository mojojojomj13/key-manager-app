package com.crm.keymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.crm.keymanager.exceptions.DatabaseException;
import com.crm.keymanager.vo.APIKeyVO;

@Repository
public class APIKeyDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(APIKeyDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public APIKeyVO getApiKeyVO(APIKeyVO apiKeyVO) throws DatabaseException {
		try {

			jdbcTemplate.query(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement pst = conn.prepareStatement(
							" SELECT * FROM API_KEYS WHERE LOWER(APP) = LOWER(?) AND LOWER(APINAME) = LOWER(?)");
					pst.setString(1, apiKeyVO.getApp());
					pst.setString(2, apiKeyVO.getApiName());
					return pst;
				}
			}, new ResultSetExtractor<APIKeyVO>() {
				@Override
				public APIKeyVO extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						apiKeyVO.setKey(rs.getString("KEY"));
						apiKeyVO.setApiName(rs.getString("APINAME"));
						apiKeyVO.setApp(rs.getString("APP"));
						return apiKeyVO;
					}
					return apiKeyVO;
				}
			});
		} catch (DataAccessException e) {
			LOGGER.error("Some error while getting API Key from DB  :: " + e.toString());
			throw new DatabaseException(" Error getting API Key from DB :: " + e.toString(), e);
		}
		return apiKeyVO;
	}

	public APIKeyVO saveApiKeyVO(APIKeyVO apiKeyVO) throws DatabaseException {
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			int rows = jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement pst = conn.prepareStatement(
							" INSERT INTO API_KEYS (APP, APINAME , KEY) VALUES (?, ?, ?) ", new String[] { "id" });
					pst.setString(1, apiKeyVO.getApp());
					pst.setString(2, apiKeyVO.getApiName());
					pst.setString(3, apiKeyVO.getKey());
					return pst;
				}
			}, holder);
			if (rows > 0)
				apiKeyVO.setId(holder.getKey().longValue());
		} catch (InvalidDataAccessApiUsageException e) {
			LOGGER.error("Error while saving apiKey to DB :: " + e.toString());
			throw new DatabaseException("Some Internal DB App Error", e);
		} catch (DataAccessException e) {
			LOGGER.error("Error while saving apiKey to DB :: " + e.toString());
			throw new DatabaseException("Some Internal DB App Error", e);
		}
		return apiKeyVO;
	}

	public int updateApiKeyVO(APIKeyVO apiKeyVO) throws DatabaseException {
		int rows = 0;
		try {
			rows = jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement pst = conn.prepareStatement(
							" UPDATE API_KEYS SET KEY = ?  WHERE  LOWER(APINAME) = LOWER(?) AND LOWER(APP) = LOWER(?) ");
					pst.setString(3, apiKeyVO.getApp());
					pst.setString(2, apiKeyVO.getApiName());
					pst.setString(1, apiKeyVO.getKey());
					return pst;
				}
			});
		} catch (InvalidDataAccessApiUsageException e) {
			LOGGER.error("Error while updating apiKey to DB :: " + e.toString());
			throw new DatabaseException("Some Internal DB App Error", e);
		} catch (DataAccessException e) {
			LOGGER.error("Error while updating apiKey to DB :: " + e.toString());
			throw new DatabaseException("Some Internal DB App Error", e);
		}
		return rows;
	}

	public int deleteApiKeyVO(APIKeyVO apiKeyVO) throws DatabaseException {
		int rows = 0;
		try {
			rows = jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement pst = conn.prepareStatement(
							" DELETE FROM API_KEYS WHERE  LOWER(APINAME) = LOWER(?) AND LOWER(APP) = LOWER(?) ");
					pst.setString(2, apiKeyVO.getApp());
					pst.setString(1, apiKeyVO.getApiName());
					return pst;
				}
			});
		} catch (InvalidDataAccessApiUsageException e) {
			LOGGER.error("Error while deleting apiKey to DB :: " + e.toString());
			throw new DatabaseException("Some Internal DB App Error", e);
		} catch (DataAccessException e) {
			LOGGER.error("Error while deleting apiKey to DB :: " + e.toString());
			throw new DatabaseException("Some Internal DB App Error", e);
		}
		return rows;
	}
}
