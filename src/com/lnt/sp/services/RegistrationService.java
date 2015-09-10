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

import com.lnt.core.common.dto.ServiceProviderRegistrationDto;
import com.lnt.sp.common.dto.UserRegistrationDto;
import com.lnt.sp.common.exception.ServiceApplicationException;
import com.lnt.sp.common.exception.ServiceRuntimeException;
import com.lnt.sp.dao.impl.RoleDao;
import com.lnt.sp.handler.IRegistrationHandler;

@Component
@Path("/registration")
public class RegistrationService {

	private static final Logger logger = LoggerFactory
			.getLogger(RegistrationService.class);

	@Autowired
	private IRegistrationHandler regHandler;

	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/create")
	//@PreAuthorize("hasAuthority('CREATE_USER')")
	public Response createUser(UserRegistrationDto register) {
		logger.info("RegistrationService createUser method");
		try {
			regHandler.createUser(register);
			return Response.ok().entity("User created successfully").build();
		} catch (ServiceApplicationException e) {
			logger.error("Application Exception while creating user: {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while creating user : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

	@POST
	@Produces({ MediaType.TEXT_PLAIN })
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/update")
//	@PreAuthorize("hasAuthority('UPDATE_MY_PROFILE')")
	public Response update(UserRegistrationDto register) {
		logger.info("RegistrationService update method");
		try {
			regHandler.updateUser(register);
			return Response.ok().entity("User updated successfully").build();
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
	@Path("/{username}")
//	@PreAuthorize("hasAuthority('VIEW_PERSONAL_DATA')")
	public Response getUser(@PathParam("username") String userName) {
		logger.info("URegistrationService getUser method");
		try {
			UserRegistrationDto user = regHandler.getUser(userName);
			return Response.ok().entity(user).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while getUser : {}", e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error("Application Exception while getUser : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/delete/{username}")
	// @PreAuthorize("hasAuthority('DELETE_ACCOUNT')")
	public Response deleteUser(@PathParam("username") String userName) {
		logger.info("RegistrationService Delete Usere method");
		try {
			regHandler.deleteUser(userName);
			return Response.ok().entity("ServiceProvider deleted successfully").build();
		} catch (ServiceApplicationException e) {
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceRuntimeException e) {
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/changepassword")
//	@PreAuthorize("hasAuthority('UPDATE_MY_PROFILE')")
	public Response changePassword(@FormParam("password") String password,
			@FormParam("newpassword") String newPassword,
			@FormParam("confirmpassword") String confirmPassword) {
		try {
			String result = regHandler.changePassword(password, newPassword,
					confirmPassword);
			return Response.ok().entity(result).build();

		} catch (ServiceApplicationException ae) {
			logger.error("Application Exception When Changing Password", ae);
			return Response.status(ae.getCode()).entity(ae.getMessage())
					.build();
		} catch (ServiceRuntimeException e) {
			logger.error("ServiceRuntimeException changing user password ", e);
			return Response.status(e.getCode()).build();
		}
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/passwordRecovery")
	public Response passwordRecovery(@FormParam("username") String userName,
			@FormParam("userAns") String questionAns) {
		String result;
		logger.info("RegistrationService passwordRecovery method ");
		try {
			result = regHandler.passwordRecovery(userName, questionAns);
			return Response.ok().entity(result).build();
		} catch (ServiceApplicationException e) {
			logger.error("Application Exception When passwordRecovery ", e);
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceRuntimeException e) {
			return Response.status(e.getCode()).build();
		}

	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/securityquestions/{username}")
	public Response getSecurityQuestions(@PathParam("username") String userName) {
		logger.info("RegistrationService getSecurityQuestions method");
		try {
			String details = regHandler.getSecurityQuestions(userName);
			return Response.ok().entity(details).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception : {}", e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error("Application Exception  {}", e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/setquestion")
//	@PreAuthorize("hasAuthority('UPDATE_MY_PROFILE')")
	public Response setSecurityQuestions(
			@FormParam("question") String question,
			@FormParam("answer") String answer) {
		logger.info("RegistrationService setSecurityQuestions method");
		try {
			regHandler.setSecurityQuestions(question, answer);
			return Response.ok().entity("question updated successfully")
					.build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception : {}", e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error("Application Exception  {}", e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/serviceproviderlist")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response getServiceProviderList() {
		logger.info("RegistrationService serviceproviderlist method");
		try {
			List<ServiceProviderRegistrationDto> serviceProviderList = regHandler.getServiceProviderList();
			return Response.ok().entity(serviceProviderList).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while getUserListByRoleName : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while getUserListByRoleName : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/userlist")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response getUserList() {
		logger.info("RegistrationService getUserListByRoleName method");
		try {
			List<UserRegistrationDto> userList = regHandler.getUserList();
			return Response.ok().entity(userList).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while getUserListByRoleName : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while getUserListByRoleName : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/getrole")
	// @PreAuthorize("hasAuthority('VIEW_PROFILE')")
	public Response getRole(@HeaderParam("lnt_access_token") String sessionID) {
		logger.info("RegistrationService getRole method");
		try {
			String role = regHandler.getUserRole(sessionID);
			return Response.ok().entity(role).build();
		} catch (ServiceRuntimeException e) {
			logger.error("Runtime Exception while getUserListByRoleName : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		} catch (ServiceApplicationException e) {
			logger.error(
					"Application Exception while getUserListByRoleName : {}",
					e.getMessage());
			return Response.status(e.getCode()).entity(e.getMessage()).build();
		}
	}

}
