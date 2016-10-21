package com.crm.keymanager.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.crm.keymanager.constants.QueryConstants;
import com.crm.keymanager.exceptions.DatabaseException;
import com.crm.keymanager.vo.Role;
import com.crm.keymanager.vo.User;

/**
 * @author Prithvish Mukherjee
 */
@Repository
public class SecurityDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public User getUser(final String userName, final String password) throws DatabaseException {
		try {
			return jdbcTemplate.query(QueryConstants.GET_USER_QRY, new Object[] { userName, password },
					(ResultSetExtractor<User>) rs -> {
						if (!rs.next()) {
							return null;
						}
						User user = new User();
						user.setUserName(rs.getString("USERNAME"));
						user.setPassword(rs.getString("PASSWORD"));
						user.setEnabled(rs.getBoolean("ENABLED"));
						return user;
					});
		} catch (DataAccessException e) {
			throw new DatabaseException("Some error while retrieving User (Security) from DB  :: " + e, e);
		}
	}

	public List<Role> getRoles(final String userName) {
		try {
			return jdbcTemplate.query(QueryConstants.GET_ROLES_QRY, new Object[] { userName },
					(RowMapper<Role>) (rs, rowNum) -> {
						Role role = new Role();
						role.setName(rs.getString("ROLE"));
						return role;
					});
		} catch (DataAccessException exception) {
			exception.printStackTrace();
		}
		return null;
	}
}