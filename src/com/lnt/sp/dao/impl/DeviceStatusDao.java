package com.lnt.sp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lnt.core.model.DeviceStatus;

@Repository
@Component
public class DeviceStatusDao extends AbstractAppSPDao<DeviceStatus, Integer> {


	public DeviceStatus getDeviceCommand(String gatewayId, String endPoint, String deviceID) {
		Session session = (Session) entityManagerCore.getDelegate();
		Criteria crit = session.createCriteria(DeviceStatus.class);
		crit.add(Restrictions.eq("gatewayID", gatewayId));
		crit.add(Restrictions.eq("endPoint", endPoint));
		crit.add(Restrictions.eq("deviceID", deviceID));
		
		List<DeviceStatus> status = crit.list();

		if (status != null && !status.isEmpty())
			return status.get(0);
		return null;
	}
}