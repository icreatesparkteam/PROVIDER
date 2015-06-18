package com.lnt.sp.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lnt.core.common.dto.GatewayDto;
import com.lnt.core.common.exception.ServiceApplicationException;

import com.lnt.sp.dao.impl.GatewayDao;
import com.lnt.core.model.Gateway;

@Component
public class GatewayManager implements IGatewayManager {

	private static final Logger logger = LoggerFactory
			.getLogger(GatewayManager.class);
	@Autowired
	private GatewayDao gatewayDao;

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

}
