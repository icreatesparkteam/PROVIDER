package com.lnt.sp.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lnt.sp.common.dto.UserContextData;

public class UserInRequest {
	private static Logger logger = LoggerFactory.getLogger(UserInRequest.class);

	private static UserInRequest instance;

	private ThreadLocal<UserContextData> serviceProvider = new ThreadLocal<UserContextData>();

	private UserInRequest() {

	}

	public static synchronized UserInRequest getInstance() {
		if (instance == null) {
			logger.info("Instantiating the threadlocal container class");
			instance = new UserInRequest();
		}
		return instance;
	}

	public void setUserContext(UserContextData user) {
		logger.info(
				"Setting the serviceProvider --- [{}] -- information of logged in user to the thread local context",
				user.getUserInfo().getId());
		this.serviceProvider.set(user);
	}
	
	public void setServiceProviderContext(UserContextData user) {
		logger.info(
				"Setting the serviceProvider --- [{}] -- information of logged in user to the thread local context",
				user.getServiceProviderInfo().getId());
		this.serviceProvider.set(user);
	}

	public UserContextData getUserContext() {
		return this.serviceProvider.get();
	}

	public void clearContext() {
		if (this.serviceProvider != null) {
			logger.info("Clearing user context");
			this.serviceProvider.remove();
		}
	}

}
