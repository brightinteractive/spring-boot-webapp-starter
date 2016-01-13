package com.bright.appstarter.testsecurity;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.bright.appstarter.test.TestData;
import com.bright.appstarter.userlogin.CurrentUser;

public class SecurityMockMvcWrapper
{
	private MockMvc mockMvc;

	public SecurityMockMvcWrapper(WebApplicationContext webApplicationContext)
	{
		mockMvc = webAppContextSetup(webApplicationContext)
			.apply(springSecurity())
			.build();
	}

	public ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception
	{
		TestData testData = new TestData();
		CurrentUser currentUser = testData.getCurrentUserWithFullPermissions();

		return performWithUser(builder, currentUser);
	}

	public ResultActions performWithUser(MockHttpServletRequestBuilder builder,
		CurrentUser currentUser) throws Exception
	{
		builder.with(user(currentUser))
			.with(csrf());

		return mockMvc.perform(builder);
	}

}
