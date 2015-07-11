package com.lnt.sp.common.dto;

import com.lnt.core.model.ServiceProvider;
import com.lnt.sp.model.User;

public class UserContextData {

	private User user;
	private ServiceProvider servProvider;

	public ServiceProvider getServiceProviderInfo() {
		return servProvider;
	}

	public void setServiceProviderInfo(ServiceProvider servProvider) {
		this.servProvider = servProvider;
	}
	
	public User getUserInfo() {
		return user;
	}

	public void setUserInfo(User user) {
		this.user = user;
	}
}
