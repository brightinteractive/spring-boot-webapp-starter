package com.bright.appstarter.user.register;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bright.appstarter.email.EmailService;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserRepository;
import com.bright.appstarter.user.UserService;
import com.bright.appstarter.user.UserService.UserAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceUnitTest
{
	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@Mock
	private EmailService emailService;

	@InjectMocks
	private RegisterService registerService;

	@Test
	public void testRegisterUserReturnsUserWithActivationToken() throws UserAlreadyExistsException
	{
		String emailAddress = "noreply@noreply.com";
		String password = "mypassword";

		when(userService.createUser(any(String.class), any(String.class), any(String.class)))
			.thenAnswer(
				invocation ->
				{
					Object[] args = invocation.getArguments();
					return new User((String) args[0], (String) args[1], (String) args[2],
						Collections.emptyList());
				});

		User user = registerService.register(emailAddress, password);

		assertNotNull(user.getActivationToken());
	}

	@Test
	public void testRegisterUserSendsActivationEmail() throws UserAlreadyExistsException
	{
		String emailAddress = "noreply@noreply.com";
		String password = "mypassword";
		String template = "activation";

		when(userService.createUser(any(String.class), any(String.class), any(String.class)))
			.thenAnswer(
				invocation ->
				{
					Object[] args = invocation.getArguments();
					return new User((String) args[0], (String) args[1], (String) args[2],
						Collections.emptyList());
				});

		registerService.register(emailAddress, password);
		verify(emailService).send(eq(emailAddress), eq(template), any());
	}

}
