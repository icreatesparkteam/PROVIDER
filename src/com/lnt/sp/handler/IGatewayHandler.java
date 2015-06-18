package com.lnt.sp.handler;

import java.util.List;

import com.lnt.core.common.dto.GatewayDto;
import com.lnt.core.common.exception.ServiceApplicationException;

public interface IGatewayHandler {

	public void createGateway(GatewayDto gateway)
			throws ServiceApplicationException;

	public GatewayDto getGatewayByUserID(int userID, int serviceProviderID)
			throws ServiceApplicationException;

	public void updateGateway(GatewayDto gateway)
			throws ServiceApplicationException;

	public void deleteGateway(String gatewayID, int userID, int serviceProviderID) throws ServiceApplicationException;

	List<GatewayDto> getGatewayList()
			throws ServiceApplicationException;

	

}
