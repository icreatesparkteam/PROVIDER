package com.lnt.sp.manager;

import java.util.List;

import com.lnt.core.common.dto.ClusterDto;
import com.lnt.core.common.dto.ClusterCommandDto;
import com.lnt.core.model.Cluster;
import com.lnt.core.common.exception.ServiceApplicationException;

public interface IClusterManager {

	public List<ClusterDto> getAllCluster() throws ServiceApplicationException;

	public ClusterDto findClusterByClusterID(String clusterID)
			throws ServiceApplicationException;
	
	public List<ClusterCommandDto> getAllClusterCommands() throws ServiceApplicationException;
	

}
