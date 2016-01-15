package com.bright.appstarter.user.register;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.testsecurity.AuthenticationSetter;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserService;
import com.bright.appstarter.user.UserUITestConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@WebAppConfiguration
@Transactional
public class RegisterControllerPostIntegrationTest
{
	@Inject
	private WebApplicationContext webApplicationContext;

	@Inject
	private UserService userService;

	@Inject
	private PasswordEncoder passwordEncoder;

	private MockMvc mockMvc;

	private String emailAddress = "martinw@bright-interactive.com";

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();

	@Before
	public void setUp()
	{
		mockMvc = webAppContextSetup(webApplicationContext)
			.build();
		authenticationSetter.setFullyAuthenticatedUser();
	}

	@Test
	public void testPostAddsUserAndSendsEmail() throws Exception
	{
		GreenMail greenMail = new GreenMail();
		greenMail.start();

		String password = "thisisapassw0rd";

		Optional<User> checkNotExisting = userService.getUserByEmailAddress(emailAddress);
		assertFalse(checkNotExisting.isPresent());

		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders
							.post(UserUITestConstants.REGISTER_URL)
							.param("emailAddress", emailAddress)
							.param("password", password)
							.param("passwordRepeated", password);

		mockMvc.perform(post)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl(UserUITestConstants.POST_REGISTER_URL));

		authenticationSetter.setFullyAuthenticatedUser();
		User newUser = userService.getUserByEmailAddress(emailAddress).get();
		assertEquals(newUser.getEmailAddress(), emailAddress);
		assertTrue(passwordEncoder.matches(password, newUser.getPasswordHash()));

		assertTrue(GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]).contains(
			"To complete your registration you need to activate your account"));
		greenMail.stop();
	}

	@Test
	public void testFormRedisplayedOnPasswordNotEqualValidationError() throws Exception
	{
		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders.post(UserUITestConstants.REGISTER_URL)
							.param("emailAddress", emailAddress)
							.param("password", "password1")
							.param("passwordRepeated", "password2");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.REGISTER_TEMPLATE))
			.andExpect(content().string(
				containsString("The two passwords did not match")));
	}

	@Test
	public void testFormRedisplayedOnPasswordEmptyValidationError() throws Exception
	{
		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders.post(UserUITestConstants.REGISTER_URL)
							.param("emailAddress", emailAddress)
							.param("password", "")
							.param("passwordRepeated", "");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.REGISTER_TEMPLATE))
			.andExpect(content().string(
				containsString("Please enter a value")));
	}

	@Test
	public void testFormRedisplayedOnBadlyFormedEmailAddressError() throws Exception
	{
		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders.post(UserUITestConstants.REGISTER_URL)
							.param("emailAddress", "martin@")
							.param("password", "password")
							.param("passwordRepeated", "password");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.REGISTER_TEMPLATE))
			.andExpect(content().string(
				containsString("Not a valid email address")));
	}

	@Test
	public void testFormRedisplayedOnEmptyEmailAddressError() throws Exception
	{
		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders.post(UserUITestConstants.REGISTER_URL)
							.param("emailAddress", "")
							.param("password", "password")
							.param("passwordRepeated", "password");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.REGISTER_TEMPLATE))
			.andExpect(content().string(
				containsString("Please enter a value")));
	}

	@Test
	public void testFormRedisplayedWhenEmailAddressInUseError() throws Exception
	{
		String password = "apassword";
		userService.createUser(emailAddress, password);

		final MockHttpServletRequestBuilder post =
						MockMvcRequestBuilders.post(UserUITestConstants.REGISTER_URL)
							.param("emailAddress", emailAddress)
							.param("password", "anotherpassword")
							.param("passwordRepeated", "anotherpassword");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.REGISTER_TEMPLATE))
			.andExpect(content().string(
				containsString("That email address is already in use")));
	}
}
