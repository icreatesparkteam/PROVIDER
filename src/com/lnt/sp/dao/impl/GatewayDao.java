package com.lnt.sp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lnt.core.model.Gateway;
import com.lnt.core.model.SmartDevice;

@Repository
@Component
public class GatewayDao extends AbstractAppSPDao<Gateway, Integer> {

	public Gateway findByUserID(int userID, int serviceProviderID) {
		Session session = (Session) entityManagerCore.getDelegate();
		Criteria crit = session.createCriteria(Gateway.class);
		crit.add(Restrictions.eq("userID", userID));
		crit.add(Restrictions.eq("serviceProviderID", serviceProviderID));
		List<Gateway> deviceList = crit.list();

		if (deviceList != null && !deviceList.isEmpty())
			return deviceList.get(0);
		return null;
	}
	
	public Gateway findByGatewayID(String gatewayID, int serviceProviderID) {
		Session session = (Session) entityManagerCore.getDelegate();
		Criteria crit = session.createCriteria(Gateway.class);
		crit.add(Restrictions.eq("gatewayID", gatewayID));
		crit.add(Restrictions.eq("serviceProviderID", serviceProviderID));
		List<Gateway> deviceList = crit.list();

		if (deviceList != null && !deviceList.isEmpty())
			return deviceList.get(0);
		return null;
	}

	public List<Gateway> getByServiceProviderById(int serviceProviderID) {
		Criterion criterion = Restrictions.eq("serviceProviderID", serviceProviderID);
		List<Gateway> gatewayList = findByCriteria(criterion);

		if (gatewayList != null && !gatewayList.isEmpty())
			return gatewayList;
		return null;
	}
	
	public List<Gateway> getAllGateway() {
		//Criterion criterion = Restrictions.isNotNull("userName");
		List<Gateway> gatewayList = findAll();

		if (gatewayList != null && !gatewayList.isEmpty())
			return gatewayList;
		return null;
	}

}