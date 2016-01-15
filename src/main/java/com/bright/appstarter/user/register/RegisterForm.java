package com.bright.appstarter.user.register;

import com.bright.appstarter.user.UserBaseForm;

public class RegisterForm extends UserBaseForm
{
	public RegisterForm()
	{
	}

	public RegisterForm(String emailAddress, String emptyPassword, String emptyPassword2)
	{
		super(emailAddress, emptyPassword, emptyPassword2);
	}
}
