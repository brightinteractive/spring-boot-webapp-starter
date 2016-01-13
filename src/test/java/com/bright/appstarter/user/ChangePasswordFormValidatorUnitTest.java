package com.bright.appstarter.user;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.springframework.validation.*;

import com.bright.appstarter.user.ChangePasswordForm;
import com.bright.appstarter.user.ChangePasswordFormValidator;
import com.bright.appstarter.user.UserService;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordFormValidatorUnitTest
{
	@Mock
	private UserService userService;

	@InjectMocks
	private ChangePasswordFormValidator validator;

	private final long userId = 5;
	private final String currentPassword = "currentpassword";

	@Test
	public void testValidateWithWrongCurrentPasswordAddsError() throws Exception
	{
		when(userService.isCurrentPassword(userId, currentPassword)).thenReturn(false);
		Errors errors = mock(Errors.class);
		ChangePasswordForm form = new ChangePasswordForm(userId, currentPassword, "new", "new");
		validator.validate(form, errors);
		verify(errors).rejectValue(any(), eq("password.wrong"));
	}

	@Test
	public void testValidateWithCorrectCurrentPasswordDoesNotAddError() throws Exception
	{
		when(userService.isCurrentPassword(userId, currentPassword)).thenReturn(true);
		Errors errors = mock(Errors.class);
		ChangePasswordForm form = new ChangePasswordForm(userId, currentPassword, "new", "new");
		validator.validate(form, errors);
		verify(errors, never()).rejectValue(any(), any());
	}

	@Test
	public void testValidateNewAndRepeatedAreNotEqualAddsError() throws Exception
	{
		when(userService.isCurrentPassword(userId, currentPassword)).thenReturn(true);
		Errors errors = mock(Errors.class);
		ChangePasswordForm form = new ChangePasswordForm(userId, currentPassword, "new",
			"newwrong");
		validator.validate(form, errors);
		verify(errors).rejectValue(any(), eq("passwords.do.not.match"));
	}
}
