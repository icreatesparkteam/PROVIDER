package com.lnt.sp.manager;

import java.util.List;

import com.lnt.core.common.dto.GatewayDto;
import com.lnt.core.common.dto.SmartDeviceDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.model.Gateway;
import com.lnt.core.model.SmartDevice;

public interface IGatewayManager {

	public void createGateway(Gateway gateway) throws ServiceApplicationException;

	public Gateway findGatewayByUserID(int userID, int serviceProviderID) throws ServiceApplicationException;
	
	public Gateway findGatewayByGatewayID(String gatewayID, int serviceProviderID) throws ServiceApplicationException;

	public List<Gateway> getGatewayByServiceProviderById(int serviceProviderID);

	public void updateGateway(Gateway gateway) throws ServiceApplicationException;

	public List<GatewayDto> getAllGateway() throws ServiceApplicationException;

	public SmartDevice findDeviceGatewayID(int gatewayID, String deviceID)
			throws ServiceApplicationException;

	public void addDevice(SmartDevice device) throws ServiceApplicationException;

	public List<SmartDeviceDto> getAllDevice( int gatewayID) throws ServiceApplicationException;
	
}
