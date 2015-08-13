package com.lnt.sp.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lnt.sp.annotations.WriteTransaction;
import com.lnt.sp.common.util.Config;
import com.lnt.core.common.dto.DeviceCommandDto;
import com.lnt.core.common.dto.GatewayDto;
import com.lnt.core.common.dto.SmartDeviceDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.common.exception.ValidationException;

import com.lnt.sp.manager.IGatewayManager;
import com.lnt.sp.manager.IServiceProviderManager;
import com.lnt.sp.manager.ISessionManager;

import com.lnt.core.model.Gateway;
import com.lnt.core.model.ServiceProvider;
import com.lnt.core.model.SmartDevice;
import com.lnt.sp.model.UserLoginSession;


@Component
public class GatewayHandler implements IGatewayHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(GatewayHandler.class);

	@Autowired
	private IGatewayManager gatewayMgr;
	
	@Autowired
	private IServiceProviderManager servMgr;
	
	@Autowired
	private ISessionManager sessionMgr;
	
	String serviceProviderName = Config.getInstance().getProperty("service.provider.username");

	@Override
	@Transactional
	@WriteTransaction
	public void createGateway(GatewayDto gatewayDto, String sessionID)
			throws ServiceApplicationException {
		logger.info("createGateway :  register method ");
		if (gatewayDto == null) {
			throw new ServiceApplicationException("Invalid gateway Details ");
		}

		if (gatewayDto.getGatewayID() == null)
			throw new ValidationException("Gateway ID is mandatory");

		ServiceProvider servProvider = servMgr.getServiceProvider(serviceProviderName);
		
		UserLoginSession session = sessionMgr.getUserSession(sessionID);
		
		
		if (gatewayMgr.findGatewayByGatewayID(gatewayDto.getGatewayID(), servProvider.getId())
				!= null) {
			throw new ValidationException(
					"Duplicate Gateway - Gateway already exists with Service provider: "
							+ gatewayDto.getGatewayID());
		}
		
		
		Gateway gateway = new Gateway();
		gateway.setGatewayID(gatewayDto.getGatewayID());
		gateway.setServiceProviderID(servProvider.getId());
		gateway.setUserID(session.getUserId());
		gateway.setActive("1");
		gatewayMgr.createGateway(gateway);
		
	}

	@Override
	@Transactional
	public GatewayDto getGatewayByUserID(String sessionID)
			throws ServiceApplicationException {
		logger.info("GatewayHandler :  getGatewayByUserID method ");
		ServiceProvider servProvider = servMgr.getServiceProvider(serviceProviderName);
		UserLoginSession session = sessionMgr.getUserSession(sessionID);
		
		Gateway gateway = gatewayMgr.findGatewayByUserID(session.getUserId(), servProvider.getId());
		if (gateway == null) {
			logger.error("gateway : {} not found", session.getUserId());
			throw new ServiceApplicationException("Gateway not found : "
					+ session.getUserId());
		}
		GatewayDto gatewayDto = new GatewayDto();
		gatewayDto.formGateway(gateway);
		return gatewayDto;
	}

	@Override
	@WriteTransaction
	public void updateGateway(GatewayDto gatewayDto, String sessionID)
			throws ServiceApplicationException {
		logger.info("GatewayHandler :  updateGateway method ");
		if (gatewayDto == null) {
			throw new ServiceApplicationException("Invalid gateway :");
		}
		ServiceProvider servProvider = servMgr.getServiceProvider(serviceProviderName);
		UserLoginSession session = sessionMgr.getUserSession(sessionID);
		Gateway gateway = gatewayMgr.findGatewayByUserID(session.getUserId(), servProvider.getId());
		if (gateway == null) {
			throw new ServiceApplicationException(
					"Gateway is not available with this username : "
							+ gatewayDto.getUserID());
		}
		gatewayDto.setServiceProviderID(servProvider.getId());
		gatewayDto.setUserID(session.getUserId());
		gatewayDto.toGateway(gateway);
		gatewayMgr.updateGateway(gateway);

	}
	
	@Override
	@WriteTransaction
	public void updateGatewayIP(String ipAddress, String sessionID)
			throws ServiceApplicationException {
		logger.info("GatewayHandler :  updateGateway method ");
		if (ipAddress == null) {
			throw new ServiceApplicationException("Invalid IP Address :");
		}
		ServiceProvider servProvider = servMgr.getServiceProvider(serviceProviderName);
		UserLoginSession session = sessionMgr.getUserSession(sessionID);
		Gateway gateway = gatewayMgr.findGatewayById(session.getUserId(), servProvider.getId());
		if (gateway == null) {
			throw new ServiceApplicationException(
					"Gateway is not available with this username");
		}
		gateway.setIPAddress(ipAddress);
		gatewayMgr.updateGateway(gateway);

	}

	@Override
	@WriteTransaction
	public void deleteGateway(String gatewayID, String sessionID) throws ServiceApplicationException {
		logger.info("GatewayHandler :  deleteGateway method ");
		
		ServiceProvider servProvider = servMgr.getServiceProvider(serviceProviderName);
		UserLoginSession session = sessionMgr.getUserSession(sessionID);
		
		Gateway gateway = gatewayMgr.findGatewayByUserID(session.getUserId(), servProvider.getId());
		if (gateway == null) {
			throw new ServiceApplicationException("Invalid Gateway :");
		}
		gateway.setActive("False");
		gatewayMgr.updateGateway(gateway);
	}

	@Override
	@Transactional
	public List<GatewayDto> getGatewayList() throws ServiceApplicationException {
		logger.info("GatewayHandler :  getGatewayList method ");
		return gatewayMgr.getAllGateway();
	}
	
	@Override
	@WriteTransaction
	public void addSmartDevice(SmartDeviceDto smartDeviceDto, String gatewayID) throws ServiceApplicationException{
		logger.info("createGateway :  addSmartDevice method ");
		if (smartDeviceDto == null) {
			throw new ServiceApplicationException("Invalid device Details ");
		}

		if (smartDeviceDto.getDeviceID() == null)
			throw new ValidationException("Device ID is mandatory");
		
		if (gatewayID == null)
			throw new ValidationException("Gateway ID is mandatory");
		
		ServiceProvider servProvider = servMgr.getServiceProvider(serviceProviderName);
		Gateway gateway = gatewayMgr.findGatewayByGatewayID(gatewayID, servProvider.getId());
		
		if(gateway == null)
		{
			throw new ValidationException(
					"Gateway doesn't exist "
							+ gatewayID);
		}

		if (gatewayMgr.findDeviceGatewayID(gateway.getId(), smartDeviceDto.getDeviceID())
				!= null) {
			throw new ValidationException(
					"Duplicate device - device already exists with Gateway: "
							+ smartDeviceDto.getDeviceID());
		}
		
		SmartDevice device = new SmartDevice();
		device.setGatewayID(gateway.getId());
		device.setDeviceID(smartDeviceDto.getDeviceID());
		device.setActive("True");
		device.setEndpoint(smartDeviceDto.getEndpoint());
		device.setCluster(smartDeviceDto.getCluster());
		device.setManufacturerID(smartDeviceDto.getManufacturerID());
		gatewayMgr.addDevice(device);
	}
	
	@Override
	@Transactional
	public List<SmartDeviceDto> getDeviceList(String gatewayID) throws ServiceApplicationException {
		logger.info("GatewayHandler :  getDeviceList method ");
		if(serviceProviderName == null)
			serviceProviderName = "servpro1";
		ServiceProvider servProvider = servMgr.getServiceProvider(serviceProviderName);
		Gateway gateway = gatewayMgr.findGatewayByGatewayID(gatewayID, servProvider.getId());
		return gatewayMgr.getAllDevice(gateway.getId());
	}

	@Override
	@Transactional
	public void deviceCommand(DeviceCommandDto command)
			throws ServiceApplicationException {
		logger.info("GatewayHandler :  deviceCommand method ");
		if(serviceProviderName == null)
			serviceProviderName = "servpro1";
		ServiceProvider servProvider = servMgr.getServiceProvider(serviceProviderName);
		logger.info("got servProvider :  deviceCommand method : "+servProvider.getId());
		Gateway gateway = gatewayMgr.findGatewayByGatewayID(command.getGatewayID(), servProvider.getId());
		gatewayMgr.executeDeviceCommand(command, gateway);
		
	}

}
