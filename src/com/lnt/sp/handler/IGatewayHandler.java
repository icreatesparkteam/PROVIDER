package com.lnt.sp.handler;

import java.util.List;

import com.lnt.core.common.dto.GatewayDto;
import com.lnt.core.common.dto.SmartDeviceDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.common.dto.DeviceCommandDto;

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

	public void deviceCommand(DeviceCommandDto command) throws ServiceApplicationException;

	

}
