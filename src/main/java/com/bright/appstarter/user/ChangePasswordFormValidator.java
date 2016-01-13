package com.bright.appstarter.user;

import javax.inject.*;

import org.springframework.stereotype.*;
import org.springframework.validation.*;

import org.apache.commons.lang3.*;

@Component
public class ChangePasswordFormValidator implements Validator
{
	@Inject
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz)
	{
		return clazz.equals(ChangePasswordForm.class);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		ChangePasswordForm form = (ChangePasswordForm) target;
		validateExistingPassword(errors, form);
		validateNewAndRepeatedAreEqual(errors, form);
	}

	private void validateExistingPassword(Errors errors, ChangePasswordForm form)
	{
		if (!StringUtils.isEmpty(form.getCurrentPassword())
			&& !userService.isCurrentPassword(form.getUserId(), form.getCurrentPassword()))
		{
			errors.rejectValue("currentPassword", "password.wrong");
		}
	}

	private void validateNewAndRepeatedAreEqual(Errors errors, ChangePasswordForm form)
	{
		if (!form.getNewPassword().equals(form.getPasswordRepeated()))
		{
			errors.rejectValue("passwordRepeated", "passwords.do.not.match");
		}
	}

}
