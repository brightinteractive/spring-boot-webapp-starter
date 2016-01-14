package com.bright.appstarter.user.changepassword;

import org.hibernate.validator.constraints.*;

public class ChangePasswordForm
{
	private long userId;

	@NotEmpty
	private String currentPassword;

	@NotEmpty
	private String newPassword;

	@NotEmpty
	private String passwordRepeated;

	public ChangePasswordForm(long userId)
	{
		this.userId = userId;
	}

	public ChangePasswordForm(Long userId,
		String currentPassword,
		String newPassword,
		String passwordRepeated)
	{
		this.userId = userId;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.passwordRepeated = passwordRepeated;
	}

	public ChangePasswordForm()
	{
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public String getCurrentPassword()
	{
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword)
	{
		this.currentPassword = currentPassword;
	}

	public String getNewPassword()
	{
		return newPassword;
	}

	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}

	public String getPasswordRepeated()
	{
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated)
	{
		this.passwordRepeated = passwordRepeated;
	}
}
