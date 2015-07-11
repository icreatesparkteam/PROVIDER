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
public class SmartDeviceDao extends AbstractAppSPDao<SmartDevice, Integer> {

	public SmartDevice findByDeviceID(int gatewayID, String deviceID) {
		Session session = (Session) entityManagerCore.getDelegate();
		Criteria crit = session.createCriteria(SmartDevice.class);
		crit.add(Restrictions.eq("gatewayID", gatewayID));
		crit.add(Restrictions.eq("deviceID", deviceID));
		List<SmartDevice> deviceList = crit.list();

		if (deviceList != null && !deviceList.isEmpty())
			return deviceList.get(0);
		return null;
	}

	public void addDevice(SmartDevice device) {
		// TODO Auto-generated method stub
		
	}

	public List<SmartDevice> getAllDevice(int gatewayID) {
		Criterion criterion = Restrictions.eq("gatewayID", gatewayID);
		List<SmartDevice> deviceList = findByCriteria(criterion);

		if (deviceList != null && !deviceList.isEmpty())
			return deviceList;
		return null;
	}
}