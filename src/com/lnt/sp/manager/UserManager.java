package com.lnt.sp.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lnt.sp.model.User;

import com.lnt.sp.common.dto.UserRegistrationDto;
import com.lnt.sp.common.exception.ServiceApplicationException;

import com.lnt.sp.dao.impl.UserDao;

@Component
public class UserManager implements IRegistrationManager {

	private static final Logger logger = LoggerFactory
			.getLogger(UserManager.class);
	@Autowired
	private UserDao userDao;

	@Override
	public void createUser(User user) throws ServiceApplicationException {
		logger.info("UserManager : serviceProvider");
		userDao.create(user);
	}

	@Override
	public User getUser(String username) {
		logger.info("UserManager Retrieving user information with userName {}",
				username);
		User user = userDao.findByUserName(username);
		return user;
	}

	@Override
	public User getUserById(int id) {
		logger.info("UserManager Retrieving user information with id {}", id);
		return userDao.findById(id);
	}

	@Override
	public void updateUser(User user) throws ServiceApplicationException {
		logger.info("UserManager : updateUser ");
		userDao.update(user);
	}

	
	@Override
	public List<UserRegistrationDto> getUserlist(int id) throws ServiceApplicationException {
		logger.info("UserManager  getUserlist for role - id {}", id);
		List<UserRegistrationDto> userDtoList = new ArrayList<>();
		List<User> userListInfoList = userDao.getUserList();
		if (userListInfoList == null) {
			throw new ServiceApplicationException(
					"Bad request.No serviceProvider available with the given role id "
							+ id);
		}
		for (User user : userListInfoList) {
			UserRegistrationDto dto = new UserRegistrationDto();
			dto.setUserName(user.getUserName());
		
			userDtoList.add(dto);

		}
		return userDtoList;
	}

	@Override
	public List<UserRegistrationDto> getAlluserlist() throws ServiceApplicationException {
		logger.info("UserManager  getAllServiceProviderlist!!!!!!!!!!!!!!");
		List<UserRegistrationDto> userDtoList = new ArrayList<>();
		List<User> userRegistrationDtoList = userDao.getUserList();
		if (userRegistrationDtoList == null) {
			throw new ServiceApplicationException(
					"Bad request.No ServiceProvider available "
							);
		}
		for (User user : userRegistrationDtoList) {
			UserRegistrationDto dto = new UserRegistrationDto();
			dto.setUserName(user.getUserName());
			dto.setState(user.getState());
			
			userDtoList.add(dto);

		}
		return userDtoList;
	}
	

}
