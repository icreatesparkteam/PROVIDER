package com.lnt.sp.common.dto;

import com.lnt.sp.model.User;

public class UserContextData {

	private User user;

	public User getServiceProviderInfo() {
		return user;
	}

	public void setServiceProviderInfo(User user) {
		this.user = user;
	}
}
