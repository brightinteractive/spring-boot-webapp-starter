package com.bright.appstarter.user.register;

import static org.junit.Assert.*;
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

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.testsecurity.AuthenticationSetter;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserRepository;
import com.bright.appstarter.user.UserUITestConstants;
import com.bright.appstarter.user.UserUrlConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@WebAppConfiguration
@Transactional
public class ActivateControllerGetIntegrationTest
{
	@Inject
	private WebApplicationContext webApplicationContext;

	@Inject
	private RegisterService registerService;

	@Inject
	private UserRepository userRepository;

	private MockMvc mockMvc;

	private AuthenticationSetter authenticationSetter = new AuthenticationSetter();

	private String emailAddress = "noreply@noreply.com";
	private String password = "mypassword";

	@Before
	public void setUp()
	{
		mockMvc = webAppContextSetup(webApplicationContext)
			.build();
		authenticationSetter.setFullyAuthenticatedUser();
	}

	@Test
	public void testValidTokenActivatesUser() throws Exception
	{
		GreenMail greenMail = new GreenMail();
		greenMail.start();

		registerService.register(emailAddress, password);

		String body = GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]);
		greenMail.stop();

		String activationToken = getActivationTokenFromEmail(body);

		final MockHttpServletRequestBuilder get =
						MockMvcRequestBuilders
							.get(UserUITestConstants.ACTIVATE_URL + "/" + activationToken);

		mockMvc.perform(get)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.ACTIVATE_TEMPLATE));

		User user = userRepository.findOneByEmailAddress(emailAddress).get();

		assertEquals(user.getActivationToken(), User.ACTIVATION_TOKEN_APPROVED);
	}

	private String getActivationTokenFromEmail(String emailBody)
	{
		String startofUri = UserUrlConstants.ACTIVATE_URL + "/";
		int beginIndex = emailBody.indexOf(startofUri) + startofUri.length();
		int endIndex = emailBody.indexOf("\r", beginIndex);
		return emailBody.substring(beginIndex, endIndex);

	}

}
