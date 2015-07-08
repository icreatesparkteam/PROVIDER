package com.lnt.sp.dao.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lnt.core.model.ServiceProvider;

@Repository
@Component
public class ServiceProviderDao extends AbstractAppSPDao<ServiceProvider, Integer> {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ServiceProviderDao.class);

	public ServiceProvider findByUserName(String userName) {
		Criterion criterion = Restrictions.eq("userName", userName);
		List<ServiceProvider> serviceProviderDaoList = findByCriteria(criterion);

		if (serviceProviderDaoList != null && !serviceProviderDaoList.isEmpty())
			return serviceProviderDaoList.get(0);
		return null;
	}

	public ServiceProvider getServiceProviderById(int userId) {
		Criterion criterion = Restrictions.eq("id", userId);
		List<ServiceProvider> serviceProviderList = findByCriteria(criterion);

		if (serviceProviderList != null && !serviceProviderList.isEmpty())
			return serviceProviderList.get(0);
		return null;
	}
	
	public List<ServiceProvider> getServiceProviderList() {
		//Criterion criterion = Restrictions.isNotNull("userName");
		List<ServiceProvider> serviceProviderList = findAll();

		if (serviceProviderList != null && !serviceProviderList.isEmpty())
			return serviceProviderList;
		return null;
	}
}