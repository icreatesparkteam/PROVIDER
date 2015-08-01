package com.lnt.sp.manager;

import java.util.List;

import com.lnt.core.common.dto.ManufacturerDto;
import com.lnt.core.common.dto.ServiceProviderRegistrationDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.model.Manufacturer;
import com.lnt.core.model.Permission;
import com.lnt.core.model.ServiceProvider;

public interface IManufacturerManager {

	public Manufacturer getManufacturer(String name) throws ServiceApplicationException;

	public Manufacturer getManufacturerById(int id);

	public List<ManufacturerDto> getAllManufacturer() throws ServiceApplicationException;
	
}
