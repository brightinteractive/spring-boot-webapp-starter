package com.bright.appstarter.user;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.testsecurity.AuthenticationSetter;
import com.bright.appstarter.testsecurity.SecurityMockMvcWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@WebAppConfiguration
@Transactional
public class UserControllerPostIntegrationTest
{
	@Inject
	private WebApplicationContext webApplicationContext;

	@Inject
	private UserService userService;

	@Inject
	private PasswordEncoder passwordEncoder;

	private SecurityMockMvcWrapper mockMvc;

	private String emailAddress = "martinw@bright-interactive.com";

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();

	@Before
	public void setUp()
	{
		mockMvc = new SecurityMockMvcWrapper(webApplicationContext);
		authenticationSetter.setFullyAuthenticatedUser();
	}

	@Test
	public void testPostWithoutIdAddsUser() throws Exception
	{
		String password = "thisisapassw0rd";

		Optional<User> checkNotExisting = userService.getUserByEmailAddress(emailAddress);
		assertFalse(checkNotExisting.isPresent());

		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders
							.post(UserUITestConstants.USER_URL)
							.param("emailAddress", emailAddress)
							.param("password", password)
							.param("passwordRepeated", password);

		mockMvc.perform(post)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl(UserUITestConstants.USERS_URL));

		authenticationSetter.setFullyAuthenticatedUser();
		User newUser = userService.getUserByEmailAddress(emailAddress).get();
		assertEquals(newUser.getEmailAddress(), emailAddress);
		assertTrue(passwordEncoder.matches(password, newUser.getPasswordHash()));
	}

	@Test
	public void testFormRedisplayedOnPasswordNotEqualValidationError() throws Exception
	{
		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders.post(UserUITestConstants.USER_URL)
							.param("emailAddress", emailAddress)
							.param("password", "password1")
							.param("passwordRepeated", "password2");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.USER_TEMPLATE))
			.andExpect(content().string(
				containsString("The two passwords did not match")));
	}

	@Test
	public void testFormRedisplayedOnPasswordEmptyValidationError() throws Exception
	{
		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders.post(UserUITestConstants.USER_URL)
							.param("emailAddress", emailAddress)
							.param("password", "")
							.param("passwordRepeated", "");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.USER_TEMPLATE))
			.andExpect(content().string(
				containsString("Please enter a value")));
	}

	@Test
	public void testFormRedisplayedOnBadlyFormedEmailAddressError() throws Exception
	{
		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders.post(UserUITestConstants.USER_URL)
							.param("emailAddress", "martin@")
							.param("password", "password")
							.param("passwordRepeated", "password");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.USER_TEMPLATE))
			.andExpect(content().string(
				containsString("Not a valid email address")));
	}

	@Test
	public void testFormRedisplayedOnEmptyEmailAddressError() throws Exception
	{
		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders.post(UserUITestConstants.USER_URL)
							.param("emailAddress", "")
							.param("password", "password")
							.param("passwordRepeated", "password");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.USER_TEMPLATE))
			.andExpect(content().string(
				containsString("Please enter a value")));
	}

	@Test
	public void testFormRedisplayedWhenEmailAddressInUseError() throws Exception
	{
		String password = "apassword";
		userService.createUser(emailAddress, password);

		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders.post(UserUITestConstants.USER_URL)
							.param("emailAddress", emailAddress)
							.param("password", "anotherpassword")
							.param("passwordRepeated", "anotherpassword");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.USER_TEMPLATE))
			.andExpect(content().string(
				containsString("That email address is already in use")));
	}

	@Test
	public void testPostWithRolesAddsUserWithRoles() throws Exception
	{
		String password = "thisisapassw0rd";

		Optional<User> checkNotExisting = userService.getUserByEmailAddress(emailAddress);
		assertFalse(checkNotExisting.isPresent());

		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders
							.post(UserUITestConstants.USER_URL)
							.param("emailAddress", emailAddress)
							.param("password", password)
							.param("passwordRepeated", password)
							.param("roles", "ADMIN");

		mockMvc.perform(post)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl(UserUITestConstants.USERS_URL));

		Collection<Role> roles = new HashSet<>();
		roles.add(Role.ADMIN);

		authenticationSetter.setFullyAuthenticatedUser();
		User newUser = userService.getUserByEmailAddress(emailAddress).get();
		assertTrue(CollectionUtils.isEqualCollection(newUser.getRoles(), roles));
	}

	@Test
	public void testUpdateExistingUserWithSameEmailAddressUpdatesUser() throws Exception
	{
		userService.createUser(emailAddress, "apassword");
		User user = userService.getUserByEmailAddress(emailAddress).get();
		assertTrue(CollectionUtils.isEqualCollection(user.getRoles(), Collections.emptyList()));

		String newPassword = "newpassword";

		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders
							.post(UserUITestConstants.USER_URL)
							.param("id", "" + user.getId())
							.param("emailAddress", emailAddress)
							.param("password", newPassword)
							.param("passwordRepeated", newPassword)
							.param("roles", "ADMIN");

		mockMvc.perform(post)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl(UserUITestConstants.USERS_URL));

		Collection<Role> roles = new HashSet<>();
		roles.add(Role.ADMIN);

		authenticationSetter.setFullyAuthenticatedUser();
		User updateUser = userService.getUser(user.getId());

		assertTrue(CollectionUtils.isEqualCollection(updateUser.getRoles(), roles));
		assertTrue(passwordEncoder.matches(newPassword, updateUser.getPasswordHash()));
	}

	@Test
	public void testUpdateExistingUserWithoutPasswordUpdatesUser() throws Exception
	{
		userService.createUser(emailAddress, "apassword");
		User user = userService.getUserByEmailAddress(emailAddress).get();

		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders
							.post(UserUITestConstants.USER_URL)
							.param("id", "" + user.getId())
							.param("emailAddress", emailAddress)
							.param("roles", "ADMIN");

		mockMvc.perform(post)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl(UserUITestConstants.USERS_URL));

		Collection<Role> roles = new HashSet<>();
		roles.add(Role.ADMIN);

		authenticationSetter.setFullyAuthenticatedUser();
		User updateUser = userService.getUser(user.getId());

		assertTrue(CollectionUtils.isEqualCollection(updateUser.getRoles(), roles));
	}

	@Test
	public void testUpdateEmailWithOneAlreadyTakenGivesValidationError() throws Exception
	{
		String alreadyTakenEmailAddress = "taken@email.com";
		userService.createUser(emailAddress, "apassword");
		userService.createUser(alreadyTakenEmailAddress, "apassword");

		User user = userService.getUserByEmailAddress(emailAddress).get();

		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders
							.post(UserUITestConstants.USER_URL)
							.param("id", "" + user.getId())
							.param("emailAddress", alreadyTakenEmailAddress);

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.USER_TEMPLATE))
			.andExpect(content().string(
				containsString("That email address is already in use")));
	}

}
