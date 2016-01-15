package com.bright.appstarter.user.changepassword;

import static com.bright.appstarter.user.UserUITestConstants.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.bright.appstarter.test.FlushingTransactionalTest;
import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.testsecurity.AuthenticationSetter;
import com.bright.appstarter.testsecurity.SecurityMockMvcWrapper;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserService;
import com.bright.appstarter.user.UserService.UserAlreadyExistsException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@WebAppConfiguration
@FlushingTransactionalTest
public class ChangePasswordControllerPostIntegrationTest
{
	@Inject
	private WebApplicationContext webApplicationContext;
	private SecurityMockMvcWrapper mockMvc;

	@Inject
	private UserService userService;

	private final String emailAddress = "m@c.com";
	private final String currentPassword = "currentPass";
	private User user;
	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();

	@Before
	public void setUp() throws UserAlreadyExistsException
	{
		authenticationSetter.setFullyAuthenticatedUser();
		mockMvc = new SecurityMockMvcWrapper(webApplicationContext);
		user = userService.createUser(emailAddress, currentPassword);
	}

	@Test
	public void testPostWithValidDataChangesPassword() throws Exception
	{
		String newPassword = "newPass";

		MockHttpServletRequestBuilder post = post(CHANGE_PASSWORD_URL)
			.param("userId", user.getId().toString())
			.param("currentPassword", currentPassword)
			.param("newPassword", newPassword)
			.param("passwordRepeated", newPassword);

		mockMvc.perform(post)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/"));

		authenticationSetter.setFullyAuthenticatedUser();
		assertTrue(userService.isCurrentPassword(user.getId(), newPassword));
	}

	@Test
	public void testFormRedisplayedWhenValidationErrors() throws Exception
	{
		MockHttpServletRequestBuilder post = post(CHANGE_PASSWORD_URL)
			.param("userId", user.getId().toString())
			.param("currentPassword", "wrong")
			.param("newPassword", "new")
			.param("passwordRepeated", "differentNew");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(CHANGE_PASSWORD_TEMPLATE))
			.andExpect(content().string(
				containsString("The password you entered was wrong")))
			.andExpect(content().string(
				containsString("The two passwords did not match")));
	}

	@Test
	public void testFormRedisplayedWhenFieldsLeftEmpty() throws Exception
	{
		MockHttpServletRequestBuilder post = post(CHANGE_PASSWORD_URL)
			.param("userId", user.getId().toString())
			.param("currentPassword", "")
			.param("newPassword", "")
			.param("passwordRepeated", "");

		mockMvc.perform(post)
			.andExpect(status().isOk())
			.andExpect(view().name(CHANGE_PASSWORD_TEMPLATE))
			.andExpect(content().string(
				containsString("Please enter a value")));
	}
}
