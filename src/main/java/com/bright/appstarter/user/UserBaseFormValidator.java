package com.bright.appstarter.user;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.apache.commons.lang3.StringUtils;

@Component
public abstract class UserBaseFormValidator implements Validator
{
	@Inject
	private UserService userService;

	protected abstract boolean isExistingUser(UserBaseForm form);

	protected abstract boolean newEmailAddressToCheck(UserBaseForm form);

	@Override
	public void validate(Object target, Errors errors)
	{
		UserBaseForm form = (UserBaseForm) target;
		validatePasswords(errors, form);
		validateEmailAddress(errors, form);
	}

	private void validatePasswords(Errors errors, UserBaseForm form)
	{
		if (passwordFieldsAreEmpty(form))
		{
			if (isExistingUser(form))
			{
				// Existing user - empty means leave password the same.
				return;
			}

			errors.rejectValue("password", "NotEmpty");
			return;
		}

		if (!form.getPassword().equals(form.getPasswordRepeated()))
		{
			errors.rejectValue("passwordRepeated", "passwords.do.not.match");
		}
	}

	private void validateEmailAddress(Errors errors, UserBaseForm form)
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

	private boolean passwordFieldsAreEmpty(UserBaseForm form)
	{
		return StringUtils.isEmpty(form.getPassword())
			&& StringUtils.isEmpty(form.getPasswordRepeated());
	}
}
