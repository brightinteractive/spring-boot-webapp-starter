package com.bright.appstarter.user.admin;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserService;

import org.apache.commons.lang3.StringUtils;

@Component
public class UserFormValidator implements Validator
{
	@Inject
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz)
	{
		return clazz.equals(UserForm.class);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		UserForm form = (UserForm) target;
		validatePasswords(errors, form);
		validateEmailAddress(errors, form);
	}

	private void validatePasswords(Errors errors, UserForm form)
	{
		if (passwordFieldsAreEmpty(form))
		{
			if (isExistingUser(form))
			{
				// Existing user - empty means leave password the same.
				return;
			}

			errors.rejectValue("password", "NotEmpty");
		}

		if (!form.getPassword().equals(form.getPasswordRepeated()))
		{
			errors.rejectValue("passwordRepeated", "passwords.do.not.match");
		}
	}

	private void validateEmailAddress(Errors errors, UserForm form)
	{
		if (!newEmailAddressToCheck(form))
		{
			return;
		}

		if (userService.emailAddressInUse(form.getEmailAddress()))
		{
			errors.rejectValue("emailAddress", "email.address.in.use");
		}
	}

	private boolean isExistingUser(UserForm form)
	{
		return form.getId() != null;
	}

	private boolean passwordFieldsAreEmpty(UserForm form)
	{
		return StringUtils.isEmpty(form.getPassword())
			&& StringUtils.isEmpty(form.getPasswordRepeated());
	}

	private boolean newEmailAddressToCheck(UserForm form)
	{
		if (isExistingUser(form))
		{
			User user = userService.getUser(form.getId());

			if (user.getEmailAddress().equalsIgnoreCase(form.getEmailAddress()))
			{
				return false;
			}
		}

		return true;
	}
}
