package com.lnt.sp.dao.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lnt.sp.model.User;

@Repository
@Component
public class UserDao extends AbstractAppDao<User, Integer> {

	public User findByUserName(String userName) {
		Criterion criterion = Restrictions.eq("userName", userName);
		List<User> userDaoList = findByCriteria(criterion);

		if (userDaoList != null && !userDaoList.isEmpty())
			return userDaoList.get(0);
		return null;
	}

	public User getUserById(int userId) {
		Criterion criterion = Restrictions.eq("id", userId);
		List<User> userList = findByCriteria(criterion);

		if (userList != null && !userList.isEmpty())
			return userList.get(0);
		return null;
	}
	
	public List<User> getUserList() {
		//Criterion criterion = Restrictions.isNotNull("userName");
		List<User> userList = findAll();

		if (userList != null && !userList.isEmpty())
			return userList;
		return null;
	}
}