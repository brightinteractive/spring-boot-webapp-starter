package com.bright.appstarter.user.admin;

import javax.inject.Inject;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.test.TestData;
import com.bright.appstarter.testsecurity.AuthenticationSetter;
import com.bright.appstarter.user.admin.UsersController;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@Transactional
@WebAppConfiguration
public class UsersControllerPermissionsIntegrationTest
{
	@Inject
	private UsersController usersController;

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();
	private TestData testData = new TestData();

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void testCallingGetWhenUnauthenticatedThrowsException() throws Exception
	{
		authenticationSetter.removeAuthenticationPrincipal();
		Model model = new ExtendedModelMap();
		usersController.get(model);
	}

	@Test(expected = AccessDeniedException.class)
	public void testCallingGetWithNonAdminRoleThrowsException() throws Exception
	{
		authenticationSetter.setAuthenticatedUser(testData
			.getCurrentUserWithNoRole());
		Model model = new ExtendedModelMap();
		usersController.get(model);
	}
}
