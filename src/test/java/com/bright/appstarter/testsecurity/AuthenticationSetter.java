package com.bright.appstarter.testsecurity;

import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bright.appstarter.user.Role;
import com.bright.appstarter.user.User;
import com.bright.appstarter.userlogin.CurrentUser;
import com.bright.appstarter.userlogin.UIPermissions;

public class AuthenticationSetter
{
	public void setAuthenticatedUser(CurrentUser currentUser)
	{
		Authentication auth =
						new UsernamePasswordAuthenticationToken(currentUser, "password",
							currentUser.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	public void setFullyAuthenticatedUser()
	{
		UIPermissions uiPermissions = new UIPermissionsFull();
		User user = new User(1L, "test", "test", Collections.singleton(Role.ADMIN));
		CurrentUser currentUser = new CurrentUser(user, uiPermissions);
		setAuthenticatedUser(currentUser);
	}

	public void removeAuthenticationPrincipal()
	{
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}
