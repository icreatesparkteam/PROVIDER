package com.lnt.sp.manager;

import java.util.List;

import com.lnt.core.common.dto.DeviceCommandDto;
import com.lnt.core.common.dto.DeviceCommandQueueDto;
import com.lnt.core.common.dto.DeviceStatusDto;
import com.lnt.core.common.dto.GatewayDto;
import com.lnt.core.common.dto.SmartDeviceDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.model.DeviceStatus;
import com.lnt.core.model.Gateway;
import com.lnt.core.model.SmartDevice;

public interface IGatewayManager {

	public void createGateway(Gateway gateway) throws ServiceApplicationException;

	public Gateway findGatewayByUserID(int userID, int serviceProviderID) throws ServiceApplicationException;
	
	public Gateway findGatewayByGatewayID(String gatewayID, int serviceProviderID) throws ServiceApplicationException;

	public List<Gateway> getGatewayByServiceProviderById(int serviceProviderID) throws ServiceApplicationException;

	public void updateGateway(Gateway gateway) throws ServiceApplicationException;

	public List<GatewayDto> getAllGateway() throws ServiceApplicationException;

	public SmartDevice findDeviceGatewayID(int gatewayID, String deviceID, String endpoint)
			throws ServiceApplicationException;

	public void addDevice(SmartDevice device) throws ServiceApplicationException;

	public List<SmartDeviceDto> getAllDevice( int gatewayID) throws ServiceApplicationException;

	public void executeDeviceCommand(DeviceCommandQueueDto command, Gateway gateway) throws ServiceApplicationException;
	
	public List<DeviceCommandQueueDto> getDeviceCommand(int gatewayId) throws ServiceApplicationException;

	public Gateway findGatewayById(int id, int serviceProviderID) throws ServiceApplicationException;
	
	public DeviceStatusDto getDeviceStatus(String gatewayId, String endPoint, String deviceID) throws ServiceApplicationException;
	
	public void updateDeviceStatus(List<DeviceStatusDto> status) throws ServiceApplicationException;;
	
}
