package com.bright.appstarter.user;

import static com.bright.appstarter.user.UserUITestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

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
public class ChangePasswordControllerGetIntegrationTest
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
		authenticationSetter.setFullyAuthenticatedUser();
		mockMvc = new SecurityMockMvcWrapper(webApplicationContext);
	}

	@Test
	public void testGetDoesNotThrow() throws Exception
	{
		String emailAddress = "any@any.com";
		User user = userService.createUser(emailAddress, "apassword");

		final MockHttpServletRequestBuilder get = get(CHANGE_PASSWORD_URL)
			.param("userId", user.getId().toString());

		mockMvc.perform(get)
			.andExpect(status().isOk())
			.andExpect(view().name(CHANGE_PASSWORD_TEMPLATE));
	}
}
