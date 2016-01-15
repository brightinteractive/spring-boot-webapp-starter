package com.bright.appstarter.user.admin;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.springframework.validation.Errors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserFormValidatorUnitTest
{
	@Mock
	private UserService userService;

	@InjectMocks
	private UserFormValidator validator;

	private final long userId = 5;
	private final String emailAddress = "noreply@noreply.com";
	private final String currentPassword = "currentpassword";

	@Before
	public void setUp()
	{
		User user = new User(userId,
			emailAddress,
			"",
			Collections.emptyList());

		when(userService.getUser(userId)).thenReturn(user);
	}

	@Test
	public void testValidateEmptyPasswordsForExistingUserDoesNotAddError() throws Exception
	{
		Errors errors = mock(Errors.class);
		String emptyPassword = null;
		UserForm form = new UserForm(userId, emailAddress, emptyPassword, emptyPassword);
		validator.validate(form, errors);
		verify(errors, never()).rejectValue(any(), any());
	}

	@Test
	public void testValidateEmptyPasswordsForNewUserAddsError() throws Exception
	{
		Errors errors = mock(Errors.class);
		String emptyPassword = null;
		UserForm form = new UserForm(null, emailAddress, emptyPassword, emptyPassword);
		validator.validate(form, errors);
		verify(errors).rejectValue(any(), eq("NotEmpty"));
	}

	@Test
	public void testValidateWithMatchingPasswordsDoesNotAddError() throws Exception
	{
		when(userService.isCurrentPassword(userId, currentPassword)).thenReturn(true);
		Errors errors = mock(Errors.class);
		UserForm form = new UserForm(userId, emailAddress, "new", "new");
		validator.validate(form, errors);
		verify(errors, never()).rejectValue(any(), any());
	}

	@Test
	public void testValidateNewAndRepeatedAreNotEqualAddsError() throws Exception
	{
		when(userService.isCurrentPassword(userId, currentPassword)).thenReturn(true);
		Errors errors = mock(Errors.class);
		UserForm form = new UserForm(userId, emailAddress, "new",
			"newwrong");
		validator.validate(form, errors);
		verify(errors).rejectValue(any(), eq("passwords.do.not.match"));
	}
}
