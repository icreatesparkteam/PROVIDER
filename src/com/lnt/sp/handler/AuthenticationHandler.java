package com.lnt.sp.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

import com.lnt.core.common.util.IConstants;
import com.lnt.core.model.Gateway;
import com.lnt.core.model.ServiceProvider;
import com.lnt.sp.common.exception.AuthenticationException;
import com.lnt.sp.common.exception.ServiceApplicationException;

import com.lnt.sp.model.User;
import com.lnt.sp.common.util.Config;
import com.lnt.sp.model.UserLoginSession;
import com.lnt.sp.annotations.WriteTransaction;
import com.lnt.sp.manager.IGatewayManager;
import com.lnt.sp.manager.IRegistrationManager;
import com.lnt.sp.manager.IServiceProviderManager;
import com.lnt.sp.manager.ISessionManager;


@Component
public class AuthenticationHandler implements IAuthenticationHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthenticationHandler.class);
	@Autowired
	private IRegistrationManager regMgr;
	
	@Autowired
	private IServiceProviderManager servMgr;

	@Autowired
	private ISessionManager sessionMgr;
	
	@Autowired
	private IGatewayManager gatewayMgr;
	
	String serviceProviderName = Config.getInstance().getProperty("service.provider.username");

	@Override
	@Transactional
	public String authenticate(String userName, String password)
			throws ServiceApplicationException, com.lnt.core.common.exception.ServiceApplicationException {
		logger.info("AuthenticationHandler.authenticate - user : {}", userName);
		User user = regMgr.getUser(userName);
		String token = "";
		if (user != null)
		{
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
			token = getToken(user);
		}
		else if(user == null)
		{
			ServiceProvider serviceProvider = servMgr.getServiceProvider(userName);
			if (serviceProvider == null) {
				throw new AuthenticationException(
						"Authentication failed : Unable to find ServiceProvider : " + userName,
						401);
			}

			if ((serviceProvider.getActive() == 0)) {
				throw new AuthenticationException(
						"Authentication failed :ServiceProvider marked as deleted  : "
								+ userName, 401);
			}

			if (serviceProvider.getAttemptsLeft() == 0) {
				Date lastUpdated = serviceProvider.getUpdated();
				Date currentTime = new Date();
				long diff = currentTime.getTime() - lastUpdated.getTime();
				long diffMinutes = diff / (60 * 1000) % 60;
				
				if (diffMinutes != IConstants.SESSION_INACTIVE_TIME) {
					throw new AuthenticationException(
							"You have crossed your maximum no. of attempts for login. Please try after "
									+ (IConstants.SESSION_INACTIVE_TIME - diffMinutes)
									+ " minute(s)",
							HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
				} else {
					serviceProvider.setAttemptsLeft(IConstants.LOGIN_ATTEMPTS);
					servMgr.updateServiceProvider(serviceProvider);
				}
			}

			String result = validatePassword(password, serviceProvider);
			if (!"Success".equals(result)) {
				serviceProvider.setAttemptsLeft(serviceProvider.getAttemptsLeft() - 1);
				servMgr.updateServiceProvider(serviceProvider);
				throw new AuthenticationException(
						"Please Enter valid login credentials",
						HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
			}
			token = getToken(serviceProvider);
		}
		else
		{
				throw new AuthenticationException(
						"Authentication failed : Unable to find any user : " + userName,
						401);
		}
		return token;
	}
	
	@Override
	@Transactional
	public String authenticate(String deviceID)
			throws ServiceApplicationException, com.lnt.core.common.exception.ServiceApplicationException {
		logger.info("AuthenticationHandler.authenticate - deviceID : {}", deviceID);
		System.out.println("AuthenticationHandler.authenticate - deviceID : {}" +deviceID);
//		User user = regMgr.getUser(userName);
		String token = "";
		System.out.println("AuthenticationHandler.authenticate - serviceProviderName : {}" +serviceProviderName);
		if(serviceProviderName == null)
			serviceProviderName = "servpro1";
		ServiceProvider servProvider = servMgr.getServiceProvider(serviceProviderName);
		System.out.println("Got service provider: {}" +servProvider.getId());
		Gateway gateway = gatewayMgr.findGatewayByGatewayID(deviceID, servProvider.getId());
		if (gateway != null)
		{
			if ("1".equals(gateway.getActive())) {
				throw new AuthenticationException(
						"Authentication failed :Gateway marked as deleted  : "
								+ deviceID, 401);
			}
	
			token = getToken(gateway);
			
		}
		else
		{
				throw new AuthenticationException(
						"Authentication failed : Unable to find Gateway : " + deviceID,
						401);
		}
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
	
	public String validatePassword(String password, ServiceProvider user)
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
	private String getToken(ServiceProvider serviceProvider) {
		return sessionMgr.createSession(serviceProvider);
	}
	
	@WriteTransaction
	private String getToken(Gateway gateway) {
		return sessionMgr.createSession(gateway);
	}

}
