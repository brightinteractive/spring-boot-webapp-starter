package com.bright.appstarter.user.admin;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ModelMap;

import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserService;
import com.bright.appstarter.user.UserUITestConstants;
import com.bright.appstarter.user.admin.UsersController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsersControllerGetUnitTest
{
	@Mock
	private UserService userService;

	@InjectMocks
	private UsersController controller;

	private MockMvc mockMvc;
	private List<User> allUsers;

	@Before
	public void setUp()
	{
		mockMvc = standaloneSetup(controller).build();

		allUsers = new ArrayList<>();
		allUsers.add(new User(1L, "m@c.com", "passwordHash"));
		when(userService.getAllUsers()).thenReturn(allUsers);
	}

	private MvcResult getUsers() throws Exception
	{
		final MockHttpServletRequestBuilder mvcRequest =
						MockMvcRequestBuilders.get(UserUITestConstants.USERS_URL);
		return mockMvc.perform(mvcRequest)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.USERS_TEMPLATE))
			.andReturn();
	}

	private ModelMap getUserModelMap() throws Exception
	{
		return getUsers().getModelAndView().getModelMap();
	}

	@Test
	public void testUsersIncludedInForm() throws Exception
	{
		final ModelMap modelMap = getUserModelMap();
		final List<?> users = (List<?>) modelMap.get("users");
		assertEquals(this.allUsers, users);
	}
}
