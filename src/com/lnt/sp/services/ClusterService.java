package com.lnt.sp.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.lnt.core.common.dto.ClusterDto;
import com.lnt.core.common.dto.ClusterCommandDto;
import com.lnt.core.common.dto.ServiceProviderRegistrationDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.common.exception.ServiceRuntimeException;
import com.lnt.sp.handler.IClusterHandler;
import com.lnt.sp.handler.IGatewayHandler;
import com.lnt.sp.handler.IRegistrationHandler;

@Component
@Path("/cluster")
public class ClusterService {

	private static final Logger logger = LoggerFactory
			.getLogger(ClusterService.class);

	@Autowired
	private IClusterHandler clusterHandler;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/clusterlist")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response getClusterList() {
		logger.info("ClusterService getClusterList method");
		try {
			List<ClusterDto> clusterList = clusterHandler.getClusterList();
			return Response.ok().entity(clusterList).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while getClusterList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while getClusterList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/clusterlistbyid")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response getClusterListByID(@HeaderParam("clusterid") String clusterID) {
		logger.info("ClusterService getClusterListByID method");
		try {
			ClusterDto clusterList = clusterHandler.getClusterListByID(clusterID);
			return Response.ok().entity(clusterList).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while getClusterListByID : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while getClusterListByID : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/clustercommandlist")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response getClusterCommandList() {
		logger.info("ClusterService getClusterCommandList method");
		try {
			List<ClusterCommandDto> clusterCommandList = clusterHandler.getAllClusterCommands();
			return Response.ok().entity(clusterCommandList).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while getClusterCommandList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while getClusterCommandList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

}
