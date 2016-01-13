package com.bright.appstarter.user;

import java.util.Collection;
import java.util.Collections;

import com.bright.appstarter.testsecurity.PermissionsBasedJustOnRoleServiceUnitTest;
import com.bright.appstarter.user.Role;
import com.bright.appstarter.user.UserPermissionsService;
import com.bright.appstarter.userlogin.CurrentUser;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CanViewAllUsersPermissionServiceUnitTest extends PermissionsBasedJustOnRoleServiceUnitTest
{
	@InjectMocks
	private UserPermissionsService service;

	@Override
	protected Collection<Role> getRolesThatHavePermission()
	{
		return Collections.singleton(Role.ADMIN);
	}

	@Override
	protected boolean callPermissionMethod(CurrentUser user)
	{
		return service.canViewAllUsers(user);
	}

}