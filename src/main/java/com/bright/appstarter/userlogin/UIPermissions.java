package com.bright.appstarter.userlogin;

public interface UIPermissions
{
	public void setCurrentUser(CurrentUser currentUser);

	public boolean getCanViewAdminMenu();

	public boolean getCanViewUsersMenu();
}
