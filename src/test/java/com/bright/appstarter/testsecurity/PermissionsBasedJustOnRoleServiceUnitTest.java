package com.bright.appstarter.testsecurity;

import static com.google.common.collect.Sets.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.bright.appstarter.test.TestData;
import com.bright.appstarter.user.Role;
import com.bright.appstarter.userlogin.CurrentUser;

public abstract class PermissionsBasedJustOnRoleServiceUnitTest
{
	private TestData testData = new TestData();

	protected abstract Collection<Role> getRolesThatHavePermission();

	protected abstract boolean callPermissionMethod(CurrentUser user);

	@Test
	public void testRolesWithPermissionCanCallPermissionMethodWithoutError()
	{
		for (Role role : getRolesThatHavePermission())
		{
			CurrentUser currentUser = testData.getCurrentUserWithRole(role);
			assertEquals(callPermissionMethod(currentUser), true);
		}
	}

	@Test
	public void testRolesWithoutPermissionCallingPermissionMethodReturnsFalse()
	{
		for (Role role : getRolesThatDoNotHavePermission())
		{
			CurrentUser currentUser = testData.getCurrentUserWithRole(role);
			assertEquals(callPermissionMethod(currentUser), false);
		}
	}

	private Collection<Role> getRolesThatDoNotHavePermission()
	{
		Set<Role> allRoles = new HashSet<Role>(Arrays.asList(Role.values()));
		Set<Role> rolesThatCan = new HashSet<Role>(
			getRolesThatHavePermission());

		return difference(allRoles, rolesThatCan);
	}
}
