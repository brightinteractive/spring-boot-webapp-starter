package com.bright.appstarter.test;

import java.util.Collections;

import com.bright.appstarter.testsecurity.UIPermissionsFull;
import com.bright.appstarter.user.Role;
import com.bright.appstarter.user.User;
import com.bright.appstarter.userlogin.CurrentUser;
import com.bright.appstarter.userlogin.UIPermissions;

public class TestData
{
	public CurrentUser getCurrentUserWithRole(Role role)
	{
		return new CurrentUser(getUserWithRole(role));
	}

	public CurrentUser getCurrentUserWithFullPermissions()
	{
		UIPermissions uiPermissions = new UIPermissionsFull();
		return new CurrentUser(getUserWithRole(Role.ADMIN), uiPermissions);
	}

	public CurrentUser getCurrentUserWithNoRole()
	{
		return new CurrentUser(getUserWithNoRole());
	}

	public User getUserWithRole(Role role)
	{
		return new User(9L, "test", "test", Collections.singleton(role));
	}

	public User getUserWithNoRole()
	{
		return new User(10L, "test", "test");
	}
}
