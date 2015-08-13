package com.lnt.sp.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lnt.core.common.dto.ServiceProviderRegistrationDto;
import com.lnt.core.model.ServiceProvider;
import com.lnt.sp.model.User;

import com.lnt.sp.common.dto.UserRegistrationDto;
import com.lnt.sp.common.exception.ServiceApplicationException;

import com.lnt.sp.dao.impl.ServiceProviderDao;
import com.lnt.sp.dao.impl.UserDao;

@Component
public class UserManager implements IRegistrationManager {

	private static final Logger logger = LoggerFactory
			.getLogger(UserManager.class);
	@Autowired
	private UserDao userDao;

	@Autowired
	private ServiceProviderDao servProDao;
	
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
		logger.info("UserManager  getAllUserProviderlist!!!!!!!!!!!!!!");
		List<UserRegistrationDto> userDtoList = new ArrayList<>();
		List<User> userRegistrationDtoList = userDao.getUserList();
		if (userRegistrationDtoList == null) {
			throw new ServiceApplicationException(
					"Bad request.No user available "
							);
		}
		for (User user : userRegistrationDtoList) {
			UserRegistrationDto dto = new UserRegistrationDto();
			dto.setUserName(user.getUserName());
			dto.setState(user.getState());
			dto.setAddress(user.getAddress());
			dto.setCity(user.getCity());
			dto.setCountry(user.getCountry());
			dto.setId(user.getId());
			dto.setPhoneNum(user.getPhoneNum());
			dto.setPrimaryEmailId(user.getPrimaryEmailId());
			dto.setsAnswer(user.getsAnswer());
			dto.setsQuestion(user.getsQuestion());
			dto.setState(user.getState());
			dto.setZipCode(user.getZipCode());
			dto.setActive(user.getActive());
			userDtoList.add(dto);

		}
		return userDtoList;
	}
	
	@Override
	public List<ServiceProviderRegistrationDto> getAllServiceProvider() throws ServiceApplicationException {
		logger.info("UserManager  getAllServiceProviderlist!!!!!!!!!!!!!!");
		List<ServiceProviderRegistrationDto> servDtoList = new ArrayList<>();
		List<ServiceProvider> servPro = servProDao.getServiceProviderList();
		if (servPro == null) {
			throw new ServiceApplicationException(
					"Bad request.No ServiceProvider available "
							);
		}
		for (ServiceProvider serv : servPro) {
			ServiceProviderRegistrationDto dto = new ServiceProviderRegistrationDto();
			dto.setUserName(serv.getUserName());
			dto.setState(serv.getState());
			dto.setAddress(serv.getAddress());
			dto.setCity(serv.getCity());
			dto.setCountry(serv.getCountry());
			dto.setPassword(serv.getPassword());
			dto.setPrimaryEmailId(serv.getPrimaryEmailId());
			dto.setsAnswer(serv.getsAnswer());
			dto.setsQuestion(serv.getsQuestion());
			dto.setState(serv.getState());
			dto.setId(serv.getId());
			servDtoList.add(dto);

		}
		return servDtoList;
	}


}
