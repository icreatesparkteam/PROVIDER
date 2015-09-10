package com.lnt.sp.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lnt.core.common.dto.DeviceCommandDto;
import com.lnt.core.common.dto.DeviceCommandQueueDto;
import com.lnt.core.common.dto.GatewayDto;
import com.lnt.core.common.dto.SmartDeviceDto;
import com.lnt.core.common.exception.ServiceApplicationException;

import com.lnt.sp.common.util.Config;
import com.lnt.sp.dao.impl.DeviceCommandQueueDao;
import com.lnt.sp.dao.impl.GatewayDao;
import com.lnt.sp.dao.impl.SmartDeviceDao;
import com.lnt.sp.zigbee.Control;
import com.lnt.core.model.DeviceCommandQueue;
import com.lnt.core.model.Gateway;
import com.lnt.core.model.ServiceProvider;
import com.lnt.core.model.SmartDevice;

@Component
public class GatewayManager implements IGatewayManager {

	private static final Logger logger = LoggerFactory
			.getLogger(GatewayManager.class);
	@Autowired
	private GatewayDao gatewayDao;
	
	@Autowired
	private SmartDeviceDao deviceDao;
	
	@Autowired
	private DeviceCommandQueueDao queueDao;
	
	@Autowired
	private IServiceProviderManager servMgr;
	
	String serviceProviderName = Config.getInstance().getProperty("service.provider.username");

	@Override
	public void createGateway(Gateway gateway) throws ServiceApplicationException {
		logger.info("GatewayManager : createGateway");
		gatewayDao.create(gateway);
	}

	@Override
	public Gateway findGatewayByUserID(int userID, int serviceProviderID) {
		logger.info("GatewayManager Retrieving gateway information with userID {}",
				userID);
		Gateway gateway = gatewayDao.findByUserID(userID, serviceProviderID);
		return gateway;
	}
	
	@Override
	public Gateway findGatewayById(int id, int serviceProviderID) {
		logger.info("GatewayManager Retrieving gateway information with userID {}",
				id);
		Gateway gateway = gatewayDao.findByID(id, serviceProviderID);
		return gateway;
	}

	@Override
	public List<Gateway> getGatewayByServiceProviderById(int serviceProviderID) {
		logger.info("GatewayManager Retrieving gateway information with serviceProviderID {}", serviceProviderID);
		return gatewayDao.getByServiceProviderById(serviceProviderID);
	}

	@Override
	public void updateGateway(Gateway gateway) throws ServiceApplicationException {
		logger.info("GatewayManager : updateGateway ");
		gatewayDao.update(gateway);
	}

	@Override
	public List<GatewayDto> getAllGateway() throws ServiceApplicationException {
		logger.info("GatewayManager  getAllGateway!!!!!!!!!!!!!!");
		List<GatewayDto> gatewayDtoList = new ArrayList<>();
		List<Gateway> gateway = gatewayDao.getAllGateway();
		if (gateway == null) {
			throw new ServiceApplicationException(
					"Bad request.No gateway available "
							);
		}
		for (Gateway gatewayList : gateway) {
			GatewayDto dto = new GatewayDto();
			dto.setServiceProviderID(gatewayList.getServiceProviderID());
			dto.setUserID(gatewayList.getUserID());
			dto.setStatus(gatewayList.getActive());
			dto.setGatewayID(gatewayList.getGatewayID());
			
			gatewayDtoList.add(dto);

		}
		return gatewayDtoList;
	}

	@Override
	public Gateway findGatewayByGatewayID(String gatewayID, int serviceProviderID) throws ServiceApplicationException {
		logger.info("GatewayManager Retrieving gateway information with Gateway ID {}",
				gatewayID);
		Gateway gateway = gatewayDao.findByGatewayID(gatewayID, serviceProviderID);
		return gateway;
	}
	
	@Override
	public SmartDevice findDeviceGatewayID(int gatewayID, String deviceID, String endpoint) throws ServiceApplicationException {
		logger.info("GatewayManager Retrieving gateway information with Device ID {}",
				deviceID);
		SmartDevice device = deviceDao.findByDeviceEndpointID(gatewayID, deviceID, endpoint);
		return device;
	}
	
	@Override
	public void addDevice(SmartDevice device) throws ServiceApplicationException {
		logger.info("GatewayManager : addDevice");
		deviceDao.create(device);
	}
	
	@Override
	public List<SmartDeviceDto> getAllDevice(int gatewayID) throws ServiceApplicationException {
		logger.info("GatewayManager  getAllDevice!!!!!!!!!!!!!!");
		List<SmartDeviceDto> deviceDtoList = new ArrayList<>();
		List<SmartDevice> device = deviceDao.getAllDevice(gatewayID);
		if (device == null) {
			throw new ServiceApplicationException(
					"Bad request.No device available "
							);
		}
		for (SmartDevice deviceList : device) {
			SmartDeviceDto dto = new SmartDeviceDto();
			dto.setDeviceID(deviceList.getDeviceID());
			dto.setGatewayID(deviceList.getGatewayID());
			dto.setStatus(deviceList.getActive());
			dto.setEndpoint(deviceList.getEndpoint());
			dto.setCluster(deviceList.getCluster());
			dto.setId(deviceList.getId());
			dto.setManufacturerID(deviceList.getManufacturerID());
			deviceDtoList.add(dto);

		}
		return deviceDtoList;
	}

	@Override
	public void executeDeviceCommand(DeviceCommandQueueDto command, Gateway gateway) throws ServiceApplicationException {
		logger.info("GatewayManager  executeDeviceCommand!!!!!!!!!!!!!!");	
		DeviceCommandQueue deviceCommand = new DeviceCommandQueue();
		deviceCommand.setCommand(command.getCommand());
		deviceCommand.setDeviceID(command.getDeviceID());
		deviceCommand.setEndpoint(command.getEndPoint());
		deviceCommand.setGatewayID(command.getGatewayID());
		deviceCommand.setCommandVal(command.getCommandVal());
		deviceCommand.setSynced(0);
		queueDao.create(deviceCommand);
	}
	
	@Override
	public List<DeviceCommandQueueDto> getDeviceCommand(int gatewayId) 
			throws ServiceApplicationException {
		logger.info("GatewayManager  getDeviceCommand!!!!!!!!!!!!!!  "+gatewayId);	
		List<DeviceCommandQueue> deviceCommand = queueDao.getDeviceCommand(gatewayId);
		List<DeviceCommandQueueDto> queuoDto = new ArrayList<>();
		
		if (deviceCommand == null) {
			throw new ServiceApplicationException(
					"Bad request.No command available "
							);
		}
		for (DeviceCommandQueue commandList : deviceCommand) {
			DeviceCommandQueueDto dto = new DeviceCommandQueueDto();
			dto.setCommand(commandList.getCommand());
			dto.setDeviceID(commandList.getDeviceID());
			dto.setEndPoint(commandList.getEndpoint());
			dto.setCommandVal(commandList.getCommandVal());
			queuoDto.add(dto);
			commandList.setSynced(1);
			queueDao.update(commandList);

		}
		
		return queuoDto;
	}

}
