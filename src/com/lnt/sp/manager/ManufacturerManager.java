package com.lnt.sp.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lnt.core.common.dto.ManufacturerDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.sp.dao.impl.ManufacturerDao;

import com.lnt.core.model.Manufacturer;

@Component
public class ManufacturerManager implements IManufacturerManager {

	private static final Logger logger = LoggerFactory
			.getLogger(ManufacturerManager.class);
	@Autowired
	private ManufacturerDao mDao;

	@Override
	public Manufacturer getManufacturer(String name) {
		logger.info("ManufacturerManager Retrieving Manufacturer information with Name {}",
				name);
		Manufacturer manufacturer = mDao.findByManufacturerName(name);
		return manufacturer;
	}

	@Override
	public Manufacturer getManufacturerById(int id) {
		logger.info("ManufacturerManager Retrieving Manufacturer information with id {}", id);
		return mDao.findById(id);
	}

	@Override
	public List<ManufacturerDto> getAllManufacturer() throws ServiceApplicationException {
		logger.info("ManufacturerManager  getAllManufacturer!!!!!!!!!!!!!!");
		List<ManufacturerDto> manufacturerDtoList = new ArrayList<>();
		List<Manufacturer> manufacturerList = mDao.getManufacturerList();
		if (manufacturerList == null) {
			throw new ServiceApplicationException(
					"Bad request.No Manufacturer available "
							);
		}
		for (Manufacturer mDto : manufacturerList) {
			ManufacturerDto dto = new ManufacturerDto();
			dto.setManufacturerName(mDto.getManufacturerName());
			manufacturerDtoList.add(dto);

		}
		return manufacturerDtoList;
	}
	
}
