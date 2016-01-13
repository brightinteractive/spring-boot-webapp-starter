package com.bright.appstarter.testsecurity;

import com.bright.appstarter.userlogin.CurrentUser;
import com.bright.appstarter.userlogin.UIPermissions;

public class UIPermissionsFull implements UIPermissions
{
	@Override
	public void setCurrentUser(CurrentUser currentUser)
	{
	}

	@Override
	public boolean getCanViewUsersMenu()
	{
		return true;
	}

	@Override
	public boolean getCanViewAdminMenu()
	{
		return true;
	}
}
