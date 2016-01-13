package com.bright.appstarter.userlogin;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bright.appstarter.stereotype.AppStarterService;
import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserPermissionsService;
import com.bright.appstarter.user.UserRepository;

@AppStarterService
public class CurrentUserDetailsService implements UserDetailsService
{
	@Inject
	@Lazy
	private UserRepository userRepository;

	@Inject
	@Lazy
	private UserPermissionsService userPermissionsService;

	/**
	 * username is email
	 */
	@Override
	public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepository.findOneByEmailAddress(username)
			.orElseThrow(
				() -> new UsernameNotFoundException("There is no user with username " + username));

		UIPermissions uiPermissions = new UIPermissionsImpl(userPermissionsService);

		return new CurrentUser(user, uiPermissions);
	}
}
