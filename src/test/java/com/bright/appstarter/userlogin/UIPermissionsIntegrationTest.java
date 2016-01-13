package com.bright.appstarter.userlogin;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.testsecurity.AuthenticationSetter;
import com.bright.appstarter.user.Role;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserService;
import com.bright.appstarter.user.UserService.UserAlreadyExistsException;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@Transactional
@WebAppConfiguration
public class UIPermissionsIntegrationTest
{
	@Inject
	private UserService userService;

	@Inject
	private UserDetailsService currentUserDetailsService;

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();

	@Before
	public void setUp()
	{
		authenticationSetter.setFullyAuthenticatedUser();
	}

	@Test
	public void testUserWithAdminRoleReturnTrueForAll() throws Exception
	{
		CurrentUser currentUser = createAndLoadCurrentUserWithRoles(Collections
			.singleton(Role.ADMIN));

		assertEquals(currentUser.getUIPermissions().getCanViewUsersMenu(), true);
	}

	@Test
	public void testUserWithNoRoleReturnFalseForAll() throws Exception
	{
		CurrentUser currentUser = createAndLoadCurrentUserWithRoles(Collections.emptyList());

		assertEquals(currentUser.getUIPermissions().getCanViewUsersMenu(), false);
	}

	private CurrentUser createAndLoadCurrentUserWithRoles(Collection<Role> roles)
		throws UserAlreadyExistsException
	{
		String emailAddress = "m@c.com";
		User user = userService.createUser(emailAddress, "password",
			roles);

		return (CurrentUser) currentUserDetailsService.loadUserByUsername(user
			.getEmailAddress());
	}
}
