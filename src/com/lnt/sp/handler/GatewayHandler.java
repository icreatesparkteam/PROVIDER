package com.lnt.sp.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lnt.sp.annotations.WriteTransaction;
import com.lnt.core.common.dto.GatewayDto;

import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.common.exception.ValidationException;

import com.lnt.sp.manager.IGatewayManager;

import com.lnt.core.model.Gateway;


@Component
public class GatewayHandler implements IGatewayHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(GatewayHandler.class);

	@Autowired
	private IGatewayManager gatewayMgr;

	@Override
	@WriteTransaction
	public void createGateway(GatewayDto gatewayDto)
			throws ServiceApplicationException {
		logger.info("createGateway :  register method ");
		if (gatewayDto == null) {
			throw new ServiceApplicationException("Invalid gateway Details ");
		}

		if (gatewayDto.getGatewayID() == null)
			throw new ValidationException("Gateway ID is mandatory");

		if (gatewayDto.getUserID() < 0)
			throw new ValidationException("user ID is mandatory");
		
		if (gatewayDto.getServiceProviderID() < 0)
			throw new ValidationException("Service Provider ID is mandatory");

		if (gatewayMgr.findGatewayByGatewayID(gatewayDto.getGatewayID(), gatewayDto.getServiceProviderID())
				!= null) {
			throw new ValidationException(
					"Duplicate Gateway - Gateway already exists with Service provider: "
							+ gatewayDto.getGatewayID());
		}
		Gateway gateway = new Gateway();
		gateway.setGatewayID(gatewayDto.getGatewayID());
		gateway.setServiceProviderID(gatewayDto.getServiceProviderID());
		gateway.setUserID(gatewayDto.getUserID());
		gateway.setActive("True");
		gatewayMgr.createGateway(gateway);
	}

	@Override
	@Transactional
	public GatewayDto getGatewayByUserID(int userID, int serviceProviderID)
			throws ServiceApplicationException {
		logger.info("GatewayHandler :  getGatewayByUserID method ");
		Gateway gateway = gatewayMgr.findGatewayByUserID(userID, serviceProviderID);
		if (gateway == null) {
			logger.error("gateway : {} not found", userID);
			throw new ServiceApplicationException("Gateway not found : "
					+ userID);
		}
		GatewayDto gatewayDto = new GatewayDto();
		gatewayDto.formGateway(gateway);
		return gatewayDto;
	}

	@Override
	@WriteTransaction
	public void updateGateway(GatewayDto gatewayDto)
			throws ServiceApplicationException {
		logger.info("GatewayHandler :  updateGateway method ");
		if (gatewayDto == null) {
			throw new ServiceApplicationException("Invalid gateway :");
		}
		Gateway gateway = gatewayMgr.findGatewayByUserID(gatewayDto.getUserID(), gatewayDto.getServiceProviderID());
		if (gateway == null) {
			throw new ServiceApplicationException(
					"Gateway is not available with this username : "
							+ gatewayDto.getUserID());
		}
		gatewayDto.toServiceProvider(gateway);
		gatewayMgr.updateGateway(gateway);

	}

	@Override
	@WriteTransaction
	public void deleteGateway(String gatewayID, int userID, int serviceProviderID) throws ServiceApplicationException {
		logger.info("GatewayHandler :  deleteGateway method ");
		Gateway gateway = gatewayMgr.findGatewayByUserID(userID, serviceProviderID);
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

}
