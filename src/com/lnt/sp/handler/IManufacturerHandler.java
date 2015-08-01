package com.lnt.sp.handler;

import java.util.List;

import com.lnt.core.common.dto.ManufacturerDto;
import com.lnt.core.common.dto.ServiceProviderRegistrationDto;
import com.lnt.core.common.exception.ServiceApplicationException;

public interface IManufacturerHandler {

	public ManufacturerDto getManufacturerByName(String name)
			throws ServiceApplicationException;
	
	public ManufacturerDto getManufacturerById(int id)
			throws ServiceApplicationException;


	List<ManufacturerDto> getManufacturerList()
			throws ServiceApplicationException;

}
