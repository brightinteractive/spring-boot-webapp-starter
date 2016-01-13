package com.bright.appstarter.user;

import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.test.TestData;
import com.bright.appstarter.testsecurity.AuthenticationSetter;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@Transactional
@WebAppConfiguration
public class UserControllerPermissionsIntegrationTest
{
	@Inject
	private UserController userController;

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();
	private TestData testData = new TestData();

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void testCallingGetWhenUnauthenticatedThrowsException() throws Exception
	{
		authenticationSetter.removeAuthenticationPrincipal();
		Optional<Long> id = Optional.of(1L);
		Model model = new ExtendedModelMap();
		userController.get(id, model);
	}

	@Test(expected = AccessDeniedException.class)
	public void testCallingGetWithNonAdminRoleThrowsException() throws Exception
	{
		Optional<Long> id = Optional.of(1L);
		Model model = new ExtendedModelMap();

		authenticationSetter.setAuthenticatedUser(testData
			.getCurrentUserWithNoRole());
		userController.get(id, model);
	}

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void testCallingPostWhenUnauthenticatedThrowsException() throws Exception
	{
		authenticationSetter.removeAuthenticationPrincipal();

		UserForm form = new UserForm();
		BindingResult result = mock(BindingResult.class);
		Model model = new ExtendedModelMap();

		userController.post(form, result, model);
	}

	@Test(expected = AccessDeniedException.class)
	public void testCallingPostWithNonAdminRoleThrowsException() throws Exception
	{
		UserForm form = new UserForm();
		BindingResult result = mock(BindingResult.class);
		Model model = new ExtendedModelMap();

		authenticationSetter.setAuthenticatedUser(testData
			.getCurrentUserWithNoRole());
		userController.post(form, result, model);
	}
}
