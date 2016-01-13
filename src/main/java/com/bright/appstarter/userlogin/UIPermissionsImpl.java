package com.bright.appstarter.userlogin;

import com.bright.appstarter.user.UserPermissionsService;

public class UIPermissionsImpl implements UIPermissions
{
	private CurrentUser currentUser;
	private UserPermissionsService userPermissionsService;

	public UIPermissionsImpl(UserPermissionsService userPermissionsService)
	{
		this.userPermissionsService = userPermissionsService;
	}

	@Override
	public void setCurrentUser(CurrentUser currentUser)
	{
		this.currentUser = currentUser;
	}

	@Override
	public boolean getCanViewUsersMenu()
	{
		return userPermissionsService.canAddOrEditUsers(currentUser);
	}

	@Override
	public boolean getCanViewAdminMenu()
	{
		return getCanViewUsersMenu();
	}
}
