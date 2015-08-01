package com.lnt.sp.handler;

import java.util.List;

import com.lnt.core.common.dto.ClusterDto;
import com.lnt.core.common.dto.ClusterCommandDto;
import com.lnt.core.common.exception.ServiceApplicationException;

public interface IClusterHandler {

	public List<ClusterDto> getClusterList() throws ServiceApplicationException;
	public ClusterDto getClusterListByID(String clusterID) throws ServiceApplicationException;
	public List<ClusterCommandDto> getAllClusterCommands() throws ServiceApplicationException;

}
