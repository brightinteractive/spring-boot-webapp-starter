package com.bright.appstarter.user.admin;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.testsecurity.AuthenticationSetter;
import com.bright.appstarter.testsecurity.SecurityMockMvcWrapper;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserService;
import com.bright.appstarter.user.UserUITestConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@WebAppConfiguration
@Transactional
public class UserControllerGetIntegrationTest
{
	@Inject
	private WebApplicationContext webApplicationContext;

	@Inject
	private UserService userService;

	private SecurityMockMvcWrapper mockMvc;

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();

	@Before
	public void setUp()
	{
		mockMvc = new SecurityMockMvcWrapper(webApplicationContext);
		authenticationSetter.setFullyAuthenticatedUser();
	}

	@Test
	public void testGetWithoutIdShowsAddUserPage() throws Exception
	{

		final MockHttpServletRequestBuilder get =
						MockMvcRequestBuilders
							.get(UserUITestConstants.USER_URL);

		mockMvc.perform(get)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.USER_TEMPLATE))
			.andExpect(content().string(
				containsString("Add user")));
	}

	@Test
	public void testGetWithIdShowsAddUserPage() throws Exception
	{
		String emailAddress = "any@any.com";
		User user = userService.createUser(emailAddress, "apassword");

		final MockHttpServletRequestBuilder get =
						MockMvcRequestBuilders
							.get(UserUITestConstants.USER_URL)
							.param("id", "" + user.getId());

		mockMvc.perform(get)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.USER_TEMPLATE))
			.andExpect(content().string(
				containsString("Edit user")));
	}
}
