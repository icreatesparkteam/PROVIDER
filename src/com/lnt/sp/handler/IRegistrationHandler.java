package com.lnt.sp.handler;

import java.util.List;

import com.lnt.core.common.dto.ServiceProviderRegistrationDto;
import com.lnt.sp.common.exception.ServiceApplicationException;
import com.lnt.sp.common.dto.UserRegistrationDto;

public interface IRegistrationHandler {

	public void createUser(UserRegistrationDto register)
			throws ServiceApplicationException;

	public UserRegistrationDto getUser(String userName)
			throws ServiceApplicationException;

	public void updateUser(UserRegistrationDto register)
			throws ServiceApplicationException;

	public void deleteUser(String userName) throws ServiceApplicationException;


	public String changePassword(String password, String newPassword,
			String confirmPassword) throws ServiceApplicationException;

	public String passwordRecovery(String userName, String questionAns)
			throws ServiceApplicationException;

	public String getSecurityQuestions(String userName)
			throws ServiceApplicationException;

	public void setSecurityQuestions(String question, String answer)
			throws ServiceApplicationException;

	List<UserRegistrationDto> getUserList()
			throws ServiceApplicationException;

	String getUserRole(String token) throws ServiceApplicationException;

	List<ServiceProviderRegistrationDto> getServiceProviderList()
			throws ServiceApplicationException;

	

}
