package com.lnt.sp.dao.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lnt.core.model.Manufacturer;
import com.lnt.core.model.ServiceProvider;

@Repository
@Component
public class ManufacturerDao extends AbstractAppSPDao<Manufacturer, Integer> {

	public Manufacturer findByManufacturerName(String name) {
		Criterion criterion = Restrictions.eq("manufacturerName", name);
		List<Manufacturer> manufacturerDaoList = findByCriteria(criterion);

		if (manufacturerDaoList != null && !manufacturerDaoList.isEmpty())
			return manufacturerDaoList.get(0);
		return null;
	}

	public Manufacturer getManufacturerById(int id) {
		Criterion criterion = Restrictions.eq("id", id);
		List<Manufacturer> manufacturerList = findByCriteria(criterion);

		if (manufacturerList != null && !manufacturerList.isEmpty())
			return manufacturerList.get(0);
		return null;
	}
	
	public List<Manufacturer> getManufacturerList() {
		//Criterion criterion = Restrictions.isNotNull("userName");
		List<Manufacturer> manufacturerList = findAll();

		if (manufacturerList != null && !manufacturerList.isEmpty())
			return manufacturerList;
		return null;
	}
}