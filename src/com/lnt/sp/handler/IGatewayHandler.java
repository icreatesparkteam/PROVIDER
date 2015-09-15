package com.lnt.sp.handler;

import java.util.List;

import com.lnt.core.common.dto.DeviceCommandQueueDto;
import com.lnt.core.common.dto.DeviceStatusDto;
import com.lnt.core.common.dto.GatewayDto;
import com.lnt.core.common.dto.SmartDeviceDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.common.dto.DeviceCommandDto;
import com.lnt.core.model.DeviceStatus;

public interface IGatewayHandler {

	public void createGateway(GatewayDto gateway, String sessionID)
			throws ServiceApplicationException;

	public GatewayDto getGatewayByUserID(String sessionID)
			throws ServiceApplicationException;

	public void updateGateway(GatewayDto gateway, String sessionID)
			throws ServiceApplicationException;

	public void deleteGateway(String gatewayID, String sessionID) throws ServiceApplicationException;

	List<GatewayDto> getGatewayList()
			throws ServiceApplicationException;

	public void addSmartDevice(SmartDeviceDto smartDevice, String gatewayID)throws ServiceApplicationException;

	public List<SmartDeviceDto> getDeviceList(String gatewayID) throws ServiceApplicationException;

	void updateGatewayIP(String ipAddress, String sessionID)
			throws ServiceApplicationException;

	public void deviceCommand(DeviceCommandQueueDto command) throws ServiceApplicationException;

	public List<DeviceCommandQueueDto> getQueuedDeviceCommand(String sessionID) throws ServiceApplicationException;

	public void setDeviceStatus(List<DeviceStatusDto> status) throws ServiceApplicationException;
	
	public DeviceStatusDto getDeviceStatus(String gatewayId, String endPoint, String deviceID) throws ServiceApplicationException;

}
