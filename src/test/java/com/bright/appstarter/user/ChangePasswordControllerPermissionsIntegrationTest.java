package com.bright.appstarter.user;

import static org.mockito.Mockito.*;

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
import com.bright.appstarter.testsecurity.AuthenticationSetter;
import com.bright.appstarter.userlogin.CurrentUser;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@Transactional
@WebAppConfiguration
public class ChangePasswordControllerPermissionsIntegrationTest
{
	@Inject
	private ChangePasswordController changePasswordController;

	@Inject
	private UserService userService;

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void testCallingGetWhenUnauthenticatedThrowsException() throws Exception
	{
		Long userId = 3L;
		Model model = new ExtendedModelMap();

		authenticationSetter.removeAuthenticationPrincipal();
		changePasswordController.get(userId, model);
	}

	@Test(expected = AccessDeniedException.class)
	public void testCallingGetWithNonAdminUserWithDifferentIdThrowsException() throws Exception
	{
		Model model = new ExtendedModelMap();
		User nonAdminUser = getNonAdminUser();
		long userWhosePasswordToChangeId = nonAdminUser.getId() + 1;

		authenticationSetter.setAuthenticatedUser(new CurrentUser(nonAdminUser));
		changePasswordController.get(userWhosePasswordToChangeId, model);
	}

	@Test
	public void testCallingGetWithNonAdminUserWithSameIdSucceeds() throws Exception
	{
		Model model = new ExtendedModelMap();
		User nonAdminUser = getNonAdminUser();

		authenticationSetter.setFullyAuthenticatedUser();
		changePasswordController.get(nonAdminUser.getId(), model);
	}

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void testCallingPostWhenUnauthenticatedThrowsException() throws Exception
	{
		ChangePasswordForm form = new ChangePasswordForm();
		BindingResult result = mock(BindingResult.class);
		Model model = new ExtendedModelMap();

		authenticationSetter.removeAuthenticationPrincipal();
		changePasswordController.post(form, result, model);
	}

	@Test(expected = AccessDeniedException.class)
	public void testCallingPostWithNonAdminUserWithDifferentIdThrowsException() throws Exception
	{
		BindingResult result = mock(BindingResult.class);
		Model model = new ExtendedModelMap();

		User nonAdminUser = getNonAdminUser();
		long userWhosePasswordToChangeId = nonAdminUser.getId() + 1;
		ChangePasswordForm form = new ChangePasswordForm();
		form.setUserId(userWhosePasswordToChangeId);

		authenticationSetter.setAuthenticatedUser(new CurrentUser(nonAdminUser));
		changePasswordController.post(form, result, model);
	}

	@Test
	public void testCallingPostWithNonAdminUserWithSameIdSucceeds() throws Exception
	{
		authenticationSetter.setFullyAuthenticatedUser();
		User user = userService.createUser("m@c.com", "password");

		BindingResult result = mock(BindingResult.class);
		Model model = new ExtendedModelMap();

		ChangePasswordForm form = new ChangePasswordForm();
		form.setUserId(user.getId());
		form.setNewPassword("newpassword");

		authenticationSetter.setAuthenticatedUser(new CurrentUser(user));
		changePasswordController.post(form, result, model);
	}

	private User getNonAdminUser()
	{
		return new User(3L, "test", "test");
	}
}
