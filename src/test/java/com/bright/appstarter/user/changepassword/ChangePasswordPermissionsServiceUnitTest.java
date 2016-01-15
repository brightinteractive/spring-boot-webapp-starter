package com.bright.appstarter.user.changepassword;

import com.bright.appstarter.test.TestData;
import com.bright.appstarter.user.Role;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserPermissionsService;
import com.bright.appstarter.userlogin.CurrentUser;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChangePasswordPermissionsServiceUnitTest
{
	@InjectMocks
	private UserPermissionsService service;

	private TestData testData = new TestData();

	@Test
	public void testCanChangePasswordReturnsTrueForAdmin()
	{
		CurrentUser currentUser = testData.getCurrentUserWithRole(Role.ADMIN);
		assertEquals(service.canChangePassword(currentUser, 999L), true);
	}

	@Test
	public void testCanChangePasswordReturnsTrueForNonAdminUserWithSameId()
	{
		long userId = 33;
		User user = new User(userId, "test", "test");
		CurrentUser currentUser = new CurrentUser(user);
		assertEquals(service.canChangePassword(currentUser, userId), true);
	}

	@Test
	public void testCanChangePasswordReturnsFalseForNonAdminUserWithDifferentId()
	{
		User user = new User(33L, "test", "test");
		CurrentUser currentUser = new CurrentUser(user);
		assertEquals(service.canChangePassword(currentUser, 2L), false);
	}
}
