package com.lnt.sp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lnt.core.model.ClusterCommand;
import com.lnt.core.model.DeviceCommandQueue;
import com.lnt.core.model.Gateway;

@Repository
@Component
public class DeviceCommandQueueDao extends AbstractAppSPDao<DeviceCommandQueue, Integer> {


	public List<DeviceCommandQueue> getDeviceCommand(int gatewayId) {
		Session session = (Session) entityManagerCore.getDelegate();
		Criteria crit = session.createCriteria(DeviceCommandQueue.class);
		crit.add(Restrictions.eq("gatewayID", gatewayId));
		crit.add(Restrictions.eq("synced", 0));
		
		List<DeviceCommandQueue> command = crit.list();

		if (command != null && !command.isEmpty())
			return command;
		return null;
	}
}