package com.bright.appstarter.user;

import static java.util.Arrays.*;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.bright.appstarter.userlogin.CurrentUser;

@RunWith(MockitoJUnitRunner.class)
public class CurrentUserUnitTest
{
	@Test
	public void testHasRoleForUserWithAllRolesReturnsTrue() throws Exception
	{
		Collection<Role> roles = asList(Role.ADMIN);
		User user = new User("test", "test", roles);

		CurrentUser currentUser = new CurrentUser(user);

		assertTrue(currentUser.hasRole(Role.ADMIN));
	}

	@Test
	public void testHasRoleForUserWithNoRolesReturnsFalse() throws Exception
	{
		User user = new User("test", "test");

		CurrentUser currentUser = new CurrentUser(user);

		assertFalse(currentUser.hasRole(Role.ADMIN));
	}

	@Test
	public void testHasAnyOfTheseRolesForUserWithAllRolesReturnsTrue() throws Exception
	{
		Collection<Role> roles = asList(Role.ADMIN);
		User user = new User("test", "test", roles);

		CurrentUser currentUser = new CurrentUser(user);

		assertTrue(currentUser.hasAnyOfTheseRoles(Collections.singleton(Role.ADMIN)));
	}

	@Test
	public void testHasAnyOfTheseRolesForUserWithNoRolesReturnsFalse() throws Exception
	{
		User user = new User("test", "test");
		CurrentUser currentUser = new CurrentUser(user);

		assertFalse(currentUser.hasAnyOfTheseRoles(Collections.singleton(Role.ADMIN)));
	}

}
