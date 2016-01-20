package com.bright.appstarter.user.register;

import java.util.UUID;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bright.appstarter.email.EmailService;
import com.bright.appstarter.email.EmailVariablesImpl;
import com.bright.appstarter.stereotype.AppStarterService;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserRepository;
import com.bright.appstarter.user.UserService;
import com.bright.appstarter.user.UserService.UserAlreadyExistsException;
import com.bright.appstarter.user.UserUrlConstants;

@AppStarterService
public class RegisterService
{
	@Inject
	private UserRepository userRepository;

	@Inject
	private UserService userService;

	@Inject
	private EmailService emailService;

	@Value("${appstarter.application.url}")
	private String applicationUrl;

	protected final Log logger = LogFactory.getLog(getClass());

	@PreAuthorize("@userPermissionsService.allowPublicAccess()")
	// public access allowed
	public User register(String emailAddress, String password) throws UserAlreadyExistsException
	{
		String registerToken = generateActivationToken();
		User newUser = userService.createUser(emailAddress, password, registerToken);
		sendActivationEmail(newUser);

		return newUser;
	}

	@PreAuthorize("true")
	// public access allowed
	public User activate(String activationToken)
	{

		User registeredUser = this.userRepository.findOneByActivationToken(activationToken);
		if (registeredUser != null)
		{
			registeredUser.setActivationToken(User.ACTIVATION_TOKEN_APPROVED);
			this.userRepository.save(registeredUser);
			return registeredUser;
		}

		throw new InvalidRegisterTokenException();
	}

	private String generateActivationToken()
	{
		return UUID.randomUUID().toString();
	}

	private void sendActivationEmail(User user)
	{
		EmailVariablesImpl variables = new EmailVariablesImpl();
		variables.addBodyVar("activationUrl",
			applicationUrl + "/" + UserUrlConstants.ACTIVATE_URL + "/" + user.getActivationToken());

		try
		{
			emailService.send(user.getEmailAddress(), "activation", variables);
		}
		catch (Throwable t)
		{
			logger
				.error("Failed to send registration email to " + user.getEmailAddress() + ":" + t);
		}
	}

	public static class InvalidRegisterTokenException extends RuntimeException
	{
		private static final long serialVersionUID = -8377434208974970957L;
	}

}
