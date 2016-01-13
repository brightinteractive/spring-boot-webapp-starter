package com.bright.appstarter.user;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;

import javax.inject.Inject;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.testsecurity.AuthenticationSetter;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@WebAppConfiguration
@Transactional
public class UserServiceIntegrationTest
{
	@Inject
	private UserService userService;

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();

	@Before
	public void setUp()
	{
		authenticationSetter.setFullyAuthenticatedUser();
	}

	@Test
	public void testSavingUserSavesUser() throws Exception
	{
		String emailAddress = "martinw@bright-interactive.com";
		String password = "thisisapassw0rd";

		Collection<Role> roles = new HashSet<>();
		roles.add(Role.ADMIN);

		userService.createUser(emailAddress, password, roles);

		User user = userService.getUserByEmailAddress(emailAddress).get();

		assertEquals(user.getEmailAddress(), emailAddress);
		assertTrue(CollectionUtils.isEqualCollection(user.getRoles(), roles));
	}
}
