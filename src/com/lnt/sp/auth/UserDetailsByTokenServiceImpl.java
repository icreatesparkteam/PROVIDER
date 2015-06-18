package com.lnt.sp.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.lnt.sp.common.auth.AuthUser;
import com.lnt.sp.common.dto.SessionInfo;
import com.lnt.sp.common.dto.UserContextData;
import com.lnt.sp.common.util.UserInRequest;
import com.lnt.sp.model.User;
import com.lnt.sp.handler.ISessionHandler;

@Component
public class UserDetailsByTokenServiceImpl implements
		AuthenticationUserDetailsService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserDetailsByTokenServiceImpl.class);

	@Autowired
	ISessionHandler sessionHandler;

	public UserDetails loadUserDetails(Authentication authentication)
			throws UsernameNotFoundException {
		String token = authentication.getName();
		logger.info("UserDetailsByTokenServiceImpl loadUserDetails ...... {}",
				token);

		SessionInfo sessionInfo = sessionHandler.getSessionInfo(token);
		User user = sessionInfo.getUser();
		AuthUser authUser = new AuthUser(user);
		
		UserContextData userContext = new UserContextData();
		userContext.setServiceProviderInfo(user);
		UserInRequest.getInstance().setUserContext(userContext);
		
		return authUser;
	}

}
