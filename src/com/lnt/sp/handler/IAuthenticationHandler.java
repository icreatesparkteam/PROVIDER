package com.lnt.sp.handler;

import com.lnt.sp.common.exception.ServiceApplicationException;
import com.lnt.sp.model.User;

public interface IAuthenticationHandler {

	public String authenticate(String userName, String password)
			throws ServiceApplicationException;

	public void logout(String token) throws ServiceApplicationException;

	public String validatePassword(String password, User user)
			throws ServiceApplicationException;

}
