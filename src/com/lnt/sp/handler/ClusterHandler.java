package com.lnt.sp.handler;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lnt.sp.annotations.WriteTransaction;
import com.lnt.sp.common.util.Config;
import com.lnt.core.common.dto.ClusterDto;
import com.lnt.core.common.dto.ClusterCommandDto;

import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.common.exception.ValidationException;

import com.lnt.sp.manager.IClusterManager;

import com.lnt.core.model.Cluster;

@Component
public class ClusterHandler implements IClusterHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(ClusterHandler.class);

	@Autowired
	private IClusterManager clusterMgr;

	@Override
	@Transactional
	public List<ClusterDto> getClusterList() throws ServiceApplicationException {
		logger.info("ClusterHandler :  getClusterList method ");
		
		return clusterMgr.getAllCluster();
	}
	
	@Override
	@Transactional
	public ClusterDto getClusterListByID(String clusterID) throws ServiceApplicationException {
		logger.info("ClusterHandler :  getClusterListByID method ");
		
		return clusterMgr.findClusterByClusterID(clusterID);
	}
	
	@Override
	@Transactional
	public List<ClusterCommandDto> getAllClusterCommands() throws ServiceApplicationException {
		logger.info("ClusterHandler :  getAllClusterCommands method ");
		
		return clusterMgr.getAllClusterCommands();
	}

}
