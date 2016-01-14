package com.bright.appstarter.user;

import static com.bright.appstarter.user.UserUITestConstants.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.ui.ModelMap;

import com.bright.appstarter.user.changepassword.ChangePasswordController;
import com.bright.appstarter.user.changepassword.ChangePasswordForm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordControllerGetUnitTest
{
	@InjectMocks
	private ChangePasswordController controller;

	private MockMvc mockMvc;
	private long userId = 5;

	@Before
	public void setUp()
	{
		mockMvc = standaloneSetup(controller).build();
	}

	private MvcResult getForm() throws Exception
	{
		final MockHttpServletRequestBuilder mvcRequest = get(CHANGE_PASSWORD_URL)
			.param("userId", "" + this.userId);
		return mockMvc.perform(mvcRequest)
			.andExpect(status().isOk())
			.andExpect(view().name(CHANGE_PASSWORD_TEMPLATE))
			.andReturn();
	}

	private ModelMap getModelMap() throws Exception
	{
		return getForm().getModelAndView().getModelMap();
	}

	@Test
	public void testFormIncludedInGet() throws Exception
	{
		final ChangePasswordForm form = (ChangePasswordForm) getModelMap().get("form");
		assertEquals(form.getUserId(), this.userId);
	}

}
