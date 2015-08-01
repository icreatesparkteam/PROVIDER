package com.lnt.sp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lnt.core.model.Cluster;

@Repository
@Component
public class ClusterDao extends AbstractAppSPDao<Cluster, Integer> {

	public Cluster findByClusterID(String clusterID) {
		Session session = (Session) entityManagerCore.getDelegate();
		Criteria crit = session.createCriteria(Cluster.class);
		crit.add(Restrictions.eq("clusterID", clusterID));
		List<Cluster> cluster = crit.list();

		if (cluster != null && !cluster.isEmpty())
			return cluster.get(0);
		return null;
	}

	public List<Cluster> getAllCluster() {
		List<Cluster> cluster = findAll();

		if (cluster != null && !cluster.isEmpty())
			return cluster;
		return null;
	}
}