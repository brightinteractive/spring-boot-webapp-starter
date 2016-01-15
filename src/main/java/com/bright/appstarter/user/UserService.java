package com.bright.appstarter.user;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bright.appstarter.stereotype.AppStarterService;

@AppStarterService
public class UserService
{
	@Inject
	private UserRepository userRepository;

	@Inject
	private PasswordEncoder passwordEncoder;

	@PreAuthorize("@userPermissionsService.canViewAllUsers(principal)")
	public Optional<User> getUserByEmailAddress(String username)
	{
		return userRepository.findOneByEmailAddress(username);
	}

	@PreAuthorize("@userPermissionsService.canAddOrEditUsers(principal)")
	public User createUser(String emailAddress,
		String password,
		Collection<Role> roles) throws UserAlreadyExistsException
	{
		return createUser(emailAddress,
			password,
			User.ACTIVATION_TOKEN_APPROVED,
			roles);
	}

	@PreAuthorize("@userPermissionsService.canAddOrEditUsers(principal)")
	public User createUser(String emailAddress,
		String password) throws UserAlreadyExistsException
	{
		return createUser(emailAddress, password, Collections.emptyList());
	}

	@PreAuthorize("@userPermissionsService.canAddOrEditUsers(principal)")
	public User createUser(String emailAddress,
		String password,
		String registerToken) throws UserAlreadyExistsException
	{
		return createUser(emailAddress,
			password,
			registerToken,
			Collections.emptyList());
	}

	@PreAuthorize("@userPermissionsService.canAddOrEditUsers(principal)")
	public User updateUser(long id,
		String emailAddress,
		Collection<Role> roles,
		Optional<String> newPassword,
		Optional<String> registerToken)
	{
		User existingUser = getUser(id);
		existingUser.setEmailAddress(emailAddress);
		existingUser.setRoles(roles);

		if (newPassword.isPresent())
		{
			existingUser.setPasswordHash(passwordEncoder.encode(newPassword.get()));
		}

		if (registerToken.isPresent())
		{
			existingUser.setActivationToken(registerToken.get());
		}

		return existingUser;
	}

	@PreAuthorize("true")
	public boolean emailAddressInUse(String emailAddress)
	{
		return userRepository.countByEmailAddress(emailAddress) > 0;
	}

	@PreAuthorize("@userPermissionsService.canViewUser(principal, #userId)")
	public User getUser(long userId)
	{
		User user = userRepository.findOne(userId);

		if (user == null)
		{
			throw new UserNotFoundException("There is no user with Id " + userId);
		}

		return user;
	}

	@PreAuthorize("@userPermissionsService.canChangePassword(principal, #userId)")
	public void updatePassword(long userId, String newPassword)
	{
		User user = getUser(userId);

		updateUser(user.getId().longValue(),
			user.getEmailAddress(),
			user.getRoles(),
			Optional.of(newPassword),
			Optional.empty());
	}

	@PreAuthorize("@userPermissionsService.canChangePassword(principal, #userId)")
	public boolean isCurrentPassword(long userId, String password)
	{
		User user = getUser(userId);
		return passwordEncoder.matches(password, user.getPasswordHash());
	}

	@PreAuthorize("@userPermissionsService.canViewAllUsers(principal)")
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}

	@PreAuthorize("@userPermissionsService.canAddOrEditUsers(principal)")
	public void deleteUser(long userId)
	{
		userRepository.delete(userId);
	}

	private User createUser(String emailAddress,
		String password,
		String registerToken,
		Collection<Role> roles) throws UserAlreadyExistsException
	{
		if (emailAddressInUse(emailAddress))
		{
			throw new UserAlreadyExistsException();
		}
		User user = new User(emailAddress,
			passwordEncoder.encode(password),
			registerToken,
			roles);
		return userRepository.save(user);
	}

	public static class UserAlreadyExistsException extends Exception
	{
		private static final long serialVersionUID = -7554956627144356692L;
	}

	public static class UserNotFoundException extends RuntimeException
	{
		private static final long serialVersionUID = -5569716362247885343L;

		public UserNotFoundException(String message)
		{
			super(message);
		}
	}
}
