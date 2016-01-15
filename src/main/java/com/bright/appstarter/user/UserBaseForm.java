package com.bright.appstarter.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public abstract class UserBaseForm
{
	@NotEmpty
	@Email
	private String emailAddress;

	private String password;
	private String passwordRepeated;

	public UserBaseForm()
	{
	}

	public UserBaseForm(String emailAddress,
		String password)
	{
		this.emailAddress = emailAddress;
		this.password = password;
	}

	public UserBaseForm(String emailAddress,
		String password,
		String passwordRepeated)
	{
		this.emailAddress = emailAddress;
		this.password = password;
		this.passwordRepeated = passwordRepeated;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPasswordRepeated()
	{
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeat)
	{
		this.passwordRepeated = passwordRepeat;
	}

	public void populateFromUser(User user)
	{
		this.emailAddress = user.getEmailAddress();
	}
}
