package com.lnt.sp.handler;

import com.lnt.sp.common.dto.SessionInfo;
import com.lnt.sp.common.exception.InvalidTokenException;
import com.lnt.sp.common.exception.TokenExpiredException;
import com.lnt.sp.model.UserLoginSession;

public interface ISessionHandler {

	public UserLoginSession getSession(String sessionId)
			throws TokenExpiredException;

	public SessionInfo getSessionInfo(String sessionId)
			throws InvalidTokenException, TokenExpiredException;

}
