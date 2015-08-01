package com.lnt.sp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lnt.core.model.ClusterCommand;

@Repository
@Component
public class ClusterCommandDao extends AbstractAppSPDao<ClusterCommand, Integer> {


	public List<ClusterCommand> getAllClusterCommands() {
		List<ClusterCommand> clusterCommand = findAll();

		if (clusterCommand != null && !clusterCommand.isEmpty())
			return clusterCommand;
		return null;
	}
}