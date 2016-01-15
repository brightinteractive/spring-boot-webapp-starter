package com.bright.appstarter.user.admin;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ModelMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bright.appstarter.user.Role;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserService;
import com.bright.appstarter.user.UserUITestConstants;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerUnitTest
{
	@Mock
	private UserService userService;

	@InjectMocks
	private UserController controller;

	private MockMvc mockMvc;

	private User user;
	private final long userId = 11;
	private final String emailAddress = "noreply@noreply.com";
	private final String passwordHash = "passwordHash";
	private Collection<Role> expectedRoles;

	@Before
	public void setUp()
	{
		mockMvc = standaloneSetup(controller).build();

		expectedRoles = new HashSet<>();

		user = new User(userId,
			emailAddress,
			passwordHash,
			expectedRoles);

		when(userService.getUser(userId)).thenReturn(user);
	}

	private MvcResult getUser(Optional<Long> optionalUserId) throws Exception
	{
		final MockHttpServletRequestBuilder mvcRequest =
						MockMvcRequestBuilders.get(UserUITestConstants.USER_URL);

		if (optionalUserId.isPresent())
		{
			mvcRequest.param("id", String.valueOf(optionalUserId.get()));
		}

		return mockMvc.perform(mvcRequest)
			.andExpect(status().isOk())
			.andExpect(view().name(UserUITestConstants.USER_TEMPLATE))
			.andReturn();
	}

	private ModelMap getUserModelMap(Optional<Long> optionalUserId) throws Exception
	{
		return getUser(optionalUserId).getModelAndView().getModelMap();
	}

	@Test
	public void testWhenIdSpecifiedUserFormIsPopulatedWithUser() throws Exception
	{
		final ModelMap modelMap = getUserModelMap(Optional.of(userId));
		final UserForm form = (UserForm) modelMap.get("form");
		assertEquals(form.getEmailAddress(), emailAddress);
		assertEquals(form.getRoles(), expectedRoles);
	}

	@Test
	public void testUserFormIsIncludedInForm() throws Exception
	{
		final ModelMap modelMap = getUserModelMap(Optional.empty());
		final UserForm form = (UserForm) modelMap.get("form");
		assertNotNull(form);
	}

	@Test
	public void testRoleOptionsIncludedInForm() throws Exception
	{
		final ModelMap modelMap = getUserModelMap(Optional.empty());
		final Map<?, ?> roleMap = (Map<?, ?>) modelMap.get("roleOptions");
		assertNotNull(roleMap);
		assertTrue(roleMap.containsKey(Role.ADMIN.toString()));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateUserUsingFormCallsCreatesUser() throws Exception
	{
		String emailAddress = "noreply@noreply.com";
		String password = "password";

		Collection<Role> roles = new HashSet<>();
		roles.add(Role.ADMIN);

		UserForm userForm = new UserForm(emailAddress,
			password,
			roles);

		when(userService.createUser(emailAddress, password, roles)).thenAnswer(
			invocation ->
			{
				Object[] args = invocation.getArguments();
				return new User(1L,
					(String) args[0],
					(String) args[1],
					(Collection<Role>) args[2]);
			});

		User savedUser = controller.saveForm(userForm);

		assertEquals(emailAddress, savedUser.getEmailAddress());
		assertEquals(roles, savedUser.getRoles());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateUserUsingFormCallsUpdatesUser() throws Exception
	{
		Long userId = 3L;
		String emailAddress = "noreply@noreply.com";
		String password = "password";
		Optional<String> optionalPassword = Optional.of(password);

		Collection<Role> roles = new HashSet<>();
		roles.add(Role.ADMIN);

		UserForm userForm = new UserForm(userId,
			emailAddress,
			optionalPassword.get(),
			roles);

		when(
			userService.updateUser(userId, emailAddress, roles, optionalPassword, Optional.empty()))
			.thenAnswer(
				invocation ->
				{
					Object[] args = invocation.getArguments();
					return new User((Long) args[0],
						(String) args[1],
						((Optional<String>) args[3]).get(),
						(Collection<Role>) args[2]);
				});

		User savedUser = controller.saveForm(userForm);

		assertEquals(userId, savedUser.getId());
		assertEquals(emailAddress, savedUser.getEmailAddress());
		assertEquals(roles, savedUser.getRoles());
	}

}
