package com.bright.appstarter.user.register;

import org.springframework.stereotype.Component;

import com.bright.appstarter.user.UserBaseForm;
import com.bright.appstarter.user.UserBaseFormValidator;

@Component
public class RegisterFormValidator extends UserBaseFormValidator
{
	@Override
	public boolean supports(Class<?> clazz)
	{
		return clazz.equals(RegisterForm.class);
	}

	@Override
	protected boolean isExistingUser(UserBaseForm form)
	{
		return false;
	}

	@Override
	protected boolean newEmailAddressToCheck(UserBaseForm form)
	{
		return true;
	}
}
