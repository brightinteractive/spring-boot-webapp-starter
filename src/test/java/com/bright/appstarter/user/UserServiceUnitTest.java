package com.bright.appstarter.user;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.bright.appstarter.user.UserService.UserAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest
{
	@Mock
	private UserRepository userRepository;

	@Spy
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@InjectMocks
	private UserService userService;

	@Test
	public void testGetUserByEmailReturnsUser()
	{
		String email = "noreply@noreply.com";
		User user = new User(email, "password");
		when(userRepository.findOneByEmailAddress(email)).thenReturn(Optional.of(user));

		Optional<User> foundUser = userService.getUserByEmailAddress(email);

		assertEquals(user, foundUser.get());
	}

	@Test
	public void testGetUserByNonExistentEmailReturnsNoUser()
	{
		String email = "noreply@noreply.com";
		when(userRepository.findOneByEmailAddress(email)).thenReturn(Optional.empty());

		Optional<User> foundUser = userService.getUserByEmailAddress(email);

		assertFalse(foundUser.isPresent());
	}

	@Test
	public void testCreateUserCreatesUser() throws Exception
	{
		String password = "password";
		String emailAddress = "noreply@noreply.com";

		when(userRepository.save(any(User.class))).thenAnswer(
			invocation -> {
				Object[] args = invocation.getArguments();
				return args[0];
			});

		User savedUser = userService.createUser(emailAddress, password);

		assertEquals(emailAddress, savedUser.getEmailAddress());
		assertTrue(passwordEncoder.matches(password, savedUser.getPasswordHash()));
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void testCannotCreateTwoUsersWithSameEmail() throws Exception
	{
		String emailAddress = "noreply@noreply.com";
		String password = "password";

		when(userRepository.countByEmailAddress(emailAddress)).thenReturn(0L).thenReturn(1L);
		when(userRepository.save(any(User.class))).thenAnswer(
			invocation -> {
				Object[] args = invocation.getArguments();
				return args[0];
			});

		userService.createUser(emailAddress, password);
		userService.createUser(emailAddress, password);
		fail("Exception not thrown");
	}

	@Test
	public void testIsCurrentPasswordReturnsFalseForWrongPassword() throws Exception
	{
		String password = "password";
		String passwordHash = "passwordhash";
		long userId = 5L;

		User user = new User(userId, "", passwordHash);

		when(userRepository.findOne(userId)).thenReturn(user);
		when(passwordEncoder.matches(passwordHash, password)).thenReturn(false);

		boolean isCurrent = userService.isCurrentPassword(userId, passwordHash);
		assertEquals(isCurrent, false);
	}

}
