package com.lnt.sp.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lnt.core.model.ServiceProvider;
import com.lnt.sp.common.cache.SessionCache;
import com.lnt.sp.common.dto.SessionInfo;
import com.lnt.sp.common.exception.InvalidTokenException;
import com.lnt.sp.common.exception.TokenExpiredException;
import com.lnt.sp.common.util.DateUtil;
import com.lnt.sp.common.util.ESessionStatus;
import com.lnt.sp.common.util.IConstants;
import com.lnt.sp.model.User;

import com.lnt.sp.model.UserLoginSession;
import com.lnt.sp.manager.IRegistrationManager;
import com.lnt.sp.manager.IServiceProviderManager;
import com.lnt.sp.manager.ISessionManager;

@Component
public class SessionHandler implements ISessionHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(SessionHandler.class);

	@Autowired
	ISessionManager sessionMgr;

	@Autowired
	IRegistrationManager regMgr;
	
	@Autowired
	IServiceProviderManager servMgr;

	@Override
	@Transactional
	public UserLoginSession getSession(String sessionId)
			throws TokenExpiredException {
		logger.info("SessionHandler : getSession sessionId : " + sessionId);
		UserLoginSession session = sessionMgr.getUserSession(sessionId);
		if (session != null) {
			Date loginTime = session.getLoginTime();
			if (session.getStatus() == ESessionStatus.EXPIRED.ordinal()
					|| DateUtil.isExpired(loginTime,
							IConstants.TOKEN_EXPIRY_DURATION)) {
				sessionMgr.expireSession(session);
				throw new TokenExpiredException("Token Expired : " + sessionId);
			}
		}

		return session;
	}

	@Override
	@Transactional
	public SessionInfo getSessionInfo(String sessionId)
			throws InvalidTokenException, TokenExpiredException {
		logger.info("SessionHandler : getSessionInfo sessionId : " + sessionId);
		UserLoginSession session = sessionMgr.getUserSession(sessionId);

		if (session == null) {
			logger.info("SessionHandler Token provided is Invalid : "
					+ sessionId);
			throw new InvalidTokenException("Token provided is Invalid : "
					+ sessionId);
		}

		Date loginTime = session.getLoginTime();
		if (session.getStatus() == ESessionStatus.EXPIRED.ordinal()
				|| DateUtil.isExpired(loginTime,
						IConstants.TOKEN_EXPIRY_DURATION)) {
			sessionMgr.expireSession(session);
			throw new TokenExpiredException("Token Expired : " + sessionId);
		}

		SessionInfo sessionInfo = SessionCache.getInstance().get(sessionId);
		if (sessionInfo == null) {
			sessionInfo = new SessionInfo();
			logger.info(
					"SessionHandler Session {} not found in cache. Retrieve from DB",
					sessionId);
			int id = session.getUserId();
			logger.info("SessionHandler user id   {} ", id);
			User user = regMgr.getUserById(id);
			if(user != null)
			{
				sessionInfo.setUser(user);
			}
			else
			{
				ServiceProvider servProv = servMgr.getServiceProviderById(id);
				sessionInfo.setSerProvider(servProv);
			}
			sessionInfo.setLastAccessedTime(System.currentTimeMillis());
			SessionCache.getInstance().put(sessionId, sessionInfo);
		}

		sessionInfo.setSession(session);
		logger.info("SeesionHandler : return sessionInfo : {}", sessionInfo);
		return sessionInfo;

	}

}
