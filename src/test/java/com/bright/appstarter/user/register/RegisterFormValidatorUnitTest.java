package com.bright.appstarter.user.register;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.springframework.validation.Errors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bright.appstarter.user.UserService;

@RunWith(MockitoJUnitRunner.class)
public class RegisterFormValidatorUnitTest
{
	@Mock
	private UserService userService;

	@InjectMocks
	private RegisterFormValidator validator;

	private final long userId = 5;
	private final String emailAddress = "noreply@noreply.com";
	private final String currentPassword = "currentpassword";

	@Test
	public void testValidateEmptyPasswordsAddsError() throws Exception
	{
		Errors errors = mock(Errors.class);
		String emptyPassword = null;
		RegisterForm form = new RegisterForm(emailAddress, emptyPassword, emptyPassword);
		validator.validate(form, errors);
		verify(errors).rejectValue(any(), eq("NotEmpty"));
	}

	@Test
	public void testValidateWithMatchingPasswordsDoesNotAddError() throws Exception
	{
		when(userService.isCurrentPassword(userId, currentPassword)).thenReturn(true);
		Errors errors = mock(Errors.class);
		RegisterForm form = new RegisterForm(emailAddress, "new", "new");
		validator.validate(form, errors);
		verify(errors, never()).rejectValue(any(), any());
	}

	@Test
	public void testValidateNewAndRepeatedAreNotEqualAddsError() throws Exception
	{
		when(userService.isCurrentPassword(userId, currentPassword)).thenReturn(true);
		Errors errors = mock(Errors.class);
		RegisterForm form = new RegisterForm(emailAddress, "new",
			"newwrong");
		validator.validate(form, errors);
		verify(errors).rejectValue(any(), eq("passwords.do.not.match"));
	}
}
