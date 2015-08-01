package com.lnt.sp.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
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

import com.lnt.core.common.dto.ManufacturerDto;
import com.lnt.core.common.dto.ServiceProviderRegistrationDto;
import com.lnt.core.common.exception.ServiceApplicationException;
import com.lnt.core.common.exception.ServiceRuntimeException;
import com.lnt.sp.handler.IManufacturerHandler;


@Component
@Path("/manufacturer")
public class ManufacturerService {

	private static final Logger logger = LoggerFactory
			.getLogger(ManufacturerService.class);

	@Autowired
	private IManufacturerHandler manufacturerHandler;

	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{getmanufacturerbyname}")
//	@PreAuthorize("hasAuthority('VIEW_PERSONAL_DATA')")
	public Response getManufacturerByName(@PathParam("name") String name) {
		logger.info("ManufacturerService getManufacturerByName method");
		try {
			ManufacturerDto manufacturer = manufacturerHandler.getManufacturerByName(name);
			return Response.ok().entity(manufacturer).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while getManufacturerByName : {}", e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error("Application Exception while getManufacturerByName : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}
	
//	@GET
//	@Produces({ MediaType.APPLICATION_JSON })
//	@Path("/{getmanufacturerbyid}")
////	@PreAuthorize("hasAuthority('VIEW_PERSONAL_DATA')")
//	public Response getManufacturerById(@PathParam("id") int id) {
//		logger.info("ManufacturerService getManufacturerByName method");
//		try {
//			ManufacturerDto manufacturer = manufacturerHandler.getManufacturerById(id);
//			return Response.ok().entity(manufacturer).build();
//		} catch (ServiceRuntimeException e) {
//			logger.error("Runtime Exception while getManufacturerById : {}", e.getMessage());
//			return Response.status(e.getCode()).entity(e.getMessage()).build();
//		} catch (ServiceApplicationException e) {
//			logger.error("Application Exception while getManufacturerById : {}",
//					e.getMessage());
//			return Response.status(e.getCode()).entity(e.getMessage()).build();
//		}
//	}

	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/manufacturerlist")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response getManufacturerList() {
		logger.info("ManufacturerService getManufacturerList method");
		try {
			List<ManufacturerDto> manufacturerList = manufacturerHandler.getManufacturerList();
			return Response.ok().entity(manufacturerList).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while getManufacturerList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while getManufacturerList : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

}
