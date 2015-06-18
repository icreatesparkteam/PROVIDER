package com.lnt.sp.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lnt.sp.common.exception.AuthenticationException;
import com.lnt.sp.common.exception.ServiceApplicationException;

import com.lnt.sp.model.User;
import com.lnt.sp.model.UserLoginSession;
import com.lnt.sp.annotations.WriteTransaction;
import com.lnt.sp.manager.IRegistrationManager;
import com.lnt.sp.manager.ISessionManager;

@Component
public class AuthenticationHandler implements IAuthenticationHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthenticationHandler.class);
	@Autowired
	private IRegistrationManager regMgr;

	@Autowired
	private ISessionManager sessionMgr;

	@Override
	@Transactional
	public String authenticate(String userName, String password)
			throws ServiceApplicationException {
		logger.info("AuthenticationHandler.authenticate - user : {}", userName);
		User user = regMgr.getUser(userName);
		if (user == null) {
			throw new AuthenticationException(
					"Authentication failed : Unable to find ServiceProvider : " + userName,
					401);
		}

		if (true == (user.getActive())) {
			throw new AuthenticationException(
					"Authentication failed :User marked as deleted  : "
							+ userName, 401);
		}

		String result = validatePassword(password, user);
		if (!"Success".equals(result)) {
			regMgr.updateUser(user);
			throw new AuthenticationException(
					"Please Enter valid login credentials",
					HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
		}
		String token = getToken(user);
		return token;
	}

	@Override
	@WriteTransaction
	public void logout(String token) throws ServiceApplicationException {
		logger.info("AuthenticationHandler.logout, token : {}", token);
		UserLoginSession session = sessionMgr.getUserSession(token);
		if (session == null) {
			throw new ServiceApplicationException(
					HttpStatus.BAD_REQUEST.value(), "Invalid Token");
		}
		sessionMgr.expireSession(session);
	}

	public String validatePassword(String password, User user)
			throws ServiceApplicationException {
		boolean valid = false;
		String result = "Success";
		String userName = user.getUserName();
		valid = user.getPassword().equals(password);
		if (!valid) {
			logger.info("Authentication failed : wrong password for user "
					+ userName);
			result = "Wrong password.";
		}
		return result;
	}

	@WriteTransaction
	private String getToken(User user) {
		return sessionMgr.createSession(user);
	}

}
