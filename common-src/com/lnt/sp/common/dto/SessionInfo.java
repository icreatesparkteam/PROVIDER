package com.lnt.sp.common.dto;

import com.lnt.sp.model.User;
import com.lnt.sp.model.UserLoginSession;

public class SessionInfo {

	private UserLoginSession session;

	private User user;

	private long lastAccessedTime;

	public UserLoginSession getSession() {
		return session;
	}

	public void setSession(UserLoginSession session) {
		this.session = session;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setLastAccessedTime(long lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}


}
