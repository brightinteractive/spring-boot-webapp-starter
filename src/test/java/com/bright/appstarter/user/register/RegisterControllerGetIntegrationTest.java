package com.bright.appstarter.user.register;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import javax.inject.Inject;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.testsecurity.AuthenticationSetter;
import com.bright.appstarter.user.UserUITestConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@WebAppConfiguration
@Transactional
public class RegisterControllerGetIntegrationTest
{
	@Inject
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();

	@Before
	public void setUp()
	{
		mockMvc = webAppContextSetup(webApplicationContext)
			.build();
		authenticationSetter.setFullyAuthenticatedUser();
	}

	@Test
	public void testGetShowsRegisterPage() throws Exception
	{

		final MockHttpServletRequestBuilder get =
						MockMvcRequestBuilders
							.get(UserUITestConstants.REGISTER_URL);

		mockMvc.perform(get)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.REGISTER_TEMPLATE))
			.andExpect(content().string(
				containsString("Register")));
	}

}
