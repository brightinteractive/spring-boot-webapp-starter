package com.bright.appstarter.user;

import com.bright.appstarter.stereotype.AppStarterService;
import com.bright.appstarter.userlogin.CurrentUser;

@AppStarterService
public class UserPermissionsService
{
	public boolean allowPublicAccess()
	{
		return true;
	}

	public boolean canViewAllUsers(CurrentUser currentUser)
	{
		if (currentUser.hasRole(Role.ADMIN))
		{
			return true;
		}

		return false;
	}

	public boolean canAddOrEditUsers(CurrentUser currentUser)
	{
		if (currentUser.hasRole(Role.ADMIN))
		{
			return true;
		}

		return false;
	}

	public boolean canViewUser(CurrentUser currentUser, long userId)
	{
		if (currentUser.hasRole(Role.ADMIN))
		{
			return true;
		}

		return currentUser.getId() == userId;
	}

	public boolean canChangePassword(CurrentUser currentUser, long userId)
	{
		if (currentUser.hasRole(Role.ADMIN))
		{
			return true;
		}

		return currentUser.getId() == userId;
	}
}
