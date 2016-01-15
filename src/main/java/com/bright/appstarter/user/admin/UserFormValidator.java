package com.bright.appstarter.user.admin;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserBaseForm;
import com.bright.appstarter.user.UserBaseFormValidator;
import com.bright.appstarter.user.UserService;

@Component
public class UserFormValidator extends UserBaseFormValidator
{
	@Inject
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz)
	{
		return clazz.equals(UserForm.class);
	}

	@Override
	protected boolean isExistingUser(UserBaseForm form)
	{
		UserForm userForm = (UserForm) form;
		return userForm.getId() != null;
	}

	@Override
	protected boolean newEmailAddressToCheck(UserBaseForm form)
	{
		UserForm userForm = (UserForm) form;

		if (isExistingUser(userForm))
		{
			User user = userService.getUser(userForm.getId());

			if (user.getEmailAddress().equalsIgnoreCase(userForm.getEmailAddress()))
			{
				return false;
			}
		}

		return true;
	}
}
