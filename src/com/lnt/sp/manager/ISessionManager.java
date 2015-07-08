package com.lnt.sp.manager;

import com.lnt.core.model.ServiceProvider;
import com.lnt.sp.model.User;
import com.lnt.sp.model.UserLoginSession;

public interface ISessionManager {

	public String createSession(User user);

	public UserLoginSession getUserSession(String sessionId);

	void expireSession(UserLoginSession session);

	String createSession(ServiceProvider serviceProvider);
}
