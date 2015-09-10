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

import com.lnt.core.common.dto.DeviceCommandQueueDto;
import com.lnt.core.common.dto.GatewayDto;
import com.lnt.core.common.dto.DeviceCommandDto;
import com.lnt.core.common.dto.ServiceProviderRegistrationDto;
import com.lnt.core.common.dto.SmartDeviceDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.common.exception.ServiceRuntimeException;
import com.lnt.sp.handler.IGatewayHandler;
import com.lnt.sp.handler.IRegistrationHandler;

@Component
@Path("/gateway")
public class GatewayService {

	private static final Logger logger = LoggerFactory
			.getLogger(GatewayService.class);

	@Autowired
	private IGatewayHandler gatewayHandler;

	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/create")
	//@PreAuthorize("hasAuthority('CREATE_USER')")
	public Response createGateway(GatewayDto gateayDto, @HeaderParam("lnt_access_token") String sessionID) {
		logger.info("GatewayService createGateway method");
		try {
			gatewayHandler.createGateway(gateayDto, sessionID);
			return Response.ok().entity("Gateway created successfully").build();
		} catch (ServiceApplicationException e) {
			logger.error("Application Exception while creating gateway: {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while creating gateway : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/update")
//	@PreAuthorize("hasAuthority('UPDATE_MY_PROFILE')")
	public Response update(GatewayDto gatewayDto, @HeaderParam("lnt_access_token") String sessionID) {
		logger.info("GatewayService update method");
		try {
			gatewayHandler.updateGateway(gatewayDto, sessionID);
			return Response.ok().entity("Gateway updated successfully").build();
		} catch (ServiceApplicationException e) {
			logger.error("Application Exception while update : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while update : {}", e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{getgatewaybyuser}")
//	@PreAuthorize("hasAuthority('VIEW_PERSONAL_DATA')")
	public Response getGatewayByUser(@HeaderParam("lnt_access_token") String sessionID) {
		logger.info("GatewayService getGatewayByUser method");
		try {
			GatewayDto gateway = gatewayHandler.getGatewayByUserID(sessionID);
			return Response.ok().entity(gateway).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while getGatewayByUser : {}", e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error("Application Exception while getGatewayByUser : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/delete/{gateway}")
	// @PreAuthorize("hasAuthority('DELETE_ACCOUNT')")
	public Response deleteGateway(@PathParam("gatewayid") String gatewayID,
			@PathParam("sessionId") String sessionID) {
		logger.info("GatewayService Delete Gateway method");
		try {
			gatewayHandler.deleteGateway(gatewayID, sessionID);
			return Response.ok().entity("Gateway deleted successfully").build();
		} catch (ServiceApplicationException e) {
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceRuntimeException e) {
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/gatewaylist")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response getGatewayList() {
		logger.info("GatewayService getGatewayList method");
		try {
			List<GatewayDto> gatewayList = gatewayHandler.getGatewayList();
			return Response.ok().entity(gatewayList).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while gatewayList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while gatewayList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}
	
	
	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/addsmartdevice")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response addSmartDevice(SmartDeviceDto smartDevice, @HeaderParam("gatewayid") String gatewayID) {
		logger.info("GatewayService addSmartDevice method");
		try {
			gatewayHandler.addSmartDevice(smartDevice, gatewayID);
			return Response.ok().entity("Device added successfully").build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while gatewayList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while gatewayList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/devicelist")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response getDeviceList(@HeaderParam("gatewayid") String gatewayID) {
		logger.info("GatewayService getDeviceList method");
		try {
			List<SmartDeviceDto> deviceList = gatewayHandler.getDeviceList(gatewayID);
			return Response.ok().entity(deviceList).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while gatewayList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while gatewayList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/devicecommand")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response deviceCommand(DeviceCommandQueueDto command) {
		logger.info("GatewayService deviceCommand method");
		try {
			gatewayHandler.deviceCommand(command);
			return Response.ok().entity("Command executed successfully").build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while executing command : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while executing command : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/queuecommand")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response getQueuedDeviceCommand(@HeaderParam("lnt_access_token") String sessionID) {
		logger.info("GatewayService getQueuedDeviceCommand method");
		try {
			List<DeviceCommandQueueDto> queue = gatewayHandler.getQueuedDeviceCommand(sessionID);
			return Response.ok().entity(queue).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while executing command : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while executing command : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}


}
