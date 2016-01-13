package com.bright.appstarter.userlogin;

import static org.junit.Assert.*;

import org.springframework.mock.web.MockHttpServletRequest;

import com.bright.appstarter.userlogin.ParameterKeepingRedirectAuthenticationFailureHandler;

import org.junit.Test;

public class ParameterKeepingRedirectAuthenticationFailureHandlerUnitTest
{
	private final static String USERNAME_PARAM_NAME = "username";
	private final static String REMEMBER_ME_PARAM_NAME = "remember-me";

	@Test
	public void testLoadUserByUsernameWithValidUsernameReturnsUser()
	{
		ParameterKeepingRedirectAuthenticationFailureHandler handler =
			new ParameterKeepingRedirectAuthenticationFailureHandler(
				"/login?error", USERNAME_PARAM_NAME, REMEMBER_ME_PARAM_NAME);

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter(USERNAME_PARAM_NAME, "martin");
		request.addParameter(REMEMBER_ME_PARAM_NAME, "on");

		String redirectUrl = handler.getRedirectUrlWithAddedParams(request);
		assertEquals(redirectUrl, "/login?error&username=martin&remember-me=on");
	}
}
