package com.lnt.sp.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lnt.core.common.dto.ClusterDto;
import com.lnt.core.model.Cluster;
import com.lnt.core.common.dto.ClusterCommandDto;
import com.lnt.core.model.ClusterCommand;
import com.lnt.core.common.exception.ServiceApplicationException;

import com.lnt.sp.dao.impl.ClusterCommandDao;
import com.lnt.sp.dao.impl.ClusterDao;

@Component
public class ClusterManager implements IClusterManager {

	private static final Logger logger = LoggerFactory
			.getLogger(ClusterManager.class);
	@Autowired
	private ClusterDao clusterDao;
	
	@Autowired
	private ClusterCommandDao clusterCommandDao;

	@Override
	public ClusterDto findClusterByClusterID(String clusterID) throws ServiceApplicationException {
		logger.info("ClusterManager Retrieving Cluster information with Cluster ID {}",
				clusterID);
		Cluster cluster = clusterDao.findByClusterID(clusterID);
		ClusterDto dto = new ClusterDto();
		dto.setClusterName(cluster.getClusterName());
		dto.setClusterID(cluster.getClusterID());
		dto.setId(cluster.getID());
		return dto;
	}
	
	@Override
	public List<ClusterDto> getAllCluster() throws ServiceApplicationException {
		logger.info("ClusterManager  getAllCluster!!!!!!!!!!!!!!");
		List<ClusterDto> clusterDtoList = new ArrayList<>();
		List<Cluster> cluster = clusterDao.getAllCluster();
		if (cluster == null) {
			throw new ServiceApplicationException(
					"Bad request.No cluster available "
							);
		}
		for (Cluster clusterList : cluster) {
			ClusterDto dto = new ClusterDto();
			dto.setClusterName(clusterList.getClusterName());
			dto.setClusterID(clusterList.getClusterID());
			dto.setId(clusterList.getID());
			clusterDtoList.add(dto);

		}
		return clusterDtoList;
	}
	
	@Override
	public List<ClusterCommandDto> getAllClusterCommands() throws ServiceApplicationException {
		logger.info("ClusterManager  getAllClusterCommands!!!!!!!!!!!!!!");
		List<ClusterCommandDto> clusterCommandDtoList = new ArrayList<>();
		List<ClusterCommand> clusterCommand = clusterCommandDao.getAllClusterCommands();
		if (clusterCommand == null) {
			throw new ServiceApplicationException(
					"Bad request.No command available "
							);
		}
		for (ClusterCommand clusterCommandList : clusterCommand) {
			ClusterCommandDto dto = new ClusterCommandDto();
			dto.setClusterID(clusterCommandList.getClusterID());
			dto.setCommandName(clusterCommandList.getCommandName());
			dto.setCommandValue(clusterCommandList.getCommandValue());
			dto.setCommandUpRange(clusterCommandList.getCommandUpRange());
			dto.setCommandLowRange(clusterCommandList.getCommandLowRange());
			clusterCommandDtoList.add(dto);

		}
		return clusterCommandDtoList;
	}

}
