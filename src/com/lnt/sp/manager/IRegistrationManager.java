package com.lnt.sp.manager;

import java.util.List;

import com.lnt.sp.common.dto.UserRegistrationDto;
import com.lnt.sp.common.exception.ServiceApplicationException;
import com.lnt.sp.model.User;

public interface IRegistrationManager {

	public void createUser(User user) throws ServiceApplicationException;

	public User getUser(String username) throws ServiceApplicationException;

	public User getUserById(int id);

	public void updateUser(User user) throws ServiceApplicationException;

	public List<UserRegistrationDto> getUserlist(int id) throws ServiceApplicationException;
	
	public List<UserRegistrationDto> getAlluserlist() throws ServiceApplicationException;
	

}
