package com.bright.appstarter.userlogin;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bright.appstarter.user.User;
import com.bright.appstarter.user.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class CurrentUserDetailsServiceUnitTest
{

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private CurrentUserDetailsService currentUserDetailsService;

	@Test
	public void testLoadUserByUsernameWithValidUsernameReturnsUser()
	{
		String email = "noreply@noreply.com";
		User user = new User(email, "password");
		when(userRepository.findOneByEmailAddress(email)).thenReturn(Optional.of(user));

		CurrentUser expectedCurrentUser = new CurrentUser(user);
		CurrentUser foundUser = currentUserDetailsService.loadUserByUsername(email);

		assertEquals(expectedCurrentUser, foundUser);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsernameWithInvalidUsernameThrowsException()
	{
		String email = "noreply@noreply.com";
		when(userRepository.findOneByEmailAddress(email)).thenReturn(Optional.empty());
		currentUserDetailsService.loadUserByUsername(email);
		fail("Expected exception not thrown");
	}
}
