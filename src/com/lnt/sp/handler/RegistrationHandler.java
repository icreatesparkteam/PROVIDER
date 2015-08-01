package com.lnt.sp.handler;

import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lnt.sp.common.dto.UserRegistrationDto;
import com.lnt.sp.common.exception.ServiceApplicationException;
import com.lnt.sp.common.exception.ValidationException;
import com.lnt.sp.common.util.IConstants;
import com.lnt.sp.common.util.UserInRequest;
import com.lnt.sp.common.util.Validator;


import com.lnt.sp.model.User;
import com.lnt.sp.annotations.WriteTransaction;
import com.lnt.sp.manager.IPasswordManager;
import com.lnt.sp.manager.IRegistrationManager;


@Component
public class RegistrationHandler implements IRegistrationHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(RegistrationHandler.class);

	@Autowired
	private IRegistrationManager regMgr;

	@Autowired
	private IPasswordManager passwordManager;

	@Autowired
	private IAuthenticationHandler authHandler;

	@Override
	@WriteTransaction
	public void createUser(UserRegistrationDto register)
			throws ServiceApplicationException {
		logger.info("createServiceProvider :  register method ");
		if (register == null) {
			throw new ServiceApplicationException("Invalid service provider Details ");
		}

		if (!Validator.mandatory(register.getPassword()))
			throw new ValidationException("Password is mandatory");

		if (!Validator.mandatory(register.getUserName()))
			throw new ValidationException("username is mandatory");

		if (regMgr.getUser(register.getUserName()) != null) {
			throw new ValidationException(
					"Duplicate service provider - service provider already exists with User name: "
							+ register.getUserName());
		}
		User user = new User();
		user = register.toUser(user);
		user.setPassword(register.getPassword());
		user.setActive(true);
		regMgr.createUser(user);
	}

	@Override
	@Transactional
	public UserRegistrationDto getUser(String userName)
			throws ServiceApplicationException {
		logger.info("ServiceProviderHandler :  getServiceProvider method ");
		User user = regMgr.getUser(userName);
		if (user == null) {
			logger.error("serviceProvider : {} not found", userName);
			throw new ServiceApplicationException("serviceProvider not found : "
					+ userName);
		}
		UserRegistrationDto reg = new UserRegistrationDto();
		reg.fromUser(user);
		return reg;
	}

	@Override
	@WriteTransaction
	public void updateUser(UserRegistrationDto register)
			throws ServiceApplicationException {
		logger.info("serviceProvider :  updateserviceProvider method ");
		if (register == null) {
			throw new ServiceApplicationException("Invalid serviceProvider :");
		}
		User user = regMgr.getUser(register.getUserName());
		if (user == null) {
			throw new ServiceApplicationException(
					"serviceProvider is not available with this username : "
							+ register.getUserName());
		}
		register.toUser(user);
		regMgr.updateUser(user);

	}

	@Override
	@WriteTransaction
	public void deleteUser(String userName) throws ServiceApplicationException {
		logger.info("UserHandler :  deleteduser method ");
		User user = regMgr.getUser(userName);
		if (user == null) {
			throw new ServiceApplicationException("Invalid serviceProvider :");
		}
		user.setActive(true);
		regMgr.updateUser(user);
	}

	@Override
	@WriteTransaction
	public String changePassword(String password, String newPassword,
			String confirmPassword) throws ServiceApplicationException {
		logger.info("serviceProviderHandler : change password ");

		if (password.equals(newPassword)) {
			throw new ValidationException(IConstants.PASSWORD_COMPARISION);
		}

		String resultMsg;
		User user = UserInRequest.getInstance().getUserContext()
				.getUserInfo();

		int userId = user.getId();
		resultMsg = passwordManager.validatePassword(user.getUserName(),
				userId, newPassword);
		if (!"success".equalsIgnoreCase(resultMsg)) {
			logger.debug(resultMsg);
			throw new ValidationException(resultMsg);
		}

		resultMsg = authHandler.validatePassword(password, user);
		if (!"Success".equalsIgnoreCase(resultMsg)) {
			throw new ValidationException(resultMsg);
		} else {
			logger.debug("Authentication success. Setting new password for user"
					+ user.getUserName());
			user.setPassword(newPassword);
			regMgr.updateUser(user);
		}

		return resultMsg;
	}

	/**
	 * Get serviceProvider name and security answer and validates answer with the answer
	 * stored in database.
	 * 
	 * @param userName
	 * @param userAns
	 * @return String specifying whether user security answer is validated or
	 *         not
	 * @throws ServiceApplicationException
	 */
	@Override
	@WriteTransaction
	public String passwordRecovery(String userName, String userAns)
			throws ServiceApplicationException {
		User user = regMgr.getUser(userName);
		if (user == null) {
			logger.error("serviceProvider : {} not found", userName);
			throw new ServiceApplicationException("serviceProvider not found : "
					+ userName);
		}
		if (userAns.equals(user.getsAnswer())) {
			char[] pswd = passwordManager.generateTmpPswd(IConstants.MINLEN,
					IConstants.MINLEN, IConstants.NOOFCAPSALPHA,
					IConstants.NOOfDIGITS, IConstants.NOOfSPLCHARS);
			String tempPassword = new String(pswd);
			user.setPassword(tempPassword);
			regMgr.updateUser(user);
			return tempPassword;
		} else {
			throw new ServiceApplicationException(
					HttpStatus.EXPECTATION_FAILED.value(),
					IConstants.VALIDATE_ANSWER_FAILED);
		}

	}

	@Override
	@Transactional
	public String getSecurityQuestions(String userName)
			throws ServiceApplicationException {
		User user = regMgr.getUser(userName);
		if (user == null) {
			logger.error("serviceProvider : {} not found", userName);
			throw new ServiceApplicationException("User not found : "
					+ userName);
		}

		String question = user.getsQuestion();
		JSONObject obj = new JSONObject();
		obj.put("question", question);
		// String jsonData={question:question, answer:answer};

		return obj.toJSONString();
	}

	@Override
	@WriteTransaction
	public void setSecurityQuestions(String question, String answer)
			throws ServiceApplicationException {
		User user = UserInRequest.getInstance().getUserContext()
				.getUserInfo();
		// UserInfo user = userMgr.getUser(userName);
		if (user == null) {
			logger.error("serviceProvider : {} not found");
			throw new ServiceApplicationException("serviceProvider not found : ");
		}
		user.setsQuestion(question);
		user.setsAnswer(answer);
		regMgr.updateUser(user);
	}


	@Override
	@Transactional
	public List<UserRegistrationDto> getUserList() throws ServiceApplicationException {
		logger.info("UserHandler :  getUserList by role id method ");
		return regMgr.getAlluserlist();
	}

}
