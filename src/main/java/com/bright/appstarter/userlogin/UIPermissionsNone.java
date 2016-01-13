package com.bright.appstarter.userlogin;

public class UIPermissionsNone implements UIPermissions
{
	@Override
	public void setCurrentUser(CurrentUser currentUser)
	{
	}

	@Override
	public boolean getCanViewUsersMenu()
	{
		return false;
	}

	@Override
	public boolean getCanViewAdminMenu()
	{
		return false;
	}
}
