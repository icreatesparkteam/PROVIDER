package com.lnt.sp.handler;

import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lnt.sp.annotations.WriteTransaction;
import com.lnt.core.common.dto.ManufacturerDto;
import com.lnt.core.common.dto.ServiceProviderRegistrationDto;

import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.common.exception.ValidationException;
import com.lnt.core.common.util.IConstants;
import com.lnt.core.common.util.ServiceProviderInRequest;
import com.lnt.core.common.util.Validator;

import com.lnt.sp.manager.IManufacturerManager;

import com.lnt.core.model.Manufacturer;
import com.lnt.core.model.ServiceProvider;


@Component
public class ManufacturerHandler implements IManufacturerHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(ManufacturerHandler.class);

	@Autowired
	private IManufacturerManager manufacturerManager;

	
	@Override
	@Transactional
	public ManufacturerDto getManufacturerByName(String name)
			throws ServiceApplicationException {
		logger.info("ManufacturerHandler :  getManufacturer method ");
		Manufacturer manufacturer = manufacturerManager.getManufacturer(name);
		if (manufacturer == null) {
			logger.error("manufacturer : {} not found", name);
			throw new ServiceApplicationException("manufacturer not found : "
					+ name);
		}
		ManufacturerDto dto = new ManufacturerDto();
		dto.formManufacturer(manufacturer);
		return dto;
	}
	
	@Override
	@Transactional
	public ManufacturerDto getManufacturerById(int id)
			throws ServiceApplicationException {
		logger.info("ManufacturerHandler :  getManufacturer method ");
		Manufacturer manufacturer = manufacturerManager.getManufacturerById(id);
		if (manufacturer == null) {
			logger.error("manufacturer : {} not found", id);
			throw new ServiceApplicationException("manufacturer not found : "
					+ id);
		}
		ManufacturerDto dto = new ManufacturerDto();
		dto.formManufacturer(manufacturer);
		return dto;
	}

	
	@Override
	@Transactional
	public List<ManufacturerDto> getManufacturerList() throws ServiceApplicationException {
		logger.info("ManufacturerHandler :  getManufacturerList by role id method ");
		return manufacturerManager.getAllManufacturer();
	}

}
