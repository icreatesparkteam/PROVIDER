package com.lnt.sp.manager;

import java.util.List;

import com.lnt.core.common.dto.GatewayDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.model.Gateway;

public interface IGatewayManager {

	public void createGateway(Gateway gateway) throws ServiceApplicationException;

	public Gateway findGatewayByUserID(int userID, int serviceProviderID) throws ServiceApplicationException;
	
	public Gateway findGatewayByGatewayID(String gatewayID, int serviceProviderID) throws ServiceApplicationException;

	public List<Gateway> getGatewayByServiceProviderById(int serviceProviderID);

	public void updateGateway(Gateway gateway) throws ServiceApplicationException;

	public List<GatewayDto> getAllGateway() throws ServiceApplicationException;
	
}
