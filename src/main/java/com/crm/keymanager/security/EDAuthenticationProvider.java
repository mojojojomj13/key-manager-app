/**
 *
 */
package com.crm.keymanager.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.crm.keymanager.dao.SecurityDAO;
import com.crm.keymanager.exceptions.DatabaseException;
import com.crm.keymanager.exceptions.ServiceException;
import com.crm.keymanager.vo.User;

/**
 * @author Prithvish Mukherjee
 */
@Component
public class EDAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	SecurityDAO securityDAO;

	@Value("${login.master.username}")
	private String masterUsername;

	private static final Logger LOGGER = LoggerFactory.getLogger(EDAuthenticationProvider.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String loginUsername = (String) authentication.getPrincipal();
		final String loginPassword = (String) authentication.getCredentials();
		User user = null;
		try {
			String hashedPwd = SHA256HashingHelper.hashMessage(loginUsername, loginPassword);
			user = securityDAO.getUser(loginUsername, hashedPwd);
			if (user == null) {
				throw new BadCredentialsException("Invalid username or password");
			}
			user.setAuthorities(securityDAO.getRoles(loginUsername));
			if (LOGGER.isInfoEnabled())
				LOGGER.info("User ::{}", new Object[] { user });
		} catch (ServiceException | DatabaseException e) {
			LOGGER.info("Some error while getting User(Security) :: " + e);
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(user, loginPassword, user.getAuthorities());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
